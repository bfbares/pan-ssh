package com.borjabares.pan_ssh.model.linkvote;

import com.borjabares.modelutil.dao.GenericDao;

public interface LinkVoteDao extends GenericDao<LinkVote, Long>{

	public boolean userVotedLink(long userId, long linkId);
	
	public boolean ipVoted(String ip, long linkId);
	
	public long getNumberOfVotes(long linkId);
	
}
