package com.borjabares.pan_ssh.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.modelutil.exceptions.InstanceNotFoundException;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@ParentPackage("god")
public class RunWorkersAction extends ActionSupport {

	@Autowired
	private PanService panService;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	@Action(value = "link_worker", results = { @Result(type = "redirect", location = "/index/") })
	public String linkWorker() throws InstanceNotFoundException {
		panService.linkWorker();
		return SUCCESS;
	}
	
	@Action(value = "user_worker", results = { @Result(type = "redirect", location = "/index/") })
	public String userWorker() throws InstanceNotFoundException {
		panService.userWorker();
		return SUCCESS;
	}

}
