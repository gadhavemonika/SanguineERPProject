<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon" sizes="16x16">
    
 	<!--<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.0/jquery-ui.min.js"></script>-->
	<script type="text/javascript" src="<spring:url value="/resources/js/jQuery.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>	
	<script type="text/javascript" src="<spring:url value="/resources/js/validations.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/TreeMenu.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/main.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/jquery.fancytree.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/jquery.numeric.js"/>"></script>
	
 	<%-- <c:url value="/style.css" var="cssURL" /> --%>

 	
 	<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/design.css"/>" />
    <link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/tree.css"/>" /> 
 	<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/jquery-ui.css"/>" />
 	<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/main.css"/>" />
<%--  	<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/ui.fancytree.css"/>" /> --%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body bgcolor="white">
<div>
		<tiles:insertAttribute name="body"></tiles:insertAttribute>
</div>
</body>
</html>