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
	 * Ready Function for Initialize textField with default value
	 * And Set date in date picker 
	**/
    $(function() 
			{	
		    	var startDate="${startDate}";
				var arr = startDate.split("/");
				Date1=arr[0]+"-"+arr[1]+"-"+arr[2];
				$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
				$("#txtFromDate" ).datepicker('setDate', Date1);
				$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
				$("#txtToDate" ).datepicker('setDate', 'today');
			});
    	var fieldName;
    
    	/**
    	 * Reset field
    	 */
    	function funResetFields()
    	{
    		$("#txtSuppCode").val('');
    		$("#txtSuppName").text('');
    	}
    	
    	/**
    	 * Get project path
    	 */
    	function getContextPath() 
    	{
    		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
    	}
    	
    	/**
    	 * Open help
    	 */
    	function funHelp(transactionName)
		{
			fieldName=transactionName;
		//	 window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:700px;dialogLeft:300px;")
			 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:700px;dialogLeft:300px;")
	    }
    	
    	/**
    	 * Set data from help windows
    	 */
		function funSetData(code)
		{
			$("#txtSuppCode").val(code);
			funSetSupplier(code);
		
		}
    	
		/**
    	 * Set supplier data passing value pary code(supplier code)
    	 */
		function funSetSupplier(code)
		{
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
						$("#txtSuppCode").val(response.strPCode);
						$("#txtSuppName").text(response.strPName);
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
    
		/**
    	 * Textfield on blur event
    	 */
		$(function() 
		{
			$('#txtSuppCode').blur(function() {
				var code = $('#txtSuppCode').val();
				if(code.trim().length > 0 && code !="?" && code !="/"){
					funSetSupplier(code);
				}
			});
		});
		//Check Validation when User Submit the Data
		function formSubmit()
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
		    else
		    {
		    		document.forms["frmProductwiseSupplierwise"].submit();
		    }
	    } 
    </script>
  </head>
  
	<body >
	<div id="formHeading">
		<label>Productwise Supplierwise</label>
	</div>
	<br />
	<br />
		<s:form name="frmProductwiseSupplierwise" method="GET" action="rptProductwiseSupplierwise.html" target="_blank">
			<table class="masterTable">
	<tr><th colspan="5"></th></tr>
			<tr>
				<td width="10%"><label id="lblFromDate">From Date</label></td>
				<td width="10%"><s:input id="txtFromDate" name="fromDate"
						path="dtFromDate" cssClass="calenderTextBox" required="true"/> <s:errors
						path="dtFromDate"></s:errors></td>
				<td width="10%"><label id="lblToDate">&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
				To Date</label></td>
				<td colspan="3"><s:input id="txtToDate" name="toDate"
						path="dtToDate" cssClass="calenderTextBox" required="true"/> <s:errors
						path="dtToDate"></s:errors></td>
						
			</tr>
				<tr>
					<td width="15%">Supplier Code</td>
					<td width="10%"><s:input  id="txtSuppCode" path="strDocCode" ondblclick="funHelp('suppcode')" cssClass="searchTextBox" cssStyle="width:150px;background-position: 136px 4px;"/></td>
					<td colspan="5"><span id="txtSuppName" style="font-size: 12px;"></span></td>
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
				<td colspan="5"></td>
					<!-- <td><input type="submit" value="Submit" /></td>
					<td><input type="reset" value="Reset" onclick="funResetFields()"/></td>	 -->				
				</tr>
			</table>
			<br>
			<p align="center">
				<input type="submit" value="Submit"  class="form_button" onclick="return formSubmit();"/>
				 <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/>
			</p>
			
		</s:form>
	</body>
</html>