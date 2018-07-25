<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="True" %>
<!DOCTYPE html>
<html>
  <head>
	  <script type="text/javascript" src="<spring:url value="/resources/js/jquery-3.0.0.min.js"/>"></script>
	  <script type="text/javascript" src="<spring:url value="/resources/js/jQKeyboard.js"/>"> </script>
	  <script type="text/javascript" src="<spring:url value="/resources/js/slideKeyboard/jquery.ml-keyboard.js"/>"></script>
	
		<link rel="stylesheet" type="text/css" href="<spring:url value="/resources/css/jQKeyboard.css "/>" />
		<link rel="stylesheet" type="text/css" href="<spring:url value="/resources/css/slideKeyboard/jquery.ml-keyboard.css"/>" />
		<link rel="stylesheet" type="text/css" href="<spring:url value="/resources/css/slideKeyboard/jquery.ml-keyboard.css"/>" />	
	
  <script type="text/javascript">
  	/**
	 *  Set Focus
	**/
  $(document).ready(function(){
       $('#username').focus();
      
       var strTouchScreenMode=localStorage.getItem("lsTouchScreenMode");
       if (strTouchScreenMode == null) 
		{
		   localStorage.setItem("lsTouchScreenMode", "N");
		}   
		/*if(strTouchScreenMode=='Y')
		{
		   $('input#username').mlKeyboard({layout: 'en_US'});
		   $('input#password').mlKeyboard({layout: 'en_US'});
		   $('#username').focus();
		}*/
  });
  	
  	
  	function funKeyBoard()
  	{
  			var lsKeyBoardYN= localStorage.getItem("lsTouchScreenMode");
  			if(lsKeyBoardYN=='Y')
  			{
  				localStorage.setItem("lsTouchScreenMode", "N");
  			}
  			if(lsKeyBoardYN=='N')
  			{
  				localStorage.setItem("lsTouchScreenMode", "Y");
  			}
  	
  	}
  	
</script>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Web Stocks</title>
  </head>

<body>
	<div>
	<div style="float: left;padding-left: 40px">
		<img alt=""src="../${pageContext.request.contextPath}/resources/images/Sanguine_ERP_Logo_1.jpg" height="100px" width="466px" onclick="funKeyBoard()">
	</div>
      <div style="padding-top: 100px">
		<s:form name="login" method="POST" action="validateUser.html">
			<div style="padding-right: 12%; padding-bottom: 0px;">
				<div
					style="float: right; width: 339px; height: 250px; -webkit-border-radius: 29px; -moz-border-radius: 29px; border-radius: 29px; border: 2px solid #0595D2; background: rgba(252, 253, 255, 0.4); -webkit-box-shadow: #42B3F4 2px 2px 2px; -moz-box-shadow: #42B3F4 2px 2px 2px; box-shadow: #42B3F4 2px 2px 2px;">
					<div
						style="width: 340px; height: 62px; background-image: url(../${pageContext.request.contextPath}/resources/images/loginlogo.png);">
						<br>
						<p align="center" style="font-size: 17px; font-weight: bold;">
							Sanguine Softwares Solutions Pvt. Ltd.</p>
					</div>

					<img alt=""
						src="../${pageContext.request.contextPath}/resources/images/login.png"
						style="display: block; padding-left: 1px">
					<div style="width: 340px; height: 115px;">
						<table>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td width="100px" style="padding-left: 30px"><s:label
										path="strUserCode"
										cssStyle="font-size: 15px;font-weight: normal;">Username </s:label></td>
								<td><s:input cssClass="loginInput" name="usercode"
										path="strUserCode" autocomplete="off" id="username"
										placeholder="User name" required="true"
										cssStyle="text-transform: uppercase;" /> <s:errors
										path="strUserCode"></s:errors></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>

							<tr>
								<td style="padding-left: 30px"><s:label path="strPassword"
										cssStyle="font-size: 15px;font-weight: normal;">Password </s:label></td>
								<td><s:input type="password" placeholder="PASSWORD"
										required="true" cssClass="loginInput" name="pass"
										path="strPassword" id="password" /> <s:errors path="strPassword"></s:errors></td>
							</tr>
							<tr>
								<td></td>
							</tr>
						</table>
					</div>
					<div
						style="width: 340px; height: 36px; background-image: url(../${pageContext.request.contextPath}/resources/images/loginfoot.png);">
						<p align="right" style="padding-right: 22px; padding-top: 9px">
							<input type="submit" value="" class="loginButton" />
						</p>
					</div>
				</div>
				<div style="float: left;padding-left: 40px;">
				<img alt=""
						src="../${pageContext.request.contextPath}/resources/images/company_Logo.png"
						style="display: block; padding-left: 1px;width : 339px;high : 250px;">
				
				</div>
				
				
				
				
				
				
				
				
				
				
				
				
			</div>
		
		</s:form>
		</div>
	</div>
	
<c:if test="${!empty invalid}">
<script type="text/javascript">
	alert("Invalid Login");
</script>
</c:if> 
<c:if test="${!empty LicenceExpired}">
<script type="text/javascript">
	alert("Licence is Expired \n Please Contact Technical Support");
</script>
</c:if> 
</body>
</html>