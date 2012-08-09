package com.borjabares.pan_ssh.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.model.report.Report;
import com.borjabares.pan_ssh.util.GlobalNames.ReportStatus;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("secure")
@SuppressWarnings("serial")
public class ReportCheckAction extends ActionSupport {

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

	@Action(value = "report_check", results = { @Result(type = "redirect", location = "/report_list/") })
	public String execute() throws Exception {

		Report report = panService.findReport(id);
		report.setStatus(ReportStatus.CHECKED);
		panService.updateReport(report);

		return SUCCESS;
	}
}
