<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Web Stocks</title>
    <script type="text/javascript">
    
    /**
	 * Open Help Form 
	 */
    function funHelp(transactionName)
	{
		// window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
    	 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")	
	}
    
    /**
	 * Set Data after selecting form Help windows
	 */
    function funSetData(code)
	{
    	$("#txtSTCode").val(code);
	}
    
    /**
	 * Checking Validation before submiting the data
	 */
    function funCallFormAction(actionName,object) 
	{	
		
	  if($("#txtSTCode").val()=="")
		{
			alert("Please Enter Stock Posting Code");
			return false;
		}
		else
		{
			if (actionName == 'submit') 
			{
					document.forms[0].action = "rptPhysicalStockPsostingSlip.html";
			}
		}
	}
	</script>
    
  </head>
  
	<body>
	<div id="formHeading">
		<label>Physical Stock Posting Slip</label>
	</div>
	<br />
	<br />
		<s:form name="PhysicalStockPostingSlip" method="GET" action="rptPhysicalStockPsostingSlip.html" target="_blank">
	   		<table class="masterTable">
			<tr><th colspan="2"></th></tr>
	   		
			    <tr>
			        <td  width="150px"><label >Stock Posting Code</label></td>
			        <td>
			        	<s:input id="txtSTCode" path="strDocCode" ondblclick="funHelp('stkpostcodeslip')" cssClass="searchTextBox" cssStyle="width:150px;background-position: 136px 4px;"/>
			        </td>
			    </tr>
			    <tr>
			      
			      	<td><label>Report Type</label></td>
					<td>	<s:select id="cmbDocType" path="strDocType" cssClass="BoxW124px">
				    		<s:option value="PDF">PDF</s:option>
				    		<s:option value="XLS">EXCEL</s:option>
				    		<s:option value="HTML">HTML</s:option>
				    		<s:option value="CSV">CSV</s:option>
				    	</s:select>
					</td>
			    </tr>
			   	<tr>
				<td colspan="2"></td>
								
				</tr>
			</table>
			<br>
			<p align="center">
				<input type="submit" value="Submit"  class="form_button" onclick="return funCallFormAction('submit',this)" />
				 <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/>
			</p>
		</s:form>
	</body>
</html>