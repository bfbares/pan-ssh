package com.borjabares.pan_ssh.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.modelutil.exceptions.InstanceNotFoundException;
import com.borjabares.pan_ssh.model.panservice.FullLink;
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
	private long id;
	private String ftitle;
	private int page;
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

	public void setPage(String page) {
		if (page.isEmpty()) {
			page = "1";
		}
		this.page = Integer.parseInt(page);
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
		try {
			if (session.containsKey(GlobalNames.USER)) {
				fullLink = panService.findFullLink(id,
						(User) session.get(GlobalNames.USER),
						request.getRemoteAddr());
			} else {
				fullLink = panService.findFullLink(id, null,
						request.getRemoteAddr());
			}
		} catch (InstanceNotFoundException e) {
			return ERROR;
		}
		
		switch (fullLink.getLink().getStatus()){
			case PUBLISHED: linkStatus = "/index";
							break;
			case QUEUED: linkStatus = "/queue";
							break;
			case DISCARD: linkStatus = "/discarded";
							break;
			case BANNED: linkStatus = "/banned";
							break;
		}
		
		return SUCCESS;
	}

	@Action(value = "link/{ftitle}", results = { @Result(location = "/linkInfo") })
	public String getByFtitle() {
		try {
			if (session.containsKey(GlobalNames.USER)) {
				fullLink = panService.findFullLinkByFtitle(ftitle,
						(User) session.get(GlobalNames.USER),
						request.getRemoteAddr());
			} else {
				fullLink = panService.findFullLinkByFtitle(ftitle, null,
						request.getRemoteAddr());
			}
		} catch (InstanceNotFoundException e) {
			return ERROR;
		}
		
		switch (fullLink.getLink().getStatus()){
		case PUBLISHED: linkStatus = "/index";
						break;
		case QUEUED: linkStatus = "/queue";
						break;
		case DISCARD: linkStatus = "/discarded";
						break;
		case BANNED: linkStatus = "/banned";
						break;
		}

		return SUCCESS;
	}

}
