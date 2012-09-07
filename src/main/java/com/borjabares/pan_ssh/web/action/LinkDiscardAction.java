package com.borjabares.pan_ssh.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.model.links.Links;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames.Level;
import com.borjabares.pan_ssh.web.interceptor.UserAware;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("user")
@Action(value = "link_discard", results = { @Result(type = "redirect", location = "/queue/") })
@SuppressWarnings("serial")
public class LinkDiscardAction extends ActionSupport implements UserAware {

	@Autowired
	private PanService panService;
	private User user;
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

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	public String execute() throws Exception {
		link = panService.findLink(id);
		if (user.getLevel() != Level.ADMIN
				&& user.getLevel() != Level.GOD){
			if (link.getLinkAuthor().getUserId() != user.getUserId()){
				return ERROR;
			}
		}

		panService.discardLink(link.getLinkId());

		return SUCCESS;
	}

}
