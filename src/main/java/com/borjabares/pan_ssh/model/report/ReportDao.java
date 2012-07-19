package com.borjabares.pan_ssh.model.report;

import java.util.List;

import com.borjabares.modelutil.dao.GenericDao;

public interface ReportDao extends GenericDao<Report, Long> {
	
	public List<Report> getPendingReports(int startIndex, int count);

	public int getNumberOfPendingReports();
	
	public boolean linkReported(long linkId);
}
