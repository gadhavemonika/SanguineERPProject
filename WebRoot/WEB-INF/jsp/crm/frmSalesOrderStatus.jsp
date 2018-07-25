<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Sales Order Status</title>

<style type="text/css">

</style>

<script type="text/javascript">
	$(document).ready(function() {

		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});

		$("#txtSOFromDate").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("#txtSOFromDate").datepicker('setDate', 'today');

		$("#txtSOToDate").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("#txtSOToDate").datepicker('setDate', 'today');

		// 		make_tree_menu('salesOrderTree');
	});

	function funSetData(code) {

		switch (fieldName) {

		case 'custMasterActive':
			funSetCustomerData(code);
			break;

		}

	}

	function funSetCustomerData(code) {

		gurl = getContextPath() + "/loadPartyMasterData.html?partyCode=";
		$.ajax({
			type : "GET",
			url : gurl + code,
			dataType : "json",
			success : function(response) {
				if ('Invalid Code' == response.strPCode) {
					alert("Invalid Customer Code");
					$("#txtPartyCode").val('');
					$("#txtPartyCode").focus();

				} else {
					$("#txtCustCode").val(response.strPCode);
					$("#txtCustName").text(response.strPName);

				}
			},
			error : function(jqXHR, exception) {
				if (jqXHR.status === 0) {
					alert('Not connect.n Verify Network.');
				} else if (jqXHR.status == 404) {
					alert('Requested page not found. [404]');
				} else if (jqXHR.status == 500) {
					alert('Internal Server Error [500].');
				} else if (exception === 'parsererror') {
					alert('Requested JSON parse failed.');
				} else if (exception === 'timeout') {
					alert('Time out error.');
				} else if (exception === 'abort') {
					alert('Ajax request aborted.');
				} else {
					alert('Uncaught Error.n' + jqXHR.responseText);
				}
			}
		});
	}

	function funHelp(transactionName) {
		fieldName = transactionName;
	//	window.showModalDialog("searchform.html?formname=" + transactionName+ "&searchText=", "","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname=" + transactionName+ "&searchText=", "","dialogHeight:600px;dialogWidth:600px;top=500,left=500");
		
	}
	
	function funOpenStatus(soCode) {
// 		window.open("frmUnplanned_Item.html?soCode=" 
// 		+ soCode.innerText, "_blank", "toolbar=yes, scrollbars=yes,fullscreen=yes, resizable=yes");
		window.open(getContextPath()+"/frmUnplanned_Item.html?saddr=1&soCode="+soCode.innerText);
	}
	
	function funShowSalesOrder()
	{
		var fromSoDate =	$("#txtSOFromDate").val();
		var toSoDate =	$("#txtSOToDate").val();
		var strCustCode =	$("#txtCustCode").val();
		gurl = getContextPath() + "/showSalesOrder.html?fromSoDate="+fromSoDate+"&toSoDate="+toSoDate+"&strCustCode="+strCustCode;
		$.ajax({
			type : "GET",
			url : gurl ,
			dataType : "json",
			success : function(response) {
				
				funRemoveTaxRows();
				$.each(response, function(i, item) {
					
					funShowTableSos(item[0],item[1],item[2],item[3],item[4],item[4],i);
				});
				
				
			},
			error : function(jqXHR, exception) {
				if (jqXHR.status === 0) {
					alert('Not connect.n Verify Network.');
				} else if (jqXHR.status == 404) {
					alert('Requested page not found. [404]');
				} else if (jqXHR.status == 500) {
					alert('Internal Server Error [500].');
				} else if (exception === 'parsererror') {
					alert('Requested JSON parse failed.');
				} else if (exception === 'timeout') {
					alert('Time out error.');
				} else if (exception === 'abort') {
					alert('Ajax request aborted.');
				} else {
					alert('Uncaught Error.n' + jqXHR.responseText);
				}
			}
		});
	}
	
	
	function funShowTableSos(soCode,soDate,custName,fullfillDate,status,bar,i)
	{
		 	var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);		   
		    rowCount=i;
		
			row.insertCell(0).innerHTML= '<a id="txtProdCode.'+i+'" href="#" onClick="funOpenStatus(this);">'+soCode+'</a>' 
		//		"<input  readonly=\"readonly\" class=\"Box prodCode\" size=\"10%\" id=\"txtProdCode."+(rowCount)+"\" value='"+soCode+"'  onClick=\"funOpenStatus("+soCode+");\" >";
		    row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box\" size=\"10%\"  id=\"lblProdName."+(rowCount)+"\" value='"+soDate+"' >";
		    
		    row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box  type=\"text\"  required = \"required\" size=\"30%\" style=\"width:100%\"  id=\"txtQtyRec."+(rowCount)+"\" value='"+custName+"'>";
		    row.insertCell(3).innerHTML= "<input  type=\"text\" class=\"Box   required  = \"required\" size=\"10%\" style=\"width:100%\"  id=\"txtDCQty."+(rowCount)+"\" value='"+fullfillDate+"'>";
		    row.insertCell(4).innerHTML= "<input  type=\"text\" class=\"Box  required = \"required\" size=\"10%\"  id=\"txtDCWt."+(rowCount)+"\" value='"+status+"%'>";
		    row.insertCell(5).innerHTML= '<div style=\"background-color: #ddd;width:100%\" ><div  style=\"height: 30px;width:'+bar+'%;background-color: green;  text-align: center;  line-height: 30px;  color: white;\"   \>'+bar+'%</div></div>';
		    
		
	}
	
	
	function funRemoveTaxRows()
	{
		var table = document.getElementById("tblProduct");
		var rowCount = table.rows.length;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}	
</script>

</head>
<body>
	<div id="formHeading">
		<label>Sales Order Status</label>
	</div>
	<s:form name="sostatus" method="POST"
		action="saveSOStatus.html?saddr=${urlHits}">
		<br>
		<table class="transTable">
			<tr>
				<td>SO From Date</td>
				<td><s:input type="text" id="txtSOFromDate"
						class="calenderTextBox" path="dteSOFromDate" /></td>

				<td>SO To Date</td>
				<td><s:input type="text" id="txtSOToDate"
						class="calenderTextBox" path="dteSOToDate" /></td>
			</tr>

			<tr>
				<td><label>Customer Code</label></td>
				<td><s:input type="text" id="txtCustCode" path="strCustCode"
						ondblclick="funHelp('custMasterActive')" cssClass="searchTextBox" /></td>
				<td colspan="1"><label id="txtCustName"></label></td>
				
				<td>
					<input type="button" id="btnExecute" value="Execute" onClick="funShowSalesOrder()" class="smallButton" />
				</td>
			</tr>
		</table>
		
		<br />
		<br />
		<div class="dynamicTableContainer" style="height: 330px">
			<table id="tblSalesOrderDtl" style="height:28px;border:#0F0;width:100%;font-size:11px;
			font-weight: bold;">	
<!-- 		<div class="dynamicTableContainer" style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; margin: auto;"> -->
<!-- 			<table id="tblSalesOrderDtl" style="width: 100%; border: #0F0; table-layout: fixed;" class="transTablex col15-center"> -->
				<tr bgcolor="#72BEFC">
					<th width="10%">Sales Order Code</th>
					<!--  COl1   -->
					<th width="10%">Sales Order Date</th>
					<!--  COl2   -->
					<th width="30%">Customer Name</th>
					<!--  COl3   -->
					<th width="10%">Fulfillment Date</th>
					<!--  COl4   -->
					<th width="10%">Status</th>
					<!--  COl5   -->
					<th width="30%">Status Bar</th>
					<!--  COl5   -->
				</tr>
				</table>
<!-- 				<table id="tblProduct" style="width:100%;border: -->
<!-- 					#0F0;table-layout:fixed;overflow:scroll;" class="transTablex col20-center"> -->
					
					<div style="background-color:  	#a4d7ff;     border: 1px solid #ccc;     display: block;     height: 280px;     margin: auto;     overflow-x: hidden;     overflow-y: scroll;     width: 100%;">
						<table id="tblProduct" style="width:100%;border: #0F0;table-layout:fixed;overflow:scroll" class="transTablex  col11-center "> 
						<tbody> 
				<tbody>  
				<col style="width:11%"><!--  COl1   -->
				<col style="width:10%"><!--  COl2   -->
				<col style="width:32%"><!--  COl5   -->
				<col style="width:11%"><!--  COl6   -->
				<col style="width:10%"><!--  COl7   -->
				<col style="width:30%"><!--  COl8   -->
					<!-- <td>
						<a href="" onClick="funOpenStatus('01SOAK0000001')">01SOAK0000001</a>
					</td>
					<td>2015-11-06</td>
					<td>M/s. ASSAD CONSULTING SERVICES.</td>
					<td>2015-12-06</td>
					<td>0%</td>
					<td>Status Bar</td> -->
				</tbody>
				</table>
				</div>
		</div>

		<br />
		<!-- <p align="center">
			<input type="submit" value="Submit" class="form_button" /> <input
				type="button" id="reset" value="Reset" class="form_button" />
		</p> -->
		<br />
		<br />

		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
	</s:form>

	<script type="text/javascript">
// 		funApplyNumberValidation();
	</script>
</body>
</html>