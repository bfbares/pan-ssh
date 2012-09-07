package com.borjabares.pan_ssh.model.comment;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.borjabares.modelutil.dao.GenericDaoHibernate;

@Repository("CommentDao")
public class CommentDaoHibernate extends GenericDaoHibernate<Comment, Long>
		implements CommentDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<Comment> listCommentsByLinkId(int startIndex, int count,
			long linkId) {
		return getSession()
				.createQuery(
						"SELECT c FROM Comment c JOIN c.link"
								+ " WHERE c.link.linkId = :linkId ORDER BY c.submited")
				.setParameter("linkId", linkId).setFirstResult(startIndex)
				.setMaxResults(count).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Comment> listCommentsByLinkIdByKarma(int startIndex, int count,
			long linkId) {
		return getSession()
				.createQuery(
						"SELECT c FROM Comment c JOIN c.link"
								+ " WHERE c.link.linkId = :linkId ORDER BY c.karma DESC")
				.setParameter("linkId", linkId).setFirstResult(startIndex)
				.setMaxResults(count).list();
	}

	@Override
	public int getNumberOfCommentsByLinkId(long linkId) {
		long numberofLinks = (Long) getSession()
				.createQuery(
						"SELECT COUNT(c) FROM Comment c JOIN c.link"
								+ " WHERE c.link.linkId = :linkId")
				.setParameter("linkId", linkId).uniqueResult();

		return (int) numberofLinks;
	}

	@Override
	public long getNumberOfCommentsByUserIdToday(long userId) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, -1);

		return (Long) getSession()
				.createQuery(
						"SELECT COUNT(c) FROM Comment c "
								+ "WHERE c.submited > :date "
								+ "AND c.author.userId = :userId")
				.setCalendarDate("date", date).setParameter("userId", userId)
				.uniqueResult();
	}

}
