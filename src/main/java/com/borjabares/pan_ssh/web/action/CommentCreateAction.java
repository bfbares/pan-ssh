package com.borjabares.pan_ssh.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.model.comment.Comment;
import com.borjabares.pan_ssh.model.commentvote.CommentVote;
import com.borjabares.pan_ssh.model.links.Links;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames.VoteType;
import com.borjabares.pan_ssh.web.interceptor.UserAware;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("user")
@Action(value = "comment_save", results = { @Result(type = "redirect", location = "/link/${link.ftitle}/"),
											@Result(name = "input", type="redirect", location = "/link/${link.ftitle}/")})
@SuppressWarnings("serial")
public class CommentCreateAction extends ActionSupport implements UserAware {

	@Autowired
	private PanService panService;
	private long linkId;
	private Comment comment;
	private User user;
	private Links link;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	public long getLinkId() {
		return linkId;
	}

	public void setLinkId(long linkId) {
		this.linkId = linkId;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	public Links getLink() {
		return link;
	}

	public void setLink(Links link) {
		this.link = link;
	}

	public String execute() throws Exception {
		link = panService.findLink(linkId);
		if (comment.getComment().length() > 600){
			addFieldError("comment.comment", "getText(error.comment.length)");
			return INPUT;
		}
		comment.setLink(link);
		comment.setAuthor(user);

		panService.createComment(comment);
		panService.createCommentVote(new CommentVote(comment, user, VoteType.UPVOTE));

		return SUCCESS;
	}
}
