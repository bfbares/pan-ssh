package com.borjabares.pan_ssh.web.interceptor;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

@SuppressWarnings("serial")
public class CookieInterceptor implements Interceptor{
	
	@Autowired
	private PanService panService;
	
	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Map<String, Object> session = actionInvocation.getInvocationContext().getSession();
		String userString = null;
		HttpServletRequest request = (HttpServletRequest) actionInvocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
		if (!session.containsKey(GlobalNames.USER) && request.getCookies()!=null){
			for(Cookie c : request.getCookies()) {
				if (c.getName().equals(GlobalNames.COOKIEUSER)){
					userString=c.getValue();
					int space = userString.indexOf(' ');
					String login = userString.substring(0, space);
					String password = userString.substring(space+1);
					long userId = panService.loginUser(login, password);
					User user = panService.findUser(userId);
					user.setLastlogin(Calendar.getInstance());
					user.setIp(request.getRemoteAddr());
					panService.updateUser(user);
					session.put(GlobalNames.USER, user);
					break;
				}
		    }
		}
		return actionInvocation.invoke();
	}

}
