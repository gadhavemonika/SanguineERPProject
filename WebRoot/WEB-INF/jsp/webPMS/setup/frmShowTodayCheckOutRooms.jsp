<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Today's Checkout List</title>
<style>
.ui-autocomplete {
    max-height: 200px;
    overflow-y: auto;
    /* prevent horizontal scrollbar */
    overflow-x: hidden;
    /* add padding to account for vertical scrollbar */
    padding-right: 20px;
}
/* IE 6 doesn't support max-height
 * we use height instead, but this forces the menu to always be this tall
 */
* html .ui-autocomplete {
    height: 200px;
}
</style>
<script type="text/javascript">


</script>


</head>

<body style="background-color: #D8EDFF;">
	<div id="formHeading">
		<label>Today's Checkout List</label>
	</div>
	<s:form name="chkOutListForm" method="POST" action="">

		<br />
		<br />
		<table class="masterTable">
				
									<tr>
										<td><lable>Sr. No</lable></td>
											
										<td><lable>Room Number</lable></td>	
										
										<td><lable>Room Type</lable></td>
									</tr>

			<c:forEach items="${listCheckout}"
									var="listCheckoutRoom" varStatus="status" >
<%-- 									<c:set var="srNo" value="1"></c:set> --%>
									<tr>
										<td><input type="text" value="${status.index+1}" readonly="readonly" Class="BoxW140px"/></td>
											
										<td><input type="text" value="${listCheckoutRoom[0]}" readonly="readonly" Class="BoxW140px"/></td>	
										
										<td><input type="text" value="${listCheckoutRoom[1]}" readonly="readonly" Class="BoxW140px"/></td>
									</tr>	
<%-- 									<c:set var="srNo" value="${srNo+1}"></c:set> --%>
			</c:forEach>							
		</table>
		<br />
		<br />
		
	</s:form>

</body>
</html>