<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sales Report</title>
<style>
.ui-autocomplete {
    max-height: 200px;
    overflow-y: auto;
    /* prevent horizontal scrollbar */
    overflow-x: hidden;
    /* add padding to account for vertical scrollbar */
    padding-right: 20px;
}
/* IE 6 doesn't support max-height
 * we use height instead, but this forces the menu to always be this tall
 */
* html .ui-autocomplete {
    height: 200px;
}
</style>
<script type="text/javascript">
$(document).ready(function() 
		{		
			document.all["divBillWise"].style.display = 'block';
			$(".tab_content").hide();
			$(".tab_content:first").show();

			$("ul.tabs li").click(function() {
				$("ul.tabs li").removeClass("active");
				$(this).addClass("active");
				$(".tab_content").hide();
				var activeTab = $(this).attr("data-state");
				$("#" + activeTab).fadeIn();
			});
		/* 		
			$(document).ajaxComplete(function(){
			   	$("#wait").css("display","none");
			});
			
			 $(document).ajaxStart(function(){
			    $("#wait").css("display","block");
			});  
			 */
			
		/* 	$("#btnExport").click(function(event) {
			
				var report=	document.getElementById("hidReportName").value;
				var searchurl=getContextPath()+"/ExportSalesReport.html?ReportName="+report;
		 		$.ajax({
			       		 type: "POST",
		    	    	url: searchurl,
		        		dataType: "json",
		        
				
			});
			}); */
			
		});


	$(function() 
		{	
		
	//	funSetDate();
		var POSDate="${POSDate}"
		//2016-11-05
	 		    var startDate="${POSDate}";
	 		  	var Date = startDate.split(" ");
				var arr = Date[0].split("-");
				Dat=arr[2]+"-"+arr[1]+"-"+arr[0];	
    			$("#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
				$("#txtFromDate" ).datepicker('setDate', Dat); 
				$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
				$("#txtToDate" ).datepicker('setDate', Dat);  
				/* 
				$("#btnExport").click(function (e)
						{
							 var fromDate=$("#txtFromDate").val();
							var toDate=$("#txtToDate").val();
							var locCode=$("#txtLocCode").val();
							var param1=locCode+","+fromDate+","+toDate; 
							var report="BillWise"
							report=	document.getElementById("hidReportName").value;
							window.location.href=getContextPath()+"/ExportExcelReport.html?reportName="+report;
						}); 
	 					*/
	 }); 

	
	
	/**
	* Open Help
	**/
	function funHelp(transactionName)
	{	
       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
    }
		
	function funSetData(code)
	{
		$("#txtCustomer").val(code);
	}
	function funSelectedReport(divID)
	 {
		//report=divID;
		var POSName=document.getElementById("cmbPOSName").value;
		var FromDate=document.getElementById("txtFromDate").value+":"+document.getElementById("txtHHFrom").value+"/"+document.getElementById("txtMMFrom").value+"/"+document.getElementById("txtAMPMFrom").value;
		var ToDate=document.getElementById("txtToDate").value+":"+document.getElementById("txtHHTo").value+"/"+document.getElementById("txtMMTo").value+"/"+document.getElementById("txtAMPMTo").value;
		var Operator=document.getElementById("txtOperator").value;
		var PayMode=document.getElementById("txtPayMode").value;
		
		var txtFromBillNo=document.getElementById("txtFromBillNo").value;
		var txtToBillNo=document.getElementById("txtToBillNo").value;
		var txtReportType=document.getElementById("txtReportType").value;
		var txtType=document.getElementById("txtType").value;
		var txtCustomer=document.getElementById("txtCustomer").value;
		var chkConsolidatePOS=document.getElementById("chkConsolidatePOS").checked;
		/* if($("#chkConsolidatePOS").attr('checked')==true)
		{
			  chkConsolidatePOS="y";	
		}
		else
			{
			  chkConsolidatePOS="n";
			} */
		//var ReportName=divID.substring(3); 
			//$("#hidReportName").val(ReportName);
			//document.getElementById("hidReportName").value;
		var hidReportName=divID;			
		if(FromDate > ToDate) {
		    alert("Enter Valid Dates");
			}
		else {
			
			$("#hidReportName").val(divID);
		funShowDiv(divID);
		 
		 switch(divID)
		 {
			 case 'divSettlementWise':
				 funLoadSettlementWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
					break;
			 case 'divBillWise':
				 funLoadBillWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
				 	break;
			 case 'divItemWise':
				 funLoadItemWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
					break;
			
			 case 'divMenuHeadWise':
				 funLoadMenuHeadWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
					break;
				//working	
			 case 'divGroupWise' :
				 funLoadGroupWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
					break;
		
			 case 'divSubGroupWise':
				 funLoadSubGroupWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
					break;
			 case 'divCustWise':
				 funLoadCustWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
				 	break;
			 case 'divWaiterWise':
				 funLoadWaiterWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
					break;
			
			 case 'divDeliveryBoyWise':
				 funLoadDeliveryBoyWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
					break;
					
			 case 'divCostCenterWise' :
				 funLoadCostCenterWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
					break;
					
			 case 'divHomeDeliveryWise':
				 funLoadHomeDeliveryWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
					break;
			 case 'divTableWise':
				 funLoadTableWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
				 	break;
			 case 'divHourlyWise':
				 funLoadHourlyWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
					break;
			
			 case 'divAreaWise':
				 funLoadAreaWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
					break;
					
			 case 'divDayWiseSales' :
				 funLoadDayWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
					break;
					
			 case 'divTaxWiseSales':
				 funLoadTaxWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
					break;
		
			 case 'divTipReport':
				 funLoadTipSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
				 	break;
			 case 'divItemModifierWise':
				 funLoadItemModifierWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
					break;
			
			 case 'divMenuHeadWiseWithModifier':
				 funLoadMenuHeadWiseWithModifierSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
					break;
					
			 case 'divItemHourlyWise' :
				 funLoadItemHourlyWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
					break;
					
			 case 'divOperatorWise':
				 funLoadOperatorSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
				 	break;
			 case 'divMonthlySalesFlash':
				 funLoadMonthlySalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
							txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName);
					break;
					
			}
		}
	 }
	
	/* divDeliveryBoyWise divCostCenterWise divHomeDeliveryWise divTableWise divHourlyWise divAreaWise divDayWiseSales
		divTaxWiseSales divTipReport divItemModifierWise  divMenuHeadWiseWithModifier divItemHourlyWise
		divOperatorWise divMonthlySalesFlash */
 	function funShowDiv(divID)
	 { 
 		$("#wait").css("display","block");
 		document.all["divSettlementWise"].style.display = 'none';
 		document.all["divBillWise"].style.display = 'none';
 		document.all["divItemWise"].style.display = 'none';
 		document.all["divMenuHeadWise"].style.display = 'none';
 		document.all["divGroupWise"].style.display = 'none';
 		document.all["divSubGroupWise"].style.display = 'none';
 		document.all["divCustWise"].style.display = 'none';
 		document.all["divWaiterWise"].style.display = 'none';
 		document.all["divDeliveryBoyWise"].style.display = 'none';
 		document.all["divCostCenterWise"].style.display = 'none';
 		document.all["divHomeDeliveryWise"].style.display = 'none';
 		document.all["divTableWise"].style.display = 'none';
 		document.all["divHourlyWise"].style.display = 'none';
 		document.all["divAreaWise"].style.display = 'none';
 		document.all["divDayWiseSales"].style.display = 'none';
 		document.all["divTaxWiseSales"].style.display = 'none';
 		document.all["divTipReport"].style.display = 'none';
 		document.all["divItemModifierWise"].style.display = 'none';
 		document.all["divMenuHeadWiseWithModifier"].style.display = 'none';
 		document.all["divItemHourlyWise"].style.display = 'none';
 		document.all["divOperatorWise"].style.display = 'none';
 		document.all["divMonthlySalesFlash"].style.display = 'none';
 		 
 		document.all[divID].style.display = 'block';
 		
 		
 		/* funLoadSalesReport(); */ 
	}
 	
 	/*fill Settlement wise data */
 	function funLoadSettlementWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadSettlementWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblSettlementWise");
			        	funRemoveProductRows("tblSettlementWiseTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblSettlementWiseTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"65%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalSubTotal+"'/>";
			    		            	   
		            			}
			            		
			            		var table = document.getElementById("tblSettlementWise");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"43%\" id=\"field3."+(rowCount)+"\" style=\"text-align:right;\" value='"+response[i].strField3+"'/>";
			            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"43%\" id=\"field4."+(rowCount)+"\" style=\"text-align:right;\" value='"+response[i].strField4+"'/>"; //text-align: right;
			            	 	            	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	/*fill bill wise data */
 	function funLoadBillWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadBillWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblBillWise");
			        	funRemoveProductRows("tblBillWiseTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblBillWiseTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"20%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\"  />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"36%\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalSubTotal+"'/>";
			    		            	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"26%\" id=\"Totfield3."+(rowCount)+"\" value='"+response[i].totalDiscAmt+"'/>";
			    		            	    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"26%\" id=\"Totfield4."+(rowCount)+"\" value='"+response[i].totalTaxAmt+"'/>";
			    		            	    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"25%\" id=\"Totfield5."+(rowCount)+"\" value='"+response[i].totalSettleAmt+"'/>";
			    		            	    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"16%\" id=\"Totfield6."+(rowCount)+"\" value='"+response[i].totalTipAmt+"'/>";
		            			
		            			}
			            		
			            		var table = document.getElementById("tblBillWise");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" style=\"text-align:center;\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"4%\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"4%\" style=\"text-align:right;\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
			            	 	    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"6%\" id=\"field5."+(rowCount)+"\" value='"+response[i].strField5+"'/>";
			            		    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"6%\" id=\"field6."+(rowCount)+"\" value='"+response[i].strField6+"'/>";
			            		    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"6%\" id=\"field7."+(rowCount)+"\" value='"+response[i].strField7+"'/>";
			            		    row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"6%\" style=\"text-align:right;\" id=\"field8."+(rowCount)+"\" value='"+response[i].strField8+"'/>";
			            		    row.insertCell(8).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" style=\"text-align:right;\" id=\"field9."+(rowCount)+"\" value='"+response[i].strField9+"'/>";
			            		    row.insertCell(9).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"8%\" style=\"text-align:right;\" id=\"field10."+(rowCount)+"\" value='"+response[i].strField10+"'/>";
			            	    	row.insertCell(10).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"7%\" style=\"text-align:right;\" id=\"field11."+(rowCount)+"\" value='"+response[i].strField11+"'/>";
			            	   	  	row.insertCell(11).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"13%\" style=\"text-align:right;\" id=\"field12."+(rowCount)+"\" value='"+response[i].strField12+"'/>";
			            	    	row.insertCell(12).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"13%\" style=\"text-align:right;\" id=\"field13."+(rowCount)+"\"  value='"+response[i].strField13+"'/>";
			            	    	row.insertCell(13).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"5%\" id=\"field14."+(rowCount)+"\" value='"+response[i].strField14+"'/>";
			            	    	row.insertCell(14).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"5%\" style=\"text-align:right;\" id=\"field15."+(rowCount)+"\" value='"+response[i].strField15+"'/>";
			            	    	row.insertCell(15).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"5%\" id=\"field16."+(rowCount)+"\" value='"+response[i].strField16+"'/>";
			            	    	row.insertCell(16).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"5%\" id=\"field17."+(rowCount)+"\" value='"+response[i].strField17+"'/>";
			            	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	/*fill Item wise data */
 	function funLoadItemWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadItemWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblItemWise");
			        	funRemoveProductRows("tblItemWiseTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblItemWiseTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"87%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"25%\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalQuantity+"'/>";
			    		            		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"22%\" id=\"Totfield3."+(rowCount)+"\" value='"+response[i].totalAmount+"'/>";
			    		            		row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"22%\" id=\"Totfield4."+(rowCount)+"\" value='"+response[i].totalSubTotal+"'/>";
			    		            		row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"15%\" id=\"Totfield5."+(rowCount)+"\" value='"+response[i].totalDiscAmt+"'/>";
		            			}
			            		
			            		var table = document.getElementById("tblItemWise");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"51%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"32%\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"23%\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"23%\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
			            		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"24%\" id=\"field5."+(rowCount)+"\" value='"+response[i].strField5+"'/>";
			            		    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"14%\" id=\"field6."+(rowCount)+"\" value='"+response[i].strField6+"'/>";
			            	 	             	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	/*fill Menu Head wise data */
 	function funLoadMenuHeadWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadMenuHeadWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblMenuHeadWise");
			        	funRemoveProductRows("tblMenuHeadWiseTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblMenuHeadWiseTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"28%\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalQuantity+"'/>";
			    		            		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"28%\" id=\"Totfield3."+(rowCount)+"\" value='"+response[i].totalAmount+"'/>";
			    		            		row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"40%\" id=\"Totfield4."+(rowCount)+"\" value='"+response[i].totalSubTotal+"'/>";
			    		            		row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"27%\" id=\"Totfield5."+(rowCount)+"\" value='"+response[i].totalDiscAmt+"'/>";
		            			}
			            		
			            		var table = document.getElementById("tblMenuHeadWise");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"23%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"24%\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"24%\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"24%\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
			            		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"24%\" id=\"field5."+(rowCount)+"\" value='"+response[i].strField5+"'/>";
			            		    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"24%\" id=\"field6."+(rowCount)+"\" value='"+response[i].strField6+"'/>";
			            		    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"23%\" id=\"field7."+(rowCount)+"\" value='"+response[i].strField7+"'/>";			            	 	             	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	/*fill Group wise data */
 	function funLoadGroupWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadGroupWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblGroupWise");
			        	funRemoveProductRows("tblGroupWiseTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblGroupWiseTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"46%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"27%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalQuantity+"'/>";
			    		            		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"36%\" style=\"text-align:right;\" id=\"Totfield3."+(rowCount)+"\" value='"+response[i].totalAmount+"'/>";
			    		            		row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"36%\" style=\"text-align:right;\" id=\"Totfield4."+(rowCount)+"\" value='"+response[i].totalSubTotal+"'/>";
			    		            		row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"27%\" style=\"text-align:right;\" id=\"Totfield5."+(rowCount)+"\" value='"+response[i].totalDiscAmt+"'/>";
		            			}
			            		
			            		var table = document.getElementById("tblGroupWise");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"23%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"24%\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"23%\" style=\"text-align:right;\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"23%\" style=\"text-align:right;\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
			            		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"23%\" style=\"text-align:right;\" id=\"field5."+(rowCount)+"\" value='"+response[i].strField5+"'/>";
			            		    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"23%\" style=\"text-align:right;\" id=\"field6."+(rowCount)+"\" value='"+response[i].strField6+"'/>";
			            		    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"23%\" style=\"text-align:right;\" id=\"field7."+(rowCount)+"\" value='"+response[i].strField7+"'/>";			            	 	             	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	/*fill Sub Group wise data */
 	function funLoadSubGroupWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadSubGroupWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblSubGroupWise");
			        	funRemoveProductRows("tblSubGroupWiseTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblSubGroupWiseTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"28%\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalQuantity+"'/>";
			    		            		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"30%\" id=\"Totfield3."+(rowCount)+"\" value='"+response[i].totalAmount+"'/>";
			    		            		row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"36%\" id=\"Totfield4."+(rowCount)+"\" value='"+response[i].totalSubTotal+"'/>";
			    		            		row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"28%\" id=\"Totfield5."+(rowCount)+"\" value='"+response[i].totalDiscAmt+"'/>";
		            			}
			            		
			            		var table = document.getElementById("tblSubGroupWise");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"40%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"24%\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"19%\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"23%\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
			            		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"23%\" id=\"field5."+(rowCount)+"\" value='"+response[i].strField5+"'/>";
			            		    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"18%\" id=\"field6."+(rowCount)+"\" value='"+response[i].strField6+"'/>";
			            		    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"18%\" id=\"field7."+(rowCount)+"\" value='"+response[i].strField7+"'/>";			            	 	             	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	
 	/*fill Cust wise data */
 	function funLoadCustWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadCustWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblCustWise");
			        	funRemoveProductRows("tblCustWiseTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblCustWiseTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"48%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalQuantity+"'/>";
			    		            		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"48%\" style=\"text-align:right;\" id=\"Totfield3."+(rowCount)+"\" value='"+response[i].totalAmount+"'/>";
		            			}
			            		
			            		var table = document.getElementById("tblCustWise");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"58%\" style=\"text-align:right;\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"58%\" style=\"text-align:right;\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    /* row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
			            		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field5."+(rowCount)+"\" value='"+response[i].strField5+"'/>";
			            		    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field6."+(rowCount)+"\" value='"+response[i].strField6+"'/>";
			            		    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field7."+(rowCount)+"\" value='"+response[i].strField7+"'/>"; */			            	 	             	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	
 	/*fill Waiter wise data */
 	function funLoadWaiterWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadWaiterWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblWaiterWise");
			        	funRemoveProductRows("tblWaiterWiseTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblWaiterWiseTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalSubTotal+"'/>";
			    		            		
		            			}
			            		
			            		var table = document.getElementById("tblWaiterWise");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" style=\"text-align:right;\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
			            		    			            	 	             	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	
 	/*fill DeliveryBoy wise data */
 	function funLoadDeliveryBoyWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadDeliveryBoyWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
				        	funRemoveProductRows("tblDeliveryBoyWise");
				        	funRemoveProductRows("tblDeliveryBoyWiseTotal");
				        	$.each(response,function(i,item){
				        		$("#wait").css("display","none");
				            	if(i==0)
			            			{
				            			var table = document.getElementById("tblDeliveryBoyWiseTotal");
				    		           	var rowCount = table.rows.length;
				    		           	var row = table.insertRow(rowCount);
				    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
				    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"69%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalSubTotal+"'/>";
				    		            		
			            			}
				            		
				            		var table = document.getElementById("tblDeliveryBoyWise");
				            		var rowCount = table.rows.length;
				            		var row = table.insertRow(rowCount);
				            		i=i+1;
				            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
				            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
				            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" style=\"text-align:right;\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
				            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" style=\"text-align:right;\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
				            		    			            	 	             	  
				            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	
 	
 	/*fill Cost Center  wise data */
 	function funLoadCostCenterWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadCostCenterWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblCostCenterWise");
			        	funRemoveProductRows("tblCostCenterWiseTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblCostCenterWiseTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"28%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalQuantity+"'/>";
			    		            		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"38%\" style=\"text-align:right;\" id=\"Totfield3."+(rowCount)+"\" value='"+response[i].totalSubTotal+"'/>";
			    		            		row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"29%\" style=\"text-align:right;\" id=\"Totfield4."+(rowCount)+"\" value='"+response[i].totalAmount+"'/>";
			    		            		row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"27%\" style=\"text-align:right;\" id=\"Totfield5."+(rowCount)+"\" value='"+response[i].totalDiscAmt+"'/>";
		            			}
			            		
			            		var table = document.getElementById("tblCostCenterWise");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"25%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"25%\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"24%\" style=\"text-align:right;\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"24%\" style=\"text-align:right;\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
			            		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"24%\" style=\"text-align:right;\" id=\"field5."+(rowCount)+"\" value='"+response[i].strField5+"'/>";
			            		    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"24%\" style=\"text-align:right;\" id=\"field6."+(rowCount)+"\" value='"+response[i].strField6+"'/>";
			            		    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"24%\" style=\"text-align:right;\" id=\"field7."+(rowCount)+"\" value='"+response[i].strField7+"'/>";			            	 	             	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	
 	
 	
 	/*fill Home delivery  wise data */
 	function funLoadHomeDeliveryWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadHomeDelWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblHomeDeliveryWise");
			        	funRemoveProductRows("tblHomeDeliveryWiseTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblHomeDeliveryWiseTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"37%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalDiscAmt+"'/>";
			    		            		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"37%\" style=\"text-align:right;\" id=\"Totfield3."+(rowCount)+"\" value='"+response[i].totalSubTotal+"'/>";
			    		            		row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"36%\" style=\"text-align:right;\" id=\"Totfield4."+(rowCount)+"\" value='"+response[i].totalAmount+"'/>";
		            			}  	
			            		
			            		var table = document.getElementById("tblHomeDeliveryWise");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"14%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"11%\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"12%\" style=\"text-align:center;\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"14%\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
			            		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"9%\" style=\"text-align:right;\" id=\"field5."+(rowCount)+"\" value='"+response[i].strField5+"'/>";
			            		    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"9%\" style=\"text-align:right;\" id=\"field6."+(rowCount)+"\" value='"+response[i].strField6+"'/>";
			            		    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"8%\" style=\"text-align:right;\" id=\"field7."+(rowCount)+"\" value='"+response[i].strField7+"'/>";
			            		    row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"11%\" style=\"text-align:right;\" id=\"field8."+(rowCount)+"\" value='"+response[i].strField8+"'/>";
			            		    row.insertCell(8).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"23%\" id=\"field9."+(rowCount)+"\" value='"+response[i].strField9+"'/>";
			            		    row.insertCell(9).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"23%\" id=\"field10."+(rowCount)+"\" value='"+response[i].strField10+"'/>";
			            		    row.insertCell(10).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"23%\" id=\"field11."+(rowCount)+"\" value='"+response[i].strField11+"'/>";			            	 	             	  

			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	
 	
 	
 	/*fill Table  wise data */
 	function funLoadTableWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadTableWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblTableWise");
			        	funRemoveProductRows("tblTableWiseTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblTableWiseTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"69%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalAmount+"'/>";
			    		         }
			            		
			            		var table = document.getElementById("tblTableWise");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"60%\" style=\"text-align:right;\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		 			            	 	             	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	
 	
 	
 	
 	/*fill Hourly wise data */
 	function funLoadHourlyWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadHourlyWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblHourlyWise");
			        	funRemoveProductRows("tblHourlyWiseTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblHourlyWiseTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"69%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalAmount+"'/>";
			    		        }
			            		
			            		var table = document.getElementById("tblHourlyWise");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"48%\" style=\"text-align:right;\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"48%\" style=\"text-align:right;\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"31%\" style=\"text-align:right;\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
			            		   			            	 	             	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	
 	
 	
 	/*fill Area wise data */
 	function funLoadAreaWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadAreaWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblAreaWise");
			        	funRemoveProductRows("tblAreaWiseTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblAreaWiseTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		            row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
	    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"54%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalAmount+"'/>";
	    		    			}
			            		
			            		var table = document.getElementById("tblAreaWise");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"59%\" style=\"text-align:right;\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		  			            	 	             	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	
 	
 	/*fill Day wise data */
 	function funLoadDayWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadDayWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblDayWiseSales");
			        	funRemoveProductRows("tblDayWiseSalesTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblDayWiseSalesTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"25%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"28%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalQuantity+"'/>";
			    		            		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"28%\" style=\"text-align:right;\" id=\"Totfield3."+(rowCount)+"\" value='"+response[i].totalSubTotal+"'/>";
			    		            		row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"28%\" style=\"text-align:right;\" id=\"Totfield4."+(rowCount)+"\" value='"+response[i].totalDiscAmt+"'/>";
			    		            		row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" style=\"text-align:right;\" id=\"Totfield5."+(rowCount)+"\" value='"+response[i].totalTaxAmt+"'/>";
			    		            		row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"28%\" style=\"text-align:right;\" id=\"Totfield6."+(rowCount)+"\" value='"+response[i].totalAmount+"'/>";
		            			}
			            		
			            		var table = document.getElementById("tblDayWiseSales");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"28%\" style=\"text-align:center;\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"28%\" style=\"text-align:right;\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"28%\" style=\"text-align:right;\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"28%\" style=\"text-align:right;\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
			            		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"28%\" style=\"text-align:right;\" id=\"field5."+(rowCount)+"\" value='"+response[i].strField5+"'/>";
			            		    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"28%\" style=\"text-align:right;\" id=\"field6."+(rowCount)+"\" value='"+response[i].strField6+"'/>";
			            		   			            	 	             	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	
 	/*fill Tax wise data */
 	function funLoadTaxWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadTaxWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblTaxWiseSales");
			        	funRemoveProductRows("tblTaxWiseSalesTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblTaxWiseSalesTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"26%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalTaxAmt+"'/>";
			    		            		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"26%\" style=\"text-align:right;\" id=\"Totfield3."+(rowCount)+"\" value='"+response[i].totalSubTotal+"'/>";
			    		        }
			            		
			            		var table = document.getElementById("tblTaxWiseSales");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"17%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"26%\" style=\"text-align:center;\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"17%\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"41%\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
			            		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"16%\" style=\"text-align:right;\" id=\"field5."+(rowCount)+"\" value='"+response[i].strField5+"'/>";
			            		    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"25%\" style=\"text-align:right;\" id=\"field6."+(rowCount)+"\" value='"+response[i].strField6+"'/>";
			            		    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"26%\" style=\"text-align:right;\" id=\"field7."+(rowCount)+"\" value='"+response[i].strField7+"'/>";			            	 	             	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	
 	/*fill Tip Sale wise data  */
 	function funLoadTipSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadTipWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblTipReport");
			        	funRemoveProductRows("tblTipReportTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblTipReportTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"18%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalDiscAmt+"'/>";
			    		            		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"18%\" style=\"text-align:right;\" id=\"Totfield3."+(rowCount)+"\" value='"+response[i].totalSubTotal+"'/>";
			    		            		row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"18%\" style=\"text-align:right;\" id=\"Totfield4."+(rowCount)+"\" value='"+response[i].totalTaxAmt+"'/>";
			    		            		row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"18%\" style=\"text-align:right;\" id=\"Totfield5."+(rowCount)+"\" value='"+response[i].totalTipAmt+"'/>";
			    		            		row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"18%\" style=\"text-align:right;\" id=\"Totfield6."+(rowCount)+"\" value='"+response[i].totalAmount+"'/>";
		            			}
			            		
			            		var table = document.getElementById("tblTipReport");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"14%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"14%\" style=\"text-align:center;\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"14%\"  style=\"text-align:center;\"id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"14%\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
			            		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"14%\" id=\"field5."+(rowCount)+"\" value='"+response[i].strField5+"'/>";
			            		    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"14%\" style=\"text-align:right;\" id=\"field6."+(rowCount)+"\" value='"+response[i].strField6+"'/>";
			            		    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"14%\" style=\"text-align:right;\" id=\"field7."+(rowCount)+"\" value='"+response[i].strField7+"'/>";	
			            			row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"14%\" style=\"text-align:right;\" id=\"field8."+(rowCount)+"\" value='"+response[i].strField8+"'/>";
			            		    row.insertCell(8).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"14%\" style=\"text-align:right;\" id=\"field9."+(rowCount)+"\" value='"+response[i].strField9+"'/>";
			            		    row.insertCell(9).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"14%\" style=\"text-align:right;\" id=\"field10."+(rowCount)+"\" value='"+response[i].strField10+"'/>";
			            		    row.insertCell(10).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"14%\" style=\"text-align:right;\" id=\"field11."+(rowCount)+"\" value='"+response[i].strField11+"'/>";
			            		    
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	
 	/*fill Item Modifier  wise data */
 	function funLoadItemModifierWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadItemModifierWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblItemModifierWise");
			        	funRemoveProductRows("tblItemModifierWiseTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblItemModifierWiseTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"44%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalQuantity+"'/>";
			    		            		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"43%\" style=\"text-align:right;\" id=\"Totfield3."+(rowCount)+"\" value='"+response[i].totalAmount+"'/>";
			    		            		
		            			}
			            		
			            		var table = document.getElementById("tblItemModifierWise");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" style=\"text-align:right;\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" style=\"text-align:right;\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
			            		    		            	 	             	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	
 	/*fill MenuHead Wise With Modifier SalesReport wise data */
 	function funLoadMenuHeadWiseWithModifierSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadMenuHeadWiseWithModSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblMenuHeadWiseWithModifier");
			        	funRemoveProductRows("tblMenuHeadWiseWithModifierTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblMenuHeadWiseWithModifierTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		            row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
	    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalQuantity+"'/>";
	    		            		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" style=\"text-align:right;\" id=\"Totfield3."+(rowCount)+"\" value='"+response[i].totalAmount+"'/>";
	    		            		
		            			}
			            		
			            		var table = document.getElementById("tblMenuHeadWiseWithModifier");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" style=\"text-align:right;\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" style=\"text-align:right;\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
			          		});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	
 	/*fill Item Hourly wise data */
 	function funLoadItemHourlyWiseSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadItemHourlyWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblItemHourlyWise");
			        	funRemoveProductRows("tblItemHourlyWiseTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblItemHourlyWiseTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"35%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalAmount+"'/>";
			    		            		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"35%\" style=\"text-align:right;\" id=\"Totfield3."+(rowCount)+"\" value='"+response[i].totalDiscAmt+"'/>";
		            			}
			            		
			            		var table = document.getElementById("tblItemHourlyWise");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"35%\" style=\"text-align:right;\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"35%\" style=\"text-align:right;\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
			            		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"35%\" style=\"text-align:right;\" id=\"field5."+(rowCount)+"\" value='"+response[i].strField5+"'/>";
			            		 			            	 	             	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	
 	/*fill Operator wise data */
 	function funLoadOperatorSalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadOperstorWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblOperatorWise");
			        	funRemoveProductRows("tblOperatorWiseTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblOperatorWiseTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		          		  	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"29%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalDiscAmt+"'/>";
			    		            		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"29%\" style=\"text-align:right;\" id=\"Totfield3."+(rowCount)+"\" value='"+response[i].totalAmount+"'/>";
		            			}
			            		
			            		var table = document.getElementById("tblOperatorWise");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"field4."+(rowCount)+"\" value='"+response[i].strField4+"'/>";
			            		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"28%\" style=\"text-align:right;\" id=\"field5."+(rowCount)+"\" value='"+response[i].strField5+"'/>";
			            		    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"28%\" style=\"text-align:right;\" id=\"field6."+(rowCount)+"\" value='"+response[i].strField6+"'/>";
			            		  			            	 	             	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
 	
 	
 	/*fill Monthly sales wise data */
 	function funLoadMonthlySalesReport(divID,POSName,FromDate,ToDate,Operator,PayMode,txtFromBillNo,txtToBillNo,
			txtReportType,txtType,txtCustomer,chkConsolidatePOS,hidReportName)
	 {
 				//	document.forms["POSSalesReportForm"].submit();
 			var searchurl=getContextPath()+"/loadMonthlyWiseSalesReport.html?POSName="+POSName+"&FromDate="+FromDate+"&ToDate="+ToDate+"&Operator="+Operator+"&PayMode="+PayMode+"&txtFromBillNo="+txtFromBillNo+
 					"&txtToBillNo="+txtToBillNo+"&txtReportType="+txtReportType+"&txtType="+txtType+"&txtCustomer="+txtCustomer+"&chkConsolidatePOS="+chkConsolidatePOS+"&hidReportName="+hidReportName;
 		 	$.ajax({
				        type: "POST",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        	funRemoveProductRows("tblMonthlySalesFlash");
			        	funRemoveProductRows("tblMonthlySalesFlashTotal");
			        	$.each(response,function(i,item){
			        		$("#wait").css("display","none");
			            	if(i==0)
		            			{
			            			var table = document.getElementById("tblMonthlySalesFlashTotal");
			    		           	var rowCount = table.rows.length;
			    		           	var row = table.insertRow(rowCount);
			    		          		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"Totfield1."+(rowCount)+"\" value=\"Total\" />"; 
			    		            		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"62%\" style=\"text-align:right;\" id=\"Totfield2."+(rowCount)+"\" value='"+response[i].totalAmount+"'/>";
			    		            		
		            			}
			            		
			            		var table = document.getElementById("tblMonthlySalesFlash");
			            		var rowCount = table.rows.length;
			            		var row = table.insertRow(rowCount);
			            		i=i+1;
			            	    	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"60%\" style=\"text-align:center;\" id=\"field1."+(rowCount)+"\" value='"+response[i].strField1+"'/>";
			            	    	row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"60%\" style=\"text-align:center;\" id=\"field2."+(rowCount)+"\" value='"+response[i].strField2+"'/>";
			            		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"60%\" style=\"text-align:right;\" id=\"field3."+(rowCount)+"\" value='"+response[i].strField3+"'/>";
			            		    			            	 	             	  
			            	});
			    
			            },
			            error: function(jqXHR, exception)
			       		 {
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
/*  	
 	function funExportReport()
 	{
 		var report=	document.getElementById("hidReportName").value;
		var searchurl=getContextPath()+"/ExportSalesReport.html?ReportName="+report;
 		$.ajax({
	       		 type: "POST",
    	    	url: searchurl,
        		dataType: "json",
        
		
	});
 }
 	 */
 	
 	function funRemoveProductRows(tableName)
	{
		var table = document.getElementById(tableName);
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
	<br/>
	<div id="formHeading">
		<label>Sales Report</label>
	</div>
	<s:form name="POSSalesReportForm" method="POST" action="rptPOSSalesReport.html">
		<br/>
		<br/>
		<div id="tab_container" style="height:40%">
				<ul class="tabs">
					<li data-state="tab1" style="width: 10%; padding-left: 2%;margin-left: 10%; " class="active" >Data</li>
					<li data-state="tab2" style="width: 10%; padding-left: 1%">Advance Filter</li>
					
				</ul>

		<div id="tab1" class="tab_content" style="height: 40%">
				
			<br/>
			<br/>
		<div>
			<table class="masterTable">

			<!-- &emsp;&ensp;&emsp;&ensp; -->
			<tr>
				<td width="30%">
				<label>POS Name </label>&emsp;&ensp;
				<s:select id="cmbPOSName" name="cmbPOSName" path="strPOSName" cssClass="BoxW124px" items="${posList}" >
				</s:select></td>
				<td><label>From Date   </label>&emsp;&ensp;
				<s:input id="txtFromDate" required="required" path="fromDate" pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox"/>
				</td>
				<td><label>To Date</label>&emsp;&ensp;
				<s:input id="txtToDate" required="required" path="toDate" pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox"/></td>
				<td>
				<input id="btnExport" type="submit" value="EXPORT"  class="form_button1" onclick="funExportReport()" />
				</td>
				
			</tr>
			<tr>
			<td>	<s:input type="hidden" id="hidReportName" path="strReportName"></s:input>
			</td>
				<td colspan="4">
				</td>
			</tr>
			<!-- <tr>
				<td colspan="4">
				</td>
			</tr> -->
		</table>	
		</div>
	<div >
	<table class="transTablex"> 	
			<tr>
				<td colspan="4">
				</td>
			</tr>
			<tr>
			<!--   <table  class="two transTablex col4-center" 
    style="width: 300%; border: #0F0; table-layout: fixed; overflow: scroll"> -->
   	<td colspan="4">
   <!-- Settlement wise table -->
   		<div id="divSettlementWise" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold; table-layout: fixed; overflow: scroll" class=" transTablex">
				<tr bgcolor="#72BEFC">
					<td width="25%">POS</td>					
					<td width="25%">Settlement Mode</td>	
					<td align="right" width="25%">Sales Amount</td>
					<td align="right" width="25%">Sales (%)  </td>
	  		   </tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblSettlementWise" style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class=" transTablex col11-center">
					<tbody>
					<col style="width:25%">					
					<col style="width:25%">
					<col style="width:25%">
					<col style="width:25%">	
					</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="25%"></td>					
					<td align ="right" width="15%">Sales Amount</td>
				 </tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblSettlementWiseTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:25%">					
					<col align="right" style="width:15%">
				</table>
			</div>
			</div>
		</div>
<!-- Bill wise table -->	
		<div id="divBillWise" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="7%">Bill No</td>					
					<td width="8%">Date</td>	
					<td width="4%">Bill time</td>
					<td width="4%">Table Name</td>
					<td width="6%">Cust Name</td>
					<td width="6%">POS</td>	
					<td width="5%">Pay Mode</td>
					<td width="5%">Delivery Charge</td>
					<td align="right" width="8%">Sub Total</td>
					<td width="6%">Disc %</td>
					<td align="right" width="6%">Disc Amt</td>
					<td align="right" width="9.5%">TAX Amt</td>
					<td align="right" width="9%">Sales Amt</td>
					<td width="5%">Remark</td>
					<td width="5%">Tip</td>
					<td width="5%">Disc Remark</td>
					<td width="5%">Reason</td>
				</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblBillWise"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:7%">					
					<col style="width:8%">
					<col style="width:4%">
					<col style="width:4%">
					<col style="width:6%">
					<col style="width:6%">					
					<col style="width:5%">
					<col style="width:5%">
					<col style="width:8%">
					<col style="width:6%">
					<col style="width:6%">					
					<col style="width:9%">
					<col style="width:9%">
					<col style="width:5%">
					<col style="width:5%"> 
					<col style="width:5%">
					<col style="width:5%">
					</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="20%"></td>					
					<td align="right" width="20%">Sub Total</td>	
					<td align="right" width="15%">Disc</td>
					<td align="right" width="15%">Tax Total</td>
					<td align="right" width="15%">Sales Amount</td>
					<td align="right" width="10%">Tip Amount</td>
					
			    </tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblBillWiseTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:20%">					
					<col style="width:20%">
					<col style="width:15%">
					<col style="width:15%">
					<col style="width:15%">					
					<col style="width:10%">
				
				</table>
			</div>
			</div>
		</div>
<!-- Item wise table -->
	<div id="divItemWise" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="12%">Item Name</td>					
					<td width="8%">POS</td>	
					<td align="right" width="6%">Quantity</td>
					<td align="right" width="6%">sub Total</td>
					<td align="right" width="6%">Sales Amount</td>
					<td align="right" width="4%">Discount</td>	
				</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblItemWise"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:12%">					
					<col style="width:8%">
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:4%">					
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="35%"></td>					
					<td align="right" width="11%">Quantity</td>
					<td align="right" width="10%">Sales Amount</td>
					<td align="right" width="10%">SubTotal</td>
					<td align="right" width="7%">Discount</td>
					
			    </tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblItemWiseTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:35%">					
					<col style="width:11%">
					<col style="width:10%">
					<col style="width:10%">
					<col style="width:7%">					
				</table>
			</div>
			</div>
		</div>
<!-- MenuHead Wise table -->
	<div id="divMenuHeadWise" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="6%">Menu Name</td>					
					<td width="6%">POS</td>	
					<td align="right" width="6%">Quantity</td>
					<td align="right" width="6%">sub Total</td>
					<td align="right" width="6%">Sales Amount</td>
					<td align="right" width="6%">Discount</td>	
					<td align="right" width="6%">Sales (%)</td>
					
				</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblMenuHeadWise"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:6%">					
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">					
					<col style="width:6%">
					</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="25%"></td>					
					<td align="right" width="15%">Quantity</td>
					<td align="right" width="15%">Sales Amount</td>
					<td align="right" width="20%">Sub Total</td>	
					<td align="right" width="15%">Discount</td>
					
			    </tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblMenuHeadWiseTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:25%">					
					<col style="width:15%">
					<col style="width:15%">
					<col style="width:20%">
					<col style="width:15%">
					
				</table>
			</div>
			</div>
		</div>
	<!-- Group wise table -->						
		<div id="divGroupWise" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="6%">Group Name</td>					
					<td width="6%">POS</td>	
					<td align="right" width="6%">Quantity</td>
					<td align="right" width="6%">sub Total</td>
					<td align="right" width="6%">Net Total</td>
					<td align="right" width="6%">Discount</td>	
					<td align="right" width="6%">Sales (%)</td>
				</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblGroupWise"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:6%">					
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">					
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="25%"></td>					
					<td align="right" width="15%">Quantity</td>
					<td align="right" width="20%">Sub Total</td>
					<td align="right" width="20%">Net Total</td>	
					<td align="right" width="15%">Discount</td>
					
			    </tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblGroupWiseTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:25%">					
					<col style="width:15%">
					<col style="width:20%">
					<col style="width:20%">
					<col style="width:15%">
				</table>
			</div>
			</div>
		</div>
<!-- SubGroup wise table -->						
		<div id="divSubGroupWise" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="10%">SubGroup Name</td>					
					<td width="6%">POS</td>	
					<td align="right" width="4%">Quantity</td>
					<td align="right" width="6%">sub Total</td>
					<td align="right" width="6%">Sales Amount</td>
					<td align="right" width="5%">Discount</td>	
					<td align="right" width="5%">Sales (%)</td>
				</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblSubGroupWise"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:10%">					
					<col style="width:6%">
					<col style="width:5%">
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:5%">	
					<col style="width:5%">				
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="21%"></td>					
					<td align="right" width="14%">Quantity</td>
					<td align="right" width="15%">Sales Amount</td>
					<td align="right" width="18%">Net Total</td>	
					<td align="right" width="15%">Discount</td>
					
			    </tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblSubGroupWiseTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:25%">					
					<col style="width:17%">
					<col style="width:18%">
					<col style="width:22%">
					<col style="width:18%">
					
				</table>
			</div>
			</div>
		</div>
<!-- Customer Wise Bill-->							
	<div id="divCustWise" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<!-- <td width="6%">Bill No</td>					
					<td width="6%">Bill Date</td>	
					<td width="6%">Customer Code</td> -->
					<td width="6%">Customer Name</td>
					<td align="right" width="6%">NO Of Bills</td>
					<!-- <td width="6%">Quantity</td> -->
					<td align="right" width="6%">Sales Amount</td>
						
				</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblCustWise"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:6%">					
					<col style="width:6%">
					<col style="width:6%">
				<%-- 	<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">					
					<col style="width:6%"> --%>
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="25%"></td>	
					<td align="right" width="15%">Quantity</td>
					<td align="right" width="15%">Sales Amount</td>
				</tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblCustWiseTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:25%">					
					<col style="width:15%">
					<col style="width:15%">
				</table>
			</div>
			</div>
		</div>
		
		
		
<!-- Waiter Wise -->							
	<div id="divWaiterWise" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="6%">POS</td>					
					<td width="6%">Waiter Full Name</td>	
					<td width="6%">Waiter Short Name</td>
					<td align="right" width="6%">Sales Amount</td>
					</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblWaiterWise"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:6%">					
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="45%"></td>					
					<td align="right" width="15%">Sales Amount</td>
				</tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblWaiterWiseTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:45%">					
					<col style="width:15%">
			</table>
			</div>
			</div>
		</div>
<!-- Delivery Boy Wise -->
<div id="divDeliveryBoyWise" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="6%">Delivery Boy Name</td>					
					<td width="6%">POS</td>	
					<td align="right" width="6%">Sales Amount</td>
					<td align="right" width="6%">Delivery Charges </td>
					
					</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblDeliveryBoyWise"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:6%">					
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="25%"></td>					
					<td align="right" width="15%">Sales Amount</td>
					
			    </tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblDeliveryBoyWiseTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:25%">					
					<col style="width:15%">
				</table>
			</div>
			</div>
		</div>
            
<!-- Cost Centre -->
<div id="divCostCenterWise" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="6%">Cost Centre Name</td>					
					<td width="6%">POS</td>	
					<td align="right" width="6%">Quantity</td>
					<td align="right" width="6%">SubTotal</td>
					<td align="right" width="6%">Sales Amount</td>
					<td align="right" width="6%">Discount</td>
					<td  align="right" width="6%">Sales (%)</td>
						
				</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblCostCenterWise"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:6%">					
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">					
					<col style="width:6%">
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="25%"></td>					
					<td align="right" width="15%">Quantity</td>
					<td align="right" width="20%">Sub Total</td>	
					<td align="right" width="15%">Sales Amount</td>
					<td align="right" width="15%">Discount</td>
				</tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblCostCenterWiseTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:25%">					
					<col style="width:15%">
					<col style="width:20%">
					<col style="width:15%">
					<col style="width:15%">
				</table>
			</div>
			</div>
		</div>
<!-- Home Delivery Wise -->
<div id="divHomeDeliveryWise" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="6%">Bill No</td>					
					<td width="5%">POS</td>	
					<td align="center" width="5%">Date</td>
					<td width="6%">Settle Mode</td>
					<td align="right" width="4%">Delivery Charges</td>
					<td align="right" width="4%">Disc Amt</td>
					<td align="right" width="4%">Tax Amt</td>
					<td align="right" width="5%">Amount</td>
					<td width="9%">Customer Name</td>
					<td width="9%">Building</td>
					<td width="9%">Delv Boy</td>
						
				</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblHomeDeliveryWise"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:6%">					
					<col style="width:5%">
					<col style="width:5%">					
					<col style="width:6%">
					<col style="width:4%">
					<col style="width:4%">
					<col style="width:4%">					
					<col style="width:5%">
					<col style="width:9%">
					<col style="width:9%">					
					<col style="width:9%">
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="25%"></td>					
					<td align="right" width="15%">Discount</td>
					<td align="right" width="15%">Tax Total</td>
					<td align="right" width="15%">Sales Amount</td>
				</tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblHomeDeliveryWiseTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:25%">					
					<col style="width:15%">
					<col style="width:15%">
					<col style="width:15%">
				</table>
			</div>
			</div>
		</div>
<!-- Table Wise -->
<div id="divTableWise" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="6%">POS</td>	
					<td width="6%">Table Name</td>					
					<td align="right" width="6%">Sales Amount</td>
					</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblTableWise"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:6%">					
					<col style="width:6%">
					<col style="width:6%">
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="25%"></td>					
					<td align="right" width="15%">Sales Amount</td>
					
			    </tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblTableWiseTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:25%">					
					<col style="width:15%">
					
				</table>
			</div>
			</div>
		</div>
<!-- Hourly Wise -->
	<div id="divHourlyWise" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="6%">Date Range</td>	
					<td align="right" width="6%">No Of Bills</td>					
					<td align="right" width="6%">Sales Amount</td>
					<td align="right" width="4%">Sales (%)</td>
					</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblHourlyWise"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:6%">					
					<col style="width:6%">					
					<col style="width:6%">
					<col style="width:4%">
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="25%"></td>					
					<td align="right" width="15%">Sales Amount</td>
					
			    </tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblHourlyWiseTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:25%">					
					<col style="width:15%">
					
				</table>
			</div>
			</div>
		</div>
<!-- Area Wise -->
	<div id="divAreaWise" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="6%">POS</td>	
					<td width="6%">Area Name</td>					
					<td align="right" width="6%">Sales Amount</td>
					</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblAreaWise"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:6%">					
					<col style="width:6%">
					<col style="width:6%">
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="35%"></td>					
					<td align="right" width="15%">Sales Amount</td>
					
			    </tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblAreaWiseTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:35%">					
					<col style="width:15%">
				</table>
			</div>
			</div>
		</div>

<!-- Day Wise -->
 <div id="divDayWiseSales" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td align="center" width="6%">Bill Date</td>
					<td align="right" width="6%">No Of Bill</td>		
					<td align="right" width="6%">Sub Total</td>		
					<td align="right" width="6%">Discount</td>
					<td align="right" width="6%">Tax Amount</td>
					<td align="right" width="6%">Grand Amount</td>
				</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblDayWiseSales"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:6%">					
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="15%"></td>
					<td align="right" width="15%">Total Bill</td>						
					<td align="right" width="15%">Sub Total</td>	
					<td align="right" width="15%">Total Discount</td>
					<td align="right" width="15%">Tax Total</td>
					<td align="right" width="15%">Total Amount</td>
					
			    </tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblDayWiseSalesTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:15%">					
					<col style="width:15%">
					<col style="width:15%">
					<col style="width:15%">
					<col style="width:16%">
					<col style="width:15%">					
				</table>
			</div>
			</div>
		</div>

<!-- Tax Wise -->
 	<div id="divTaxWiseSales" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="4%">Bill No</td>
					<td align="center" width="6%">Bill Date</td>
					<td width="4%">Tax Code</td>
					<td width="9%">Tax Name</td>
					<td align="right" width="4%">Tax Percentage</td>		
					<td align="right" width="6%">Taxable Amount</td>
					<td align="right" width="6%">Tax Amount</td>
				</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblTaxWiseSales"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:4%">					
					<col style="width:6%">
					<col style="width:4%">
					<col style="width:9%">
					<col style="width:4%">
					<col style="width:6%">
					<col style="width:6%">
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="45%"></td>					
					<td align="right" width="10%">Total Taxable</td>
					<td align="right" width="10%">Total Tax</td>
				</tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblTaxWiseSalesTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:45%">					
					<col style="width:10%">
					<col style="width:10%">
				</table>
			</div>
			</div>
	</div>
<!-- Tip wise -->
	<div id="divTipReport" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="6%">Bill No</td>					
					<td align="center" width="6%">Date</td>
					<td width="6%">Bill Time</td>
					<td width="6%">POS Code</td>
					<td width="6%">Set Mode</td>
					<td align="right" width="6%">Disc %</td>
					<td align="right" width="6%">Disc Amt</td>
					<td align="right" width="6%">Sub Total</td>
					<td align="right" width="6%">Tax Amt</td>
					<td align="right" width="6%">Tip Amt</td>
					<td align="right" width="6%">Sales Amount</td>
				</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblTipReport"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:6%">					
					<col style="width:6%">
					<col style="width:6%">					
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">					
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">					
					<col style="width:6%">
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="40%"></td>					
					<td align="right" width="10%">Discount</td>	
					<td align="right" width="10%">Sub Total</td>
					<td align="right" width="10%">Tax Total</td>
					<td align="right" width="10%">Tip Amount</td>
					<td align="right" width="10%">Sales Amount</td>
					
			    </tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblTipReportTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:40%">					
					<col style="width:10%">
					<col style="width:10%">
					<col style="width:10%">
					<col style="width:10%">
					<col style="width:10%">					
				</table>
			</div>
			</div>
		</div>
<!-- Item Modifier Wise -->		
	<div id="divItemModifierWise" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="6%">Modifier Name</td>
					<td width="6%">POS</td>					
					<td align="right" width="6%">Quantity</td>	
					<td align="right" width="6%">Sales Amount</td>
					</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblItemModifierWise"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:6%">					
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="30%"></td>					
					<td align="right" width="15%">Quantity</td>
					<td align="right" width="15%">Sales Amount</td>
				
					
			    </tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblItemModifierWiseTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:30%">					
					<col style="width:15%">
					<col style="width:15%">
				</table>
			</div>
			</div>
		</div>
<!-- MenuHeadWiseWithModifier -->
	<div id="divMenuHeadWiseWithModifier" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="6%">Menu Name</td>
					<td width="6%">POS</td>					
					<td align="right" width="6%">Quantity</td>	
					<td align="right" width="6%">Sales Amount</td>
					</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblMenuHeadWiseWithModifier"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:6%">					
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="20%"></td>					
					<td align="right" width="10%">Quantity</td>
					<td align="right" width="10%">Sales Amount</td>
					
			    </tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblMenuHeadWiseWithModifierTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:20%">					
					<col style="width:10%">
					<col style="width:10%">
				</table>
			</div>
			</div>
		</div>
		
<!-- Item Wise Hourly -->
		<div id="divItemHourlyWise" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="4%">Time Range</td>					
					<td width="8%">Item Name</td>
					<td align="right" width="6%">Quantity</td>
					<td align="right" width="6%">Item Amount</td>
					<td align="right" width="6%">Discount</td>	
				</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblItemHourlyWise"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:4%">
					<col style="width:8%">
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">					
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="30%"></td>					
					<td align="right" width="10%">Total Amount</td>	
					<td align="right" width="10%">Total Discount </td>
				</tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblItemHourlyWiseTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:30%">					
					<col style="width:10%">
					<col style="width:10%">
				</table>
			</div>
			</div>
		</div>
		
<!-- Operator Wise -->
		<div id="divOperatorWise" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="6%">Operator Code</td>					
					<td width="6%">Operator Name</td>
					<td width="6%">POS</td>
					<td width="6%">Payment Mode</td>
					<td align="right" width="6%">Disc Amount</td>	
					<td align="right" width="6%">Sales Amount</td>
				</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblOperatorWise"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">
					<col style="width:6%">						
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="40%"></td>					
					<td align="right" width="10%">Discount Amount</td>
					<td align="right" width="10%">Sales Amount</td>
					
			    </tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblOperatorWiseTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:40%">					
					<col style="width:10%">
					<col style="width:10%">
				</table>
			</div>
			</div>
		</div>
<!-- Monthly Wise -->		
	<div id="divMonthlySalesFlash" class="" style="width: 100%; height: 400px; display:none;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td align="center" width="6%">Month</td>
					<td align="center" width="6%">Year</td>					
					<td align="right" width="6%">Total Sales</td>
					</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblMonthlySalesFlash"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:6%">					
					<col style="width:6%">
					<col style="width:6%">
				</tbody>
				</table>
			</div>
			<div class="" style="width: 100%; height: 200px; display: block;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="25%"></td>					
					<td align="right" width="13%">Total Sale</td>
					
			    </tr>
			  </table>
		 	<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 100px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblMonthlySalesFlashTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:25%">					
					<col style="width:13%">
				</table>
			</div>
			</div>
		</div>
		
		
<!-- All Tables End Here -->
	</td>
			</tr>
			<tr>
			<td colspan="4"></td></tr>
			<tr>
			<td colspan="4">
			<%-- <div id="tableTotal" class="" style="width: 100%; height: 90px;" >
			<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="25%"></td>					
					<td width="20%">Sub Total</td>	
					<td width="15%">Quantity</td>
					<td width="15%">Disc</td>
					<td width="15%">Tax Total</td>
					<td width="15%">Sales Amount</td>
					<td width="15%">Tip Amount</td>
					
			  </tr>
			  </table>
		 	
		  <div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 50%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblTotalData"
					style="width: 100%; height: 90%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:25%">					
					<col style="width:15%">
					<col style="width:15%">
					<col style="width:15%">
					<col style="width:15%">
					<col style="width:15%">					
					<col style="width:15%">
				
				</table>
		  </div>
		 </div> --%>
		 <tr></tr>
		 <tr>
		 <%-- <img id="Desktop" src="../${pageContext.request.contextPath}/resources/images/""/> ... border: solid 0px #000000; width: 150px; height: 22px;" 
		 
		  <img  src="../${pageContext.request.contextPath}/resources/images/imgMoveUp.png" onclick="funMoveSelectedRow(1)">\\
		  <td><input type="submit" value="Submit" tabindex="3" style="background-image: url("/resources/images/imgSettlementWise.png");"/></td>
		 --%>
		 
		 <td colspan="4">
			<div id="tableImg" style="width: 980px; height: 120px; overflow-x: scroll; overflow-y: hidden;">
			<table style="height:120px; border: #0F0;width: 100%;font-size:11px;overflow-x: scroll; font-weight: bold;">	    
			 <tr bgcolor="#72BEFC">
			        
					<td style="margin-left: 10px;margin-right: 10px;"><img  src="../${pageContext.request.contextPath}/resources/images/imgSettlementWise.png" onclick="funSelectedReport('divSettlementWise')" ></td>
					<td style="text-align: center;"><img  src="../${pageContext.request.contextPath}/resources/images/imgBillWise.png" onclick="funSelectedReport('divBillWise')"></td>
					<td align="center"><img  src="../${pageContext.request.contextPath}/resources/images/imgItemWise.png" onclick="funSelectedReport('divItemWise')"></td>
					<td width="120px"><img  src="../${pageContext.request.contextPath}/resources/images/imgMenuHeadWise.png" onclick="funSelectedReport('divMenuHeadWise')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgGroupWise.png" onclick="funSelectedReport('divGroupWise')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgSubGroupWise.png" onclick="funSelectedReport('divSubGroupWise')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgCustWise.png" onclick="funSelectedReport('divCustWise')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgWaiterWise.png" onclick="funSelectedReport('divWaiterWise')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgDeliveryBoyWise.png" onclick="funSelectedReport('divDeliveryBoyWise')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgCostCenterWise.png" onclick="funSelectedReport('divCostCenterWise')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgHomeDeliveryWise.png" onclick="funSelectedReport('divHomeDeliveryWise')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgTableWise.png" onclick="funSelectedReport('divTableWise')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgHourlyWise.png" onclick="funSelectedReport('divHourlyWise')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgAreaWise.png" onclick="funSelectedReport('divAreaWise')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgDayWiseSales.png" onclick="funSelectedReport('divDayWiseSales')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgTaxWiseSales.png" onclick="funSelectedReport('divTaxWiseSales')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgTipreport.png" onclick="funSelectedReport('divTipReport')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgItemModifierWise.png" onclick="funSelectedReport('divItemModifierWise')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgMenuHeadWiseWithModifier.png" onclick="funSelectedReport('divMenuHeadWiseWithModifier')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgItemHourlyWise.png" onclick="funSelectedReport('divItemHourlyWise')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgOperatorWise.png" onclick="funSelectedReport('divOperatorWise')"></td>
					<td><img  src="../${pageContext.request.contextPath}/resources/images/imgMonthlySalesFlash.png" onclick="funSelectedReport('divMonthlySalesFlash')"></td>
			 </tr>
			</table>        	 	
			</div>
			 </td>
		 	</tr>
		</table>
		</div>
		<br/>
		<br/>
				<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:45%;left:45%;padding:2px;">
					<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
				</div>
	</div>
		 <div id="tab2" class="tab_content" style="height: 400px">
				
		<br/>
		<br/>
		<table class="masterTable">
		<tr>
	    	<td> 
			<label  style="display:inline-block; width:100px" >Operator </label>
			<s:select colspan="3" type="text" items="${Operator}" id="txtOperator" path="strOperator" cssClass="BoxW124px" />
		    </td>
		   	<td> 
			<label  style="display:inline-block; width:100px">Pay Mode </label>
			<s:select colspan="3" type="text" items="${PayMode}" id="txtPayMode" path="strPayMode" cssClass="BoxW124px" />
	     	</td>
	
		</tr>
		
		<tr>
	    	<td> 
			<label  style="display:inline-block; width:100px">Time From </label>
			<s:select colspan="3" type="text" items="${HH}" id="txtHHFrom" path="strHHFrom" cssClass="BoxW124px" >
			<option selected="selected" value="">HH</option>
			</s:select>
			<s:select colspan="3" type="text" items="${MM}" id="txtMMFrom" path="strMMFrom" cssClass="BoxW124px" >
			<option selected="selected" value="">MM</option>
			</s:select>
			<s:select id="txtAMPMFrom" path="strAMPMFrom" cssClass="BoxW124px">
					<option selected="selected" value="AM">AM</option>
					<option value="PM">PM</option>
					</s:select>
		    </td>
		   	<td> 
			<label style="display:inline-block; width:100px">To Time </label>
			<s:select colspan="3" type="text" items="${HH}" id="txtHHTo" path="strHHTo" cssClass="BoxW124px">
			<option selected="selected" value="">HH</option>
			</s:select>
			<s:select colspan="3" type="text" items="${MM}" id="txtMMTo" path="strMMTo" cssClass="BoxW124px">
			<option selected="selected" value="">MM</option>
			</s:select>
			<s:select id="txtAMPMTo" path="strAMPMTo" cssClass="BoxW124px">
					<option selected="selected" value="AM">AM</option>
					<option value="PM">PM</option>
			</s:select>
			</td>
		</tr>
		
		<tr>
				<td>
					<label style="display:inline-block; width:100px">From Bill No</label>
					<s:input type="text" id="txtFromBillNo" path="strFromBillNo" cssClass="longTextBox" />
				</td>
				<td>
					<label style="display:inline-block; width:100px">To Bill No</label>
					<s:input type="text" id="txtToBillNo" path="strToBillNo" cssClass="longTextBox" />
				</td>
		</tr>
		<tr>
				<td>
				<label style="display:inline-block; width:100px">Report Type</label>
				<s:select id="txtReportType" path="strReportType" cssClass="BoxW124px">
					<option selected="selected" value="Bill Wise">Bill Wise</option>
					<option value="Customer Wise">Customer Wise</option>
					<option value="Item Wise">Item Wise</option>
				</s:select>
				</td>
				<td>
				<label style="display:inline-block; width:100px">Type</label>
				<s:select id="txtType" path="strType" cssClass="BoxW124px">
					<option selected="selected" value="Data">Data</option>
					<option value="Chart">Chart</option>
				</s:select>
				</td>
		</tr>
		
		<tr>
				<td>
					<label style="display:inline-block; width:100px">Customer</label>
					<s:input type="text" id="txtCustomer" path="strCustomer" cssClass="searchTextBox" ondblclick="funHelp('POSCustomerMaster')" />
				</td>
				<td>
					<label style="display:inline-block; width:100px">Consolidate POS</label>
					<s:input type="checkbox"  id="chkConsolidatePOS" path="strConsolidatePOS"  style="width: 8%"></s:input>
				</td>
		</tr>
		
		</table>
		</div> 
		</div>
		
	</s:form>

</body>
</html>