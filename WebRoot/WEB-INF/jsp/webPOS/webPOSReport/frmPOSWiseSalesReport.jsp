<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>POS Wise Report</title>
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

.header {
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


	
$(function() 
		{		
			$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtFromDate" ).datepicker('setDate', 'today');
			$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtToDate" ).datepicker('setDate', 'today');
			
		}); 
		
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

	$("#execute").click(function(event) {
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
				funFetchColNames();
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
	funFetchColNames();
	
}

function funDeleteTableAllRows()
{
	$('#tblHeader tbody').empty();
	$('#tblData tbody').empty();
	$('#tblTotal tbody').empty();
	var table = document.getElementById("tblData");
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

		

function funFetchColNames() {
	
	var posName=$('#cmbPOSName').val();
	var strViewTypedata=$('#cmbViewType').val();
	
	var gurl = getContextPath()+"/loadPOSWiseSalesReport.html";
	
	
	$.ajax({
		type : "GET",
		data:{ fromDate:fDate,
				toDate:tDate,
				strViewTypedata:strViewTypedata,
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
				funFillHeaderCol(response.listcol);
	      
				
			$.each(response.List,function(i,item){
	            	
					funFillTableCol(item[0],item[1],item[2],item[3]);
            	});
				
				funFillTotalCol(response.totalList);
        	
			}
			
		}
	});
	}
	function funFillHeaderCol(rowData) 
	{
		var table = document.getElementById("tblHeader");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    for(var i=0;i<rowData.length;i++)
	    	 {
	   		
	 	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList["+(rowCount)+"].strCol"+(i)+"\" id=\"rowList["+(rowCount)+"].strCol"+(i)+"\" value='"+rowData[i]+"' />";
	   		
	   		 }
		
	  
	}

	function funFillTableCol(item0,item1,item2,item3)
	{
	var table = document.getElementById("tblData");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

      
      row.insertCell(0).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item0+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; 
      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item1+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtCompStk."+(rowCount)+"\" value='"+item2+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtPhyStk."+(rowCount)+"\" value='"+item3+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	}
	
	function funFillTotalCol(rowData) 
	{
		var table = document.getElementById("tblTotal");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    for(var i=0;i<rowData.length;i++)
	    	 {
	   		
	 	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList["+(rowCount)+"].strCol"+(i)+"\" id=\"rowList["+(rowCount)+"].strCol"+(i)+"\" value='"+rowData[i]+"' />";
	   		
	   		 }
		
	  
	}

	
	
</script>


</head>

<body onload="funLoadTableData()">
	<div id="formHeading">
		<label>POS  Wise  Sales Report</label>
	</div>
	<br />
	<br />
	<s:form name="POSWiseSalesReport" method="POST"
		action="rptPOSWiseSalesReport.html?saddr=${urlHits}"
		target="_blank">
		<div>
			<div>

				<table class="masterTable" style="margin-left: 70px;">
<tr>
						<td><label>Report Type</label></td>
						<td><s:select id="cmbViewType" path="strViewType"
								cssClass="BoxW124px">
								<s:option value="ITEM WISE">ITEM WISE</s:option>
								<s:option value="GROUP WISE">GROUP WISE</s:option>
								<s:option value="SUB GROUP WISE">SUB GROUP WISE</s:option>
								<s:option value="MENU HEAD WISE">MENU HEAD WISE</s:option>
					</s:select></td>

						
					
				<td><label>Chart</label></td>
				<td ><input type="checkbox"  id="strChart"   style="width: 3%"></input>
				 
				</td>
				
			</tr>
					<tr>
						
						<td><label>From Date</label></td>
						<td><s:input id="txtFromDate" required="required"
								path="fromDate" pattern="\d{1,2}-\d{1,2}-\d{4}"
								cssClass="calenderTextBox" /></td>

						<td><label>To Date</label></td>
						<td><s:input id="txtToDate" required="required" path="toDate"
								pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox" /></td>

					</tr>
					

				</table>
			</div>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 500px; margin-left: 70px; overflow-x: scroll; overflow-y: scroll; width: 80%;">
				
				
				<table id="tblHeader" class="transTablex"
					style="width: 100%; text-align: center !important; ">
			
				</table>
				
				
				
				
				<table id="tblData" class="transTablex"
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
		
		</div>
		<br />
		<br />
		<p align="center">
			<input type="button" value="Execute" class="form_button"id="execute" /> 
				<input type="submit" value="Export"	class="form_button" id="submit" /> 
				<input type="reset" value="Reset"class="form_button" id="btnReset" />

		</p>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 40%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>

	</s:form>

</body>
</html>