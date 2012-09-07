package com.borjabares.pan_ssh.web.action;

import java.util.Calendar;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.model.comment.Comment;
import com.borjabares.pan_ssh.model.links.Links;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames.Level;
import com.borjabares.pan_ssh.web.interceptor.UserAware;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("user")
@SuppressWarnings("serial")
public class CommentEditAction extends ActionSupport implements UserAware {

	@Autowired
	private PanService panService;
	private long id;
	private String newComment;
	private Comment comment;
	private Links link;
	private User user;

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

	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Links getLink() {
		return link;
	}

	public void setLink(Links link) {
		this.link = link;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Action(value = "commentForm", results = { @Result(location = "/commentForm")})
	public String populate() throws Exception {
		comment = panService.findComment(id);

		if (user.getLevel() != Level.ADMIN && user.getLevel() != Level.GOD
				&& comment.getAuthor() != user) {
			return ERROR;
		}

		Calendar actual = Calendar.getInstance();
		actual.roll(Calendar.HOUR_OF_DAY, false);
		actual.roll(Calendar.HOUR_OF_DAY, false);
		actual.roll(Calendar.HOUR_OF_DAY, false);
		if (!comment.getSubmited().after(actual)){
			return ERROR;
		}
		return SUCCESS;
	}

	@Action(value = "comment_update", results = { @Result(type = "redirect", location = "/link/${link.ftitle}/"),
			@Result(name = "input", location = "/commentForm")})
	public String save() throws Exception{
		if (newComment.length()>600 || newComment.length()<=1){
			addFieldError("newComment", "getText(error.comment.length)");
			return INPUT;
		}
		comment = panService.findComment(id);
		link = panService.findLink(comment.getLink().getLinkId());
		
		comment.setComment(newComment);
		
		panService.updateComment(comment);
		
		return SUCCESS;
	}
}
