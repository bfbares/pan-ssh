package com.borjabares.pan_ssh.web.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.modelutil.exceptions.InstanceNotFoundException;
import com.borjabares.pan_ssh.exceptions.DuplicatedCategoryNameException;
import com.borjabares.pan_ssh.exceptions.ParentCategoryException;
import com.borjabares.pan_ssh.model.category.Category;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ParentPackage("god")
@SuppressWarnings("serial")
public class CategoryCreateAction extends ActionSupport implements Preparable {

	@Autowired
	private PanService panService;
	private Category category;
	private List<Category> categories;
	private long id;
	private long parent;

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

	public long getParent() {
		return parent;
	}
	
	public void setParent(long parent) {
		this.parent = parent;
	}

	@Override
	public void prepare() throws Exception {
		categories = panService.listParentCategories();
	}

	@Action(value = "categoryForm", results = { @Result(location = "/categoryForm") })
	public String populate() throws InstanceNotFoundException{
		
		if (id!=0){
			category = panService.findCategory(id);
			parent = category.getParent().getCategoryId();
		}
		
		return SUCCESS;
	}

	@Action(value = "category_save", results = {
			@Result(location = "/categorySuccess"),
			@Result(name = "input", location = "/categoryForm") })
	public String save() throws Exception{
		
		if (parent!=0){
			category.setParent(panService.findCategory(parent));
		}

		try {
			if (category.getCategoryId() == 0) {
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
