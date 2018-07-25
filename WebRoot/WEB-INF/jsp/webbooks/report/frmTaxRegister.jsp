<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@	taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	$(function() {
		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});
		/*$("#txtFromDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#txtFromDate").datepicker('setDate', 'today');*/
		var startDate="${startDate}";
		var arr = startDate.split("/");
		Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
		$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtFromDate" ).datepicker('setDate', Dat);
		$("#txtToDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#txtToDate").datepicker('setDate', 'today');

	});

	function funRemoveRows() {
		var table = document.getElementById("tblTaxRegister");
		var rowCount = table.rows.length;
		while (rowCount > 0) {
			table.deleteRow(0);
			rowCount--;
		}
	}

	function funFillTaxRegisterDetail() {
		
		var fromDate = $("#txtFromDate").val();
		var toDate = $("#txtToDate").val();
		var currency = $("#cmbCurrency").val();
		var taxRegurl = getContextPath()
				+ "/getTaxRegisterDetail.html?fromDate=" + fromDate
				+ "&toDate=" + toDate + "&currency=" + currency;
		//var newcontent = '<table id="tblTaxRegister" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labls1">Bill No</td><td id="labls2">Bill Date</td><td id="labls3">Tax Desc</td><td id="labls4">Taxable Amt</td><td id="labls5">Tax Amt</td></tr>';

		$.ajax({
			type : "GET",
			url : taxRegurl,
			dataType : "json",
			success : function(response) {
				
				$("#tblTaxRegister tr").remove(); 
				$.each(response.TaxDetailData, function(i, item) {
					funAddTaxRegisterRow(
							response.TaxDetailData[i].transDocCode,
							response.TaxDetailData[i].transDocDate,
							response.TaxDetailData[i].taxDesc,
							response.TaxDetailData[i].taxAmount,
							response.TaxDetailData[i].taxableAmount);
				});

				var table = document.getElementById("tblTaxSummary");
				$("#tblTaxSummary tr").remove(); 
				
				$.each(response.TaxSummaryData, function(i, item) {
					funAddTaxRegisterSummaryRow(
							response.TaxSummaryData[i].taxDesc,
							response.TaxSummaryData[i].taxableAmount,
							response.TaxSummaryData[i].taxAmount);
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

		//newcontent += '</table>';
		//$('#TaxRegister').html(newcontent);
	}

	function funAddTaxRegisterRow(transDocCode, transDocDate, taxDesc,			taxAmount, taxableAmount) 
	{
		var table = document.getElementById("tblTaxRegister");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

		//row.insertCell(0).innerHTML= "<input type=\"text\" size=\"10%\" readonly=\"readonly\" style=\"text-align: right;\" value="+transDocCode+">";
		//row.insertCell(1).innerHTML= "<input type=\"text\" size=\"10%\" readonly=\"readonly\" style=\"text-align: right;\" value="+transDocDate+">";
		//row.insertCell(2).innerHTML= "<input type=\"text\" size=\"15%\" readonly=\"readonly\" style=\"text-align: right;\" value="+taxDesc+">";

		row.insertCell(0).innerHTML = "<label size=\"12%\" style=\"text-align: right; \">"+ transDocCode + "</label>";
		row.insertCell(1).innerHTML = "<label size=\"16%\" style=\"text-align: right;\">"+ transDocDate + "</label>";
		row.insertCell(2).innerHTML = "<label size=\"10%\" style=\"text-align: right;\">"+ taxDesc + "</label>";
		row.insertCell(3).innerHTML = "<input type=\"text\" size=\"20%\" readonly=\"readonly\" style=\"text-align: right;\" value="+ taxableAmount + ">";
		row.insertCell(4).innerHTML = "<input type=\"text\" size=\"20%\" readonly=\"readonly\" style=\"text-align: right;\" value="+ taxAmount + ">";
	}
	
	
	
	function funAddTaxRegisterSummaryRow(taxDesc,taxableAmount,taxAmount) 
	{
		var table = document.getElementById("tblTaxSummary");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

		row.insertCell(0).innerHTML = "<label size=\"100%\" style=\"text-align: left;\">"+ taxDesc + "</label>";
		row.insertCell(1).innerHTML = "<input type=\"text\" size=\"20%\" readonly=\"readonly\" style=\"text-align: right;\" value="+ taxableAmount + ">";
		row.insertCell(2).innerHTML = "<input type=\"text\" size=\"20%\" readonly=\"readonly\" style=\"text-align: right;\" value="+ taxAmount + ">";
		
		
		
	}
	

	function funExportToExcel() {
		var fromDate = $("#txtFromDate").val();
		var toDate = $("#txtToDate").val();
		var currency = $("#cmbCurrency").val();
		window.location.href = getContextPath()
				+ "/taxRegisterExcelReport.html?fromDate=" + fromDate
				+ "&toDate=" + toDate + "&currency=" + currency;
	}
</script>
<body>
	<div id="formHeading">
		<label>Tax Register</label>
	</div>

	<br />
	<br />

	<s:form name="TaxRegister" method="GET" action="rptTaxRegister.html"
		target="_blank">
		<div>
			<table class="masterTable">
				<tr>
					<td width="10%"><label>From Date </label></td>
					<td width="10%" colspan="1"><s:input id="txtFromDate"
							path="dteFromDate" required="true" readonly="readonly"
							cssClass="calenderTextBox" /></td>
					<td width="10%"><label>To Date </label></td>
					<td width="10%"><s:input id="txtToDate" path="dteToDate"
							required="true" readonly="readonly" cssClass="calenderTextBox" /></td>
					<td width="10%"><label>Currency</label></td>
					<td><s:select id="cmbCurrency" path="currency"
							items="${currencyList}" cssClass="BoxW124px"></s:select></td>
				</tr>
			</table>
		</div>


		<table style="width: 80%;" class="transTablex col5-center">
			<tr>
				<td style="width: 7%">Bill No</td>
				<td style="width: 7%">Bill Date</td>
				<td style="width: 15%">Tax Desc</td>
				<td style="width: 16%">Taxable Amount</td>
				<td style="width: 16%">Tax Amount</td>
			</tr>
		</table>

		<div
			style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 300px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 80%;">
			<table id="tblTaxRegister" class="transTablex col5-center"
				style="width: 100%; height: 100%">
			    <tbody>
					<col style="width: 11.5%">
					<col style="width: 11.8%">
					<col style="width: 25.2%;">
					<col style="width: 26%;">
					<col style="width: 25%;">
					</tbody>	
			</table>		
		</div>

		<br>

		<div class="dynamicTableContainer " style="height: 200px; width: 80%;">

			<table
				style="height: 28px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;"
				class="transTablex col3-center">
				<tr bgcolor="#72BEFC" style="height: 24px;">
					<td width="18%" style="padding-left: 4px">Tax Desc</td>
					<td width="10%" style="padding-left: 4px">Taxable</td>
					<td width="10%" style="padding-left: 4px">Tax Amt</td>
				</tr>
			</table>

			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">

				<table id="tblTaxSummary"
					style="width: 104%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col3-center">
					<tbody>
					<col style="width: 38%">
					<col style="width: 16%">
					<col style="width: 26%;">
					</tbody>
				</table>
			</div>
		</div>




		<!-- <dl id="TaxRegister" style="width: 95%; margin-left: 26px; overflow:auto;"></dl>  -->

		<br>

		<p align="center">
			<input type="button" value="SUBMIT"
				onclick="funFillTaxRegisterDetail()" class="form_button" /> <input
				id="btnExport" type="button" value="EXPORT" class="form_button"
				onclick="funExportToExcel()" /> <input type="button" value="RESET"
				class="form_button" onclick="funResetFields()" />
		</p>
		
		<br/><br/>
	</s:form>

</body>
</html>