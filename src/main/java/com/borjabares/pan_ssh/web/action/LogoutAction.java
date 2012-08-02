package com.borjabares.pan_ssh.web.action;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.borjabares.pan_ssh.util.GlobalNames;
import com.opensymphony.xwork2.ActionSupport;

@Action(value = "logout", results = { @Result(type = "redirect", location = "/index") })
@SuppressWarnings("serial")
public class LogoutAction extends ActionSupport implements SessionAware,
		ServletResponseAware {

	private Map<String, Object> session;
	private HttpServletResponse response;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String execute() {

		if (!session.containsKey(GlobalNames.USER)) {
			return ERROR;
		}

		Cookie cookie = new Cookie(GlobalNames.COOKIEUSER, null);
		cookie.setMaxAge(0); // Deletes Cookie
		response.addCookie(cookie);
		
		session.remove(GlobalNames.USER);

		return SUCCESS;
	}

}
