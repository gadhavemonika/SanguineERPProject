
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Web Stocks</title>
 	<!--<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.0/jquery-ui.min.js"></script>-->
	<script type="text/javascript" src="<spring:url value="/jQuery.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/jquery-ui.min.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/validations.js"/>"></script>
 	<c:url value="/style.css" var="cssURL" />
 	<link rel="stylesheet" type="text/css" media="screen" href="${cssURL}" />
	<script type="text/javascript">
	
   	function getContextPath() 
   	{
	  	return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	</script>
	
  	</head>
		<body class="headerBody">
			<h2> WEB STOCKS </h2>
 			<br><c:out value="${username}"/>
		</body>
</html>