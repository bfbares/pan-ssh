package com.borjabares.pan_ssh.model.comment;

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

import org.hibernate.annotations.Type;

import com.borjabares.pan_ssh.model.links.Links;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames.CommentStatus;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Entity
@Validations(stringLengthFields = {
		@StringLengthFieldValidator(fieldName = "comment", maxLength = "600", trim = true, message = "El comentario no puede exceder los ${maxLength} caracteres.", key = "error.comment.length") }
)
public class Comment {
	private long commentId;
	private Links link;
	private User author;
	private String comment;
	private Calendar submited;
	private float karma;
	private CommentStatus status;

	public Comment() {
		this.karma = 0;
		this.submited = Calendar.getInstance();
		this.status = CommentStatus.ACTIVE;
	}

	public Comment(Links link, User author, String comment) {
		this.link = link;
		this.author = author;
		this.comment = comment;
		this.submited = Calendar.getInstance();
		this.karma = 0;
		this.status = CommentStatus.ACTIVE;
	}

	@SequenceGenerator(name = "CommentIdGenerator", sequenceName = "CommentSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CommentIdGenerator")
	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY) 
	@JoinColumn(name="linkId")
	public Links getLink() {
		return link;
	}

	public void setLink(Links link) {
		this.link = link;
	}

	@ManyToOne(optional=false, fetch=FetchType.EAGER) 
	@JoinColumn(name="userId")
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	@Type(type="text")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getSubmited() {
		return submited;
	}

	public void setSubmited(Calendar submited) {
		this.submited = submited;
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

	@Enumerated(EnumType.STRING)
	public CommentStatus getStatus() {
		return status;
	}

	public void setStatus(CommentStatus status) {
		this.status = status;
	}

}
