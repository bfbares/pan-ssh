package com.borjabares.pan_ssh.model.linkvote;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.borjabares.modelutil.dao.GenericDaoHibernate;
import com.borjabares.pan_ssh.util.GlobalNames.VoteType;

@Repository("LinkVoteDao")
public class LinkVoteDaoHibernate extends GenericDaoHibernate<LinkVote, Long>
		implements LinkVoteDao {

	@Override
	public boolean userVotedLink(long userId, long linkId) {
		LinkVote linkvote = (LinkVote) getSession()
				.createQuery(
						"SELECT l FROM LinkVote l WHERE l.user.userId = :userId"
								+ " AND l.link.linkId = :linkId")
				.setParameter("userId", userId).setParameter("linkId", linkId)
				.uniqueResult();

		if (linkvote != null) {
			return true;
		}

		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean ipVoted(String ip, long linkId) {
		List<LinkVote> linkvote = getSession()
				.createQuery(
						"SELECT l FROM LinkVote l WHERE l.ip = :ip"
								+ " AND l.link.linkId = :linkId")
				.setParameter("ip", ip).setParameter("linkId", linkId).list();

		if (!linkvote.isEmpty()) {
			return true;
		}

		return false;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<LinkVote> getUpvotesTodayByUser(long userId) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, -1);
		return getSession()
				.createQuery(
						"SELECT l FROM LinkVote l "
								+ "WHERE l.type = :type AND l.user.userId = :userId"
								+ " AND l.link.published > :date AND l.link.published > l.submited")
				.setParameter("userId", userId)
				.setParameter("type", VoteType.UPVOTE)
				.setCalendarDate("date", date).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LinkVote> getDownvotesTodayByUser(long userId) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, -1);
		return getSession()
				.createQuery(
						"SELECT l FROM LinkVote l "
								+ "WHERE l.type = :type AND l.user.userId = :userId"
								+ " AND l.link.published > :date AND l.link.published > l.submited")
				.setParameter("userId", userId)
				.setParameter("type", VoteType.DOWNVOTE)
				.setCalendarDate("date", date).list();
	}

	@Override
	public long getNumberOfVotes(long linkId) {
		return (Long) getSession()
				.createQuery(
						"SELECT COUNT(l) FROM LinkVote l WHERE l.link.linkId = :linkId")
				.setParameter("linkId", linkId).uniqueResult();
	}

	@Override
	public long getNumberOfUpvotes(long linkId) {
		return (Long) getSession()
				.createQuery(
						"SELECT COUNT(l) FROM LinkVote l "
								+ "WHERE l.link.linkId = :linkId AND l.type = :type")
				.setParameter("linkId", linkId)
				.setParameter("type", VoteType.UPVOTE).uniqueResult();
	}

	@Override
	public long getNumberOfNonAnonymousUpvotes(long linkId) {
		return (Long) getSession()
				.createQuery(
						"SELECT COUNT(l) FROM LinkVote l "
								+ "WHERE l.link.linkId = :linkId"
								+ " AND l.type = :type AND l.user IS NOT NULL")
				.setParameter("linkId", linkId)
				.setParameter("type", VoteType.UPVOTE).uniqueResult();
	}

	@Override
	public long getNumberOfUpvotesTodayByUser(long userId) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, -1);
		return (Long) getSession()
				.createQuery(
						"SELECT COUNT(l) FROM LinkVote l "
								+ "WHERE l.type = :type AND l.user.userId = :userId"
								+ " AND l.link.published > :date AND l.link.published > l.submited")
				.setParameter("userId", userId)
				.setParameter("type", VoteType.UPVOTE)
				.setCalendarDate("date", date).uniqueResult();
	}

	@Override
	public long getNumberOfVotesTodayByUser(long userId) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, -1);
		return (Long) getSession()
				.createQuery(
						"SELECT COUNT(l) FROM LinkVote l "
								+ "WHERE l.user.userId = :userId"
								+ " AND l.submited > :date")
				.setParameter("userId", userId).setCalendarDate("date", date)
				.uniqueResult();
	}

}
