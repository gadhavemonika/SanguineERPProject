<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML>
<html>
<head>
 <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon" sizes="16x16">
    
 	<!--<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.0/jquery-ui.min.js"></script>-->
 	<script type="text/javascript" src="<spring:url value="/resources/js/jquery-3.0.0.min.js"/>"></script>
 	<script type="text/javascript" src="<spring:url value="/resources/js/jquery-3.0.0.migrate.js"/>"></script>
 		<script type="text/javascript" src="<spring:url value="/resources/js/jQKeyboard.js"/>"> </script>
<%-- 	<script type="text/javascript" src="<spring:url value="/resources/js/jQuery.js"/>"></script> --%>
	<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>	
	<script type="text/javascript" src="<spring:url value="/resources/js/validations.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/TreeMenu.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/main.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/jquery.fancytree.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/jquery.numeric.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/pagination.js"/>"></script>
	
	<script type="text/javascript" src="<spring:url value="/resources/js/jquery.timepicker.min.js"/>"></script>
	
	<script type="text/javascript" src="<spring:url value="/resources/js/slideKeyboard/jquery.ml-keyboard.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/hindiTyping.js"/>"></script>
	
	
        <!-- Load data to paginate -->
	
 	<%-- <c:url value="/style.css" var="cssURL" /> --%>
 	
 	<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/design.css"/>" />
    <link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/tree.css"/>" /> 
 	<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/jquery-ui.css"/>" />
 	<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/main.css"/>" />
<%--  	<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/ui.fancytree.css"/>" /> --%>
	<link rel="stylesheet" href="<spring:url value="/resources/css/pagination.css"/>" />
	<link rel="stylesheet" type="text/css" href="<spring:url value="/resources/css/jQKeyboard.css "/>" />
	
	<link rel="stylesheet" type="text/css" href="<spring:url value="/resources/css/slideKeyboard/jquery.ml-keyboard.css"/>" />
	<link rel="stylesheet" type="text/css" href="<spring:url value="/resources/css/slideKeyboard/jquery.ml-keyboard.css"/>" />
	
	<link rel="stylesheet" type="text/css" href="<spring:url value="/resources/css/jquery.timepicker.css"/>" />
	
	
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title"></tiles:insertAttribute>
</title>

</head>

<body bgcolor="#D8EDFF">
	<div class="row" style=" height: 28px;">
		<tiles:insertAttribute name="banner"></tiles:insertAttribute>
	</div>
	<div style="background-color: inherit; top: 28px; bottom: 0;">
		<tiles:insertAttribute name="body"></tiles:insertAttribute>
	</div>

</body>
</html>




