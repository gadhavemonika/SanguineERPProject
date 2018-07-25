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
					
					var code='<%=session.getAttribute("locationCode").toString()%>';
					funSetLocation(code);
					funOnClckBillWiseBtn('divBillWise');
					
					$(document).ajaxStart(function()
							{
							    $("#wait").css("display","block");
							});
							$(document).ajaxComplete(function()
							{
								$("#wait").css("display","none");
							});
		
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
		 	
			function funCalculateInvoiceFlash()
			{	
				var Code=$('#cmbSettlement').val();	 
				if(Code=="")
				{
					Code="All";
				}
				var frmDte1=$('#txtFromDate').val();
				var toDte1=$('#txtToDate').val();
				var locCode1=$('#txtLocCode').val();
				var searchUrl=getContextPath()+"/loadInvoiceFlash.html?settlementcode="+Code+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode1+"&custCode="+custCode;
			
				$.ajax({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	funShowProductDetail(response)
					    	invoceFlashData=response[0];
 					    	showTable();
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
			
			
			function funShowProductDetail(invoiceData)
			{
				var table = document.getElementById("tblProdDet");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			 
		       var strSOCode="";
			    row.insertCell(0).innerHTML= "<input name=\"strInvCode.["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box txtProdCode\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
			    row.insertCell(1).innerHTML= "<input name=\"dteInv.["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"40%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
			    row.insertCell(2).innerHTML= "<input name=\"strProdType.["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"0%\" id=\"txtProdTpye."+(rowCount)+"\" value='"+strProdType+"'/>";
			    row.insertCell(3).innerHTML= "<input name=\"dblQty.["+(rowCount)+"]\" type=\"text\"  required = \"required\" style=\"text-align: right;\" size=\"3.9%\"  class=\"decimal-places inputText-Auto  txtQty\" id=\"txtQty."+(rowCount)+"\" value="+dblQty+" onblur=\"Javacsript:funUpdatePrice(this)\">";
			    row.insertCell(4).innerHTML= "<input name=\"dblWeight.["+(rowCount)+"]\" type=\"text\"  required = \"required\" style=\"text-align: right;\" size=\"3.9%\" class=\"decimal-places inputText-Auto\" id=\"txtWeight."+(rowCount)+"\" value="+dblWeight+" >";
			    row.insertCell(5).innerHTML= "<input name=\"dblTotalWeight.["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"3.9%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+dblTotalWeight+"'/>";
			    row.insertCell(6).innerHTML= "<input name=\"dblUnitPrice.["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box txtUnitprice\" style=\"text-align: right;\" \size=\"3.9%\" id=\"unitprice."+(rowCount)+"\"   value='"+unitprice+"'/>";
			
			
			
			   // funGetTotal();
			    return false;
			}

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
// 						$("#btnExport").click(function (e)
// 						{
// 							var Code=$('#txtCustCode').val();	 
// 							if(Code=="")
// 							{
// 								Code="All";
// 							}
// 							var frmDte1=$('#txtFromDate').val();
// 							var toDte1=$('#txtToDate').val();
// 							var locCode1=$('#txtLocCode').val();

		
// 						window.location.href=getContextPath()+"/exportInvoiceExcel.html?custcode="+Code+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode1;
									
// 									});
					});
			
			
			
			
			
			
			
			function funShowTableGUI(divID)
			{
				hidReportName=divID;
				// for hide Table GUI
				document.all["divTenderWise"].style.display = 'none';
 				document.all["divOperatorWise"].style.display = 'none';
 				document.all["divBillWise"].style.display = 'none';
				document.all["divCustomerWise"].style.display = 'none';
				document.all["divSKUWise"].style.display = 'none';
				document.all["divCategoryWise"].style.display = 'none';
				document.all["divManufactureWise"].style.display = 'none';
				document.all["divDepartmentWise"].style.display = 'none';
				document.all["divMonthWise"].style.display = 'none';
				document.all["divRegionWise"].style.display = 'none';
				
// 				document.all["divProfessionTable"].style.display = 'none';
// 				document.all["divMaritalTable"].style.display = 'none';
// 				document.all["divEducationTable"].style.display = 'none';
// 				document.all["divCommitteeMemberRoleTable"].style.display = 'none';
// 				document.all[ 'divRelationTable' ].style.display = 'none';
				

// 		 		document.all["divStaffTable"].style.display = 'none';
// 		 		document.all["divCurrencyDetailsTable"].style.display = 'none';
// 		 		document.all["divInvitedByTable"].style.display = 'none';
// 		 		document.all["divItemCategoryTable"].style.display = 'none';
// 		 		document.all["divProfileTable"].style.display = 'none';
// 		 		document.all["divSalutationTable"].style.display = 'none';
// 		 		document.all["divTitleTable"].style.display = 'none';
				
				
				// for show Table GUI
				document.all[divID].style.display = 'block';
				if(divID=='divCategoryWise')
				 {
					 document.getElementById("lblWithDisAmt").style.visibility = "visible";
					 document.getElementById("cmbWithDisAmt").style.visibility = "visible";
				 }
				else 
				 {
					 document.getElementById("lblWithDisAmt").style.visibility = "hidden"; 
					 document.getElementById("cmbWithDisAmt").style.visibility = "hidden";   
				 }
				 	
					
			}
			
			
			
			function funOnClckBillWiseBtn( divId)
			{
				funShowTableGUI(divId);
				var settCode=$('#cmbSettlement').val();
				var locCode=$('#txtLocCode').val();
				var custCode=$('#txtCustCode').val();
				
			
				//var frmDte1=$('#txtFromDate').val();
				//var toDte1=$('#txtToDate').val();
				var locCode1=$('#txtLocCode').val();
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
					    	funGetTotalValue(parseFloat(response[1]),response[2],response[3]);
					    	
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
			
			function funGetTotalValue(dblTotalValue,dblSubTotalValue,dblTaxTotalValue)
			{
				$("#txtTotValue").val(parseFloat(dblTotalValue).toFixed(maxQuantityDecimalPlaceLimit));
				$("#txtSubTotValue").val(parseFloat(dblSubTotalValue).toFixed(maxQuantityDecimalPlaceLimit));
				$("#txtTaxTotValue").val(parseFloat(dblTaxTotalValue).toFixed(maxQuantityDecimalPlaceLimit));
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
			
			
			function funOnClckTenderWiseBtn( divId)
			{
				funShowTableGUI(divId)
				var settCode=$('#cmbSettlement').val();
				var locCode=$('#txtLocCode').val();
				var custCode=$('#txtCustCode').val();
				
			
				//var frmDte1=$('#txtFromDate').val();
				//var toDte1=$('#txtToDate').val();
				var locCode1=$('#txtLocCode').val();
				var currencyCode=$('#cmbCurrency').val();
				var searchUrl=getContextPath()+"/loadTenderWiseDtl.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
			
				$.ajax({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	funTenderWiseProductDetail(response[0]);
					    	$("#txtTotValue").val(parseFloat(response[1]).toFixed(maxQuantityDecimalPlaceLimit));
					    	document.getElementById("txtSubTotValue").style.visibility = "hidden"; 
							document.getElementById("txtTaxTotValue").style.visibility = "hidden";  
					    	//$("#txtSubTotValue").val(parseFloat(0).toFixed(maxQuantityDecimalPlaceLimit));
							//$("#txtTaxTotValue").val(parseFloat(0).toFixed(maxQuantityDecimalPlaceLimit));
					    	
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
			
			function funTenderWiseProductDetail(ProdDtl)
			{
				$('#tblTenderProdDet tbody').empty();
				for(var i=0;i<ProdDtl.length;i++)
				{
				 var data=ProdDtl[i];
				 data[3]=parseFloat(data[3]).toFixed(maxQuantityDecimalPlaceLimit);
				
			    var table = document.getElementById("tblTenderProdDet");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    row.insertCell(0).innerHTML= "<input name=\"StrInvCode["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"20%\" id=\"StrInvCode."+(rowCount)+"\" value='"+data[0]+"'>";		    
			    row.insertCell(1).innerHTML= "<input name=\"DteInvDate["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"25%\" id=\"DteInvDate."+(rowCount)+"\" value='"+data[1]+"'>";
			    row.insertCell(2).innerHTML= "<input name=\"StrCustName["+(rowCount)+"]\" id=\"StrCustName."+(rowCount)+"\" readonly=\"readonly\"  size=\"14%\" class=\"Box\" value="+data[2]+">";
			    row.insertCell(3).innerHTML= "<input name=\"StrCurrency["+(rowCount)+"]\" id=\"StrCurrency."+(rowCount)+"\" readonly=\"readonly\"  size=\"10%\" class=\"Box\" value="+data[4]+">";
			    row.insertCell(4).innerHTML= "<input name=\"StrAgainst["+(rowCount)+"]\" id=\"StrAgainst."+(rowCount)+"\"readonly=\"readonly\"  style=\"text-align: right;\"  size=\"25%\" class=\"Box\"  value="+data[3]+">";
			    
			    funApplyNumberValidation();
				}
			}
			
			
			
			function funOnClckoperatorWiseBtn( divId)
			{
				funShowTableGUI(divId)
				var settCode=$('#cmbSettlement').val();
				var locCode=$('#txtLocCode').val();
				var custCode=$('#txtCustCode').val();
			
				//var frmDte1=$('#txtFromDate').val();
				//var toDte1=$('#txtToDate').val();
				var locCode1=$('#txtLocCode').val();
				var currencyCode=$('#cmbCurrency').val();
				var searchUrl=getContextPath()+"/loadOpertorWiseDtl.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
			
				$.ajax({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	funOperatorrWiseProductDetail(response[0],response[2]);
					    	$("#txtTotValue").val(parseFloat(response[1]).toFixed(maxQuantityDecimalPlaceLimit));
					    	document.getElementById("txtSubTotValue").style.visibility = "hidden"; 
							document.getElementById("txtTaxTotValue").style.visibility = "hidden";  
					    	
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
			
			function funOperatorrWiseProductDetail(ProdDtl,userWiseSalesAmt)
			{
				var userName="";
				$('#tblOpertorProdDet tbody').empty();
				var rowCount = "";
			    var row ="";
				var amt='';
				for(var i=0;i<ProdDtl.length;i++)
				{
				 var data=ProdDtl[i];
				 var nextProdDtl="";
				 var j=i+1;
				 if(j<ProdDtl.length)
					 {
					 var j=i+1;
					 nextProdDtl=ProdDtl[j];					 
					 }
				 else{
					 nextProdDtl=ProdDtl[i];
				 }
				var usercode="";
			
				$.each(userWiseSalesAmt, function(j,item)
						{
							
							if(j==data[0])
								{
								amt=item;
								}
						});
				
				data[2]=parseFloat(data[2]).toFixed(maxQuantityDecimalPlaceLimit);
				data[3]=parseFloat(data[3]).toFixed(maxQuantityDecimalPlaceLimit);
				amt=parseFloat(amt).toFixed(maxQuantityDecimalPlaceLimit);
			
			    var table = document.getElementById("tblOpertorProdDet");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    row.insertCell(0).innerHTML= "<input name=\"OperatorCode["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"20%\" id=\"OperatorCode."+(rowCount)+"\" value='"+data[0]+"'>";		    
			    row.insertCell(1).innerHTML= "<input name=\"OperatorName["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"25%\" id=\"OperatorName."+(rowCount)+"\" value='"+data[1]+"'>";
			    row.insertCell(2).innerHTML= "<input name=\"currency["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"10%\" id=\"currency."+(rowCount)+"\" value='"+data[5]+"'>";
			    row.insertCell(3).innerHTML= "<input name=\"SalesAmt["+(rowCount)+"]\" id=\"SalesAmt."+(rowCount)+"\"readonly=\"readonly\" style=\"text-align: right;\"  size=\"14%\" class=\"Box\" value="+data[2]+">";
			    row.insertCell(4).innerHTML= "<input name=\"DiscAmt["+(rowCount)+"]\" id=\"DiscAmt."+(rowCount)+"\" readonly=\"readonly\"  style=\"text-align: right;\"  size=\"14%\" class=\"Box\"  value="+data[3]+">";
			    row.insertCell(5).innerHTML= "<input name=\"PaymentMode["+(rowCount)+"]\" id=\"PaymentMode."+(rowCount)+"\" readonly=\"readonly\"  style=\"text-align: right;\"  size=\"14%\" class=\"Box\"  value="+data[4]+">";
			   
			    if(data[0]==nextProdDtl[0])
			    {    
				if(i==(ProdDtl.length-1))
					{
					 row.insertCell(6).innerHTML= "<input name=\"operatorTotl["+(rowCount)+"]\" id=\"operatorTotl."+(rowCount)+"\" readonly=\"readonly\"  style=\"text-align: right;\"  size=\"14%\" class=\"Box\"  value="+amt+">";
					}else{
			    	row.insertCell(6).innerHTML= "<input name=\"operatorTotl["+(rowCount)+"]\" id=\"operatorTotl."+(rowCount)+"\" readonly=\"readonly\"  style=\"text-align: right;\"  size=\"14%\" class=\"Box\"  value="+usercode+">";
			    }}
			    else{
 			    row.insertCell(6).innerHTML= "<input name=\"operatorTotl["+(rowCount)+"]\" id=\"operatorTotl."+(rowCount)+"\" readonly=\"readonly\"  style=\"text-align: right;\"  size=\"14%\" class=\"Box\"  value="+amt+">";
			    }
					}
				
				  funApplyNumberValidation();
			}
			
			
			
			function funOnClckCuctomerWiseBtn( divId)
			{
				funShowTableGUI(divId)
				var settCode=$('#cmbSettlement').val();
				var locCode=$('#txtLocCode').val();
				var custCode=$('#txtCustCode').val();
			
				//var frmDte1=$('#txtFromDate').val();
				//var toDte1=$('#txtToDate').val();
				var locCode1=$('#txtLocCode').val();
				var currencyCode=$('#cmbCurrency').val();
				var searchUrl=getContextPath()+"/loadCustomerWiseDtl.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
			
				$.ajax({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	funcustomerWiseProductDetail(response[0]);
					    	$("#txtTotValue").val(parseFloat(response[1]).toFixed(maxQuantityDecimalPlaceLimit));
					    	document.getElementById("txtSubTotValue").style.visibility = "hidden"; 
							document.getElementById("txtTaxTotValue").style.visibility = "hidden";
					    	
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
			
			function funcustomerWiseProductDetail(ProdDtl)
			{
				var userName="";
				$('#tblCustomerProdDet tbody').empty();
				var rowCount = "";
			    var row ="";
				var amt='';
				for(var i=0;i<ProdDtl.length;i++)
				{
				 var data=ProdDtl[i];
				
				 data[4]=parseFloat(data[4]).toFixed(maxQuantityDecimalPlaceLimit);
				
			    var table = document.getElementById("tblCustomerProdDet");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    var formName='frmCustomerWiseInvoiceFlashForInvoice';
				row.insertCell(0).innerHTML= "<a  name=\"CustomerCode["+(rowCount)+"]\" readonly=\"readonly\" href="+formName+"\.html?code="+data[0]+"&fromDate="+frmDte1+"&toDate="+toDte1+"&locCode="+$('#txtLocCode').val()+"&settleCode="+$('#cmbSettlement').val()+"&currencyCode="+$('#cmbCurrency').val()+"\ target=\"_blank\"  class=\"Box\" size=\"20%\" id=\"CustomerCode."+(rowCount)+"\" >"+data[0]+"</a>";			    
			    row.insertCell(1).innerHTML= "<input name=\"CustomerName["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"25%\" id=\"CustomerName."+(rowCount)+"\" value='"+data[1]+"'>";
			    row.insertCell(2).innerHTML= "<input name=\"CustomerType["+(rowCount)+"]\" id=\"CustomerType."+(rowCount)+"\" readonly=\"readonly\"  size=\"14%\" class=\"Box\" value="+data[2]+">";
			    row.insertCell(3).innerHTML= "<input name=\"NoOfBills["+(rowCount)+"]\" id=\"NoOfBills."+(rowCount)+"\" readonly=\"readonly\"  style=\"text-align: right;\"  size=\"14%\" class=\"Box\"  value="+data[3]+">";
			    row.insertCell(4).innerHTML= "<input name=\"currency["+(rowCount)+"]\" id=\"currency."+(rowCount)+"\" readonly=\"readonly\"  style=\"text-align: right;\"  size=\"10%\" class=\"Box\"  value="+data[5]+">";
			    row.insertCell(5).innerHTML= "<input name=\"SalesAmt["+(rowCount)+"]\" id=\"SalesAmt."+(rowCount)+"\" readonly=\"readonly\"  style=\"text-align: right;\"  size=\"14%\" class=\"Box\"  value="+data[4]+">";
			   
				}
				
				  funApplyNumberValidation();
			}
			
			
			function funOnClckSKUWiseBtn( divId)
			{
				funShowTableGUI(divId)
				var settCode=$('#cmbSettlement').val();
				var locCode=$('#txtLocCode').val();
				var custCode=$('#txtCustCode').val();
			
				//var frmDte1=$('#txtFromDate').val();
				//var toDte1=$('#txtToDate').val();
				var locCode1=$('#txtLocCode').val();
				var currencyCode=$('#cmbCurrency').val();
				var searchUrl=getContextPath()+"/loadSKUWiseDtl.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
			
				$.ajax({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	funSKUWiseProductDetail(response[0]);
					    	$("#txtTotValue").val(parseFloat(response[1]).toFixed(maxQuantityDecimalPlaceLimit));
					    	document.getElementById("txtTaxTotValue").style.visibility = "visible";
					    	$("#txtTaxTotValue").val(parseFloat(response[2]).toFixed(maxQuantityDecimalPlaceLimit));
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
			
			function funSKUWiseProductDetail(ProdDtl)
			{
				var userName="";
				$('#tblSKUProdDet tbody').empty();
				var rowCount = "";
			    var row ="";
				var amt='';
				for(var i=0;i<ProdDtl.length;i++)
				{
				 var data=ProdDtl[i];
				
				 data[2]=parseFloat(data[2]).toFixed(maxQuantityDecimalPlaceLimit);
				 data[3]=parseFloat(data[3]).toFixed(maxQuantityDecimalPlaceLimit);
				 
			    var table = document.getElementById("tblSKUProdDet");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    var formName='frmProductWiseFlashForInvoice';
			    
			    row.insertCell(0).innerHTML= "<a  name=\"SKUCode["+(rowCount)+"]\" readonly=\"readonly\" href="+formName+"\.html?code="+data[0]+"&fromDate="+frmDte1+"&toDate="+toDte1+"&locCode="+$('#txtLocCode').val()+"&custCode="+$('#txtCustCode').val()+"&settleCode="+$('#cmbSettlement').val()+"&reportName=productwise"+"&currencyCode="+$('#cmbCurrency').val()+"\ target=\"_blank\"  class=\"Box\" size=\"20%\" id=\"SKUCode."+(rowCount)+"\"  >"+data[0]+"</a>";			    
			    row.insertCell(1).innerHTML= "<input name=\"SKUName["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"25%\" id=\"SKUName."+(rowCount)+"\" value='"+data[1]+"'>";
			    row.insertCell(2).innerHTML= "<input name=\"currency["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"10%\" id=\"currency."+(rowCount)+"\" value='"+data[4]+"'>";
			    row.insertCell(3).innerHTML= "<input name=\"SKUQty["+(rowCount)+"]\" id=\"SKUQty."+(rowCount)+"\"readonly=\"readonly\" style=\"text-align: right;\" size=\"18%\" class=\"Box\" value="+data[2]+">";
			    row.insertCell(4).innerHTML= "<input name=\"SKUAmount["+(rowCount)+"]\" id=\"SKUAmount."+(rowCount)+"\" readonly=\"readonly\"  style=\"text-align: right;\"  size=\"14%\" class=\"Box\"  value="+data[3]+">";
			    
			    
			   }
				
				funApplyNumberValidation();
			}
			
			
		
			function funOnClckCategoryWiseBtn( divId)
			{
				funShowTableGUI(divId)
				var settCode=$('#cmbSettlement').val();
				var locCode=$('#txtLocCode').val();
				var custCode=$('#txtCustCode').val();
			
				//var frmDte1=$('#txtFromDate').val();
				//var toDte1=$('#txtToDate').val();
				var locCode1=$('#txtLocCode').val();
				var currencyCode=$('#cmbCurrency').val();
				var searchUrl=getContextPath()+"/loadCategoryWiseDtl.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&withZeroAmt="+$("#cmbWithDisAmt").val()+"&custCode="+custCode+"&currencyCode="+currencyCode;
			
				$.ajax({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	funCategoryWiseProductDetail(response[0],response[1]);
					    	
					    	//$("#txtTotValue").val(parseFloat(response[1]).toFixed(maxQuantityDecimalPlaceLimit));
					    	document.getElementById("txtSubTotValue").style.visibility = "visible"; 
							document.getElementById("txtTaxTotValue").style.visibility = "visible";
					    	funGetTotalValue(response[1],response[3],response[2]);
					    	
					    	
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
			
			function funCategoryWiseProductDetail(ProdDtl,grandTotal)
			{
				var userName="";
				$('#tblCategoryProdDet tbody').empty();
				var rowCount = "";
			    var row ="";
				var amt='';
				for(var i=0;i<ProdDtl.length;i++)
				{
				 var data=ProdDtl[i];
				  
				/* if ($("#cmbWithDisAmt").val() == "Yes")
					{
					 data[3]=parseFloat(data[3])-parseFloat(data[4]);	
					}
				*/
				 data[2]=parseFloat(data[2]).toFixed(maxQuantityDecimalPlaceLimit);
				 data[3]=parseFloat(data[3]).toFixed(maxQuantityDecimalPlaceLimit);
				 data[4]=parseFloat(data[4]).toFixed(maxQuantityDecimalPlaceLimit);
				
				 var salesPer=Math.round((parseFloat(data[3])/parseFloat(grandTotal))*100)+"%";
					
			    var table = document.getElementById("tblCategoryProdDet");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    row.insertCell(0).innerHTML= "<input name=\"CategoryCode["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"20%\" id=\"CategoryCode."+(rowCount)+"\" value='"+data[0]+"'>";		    
			    row.insertCell(1).innerHTML= "<input name=\"CategoryName["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"25%\" id=\"CategoryName."+(rowCount)+"\" value='"+data[1]+"'>";
			    row.insertCell(2).innerHTML= "<input name=\"Currency["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"10%\" id=\"Currency."+(rowCount)+"\" value='"+data[5]+"'>";
			    row.insertCell(3).innerHTML= "<input name=\"CategoryQty["+(rowCount)+"]\" id=\"CategoryQty."+(rowCount)+"\" readonly=\"readonly\" size=\"16%\" style=\"text-align: right;\" class=\"Box\" value="+data[2]+">";
			    row.insertCell(4).innerHTML= "<input name=\"CategoryDisAmount["+(rowCount)+"]\" id=\"CategoryDisAmount."+(rowCount)+"\" readonly=\"readonly\"  style=\"text-align: right;\"  size=\"14%\" class=\"Box\"  value="+data[4]+">";
			    row.insertCell(5).innerHTML= "<input name=\"CategoryAmount["+(rowCount)+"]\" id=\"CategoryAmount."+(rowCount)+"\" readonly=\"readonly\"  style=\"text-align: right;\"  size=\"14%\" class=\"Box\"  value="+data[3]+">";
			    row.insertCell(6).innerHTML= "<input name=\"CategorySalesPer["+(rowCount)+"]\" id=\"CategorySalesPer."+(rowCount)+"\" readonly=\"readonly\"  style=\"text-align: right;\"  size=\"14%\" class=\"Box\"  value="+salesPer+">";
			    }
				
				  funApplyNumberValidation();
			}
			
		
			function funOnClckManufactureWiseBtn( divId)
			{
				funShowTableGUI(divId)
				var settCode=$('#cmbSettlement').val();
				var locCode=$('#txtLocCode').val();
				var custCode=$('#txtCustCode').val();
			
				//var frmDte1=$('#txtFromDate').val();
				//var toDte1=$('#txtToDate').val();
				var locCode1=$('#txtLocCode').val();
				var currencyCode=$('#cmbCurrency').val();
				var searchUrl=getContextPath()+"/loadManufactureWiseDtl.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
			
				$.ajax({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	funManufactureWiseProductDetail(response[0]);
					    	$("#txtTotValue").val(parseFloat(response[1]).toFixed(maxQuantityDecimalPlaceLimit));
					    	document.getElementById("txtTaxTotValue").style.visibility = "visible";
					    	$("#txtTaxTotValue").val(parseFloat(response[2]).toFixed(maxQuantityDecimalPlaceLimit));
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
			
			function funManufactureWiseProductDetail(ProdDtl)
			{
				var userName="";
				$('#tblManufactureProdDet tbody').empty();
				var rowCount = "";
			    var row ="";
				var amt='';
				for(var i=0;i<ProdDtl.length;i++)
				{
				 var data=ProdDtl[i];
				 data[3]=parseFloat(data[3]).toFixed(maxQuantityDecimalPlaceLimit);
				 
			    var table = document.getElementById("tblManufactureProdDet");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    row.insertCell(0).innerHTML= "<input name=\"ManufactureCode["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"20%\" id=\"ManufactureCode."+(rowCount)+"\" value='"+data[0]+"'>";		    
			    row.insertCell(1).innerHTML= "<input name=\"ManufactureName["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"25%\" id=\"ManufactureName."+(rowCount)+"\" value='"+data[1]+"'>";
			    row.insertCell(2).innerHTML= "<input name=\"Currency["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"10%\" id=\"Currency."+(rowCount)+"\" value='"+data[4]+"'>";
			    row.insertCell(3).innerHTML= "<input name=\"ManufactureQty["+(rowCount)+"]\" id=\"ManufactureQty."+(rowCount)+"\" readonly=\"readonly\"  size=\"18%\" style=\"text-align: right;\" class=\"Box\" value="+data[2]+">";
			    row.insertCell(4).innerHTML= "<input name=\"ManufactureAmount["+(rowCount)+"]\" id=\"ManufactureAmount."+(rowCount)+"\" readonly=\"readonly\"  style=\"text-align: right;\"  size=\"14%\" class=\"Box\"  value="+data[3]+">";
			    
			   
					}
				
				  funApplyNumberValidation();
			}
			
			
			function funOnClckDepartmentWiseBtn( divId)
			{
				funShowTableGUI(divId)
				var settCode=$('#cmbSettlement').val();
				var locCode=$('#txtLocCode').val();
				if(locCode=="")
					{
					locCode="All";
					}
				
				var custCode=$('#txtCustCode').val();
				//var frmDte1=$('#txtFromDate').val();
				//var toDte1=$('#txtToDate').val();
				var locCode1=$('#txtLocCode').val();
				var currencyCode=$('#cmbCurrency').val();
				var searchUrl=getContextPath()+"/loadDepartmentWiseDtl.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
			
				$.ajax({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	funDepartmentWiseProductDetail(response[0]);
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
			
			function funDepartmentWiseProductDetail(ProdDtl)
			{
				var userName="";
				$('#tblDepartmentProdDet tbody').empty();
				var rowCount = "";
			    var row ="";
				var amt='';
				for(var i=0;i<ProdDtl.length;i++)
				{
				 var data=ProdDtl[i];
				 data[3]=parseFloat(data[3]).toFixed(maxQuantityDecimalPlaceLimit);
				 	
				
			    var table = document.getElementById("tblDepartmentProdDet");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    row.insertCell(0).innerHTML= "<input name=\"locationCode["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"20%\" id=\"locationCode."+(rowCount)+"\" value='"+data[0]+"'>";		    
			    row.insertCell(1).innerHTML= "<input name=\"locationName["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"25%\" id=\"locationName."+(rowCount)+"\" value='"+data[1]+"'>";
			    row.insertCell(2).innerHTML= "<input name=\"currency["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"10%\" id=\"currency."+(rowCount)+"\" value='"+data[4]+"'>";
			    row.insertCell(3).innerHTML= "<input name=\"locationQty["+(rowCount)+"]\" id=\"locationQty."+(rowCount)+"\" readonly=\"readonly\"  size=\"18%\" style=\"text-align: right;\" class=\"Box\" value="+data[2]+">";
			    row.insertCell(4).innerHTML= "<input name=\"locationAmount["+(rowCount)+"]\" id=\"locationAmount."+(rowCount)+"\" readonly=\"readonly\" style=\"text-align: right;\"  size=\"14%\" class=\"Box\"  value="+data[3]+">";
			    
			   
					}
				
				  funApplyNumberValidation();
			}
			
			function funOnClickMonthWiseBtn( divId)
			{
				funShowTableGUI(divId)
				var settCode=$('#cmbSettlement').val();
				var locCode=$('#txtLocCode').val();
				var locCode1=$('#txtLocCode').val();
				var custCode=$('#txtCustCode').val();
				var currencyCode=$('#cmbCurrency').val();
				var searchUrl=getContextPath()+"/loadMonthWiseDtl.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
			
				$.ajax({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	funMonthWiseProductDetail(response[0]);
					    	$("#txtTotValue").val(parseFloat(response[1]).toFixed(maxQuantityDecimalPlaceLimit));
					    	document.getElementById("txtSubTotValue").style.visibility = "hidden"; 
							document.getElementById("txtTaxTotValue").style.visibility = "hidden"; 
					    	
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
			
			function funMonthWiseProductDetail(ProdDtl)
			{
				$('#tblMonthProdDet tbody').empty();
				for(var i=0;i<ProdDtl.length;i++)
				{
				 var data=ProdDtl[i];
				 data[3]=parseFloat(data[3]).toFixed(maxQuantityDecimalPlaceLimit);
				
			    var table = document.getElementById("tblMonthProdDet");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    row.insertCell(0).innerHTML= "<input name=\"DteInvDate["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"25%\" id=\"DteInvDate."+(rowCount)+"\" value='"+data[1]+"'>";
			    row.insertCell(1).innerHTML= "<input name=\"StrCustName["+(rowCount)+"]\" id=\"StrCustName."+(rowCount)+"\" readonly=\"readonly\"  size=\"14%\" class=\"Box\" value="+data[2]+">";
			    row.insertCell(2).innerHTML= "<input name=\"StrMonth["+(rowCount)+"]\" id=\"StrMonth."+(rowCount)+"\"readonly=\"readonly\"  style=\"text-align: left;\"  size=\"25%\" class=\"Box\"  value="+data[4]+">"; 
			    row.insertCell(3).innerHTML= "<input name=\"StrYear["+(rowCount)+"]\" id=\"StrYear."+(rowCount)+"\"readonly=\"readonly\"  style=\"text-align: left;\"  size=\"25%\" class=\"Box\"  value="+data[5]+">";
			    row.insertCell(4).innerHTML= "<input name=\"strCurrency["+(rowCount)+"]\" id=\"strCurrency."+(rowCount)+"\"readonly=\"readonly\"  style=\"text-align: left;\"  size=\"10%\" class=\"Box\"  value="+data[6]+">";
			    row.insertCell(5).innerHTML= "<input name=\"strAmt["+(rowCount)+"]\" id=\"strAmt."+(rowCount)+"\" readonly=\"readonly\"  style=\"text-align: right;\" size=\"14%\" class=\"Box\" value="+data[3]+">";
			    
			    funApplyNumberValidation();
				}
			}
			
			
			function funOnClickRegionWiseBtn( divId)
			{
				funShowTableGUI(divId)
				var settCode=$('#cmbSettlement').val();
				var locCode=$('#txtLocCode').val();
				var locCode1=$('#txtLocCode').val();
				var custCode=$('#txtCustCode').val();
				var currencyCode=$('#cmbCurrency').val();
				var searchUrl=getContextPath()+"/loadRegionWiseDtl.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
			
				$.ajax({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	funRegionWiseProductDetail(response[0]);
					    	$("#txtTotValue").val(parseFloat(response[1]).toFixed(maxQuantityDecimalPlaceLimit));
					    	document.getElementById("txtSubTotValue").style.visibility = "hidden"; 
							document.getElementById("txtTaxTotValue").style.visibility = "hidden"; 
					    	
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
			
			function funRegionWiseProductDetail(ProdDtl)
			{
				$('#tblRegionDet tbody').empty();
				for(var i=0;i<ProdDtl.length;i++)
				{
				 var data=ProdDtl[i];
				 data[2]=parseFloat(data[2]).toFixed(maxQuantityDecimalPlaceLimit);
				
			    var table = document.getElementById("tblRegionDet");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    var formName='frmProductWiseFlashForInvoice';
				row.insertCell(0).innerHTML= "<a  name=\"strRegionCode["+(rowCount)+"]\" readonly=\"readonly\" href="+formName+"\.html?code="+data[0]+"&fromDate="+frmDte1+"&toDate="+toDte1+"&locCode="+$('#txtLocCode').val()+"&custCode="+$('#txtCustCode').val()+"&settleCode="+$('#cmbSettlement').val()+"&reportName=regionwise"+"&currencyCode="+$('#cmbCurrency').val()+"\ target=\"_blank\"  class=\"Box\" size=\"20%\"id=\"strRegionCode."+(rowCount)+"\" >"+data[0]+"</a>";	
			    row.insertCell(1).innerHTML= "<input name=\"strRegionName["+(rowCount)+"]\" id=\"strRegionName."+(rowCount)+"\" readonly=\"readonly\"  size=\"14%\" class=\"Box\" value="+data[1]+">";
			    row.insertCell(2).innerHTML= "<input name=\"strCurrency["+(rowCount)+"]\" id=\"strCurrency."+(rowCount)+"\" readonly=\"readonly\"  size=\"14%\" class=\"Box\" value="+data[3]+">";
			    row.insertCell(3).innerHTML= "<input name=\"strAmount["+(rowCount)+"]\" id=\"strAmount."+(rowCount)+"\" readonly=\"readonly\"  style=\"text-align: right;\" size=\"14%\" class=\"Box\" value="+data[2]+">";
			    
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
				
				if(hidReportName=='divBillWise')
				 {
					window.location.href = getContextPath()+"/exportBillWiseInvoiceFlash.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
				 }
				else if(hidReportName=='divTenderWise')
				 {
					window.location.href = getContextPath()+"/exportSettlementWiseInvoiceFlash.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
				 }
				else if(hidReportName=='divOperatorWise')
				 {
					window.location.href = getContextPath()+"/exportOperatorWiseInvoiceFlash.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
				 }
				else if(hidReportName=='divCustomerWise')
				 {
					window.location.href = getContextPath()+"/exportCustomerWiseInvoiceFlash.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
				 }
				else if(hidReportName=='divSKUWise')
				 {
					window.location.href = getContextPath()+"/exportProductWiseInvoiceFlash.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
				 }
				else if(hidReportName=='divCategoryWise')
				 {
					window.location.href = getContextPath()+"/exportGroupSubGroupWiseInvoiceFlash.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&withZeroAmt="+$("#cmbWithDisAmt").val()+"&custCode="+custCode+"&currencyCode="+currencyCode;
				 }
				else if(hidReportName=='divManufactureWise')
				 {
					window.location.href = getContextPath()+"/exportManufactureWiseInvoiceFlash.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
				 }
				else if(hidReportName=='divDepartmentWise')
				 {
					window.location.href = getContextPath()+"/exportDepartmentWiseInvoiceFlash.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
				 }
				else if(hidReportName=='divMonthWise')
				 {
					window.location.href = getContextPath()+"/exportMonthWiseInvoiceFlash.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
				 }
				else if(hidReportName=='divRegionWise')
				 {
					window.location.href = getContextPath()+"/exportRegionWiseInvoiceFlash.html?settlementcode="+settCode+"&frmDte="+frmDte1+"&toDte="+toDte1+"&locCode="+locCode+"&custCode="+custCode+"&currencyCode="+currencyCode;
				 }
				
				
		 	}
		 	
			function funResetCustomer()
		 	{
				$("#txtCustCode").val("All");
				$("#lblCustName").text("");
		 	}
		 	 
			
			function funOpenWindow(obj)
			{   if(hidReportName=='divSKUWise')
				 {
				   window.location.href=getContextPath()+"/frmProductWiseFlashForInvoice.html?code="+obj.value+"&fromDate="+frmDte1+"&toDate="+toDte1+"&locCode="+$('#txtLocCode').val()+"&custCode="+$('#txtCustCode').val()+"&settleCode="+$('#cmbSettlement').val()+"&reportName=productwise";
				 }
			    else if(hidReportName=='divRegionWise')
				 {
			    	window.location.href=getContextPath()+"/frmProductWiseFlashForInvoice.html?code="+obj.value+"&fromDate="+frmDte1+"&toDate="+toDte1+"&locCode="+$('#txtLocCode').val()+"&custCode="+$('#txtCustCode').val()+"&settleCode="+$('#cmbSettlement').val()+"&reportName=regionwise"  ;
				 }   
			    else if(hidReportName=='divCustomerWise')
				 {
			    	window.location.href=getContextPath()+"/frmCustomerWiseInvoiceFlashForInvoice.html?code="+obj.value+"&fromDate="+frmDte1+"&toDate="+toDte1+"&locCode="+$('#txtLocCode').val()+"&settleCode="+$('#cmbSettlement').val()  ;
				 } 
			
			}
			
			
		</script>
</head>

<body>
	<div id="formHeading">
		<label>Invoice Flash</label>
	</div>
	<s:form name="Form" method="GET" action="">
		<br />

		<table class="transTable">
			<tr>
				<td><label id="lblFromDate">From Date</label></td>
				<td><s:input id="txtFromDate" required="required" path=""
						name="fromDate" cssClass="calenderTextBox" onchange="funChangeFromDate();"  /></td>
				<td><label id="lblToDate">To Date</label>&nbsp; &nbsp;&nbsp;
				<s:input id="txtToDate" name="toDate" path=""
						cssClass="calenderTextBox" onchange="funChangeToDate();" />
			    </td>			
				<td><label id="">Settlement</label> &nbsp; &nbsp;&nbsp; &nbsp;
				<s:select id="cmbSettlement" path="strSettlementCode" items="${settlementList}" cssClass="BoxW124px">
			    </s:select></td> 	
				<td><input id="btnExport" type="button" value="EXPORT"  class="form_button1" onclick="funExportReport()" />
				    <input id="btnReset" type="button" value="RESET"  class="form_button1" onclick="funResetCustomer()" />
				</td>	
				<td></td>
				<td></td>
				<td></td>
				
			</tr>
			<tr>
				<td><label>Location Code</label></td>
				<td><s:input type="text" id="txtLocCode" path="strLocCode"
						cssClass="searchTextBox" ondblclick="funHelp('locationmaster');" /></td>
				<td ><label id="lblLocName"></label></td>
				<td ><label id="">Customer Code</label> &nbsp;
				<s:input type="text" id="txtCustCode" path="strCustCode"
						cssClass="searchTextBox" ondblclick="funHelp('custMaster');" value="All"/>
						<label id="lblCustName"></label></td> 	
				
				<td><s:input type="hidden" id="hidReportName" path=""></s:input></td>		
				<td></td>	
				<td></td>
				<td></td>
			</tr>
			
            <tr>
               <td><label id="">Currency</label></td>
				<td><s:select id="cmbCurrency" path="strCurrencyCode" items="${currencyList}" cssClass="BoxW124px">
			    </s:select></td> 
			   <td><label id="lblWithDisAmt">With Discount Amt</label></td>
			   <td><s:select id="cmbWithDisAmt" path="" cssClass="BoxW116px">
			    		<s:option value="Yes">Yes</s:option><s:options/>
			    		<s:option value="No">No</s:option><s:options/>
			    	     </s:select>
			   </td>
			    <td></td>
			    <td></td>
			    <td></td>
			    <td></td>
            </tr>


		</table>



<br/>
		<div id="divBillWise" class="dynamicTableContainer"
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




		<div id="divTenderWise" class="dynamicTableContainer"
			style="height: 400px;">
			<table style="width: 100%; border: #0F0; table-layout: fixed;"
				class="transTablex col15-center">
				<tr bgcolor="#72BEFC">
					<td width="8%">Settlement Code</td>
					<!--  COl1   -->
					<td width="8%">Settlement Name</td>
					<!--  COl2   -->

					<td width="4.9%">Settlement Type</td>
					<!--  COl3   -->
					<td width="4.8%">Currency</td>
					<!--  COl4   -->
					<td width="5.3%">Sales Amount</td>
					<!-- COl5   -->

				</tr>
			</table>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 330px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblTenderProdDet"
					style="width: 100%; border: #0F0; table-layout: fixed;"
					class="transTablex col15-center">
					<tbody>
					<col style="width: 7.5%">
					<!--  COl1   -->
					<col style="width: 7.5%">
					<!--  COl2   -->
					<col style="width: 4.5%">
					<!--  COl3   -->
					<col style="width: 4.5%">
					<!--  COl4   -->
					<col style="width: 4.5%">
					<!--  COl5   -->



					</tbody>

				</table>
			</div>

		</div>




		<div id="divOperatorWise" class="dynamicTableContainer"
			style="height: 400px;">
			<table style="width: 100%; border: #0F0; table-layout: fixed;"
				class="transTablex col15-center">
				<tr bgcolor="#72BEFC">
					<td width="8%">User Code</td>
					<!--  COl1   -->
					<td width="8%">User Name</td>
					<!--  COl2   -->
					<td width="5%">Currency</td>
					<!--  COl3   -->
					<td width="4.8%">Sales Amount</td>
					<!-- COl4  -->

					<td width="4.9%">Discount Amt</td>
					<!--  COl5   -->
					<td width="4.8%">Settlement Mode</td>
					<!--  COl6   -->
					<td width="5.5%">Operator total</td>
					<!--  COl7   -->
					

				</tr>
			</table>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 330px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblOpertorProdDet"
					style="width: 100%; border: #0F0; table-layout: fixed;"
					class="transTablex col15-center">
					<tbody>
					<col style="width: 7.5%">
					<!--  COl1   -->
					<col style="width: 7.5%">
					<!--  COl2   -->
					<col style="width: 4.5%">
					<!--  COl3   -->
					<col style="width: 4.5%">
					<!--  COl4   -->
					<col style="width: 4.5%">
					<!--  COl5   -->
					<col style="width: 4.5%">
					<!--  COl6   -->
					<col style="width: 4.5%">
					<!--  COl7   -->
					


					</tbody>

				</table>
			</div>

		</div>



	<div id="divCustomerWise" class="dynamicTableContainer"
			style="height: 400px;">
			<table style="width: 100%; border: #0F0; table-layout: fixed;"
				class="transTablex col15-center">
				<tr bgcolor="#72BEFC">
					<td width="4%">Customer Code</td>
					<!--  COl1   -->
					<td width="12%">Customer Name</td>
					<!--  COl2   -->
					<td width="5%">Customer Type</td>
					<!-- COl3  -->
					<td width="4.8%">No Of Bills</td>
					<!--  COl4   -->
					<td width="4.9%">Currency</td>
					<!--  COl5   -->
					<td width="5.3%">Sales Amount</td>
					<!--  COl6   -->

				</tr>
			</table>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 330px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblCustomerProdDet"
					style="width: 100%; border: #0F0; table-layout: fixed;"
					class="transTablex col15-center">
					<tbody>
					<col style="width: 3.7%">
					<!--  COl1   -->
					<col style="width: 11.2%">
					<!--  COl2   -->
					<col style="width: 4.5%">
					<!--  COl3   -->
					<col style="width: 4.5%">
					<!--  COl4   -->
					<col style="width: 4.5%">
					<!--  COl5   -->
					<col style="width: 4.5%">
					<!--  COl6   -->
				
					</tbody>

				</table>
			</div>

		</div>


	<div id="divSKUWise" class="dynamicTableContainer"
			style="height: 400px;">
			<table style="width: 100%; border: #0F0; table-layout: fixed;"
				class="transTablex col15-center">
				<tr bgcolor="#72BEFC">
					<td width="3.1%">Product Code</td>
					<!--  COl1   -->
					<td width="9.3%">Product Name</td>
					<!--  COl2   -->
					<td width="4.8%">Currency</td>
					<!-- COl3  -->
					<td width="5%">Quantity</td>
					<!-- COl4  -->
					<td width="5.2%">Sales Amount</td>
					<!--  COl5   -->
					

				</tr>
			</table>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 330px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblSKUProdDet"
					style="width: 100%; border: #0F0; table-layout: fixed;"
					class="transTablex col15-center">
					<tbody>
					<col style="width: 3.5%">
					<!--  COl1   -->
					<col style="width: 10.5%">
					<!--  COl2   -->
					<col style="width: 5.5%">
					<!--  COl3   -->
					<col style="width: 5.5%">
					<!--  COl4   -->
					<col style="width: 5.5%">
					<!--  COl5   -->
				
					
				
					</tbody>

				</table>
			</div>

		</div>


	<div id="divCategoryWise" class="dynamicTableContainer"
			style="height: 400px;">
			<table style="width: 100%; border: #0F0; table-layout: fixed;"
				class="transTablex col15-center">
				<tr bgcolor="#72BEFC">
					<td width="8%">Group Name</td>
					<!--  COl1   -->
					<td width="8%">SubGroup Name</td>
					<!--  COl2   -->
					<td width="5%">Currency</td>
					<!--  COl3   -->
					<td width="4.9%">Quantity</td>
					<!-- COl4  -->
					<td width="4.8%">Dis Amount</td>
					<!--  COl5   -->
					<td width="4.9%">Sales Amount</td>
					<!--  COl6   -->
					<td width="5.4%">Sales Per</td>
					<!--  COl7   -->
					

				</tr>
			</table>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 330px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblCategoryProdDet"
					style="width: 100%; border: #0F0; table-layout: fixed;"
					class="transTablex col15-center">
					<tbody>
					<col style="width: 7.5%">
					<!--  COl1   -->
					<col style="width: 7.5%">
					<!--  COl2   -->
					<col style="width: 4.5%">
					<!--  COl3   -->
					<col style="width: 4.5%">
					<!--  COl4   -->
					<col style="width: 4.5%">
					<!--  COl5   -->
					<col style="width: 4.5%">
					<!--  COl6   -->
					<col style="width: 4.5%">
					<!--  COl7   -->
				
					</tbody>

				</table>
			</div>

		</div>


	<div id="divManufactureWise" class="dynamicTableContainer"
			style="height: 400px;">
			<table style="width: 100%; border: #0F0; table-layout: fixed;"
				class="transTablex col15-center">
				<tr bgcolor="#72BEFC">
					<td width="8%">Category Code</td>
					<!--  COl1   -->
					<td width="8%">Category Name</td>
					<!--  COl2   -->
					<td width="5%">Currency</td>
					<!--  COl3   -->
					<td width="5%">Quantity</td>
					<!-- COl4  -->
					<td width="5%">Sales Amount</td>
					<!--  COl5   -->
					

				</tr>
			</table>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 330px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblManufactureProdDet"
					style="width: 100%; border: #0F0; table-layout: fixed;"
					class="transTablex col15-center">
					<tbody>
					<col style="width: 7.5%">
					<!--  COl1   -->
					<col style="width: 7.5%">
					<!--  COl2   -->
					<col style="width: 4.5%">
					<!--  COl3   -->
					<col style="width: 4.5%">
					<!--  COl4   -->
					<col style="width: 4.5%">
					<!--  COl5   -->
				
				
					</tbody>

				</table>
			</div>

		</div>
		
		<div id="divDepartmentWise" class="dynamicTableContainer"
			style="height: 400px;">
			<table style="width: 100%; border: #0F0; table-layout: fixed;"
				class="transTablex col15-center">
				<tr bgcolor="#72BEFC">
					<td width="8%">Department Code</td>
					<!--  COl1   -->
					<td width="8%">Department Name</td>
					<!--  COl2   -->
					<td width="4.9%">Currency</td>
					<!--  COl3   -->
					<td width="4.8%">Quantity</td>
					<!-- COl4  -->
					<td width="5.3%">Sales Amount</td>
					<!--  COl5   -->
					

				</tr>
			</table>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 330px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblDepartmentProdDet"
					style="width: 100%; border: #0F0; table-layout: fixed;"
					class="transTablex col15-center">
					<tbody>
					<col style="width: 7.5%">
					<!--  COl1   -->
					<col style="width: 7.5%">
					<!--  COl2   -->
					<col style="width: 4.5%">
					<!--  COl3   -->
					<col style="width: 4.5%">
					<!--  COl4   -->
					<col style="width: 4.5%">
					<!--  COl5   -->
				
				
					</tbody>

				</table>
			</div>

		</div>
		
	    <div id="divMonthWise" class="dynamicTableContainer"
			style="height: 400px;">
			<table style="width: 100%; border: #0F0; table-layout: fixed;"
				class="transTablex col15-center">
				<tr bgcolor="#72BEFC">
				    <td width="8%">Settlement Name</td>
					<!--  COl1   -->
					<td width="5%">Settlement Type</td>
					<!--  COl2   -->
					<td width="4.9%">Month Name</td>
					<!--  COl3   -->
					<td width="4.9%">Year</td>
					<!--  COl4   -->
					<td width="4.9%">Currency</td>
					<!--  COl5   -->
					<td width="5.4%">Sales Amount</td>
					<!-- COl6   -->

				</tr>
			</table>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 330px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblMonthProdDet"
					style="width: 100%; border: #0F0; table-layout: fixed;"
					class="transTablex col15-center">
					<tbody>
					<col style="width: 7.5%">
					<!--  COl1   -->
					<col style="width: 4.5%">
					<!--  COl2   -->
					<col style="width: 4.5%">
					<!--  COl3   -->
					<col style="width: 4.5%">
					<!--  COl4   -->
					<col style="width: 4.5%">
					<!--  COl5   -->
					<col style="width: 4.5%" >
					<!--  COl6   -->
					</tbody>

				</table>
			</div>

		</div>
		
		
		<div id="divRegionWise" class="dynamicTableContainer"
			style="height: 400px;">
			<table style="width: 100%; border: #0F0; table-layout: fixed;"
				class="transTablex col15-center">
				<tr bgcolor="#72BEFC">
				    <td width="4.5%">Region Code</td>
					<!--  COl1   -->
					<td width="9.5%">Region Name</td>
					<!--  COl2   -->
					<td width="4%">Currency</td>
					<!--  COl3   -->
					<td width="5.4%">Sales Amount</td>
					<!-- COl4   -->

				</tr>
			</table>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 330px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblRegionDet"
					style="width: 100%; border: #0F0; table-layout: fixed;"
					class="transTablex col15-center">
					<tbody>
					<col style="width: 4.5%">
					<!--  COl1   -->
					<col style="width: 9.5%">
					<!--  COl2   -->
					<col style="width: 4%">
					<!--  COl3   -->
					<col style="width: 5%">
					<!--  COl4   -->
					</tbody>

				</table>
			</div>

		</div>
		
		
		<div id="divValueTotal"
			style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 50px; margin: auto; overflow-x: hidden; overflow-y: hidden; width: 95%;">
			<table id="tblTotalFlash" class="transTablex"
				style="width: 100%; font-size: 11px; font-weight: bold;">
				
				<tr style="margin-left: 28px">
				
					<td id="labld26" style="width:70%; text-align:right">Total</td>
					<td id="tdSubTotValue" style="width:10%; align:right">
						<input id="txtSubTotValue" style="width: 100%; text-align: right;" class="Box"></input>
					</td>
					<td id="tdTaxTotValue" style="width:10%; align:right">
						<input id="txtTaxTotValue" style="width: 100%; text-align: right;" class="Box"></input>
					</td>
					<td id="tdTotValue" style="width:10%; align:right">
						<input id="txtTotValue" style="width: 100%; text-align: right;" class="Box"></input>
					</td>

				</tr>
			</table>
		</div>


		<div
			style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 50px; margin: auto;  width: 95%; overflow-x: scroll; overflow-y: hidden;">
			<table>
			<tr>
				<td><input id="btnBillWise" type="button" class="form_button"
					value="Bill Wise" onclick="funOnClckBillWiseBtn('divBillWise')" /></td>
				<td><input id="btnTenderWise" type="button" class="form_button"
					value="Settlement Wise"  style="background-size: 120px 24px; width: 120px;"
					onclick="funOnClckTenderWiseBtn('divTenderWise')" /></td>
				<td><input id="btnOperatorWise" type="button"
					class="form_button" value="Operator Wise"
					onclick="funOnClckoperatorWiseBtn('divOperatorWise')" style="background-size: 110px 24px; width: 110px;" /></td>
				<td><input id="btnCustomerWise" type="button"
					class="form_button" value="Customer Wise"  onclick="funOnClckCuctomerWiseBtn('divCustomerWise')" style="background-size: 110px 24px; width: 110px;" /></td>
				<td><input id="btnSkuWise" type="button" class="form_button"
					value="Product Wise"  onclick="funOnClckSKUWiseBtn('divSKUWise')" /></td>
				<td><input id="btnCategoryWise" type="button"
					class="form_button" value="Group SubGroup Wise"  onclick="funOnClckCategoryWiseBtn('divCategoryWise')" style="background-size: 140px 24px; width: 140px;" /></td>
				<td><input id="btnManufacturerWise" type="button"
					class="form_button" value="Manufacturer Wise" onclick="funOnClckManufactureWiseBtn('divManufactureWise')" style="background-size: 130px 24px; width: 130px;" /></td>
				<td><input id="btnAttributeWise" type="button"
					class="form_button" value="Attribute Wise" /></td>
				<td><input id="btnDepartmentWise" type="button" 
					 class="form_button" value="Department Wise" onclick="funOnClckDepartmentWiseBtn('divDepartmentWise')"  style="background-size: 120px 24px; width: 120px;" /></td>
				<td><input id="btnMonthWise" type="button" class="form_button"
					value="Month Wise"  style="background-size: 100px 24px; width: 100px;"
					onclick="funOnClickMonthWiseBtn('divMonthWise')" /></td>
				<td><input id="btnRegionWise" type="button" class="form_button"
					value="Region Wise"  style="background-size: 100px 24px; width: 100px;"
					onclick="funOnClickRegionWiseBtn('divRegionWise')" /></td>			 
			</tr>
			</table>	 
		</div>
		<!-- 		<table id="tblButton" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"> -->
		<!-- 		<tr style="margin-left: 28px"> -->
		<!-- 			<td id="labld26" width="70%" align="right">Total Value</td> -->
		<!-- 			<td id="tdSubTotValue" width="10%" align="right"> -->
		<!-- 			<input id="txtSubTotValue" style="width: 100%;text-align: right;" class="Box"></input></td> -->
		<!-- 			<td id="tdTaxTotValue" width="10%" align="right"> -->
		<!-- 			<input id="txtTaxTotValue" style="width: 100%;text-align: right;" class="Box"></input></td> -->
		<!-- 			<td id="tdTotValue" width="10%" align="right"> -->
		<!-- 			<input id="txtTotValue" style="width: 100%;text-align: right;" class="Box"></input></td> -->

		<!-- 			</tr> -->
		<!-- 		</table> -->
<br/>
<br/>
<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
	</s:form>
</body>
</html>