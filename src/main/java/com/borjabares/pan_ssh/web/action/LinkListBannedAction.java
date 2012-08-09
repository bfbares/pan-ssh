package com.borjabares.pan_ssh.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.model.links.FullLink;
import com.borjabares.pan_ssh.model.panservice.ObjectBlock;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames;
import com.borjabares.pan_ssh.util.GlobalNames.LinkStatus;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("secure")
@SuppressWarnings("serial")
public class LinkListBannedAction extends ActionSupport implements
		ServletRequestAware, SessionAware {

	@Autowired
	private PanService panService;
	private ObjectBlock<FullLink> objectBlock;
	private int page;
	private Map<String, Object> session;
	private String actionurl;
	private HttpServletRequest request;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	public ObjectBlock<FullLink> getObjectBlock() {
		return objectBlock;
	}

	public void setObjectBlock(ObjectBlock<FullLink> objectBlock) {
		this.objectBlock = objectBlock;
	}

	public int getPage() {
		return page;
	}

	public void setPage(String page) {
		if (page.isEmpty()) {
			page = "1";
		}
		this.page = Integer.parseInt(page);
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getActionurl() {
		return actionurl;
	}

	public void setActionurl(String actionurl) {
		this.actionurl = actionurl;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Action(value = "banned/{page}", results = { @Result(location = "/linkListBanned") })
	public String listBanned() {
		int count = 20;

		actionurl = "/banned";

		if (page == 0) {
			page = 1;
		}

		objectBlock = panService.listFullLinksByStatus((page - 1) * count,
					count, LinkStatus.BANNED,
					(User) session.get(GlobalNames.USER),
					request.getRemoteAddr());
		return SUCCESS;
	}

}
