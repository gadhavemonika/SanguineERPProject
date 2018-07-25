
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%-- Started Default Script For Page  --%>

<script type="text/javascript"
	src="<spring:url value="/resources/js/jQuery.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/validations.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/TreeMenu.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/main.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery.fancytree.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery.numeric.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery.ui-jalert.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/pagination.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery-ui.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery.excelexport.js"/>"></script>

<%-- End Default Script For Page  --%>

<%-- Started Default CSS For Page  --%>

<link rel="icon"
	href="${pageContext.request.contextPath}/resources/images/favicon.ico"
	type="image/x-icon" sizes="16x16">
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:url value="/resources/css/modal_design.css"/>" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:url value="/resources/css/tree.css"/>" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:url value="/resources/css/jquery-ui.css"/>" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:url value="/resources/css/main.css"/>" />
<link rel="stylesheet"
	href="<spring:url value="/resources/css/pagination.css"/>" />

<%-- End Default CSS For Page  --%>

<%--  Started Script and CSS For Select Time in textBox  --%>

<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery.timepicker.min.js"/>"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:url value="/resources/css/jquery.timepicker.css"/>" />

<%-- End Script and CSS For Select Time in textBox  --%>


<title>Web Stocks</title>

<script type="text/javascript">
	
</script>
<script type="text/JavaScript">
	document.onkeypress = stopRKey;
	function stopRKey(evt) {
		var evt = (evt) ? evt : ((event) ? event : null);
		var node = (evt.target) ? evt.target
				: ((evt.srcElement) ? evt.srcElement : null);
		if (evt.keyCode == 13) {
			return false;
		}
	}

	function funHelp1() {
		txtAgainst = $("#txtAgainst").val();
		funSetTransCodeHelp(txtAgainst);
		fieldName = txtAgainst;
// 		window.showModalDialog("searchform.html?formname=" + transactionName
// 				+ "&searchText=", "",
// 				"dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		
		window.open("searchform.html?formname=" + transactionName
				+ "&searchText=", "",
				"dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}

	function funSetTransCodeHelp(txtAgainst) {
		switch (txtAgainst) {
		case 'salesOrder':
			reportName = "loadExciseSalesMasterData.html";
			transactionName = 'salesorder';
			break;

		case 'productionOrder':
			reportName = "loadOPData.html";
			transactionName = 'ProductionOrder';
			break;

		case 'serviceOrder':
			reportName = "loadExciseBrandOpeningMasterData.html";
			transactionName = 'ServiceOrder';
			break;
		}
	}

	function funSetData(code) {

		/*switch (fieldName) {

		case 'salesOrder':
			funSetSOCode(code);
			break;

		case 'productmaster':
			funSetProductData(code);
			break;

		}
		*/
	}

	function funSetSOCode(SOcode) 
	{
		$("#txtSOCode").val(SOcode);
	}
	
	
	function funChangeSO()
	{
		var soCode= $("#txtSOCode").val();
		window.open(getContextPath()+"/frmUnplanned_Item.html?saddr=0&soCode="+soCode,"_self");
	}
	
	//Combo Box Change then set value
	function funOnChange() {
		if ($("#txtAgainst").val() == "salesOrder")
		{
			$("#txtSOCode").val("");
		}
		else if ($("#txtAgainst").val() == "Production Order")
		{
			$("#txtSOCode").val("");
		}
		else 
		{
			$("#txtSOCode").val("");
		}
		
	}
	

	
	
</script>
</head>
<body>

	<div style="height: 50px;">
	</div>
	<s:form name="InSoStatus" method="GET" action="">
		<table style="margin:auto;">
			<tr>
				<td><label>Against</label></td>
				<td><select id="txtAgainst" class="BoxW124px" onchange="funOnChange();">
						<option value="salesOrder">Sales Order</option>
						<option value="productionOrder">Production Order</option>
						<option value="serviceOrder">Service Order</option>
				</select></td>
				<td><label>Order Code</label></td>
				<td><input type="text" id="txtSOCode" ondblclick="funHelp1()"
					class="searchTextBox" value="${soCode}" /></td>
				<td></td>
				<td><input type="button"
					id="generateJobOrder"  onclick ="funChangeSO()" value="Display" /></td>
			</tr>
		</table>
		
	</s:form>
</body>
</html>