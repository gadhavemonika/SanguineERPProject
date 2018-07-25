<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
 
  <script type="text/javascript">
 
  $(document).ready(function()
			{
	var message='';
	<%if (session.getAttribute("posLicStatus") != null) {
		            if(session.getAttribute("posLicMsg") != null){%>
		            message='<%=session.getAttribute("posLicMsg").toString()%>';
		            <%
		            session.removeAttribute("posLicMsg");
		            }
					session.removeAttribute("posLicStatus");
					%>	
		alert("\n"+message);
	<%
	}%>

});
  
  

</script>
  
  

    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Web Stocks</title>
  </head>

<body>
 <div style="float: left;padding-left: 40px;width: 500px;height: 400px;">
		<img alt=""src="../${pageContext.request.contextPath}/resources/images/Sanguine_ERP_Logo_1.jpg" height="100px" width="466px">
	<div style="float: left;padding-left: 40px;">
		<img alt=""
				src="../${pageContext.request.contextPath}/resources/images/company_Logo.png"
				style="display: block; padding-left: 1px;width : 339px;high : 250px;">
				
	</div>
	</div> 
	
<div style="margin-left: auto;
    margin-right: auto;
    width: 100%;
    height:100%;
    padding-top: 60px;">
	<c:forEach items="${moduleMap}" var="draw1" varStatus="status1">
			<div class="moduleIcon">
				<div>
				<p align="center"><a href="redirectToModule.html?moduleName=${draw1.key}"><img src="../${pageContext.request.contextPath}/resources/images/${draw1.value}" title="${draw1.key}"> </a> </p>
				</div>
				
			</div>
		</c:forEach>
</div>
</body>
</html>