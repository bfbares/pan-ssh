package com.borjabares.pan_ssh.exceptions;

@SuppressWarnings("serial")
public class DuplicatedUserEmailException extends Exception {
	private String email;

	public DuplicatedUserEmailException(String email) {
		super("Email " + email + "already registered");
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

}
