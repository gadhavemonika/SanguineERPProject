<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script>
var fDate,tDate;

$(document).ready(function() {
	
	

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
	$('#tblExciseCategoryWiseSale tbody').empty();
	
	var table = document.getElementById("tblExciseCategoryWiseSale");
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

	function funAdd() {
		var searchUrl = "";
		searchUrl = getContextPath() + "/saveCategoryWiseSale.html?fromDate=" + fDate + "&toDate=" + tDate;
	
		$.ajax({
			type : "GET",
			
			url : searchUrl,
			dataType : "json",
			
			success : function(response) {
				if (response.Header == '' || response.Header == undefined ) {
					alert("Data Not Found");
				} else {
				
				funAddHeders(response.Header);
// 				funDate(response.Date);
 				funAddFullRow(response.Data);
				

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
	
	function funAddHeders(headerData){
		var table = document.getElementById("tblExciseCategoryWiseSale");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
		    for(var i=0;i<headerData.length;i++){
		    	
	    			 row.insertCell(i).innerHTML= "<input type=\"Box\" size=\"8%\" readonly=\"readonly\" class=\"Box\" name=\"colRow["+(i)+"].strCol"+(i+1)+"\" value="+headerData[i]+" />";
	    		
				}
		} 
	function funDate(dateData)
	{
		var table = document.getElementById("tblExciseCategoryWiseSale");
	    var rowCount = table.rows.length;
	    var row ;
	    for(var i=0;i<dateData.length;i++){
	    	 row = table.insertRow(rowCount);
			 row.insertCell(0).innerHTML= "<input type=\"Box\"  readonly=\"readonly\" class=\"Box\" name=\"date["+(i+1)+"].dteDate"+(i+1)+"\" value="+dateData[i]+" />";
			 rowCount++;
			
		}
		
	}

	function funAddFullRow(rowData)
	{
		var table = document.getElementById("tblExciseCategoryWiseSale");
	    var rowCount = 1;
	    var row,dtecount=0,dataCount=1 ;
	    for(var i=0;i<(rowData.length/2);i++){
	    	 row = table.insertRow(rowCount);
	    	
    			 var dateData= rowData[dtecount];
    	    	 var data= rowData[dataCount];
        	
	    	 var count=0;
	    	 for(var j=0;j<data.length+1;j++){
	    	 if(j==0){
	    		 row.insertCell(0).innerHTML= "<input type=\"Box\"  readonly=\"readonly\" class=\"Box\" name=\"fullRowData["+(j+1)+"].dteDate"+(j+1)+"\" value="+dateData+" />";
	    		 }
	    		 else{
			 row.insertCell(j).innerHTML= "<input type=\"Box\"  readonly=\"readonly\" class=\"Box\" name=\"fullRowData["+(count)+"].strSale"+(count)+"\" value="+data[count]+" />";
	    	 count++;
	    	 }}
	    	 dtecount=dtecount+2;
	    	 dataCount=dataCount+2;
	    	 rowCount++;
		}
		
	}
</script>
</head>
<body>
	<div id="formHeading">
		<label>Category Wise Sale</label>
	</div>

	<br />
	<br />

	<s:form name="frmExciseCategoryWiseSale" method="POST" action="frmExciseCategoryWiseSale.jsp" >
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
			<table id="tblExciseCategoryWiseSale" class="transTablex"
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