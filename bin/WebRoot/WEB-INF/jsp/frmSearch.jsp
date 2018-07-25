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
		function funPreviousForm(object)
		{
			window.opener.funSetData(object.innerHTML);			
			window.close();
		}
	</script>
</head>

<body>

<table width="100%" border="1">
<tr>
	<c:forEach items="${listColumns}" var="column">
		<th><c:out value="${column}" /></th>
	</c:forEach>
</tr>
	<c:forEach items="${listRecords}" var="records">
		<tr>
			<c:if test="${!empty records.field1}" >
			<td>
			    <label id="field1" onclick="funPreviousForm(this)"><c:out value="${records.field1}" /></label>
			</td>
			</c:if>
			
			<c:if test="${!empty records.field2}" >
			<td><label id="field2"><c:out value="${records.field2}" /></label></td>
			</c:if>
			
			<c:if test="${!empty records.field3}" >
			<td><c:out value="${records.field3}" /></td>
			</c:if>
			
			<c:if test="${!empty records.field4}" >
			<td><c:out value="${records.field4}" /></td>
			</c:if>
			
			<c:if test="${!empty records.field5}" >
			<td><c:out value="${records.field5}" /></td>
			</c:if>
			
			<c:if test="${!empty records.field6}" >
			<td><c:out value="${records.field6}" /></td>
			</c:if>
			
			<c:if test="${!empty records.field7}" >
			<td><c:out value="${records.field7}" /></td>
			</c:if>
			
			<c:if test="${!empty records.field8}" >
			<td><c:out value="${records.field8}" /></td>
			</c:if>
			
			<c:if test="${!empty records.field9}" >
			<td><c:out value="${records.field9}" /></td>
			</c:if>
			
			<c:if test="${!empty records.field10}" >
			<td><c:out value="${records.field10}" /></td>
			</c:if>
			
			<c:if test="${!empty records.field11}" >
			<td><c:out value="${records.field11}" /></td>
			</c:if>
			
			<c:if test="${!empty records.field12}" >
			<td><c:out value="${records.field12}" /></td>
			</c:if>
		</tr>
	</c:forEach>
</table>
</body>
</html>