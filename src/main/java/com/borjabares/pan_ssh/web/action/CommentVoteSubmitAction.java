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
@SuppressWarnings("serial")
public class CommentVoteSubmitAction extends ActionSupport implements UserAware {

	@Autowired
	private PanService panService;
	private long id;
	private VoteType voteType;
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

	public VoteType getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}

	public Links getLink() {
		return link;
	}

	public void setLink(Links link) {
		this.link = link;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}
	
	public String vote() throws Exception{
		Comment comment = panService.findComment(id);
		link = panService.findLink(comment.getLink().getLinkId());
		
		panService.createCommentVote(new CommentVote(comment, user, voteType));
		
		return SUCCESS;
	}

	@Action(value = "c_upvote", results = {
			@Result(type="redirect",location = "/link/${link.ftitle}/")})
	public String upvote() throws Exception{
		voteType = VoteType.UPVOTE;		
		
		return vote();
	}
	
	@Action(value = "c_downvote", results = {
			@Result(type="redirect",location = "/link/${link.ftitle}/")})
	public String downvote() throws Exception{
		voteType = VoteType.DOWNVOTE;		
		
		return vote();
	}
}
