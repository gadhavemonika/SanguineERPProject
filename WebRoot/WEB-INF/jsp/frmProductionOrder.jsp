
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Production Order</title>

<script type="text/javascript">
	$(document).ready(function() {
		resetForms('productonorderform');
		$("#txtProdCode").focus();
	});
</script>
<script type="text/javascript">
	/**
	 * Global variable
	 */
	var fieldName,listRow=0;
	var flgstk;
	/**
	 * Ready Function for Initialize textField with default value
	 * And Set date in date picker 
	 */
	$(function() {
		$("#dtOPDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#dtOPDate" ).datepicker('setDate', 'today');
		$("#dtFulmtDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#dtFulmtDate" ).datepicker('setDate', 'today');
		$("#dtfulfilled").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#dtfulfilled" ).datepicker('setDate', 'today');
		 /**
		  * Ready Function for Ajax Waiting and reset form
		  */
		$(document).ajaxStart(function(){
		    $("#wait").css("display","block");
		  });
		  $(document).ajaxComplete(function(){
		    $("#wait").css("display","none");
		  });
	});

	 /**
	 * Open help windows
	 */
	function funHelp(transactionName) {
		fieldName = transactionName;

// 		window.showModalDialog("searchform.html?formname=" + transactionName
// 				+ "&searchText=", "",
// 				"dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")

		window.open("searchform.html?formname=" + transactionName
				+ "&searchText=", "",
				"dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	}
	/**
	 * Set Data after selecting form Help windows
	 */
	function funSetData(code) {
		var searchUrl = "";
		switch (fieldName) {
		case 'locationmaster':
			funSetLocation(code);
			break;
// 		case 'productInUse':
		case 'productProduced' :	
			funSetProduct(code);
			break;
		case 'ProductionOrder':
			funGetOPData(code);
			break;
		}
	}
	/**
	 * Set Location Data after selecting form Help windows
	 */
	function funSetLocation(code) {
		searchUrl = getContextPath() + "/loadLocationMasterData.html?locCode="
				+ code;
		//alert(searchUrl);
		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				if(response.strLocCode=='Invalid Code')
		       	{
		       		alert("Invalid Location Code");
		       		$("#strLocCode").val('');
		       		$("#strLocName").text("");
		       		$("#strLocCode").focus();
		       	}
		       	else
		       	{
				$("#strLocCode").val(response.strLocCode);
				$('#strLocName').text(response.strLocName);
				$("#strProdCode").focus();
		       	}
			},
			error: function(jqXHR, exception) {
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
	/**
	 * Set Product Data after selecting form Help windows
	 */
	function funSetProduct(code) {
		var searchUrl = "";
		searchUrl = getContextPath() + "/loadProductMasterData.html?prodCode="
				+ code;
		//alert(searchUrl);
		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				if('Invalid Code' == response.strProdCode){
		    		alert('Invalid Product Code');
			    	$("#strProdCode").val('');
			    	$("#strProductName").text('');
			    	$("#strProdCode").focus();
		    	}else{
				$("#strProdCode").val(response.strProdCode);
				$("#strProductName").text(response.strProdName);
				$("#dblUnitPrice").val(response.dblCostRM);
				$("#dblWeight").val(response.dblWeight);
				var dblStock=funGetProductStock(response.strProdCode);
				$('#lblStock').text(dblStock);
				$("#dblQty").focus();
		    	}
			},
			error: function(jqXHR, exception) {
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

	/**
	 * Get and Set Production Order Hd Data after selecting form Help windows
	 */
	function funGetOPData(code) {
		searchUrl = getContextPath() + "/loadOPData.html?OPCode=" + code;
		//alert(searchUrl);
		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				funFillData(response);
			},
			error: function(jqXHR, exception) {
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

	/**
	 * Fill Production Order Data
	 */
	function funFillData(response) {
		$.each(response, function(i, item) { 
			if(response[i].strOPCode == 'Invalid Code'){
				alert('Invalid Code');
				$("#strOPCode").val('');
				$("#strOPCode").focus();
			}else{
				$("#strOPCode").val(response[i].strOPCode);
				$("#dtOPDate").val(response[i].dtOPDate);
				$("#dtFulmtDate").val(response[i].dtFulmtDate);
				$("#dtfulfilled").val(response[i].dtfulfilled);
				$("#strLocCode").val(response[i].strLocCode);
				$("#strLocName").text(response[i].strLocName);
				$("#cmbAgainst").val(response[i].strAgainst);
				$("#txtDocCode").val(response[i].strCode);
				$("#strNarration").val(response[i].strNarration);
				$("#btnAdd").focus();
				funGetProdData(response[i].strOPCode);
			}
		});
	}

	/**
	 * Get and Set Production Order Dtl Data after selecting form Help windows
	 */
	function funGetProdData(code) {
		searchUrl = getContextPath() + "/loadOPDtlData.html?OPCode=" + code;
		//alert(searchUrl);
		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				funRemRows();
				var count=0;
				$.each(response, function(i, item) {
					count=i;
					funfillProdRow(response[i].strProdCode,
							response[i].strProdName, response[i].dblUnitPrice,
							response[i].dblQty, response[i].dblWeight,
							response[i].strSpCode);

				});
				listRow=count+1;
			},
			error: function(jqXHR, exception) {
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

	/**
	 * Filling Grid
	 */
	function funfillProdRow(strProdCode, strProdName, dblUnitPrice, dblOrdQty,
			dblWeight, strSpCode) {
		var table = document.getElementById("tblProdDet");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		var dblStock="";
		var dblAcceptQty ="";
		if(flgstk=="Y")
			{
				dblStock=funGetProductStock(strProdCode);
			}else
				{
					dblAcceptQty =dblOrdQty;
				}
		dblUnitPrice=parseFloat(dblUnitPrice).toFixed(maxAmountDecimalPlaceLimit);
		dblOrdQty=parseFloat(dblOrdQty).toFixed(maxQuantityDecimalPlaceLimit);
		dblWeight=parseFloat(dblWeight).toFixed(maxQuantityDecimalPlaceLimit);
		
		if(dblOrdQty<1)
			{
			return false;
			}
		
		if(flgstk=="Y")
		{
			dblAcceptQty = parseFloat(dblStock).toFixed(maxQuantityDecimalPlaceLimit)-dblOrdQty;
		}
		if(dblAcceptQty<1 )
			{
				return false;
			}else 
				{
					dblAcceptQty = 1*parseFloat(dblAcceptQty).toFixed(maxQuantityDecimalPlaceLimit);
				}
		
		var dblTotalPrice = parseFloat(dblAcceptQty).toFixed(maxQuantityDecimalPlaceLimit) * dblUnitPrice;
		dblTotalPrice=parseFloat(dblTotalPrice).toFixed(maxAmountDecimalPlaceLimit);
		
// 		$('#tblProdDet tr').each(function()
// 		{
// 			var pCode=$(this).find('input').val();
// 			//alert(strProdCode+"         "+$(this).find('input').val());
// 			if(strProdCode==pCode)
//     		{
// 		    	 var preQty = $(this).find(".dblQty").val();
// 		    	 var preTotalPrice = $(this).find(".dblTotalPrice").val();
// 		    	 dblQty+=parseFloat(preQty);
// 		    	 dblTotalPrice+=parseFloat(preTotalPrice);
// 		    	 //$(this).find(".dblQty").val(dblQty);
// 		    	 //$(this).find(".dblTotalPrice").val(dblTotalPrice);
// 		    	 funDeleteRow(this);
//     		}
			
// 		});
		
		
		
		row.insertCell(0).innerHTML = "<input  readonly=\"readonly\" class=\"Box\" size=\"8%\" name=\"listclsProductionOrderDtlModel["
				+ (rowCount)
				+ "].strProdCode\" id=\"txtProdCode."
				+ (rowCount)
				+ "\" value='" + strProdCode + "' >";
		row.insertCell(1).innerHTML = strProdName;
// 		row.insertCell(2).innerHTML = "<input type=\"text\"  required = \"required\" style=\"text-align: right;width:100%\" name=\"listclsProductionOrderDtlModel["
// 				+ (rowCount)
// 				+ "].dblQty\"  id=\"dblQty."
// 				+ (rowCount)
// 				+ "\" value="
// 				+ dblQty
// 				+ " onblur=\"funUpdatePrice(this);\" class=\"decimal-places inputText-Auto dblQty\">";

		row.insertCell(2).innerHTML = "<input readonly=\"readonly\" class=\"Box\"    id=\"dblOrdQty."	+ (rowCount)+ "\" value="+ dblOrdQty+ " >";
				
		row.insertCell(3).innerHTML = "<input readonly=\"readonly\" class=\"Box\"    id=\"dblStock."	+ (rowCount)+ "\" value="+ dblStock+ " >";
				
		row.insertCell(4).innerHTML = "<input type=\"text\"  required = \"required\" style=\"text-align: right;width:100%\" name=\"listclsProductionOrderDtlModel["+ (rowCount)+ "].dblQty\" id=\"dblQty."	+ (rowCount)+ "\" value="+ dblAcceptQty+ " onblur=\"funUpdatePrice(this);\" class=\"decimal-places inputText-Auto\">";		
			

		
		row.insertCell(5).innerHTML = "<input type=\"text\"  required = \"required\" style=\"text-align: right;width:100%\" name=\"listclsProductionOrderDtlModel["
				+ (rowCount)
				+ "].dblWeight\" class=\"decimal-places inputText-Auto\" id=\"dblWeight."
				+ (rowCount)
				+ "\" value=" + dblWeight + " >";
		row.insertCell(6).innerHTML = "<input type=\"text\"  readonly=\"readonly\" style=\"text-align: right;width:100%\" name=\"listclsProductionOrderDtlModel[" + (rowCount) + "].dblUnitPrice\" id=\"dblUnitPrice."	+ (rowCount)+ "\" value="+ dblUnitPrice+ " class=\"decimal-places-amt inputText-Auto\">";
		//row.insertCell(5).innerHTML = "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" name=\"listclsProductionOrderDtlModel["+ (rowCount) + "].dblTotalPrice\" id=\"dblTotalPrice."	+ (rowCount)+ "\" class=\"dblTotalPrice\ value="+ dblTotalPrice+ ">";
		row.insertCell(7).innerHTML = "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\"  id=\"dblTotalPrice."	+ (rowCount)+ "\" value="+ dblTotalPrice+ " class=\"decimal-places-amt inputText-Auto\" >";
		
		row.insertCell(8).innerHTML = "<input readonly=\"readonly\"  name=\"listclsProductionOrderDtlModel["
				+ (rowCount)
				+ "].strSpCode\" id=\"strSpCode."+ (rowCount)+ "\" value='"+ strSpCode + ">";
		row.insertCell(9).innerHTML = '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
		 funApplyNumberValidation();
	}
	
	/**
	 * Remove all product from grid
	 */
	function funRemRows() {
		var table = document.getElementById("tblProdDet");
		var rowCount = table.rows.length;

		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	
	/**
	 * Update total price when user change the qty 
	 */
	function funUpdatePrice(object) {
		var index = object.parentNode.parentNode.rowIndex - 1;
		var price = parseFloat(document.getElementById("dblUnitPrice." + index).value)
				* parseFloat(object.value);
		price=parseFloat(price).toFixed(maxAmountDecimalPlaceLimit);
		document.getElementById("dblTotalPrice." + index).value = price;

	}
	/**
	 * Clear textfiled after adding data in textfield
	 */
	function funClearProduct() {
		$("#strProdCode").val("");
		$("#strProdName").text("");
		$("#dblQty").val("");
		$("#dblWeight").val("");
		$("#dblUnitPrice").val("");
		document.all("strProdCode").focus();
	}
	/**
	 * Check validation before adding product data in grid
	 */
	function btnAdd_onclick() {
		if (document.all("strProdCode").value != "") {
			if (document.all("strProdCode").value != ""
					&& document.all("dblQty").value != 0) {
				funAddProductRow();
				funClearProduct();
			} else {
				alert("Please Enter Quantity");
				document.all("dblQty").focus();
				return false;
			}
		} else {
			alert("Please Enter Product Code or Search");
			document.all("strProdCode").focus();
			return false;
		}
	}
	/**
	 * Adding Product Data in grid 
	 */
	function funAddProductRow() {

		var strProdCode = $("#strProdCode").val();
		var strProdName = $("#strProductName").text();
		var dblStock = $("#lblStock").text();
		var dblOrdQty = $("#dblQty").val();
		dblOrdQty= parseFloat(dblOrdQty).toFixed(maxQuantityDecimalPlaceLimit);
		var dblAcceptQty = dblOrdQty - parseFloat(dblStock).toFixed(maxQuantityDecimalPlaceLimit);
		var dblWeight = $("#dblWeight").val();
		dblWeight= parseFloat(dblWeight).toFixed(maxQuantityDecimalPlaceLimit);
		var dblUnitPrice = $("#dblUnitPrice").val();
		dblUnitPrice= parseFloat(dblUnitPrice).toFixed(maxAmountDecimalPlaceLimit);
		var amount = (dblUnitPrice * dblAcceptQty);
		amount= parseFloat(amount).toFixed(maxAmountDecimalPlaceLimit);
		var strSpCode = "NA";
		var table = document.getElementById("tblProdDet");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		rowCount=listRow;
		row.insertCell(0).innerHTML = "<input  readonly=\"readonly\" class=\"Box\" size=\"8%\" name=\"listclsProductionOrderDtlModel["	+ (rowCount) + "].strProdCode\" id=\"strProdCode."	+ (rowCount) + "\" value=" + strProdCode + " >";
		row.insertCell(1).innerHTML = strProdName;
		row.insertCell(2).innerHTML = "<input readonly=\"readonly\" class=\"Box\"    id=\"dblOrdQty."	+ (rowCount)+ "\" value="+ dblOrdQty+ " >";
				
		row.insertCell(3).innerHTML = "<input readonly=\"readonly\" class=\"Box\"    id=\"dblStock."	+ (rowCount)+ "\" value="+ dblStock+ " >";
				
		row.insertCell(4).innerHTML = "<input type=\"text\"  required = \"required\" style=\"text-align: right;width:100%\" name=\"listclsProductionOrderDtlModel["+ (rowCount)+ "].dblQty\" id=\"dblQty."	+ (rowCount)+ "\" value="+ dblAcceptQty+ " onblur=\"funUpdatePrice(this);\" class=\"decimal-places inputText-Auto\">";		
				
		row.insertCell(5).innerHTML = "<input type=\"text\"  required = \"required\" style=\"text-align: right;width:100%\" name=\"listclsProductionOrderDtlModel["
				+ (rowCount)
				+ "].dblWeight\" class=\"decimal-places inputText-Auto\" id=\"dblWeight."
				+ (rowCount)
				+ "\" value=" + dblWeight + ">";
		row.insertCell(6).innerHTML = "<input type=\"text\"  readonly=\"readonly\" style=\"text-align: right;width:100%\" name=\"listclsProductionOrderDtlModel["
				+ (rowCount)
				+ "].dblUnitPrice\" id=\"dblUnitPrice."
				+ (rowCount)
				+ "\" value="
				+ dblUnitPrice
				+ " class=\"decimal-places-amt inputText-Auto\">";
		row.insertCell(7).innerHTML = amount;
		row.insertCell(8).innerHTML = "<input readonly=\"readonly\" class=\"Box\" name=\"listclsProductionOrderDtlModel["
				+ (rowCount)
				+ "].strSpCode\" id=\"strSpCode."
				+ (rowCount)
				+ "\" value="+ strSpCode+">";
		row.insertCell(9).innerHTML = '<input type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
		listRow++;
		funApplyNumberValidation();
		
		return false;
	}
	/**
	 * Delete a particular record from a grid
	 */
	function funDeleteRow(obj) {
		var index = obj.parentNode.parentNode.rowIndex;
		var table = document.getElementById("tblProdDet");
		table.deleteRow(index);
	}
	
	/**
	 * Checking Validation before submiting the data
	 */
	function funCallFormAction(actionName, object) {
		if (!fun_isDate($("#dtOPDate").val())) {
			alert('Invalid Date');
			$("#dtOPDate").focus();
			return false;
		}
		if (!fun_isDate($("#dtFulmtDate").val())) {
			alert('Invalid Date');
			$("#dtFulmtDate").focus();
			return false;
		}
		if (!fun_isDate($("#dtfulfilled").val())) {
			alert('Invalid Date');
			$("#dtfulfilled").focus();
			return false;
		}
		if ($("#strLocCode").val() == '') {
			alert("Enter Location or Search");
			$("#strLocCode").focus();
			return false;
		}
	
		var table = document.getElementById("tblProdDet");
		var rowCount = table.rows.length;
		if(rowCount == 0){
			alert('Please Select Products');
			return false;
		}
		else {
			return true;
			/* if (actionName == 'submit') {
				document.forms[0].action = "saveProductionOrderData.html";
			} */
		}
	}
	
	/**
	 *  Ready function for Textfield on blur event
	 */
	$(function(){
		$('#strOPCode').blur(function () {			  
				   var code=$('#strOPCode').val();
				   if (code.trim().length > 0 && code !="?" && code !="/") {
					   funGetOPData(code);
			   }
			});
		
		$('#strLocCode').blur(function () {
			 var code=$('#strLocCode').val();  
			 if (code.trim().length > 0 && code !="?" && code !="/") {
					   funSetLocation(code);
				   }
			});
		
		$('#strProdCode').blur(function () {
				   var code=$('#strProdCode').val();
				   if (code.trim().length > 0 && code !="?" && code !="/") {
					   funSetProduct(code);
				   }
			});
			
		});
	
	/**
	 * Apply number text filed validation
	 */
	function funApplyNumberValidation(){
		$(".numeric").numeric();
		$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
		$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
		$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
	    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
	    $(".decimal-places-amt").numeric({ decimalPlaces: maxAmountDecimalPlaceLimit, negative: false });
	}
	/**
	 * Ready Function 
	 * And Getting session Value
	 * Success Message after Submit the Form
	 * open slip
	 */
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
			alert("Data Save successfully\n\n"+message);
		<%
		}}%>
		/**
		 * Checking Authorization
		 */
		var flagOpenFromAuthorization="${flagOpenFromAuthorization}";
		if(flagOpenFromAuthorization == 'true')
		{
			funGetOPData("${authorizationProdOrderCode}");
		}
		
		var code='';
		<%if(null!=session.getAttribute("rptOPCode")){%>
		code='<%=session.getAttribute("rptOPCode").toString()%>';
		<%session.removeAttribute("rptOPCode");%>
		var isOk=confirm("Do You Want to Generate Slip?");
		var dtFullfilled = $('#dtfulfilled').val();
		if(isOk)
			{ 
			window.open(getContextPath()+"/openProductionOrderSlip.html?rptOPCode="+code,'_blank');
			window.open(getContextPath()+"/openProductionCompilationSlip.html?rptOPCode="+code,'_blank');
			window.open(getContextPath()+"/openCustomerWiseCategoryWiseSO.html?rptOPCode="+code,'_blank');
			window.open(getContextPath()+"/openLocationwiseCategorywiseSOReport.html?rptOPCode="+code,'_blank');
			}
		
// 		if(code.length>12)
// 			{
			
// 			var codes = code.split(",");
// 			for(i=0 ;i<codes.length;i++)
// 				{
// 					if(isOk){
// 					window.open(getContextPath()+"/openProductionOrderSlip.html?rptOPCode="+codes[i],'_blank');
// 					}
// 				}
			
// 			}else
// 				{
// 				if(isOk){
// 					window.open(getContextPath()+"/openProductionOrderSlip.html?rptOPCode="+code,'_blank');
// 					}
// 				}
		
		
		
		
		<%}%>
	 });
	function funResetField()
	{
		location.reload(true);
	}
	
	
	//Combo Box Change then set value
	function funOnChange() {
		if ($("#cmbAgainst").val() == "Direct")
		{
			$("#txtDocCode").css('visibility', 'hidden');
		}
		else 
		{
			$("#txtDocCode").css('visibility', 'visible');
		}
		
	}
	
	
	//Open Against Form
	function funOpenAgainst() {
		if ($("#cmbAgainst").val() == "Sales Order") {
			{
				var dtFullfilled = $('#dtfulfilled').val();
				var locCode = $('#strLocCode').val();
				funOpenPOHelp(locCode,dtFullfilled);
				return false;
			}
	}
	}
		
		//Open Against PO From and Set PO Code in combo box
		function funOpenPOHelp(locCode,dtFullfilled) {
// 			var retval = window.showModalDialog(
// 					"frmPOSales.html?strlocCode="+locCode+"&dtFullFilled="+dtFullfilled,
// 					"dialogHeight:600px;dialogWidth:500px;dialogLeft:400px;")
			var retval = window.open(
					"frmPOSales.html?strlocCode="+locCode+"&dtFullFilled="+dtFullfilled,"",
					"dialogHeight:600px;dialogWidth:500px;dialogLeft:400px;");
			
			var timer = setInterval(function ()
				    {
					if(retval.closed)
						{
							if (retval.returnValue != null)
							{
								strVal = retval.returnValue.split("#")
								$("#txtDocCode").val(strVal[0]);
								funRemRows();
								funSetSalesOrder();
								
								var SOCodes=strVal[0].split(",");
			
							}
							clearInterval(timer);
						}
				    }, 500);
			
			
// 			if (retval != null)
// 			{
// 				strVal = retval.split("#")
// 				$("#txtDocCode").val(strVal[0]);
// 				funRemRows();
// 				funSetSalesOrder();
				
// 				var SOCodes=strVal[0].split(",");

// 			}
		}
		/*-----------------------------------Start For Child Product Shown in Grid----------------------------------- */		
		
// 		function funSetSalesOrder()
// 		{

// 		    strCodes = $('#txtDocCode').val();
// 		    strSOCodes = strCodes.split(",")

// 		    	var searchUrl=getContextPath()+ "/loadAgainstSO.html?SOCode=" + strCodes ;
// 				$.ajax({
// 				        type: "GET",
// 				        url: searchUrl,
// 					    dataType: "json",
// 					    success: function(response)
// 					    {	
					    	
// 					    	$.each(response, function(cnt,item)
// 							{
					    		
// 					    		funfillProdRow(item[0],item[1],item[4],item[2]
// 					    		,item[3],"");
// 							});
					    	

// 					    },
// 					    error: function(jqXHR, exception) {
// 				            if (jqXHR.status === 0) {
// 				                alert('Not connect.n Verify Network.');
// 				            } else if (jqXHR.status == 404) {
// 				                alert('Requested page not found. [404]');
// 				            } else if (jqXHR.status == 500) {
// 				                alert('Internal Server Error [500].');
// 				            } else if (exception === 'parsererror') {
// 				                alert('Requested JSON parse failed.');
// 				            } else if (exception === 'timeout') {
// 				                alert('Time out error.');
// 				            } else if (exception === 'abort') {
// 				                alert('Ajax request aborted.');
// 				            } else {
// 				                alert('Uncaught Error.n' + jqXHR.responseText);
// 				            }		            
// 				        }
// 				      });
// 		    }

/*-----------------------------------End For Child Product Shown in Grid----------------------------------- */

		function funSetSalesOrder()
		{

		    strCodes = $('#txtDocCode').val();
		    strSOCodes = strCodes.split(",")

		    	var searchUrl=getContextPath()+ "/loadAgainstSO.html?SOCode=" + strCodes ;
				$.ajax({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {	
					    	
					    	$.each(response, function(cnt,item)
							{
// 								if(funDuplicateProduct(item));
// 									{
					    		
					    				funfillProdRow(item.strProdCode,item.strProdName,item.dblUnitPrice,item.dblQty
							    		,item.dblWeight,"");
// 									}
							});
					    	

					    },
					    error: function(jqXHR, exception) {
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
		
		
		function funDuplicateProduct(item)
		{
			var strProdCode =item.strProdCode;
			var qty =item.dblQty;
		    var table = document.getElementById("tblProdDet");
		    var rowCount = table.rows.length;		   
		    var flgDProduct=true;
		    if(rowCount > 0)
		    	{
				    $('#tblProdDet tr').each(function()
				    {
					    if(strProdCode==$(this).find('input').val())// `this` is TR DOM element
	    				{
					    	//alert("Already added "+ strProdCode);
					    	 var preQty = $(this).find(".dblQty").val();
					    	
					    	qty=parseFloat(qty)+parseFloat(preQty);
					    	$(this).find(".dblQty").val(qty);

		    				flgDProduct=false;
	    				}

					});

		    	}

		    return flgDProduct;
		  
		}

	

		/**
		 * Get product stock passing value product code
		 */
		function funGetProductStock(strProdCode)
		{
			var searchUrl="";	
			var locCode=$("#strLocCode").val();
			var dblStock="0";
			searchUrl=getContextPath()+"/getProductStock.html?prodCode="+strProdCode+"&locCode="+locCode;	
			//alert(searchUrl);		
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    async: false,
				    success: function(response)
				    {
				    	dblStock= response;
				    },
				    error: function(jqXHR, exception) {
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
			return Math.round(dblStock * 100) / 100;
		}
		
		
		/**
		 * Open Autogenerate Requisition data 
		 */
		function funOpenAutoGeneratedProductionOrder()			
		{
			$("#cmbAgainst").val('Direct');
			$("#txtDocCode").css('visibility', 'hidden');
	      //  response=window.showModalDialog("frmAutoGenProductionOrder.html","","dialogHeight:800px;dialogWidth:850px;dialogLeft:250px;");
	        response=window.open("frmAutoGenProductionOrder.html","","dialogHeight:800px;dialogWidth:850px;dialogLeft:250px;");
	        var count=0;
	        if(response!=null && response!="null")
	        	{
	        	funRemRows();	
			        $.each(response, function(i,item)
		               { //alert(response[i].strUOM);
						// funfillProdRow(response[i].strProdCode,response[i].strProdName,response[i].dblQty,response[i].dblPrice,response[i].dblOrderQty*response[i].dblPrice,"",response[i].strUOM);                
						flgstk= "N";
						 funfillProdRow(response[i].strProdCode,response[i].strProdName, response[i].dblUnitPrice,response[i].dblQty, response[i].dblWeight,"");
		               
		               });
			        listRow=count+1;
		        	
	        	}
		}
		
		
		
		
		
	
	
</script>
</head>
<body onload="funOnLoad();">
	<div id="formHeading">
<!-- 		<label>Meal Planning</label> -->
<label>Production Order</label>
	</div>

	<s:form method="POST" action="saveProductionOrderData.html?saddr=${urlHits}"
		name="productonorderform">
		<br>
		
		<table class="transTable">
			<tr>
				<th align="right"><a onclick="funOpenAutoGeneratedProductionOrder();"
					href="javascript:void(0);">Auto Generate</a>&nbsp; &nbsp; &nbsp;
					&nbsp; &nbsp;</th>
				<%-- <td align="right"><span> <a id="baseUrl" href="attachDoc.html?ReqCode="> Attatch Documents </a> </span></td> --%>
			</tr>
		</table>
		
		
		<table class="transTable">
			<tr>
				<th colspan="6" align="right">&nbsp; &nbsp; &nbsp; &nbsp;</th>
			</tr>
			<tr>
				<td width="150px"><label>Production Order Code</label></td>
				<td width="150px"><s:input path="strOPCode" id="strOPCode"
						ondblclick="funHelp('ProductionOrder')" cssClass="searchTextBox" /></td>
				<td width="100px"><label>Date</label></td>
				<td width="150px"><s:input path="dtOPDate" id="dtOPDate" required="true"
						autocomplete="false" cssClass="calenderTextBox" /></td>
				<td width="100px"><label>Status </label></td>
				<td><s:label path="strStatus"></s:label></td>
			</tr>


			<tr>
				<td><label>Against</label></td>
				<td ><s:select id="cmbAgainst" items="${strProcessList}" 
						onchange="funOnChange();" name="cmbAgainst"  cssClass="BoxW124px" path="strAgainst">
						</s:select>
						</td>
				<td colspan="4"><s:input id="txtDocCode" path="strDocCode" readonly="readonly" 
				ondblclick="funOpenAgainst()" class="searchTextBox"></s:input></td>		
						
<%-- 				<s:select path="strAgainst" id="cmbAgainst" --%>
<%-- 						cssClass="BoxW124px"> --%>
<%-- 						<s:option value="Direct">Direct</s:option> --%>
<%-- 						<s:option value="Sales Projection">Sales Projection</s:option> --%>
<%-- 					</s:select></td> --%>
			</tr>

			<tr>
				<td><label>Fullfillment Date</label></td>
				<td><s:input path="dtFulmtDate" id="dtFulmtDate" required="true"
						cssClass="calenderTextBox" /></td>
				<td><label>Fullfilled Date</label></td>
				<td colspan="3"><s:input path="dtfulfilled" id="dtfulfilled" required="true"
						cssClass="calenderTextBox" /></td>
			</tr>


			<tr>
				<td><label>Location </label></td>
				<td><s:input path="strLocCode" id="strLocCode" required="true"
						value="${locationCode}" ondblclick="funHelp('locationmaster')"
						cssClass="searchTextBox" /></td>
				<td colspan="4"><label id="strLocName" class="namelabel">${locationName}</label>
			</tr>


		</table>
		<table class="transTableMiddle">
			<tr>
				<td width="150px"><label>Product</label></td>
				<td width="150px"><input id="strProdCode" 
						ondblclick="funHelp('productProduced')" 
					class="searchTextBox" /></td>
				<td colspan="3"><label id="strProductName" class="namelabel" style="font-size: 12px;"></label></td>
				<td><label id="Stock" class="namelabel"></label>Stock</td>
				<td colspan="2"><label id="lblStock" class="namelabel"></label></td>

			</tr>
			<tr>
				<td><label>Quantity</label></td>
				<td><input id="dblQty" type="text" 
					class="decimal-places numberField"></td>
				<td><label>Unit Price</label></td>
				<td width="120px"><input id="dblUnitPrice" type="text"
					  class="decimal-places-amt numberField"></td>
				<td width="100px"><label>Wt/Unit</label></td>
				<td width="120px"><input id="dblWeight" type="text"
					 class="decimal-places numberField"></td>
				<td><input type="button" value="Add"
					onclick="return btnAdd_onclick()" class="smallButton" id="btnAdd"/></td>
			</tr>
		</table>

		<div class="dynamicTableContainer" style="height: 300px;">
			<table
				style="height: 20px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="6%">Product Code</td>
					<!--  COl1   -->
					<td width="14%">Product Name</td>
					<!--  COl2   -->
					<td width="6%" align="right">Order Qty</td>
					<!--  COl3   -->
					<td width="6%" align="right">Stock</td>
					<!--  COl4   -->
					<td width="6%" align="right">Accepted Qty</td>
					<!--  COl5   -->
					<td width="6%" align="right">Wt/Unit</td>
					<!--  COl6   -->
					<td width="6%" align="right">Unit Price</td>
					<!--  COl7   -->
					<td width="6%" align="right">Total Price</td>
					<!--  COl8   -->
					<td width="6%">SP Code</td>
					<!--  COl9   -->
					<td width="5%">Delete</td>
					<!--  COl10   -->
				</tr>
			</table>

			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">


				<table id="tblProdDet"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col6-right col8-center">
					<tbody>
					<col style="width: 6%">
					<!--  COl1   -->
					<col style="width: 14%">
					<!--  COl2   -->
					<col style="width: 6%">
					<!--  COl3   -->
					<col style="width: 6%">
					<!--  COl4   -->
					<col style="width: 6%">
					<!--  COl5   -->
					<col style="width: 6%">
					<!--  COl6   -->
					<col style="width: 6%">
					<!--  COl7   -->
					<col style="width: 6%">
					<!--  COl8   -->
					<col style="width: 6%">
					<!--  COl9   -->
					<col style="width: 4.2%">
					<!--  COl10   -->

				

					</tbody>
				</table>
			</div>
		</div>

	
		<table class="transTableMiddle1">
			<tr>
				<td><label>Narration</label></td>
				<td><s:textarea path="strNarration" id="strNarration" /></td>
			</tr>

		</table>
		<br>
		<p align="center">
			<input type="submit" value="Submit"
				onclick="return funCallFormAction('submit',this)"
				class="form_button" /> &nbsp; &nbsp; &nbsp; <input type="reset"
				value="Reset" class="form_button" onclick="funResetField()" />
		</p>
		<!-- 	<input type="submit" value="Submit" onclick="return funCallFormAction('submit',this)"/>
		<input type="reset" value="Reset" /> -->
		<br>
		<br>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
		
	</s:form>
</body>
</html>