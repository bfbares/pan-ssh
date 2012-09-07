package com.borjabares.pan_ssh.model.panservice;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.borjabares.pan_ssh.model.category.CategoryDao;
import com.borjabares.pan_ssh.model.comment.Comment;
import com.borjabares.pan_ssh.model.comment.CommentDao;
import com.borjabares.pan_ssh.model.commentvote.CommentVote;
import com.borjabares.pan_ssh.model.commentvote.CommentVoteDao;
import com.borjabares.pan_ssh.model.links.Links;
import com.borjabares.pan_ssh.model.links.LinksDao;
import com.borjabares.pan_ssh.model.linkvote.LinkVote;
import com.borjabares.pan_ssh.model.linkvote.LinkVoteDao;
import com.borjabares.pan_ssh.model.report.Report;
import com.borjabares.pan_ssh.model.report.ReportDao;
import com.borjabares.pan_ssh.model.user.User;
import com.borjabares.pan_ssh.model.user.UserDao;
import com.borjabares.pan_ssh.util.GlobalNames.VoteType;
import com.borjabares.pan_ssh.util.PasswordCodificator;
import com.borjabares.pan_ssh.util.GlobalNames.Level;
import com.borjabares.pan_ssh.util.GlobalNames.LinkStatus;

@Service("PanService")
@Transactional
public class PanServiceImpl implements PanService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private LinksDao linksDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private ReportDao reportDao;

	@Autowired
	private LinkVoteDao linkvoteDao;

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private CommentVoteDao commentvoteDao;

	// **************************************USER**************************************

	@Override
	public User createUser(User user) throws NoSuchAlgorithmException,
			DuplicatedUserLoginException, DuplicatedUserEmailException {
		if (userDao.userExists(user.getLogin())) {
			throw new DuplicatedUserLoginException(user.getLogin());
		}

		if (userDao.emailExists(user.getEmail())) {
			throw new DuplicatedUserEmailException(user.getEmail());
		}

		user.setPassword(PasswordCodificator.codify(user.getPassword()));
		userDao.save(user);

		return user;
	}

	@Override
	public void updateUser(User user) throws InstanceNotFoundException {
		userDao.save(user);
	}

	@Override
	public void updateUserAndPass(User user) throws InstanceNotFoundException,
			NoSuchAlgorithmException {
		user.setPassword(PasswordCodificator.codify(user.getPassword()));
		userDao.save(user);
	}

	@Override
	public void deleteUser(long userId) throws InstanceNotFoundException {
		userDao.remove(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public User findUser(long userId) throws InstanceNotFoundException {
		return userDao.find(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public User findUserByLogin(String login) {
		return userDao.findUserByLogin(login);
	}

	@Override
	@Transactional(readOnly = true)
	public ObjectBlock<User> listAllUsers(int startIndex, int count) {
		return new ObjectBlock<User>(userDao.listUsers(startIndex, count),
				startIndex, count, userDao.getNumberOfUsers());
	}

	@Override
	@Transactional(readOnly = true)
	public ObjectBlock<User> listUsersByCriteria(int startIndex, int count,
			String criteria) {
		return new ObjectBlock<User>(userDao.listUsersByCriteria(startIndex,
				count, criteria), startIndex, count, userDao.getNumberOfUsers());

	}

	@Override
	@Transactional(readOnly = true)
	public ObjectBlock<User> listUsersByLevel(int startIndex, int count,
			Level level) {
		return new ObjectBlock<User>(userDao.listUsersOfLevel(startIndex,
				count, level), startIndex, count, userDao.getNumberOfUsers());
	}

	@Override
	@Transactional(readOnly = true)
	public int getNumberOfUsers() {
		return userDao.getNumberOfUsers();
	}

	@Override
	@Transactional(readOnly = true)
	public long loginUser(String login, String password) {
		return userDao.login(login, password);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean userExists(String login) {
		return userDao.userExists(login);
	}

	// **************************************LINK**************************************

	@Override
	@Transactional
	public Links createLink(Links link) throws DuplicatedLinkException {
		if (linksDao.existsUrl(link.getUrl())) {
			throw new DuplicatedLinkException(link.getUrl());
		}

		int i = 0;
		String ftitle = link.getFtitle();
		while (linksDao.existsFtitle(ftitle)) {
			i++;
			ftitle = link.getFtitle().concat("-" + i);
		}

		link.setFtitle(ftitle);

		linksDao.save(link);

		return link;
	}

	@Override
	@Transactional
	public void updateLink(Links link) throws InstanceNotFoundException {
		linksDao.save(link);
	}

	@Override
	@Transactional
	public void deleteLink(long linkId) throws InstanceNotFoundException {
		linksDao.remove(linkId);
	}

	@Override
	@Transactional(readOnly = true)
	public Links findLink(long linkId) throws InstanceNotFoundException {
		return linksDao.find(linkId);
	}

	@Override
	@Transactional
	public void publishLink(long linkId) throws InstanceNotFoundException {
		Links link = linksDao.find(linkId);
		link.setStatus(LinkStatus.PUBLISHED);
		link.setPublished(Calendar.getInstance());
		updateLink(link);
	}

	@Override
	@Transactional
	public void discardLink(long linkId) throws InstanceNotFoundException {
		Links link = linksDao.find(linkId);
		link.setStatus(LinkStatus.DISCARD);
		link.setPublished(Calendar.getInstance());
		updateLink(link);
	}

	@Transactional(readOnly = true)
	private FullLink fromLinkToFullLink(Links link, User user, String ip) {

		if (user == null) {
			return new FullLink(link, linkvoteDao.getNumberOfVotes(link
					.getLinkId()), commentDao.getNumberOfCommentsByLinkId(link
					.getLinkId()), linkvoteDao.ipVoted(ip, link.getLinkId()),
					null);
		} else {
			return new FullLink(link, linkvoteDao.getNumberOfVotes(link
					.getLinkId()), commentDao.getNumberOfCommentsByLinkId(link
					.getLinkId()), linkvoteDao.userVotedLink(user.getUserId(),
					link.getLinkId()), user);
		}
	}

	@Transactional(readOnly = true)
	private List<FullLink> fromLinkToFullLink(List<Links> links, User user,
			String ip) {
		List<FullLink> fullLink = new ArrayList<FullLink>();

		for (Iterator<Links> iterator = links.iterator(); iterator.hasNext();) {
			Links links2 = (Links) iterator.next();
			fullLink.add(fromLinkToFullLink(links2, user, ip));
		}

		return fullLink;

	}

	@Override
	@Transactional(readOnly = true)
	public FullLink findFullLink(long linkId, User user, String ip)
			throws InstanceNotFoundException {
		Links link = linksDao.find(linkId);

		return fromLinkToFullLink(link, user, ip);
	}

	@Override
	@Transactional(readOnly = true)
	public FullLink findFullLinkByFtitle(String ftitle, User user, String ip)
			throws InstanceNotFoundException {
		Links link = linksDao.findLinkByFtitle(ftitle);
		if (link == null) {
			throw new InstanceNotFoundException(link, ftitle);
		}

		return fromLinkToFullLink(link, user, ip);
	}

	@Override
	@Transactional(readOnly = true)
	public ObjectBlock<Links> listLinks(int startIndex, int count) {
		return new ObjectBlock<Links>(linksDao.listLinks(startIndex, count),
				startIndex, count, linksDao.getNumberOfLinks());
	}

	@Override
	@Transactional(readOnly = true)
	public ObjectBlock<Links> listLinksByUserId(int startIndex, int count,
			long userId) {
		return new ObjectBlock<Links>(linksDao.getLinksByUserId(startIndex,
				count, userId), startIndex, count,
				linksDao.getNumberOfLinksByUserId(userId));
	}

	@Override
	@Transactional(readOnly = true)
	public ObjectBlock<Links> listLinksByStatus(int startIndex, int count,
			LinkStatus status) {
		return new ObjectBlock<Links>(linksDao.getLinksByStatus(startIndex,
				count, status), startIndex, count,
				linksDao.getNumberOfLinksByStatus(status));
	}

	@Override
	@Transactional(readOnly = true)
	public ObjectBlock<FullLink> listFullLinksByStatus(int startIndex,
			int count, LinkStatus status, User user, String ip) {
		List<Links> links = linksDao
				.getLinksByStatus(startIndex, count, status);

		return new ObjectBlock<FullLink>(fromLinkToFullLink(links, user, ip),
				startIndex, count, linksDao.getNumberOfLinksByStatus(status));
	}

	@Override
	@Transactional(readOnly = true)
	public ObjectBlock<FullLink> listFullLinksByCategoryAndStatus(
			int startIndex, int count, LinkStatus status, Category category,
			User user, String ip) {
		List<Links> links = linksDao.getLinksByCategoryAndStatus(startIndex,
				count, status, category);

		return new ObjectBlock<FullLink>(fromLinkToFullLink(links, user, ip),
				startIndex, count,
				linksDao.getNumberOfLinksByCategoryAndStatus(status, category));
	}

	@Override
	@Transactional(readOnly = true)
	public ObjectBlock<FullLink> listFullLinksByParentCategoryAndStatus(
			int startIndex, int count, LinkStatus status, Category category,
			User user, String ip) {
		List<Links> links = linksDao.getLinksByParentCategoryAndStatus(
				startIndex, count, status, category);

		return new ObjectBlock<FullLink>(fromLinkToFullLink(links, user, ip),
				startIndex, count,
				linksDao.getNumberOfLinksByParentCategoryAndStatus(status,
						category));
	}

	// ************************************CATEGORY************************************

	@Override
	@Transactional
	public Category createCategory(Category category)
			throws ParentCategoryException, DuplicatedCategoryNameException {
		if (categoryDao.existsCategory(category.getName())) {
			throw new DuplicatedCategoryNameException(category.getName());
		}
		if (category.getParent() != null) {
			try {
				Category parent = categoryDao.find(category.getParent()
						.getCategoryId());
				if (parent.getParent() != null) {
					throw new ParentCategoryException(category.getParent()
							.getCategoryId());
				}
			} catch (InstanceNotFoundException e) {
				throw new ParentCategoryException(category.getParent()
						.getCategoryId());
			}
		}

		categoryDao.save(category);

		return category;
	}

	@Override
	@Transactional
	public void updateCategory(Category category) {
		categoryDao.save(category);
	}

	@Override
	@Transactional
	public void deleteCategory(long categoryId)
			throws InstanceNotFoundException {
		categoryDao.remove(categoryId);
	}

	@Override
	@Transactional(readOnly = true)
	public Category findCategory(long categoryId)
			throws InstanceNotFoundException {
		return categoryDao.find(categoryId);
	}

	@Override
	@Transactional(readOnly = true)
	public Category findCategoryByName(String name)
			throws InstanceNotFoundException {
		Category category = categoryDao.findCategoryByName(name);

		if (category == null) {
			throw new InstanceNotFoundException(name, "Category");
		}

		return category;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> listParentCategories() {
		return categoryDao.listParentCategories();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> listNonParentCategories() {
		return categoryDao.listNonParentCategories();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> listAllCategories() {
		return categoryDao.listAllCategories();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> listAllCategoriesSorted() {
		List<Category> toRet = new ArrayList<Category>();
		List<Category> parents = categoryDao.listParentCategories();
		for (Iterator<Category> iterator = parents.iterator(); iterator
				.hasNext();) {
			Category parent = (Category) iterator.next();
			toRet.add(parent);
			List<Category> childrens = categoryDao.listCategoryChildrens(parent
					.getCategoryId());
			for (Iterator<Category> iterator2 = childrens.iterator(); iterator2
					.hasNext();) {
				Category children = (Category) iterator2.next();
				children.setName("- " + children.getName());
				toRet.add(children);
			}
		}
		return toRet;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> listCategoryChildrens(long parentId) {
		return categoryDao.listCategoryChildrens(parentId);
	}

	// *************************************REPORT*************************************

	@Override
	@Transactional
	public Report createReport(Report report)
			throws LinkAlreadyReportedException {
		if (reportDao.linkReported(report.getLink().getLinkId())) {
			throw new LinkAlreadyReportedException(report.getLink().getLinkId());
		}

		reportDao.save(report);

		return report;
	}

	@Override
	@Transactional
	public void updateReport(Report report) {
		reportDao.save(report);
	}

	@Override
	@Transactional(readOnly = true)
	public ObjectBlock<Report> listPendingReports(int startIndex, int count) {
		return new ObjectBlock<Report>(reportDao.getPendingReports(startIndex,
				count), startIndex, count,
				reportDao.getNumberOfPendingReports());
	}

	@Override
	@Transactional
	public void deleteReport(long reportId) throws InstanceNotFoundException {
		reportDao.remove(reportId);
	}

	@Override
	@Transactional(readOnly = true)
	public Report findReport(long reportId) throws InstanceNotFoundException {
		return reportDao.find(reportId);
	}

	// ************************************LINKVOTE************************************

	@Override
	@Transactional
	public LinkVote createVote(LinkVote vote) throws IpVotedException,
			UserVotedException {
		float karma = 8;
		if (vote.getUser() == null) {
			if (linkvoteDao.ipVoted(vote.getIp(), vote.getLink().getLinkId())) {
				throw new IpVotedException(vote.getIp(), vote.getLink()
						.getLinkId());
			}
		} else {
			if (linkvoteDao.userVotedLink(vote.getUser().getUserId(), vote
					.getLink().getLinkId())) {
				throw new UserVotedException(vote.getUser().getUserId(), vote
						.getLink().getLinkId());
			}
			karma = vote.getUser().getKarma();
		}

		if (vote.getType() == VoteType.DOWNVOTE) {
			vote.getLink().addKarma(-karma);
		} else {
			vote.getLink().addKarma(karma);
		}

		linksDao.save(vote.getLink());
		linkvoteDao.save(vote);

		return vote;
	}

	@Override
	@Transactional(readOnly = true)
	public LinkVote findVote(long voteId) throws InstanceNotFoundException {
		return linkvoteDao.find(voteId);
	}

	// ************************************COMMENT*************************************

	@Override
	@Transactional
	public Comment createComment(Comment comment) {
		commentDao.save(comment);

		return comment;
	}

	@Override
	@Transactional
	public void updateComment(Comment comment) {
		commentDao.save(comment);
	}

	@Override
	@Transactional(readOnly = true)
	public Comment findComment(long commentId) throws InstanceNotFoundException {
		return commentDao.find(commentId);
	}

	@Transactional(readOnly = true)
	private FullComment fromCommentToFullComment(Comment comment, User user) {
		if (user != null) {
			return new FullComment(comment,
					commentvoteDao.getNumberOfVotes(comment.getCommentId()),
					commentvoteDao.userVotedComment(user.getUserId(),
							comment.getCommentId()), user);
		} else {
			return new FullComment(comment,
					commentvoteDao.getNumberOfVotes(comment.getCommentId()),
					false, user);
		}
	}

	@Transactional(readOnly = true)
	private List<FullComment> fromCommentToFullComment(List<Comment> comments,
			User user) {
		List<FullComment> toRet = new ArrayList<FullComment>();
		for (Iterator<Comment> iterator = comments.iterator(); iterator
				.hasNext();) {
			Comment comment = (Comment) iterator.next();
			toRet.add(fromCommentToFullComment(comment, user));
		}
		return toRet;
	}

	@Override
	@Transactional(readOnly = true)
	public ObjectBlock<FullComment> listCommentsByLinkId(int startIndex,
			int count, long linkId, User user) {
		List<Comment> list = commentDao.listCommentsByLinkId(startIndex, count,
				linkId);
		return new ObjectBlock<FullComment>(
				fromCommentToFullComment(list, user), startIndex, count,
				commentDao.getNumberOfCommentsByLinkId(linkId));
	}

	@Override
	@Transactional(readOnly = true)
	public ObjectBlock<FullComment> listCommentsByLinkIdByKarma(int startIndex,
			int count, long linkId, User user) {
		List<Comment> list = commentDao.listCommentsByLinkIdByKarma(startIndex,
				count, linkId);
		return new ObjectBlock<FullComment>(
				fromCommentToFullComment(list, user), startIndex, count,
				commentDao.getNumberOfCommentsByLinkId(linkId));
	}

	// **********************************COMMENTVOTE***********************************

	@Override
	@Transactional
	public CommentVote createCommentVote(CommentVote commentVote)
			throws UserVotedException, InstanceNotFoundException {
		if (commentvoteDao.userVotedComment(commentVote.getUser().getUserId(),
				commentVote.getComment().getCommentId())) {
			throw new UserVotedException(commentVote.getUser().getUserId(),
					commentVote.getComment().getCommentId());
		}
		Comment comment = commentDao.find(commentVote.getComment()
				.getCommentId());
		float karma = commentVote.getUser().getKarma();
		if (commentVote.getType() == VoteType.DOWNVOTE) {
			comment.addKarma(-karma);
		} else {
			comment.addKarma(karma);
		}
		commentvoteDao.save(commentVote);
		commentDao.save(comment);
		return commentVote;
	}

	@Override
	@Transactional(readOnly = true)
	public CommentVote findCommentVote(long voteId)
			throws InstanceNotFoundException {
		return commentvoteDao.find(voteId);
	}

	// ************************************WORKERS*************************************

	@Override
	@Transactional
	@Scheduled(cron = "0 */20 * * * ?")
	public void linkWorker() throws InstanceNotFoundException {

		// PUBLISH
		if (linksDao.getNumberOfLinksByStatus(LinkStatus.QUEUED) > 10) {
			// Calculate category coefficient
			List<Category> categories = categoryDao.listParentCategories();
			float catNum = categories.size();
			float publishedNum = linksDao
					.getNumberOfLinksByStatus(LinkStatus.PUBLISHED);
			List<Float> catCoef = new ArrayList<Float>();
			for (Iterator<Category> iterator = categories.iterator(); iterator
					.hasNext();) {
				Category category = (Category) iterator.next();
				float linksCat = linksDao
						.getNumberOfLinksByParentCategoryAndStatus(
								LinkStatus.PUBLISHED, category);
				if (linksCat > 0 && publishedNum > 0) {
					catCoef.add(((1 / catNum) / (linksCat / publishedNum)));
				} else {
					catCoef.add(2.0F);
				}
			}

			List<Links> linksList = linksDao.getQueuedLinksByKarma();
			boolean isFirst = true;
			List<Float> linkBonus = new ArrayList<Float>();
			for (Iterator<Links> iterator = linksList.iterator(); iterator
					.hasNext();) {
				Links link = (Links) iterator.next();
				long linkId = link.getLinkId();
				float bonus = 0;

				if (linkvoteDao.getNumberOfVotes(linkId) > 10) {
					float karma = link.getKarma();
					// Bonus for being first
					if (isFirst) {
						bonus += (karma * 1.2) - karma;
						isFirst = false;
					}
					Calendar actual = Calendar.getInstance();
					actual.add(Calendar.MINUTE, -(5 * 30));
					Calendar submited = link.getSubmited();
					// Bonus for being recent
					if (submited.after(actual)) {
						for (float timeBonus = 1.2F; timeBonus <= 2.0; timeBonus += 0.2F) {
							actual.add(Calendar.MINUTE, 30);
							if (submited.before(actual)) {
								bonus += (karma * timeBonus) - karma;
								break;
							}
						}
					}
					double upvotes = linkvoteDao.getNumberOfUpvotes(linkId);
					double votes = linkvoteDao.getNumberOfVotes(linkId);
					// Bonus for number of votes
					if (votes > 100) {
						bonus += (karma * 1.1F) - karma;
					}
					// Bonus for Upvote percentage
					if ((upvotes / votes) >= 0.75) {
						bonus += (karma * 1.2F) - karma;
					}
					// Bonus for non anonymous vote percentage
					if ((linkvoteDao.getNumberOfNonAnonymousUpvotes(linkId) / upvotes) >= 0.75) {
						bonus += (karma * 1.3F) - karma;
					}
					// No upvotes = Discard
					if (upvotes == 1 && votes > 50) {
						discardLink(link.getLinkId());
						linkBonus.add(bonus);
						continue;
					}
					// -150 karma = Discard
					if (karma < -150) {
						discardLink(link.getLinkId());
						linkBonus.add(bonus);
						continue;
					}
					// <70% Downvotes = Discard
					if (upvotes / votes <= 0.3 && votes > 50) {
						discardLink(link.getLinkId());
						linkBonus.add(bonus);
						continue;
					}
					bonus += (karma * catCoef.get(categories.indexOf(link
							.getCategoryId().getParent()))) - karma;
				}
				linkBonus.add(bonus);
			}
			Links candidate = new Links();
			int index = 0;
			float karmaCanditate = 0F;

			for (Iterator<Links> iterator = linksList.iterator(); iterator
					.hasNext();) {
				Links link = (Links) iterator.next();
				float bonus = linkBonus.get(index);
				if (link.getKarma() + bonus > karmaCanditate
						&& link.getStatus() == LinkStatus.QUEUED
						&& linkvoteDao.getNumberOfVotes(link.getLinkId()) > 10) {
					candidate = link;
					karmaCanditate = link.getKarma() + bonus;
				}
				index++;
			}
			if (candidate.getCategoryId() != null) {
				publishLink(candidate.getLinkId());
			}
		}

		// DISCARD
		List<Links> discardedLinks = linksDao.getQueuedLinksOlderThanAWeek();
		for (Iterator<Links> iterator = discardedLinks.iterator(); iterator
				.hasNext();) {
			Links link = (Links) iterator.next();
			discardLink(link.getLinkId());
		}
	}

	@Override
	@Transactional
	@Scheduled(cron = "0 10 5 * * ?")
	public void userWorker() throws InstanceNotFoundException {
		List<User> users = userDao.listUsersLoggedToday();

		for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			long userId = user.getUserId();
			float newKarma = 0;
			float temp = 0;

			// BONUS
			temp = linksDao.getNumberOfPublishedLinksTodayByUser(userId) * 4;

			if (temp > 10) {
				temp = 10;
			}

			newKarma += temp;
			temp = reportDao.getNumberOfReportsByUserIdToday(userId);

			if (temp > 2) {
				temp = 2;
			}

			newKarma += temp;
			temp = linkvoteDao.getNumberOfUpvotesTodayByUser(userId) * 0.6F;

			if (temp > 5) {
				temp = 5;
			}

			newKarma += temp;
			temp = 0;

			// Downvoting a fast discarded link
			List<LinkVote> votes = linkvoteDao.getDownvotesTodayByUser(userId);
			for (Iterator<LinkVote> iterator2 = votes.iterator(); iterator2
					.hasNext();) {
				LinkVote linkVote = (LinkVote) iterator2.next();
				Calendar submited = linkVote.getLink().getSubmited();
				submited.add(Calendar.MINUTE, +30);
				if (linkVote.getLink().getStatus() == LinkStatus.DISCARD
						&& submited.after(linkVote.getSubmited())
						&& submited.after(linkVote.getLink().getPublished())) {
					temp += 0.4F;
				}
			}

			if (temp > 5) {
				temp = 5;
			}

			newKarma += temp;
			temp = 0;

			temp = commentvoteDao.getCommentUpvotesTodayToUserId(userId) * 0.6F;

			if (temp > 5) {
				temp = 5;
			}

			newKarma += temp;
			temp = 0;

			long submissionsToday = 0;

			submissionsToday += commentDao
					.getNumberOfCommentsByUserIdToday(userId);
			submissionsToday += commentvoteDao
					.getCommentVotesTodayByUserId(userId);
			submissionsToday += linksDao.getNumberOfLinksTodayByUser(userId);
			submissionsToday += linkvoteDao.getNumberOfVotesTodayByUser(userId);

			temp = submissionsToday * 0.2F;

			if (temp > 5) {
				temp = 5;
			}

			newKarma += temp;
			temp = 0;

			// Less active users don't get punished by karma system
			if (newKarma < 9) {
				newKarma = 9;
			}

			// MALUS

			temp = (commentvoteDao.getCommentVotesTodayToUserId(userId) - commentvoteDao
					.getCommentUpvotesTodayToUserId(userId)) * 0.2F;

			if (temp > 5) {
				temp = 5;
			}

			newKarma -= temp;
			temp = 0;

			// Upvoting a fast discarded link
			votes = linkvoteDao.getUpvotesTodayByUser(userId);
			for (Iterator<LinkVote> iterator2 = votes.iterator(); iterator2
					.hasNext();) {
				LinkVote linkVote = (LinkVote) iterator2.next();
				Calendar submited = linkVote.getLink().getSubmited();
				submited.add(Calendar.MINUTE, +30);
				if (linkVote.getLink().getStatus() == LinkStatus.DISCARD
						&& submited.after(linkVote.getSubmited())
						&& submited.after(linkVote.getLink().getPublished())) {
					temp += 0.2F;
				}
			}

			if (temp > 5) {
				temp = 5;
			}

			newKarma -= temp;

			if (newKarma > 25) {
				newKarma = 25;
			}

			if (newKarma < 0) {
				newKarma = 0;
			}

			// user.setKarma((0.7F * user.getKarma()) + (0.3F * newKarma));
			// updateUser(user);

		}

		List<Links> publishedToday = linksDao.getPublishedLinksToday();

		// Adding Users not logged today but with sendend links that became
		// published
		for (Iterator<Links> iterator = publishedToday.iterator(); iterator
				.hasNext();) {
			Links link = (Links) iterator.next();
			if (!users.contains(link.getLinkAuthor())) {
				if (link.getLinkAuthor().getKarma() <= 23) {
					// link.getLinkAuthor().setKarma(link.getLinkAuthor().getKarma()+2);
					// updateUser(link.getLinkAuthor());
				}
			}
		}
	}
}