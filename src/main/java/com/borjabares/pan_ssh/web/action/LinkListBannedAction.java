package com.borjabares.pan_ssh.web.action;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.modelutil.exceptions.InstanceNotFoundException;
import com.borjabares.pan_ssh.model.category.Category;
import com.borjabares.pan_ssh.model.panservice.FullLink;
import com.borjabares.pan_ssh.model.panservice.ObjectBlock;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames;
import com.borjabares.pan_ssh.util.GlobalNames.LinkStatus;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("secure")
@SuppressWarnings("serial")
public class LinkListBannedAction extends ActionSupport implements SessionAware {

	@Autowired
	private PanService panService;
	private ObjectBlock<FullLink> objectBlock;
	private String category;
	private int page;
	private String actionurl;
	private String linkStatus;
	private Map<String, Object> session;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	public ObjectBlock<FullLink> getObjectBlock() {
		return objectBlock;
	}

	public void setObjectBlock(ObjectBlock<FullLink> objectBlock) {
		this.objectBlock = objectBlock;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	public void setPage(String page) {
		try{
			this.page = Integer.parseInt(page);
		} catch (NumberFormatException e){
			this.page = 1;
		}
	}

	public void setPage(String[] page) {
		try{
			this.page = Integer.parseInt(page[0]);
		} catch (NumberFormatException e){
			this.page = 1;
		}
	}

	public String getActionurl() {
		return actionurl;
	}

	public void setActionurl(String actionurl) {
		this.actionurl = actionurl;
	}

	public String getLinkStatus() {
		return linkStatus;
	}

	public void setLinkStatus(String linkStatus) {
		this.linkStatus = linkStatus;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Action(value = "banned/{page}", results = { @Result(location = "/linkListBanned") })
	public String listBanned() {
		int count = 20;

		actionurl = "/banned";
		linkStatus = "/banned";

		if (page == 0) {
			page = 1;
		}

		objectBlock = panService.listFullLinksByStatus((page - 1) * count,
				count, LinkStatus.BANNED, (User) session.get(GlobalNames.USER),
				null);
		return SUCCESS;
	}

	@Action(value = "{category}/banned/{page}", results = { @Result(location = "/linkListBanned") })
	public String listBannedByCategory() throws InstanceNotFoundException {
		int count = 20;

		Category category = panService.findCategoryByName(this.category);

		actionurl = category + "/banned";
		linkStatus = "/banned";

		if (page <= 0) {
			page = 1;
		}

		if (category.getParent() == null) {
			objectBlock = panService.listFullLinksByParentCategoryAndStatus(
					(page - 1) * count, count, LinkStatus.BANNED, category,
					(User) session.get(GlobalNames.USER), null);
		} else {
			objectBlock = panService.listFullLinksByCategoryAndStatus(
					(page - 1) * count, count, LinkStatus.BANNED, category,
					(User) session.get(GlobalNames.USER), null);
		}

		return SUCCESS;
	}

}
