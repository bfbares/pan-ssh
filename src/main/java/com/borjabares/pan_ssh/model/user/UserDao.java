package com.borjabares.pan_ssh.model.user;

import java.util.List;

import com.borjabares.modelutil.dao.GenericDao;
import com.borjabares.pan_ssh.util.GlobalNames.Level;

public interface UserDao extends GenericDao<User, Long> {

	public List<User> listUsers(int startIndex, int count);

	public List<User> listUsersByCriteria(int startIndex, int count,
			String criteria);

	public List<User> listUsersOfLevel(int startIndex, int count, Level level);
	
	public List<User> listUsersLoggedToday();

	public int getNumberOfUsers();

	public long login(String login, String password);

	public boolean userExists(String login);

	public boolean emailExists(String email);

	public User findUserByLogin(String login);

}
