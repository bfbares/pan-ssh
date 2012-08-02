package com.borjabares.pan_ssh.web.interceptor;

import java.util.Map;

import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

@SuppressWarnings("serial")
public class AuthenticationInterceptor implements Interceptor{
	
	private String rol;

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public void destroy() {		
	}

	@Override
	public void init() {		
	}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Map<String, Object> session = actionInvocation.getInvocationContext().getSession();
		User user = (User) session.get(GlobalNames.USER);
		
		if (user == null){
			return Action.LOGIN;
		} else {
            if (rol != null){
            	if (!user.getLevel().toString().equals("GOD")){
	            	if (rol.equals("GOD")){
	            		return Action.ERROR;
	            	}
	            	if (rol.equals("ADMIN") && !(user.getLevel().toString().equals("ADMIN"))){
	            		return Action.ERROR;
	            	}
            	}
            }
			Action action = (Action) actionInvocation.getAction();
			
			if (action instanceof UserAware) {
				((UserAware)action).setUser(user);
			}
		}
		
		return actionInvocation.invoke();
	}

}
