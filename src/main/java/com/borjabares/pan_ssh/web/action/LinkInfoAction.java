package com.borjabares.pan_ssh.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.modelutil.exceptions.InstanceNotFoundException;
import com.borjabares.pan_ssh.model.panservice.FullComment;
import com.borjabares.pan_ssh.model.panservice.FullLink;
import com.borjabares.pan_ssh.model.panservice.ObjectBlock;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LinkInfoAction extends ActionSupport implements
		ServletRequestAware, SessionAware {

	@Autowired
	private PanService panService;
	private FullLink fullLink;
	private ObjectBlock<FullComment> objectBlock;
	private long id;
	private String ftitle;
	private int page;
	private String actionurl;
	private String linkStatus;
	private Map<String, Object> session;
	private HttpServletRequest request;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	public FullLink getFullLink() {
		return fullLink;
	}

	public void setFullLink(FullLink fullLink) {
		this.fullLink = fullLink;
	}

	public ObjectBlock<FullComment> getObjectBlock() {
		return objectBlock;
	}

	public void setObjectBlock(ObjectBlock<FullComment> objectBlock) {
		this.objectBlock = objectBlock;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFtitle() {
		return ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setPage(String[] page) {
		try {
			this.page = Integer.parseInt(page[0]);
		} catch (NumberFormatException e) {
			this.page = 1;
		}
	}

	public String getActionurl() {
		return actionurl;
	}

	public void setActionurl(String actionurl) {
		this.actionurl = actionurl;
	}

	public String getLinkStatus() {
		return linkStatus;
	}

	public void setLinkStatus(String linkStatus) {
		this.linkStatus = linkStatus;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Action(value = "link_info", results = { @Result(location = "/linkInfo") })
	public String getById() {
		int count = 15;

		if (page <= 0) {
			page = 1;
		}

		try {
			if (session.containsKey(GlobalNames.USER)) {
				User user = (User) session.get(GlobalNames.USER);
				fullLink = panService.findFullLink(id, user,
						request.getRemoteAddr());
				objectBlock = panService.listCommentsByLinkId((page - 1)
						* count, count, fullLink.getLink().getLinkId(), user);
			} else {
				fullLink = panService.findFullLink(id, null,
						request.getRemoteAddr());
				objectBlock = panService.listCommentsByLinkId((page - 1)
						* count, count, fullLink.getLink().getLinkId(), null);
			}
		} catch (InstanceNotFoundException e) {
			return ERROR;
		}

		switch (fullLink.getLink().getStatus()) {
		case PUBLISHED:
			linkStatus = "/index";
			break;
		case QUEUED:
			linkStatus = "/queue";
			break;
		case DISCARD:
			linkStatus = "/discarded";
			break;
		case BANNED:
			linkStatus = "/banned";
			break;
		}

		return SUCCESS;
	}

	@Action(value = "link/{ftitle}/{page}", results = { @Result(location = "/linkInfo") })
	public String getByFtitle() {
		int count = 15;

		if (page <= 0) {
			page = 1;
		}

		actionurl = "/link/" + ftitle;

		try {
			if (session.containsKey(GlobalNames.USER)) {
				User user = (User) session.get(GlobalNames.USER);
				fullLink = panService.findFullLinkByFtitle(ftitle, user,
						request.getRemoteAddr());
				objectBlock = panService.listCommentsByLinkId((page - 1)
						* count, count, fullLink.getLink().getLinkId(), user);
			} else {
				fullLink = panService.findFullLinkByFtitle(ftitle, null,
						request.getRemoteAddr());
				objectBlock = panService.listCommentsByLinkId((page - 1)
						* count, count, fullLink.getLink().getLinkId(), null);
			}
		} catch (InstanceNotFoundException e) {
			return ERROR;
		}

		switch (fullLink.getLink().getStatus()) {
		case PUBLISHED:
			linkStatus = "/index";
			break;
		case QUEUED:
			linkStatus = "/queue";
			break;
		case DISCARD:
			linkStatus = "/discarded";
			break;
		case BANNED:
			linkStatus = "/banned";
			break;
		}

		return SUCCESS;
	}

}
