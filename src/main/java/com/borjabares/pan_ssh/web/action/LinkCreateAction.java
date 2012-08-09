package com.borjabares.pan_ssh.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.exceptions.DuplicatedLinkException;
import com.borjabares.pan_ssh.model.category.Category;
import com.borjabares.pan_ssh.model.links.Links;
import com.borjabares.pan_ssh.model.linkvote.LinkVote;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.FriendlyTitleGenerator;
import com.borjabares.pan_ssh.util.GlobalNames.VoteType;
import com.borjabares.pan_ssh.util.URLExpander;
import com.borjabares.pan_ssh.web.interceptor.UserAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ParentPackage("user")
@SuppressWarnings("serial")
public class LinkCreateAction extends ActionSupport implements ServletRequestAware, UserAware, Preparable{

	@Autowired
	private PanService panService;
	private User user;
	private Links link;
	private long categoryId;
	private HttpServletRequest request;
	private List<Category> categories;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	@VisitorFieldValidator(message = "")
	public Links getLink() {
		return link;
	}

	public void setLink(Links link) {
		this.link = link;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@Override
	public void prepare() throws Exception {
		categories = panService.listNonParentCategories();
	}
	
	@Action(value = "linkForm", results = { @Result(location = "/linkForm") })
	public String populate() {

		if (categories.isEmpty()) {
			return ERROR;
		}

		return SUCCESS;
	}

	@Action(value = "link_save", results = { @Result(location = "/linkSuccess"),
			@Result(name = "input", location = "/linkForm") })
	public String save() throws Exception{

		if (categoryId==0){
			addActionError(getText("error.category.required"));
			return INPUT;
		}
		
		link.setLinkAuthor(user);
		link.setCategoryId(panService.findCategory(categoryId));
		String expandedURL = URLExpander.expandShortURL(link.getUrl());
		if (expandedURL != null){
			link.setUrl(expandedURL);
		}
		
		link.setFtitle(FriendlyTitleGenerator.generate(link.getTitle()));
		
		try {
			panService.createLink(link);
		} catch (DuplicatedLinkException e) {
			addFieldError("link.url", getText("error.url.duplicated"));
			return INPUT;
		}

		panService.createVote(new LinkVote(link, user, VoteType.UPVOTE, request.getRemoteAddr()));
		
		return SUCCESS;
	}
}
