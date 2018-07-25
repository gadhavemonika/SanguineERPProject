<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
		
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">

	function funHelp(transactionName)
	{
	    window.open("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
	}

	function funSetData(code)
	{
		document.getElementById("strUserCode").value=code;
	}

</script>
<title>Insert title here</title>
<tab:tabConfig />
</head>
<body>
	<s:form action="saveSecurityShell.html" method ="POST">
		<table>
			<thead>
				<tr>
					<th><label>User Code</label>
						<s:input path="strUserCode" id="strUserCode" readonly="true" ondblclick="funHelp('usermaster')"/>
					</th>
					
					<th><label>User Name</label>
						<input id="UserName" readonly="readonly">
					</th>
				</tr>
			</thead>
		</table>
	<br/>

<!-- Start of tab container -->
<tab:tabContainer id="securityShells">
			
<!--  Start of Masters tab-->
	<tab:tabPane id="Masters" tabTitle="Masters">
		<table width="100%" border="1">
			<thead>
				<tr>
					<th width="70%" >Form Name</th>
					<th></th>
					<th>Add</th>
					<th>Edit</th>
					<th>View</th>
					<th>Print</th>
				</tr>
			</thead>
			
			<c:forEach items="${treeList.listMasterForms}" var="tree" varStatus="status">
				<tr>
					<td><input name="listMasterForms[${status.index}].strFormDesc" value="${tree.strFormDesc}"/></td>
					<td><input type="hidden" name="listMasterForms[${status.index}].strFormName" value="${tree.strFormName}"/></td>
					<td align="center"><input type="checkbox" name="listMasterForms[${status.index}].strAdd" value="${tree.strAdd}" /></td>
					<td align="center"><input type="checkbox" name="listMasterForms[${status.index}].strEdit" value="${tree.strEdit}" /></td>
					<td align="center"><input type="checkbox" name="listMasterForms[${status.index}].strView" value="${tree.strView}" /></td>
					<td align="center"><input type="checkbox" name="listMasterForms[${status.index}].strPrint" value="${tree.strPrint}" /></td>
				</tr>
			</c:forEach>
		</table>
	</tab:tabPane>
<!--  End of  Masters tab-->


<!-- Start of Transaction tab -->

	<tab:tabPane id="Transactions" tabTitle="Transactions">
		<table width="100%" border="1">
			<thead>
				<tr>
					<th width="60%">Form Name</th>
					<th></th>
					<th>Add</th>
					<th>Edit</th>
					<th>Delete</th>
					<th>View</th>
					<th>Print</th>
					<th>Authorise</th>
				</tr>
		</thead>
			<c:forEach items="${treeList.listTransactionForms}" var="tree" varStatus="status">
				<tr>
						<td><input name="listTransactionForms[${status.index}].strFormDesc" value="${tree.strFormDesc}"/></td>
						<td><input type="hidden" name="listTransactionForms[${status.index}].strFormName" value="${tree.strFormName}"/></td>
						<td align="center"><input type="checkbox" name="listTransactionForms[${status.index}].strAdd" value="${tree.strAdd}" /></td>
					 	<td align="center"><input type="checkbox" name="listTransactionForms[${status.index}].strEdit" value="${tree.strEdit}" /></td>
					 	<td align="center"><input type="checkbox" name="listTransactionForms[${status.index}].strDelete" value="${tree.strDelete}" /></td>
					 	<td align="center"><input type="checkbox" name="listTransactionForms[${status.index}].strView" value="${tree.strView}" /></td>
					 	<td align="center"><input type="checkbox" name="listTransactionForms[${status.index}].strPrint" value="${tree.strPrint}" /></td>
					 	<td align="center"><input type="checkbox" name="listTransactionForms[${status.index}].strAuthorise" value="${tree.strAuthorise}" /></td>
				</tr>
			</c:forEach>
		</table>
	</tab:tabPane>
<!-- End of Transaction tab -->


<!-- Start of Process Tab -->

	<tab:tabPane id="Process" tabTitle="Process">
		This id my Process Tab
		</tab:tabPane>
					
<!-- End  of Process Tab -->


<!-- Start of Reports Tab -->
	<tab:tabPane id="Reports" tabTitle="Reports">
		<table width="100%" border="1">
			<thead>
				<tr>
					<th width="70%">Form Name</th>
					<th></th>
					<th>Grant</th>
				</tr>
			</thead>
			<c:forEach items="${treeList.listReportForms}" var="tree" varStatus="status">
				<tr>
					<td><label id="listReportForms[${status.index}].strFormDesc" >${tree.strFormDesc}</label></td>
					<td><input type="hidden" name="listReportForms[${status.index}].strFormName" value="${tree.strFormName}"/></td>
				 	<td align="center"><input type="checkbox" name="listReportForms[${status.index}].strGrant" value="${tree.strGrant}" /></td>
				</tr>
			</c:forEach>
		</table>
	</tab:tabPane>
<!-- End  of Reports Tab -->


<!-- Start of tools tab -->

	<tab:tabPane id="Tools" tabTitle="Tools">
		<table width="100%" border="1">
			<thead>
				<tr>
					<th width="70%">Form Name</th>
					<th></th>
					<th>Grant</th>
				</tr>
			</thead>
			<c:forEach items="${treeList.listUtilityForms}" var="tree" varStatus="status">
				<tr>
					<td><label id="listUtilityForms[${status.index}].strFormDesc" >${tree.strFormDesc}</label></td>
					<td><input type="hidden" name="listUtilityForms[${status.index}].strFormName" value="${tree.strFormName}"/></td>
					<td align="center"><input type="checkbox" name="listUtilityForms[${status.index}].strGrant" value="${tree.strGrant}" /></td>
				</tr>
			</c:forEach>
		</table>
	</tab:tabPane>

<!-- End  of tools tab -->


<!-- Start of Mobile Applications Tab -->
	<tab:tabPane id="MobileApplications" tabTitle="Mobile Applications">
			This id my Mobile Applications Tab
	</tab:tabPane>
<!-- End of Mobile Applications Tab -->


</tab:tabContainer>
<!-- End Of tab container -->
	<table>	
		<tr>
			    <td colspan="1"><input type="submit" value="Submit" tabindex="3" /></td>
			    <td colspan="1"><input type="reset" value="Reset" /></td>
		</tr>
	</table>
	
</s:form>

</body>
</html>