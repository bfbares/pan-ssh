package com.borjabares.pan_ssh.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.model.category.Category;
import com.borjabares.pan_ssh.model.panservice.FullLink;
import com.borjabares.pan_ssh.model.panservice.ObjectBlock;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames;
import com.borjabares.pan_ssh.util.GlobalNames.LinkStatus;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LinkListAction extends ActionSupport implements
		ServletRequestAware, SessionAware {

	@Autowired
	private PanService panService;
	private ObjectBlock<FullLink> objectBlock;
	private String category;
	private int page;
	private String actionurl;
	private String linkStatus;
	private Map<String, Object> session;
	private HttpServletRequest request;

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

	public void setPage(String page) {
		if (page.isEmpty()) {
			page = "1";
		}
		this.page = Integer.parseInt(page);
	}
	
	public void setPage(int page) {
		this.page = page;
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

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Action(value = "index/{page}", results = { @Result(location = "/linkListPublished") })
	public String listPublished() {
		int count = 20;

		actionurl = "/index";
		linkStatus = "/index";

		if (page == 0) {
			page = 1;
		}

		if (session.containsKey(GlobalNames.USER)) {
			objectBlock = panService.listFullLinksByStatus((page - 1) * count,
					count, LinkStatus.PUBLISHED,
					(User) session.get(GlobalNames.USER), null);
		} else {
			objectBlock = panService.listFullLinksByStatus((page - 1) * count,
					count, LinkStatus.PUBLISHED, null, request.getRemoteAddr());
		}

		return SUCCESS;
	}

	@Action(value = "queue/{page}", results = { @Result(location = "/linkListQueue") })
	public String listPending() {
		int count = 20;

		actionurl = "/queue";
		linkStatus = "/queue";

		if (page == 0) {
			page = 1;
		}

		if (session.containsKey(GlobalNames.USER)) {
			objectBlock = panService.listFullLinksByStatus((page - 1) * count,
					count, LinkStatus.QUEUED,
					(User) session.get(GlobalNames.USER), null);
		} else {
			objectBlock = panService.listFullLinksByStatus((page - 1) * count,
					count, LinkStatus.QUEUED, null, request.getRemoteAddr());
		}

		return SUCCESS;
	}

	@Action(value = "discarded/{page}", results = { @Result(location = "/linkListDiscarded") })
	public String listDiscarded() {
		int count = 20;

		actionurl = "/discarded";
		linkStatus = "/discarded";

		if (page == 0) {
			page = 1;
		}

		if (session.containsKey(GlobalNames.USER)) {
			objectBlock = panService.listFullLinksByStatus((page - 1) * count,
					count, LinkStatus.DISCARD,
					(User) session.get(GlobalNames.USER), null);
		} else {
			objectBlock = panService.listFullLinksByStatus((page - 1) * count,
					count, LinkStatus.DISCARD, null, request.getRemoteAddr());
		}

		return SUCCESS;
	}
	
	@Action(value = "{category}/index/{page}", results = { @Result(location = "/linkListPublished") })
	public String listPublishedByCategory() throws Exception{
		int count = 20;
		Category category = panService.findCategoryByName(this.category);
		actionurl = category+"/index";
		linkStatus = "/index";

		if (page == 0) {
			page = 1;
		}
		
		if (category.getParent() == null){
			if (session.containsKey(GlobalNames.USER)) {
				objectBlock = panService.listFullLinksByParentCategoryAndStatus((page - 1) * count,
						count, LinkStatus.PUBLISHED, category, 
						(User) session.get(GlobalNames.USER), null);
			} else {
				objectBlock = panService.listFullLinksByParentCategoryAndStatus((page - 1) * count,
						count, LinkStatus.PUBLISHED, category,  null, request.getRemoteAddr());
			}
		} else {
			if (session.containsKey(GlobalNames.USER)) {
				objectBlock = panService.listFullLinksByCategoryAndStatus((page - 1) * count,
						count, LinkStatus.PUBLISHED, category, 
						(User) session.get(GlobalNames.USER), null);
			} else {
				objectBlock = panService.listFullLinksByCategoryAndStatus((page - 1) * count,
						count, LinkStatus.PUBLISHED, category,  null, request.getRemoteAddr());
			}
		}

		return SUCCESS;
	}

	@Action(value = "{category}/queue/{page}", results = { @Result(location = "/linkListQueue") })
	public String listPendingByCategory() throws Exception{
	
		int count = 20;
		Category category = panService.findCategoryByName(this.category);
		actionurl = category+"/queue";
		linkStatus = "/queue";

		if (page == 0) {
			page = 1;
		}

		if (category.getParent() == null){
			if (session.containsKey(GlobalNames.USER)) {
				objectBlock = panService.listFullLinksByParentCategoryAndStatus((page - 1) * count,
						count, LinkStatus.QUEUED, category, 
						(User) session.get(GlobalNames.USER), null);
			} else {
				objectBlock = panService.listFullLinksByParentCategoryAndStatus((page - 1) * count,
						count, LinkStatus.QUEUED, category,  null, request.getRemoteAddr());
			}
		} else {
			if (session.containsKey(GlobalNames.USER)) {
				objectBlock = panService.listFullLinksByCategoryAndStatus((page - 1) * count,
						count, LinkStatus.QUEUED, category, 
						(User) session.get(GlobalNames.USER), null);
			} else {
				objectBlock = panService.listFullLinksByCategoryAndStatus((page - 1) * count,
						count, LinkStatus.QUEUED, category,  null, request.getRemoteAddr());
			}
		}

		return SUCCESS;
	}

	@Action(value = "{category}/discarded/{page}", results = { @Result(location = "/linkListDiscarded") })
	public String listDiscardedByCategory() throws Exception{
		int count = 20;
		Category category = panService.findCategoryByName(this.category);
		actionurl = category+"/discarded";
		linkStatus = "/discarded";

		if (page == 0) {
			page = 1;
		}

		if (category.getParent() == null){
			if (session.containsKey(GlobalNames.USER)) {
				objectBlock = panService.listFullLinksByParentCategoryAndStatus((page - 1) * count,
						count, LinkStatus.DISCARD, category, 
						(User) session.get(GlobalNames.USER), null);
			} else {
				objectBlock = panService.listFullLinksByParentCategoryAndStatus((page - 1) * count,
						count, LinkStatus.DISCARD, category,  null, request.getRemoteAddr());
			}
		} else {
			if (session.containsKey(GlobalNames.USER)) {
				objectBlock = panService.listFullLinksByCategoryAndStatus((page - 1) * count,
						count, LinkStatus.DISCARD, category, 
						(User) session.get(GlobalNames.USER), null);
			} else {
				objectBlock = panService.listFullLinksByCategoryAndStatus((page - 1) * count,
						count, LinkStatus.DISCARD, category,  null, request.getRemoteAddr());
			}
		}

		return SUCCESS;
	}

}
