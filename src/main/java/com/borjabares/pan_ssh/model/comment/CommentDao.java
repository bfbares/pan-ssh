package com.borjabares.pan_ssh.model.comment;

import java.util.List;

import com.borjabares.modelutil.dao.GenericDao;

public interface CommentDao extends GenericDao<Comment, Long>{
	
	public List<Comment> listCommentsByLinkId(int startIndex, int count, long linkId);
	
	public List<Comment> listCommentsByLinkIdByKarma(int startIndex, int count, long linkId);
	
	public int getNumberOfCommentsByLinkId(long linkId);
	
	public long getNumberOfCommentsByUserIdToday(long userId);

}
