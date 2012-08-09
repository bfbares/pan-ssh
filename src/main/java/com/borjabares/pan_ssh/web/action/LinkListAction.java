package com.borjabares.pan_ssh.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
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

@SuppressWarnings("serial")
public class LinkListAction extends ActionSupport implements
		ServletRequestAware, SessionAware {

	@Autowired
	private PanService panService;
	private ObjectBlock<FullLink> objectBlock;
	private int page;
	private String actionurl;
	private Map<String, Object> session;
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

	public void setPage(int page) {
		this.page = page;
	}

	public void setPage(String page) {
		if (page.isEmpty()) {
			page = "1";
		}
		this.page = Integer.parseInt(page);
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

	@Action(value = "index/{page}", results = { @Result(location = "/linkListPublished") })
	public String listPublished() {
		int count = 20;

		actionurl = "/index";

		if (page == 0) {
			page = 1;
		}

		if (session.containsKey(GlobalNames.USER)) {
			objectBlock = panService.listFullLinksByStatus((page - 1) * count,
					count, LinkStatus.PUBLISHED,
					(User) session.get(GlobalNames.USER), null);
		} else {
			objectBlock = panService.listFullLinksByStatus((page - 1) * count,
					count, LinkStatus.PUBLISHED, null, request.getRemoteAddr());
		}

		return SUCCESS;
	}

	@Action(value = "queue/{page}", results = { @Result(location = "/linkListQueue") })
	public String listPending() {
		int count = 20;

		actionurl = "/queue";

		if (page == 0) {
			page = 1;
		}

		if (session.containsKey(GlobalNames.USER)) {
			objectBlock = panService.listFullLinksByStatus((page - 1) * count,
					count, LinkStatus.QUEUED,
					(User) session.get(GlobalNames.USER), null);
		} else {
			objectBlock = panService.listFullLinksByStatus((page - 1) * count,
					count, LinkStatus.QUEUED, null, request.getRemoteAddr());
		}

		return SUCCESS;
	}

	@Action(value = "discarded/{page}", results = { @Result(location = "/linkListDiscarded") })
	public String listDiscarded() {
		int count = 20;

		actionurl = "/discarded";

		if (page == 0) {
			page = 1;
		}

		if (session.containsKey(GlobalNames.USER)) {
			objectBlock = panService.listFullLinksByStatus((page - 1) * count,
					count, LinkStatus.DISCARD,
					(User) session.get(GlobalNames.USER), null);
		} else {
			objectBlock = panService.listFullLinksByStatus((page - 1) * count,
					count, LinkStatus.DISCARD, null, request.getRemoteAddr());
		}

		return SUCCESS;
	}

}
