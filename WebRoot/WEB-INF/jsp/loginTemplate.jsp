<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<title><tiles:insertAttribute name="title" ignore="true"></tiles:insertAttribute></title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">


</style>
</head>
<body bgcolor="white">
<div style="height: 200px">
<tiles:insertAttribute name="loginheader"></tiles:insertAttribute>
</div>
<div style="background-color: inherit; top: 200px; bottom: 0;">
		<tiles:insertAttribute name="body"></tiles:insertAttribute>
</div>

<div style="height: 25px">
	<tiles:insertAttribute name="footer"></tiles:insertAttribute>
</div>

</body>
<%-- <body style="width: 99.7%;height: 100%">

<table style="border:none;border-spacing:0px;width: 100%;height: 100%; border-collapse: collapse; cellspecing:0px; cellspadding:0px;">
<tr>
<td height="25%"  style="border:1px;"><tiles:insertAttribute name="loginheader"></tiles:insertAttribute></td>
</tr>

<tr>
<td height="72%" style="border:1px;" ><tiles:insertAttribute name="body"></tiles:insertAttribute>	</td>
</tr>


<tr>
<td height="3%"><tiles:insertAttribute name="footer"></tiles:insertAttribute></td>
</tr>


</table>

</body> --%>
</html>