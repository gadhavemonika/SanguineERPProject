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
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	

	function funSetData(code){

		switch(fieldName){

			case 'WCCompanyCode' : 
				funSetCompanyCode(code);
				break;
			case 'WCComAreaMaster' : 
				funSetComAreaCode(code);
				break;
			case 'WCComCityMaster' : 
				funSetComCityCode(code);
				break;
			case 'WCComStateMaster' : 
				funSetComStateCode(code);
				break;
			case 'WCComRegionMaster' : 
				funSetComRegionCode(code);
				break;
			case 'WCComCountryMaster' : 
				funSetComCountryCode(code);
				break;
			case 'WCCategoryMaster' : 
				funSetComRegionCode(code);
				break;
				
			case 'WCmemProfileCustomer' :
				funloadMemberData(code);
				break;

			case 'WCCatMaster' :
				funSetCategoryData(code);
				
		}
	}


	function funSetCompanyCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadWebClubCompanyData.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 

				if(response.strCompanyCode=='Invalid Code')
	        	{
	        		alert("Invalid Company Code");
	        		$("#txtCompanyCode").val('');
	        	}
	        	else
	        	{      
		        	$("#txtCompanyCode").val(code);
		        	$("#txtCompanyName").val(response.strCompanyName);
		        	$("#txtAnnualTrunover").val(response.dblAnnualTrunover);
		        	$("#txtCapital").val(response.dblCapital);
		        	$("#txtMemberCode").val(response.strMemberCode);
		        	$("#txtCategoryCode").val(response.strCategoryCode);
		        	$("#txtActiveNominee").val(response.strActiveNominee);
		        	$("#txtAddress1").val(response.strAddress1);
		        	$("#txtAddress2").val(response.strAddress2);
		        	$("#txtAddress3").val(response.strAddress3);
		        	$("#txtLandMark").val(response.strLandmark);
		        	$("#txtAreaCode").val(response.strAreaCode);
		        	$("#txtCtCode").val(response.strCityCode);
		        	$("#txtStateCode").val(response.strStateCode);
		        	$("#txtRegionCode").val(response.strRegionCode);
		        	$("#txtCountryCode").val(response.strCountryCode);
		        	
		        	$("#txtPinCode").val(response.strPin);
		        	$("#txtTelePhone1").val(response.strTelephone1);
		        	$("#txtTelePhone2").val(response.strTelephone2);
		        	$("#txtFax1").val(response.strFax1);
		        	$("#txtFax2").val(response.strFax2);
// 		        	$("#").val(response.);
		        	
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

	function funSetComAreaCode(code){

		$("#txtAreaCode").val(code);
		var searchurl=getContextPath()+"/loadAreaCode.html?docCode="+code;
		//alert(searchurl);
		
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strAreaCode=='Invalid Code')
		        	{
		        		alert("Invalid Group Code");
		        		$("#txtGroupCode").val('');
		        	}
		        	else
		        	{ 
			        	$("#txtAreaName").val(response.strAreaName);
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
	
	
	function funSetComCityCode(code){
		//alert("Hii");
		$("#txtCtCode").val(code);
		var searchurl=getContextPath()+"/loadCityCode.html?docCode="+code;
		//alert(searchurl);
		
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strCityCode=='Invalid Code')
		        	{
		        		alert("Invalid City Code In");
		        		$("#txtResidentCtCode").val('');
		        	}
		        	else
		        	{
			        		$("#txtCityName").val(response.strCityName);
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
	
	
	function funSetComCountryCode(code){
		  
		$("#txtCountryCode").val(code);
		var searchurl=getContextPath()+"/loadCountryCode.html?docCode="+code;
		//alert(searchurl);
		
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strCountryCode=='Invalid Code')
		        	{
		        		alert("Invalid Country Code In");
		        		$("#txtCountryCode").val('');
		        	}
		        	else
		        	{
		        		$("#txtCountryName").val(response.strCountryName);
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

	function funSetComStateCode(code){
		  
		$("#txtStateCode").val(code);
		var searchurl=getContextPath()+"/loadStateCode.html?docCode="+code;
		//alert(searchurl);
		
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strStateCode=='Invalid Code')
		        	{
		        		alert("Invalid State Code In");
		        		$("#txtStateCode").val('');
		        	}
		        	else
		        	{
		        		$("#txtStateName").val(response.strStateName);
		        		 
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
			function funSetComRegionCode(code){
				
				$("#txtRegionCode").val(code);
				var searchurl=getContextPath()+"/loadRegionCode.html?docCode="+code;
				//alert(searchurl);
				    
					$.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strRegionCode=='Invalid Code')
				        	{
				        		alert("Invalid Region Code In");
				        		$("#txtRegionCode").val('');
				        	}
				        	else
				        	{
				        		$("#txtRegionName").val(response.strRegionName);
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
	
			
			function funSetCategoryData(code)
			{
				$("#txtCategoryCode").val(code);
				var searchurl=getContextPath()+"/loadWebClubMemberCategoryMaster.html?catCode="+code;
				//alert(searchurl);
				 $.ajax({
					        type: "GET",
					        url: searchurl,
					        dataType: "json",
					        success: function(response)
					        {
					        	if(response.strGCode=='Invalid Code')
					        	{
					        		alert("Invalid Category Code");
					        		$("#txtCatCode").val('');
					        	}
					        	else
					        	{
						        	//$("#txtCategoryCode").val(code);
						        	$("#strCategoryName").val(response.strCategoryName);
						        	
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
			
			
			function funloadMemberData(code)
			{
				$("#txtMemberCode").val(code);
				var searchurl=getContextPath()+"/loadWebClubMemberProfileData.html?primaryCode="+code;
				//alert(searchurl);
				 $.ajax({
					        type: "GET",
					        url: searchurl,
					        success: function(response)
					        {
					        	if(response.strMemberCode=='Invalid Code')
					        	{
					        		alert("Invalid Member Code");
					        		$("#txtMemberCode").val('');
					           	}
					        	else
					        	{  
					        		$("#txtMemberName").val(response[0].strFullName);
						        	
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
			
			
			function funSetCategoryData(code)
			{
				$("#txtCategoryCode").val(code);
				var searchurl=getContextPath()+"/loadWebClubMemberCategoryMaster.html?catCode="+code;
				//alert(searchurl);
				 $.ajax({
					        type: "GET",
					        url: searchurl,
					        dataType: "json",
					        success: function(response)
					        {
					        	if(response.strCatCode=='Invalid Code')
					        	{
					        		alert("Invalid Category Code");
					        		$("#txtCategoryCode").val('');
					        	}
					        	else
					        	{
					        		
						        	$("#txtCategoryName").val(response.strCatName);
						        	
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
			
			
	
			function funResetFields()
			{
				location.reload(true); 
			}
	
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>CompanyMaster</label>
	</div>

<br/>
<br/>

	<s:form name="CompanyMaster" method="POST" action="saveCompanyMaster.html">

		<table class="masterTable">
						
					<tr>
							<td ><label>Company Name</label></td>
							<td ><s:input id="txtCompanyCode"
							ondblclick="funHelp('WCCompanyCode')" cssClass="searchTextBox" 
							readonly="true" type="text" path="strCompanyCode" ></s:input></td>
									
							<td colspan="2"><s:input id="txtCompanyName" path="strCompanyName" 
									cssClass="longTextBox" type="text"></s:input></td>
							<td>Type of Company</td>
					<td><s:select id="cmbCompanyType" name="cmbCompanyType" path="" cssClass="BoxW124px" >
								 <option value="N">No</option>
				 				 <option value="Y">Yes</option>
				 				</s:select></td>		
									
							
		</tr>
		<tr>
						<td>Annual Turnover</td>
					<td colspan="2"><s:input id="txtAnnualTrunover" path="dblAnnualTrunover" 
									cssClass="longTextBox" type="text"></s:input> In Cr. </td>
					<td>Capital and Reserved</td>
					<td colspan="2"><s:input id="txtCapital" path="dblCapital" 
									cssClass="longTextBox" type="text"></s:input> In Cr. </td>				
		
		</tr>
		<tr>
					<td ><label>Member Code</label></td>
							<td width="150px"><s:input id="txtMemberCode"
									ondblclick="funHelp('WCmemProfileCustomer')" cssClass="searchTextBox"
									type="text" path="strMemberCode" ></s:input></td>
					<td colspan="4"><s:input id="txtMemberName" path=""
									cssClass="longTextBox" type="text"  style="width: 34%" ></s:input></td>
		
		</tr>
		<tr>
					<td ><label>Category Code</label></td>
							<td width="150px"><s:input id="txtCategoryCode"
									ondblclick="funHelp('WCCatMaster')" cssClass="searchTextBox"
									type="text" path="strCategoryCode" ></s:input></td>
					<td colspan="4"><s:input id="txtCategoryName"  path=""
									cssClass="longTextBox" type="text"  style="width: 34%"  ></s:input></td>
		
		</tr>
		<tr>
							<td><label>No. of Active </label></td>
							<td colspan="5"><s:input id="txtActiveNominee" path="strActiveNominee" 
									cssClass="longTextBox" type="text" style="width: 21%"></s:input></td>
		</tr>
		
		<tr>
						
						
						<td ><label>Address Line1</label></td>
						<td colspan="5"><s:input id="txtAddress1" path="strAddress1" 
									cssClass="longTextBox" type="text"  style="width: 34%" ></s:input></td>
		</tr>
		<tr>
						<td width="120px"><label>Address Line2</label></td>
						<td colspan="2"><s:input id="txtAddress2" path="strAddress2" 
									cssClass="longTextBox" type="text" style="width: 83%" ></s:input></td>
									
						<td width="120px"><label>Address Line3</label></td>
						<td colspan="2"><s:input id="txtAddress3" path="strAddress3" 
									cssClass="longTextBox" type="text"></s:input></td>					
		
		</tr>
		
		<tr>
						<td ><label>Landmark</label></td>
						<td><s:input id="txtLandMark" path="strLandmark"
									cssClass="longTextBox" type="text" style="width: 82%" ></s:input></td>
									
						<td ><label>Area Code</label></td>
							<td ><s:input id="txtAreaCode"
									ondblclick="funHelp('WCComAreaMaster')" cssClass="searchTextBox" 
									type="text" path="strAreaCode" ></s:input></td>
									
						<td colspan="3"><s:input id="txtAreaName" path=""
									cssClass="longTextBox" type="text"></s:input></td>
		</tr>
		<tr>
						<td ><label>City Code</label></td>
							<td ><s:input id="txtCtCode"
									ondblclick="funHelp('WCComCityMaster')" cssClass="searchTextBox" 
									type="text" path="strCityCode" ></s:input></td>
									
						<td><s:input id="txtCityName" path="" 
									cssClass="longTextBox" type="text" style="width: 82%"></s:input></td>
									
						<td ><label>State Code</label></td>
							<td ><s:input id="txtStateCode"
									ondblclick="funHelp('WCComStateMaster')" cssClass="searchTextBox" 
									type="text" path="strStateCode" ></s:input></td>
									
						<td ><s:input id="txtStateName" path="" 
									cssClass="longTextBox" type="text" style="width: 92%" ></s:input></td>		
		</tr>
		<tr>
						<td><label>Region Code</label></td>
							<td><s:input id="txtRegionCode"
									ondblclick="funHelp('WCComRegionMaster')" cssClass="searchTextBox" 
									type="text" path="strRegionCode" ></s:input></td>
									
						<td><s:input id="txtRegionName" path="" 
									cssClass="longTextBox" type="text" style="width: 82%" ></s:input></td>		
									
						<td><label>Country Code</label></td>
						<td><s:input id="txtCountryCode" 
									ondblclick="funHelp('WCComCountryMaster')"  cssClass="searchTextBox" required="required"
									type="text" path="strCountryCode"  ></s:input></td>
									
						<td ><s:input id="txtCountryName" path="" 
									cssClass="longTextBox" type="text" style="width: 92%" ></s:input></td>	
		</tr>
		<tr>
						<td ><label>PinCode</label></td>
						<td colspan="2"><s:input id="txtPinCode" path="strPin" 
									class="decimal-places numberField" type="text" style="width: 45%"></s:input></td>
									
						<td ><label>Telephone</label></td>
						<td ><s:input id="txtTelePhone1" path="strTelephone1" 
									class="decimal-places numberField" type="text"></s:input></td>
						<td ><s:input id="txtTelePhone2" path="strTelephone2" 
									class="decimal-places numberField" type="text"></s:input></td>
								
		</tr>
		
		<tr>
						<td ><label>Fax</label></td>
						<td><s:input id="txtFax1" path="strFax1" 
									class="decimal-places numberField" type="text"></s:input></td>
						
						<td colspan="0"><s:input id="txtFax2" path="strFax2" 
									class="decimal-places numberField" type="text"></s:input></td>
	</tr>
		
		
		</table>
					

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
