package com.borjabares.pan_ssh.exceptions;

@SuppressWarnings("serial")
public class DuplicatedCategoryNameException extends Exception {
	private String name;

	public DuplicatedCategoryNameException(String name) {
		super("Category with "+ name + "already exists");
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
