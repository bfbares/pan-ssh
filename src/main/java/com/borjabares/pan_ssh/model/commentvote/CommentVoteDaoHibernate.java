package com.borjabares.pan_ssh.model.commentvote;

import java.util.Calendar;

import org.springframework.stereotype.Repository;

import com.borjabares.modelutil.dao.GenericDaoHibernate;
import com.borjabares.pan_ssh.util.GlobalNames.VoteType;

@Repository("CommentVoteDao")
public class CommentVoteDaoHibernate extends
		GenericDaoHibernate<CommentVote, Long> implements CommentVoteDao {

	@Override
	public long getCommentVotesTodayToUserId(long userId) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, -1);
		return (Long) getSession()
				.createQuery(
						"SELECT COUNT(c) FROM CommentVote c "
								+ "WHERE c.comment.author.userId = :userId"
								+ " AND c.submited > :date")
				.setParameter("userId", userId).setCalendarDate("date", date)
				.uniqueResult();
	}

	@Override
	public long getCommentUpvotesTodayToUserId(long userId) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, -1);
		return (Long) getSession()
				.createQuery(
						"SELECT COUNT(c) FROM CommentVote c "
								+ "WHERE c.type = :type "
								+ "AND c.comment.author.userId = :userId "
								+ "AND c.submited > :date")
				.setParameter("type", VoteType.UPVOTE)
				.setParameter("userId", userId).setCalendarDate("date", date)
				.uniqueResult();
	}

	@Override
	public boolean userVotedComment(long userId, long commentId) {
		CommentVote commentvote = (CommentVote) getSession()
				.createQuery(
						"SELECT c FROM CommentVote c WHERE c.user.userId = :userId"
								+ " AND c.comment.commentId = :commentId")
				.setParameter("userId", userId)
				.setParameter("commentId", commentId).uniqueResult();

		if (commentvote != null) {
			return true;
		}

		return false;
	}

	@Override
	public long getNumberOfVotes(long commentId) {
		return (Long) getSession()
				.createQuery(
						"SELECT COUNT(c) FROM CommentVote c "
								+ "WHERE c.comment.commentId = :commentId")
				.setParameter("commentId", commentId).uniqueResult();

	}
	
	@Override
	public long getCommentVotesTodayByUserId(long userId){
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, -1);
		return (Long) getSession()
				.createQuery(
						"SELECT COUNT(c) FROM CommentVote c "
								+ "WHERE c.user.userId = :userId"
								+ " AND c.submited > :date")
				.setParameter("userId", userId).setCalendarDate("date", date)
				.uniqueResult();
	}

}
