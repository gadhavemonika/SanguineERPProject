<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script>$(document).ready(function() {
	
	var subcategory,brandlist,closingstck;
	$("#txtFromDate").datepicker({
		dateFormat : 'yy-mm-dd'
	});
	$("#txtFromDate").datepicker('setDate', 'today');

	$("#txtToDate").datepicker({
		dateFormat : 'yy-mm-dd'
	});
	$("#txtToDate").datepicker('setDate', 'today');

	
	$("[type='reset']").click(function(){
		location.reload(true);
	});
	

	$("#btnSubmit").click(function(event) {
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
				funAdd();
			}
		}
	});

});

		function funDeleteTableAllRows()
		{
			$('#tblBrandWiseClosingReport tbody').empty();
			
			var table = document.getElementById("tblBrandWiseClosingReport");
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




	function funAdd() {
		var searchUrl = "";
		searchUrl = getContextPath() + "/loadBrandWiseClosingReport.html?fromDate=" + fDate + "&toDate=" + tDate;
	   
		$.ajax({
			type : "GET",
			
			url : searchUrl,
			dataType : "json",
			
			success : function(response) {
				if (response.subCategoryName == '' || response.subCategoryName == undefined ) {
					alert("Data Not Found");
				} else {
				funAddHeader();
				funAddFullRow(response.subCategoryName,response.brandNameList,response.closingstckList);
				}},
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
	
	function funAddHeader() {
		var $row = $('<tr id="headerRow">' +
				 '<th><h4>BrandName</h4></th>' + '<th><h4>CLOSING STOCK</h4></th>'
				+ '</tr>');

		$('#tblBrandWiseClosingReport').prepend($row);
	}
	
	function funAddFullRow(subcategory,brandlist,closingstck)
	{
		
		var table = document.getElementById("tblBrandWiseClosingReport");
		var rowCount = table.rows.length;
		var row, rowData;
		 
		for (var j = 0; j < subcategory.length; j++) {
			rowCount = table.rows.length;
			row = table.insertRow(rowCount);
			row.insertCell(0).innerHTML= "<input class=\"Box\"  name=\"objBrandWiseInquiry["+(rowCount)+"].strOpeningStk(j)\" value='"+subcategory[j]+"'>";
			row.insertCell(1);
			var brandData=brandlist[j];
			var closingData=closingstck[j];
			rowCount++;
       for (var i = 0; i < brandData.length; i++) {
    	   row = table.insertRow(rowCount);
    	   row.insertCell(0).innerHTML ="<input class=\"Box\"  name=\"objBrandWiseInquiry["+(rowCount)+"].strPurchase(i)\" value='"+brandData[i]+"'>";
    	   row.insertCell(1).innerHTML = "<input class=\"Box\"  name=\"objBrandWiseInquiry["+(rowCount)+"].strClosingStk(i)\" value='"+closingData[i]+"'>";
 			rowCount++;
			}
   	   
		}
		
	}
</script>
<body>
	<div id="formHeading">
		<label>Brand Wise Closing Report</label>
	</div>

	<br />
	<br />

	<s:form name="frmBrandWiseClosingReport" method="POST" action="frmBrandWiseClosingReport.jsp" >
		<div>
			<table class="masterTable">
				<tr>
					<td><label>From Date</label></td>
					<td><s:input type="text" id="txtFromDate"
							class="calenderTextBox" path="dteFromDate" required="required" ></s:input>
					</td>
				
					<td><label>To Date</label></td>
					<td><s:input type="text" id="txtToDate"
							class="calenderTextBox" path="dteToDate" required="required" ></s:input></td>
				</tr>
			
			</table>
		   <br/>
		   <br/>
	
		<div
			style="width: 100%; overflow-x: auto !important; overflow-y: auto !important;">
			<table id="tblBrandWiseClosingReport" class="transTablex"
				style="width: 100%; text-align: center !important;">
			</table>
		</div>
			<p align="center">
			<input type="button" value="Display" class="form_button" id="btnSubmit"/>
			<input type="submit" value="Print" class="form_button" id="submit" hidden="hidden"/>
			<input type="reset" value="Reset" class="form_button" id="btnReset"/>
			
		</p>
		</div>
	</s:form>

</body>
</html>