<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="sp"%>
<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	var listRow = 0;

	$(document).ready(function() {
		
		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});
		
		$( "#txtTpDate" ).datepicker({ dateFormat: 'yy-mm-dd' });
		$( "#txtTpDate" ).datepicker('setDate','today');

	});
	
	
	function funResetFields()
	{
		location.reload(true); 	
    }

	function funHelp(transactionName) 
	{
		fieldName = transactionName;
		var licenceCode=$("#txtLicenceCode").val();
		if(transactionName=="TPCode")
		{
			if($("#txtLicenceCode").val().trim().length == 0){
				
				alert("Please Select Licence ");
			}else{
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&licenceCode="+licenceCode+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
				window.open("searchform.html?formname="+transactionName+"&licenceCode="+licenceCode+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
		}
	   }else{
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
	}}

	function funSetData(code) {

		switch (fieldName) {
		
		case 'LicenceCode':
			funSetLicenceData(code);
			break;
		case 'TPCode':
			funSetTPData(code);
			break;
		case 'SupplierCode':
			funSetSupplierName(code);
			break;
		case 'BrandCode':
			funSetBrandName(code);
			break;
		}
	}

	function funSetBrandName(code) {
		$("#txtBottals").focus();
		funClearBrandDetails();

		var searchURL = getContextPath()
				+ "/loadExciseBrandMasterData.html?brandCode=" + code;
		$.ajax({
			type : "GET",
			url : searchURL,
			dataType : "json",
			success : function(response) {
				if (response.strBrandCode == 'Invalid Code') {
					alert("Invalid Brand Code Please Select Again");
					$("#txtBrandCode").val('');
					$("#txtBrandCode").focus();

				} else {
					$("#txtBrandCode").val(response.strBrandCode);
					$("#txtName").text(response.strBrandName);
					$("#txtStrength").text(response.dblStrength);
					$("#txtSize").text(response.strSizeName);
					
					var MRP = parseFloat(response.dblRate)*(parseInt(response.intSizeQty)/parseInt(response.intPegSize))
					
					$("#txtMRP").text(MRP);
					$("#txtPegPrice").text(response.dblPegPrice);
					
					intPegSize=response.intPegSize;
	        		intBrandSize=response.intSizeQty;
	        		dblOpStk=response.strAvailableStk;
	        		
	        		var arr = dblOpStk.toString().split('.');
	       			var opBts=0;
	       			var opMls=0
	       			if(arr.length>1){
	       				opBts=arr[0];
	       				opMls=arr[1];
	       			}else{
	       				opBts=arr[0];
	       			}
	       			
	       			opMls=parseInt(opMls)/parseInt(intPegSize);
	       			if(parseFloat(opBts+"."+opMls) >0){
	       				$("#txtAvailabelStk").html("<span style='color:#00802b'><b>Available Stock :&nbsp;&nbsp;&nbsp;&nbsp;"+opBts+"."+opMls+"</b></span>");
	       			}else{
	       				$("#txtAvailabelStk").html("<span style='color:#ff3300'><b>Available Stock :&nbsp;&nbsp;&nbsp;&nbsp;"+opBts+"."+opMls+"</b></span>");
	       			}	       			
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

	function funSetSupplierName(code) {
		$("#txtInvoiceNo").focus();
		funClearBrandRowData();
		var searchURL = getContextPath()
				+ "/loadExciseSupplierMasterData.html?supplierCode=" + code;

		$.ajax({
			type : "GET",
			url : searchURL,
			dataType : "json",
			success : function(response) {
				if (response.strSupplierCode == 'Invalid Code') {
					alert("Invalid Excise Supplier Code Please Select Again");
					$("#txtSupplierCode").val('');
					$("#txtSupplierCode").focus();

				} else {
					$("#txtSupplierCode").val(response.strSupplierCode);
					$("#txtSupplierName").text(response.strSupplierName);
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
	
	function funSetLicenceData(code) {
		$("#txtTPCode").focus();
		var gurl=getContextPath()+"/loadExciseLicenceMasterData.html?licenceCode=";
			$.ajax({
		        type: "GET",				        
		        url: gurl+code,
		        dataType: "json",
		        success: function(response)
		        {	
		        	if(response.strLicenceNo=='Invalid Code')
		        	{
		        		alert("Invalid Licence Code Please Select Again");
		        		$("#txtLicenceCode").val('');
		        	}
		        	else
		        	{
		        		$("#txtLicenceCode").val(response.strLicenceCode);
		        		$("#licenceNo").text(response.strLicenceNo);
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
	
	
	function btnAdd_onclick() {

		if ($("#txtBrandCode").val().trim().length == 0) {
			alert("Please Enter Brand Code Or Search");
			$('#txtBrandCode').focus();
			return false;
		}

		if (($("#txtBottals").val() == 0) || ($("#txtBottals").val().trim().length == 0)) {
			alert("Please Enter Quantity ");
			$('#txtBottals').focus();
			return false;
		} 
		
		var strBrandCode = $("#txtBrandCode").val();
		if (funDuplicateBrand(strBrandCode)) {
			funAddBrandRow();
		}

	}

	function funDuplicateBrand(strBrandCode) {
		var table = document.getElementById("tblTPDet");
		var rowCount = table.rows.length;
		var flag = true;
		if (rowCount > 0) {
			$('#tblTPDet tr').each(function() {
				if (strBrandCode == $(this).find('input').val())// `this` is TR DOM element
				{
					alert("Already added " + strBrandCode);
					flag = false;
				}
			});

		}
		return flag;

	}

	function funAddBrandRow() {
		var strBrandCode = $("#txtBrandCode").val().trim();
		var strBrandName = $("#txtName").text();
		var strSize = $("#txtSize").text();
		var dblBrandMRP = $("#txtMRP").text();
		var dblBrandPegPrice = $("#txtPegPrice").text();
		var intBottals = $("#txtBottals").val();
		
		var dblMRP = parseFloat(dblBrandMRP);
		var dblTotalBrandPrice = parseFloat(dblMRP) * intBottals;
		dblTotalPrice = parseFloat(dblTotalBrandPrice);

		var tax = $("#txtTax").val();
		if (tax != '') {
			var dblTax = parseFloat(tax);
		} else {
			dblTax = 0;
		}
		var fees = $("#txtFees").val();
		if (fees != '') {
			var dblFees = parseFloat(fees);
		} else {
			dblFees = 0;
		}

		var strCase = $("#txtCases").val();
		if (strCase == '') {
			strCase = "NA";
		}
		var strBatch = $("#txtBatch").val();
		if (strBatch == '') {
			strBatch = "NA";
		}

		var strCase = $("#txtCases").val();
		if (strCase == '') {
			strCase = 0;
		}
		var strMfgDate = $("#txtMfgDate").val();
		if (strMfgDate == '') {
			strMfgDate = "NA";
		}

		var strRemark = $("#txtRemark").val();
		if (strRemark == '') {
			strRemark = "NA";
		}

		var table = document.getElementById("tblTPDet");
		var rowCount1 = table.rows.length;
		var row = table.insertRow(rowCount1);
		var rowCount = listRow;

		row.insertCell(0).innerHTML = "<input  readonly=\"readonly\" size=\"10%\" class=\"Box\" name=\"objTPMasterdtlModel["+(rowCount)+"].strBrandCode\" id=\"txtBrandCode."
				+(rowCount) + "\" value='" + strBrandCode + "' />";

		row.insertCell(1).innerHTML = "<input size=\"20%\"  readonly=\"readonly\" class=\"Box\" id=\"txtBrandName."
				+ (rowCount) + "\" value='" + strBrandName + "' />";

		row.insertCell(2).innerHTML = "<input size=\"8%\" readonly=\"readonly\" class=\"Box\" id=\"txtBrandSize."
				+ (rowCount) + "\" value='" + strSize + "' />";

		row.insertCell(3).innerHTML = "<input size=\"5%\" readonly=\"readonly\" class=\"Box txtBrandMRP\" id=\"txtBrandMRP."
				+ (rowCount) + "\" value='" + dblMRP + "' />";

		row.insertCell(4).innerHTML = "<input type=\"text\" size=\"10%\" class=\"positive-integer numeric inputText-Auto txtBottals\" name=\"objTPMasterdtlModel["+ (rowCount)+"].intBottals\" id=\"txtBottals." 
				+(rowCount)+ "\"	required = \"required\" value='"+ intBottals+ "' onchange=\"Javacsript:funUpdatePrice(this)\" />";

		row.insertCell(5).innerHTML = "<input size=\"10%\" readonly=\"readonly\" class=\"Box dblBrandTotal\" id=\"dblBrandTotal."
				+ (rowCount)+ "\" value='"+ dblTotalPrice+ "' name=\"objTPMasterdtlModel["+ (rowCount)+ "].dblBrandTotal\" />";

		row.insertCell(6).innerHTML = "<input size=\"10%\" class=\"decimal-places numeric inputText-Auto txtBrandTax\" required=\"required\" name=\"objTPMasterdtlModel["
				+ (rowCount)+ "].dblBrandTax\" id=\"txtBrandTax."+ (rowCount)+ "\" value='"+ dblTax+ "' onchange=\"Javascript:funCalculateTotalAmt()\" />";

		row.insertCell(7).innerHTML = "<input size=\"10%\" class=\"decimal-places numeric inputText-Auto txtBrandFees\" required=\"required\" name=\"objTPMasterdtlModel["
				+ (rowCount)+ "].dblBrandFee\" id=\"txtBrandFees."+ (rowCount)+ "\" value='"+ dblFees+ "' onchange=\"Javacsript:funCalculateTotalAmt()\" />";

		row.insertCell(8).innerHTML = "<input size=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"objTPMasterdtlModel["
				+ (rowCount)+ "].intBrandCases\" id=\"txtBrandCases."+ (rowCount) + "\" value='" + strCase + "' />";

		row.insertCell(9).innerHTML = "<input size=\"10%\" readonly=\"readonly\" class=\"Box\" name=\"objTPMasterdtlModel["
				+ (rowCount)+ "].strBrandBatchNo\" id=\"txtBrandBatchNo."+ (rowCount) + "\" value=" + strBatch + " />";
		
		row.insertCell(10).innerHTML = "<input size=\"10%\" readonly=\"readonly\" class=\"Box\" name=\"objTPMasterdtlModel["
				+ (rowCount)+ "].strMonOfMfg\" id=\"txtMonOfMfg."+ (rowCount)+ "\"  value=" + strMfgDate + " />";

		row.insertCell(11).innerHTML = "<input size=\"10%\" readonly=\"readonly\" class=\"Box totalValueCell\" name=\"objTPMasterdtlModel["
				+ (rowCount)+ "].strRemark\" id=\"txtRemark."+ (rowCount)+ "\" readonly=\"true\"  value=" + strRemark + ">";

		row.insertCell(12).innerHTML = '<input size=\"3%\" type="button" value = "Delete"  class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';

		listRow++;

		funClearBrandDetails();
		funApplyNumberValidation();
		funCalculateTotalAmt();

		$('#txtBrandCode').focus();
		return false;
	}
	
	function funCalculateTotalAmt() {
		var totalPurchase = 0;
		var totalTax = 0;
		var totalFee = 0
		var totalBill = 0

		var pur = 0;
		var tax = 0;
		var bill = 0;

		$('#tblTPDet tr').each(function() {
			if ($(this).find(".dblBrandTotal").val() != '') {
				pur = parseFloat($(this).find(".dblBrandTotal").val());
			}
			if ($(this).find(".txtBrandTax").val() != '') {
				tax = parseFloat($(this).find(".txtBrandTax").val());
			}
			if ($(this).find(".txtBrandFees").val() != '') {
				bill = parseFloat($(this).find(".txtBrandFees").val())
			}
			totalPurchase = pur + totalPurchase;
			totalTax = tax + totalTax;
			totalFee = bill + totalFee;
		});

		$("#txtTotalPurchase").val(totalPurchase);
		$("#txtTotalTax").val(totalTax);
		$("#txtTotalFees").val(totalFee);

		totalBill = (totalPurchase) + (totalTax) + (totalFee);

		$("#txtTotalBill").val(totalBill);
	}

	function funUpdatePrice(object) {
		var intBottals = 0;
		var dblBrandCost = 0;

		var $row = $(object).closest("tr");
		if ($row.find(".txtBrandMRP").val() != '') {
			dblBrandCost = parseFloat($row.find(".txtBrandMRP").val());
		}

		if ($row.find(".txtBottals").val() != '') {
			intBottals = parseFloat($row.find(".txtBottals").val());
		}
		var dblTotalBrandPrice = parseFloat(dblBrandCost) * intBottals;

		if (dblTotalBrandPrice == '' || !($.isNumeric(dblTotalBrandPrice))) {
			dblTotalBrandPrice = 0;
		}
		dblTotalPrice = parseFloat(dblTotalBrandPrice);
		$row.find(".dblBrandTotal").val(dblTotalPrice);

		funCalculateTotalAmt();
	}

	function funDeleteRow(obj) {
		var index = obj.parentNode.parentNode.rowIndex;
		var table = document.getElementById("tblTPDet");
		table.deleteRow(index);
		funCalculateTotalAmt();
	}

	function funClearBrandDetails() {
		$("#txtBrandCode").val("");
		$("#txtName").text("");
		$("#txtStrength").text("");
		$("#txtSize").text("");
		$("#txtMRP").text("");
		$("#txtAvailabelStk").text("");
		$("#txtBottals").val("");
		$("#txtTax").val("");
		$("#txtFees").val("");
		$("#txtCases").val("");
		$("#txtBatch").val("");
		$("#txtMfgDate").val("");
		$("#txtRemark").val("");
	}

	function funClearBrandRowData() {
		$("#tblTPDet tr").remove();
	}

	function funSetTPData(code) {

		funClearBrandRowData();
		$("#txtInvoiceNo").focus();
		var searchURL = getContextPath()+ "/loadExciseTPMasterData.html?tpCode=" + code;
		$("#txtTPCode").val(code);
		$.ajax({
			type : "GET",
			url : searchURL,
			dataType : "json",
			success : function(response) {
				if (response.strTPCode == 'Invalid Code') {
					alert("Invalid Brand Code Please Select Again");
					$("#txtTPCode").val('');
					$("#txtTPCode").focus();
					funResetFields();

				} else {
					$("#txtTPCode").val(response.strTPCode);
					$("#txtTPNo").val(response.strTPNo);
					$("#txtLicenceCode").val(response.strLicenceCode);
					$("#txtTpDate").val(response.strTpDate);
	        		$("#licenceNo").text(response.strLicenceNo);		
	        		$("#txtInvoiceNo").val(response.strInvoiceNo)
					$("#txtTotalPurchase").val(response.dblTotalPurchase);
					$("#txtTotalTax").val(response.dblTotalTax);
					$("#txtTotalFees").val(response.dblTotalFees);
					$("#txtTotalBill").val(response.dblTotalBill);
					$("#txtSupplierCode").val(response.strSupplierCode);
					$("#txtSupplierName").text(response.strSupplierName);

					funSetTPDtl(response.objTPMasterdtlModel);
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

	function funSetTPDtl(response) {
			var count=0;
		$.each(response, function(i, item) {
			funfillBrandRow(response[i]);
			count=i;
		});
		listRow=count+1;
	}
	
	function funfillBrandRow(brandData) {
		var table = document.getElementById("tblTPDet");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		
		row.insertCell(0).innerHTML = "<input size=\"10%\" readonly=\"readonly\" class=\"Box\" name=\"objTPMasterdtlModel["
				+ (rowCount)+ "].strBrandCode\" id=\"txtBrandCode."+ (rowCount) + "\" value='" + brandData.strBrandCode + "' />";

		row.insertCell(1).innerHTML = "<input size=\"15%\" readonly=\"readonly\" class=\"Box\" id=\"txtBrandName."
				+ (rowCount) + "\" value='" + brandData.strBrandName + "' />";

		row.insertCell(2).innerHTML = "<input size=\"8%\" readonly=\"readonly\" class=\"Box\" id=\"txtBrandSize."
				+ (rowCount) + "\" value='" + brandData.strBrandSize + "' />";

		row.insertCell(3).innerHTML = "<input size=\"5%\" readonly=\"readonly\" class=\"Box txtBrandMRP\" id=\"txtBrandMRP."
				+ (rowCount) + "\" value='" + brandData.dblBrandMRP + "' />";

		row.insertCell(4).innerHTML = "<input size=\"10%\" type=\"text\" class=\"positive-integer numeric inputText-Auto txtBottals\" name=\"objTPMasterdtlModel["
				+ (rowCount)+ "].intBottals\" id=\"txtBottals."	+ (rowCount)+ "\"	required = \"required\" value='"+ brandData.intBottals
				+ "' onchange=\"Javacsript:funUpdatePrice(this)\" />";

		row.insertCell(5).innerHTML = "<input size=\"10%\" readonly=\"readonly\" class=\"Box dblBrandTotal\" id=\"dblBrandTotal."
				+ (rowCount)+ "\" value='"+ brandData.dblBrandTotal+ "' name=\"objTPMasterdtlModel["+ (rowCount)+ "].dblBrandTotal\" />";

		row.insertCell(6).innerHTML = "<input size=\"10%\" type=\"text\" class=\"decimal-places numeric inputText-Auto txtBrandTax\" required=\"required\" name=\"objTPMasterdtlModel["
				+ (rowCount)+ "].dblBrandTax\" id=\"txtBrandTax."+ (rowCount)+ "\" value='"+ brandData.dblBrandTax+ "' onchange=\"Javacsript:funCalculateTotalAmt()\" />";

		row.insertCell(7).innerHTML = "<input size=\"10%\" type=\"text\" class=\"decimal-places numeric inputText-Auto txtBrandFees\" required=\"required\" name=\"objTPMasterdtlModel["
				+ (rowCount)+ "].dblBrandFee\" id=\"txtBrandFees."+ (rowCount)+ "\" value='"+ brandData.dblBrandFee+ "' onchange=\"Javacsript:funCalculateTotalAmt()\" />";

		row.insertCell(8).innerHTML = "<input size=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"objTPMasterdtlModel["
				+ (rowCount)+ "].intBrandCases\" id=\"txtBrandCases."+ (rowCount) + "\" value='" + brandData.intBrandCases + "' />";

		row.insertCell(9).innerHTML = "<input size=\"10%\" readonly=\"readonly\" class=\"Box\" name=\"objTPMasterdtlModel["
				+ (rowCount)+ "].strBrandBatchNo\" id=\"txtBrandBatchNo."+ (rowCount) + "\"  value=" + brandData.strBrandBatchNo + " />";

		row.insertCell(10).innerHTML = "<input size=\"10%\" readonly=\"readonly\" class=\"Box\" name=\"objTPMasterdtlModel["
				+ (rowCount)+ "].strMonOfMfg\" id=\"txtMonOfMfg."+ (rowCount)+ "\"  value=" + brandData.strMonOfMfg + " />";

		row.insertCell(11).innerHTML = "<input size=\"10%\" readonly=\"readonly\" class=\"Box totalValueCell\" name=\"objTPMasterdtlModel["
				+ (rowCount)+ "].strRemark\" id=\"txtRemark."+ (rowCount)+ "\" readonly=\"true\"  value=" + brandData.strRemark + ">";

		row.insertCell(12).innerHTML = '<input size=\"3%\" type="button" value = "Delete"  class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';

		funClearBrandDetails();
		funApplyNumberValidation();
		funCalculateTotalAmt();
	}

	function funClearSupplierData() {
		$("#txtSupplierName").text('');
	}

	$(function() {
		
		var message='';
		<%if (session.getAttribute("success") != null) {
			            if(session.getAttribute("successMessage") != null){%>
			            message='<%=session.getAttribute("successMessage").toString()%>';
			            <%
			            session.removeAttribute("successMessage");
			            }
						boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
						session.removeAttribute("success");
						if (test) {
						%>	
				alert(message+"\n Saved Successfully");
		<%
		}}%>
		
		$('#txtLicenceCode').blur(function() {
			var code = $('#txtLicenceCode').val();
			if (code.trim().length > 0 && code != "?" && code != "/") {
				funSetLicenceData(code);
			}
		});

		$('#txtTPCode').blur(function() {
			var code = $('#txtTPCode').val();
			if (code.trim().length > 0 && code != "?" && code != "/") {
				funSetTPData(code);
			}
		});

		$('#txtBrandCode').blur(function() {
			var code = $('#txtBrandCode').val();
			if (code.trim().length > 0 && code != "?" && code != "/") {
				funSetBrandName(code);
			}
		});

		$('#txtSupplierCode').blur(function() {
			var code = $('#txtSupplierCode').val();
			if (code.trim().length > 0 && code != "?" && code != "/") {
				funSetSupplierName(code);
			}
		});
		
		$("#reset").click(function(){
			location.reload();
		});

		$("form").submit(function() {

			if ($("#txtSupplierCode").val() == '') {
				alert("Please Enter Supplier Or Search");
				return false;
			}
			
			var table = document.getElementById("tblTPDet");
			var rowCount = table.rows.length;
			if (rowCount == 0) {
				alert("Please Add Brand in Grid");
				return false;
			}

		});
		
		$('#txtTPNo').blur(function() {
			var code = $('#txtTPNo').val();
			if (code.trim().length > 0 && code != "?" && code != "/") {
				funCheckTpNo(code);
			}
		});
		

	});
	
	function funApplyNumberValidation(){
		$(".numeric").numeric();
		$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
		$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
		$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
	    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
	}
	
	function funCheckTpNo(code)
	{
		if(code.length>0)
			{
				var searchURL = getContextPath()+ "/loadTpNoData.html?tpNo=" + code;
				
				$.ajax({
					type : "GET",
					url : searchURL,
					dataType : "json",
					success : function(response) {
						if (response.strTPNo == '') {
// 							alert("Invalid TpNo Please Select Again");
							$("#txtTPNo").val(code);
							//$("#txtTPNo").focus();
							
	
						} else {
							
							var resTpNo = response.strTPNo;
							if(resTpNo==code)
								{
									alert("This Tp Number is already Created ");
									$("#txtTPNo").val('');
									$("#txtTPNo").focus();
								}
						
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
		else
			{
			alert("Please Enter TP NO. ");
			}
		
		
	}
	
	function funValidateFields() 
	{
		
		if(funGetMonthEnd(document.all("txtLicenceCode").value,document.all("txtTpDate").value)!=true)
        {
            alert("Month End Not Done");
            return false
              
        }
		else
		{
			return true;
		}
	}
	//Check Month End done or not
	function funGetMonthEnd(licenceCode,date) {
		var strMonthEnd="";
		var searchUrl = "";
		searchUrl = getContextPath()
				+ "/checkExciseMonthEnd.html?licenceCode=" + licenceCode+"&date="+date+"&formName=Transport " ;

		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			async: false,
			success : function(response) {
				strMonthEnd=response;
				//alert(strMonthEnd);
				
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
		if(strMonthEnd=="1")
			return false;
		if(strMonthEnd=="0")
			return true;
	}
	
	
</script>

</head>
<body>

	<div id="formHeading">
		<label>Transport Pass</label>
	</div>
	<br />
	<s:form name="TPMaster" method="POST" action="saveExciseTPMaster.html?saddr=${urlHits}">
		<br>
		<table class="transTable">
			<tr>
				<td>
					<label>Licence Code</label>
				</td>
				<td>
					<s:input id="txtLicenceCode" ondblclick="funHelp('LicenceCode')" type="text" path="strLicenceCode" cssClass="searchTextBox" value="${LicenceCode}"  required="true"/>
				</td>
				<td><s:label id="licenceNo" path="strLicenceNo" >${LicenceNo}</s:label></td>
				<td>TP Code</td>
				<td>
					<s:input id="txtTPCode" ondblclick="funHelp('TPCode')" type="text" path="strTPCode" cssClass="searchTextBox" />
				</td>
				<td>Tp Number</td>
				<td>
					<s:input id="txtTPNo" path="strTPNo" cssClass="BoxW116px" required="true" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td>Supplier</td>
				<td>
					<s:input id="txtSupplierCode" type="text" required="true" path="strSupplierCode" cssClass="searchTextBox" ondblclick="funHelp('SupplierCode');" />
				</td>
				<td>
					<s:label path="strSupplierName" id="txtSupplierName" />
				</td>
				<td></td>
				<td>
					<label>Invoice Number</label>
				</td>
				<td>
					<s:input id="txtInvoiceNo" type="text" required="true" path="strInvoiceNo" cssClass="BoxW116px" />
				</td>
				<td>TP Date</td>
				<td>
					<s:input type="text" id="txtTpDate" class="calenderTextBox"	path="strTpDate" />
				</td>
			</tr>
		</table>
		
		<br>
		<table class="transTableMiddle">
			<tr>
				<td width="12%"><label>Brand</label></td>
				<td width="20%"><input id="txtBrandCode" ondblclick="funHelp('BrandCode')" class="searchTextBox"  /></td>
				<td width="20%">Name &nbsp;<label id="txtName"></label></td>	
				<td width="12%">Strength &nbsp;<label id="txtStrength"></label></td>
				<td width="11%"> Size &nbsp;<label id="txtSize" style="width:50px !important"></label></td>
				<td width="12%">MRP &nbsp;<label id="txtMRP"></label></td>
				<td width="12%"><label id="txtAvailabelStk"></label></td>
			</tr>
			<tr>
				<td><label>Qty</label></td>
				<td><input type="text" id="txtBottals" class="BoxW116px positive-integer"/></td>
				
				<td><label>Batch No</label></td>
				<td><input type="text" id="txtBatch" class="BoxW116px" /></td>
				
				<td><label>Remark</label></td>
				<td><input type="text" id="txtRemark" class="BoxW116px" /></td>
				<td></td>
			</tr>
			<tr>
				<td>
<!-- 					<label>Tax</label> -->
				</td>
				<td><input type="hidden" id="txtTax" class="BoxW116px decimal-places" /></td>
				<td><input type="Button" value="Add" onclick="return btnAdd_onclick()" class="smallButton" /></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
						<tr>

				<td>
<!-- 					<label>Fees</label> -->
				</td>
				<td>
					<input type="hidden" id="txtFees" class="BoxW116px decimal-places" />
				</td>
				
				<td>
<!-- 					<label>Case Of Bts.</label> -->
				</td>
				<td>
					<input type="hidden" id="txtCases" class="BoxW116px positive" />
				</td>
			
				<td>
<!-- 					<label>Month Of Mfg</label> -->
				</td>
				<td>
					<input type="hidden" id="txtMfgDate" class="BoxW116px" />
				</td>
				<td></td>
			</tr>
		</table>
		<br>
		<div class="dynamicTableContainer" style=" overflow: visible !important;">
			<table style="height: 20px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">

				<tr bgcolor="#72BEFC">
					<td width="10%">Brand Code</td>
					<td width="12%">Brand Name</td>
					<td width="4%">Size</td>
					<td width="4%">MRP</td>
					<td width="3%">No.of Btl's</td>
					<td width="7%">Total</td>
					<td width="4%">Tax</td>
					<td width="4%">Fee</td>
					<td width="3%">Cases</td>
					<td width="5%">Batch No</td>
					<td width="5%">Mon Of Mfg.</td>
					<td width="10%">Remark</td>
					<td width="2%">Delete</td>
				</tr>
			</table>

			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 190px; margin: auto; width: 100%; overflow-y: scroll;">

				<table id="tblTPDet"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col13-center">
					<tbody>
					<col width="10%">
					<col style="width: 12%">
					<col style="width: 4%">
					<col style="width: 4%">
					<col style="width: 3%">
					<col style="width: 7%">
					<col style="width: 4%">
					<col style="width: 4%">
					<col style="width: 3%">
					<col style="width: 5%">
					<col style="width: 5%">
					<col style="width: 10%">
					<col style="width: 2%">

					</tbody>
				</table>
			</div>
		</div>
	<br/>
	<br/>
	
		<table class="transTable">
			<tr>
				<td>Total Purchase</td>
				<td>
					<s:input id="txtTotalPurchase" path="dblTotalPurchase" required="true"	
						readonly="true" cssClass="decimal-places-amt numeric" 
						cssStyle="width:60px !important" value="0.0" />
				</td>
				
				<td>Total Tax</td>
				<td>
					<s:input id="txtTotalTax" path="dblTotalTax" required="true" value="0.0"
						readonly="true" cssClass="decimal-places-amt numeric" cssStyle="width:60px !important" /> 
				</td>
				<td>Total Fee</td>
				<td>
					<s:input id="txtTotalFees" path="dblTotalFees" required="true" value="0.0"
						readonly="true" cssClass="decimal-places-amt numeric" cssStyle="width:60px !important" /> 
				</td>
				
			</tr>
			<tr>
			<td colspan="6"></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td>Total Bill</td>
				<td><s:input id="txtTotalBill" path="dblTotalBill" required="true"
						readonly="true" cssClass="decimal-places-amt numeric" /></td>
				<td></td>
				<td></td>
			</tr>

		</table>

		<br />
		<p align="center">
			<input type="submit" value="Submit" class="form_button" onclick="return funValidateFields();"/> 
			<input type="button" id="reset" value="Reset" class="form_button" />
		</p>
		<br />
		<br />

		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
	</s:form>

	<script type="text/javascript">
		funApplyNumberValidation();
	</script>
</body>
</html>