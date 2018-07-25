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
	
		/**
		 * Ready Function for Initialize textField with default value
		 * And Set date in date picker 
		 */
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
				var reportType=$("#cmbReportType").val();
				var formName=$("#cmbFormName").val();
				
				if(formName=='frmPurchaseOrder')
				{
					if(reportType=='Summary')
					{
						funGetPODataForSummary();
					}
					else
					{
						funGetPODataForDetail();
					}
				}
				else if(formName=='frmPurchaseIndent')
				{
					if(reportType=='Summary')
					{
						funGetPIDataForSummary();
					}
					else
					{
						funGetPIDataForDetail();
					}
				}
				else if(formName=="frmMaterialReq")
				{
					if(reportType=='Summary')
					{
						funGetMatReqDataForSummary();
					}
					else
					{
						funGetMatReqDataForDetail();
					}
				}
				else if(formName=="frmSalesOrder")
				{
					if(reportType=='Summary')
					{
						funGetSODataForSummary();
					}
					else
					{
						funGetSODataForDetail();
					}
				}
			});
		});
		
			/**
			* Combo box onchage Event
			**/
			function funTransTypeOnChange() 
			{
				var cmbValue =  $("#cmbFormName").val();
				
				if(cmbValue=="frmMaterialReq")
				{
					$("#lblLocation1").text("Location By");
					$("#lblLocation2").text("Location On");
					$('#lblLocation2').css('visibility','visible');
					$('#tdLocation2').css('visibility','visible');
					$('#lblSuppCode').css('visibility','hidden');
					$('#tdPOStatus').css('visibility','hidden');
					funLoadLocation1('frmMaterialReq');
					funLoadLocation2('frmMaterialReq');
				}
				if(cmbValue=="frmPurchaseOrder")
				{
					$("#lblSupp").text("Supplier");
					$("#lblLocation1").text("Location");
					$('#lblSuppCode').css('visibility','visible');
					$('#tdPOStatus').css('visibility','visible');
					$('#tdLocation2').css('visibility','hidden');
					$('#lblLocation2').css('visibility','hidden');
					funLoadLocation1(cmbValue);
				}
				else if(cmbValue=="frmPurchaseIndent")
				{
					$("#lblLocation1").text("Location");
					$('#lblLocation2').css('visibility','hidden');
					$('#tdLocation2').css('visibility','hidden');
					$('#lblSuppCode').css('visibility','visible');
					$('#tdPOStatus').css('visibility','visible');
					/* $('#lblSuppCode').css('visibility','hidden');
					$('#tdPOStatus').css('visibility','hidden'); */
					funLoadLocation1(cmbValue);
				}
				else if(cmbValue=="frmSalesOrder")
				{
					$("#lblSupp").text("Customer");
					$("#lblLocation1").text("Location");
					$('#lblSuppCode').css('visibility','visible');
					$('#tdPOStatus').css('visibility','visible');
					$('#tdLocation2').css('visibility','hidden');
					$('#lblLocation2').css('visibility','hidden');
					funLoadLocation1(cmbValue);
				}
		}
			
		
		/**
		 * Remove all product from grid
		**/
		function funClearRows() 
		{
			var table = document.getElementById("tblPendingDoc");
			var rowCount = table.rows.length;

			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
		}
		
		/**
		 * Get Summary Data
		**/
		function funGetPODataForSummary()
		{
			var formName=$("#cmbFormName").val();
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var locCode=$("#txtLocBy").val();
			var reportType=$("#cmbReportType").val();
			
			if(formName=='frmMaterialReq')
			{
				locCode=locCode+'!'+$("#txtLocOn").val();
			}
			var strSuppCode=$("#txtSuppCode").val();
			if(strSuppCode.trim().length==0)
				{
					strSuppCode="ALL";
				}
			var strPOStatus=$("#cmbPOStatus").val(); 
			var param1=formName+","+locCode+","+reportType+","+strSuppCode+","+strPOStatus;
			var searchUrl=getContextPath()+"/loadPendingDocData.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate;
			
			//Filling Hedding in Grid
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funClearRows();
				    	var table = document.getElementById("tblPendingDoc");
						var rowCount = table.rows.length;
					    var row = table.insertRow(rowCount);
						row.insertCell(0).innerHTML= "<label id=\"label1\"> PO Code </lable>";
						row.insertCell(1).innerHTML= "<label id=\"label2\"> PO Date </lable> ";
						row.insertCell(2).innerHTML= "<label id=\"label3\"> Delivery Date </lable> ";
						row.insertCell(3).innerHTML= "<label id=\"label4\"> Supplier Code </lable> ";
						row.insertCell(4).innerHTML= "<label id=\"label5\"> Supplier Name </lable> ";
						row.insertCell(5).innerHTML= "<label id=\"label5\"> Against </lable> ";
						row.insertCell(6).innerHTML= "<label id=\"label5\"> Purchase Indent </lable> ";
						row.insertCell(7).innerHTML= "<label id=\"label6\"> PO Sub Total </lable> ";
						row.insertCell(8).innerHTML= "<label id=\"label7\"> Tax Amt</lable> ";
						row.insertCell(9).innerHTML= "<label id=\"label8\"> Final PO Amt</lable> ";
						row.insertCell(10).innerHTML= "<label id=\"label9\"> User </lable> ";
						row.insertCell(11).innerHTML= "<label id=\"label10\"> Date Created </lable> ";
						row.insertCell(12).innerHTML= "<label id=\"label11\"> Authorise </lable> ";
						row.insertCell(13).innerHTML= "<label id=\"label11\"> Authorise Level </lable> ";
						
						$.each(response, function(i,item)
						{			    		
							funFillPOSummary(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7]
								,item[8],item[9],item[10],item[11],item[12],item[13]);
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
		
		/**
		 * Filling PO Summary Data
		**/
		function funFillPOSummary(poCode,poDate,delDate,suppCode,suppName,against,purIndent,subTotal,taxAmt,finalAmt
				,user,dateCreated,authorise,authoriseLevel)
		{			
			var table = document.getElementById("tblPendingDoc");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			
			row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" value='"+poCode+"' />";
			
			row.insertCell(1).innerHTML= "<label>"+poDate+"</label>";
			row.insertCell(2).innerHTML= "<label>"+delDate+"</label>";
			row.insertCell(3).innerHTML= "<label>"+suppCode+"</label>";
			row.insertCell(4).innerHTML= "<label>"+suppName+"</label>";
			row.insertCell(5).innerHTML= "<label>"+against+"</label>";
			row.insertCell(6).innerHTML= "<label>"+purIndent+"</label>";
			row.insertCell(7).innerHTML= "<label>"+subTotal+"</label>";
			row.insertCell(8).innerHTML= "<label>"+taxAmt+"</label>";
			row.insertCell(9).innerHTML= "<label>"+finalAmt+"</label>";
			row.insertCell(10).innerHTML= "<label>"+user+"</label>";
			row.insertCell(11).innerHTML= "<label>"+dateCreated+"</label>";
			row.insertCell(12).innerHTML= "<label>"+authorise+"</label>";
			row.insertCell(13).innerHTML= "<label>"+authoriseLevel+"</label>";
		}
		
		/**
		 * Get PO Details Data
		**/
		function funGetPODataForDetail()
		{
			var formName=$("#cmbFormName").val();
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var locCode=$("#txtLocBy").val();
			
			var reportType=$("#cmbReportType").val();
			
			if(formName=='frmMaterialReq')
			{
				locCode=locCode+'!'+$("#txtLocOn").val();
			}
			var strSuppCode=$("#txtSuppCode").val();
			if(strSuppCode.trim().length==0)
			{
				strSuppCode="ALL";
			}
			strPOStatus=$("#cmbPOStatus").val(); 
			var param1=formName+","+locCode+","+reportType+","+strSuppCode+","+strPOStatus;
			var searchUrl=getContextPath()+"/loadPendingDocData.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate;

			//Filling Hedding in Grid
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funClearRows();
				    	var table = document.getElementById("tblPendingDoc");
						var rowCount = table.rows.length;
					    var row = table.insertRow(rowCount);
					    
					    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"width=100%;\" size=\"15%\" value='PO Code' />";
					    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"width=100%;\" size=\"15%\" value='PO Date' />";
						row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"width=100%;\" size=\"15%\" value='Delivery Date' />";
						row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"width=100%;\" size=\"15%\" value='Supplier Code' />";
						
						row.insertCell(4).innerHTML= "<label id=\"label5\"> Supplier Name </lable> ";
						row.insertCell(5).innerHTML= "<label id=\"label5\"> Against </lable> ";
						row.insertCell(6).innerHTML= "<label id=\"label5\"> Purchase Indent </lable> ";
						row.insertCell(7).innerHTML= "<label id=\"label6\"> PO Sub Total </lable> ";
						row.insertCell(8).innerHTML= "<label id=\"label7\"> Tax Amt</lable> ";
						row.insertCell(9).innerHTML= "<label id=\"label8\"> Final PO Amt</lable> ";
						row.insertCell(10).innerHTML= "<label id=\"label9\"> Product Code </lable> ";
						row.insertCell(11).innerHTML= "<label id=\"label10\"> Product Name </lable> ";
						row.insertCell(12).innerHTML= "<label id=\"label11\"> UOM </lable> ";
						row.insertCell(13).innerHTML= "<label id=\"label12\"> Order Qty </lable> ";
						row.insertCell(14).innerHTML= "<label id=\"label13\"> Price </lable> ";
						row.insertCell(15).innerHTML= "<label id=\"label14\"> Total Price </lable> ";
						row.insertCell(16).innerHTML= "<label id=\"label15\"> Received Qty </lable>";
						row.insertCell(17).innerHTML= "<label id=\"label16\"> Balance Qty </lable>";
						row.insertCell(18).innerHTML= "<label id=\"label16\"> total Pur Price</lable>";
						row.insertCell(19).innerHTML= "<label id=\"label17\"> GRN Code</lable>";
						row.insertCell(20).innerHTML= "<label id=\"label18\"> User </lable> ";
						row.insertCell(21).innerHTML= "<label id=\"label19\"> Date Created </lable> ";
						row.insertCell(22).innerHTML= "<label id=\"label20\"> Authorise </lable> ";
						row.insertCell(23).innerHTML= "<label id=\"label21\"> Authorise Level </lable> ";
						
						$.each(response, function(i,item)
						{
							funFillPODetail(item[0],item[1],item[2],item[3],item[4],item[5],item[6]
								,item[7],item[8],item[9],item[10],item[11],item[12],item[13]
								,item[14],item[15],item[16],item[17],item[18],item[19],item[20],item[21],item[22],item[23]);
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
		
		/**
		 * Filling PO Details Data
		**/
		function funFillPODetail(poCode,poDate,delDate,suppCode,suppName,against,purIndent,subTotal,taxAmt,finalAmt
				,pordCode,prodName,uom,orderQty,price,totalPrice,recQty,balQty,GRNCode,user,dateCreated,authorise,authoriseLevel,purPrice)
		{
			var totalAmt=parseFloat(totalPrice);
			totalAmt=totalAmt.toFixed(2);
			var table = document.getElementById("tblPendingDoc");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			
			row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"\" value='"+poCode+"' />";
			row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"\" value='"+poDate+"' />";
			row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"\" value='"+delDate+"' />";
			row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"\" value='"+suppCode+"' />";
			row.insertCell(4).innerHTML= "<label>"+suppName+"</label>";
			row.insertCell(5).innerHTML= "<label>"+against+"</label>";
			row.insertCell(6).innerHTML= "<label>"+purIndent+"</label>";

			row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"10%\" value='"+subTotal+"' />";
			row.insertCell(8).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"10%\" value='"+taxAmt+"' />";
			row.insertCell(9).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"10%\" value='"+finalAmt+"' />";
			row.insertCell(10).innerHTML= "<label>"+pordCode+"</label>";
			row.insertCell(11).innerHTML= "<label>"+prodName+"</label>";
			row.insertCell(12).innerHTML= "<label>"+uom+"</label>";
			
			row.insertCell(13).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"10%\" value='"+orderQty+"' />";
			row.insertCell(14).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"10%\" value='"+price+"' />";
			row.insertCell(15).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"10%\" value='"+totalAmt+"' />";
			row.insertCell(16).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"10%\" value='"+recQty+"' />";
			
			row.insertCell(17).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"width=10%;text-align: right;\" size=\"10%\" value='"+balQty+"' />";
			row.insertCell(18).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"width=10%;text-align: right;\" size=\"10%\" value='"+purPrice+"' />";
			
			row.insertCell(19).innerHTML= "<label>"+GRNCode+"</label>";
			row.insertCell(20).innerHTML= "<label>"+user+"</label>";
			row.insertCell(21).innerHTML= "<label>"+dateCreated+"</label>";
			row.insertCell(22).innerHTML= "<label>"+authorise+"</label>";
			row.insertCell(23).innerHTML= "<label>"+authoriseLevel+"</label>";
		}
		
		
		/**
		 * Get PI summary Data with validation
		**/
		function funGetPIDataForSummary()
		{
			var formName=$("#cmbFormName").val();
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var locCode=$("#txtLocBy").val();
			if(locCode.trim().length==0)
			{
				alert("Please Select Location");
				$("#txtLocBy").focus();
				return false;
			}
			var reportType=$("#cmbReportType").val();
			
			if(formName=='frmMaterialReq')
			{
				locCode=locCode+'!'+$("#txtLocOn").val();
			}
			var strSuppCode=$("#txtSuppCode").val();
			if(strSuppCode.trim().length==0)
			{
				strSuppCode="ALL";
			}
			var strPOStatus=$("#cmbPOStatus").val(); 
			var param1=formName+","+locCode+","+reportType+","+strSuppCode+","+strPOStatus;
			var searchUrl=getContextPath()+"/loadPendingDocData.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funClearRows();
				    	var table = document.getElementById("tblPendingDoc");
						var rowCount = table.rows.length;
					    var row = table.insertRow(rowCount);
						row.insertCell(0).innerHTML= "<label id=\"label1\"> PI Code </label>";
						row.insertCell(1).innerHTML= "<label id=\"label2\"> PI Date </label> ";
						row.insertCell(2).innerHTML= "<label id=\"label3\"> Location </label> ";
						row.insertCell(3).innerHTML= "<label id=\"label4\"> Narration </label> ";
						row.insertCell(4).innerHTML= "<label id=\"label5\"> Total Amount </label> ";
						
						$.each(response, function(i,item)
						{			    		
							funFillPISummary(item[0],item[1],item[2],item[3],item[4]);
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
		
		/**
		 * Fill PI summary Data
		**/
		function funFillPISummary(piCode,piDate,location,narration,amt)
		{			
			var table = document.getElementById("tblPendingDoc");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			
			var totalAmt=parseFloat(amt);
			totalAmt=totalAmt.toFixed(2);
			row.insertCell(0).innerHTML= "<label>"+piCode+"</label>";
			row.insertCell(1).innerHTML= "<label>"+piDate+"</label>";
			row.insertCell(2).innerHTML= "<label>"+location+"</label>";
			row.insertCell(3).innerHTML= "<label>"+narration+"</label>";
			row.insertCell(4).innerHTML= "<label>"+totalAmt+"</label>";
		}
		
		
		/**
		 * Get PI Details Data
		**/
		function funGetPIDataForDetail()
		{
			var formName=$("#cmbFormName").val();
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var locCode=$("#txtLocBy").val();
			if(locCode.trim().length==0)
			{
				alert("Please Select Location");
				$("#txtLocBy").focus();
				return false;
			}
			var reportType=$("#cmbReportType").val();
			
			if(formName=='frmMaterialReq')
			{
				locCode=locCode+'!'+$("#txtLocOn").val();
			}
			
			var strSuppCode=$("#txtSuppCode").val();
			if(strSuppCode.trim().length==0)
			{
				strSuppCode="ALL";
			}
			
			var strPOStatus=$("#cmbPOStatus").val(); 
			var param1=formName+","+locCode+","+reportType+","+strSuppCode+","+strPOStatus;
			var searchUrl=getContextPath()+"/loadPendingDocData.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funClearRows();
				    	var table = document.getElementById("tblPendingDoc");
						var rowCount = table.rows.length;
					    var row = table.insertRow(rowCount);
						row.insertCell(0).innerHTML= "<label id=\"label1\"> PI Code </label>";
						row.insertCell(1).innerHTML= "<label id=\"label2\"> Product Code </label>";
						row.insertCell(2).innerHTML= "<label id=\"label3\"> Product Name </label>";
						row.insertCell(3).innerHTML= "<label id=\"label4\"> UOM </label>";
						row.insertCell(4).innerHTML= "<label id=\"label5\"> Qty </label>";
						row.insertCell(5).innerHTML= "<label id=\"label6\"> Reorder Qty </label>";
						row.insertCell(6).innerHTML= "<label id=\"label7\"> Purpose </label>";
						
						$.each(response, function(i,item)
						{
							funFillPIDetail(item[0],item[1],item[2],item[3],item[4],item[5],item[6]);
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
		
		/**
		 * Fill PI Details Data
		**/
		function funFillPIDetail(piCode,prodCode,prodName,uom,qty,reorderQty,purpose)
		{
			var table = document.getElementById("tblPendingDoc");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			
			row.insertCell(0).innerHTML= "<label>"+piCode+"</label>";
			row.insertCell(1).innerHTML= "<label>"+prodCode+"</label>";
			row.insertCell(2).innerHTML= "<label>"+prodName+"</label>";
			row.insertCell(3).innerHTML= "<label>"+uom+"</label>";
			row.insertCell(4).innerHTML= "<label>"+qty+"</label>";
			row.insertCell(5).innerHTML= "<label>"+reorderQty+"</label>";
			row.insertCell(6).innerHTML= "<label>"+purpose+"</label>";
		}
			
		/**
		 * Get Material Requisition Summary Data
		**/
		function funGetMatReqDataForSummary()
		{
			var formName=$("#cmbFormName").val();
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();			
			var reportType=$("#cmbReportType").val();
			var locCode=$("#txtLocBy").val();	
			if(locCode.trim().length==0)
			{
				alert("Please Select Location");
				$("#txtLocBy").focus();
				return false;
			}
			if($("#txtLocOn").val().trim().length==0)
			{
				alert("Please Select Location By");
				$("#txtLocOn").focus();
				return false;
			}
			locCode=locCode+'!'+$("#txtLocOn").val();
			
			var param1=formName+","+locCode+","+reportType;
			var searchUrl=getContextPath()+"/loadPendingDocData.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funClearRows();
				    	var table = document.getElementById("tblPendingDoc");
						var rowCount = table.rows.length;
					    var row = table.insertRow(rowCount);
						row.insertCell(0).innerHTML= "<label id=\"label1\"> Req Code </label>";
						row.insertCell(1).innerHTML= "<label id=\"label2\"> Req Date </label> ";
						row.insertCell(2).innerHTML= "<label id=\"label3\"> Location By </label> ";
						row.insertCell(3).innerHTML= "<label id=\"label4\"> Location On </label> ";
						row.insertCell(4).innerHTML= "<label id=\"label5\"> Remarks </label> ";
						row.insertCell(5).innerHTML= "<label id=\"label6\"> User Created </label> ";
						row.insertCell(6).innerHTML= "<label id=\"label7\"> Created Date </label> ";
						row.insertCell(7).innerHTML= "<label id=\"label8\"> Authorise </label> ";
						row.insertCell(8).innerHTML= "<label id=\"label9\"> Authorise Level </label> ";
						
						$.each(response, function(i,item)
						{			    		
							funFillMatReqSummary(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8]);
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
		

		/**
		 * Fill Material Requisition Summary Data
		**/
		function funFillMatReqSummary(reqCode,reqDate,locationBy,locationOn,remarks,userCreated,createdDate,authorise,authoriseLevel)
		{			
			var table = document.getElementById("tblPendingDoc");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			
			row.insertCell(0).innerHTML= "<label>"+reqCode+"</label>";
			row.insertCell(1).innerHTML= "<label>"+reqDate+"</label>";
			row.insertCell(2).innerHTML= "<label>"+locationBy+"</label>";
			row.insertCell(3).innerHTML= "<label>"+locationOn+"</label>";
			row.insertCell(4).innerHTML= "<label>"+remarks+"</label>";
			row.insertCell(5).innerHTML= "<label>"+userCreated+"</label>";
			row.insertCell(6).innerHTML= "<label>"+createdDate+"</label>";
			row.insertCell(7).innerHTML= "<label>"+authorise+"</label>";
			row.insertCell(8).innerHTML= "<label>"+authoriseLevel+"</label>";
		}
		

		/**
		 * Get Material Requisition Details Data
		**/
		function funGetMatReqDataForDetail()
		{
			var formName=$("#cmbFormName").val();
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			
			var locCode=$("#txtLocBy").val();
			if(locCode.trim().length==0)
				{
					alert("Please Select Location");
					$("#txtLocBy").focus();
					return false;
				}
			if($("#txtLocOn").val().trim().length==0)
			{
				alert("Please Select Location By");
				$("#txtLocOn").focus();
				return false;
			}
			var reportType=$("#cmbReportType").val();
			
			locCode=locCode+'!'+$("#txtLocOn").val();
			
			var param1=formName+","+locCode+","+reportType;
			var searchUrl=getContextPath()+"/loadPendingDocData.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funClearRows();
				    	var table = document.getElementById("tblPendingDoc");
						var rowCount = table.rows.length;
					    var row = table.insertRow(rowCount);
					    row.insertCell(0).innerHTML= "<label id=\"label1\"> Req Code </label>";
						row.insertCell(1).innerHTML= "<label id=\"label2\"> Req Date </label> ";
						row.insertCell(2).innerHTML= "<label id=\"label3\"> Location By </label> ";
						row.insertCell(3).innerHTML= "<label id=\"label4\"> Location On </label> ";
						row.insertCell(4).innerHTML= "<label id=\"label5\"> Product Code </label>";
						row.insertCell(5).innerHTML= "<label id=\"label6\"> Part No </label>";
						row.insertCell(6).innerHTML= "<label id=\"label7\"> Product Name </label>";
						row.insertCell(7).innerHTML= "<label id=\"label8\"> Req Qty </label>";
						row.insertCell(8).innerHTML= "<label id=\"label9\"> MIS Qty </label>";
						row.insertCell(9).innerHTML= "<label id=\"label10\"> Pending Qty </label>";
						row.insertCell(10).innerHTML= "<label id=\"label11\"> Price </label>";
						row.insertCell(11).innerHTML= "<label id=\"label12\"> Value </label>";
						row.insertCell(12).innerHTML= "<label id=\"label13\"> Remarks </label>";
						row.insertCell(13).innerHTML= "<label id=\"label14\"> Issue UOM </label>";
						row.insertCell(14).innerHTML= "<label id=\"label15\"> Narration </label>";
						row.insertCell(15).innerHTML= "<label id=\"label16\"> User Created </label>";
						row.insertCell(16).innerHTML= "<label id=\"label17\"> Created Date </label>";
						row.insertCell(17).innerHTML= "<label id=\"label18\"> Authorise </label>";
						row.insertCell(18).innerHTML= "<label id=\"label19\"> Authorise Level </label>";
						
						$.each(response, function(i,item)
						{
							funFillMatReqDetail(item[0],item[1],item[2],item[3],item[4],item[5],item[6],
									item[7],item[8],item[9],item[10],item[11],item[12],item[13],
									item[14],item[15],item[16],item[17],item[18]);
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
		
		/**
		 * Fill Material Requisition Details Data
		**/
		function funFillMatReqDetail(reqCode,reqDate,locBy,locOn,prodCode,partNo,prodName,reqQty,MISQty,pendingQty
				,price,value,remarks,issueUOM,narration,userCreated,createdDate,authorise,authoriseLevel)
		{
			var table = document.getElementById("tblPendingDoc");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			
			row.insertCell(0).innerHTML= "<label>"+reqCode+"</label>";
			row.insertCell(1).innerHTML= "<label>"+reqDate+"</label>";
			row.insertCell(2).innerHTML= "<label>"+locBy+"</label>";
			row.insertCell(3).innerHTML= "<label>"+locOn+"</label>";			
			row.insertCell(4).innerHTML= "<label>"+prodCode+"</label>";
			row.insertCell(5).innerHTML= "<label>"+partNo+"</label>";
			row.insertCell(6).innerHTML= "<label>"+prodName+"</label>";
			row.insertCell(7).innerHTML= "<label>"+reqQty+"</label>";
			row.insertCell(8).innerHTML= "<label>"+MISQty+"</label>";
			row.insertCell(9).innerHTML= "<label>"+pendingQty+"</label>";
			row.insertCell(10).innerHTML= "<label>"+price+"</label>";
			row.insertCell(11).innerHTML= "<label>"+value+"</label>";
			row.insertCell(12).innerHTML= "<label>"+remarks+"</label>";
			row.insertCell(13).innerHTML= "<label>"+issueUOM+"</label>";
			row.insertCell(14).innerHTML= "<label>"+narration+"</label>";			
			row.insertCell(15).innerHTML= "<label>"+userCreated+"</label>";
			row.insertCell(16).innerHTML= "<label>"+createdDate+"</label>";
			row.insertCell(17).innerHTML= "<label>"+authorise+"</label>";
			row.insertCell(18).innerHTML= "<label>"+authoriseLevel+"</label>";
		}
		
		
		
		/**
		 * Excel Export
		**/
		$(document).ready(function () 
		{
			$("#btnExport").click(function (e)
			{
				var formName=$("#cmbFormName").val();
				var fromDate=$("#txtFromDate").val();
				var toDate=$("#txtToDate").val();
				var locCode=$("#txtLocBy").val();
				var reportType=$("#cmbReportType").val();
				locCode=locCode+'!'+$("#txtLocOn").val();
				var strSuppCode=$("#txtSuppCode").val();
				if(strSuppCode.trim().length==0)
				{
					strSuppCode="ALL";
				}
				strPOStatus=$("#cmbPOStatus").val(); 
				var param1=formName+","+locCode+","+reportType+","+strSuppCode+","+strPOStatus;
				window.location.href=getContextPath()+"/exportDataForPendingDoc.html?param="+param1+"&fDate="+fromDate+"&tDate="+toDate;
			});
		});
		 
		/**
		 * Open help
		**/
		var fieldName;
		function funHelp(transactionName)
		{
			var frmSelect=$("#cmbFormName").val();
			if(frmSelect=="frmSalesOrder"){
				if(transactionName=="suppcode"){
					transactionName="custMaster";	
				}
			}
			fieldName=transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		}
		function funSetData(code)
		{	
			switch (fieldName)
			{
				case 'locby':
					funSetLocationBy(code);					
				break;				
				case 'locon':
					funSetLocationOn(code);					
				break;			
				case 'suppcode':
			    	funSetSupplier(code);
			        break;
				case 'custMaster' :
					funSetCustomer(code);
			        break;
				
			}
				
		}
		
		/**
		 * Get and set Location By Data passing value Location code
		**/
		function funSetLocationBy(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadLocationMasterData.html?locCode="+code;			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if (response.strLocCode == 'Invalid Code') {
							alert("Invalid Location Code");
							$("#txtLocBy").val('');
							$("#lblLocBy").text("");
							$("#txtLocBy").focus();
						}
				    	else
				    		{
				    		$("#txtLocBy").val(response.strLocCode);
				    		$("#lblLocBy").text(response.strLocName);
		        			strLocationType=response.strType;
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
		 * Get and set Location On Data passing value Location code
		**/
		function funSetLocationOn(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadLocationMasterData.html?locCode="+code;			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if (response.strLocCode == 'Invalid Code') {
							alert("Invalid Location Code");
							$("#txtLocOn").val('');
							$("#lblLocOn").text("");
							$("#txtLocOn").focus();
						}
				    	else
				    		{
					    	$("#txtLocOn").val(response.strLocCode);
					    	$("#lblLocOn").text(response.strLocName);
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
		 * Get and set supplier Data passing value supplier code
		**/
		function funSetSupplier(code) {
			var searchUrl = "";
			searchUrl = getContextPath()
					+ "/loadSupplierMasterData.html?partyCode=" + code;

			$.ajax({
				type : "GET",
				url : searchUrl,
				dataType : "json",
				success : function(response) {
					if ('Invalid Code' == response.strPCode) {
						alert('Invalid Supplier Code');
						$("#txtSuppCode").val('');
						$("#txtSuppName").text('');
						$("#txtSuppCode").focus();
					} else {
						$("#txtSuppCode").val(response.strPCode);
						$("#txtSuppName").text(response.strPName);
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

		 function funSetCustomer(code)
			{
				gurl=getContextPath()+"/loadPartyMasterData.html?partyCode=";
				$.ajax({
			        type: "GET",
			        url: gurl+code,
			        dataType: "json",
			        success: function(response)
			        {		        	
			        	if ('Invalid Code' == response.strPCode) {
							alert('Invalid Supplier Code');
							$("#txtSuppCode").val('');
							$("#txtSuppName").text('');
							$("#txtSuppCode").focus();
						} else {
							$("#txtSuppCode").val(response.strPCode);
							$("#txtSuppName").text(response.strPName);
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
		 * Textfiled on blur event
		**/
		$(function() {

			$('#txtLocBy').blur(function() {				
				var code = $('#txtLocBy').val();
				if(code.trim().length > 0 && code !="?" && code !="/"){
					funSetLocationBy(code);
			}
		});
			
			$('#txtLocOn').blur(function() {				
					var code = $('#txtLocOn').val();
					if(code.trim().length > 0 && code !="?" && code !="/"){
						funSetLocationOn(code);
				}
			});

			$('#txtSuppCode').blur(function() {
				var code = $('#txtSuppCode').val();
				if(code.trim().length > 0 && code !="?" && code !="/"){
					funSetSupplier(code);
				}
			});
			$("#cmbPOStatus").val("ALL");
		});
		
		
		/**
		 * Get Summary Data
		**/
		function funGetSODataForSummary()
		{
			var formName=$("#cmbFormName").val();
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var locCode=$("#txtLocBy").val();
			var reportType=$("#cmbReportType").val();
			
			if(formName=='frmMaterialReq')
			{
				locCode=locCode+'!'+$("#txtLocOn").val();
			}
			var strSuppCode=$("#txtSuppCode").val();
			if(strSuppCode.trim().length==0)
				{
					strSuppCode="ALL";
				}
			var strPOStatus=$("#cmbPOStatus").val(); 
			var param1=formName+","+locCode+","+reportType+","+strSuppCode+","+strPOStatus;
			var searchUrl=getContextPath()+"/loadPendingDocData.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate;
			
			//Filling Hedding in Grid
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funClearRows();
				    	var table = document.getElementById("tblPendingDoc");
						var rowCount = table.rows.length;
					    var row = table.insertRow(rowCount);
						row.insertCell(0).innerHTML= "<label id=\"label1\"> SO Code </lable>";
						row.insertCell(1).innerHTML= "<label id=\"label2\"> SO Date </lable> ";
						row.insertCell(2).innerHTML= "<label id=\"label4\"> Cust Code </lable> ";
						row.insertCell(3).innerHTML= "<label id=\"label5\"> Cust Name </lable> ";
						row.insertCell(4).innerHTML= "<label id=\"label5\"> Against </lable> ";
						row.insertCell(5).innerHTML= "<label id=\"label6\"> SO Sub Total </lable> ";
						row.insertCell(6).innerHTML= "<label id=\"label7\"> Tax Amt</lable> ";
						row.insertCell(7).innerHTML= "<label id=\"label8\"> Final SO Amt</lable> ";
						row.insertCell(8).innerHTML= "<label id=\"label8\"> Inv Code</lable> ";
						row.insertCell(9).innerHTML= "<label id=\"label8\"> Inv Tax Amt</lable> ";
						row.insertCell(10).innerHTML= "<label id=\"label8\"> Inv Total Amt</lable> ";
						row.insertCell(11).innerHTML= "<label id=\"label9\"> User </lable> ";
						row.insertCell(12).innerHTML= "<label id=\"label10\"> Date Created </lable> ";
						row.insertCell(13).innerHTML= "<label id=\"label11\"> Authorise </lable> ";
						row.insertCell(14).innerHTML= "<label id=\"label11\"> Authorise Level </lable> ";
						
						$.each(response, function(i,item)
						{			    		
							funFillSOSummary(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7]
								,item[8],item[9],item[10],item[11],item[12],item[13],item[14]);
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
		
		/**
		 * Filling PO Summary Data
		**/
		function funFillSOSummary(poCode,poDate,suppCode,suppName,against,subTotal,taxAmt,finalAmt
				,user,dateCreated,authorise,authoriseLevel,invCode,invTax,invTot)
		{			
			var table = document.getElementById("tblPendingDoc");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			
			row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" value='"+poCode+"' />";
			
			row.insertCell(1).innerHTML= "<label>"+poDate+"</label>";
			row.insertCell(2).innerHTML= "<label>"+suppCode+"</label>";
			row.insertCell(3).innerHTML= "<label>"+suppName+"</label>";
			row.insertCell(4).innerHTML= "<label>"+against+"</label>";
			row.insertCell(5).innerHTML= "<label>"+subTotal+"</label>";
			row.insertCell(6).innerHTML= "<label>"+taxAmt+"</label>";
			row.insertCell(7).innerHTML= "<label>"+finalAmt+"</label>";
			
			row.insertCell(8).innerHTML= "<label>"+invCode+"</label>";
			row.insertCell(9).innerHTML= "<label>"+invTax+"</label>";
			row.insertCell(10).innerHTML= "<label>"+invTot+"</label>";
			
			row.insertCell(11).innerHTML= "<label>"+user+"</label>";
			row.insertCell(12).innerHTML= "<label>"+dateCreated+"</label>";
			row.insertCell(13).innerHTML= "<label>"+authorise+"</label>";
			row.insertCell(14).innerHTML= "<label>"+authoriseLevel+"</label>";
		}
		
		/**
		 * Get PO Details Data
		**/
		function funGetSODataForDetail()
		{
			var formName=$("#cmbFormName").val();
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var locCode=$("#txtLocBy").val();
			
			var reportType=$("#cmbReportType").val();
			
			if(formName=='frmMaterialReq')
			{
				locCode=locCode+'!'+$("#txtLocOn").val();
			}
			var strSuppCode=$("#txtSuppCode").val();
			if(strSuppCode.trim().length==0)
			{
				strSuppCode="ALL";
			}
			strPOStatus=$("#cmbPOStatus").val(); 
			var param1=formName+","+locCode+","+reportType+","+strSuppCode+","+strPOStatus;
			var searchUrl=getContextPath()+"/loadPendingDocData.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate;

			//Filling Hedding in Grid
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funClearRows();
				    	var table = document.getElementById("tblPendingDoc");
						var rowCount = table.rows.length;
					    var row = table.insertRow(rowCount);
					    
					    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"width=100%;\" size=\"15%\" value='SO Code' />";
					    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"width=100%;\" size=\"15%\" value='SO Date' />";
						row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"width=100%;\" size=\"15%\" value='Cust Code' />";
					
						row.insertCell(3).innerHTML= "<label id=\"label5\"> Cust Name </lable> ";
						row.insertCell(4).innerHTML= "<label id=\"label4\"> Against </lable> ";
						row.insertCell(5).innerHTML= "<label id=\"label6\"> SO Sub Total </lable> ";
						row.insertCell(6).innerHTML= "<label id=\"label7\"> Tax Amt</lable> ";
						row.insertCell(7).innerHTML= "<label id=\"label8\"> Final SO Amt</lable> ";
						row.insertCell(8).innerHTML= "<label id=\"label9\"> Product Code </lable> ";
						row.insertCell(9).innerHTML= "<label id=\"label10\"> Product Name </lable> ";
						row.insertCell(10).innerHTML= "<label id=\"label11\"> UOM </lable> ";
						row.insertCell(11).innerHTML= "<label id=\"label12\"> Order Qty </lable> ";
						row.insertCell(12).innerHTML= "<label id=\"label13\"> Price </lable> ";
						row.insertCell(13).innerHTML= "<label id=\"label14\"> Total Price </lable> ";
						row.insertCell(14).innerHTML= "<label id=\"label15\"> Received Qty </lable>";
						row.insertCell(15).innerHTML= "<label id=\"label16\"> Balance Qty </lable>";
						row.insertCell(16).innerHTML= "<label id=\"label16\"> Sale Price </lable>";
						
						row.insertCell(17).innerHTML= "<label id=\"label17\"> Invoice Code</lable>";
						row.insertCell(18).innerHTML= "<label id=\"label18\"> User </lable> ";
						row.insertCell(19).innerHTML= "<label id=\"label19\"> Date Created </lable> ";
						row.insertCell(20).innerHTML= "<label id=\"label20\"> Authorise </lable> ";
						row.insertCell(21).innerHTML= "<label id=\"label21\"> Authorise Level </lable> ";
						
						$.each(response, function(i,item)
						{
							funFillSODetail(item[0],item[1],item[2],item[3],item[4],item[5],item[6]
								,item[7],item[8],item[9],item[10],item[11],item[12],item[13]
								,item[14],item[15],item[16],item[17],item[18],item[19],item[20],item[21],item[22]);
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
		
		/**
		 * Filling PO Details Data
		**/
		function funFillSODetail(poCode,poDate,suppCode,suppName,against,subTotal,taxAmt,finalAmt
				,pordCode,prodName,uom,orderQty,price,totalPrice,recQty,balQty,GRNCode,user,dateCreated,authorise,authoriseLevel,SalesPrice)
		{
			var totalAmt=parseFloat(totalPrice);
			totalAmt=totalAmt.toFixed(2);
			var table = document.getElementById("tblPendingDoc");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			
			row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"\" value='"+poCode+"' />";
			row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"\" value='"+poDate+"' />";
			row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"\" value='"+suppCode+"' />";
			row.insertCell(3).innerHTML= "<label>"+suppName+"</label>";
			row.insertCell(4).innerHTML= "<label>"+against+"</label>";
			
			row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"10%\" value='"+subTotal+"' />";
			row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"10%\" value='"+taxAmt+"' />";
			row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"10%\" value='"+finalAmt+"' />";
			row.insertCell(8).innerHTML= "<label>"+pordCode+"</label>";
			row.insertCell(9).innerHTML= "<label>"+prodName+"</label>";
			row.insertCell(10).innerHTML= "<label>"+uom+"</label>";
			
			row.insertCell(11).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"10%\" value='"+orderQty+"' />";
			row.insertCell(12).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"10%\" value='"+price+"' />";
			row.insertCell(13).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"10%\" value='"+totalAmt+"' />";
			row.insertCell(14).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"10%\" value='"+recQty+"' />";
			
			row.insertCell(15).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"width=10%;text-align: right;\" size=\"10%\" value='"+balQty+"' />";
			row.insertCell(16).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"width=10%;text-align: right;\" size=\"10%\" value='"+SalesPrice+"' />";
		
			row.insertCell(17).innerHTML= "<label>"+GRNCode+"</label>";
			row.insertCell(18).innerHTML= "<label>"+user+"</label>";
			row.insertCell(19).innerHTML= "<label>"+dateCreated+"</label>";
			row.insertCell(20).innerHTML= "<label>"+authorise+"</label>";
			row.insertCell(21).innerHTML= "<label>"+authoriseLevel+"</label>";
		}

	</script>
</head>
<body>
<div id="formHeading">
		<label>Pending Documents Flash</label>
	</div>
	<s:form action="frmPendingDocFlash.html" method="GET" name="frmStkFlash">
		<br>
			<table class="transTable">
			<tr><th colspan="7"></th></tr>
				<tr>
					<td width="15%">Transaction Type</td>
					<td>
						<s:select id="cmbFormName" path="strFormName" cssClass="longTextBox" cssStyle="width:160px;" onchange="funTransTypeOnChange() ;">
							<s:options items="${listFormName}"/>
						</s:select>
					</td>
					<td id="tdPOStatus">
						<s:select id="cmbPOStatus" path="strPoStatus" cssClass="longTextBox" cssStyle="width:100px;" >
							<s:options items="${listPOStatus}"/>
						</s:select>
					</td>
					 <td colspan="2" id="lblSuppCode" style="visibility:hidden;">
					 <label id="lblSupp" >Supplier</label>
				    <s:input id="txtSuppCode" path="strSupplierCode"  ondblclick="funHelp('suppcode')" cssClass="searchTextBox"  cssStyle="width:70px;background-position: 60px 2px;"/>
					
					<label id="txtSuppName" style="font-size: 12px;"></label></td>
				
				</tr>
				<tr>
					<td width="10%" id="lblLocation1"><label>Location</label></td>
			    		<td><s:input id="txtLocBy" name="txtLocBy" path="strLocationCode1" required = "true" ondblclick="funHelp('locby')"
						cssClass="searchTextBox" cssStyle="width:73px;background-position: 60px 2px;"></s:input>
						<label id="lblLocBy" class="namelabel"></label>
						</td>
					
					<td id="lblLocation2" style="visibility:hidden;" width="10%"><label>Location On</label></td>
					<td id="tdLocation2" style="visibility:hidden;" >
						<s:input id="txtLocOn" name="txtLocOn" path="strLocationCode2" required = "true"
						 ondblclick="funHelp('locon')"
						cssClass="searchTextBox"  cssStyle="width:70px;background-position: 60px 2px;" />
					<label id="lblLocOn" class="namelabel"></label>
					</td>
				
				</tr>
					
				<tr>
				    <td><label id="lblFromDate">From Date</label></td>
			        <td>
			            <s:input id="txtFromDate" name="fromDate" path="dteFromDate" cssClass="calenderTextBox"/>
			        	<s:errors path="dteFromDate"></s:errors>
			        </td>
				        
			        <td><label id="lblToDate">To Date</label></td>
			        <td colspan="5">
			            <s:input id="txtToDate" name="toDate" path="dteToDate" cssClass="calenderTextBox"/>
			        	<s:errors path="dteToDate"></s:errors>
			        </td>
			    
				</tr>
						
				<tr>
					<td >Report Type</td>
					<td colspan="6">
						<s:select path="strReportType" id="cmbReportType" cssClass="BoxW124px">
							<option value="Detail">Detail</option>
							<option value="Summary">Summary</option>
						</s:select>
					</td>		
				</tr>	

				<tr>
					<td><input id="btnExecute" type="button" class="form_button1" value="EXECUTE"/></td>
					<td  width="10%">
						<s:select path="strExportType" id="cmbExportType"  cssClass="BoxW124px">
							<option value="Excel">Excel</option>
						</s:select>
					</td>
					<td colspan="5">
						<input id="btnExport" type="button" value="EXPORT"  class="form_button1"/>
					</td>
				</tr>
			</table>
			
			<br>
			<div id="dvStock" style="width: 110% ;height: 100% ;padding-left: 26px;">
			<div style="width: 95%;
                 height: 400px;
                 overflow: scroll;
                 overflow-x:scroll;
                  overflow-y:scroll;
                 display: block;" >
				<table id="tblPendingDoc" border="1" class="transTablex"  style="table-layout:fixed;overflow:scroll;width: 195%;">
							
				</table>
				
				
			</div> 
		</div> 
		<br><br>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
		</div>
	</s:form>
	<script type="text/javascript">
	
	funTransTypeOnChange();
	</script>
</body>
</html>