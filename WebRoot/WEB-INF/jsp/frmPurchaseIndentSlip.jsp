<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  	<link rel="stylesheet" type="text/css" href="default.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Purchase Indent Slip</title>
    <script type="text/javascript">
    	
    	var fieldName;
    	/**
    	 * Ready Function for Initialize textField with default value
    	 * And Set date in date picker 
    	 **/
    	$(function() 
    			{	
		    		var startDate="${startDate}";
					var arr = startDate.split("/");
					Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
    				$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
    				$("#txtFromDate" ).datepicker('setDate', Dat);
    				$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
    				$("#txtToDate" ).datepicker('setDate', 'today');
    			});
    	
    	/**
    	 * Rest textfield value
    	 **/
    	function funResetFields()
    	{
    		$("#txtFromPICode").val("");
    		$("#txtToPICode").val("");
    	}
    	
    	/**
    	 * Open Help form
    	 **/
    	function funHelp(transactionName)
		{
			fieldName=transactionName;
			if(transactionName=="ToPICode")
			{
// 				transactionName="PICode";
				transactionName="PICodeslip";
			}
		//	 window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
			 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
	    }
		
    	/**
    	 *  Get Data from help Selection 
    	**/
		function funSetData(code)
		{
			if(fieldName=="PICode")
			{
//	 			if(fieldName=="PICode")
				if(fieldName=="PICodeslip")	
				 {
					
				 }	
				
			}
			if(fieldName=="ToPICode")
			{
				$("#txtToPICode").val(code);
			}
			
		}
    	
		/**
		* Checking Validation when user Click On Submit Button
		**/
		function funCallFormAction(actionName,object) 
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
				 alert("To Date Should Not Be Less Than Form Date");
			     $("#txtToDate").focus();
				 return false;		    	
			}
			if (actionName == 'submit') 
			{
					document.forms[0].action = "rptPISlip.html";
			}
		}
    
    </script>
  </head>
<body>
<div id="formHeading">
		<label>Purchase Indent Slip</label>
	</div>
<s:form name="PISlip" method="GET" action="rptPISlip.html" target="_blank">

<br />
<br />
<table class="masterTable">
	<tr><th colspan="4"></th></tr>
			<tr>
				<td width="10%"><label id="lblFromDate">From Date</label></td>
				<td width="10%"><s:input id="txtFromDate" name="fromDate"
						path="dtFromDate" cssClass="calenderTextBox" required="true"/> <s:errors
						path="dtFromDate"></s:errors></td>
				<td width="10%"><label id="lblToDate">&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
				To Date</label></td>
				<td colspan="3"><s:input id="txtToDate" name="toDate"
						path="dtToDate" cssClass="calenderTextBox" required="true"/> <s:errors
						path="dtToDate"></s:errors></td>
						
			</tr>
	<tr>
		<td width="10%">PI Code</td>
		<td width="1%" colspan="2">
		<s:input type ="text" path="strFromDocCode" id="txtFromPICode" name="strFromPICode" readonly="true" placeholder="From PI Code"  class="searchTextBox" style="width: 118px;background-position: 104px 2px;" ondblclick="funHelp('PICodeslip')"/> 
		</td>
		<td width="0%" colspan="2">
		<s:input type ="text" path="strToDocCode" id="txtToPICode" name="strToPICode" readonly="true" placeholder="To PI Code"  class="searchTextBox" style="width: 118px;background-position: 104px 2px;" ondblclick="funHelp('ToPICode')"/> 
		</td>
		
	</tr>
	<tr>
	<td><label>Report Type</label></td>
					<td colspan="5">
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
			 <input type="button" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

</s:form>
</body>
</html>