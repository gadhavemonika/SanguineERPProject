<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Requisition Slip</title>
	<script type="text/javascript">
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
		//	 window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
			 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
    	/**
    	*  Get Data from help Selection 
    	**/
		function funSetData(code)
		{
			$("#txtReqCode").val(code);
		}
		/**
		* Checking Validation on Submit form
		**/
		function funCallFormAction(actionName,object) 
		{	
		  if($("#txtReqCode").val()=="")
			{
				alert("Please Enter Requisition Code");
				return false;
			}
			else
			{
				if (actionName == 'submit') 
				{
						document.forms[0].action = "rptRequisitionSlip.html";
				}
			}
		}
    </script>
</head>
<body>
	<p>Requisition Slip</p>
	<s:form name="ReqSlip" method="POST" action="rptRequisitionSlip.html" target="_blank">
		<table>
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
				<td><input type="submit" onclick="return funCallFormAction('submit',this)"  value ="submit"></td>
				<td><input type="Reset" value ="Reset"></td>
			</tr>
		</table>
	</s:form>
</body>
</html>