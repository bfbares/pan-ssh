package com.borjabares.pan_ssh.model.links;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.borjabares.modelutil.dao.GenericDaoHibernate;
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
		return getSession()
				.createQuery(
						"SELECT l FROM Links l WHERE l.status = :status ORDER BY l.submited DESC")
				.setParameter("status", status).setFirstResult(startIndex)
				.setMaxResults(count).list();
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

}
