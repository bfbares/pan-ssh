package com.borjabares.pan_ssh.model.user;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.borjabares.modelutil.dao.GenericDaoHibernate;
import com.borjabares.pan_ssh.util.GlobalNames.Level;

@Repository("UserDao")
public class UserDaoHibernate extends GenericDaoHibernate<User, Long> implements
		UserDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<User> listUsers(int startIndex, int count) {
		return getSession()
				.createQuery("SELECT u FROM User u ORDER BY u.login")
				.setFirstResult(startIndex).setMaxResults(count).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> listUsersByCriteria(int startIndex, int count,
			String criteria) {
		return getSession()
				.createQuery("SELECT u FROM User u ORDER BY u." + criteria)
				.setFirstResult(startIndex).setMaxResults(count).list();

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> listUsersOfLevel(int startIndex, int count, Level level) {
		return getSession()
				.createQuery(
						"SELECT u FROM User u WHERE u.level = :level ORDER BY u.login")
				.setParameter("level", level).setFirstResult(startIndex)
				.setMaxResults(count).list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> listUsersLoggedToday() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, -1);
		return getSession()
				.createQuery(
						"SELECT u FROM User u WHERE u.lastlogin > :date")
				.setCalendarDate("date", date).list();
	}

	@Override
	public int getNumberOfUsers() {
		long numberOfUsers = (Long) getSession().createQuery(
				"SELECT COUNT(u) FROM User u").uniqueResult();
		return (int) numberOfUsers;
	}

	@Override
	public long login(String login, String password) {
		User user = (User) getSession()
				.createQuery("SELECT u FROM User u WHERE u.login = :login")
				.setParameter("login", login).uniqueResult();

		if (user != null && password.equals(user.getPassword())) {
			return user.getUserId();
		}

		return 0;
	}

	@Override
	public boolean userExists(String login) {
		User user = (User) getSession()
				.createQuery("SELECT u FROM User u WHERE u.login = :login")
				.setParameter("login", login).uniqueResult();

		if (user != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean emailExists(String email) {
		User user = (User) getSession()
				.createQuery("SELECT u FROM User u WHERE u.email = :email")
				.setParameter("email", email).uniqueResult();

		if (user != null) {
			return true;
		}

		return false;
	}

	public User findUserByLogin(String login) {
		return (User) getSession()
				.createQuery("SELECT u FROM User u WHERE u.login = :login")
				.setParameter("login", login).uniqueResult();
	}

}
