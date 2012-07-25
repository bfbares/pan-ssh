package com.borjabares.pan_ssh.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.exceptions.DuplicatedUserEmailException;
import com.borjabares.pan_ssh.exceptions.DuplicatedUserLoginException;
import com.borjabares.pan_ssh.model.panservice.ObjectBlock;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.Trimmer;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Validations(requiredStrings = {
		@RequiredStringValidator(fieldName = "confirmPassword", message = "Debe confirmar la contraseña.", key = "error.confpass.required", trim = true, shortCircuit = true) }
)
public class UserAction extends ActionSupport implements ServletRequestAware{
	@Autowired
	private PanService panService;
	private User user;
	private ObjectBlock<User> userBlock;
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

	public ObjectBlock<User> getUserBlock() {
		return userBlock;
	}

	public void setUserBlock(ObjectBlock<User> userBlock) {
		this.userBlock = userBlock;
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
	
	@Action(value = "user_save", results = {
			@Result(location = "/userSuccess"),
			@Result(name = "input", location = "/userForm") })
	public String save() throws Exception {
		
		if  (user.getLogin().equals(user.getPassword())){
			addFieldError("user.password", getText("error.passlog.same"));
			return INPUT;
		}
		
		if (!Trimmer.trim(confirmPassword).equals(user.getPassword())){
			addFieldError("confirmPassword", getText("error.confpass.diff"));
			return INPUT;
		}
		if (user.getUserId() == 0) {
			user.setIp(request.getRemoteAddr());
			try{
				panService.createUser(user);
			} catch (DuplicatedUserLoginException e){
				addFieldError("user.login",getText("error.login.duplicated"));
				return INPUT;
			} catch (DuplicatedUserEmailException e){
				addFieldError("user.email",getText("error.email.duplicated"));
				return INPUT;
			}
		} else {
			panService.updateUser(user);
		}

		return SUCCESS;
	}

}
