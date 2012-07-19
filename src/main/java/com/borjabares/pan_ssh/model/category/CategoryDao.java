package com.borjabares.pan_ssh.model.category;

import com.borjabares.modelutil.dao.GenericDao;

public interface CategoryDao extends GenericDao<Category, Long> {

	public boolean existsCategory(String name);
	
}
