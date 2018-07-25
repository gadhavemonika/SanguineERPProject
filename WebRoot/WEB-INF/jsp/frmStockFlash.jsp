<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
	<script type="text/javascript" src="<spring:url value="/resources/js/jQuery.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>	
	<script type="text/javascript" src="<spring:url value="/resources/js/validations.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/pagination.js"/>"></script>
        <!-- Load data to paginate -->
	<link rel="stylesheet" href="<spring:url value="/resources/css/pagination.css"/>" />

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript">

 		var StkFlashData;
 		var loggedInProperty="";
 		var loggedInLocation="";
 		$(document).ready(function() 
 				{
		  	loggedInProperty="${LoggedInProp}";
			loggedInLocation="${LoggedInLoc}";
			$("#cmbProperty").val(loggedInProperty);
			//alert(loggedInProperty);
			var propCode=$("#cmbProperty").val();
			//funFillLocationCombo(propCode);
			$("#divValueTotal").hide();
			funAddExportType();
		});	
 		function funChangeLocationCombo()
		{
			var propCode=$("#cmbProperty").val();
			funFillLocationCombo(propCode);
		}
	
 		function funFillCombo(code) {
 			var searchUrl = getContextPath() + "/loadSubGroupCombo.html?code="+ code;
 			$.ajax({
 				type : "GET",
 				url : searchUrl,
 				dataType : "json",
 				success : function(response) {
 					var html = '<option value="ALL">ALL</option>';
 					$.each(response, function(key, value) {
 						html += '<option value="' + key + '">' + value
 								+ '</option>';
 					});
 					html += '</option>';
 					$('#strSGCode').html(html);
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

		function funFillLocationCombo(propCode) 
		{
			var usercode='<%=session.getAttribute("usercode").toString()%>';
			var searchUrl = getContextPath() + "/loadForInventryLocationForProperty.html?propCode="+ propCode;
						
			
			$.ajax({
				type : "GET",
				url : searchUrl,
				dataType : "json",
				success : function(response) {
					var html = '';
					$.each(response, function(key, value) {
						html += '<option value="' + value[1] + '">'+value[0]
								+ '</option>';
					});
					html += '</option>';
					$('#cmbLocation').html(html);
				//	$("#cmbLocation").val(loggedInLocation);
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
 
		function funGetTotalValue(dblTotalValue)
		{
			$("#txtTotValue").val(parseFloat(dblTotalValue).toFixed(maxAmountDecimalPlaceLimit));
		}
	 	function showTable()
		{
			var optInit = getOptionsFromForm();
		    $("#Pagination").pagination(StkFlashData.length, optInit);	
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
		
		$(document).ready(function() 
		{
			var startDate="${startDate}";
			var arr = startDate.split("/");
			Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
			$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtFromDate").datepicker('setDate',Dat);
			$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtToDate").datepicker('setDate', 'today');
			$("#cmbLocation").val("${locationCode}");
			$("#btnExecute").click(function( event )
			{
				var fromDate=$("#txtFromDate").val();
				var toDate=$("#txtToDate").val();
				if($("#cmbReportType").val()=='Detail')
				{
					document.getElementById("divValueTotal").style.visibility = "visible";
					funCalculateStockFlashForDetail();
				}
				if($("#cmbReportType").val()=='Summary')
				{
					document.getElementById("divValueTotal").style.visibility = "visible";
					funCalculateStockFlashForSummary();
				}
				if($("#cmbReportType").val()=='Mini')
				{
					document.getElementById("divValueTotal").style.visibility = "visible";
					funCalculateMiniStockFlash();
				}
				if($("#cmbReportType").val()=='Total')
				{
					document.getElementById("divValueTotal").style.visibility = "hidden"; 
					funCalculateStockFlashForTotal();
				}
				
				
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
		
		
		
		function funClick(obj)
		{
			var prodCode=document.getElementById(""+obj.id+"").innerHTML;
			var locCode=$("#cmbLocation").val();
			var propCode=$("#cmbProperty").val();
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var param1=prodCode+","+locCode+","+propCode;
			window.open(getContextPath()+"/frmStockLedgerFromStockFlash.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate);
		}
		
		function pageselectCallback(page_index, jq)
		{
		    // Get number of elements per pagionation page from form
		    var max_elem = Math.min((page_index+1) * items_per_page, StkFlashData.length);
		    var newcontent="";
			var currValue='<%=session.getAttribute("currValue").toString()%>';
    		if(currValue==null)
    			{
    				currValue=1;
    			}	
		    if($("#cmbReportType").val()=='Detail')
		   	{		    	
			   	newcontent = '<table id="tblStockFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">Property Name</td><td id="labld2"> Product Code</td><td id="labld3"> Product Name</td>	<td id="labld4"> Location </td>	<td id="labld5"> Group</td><td id="labld6"> Sub Group</td><td id="labld7"> UOM</td><td id="labld8"> Bin No</td><td id="labld9"> Unit Price</td><td id="labld10"> Opening Stock</td><td id="labld11"> GRN</td><td id="labld12"> SCGRN</td><td id="labld13"> Stock Transfer In</td><td id="labld14"> Stock Adj In</td><td id="labld15"> MIS In</td><td id="labld16"> Qty Produced</td><td id="labld17"> Sales Return</td><td id="labld18"> Material Return</td><td id="labld19"> Purchase Return</td><td id="labld20"> Delivery Note</td><td id="labld21"> Stock Trans Out</td><td id="labld22"> Stock Adj Out</td><td id="labld23"> MIS Out</td><td id="labld24"> Qty Consumed</td><td id="labld25"> Sales</td><td id="labld26">Closing Stock</td><td id="labld27">Value</td><td id="labld28">Issue UOM Stock</td><td id="labld29">Issue Conversion</td><td id="labld30">Issue UOM </td><td id="labld31">Part No</td></tr>';
			   	// Iterate through a selection of the content and build an HTML string
			    for(var i=page_index*items_per_page;i<max_elem;i++)
			    {
			        newcontent += '<tr><td>'+StkFlashData[i].strPropertyName+'</td>';
			        //newcontent += '<td>'+StkFlashData[i].strProdCode+'</td>';
			        newcontent += '<td><a id="stkLedgerUrl.'+i+'" href="#" onclick="funClick(this);">'+StkFlashData[i].strProdCode+'</a></td>';
			        newcontent += '<td>'+StkFlashData[i].strProdName+'</td>';
			        newcontent += '<td>'+StkFlashData[i].strLocName+'</td>';
			        newcontent += '<td>'+StkFlashData[i].strGroupName+'</td>';
			        newcontent += '<td>'+StkFlashData[i].strSubGroupName+'</td>';
			        newcontent += '<td>'+StkFlashData[i].strUOM+'</td>';
			        newcontent += '<td>'+StkFlashData[i].strBinNo+'</td>';
			    	        
			        var qtyWithUOM=$("#cmbQtyWithUOM").val();
			        if(qtyWithUOM=='No')
			        {
				        newcontent += '<td align="right">'+(parseFloat(StkFlashData[i].dblCostRM)/currValue).toFixed(maxAmountDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblOpStock).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblGRN).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblSCGRN).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblStkTransIn).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblStkAdjIn).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblMISIn).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblQtyProduced).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblSalesReturn).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblMaterialReturnIn).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblPurchaseReturn).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblDeliveryNote).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblStkTransOut).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblStkAdjOut).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblMISOut).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblQtyConsumed).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				       
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblSales).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblClosingStock).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblValue/currValue).toFixed(maxAmountDecimalPlaceLimit) +'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblIssueUOMStock).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblIssueConversion+'</td>';
				        
			        }
			        else
				    {				        
				        newcontent += '<td align="right">'+StkFlashData[i].dblCostRM/currValue+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblOpStock+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblGRN+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblSCGRN+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblStkTransIn+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblStkAdjIn+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblMISIn+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblQtyProduced+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblSalesReturn+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblMaterialReturnIn+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblPurchaseReturn+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblDeliveryNote+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblStkTransOut+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblStkAdjOut+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblMISOut+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblQtyConsumed+'</td>';
				       
				        newcontent += '<td align="right">'+StkFlashData[i].dblSales+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblClosingStock+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblValue/currValue).toFixed(maxAmountDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblIssueUOMStock+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblIssueConversion+'</td>';
					}
			        
			        
			        newcontent += '<td>'+StkFlashData[i].strIssueUOM+'</td>';
			        newcontent += '<td>'+StkFlashData[i].strPartNo+'</td></tr>';
			    }			   
		    }
		    if($("#cmbReportType").val()=='Summary') 
		    {
			   	newcontent = '<table id="tblStockFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labls1">Property Name</td><td id="labls2">Product Code</td><td id="labls3">Product Name</td><td id="labls4">Location</td><td id="labls5">Group</td><td id="labls6">Sub Group</td><td id="labls7">UOM</td><td id="labls8">Bin No</td><td id="labls9">Unit Price</td><td id="labls10">Opening Stock</td><td id="labls11">Receipts</td><td id="labls12">Issue</td><td id="labls13">Closing Stock</td><td id="labls14">Value</td><td id="labls15">Issue UOM Stock</td><td id="labls16">Issue Conversion</td><td id="labls17">Issue UOM</td><td id="labls18">Part No</td></tr>';
					   
			    // Iterate through a selection of the content and build an HTML string
			    for(var i=page_index*items_per_page;i<max_elem;i++)
			    {
			        newcontent += '<tr><td>'+StkFlashData[i].strPropertyName+'</td>';
			        //newcontent += '<td>'+StkFlashData[i].strProdCode+'</td>';
			        newcontent += '<td><a id="stkLedgerUrl.'+i+'" href="#" onclick="funClick(this);">'+StkFlashData[i].strProdCode+'</a></td>';
			        //newcontent += '<td onclick="funClick(this);>'+StkFlashData[i].strProdCode+'</td>';
			        
			        newcontent += '<td>'+StkFlashData[i].strProdName+'</td>';
			        newcontent += '<td>'+StkFlashData[i].strLocName+'</td>';
			        newcontent += '<td>'+StkFlashData[i].strGroupName+'</td>';
			        newcontent += '<td>'+StkFlashData[i].strSubGroupName+'</td>';
			        newcontent += '<td>'+StkFlashData[i].strUOM+'</td>';
			        newcontent += '<td>'+StkFlashData[i].strBinNo+'</td>';
			        
			        var qtyWithUOM=$("#cmbQtyWithUOM").val();
			        if(qtyWithUOM=='No')
			        {
				        newcontent += '<td align="right">'+parseFloat((StkFlashData[i].dblCostRM/currValue)).toFixed(maxAmountDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblOpStock).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblReceipts).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblIssue).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblClosingStock).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblValue/currValue).toFixed(maxAmountDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblIssueUOMStock).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblIssueConversion+'</td>';				        
			        }			        
			        else
					{
				        newcontent += '<td align="right">'+StkFlashData[i].dblCostRM/currValue+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblOpStock+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblReceipts+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblIssue+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblClosingStock+'</td>';
				        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblValue/currValue).toFixed(maxAmountDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblIssueUOMStock+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i].dblIssueConversion+'</td>';
					}
			        
			        newcontent += '<td>'+StkFlashData[i].strIssueUOM+'</td>';
			        newcontent += '<td>'+StkFlashData[i].strPartNo+'</td><tr>';
			    }
		    }
		    if($("#cmbReportType").val()=='Mini') 
		    {
	        	newcontent = '<table id="tblStockFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labls2">Product Code</td><td id="labls3">Product Name</td><td id="labls13">Closing Stock</td><td id="labls14">Value</td><td style="width: 10%;"></td></tr>';
				   
			    // Iterate through a selection of the content and build an HTML string
			    for(var i=page_index*items_per_page;i<max_elem;i++)
			    {
			        newcontent += '<td><a id="stkLedgerUrl.'+i+'" href="#" onclick="funClick(this);">'+StkFlashData[i].strProdCode+'</a></td>';
			        newcontent += '<td>'+StkFlashData[i].strProdName+'</td>';
			        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblClosingStock).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
			        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblValue/currValue).toFixed(maxAmountDecimalPlaceLimit)+'</td>';

			        newcontent += '<td></td><tr>';
			    }	
		    }
		    if($("#cmbReportType").val()=='Total') 
		    {
	        	newcontent = '<table id="tblStockFlash" class="transTablex" style="width: 50%;  float:left;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labls3" style="font-size:14px;">Transaction Type</td><td align="right" id="labls14" style="font-size:14px;">Value</td></tr>';
				   
			    // Iterate through a selection of the content and build an HTML string
			    for(var i=page_index*items_per_page;i<max_elem;i++)
			    {
			    	
			        newcontent += '<td>'+StkFlashData[i].strPropertyName+'</td>';
			        newcontent += '<td align="right">'+parseFloat(StkFlashData[i].dblValue/currValue).toFixed(maxAmountDecimalPlaceLimit)+'</td><tr>';
			    }	
		    }
	
		    
		    
		    
		    newcontent += '</table>';
		    // Replace old content with new content
		   
		    $('#Searchresult').html(newcontent);
		   
		    // Prevent click eventpropagation
		    return false;
		}
				
		
		function funCalculateStockFlashForSummary()
		{
			var currValue='<%=session.getAttribute("currValue").toString()%>';
			var reportType=$("#cmbReportType").val();
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var locCode=$("#cmbLocation").val();
			var propCode=$("#cmbProperty").val();
			var showZeroItems=$("#cmbShowZeroItems").val();
			var strGCode= $('#strGCode').val();
			var strSGCode= $('#strSGCode').val();
			var strNonStkItems=$("#cmbNonStkItems").val();
			var qtyWithUOM=$("#cmbQtyWithUOM").val();
			var prodType=$("#txtProdType").val();
			var param1=reportType+","+locCode+","+propCode+","+showZeroItems+","+strSGCode+","+strNonStkItems+","+strGCode+","+qtyWithUOM;
			var manufCode=$("#txtManufacturerCode").val();
			var searchUrl=getContextPath()+"/frmStockFlashSummaryReport.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate+"&prodType="+prodType+"&ManufCode="+manufCode;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {				    	
				    	StkFlashData=response[0];
				    	showTable();
				    	funGetTotalValue(response[1]/currValue);
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
		
		
		function funCalculateStockFlashForDetail()
		{			
			var currValue='<%=session.getAttribute("currValue").toString()%>';
			var reportType=$("#cmbReportType").val();
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var locCode=$("#cmbLocation").val();
			var propCode=$("#cmbProperty").val();
			var showZeroItems=$("#cmbShowZeroItems").val();
			var strGCode= $('#strGCode').val();
			var strSGCode= $('#strSGCode').val();
			var strNonStkItems=$("#cmbNonStkItems").val();
			var qtyWithUOM=$("#cmbQtyWithUOM").val();
			var prodType=$("#txtProdType").val();
			
			var param1=reportType+","+locCode+","+propCode+","+showZeroItems+","+strSGCode+","+strNonStkItems+","+strGCode+","+qtyWithUOM;
			var paramForStkLedger=locCode+","+propCode;		
			var manufCode=$("#txtManufacturerCode").val();
			var searchUrl=getContextPath()+"/frmStockFlashDetailReport.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate+"&prodType="+prodType+"&ManufCode="+manufCode;
			//alert(searchUrl);
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	StkFlashData=response[0];
				    	showTable();
				    	funGetTotalValue(response[1]/currValue);	
				    	
				    	
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
		
		function funCalculateMiniStockFlash()
		{
			var currValue='<%=session.getAttribute("currValue").toString()%>';
			var reportType=$("#cmbReportType").val();
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var locCode=$("#cmbLocation").val();
			var propCode=$("#cmbProperty").val();
			var showZeroItems=$("#cmbShowZeroItems").val();
			var strGCode= $('#strGCode').val();
			var strSGCode= $('#strSGCode').val();
			var strNonStkItems=$("#cmbNonStkItems").val();
			var qtyWithUOM=$("#cmbQtyWithUOM").val();
			var prodType=$("#txtProdType").val();
			var param1=reportType+","+locCode+","+propCode+","+showZeroItems+","+strSGCode+","+strNonStkItems+","+strGCode+","+qtyWithUOM;
			var manufCode=$("#txtManufacturerCode").val();
			var searchUrl=getContextPath()+"/frmMiniStockFlashReport.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate+"&prodType="+prodType+"&ManufCode="+manufCode;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {				    	
				    	StkFlashData=response[0];
				    	showTable();
				    	funGetTotalValue(response[1]/currValue);
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
		
		
		function funCalculateStockFlashForTotal()
		{			
			var currValue='<%=session.getAttribute("currValue").toString()%>';
			var reportType=$("#cmbReportType").val();
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var locCode=$("#cmbLocation").val();
			var propCode=$("#cmbProperty").val();
			var showZeroItems=$("#cmbShowZeroItems").val();
			var strGCode= $('#strGCode').val();
			var strSGCode= $('#strSGCode').val();
			var strNonStkItems=$("#cmbNonStkItems").val();
			var qtyWithUOM=$("#cmbQtyWithUOM").val();
			var prodType=$("#txtProdType").val();
			
			var param1=reportType+","+locCode+","+propCode+","+showZeroItems+","+strSGCode+","+strNonStkItems+","+strGCode+","+qtyWithUOM;
			var paramForStkLedger=locCode+","+propCode;		
			var manufCode=$("#txtManufacturerCode").val();
			var searchUrl=getContextPath()+"/frmStockFlashTotalReport.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate+"&prodType="+prodType+"&ManufCode="+manufCode;
			//alert(searchUrl);
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	StkFlashData=response[0];
				    	showTable();		       	
				    //	funGetTotalValue(response[1]/currValue);	
				    	
				    	
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
		
		
		
		$(document).ready(function () 
		{			 
			$("#btnExport").click(function (e)
			{
				var reportType=$("#cmbReportType").val();
				var fromDate=$("#txtFromDate").val();
				var toDate=$("#txtToDate").val();
				var locCode=$("#cmbLocation").val();
				var propCode=$("#cmbProperty").val();
				var showZeroItems=$("#cmbShowZeroItems").val();
				var strGCode= $('#strGCode').val();
				var strSGCode= $('#strSGCode').val();
				var strNonStkItems=$("#cmbNonStkItems").val();
				var qtyWithUOM=$("#cmbQtyWithUOM").val();
				var strExportType=$("#cmbExportType").val();
				var prodType=$("#txtProdType").val();
				var param1=reportType+","+locCode+","+propCode+","+showZeroItems+","+strSGCode+","+strNonStkItems+","+strGCode+","+qtyWithUOM+","+strExportType;
				//var param1=reportType+","+locCode+","+propCode+","+showZeroItems;
				var manufCode=$("#txtManufacturerCode").val();
				if(reportType=='Summary' && strExportType=="PDF" )
					{
					window.location.href=getContextPath()+"/rptStockFlash.html?strExportType="+strExportType+"&prodType="+prodType+"&param1="+param1+"&ManufCode="+manufCode;
					}else if( reportType=='Mini')
						{
						window.location.href=getContextPath()+"/rptStkFlashMiniReport.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate+"&prodType="+prodType+"&ManufCode="+manufCode;
						}else 
							{
								window.location.href=getContextPath()+"/downloadExcel.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate+"&prodType="+prodType+"&ManufCode="+manufCode;
							}
						});
		});
		function funHelp(transactionName)
		{	       
	       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		function funSetData(code)
		{
			$("#txtManufacturerCode").val(code);
		}
		
		 		

        function funAddExportType()
		{
        	var cSelect = document.getElementById("cmbExportType"); 
        	while (cSelect.options.length > 0) 
	       	 { 
	       	  cSelect.remove(0); 
	       	 }
        	if($("#cmbReportType").val()=='Detail')
        	 {
        		cSelect.add(new Option('Excel'));
        	 }
        	else if($("#cmbReportType").val()=='Total')
	       	 {
	       		cSelect.add(new Option('Excel'));
	       	 }
        	else
        	 {
        		cSelect.add(new Option('Excel'));
        		cSelect.add(new Option('PDF'));
        	 }	 
		} 
		 
		 
	</script>
</head>
<body onload="funOnLoad();">
<div id="formHeading">
		<label>Stock Flash</label>
	</div>
	<s:form action="frmStockFlashReport.html" method="GET" name="frmStkFlash" target="_blank">
		<br>
	
			<table class="transTable">
			<tr><th colspan="10"></th></tr>
				<tr>
					<td width="10%">Property Code</td>
					<td width="20%">
						<s:select id="cmbProperty" name="propCode" path="strPropertyCode" cssClass="longTextBox" cssStyle="width:100%" onchange="funChangeLocationCombo();">
			    			<s:options items="${listProperty}"/>
			    		</s:select>
					</td>
						
					<td width="5%"><label>Location</label></td>
					<td>
						<s:select id="cmbLocation" name="locCode" path="strLocationCode" cssClass="longTextBox" cssStyle="width:180px;" >
			    			<s:options items="${listLocation}"/>
			    		</s:select>
					</td>
					<td><label>Group</label></td>
					<td><s:select path="strGCode" items="${listGroup}"
							id="strGCode" onchange="funFillCombo(this.value);" cssClass="BoxW124px"></s:select></td>
					<td><label>SubGroup</label></td>
					<td><s:select path="strSGCode" items="${listSubGroup}"
							id="strSGCode" cssClass="BoxW124px">
						</s:select>
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
			        <td>Report Type</td>
					<td>
						<s:select path="strReportType" id="cmbReportType" cssClass="BoxW124px" onchange="funAddExportType();">
							<option value="Detail">Detail</option>
							<option value="Summary">Summary</option>
							<option value="Mini">Mini</option>
							<option value="Total">Total</option>
						</s:select>
					</td>
											
					<td>Zero Transaction Products</td>
					<td>
						<select id="cmbShowZeroItems" class="BoxW124px">
							<option>No</option>
							<option>Yes</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td>Items Type</td>
					<td>
						<select id="cmbNonStkItems" class="BoxW124px">
							<option selected="selected">Stockable</option>
							<option>Non Stockable</option>
							<option>Both</option>
						</select>
					</td>
					
					<td>Quantity With UOM</td>
					<td >
						<select id="cmbQtyWithUOM" class="BoxW124px">
							<option selected="selected">No</option>
							<option>Yes</option>
						</select>
						
					</td>
					
					<td>Product Type</td>
						<td ><s:select id="txtProdType" name="prodType" path="strProdType" items="${typeList}" cssClass="BoxW124px"/></td>
						
					<td >Manufacturer Code</td>
				<td><s:input id="txtManufacturerCode" path="strManufacturerCode"
						cssClass="searchTextBox jQKeyboard form-control" readonly="true" ondblclick="funHelp('manufactureMaster')" /></td>
				</tr>
				
				<tr>
					<td><input id="btnExecute" type="button" class="form_button1" value="EXECUTE"/></td>
					
					<td>
						<s:select path="strExportType" id="cmbExportType"  cssClass="BoxW124px">
							<option value="Excel">Excel</option>
						</s:select>
					</td>
					<td colspan="7">						
						<input id="btnExport" type="button" value="EXPORT"  class="form_button1"/>
					</td>
				</tr>
			</table>
			
			<dl id="Searchresult" style="width: 95%; margin-left: 26px; overflow:auto;"></dl>
		<div id="Pagination" class="pagination" style="width: 80%;margin-left: 26px;">
		
		</div>
		
		<br>
		<br>
		
		
		<div id="divValueTotal"
			style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 40px; margin: auto; overflow-x: hidden; overflow-y: hidden; width: 95%;">
			<table id="tblTotalFlash" class="transTablex"
				style="width: 100%; font-size: 11px; font-weight: bold;">
				
				<tr style="margin-left: 28px">
				
					<td id="labld26" style="width:20%; text-align:right">Total</td>
					
					<td id="tdTotValue" style="width:10%; align:right">
						<input id="txtTotValue" style="width: 100%; text-align: right;" class="Box"></input>
					</td>

				</tr>
			</table>
		</div>
		
		
		
		
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
	</s:form>
	
</body>
</html>