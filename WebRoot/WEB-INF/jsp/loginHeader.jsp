<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon" sizes="16x16">
<title>Insert title here</title>
<script type="text/javascript" src="<spring:url value="/resources/js/jQuery.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/jquery.numeric.js"/>"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/design.css"/>" />
</head>
<body >
<div style="min-width: 100%;height: 100%">

  <img  src="../${pageContext.request.contextPath}/resources/images/Sanguine_ERP_Header.jpg" width="100%" height="200px" style="background-repeat: no-repeat"> 
</div>
</body>
</html>