package com.borjabares.pan_ssh.model.links;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.borjabares.modelutil.dao.GenericDaoHibernate;
import com.borjabares.pan_ssh.model.category.Category;
import com.borjabares.pan_ssh.util.GlobalNames.LinkStatus;

@Repository("LinksDao")
public class LinksDaoHibernate extends GenericDaoHibernate<Links, Long>
		implements LinksDao {

	@Override
	public boolean existsUrl(String url) {
		Links link = (Links) getSession()
				.createQuery("SELECT l FROM Links l WHERE l.url = :url")
				.setParameter("url", url).uniqueResult();

		if (link != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean existsFtitle(String ftitle) {
		Links link = (Links) getSession()
				.createQuery("SELECT l FROM Links l WHERE l.ftitle = :ftitle")
				.setParameter("ftitle", ftitle).uniqueResult();

		if (link != null) {
			return true;
		}

		return false;
	}

	public Links findLinkByFtitle(String ftitle) {
		return (Links) getSession()
				.createQuery("SELECT l FROM Links l WHERE l.ftitle = :ftitle")
				.setParameter("ftitle", ftitle).uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Links> listLinks(int startIndex, int count) {
		return getSession()
				.createQuery("SELECT l FROM Links l ORDER BY l.submited DESC")
				.setFirstResult(startIndex).setMaxResults(count).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Links> getLinksByUserId(int startIndex, int count, long userId) {
		return getSession()
				.createQuery(
						"SELECT l FROM Links l WHERE l.linkAuthor.userId = :userId ORDER BY l.submited DESC")
				.setParameter("userId", userId).setFirstResult(startIndex)
				.setMaxResults(count).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Links> getLinksByStatus(int startIndex, int count,
			LinkStatus status) {
		if (status != LinkStatus.PUBLISHED) {
			return getSession()
					.createQuery(
							"SELECT l FROM Links l WHERE l.status = :status ORDER BY l.submited DESC")
					.setParameter("status", status).setFirstResult(startIndex)
					.setMaxResults(count).list();
		} else {
			return getSession()
					.createQuery(
							"SELECT l FROM Links l WHERE l.status = :status ORDER BY l.published DESC")
					.setParameter("status", status).setFirstResult(startIndex)
					.setMaxResults(count).list();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Links> getLinksByCategoryAndStatus(int startIndex, int count,
			LinkStatus status, Category category) {
		if (status != LinkStatus.PUBLISHED) {
			return getSession()
					.createQuery(
							"SELECT l FROM Links l JOIN l.categoryId c WHERE l.status = :status "
									+ "AND c.categoryId.categoryId = :categoryId ORDER BY l.submited DESC")
					.setParameter("status", status)
					.setParameter("categoryId", category.getCategoryId())
					.setFirstResult(startIndex).setMaxResults(count).list();
		} else {
			return getSession()
					.createQuery(
							"SELECT l FROM Links l JOIN l.categoryId c WHERE l.status = :status "
									+ "AND c.categoryId.categoryId = :categoryId ORDER BY l.published DESC")
					.setParameter("status", status)
					.setParameter("categoryId", category.getCategoryId())
					.setFirstResult(startIndex).setMaxResults(count).list();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Links> getLinksByParentCategoryAndStatus(int startIndex,
			int count, LinkStatus status, Category category) {
		if (status != LinkStatus.PUBLISHED) {
			return getSession()
					.createQuery(
							"SELECT l FROM Links l JOIN l.categoryId c WHERE l.status = :status "
									+ "AND c.categoryId.parent.categoryId = :categoryId ORDER BY l.submited DESC")
					.setParameter("status", status)
					.setParameter("categoryId", category.getCategoryId())
					.setFirstResult(startIndex).setMaxResults(count).list();
		} else {
			return getSession()
					.createQuery(
							"SELECT l FROM Links l JOIN l.categoryId c WHERE l.status = :status "
									+ "AND c.categoryId.parent.categoryId = :categoryId ORDER BY l.published DESC")
					.setParameter("status", status)
					.setParameter("categoryId", category.getCategoryId())
					.setFirstResult(startIndex).setMaxResults(count).list();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Links> getOlderQueuedLinks(int count) {
		return getSession()
				.createQuery(
						"SELECT l FROM Links l WHERE l.status = :status "
								+ "ORDER BY l.submited")
				.setParameter("status", LinkStatus.QUEUED).setMaxResults(count)
				.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Links> getQueuedLinksByKarma() {
		return getSession()
				.createQuery(
						"SELECT l FROM Links l WHERE l.status = :status "
								+ "ORDER BY l.karma DESC")
				.setParameter("status", LinkStatus.QUEUED).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Links> getQueuedLinksOlderThanAWeek() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.WEEK_OF_YEAR, -1);
		return getSession()
				.createQuery(
						"SELECT l FROM Links l WHERE l.status = :status "
								+ "AND l.submited < :date")
				.setParameter("status", LinkStatus.QUEUED)
				.setCalendarDate("date", date).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Links> getPublishedLinksToday() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, -1);
		return getSession()
				.createQuery(
						"SELECT l FROM Links l WHERE l.status = :status "
								+ "AND l.published > :date")
				.setParameter("status", LinkStatus.PUBLISHED)
				.setCalendarDate("date", date).list();
	}

	@Override
	public int getNumberOfLinks() {
		long numberofLinks = (Long) getSession().createQuery(
				"SELECT COUNT(l) FROM Links l").uniqueResult();

		return (int) numberofLinks;
	}

	@Override
	public int getNumberOfLinksByUserId(long userId) {
		long numberofLinks = (Long) getSession()
				.createQuery(
						"SELECT COUNT(l) FROM Links l WHERE l.linkAuthor.userId = :userId")
				.setParameter("userId", userId).uniqueResult();

		return (int) numberofLinks;
	}

	@Override
	public int getNumberOfLinksByStatus(LinkStatus status) {
		long numberofLinks = (Long) getSession()
				.createQuery(
						"SELECT COUNT(l) FROM Links l WHERE l.status = :status")
				.setParameter("status", status).uniqueResult();

		return (int) numberofLinks;
	}

	@Override
	public int getNumberOfLinksByCategoryAndStatus(LinkStatus status,
			Category category) {
		long numberofLinks = (Long) getSession()
				.createQuery(
						"SELECT COUNT(l) FROM Links l JOIN l.categoryId c WHERE l.status = :status "
								+ "AND c.categoryId.categoryId = :categoryId")
				.setParameter("status", status)
				.setParameter("categoryId", category.getCategoryId())
				.uniqueResult();

		return (int) numberofLinks;
	}

	@Override
	public int getNumberOfLinksByParentCategoryAndStatus(LinkStatus status,
			Category category) {
		long numberofLinks = (Long) getSession()
				.createQuery(
						"SELECT COUNT(l) FROM Links l JOIN l.categoryId c WHERE l.status = :status "
								+ "AND c.categoryId.parent.categoryId = :categoryId")
				.setParameter("status", status)
				.setParameter("categoryId", category.getCategoryId())
				.uniqueResult();

		return (int) numberofLinks;
	}

	@Override
	public int getNumberOfPublishedLinksTodayByUser(long userId) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, -1);
		long numberofLinks = (Long) getSession()
				.createQuery(
						"SELECT COUNT(l) FROM Links l WHERE l.status = :status "
								+ "AND l.published > :date AND l.linkAuthor.userId = :userId")
				.setParameter("status", LinkStatus.PUBLISHED)
				.setParameter("userId", userId).setCalendarDate("date", date)
				.uniqueResult();

		return (int) numberofLinks;
	}

	@Override
	public int getNumberOfLinksTodayByUser(long userId) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, -1);
		long numberofLinks = (Long) getSession()
				.createQuery(
						"SELECT COUNT(l) FROM Links l WHERE l.published > :date "
								+ "AND l.linkAuthor.userId = :userId")
				.setParameter("userId", userId).setCalendarDate("date", date)
				.uniqueResult();

		return (int) numberofLinks;
	}

}
