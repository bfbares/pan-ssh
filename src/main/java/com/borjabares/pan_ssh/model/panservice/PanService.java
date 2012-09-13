package com.borjabares.pan_ssh.model.panservice;

import java.security.NoSuchAlgorithmException;
import java.util.List;

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
import com.borjabares.pan_ssh.model.comment.Comment;
import com.borjabares.pan_ssh.model.commentvote.CommentVote;
import com.borjabares.pan_ssh.model.links.Links;
import com.borjabares.pan_ssh.model.linkvote.LinkVote;
import com.borjabares.pan_ssh.model.report.Report;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.util.GlobalNames.Level;
import com.borjabares.pan_ssh.util.GlobalNames.LinkStatus;

public interface PanService {

	public User createUser(User user) throws NoSuchAlgorithmException,
			DuplicatedUserLoginException, DuplicatedUserEmailException;

	public void updateUser(User user) throws InstanceNotFoundException;

	public void updateUserAndPass(User user) throws InstanceNotFoundException,
			NoSuchAlgorithmException;

	public void deleteUser(long userId) throws InstanceNotFoundException;

	public User findUser(long userId) throws InstanceNotFoundException;

	public User findUserByLogin(String login);

	public ObjectBlock<User> listAllUsers(int startIndex, int count);

	public ObjectBlock<User> listUsersByCriteria(int startIndex, int count,
			String criteria);

	public ObjectBlock<User> listUsersByLevel(int startIndex, int count,
			Level level);

	public int getNumberOfUsers();

	public long loginUser(String login, String password);

	public boolean userExists(String login);

	public Links createLink(Links link) throws DuplicatedLinkException;

	public void updateLink(Links link) throws InstanceNotFoundException;

	public void deleteLink(long linkId) throws InstanceNotFoundException;

	public Links findLink(long linkId) throws InstanceNotFoundException;
	
	public void publishLink(long linkId) throws InstanceNotFoundException;
	
	public void discardLink(long linkId) throws InstanceNotFoundException;
	
	public FullLink findFullLink(long linkId, User user, String ip)
			throws InstanceNotFoundException;

	public FullLink findFullLinkByFtitle(String ftitle, User user, String ip)
			throws InstanceNotFoundException;

	public ObjectBlock<Links> listLinks(int startIndex, int count);

	public ObjectBlock<Links> listLinksByUserId(int startIndex, int count,
			long userId);

	public ObjectBlock<Links> listLinksByStatus(int startIndex, int count,
			LinkStatus status);

	public ObjectBlock<FullLink> listFullLinksByStatus(int startIndex,
			int count, LinkStatus status, User user, String ip);
	
	public ObjectBlock<FullLink> listFullLinksByCategoryAndStatus(int startIndex,
			int count, LinkStatus status, Category category, User user, String ip);
	
	public ObjectBlock<FullLink> listFullLinksByParentCategoryAndStatus(int startIndex,
			int count, LinkStatus status, Category category, User user, String ip);

	public Category createCategory(Category category)
			throws ParentCategoryException, DuplicatedCategoryNameException;

	public void updateCategory(Category category);

	public void deleteCategory(long categoryId)
			throws InstanceNotFoundException;

	public Category findCategory(long categoryId)
			throws InstanceNotFoundException;

	public Category findCategoryByName(String name)
	throws InstanceNotFoundException;
	
	public List<Category> listParentCategories();

	public List<Category> listNonParentCategories();

	public List<Category> listAllCategories();
	
	public List<Category> listAllCategoriesSorted();

	public List<Category> listCategoryChildrens(long parentId);

	public Report createReport(Report report)
			throws LinkAlreadyReportedException;

	public void updateReport(Report report);

	public ObjectBlock<Report> listPendingReports(int startIndex, int count);

	public void deleteReport(long reportId) throws InstanceNotFoundException;

	public Report findReport(long reportId) throws InstanceNotFoundException;

	public LinkVote createVote(LinkVote vote) throws IpVotedException,
			UserVotedException;

	public LinkVote findVote(long voteId) throws InstanceNotFoundException;
	
	public long getNumberOfVotes(long linkId);
	
	public Comment createComment(Comment comment);
	
	public void updateComment(Comment comment);
	
	public Comment findComment(long commentId) throws InstanceNotFoundException;
	
	public ObjectBlock<FullComment> listCommentsByLinkId(int startIndex, int count, long linkId, User user);
	
	public ObjectBlock<FullComment> listCommentsByLinkIdByKarma(int startIndex, int count, long linkId, User user);
	
	public CommentVote createCommentVote(CommentVote commentVote) throws UserVotedException, InstanceNotFoundException ;
	
	public CommentVote findCommentVote(long voteId) throws InstanceNotFoundException;

	public void linkWorker() throws InstanceNotFoundException;
	
	public void userWorker() throws InstanceNotFoundException;
	
}
