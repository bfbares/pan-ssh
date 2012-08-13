package com.borjabares.pan_ssh.web.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.model.category.Category;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("god")
@Action(value = "category_list", results = { @Result(location = "/categoryList") })
@SuppressWarnings("serial")
public class CategoryListAction extends ActionSupport {

	@Autowired
	private PanService panService;
	private List<Category> list;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	public List<Category> getList() {
		return list;
	}

	public void setList(List<Category> list) {
		this.list = list;
	}
	
	public String execute(){
		
		list = panService.listAllCategoriesSorted();
		
		System.out.println(list);
		
		return SUCCESS;
	}

}
