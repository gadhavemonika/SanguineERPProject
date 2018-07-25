<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">




<%-- <link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/jquery-ui.css"/>" /> --%>
<%-- <link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/design.css"/>" /> --%>

<%-- <script type="text/javascript" src="<spring:url value="/resources/js/jquery.timepicker.min.js"/>"></script> --%>
<%-- <link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/jquery.timepicker.css"/>" /> --%>

<title>Search Form</title>
</head>
<body>
<%-- <table  style="border: 1px solid black ;border-spacing:0px;min-height:100%;width: 100%;height: 100%; border-collapse: collapse; cellspecing:0px; cellspadding:0px;">
<tr>
<td height="100%" style="border: 1px;background-color: #D8EDFF;min-height:100%;position:inherit;">
<tiles:insertAttribute name="body"></tiles:insertAttribute>
</td>
</tr>
</table> --%>

<div style="  position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    background-color: #D8EDFF;">
    <tiles:insertAttribute name="body"></tiles:insertAttribute>
    </div>
</body>
</html>