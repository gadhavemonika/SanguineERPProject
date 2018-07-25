<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Debit Card Flash Report</title>
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
	padding-left: 0;
	width: 60px
}

	background: inherit;
	border: 0 solid #060006;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: bold;
	outline: 0 none;
	padding-left: 0;
	width: 60px
}
</style>
<script type="text/javascript">
/*On form Load It Reset form :Ritesh 22 Nov 2014*/
$(function() 
   			{		
   				$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
   				$("#txtFromDate" ).datepicker('setDate', 'today');
   				$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
   				$("#txtToDate" ).datepicker('setDate', 'today');
   				
   			}); 
   			
function funHelp(transactionName)
{	       
   // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
   window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
}

function funSetData(code)
{
	$("#txtCustomerCode").val(code);
	var searchurl=getContextPath()+"/loadPOSCustomerMasterData.html?POSCustomerCode="+code;
	 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strCustomerTypeMasterCode=='Invalid Code')
		        	{
		        		alert("Invalid Customer  Code");
		        		$("#txtCustomerCode").val('');
		        	}
		        	else
		        	{
			           	$("#txtCustomerName").val(response.strCustomerName);
			        	$("#txtCustomerName").focus();
			        	
			        	
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

function funSetDate()
{
	
	var searchurl=getContextPath()+"/getPOSDate.html";
	 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	/* var dateTime=response.POSDate;
		        	var date=dateTime.split(" ");
		        	$("#txtFromDate").val(date[0]);
		        	$("#txtToDate").val(date[0]); */
		        	
		        var date = new Date(response.POSDate);
		        var	dateTime=date.getDate()  + '-' + (date.getMonth() + 1)+ '-' +  date.getFullYear();
		        var posDate=dateTime.split(" ");
		        $("#txtFromDate").val(posDate[0]);
	        	$("#txtToDate").val(posDate[0]);
	        	
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
	 /**
	 * Success Message After Saving Record
	 **/
	 $(document).ready(function()
	 {
	 	var message='';
	 	<%if (session.getAttribute("success") != null) 
	 	{
	 		if(session.getAttribute("successMessage") != null)
	 		{%>
	 			message='<%=session.getAttribute("successMessage").toString()%>';
	 		    <%
	 		    session.removeAttribute("successMessage");
	 		}
	 		boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
	 		session.removeAttribute("success");
	 		if (test) 
	 		{
	 			%>alert("Data Saved \n\n"+message);<%
	 		}
	 	}%>
	 	
	 	$(document).ajaxStart(function() {
	 		$("#wait").css("display", "block");
	 	});
	 	$(document).ajaxComplete(function() {
	 		$("#wait").css("display", "none");
	 	});

	 	$("#txtFromDate").datepicker({
	 		dateFormat : 'dd-mm-yy'
	 	});
	 	$("#txtFromDate").datepicker('setDate', 'today');

	 	$("#txtToDate").datepicker({
	 		dateFormat : 'dd-mm-yy'
	 	});
	 	$("#txtToDate").datepicker('setDate', 'today');
	 	
	 	$("[type='reset']").click(function(){
	 		location.reload(true);
	 	});
	 	
	 	$("form").submit(function(event){
			  if($("#txtCustomerName").val().trim()=="")
				{
					alert("Please Select Customer Name");
					return false;
				}
			 
			  else{
				 
				  return true;
			  }
			});

	 	$("#img1").click(function(event) {
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
	 				funFetchColNames($(this).attr("alt"));
	 			}
	 		}
	 	});
	 	
	 	$("#img2").click(function(event) {
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
					funFetchColNames($(this).attr("alt"));
				}
			}
		});

	 	$("#img3").click(function(event) {
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
					funFetchColNames($(this).attr("alt"));
				}
			}
		});
	 	
	 	$("#img4").click(function(event) {
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
					funFetchColNames($(this).attr("alt"));
				}
			}
		});
	 	
	 	$("#img5").click(function(event) {
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
					funFetchColNames( $(this).attr("alt"));
				}
			}
		});
	 	
	 	$("#img6").click(function(event) {
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
					funFetchColNames( $(this).attr("alt"));
				}
			}
		});
	 });
	 
	 function funLoadTableData()
	 {
		 funSetDate();
	 	var fromDate = $("#txtFromDate").val();
	 	var toDate = $("#txtToDate").val();
	 	fDate=fromDate;
	 	tDate=toDate;
	 	funDeleteTableAllRows();
	 	funFetchColNames("Consumption Report");
	 	
	 }

	 function funDeleteTableAllRows()
	 {
	 	$('#tblDebitCardFlashReport tbody').empty();
	 	$('#tblDebitCardFlashHeader tbody').empty();
	 	$('#tblTotal tbody').empty();
	 	var table = document.getElementById("tblDebitCardFlashReport");
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
	     	 return false;
	      }
	 }

	 		

	 function funFetchColNames(btnId) {
	 	
	 	var posName=$('#cmbPOSName').val();
	 	var auditType=btnId;
	 	document.getElementById("txtAuditType").value=btnId;
	 	var gurl = getContextPath()+"/loadDebitCardFlash.html";
	 	
	 	
	 	$.ajax({
	 		type : "GET",
	 		data:{ fromDate:fDate,
	 				toDate:tDate,
	 				posName:posName,
	 				auditType:btnId,
	 			},
	 		url : gurl,
	 		dataType : "json",
	 		success : function(response) {
	 		 	if (response== 0) 
	 			{
	 				alert("Data Not Found");
	 			} 
	 			else 
	 			{ 
	 				funFillheaderCol(response.arrListHeader);
	 		 	switch(auditType)
	 				 { 
	 				
	 				 		case "Consumption Report": 
				 				   {
				 					$.each(response.List,function(i,item){
				 	            	funFillTableCol7(item[0],item[1],item[2],item[3],item[4],item[5],item[6]);
				             		});
	 			 					}
	 						break;
	 						
	 						case "Recharge Details":
			 				   {
			 					$.each(response.List,function(i,item){
			 	            	funFillTableCol9(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8]);
			             		});
			 					}
					 	break;
	 						case "Refund Details": 
			 				   {
			 					$.each(response.List,function(i,item){
			 	            	funFillTableCol7(item[0],item[1],item[2],item[3],item[4],item[5],item[6]);
			             		});
			 					}
						break;
						
						break;
	 					 	case "Debit Card Status": 
			 				   {
			 					$.each(response.List,function(i,item){
			 	            	funFillTableCol5(item[0],item[1],item[2],item[3],item[4]);
			             		});
			 					}
						break; 
						break;
	 						case "Unused Card Balance": 
			 				   {
			 					$.each(response.List,function(i,item){
			 	            	funFillTableCol4(item[0],item[1],item[2],item[3]);
			             		});
			 					}
						break;
						
						break;
	 						case "User Wise Recharge Details": 
			 				   {
			 					$.each(response.List,function(i,item){
			 	            	funFillTableCol4(item[0],item[1],item[2],item[3]);
			             		});
			 					}
						break;
	 				} 
	 				
	 				funFillTotalCol(response.totalList);
	         	
	 			}
	 			
	 		}
	 });
	 }

	 	function funFillTableCol7(item0,item1,item2,item3,item4,item5,item6)
		{
			var table = document.getElementById("tblDebitCardFlashReport");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);

		      /*row.insertCell(0).innerHTML= "<input   class=\"Box \" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+strBillNo+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; */
		      row.insertCell(0).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item0+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; 
		      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item1+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtCompStk."+(rowCount)+"\" value='"+item2+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtPhyStk."+(rowCount)+"\" value='"+item3+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      row.insertCell(4).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtVariance."+(rowCount)+"\" value='"+item4+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      /* row.insertCell(5).innerHTML= "<input   class=\"Box \" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+dblBillAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; */
		      row.insertCell(5).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" id=\"txtDate."+(rowCount)+"\" value='"+item5+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      row.insertCell(6).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item6+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		     
		}
	 	
	 	function funFillTableCol4(item0,item1,item2,item3)
		{
			var table = document.getElementById("tblDebitCardFlashReport");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);

		      
		      row.insertCell(0).innerHTML= "<input  readonly=\"readonly\"  class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item0+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; 
		      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\"  class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item1+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\"  class=\"Box \"  id=\"txtCompStk."+(rowCount)+"\" value='"+item2+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\"  class=\"Box \"  id=\"txtPhyStk."+(rowCount)+"\" value='"+item3+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      
		}
	 	
	 	function funFillTableCol5(item0,item1,item2,item3,item4)
		{
			var table = document.getElementById("tblDebitCardFlashReport");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);

		      
		      row.insertCell(0).innerHTML= "<input  readonly=\"readonly\"  class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item0+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; 
		      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\"  class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item1+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\"  class=\"Box \"  id=\"txtCompStk."+(rowCount)+"\" value='"+item2+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\"  class=\"Box \"  id=\"txtPhyStk."+(rowCount)+"\" value='"+item3+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      row.insertCell(4).innerHTML= "<input  readonly=\"readonly\"  class=\"Box \"  id=\"txtPhyStk."+(rowCount)+"\" value='"+item4+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      
		}
	 	
	 	
	 	function funFillTableCol9(item0,item1,item2,item3,item4,item5,item6,item7,item8)
		{
			var table = document.getElementById("tblDebitCardFlashReport");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);

		      /*row.insertCell(0).innerHTML= "<input   class=\"Box \" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+strBillNo+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; */
		      row.insertCell(0).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item0+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; 
		      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item1+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtCompStk."+(rowCount)+"\" value='"+item2+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtPhyStk."+(rowCount)+"\" value='"+item3+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      row.insertCell(4).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtVariance."+(rowCount)+"\" value='"+item4+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      /* row.insertCell(5).innerHTML= "<input   class=\"Box \" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+dblBillAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; */
		      row.insertCell(5).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" id=\"txtDate."+(rowCount)+"\" value='"+item5+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      row.insertCell(6).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item6+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      row.insertCell(7).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item7+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		      row.insertCell(8).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item8+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		}

	 	
	 	function funFillTotalCol(rowData) 
		{
			var table = document.getElementById("tblTotal");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    for(var i=0;i<rowData.length;i++)
		    	 {
		   		
		 	   			row.insertCell(i).innerHTML = "<input  readonly=\"readonly\" class=\"cell\" name=\"rowList["+(rowCount)+"].strCol"+(i)+"\" id=\"rowList["+(rowCount)+"].strCol"+(i)+"\" value='"+rowData[i]+"' />";
		   		
		   		 }
			
		  
		}
	 	function funFillheaderCol(rowData) 
		{
			var table = document.getElementById("tblDebitCardFlashHeader");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    for(var i=0;i<rowData.length;i++)
		    	 {
		   		
		 	   			row.insertCell(i).innerHTML = "<input  readonly=\"readonly\"  style=\"width:100%\"  class=\"cell\" name=\"rowList["+(rowCount)+"].strCol"+(i)+"\" id=\"rowList["+(rowCount)+"].strCol"+(i)+"\" value='"+rowData[i]+"' />";
		   		
		   		 }
			
		  
		}
		
 		
	 	
</script>


</head>

<body  onload="funLoadTableData()">
	<div id="formHeading">
		<label>Debit Card Flash Report</label>
	</div>
	<br />
	<br />
	<s:form name="POSDebitCardFlashReport" method="POST" action="rptPOSDebitCardFlashReport.html?saddr=${urlHits}"
		target="_blank">
		<div>
			<div>

				<table class="masterTable" style="margin-left: 70px;">
                 <tr>
					<td width="140px">POS Name</td>
					<td colspan="4">
					<s:select id="cmbPOSName" name="cmbPOSName" path="strPOSName" cssClass="BoxW124px" items="${posList}" >
					</s:select></td>	
						<td><label>From Date</label></td>
						<td ><s:input id="txtFromDate" required="required"
								path="fromDate" pattern="\d{1,2}-\d{1,2}-\d{4}"
								cssClass="calenderTextBox" /></td>

						<td><label>To Date</label></td>
						<td><s:input id="txtToDate" required="required" path="toDate"
								pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox" /></td>

					<tr >
				<td width="140px">Customer Name   </td>
				<td colspan="4"><s:input id="txtCustomerName" path="strCustomerName"  
				 cssStyle="text-transform: uppercase;" cssClass="BoxW116px" onclick="funHelp('POSCustomerMaster');"/></td>
				 
						
				<td><label>Report Type</label></td>
				<td >
						<s:select id="cmbDocType" path="strDocType" cssClass="BoxW124px">
				    		<s:option value="PDF">PDF</s:option>
				    		<s:option value="XLS">EXCEL</s:option>
				    		
				    	</s:select>
					</td>
					
			
		
				<td><input type="Submit" value="Export"class="form_button"  id="submit"  /> </td>
				<td><input type="reset" value="Reset"class="form_button" id="btnReset" /></td>
			
			</tr>
		</table>
	</div>
			
			
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 500px; margin-left: 70px; overflow-x: scroll; overflow-y: scroll; width: 80%;">
				
				
				<table id="tblDebitCardFlashHeader" class="transTablex"
					style="width: 100%; text-align: center !important; ">
			
			<%-- <col style="width:10%"><!--  COl1   -->
			<col style="width:10%"><!--  COl1   -->
			<col style="width:10%"><!--  COl1   -->
			<col style="width:60%"><!--  COl1   --> --%>
				</table>
				
				
				
				
				<table id="tblDebitCardFlashReport" class="transTablex"
					style="width: 100%; text-align: center !important;">
				</table>
				
				</div>	
			
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 70px; margin-left: 70px; overflow-x: scroll; overflow-y: hidden; width: 80%;">
				
	
				<table id="tblTotal" class="transTablex"
					style="width: 100%; text-align:">
					<col style="width:60%"><!--  COl1   -->
					
			
				</table>
				
			</div>
		
		
	
	<div 
	    style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 110px; margin-left: 70px; overflow-x: hidden; overflow-y: hidden; width: 80%;">
				<table class="masterTable1" style="margin-left: 70px;">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
					<img  src="../${pageContext.request.contextPath}/resources/images/Consumption Report.png" id="img1" alt="Consumption Report">
					&nbsp;
					<img  src="../${pageContext.request.contextPath}/resources/images/Recharge Details.png" id="img2" alt="Recharge Details">
					&nbsp;
					<img  src="../${pageContext.request.contextPath}/resources/images/Refund Details.png"  id="img3" alt="Refund Details">
					&nbsp;
					<img  src="../${pageContext.request.contextPath}/resources/images/Debit Card Status.png" id="img4" alt="Debit Card Status">
					&nbsp;
					<img  src="../${pageContext.request.contextPath}/resources/images/Unused Card Balance.png"  id="img5" alt="Unused Card Balance">
				    &nbsp;
					<img  src="../${pageContext.request.contextPath}/resources/images/User Wise Recharge Details.png"  id="img6" alt="User Wise Recharge Details">
					<s:input type="hidden" id="txtAuditType" name="txtAuditType" path="strPSPCode" />
				
	 </table>
	 </div>										
		<br />
		<br />
			
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 40%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>

	</s:form>

</body>
</html>