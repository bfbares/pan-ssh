package com.borjabares.pan_ssh.model.panservice;

import java.util.Calendar;

import com.borjabares.pan_ssh.model.comment.Comment;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames.CommentStatus;

public class FullComment {
	private Comment comment;
	private long numberOfVotes;
	private boolean mine;
	private boolean votable;
	private boolean userEditable;
	private boolean adminEditable;

	public FullComment() {
	}

	public FullComment(Comment comment, long numberOfVotes, boolean voted, User user) {
		this.comment = comment;
		this.numberOfVotes = numberOfVotes;
		this.votable = (comment.getStatus() == CommentStatus.ACTIVE && !voted);
		Calendar actual = Calendar.getInstance();
		actual.add(Calendar.HOUR_OF_DAY, -1);
		this.userEditable = comment.getSubmited().after(actual);
		actual.add(Calendar.HOUR_OF_DAY, -1);
		actual.add(Calendar.HOUR_OF_DAY, -1);
		this.adminEditable = comment.getSubmited().after(actual);
		if (user != null){
			this.mine = comment.getAuthor().getUserId() == user.getUserId();
		} else {
			this.mine = false;
			this.votable = false;
		}
		this.userEditable = this.userEditable && this.mine;
		if (comment.getStatus() == CommentStatus.INACTIVE){
			this.votable = false;
			this.userEditable = false;
		}
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public long getNumberOfVotes() {
		return numberOfVotes;
	}

	public void setNumberOfVotes(long numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}

	public boolean getMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public boolean getVotable() {
		return votable;
	}

	public void setVotable(boolean votable) {
		this.votable = votable;
	}

	public boolean getUserEditable() {
		return userEditable;
	}

	public void setUserEditable(boolean userEditable) {
		this.userEditable = userEditable;
	}

	public boolean getAdminEditable() {
		return adminEditable;
	}

	public void setAdminEditable(boolean adminEditable) {
		this.adminEditable = adminEditable;
	}

}
