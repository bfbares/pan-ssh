package com.borjabares.pan_ssh.exceptions;

@SuppressWarnings("serial")
public class DuplicatedLinkException extends Exception {

	private String link;

	public DuplicatedLinkException(String link) {
		super("Link " + link + "already submited");
		this.link = link;
	}

	public String getLink() {
		return link;
	}

}
