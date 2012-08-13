package com.borjabares.pan_ssh.model.panservice;

import java.util.Calendar;

import com.borjabares.pan_ssh.model.links.Links;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames.LinkStatus;

public class FullLink {

	private Links link;
	private long numberOfVotes;
	private boolean votable;
	private boolean voted;
	private boolean mine;
	private boolean editable;
	private boolean userDisposable;
	private boolean adminDisposable;

	public FullLink() {
	}

	public FullLink(Links link, long numberOfVotes, boolean voted, User user) {
		this.link = link;
		this.numberOfVotes = numberOfVotes;
		this.voted = voted;
		this.adminDisposable = false;
		this.votable = !(link.getStatus() == LinkStatus.DISCARD || link
				.getStatus() == LinkStatus.BANNED);
		if (user != null) {
			this.mine = link.getLinkAuthor().getUserId() == user.getUserId();
			Calendar actual = Calendar.getInstance();
			actual.roll(Calendar.HOUR_OF_DAY, false);
			this.editable = link.getSubmited().after(actual);
			actual.roll(Calendar.HOUR_OF_DAY, true);
			actual.roll(Calendar.DAY_OF_MONTH, false);
			if (link.getStatus() == LinkStatus.QUEUED) {
				if (mine) {
					this.userDisposable = link.getSubmited().after(actual);
				}
				this.adminDisposable = true;
			}
			if (link.getStatus() == LinkStatus.PUBLISHED) {
				this.adminDisposable = true;
			}
		}
	}

	public FullLink(Links link, long numberOfVotes, boolean votable,
			boolean voted, boolean mine, boolean editable,
			boolean userDisposable, boolean adminDisposable) {
		this.link = link;
		this.numberOfVotes = numberOfVotes;
		this.votable = votable;
		this.voted = voted;
		this.mine = mine;
		this.editable = editable;
		this.userDisposable = userDisposable;
		this.adminDisposable = adminDisposable;
	}

	public Links getLink() {
		return link;
	}

	public void setLink(Links link) {
		this.link = link;
	}

	public long getNumberOfVotes() {
		return numberOfVotes;
	}

	public void setNumberOfVotes(long numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}

	public boolean getVotable() {
		return votable;
	}

	public void setVotable(boolean votable) {
		this.votable = votable;
	}

	public boolean getVoted() {
		return voted;
	}

	public void setVoted(boolean voted) {
		this.voted = voted;
	}

	public boolean getMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public boolean getEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean getUserDisposable() {
		return userDisposable;
	}

	public void setUserDisposable(boolean userDisposable) {
		this.userDisposable = userDisposable;
	}

	public boolean getAdminDisposable() {
		return adminDisposable;
	}

	public void setAdminDisposable(boolean adminDisposable) {
		this.adminDisposable = adminDisposable;
	}

}
