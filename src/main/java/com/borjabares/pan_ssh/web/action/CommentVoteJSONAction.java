package com.borjabares.pan_ssh.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.model.comment.Comment;
import com.borjabares.pan_ssh.model.commentvote.CommentVote;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames.VoteType;
import com.borjabares.pan_ssh.web.interceptor.UserAware;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("user")
@SuppressWarnings("serial")
public class CommentVoteJSONAction extends ActionSupport implements UserAware {

	@Autowired
	private PanService panService;
	private long id;
	private VoteType voteType;
	private User user;
	private float karma;
	private String result;

	public void setPanService(PanService panService) {
		this.panService = panService;
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

	public float getKarma() {
		return karma;
	}

	public void setKarma(float karma) {
		this.karma = karma;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String vote() throws Exception{
		Comment comment = panService.findComment(id);
		
		panService.createCommentVote(new CommentVote(comment, user, voteType));
		karma = panService.findComment(comment.getCommentId()).getKarma();
		
		return SUCCESS;
	}

	@Action(value = "c_upvote_js", results = { @Result(type = "json") })
	public String upvote() throws Exception {
		voteType = VoteType.UPVOTE;
		result = vote();

		return SUCCESS;
	}

	@Action(value = "c_downvote_js", results = { @Result(type = "json") })
	public String downvote() throws Exception {
		voteType = VoteType.DOWNVOTE;
		result = vote();

		return SUCCESS;
	}
}
