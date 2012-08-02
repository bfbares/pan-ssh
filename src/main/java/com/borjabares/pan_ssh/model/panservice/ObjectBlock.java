package com.borjabares.pan_ssh.model.panservice;

import java.util.List;

public class ObjectBlock<O> {
	private List<O> list;
	private Pagination pagination;

	public ObjectBlock(List<O> list,int startIndex, int count, int total) {
		this.list = list;
		this.pagination = new Pagination(startIndex, count, total);
	}

	public List<O> getList() {
		return list;
	}

	public Pagination getPagination() {
		return pagination;
	}

}
