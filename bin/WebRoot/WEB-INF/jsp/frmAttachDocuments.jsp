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
		
		<s:form method="post" action="uploadFile.html" enctype="multipart/form-data">
		    <%-- <s:errors path="*" cssClass="error"/> --%>
		    
		    <table width="100%">
				<tr>
					<td >Transaction</td>
					<td></td>
					<td >
						<input type="hidden" name="formname" value="<c:out value="${formname}"/>"/>
						<input type="hidden" name="test" value="<c:out value="${formname}"/>"/>
						
						<c:out value="${formname}"/>
					</td>						
					
					<td></td>
					
					<td >Code</td>
					<td></td>
					<td >
						<input type="hidden" name="code" value="<c:out value="${code}"/>"/>
						<c:out value="${code}"/>
					</td>
				</tr>
			</table>
		    
		    <table>
			    <tr>
			        <td><s:label path="strActualFileName">Name</s:label></td>
			        <td><s:input path="strActualFileName" /></td>
			    </tr>			    
			    <tr>
			        <td><s:label path="binContent">Document</s:label></td>
			        <td><input type="file" name="file" id="file_upload"></input></td>
			    </tr>
			    <tr>
			        <td colspan="2">
			            <input type="submit" value="Add Document"/>
			        </td>
			    </tr>
			</table> 
		</s:form>
		
		<br/>
		<h3>Document List</h3>
		<c:if  test="${!empty documentList}">
			<table class="data">
				<tr>
				    <th>Name</th>
				    <th>Description</th>
				    <th>&nbsp;</th>
				</tr>
				
				<c:forEach items="${documentList}" var="doc">
				    <tr>
				        <td width="200px">${doc.strActualFileName}</td>
				    	<td>
				    		<a href="${pageContext.request.contextPath}/download/${doc.strCode},${doc.intFileNo}.html">${doc.strActualFileName}</a>
					<!-- 	<a href="${pageContext.request.contextPath}/remove/${doc.strCode}.html"
			           			onclick="return confirm('Are you sure you want to delete this document?')">${doc.strActualFileName}</a>
			           			 -->
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		
</body>
</html>