
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Web-POS Module Selection</title>
</head>
<body style="margin-left: 18%;margin-top: 20%;">

				<div >
				 <a href="frmWebPOSSelectionMaster.html" ><img  src="../${pageContext.request.contextPath}/resources/images/imgMasters.png" title="Master" ></a> &nbsp;&nbsp;
					&nbsp;&nbsp;
					
				<a href="frmWebPOSSelectionTransection.html" ><img  src="../${pageContext.request.contextPath}/resources/images/imgTransactions.png" title="Transactions" ></a> 
					&nbsp;&nbsp;	
					
				<a href="frmWebPOSSelectionReport.html" ><img  src="../${pageContext.request.contextPath}/resources/images/imgReports.png" title="Report"  ></a>
					
				</div>


</body>
</html>