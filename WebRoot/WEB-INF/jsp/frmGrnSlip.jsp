<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  	<link rel="stylesheet" type="text/css" href="default.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>GRN Slip</title>
    <script type="text/javascript">
    	
    	var fieldName;
    	
    	//Set date in date picker when form is loading or document is reddy or initialize with default value
    	$(function() 
    			{	
		    		var startDate="${startDate}";
					var arr = startDate.split("/");
					Date1=arr[0]+"-"+arr[1]+"-"+arr[2];
    				$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
    				$("#txtFromDate" ).datepicker('setDate', Date1);
    				$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
    				$("#txtToDate" ).datepicker('setDate', 'today');
    				
    				$('#txtSuppCode').blur(function() {
    					var code = $('#txtSuppCode').val();
    					if(code.trim().length > 0 && code !="?" && code !="/"){
    						funSetSupplier(code);
    						$('#txtSuppCode').val('');
    					}
    				});
    				funRemRows("tblSupp");
    			});
    	
    	//Reset the filed
    	function funResetFields()
    	{
    		$("#txtFromGRNCode").val('');
    		$("#txtToGRNCode").val('');
    	}
    	
    	//Open Help Form
    	function funHelp(transactionName)
		{
			fieldName=transactionName;
			if(transactionName=="Togrncode")
			{
// 				transactionName="grncode";
				transactionName="grncodeforprint";
			}
			// window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1100px;dialogLeft:200px;")
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1100px;dialogLeft:200px;")
	    }
		
    	//Set Data based on help Selection
		function funSetData(code)
		{
			switch(fieldName)
			{
			case 'grncode':
					$("#txtFromGRNCode").val(code);
					break;
					
			case 'Togrncode':
					$("#txtToGRNCode").val(code);
					break;
					
			case 'suppcode':
		    	funSetSupplier(code);
		        break;
		        
			case 'grncodeforprint':
				$("#txtFromGRNCode").val(code);
				break; 

			}
		}
    	
    	   //Get Supplier data
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
							alert('Invalid Code');
							$("#txtSuppCode").val('');
							$("#txtSuppName").text('');
							$("#txtSuppCode").focus();
						} else {
							funfillSupplier(response.strPCode,response.strPName);
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
    	   
    	     //fill supplier Data
			 function funfillSupplier(strSuppCode,strSuppName) 
				{
					var table = document.getElementById("tblSupp");
				    var rowCount = table.rows.length;
				    var row = table.insertRow(rowCount);
				    
				    row.insertCell(0).innerHTML= "<input id=\"cbSuppSel."+(rowCount)+"\" type=\"checkbox\" checked=\"checked\" name=\"suppthemes\" value='"+strSuppCode+"' class=\"suppCheckBoxClass\" />";
				    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"15%\" id=\"strSuppCode."+(rowCount)+"\" value='"+strSuppCode+"' >";
				    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"strSuppName."+(rowCount)+"\" value='"+strSuppName+"' >";
				}
			   
    	     //Remove Table data when pass a table ID as parameter
			 function funRemRows(tablename) 
				{
					var table = document.getElementById(tablename);
					var rowCount = table.rows.length;
					while(rowCount>0)
					{
						table.deleteRow(0);
						rowCount--;
					}
				}
			 
			 		$(document).ready(function () 
						{
							$("#chkSuppALL").click(function ()
							{
							    $(".suppCheckBoxClass").prop('checked', $(this).prop('checked'));
							});
												
						});
			 		
		//Calling Report on Submit Button
		function funCallFormAction(actionName,object) 
		{	
			
			if (actionName == 'submit') 
			{
				var spFromDate=$("#txtFromDate").val().split('-');
				var spToDate=$("#txtToDate").val().split('-');
				var FromDate= new Date(spFromDate[2],spFromDate[1]-1,spFromDate[0]);
				var ToDate = new Date(spToDate[2],spToDate[1]-1,spToDate[0]);
				if(!fun_isDate($("#txtFromDate").val())) 
			    {
					 alert('Invalid From Date');
					 $("#txtFromDate").focus();
					 return false;  
			    }
			    if(!fun_isDate($("#txtToDate").val())) 
			    {
					 alert('Invalid To Date');
					 $("#txtToDate").focus();
					 return false;  
			    }
				if(ToDate<FromDate)
				{
				    	alert("To Date is not < Form Date");
				    	$("#txtToDate").focus();
						return false;		    	
				}
				var strSuppCode="";
				$('input[name="suppthemes"]:checked').each(function() {
					 if(strSuppCode.length>0)
						 {
						 strSuppCode=strSuppCode+","+this.value;
						 }
						 else
						 {
							 strSuppCode=this.value;
						 }
					 
					});
				 $("#hidSuppCode").val(strSuppCode);
				 
				 document.forms[0].action = "rptGrnSlip.html";
			}
			
		}
    
    </script>
  </head>
<body>
<div id="formHeading">
		<label>GRN Slip</label>
	</div>
<s:form name="GRNSlip" method="GET" action="rptGrnSlip.html" target="_blank">

<br />
<br />
<table class="masterTable">
	<tr><th colspan="8"></th></tr>
	<tr>
				<td width="10%"><label id="lblFromDate">From Date</label></td>
				<td width="10%"><s:input id="txtFromDate" name="fromDate"
						path="dtFromDate" cssClass="calenderTextBox" required="true"/> <s:errors
						path="dtFromDate"></s:errors></td>
						<td></td>
				<td width="10%"><label id="lblToDate">To Date</label></td>
				<td colspan="3"><s:input id="txtToDate" name="toDate"
						path="dtToDate" cssClass="calenderTextBox" required="true"/> <s:errors
						path="dtToDate"></s:errors></td>
						
			</tr>
			</table>
			<br>
				<table class="masterTable">
			<tr>
				<td width="49%">Supplier&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 <input id="txtSuppCode"  ondblclick="funHelp('suppcode')" Class="searchTextBox" />
				<label id="txtSuppName"></label>
				
				</td>
			</tr>
			<tr>
			<td style="padding: 0 !important;">
					<div
						style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; overflow-x: hidden; overflow-y: scroll;">

						<table id="" class="masterTable"
							style="width: 100%; border-collapse: separate;">
							<tbody>
								<tr bgcolor="#72BEFC">
									<td width="15%"><input type="checkbox" id="chkSuppALL"
										onclick="funCheckUnchecksupp()" />Select</td>
									<td width="25%">Supplier Code</td>
									<td width="65%">Supplier Name</td>

								</tr>
							</tbody>
						</table>
						<table id="tblSupp" class="masterTable"
							style="width: 100%; border-collapse: separate;">
							<tbody>
								<tr bgcolor="#72BEFC">
									<td width="15%"></td>
									<td width="25%"></td>
									<td width="65%"></td>

								</tr>
							</tbody>
						</table>
					</div>
				</td>
			</tr>
			</table>
			<br>
			<table class="masterTable">
	<tr>
		<td width="10%">GRN Code</td>
		<%-- <td width="150px" colspan="2"><s:input type ="text" path="strDocCode" id="txtGRNCode" name="strGRNCode" readonly="readonly"  class="searchTextBox" style="width: 150px;background-position: 136px 4px;" ondblclick="funHelp('grncode')"/> </td> --%>
		
		<td width="1%" colspan="2">
		<s:input type ="text" path="strFromDocCode" id="txtFromGRNCode" name="strGRNCode" readonly="true" placeholder="From GRN Code"  class="searchTextBox" style="width: 150px;background-position: 136px 4px;" ondblclick="funHelp('grncodeforprint')"/> 
		</td>
		<td width="0%" colspan="2">
		<s:input type ="text" path="strToDocCode" id="txtToGRNCode" name="strGRNCode" readonly="true" placeholder="To GRN Code"  class="searchTextBox" style="width: 150px;background-position: 136px 4px;" ondblclick="funHelp('Togrncode')"/> 
		</td>
	</tr>
	<tr>
	<td><label>Report Type</label></td>
					<td colspan="4">
						<s:select id="cmbDocType" path="strDocType" cssClass="BoxW124px">
				    		<s:option value="PDF">PDF</s:option>
				    		<s:option value="XLS">EXCEL</s:option>
				    		<s:option value="HTML">HTML</s:option>
				    		<s:option value="CSV">CSV</s:option>
				    	</s:select>
			</td>
	</tr>
	<tr>
		<td colspan="4"></td>
		
	</tr>
</table>
<br>
		<p align="center">
			<input type="submit" value="Submit" onclick="return funCallFormAction('submit',this)" class="form_button"/>
			 <input type="button" value="Reset" class="form_button" />
		</p>
<s:input type="hidden" id="hidSuppCode" path="strSuppCode"></s:input>
</s:form>
</body>
</html>