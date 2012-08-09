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

@ParentPackage("god")
@SuppressWarnings("serial")
public class UserLevelAdminAction extends ActionSupport {

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

	@Action(value = "user_admin", results = { @Result(type = "redirect", location = "/user_list/") })
	public String admin() throws InstanceNotFoundException {

		user = panService.findUser(id);

		if (user.getLevel() != Level.GOD) {
			user.setLevel(Level.ADMIN);
		}

		panService.updateUser(user);

		return SUCCESS;
	}

	@Action(value = "user_normal", results = { @Result(type = "redirect", location = "/user_list/") })
	public String normal() throws InstanceNotFoundException {

		user = panService.findUser(id);

		if (user.getLevel() != Level.GOD) {
			user.setLevel(Level.NORMAL);
		}

		panService.updateUser(user);

		return SUCCESS;
	}

}
