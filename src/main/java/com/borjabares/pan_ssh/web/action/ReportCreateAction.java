package com.borjabares.pan_ssh.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.exceptions.LinkAlreadyReportedException;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.report.Report;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.web.interceptor.UserAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ParentPackage("user")
@Action(value = "report_save", results = { @Result(location = "/reportSuccess"),
			@Result(name = "input", location = "/reportForm") })
@SuppressWarnings("serial")
public class ReportCreateAction extends ActionSupport implements UserAware{
	@Autowired
	private PanService panService;
	private long id;
	private Report report;
	private User user;
	

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

	@VisitorFieldValidator(message = "")
	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}
	
	@Override
	public void setUser(User user) {
		this.user = user;
	}
	
	public String execute() throws Exception{
		report.setUser(user);
		report.setLink(panService.findLink(id));
		
		try {
			panService.createReport(report);
		} catch (LinkAlreadyReportedException e) {
			addActionError(getText("error.linkreported"));
			return INPUT;
		}
		
		return SUCCESS;
	}

}
