<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>



	<c:forEach items="${posList}" var="draw1" varStatus="status1">
			<div class="mainMenuIcon">
				<div style="padding-left: 30%; padding-right: 10%">
				<a href="frmWebPOSMainMenu.html?saddr=1&strPOSCode=${draw1.strPosCode}"><img id="Desktop" src="../${pageContext.request.contextPath}/resources/images/imgPOSSelection1.png" title="${draw1.strPosName}" ></a>
				</div>
				<div style=" text-align: center;font-size:0.7em;font-weight:bold;">${draw1.strPosName}</div>
			</div>
		</c:forEach>



</body>
</html>