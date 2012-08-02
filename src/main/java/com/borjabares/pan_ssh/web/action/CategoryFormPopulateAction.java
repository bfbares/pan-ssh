package com.borjabares.pan_ssh.web.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.modelutil.exceptions.InstanceNotFoundException;
import com.borjabares.pan_ssh.model.category.Category;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.opensymphony.xwork2.ActionSupport;

@Action(value = "categoryForm", results = { @Result(location = "/categoryForm") })
@SuppressWarnings("serial")
public class CategoryFormPopulateAction extends ActionSupport {

	@Autowired
	private PanService panService;
	private List<Category> categories;
	private long id;
	private Category category;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String execute() throws InstanceNotFoundException{
		
		if (id!=0){
			category = panService.findCategory(id);
		}
		
		categories = panService.listParentCategories();
		
		return SUCCESS;
	}

}
