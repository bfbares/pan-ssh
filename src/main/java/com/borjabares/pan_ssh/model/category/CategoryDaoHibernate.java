package com.borjabares.pan_ssh.model.category;

import org.springframework.stereotype.Repository;

import com.borjabares.modelutil.dao.GenericDaoHibernate;

@Repository("CategoryDao")
public class CategoryDaoHibernate extends GenericDaoHibernate<Category, Long>
		implements CategoryDao {
	
	@Override
	public boolean existsCategory(String name) {
		Category category = (Category) getSession()
				.createQuery("SELECT c FROM Category c WHERE c.name = :name")
				.setParameter("name", name).uniqueResult();

		if (category != null) {
			return true;
		}

		return false;
	}

}
