package com.borjabares.pan_ssh.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.model.links.Links;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.util.GlobalNames.LinkStatus;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("secure")
@SuppressWarnings("serial")
public class LinkBanRequeueAction extends ActionSupport {

	@Autowired
	private PanService panService;
	private Links link;
	private long id;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	public Links getLink() {
		return link;
	}

	public void setLink(Links link) {
		this.link = link;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Action(value = "link_ban", results = { @Result(type = "redirect", location = "/banned/") })
	public String ban() throws Exception {
		link = panService.findLink(id);
		link.setStatus(LinkStatus.BANNED);
		panService.updateLink(link);

		return SUCCESS;
	}
	
	@Action(value = "requeue", results = { @Result(type = "redirect", location = "/queue/") })
	public String requeue() throws Exception {
		link = panService.findLink(id);
		link.setStatus(LinkStatus.QUEUED);
		panService.updateLink(link);

		return SUCCESS;
	}

}