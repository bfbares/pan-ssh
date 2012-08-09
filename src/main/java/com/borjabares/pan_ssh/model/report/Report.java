package com.borjabares.pan_ssh.model.report;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.borjabares.pan_ssh.model.links.Links;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames.ReportStatus;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Entity
@Validations(requiredStrings = {
		@RequiredStringValidator(fieldName = "reason", message = "Debe proporcionar un motivo.", key = "error.reason.required", trim = true, shortCircuit = true) }, 
		stringLengthFields = {
		@StringLengthFieldValidator(fieldName = "reason", minLength = "6", maxLength = "360", trim = true, message = "El motivo debe tener entre ${minLength} y ${maxLength} caracteres.", key = "error.reason.length") }
)
public class Report {
	private long reportId;
	private Links link;
	private User user;
	private Calendar submited;
	private String reason;
	private ReportStatus status;

	public Report() {
		this.submited = Calendar.getInstance();
		this.status = ReportStatus.PENDING;
	}

	public Report(Links link, User user, String reason) {
		this.link = link;
		this.user = user;
		this.reason = reason;
		this.submited = Calendar.getInstance();
		this.status = ReportStatus.PENDING;
	}

	@SequenceGenerator(name = "ReportIdGenerator", sequenceName = "ReportSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ReportIdGenerator")
	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	@OneToOne(optional=false, fetch=FetchType.EAGER) 
	@JoinColumn(name="linkId")
	public Links getLink() {
		return link;
	}

	public void setLink(Links link) {
		this.link = link;
	}

	@ManyToOne(optional=false, fetch=FetchType.EAGER) 
	@JoinColumn(name="userId")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getSubmited() {
		return submited;
	}

	public void setSubmited(Calendar submited) {
		this.submited = submited;
	}

	@Type(type="text")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Enumerated(EnumType.STRING)
	public ReportStatus getStatus() {
		return status;
	}

	public void setStatus(ReportStatus status) {
		this.status = status;
	}

}
