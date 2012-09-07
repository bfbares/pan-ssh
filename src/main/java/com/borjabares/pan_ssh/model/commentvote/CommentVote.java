package com.borjabares.pan_ssh.model.commentvote;

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

import com.borjabares.pan_ssh.model.comment.Comment;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames.VoteType;

@Entity
@org.hibernate.annotations.Entity(mutable = false)
public class CommentVote {
	private long voteId;
	private Comment comment;
	private User user;
	private Calendar submited;
	private float karma;
	private VoteType type;

	public CommentVote() {
		this.submited = Calendar.getInstance();
	}

	public CommentVote(Comment comment, User user, VoteType type) {
		this.comment = comment;
		this.user = user;
		this.submited = Calendar.getInstance();
		this.karma = user.getKarma();
		this.type = type;
	}

	@SequenceGenerator(name = "CommentVoteIdGenerator", sequenceName = "CommentVoteSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CommentVoteIdGenerator")
	public long getVoteId() {
		return voteId;
	}

	public void setVoteId(long voteId) {
		this.voteId = voteId;
	}

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "commentId")
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	
	@Enumerated(EnumType.STRING)
	public VoteType getType() {
		return type;
	}

	public void setType(VoteType type) {
		this.type = type;
	}

}
