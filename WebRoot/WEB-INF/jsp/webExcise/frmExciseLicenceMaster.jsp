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

	function funSetData(code)
	{
		switch (fieldName) 
		{
		   case 'LicenceCode':
		    	funSetLicenceData(code);
		        break;
				   
		   case 'CityCode':
			   funSetCity(code);
			   break;
			   
		   case 'exciseproperty':
			   funSetPropertyData(code);
		        break;	   
		}
	}
	
	function funSetPropertyData(code)
	{
		
		$("#txtPropertyCode").val(code);
		$.ajax({
				type : "GET",
				url : getContextPath()+ "/loadPropertyMasterData.html?Code=" + code,
				dataType : "json",
				success : function(resp) 
				{
					$("#txtPropertyCode").val(resp.propertyCode);
// 					$("#lblPropertyName").text(resp.propertyName);
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
	
	function funSetCity(CityCode)
	{
		$("#txtPINCode").focus();			
		var gurl=getContextPath()+"/loadExciseCityMasterData.html?cityCode=";
		
		$.ajax({
	        type: "GET",				        
	        url: gurl+CityCode,
	        dataType: "json",
	        success: function(response)
	        {	
	        	if(response.StrCityCode=='Invalid City Code')
	        	{
	        		alert("Invalid City Code Please Select Again");
	        		$("#txtCityCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtCity").val(response.strCityCode);
	        		$("#txtcityName").html(response.strCityName);
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
	
	function funSetLicenceData(code)
	{
		var propCode=$("#txtPropertyCode").val;
		$("#txtLicenceNo").focus();
		var gurl=getContextPath()+"/loadExciseLicenceMasterData.html?licenceCode="+code+"&propertyCode="+propCode;
		$.ajax({
	        type: "GET",				        
// 	        url: gurl+code,
			url: gurl,
	        dataType: "json",
	        success: function(response)
	        {	
	        	if(response.strLicenceNo=='Invalid Code')
	        	{
	        		alert("Invalid Licence Code Please Select Again");
	        		$("#txtLicenceCode").val('');
	        		$("#txtLicenceName").focus();
	        	}
	        	else
	        	{
	        		$("#txtLicenceCode").val(response.strLicenceCode);
	        		$("#txtLicenceNo").val(response.strLicenceNo);
	        		$("#txtLicenceName").val(response.strLicenceName);
	        		$("#txtVATNo").val(response.strVATNo);
	        		$("#txtTINNo").val(response.strTINNo);
	        		$("#txtAddress1").val(response.strAddress1);
	        		$("#txtAddress2").val(response.strAddress2);
	        		$("#txtAddress3").val(response.strAddress3);
	        		$("#txtExternalCode").val(response.strExternalCode);
	        		$("#txtCity").val(response.strCity);
	        		$("#txtPINCode").val(response.strPINCode);
	        		$("#txtTelephoneNo").val(response.longTelephoneNo);
	        		$("#txtMobileNo").val(response.longMobileNo);
	        		$("#txtEmailId").val(response.strEmailId);
	        		$("#txtContactPerson").val(response.strContactPerson);
	        		$("#txtBusinessCode").val(response.strBusinessCode);	       
	        		$("#txtPropertyCode").val(response.strPropertyCode);
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


function funApplyNumberValidation(){
			$(".numeric").numeric();
			$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
			$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
			$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
		    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
		}
		
		$(function(){
			
			$("#txtLicenceNo").focus();
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
				alert("Licence Data Save successfully\n\n"+message);
			<%
			}}%>
			
			$('#txtLicenceCode').blur(function () 
					{
					 var code=$('#txtLicenceCode').val();
					 if (code.trim().length > 0 && code !="?" && code !="/"){							   
						 funSetLicenceData(code);
					   }
					});
			
			$('#txtCity').blur(function () 
					{
					 var code=$('#txtCity').val();
					 if (code.trim().length > 0 && code !="?" && code !="/"){							   
						 funSetCity(code);
					   }
					});
		});
		
		
// 		 $(document).ready(function()
// 		    		{	
			 
// 			          funUserDetail();
			 
// 		    		});
		
// 		 funUserDetail(){
			 
// 				$.ajax({
// 					type : "GET",
// 					url : getContextPath()+ "/loadUserDetail.html" ,
// 					dataType : "json",
				
// 					success: function(response)
// 			        {
						
// 						if (response == '' || response == undefined ) {
// 							alert("Data Not Found");
// 						} else {	
							
						
// 			        		funFillUserGrid(response);
			        		
// 			        	}
// 					},
// 					error: function(jqXHR, exception) 
// 					{
// 			            if (jqXHR.status === 0) {
// 			                alert('Not connect.n Verify Network.');
// 			            } else if (jqXHR.status == 404) {
// 			                alert('Requested page not found. [404]');
// 			            } else if (jqXHR.status == 500) {
// 			                alert('Internal Server Error [500].');
// 			            } else if (exception === 'parsererror') {
// 			                alert('Requested JSON parse failed.');
// 			            } else if (exception === 'timeout') {
// 			                alert('Time out error.');
// 			            } else if (exception === 'abort') {
// 			                alert('Ajax request aborted.');
// 			            } else {
// 			                alert('Uncaught Error.n' + jqXHR.responseText);
// 			            }		            
// 			        }			
// 				});
// 			}

		 
// 		
// 		 funFillUserGrid(data)
// 		 {
			 
			
			 
// 		 }
		  
			 
		 
		
		

</script>

</head>
<body>
	<div id="formHeading">
	<label>Licence Master</label>
	</div>

<br/>
<br/>

	<s:form name="LicenceMaster" method="POST" action="saveExciseLicenceMaster.html?saddr=${urlHits}" >
		<table class="masterTable">
			<tr>
				<td width="100px">
					<label>Licence Code</label>
				</td>
				<td width="150px">
					<s:input type="text" id="txtLicenceCode" path="strLicenceCode" cssClass="searchTextBox" ondblclick="funHelp('LicenceCode')"/>
				</td>
				<td width="100px">
					<label>Licence No</label>
				</td>
				<td width="150px">
					<s:input type="text"  id="txtLicenceNo" path="strLicenceNo" cssClass="BoxW124px" required="true" />
				</td>
				
				<td>
					<label>Licence Name</label>
				</td>
				<td colspan="2">
					<s:input type="text" id="txtLicenceName" path="strLicenceName" cssClass="longTextBox"   cssStyle="width: 60%;" />
				</td>
			</tr>
			<tr>
			<td>
					<label>Property Code</label>
				</td>
				<td>
					<s:input type="text" id="txtPropertyCode"   path="strPropertyCode" cssClass="searchTextBox"  ondblclick="funHelp('exciseproperty')" />
				</td>
				<td>
					<label>Address 1</label>
				</td>
				<td colspan="3">
					<s:input type="text" id="txtAddress1" path="strAddress1" cssClass="longTextBox"  cssStyle="width: 75%;" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>Address 2</label>
				</td>
				<td colspan="3">
					<s:input type="text" id="txtAddress2" path="strAddress2" cssClass="longTextBox"  cssStyle="width: 60%;" />
				</td>
				<td>
					<label>Address 3</label>
				</td>
				<td colspan="2">
					<s:input type="text" id="txtAddress3" path="strAddress3" cssClass="longTextBox"  cssStyle="width: 60%;" />
				</td>
			</tr>
			<tr>
				<td>
			    	<label>External Code</label>
			    </td>
						    
			    <td>
			    	<s:input id="txtExternalCode" name="txtExternalCode" path="strExternalCode"  cssClass="BoxW116px"/>
			    </td>
			    
				<td width="100px">
					<label>Telephone No</label>
				</td>
				<td width="150px">
					<s:input type="text" id="txtTelephoneNo" path="longTelephoneNo" cssClass="BoxW124px numeric" maxlength="11" />
				</td>
				<td width="100px">
					<label>Email Id</label>
				</td>
				<td width="150px">
					<s:input type="email" id="txtEmailId" path="strEmailId" cssClass="BoxW124px" required="true" pattern='\S+@\S+\.\S+' />
				</td>
				<td></td>
			</tr>
			<tr>
				<td width="100px">
					<label>Business Code</label>
				</td>
				<td width="150px">
					<s:input type="text" id="txtBusinessCode" path="strBusinessCode" cssClass="BoxW124px" />
				</td>
		
				<td width="100px">
					<label>VAT No</label>
				</td>
				<td width="150px">
					<s:input type="text" id="txtVATNo" path="strVATNo" cssClass="BoxW124px" />
				</td>
				<td  width="100px">
					<label>TIN No</label>
				</td>
				<td width="150px">
					<s:input type="text" id="txtTINNo" path="strTINNo" cssClass="BoxW124px" />
				</td> 
				<td></td>
			</tr>
				
			<tr>
				<td width="100px">
					<label>City</label>
				</td>
				<td width="150px">
					<s:input type="text" id="txtCity" path="strCity" required="true" readonly="true" cssClass="searchTextBox" ondblclick="funHelp('CityCode')" />
				</td>
				<td><label id="txtcityName"></label></td>
			
				<td width="100px">
					<label>PIN Code</label>
				</td>
				<td width="150px">
					<s:input type="text" id="txtPINCode" path="strPINCode" cssClass="BoxW124px numeric positive-integer" maxlength="6" pattern="\d{6}-?(\d{4})?$" />
				</td>
			
				<td width="100px">
					<label>Mobile No</label>
				</td>
				<td width="150px">
					<s:input type="text" id="txtMobileNo" path="longMobileNo" cssClass="BoxW124px numeric positive-integer" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="100px">
					<label>Contact Person</label>
				</td>
				<td width="150px">
					<s:input type="text" id="txtContactPerson" path="strContactPerson" cssClass="BoxW124px" />
				</td>
				<td colspan="5"></td>
			</tr>
		</table>

 		<div
			style="width: 100%; overflow-x: auto !important; overflow-y: auto !important;">
			<table id="tblExciseUserDetail" class="transTablex"
				style="width: 100%; text-align: center !important;">
			</table>
		</div>
		<br/>
		<br/>
		<p align="center">
			<input type="submit" value="Submit" class="form_button" />
			<input type="reset" value="Reset" class="form_button" />
		</p>

	</s:form>
	<script type="text/javascript">
  	funApplyNumberValidation();
	</script>
</body>
</html>
