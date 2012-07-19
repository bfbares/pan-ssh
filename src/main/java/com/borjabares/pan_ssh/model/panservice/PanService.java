package com.borjabares.pan_ssh.model.panservice;

import java.security.NoSuchAlgorithmException;

import com.borjabares.modelutil.exceptions.InstanceNotFoundException;
import com.borjabares.pan_ssh.exceptions.DuplicatedCategoryNameException;
import com.borjabares.pan_ssh.exceptions.DuplicatedLinkException;
import com.borjabares.pan_ssh.exceptions.DuplicatedUserEmailException;
import com.borjabares.pan_ssh.exceptions.DuplicatedUserLoginException;
import com.borjabares.pan_ssh.exceptions.IpVotedException;
import com.borjabares.pan_ssh.exceptions.LinkAlreadyReportedException;
import com.borjabares.pan_ssh.exceptions.ParentCategoryException;
import com.borjabares.pan_ssh.exceptions.UserVotedException;
import com.borjabares.pan_ssh.model.category.Category;
import com.borjabares.pan_ssh.model.links.Links;
import com.borjabares.pan_ssh.model.linkvote.LinkVote;
import com.borjabares.pan_ssh.model.report.Report;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames.Level;
import com.borjabares.pan_ssh.util.GlobalNames.LinkStatus;

public interface PanService {

	public User createUser(User user) throws NoSuchAlgorithmException,
			DuplicatedUserLoginException, DuplicatedUserEmailException;

	public void updateUser(User user) throws InstanceNotFoundException,
			NoSuchAlgorithmException;

	public void deleteUser(long userId) throws InstanceNotFoundException;

	public User findUser(long userId) throws InstanceNotFoundException;

	public ObjectBlock<User> listAllUsers(int startIndex, int count);

	public ObjectBlock<User> listAllUsersSortedBy(int startIndex, int count,
			String criteria, boolean asc);

	public ObjectBlock<User> listUsersByLevel(int startIndex, int count, Level level);

	public int getNumberOfUsers();

	public long loginUser(String login, String password);

	public boolean userExists(String login);

	public Links createLink(long userId, Links link)
			throws InstanceNotFoundException, DuplicatedLinkException;

	public void updateLink(Links link) throws InstanceNotFoundException;

	public void deleteLink(long linkId) throws InstanceNotFoundException;

	public Links findLink(long linkId) throws InstanceNotFoundException;
	
	public ObjectBlock<Links> listLinks(int startIndex, int count);
	
	public ObjectBlock<Links> listLinksByUserId(int startIndex, int count, long userId);
	
	public ObjectBlock<Links> listLinksByStatus(int startIndex, int count, LinkStatus status);

	public Category createCategory(Category category)
			throws ParentCategoryException, DuplicatedCategoryNameException;

	public void updateCategory(Category category);

	public void deleteCategory(long categoryId)
			throws InstanceNotFoundException;

	public Category findCategory(long categoryId)
			throws InstanceNotFoundException;

	public Report createReport(Report report) throws LinkAlreadyReportedException;
	
	public void updateReport(Report report);
	
	public ObjectBlock<Report> listPendingReports(int startIndex, int count);
	
	public void deleteReport(long reportId) throws InstanceNotFoundException;
	
	public Report findReport(long reportId) throws InstanceNotFoundException;
	
	public LinkVote createVote(LinkVote vote) throws IpVotedException, UserVotedException;
	
	public LinkVote findVote(long voteId) throws InstanceNotFoundException;

}
