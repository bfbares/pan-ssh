package com.borjabares.pan_ssh.model.links;

import java.util.List;

import com.borjabares.modelutil.dao.GenericDao;
import com.borjabares.pan_ssh.util.GlobalNames.LinkStatus;

public interface LinksDao extends GenericDao<Links, Long> {
	
	public boolean existsUrl(String url);
	
	public List<Links> listLinks(int startIndex, int count);
	
	public List<Links> getLinksByUserId(int startIndex, int count, long userId);
	
	public List<Links> getLinksByStatus(int startIndex, int count, LinkStatus status);
	
	public int getNumberOfLinks();
	
	public int getNumberOfLinksByUserId(long userId);
	
	public int getNumberOfLinksByStatus(LinkStatus status);
	
}
