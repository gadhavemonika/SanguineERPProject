<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Web Stocks</title>
  </head>
  
	<body>
	
		<c:forEach items="${users}" var="us">
			<tr>
				<td><c:out value="${us.strUserCode}"/></td>
				<td><c:out value="${us.strUserName}"/></td>
			</tr>
		</c:forEach>
	
	<!--  
		<s:form method="GET" action="/sdnext/validateProperty.html">
	   		<table>
			    <tr>
			    	<td>Company :</td>
					<td>
						<s:select path="strCompanyName">
				        	<s:option value="Cabiante Systems Pvt. Ltd." label="Select" />				               	
			            </s:select>
				    </td>
			    </tr>
			    <tr>
			        <td>Property Name :</td>
					<td>
						<s:select path="strPropertyName">
				        	<s:option value="Wagholi" label="Select" />
			            </s:select>
				    </td>
			    </tr>
			    <tr>
			    	<td>Financial Year :</td>
					<td>
						<s:select path="strFinancialYear">
				        	<s:option value="2014-2015" label="Select" />
				        	<s:option value="2013-2014" label="Select" />
				        	<s:option value="2012-2013" label="Select" />
				        	<s:option value="2011-2012" label="Select" />				               	
			            </s:select>
				    </td>
			    </tr>
			    <tr>
			      <td colspan="2"><input type="submit" value="Submit"/></td>
		      	</tr>		      
			</table> 
		</s:form>
		-->
	</body>
</html>