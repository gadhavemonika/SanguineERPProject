<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@	taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	$(function() {
		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");		});

		/*$("#txtFromDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#txtFromDate").datepicker('setDate', 'today');*/
		var startDate="${startDate}";
		var arr = startDate.split("/");
		Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
		$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtFromDate" ).datepicker('setDate', Dat);
		$("#txtToDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#txtToDate").datepicker('setDate', 'today');

	});
	
	

	
	
	function funFillTable(objDebtorDtl)
	{
		 var table=document.getElementById("tblAgeingReport");	    
		    var rowCount=table.rows.length;
		    var row = table.insertRow(rowCount);	
		    
		    
		    
		    row.insertCell(0).innerHTML = "<input readonly=\"readonly\" size=\"90%\" class=\"Box\"  style=\"text-align: left;  height:20px;\"   value='"+objDebtorDtl.strDebtorName+"' />";
		    row.insertCell(1).innerHTML = "<input readonly=\"readonly\" size=\"10%\" class=\"Box\"  style=\"text-align: right;  height:20px;\"   value='"+objDebtorDtl.dblBalAmt+"' />";
		    row.insertCell(2).innerHTML = "<input readonly=\"readonly\" size=\"10%\" class=\"Box\"  style=\"text-align: right;  height:20px;\"   value='"+objDebtorDtl.dblCol0+"' />";
		    row.insertCell(3).innerHTML = "<input readonly=\"readonly\" size=\"10%\" class=\"Box\"  style=\"text-align: right;  height:20px;\"   value='"+objDebtorDtl.dblCol1+"' />";
		    row.insertCell(4).innerHTML = "<input readonly=\"readonly\" size=\"10%\" class=\"Box\"  style=\"text-align: right;  height:20px;\"   value='"+objDebtorDtl.dblCol2+"' />";
		    row.insertCell(5).innerHTML = "<input readonly=\"readonly\" size=\"10%\" class=\"Box\"  style=\"text-align: right;  height:20px;\"   value='"+objDebtorDtl.dblCol3+"' />";
		    row.insertCell(6).innerHTML = "<input readonly=\"readonly\" size=\"10%\" class=\"Box\"  style=\"text-align: right;  height:20px;\"   value='"+objDebtorDtl.dblCol4+"' />";
		    
		   
	}
	
	
	
	
	
	function funGetAgeingData(glCode,glName,fromDate,toDate,currency)
	{
		
		
		
		$("#tblAgeingReport").empty();
		
		
		var table=document.getElementById("tblAgeingReport");	    
	    var rowCount=table.rows.length;
	    var row = table.insertRow(rowCount);	
	    
	    
	    
	    row.insertCell(0).innerHTML = "<input readonly=\"readonly\" size=\"90%\" class=\"Box\"  style=\"text-align: left;  height:20px;\"   value='Debtor Name' />";
	    row.insertCell(1).innerHTML = "<input readonly=\"readonly\" size=\"10%\" class=\"Box\"  style=\"text-align: right;  height:20px;\"   value='Outstanding' />";
	    row.insertCell(2).innerHTML = "<input readonly=\"readonly\" size=\"10%\" class=\"Box\"  style=\"text-align: right;  height:20px;\"   value='0-30' />";
	    row.insertCell(3).innerHTML = "<input readonly=\"readonly\" size=\"10%\" class=\"Box\"  style=\"text-align: right;  height:20px;\"   value='31-60' />";
	    row.insertCell(4).innerHTML = "<input readonly=\"readonly\" size=\"10%\" class=\"Box\"  style=\"text-align: right;  height:20px;\"   value='61-90' />";
	    row.insertCell(5).innerHTML = "<input readonly=\"readonly\" size=\"10%\" class=\"Box\"  style=\"text-align: right;  height:20px;\"   value='91-120' />";
	    row.insertCell(6).innerHTML = "<input readonly=\"readonly\" size=\"10%\" class=\"Box\"  style=\"text-align: right;  height:20px;\"   value='121-150' />";
		
		
		
		
		

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/getAgeingData.html?glCode=" + glCode+"&fromDate="+fromDate+"&toDate="+toDate+"&currency="+currency,
			dataType : "json",
			success : function(response)
			{ 
								
				
				$.each(response, function(i,item)
				{				
					
					
					
				    funFillTable(item);				    				   				   
				});
			},
			error : function(e){
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
				$("#btnExecute").click(function( event )
				{				
					
					var glCode=$("#txtGLCode").val();
					var glName=$("#lblGLCode").text();
					var fromDate=$("#txtFromDate").val();
					var toDate=$("#txtToDate").val();
					var currency=$("#cmbCurrency").val();
					
					funGetAgeingData(glCode,glName,fromDate,toDate,currency);	
				});
			
			});
	
	
	
	
	
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	
	function funSetData(code){

		switch(fieldName){

			case 'debtorAccountCode' : 
				funSetGLCode(code);
				break;
				

		}
	}
	
	function funSetGLCode(code)
	{

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadAccontCodeAndName.html?accountCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strVouchNo!="Invalid")
		    	{
					$("#txtGLCode").val(response.strAccountCode);
					$("#lblGLCode").text(response.strAccountName);
								
		    	}
		    	else
			    {
			    	alert("Invalid Receipt No");
			    	$("#txtVouchNo").val("");
			    	$("#txtVouchNo").focus();
			    	return false;
			    }
			},
			error : function(e){
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
	
	
	
	
	
	
	
	
</script>
<body>
	<div id="formHeading">
		<label>Ageing Report</label>
	</div>

	<br />
	<br />

	<s:form name="AgeingReport" method="GET" action="" >
		<table class="masterTable" style="width: 99%">
		
		
				<tr>	
					<td><label>GL Code</label></td>
					<td><s:input type="text" id="txtGLCode" path="strAccountCode" class="searchTextBox" ondblclick="funHelp('debtorAccountCode')"/></td>
					<td  colspan="4"><label id="lblGLCode"></label></td>
				</tr>
		
				<tr>
					<td><label>From Date</label></td>
					<td><s:input type="text" id="txtFromDate" path="dteFromDate" class="calenderTextBox" required="required" /></td>

					<td><label>To Date</label></td>
					<td><s:input type="text" id="txtToDate" path="dteToDate" class="calenderTextBox" required="required" /></td>
					<td colspan="2"></td>
				</tr>
				
				<tr>
			    	<td><s:input  id="txt1"  path="strCol1_0_30"  cssClass="longTextBox" style="width: 80px;" value="30" /></td>	
			    	<td><s:input  id="txt2"  path="strCol2_31_60"  cssClass="longTextBox" style="width: 80px;" value="60"/></td>
			    	<td><s:input  id="txt3"  path="strCol3_61_90"  cssClass="longTextBox" style="width: 80px;" value="90"/></td>	
				    <td><s:input  id="txt4"  path="strCol4_91_120"  cssClass="longTextBox" style="width: 80px;" value="120"/></td>						    
				    <td><s:input  id="txt5"  path="strCol5_121_150"  cssClass="longTextBox" style="width: 80px;" value="150"/></td>	
				    <td><input  id="btnExecute" type="button" value="Execute" tabindex="3" class="smallButton" /></td>
				 </tr>
				 
<!-- 				 <tr> -->
<!-- 					<td><label>Currency </label></td> -->
<%-- 					<td><s:select id="cmbCurrency" items="${currencyList}" path="strCurrency" cssClass="BoxW124px"> --%>
<%-- 						</s:select></td> --%>
<!-- 					<td colspan="4"></td> -->
<!-- 				</tr> -->
				
			</table>
		
							
					
			<br>
			<br>
			<div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 400px;	margin: auto;overflow-x: hidden; overflow-y: scroll;width: 99%;">
				<!-- Dynamic Table Generation for tab4 (Opening Balance) -->
				<table id="tblAgeingReport" class="transTablex" style="width: 100%">				
					<thead>
						<tr>							
					 		<td>Debtor Name</td>
					 		<td>Outstanding</td>
					        <td>0-30</td>
					        <td>31-60</td>
					        <td>61-90</td>
					        <td>91-120</td>
					        <td>121-150</td>			       
				   		</tr>
				   	</thead>
				   	<tbody>
				   	</tbody>			   						   	    				  
				</table>	
			</div>		
							
				
		<br />
		<br />
		<p align="center">
			<!-- <input id="btnExecute" type="button" value="Submit" tabindex="3" class="form_button1" /> -->
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
	</s:form>


</body>
</html>