package com.borjabares.pan_ssh.web.interceptor;

import com.borjabares.pan_ssh.model.user.User;

public interface UserAware {

	public void setUser(User user);
	
}
