package com.borjabares.pan_ssh.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.exceptions.DuplicatedUserEmailException;
import com.borjabares.pan_ssh.exceptions.DuplicatedUserLoginException;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames;
import com.borjabares.pan_ssh.util.Trimmer;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@Action(value = "user_save", results = {
		@Result(location = "/userSuccess"),
		@Result(name = "input", location = "/userForm") })
@SuppressWarnings("serial")
@Validations(requiredStrings = {
		@RequiredStringValidator(fieldName = "confirmPassword", message = "Debe confirmar la contraseña.", key = "error.confpass.required", trim = true, shortCircuit = true) }
)
public class UserCreateAction extends ActionSupport implements ServletRequestAware, SessionAware{
	@Autowired
	private PanService panService;
	private User user;
	private HttpServletRequest request;
	private String confirmPassword;
	private Map<String, Object> session;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	@VisitorFieldValidator(message = "")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String execute() throws Exception {
		
		if  (user.getLogin().equals(user.getPassword())){
			addFieldError("user.password", getText("error.passlog.same"));
			return INPUT;
		}
		
		if (!Trimmer.trim(confirmPassword).equals(user.getPassword())){
			addFieldError("confirmPassword", getText("error.confpass.diff"));
			return INPUT;
		}
		
		user.setIp(request.getRemoteAddr());
		try{
			panService.createUser(user);
			session.put(GlobalNames.USER, user);
//			for (int i=0; i<=250; i++){
//				user = new User("Paco"+i,"1234567","paco"+i+"@gmail.com","192.168.1."+i);
//				panService.createUser(user);
//			}
		} catch (DuplicatedUserLoginException e){
			addFieldError("user.login",getText("error.login.duplicated"));
			return INPUT;
		} catch (DuplicatedUserEmailException e){
			addFieldError("user.email",getText("error.email.duplicated"));
			return INPUT;
		}

		return SUCCESS;
	}
	
}
