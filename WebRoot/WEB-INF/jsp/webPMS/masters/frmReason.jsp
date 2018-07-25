<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reason Master</title>

<script type="text/javascript">

	/**
	* Open Help
	**/
	function funHelp(transactionName)
	{
	    window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
// 	    window.open("searchform.html?formname=LocationToAllPropertyStore&searchText="+byLocation,"","dialogHeight:600px,dialogWidth:1000px,dialogLeft:200px")		       
	}
		
	
		/**
		*   Attached document Link
		**/
		$(function()
		{
		
			$('a#baseUrl').click(function() 
			{
				if($("#txtReasonCode").val().trim()=="")
				{
					alert("Please Select Reason Code");
					return false;
				}
			   window.open('attachDoc.html?transName=frmReason.jsp&formName=Reason Master&code='+$('#txtReasonCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			});
			
			
			/**
			* On Blur Event on Reason Code Textfield
			**/
			$('#txtReasonCode').blur(function() 
			{
					var code = $('#txtReasonCode').val();
					if (code.trim().length > 0 && code !="?" && code !="/")
					{				
					 funSetData(code);						
					}
			});
			
			$('#txtReasonDesc').blur(function () {
				 var strReasonDesc=$('#txtReasonDesc').val();
			      var st = strReasonDesc.replace(/\s{2,}/g, ' ');
			      $('#txtReasonDesc').val(st);
				});
			
		});
		

		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(Reason Code)
		**/
		
		function funSetData(code)
		{
			$("#txtReasonCode").val(code);
			var searchurl=getContextPath()+"/loadPMSReasonMasterData.html?reasonCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strReasonCode=='Invalid Code')
				        	{
				        		alert("Invalid Reason Code");
				        		$("#txtReasonCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtReasonDesc").val(response.strReasonDesc);
					        	$("#cmbReasonType").val(response.strReasonType);
					        	
					        	
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
					%>
					alert("Data Save successfully\n\n"+message);
				<%
				}
			}%>
		});
		

		/**
			 *  Check Validation Before Saving Record
		**/
		function funCallFormAction(actionName,object) 
		{
			var flg=true;
			if($('#txtReasonDesc').val()=='')
			{
				alert('Enter Reason Name ');
				flg=false;
			}
			return flg;
		}
</script>


</head>
<body>
	<div id="formHeading">
		<label>Reason Master</label>
	</div>
	
	<s:form name="Reason" method="GET" action="saveReasonMaster.html?" >
	
		<table class="masterTable">
           
           	<tr>
				<th align="right" colspan="2"><a id="baseUrl" href="#"> Attach Documents</a>&nbsp; &nbsp; &nbsp; &nbsp;</th>
			</tr>
			
			<tr>
			    <td><label>Reason Code</label></td>
				<td><s:input id="txtReasonCode" path="strReasonCode" cssClass="searchTextBox" ondblclick="funHelp('reasonPMS')" /></td>				
			</tr>
			
			<tr>
			    <td><label>Select Reason Type</label></td>
				<td>
				 	<s:select id="cmbReasonType" path="strReasonType" cssClass="BoxW124px">
				   		<option selected="selected" value="Management Reason">Management Reason</option>
			         	<option value="Allowance Reason">Allowance Reason</option>
			          	<option value="Cancellation Reason">Cancellation Reason</option>
			          	<option value="Discount Reason">Discount Reason</option>
			          	<option value="Movement Reason">Movement Reason</option>
			          	<option value="Maintainance Reason"> Maintainance Reason </option>
			         	<option value="Undo Check-in Reason">Undo Check-in Reason</option>
		         	</s:select>
				</td>
			</tr>
			
			<tr>
			    <td><label>Reason Description</label></td>
				<td><s:input id="txtReasonDesc" path="strReasonDesc" cssClass="longTextBox" /></td>
			</tr>
			
		</table>
		
		
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button"  onclick="return funCallFormAction('submit',this);" />
            <input type="reset" value="Reset" class="form_button" />   
		</p>
	</s:form>
	
</body>
</html>
