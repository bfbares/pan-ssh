package com.borjabares.pan_ssh.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.model.panservice.ObjectBlock;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("secure")
@Action(value = "user_list", results = { @Result(location = "/userList") })
@SuppressWarnings("serial")
public class UserListAction extends ActionSupport {
	@Autowired
	private PanService panService;
	private ObjectBlock<User> objectBlock;
	private int page;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	public ObjectBlock<User> getObjectBlock() {
		return objectBlock;
	}

	public void setObjectBlock(ObjectBlock<User> objectBlock) {
		this.objectBlock = objectBlock;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String execute(){
		int count=25;
		
		objectBlock = panService.listAllUsers(page*count, count);
		
		return SUCCESS;
	}
}
