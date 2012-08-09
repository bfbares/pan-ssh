package com.borjabares.pan_ssh.web.action;

import java.util.Calendar;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.modelutil.exceptions.InstanceNotFoundException;
import com.borjabares.pan_ssh.model.category.Category;
import com.borjabares.pan_ssh.model.links.Links;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.URLExpander;
import com.borjabares.pan_ssh.util.GlobalNames.Level;
import com.borjabares.pan_ssh.web.interceptor.UserAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ParentPackage("user")
@SuppressWarnings("serial")
public class LinkEditAction extends ActionSupport implements UserAware, Preparable {

	@Autowired
	private PanService panService;
	private User user;
	private Links link;
	private long categoryId;
	private List<Category> categories;
	private long id;

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

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public void prepare() throws Exception {
		categories = panService.listNonParentCategories();
	}
	
	private boolean isEditable(){
		Calendar actual = Calendar.getInstance();
		actual.roll(Calendar.HOUR_OF_DAY, false);
		if (user.getLevel() != Level.ADMIN && user.getLevel() != Level.GOD) {
			if (link.getLinkAuthor().getUserId() != user.getUserId()) {
				addActionError(getText("error.noneditable"));
				return false;
			}
			if (link.getSubmited().before(actual)){
				addActionError(getText("error.noneditable"));
				return false;
			}
		}
		if (user.getLevel() != Level.ADMIN){
			actual.roll(Calendar.HOUR_OF_DAY, true);
			actual.roll(Calendar.DAY_OF_MONTH, false);
			if (link.getSubmited().before(actual)){
				addActionError(getText("error.noneditable"));
				return false;
			}
		}
		return true;
	}

	@Action(value = "link_edit", results = { @Result(location = "/linkEditForm") })
	public String populate() throws InstanceNotFoundException {

		link = panService.findLink(id);
		
		if (categories.isEmpty()) {
			return ERROR;
		}

		isEditable();

		return SUCCESS;
	}

	@Action(value = "link_update", results = { @Result(type="redirect",location = "/link/${link.ftitle}"),
			@Result(name = "input", location = "/link_edit") })
	public String update() throws Exception{

		if (!isEditable()){
			return INPUT;
		}
		
		if (categoryId==0){
			addActionError(getText("error.category.required"));
			return INPUT;
		}
		
		link.setCategoryId(panService.findCategory(categoryId));
		
		String expandedURL = URLExpander.expandShortURL(link.getUrl());
		if (expandedURL != null){
			link.setUrl(expandedURL);
		}
		
		Links aux = panService.findLink(link.getLinkId());
		
		link.setFtitle(aux.getFtitle());
		link.setKarma(aux.getKarma());
		link.setLinkAuthor(aux.getLinkAuthor());
		link.setPublished(aux.getPublished());
		link.setStatus(aux.getStatus());
		link.setSubmited(aux.getSubmited());
		link.setTags(aux.getTags());
		link.setVersion(aux.getVersion());
		
		panService.updateLink(link);
		
		return SUCCESS;
	}
	
}
