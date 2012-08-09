package com.borjabares.pan_ssh.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.modelutil.exceptions.InstanceNotFoundException;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames.Level;
import com.borjabares.pan_ssh.web.interceptor.UserAware;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("user")
@Action(value = "user_autodisable", results = { @Result(type = "redirect", location = "/logout") })
@SuppressWarnings("serial")
public class UserLevelAutodisableAction extends ActionSupport implements
		UserAware {

	@Autowired
	private PanService panService;
	private User user;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	public String execute() throws InstanceNotFoundException {

		if (user.getLevel() != Level.GOD) {
			user.setLevel(Level.AUTODISABLED);
		}

		panService.updateUser(user);

		return SUCCESS;
	}

}