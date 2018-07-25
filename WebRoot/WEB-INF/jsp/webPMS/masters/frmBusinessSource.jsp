<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Business Source Master</title>

<script type="text/javascript">

		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{
			window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		    //window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		}
		
		
		

		/**
		*   Attached document Link
		**/
		$(function()
		{
		
			$('a#baseUrl').click(function() 
			{
				if($("#txtBusinessCode").val().trim()=="")
				{
					alert("Please Select Business Code");
					return false;
				}
			   	window.open('attachDoc.html?transName=frmBusinessSource.jsp&formName=BusinessSource Master&code='+$('#txtBusinessCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			});
			
			/**
			* On Blur Event on Business Code Textfield
			**/
			$('#txtBusinessCode').blur(function() 
			{
				var code = $('#txtBusinessCode').val();
				if (code.trim().length > 0 && code !="?" && code !="/")
				{
					funSetData(code);
				}
			});
			
			$('#txtBusinessDesc').blur(function () {
				 var strDescription=$('#txtBusinessDesc').val();
			      var st = strDescription.replace(/\s{2,}/g, ' ');
			      $('#txtBusinessDesc').val(st);
				});
			
		});
		
	

		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(Business Source Code)
		**/
		
		function funSetData(code)
		{
			$("#txtBusinessCode").val(code);
			var searchurl=getContextPath()+"/loadBusinessMasterData.html?businessCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strBusinessSourceCode=='Invalid Code')
				        	{
				        		alert("Invalid Business Code");
				        		$("#txtBusinessCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtBusinessDesc").val(response.strDescription);
					        	$("#cmbInvolvesAmt").val(response.strInvolvesAmt);
					        	$("#cmbInstrument").val(response.strInstAccepted);
					        	$("#cmbRequestSlip").val(response.strReqSlipReqd);
					        	
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
			<%if (session.getAttribute("success") != null) {
				            if(session.getAttribute("successMessage") != null){%>
				            message='<%=session.getAttribute("successMessage").toString()%>';
				            <%
				            session.removeAttribute("successMessage");
				            }
							boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
							session.removeAttribute("success");
							if (test) {
							%>	
				alert("Data Save successfully\n\n"+message);
			<%
			}}%>

		});
	
		
		 /**
			 *  Check Validation Before Saving Record
			 **/
					function funCallFormAction(actionName,object) 
					{
						var flg=true;
						if($('#txtBusinessDesc').val()=='')
						{
							 alert('Enter Business Description');
							 flg=false;
							  
						}
						return flg;
					}
				
		
		
		
	
</script>


</head>
<body>
	<div id="formHeading">
		<label>Business Source Master</label>
	</div>
	
	<s:form name="Business" method="GET" action="saveBusinessSourceMaster.html?" >
	
		<table class="masterTable">

           <tr>
				<th align="right" colspan="2"><a id="baseUrl"
					href="#"> Attach Documents</a>&nbsp; &nbsp; &nbsp;
						&nbsp;</th>
			</tr>

			<tr>
			    <td><label>Code</label></td>
				<td><s:input id="txtBusinessCode" path="strBusinessSourceCode" cssClass="searchTextBox" ondblclick="funHelp('business')" /></td>				
			</tr>
			
			<tr>
			    <td><label>Description</label></td>
				<td><s:input id="txtBusinessDesc" path="strDescription" cssClass="longTextBox" /></td>				
			</tr>
			
			<tr>
				 <td><label>Involves amount</label></td>
				 <td  >
				 <s:select id="cmbInvolvesAmt" path="strInvolvesAmt" cssClass="BoxW124px">
				    <option selected="selected" value="Y">Yes</option>			           
			        <option value="N">No</option>
		         </s:select>
				</td>						
			</tr>
			
			<tr>
				 <td><label>Instrument accepted</label></td>
				 <td  >
				 <s:select id="cmbInstrument" path="strInstAccepted" cssClass="BoxW124px">
				    <option selected="selected" value="Y">Yes</option>			           
			        <option value="N">No</option>
		         </s:select>
				</td>
			</tr>
			
			<tr>
				 <td><label>Request Slip Required</label></td>
				 <td  >
				 <s:select id="cmbRequestSlip" path="strReqSlipReqd" cssClass="BoxW124px">
				    <option selected="selected" value="Y">Yes</option>
			        <option value="N">No</option>
		         </s:select>
				</td>
			</tr>
			
			
			
		</table>
		
		
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funCallFormAction('submit',this);" />
            <input type="reset" value="Reset" class="form_button" />
           
            
		</p>
	</s:form>
	
</body>
</html>