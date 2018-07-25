<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GROUP MASTER</title>
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
		  $('input#txtAdvOrderCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtAdvOrderName').mlKeyboard({layout: 'en_US'});
		  
		  $("form").submit(function(event){
			  if($("#txtAdvOrderName").val().trim()=="")
				{
					alert("Please Enter Advance Order Type Name");
					return false;
				}
			  else{
				  flg=funCallFormAction();
				  return flg;
			  }
			});
		}); 




	/**
	* Reset The Group Name TextField
	**/
	function funResetFields()
	{
		$("#txtAdvOrderName").focus();
		
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
			$("#txtAdvOrderCode").val(code);
			var searchurl=getContextPath()+"/loadPOSAdvOrderMasterData.html?POSAdvOrderCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strAdvOrderCode=='Invalid Code')
				        	{
				        		alert("Invalid Adv Order Code");
				        		$("#txtAdvOrderCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtAdvOrderCode").val(response.strAdvOrderCode);
					        	$("#txtAdvOrderName").val(response.strAdvOrderName);
					        	$("#txtAdvOrderName").focus();
					        	$("#cmbOperational").val(response.strOperational);
					        	
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
		
		
		
		
		/**
		*  Check Validation Before Saving Record
		**/
		
		
		function funCallFormAction() 
		{
			var flg=true;
			
			var name = $('#txtAdvOrderName').val();
			var code= $('#txtAdvOrderCode').val();
			
				 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/checkAdvOrderTypeName.html?name="+name+"&code="+code,
				        async: false,
				        dataType: "text",
				        success: function(response)
				        {
				        	if(response=="false")
				        		{
				        			alert("Adv Order Type Already Exist!");
				        			$('#txtAdvOrderName').focus();
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

<body>

<div id="formHeading">
		<label>Advance Order Master</label>
	</div>
	<s:form name="AdvOrderForm" method="POST" action="savePOSAdvanceOrderMaster.html?saddr=${urlHits}">

		<br />
		<br />
		<table class="masterTable">

			<tr>
				<td width="140px">Advance Order Type Code</td>
				<td><s:input id="txtAdvOrderCode" path="strAdvOrderCode"
						cssClass="searchTextBox jQKeyboard form-control" readonly="true" ondblclick="funHelp('POSAdvOrderMaster')" /></td>
			</tr>
			<tr>
				<td><label>Advance Order Type Name</label></td>
				<td><s:input colspan="3" type="text" id="txtAdvOrderName" 
						name="txtAdvOrderName" path="strAdvOrderName" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox jQKeyboard form-control"  /> 
		
			<tr>
			<td><label>Operational</label></td>
				<td><s:select id="cmbOperational" name="cmbOperational" path="strOperational" cssClass="BoxW124px" >
				<option value="Y">Yes</option>
				 <option value="N">No</option>
				
				 </s:select>
				</td>
				
			</tr>
			
			
			<tr>
				<td></td>
				<td></td>
			</tr>
		</table>
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button"/> 
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
	</s:form>
	

</body>


</html>