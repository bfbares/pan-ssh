package com.borjabares.pan_ssh.model.linkvote;

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

import com.borjabares.pan_ssh.model.links.Links;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames.VoteType;

@Entity
@org.hibernate.annotations.Entity(mutable = false)
public class LinkVote {
	private long voteId;
	private Links link;
	private User user;
	private Calendar submited;
	private float karma;
	private VoteType type;
	private String ip;

	public LinkVote() {
	}

	public LinkVote(Links link, VoteType type, String ip) {
		this.link = link;
		this.submited = Calendar.getInstance();
		this.karma = 4.0F;
		this.type = type;
		this.ip = ip;
	}

	public LinkVote(Links link, User user, VoteType type, String ip) {
		this.link = link;
		this.user = user;
		this.submited = Calendar.getInstance();
		this.karma = user.getKarma();
		this.type = type;
		this.ip = ip;
	}

	@SequenceGenerator(name = "LinkVoteIdGenerator", sequenceName = "LinkVoteSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "LinkVoteIdGenerator")
	public long getVoteId() {
		return voteId;
	}

	public void setVoteId(long voteId) {
		this.voteId = voteId;
	}

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "linkId")
	public Links getLink() {
		return link;
	}

	public void setLink(Links link) {
		this.link = link;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
