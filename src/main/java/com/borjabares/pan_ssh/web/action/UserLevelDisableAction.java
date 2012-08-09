package com.borjabares.pan_ssh.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.modelutil.exceptions.InstanceNotFoundException;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames.Level;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("secure")
@Action(value = "user_disable", results = { @Result(type = "redirect", location = "/user_list/") })
@SuppressWarnings("serial")
public class UserLevelDisableAction extends ActionSupport {

	@Autowired
	private PanService panService;
	private User user;
	private long id;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String execute() throws InstanceNotFoundException {

		user = panService.findUser(id);

		if (user.getLevel() != Level.GOD) {
			user.setLevel(Level.DISABLED);
		}

		panService.updateUser(user);

		return SUCCESS;
	}

}