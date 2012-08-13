package com.borjabares.pan_ssh.model.category;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.borjabares.pan_ssh.util.FriendlyTitleGenerator;
import com.borjabares.pan_ssh.util.Trimmer;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Entity
@Validations(
		requiredStrings = {
		@RequiredStringValidator(fieldName = "name", message = "Debe proporcionar un nombre para la categoría.", key = "error.name.required", trim = true, shortCircuit = true)}, 
		stringLengthFields = {
		@StringLengthFieldValidator(fieldName = "name", minLength = "4", maxLength = "15", trim = true, message = "El nombre de la categoría debe tener entre ${minLength} y ${maxLength} caracteres.", key = "error.name.length")}
)
public class Category {
	private long categoryId;
	private String name;
	private Category parent;

	public Category() {
	}

	public Category(String name, Category parent) {
		this.name = name;
		this.parent = parent;
	}

	@SequenceGenerator(name = "CategoryIdGenerator", sequenceName = "CategorySeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CategoryIdGenerator")
	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = Trimmer.trim(name);
	}

	@ManyToOne(optional=true, fetch=FetchType.EAGER) 
	@JoinColumn(name="parent", nullable = true)
	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}
	
	public String toFriendly(){
		return FriendlyTitleGenerator.stripDiacritics(name).toLowerCase();
	}

	@Override
	public String toString() {
		return "Category [\ncategoryId=" + categoryId + ", \nname=" + name
				+ ", \nparent=" + parent + "]";
	}
}
