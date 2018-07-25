<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	var fieldName,flgSACode,listRow=0;

	$(function() 
	{
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
		
		$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$( "#txtFromDate" ).datepicker('setDate','today');
		
		$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
		$( "#txtToDate" ).datepicker('setDate','today');
		
		$("[type='reset']").click(function(){
			location.reload(true);
		});
		
		$('#txtLicenceCode').blur(function (){
				 var code=$('#txtLicenceCode').val();
				 if (code.trim().length > 0 && code !="?" && code !="/")
				 {							   
					 funSetLicenceData(code);
	   			}
		});
		
		$("#btnSubmit").click(function(event)
		{
			if(funDeleteTableAllRows()){
				var fromDate=$("#txtFromDate").val();
				var toDate=$("#txtToDate").val();
				var licenceCode=$("#txtLicenceCode").val();
				if(licenceCode.trim()=='' && licenceCode.trim().length==0)
				{
					alert("Please Enter Licence Code");
					return false;
				}else if(fromDate.trim()=='' && fromDate.trim().length==0)
				{
					alert("Please Enter From Date");
					return false;
				}else if(toDate.trim()=='' && toDate.trim().length==0)
				{
					alert("Please Enter To Date");
					return false;
				}else{
					funFillPOSLinkUpTable(licenceCode,fromDate,toDate);
				}
			}
			
		});
		
		
		$("form").submit(function(event){			
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var licenceCode=$("#txtLicenceCode").val();
			
			if(licenceCode=='')
			{
				alert("Please Enter Licence Code");
				return false;
			}
				
			if(fromDate=='')
			{
				alert("Please Enter From Date");
				return false;
			}
			if(toDate=='')
			{
				alert("Please Enter To Date");
				return false;
			}
			
			return true;
		});		
	});
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
	}
	
	function funSetData(code)
	{	
		switch (fieldName) 
		{			   
		   case 'LicenceCode':
			   	funSetLicenceData(code);
	        	break;
		}
	}
	
	function funSetLicenceData(code)
	{
		$("#txtFromDate").focus();
		var searchUrl="";
		searchUrl=getContextPath()+"/loadExciseLicenceMasterData.html?licenceCode="+code;
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	if(response.strLicenceNo=='Invalid Code')
		        	{
		        		alert("Invalid Licence Code Please Select Again");
		        		$("#txtLicenceCode").focus();
		        	}
		        	else
		        	{
		        		$("#txtLicenceCode").val(response.strLicenceCode);
		        		$("#txtLicenceNo").text(response.strLicenceNo);
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
	
	function funFillPOSLinkUpTable(code,fromDate,toDate)
	{
		flgSACode=false;
		var searchUrl="";
		var param=code+","+fromDate+","+toDate;
		searchUrl=getContextPath()+"/loadExcisePOSLinkUpData.html?param="+param;
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	if(response.length>0){
				    	$.each(response, function(i,item)
						{
				    		funAddRow(response[i][0],response[i][1],response[i][2],response[i][3],response[i][4],response[i][5],response[i][7],response[i][8],response[i][9]);
						});
				    	$("#txtLocCode").val(' ');
			    	}else{
			    		alert("No Records Found");
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
	
	function funAddRow(posItemCode,posItemName,brandCode,brandName,strBillNo,qty,billDate,intId,strPOSCode)
	{
		var table = document.getElementById("tblPOSLinkUp");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    if(strBillNo!=''){
	    	flgSACode=true;
	    }
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" name=\"listExcisePOSSale["+(rowCount)+"].strPOSItemCode\" id=\"txtPOSItemCode."+(rowCount)+"\" value='"+posItemCode+"' />";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" name=\"listExcisePOSSale["+(rowCount)+"].strPOSItemName\" id=\"txtPOSItemName."+(rowCount)+"\" value='"+posItemName+"' />";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" name=\"listExcisePOSSale["+(rowCount)+"].strBrandCode\" id=\"strBrandCode."+(rowCount)+"\" value='"+brandCode+"' />";
	    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" name=\"listExcisePOSSale["+(rowCount)+"].strBrandName\" id=\"strBrandName."+(rowCount)+"\" value='"+brandName+"' />";
	    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" name=\"listExcisePOSSale["+(rowCount)+"].strBillNo\" id=\"strBillNo."+(rowCount)+"\" value='"+strBillNo+"' />";
		row.insertCell(5).innerHTML= "<input readonly=\"readonly\" type=\"text\" class=\"Box\" name=\"listExcisePOSSale["+(rowCount)+"].intQuantity\" id=\"txtQty."+(rowCount)+"\" value='"+qty+"' />";
	    row.insertCell(6).innerHTML= '<input type="button" value = "Delete"  class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
	    row.insertCell(7).innerHTML= "<input readonly=\"readonly\" type=\"hidden\" class=\"Box\" name=\"listExcisePOSSale["+(rowCount)+"].dteBillDate\" id=\"txtBillDate."+(rowCount)+"\" value='"+billDate+"' />";
	    row.insertCell(8).innerHTML= "<input readonly=\"readonly\" type=\"hidden\" class=\"Box\" name=\"listExcisePOSSale["+(rowCount)+"].intId\" id=\"txtIntId."+(rowCount)+"\" value='"+intId+"' />";
	    row.insertCell(9).innerHTML= "<input readonly=\"readonly\" type=\"hidden\" class=\"Box\" name=\"listExcisePOSSale["+(rowCount)+"].strPOSCode\" id=\"strPOSCode."+(rowCount)+"\" value='"+strPOSCode+"' />";

	}
	
	
	function funDeleteRow(obj) 
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblPOSLinkUp");
	    table.deleteRow(index);
	}
	
	
	function funDeleteTableAllRows()
	{
		var table = document.getElementById("tblPOSLinkUp");
		var rowCount = table.rows.length;
		for(var i=1;i<rowCount;i++)
		{
			table.deleteRow(i);
		}
		var rowCount1 = table.rows.length;
		if(rowCount1<=1){
			return true;
		}else{
			return false;
		}
	}
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>Excise POS Sales Details</label>
	</div>

<br/>
<br/>

	<s:form name="POSSalesDtl" method="POST" action="saveExcisePOSSale.html?saddr=${urlHits}">
	<div class="masterTable">
		<table class="masterTable" style="width:100%;">
			
			<tr>
				<td><label>Licence No</label></td>
			    <td><s:input type="text" id="txtLicenceCode" name="licenceCode" path="strLicenceCode" class="searchTextBox" ondblclick="funHelp('LicenceCode')" required="true" value="${LicenceCode}" /> </td>
			    <td colspan="2">
			    <s:label id="txtLicenceNo" path="strLicenceNo" >${LicenceNo}</s:label></td>
				<td></td>
			</tr>
			
			<tr>
				<td>
					<label>From Date</label>
				</td>
				<td>
					<s:input type="text" id="txtFromDate" class="calenderTextBox" path="strFromDate" />
				</td>
				
				<td>
					<label>To Date</label>
				</td>
				<td>
					<s:input type="text" id="txtToDate" class="calenderTextBox" path="strToDate"/>
				</td>
				<td></td>
			</tr>
		</table>	

		<table id="tblPOSLinkUp" class="transTablex" style="width:100%;">
		
		<tr style="border:solid thin !important; font-size: 13px !important;">
				<th>
					<label>POS Item Code</label>
				</th>
				
				<th>
					<label>POS Item Name</label>
				</th>
				
				<th>
					<label>Brand Code</label>
				</th>
				
				<th>
					<label>Brand Name</label>
				</th>
				
				<th>
					<label>Bill No</label>
				</th>
				<th>
					<label>Sale Quantity</label>
				</th>
				<th>
					<label>Delete</label>
				</th>
				<th></th>
				<th></th>
				<th></th>
			</tr>
		
		</table>
		<br />
		<br />
		<p align="center">
			<input type="button" value="Submit" class="form_button" id="btnSubmit"/>
			<input type="submit" value="Generate Bill" tabindex="3" class="form_button" id="btnGenerateBill" /> 
			<input type="reset" value="Reset" class="form_button"/>
		</p>
	</div>
	</s:form>
</body>
</html>
