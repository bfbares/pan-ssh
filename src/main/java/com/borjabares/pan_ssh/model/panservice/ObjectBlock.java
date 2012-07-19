package com.borjabares.pan_ssh.model.panservice;

import java.util.List;

public class ObjectBlock<O> {
	private List<O> list;
	private int pageNum;

	public ObjectBlock(List<O> list, int pageNum) {
		this.list = list;
		this.pageNum = pageNum;
	}

	public List<O> getList() {
		return list;
	}

	public int getPageNum() {
		return pageNum;
	}
}
