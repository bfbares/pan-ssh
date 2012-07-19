package com.borjabares.pan_ssh.exceptions;

@SuppressWarnings("serial")
public class UserVotedException extends Exception {
	private long userId;
	private long linkId;

	public UserVotedException(long userId, long linkId) {
		super(userId + " already voted link number " + linkId);
		this.userId = userId;
		this.linkId = linkId;
	}

	public long getUserId() {
		return userId;
	}

	public long getLinkId() {
		return linkId;
	}
}
