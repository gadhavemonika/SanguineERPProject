<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
$(document).ready(function () {
	  $('input#txttimeShiftStart').mlKeyboard({layout: 'en_US'});
	  $('input#txttimeShiftEnd').mlKeyboard({layout: 'en_US'});
	 
});
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	

	/**
	* Get and Set data from help file and load data Based on Selection Passing Value(Group Code)
	**/
	function funSetData(code)
	{
		$("#txtShiftCode").val(code);
		var searchurl=getContextPath()+"/loadPOSShiftMasterData.html?POSShiftCode="+code;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strCustomerTypeMasterCode=='Invalid Code')
			        	{
			        		alert("Invalid Shift Code");
			        		$("#txtShiftCode").val('');
			        	}
			        	else
			        	{  
				        	$("#txtShiftCode").val(response.intShiftCode);
				        	$("#txtPOSCode").val(response.strPOSCode);
				        	$("#txttimeShiftStart").val(response.strtimeShiftStart);
				        	$("#txttimeShiftStart").focus();
				        	$("#txttimeShiftEnd").val(response.strtimeShiftEnd);
				        	$("#txtBillDateTimeType").val(response.strBillDateTimeType);
				        	$("#cmbtimeShiftStart").val(response.strAMPMStart);
				        	$("#cmbtimeShiftEnd").val(response.strAMPMEnd);
				        	 
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
	
	/**
	* Success Message After Saving Record
	**/
	$(document).ready(function()
	{
		var message='';
		<%if (session.getAttribute("success") != null) 
		{
			if(session.getAttribute("successMessage") != null)
			{%>
				message='<%=session.getAttribute("successMessage").toString()%>';
			    <%
			    session.removeAttribute("successMessage");
			}
			boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
			session.removeAttribute("success");
			if (test) 
			{
				%>alert("Data Saved \n\n"+message);<%
			}
		}%>
	});

	$(function() {
		  $('#staticParent').on('keydown', '#txttimeShiftStart', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()});
		  $('#staticParent').on('keydown', '#txttimeShiftEnd', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()});
		  $('#staticParent').on('keydown', '#txtShiftCode', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()});
		})
	 		
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>Shift Master</label>
	</div>

<br/>
<br/>

	<s:form name="ShiftMaster" method="POST" action="savePOSShiftMaster.html?saddr=${urlHits}">
<div id="staticParent">
		<table class="masterTable">
			<tr>
				<td>
					<label>Shift No</label>
				</td>
				<td>
					<s:input colspan="3" type="text"  id="txtShiftCode" path="intShiftCode" cssClass="searchTextBox" ondblclick="funHelp('POSshiMaster')" />
				</td>
			</tr>
			<tr>
				<td>
					<label>POS</label>
				</td>
				<td>
					<s:select id="txtPOSCode" path="strPOSCode" items="${posList}" cssClass="BoxW124px" />
				</td>
			</tr>
			 <tr>
				 <td>
					<label>Shift Start</label>
				</td>
				<td>
					<s:input colspan="3"  id="txttimeShiftStart" path="strtimeShiftStart" cssClass="BoxW116px" />
				    
				     <s:select id="cmbtimeShiftStart" path="strAMPMStart" cssClass="BoxW48px">
				    			<option selected="selected" value="AM">AM</option>
			        			<option value="PM">PM</option>
		         			</s:select>
					</td> 
			 </tr> 
					<tr>
				<td>
					<label>Shift End</label>
				</td>
					<td>
				<s:input colspan="3" type="text"  id="txttimeShiftEnd" path="strtimeShiftEnd" cssClass="BoxW116px" />
				 <s:select id="cmbtimeShiftEnd" path="strAMPMEnd" cssClass="BoxW48px">
				    			<option selected="selected" value="AM">AM</option>
			        			<option value="PM">PM</option>
		         			</s:select>
				</td>
			</tr>  
				
			<tr>
				<td>
					<label>Bill Date Time Type</label>
				</td>
				<td>
					<%-- <s:input id="txtBillDateTimeType" path="strBillDateTimeType"   cssClass="BoxW116px"/> --%>
					<s:select id="cmbBillDateTimeType" path="strBillDateTimeType" cssClass="BoxW124px">
				    			<option selected="selected" value="Pos Date">Pos Date</option>
			        			<option value="System Date">System Date</option>
		         			</s:select>
				</td>
			</tr>
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
</div>
	</s:form>
</body>
</html>
