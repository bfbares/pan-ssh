package com.borjabares.pan_ssh.model.category;

import java.util.List;

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

	@SuppressWarnings("unchecked")
	public List<Category> listParentCategories() {
		return getSession().createQuery(
				"SELECT c FROM Category c WHERE c.parent = 0 ORDER BY c.name")
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<Category> listNonParentCategories() {
		return getSession().createQuery(
				"SELECT c FROM Category c WHERE c.parent != 0 ORDER BY c.name")
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<Category> listAllCategories() {
		return getSession().createQuery(
				"SELECT c FROM Category c ORDER BY c.categoryId").list();
	}

	@SuppressWarnings("unchecked")
	public List<Category> listCategoryChildrens(long parentId) {
		return getSession()
				.createQuery(
						"SELECT c FROM Category c WHERE parent = :parentId")
				.setParameter("parentId", parentId).list();
	}
}
