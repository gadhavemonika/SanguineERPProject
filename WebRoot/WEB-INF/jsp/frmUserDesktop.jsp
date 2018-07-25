<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">

</script>
</head>
<body>
<div id="formHeading">
		<label>Desktop</label>
	</div>
<form action="saveUserDesktop.html" method="post" name="userDesktop">
<br />
		
		<table class="masterTable" >
		<c:forEach items="${listDesktop}" var="desktop" varStatus="status">
			<tr><td><input type="hidden"
				name="listUserDesktop[${status.index}].strformname"
				value="${desktop.strformname}" />
			<input type="checkbox"
				name="listUserDesktop[${status.index}].desktopForm"
				<c:if test="${desktop.desktopForm == true}">checked="checked"</c:if>
				value=true> &nbsp;&nbsp; ${desktop.formDesc}
				</td></tr>
	
		</c:forEach>
		</table>
		<br>
		<p align="center">
			<input type="submit" value="Submit" class="form_button" />
		</p>
		<br>
		<br>
</form>
</body>
</html>