<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GROUP MASTER</title>
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

.cell {
	background: inherit;
	border: 0 solid #060006;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	outline: 0 none;
	font-weight: bold;
	padding-left: 0;
	width: 80%
}

.header {
	border: #c0c0c0 1px solid;
	background: #78BEF9;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: bold;
	outline: 0 none;
	padding-left: 0;
	width: 100%;
	height:100%
}
</style>
<script type="text/javascript">


	
	$(document).ready(function() {
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
			alert(message);
		<%
		}}%>		

		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});

		 $(function() 
	    			{		
		 var POSDate="${POSDate}"
			    var startDate="${POSDate}";
			  	var Date = startDate.split(" ");
				var arr = Date[0].split("-");
				Dat=arr[2]+"-"+arr[1]+"-"+arr[0];	
				$("#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
				$("#txtFromDate" ).datepicker('setDate', Dat); 
				$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
				$("#txtToDate" ).datepicker('setDate', Dat); 
 				
	    			
				funLoadTableData();
	    			}); 
		
		$("[type='reset']").click(function(){
			location.reload(true);
		});
		
		$("form").submit(function(event){
			var table = document.getElementById("tblAuditFlash");
			var rowCount = table.rows.length;
			if (rowCount > 2){
				$("#txtFromDate").val(fDate);
				$("#txtToDate").val(tDate);
				return true;
			} else {
				alert("Data Not Available");
				return false;
			}
		});

		$("#btnSubmit1").click(function(event) {
			var fromDate = $("#txtFromDate").val();
			var toDate = $("#txtToDate").val();

			if (fromDate.trim() == '' && fromDate.trim().length == 0) {
				alert("Please Enter From Date");
				return false;
			}
			if (toDate.trim() == '' && toDate.trim().length == 0) {
				alert("Please Enter To Date");
				return false;
			}
			if(funDeleteTableAllRows()){
				if(CalculateDateDiff(fromDate,toDate)){
					fDate=fromDate;
					tDate=toDate;
					funFetchColNames( $("#btnSubmit1").val());
				}
			}
		});

	
	
	$("#btnSubmit2").click(function(event) {
		var fromDate = $("#txtFromDate").val();
		var toDate = $("#txtToDate").val();

		if (fromDate.trim() == '' && fromDate.trim().length == 0) {
			alert("Please Enter From Date");
			return false;
		}
		if (toDate.trim() == '' && toDate.trim().length == 0) {
			alert("Please Enter To Date");
			return false;
		}
		if(funDeleteTableAllRows()){
			if(CalculateDateDiff(fromDate,toDate)){
				fDate=fromDate;
				tDate=toDate;
				funFetchColNames( $("#btnSubmit2").val());
			}
		}
	});
	
	$("#btnSubmit3").click(function(event) {
		var fromDate = $("#txtFromDate").val();
		var toDate = $("#txtToDate").val();

		if (fromDate.trim() == '' && fromDate.trim().length == 0) {
			alert("Please Enter From Date");
			return false;
		}
		if (toDate.trim() == '' && toDate.trim().length == 0) {
			alert("Please Enter To Date");
			return false;
		}
		if(funDeleteTableAllRows()){
			if(CalculateDateDiff(fromDate,toDate)){
				fDate=fromDate;
				tDate=toDate;
				funFetchColNames( $("#btnSubmit3").val());
			}
		}
	});
	
	$("#btnSubmit4").click(function(event) {
		var fromDate = $("#txtFromDate").val();
		var toDate = $("#txtToDate").val();

		if (fromDate.trim() == '' && fromDate.trim().length == 0) {
			alert("Please Enter From Date");
			return false;
		}
		if (toDate.trim() == '' && toDate.trim().length == 0) {
			alert("Please Enter To Date");
			return false;
		}
		if(funDeleteTableAllRows()){
			if(CalculateDateDiff(fromDate,toDate)){
				fDate=fromDate;
				tDate=toDate;
				funFetchColNames( $("#btnSubmit4").val());
			}
		}
	});
	
	$("#btnSubmit5").click(function(event) {
		var fromDate = $("#txtFromDate").val();
		var toDate = $("#txtToDate").val();

		if (fromDate.trim() == '' && fromDate.trim().length == 0) {
			alert("Please Enter From Date");
			return false;
		}
		if (toDate.trim() == '' && toDate.trim().length == 0) {
			alert("Please Enter To Date");
			return false;
		}
		if(funDeleteTableAllRows()){
			if(CalculateDateDiff(fromDate,toDate)){
				fDate=fromDate;
				tDate=toDate;
				funFetchColNames( $("#btnSubmit5").val());
			}
		}
	});
	
	$("#btnSubmit6").click(function(event) {
		var fromDate = $("#txtFromDate").val();
		var toDate = $("#txtToDate").val();

		if (fromDate.trim() == '' && fromDate.trim().length == 0) {
			alert("Please Enter From Date");
			return false;
		}
		if (toDate.trim() == '' && toDate.trim().length == 0) {
			alert("Please Enter To Date");
			return false;
		}
		if(funDeleteTableAllRows()){
			if(CalculateDateDiff(fromDate,toDate)){
				fDate=fromDate;
				tDate=toDate;
				funFetchColNames( $("#btnSubmit6").val());
			}
		}
	});
	
	$("#btnSubmit7").click(function(event) {
		var fromDate = $("#txtFromDate").val();
		var toDate = $("#txtToDate").val();

		if (fromDate.trim() == '' && fromDate.trim().length == 0) {
			alert("Please Enter From Date");
			return false;
		}
		if (toDate.trim() == '' && toDate.trim().length == 0) {
			alert("Please Enter To Date");
			return false;
		}
		if(funDeleteTableAllRows()){
			if(CalculateDateDiff(fromDate,toDate)){
				fDate=fromDate;
				tDate=toDate;
				funFetchColNames( $("#btnSubmit7").val());
			}
		}
	});
	
	$("#btnSubmit8").click(function(event) {
		var fromDate = $("#txtFromDate").val();
		var toDate = $("#txtToDate").val();

		if (fromDate.trim() == '' && fromDate.trim().length == 0) {
			alert("Please Enter From Date");
			return false;
		}
		if (toDate.trim() == '' && toDate.trim().length == 0) {
			alert("Please Enter To Date");
			return false;
		}
		if(funDeleteTableAllRows()){
			if(CalculateDateDiff(fromDate,toDate)){
				fDate=fromDate;
				tDate=toDate;
				funFetchColNames( $("#btnSubmit8").val());
			}
		}
	});

});

	
	function funLoadTableData()
	{
		funFetchColNames("Modified Bill");
		
	}
	
	
	
	function funDeleteTableAllRows()
	{
		$('#tblAuditFlash tbody').empty();
		$('#tblTotal tbody').empty();
		var table = document.getElementById("tblAuditFlash");
		var rowCount1 = table.rows.length;
		if(rowCount1==0){
			return true;
		}else{
			return false;
		}
	}
	
	function CalculateDateDiff(fromDate,toDate) {

		var frmDate= fromDate.split('-');
	    var fDate = new Date(frmDate[2],frmDate[1],frmDate[0]);
	    
	    var tDate= toDate.split('-');
	    var t1Date = new Date(tDate[2],tDate[1],tDate[0]);

    	var dateDiff=t1Date-fDate;
  		 if (dateDiff >= 0 ) 
  		 {
         	return true;
         }else{
        	 alert("Please Check From Date And To Date");
        	 return false
         }
	}

	
	function funFetchColNames(btnId) {
		var fromDate = $('#txtFromDate').val();
		 var toDate = $('#txtToDate').val();
		var strReportType=$('#cmbReportType').val();
		var posName=$('#cmbPOSName').val();
		var strUserName=$('#cmbUserName').val();
		var strReason=$('#cmbReason').val();
		var auditType=btnId;
		document.getElementById("txtAuditType").value=btnId;
			var gurl = getContextPath() + "/loadAuditFlash.html";
		
		
		$.ajax({
			type : "GET",
			data:{ fromDate:fromDate,
					toDate:toDate,
					strReportType:strReportType,
					posName:posName,
					strUserName:strUserName,
					strReason:strReason,
					auditType:btnId,
				},
			url : gurl,
			dataType : "json",
			success : function(response) {
				if (response.RowCount == 0  ) {
					alert("Data Not Found");
				} else {
					
					//Add Sub Category Headers
					funAddHeaderRow(response.ColHeader);
					
					//Add Size Names And Headers
					switch(auditType)
			 {
					case "Modified Bill":
			   {	
				   if(strReportType=="Summary")
					{
					 
				$.each(response.List,function(i,item){
		            	
						funFillTableWith10Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9]);
	            	});
			   }	
				   else
				   {	
						$.each(response.List,function(i,item){
				            	
								funFillTableWith10Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9]);
			            	});
							
					}
					funFillTotalData(response.totalList);
			   }
			   break;
					case "Voided Bill":
			   {	
				   if(strReportType=="Summary")
					{
					 
				$.each(response.List,function(i,item){
		            	
						funFillTableWith8Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7]);
	            	});
			   }
				   else
				   {	
						$.each(response.List,function(i,item){
				            	
								funFillTableWith10Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9]);
			            	});
							
					   }
					funFillTotalData(response.totalList);
			   }
			   break;
					case "Voided Advanced Order":
			   {
				   if(strReportType=="Summary")
					{
					 
				$.each(response.List,function(i,item){
		            	
						funFillTableWith8Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7]);
	            	});
					}
				   else
				   {	
						$.each(response.List,function(i,item){
				            	
								funFillTableWith10Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9]);
			            	});
							
					   }
					funFillTotalData(response.totalList);
			   }
			   break;
					case "Line Void":
					   {
							 
						$.each(response.List,function(i,item){
				            	
								funFillTableWith8Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7]);
			            	});
							
						funFillTotalData(response.totalList);   
					   }
					   break;
					case "Voided KOT":
					   {
						   if(strReportType=="Summary")
							{
							 
						$.each(response.List,function(i,item){
				            	
								funFillTableWith9Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8]);
			            	});
							}
						   else
						   {	
								$.each(response.List,function(i,item){
						            	
									funFillTableWith11Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9],item[10]);
					            	});
									
							   }
							funFillTotalData(response.totalList);
					   }
					   break;
					case "Time Audit":
					   {
							 
						$.each(response.List,function(i,item){
				            	
								funFillTableWith8Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7]);
			            	});
							
						   
					   }
					   break;
					case "KOT Analysis":
					   {
							 
						$.each(response.List,function(i,item){
				            	
								funFillTableWith7Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6]);
			            	});
							
						funFillTotalData(response.totalList); 
					   }
					   break;
					case "Moved KOT":
					   {
						   if(strReportType=="Summary")
							{
							 
						$.each(response.List,function(i,item){
				            	
								funFillTableWith9Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8]);
			            	});
							}
						   else
						   {	
								$.each(response.List,function(i,item){
						            	
									funFillTableWith11Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9],item[10]);
					            	});
									
							   }
							funFillTotalData(response.totalList);
					   }
		
			 }	
				
				}//Else block Of Response
				
				
			
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
	
	function funFillTotalData(rowData) 
	{
		var table = document.getElementById("tblTotal");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    for(var i=0;i<rowData.length;i++)
	    	 {
	   		
	 	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList["+(rowCount)+"].strCol"+(i)+"\" id=\"rowList["+(rowCount)+"].strCol"+(i)+"\" value='"+rowData[i]+"' />";
	   		
	   		 }
		
	  
	}
	
 	function funAddHeaderRow(rowData){
		var table = document.getElementById("tblAuditFlash");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
		    for(var i=0;i<rowData.length;i++){
		    	if(i==0){
	    			 row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"colRow["+(rowCount)+"].strCol"+(i)+"\" value='"+rowData[i]+"' />";
	    		 } else {
		    			row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"colRow["+(rowCount)+"].strCol"+(i)+"\" value='"+rowData[i]+"' />";
		    			
		    		}
				}
		} 
	
 	function funFillTableWith11Col(strBillNo,strBillDate,strModifiedDate,strEntryTime,strModifyTime,dblBillAmt,dblNetAmt,strUserCreated,strUserEdited,strReasonName,strItemName)
	{
		var table = document.getElementById("tblAuditFlash");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

	      row.insertCell(0).innerHTML= "<input   class=\"Box \" style=\"text-align:right;\" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+strBillNo+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+strBillDate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+strModifiedDate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+strEntryTime+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(4).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+strModifyTime+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(5).innerHTML= "<input   class=\"Box \" style=\"text-align:left;\" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+dblBillAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(6).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align:left;\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+dblNetAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(7).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align:right;\" size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+strUserCreated+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(8).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align:right;\" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+strUserEdited+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(9).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align:right;\" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+strReasonName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(10).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align:right;\" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+strItemName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	}
		
 	function funFillTableWith10Col(strBillNo,strBillDate,strModifiedDate,strEntryTime,strModifyTime,dblBillAmt,dblNetAmt,strUserCreated,strUserEdited,strReasonName)
	{
		var table = document.getElementById("tblAuditFlash");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

		 row.insertCell(0).innerHTML= "<input  class=\"Box \" style=\"text-align:right;\" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+strBillNo+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+strBillDate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+strModifiedDate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+strEntryTime+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(4).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+strModifyTime+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	     
	      row.insertCell(5).innerHTML= "<input class=\"Box \" style=\"text-align:left;\" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+dblBillAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(6).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align:left;\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+dblNetAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(7).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align:right;\" size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+strUserCreated+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(8).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align:right;\" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+strUserEdited+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(9).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align:right;\" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+strReasonName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      
	}
 	
 	function funFillTableWith9Col(strBillNo,strBillDate,strModifiedDate,strEntryTime,strModifyTime,dblBillAmt,dblNetAmt,strUserCreated,strUserEdited)
	{
		var table = document.getElementById("tblAuditFlash");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

		 row.insertCell(0).innerHTML= "<input   class=\"Box \" style=\"text-align:right;\" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+strBillNo+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+strBillDate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+strModifiedDate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+strEntryTime+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(4).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+strModifyTime+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	     row.insertCell(5).innerHTML= "<input   class=\"Box \" style=\"text-align:left;\" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+dblBillAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(6).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align:left;\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+dblNetAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(7).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align:right;\" size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+strUserCreated+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(8).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align:right;\" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+strUserEdited+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    
	      
	}
	
 	function funFillTableWith8Col(strBillNo,strBillDate,strModifiedDate,strEntryTime,strModifyTime,dblBillAmt,dblNetAmt,strUserCreated)
	{
		var table = document.getElementById("tblAuditFlash");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

		 row.insertCell(0).innerHTML= "<input   class=\"Box \" style=\"text-align:right;\" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+strBillNo+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+strBillDate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+strModifiedDate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+strEntryTime+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(4).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+strModifyTime+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	     row.insertCell(5).innerHTML= "<input   class=\"Box \" style=\"text-align:left;\" size=\"10%\"  value='"+dblBillAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(6).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align:left;\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+dblNetAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(7).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align:right;\" size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+strUserCreated+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      
	}
 	function funFillTableWith7Col(strBillNo,strBillDate,strModifiedDate,strEntryTime,strModifyTime,dblBillAmt,dblNetAmt)
	{
		var table = document.getElementById("tblAuditFlash");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

		 row.insertCell(0).innerHTML= "<input   class=\"Box \" style=\"text-align:right;\" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+strBillNo+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+strBillDate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+strModifiedDate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+strEntryTime+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(4).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+strModifyTime+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(5).innerHTML= "<input   class=\"Box \" style=\"text-align:left;\" size=\"10%\"  value='"+dblBillAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(6).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align:left;\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+dblNetAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	     
	      
	}
	
</script>


</head>

<body>
	<div id="formHeading">
		<label>POS Audit Flash</label>
	</div>
	<br />
	<br />
	<s:form name="POSAuditFlash" method="POST"
		action="rptPOSAuditFlash.html?saddr=${urlHits}"
		target="_blank">
		<div>
			<div>

				<table class="masterTable" style="margin:auto;">

					<tr>
						<td width="140px">POS Name</td>
						<td><s:select id="cmbPOSName" name="cmbPOSName"
								path="strPOSName" cssClass="BoxW124px" items="${posList}">

							</s:select></td>
							<td><label>User</label></td>
							<td >
						<s:select id="cmbUserName" name="cmbUserName" path="strSGName" cssClass="BoxW124px" items="${userList}">
					</s:select>
					</td>
 						
						<td><label>From Date</label></td>
						<td><s:input id="txtFromDate" required="required"
								path="fromDate" pattern="\d{1,2}-\d{1,2}-\d{4}"
								cssClass="calenderTextBox" /></td>

						<td><label>To Date</label></td>
						<td><s:input id="txtToDate" required="required" path="toDate"
								pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox" /></td>
 		
 						
					</tr>
					<tr>
						<td><label>Type</label></td>
					<td >
						<s:select id="cmbReportType" path="strReportType" cssClass="BoxW124px">
				    		<s:option value="Summary">Summary</s:option>
				    		<s:option value="Details">Details</s:option>
				    		
				    	</s:select>
					</td>
						<td><label>Reason</label></td>
					<td colspan="3">
						<s:select id="cmbReason" name="cmbReason" path="strReasonMaster" cssClass="BoxW124px" items="${ReasonMasterList}">
						</s:select>
					</td>
						<td></td>	
						<td><input type="submit" value="Export"
				class="form_button" id="submit" /></td>
						
					</tr>

				</table>
			</div>
			
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 500px; margin:auto; overflow-x: scroll; overflow-y: scroll; width: 80%;">
				
				<table id="tblAuditFlash" class="transTablex"
					style="width: 100%; text-align: center !important;">
				</table>
				
			</div>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 50px; margin:auto; overflow-x: scroll; overflow-y: scroll; width: 80%;">
				
	
				<table id="tblTotal" class="transTablex"
					style="width: 100%; text-align: center !important;">
				</table>
			</div>
		</div>
		<br />
		<br />
		<p align="center">
			<input type="button" value="Modified Bill" class="form_button"
				id="btnSubmit1" />  
				<input type="button" value="Voided Bill" class="form_button"
				id="btnSubmit2" />  
				<input type="button" value="Voided Advanced Order" class="form_button"
				id="btnSubmit3" />  
				<input type="button" value="Line Void" class="form_button"
				id="btnSubmit4" />  
				<input type="button" value="Voided KOT" class="form_button"
				id="btnSubmit5" />  
				<input type="button" value="Time Audit" class="form_button"
				id="btnSubmit6" />  
				<input type="button" value="KOT Analysis" class="form_button"
				id="btnSubmit7" />  
				<input type="button" value="Moved KOT" class="form_button"
				id="btnSubmit8" />  
		</p>
		<s:input type="hidden" id="txtAuditType" name="txtAuditType" path="strPSPCode" />
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 50%; left: 50%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>

	</s:form>

</body>
</html>