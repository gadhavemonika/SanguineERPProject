<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head><script>
var transType,fDate,tDate,data,transCode;
$(document).ready(function() {

	$("#txtFromDate").datepicker({
		dateFormat : 'yy-mm-dd'
	});
	$("#txtFromDate").datepicker('setDate', 'today');

	$("#txtToDate").datepicker({
		dateFormat : 'yy-mm-dd'
	});
	$("#txtToDate").datepicker('setDate', 'today');

	$("#btnSubmit").click(function(event) {
		var fromDate = $("#txtFromDate").val();
		var toDate = $("#txtToDate").val();
		inPeg=$('#txtInPeg').val();
		if (fromDate.trim() == '' && fromDate.trim().length == 0) {
			alert("Please Enter From Date");
			return false;
		}
		if (toDate.trim() == '' && toDate.trim().length == 0) {
			alert("Please Enter To Date");
			return false;
		}


		if (funDeleteTableAllRows()) {
				if (CalculateDateDiff(fromDate, toDate)) {
				fDate = fromDate;
				tDate = toDate;
				var selectValue=$("#cmbTransType").val();
				funAdd(selectValue);
			}
		}
	});

});

		
			function funDeleteTableAllRows()
			{
				$('#tblAuditFlash tbody').empty();
				
				var table = document.getElementById("tblAuditFlash");
				var rowCount1 = table.rows.length;
				if(rowCount1==0){
					return true;
				}else{
					return false;
				}
			}
			function CalculateDateDiff(fromDate, toDate) {
			
				var frmDate = fromDate.split('-');
				var fDate = new Date(frmDate[0], frmDate[1], frmDate[2]);
			
				var tDate = toDate.split('-');
				var t1Date = new Date(tDate[0], tDate[1], tDate[2]);
			
				var dateDiff = t1Date - fDate;
				if (dateDiff >= 0) {
					return true;
				} else {
					alert("Please Check From Date And To Date");
					return false
				}
			}
			
			function funAdd(transType) {
				var searchUrl = "";
				
				searchUrl = getContextPath() + "/loadExciseAuditData.html?fromDate=" + fDate + "&toDate=" + tDate+"&transType="+ transType ;
			
				$.ajax({
					type : "GET",
					
					url : searchUrl,
					
					dataType : "json",
					 
					success : function(response) {
						if (response == '' || response== undefined ) {
							alert("Data Not Found");
						} else {
						funAddHeader();
						funAddFullRow(response);
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
      function funAddHeader(){
    	  
    	  $('#tblAuditFlash tbody').empty();
    		var $row = $('<tr id="headerRow">' +
    				'<th ><h4>Code</h4></th>' +'<th ><h4>BillDate</h4></th>' +'<th><h4>UserModified</h4></th>'
    				+ '<th><h4>Date_Modified</h4></th>' 
    				+ '</tr>');

    		$('#tblAuditFlash').prepend($row);
      }
      
  	function funAddFullRow(data) 
  	{
  		if (data != null) 
  		{
  			var table = document.getElementById("tblAuditFlash");
  			var rowCount = table.rows.length;
  			var row, rowData;
  			
  			for (var j = 0; j < data.length; j++) 
  			{
  				row = table.insertRow(rowCount);
  				rowData = data[j]
  				for (var i = 0; i < rowData.length; i++) 
  				{
  					var tpCode= rowData[i];  		
      				if (i == 0) 
      				{
    	  
    					row.insertCell(i).innerHTML='<a  id="strOpeningStk.'+i+'" href="#" onclick="funCallAuditDtl(this);" > '+rowData[i]+' </a> ';
    					//row.insertCell(i).innerHTML='<a  id=\"strOpeningStk.'+(rowCount)+'" href="#" onclick="funCallAuditDtl(this);" name=\"objExciseAuditFlash['+rowCount+'].strOpeningStk\"  value='+rowData[i]+' > '+rowData[i]+' </a> ';
    					//  row.insertCell(i).innerHTML= "<input class=\"Box\"  name=\"objExciseAuditFlash["+rowCount+"].strOpeningStk\"  id=\"strOpeningStk."+(rowCount)+"\"  value="+rowData[i]+" onblur=\"funCallAuditDtl(this);\"> ";
      				}      
                 	else if (i == 1) {
  						row.insertCell(i).innerHTML= "<input class=\"Box\"  name=\"objExciseAuditFlash["+(rowCount)+"].dteDate\" value="+rowData[i]+">";
  						
  					} else if (i == 2) {
  						row.insertCell(i).innerHTML =  "<input class=\"Box\"  name=\"objExciseAuditFlash["+(rowCount)+"].strSale\" value="+rowData[i]+">";
  						
  					} else {
  						row.insertCell(i).innerHTML ="<input class=\"Box\"  name=\"objExciseAuditFlash["+(rowCount)+"].strPurchase\" value="+rowData[i]+">";
  					}
  				}
  				rowCount++;  				
  			}
  		}
  	}  	
  	
  	function funCallAuditDtl(object){
//   		;
		var transCode=document.getElementById(""+object.id+"").innerHTML;
  		var index=object.parentNode.parentNode.rowIndex;
		var searchUrl = "";		
		searchUrl = getContextPath()+"/funExciseAuditdtl.html?transCode="+transCode ;
		
		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				if (response == '' || response== undefined ) {
					alert("Data Not Found");
				} else {
					funAddHeaderDtl();
				funAddFullRowDtl(response);
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
  	
  	
    function funAddHeaderDtl(){
  	  
  	  $('#tblAuditFlash tbody').empty();
  		var $row = $('<tr id="headerRow">' +
  				'<th ><h4>Trans_Code</h4></th>' +'<th ><h4>BillNo</h4></th>' +'<th><h4>BrandCode</h4></th>'
  				+ '<th><h4>Qty In ML</h4></th>' 
  				+ '</tr>');

  		$('#tblAuditFlash').prepend($row);
    }

	function funAddFullRowDtl(auditDtlData) 
  	{
  		if (auditDtlData != null) 
  		{
  			var table = document.getElementById("tblAuditFlash");
  			var rowCount = table.rows.length;
  			var row, rowData;
  			
  			for (var j = 0; j < auditDtlData.length; j++) 
  			{
  				row = table.insertRow(rowCount);
  				rowData = auditDtlData[j]
  				for (var i = 0; i < rowData.length; i++) 
  				{
  					var tpCode= rowData[i];  		
      				if (i == 0) 
      				{
    	  
    					row.insertCell(i).innerHTML="<input class=\"Box\"  align=center name=\"objExciseAuditFlash["+(rowCount)+"].strOpeningStk\" value="+rowData[i]+">";
      				}      
                 	else if (i == 1) {
  						row.insertCell(i).innerHTML= "<input class=\"Box\"  name=\"objExciseAuditFlash["+(rowCount)+"].dteDate\" value="+rowData[i]+">";
  						
  					} else if (i == 2) {
  						row.insertCell(i).innerHTML =  "<input class=\"Box\"  name=\"objExciseAuditFlash["+(rowCount)+"].strSale\" value="+rowData[i]+">";
  						
  					} else {
  						row.insertCell(i).innerHTML ="<input class=\"Box\"  name=\"objExciseAuditFlash["+(rowCount)+"].strPurchase\" value="+rowData[i]+">";
  					}
  				}
  				rowCount++;  				
  			}
  		}
  	}
</script>
<body>
<div id="formHeading">
		<label>Excise Audit Flash</label>
	</div>
	<s:form action="#" method="GET" name="frmExciseAuditFlash">
		<br>
	
			<table class="transTable">
			
				<tr>
				    <td><label id="lblFromDate">From Date</label></td>
			        <td>
			            <s:input id="txtFromDate" name="fromDate" path="dtFromDate" cssClass="calenderTextBox"/>
			        </td>
				        
			        <td><label id="lblToDate">To Date</label></td>
			        <td>
			            <s:input id="txtToDate" name="toDate" path="dtToDate" cssClass="calenderTextBox"/>
			        </td>
			      
				</tr>
					<tr>
					<td width="8%"><label>Report Type</label></td>
					<td width="10%">
						<s:select id="cmbReportType" path="strReportType" cssClass="longTextBox">
						  <option value="Edited">Edited</option>
						  <option value="Deleted">Deleted</option>
					</s:select>
			    		
					</td>
					<td width="8%"><label>Transaction Type</label></td>
					<td width="10%" >
						<s:select id="cmbTransType" path="strTransType" cssClass="longTextBox">
						 <option value="frmExciseTransportPass">Excise Tp</option>
						  <option value="frmExciseManualSale">Excise Sale</option>
						  <option value="frmExciseBillGenerate">Bill Generation</option>
						</s:select>
			    		
					</td></tr>
					</table>
					<br>
					<br>
				
	<div
			style="width: 100%; overflow-x: auto !important; overflow-y: auto !important;">
			<table id="tblAuditFlash" class="transTablex"
				style="width: 100%; text-align: center !important;">
			</table>
		</div>
		<p align="center">
			<input type="button" value="Submit" class="form_button" id="btnSubmit" />                       
			<input type="reset" value="Reset" class="form_button" />
		</p>
	
	</s:form>
</body>
</html>