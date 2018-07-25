<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PROPERTY MASTER</title>

<script type="text/javascript">
	/*On form Load It Reset form :Ritesh 22 Nov 2014*/
	$(document).ready(function() {
		resetForms('propertyForm');
		$("#txtPropName").focus();
	});

</script>
<script type="text/javascript">
	var fieldName;
	function funHelp(transactionName) {
		fieldName = transactionName;
		//window.open("searchform.html?formname=" + transactionName+ "&searchText=", 'window', 'width=600,height=600');
	//	 window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	}

	function funSetData(code) {
		switch (fieldName) {
		case "exciseproperty":
			funSetPropertyData(code);
			break;
		}
	}
 
	function funSetPropertyData(code)
	{
		
		$.ajax({
				type : "GET",
				url : getContextPath()+ "/loadExcisePropertyMasterData.html?Code=" + code,
				dataType : "json",
				success : function(resp) 
				{
					$("#txtPropertyCode").val(resp.propertyCode);
					$("#txtPropName").val(resp.propertyName);
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

	function funResetFields() {
		
		$("#txtPropName").focus();
	
	}

	$(function() {
		$('a#baseUrl').click(function() {
			if ($("#txtPropCode").val().trim() == "") {
				alert("Please Select Property Code");
				return false;
			}
			window.open('attachDoc.html?transName=frmExcisePropertyMaster.jsp&formName=Property Master&code='+$('#txtPropCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		});
		
		$('#txtPropCode').blur(function(){			
				var code=$('#txtPropCode').val();
				if (code.trim().length > 0 && code !="?" && code !="/") {
				funSetPropertyData(code);
				}
		});
	});
	
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
 function funCallFormAction(actionName,object) 
	{
		var flg=true;
		if($('#txtPropCode').val()=='')
		{
			var code = $('#txtPropName').val();
			 $.ajax({
			        type: "GET",
			        url: getContextPath()+"/checkExcisePropertName.html?porpName="+code,
			        async: false,
			        dataType: "text",
			        success: function(response)
			        {
			        	if(response=="true")
			        		{
			        			alert("Prpoerty Name Already Exist!");
			        			$('#txtPropName').focus();
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
		}
		//alert("flg"+flg);
		return flg;
	}
</script>


</head>
<body onload="onLoadFunction();">
	<div id="formHeading">
		<label>Excise Property Master</label>
	</div>
	<s:form name="propertyForm" method="POST" action="saveExcisePropertyMaster.html?saddr=${urlHits}">

		<br />
		<br />
		<table class="masterTable">
			<tr>
				<th align="right" colspan="4"><a id="baseUrl"
					href="#"> Attach Documents</a> &nbsp; &nbsp; &nbsp;
					&nbsp;</th>
			</tr>

			<tr>
				<td width="120px"><label>Property Code</label></td>
				<td colspan="3"><s:input path="propertyCode" id="txtPropCode"
						ondblclick="funHelp('exciseproperty');" cssClass="searchTextBox"
						 /></td>
			</tr>

			<tr>
				<td><label>Name </label></td>
				<td colspan="3"><s:input path="propertyName" id="txtPropName"
					cssStyle="text-transform: uppercase;" autocomplete="off"	required="true" cssClass="longTextBox" /></td>
			</tr>
			<tr><td colspan="4"></td></tr>
			<%-- <tr>
				<td><label>Address Line 1 </label></td>
				<td colspan="3"><s:input path="addressLine1" id="txtAddLine1"
						required="true" cssClass="longTextBox" /></td>
			</tr>
			<tr>
				<td><label>Address Line 2 </label></td>
				<td colspan="3"><s:input path="addressLine2" id="txtAddLine2"
						required="true" cssClass="longTextBox" /></td>
			</tr>
			<tr>
				<td><label>City </label></td>
				<td width="160px"><s:input path="city" id="txtCity" required="true"
						cssClass="BoxW116px" /></td>
				<td width="90px"><label>State </label></td>
				<td><s:input path="state" id="txtState" required="true"
						cssClass="BoxW116px" /></td>
			</tr>
			<tr>
				<td><label>Country </label></td>
				<td><s:input path="country" id="txtCountry" required="true"
						cssClass="BoxW116px" /></td>
				<td><label>Pin </label></td>
				<td><s:input path="pin" id="txtPin" type="number"
						required="true" cssClass="BoxW116px" /></td>
			</tr>
			<tr>
				<td><label>Phone </label></td>
				<td><s:input path="phone" id="txtPhone" required="true"
						cssClass="BoxW116px" /></td>
				<td><label>Mobile </label></td>
				<td><s:input path="mobile" id="txtMobile" required="true"
						cssClass="BoxW116px" /></td>
			</tr>
			<tr>
				<td><label>Fax </label></td>
				<td><s:input path="fax" id="txtFax" type="number"
						required="true" cssClass="BoxW116px" /></td>
				<td><label>Contact </label></td>
				<td><s:input path="contact" id="txtContact" type="number"
						required="true" cssClass="BoxW116px" /></td>
			</tr>
			<tr>
				<td><label>Email </label></td>
				<td><s:input path="email" id="txtEmail" type="email"
						required="true" cssClass="BoxW200px" /></td>
				<td></td>
				<td></td>
			</tr> --%>
			
		</table>
		<br />
		

		<p align="center">
			<input type="submit" value="Submit" class="form_button"
				onclick="return funCallFormAction('submit',this);" /> <input type="reset"
				value="Reset" class="form_button" onclick="funResetFields()" />
		</p>
	</s:form>

</body>
</html>