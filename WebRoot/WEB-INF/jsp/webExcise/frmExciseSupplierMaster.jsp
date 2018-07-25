<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">

var fieldName;

function funHelp(transactionName)
{
	fieldName=transactionName;
//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
	window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
}

	function funSetData(code){

		switch(fieldName){

			case 'SupplierCode' : 
				funSetSupplierCode(code);
				break;
				
			case 'CityCode' : 
				funSetCityCode(code);
				break;
		}
	}
	
	function funSetCityCode(CityCode)
	{
			$("txtPINCode").focus();			
			var gurl=getContextPath()+"/loadExciseCityMasterData.html?cityCode=";
			$.ajax({
		        type: "GET",				        
		        url: gurl+CityCode,
		        dataType: "json",
		        success: function(response)
		        {	
		        	if(response.StrCityCode=='Invalid Code')
		        	{
		        		alert("Invalid City Code Please Select Again");
		        		$("#txtCityCode").val('');
		        	}
		        	else
		        	{
		        		$("#txtCity").val(response.strCityCode);
		        		$("#txtCityName").html(response.strCityName);
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
	

	function funSetSupplierCode(code)
	{
		$("#txtSupplierNo").focus();
		var gurl=getContextPath()+"/loadExciseSupplierMasterData.html?supplierCode=";
		$.ajax({
	        type: "GET",				        
	        url: gurl+code,
	        dataType: "json",
	        success: function(response)
	        {	
	        	if(response.strSupplierNo=='Invalid Code')
	        	{
	        		alert("Invalid Supplier Code Please Select Again");
	        		$("#txtSupplierCode").val('');
	        		$("#txtSupplierName").focus();
	        	}
	        	else
	        	{
	        		$("#txtSupplierCode").val(response.strSupplierCode);
// 	        		$("#txtSupplierNo").val(response.strSupplierNo);
	        		$("#txtSupplierName").val(response.strSupplierName);
	        		$("#txtVATNo").val(response.strVATNo);
	        		$("#txtTINNo").val(response.strTINNo);
	        		$("#txtAddress").val(response.strAddress);
	        		$("#txtCity").val(response.strCityCode);
	        		$("#txtCityName").html(response.strCityName);
	        		$("#txtPINCode").val(response.strPINCode);
	        		$("#txtTelephoneNo").val(response.longTelephoneNo);
	        		$("#txtMobileNo").val(response.longMobileNo);
	        		$("#txtEmailId").val(response.strEmailId);
	        		$("#txtContactPerson").val(response.strContactPerson);
	        		
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
	
	$(function(){
		
		$("#txtSupplierNo").focus();
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
			alert("Supplier Data Save successfully\n\n"+message);
		<%
		}}%>
		
		$('#txtSupplierCode').blur(function () 
				{
				 var code=$('#txtSupplierCode').val();
				 if (code.trim().length > 0 && code !="?" && code !="/"){							   
					 funSetSupplierCode(code);
				   }
				});
		
		$('#txtCity').blur(function () 
				{
				 var code=$('#txtCity').val();
				 if (code.trim().length > 0 && code !="?" && code !="/"){							   
					 funSetCityCode(code);
				   }
				});
	});
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>Excise Supplier Master</label>
	</div>

<br/>
<br/>

	<s:form name="ExciseSupplierMaster" method="POST" action="saveExciseSupplierMaster.html?saddr=${urlHits}">

		<table class="masterTable">
			<tr>
				<td width="100px">
					<label>Supplier Code</label>
				</td>
				<td width="150px">
					<s:input type="text" id="txtSupplierCode" path="strSupplierCode" cssClass="searchTextBox" ondblclick="funHelp('SupplierCode')"/>
				</td>
				<td width="100px"></td>
				<td width="100px">
<!-- 					<label>Supplier No</label> -->
				</td>
				<td width="150px">
<%-- 					<s:input type="text" id="txtSupplierNo" path="strSupplierNo" cssClass="BoxW124px" required="true" /> --%>
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>Supplier Name</label>
				</td>
				<td colspan="4">
					<s:input type="text" id="txtSupplierName" path="strSupplierName" cssClass="longTextBox" required="true" cssStyle=" width: 69% ;" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>Address</label>
				</td>
				<td colspan="4">
					<s:input type="text" id="txtAddress" path="strAddress" cssClass="longTextBox" cssStyle=" width: 69% ;" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>VAT No</label>
				</td>
				<td>
					<s:input type="text" id="txtVATNo" path="strVATNo" cssClass="BoxW124px"  />
				</td>
			<td></td>
				<td>
					<label>TIN No</label>
				</td>
				<td>
					<s:input type="text" id="txtTINNo" path="strTINNo" cssClass="BoxW124px" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>City</label>
				</td>
				<td>
					<s:input type="text" id="txtCity" path="strCityCode" cssClass="searchTextBox" ondblclick="funHelp('CityCode')" required="true"/>
				</td>
				<td><s:label path="strCityName" id="txtCityName"/></td>
				<td>
					<label>PINCode</label>
				</td>
				<td>
<%-- 					<s:input type="text" id="txtPINCode" path="strPINCode" cssClass="BoxW124px" maxlength="6" pattern="\d{6}-?(\d{4})?$"/> --%>
						<s:input type="text" id="txtPINCode" path="strPINCode" cssClass="BoxW124px" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>Contact Person</label>
				</td>
				<td>
					<s:input type="text" id="txtContactPerson" path="strContactPerson" cssClass="BoxW124px" />
				</td>
				<td></td>
				<td>
					<label>Telephone No</label>
				</td>
				<td>
					<s:input type="text"  id="txtTelephoneNo" path="longTelephoneNo" cssClass="BoxW124px" maxlength="11"  />
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>Email Id</label>
				</td>
				<td>
<%-- 					<s:input type="text" id="txtEmailId" path="strEmailId" cssClass="BoxW124px" pattern='\S+@\S+\.\S+' /> --%>
						<s:input type="text" id="txtEmailId" path="strEmailId" cssClass="BoxW124px"  />
				</td>
				<td></td>
				<td>
					<label>Mobile No</label>
				</td>
				<td>
					<s:input type="text" id="txtMobileNo" cssClass="BoxW124px" path="longMobileNo"  />
				</td>
				<td></td>
			</tr>
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" class="form_button" />
			<input type="reset" value="Reset" class="form_button" />
		</p>

	</s:form>
</body>
</html>
