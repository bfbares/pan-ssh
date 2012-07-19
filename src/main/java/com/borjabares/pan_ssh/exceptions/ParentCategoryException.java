package com.borjabares.pan_ssh.exceptions;

@SuppressWarnings("serial")
public class ParentCategoryException extends Exception {

	private long parent;

	public ParentCategoryException(long parent) {
		super("Category with id: "+ parent +" not found");
		this.parent = parent;
	}

	public long getParent() {
		return parent;
	}
}
