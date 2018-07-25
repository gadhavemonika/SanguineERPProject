<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="sp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<script type="text/javascript">
	/**
	 * Ready Function for Initialization Text Field with default value 
	 * Set Date in date picker
	 */
	$(function()
		{
			$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtFromDate").datepicker('setDate','today');
			$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtToDate").datepicker('setDate', 'today');
		});
	/**
	 * Open help form
	 */
	var fieldName="";
	function funHelp(transactionName)
	{
		fieldName = transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
	}
	
	 /**
	 * Set Data after selecting form Help windows
	 */
	function funSetData(code)
	{
		switch (fieldName) 
		{
		    case 'locationmaster':
		    	funSetLocation(code);
		        break;
		}
	}
	 /**
	 * Set Location data passing value location code
	 */
	function funSetLocation(code) {
		var searchUrl = "";
		searchUrl = getContextPath()
				+ "/loadLocationMasterData.html?locCode=" + code;
	
		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				if (response.strLocCode == 'Invalid Code') {
					alert("Invalid Location Code");
					$("#txtLocCode").val('');
					$("#lblLocName").text("");
					$("#txtLocCode").focus();
				} else {
					$("#txtLocCode").val(response.strLocCode);						
					$("#lblLocName").text(response.strLocName);
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
	 
	 /**
	 * Ready function when execute button clicked or load Stock variance Data
	 */
	$(document).ready(function() 
			{
				$("#btnExecute").click(function(){
				var fromDate=$("#txtFromDate").val();
				var toDate=$("#txtToDate").val();
				var locCode=$("#txtLocCode").val();
				var param1=locCode+","+fromDate+","+toDate;
				var searchUrl=getContextPath()+"/loadStkVarianceFlashData.html?param1="+param1;
				$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funDeleteTableAllRows()
						$.each(response, function(i,item)
						{
						  funAddRow(response[i][0],response[i][1],response[i][2],response[i][3],response[i][4],response[i][5],response[i][6],response[i][7]);
						});
				    		
				    	funGetTotalVariance();
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
		
		});
		});
		
	 /**
	 * filling Records in grid
	 */
	function funAddRow(strSGName,strProdCode,strProdName,CStkQty,phystkQty,variance,UnitPrice,value)
	{
		var ProductData=fungetConversionUOM(strProdCode);
		var ConversionValue=ProductData.dblRecipeConversion;
		var ReceivedconversionUOM=ProductData.strReceivedUOM;
		var issuedconversionUOM=ProductData.strIssueUOM;
		var recipeconversionUOM=ProductData.strRecipeUOM;
		CStkQty=parseFloat(CStkQty).toFixed(maxQuantityDecimalPlaceLimit);
	    var tempStkQty=CStkQty.split(".");
	    DiscurrentStkQty=tempStkQty[0]+" "+ReceivedconversionUOM+"."+parseFloat("0."+tempStkQty[1])*parseFloat(ConversionValue)+" "+recipeconversionUOM;
	    phystkQty=parseFloat(phystkQty).toFixed(maxQuantityDecimalPlaceLimit);
		var tempQty=phystkQty.split(".");
		var Displyqty=tempQty[0]+" "+ReceivedconversionUOM+"."+Math.round(parseFloat("0."+tempQty[1])*parseFloat(ConversionValue))+" "+recipeconversionUOM;
		
		variance=parseFloat(variance).toFixed(maxQuantityDecimalPlaceLimit);
		var tempvariance=variance.split(".");
		var DisplayVariance=tempvariance[0]+" "+ReceivedconversionUOM+"."+parseFloat("0."+tempvariance[1])*parseFloat(ConversionValue)+" "+recipeconversionUOM;
	    var table = document.getElementById("tblProdDet");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\"  id=\"txtSGCode."+(rowCount)+"\" value='"+strSGName+"' />";
// 	    row.insertCell(1).innerHTML= "<input type=\"hidden\" id=\"txtPOSItemCode."+(rowCount)+"\" value='"+strProdCode+"' />";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\"  id=\"txtPOSItemName."+(rowCount)+"\" value='"+strProdName+"' />";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" style=\"text-align:right\" class=\"Box\" size=\"8%\"  id=\"txtWSItemCode."+(rowCount)+"\" value='"+DiscurrentStkQty+"' />";
	    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" style=\"text-align:right\" class=\"Box\" size=\"8%\"  id=\"txtWSItemCode."+(rowCount)+"\" value='"+Displyqty+"' />";
	    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" style=\"text-align:right\" class=\"Box\" size=\"8%\"  id=\"txtWSItemName."+(rowCount)+"\" value='"+DisplayVariance+"' />";
	    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" style=\"text-align:right\" class=\"Box\" size=\"8%\"  id=\"strWSItemType."+(rowCount)+"\" value='"+parseFloat(UnitPrice).toFixed(maxAmountDecimalPlaceLimit)+"' />";
	    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" style=\"text-align:right\" class=\"Box totalVarCell\" size=\"8%\"  id=\"txtValue."+(rowCount)+"\" value='"+parseFloat(value).toFixed(maxAmountDecimalPlaceLimit)+"' />";
	}
	 /**
	 * Remove all rcords from grid
	 */
	function funDeleteTableAllRows()
	{
		$("#tblProdDet tr").remove();
	}
	 /**
	 * Calculate total variance
	 */
	function funGetTotalVariance()
	{
		var totalVariance=0.00;
		var table = document.getElementById("tblProdDet");
		var rowCount = table.rows.length;
		for(i=0;i<rowCount;i++)
		{
		    var temptotalVariance =  document.getElementById("txtValue."+i).value;
		    totalVariance=parseFloat(totalVariance)+parseFloat(temptotalVariance);
		 };
		totalVariance=parseFloat(totalVariance).toFixed(maxAmountDecimalPlaceLimit);
		$("#txtTotVar").val(totalVariance);
	}
	
	 /**
	 * On blur event in textfield
	 */
	$(function()
		{
			$('#txtLocCode').blur(function () 
			{
				var code=$('#txtLocCode').val();
				if (code.trim().length > 0 && code !="?" && code !="/")
				{							   
					funSetLocation(code);
					 
				 }
			});
			 /**
			 * Export to excel
			 */
			 $("#btnExport").click(function (e)
					{
						var fromDate=$("#txtFromDate").val();
						var toDate=$("#txtToDate").val();
						var locCode=$("#txtLocCode").val();
						var param1=locCode+","+fromDate+","+toDate;
						var reportType=$("#cmbExportType").val();
						
						if(reportType=="Excel"){
						window.location.href=getContextPath()+"/ExportExcelStkVariance.html?param1="+param1;
						}
						else{
							window.location.href=getContextPath()+"/rptStkVarianceFlashReport.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate;
						}
					}); 
			
			
		});
	 /**
	 * Get conversion Ratio from product master
	 */
	function fungetConversionUOM(code)
	{
		var searchUrl="";
		var ProductData;
		searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
		$.ajax
		({
	        type: "GET",
	        url: searchUrl,
		    dataType: "json",
		    async: false,
		    success: function(response)
		    {
		    	ProductData=response;
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
		return ProductData;
	}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock Variance Flash</title>
</head>
<body>
<div id="formHeading">
		<label>Stock Variance Flash</label>
	</div>
	<s:form name="stkVarfls" method="POST" action="">	
		<br>
		<table class="transTable">
			<tr>
				<th colspan="7"></th>
			<tr>
				<td width="10%"><label id="lblFromDate">From Date</label></td>
				<td width="25%"><s:input id="txtFromDate" name="fromDate" path="dtFromDate" title="Select From Date"
				cssClass="calenderTextBox" /> <s:errors path="dtFromDate"></s:errors>
				</td>

				<td width="10%"><label id="lblToDate">To Date</label></td>
				<td><s:input id="txtToDate" name="toDate" path="dtToDate" title="Select To Date"
						cssClass="calenderTextBox" /> <s:errors path="dtToDate"></s:errors>
				</td>
				
			</tr>
			<tr>
				<td width="5%"><label>Location</label></td>
				<td colspan="3">
				 <s:input id="txtLocCode" path="strLocationCode" placeholder="ALL Location" title="Double Click To Select Location"
				  ondblclick="funHelp('locationmaster')" cssClass="searchTextBox"/>
				  <label id="lblLocName"></label>
			   </td>
			</tr>
			</table>
			<table class="transTable">
			<tr></tr>
			<tr>
					<td width="4%"><input id="btnExecute" type="button" class="form_button1" title="Execute" value="EXECUTE"/></td>
					<td width="10%">
						<s:select path="strExportType" id="cmbExportType"  cssClass="BoxW124px" title="Select Export Type">
							<option value="Excel">Excel</option>
							<option value="PDF">PDF</option>
						</s:select>
					</td>
					<td width="55%" colspan="2">						
						<input id="btnExport" type="button" value="EXPORT"  title="Export" class="form_button1"/>
					</td>
				</tr>
		</table>
		
		<div id="divProdDet" class="dynamicTableContainer" style="height: 280px;">
			<table class="transTablex" style="height: 25px; border: #0F0;width: 99.9%;font-size:11px;
			font-weight: bold;overflow: scroll;table-layout: fixed;">
				<tr bgcolor="#72BEFC">
					<th style="width:10%;text-align:left">SubGroup Name</th>					
					<th style="width:49.5%;text-align:left">Product Name</th>	
					<th style="width:8%;text-align:left" >C Stock</th>				
					<th style="width:8%;text-align:left">Phy Stk Qty</th>
                    <th style="width:8%;text-align:left">Variance</th> 
                    <th style="width:8%;text-align:left">Unit Price</th>
                    <th style="width:8%;text-align:left">Value</th> 
				</tr>
			</table>
				<div
				style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 196px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
					<table id="tblProdDet"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:10%">	
								
					<col style="width:50%">
					<col style="width:8.5%">
					<col style="width:8%">
					<col style="width:8%">
					<col style="width:8%">
					<col style="width:7%">
					</tbody>
				</table>
			</div>
			
			<table class="transTable">
				<tr>
				<td style="width:87%;text-align: right;">Total Variance Value</td>
				<td width="13%"><input type="text" id="txtTotVar" readonly="readonly" style="margin-right:14px" value=0.00 Class="decimal-places-amt numberField"></td>
				</tr>
			</table>
		</div>
		
		<br>
			<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
	</s:form>
	
</body>
</html>