<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Requisition List</title>
	<script>
		
		var fieldName;
	    
		/**
	     * Reset Textfield
	    **/
		function funResetFields()
		{
			$("#txtReqCode").val('');
		}
		
		/**
    	 *  Open Help from
    	**/
		function funHelp(transactionName)
		{
			fieldName=transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=", 'window', 'width=600,height=600');
			window.open("searchform.html?formname="+transactionName+"&searchText=", 'window', 'width=600,height=600');
	    }
		/**
    	*  Get Data from help Selection 
    	**/
		function funSetData(code)
		{
			$("#txtReqCode").val(code);
		}
	
		/**
    	 * Combo box on change event 
    	**/
		function funOnChange()
		{
			if($("#cmbAgainst").val()=="Direct")
			{
				$("#txtReqCode").css('visibility','hidden');
			}
			else
			{
				$("#txtReqCode").css('visibility','visible');
			}
		}
		/**
    	*  Ready function to set Data in datepicker
    	**/
		$(function() 
		{
			$( "#txtFromDate").datepicker();
			$( "#txtToDate").datepicker();
		
		});
		
	</script>

</head>
<body>
<p>Requisition List</p><br>
	<s:form name="ReqList" method="POST" action="rptRequisitionList.html" target="_blank">
		<table>
			<tr>
				<td>From Date :</td>
				<td><s:input id="txtFromDate" path="dtFromDate"/></td>
			</tr>
			
			<tr>
				<td>To Date :</td>
				<td><td><s:input id="txtToDate" path="dtToDate"/></td>
			</tr>
	
			<tr> 
  				<td>Against</td>
  				<td><s:select id="cmbAgainst" onchange="funOnChange();" name="cmbAgainst" style="width: 65%" path="strAgainst">
	  					<option value="All">All</option>
	  					<option value="Direct">Direct</option>
	  					<option value="Work Order">Work Order</option> 
					</s:select>
				</td>
  			</tr>
  
  			<tr>
				<td>Requisition Code :</td>
				<td><s:input class="textbox" id="txtReqCode" path="strDocCode" ondblclick="funHelp('MaterialReq')"/></td>
			</tr>
	
			<tr>
				<td><label>Report Type :</label></td>
				<td>
					<s:select id="cmbDocType" path="strDocType">
			    		<s:option value="PDF">PDF</s:option>
			    		<s:option value="XLS">EXCEL</s:option>
			    		<s:option value="HTML">HTML</s:option>
			    		<s:option value="CSV">CSV</s:option>
			    	</s:select>
				</td>
			</tr>
				
			<tr>
				<td><input type="submit" value ="submit"></td>
				<td><input type="Reset" value ="Reset"></td>
			</tr>
			
		</table>
	</s:form>
</body>
</html>