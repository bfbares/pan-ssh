package com.borjabares.pan_ssh.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames.Level;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@Action(value = "firstrun_save", results = {
		@Result(type = "redirect", location = "/index"),
		@Result(name = "error", location = "/index"),
		@Result(name = "input", location = "/firstrun") })
@SuppressWarnings("serial")
@Validations(requiredStrings = {
		@RequiredStringValidator(fieldName = "confirmPassword", message = "Debe confirmar la contraseña.", key = "error.confpass.required", trim = true, shortCircuit = true) }
)
public class FirstRunAction extends ActionSupport implements ServletRequestAware{
	@Autowired
	private PanService panService;
	private User user;
	private HttpServletRequest request;
	private String confirmPassword;

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
	public String execute() throws Exception{
		if (panService.getNumberOfUsers()!=0){
			return ERROR;
		}
		if  (user.getLogin().equals(user.getPassword())){
			addFieldError("user.password", getText("error.passlog.same"));
			return INPUT;
		}
		
		if (!confirmPassword.equals(user.getPassword())){
			addFieldError("confirmPassword", getText("error.confpass.diff"));
			return INPUT;
		}
		
		user.setLevel(Level.GOD);
		user.setIp(request.getRemoteAddr());
		//panService.createUser(user);
		
		return SUCCESS;
	}

}
