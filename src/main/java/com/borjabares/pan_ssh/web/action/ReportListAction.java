package com.borjabares.pan_ssh.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.model.panservice.ObjectBlock;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.report.Report;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("secure")
@SuppressWarnings("serial")
public class ReportListAction extends ActionSupport {

	@Autowired
	private PanService panService;
	private ObjectBlock<Report> objectBlock;
	private int page;
	private String actionurl;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	public ObjectBlock<Report> getObjectBlock() {
		return objectBlock;
	}

	public void setObjectBlock(ObjectBlock<Report> objectBlock) {
		this.objectBlock = objectBlock;
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

	@Action(value = "report_list/{page}", results = { @Result(location = "/reportList") })
	public String list() {
		int count = 25;
		
		actionurl = "/report_list";

		if (page <= 0) {
			page = 1;
		}

		objectBlock = panService.listPendingReports((page - 1) * count, count);

		return SUCCESS;
	}
}
