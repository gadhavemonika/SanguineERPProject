package com.sanguine.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsAttachDocModel;

@Repository("clsAttachDocDao")
public class clsAttachDocDaoImpl implements clsAttachDocDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void funSaveDoc(clsAttachDocModel objModel) {
		Session session = sessionFactory.getCurrentSession();
		session.save(objModel);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<clsAttachDocModel> funListDocs(String docCode, String clientCode) {
		List<clsAttachDocModel> documents = null;
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from clsAttachDocModel where strCode = :docCode and strClientCode= :clientCode");
			query.setParameter("docCode", docCode);
			query.setParameter("clientCode", clientCode);
			@SuppressWarnings("rawtypes")
			List list = query.list();
			documents = (List<clsAttachDocModel>) list;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return documents;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List funGetDoc(String code, String fileNo, String clientCode) {

		Query query = sessionFactory.getCurrentSession().createQuery("from clsAttachDocModel where strCode = :docCode and intId = :intId" + " and strClientCode= :clientCode");
		query.setParameter("docCode", code);
		query.setParameter("intId", Long.parseLong(fileNo));
		query.setParameter("clientCode", clientCode);

		List list = query.list();
		return list;
	}

	@Transactional
	public void funDeleteDoc(Long id) {
		Session session = sessionFactory.getCurrentSession();
		clsAttachDocModel document = (clsAttachDocModel) session.get(clsAttachDocModel.class, id);
		session.delete(document);
	}

	@Transactional
	public void funDeleteAttachment(String docName, String dcode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("Delete clsAttachDocModel " + "where strCode = :docCode and strActualFileName = :strActualFileName and strClientCode= :strClientCode");

		query.setParameter("docCode", dcode);
		query.setParameter("strActualFileName", docName);
		query.setParameter("strClientCode", clientCode);
		int result = query.executeUpdate();
		System.out.println("Result=" + result);

		// Query query =
		// sessionFactory.getCurrentSession().createQuery("delete clsMISDtlModel where strMISCode = :MISCode");
		// query.setParameter("MISCode", MISCode);
		// int result = query.executeUpdate();
		// System.out.println("Result="+result);

	}

}
