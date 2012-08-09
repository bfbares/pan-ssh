package com.borjabares.pan_ssh.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.opensymphony.xwork2.ActionSupport;

@Action(value = "user/{login}", results = { @Result(location = "/userInfo") })
@SuppressWarnings("serial")
public class UserInfoAction extends ActionSupport {
	
	@Autowired
	private PanService panService;
	private User user;
	private String login;

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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String execute(){
		if(login==null){
			return ERROR;
		}
		
		user = panService.findUserByLogin(login);
		
		if (user == null){
			return ERROR;
		}
		
		return SUCCESS;
	}

}
