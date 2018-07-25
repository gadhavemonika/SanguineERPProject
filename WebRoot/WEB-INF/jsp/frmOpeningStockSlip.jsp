<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  	<link rel="stylesheet" type="text/css" href="default.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Opening Stock Slip</title>
    <script type="text/javascript">
    	
	    /**
		  *  Global variable
		 **/
    	var fieldName;
    
    	/**
		  *  Reset the form
		 **/
    	function funResetFields()
    	{
    		$("#txtOpStkCode").val('');
    	}
    	
    	/**
		  * Open help form
		 **/
    	function funHelp(transactionName)
		{
			fieldName=transactionName;
			//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=", 'window', 'width=600,height=600');
		//	 window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
			 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
    	
    	/**
    	* 	Get and Set from Help From windows
    	**/
		function funSetData(code)
		{
			$("#txtOpStkCode").val(code);
		}
    	
		 /**
		  *  Check Validation Before Submit The Form
		 **/
		function funCallFormAction(actionName,object) 
		{	
			
		  if($("#txtOpStkCode").val()=="")
			{
				alert("Please Opening Stock Code");
				return false;
			}
			else
			{
				if (actionName == 'submit') 
				{
						document.forms[0].action = "rptOpeningStockSlip.html";
				}
			}
		}
    
    </script>
  </head>
<body>
<div id="formHeading">
		<label>Opening Stock Slip</label>
	</div>
<s:form name="GRNSlip" method="GET" action="rptOpeningStockSlip.html" target="_blank">

<br />
<br />
<table class="masterTable">
	<tr><th colspan="4"></th></tr>
	<tr>
		<td width="150px">Opening Stock Code</td>
		<td width="150px" colspan="2"><s:input type ="text" path="strDocCode" id="txtOpStkCode" name="strGRNCode" readonly="readonly"  class="searchTextBox" style="width: 150px;background-position: 136px 4px;" ondblclick="funHelp('opstockslip')"/> </td>
		
	</tr>
	<tr>
	<td><label>Report Type</label></td>
					<td colspan="2">
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

</s:form>
</body>
</html>