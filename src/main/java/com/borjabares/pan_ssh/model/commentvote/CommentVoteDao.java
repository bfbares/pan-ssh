package com.borjabares.pan_ssh.model.commentvote;

import com.borjabares.modelutil.dao.GenericDao;

public interface CommentVoteDao extends GenericDao<CommentVote, Long>{
	
	public long getCommentVotesTodayToUserId(long userId);
	
	public long getCommentUpvotesTodayToUserId(long userId);
	
	public boolean userVotedComment(long userId, long commentId);
	
	public long getNumberOfVotes(long commentId);
	
	public long getCommentVotesTodayByUserId(long userId);
	
}
