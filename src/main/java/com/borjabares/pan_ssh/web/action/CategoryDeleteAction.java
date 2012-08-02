package com.borjabares.pan_ssh.web.action;

import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.modelutil.exceptions.InstanceNotFoundException;
import com.borjabares.pan_ssh.model.category.Category;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("god")
@SuppressWarnings("serial")
@Action(value = "category_delete", results = { @Result(location = "/categoryDelSuccess") })
public class CategoryDeleteAction extends ActionSupport {

	@Autowired
	private PanService panService;
	private long id;

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

	public String execute() throws InstanceNotFoundException {

		List<Category> childrens = panService.listCategoryChildrens(id);

		if (childrens != null) {
			for (Iterator<Category> iterator = childrens.iterator(); iterator
					.hasNext();) {
				Category category = (Category) iterator.next();
				panService.deleteCategory(category.getCategoryId());
			}
		}

		panService.deleteCategory(id);

		return SUCCESS;
	}

}
