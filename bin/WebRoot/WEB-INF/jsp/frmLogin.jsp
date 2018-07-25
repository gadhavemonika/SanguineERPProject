<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Web Stocks</title>
  </head>
  
	<body>
		<s:form name="login" method="POST" action="validateUser.html">
	   		<table>
			    <tr>
			        <td><s:label path="strUserCode">UserCode:</s:label></td>
			        <td>
			        	<s:input name="usercode" path="strUserCode" value="super"/>
			        	<s:errors path="strUserCode"></s:errors>
			        </td>
			    </tr>
			    <tr>
			        <td><s:label path="strPassword">Password:</s:label></td>
			        <td>
			        	<s:input name="pass" path="strPassword" value="super"/>
			        	<s:errors path="strPassword"></s:errors>
			        </td>
			    </tr>
			    <tr>
			      <td colspan="2"><input type="submit" value="Submit" /></td>
			      <td colspan="2"><input type="reset" value="Reset" /></td>			      
		      	</tr>		      
			</table> 
		</s:form>
	</body>
</html>