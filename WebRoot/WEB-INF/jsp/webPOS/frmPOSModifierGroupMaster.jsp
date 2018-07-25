<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Modifier Group Master</title>
<style>
.ui-autocomplete {
	max-height: 200px;
	overflow-y: auto;
	/* prevent horizontal scrollbar */
	overflow-x: hidden;
	/* add padding to account for vertical scrollbar */
	padding-right: 20px;
}
/* IE 6 doesn't support max-height
 * we use height instead, but this forces the menu to always be this tall
 */
* html .ui-autocomplete {
	height: 200px;
}
</style>
<script type="text/javascript">
/*On form Load It Reset form :Ritesh 22 Nov 2014*/


 $(document).ready(function () {
	  $('input#txtModifierGroupCode').mlKeyboard({layout: 'en_US'});
	  $('input#txtModifierGroupName').mlKeyboard({layout: 'en_US'});
	  
	  $("form").submit(function(event){
		  if($("#txtModifierGroupName").val().trim()=="")
			{
				alert("Please Enter Modifier group Name");
				return false;
			}
		  else{
			  flg=funCallFormAction();
			  return flg;
		  }
		});
	}); 


	/**
	* Reset The TextField
	**/
	function funResetFields()
	{
		$("#txtGroupName").focus();
		$("#txtOperationType").val('N');
   }
	
	
		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{	       
	       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
		
		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(Group Code)
		**/
		function funSetData(code)
		{
			$("#txtGroupCode").val(code);
			var searchurl=getContextPath()+"/loadPOSModifierGroupMasterData.html?POSModifierGPCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strGCode=='Invalid Code')
				        	{
				        		alert("Invalid Group Code");
				        		$("#txtModifierGroupCode").val('');
				        	}
				        	else
				        	{
				               	$("#txtModifierGroupCode").val(response.strModifierGroupCode);
					        	$("#txtModifierGroupName").val(response.strModifierGroupName);
					        	$("#txtModifierGroupName").focus();
					        	$("#txtModifierGroupShortName").val(response.strModifierGroupName);
					        	$("#cmbOperational").val(response.strModGrpOperational);
					        	$("#cmbMinModifierSelection").val(response.strMinModifierSelection);
					        	$("#txtMinItemLimit").val(response.strMinItemLimit);
					        	$("#cmbMaxModifierSelection").val(response.strMaxModifierSelection);
					        	$("#txtMaxItemLimit").val(response.strMaxItemLimit);
					        	$("#cmbSequenceNo").val(response.strSequenceNo);		        						        	
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
		
		
		function funCallFormAction(actionName,object) 
		{
			var flg=true;
			
			
				var name = $('#txtModifierGroupName').val();
				var code= $('#txtModifierGroupCode').val();
				 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/checkModGrpName.html?groupName="+name+"&groupCode"+code,
				        async: false,
				        dataType: "text",
				        success: function(response)
				        {
				        	if(response=="false")
				        		{
				        			alert("Group Name Already Exist!");
				        			$('#txtModifierGroupName').focus();
				        			flg= false;
					    		}
					    	else
					    		{
					    			flg=true;
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
			

			return flg;
		}

		
</script>




</head>
<body onload="funOnLoad();">
	<div id="formHeading">
		<label>Modifier Group Master</label>
	</div>
	<s:form name="ModgrpForm" method="POST"
		action="saveModifierGroupMaster.html?saddr=${urlHits}">

		<br/>
		<br/>
		<br/>
		<br/>
		<table class="masterTable">

			<tr>
				<td width="140px">Modifier Group Code</td>
				<td width="140px"><s:input id="txtModifierGroupCode"
						path="strModifierGroupCode" cssClass="searchTextBox"
						ondblclick="funHelp('POSModifierGroupMaster')" /></td>
					<td></td>
			</tr>
			<tr>
				<td><label>Modifier Group Name</label></td>
				<td><s:input type="text" id="txtModifierGroupName"
						name="txtModifierGroupName" path="strModifierGroupName"
						required="true" cssClass="longTextBox"/></td>
			<td></td>
			</tr>
			<tr>
				<td width="20%"><label>Modifier Group Short Name </label></td>
				<td width="40%"><s:input id="txtModifierGroupShortName"
						path="strModifierGroupShortName" cssClass="longTextBox"/></td>
			<td></td>
			</tr>
			<tr>
				<td><label>Apply MIN. Modifier Selection</label></td>
				<td><s:select id="cmbMinModifierSelection"
						path="strMinModifierSelection" cssClass="BoxW124px">
						<option selected="selected" value="Y">Yes</option>
						<option value="N">No</option>
					</s:select>
					<s:input id="txtMinItemLimit"
						path="strMinItemLimit" cssClass="longTextBox" style="searchTextBox; width:38%"/></td>
						<td></td>
			</tr>

			<tr>
			<tr>
				<td><label>Apply MAX. Modifier Selection</label></td>
				<td><s:select id="cmbMaxModifierSelection"
						path="strMaxModifierSelection" cssClass="BoxW124px">
						<option selected="selected" value="Y">Yes</option>
						<option value="N">No</option>
					</s:select>
				<s:input id="txtMaxItemLimit"
						path="strMaxItemLimit" cssClass="longTextBox" style="searchTextBox; width:38%" /></td>
						<td></td>
			</tr>

			<tr>
				<td width="20%"><label>Sequence No. </label></td>
				<td><s:select id="cmbSequenceNo" items="${listSeqNo}"
						path="strSequenceNo" cssClass="BoxW124px">
						
						<!-- <option selected="selected" value="Y">Yes</option>
						<option value="N">No</option>
						 -->
						 </s:select></td>
			<td></td>
			</tr>
			<tr>
				<td><label>Operational</label></td>
				<td><s:select id="cmbOperational"
						path="strModGrpOperational" cssClass="BoxW124px">
						<option selected="selected" value="YES">Yes</option>
						<option value="NO">NO</option>
					</s:select></td>
				<td></td>
			</tr>
			<tr>
				<td><%-- <s:input colspan="3" type="hidden" id="txtOperationType"
						value="N" name="txtOperationType" path="strOperationType" /> --%>
						</td>
						<td></td>
						<td></td>
			</tr>

			<tr>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button"
				onclick="funResetFields()" />
		</p>
	</s:form>

</body>
</html>