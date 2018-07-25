<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Web Stocks</title>
     <script type="text/javascript">
    $(document).ready(function(){
    	var s=getContextPath()+"/resources/images/";
    	
    	
    	$("#Desktop").attr('src', s+'Desktop.png');
    	$("#Desktop").attr('title','Desktop');
	});
    
    </script> 

   
  </head>
  
	<body>
	
	<br>
	<div class="mainMenuIcon" >
	<div style="padding-left: 30%; padding-right: 10%">
	<a href="frmUserDesktop.html"><img id="Desktop" src="" ></a>
	</div>
	<div style="text-align: center;font-size:0.7em;font-weight:bold;">
	Desktop
	</div>
	</div>

		<c:forEach items="${desktop}" var="draw1" varStatus="status1">
			<div class="mainMenuIcon">
				<div style="padding-left: 30%; padding-right: 10%">
				<a href="${draw1.strRequestMapping}?saddr=1"><img id="Desktop" src="../${pageContext.request.contextPath}/resources/images/${draw1.strImgName}" title="${draw1.strFormDesc}" ></a>
				</div>
				<div style=" text-align: center;font-size:0.7em;font-weight:bold;">${draw1.strFormDesc}</div>
			</div>
		</c:forEach>
	</body>
</html>