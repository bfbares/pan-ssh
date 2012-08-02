package com.borjabares.pan_ssh.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.exceptions.DuplicatedCategoryNameException;
import com.borjabares.pan_ssh.exceptions.ParentCategoryException;
import com.borjabares.pan_ssh.model.category.Category;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ParentPackage("god")
@SuppressWarnings("serial")
@Action(value = "category_save", results = {
		@Result(location = "/categorySuccess"),
		@Result(name = "input", location = "/categoryForm") })
public class CategoryCreateAction extends ActionSupport {

	@Autowired
	private PanService panService;
	private Category category;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	@VisitorFieldValidator(message = "")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public String execute(){
		
		try {
			if (category.getCategoryId()==0){
				panService.createCategory(category);
			} else {
				panService.updateCategory(category);
			}
		} catch (ParentCategoryException e) {
			addFieldError("category.parent", getText("error.parent"));
			return INPUT;
		} catch (DuplicatedCategoryNameException e) {
			addFieldError("category.name", getText("error.name.duplicated"));
			return INPUT;
		}
		
		return SUCCESS;
	}
	
}
