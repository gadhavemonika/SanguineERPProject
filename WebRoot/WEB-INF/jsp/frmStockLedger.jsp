<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" src="<spring:url value="/resources/js/jQuery.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/validations.js"/>"></script>
	<script>

		$(function ()
		{
			var startDate="${startDate}";
			var arr = startDate.split("/");
			Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
			$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtFromDate" ).datepicker('setDate', Dat);
			$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtToDate" ).datepicker('setDate', 'today');
			 var strPropCode='<%=session.getAttribute("propertyCode").toString()%>';
			 var locationCode ='<%=session.getAttribute("locationCode").toString()%>';
			 $("#cmbProperty").val(strPropCode);
			 $("#cmbLocation").val(locationCode);
			
			$("#btnExecute").click(function( event )
			{
				if($("#txtProdCode").val()=='')
				{
					alert("Enter Product Code!");
					return false;
				}
				
				var fromDate=$("#txtFromDate").val();
				var toDate=$("#txtToDate").val();
				var table = document.getElementById("tblStockLedger");
				var rowCount = table.rows.length;
				while(rowCount>0)
				{
					table.deleteRow(0);
					rowCount--;
				}
				
				var table1 = document.getElementById("tblStockLedgerSummary");
				var rowCount1 = table1.rows.length;
				while(rowCount1>0)
				{
					table1.deleteRow(0);
					rowCount1--;
				}
				
				var fromDate=$("#txtFromDate").val();
				var toDate=$("#txtToDate").val();
				var locCode=$("#cmbLocation").val();
				var propCode=$("#cmbProperty").val();
				var prodCode=$("#txtProdCode").val();
				funGetStockLedger(fromDate,toDate,locCode,propCode,prodCode);
			});
			
			$('a#urlDocCode').click(function() 
			{
			    $(this).attr('target', '_blank');
			});
		});
		
		
		function funGetStockLedger(fromDate,toDate,locCode,propCode,prodCode)
		{
			var qtyWithUOM=$("#cmbQtyWithUOM").val();
			var param1=locCode+","+propCode+","+prodCode+",detail,"+qtyWithUOM;
			var searchUrl=getContextPath()+"/frmStockLedgerReport.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funShowStockLedger(response);
				    },
					error: function(e)
				    {
				       	alert('Error:=' + e);
				    }
			      });
		}
		
		
		function funShowStockLedger(response)
		{
			var qtyWithUOM=$("#cmbQtyWithUOM").val();
			$("#lblFromDate1").text("From");
			var fd=$("#txtFromDate").val();
			var td=$("#txtToDate").val();
			
			var fromDate=funGetDate(fd,"dd/MM/yyyy");
			var toDate=funGetDate(td,"dd/MM/yyyy");
			
			var table = document.getElementById("tblStockLedger");
			var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
			row.insertCell(0).innerHTML= "<label>Transaction Date</label>";
			row.insertCell(1).innerHTML= "<label>CostCenter/Supplier Name</label>";
			row.insertCell(2).innerHTML= "<label>Transaction Type</label>";
			row.insertCell(3).innerHTML= "<label>Ref No</label>";
			row.insertCell(4).innerHTML= "<label>Receipt</label>";
			row.insertCell(5).innerHTML= "<label>Issue</label>";
			row.insertCell(6).innerHTML= "<label>Balance</label>";
			row.insertCell(7).innerHTML= "<label>Rate</label>";
			row.insertCell(8).innerHTML= "<label>Value</label>";
			
			rowCount=rowCount+1;
			//var records = [];
			   	var currValue='<%=session.getAttribute("currValue").toString()%>';
		    		if(currValue==null)
		    			{
		    				currValue=1;
		    			}
			$.each(response, function(i,item)
			{
				var row1 = table.insertRow(rowCount);
				if(item[2]!='')
				{
					row1.insertCell(0).innerHTML= "<label>"+item[0]+"</label>";
					row1.insertCell(1).innerHTML= "<label>"+item[5]+"</label>";
					row1.insertCell(2).innerHTML= "<label>"+item[1]+"</label>";
					row1.insertCell(3).innerHTML= "<a id=\"urlDocCode\" href=\"openSlip.html?docCode="+item[2]+","+item[1]+"\" target=\"_blank\" >"+item[2]+"</a>";
// 			   		row1.insertCell(4).innerHTML= "<label>"+item[3].toFixed(maxQuantityDecimalPlaceLimit)+"</label>";
// 				   	row1.insertCell(5).innerHTML= "<label>"+item[4].toFixed(maxQuantityDecimalPlaceLimit)+"</label>";
				   		if(qtyWithUOM=='Yes')
			   		{
				   		row1.insertCell(4).innerHTML= "<label>"+item[3]+"</label>";
					   	row1.insertCell(5).innerHTML= "<label>"+item[4]+"</label>";
			   		}else
			   			{
			   			row1.insertCell(4).innerHTML= "<label>"+item[3].toFixed(maxQuantityDecimalPlaceLimit)+"</label>";
					   	row1.insertCell(5).innerHTML= "<label>"+item[4].toFixed(maxQuantityDecimalPlaceLimit)+"</label>";
			   			}
				   	
				   	
				   	row1.insertCell(6).innerHTML= "<label>0</label>";
				   	row1.insertCell(7).innerHTML= "<label>"+item[6]/currValue+"</label>";
				   	row1.insertCell(8).innerHTML= "<label>0</label>";
				   	if(qtyWithUOM=='Yes')
			   		{
				   	
				   		row1.insertCell(9).innerHTML= "<input type=\"hidden\" value="+item[7]+"></input>";
				   		//row1.insertCell(8).innerHTML= "<label type=\"hidden\">"+item[7]+"</label>";
			   		}
				}
			});
			
			var bal=0;
			var balUOM=0;
			var openingStk=0;
			var totalRec=0,totalIssue=0;
			var rowCount1 = table.rows.length;
		    var tableRows=table.rows;
		    var rateForValue=0;
		    
		for (var cnt = 1; cnt < rowCount1; cnt++) {
				var cells1 = tableRows[cnt].cells;

				var rec = cells1[4].innerHTML;
				rec = rec.substring(7, rec.lastIndexOf("<"));
				var issue = cells1[5].innerHTML;
				issue = issue.substring(7, issue.lastIndexOf("<"));
				
				var qtyWithUOM = $("#cmbQtyWithUOM").val();
				
				var rate1 = cells1[7].innerHTML;
				var rate = parseFloat(rate1.substring(7, rate1.lastIndexOf("<")));
				if (qtyWithUOM == 'No') 
				{
					var negtveSign="";
					if(issue.charAt(0)=="-")
						{
						var issueData=issue.substring(1,issue.length);
						bal = bal + (parseFloat(rec) + parseFloat(issue));
						
						}else{
						bal = bal + (parseFloat(rec) - parseFloat(issue));
						}
						cells1[6].innerHTML = "<label>"+bal.toFixed(maxQuantityDecimalPlaceLimit)+ "</label>";
// 						bal = negtveSign+""+bal;
				} else {
					var tempRecipts = 0;
					var tempIssue = 0;
					var totRowBal = 0;
					var uom = cells1[9].innerHTML;
					//alert(uom);

// 					uom = uom.substring(14, uom.lastIndexOf("type") - 2);
					uom = uom.substring(uom.lastIndexOf("value")+7, uom.length-2);
					//alert(uom);
					var spUOM = uom.split('!');
					var recipeConv = spUOM[0];
					var spUOM = uom.split('!');
					var recipeConv = spUOM[0];
					var issueConv = spUOM[1];
					var receivedUOM = spUOM[2];
					var recipeUOM = spUOM[3];

					if (rec == '0') {
						tempRecipts += parseFloat(rec);
					} else {
						var dblReptHigh = 0;
						var dblRectlow = 0;

						if (rec.indexOf(receivedUOM) > -1) {
							var highNLowData = rec.split(".");
							var highNo = highNLowData[0].split(" ");
							dblReptHigh = parseFloat(highNo[0])	* parseFloat(recipeConv);

							var lowNo = highNLowData[1].split(" ");
							if(lowNo !="")
							{
								dblRectlow = parseFloat(lowNo[0]);
								var totHighlowRecipt = dblReptHigh + dblRectlow;
								tempRecipts = parseFloat(tempRecipts)+ totHighlowRecipt;
							}else
								{
									tempRecipts = parseFloat(tempRecipts)+ dblReptHigh;
								}
							
						} else {
							dblRectlow = rec.split(" ");
							dblRectlow = parseFloat(dblRectlow[0]);
							tempRecipts = parseFloat(tempRecipts) + dblRectlow;
						}

					}

					if (issue == '0') {
						tempIssue += parseFloat(issue);
					} else {
						var dblIssHigh = 0;
						var dblIsslow = 0;

						if (issue.indexOf(receivedUOM) > -1) {
							var highNLowData = issue.split(".");
							var highNo = highNLowData[0].split(" ");
							dblIssHigh = parseFloat(highNo[0])* parseFloat(recipeConv);

							var lowNo = highNLowData[1].split(" ");
							if(lowNo !="")
							{
								dblIsslow = parseFloat(lowNo[0]);
								var totHighlowIssue = dblIssHigh + dblIsslow;
								tempIssue = parseFloat(tempIssue) + totHighlowIssue;
							}else
								{
									tempIssue = parseFloat(tempIssue) + dblIssHigh;
								}
							
						} else {
							dblIsslow = issue.split(" ");
							dblIsslow = parseFloat(dblIsslow[0]);
							tempIssue = parseFloat(tempIssue) + dblIsslow;
						}

					}
					totRowBal = tempRecipts - tempIssue;

					totRowBal = parseFloat(totRowBal) / parseFloat(recipeConv);

					bal = bal +parseFloat(totRowBal.toFixed(maxAmountDecimalPlaceLimit));
					
					var restQty = 0;
					var balance = bal + "";
					var spBalance = balance.split('.');

					if ((bal + "").indexOf('.') > -1) {
						restQty = parseFloat(bal)- parseFloat(spBalance[0]);
					}

					var finalBal = "";
					if (spBalance[0] != 'undefined') {
						finalBal = spBalance[0] + ' ' + receivedUOM;
					}
					if (spBalance[1] != "undefined") {
						var balWithUOM = parseFloat(restQty.toFixed(maxAmountDecimalPlaceLimit))* parseFloat(recipeConv);
						finalBal = finalBal + '.' + balWithUOM + ' '+ recipeUOM;
					}

					// 		        	}
					cells1[6].innerHTML = "<label>" + finalBal + "</label>";
				}

				cells1[7].innerHTML = "<label>"	+ rate.toFixed(maxAmountDecimalPlaceLimit) + "</label>";

				var issueOrReceipt = parseFloat(rec) + parseFloat(issue);
				var value = parseFloat(rate * issueOrReceipt).toFixed(maxAmountDecimalPlaceLimit);
				cells1[8].innerHTML = "<label>" + value + "</label>";

				var transName = cells1[2].innerHTML;
				if (transName == '<label>Opening Stk</label>') {
					openingStk = bal;
				} else {
					totalRec = totalRec + parseFloat(rec);
					totalIssue = totalIssue + parseFloat(issue);
				}

				rateForValue = rate;
			}

			var closingBalance = parseFloat(openingStk) + parseFloat(totalRec);
			closingBalance = closingBalance - parseFloat(totalIssue);

			var table = document.getElementById("tblStockLedgerSummary");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			row.insertCell(0).innerHTML = "<label>Transaction Type</label>";
			row.insertCell(1).innerHTML = "<label>Quantity</label>";
			row.insertCell(2).innerHTML = "<label>Value</label>";
			rowCount = rowCount + 1;

			var row1 = table.insertRow(rowCount);
			row1.insertCell(0).innerHTML = "<label>Opening Stock</label>";
			row1.insertCell(1).innerHTML = "<label>"+ parseFloat(openingStk).toFixed(maxQuantityDecimalPlaceLimit) + "</label>";
			row1.insertCell(2).innerHTML = "<label>"+ (parseFloat(openingStk) * parseFloat(rateForValue)).toFixed(maxAmountDecimalPlaceLimit) + "</label>";

			rowCount = rowCount + 1;
			row1 = table.insertRow(rowCount);
			row1.insertCell(0).innerHTML = "<label>Total Receipts</label>";
			row1.insertCell(1).innerHTML = "<label>"+ parseFloat(totalRec).toFixed(maxQuantityDecimalPlaceLimit)+ "</label>";
			row1.insertCell(2).innerHTML = "<label>"+ (parseFloat(totalRec) * parseFloat(rateForValue)).toFixed(maxAmountDecimalPlaceLimit) + "</label>";

			rowCount = rowCount + 1;
			row1 = table.insertRow(rowCount);
			row1.insertCell(0).innerHTML = "<label>Total Issues</label>";
			row1.insertCell(1).innerHTML = "<label>"+ parseFloat(totalIssue).toFixed(maxQuantityDecimalPlaceLimit) + "</label>";
			row1.insertCell(2).innerHTML = "<label>"+ (parseFloat(totalIssue) * parseFloat(rateForValue)).toFixed(maxAmountDecimalPlaceLimit) + "</label>";

			rowCount = rowCount + 1;
			row1 = table.insertRow(rowCount);
			row1.insertCell(0).innerHTML = "<label>Closing Balance</label>";
			row1.insertCell(1).innerHTML = "<label>"+ parseFloat(closingBalance).toFixed(maxQuantityDecimalPlaceLimit) + "</label>";
			row1.insertCell(2).innerHTML = "<label>"+ (parseFloat(closingBalance) * parseFloat(rateForValue)).toFixed(maxAmountDecimalPlaceLimit) + "</label>";
		}

		function sortFunction(a, b) {
			var dateA = getDate(a).getTime();
			var dateB = getDate(b).getTime();
			return dateA < dateB ? 1 : -1;
		}

		function getDate(obj) {
			var parts = obj.tran_date.split('-');
			return new Date(parts[2], parts[1] - 1, parts[0]);
		}

		function funGetDate(date, format) {
			var retDate = '';
			if (format == 'dd-MM-yyyy') {
				var arr = date.split('-');
				retDate = arr[2] + "-" + arr[1] + "-" + arr[0];
			} else if (format == 'dd/MM/yyyy') {
				var arr = date.split('/');
				retDate = arr[2] + "-" + arr[1] + "-" + arr[0];
			}
			return retDate;
		}

		function funHelp(transactionName) {
			fieldName = transactionName;
			//window.open("searchform.html?formname="+transactionName+"&searchText=", 'window', 'width=600,height=600');
			//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
			window.open("searchform.html?formname=" + transactionName
					+ "&searchText=", "",
					"dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
		}

		function funSetData(code) {
			switch (fieldName) {
			case 'productmaster':
				funSetProduct(code);
				break;
			}
		}

		function funSetProduct(code) {
			var searchUrl = "";

			searchUrl = getContextPath()
					+ "/loadProductMasterData.html?prodCode=" + code;
			$.ajax({
				type : "GET",
				url : searchUrl,
				dataType : "json",
				success : function(response) {
					$("#txtProdCode").val(response.strProdCode);
					$("#lblProdName").text(response.strProdName);
				},
				error : function(e) {
					alert('Error:=' + e);
				}
			});
		}
		$(document).ready(
				function() {
					$("#btnExport").click(
							function() {
								/* var dtltbl = $('#dvStockLedger').html(); 
								window.open('data:application/vnd.ms-excel,' + encodeURIComponent($('#dvStockLedger').html())); 
								 */

								/*var reportType=$("#cmbExportType").val();
								var locName=$("#cmbLocation option:selected").text();
								var strProdName= $('#lblProdName').text();
								var dtFromDate=$("#txtFromDate").val();
								var dtToDate=$("#txtToDate").val();
								var param1=reportType+","+locName+","+strProdName+","+dtFromDate+","+dtToDate;
								window.location.href=getContextPath()+"/frmExportStockLedger.html?param1="+param1;
								 */
								var fromDate = $("#txtFromDate").val();
								var toDate = $("#txtToDate").val();
								var locCode = $("#cmbLocation").val();
								var propCode = $("#cmbProperty").val();
								var prodCode = $("#txtProdCode").val();
								var qtyWithUOM = $("#cmbQtyWithUOM").val();
								var param1 = locCode + "," + propCode + ","
										+ prodCode + ",detail," + qtyWithUOM;
								window.location.href = getContextPath()
										+ "/frmExportStockLedger.html?param1="
										+ param1 + "&fDate=" + fromDate
										+ "&tDate=" + toDate;
							});
				});

		$(document).ready(
				function() {
					var flgStkFlash = "${flgStkFlash}";
					if (flgStkFlash == 'true') {
						var param = ("${stkledger}");
						var spParam = param.split(',');
						$("#cmbLocation").val(spParam[1]);
						$("#cmbProperty").val(spParam[2]);
						$("#txtProdCode").val(spParam[0]);
						funSetProduct(spParam[0]);
						$("#txtFromDate").val(spParam[3]);
						$("#txtToDate").val(spParam[4]);

						funGetStockLedger(spParam[3], spParam[4], spParam[1],
								spParam[2], spParam[0]);
					}
				});

		function funChangeLocationCombo() {
			var propCode = $("#cmbProperty").val();
			funFillLocationCombo(propCode);
		}

		function funFillLocationCombo(propCode) {
			var searchUrl = getContextPath()
					+ "/loadForInventryLocationForProperty.html?propCode=" + propCode;
			$.ajax({
				type : "GET",
				url : searchUrl,
				dataType : "json",
				success : function(response) {
					var html = '';
					$.each(response, function(key, value) {
						html += '<option value="' + value[1] + '">' + value[0]
								+ '</option>';
					});
					html += '</option>';
					$('#cmbLocation').html(html);
					//$("#cmbLocation").val(loggedInLocation);
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
	</script>
</head>
<body onload="funOnLoad();">
<div id="formHeading">
		<label>Stock Ledger</label>
	</div>
	<s:form action="frmStockLedgerReport.html" method="GET" name="frmStkFlash" id="frmStkFlash">
		
		<br>
			<table class="transTable">
			<tr><th colspan="7"></th></tr>
				<tr>
					<td  width="10%">Property Code</td>
					<td width="20%">
						<s:select id="cmbProperty" name="propCode" path="strPropertyCode" onchange="funChangeLocationCombo();" cssClass="longTextBox" cssStyle="width:100%">
			    			<s:options items="${listProperty}"/>
			    		</s:select>
					</td>
						
					<td width="5%"><label>Location</label></td>
					<td width="10%">
						<s:select id="cmbLocation" name="locCode" path="strLocationCode" cssClass="longTextBox" cssStyle="width:180px;">
			    			<s:options items="${listLocation}" />
			    		</s:select>
					</td>
					
					<td width="10%"><label>Product</label></td>
					<td width="10%">
			            <input id="txtProdCode" ondblclick="funHelp('productmaster');" class="searchTextBox"/>
			        </td>
			        <td>
			            <label  id="lblProdName"></label>
			        </td>
				</tr>
					
				<tr>
				    <td><label id="lblFromDate">From Date</label></td>
			        <td>
			            <s:input id="txtFromDate" name="fromDate" path="dteFromDate" cssClass="calenderTextBox"/>
			        	<s:errors path="dteFromDate"></s:errors>
			        </td>
				        
			        <td><label id="lblToDate">To Date</label></td>
			        <td>
			            <s:input id="txtToDate" name="toDate" path="dteToDate" cssClass="calenderTextBox"/>
			        	<s:errors path="dteToDate"></s:errors>
			        </td>
			        			        
			        <td width="10%"><label>Quantity With UOM</label></td>
					<td width="10%">
						<select id="cmbQtyWithUOM" class="BoxW124px">
							<option selected="selected">No</option>
							<option>Yes</option>
						</select>
					</td>
			        
				</tr>
						
				<tr>
					<td><input id="btnExecute" type="button" value="EXECUTE" class="form_button1"/></td>
					
					<td colspan="6">
					
						<s:select path="strExportType" id="cmbExportType" cssClass="BoxW124px">
<!-- 							<option value="pdf">PDF</option> -->
							<option value="xls">Excel</option>
						</s:select>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input id="btnExport" type="button" value="EXPORT" class="form_button1"/>
					</td>
					
				</tr>
			</table>
			
			<br><br>
			
			<!-- 
			<table  class="transTable">
				<tr>
					<td width="100px"><label id="lblFromDate1"></label></td><td>
					<label id="lblFromDate2"></label>
					<label id="lblToDate1"></label>
					<label id="lblToDate2"></label></td>
				</tr>
				
				<tr>
					<td><label id="lblLocName1"></label></td>
					<td><label id="lblLocName2"></label></td>
				</tr>
				
				<tr>
					<td><label id="lblProdCode1"></label></td>
					<td><label id="lblProdCode2"></label></td>
				</tr>
				
				<tr>
					<td><label id="lblProdName1"></label></td>
					<td><label id="lblProdName2"></label></td>
				</tr>
				
				<tr></tr>
				<tr></tr>
				
			</table> -->
			<br>
			
			<div id="dvStockLedger" style="width: 100% ;height: 100% ;">
				<table id="tblStockLedger" class="transTable col5-right col6-right col7-right col8-right col9-right"></table>
			</div>
			
			<br><br>
			
			<div id="dvStockLedgerSummary" style="width: 30% ;height: 100% ;">
				<table id="tblStockLedgerSummary"  class="transTable col2-right col3-right">					
				</table>
			</div>
			
	
		<br><br>
	</s:form>
</body>
</html>
