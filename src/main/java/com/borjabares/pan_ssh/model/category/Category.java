package com.borjabares.pan_ssh.model.category;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

@Entity
public class Category {
	private long categoryId;
	private String name;
	private long parent;
	private long version;

	public Category() {
	}

	public Category(String name, long parent) {
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
		this.name = name;
	}

	public long getParent() {
		return parent;
	}

	public void setParent(long parent) {
		this.parent = parent;
	}

	@Version
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Category [\ncategoryId=" + categoryId + ", \nname=" + name
				+ ", \nparent=" + parent + ", \nversion=" + version + "]";
	}
}
