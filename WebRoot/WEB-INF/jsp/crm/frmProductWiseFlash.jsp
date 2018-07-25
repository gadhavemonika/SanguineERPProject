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
					var reportName='${reportName}'; 
					if(reportName!="Without DrillDown")
					{
						if(reportName=="productwise")
						{
							var code='${code}';
							frmDte1='${fromDate}';
							toDte1='${toDate}';	
							$('#txtFromDate').val(frmDte1);
							$('#txtToDate').val(toDte1);
							$("#txtProdCode").val(code);
							$("#txtLocCode").val('${locCode}');
							$("#txtCustCode").val('${custCode}');
							$("#cmbSettlement").val('${settleCode}');
							$("#cmbCurrency").val('${currencyCode}');
							funSetLocation('${locCode}');
							if('${custCode}'!="All")
							{
								funSetCuster('${custCode}');
							}
							funSetProduct(code);
							funOnExecuteBtn('divSKUWise');
						}
						else if(reportName=="regionwise")
						{
							var code='${code}';
							frmDte1='${fromDate}';
							toDte1='${toDate}';	
							$('#txtFromDate').val(frmDte1);
							$('#txtToDate').val(toDte1);
							$("#txtRegionCode").val(code);
							$("#txtLocCode").val('${locCode}');
							$("#txtCustCode").val('${custCode}');
							$("#cmbSettlement").val('${settleCode}');
							$("#cmbCurrency").val('${currencyCode}');
							funSetLocation('${locCode}');
							if('${custCode}'!="All")
							{
								funSetCuster('${custCode}');
							}
							funSetRegion(code);
							funOnExecuteBtn('divSKUWise');
						}
						
						
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
			 			
				 case 'productProduced':
				    	funSetProduct(code);
				        break;	
				        
				 case 'crmRegionMaster':
					  funSetRegion(code);
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
		 	
		 	
		 	
		 	function funSetProduct(code) {
				var searchUrl = "";
				var suppCode="";
				var billDate="";
				var locCode=$("#txtLocCode").val();
				searchUrl = getContextPath()+ "/loadProductDataWithTax.html?prodCode=" + code+"&locCode="+locCode+"&suppCode="+suppCode+"&billDate="+billDate;
					$.ajax({
						type : "GET",
						url : searchUrl,
						dataType : "json",
						success : function(response) {

							if ('Invalid Code' == response.strProdCode) {
								alert('Invalid Product Code');
								$("#txtProdCode").val('');
							} 
							else 
							{
							  	$("#txtProdCode").val(response.strProdCode);
								$("#lblProdName").text(response.strProdName);
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
		 	
		 	
		 	function funSetRegion(code) 
		 	{
		 		$("#txtRegionCode").val(code);
				var searchurl=getContextPath()+"/loadCRMRegionMasterData.html?code="+code;
				 $.ajax({
					        type: "GET",
					        url: searchurl,
					        dataType: "json",
					        success: function(response)
					        {
					        	if(response.strRegionCode=='Invalid Code')
					        	{
					        		alert("Invalid Manufacturer Code");
					        		$("#txtRegionCode").val('');
					        	}
					        	else
					        	{
						        	$("#lblRegionName").text(response.strRegionDesc);
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
		 	
		 		
			$(document).ready(function() 
			{
				$("#btnExecute").click(function( event )
				{
// 					funCalculateInvoiceFlash();
					
				});
					
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
				var prodCode=$('#txtProdCode').val();
				var regionCode=$('#txtRegionCode').val();
				var currencyCode=$('#cmbCurrency').val();
			
				//var frmDte1=$('#txtFromDate').val();
				//var toDte1=$('#txtToDate').val(); 
				var locCode=locCode;
				var searchUrl=getContextPath()+"/loadCustProductWiseDtl.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&prodCode="+prodCode+"&regionCode="+regionCode+"&currencyCode="+currencyCode;
			
				$.ajax({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	funCustProductDetail(response[0]);
					    	$("#txtTaxTotValue").val(parseFloat(response[1]).toFixed(maxQuantityDecimalPlaceLimit));
					    	$("#txtTotValue").val(parseFloat(response[2]).toFixed(maxQuantityDecimalPlaceLimit));
					    	document.getElementById("txtSubTotValue").style.visibility = "hidden"; 
					    	
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
			
			function funCustProductDetail(ProdDtl)
			{
				var userName="";
				$('#tblCustProdDtl tbody').empty();
				var rowCount = "";
			    var row ="";
				var amt='';
				for(var i=0;i<ProdDtl.length;i++)
				{
				 var data=ProdDtl[i];
				
				 data[5]=parseFloat(data[5]).toFixed(maxQuantityDecimalPlaceLimit);
				 data[6]=parseFloat(data[6]).toFixed(maxQuantityDecimalPlaceLimit);
				 
			    var table = document.getElementById("tblCustProdDtl");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    row.insertCell(0).innerHTML= "<input name=\"InvoiceNo["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"20%\" id=\"InvoiceNo."+(rowCount)+"\" value='"+data[0]+"'>";   
			    row.insertCell(1).innerHTML= "<input name=\"ProdCode["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"20%\" id=\"ProdCode."+(rowCount)+"\" value='"+data[1]+"'>";		    
			    row.insertCell(2).innerHTML= "<input name=\"ProdName["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"30%\" id=\"ProdName."+(rowCount)+"\" value='"+data[2]+"'>";
			    row.insertCell(3).innerHTML= "<input name=\"CustName["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"25%\" id=\"CustName."+(rowCount)+"\" value='"+data[3]+"'>";
			    row.insertCell(4).innerHTML= "<input name=\"RegionName["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"15%\" id=\"RegionName."+(rowCount)+"\" value='"+data[4]+"'>";
			    row.insertCell(5).innerHTML= "<input name=\"Currency["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"15%\" id=\"Currency."+(rowCount)+"\" value='"+data[7]+"'>";
			    row.insertCell(6).innerHTML= "<input name=\"ProdQty["+(rowCount)+"]\" id=\"ProdQty."+(rowCount)+"\"readonly=\"readonly\" style=\"text-align: right;\" size=\"13%\" class=\"Box\" value="+data[5]+">";
			    row.insertCell(7).innerHTML= "<input name=\"ProdAmount["+(rowCount)+"]\" id=\"ProdAmount."+(rowCount)+"\" readonly=\"readonly\"  style=\"text-align: right;\"  size=\"11%\" class=\"Box\"  value="+data[6]+">";
			    
			   }
				
				funApplyNumberValidation();
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
				var prodCode=$('#txtProdCode').val();
				var regionCode=$('#txtRegionCode').val();
				var currencyCode=$('#cmbCurrency').val();
				window.location.href = getContextPath()+"/exportCustProductWiseFlash.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&prodCode="+prodCode+"&regionCode="+regionCode+"&currencyCode="+currencyCode;
		 	}
		 	
			function funResetCustomer()
		 	{
				$("#txtCustCode").val("All");
				$("#lblCustName").text("");
				$("#txtProdCode").val("All");
				$("#lblProdName").text("");
				$("#txtRegionCode").val("All");
				$("#lblRegionName").text("");
		 	}
		 	 
			
			
		</script>
</head>

<body>
	<div id="formHeading">
		<label>ProductWise Flash</label>
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
				    <input id="btnExecute" type="button" value="EXECUTE"  class="form_button1" onclick="funOnExecuteBtn('divSKUWise')" />
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
					<label id="lblCustName"></label>	
				</td>	
				<td ><label id="">Currency</label>
				     <s:select id="cmbCurrency" path="strCurrencyCode" items="${currencyList}" cssClass="BoxW124px">
			         </s:select>
				</td> 		
			</tr>

			<tr>
			    <td ><label id="">Region Code</label>
				<td><s:input type="text" id="txtRegionCode" path=""
						cssClass="searchTextBox" ondblclick="funHelp('crmRegionMaster')" value="All"/></td>
				<td ><label id="lblRegionName"></label></td> 
				<td ><label id="">Product Code</label>
				<td><s:input type="text" id="txtProdCode" path=""
						cssClass="searchTextBox" ondblclick="funHelp('productProduced')" value="All"/></td>
				<td ><label id="lblProdName"></label></td> 
				 				
			 </tr>



		</table>



<br/>
	


	<div id="divSKUWise" class="dynamicTableContainer"
			style="height: 400px;">
			<table style="width: 100%; border: #0F0; table-layout: fixed;"
				class="transTablex col15-center">
				<tr bgcolor="#72BEFC">
				
				    <td width="6%">Invoice No</td>
					<!--  COl1   -->
					<td width="6%">Product Code</td>
					<!--  COl2   -->
					<td width="10.9%">Product Name</td>
					<!--  COl3   -->
					<td width="9.1%">Customer Name</td>
					<!--  COl4   -->
					<td width="5.5%">Region Name</td>
					<!--  COl5   -->
					<td width="5.5%">Currency</td>
					<!--  COl6   -->
					<td width="5%">Quantity</td>
					<!-- COl7  -->
					<td width="5%">Sales Amount</td>
					<!--  COl8   -->
					

				</tr>
			</table>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 330px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblCustProdDtl"
					style="width: 100%; border: #0F0; table-layout: fixed;"
					class="transTablex col15-center">
					<tbody>
					<col style="width:6.2%">
					<!--  COl1   -->
					<col style="width:6.2%">
					<!--  COl2   -->
					<col style="width: 11.3%">
					<!--  COl3   -->
					<col style="width: 9.5%">
					<!--  COl4   -->
					<col style="width: 5.6%">
					<!--  COl5   -->
					<col style="width: 5.6%">
					<!--  COl6   -->
					<col style="width: 5.3%">
					<!--  COl7   -->
					<col style="width: 4.2%">
					<!--  COl8   -->
				
					
				
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
					<td id="tdTotValue" style="width:13%; align:right">
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