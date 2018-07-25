<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jQuery.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>
	
<script type="text/javascript" src="<spring:url value="/resources/js/pagination.js"/>"></script>
        <!-- Load data to paginate -->
<link rel="stylesheet" href="<spring:url value="/resources/css/pagination.css"/>" />

<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:url value="/resources/css/design.css"/>" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">

var members,propertyName,locationName;

	//Set header Master Or Transaction
	$(document).ready(function () {
		strHeadingType='<%=request.getParameter("strHeadingType") %>'
		$("#strHeader").text(strHeadingType);
		funLoadProperty('All');
		
	// src ="../${pageContext.request.contextPath}/resources/WEB_INF/jsp/HelpForms/frmWebStockHelpMaterialIssueSlip.jsp";
	window.open('frmWebStockHelpMaterialIssueSlip.html',"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		//var returnVal=window.showModalDialog("frmWebStockHelpMaterialIssueSlip.html","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:350px;dialogTop:100px");
	
	});
	
	
	function getContextPath() 
	{
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	



	 
	
	
</script>
<style type="text/css">

</style>

<title>Insert title here</title>
<%-- <tab:tabConfig /> --%>
</head>
<body onload="funOnLoad()">

	<div id="formHeading">
		<label id="strHeader"></label>
	</div>
	<div>
		<form action="frmSaveData.html" method="GET">
			<br />
		
			<dl id="Searchresult"></dl>
			
			
			
			
		<div id="Pagination" class="pagination"></div>
			
			<br>
			<br>
			
		</form>
	</div>
</body>
</html>