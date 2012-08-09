package com.borjabares.pan_ssh.model.linkvote;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.borjabares.modelutil.dao.GenericDaoHibernate;

@Repository("LinkVoteDao")
public class LinkVoteDaoHibernate extends GenericDaoHibernate<LinkVote, Long>
		implements LinkVoteDao {

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

	@SuppressWarnings("unchecked")
	public boolean ipVoted(String ip, long linkId) {
		List <LinkVote> linkvote = getSession()
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
	public long getNumberOfVotes(long linkId) {
		return (Long) getSession()
				.createQuery(
						"SELECT COUNT(l) FROM LinkVote l WHERE l.link.linkId = :linkId")
				.setParameter("linkId", linkId).uniqueResult();

	}
}
