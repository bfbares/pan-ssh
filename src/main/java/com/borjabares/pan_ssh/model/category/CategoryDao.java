package com.borjabares.pan_ssh.model.category;

import java.util.List;

import com.borjabares.modelutil.dao.GenericDao;

public interface CategoryDao extends GenericDao<Category, Long> {

	public boolean existsCategory(String name);
	
	public List<Category> listParentCategories();
	
	public List<Category> listNonParentCategories();
	
	public List<Category> listAllCategories();
	
	public List<Category> listCategoryChildrens(long parentId);
	
}
