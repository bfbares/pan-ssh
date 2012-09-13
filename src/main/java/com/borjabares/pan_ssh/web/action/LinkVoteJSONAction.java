package com.borjabares.pan_ssh.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.exceptions.IpVotedException;
import com.borjabares.pan_ssh.model.links.Links;
import com.borjabares.pan_ssh.model.linkvote.LinkVote;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames;
import com.borjabares.pan_ssh.util.GlobalNames.VoteType;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LinkVoteJSONAction extends ActionSupport implements SessionAware, ServletRequestAware{

	@Autowired
	private PanService panService;
	private long id;
	private Map<String, Object> session;
	private HttpServletRequest request;
	private VoteType voteType;
	private Links link;
	private long numVote;
	private String text;
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
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setLink(Links link) {
		this.link = link;
	}

	public long getNumVote() {
		return numVote;
	}

	public void setNumVote(long numVote) {
		this.numVote = numVote;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	private String vote() throws Exception{
		link = panService.findLink(id);
		String ip = request.getRemoteAddr();
		
		if (session.containsKey(GlobalNames.USER)) {
			panService.createVote( new LinkVote(link, (User) session.get(GlobalNames.USER), voteType, ip));
		} else {		
			try {
				panService.createVote(new LinkVote(link, voteType, ip));
			} catch (IpVotedException e) {
				return INPUT;
			} 
		}		
		
		numVote = panService.getNumberOfVotes(link.getLinkId());
		text = getText("form.voted");
		
		return SUCCESS;
	}
	
	@Action(value = "upvote_js", results = {
			@Result(type="json")})
	public String upvote() throws Exception{
		voteType = VoteType.UPVOTE;
		result = vote();
		
		return SUCCESS;
	}
	
	@Action(value = "downvote_js", results = {
			@Result(type="json")})
	public String downvote() throws Exception{
		voteType = VoteType.DOWNVOTE;
		result = vote();
		
		return SUCCESS;
	}
	

}
