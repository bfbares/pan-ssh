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

	@Override
	public Category findCategoryByName(String name) {
		return (Category) getSession()
				.createQuery("SELECT c FROM Category c WHERE c.name = :name")
				.setParameter("name", name).uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Category> listParentCategories() {
		return getSession()
				.createQuery(
						"SELECT c FROM Category c WHERE c.parent = null ORDER BY c.name")
				.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Category> listNonParentCategories() {
		return getSession()
				.createQuery(
						"SELECT p FROM Category c INNER JOIN c.parent p ORDER BY p.name")
				.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Category> listAllCategories() {
		return getSession().createQuery(
				"SELECT c FROM Category c ORDER BY c.categoryId").list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Category> listCategoryChildrens(long parentId) {
		return getSession()
				.createQuery(
						"SELECT c FROM Category c"
								+ " WHERE c.parent.categoryId = :parentId ORDER BY c.name")
				.setParameter("parentId", parentId).list();
	}
}
