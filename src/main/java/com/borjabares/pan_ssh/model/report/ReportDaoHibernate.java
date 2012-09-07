package com.borjabares.pan_ssh.model.report;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.borjabares.modelutil.dao.GenericDaoHibernate;
import com.borjabares.pan_ssh.util.GlobalNames.ReportStatus;

@Repository("ReportDao")
public class ReportDaoHibernate extends GenericDaoHibernate<Report, Long>
		implements ReportDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<Report> getPendingReports(int startIndex, int count) {
		return getSession()
				.createQuery(
						"SELECT r FROM Report r WHERE r.status = :pending ORDER BY r.submited")
				.setParameter("pending", ReportStatus.PENDING)
				.setFirstResult(startIndex).setMaxResults(count).list();
	}

	@Override
	public int getNumberOfPendingReports() {
		long numberOfReports = (Long) getSession()
				.createQuery(
						"SELECT COUNT(r) FROM Report r WHERE r.status = :pending")
				.setParameter("pending", ReportStatus.PENDING).uniqueResult();
		return (int) numberOfReports;
	}

	@Override
	public int getNumberOfReportsByUserIdToday(long userId) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, -1);
		long numberOfReports = (Long) getSession()
				.createQuery(
						"SELECT COUNT(r) FROM Report r WHERE r.user.userId = :userId"
								+ " AND r.submited > :date")
				.setParameter("userId", userId).setCalendarDate("date", date)
				.uniqueResult();
		return (int) numberOfReports;
	}

	@Override
	public boolean linkReported(long linkId) {
		Report report = (Report) getSession()
				.createQuery(
						"SELECT r FROM Report r WHERE r.link.linkId = :linkId")
				.setParameter("linkId", linkId).uniqueResult();

		if (report != null && report.getStatus() == ReportStatus.PENDING) {
			return true;
		}

		return false;
	}

}
