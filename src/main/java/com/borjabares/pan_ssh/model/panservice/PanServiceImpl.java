package com.borjabares.pan_ssh.model.panservice;

import java.security.NoSuchAlgorithmException;
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
	public ObjectBlock<User> listAllUsers(int startIndex, int count) {
		return new ObjectBlock<User>(userDao.listUsers(startIndex, count),
				startIndex, count, userDao.getNumberOfUsers());
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
	public Links createLink(long userId, Links link)
			throws InstanceNotFoundException, DuplicatedLinkException {
		if (linksDao.existsUrl(link.getUrl())) {
			throw new DuplicatedLinkException(link.getUrl());
		}
		User user = userDao.find(userId);
		link.setLinkAuthor(user);
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
	@Transactional
	public Category createCategory(Category category)
			throws ParentCategoryException, DuplicatedCategoryNameException {
		if (categoryDao.existsCategory(category.getName())) {
			throw new DuplicatedCategoryNameException(category.getName());
		}
		if (category.getParent() != 0) {
			try {
				Category parent = categoryDao.find(category.getParent());
				if (parent.getParent() != 0) {
					throw new ParentCategoryException(category.getParent());
				}
			} catch (InstanceNotFoundException e) {
				throw new ParentCategoryException(category.getParent());
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
	public List<Category> listParentCategories(){
		return categoryDao.listParentCategories();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Category> listAllCategories(){
		return categoryDao.listAllCategories();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Category> listCategoryChildrens(long parentId){
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
		}
		voteDao.save(vote);

		return vote;
	}

	@Override
	@Transactional(readOnly = true)
	public LinkVote findVote(long voteId) throws InstanceNotFoundException {
		return voteDao.find(voteId);
	}

}
