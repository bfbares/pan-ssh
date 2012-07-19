package com.borjabares.pan_ssh.exceptions;

@SuppressWarnings("serial")
public class LinkAlreadyReportedException extends Exception {
	private long linkId;

	public LinkAlreadyReportedException(long linkId) {
		super("This links has been already reported");
		this.linkId = linkId;
	}

	public long getLinkId() {
		return linkId;
	}
}
