package com.borjabares.pan_ssh.model.linkvote;

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

	public boolean ipVoted(String ip, long linkId) {
		LinkVote linkvote = (LinkVote) getSession()
				.createQuery(
						"SELECT l FROM LinkVote l WHERE l.ip = :ip"
								+ " AND l.link.linkId = :linkId")
				.setParameter("ip", ip).setParameter("linkId", linkId)
				.uniqueResult();

		if (linkvote != null) {
			return true;
		}

		return false;
	}
}
