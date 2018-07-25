package com.sanguine.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.model.clsUserDesktopModel;
import com.sanguine.util.clsTreeRootNodeItemUtil;
import com.sanguine.util.clsUserDesktopUtil;

@Repository("clsTreeMenuDao")
public class clsTreeMenuDaoImpl implements clsTreeMenuDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private HttpServletRequest req;

	@SuppressWarnings("unchecked")
	@Override
	public List<clsTreeMasterModel> funGetMenuForm() {

		String moduleCode = "1";
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			moduleCode = "1";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			moduleCode = "2";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			moduleCode = "3";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			moduleCode = "4";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBook")) {
			moduleCode = "5";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			moduleCode = "6";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("7-WebPOS")) {
			moduleCode = "7";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			moduleCode = "8";
		}
		String sql = "select strFormDesc,strType,strRequestMapping from clsTreeMasterModel where strModule='" + moduleCode + "' and strFormAccessYN='Y' ";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		@SuppressWarnings("rawtypes")
		List results = query.list();
		return (List<clsTreeMasterModel>) results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsTreeRootNodeItemUtil> getRootNodeItems(String userCode, String clientCode, String rootNode) {

		String moduleCode = "1";
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			moduleCode = "1";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			moduleCode = "2";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			moduleCode = "3";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			moduleCode = "4";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR")) {
			moduleCode = "5";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			moduleCode = "6";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("7-WebPOS")) {
			moduleCode = "7";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			moduleCode = "8";
		}
		String sql = "select * from (select a.strFormDesc,a.strRequestMapping,a.strRootNode,a.strType,a.intFormNo " + "from tbltreemast a ,tbluserdtl b where a.strFormName=b.strFormName and b.strUserCode='" + userCode + "' and b.strClientCode='" + clientCode + "' and a.strRootNode='" + rootNode + "' and a.strModule='" + moduleCode + "' and a.strFormAccessYN='Y'  UNION ALL select a.strFormDesc,"
				+ "a.strRequestMapping,a.strRootNode,a.strType,a.intFormNo from tbltreemast a " + "where a.strtype = 'O' and a.strRootNode='" + rootNode + "' and a.strModule='" + moduleCode + "' and a.strFormAccessYN='Y') as a ORDER BY a.intFormNo";
		List<clsTreeRootNodeItemUtil> objTreeNode = (List<clsTreeRootNodeItemUtil>) sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return objTreeNode;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsTreeRootNodeItemUtil> getRootNodeItems(String rootNode) {

		String moduleCode = "1";
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			moduleCode = "1";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			moduleCode = "2";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			moduleCode = "3";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			moduleCode = "4";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR")) {
			moduleCode = "5";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			moduleCode = "6";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("7-WebPOS")) {
			moduleCode = "7";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			moduleCode = "8";
		}
		String sql = "select a.strFormDesc,a.strRequestMapping,a.strRootNode,a.strType from tbltreemast a where a.strRootNode='" + rootNode + "' and a.strModule='" + moduleCode + "' and a.strFormAccessYN='Y'  order by a.intFormNo";
		List<clsTreeRootNodeItemUtil> objTreeNode = (List<clsTreeRootNodeItemUtil>) sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return objTreeNode;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsUserDesktopUtil> funGetForms() {

		String moduleCode = "1";
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			moduleCode = "1";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			moduleCode = "2";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			moduleCode = "3";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			moduleCode = "4";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR")) {
			moduleCode = "5";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			moduleCode = "6";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("7-WebPOS")) {
			moduleCode = "7";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			moduleCode = "8";
		}
		String sql = "select a.strFormName,a.strFormDesc,a.strImgName,a.strRequestMapping " + "from tbltreemast a where a.strModule='" + moduleCode + "' and a.strFormAccessYN='Y' and a.strRequestMapping <>'' ";

		List<clsUserDesktopUtil> objDesktopItem = sessionFactory.getCurrentSession().createSQLQuery(sql).list();

		return objDesktopItem;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsUserDesktopUtil> funGetForms(String userCode, String clientCode) {

		String moduleCode = "1";
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			moduleCode = "1";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			moduleCode = "2";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			moduleCode = "3";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			moduleCode = "4";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR")) {
			moduleCode = "5";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			moduleCode = "6";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("7-WebPOS")) {
			moduleCode = "7";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			moduleCode = "8";
		}
		String sql = "select a.strFormName,b.strFormDesc,b.strImgName,b.strRequestMapping " + "from tbluserdtl a,tbltreemast b where a.strFormName=b.strFormName and b.strModule='" + moduleCode + "' " + "and a.strUserCode='" + userCode + "' and a.strClientCode='" + clientCode + "' and b.strFormAccessYN='Y' ";

		List<clsUserDesktopUtil> objDesktopItem = sessionFactory.getCurrentSession().createSQLQuery(sql).list();

		return objDesktopItem;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsUserDesktopModel> getUserDesktopForm(String userCode) {

		String moduleCode = "1";
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			moduleCode = "1";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			moduleCode = "2";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			moduleCode = "3";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			moduleCode = "4";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR")) {
			moduleCode = "5";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			moduleCode = "6";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("7-WebPOS")) {
			moduleCode = "7";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			moduleCode = "8";
		}
		String sql = "select b.strusercode,a.strformname from tbltreemast a,tbluserdesktop b " + "where strUserCode='" + userCode + "' and b.strformname=a.strformname and a.strModule='" + moduleCode + "' and a.strFormAccessYN='Y'";
		List<clsUserDesktopModel> objDesktopItem = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return objDesktopItem;
	}

	@Override
	public void funDeleteDesktopForm(String userCode) {
		String query = "delete from tbluserdesktop where strusercode = '" + userCode + "'";
		sessionFactory.getCurrentSession().createSQLQuery(query).executeUpdate();

	}

	@Override
	public void funInsertDesktopForm(String strformname, String userCode) {
		String query = "insert into tbluserdesktop (strusercode,strformname) values('" + userCode + "','" + strformname + "')";
		sessionFactory.getCurrentSession().createSQLQuery(query).executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsUserDesktopUtil> funGetDesktopForms(String userCode) {

		String moduleCode = "1";
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			moduleCode = "1";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			moduleCode = "2";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			moduleCode = "3";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			moduleCode = "4";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR")) {
			moduleCode = "5";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			moduleCode = "6";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("7-WebPOS")) {
			moduleCode = "7";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			moduleCode = "8";
		}
		String sql = "select a.strFormName,a.strFormDesc,a.strImgName,a.strRequestMapping " + "from tbltreemast a,tbluserdesktop b where a.strFormName=b.strformname and a.strModule='" + moduleCode + "' and b.strusercode='" + userCode + "'  and a.strFormAccessYN='Y' ";
		List<clsUserDesktopUtil> objDesktopItem = sessionFactory.getCurrentSession().createSQLQuery(sql).list();

		return objDesktopItem;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsUserDesktopUtil> funGetDesktopForms(String userCode, String clientCode) {

		String moduleCode = "1";
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			moduleCode = "1";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			moduleCode = "2";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			moduleCode = "3";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			moduleCode = "4";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR")) {
			moduleCode = "5";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			moduleCode = "6";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("7-WebPOS")) {
			moduleCode = "7";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			moduleCode = "8";
		}
		String sql = "select a.strFormName,b.strFormDesc,b.strImgName,b.strRequestMapping " + "from tbluserdtl a,tbltreemast b,tbluserdesktop c where a.strFormName=b.strFormName and b.strModule='" + moduleCode + "' and a.strUserCode=c.strusercode and b.strFormName=c.strformname and a.strUserCode='" + userCode + "' " + "and a.strClientCode='" + clientCode + "'  and c.strusercode='" + userCode
				+ "' and b.strFormAccessYN='Y' ";

		List<clsUserDesktopUtil> objDesktopItem = sessionFactory.getCurrentSession().createSQLQuery(sql).list();

		return objDesktopItem;
	}

}
