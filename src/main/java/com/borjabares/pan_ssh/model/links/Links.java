package com.borjabares.pan_ssh.model.links;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Type;

import com.borjabares.pan_ssh.model.category.Category;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.FriendlyTitleGenerator;
import com.borjabares.pan_ssh.util.GlobalNames.LinkStatus;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.UrlValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Entity
@Validations(requiredStrings = {
		@RequiredStringValidator(fieldName = "url", message = "Debe proporcionar un enlace.", key = "error.url.required", trim = true, shortCircuit = true),
		@RequiredStringValidator(fieldName = "title", message = "Debe proporcionar un título para la noticia.", key = "error.title.required", shortCircuit = true),
		@RequiredStringValidator(fieldName = "description", message = "Debe proporcionar una descripción.", key = "error.description.required", trim = true, shortCircuit = true) }, 
		stringLengthFields = {
		@StringLengthFieldValidator(fieldName = "title", minLength = "25", maxLength = "120", trim = true, message = "El título debe tener entre ${minLength} y ${maxLength} caracteres.", key = "error.title.length"),
		@StringLengthFieldValidator(fieldName = "description", minLength = "50", maxLength = "360", trim = true, message = "La descripción debe tener entre ${minLength} y ${maxLength} caracteres.", key = "error.description.length") }, 
		urls = { @UrlValidator(fieldName = "url", message = "Debe introducir una url válida.", key = "error.url.validator")}
)
public class Links {
	private long linkId;
	private User linkAuthor;
	private Category categoryId;
	private String url;
	private String title;
	private String ftitle;
	private String description;
	private Calendar submited;
	private Calendar published;
	private float karma;
	private String tags;
	private LinkStatus status;
	private long version;

	public Links(){
		this.submited = Calendar.getInstance();
		this.karma = 0.0F;
		this.status = LinkStatus.QUEUED;
	}

	public Links(String url, String title, String description, String tags, User user, Category category) {
		this.url = url;
		this.title = title;
		this.ftitle = FriendlyTitleGenerator.generate(title);
		this.description = description;
		this.tags = tags;
		this.linkAuthor = user;
		this.categoryId=category;
		this.submited = Calendar.getInstance();
		this.karma = 0.0F;
		this.status = LinkStatus.QUEUED;
	}

	@SequenceGenerator(name = "LinkIdGenerator", sequenceName = "LinkSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "LinkIdGenerator")
	public long getLinkId() {
		return linkId;
	}

	public void setLinkId(long linkId) {
		this.linkId = linkId;
	}

	@ManyToOne(optional=false, fetch=FetchType.EAGER) 
	@JoinColumn(name="linkAuthor")
	public User getLinkAuthor() {
		return linkAuthor;
	}

	public void setLinkAuthor(User linkAuthor) {
		this.linkAuthor = linkAuthor;
	}
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER) 
	@JoinColumn(name="categoryId")
	public Category getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Category categoryId) {
		this.categoryId = categoryId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFtitle() {
		return ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	@Type(type="text")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getSubmited() {
		return submited;
	}

	public void setSubmited(Calendar submited) {
		this.submited = submited;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getPublished() {
		return published;
	}

	public void setPublished(Calendar published) {
		this.published = published;
	}

	public float getKarma() {
		return karma;
	}

	public void setKarma(float karma) {
		this.karma = karma;
	}
	
	public void addKarma(float karma) {
		this.karma += karma;
	}

	@Type(type="text")
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@Enumerated(EnumType.STRING)
	public LinkStatus getStatus() {
		return status;
	}

	public void setStatus(LinkStatus status) {
		this.status = status;
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
		return "Links [\nlinkId=" + linkId + ", \nlinkAuthor=" + linkAuthor.getLogin()
				+ ", \ncategoryId=" + categoryId + ", \nurl=" + url + ", \ntitle="
				+ title + ", \ndescription=" + description + ", \nsubmited="
				+ submited.get(Calendar.DAY_OF_MONTH) + "/" + (submited.get(Calendar.MONTH)
						+ 1 )+ "/" + submited.get(Calendar.YEAR) + ", \nkarma=" + karma + ", \ntags=" + tags
				+ ", \nstatus=" + status + ", \nversion=" + version + "]";
	}
}
