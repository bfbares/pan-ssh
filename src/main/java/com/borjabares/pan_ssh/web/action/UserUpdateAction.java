package com.borjabares.pan_ssh.web.action;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames;
import com.borjabares.pan_ssh.util.PasswordCodificator;
import com.borjabares.pan_ssh.util.Trimmer;
import com.borjabares.pan_ssh.web.interceptor.UserAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.UrlValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@ParentPackage("user")
@SuppressWarnings("serial")
@Validations(	stringLengthFields = { 
					@StringLengthFieldValidator(fieldName = "newPass", minLength = "6", maxLength = "15", trim = true, message = "La contraseña debe tener entre ${minLength} y ${maxLength} caracteres.", key = "error.password.length") }, 
			 	urls = { 
					@UrlValidator(fieldName = "url", message = "Debe introducir una url válida.", key = "error.url.validator") })
public class UserUpdateAction extends ActionSupport implements
		ServletRequestAware, UserAware, SessionAware, ServletResponseAware {

	@Autowired
	private PanService panService;
	private User user;
	private String url;
	private String pass;
	private String newPass;
	private String newPassConf;
	private Map<String, Object> session;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = Trimmer.trim(newPass);
	}

	public String getNewPassConf() {
		return newPassConf;
	}

	public void setNewPassConf(String newPassConf) {
		this.newPassConf = newPassConf;
	}

	@Override
	public void setUser(User user) {
		this.user = user;

	}

	public User getUser() {
		return user;
	}

	public Map<String, Object> getSession() {
		return session;
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

	@Action(value = "profileForm", results = { @Result(location = "/userEdit"),
			@Result(name = "input", location = "/userEdit") })
	public String repopulate() {
		return SUCCESS;
	}

	@Action(value = "user_edit_save", results = {
			@Result(location = "/userEditSuccess"),
			@Result(name = "input", location = "/userEdit") })
	public String save() throws Exception {
		if (!PasswordCodificator.codify(pass).equals(user.getPassword())) {
			addFieldError("pass", getText("error.password.incorrect"));
			return INPUT;
		}
		user.setLastlogin(Calendar.getInstance());
		user.setIp(request.getRemoteAddr());
		if (!url.equals("")){
			user.setUrl(url);
		} else {
			user.setUrl(null);
		}
		if (newPass.equals("")) {
			panService.updateUser(user);
		} else {
			if (!newPass.equals(newPassConf)) {
				addFieldError("newPass", getText("error.newPass.diff"));
				return INPUT;
			}
			user.setPassword(newPass);
			panService.updateUserAndPass(user);
			session.remove(GlobalNames.USER);
			session.put(GlobalNames.USER, user);
			if (request.getCookies()!=null){
				for(Cookie c : request.getCookies()) {
					if (c.getName().equals(GlobalNames.COOKIEUSER)){
						Cookie cookie = new Cookie(GlobalNames.COOKIEUSER, user.toCookie());
						cookie.setMaxAge(60 * 60 * 24 * 31); // Make the cookie last a month
						response.addCookie(cookie);
					}
				}
			}
		}
		return SUCCESS;
	}

}
