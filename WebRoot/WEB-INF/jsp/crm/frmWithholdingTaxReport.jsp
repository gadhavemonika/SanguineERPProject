<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@	taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Withholding Tax Report</title>
</head>
<script type="text/javascript">
	$(function() {
		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});
		/*$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtFromDate" ).datepicker('setDate', 'today');
		*/
		var startDate="${startDate}";
		var arr = startDate.split("/");
		Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
		$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtFromDate" ).datepicker('setDate', Dat);
		$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtToDate" ).datepicker('setDate', 'today');
		
		

	});
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
        
      //  window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
      window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;top=500,left=500")
    }
	
	
	function funSetData(code)
	{
		switch (fieldName) 
		{		   
		   case 'custMaster':
			   funSetCustomer(code);
		        break;   
		}
	}
	
	function funSetCustomer(code)
	{
		gurl=getContextPath()+"/loadPartyMasterData.html?partyCode=";
		$.ajax({
	        type: "GET",
	        url: gurl+code,
	        dataType: "json",
	        success: function(response)
	        {		        	
	        		if('Invalid Code' == response.strPCode){
	        			alert("Invalid Customer Code");
	        			$("#txtPartyCode").val('');
	        			$("#txtPartyCode").focus();
	        			
	        		}else{
	        			$("#txtPartyCode").val(response.strPCode);
						$("#txtPartyName").val(response.strPName);
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
</script>
<body>
	<div id="formHeading">
		<label>Withholding Tax Report</label>
	</div>

	<br />
	<br />

	<s:form name="frmWithholdingTaxReport" method="GET" action="rptWithholdingTaxReport.html" target="_blank">
		<div>
			<table class="transTable">
			    <tr>
					<td width="10%"><label>From Date </label></td>
					<td width="10%" colspan="1"><s:input id="txtFromDate" path="dteFromDate" required="true" readonly="readonly" cssClass="calenderTextBox"/></td>
					<td width="10%"><label>To Date </label></td>
					<td width="10%"><s:input id="txtToDate" path="dteToDate" required="true" readonly="readonly" cssClass="calenderTextBox"/>
					</td>	
				</tr>
				<tr>
					<td width="140px">
				        <label>Customer Code </label>
				    </td>
				    <td  width="15%">
				       <s:input id="txtPartyCode" name="txtPartyCode"  path="strCustCode" ondblclick="funHelp('custMaster')"   cssClass="searchTextBox"/>
				     </td>
				     <td><label>Customer Name</label></td>
				     <td colspan="3">
				      <s:input size="80px" type="text" id="txtPartyName"  name="txtPartyName" path="strCustName" cssStyle="text-transform: uppercase;" cssClass="longTextBox" />
				     </td>
				</tr>
			</table>
		</div>
		<p align="center">
				<input type="submit" value="Submit"  class="form_button" />
				 <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/>
			</p>
	</s:form>

</body>
</html>