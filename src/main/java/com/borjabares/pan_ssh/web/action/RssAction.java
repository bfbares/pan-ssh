package com.borjabares.pan_ssh.web.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.borjabares.pan_ssh.model.links.Links;
import com.borjabares.pan_ssh.model.panservice.PanService;
import com.borjabares.pan_ssh.util.GlobalNames.LinkStatus;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;

@SuppressWarnings("serial")
public class RssAction extends ActionSupport {

	@Autowired
	private PanService panService;
	private List<Links> links;
	private SyndFeed myFeed;

	public PanService getPanService() {
		return panService;
	}

	public void setPanService(PanService panService) {
		this.panService = panService;
	}

	public List<Links> getLinks() {
		return links;
	}

	public void setLinks(List<Links> links) {
		this.links = links;
	}

	public SyndFeed getMyFeed() {
		return myFeed;
	}

	public void setMyFeed(SyndFeed myFeed) {
		this.myFeed = myFeed;
	}

	public String execute() {

		myFeed = new SyndFeedImpl();

		myFeed.setFeedType("rss_2.0");
		myFeed.setTitle(getText("pan.title"));
		myFeed.setLink(getText("pan.url"));
		myFeed.setDescription(getText("pan.description"));

		List<SyndEntry> entries = new ArrayList<SyndEntry>();

		links = panService.listLinksByStatus(0, 50, LinkStatus.PUBLISHED).getList();
		for (Iterator<Links> iterator = links.iterator(); iterator.hasNext();) {
			Links link = (Links) iterator.next();
			SyndEntry entry = new SyndEntryImpl();
			entry.setTitle(link.getTitle());
			entry.setAuthor(link.getLinkAuthor().getLogin());
			entry.setPublishedDate(link.getPublished().getTime());
			entry.setLink(link.getUrl());
			SyndContent description = new SyndContentImpl();
			description.setValue(link.getDescription());
			entry.setDescription(description);
			entries.add(entry);
		}

		myFeed.setEntries(entries);

		return SUCCESS;
	}
}
