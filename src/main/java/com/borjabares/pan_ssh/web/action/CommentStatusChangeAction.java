package com.borjabares.pan_ssh.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.model.comment.Comment;
import com.borjabares.pan_ssh.model.links.Links;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.util.GlobalNames.CommentStatus;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("secure")
@SuppressWarnings("serial")
public class CommentStatusChangeAction extends ActionSupport {

	@Autowired
	private PanService panService;
	private long id;
	private Links link;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Links getLink() {
		return link;
	}

	public void setLink(Links link) {
		this.link = link;
	}
	
	@Action(value = "comment_disable", results = { @Result(type = "redirect", location = "/link/${link.ftitle}/")})
	public String disable() throws Exception{
		Comment comment = panService.findComment(id);
		link = panService.findLink(comment.getLink().getLinkId());
		
		comment.setStatus(CommentStatus.INACTIVE);
		panService.updateComment(comment);
		
		return SUCCESS;
	}
	
	@Action(value = "comment_enable", results = { @Result(type = "redirect", location = "/link/${link.ftitle}/")})
	public String enable() throws Exception{
		Comment comment = panService.findComment(id);
		link = panService.findLink(comment.getLink().getLinkId());
		
		comment.setStatus(CommentStatus.ACTIVE);
		panService.updateComment(comment);
		
		return SUCCESS;
	}

}
