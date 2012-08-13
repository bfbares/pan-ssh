package com.borjabares.pan_ssh.model.panservice;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	private LinkVoteDao voteDao;

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

	@Transactional(readOnly = true)
	private FullLink fromLinkToFullLink(Links link, User user, String ip) {
		
		if (user == null) {
			return new FullLink(link,
					voteDao.getNumberOfVotes(link.getLinkId()),
					voteDao.ipVoted(ip, link.getLinkId()), null);
		} else {
			return new FullLink(link,
					voteDao.getNumberOfVotes(link.getLinkId()),
					voteDao.userVotedLink(user.getUserId(), link.getLinkId()),
					user);
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

		return new ObjectBlock<FullLink>(fromLinkToFullLink(links, user, ip), startIndex, count,
				linksDao.getNumberOfLinksByStatus(status));
	}

	@Override
	@Transactional(readOnly = true)
	public ObjectBlock<FullLink> listFullLinksByCategoryAndStatus(
			int startIndex, int count, LinkStatus status, Category category, User user, String ip) {
		List<Links> links = linksDao.getLinksByCategoryAndStatus(startIndex, count, status, category);

		return new ObjectBlock<FullLink>(fromLinkToFullLink(links, user, ip), startIndex, count,
				linksDao.getNumberOfLinksByCategoryAndStatus(status, category));
	}
	
	@Override
	@Transactional(readOnly = true)
	public ObjectBlock<FullLink> listFullLinksByParentCategoryAndStatus(
			int startIndex, int count, LinkStatus status, Category category, User user, String ip) {
		List<Links> links = linksDao.getLinksByParentCategoryAndStatus(startIndex, count, status, category);

		return new ObjectBlock<FullLink>(fromLinkToFullLink(links, user, ip), startIndex, count,
				linksDao.getNumberOfLinksByParentCategoryAndStatus(status, category));
	}

	@Override
	@Transactional
	public Category createCategory(Category category)
			throws ParentCategoryException, DuplicatedCategoryNameException {
		if (categoryDao.existsCategory(category.getName())) {
			throw new DuplicatedCategoryNameException(category.getName());
		}
		if (category.getParent() != null) {
			try {
				Category parent = categoryDao.find(category.getParent().getCategoryId());
				if (parent.getParent() != null) {
					throw new ParentCategoryException(category.getParent().getCategoryId());
				}
			} catch (InstanceNotFoundException e) {
				throw new ParentCategoryException(category.getParent().getCategoryId());
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
	throws InstanceNotFoundException{
		Category category = categoryDao.findCategoryByName(name);
		
		if (category==null){
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

	@Override
	@Transactional
	public LinkVote createVote(LinkVote vote) throws IpVotedException,
			UserVotedException {
		float karma = 8;
		if (vote.getUser() == null) {
			if (voteDao.ipVoted(vote.getIp(), vote.getLink().getLinkId())) {
				throw new IpVotedException(vote.getIp(), vote.getLink()
						.getLinkId());
			}
		} else {
			if (voteDao.userVotedLink(vote.getUser().getUserId(), vote
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
		voteDao.save(vote);

		return vote;
	}

	@Override
	@Transactional(readOnly = true)
	public LinkVote findVote(long voteId) throws InstanceNotFoundException {
		return voteDao.find(voteId);
	}

}
