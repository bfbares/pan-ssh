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
@Action(value = "user_list/{criteria}/{page}", results = { @Result(location = "/userList") })
@SuppressWarnings("serial")
public class UserListAction extends ActionSupport {
	@Autowired
	private PanService panService;
	private ObjectBlock<User> objectBlock;
	private String criteria;
	private int page;
	private String actionurl;

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

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	public void setPage(String page) {
		try{
			this.page = Integer.parseInt(page);
		} catch (NumberFormatException e){
			this.page = 1;
		}
	}
	
	public void setPage(String[] page) {
		try{
			this.page = Integer.parseInt(page[0]);
		} catch (NumberFormatException e){
			this.page = 1;
		}
	}

	public String getActionurl() {
		return actionurl;
	}

	public void setActionurl(String actionurl) {
		this.actionurl = actionurl;
	}

	public String execute() {
		int count = 10;
		
		actionurl = "/user_list/"+criteria;

		if (page <= 0) {
			page = 1;
		}

		objectBlock = panService.listUsersByCriteria((page -1)*count, count, criteria);

		return SUCCESS;
	}
}
