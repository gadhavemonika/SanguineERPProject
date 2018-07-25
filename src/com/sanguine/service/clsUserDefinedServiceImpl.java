package com.sanguine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsUserDefinedReportModel;

@Service("userDefinedService")
public class clsUserDefinedServiceImpl implements clsUserDefinedService {
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Map<String, String> funGetTableNames(String queryType) {
		Map<String, String> table_names = new HashMap<String, String>();
		if (queryType.equals("table")) {
			Map<String, ClassMetadata> all_class_map = sessionFactory.getAllClassMetadata();

			for (Map.Entry<String, ClassMetadata> entry : all_class_map.entrySet()) {
				if (entry.getValue() instanceof SingleTableEntityPersister) {
					String className = entry.getKey().substring(entry.getKey().lastIndexOf(".") + 1);
					table_names.put(className, ((SingleTableEntityPersister) entry.getValue()).getTableName());
				}
			}
		} else {
			Map<String, ClassMetadata> all_class_map = sessionFactory.getAllClassMetadata();
			for (Map.Entry<String, ClassMetadata> entry : all_class_map.entrySet()) {
				if (entry.getValue() instanceof SingleTableEntityPersister) {
					String className = entry.getKey().substring(entry.getKey().lastIndexOf(".") + 1);
					table_names.put(((SingleTableEntityPersister) entry.getValue()).getTableName(), className);
				}
			}
		}
		System.out.println(table_names);
		return table_names;
	}

	@Transactional
	public Map<String, String> funGetColumnNames(String entityName) {
		Map<String, String> column_names = null;
		Map<String, ClassMetadata> all_class_map = sessionFactory.getAllClassMetadata();
		ClassMetadata classMetadata = all_class_map.get("com.sanguine.model." + entityName);
		if (classMetadata instanceof SingleTableEntityPersister) {
			SingleTableEntityPersister metaData = (SingleTableEntityPersister) classMetadata;
			column_names = new HashMap<String, String>();
			for (String propertyName : metaData.getPropertyNames()) {
				column_names.put(propertyName, metaData.getPropertyColumnNames(propertyName)[0]);
			}
			System.out.println(column_names);
		}
		return column_names;
	}

	@Transactional
	public Map<String, String> funGetPropertyNames(String entityName) {
		Map<String, String> column_names = null;
		Map<String, ClassMetadata> all_class_map = sessionFactory.getAllClassMetadata();
		ClassMetadata classMetadata = all_class_map.get("com.sanguine.model." + entityName);
		if (classMetadata instanceof SingleTableEntityPersister) {
			SingleTableEntityPersister metaData = (SingleTableEntityPersister) classMetadata;
			column_names = new HashMap<String, String>();
			for (String propertyName : metaData.getPropertyNames()) {
				column_names.put(metaData.getPropertyColumnNames(propertyName)[0], propertyName);
			}
			System.out.println(column_names);
		}
		return column_names;
	}

	@Override
	@Transactional
	public boolean funCheckQuery(String query) {
		try {
			// List
			// listLastNo=sessionFactory.getCurrentSession().createSQLQuery(query).list();
			Query q = sessionFactory.getCurrentSession().createSQLQuery(query);
			List listLastNo = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public void funSaveUpdateUR(clsUserDefinedReportModel objURModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objURModel);
	}
}
