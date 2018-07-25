<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">
		var invoceFlashData,maxQuantityDecimalPlaceLimit=2,frmDte1="",toDte1="";
		
		$(document).ready(function() 
				{		
				
					$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
					$("#txtFromDate").datepicker('setDate','today');
					$("#txtFromDate").datepicker();
					$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
					$("#txtToDate" ).datepicker('setDate', 'today');
					$("#txtToDate").datepicker();
					
					var oldFrmDate=$('#txtFromDate').val().split('-');
					var oldToDate=$('#txtToDate').val().split('-');
					frmDte1=oldFrmDate[2]+"-"+oldFrmDate[1]+"-"+oldFrmDate[0];
					toDte1=oldToDate[2]+"-"+oldToDate[1]+"-"+oldToDate[0];
					
					var locCode='<%=session.getAttribute("locationCode").toString()%>';
					funSetLocation(locCode);
					var custCode='${custCode}'; 
					if(custCode!="Without DrillDown")
					{
						frmDte1='${fromDate}';
						toDte1='${toDate}';	
						$('#txtFromDate').val(frmDte1);
						$('#txtToDate').val(toDte1);
						$("#txtLocCode").val('${locCode}');
						$("#txtCustCode").val(custCode);
						$("#cmbSettlement").val('${settleCode}');
						$("#cmbCurrency").val('${currencyCode}');
						funSetLocation('${locCode}');
						funSetCuster(custCode);
						funOnExecuteBtn('divInvoiceWise');
					}
					
						
					
					
					
					
					$(document).ajaxStart(function()
							{
							    $("#wait").css("display","block");
							});
							$(document).ajaxComplete(function()
							{
								$("#wait").css("display","none");
							});
		
				});
		
		$(function() 
				{
					
					
				});
		
		function funHelp(transactionName)
		{
			fieldName = transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;top=500,left=500")
		}

		 	function funSetData(code)
			{
		 		
		 		switch (fieldName)
				{
				
				 case 'locationmaster':
				    	funSetLocation(code);
				        break;
				        
				 case 'custMaster' :
			 			funSetCuster(code);
			 			break;  
			 			
				}		
			}

		 	function funSetCuster(code)
			{
				var gurl=getContextPath()+"/loadPartyMasterData.html?partyCode=";
				$.ajax({
			        type: "GET",
			        url: gurl+code,
			        dataType: "json",
			        success: function(response)
			        {		        	
			        		if('Invalid Code' == response.strPCode){
			        			alert("Invalid Customer Code");
			        			$("#strCustCode").val('');
			        			$("#strCustCode").focus();
			        		}else{			   
			        			$("#txtCustCode").val(response.strPCode);
								$("#lblCustName").text(response.strPName);
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
		 					$("#txtProdCode").focus();
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
		 	

		 		
			$(document).ready(function() 
			{
				
				$(document).ajaxStart(function()
				{
				    $("#wait").css("display","block");
				});
				$(document).ajaxComplete(function()
				{
					$("#wait").css("display","none");
				});
			});
		 	
		
			
			

			function showTable()
			{
				var optInit = getOptionsFromForm();
			    $("#Pagination").pagination(invoceFlashData.length, optInit);	
			    $("#divValueTotal").show();
			    
			}
		
			var items_per_page = 10;
			function getOptionsFromForm()
			{
			    var opt = {callback: pageselectCallback};
				opt['items_per_page'] = items_per_page;
				opt['num_display_entries'] = 10;
				opt['num_edge_entries'] = 3;
				opt['prev_text'] = "Prev";
				opt['next_text'] = "Next";
			    return opt;
			}
			
			
			
			function pageselectCallback(page_index, jq)
			{
			    // Get number of elements per pagionation page from form
			    var max_elem = Math.min((page_index+1) * items_per_page, invoceFlashData.length);
			    var newcontent="";
			  		    	
				   	newcontent = '<table id="tblInvoiceFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">Invoice Code</td><td id="labld2"> Date</td><td id="labld3"> Customer Name</td>	<td id="labld4"> Against </td> <td id="labld5"> Vehicle No </td>	<td id="labld6"> Excisable </td> <td id="labld7"> SubTotal</td><td id="labld8"> Tax Amount</td><td id="labld9"> Grand Toal</td></tr>';
				   	// Iterate through a selection of the content and build an HTML string
				    for(var i=page_index*items_per_page;i<max_elem;i++)
				    {
				        newcontent += '<tr><td><a id="stkLedgerUrl.'+i+'" href="#" onclick="funClick(this);">'+invoceFlashData[i].strInvCode+'</a></td>';
				        newcontent += '<td>'+invoceFlashData[i].dteInvDate+'</td>';
				        newcontent += '<td>'+invoceFlashData[i].strCustName+'</td>';
				        newcontent += '<td>'+invoceFlashData[i].strAgainst+'</td>';
				        newcontent += '<td>'+invoceFlashData[i].strVehNo+'</td>';
				        newcontent += '<td align="Center">'+invoceFlashData[i].strExciseable+'</td>';
				        newcontent += '<td align="right">'+parseFloat(invoceFlashData[i].dblSubTotalAmt).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(invoceFlashData[i].dblTaxAmt).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(invoceFlashData[i].dblTotalAmt).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				    }
			    
			    newcontent += '</table>';
			    // Replace old content with new content
			   
			    $('#Searchresult').html(newcontent);
			   
			    // Prevent click eventpropagation
			    return false;
			}
			
			function funClick(obj,dteObj)
			{
				var code=document.getElementById(""+obj.id+"").innerHTML;
				window.open(getContextPath()+"/rptInvoiceSlipFromat2.html?rptInvCode="+code,'_blank');
				window.open(getContextPath()+"/rptInvoiceSlipNonExcisableFromat2.html?rptInvCode="+code,'_blank');
			}
			
			
			
			$(document).ready(function () 
			 {			 

			 });
			
			
		
			
			
		
			
			function funGetTotalValue(dblTotalValue,dblSubTotalValue,dblTaxTotalValue)
			{
				$("#txtTotValue").val(parseFloat(dblTotalValue).toFixed(maxQuantityDecimalPlaceLimit));
				$("#txtSubTotValue").val(parseFloat(dblSubTotalValue).toFixed(maxQuantityDecimalPlaceLimit));
				$("#txtTaxTotValue").val(parseFloat(dblTaxTotalValue).toFixed(maxQuantityDecimalPlaceLimit));
			}
			
		
			
			function funOpenInvoiceFormat()
			{
				var invPath="";
					invoiceformat='<%=session.getAttribute("invoieFormat").toString()%>';
					if(invoiceformat=="Format 1")
						{
							invPath="openRptInvoiceSlip";
							
						}
					else if(invoiceformat=="Format 2")
						{
							invPath="rptInvoiceSlipFromat2";
						}
					else if(invoiceformat=="Format 5")
						{
							invPath="rptInvoiceSlipFormat5Report";
						}
					else if(invoiceformat=="RetailNonGSTA4"){
						 invPath="openRptInvoiceRetailNonGSTReport";
						}
					else if("Format 6")
						{
							invPath="rptInvoiceSlipFormat6Report";
						}
						else
					    {
							invPath="rptInvoiceSlipFormat6Report";
					    }
				return invPath;
			}
			
			function funApplyNumberValidation(){
				$(".numeric").numeric();
				$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
				$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
				$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
			    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
			    $(".decimal-places-amt").numeric({ decimalPlaces: maxAmountDecimalPlaceLimit, negative: false });
			}
			
		
			
			
			function funOnExecuteBtn( divId)
			{
				
				var settCode=$('#cmbSettlement').val();
				var locCode=$('#txtLocCode').val();
				var custCode=$('#txtCustCode').val();
				var currencyCode=$('#cmbCurrency').val();
				var searchUrl=getContextPath()+"/loadInvoiceFlash.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
			
				$.ajax({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	funBillWiseProductDetail(response[0])
					    	document.getElementById("txtSubTotValue").style.visibility = "visible"; 
							document.getElementById("txtTaxTotValue").style.visibility = "visible";
							funGetTotalValue(response[1],response[2],response[3]);
					    	
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
			
			function funBillWiseProductDetail(ProdDtl)
			{
				$('#tblInvoiceDet tbody').empty();
				for(var i=0;i<ProdDtl.length;i++)
				{
				 var data=ProdDtl[i];
				 
				data.dblSubTotalAmt=parseFloat(data.dblSubTotalAmt).toFixed(maxQuantityDecimalPlaceLimit);
				data.dblTaxAmt=parseFloat(data.dblTaxAmt).toFixed(maxQuantityDecimalPlaceLimit);
				data.dblTotalAmt=parseFloat(data.dblTotalAmt).toFixed(3);
				var invoiceUrl=funOpenInvoiceFormat();
			    var table = document.getElementById("tblInvoiceDet");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    row.insertCell(0).innerHTML= "<a  name=\"StrInvCode["+(rowCount)+"]\"  href="+invoiceUrl+"\.html?rptInvCode="+data.strInvCode+"&rptInvDate="+data.dteInvDate+"\ target=\"_blank\"  id=\"StrInvCode."+(rowCount)+"\" >"+data.strInvCode+"</a>";		    
			    row.insertCell(1).innerHTML= "<input name=\"DteInvDate["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"15%\" id=\"DteInvDate."+(rowCount)+"\" value='"+data.dteInvDate+"'>";
			    row.insertCell(2).innerHTML= "<input name=\"strSerialNo["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"15%\" id=\"strSerialNo."+(rowCount)+"\" value='"+data.strSerialNo+"'>";
			    row.insertCell(3).innerHTML= "<input name=\"CustomerName["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"30%\" id=\"CustomerName."+(rowCount)+"\" value='"+data.strCustName+"'>";
			    row.insertCell(4).innerHTML= "<input name=\"strSettleDesc["+(rowCount)+"]\" id=\"strSettleDesc."+(rowCount)+"\" readonly=\"readonly\"   size=\"14%\" class=\"Box\" value="+data.strSettleDesc+">";
			    row.insertCell(5).innerHTML= "<input name=\"StrAgainst["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"25%\" id=\"StrAgainst."+(rowCount)+"\" value='"+data.strAgainst+"'>";
			    row.insertCell(6).innerHTML= "<input name=\"StrVehNo["+(rowCount)+"]\" id=\"StrVehNo."+(rowCount)+"\" readonly=\"readonly\" size=\"14%\" class=\"Box\" value="+data.strVehNo+">";
			    row.insertCell(7).innerHTML= "<input name=\"StrCurrency["+(rowCount)+"]\" id=\"StrCurrency."+(rowCount)+"\" readonly=\"readonly\" size=\"10%\" class=\"Box\" value="+data.strCurrency+">";
			    row.insertCell(8).innerHTML= "<input name=\"DblSubTotalAmt["+(rowCount)+"]\" id=\"DblSubTotalAmt."+(rowCount)+"\" readonly=\"readonly\" style=\"text-align: right;\" size=\"13%\" class=\"Box\" value="+data.dblSubTotalAmt+">";
			    row.insertCell(9).innerHTML= "<input name=\"DblTaxAmt["+(rowCount)+"]\" id=\"DblTaxAmt."+(rowCount)+"\" readonly=\"readonly\" style=\"text-align: right;\" size=\"12%\" class=\"Box\" value="+data.dblTaxAmt+">";
			    row.insertCell(10).innerHTML= "<input name=\"DblTotalAmt["+(rowCount)+"]\" id=\"DblTotalAmt."+(rowCount)+"\" readonly=\"readonly\" style=\"text-align: right;\" size=\"10%\" class=\"Box\" value="+data.dblTotalAmt+">";
			   
			    
			    funApplyNumberValidation();
				}
			}
			
		
			
		
			
			function funChangeFromDate()
			{
				var oldFrmDate=$('#txtFromDate').val().split('-');
				var oldToDate=$('#txtToDate').val().split('-');
				frmDte1=oldFrmDate[2]+"-"+oldFrmDate[1]+"-"+oldFrmDate[0];
				toDte1=oldToDate[2]+"-"+oldToDate[1]+"-"+oldToDate[0];
			}
			function funChangeToDate()
			{
				var oldFrmDate=$('#txtFromDate').val().split('-');
				var oldToDate=$('#txtToDate').val().split('-');
				frmDte1=oldFrmDate[2]+"-"+oldFrmDate[1]+"-"+oldFrmDate[0];
				toDte1=oldToDate[2]+"-"+oldToDate[1]+"-"+oldToDate[0];
			}
			
			
			 	
		 	function funExportReport()
		 	{
		 		var settCode=$('#cmbSettlement').val();
				var locCode=$('#txtLocCode').val();
				if(locCode=="")
					{
					locCode="All";
					}
				var custCode=$('#txtCustCode').val();
				var currencyCode=$('#cmbCurrency').val();
				window.location.href = getContextPath()+"/exportBillWiseInvoiceFlash.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
		 	}
		 	
			function funResetCustomer()
		 	{
				$("#txtCustCode").val("All");
				$("#lblCustName").text("");
				$("#txtProdCode").val("All");
				$("#lblProdName").text("");
		 	}
		 	 
			
			
		</script>
</head>

<body>
	<div id="formHeading">
		<label>Customer Wise Invoice Flash</label>
	</div>
	<s:form name="Form" method="GET" action="">
		<br />

		<table class="transTable">
			<tr>
			
			
				<td><label id="lblFromDate">From Date</label></td>
				<td><s:input id="txtFromDate" required="required" path=""
						name="fromDate" cssClass="calenderTextBox" onchange="funChangeFromDate();"  /></td>
				<td style="width: 10%;"><label id="lblToDate">To Date</label></td>
				<td ><s:input id="txtToDate" name="toDate" path=""
						cssClass="calenderTextBox" onchange="funChangeToDate();" /></td>				
				<td><label id="">Settlement</label>&nbsp; &nbsp;&nbsp; &nbsp;
				<s:select id="cmbSettlement" path="strSettlementCode" items="${settlementList}" cssClass="BoxW124px">
			    </s:select></td> 	
				<td>
				    <input id="btnExecute" type="button" value="EXECUTE"  class="form_button1" onclick="funOnExecuteBtn('divInvoiceWise')" />
				    <input id="btnExport" type="button" value="EXPORT"  class="form_button1" onclick="funExportReport()" />
				    <input id="btnReset" type="button" value="RESET"  class="form_button1" onclick="funResetCustomer()" />
				</td>				
			</tr>
			
			<tr>
				<td><label>Location Code</label></td>
				<td><s:input type="text" id="txtLocCode" path="strLocCode"
						cssClass="searchTextBox" ondblclick="funHelp('locationmaster');" /></td>
				<td ><label id="lblLocName"></label></td>	
				<td ><label id="">Customer Code</label></td>
				<td><s:input type="text" id="txtCustCode" path="strCustCode"
						cssClass="searchTextBox" ondblclick="funHelp('custMaster');" value="All"/>
				</td>	
				<td ><label id="lblCustName"></label></td> 		
				
			</tr>
			
			<tr>
				<td><label id="">Currency</label></td>
				<td><s:select id="cmbCurrency" path="strCurrencyCode" items="${currencyList}" cssClass="BoxW124px">
			         </s:select></td>
				<td ></td>	
				<td ></td>
				<td></td>	
				<td ></td> 		
				
			</tr>


		</table>



<br/>
	


	<div id="divInvoiceWise" class="dynamicTableContainer"
			style="height: 400px;">
			<table style="width: 100%; border: #0F0; table-layout: fixed;"
				class="transTablex col15-center">
				<tr bgcolor="#72BEFC">
					<td width="7.6%">Invoice Code</td>
					<!--  COl1   -->
					<td width="5.3%">Date</td>
					<!--  COl2   -->
					<td width="5.4%">JV No</td>
					<!--  COl3   -->
					<td width="11%">Customer Name</td>
					<!--  COl4   -->
					<td width="4.6%"> Settement</td>
					<!--  COl5   -->
					<td width="4.5%">Against</td>
					<!-- COl6   -->
					<td width="3.2%">Vehicle No</td>
					<!-- COl7   -->
					<td width="3.2%">Currency</td>  
					<!--  COl8   -->
					<td width="5.8%">SubTotal</td>
					<!--  COl9   -->
					<td width="5.3%">Tax Amount</td>
					<!--  COl10   -->
					<td width="5.2%">Grand Total</td>
					<!--COl11   -->

				</tr>
			</table>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 330px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblInvoiceDet"
					style="width: 100%; border: #0F0; table-layout: fixed;"
					class="transTablex col15-center">
					<tbody>
					<col style="width: 7%">
					<!--  COl1   -->
					<col style="width: 5%">
					<!--  COl2   -->
					<col style="width: 5%">
					<!--  COl3   -->
					<col style="width: 10.5%">
					<!--  COl4   -->
					<col style="width: 4.3%">
					<!--  COl5   -->
					<col style="width: 4.2%">
					<!--COl6   -->
					<col style="width: 3%">  
					<!--COl7   -->
					<col style="width: 3%">
					<!-- COl8   -->
					<col style="width: 5.5%">
					<!--  COl9   -->
					<col style="width: 5%">
					<!--  COl10   -->
					<col style="width: 4%">
					<!--  COl11  -->


					</tbody>

				</table>
			</div>

		</div>


		
		
		<div id="divValueTotal"
			style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 50px; margin: auto; overflow-x: hidden; overflow-y: hidden; width: 95%;">
			<table id="tblTotalFlash" class="transTablex"
				style="width: 100%; font-size: 11px; font-weight: bold;">
				
				<tr style="margin-left: 28px">
				
					<td id="labld26" style="width:60%; text-align:right">Total</td>
					<td id="tdSubTotValue" style="width:13%; align:right">
						<input id="txtSubTotValue" style="width: 100%; text-align: right;" class="Box"></input>
					</td>
					<td id="tdTaxTotValue" style="width:13%; align:right">
						<input id="txtTaxTotValue" style="width: 100%; text-align: right;" class="Box"></input>
					</td>
					<td id="tdTotValue" style="width:14%; align:right">
						<input id="txtTotValue" style="width: 100%; text-align: right;" class="Box"></input>
					</td>

				</tr>
			</table>
		</div>


<br/>
<br/>

	</s:form>
</body>
</html>