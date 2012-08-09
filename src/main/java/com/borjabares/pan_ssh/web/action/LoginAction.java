package com.borjabares.pan_ssh.web.action;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames;
import com.borjabares.pan_ssh.util.GlobalNames.Level;
import com.borjabares.pan_ssh.util.PasswordCodificator;
import com.borjabares.pan_ssh.util.Trimmer;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Action(value = "login", results = {
		@Result(type = "redirect", location = "/index/"),
		@Result(name = "input", location = "/loginForm") })
@SuppressWarnings("serial")
@Validations(requiredStrings = {
		@RequiredStringValidator(fieldName = "login", message = "Debe proporcionar un nombre de usuario.", key = "error.login.required", trim = true),
		@RequiredStringValidator(fieldName = "password", message = "Debe proporcionar una contraseña.", key = "error.password.required", trim = true) })
public class LoginAction extends ActionSupport implements ServletRequestAware,
		SessionAware, ServletResponseAware {

	@Autowired
	private PanService panService;
	private String login;
	private String password;
	private boolean remember;
	private Map<String, Object> session;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = Trimmer.trim(login);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = Trimmer.trim(password);
	}

	public boolean getRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String execute() throws Exception {
		long id = panService.loginUser(login,
				PasswordCodificator.codify(password));
		User user;

		if (id == 0) {
			if (panService.userExists(login)) {
				addFieldError("password", getText("error.password.incorrect"));
			} else {
				addFieldError("login", getText("error.login.inexistent"));
			}
			return INPUT;
		}

		user = panService.findUser(id);
		
		if (user.getLevel() == Level.DISABLED || 
			user.getLevel() == Level.AUTODISABLED){
			addActionError(getText("error.login.disabled"));
			return INPUT;
		}
		
		user.setLastlogin(Calendar.getInstance());
		user.setIp(request.getRemoteAddr());
		panService.updateUser(user);
		session.put(GlobalNames.USER, user);

		if (remember) {
			Cookie cookie = new Cookie(GlobalNames.COOKIEUSER, user.toCookie());
			cookie.setMaxAge(60 * 60 * 24 * 31); // Make the cookie last a month
			response.addCookie(cookie);
		}

		return SUCCESS;
	}
}
