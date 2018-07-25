<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<link rel="stylesheet" type="text/css" href="default.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Web Stocks</title>
    <script type="text/javascript">
	    /**
		 * Global variable
		 */
    	var fieldName;
    
    	 /**
    	 * Reset Field
    	 */
    	function funResetFields()
    	{
    		$("#txtSACode").val('');
    	}
    	
    	 /**
    	 * Open help window
    	 */
    	function funHelp(transactionName)
		{
			fieldName=transactionName;
		//	 window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
			 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
	    }
    	/**
		 * Set Data after selecting form Help windows
		 */
		function funSetData(code)
		{
			$("#txtSACode").val(code);
		}
    	
    	/**
		 * Checking Validation before submiting the data
		 */
		function funCallFormAction(actionName,object) 
		{	
			
		  if($("#txtSACode").val()=="")
			{
				alert("Please Enter Stock Adjustment Code");
				return false;
			}
			else
			{
				if (actionName == 'submit') 
				{
						document.forms[0].action = "rptStockAdjustmentSlip.html";
				}
			}
		}
    
    </script>
  </head>
  
	<body >
	<div id="formHeading">
		<label>Stock Adjustment Slip</label>
	</div>
	<br />
	<br />
		<s:form name="stkAdjSlip" method="GET" action="rptStockAdjustmentSlip.html" target="_blank">
			<table class="masterTable">
	<tr><th colspan="2"></th></tr>
				<tr>
					<td width="150px"><label>Stock Adjustment Code</label></td>
					<td><s:input  id="txtSACode" path="strDocCode" ondblclick="funHelp('stkadjcodeslip')" cssClass="searchTextBox" cssStyle="width:150px;background-position: 136px 4px;"/></td>
				</tr>
				
				<tr>
					<td><label>Report Type</label></td>
					<td>
						<s:select id="cmbDocType" path="strDocType" cssClass="BoxW124px">
				    		<s:option value="PDF">PDF</s:option>
				    		<s:option value="XLS">EXCEL</s:option>
				    		<s:option value="HTML">HTML</s:option>
				    		<s:option value="Text">40Column</s:option>
				    	</s:select>
					</td>
				</tr>
				
				
				<tr>
					<td><label>Report Format</label></td>
					<td>
						<s:select id="cmbReportType" path="strReportType" cssClass="BoxW124px">
				    		<s:option value="List">List Wise</s:option>
				    		<s:option value="Recipe">Recipe Wise</s:option>
				    		
				    	</s:select>
					</td>
				</tr>
			
			</table>
			<br>
			<p align="center">
				<input type="submit" value="Submit" onclick="return funCallFormAction('submit',this)"  class="form_button"/>
				 <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/>
			</p>
			
		</s:form>
	</body>
</html>