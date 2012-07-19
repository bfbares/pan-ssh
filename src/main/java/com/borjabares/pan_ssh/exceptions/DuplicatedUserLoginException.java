package com.borjabares.pan_ssh.exceptions;

@SuppressWarnings("serial")
public class DuplicatedUserLoginException extends Exception {

	private String login;

	public DuplicatedUserLoginException(String login) {
		super("User " + login + "already exists");
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

}
