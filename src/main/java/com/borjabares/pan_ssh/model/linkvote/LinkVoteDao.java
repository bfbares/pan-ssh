package com.borjabares.pan_ssh.model.linkvote;

import java.util.List;

import com.borjabares.modelutil.dao.GenericDao;

public interface LinkVoteDao extends GenericDao<LinkVote, Long>{

	public boolean userVotedLink(long userId, long linkId);
	
	public boolean ipVoted(String ip, long linkId);
	
	public List<LinkVote> getUpvotesTodayByUser(long userId);
	
	public List<LinkVote> getDownvotesTodayByUser(long userId);
	
	public long getNumberOfVotes(long linkId);
	
	public long getNumberOfUpvotes(long linkId);
	
	public long getNumberOfNonAnonymousUpvotes(long linkId);
	
	public long getNumberOfUpvotesTodayByUser(long userId);
	
	public long getNumberOfVotesTodayByUser(long userId);
	
}
