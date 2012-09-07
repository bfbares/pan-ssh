package com.borjabares.pan_ssh.model.links;

import java.util.List;

import com.borjabares.modelutil.dao.GenericDao;
import com.borjabares.pan_ssh.model.category.Category;
import com.borjabares.pan_ssh.util.GlobalNames.LinkStatus;

public interface LinksDao extends GenericDao<Links, Long> {

	public boolean existsUrl(String url);

	public boolean existsFtitle(String ftitle);

	public Links findLinkByFtitle(String ftitle);

	public List<Links> listLinks(int startIndex, int count);

	public List<Links> getLinksByUserId(int startIndex, int count, long userId);

	public List<Links> getLinksByStatus(int startIndex, int count,
			LinkStatus status);

	public List<Links> getLinksByCategoryAndStatus(int startIndex, int count,
			LinkStatus status, Category category);
	
	public List<Links> getLinksByParentCategoryAndStatus(int startIndex, int count,
			LinkStatus status, Category category);
	
	public List<Links> getOlderQueuedLinks(int count);
	
	public List<Links> getQueuedLinksByKarma();
	
	public List<Links> getQueuedLinksOlderThanAWeek();
	
	public List<Links> getPublishedLinksToday();

	public int getNumberOfLinks();

	public int getNumberOfLinksByUserId(long userId);

	public int getNumberOfLinksByStatus(LinkStatus status);

	public int getNumberOfLinksByCategoryAndStatus(LinkStatus status,
			Category category);

	public int getNumberOfLinksByParentCategoryAndStatus(LinkStatus status,
			Category category);
	
	public int getNumberOfPublishedLinksTodayByUser(long userId);
	
	public int getNumberOfLinksTodayByUser(long userId);
	
}
