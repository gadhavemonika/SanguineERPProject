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

	$(function() 
	{
	});

	function funSetData(code){

		switch(fieldName){

			case 'CorporateCode' : 
				funSetCorporateCode(code);
				break;
		}
	}


	function funSetCorporateCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadCorporateCode.html?corpcode=" + code,
			dataType : "json",
			success: function(response)
	        {
				
	        	if(response.strCorporateCode=='Invalid Code')
	        	{
	        		alert("Invalid Agent Code");
	        		$("#txtCorporateCode").val('');
	        	}
	        	else
	        	{					        	    	        		
	        		$("#txtCorporateCode").val(response.strCorporateCode);
	        		$("#txtCorporateDesc").val(response.strCorporateDesc);
	        	    $("#txtPersonIncharge").val(response.strPersonIncharge);
	        		$("#txtAddress").val(response.strAddress);
	        		$("#txtCity").val(response.strCity);
	        		$("#txtState").val(response.strState);
	        		$("#txtCountry").val(response.strCountry);
	        		$("#txtMobileNo").val(response.lngMobileNo);
	        		$("#txtTelephoneNo").val(response.lngTelephoneNo);
	        		$("#txtFax").val(response.lngFax);
	        		$("#txtArea").val(response.strArea);
	        		$("#txtPinCode").val(response.intPinCode);
	        		$("#txtSegmentCode").val(response.strSegmentCode);
	        		$("#txtPlanCode").val(response.strPlanCode);
	        		$("#txtRemarks").val(response.strRemarks);
	        		$("#txtAgentType").val(response.strAgentType);
	        		$("#txtCreditLimit").val(response.dblCreditLimit);
	        		
	        		
	        		if(response.strBlackList=='Y')
			    	{
			    		document.getElementById("chkBlackList").checked=true;
			    	}
			    	else
			    	{
			    		document.getElementById("chkBlackList").checked=false;
			    	}
			    	
			    	if(response.strCreditAllowed=='Y')
			    	{
			    		document.getElementById("chkCreditAllowed").checked=true;
			    	}
			    	else
			    	{
			    		document.getElementById("chkCreditAllowed").checked=false;
			    	}
			    	
	        	}
			},
			error: function(jqXHR, exception) 
			{
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



	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		//1window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
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
	
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>CorporateMaster</label>
	</div>

<br/>
<br/>

	<s:form name="CorporateMaster" method="POST" action="saveCorporateMaster.html">

		<table class="masterTable">
			<tr>
				<td>
					<label>CorporateCode</label>
				</td>
				<td  >
					<s:input colspan="3"  type="text" id="txtCorporateCode" path="strCorporateCode" cssClass="searchTextBox" ondblclick="funHelp('CorporateCode');"/>
				    <s:input  type="text" id="txtCorporateDesc" path="strCorporateDesc" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>PersonIncharge</label>
				</td>
				
				<td>
					<s:input colspan="3"  type="text" id="txtPersonIncharge" path="strPersonIncharge" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Discount Percentage</label>
				</td>
				<td>
					<s:input colspan="3"  type="text" id="txtDiscountPer" path="dblDiscountPer" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Address</label>
				</td>
				<td>
					<s:input colspan="3"  type="text" id="txtAddress" path="strAddress" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>City</label>
				</td>
				<td> 
					<s:select colspan="3"  id="txtCity" path="strCity"  items="${cityArrLsit}"  cssClass="BoxW124px">
					
				   </s:select>
				</td>
			</tr>
			<tr>
				<td>
					<label>State</label>
				</td>
				<td>
					<s:select colspan="3"  id="txtState" path="strState"  items="${stateArrLsit}" cssClass="BoxW124px">
					
				    </s:select>
				    
				</td>
			</tr>
			<tr>
				<td>
					<label>Country</label>
				</td>
				<td>
					<s:select colspan="3"  id="txtCountry" path="strCountry"  items="${countryArrLsit}"  cssClass="BoxW124px">
					
					  </s:select>
				</td>
			</tr>
			<tr>
				<td>
					<label>MobileNo</label>
				</td>
				<td>
					<s:input colspan="3" type="text" class="numeric" id="txtMobileNo" path="lngMobileNo" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>TelephoneNo</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtTelephoneNo" path="lngTelephoneNo" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Fax</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtFax" path="lngFax" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Area</label>
				</td>
				<td>

					<s:select id="txtArea" path="strArea"  cssClass="BoxW124px">
				 <s:option value="Select">Select</s:option></s:select>
				</td>
			</tr>
			<tr>
				<td>
					<label>PinCode</label>
				</td>
				<td>
					<s:input colspan="3" type="text" class="numeric" id="txtPinCode" path="intPinCode" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>SegmentCode</label>
				</td>
				<td>
					<s:select id="txtSegmentCode" path="strSegmentCode"  cssClass="BoxW124px">
					<s:option value="Select">Select</s:option>
					</s:select>
				</td>
			</tr>
			<tr>
				<td>
					<label>PlanCode</label>
				</td>
				<td>
					<s:select id="txtPlanCode" path="strPlanCode"   cssClass="BoxW124px">
					<s:option value="Select">Select</s:option>
					<s:option value="American Plan">American Plan</s:option>
					<s:option value="Continental Plan">Continental Plan</s:option>
					<s:option value="Modified American Plan">Modified American Plan</s:option>
					<s:option value="Not Applicable">Not Applicable</s:option>
					</s:select>
				</td>
			</tr>
			<tr>
				<td>
					<label>Remarks</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtRemarks" path="strRemarks" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>AgentType</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtAgentType" path="strAgentType" cssClass="longTextBox" />
				</td>
			</tr>
			
			<tr>
				<td>
					<label>CreditLimit</label>
				</td>
				<td>
					<s:input type="number" step="0.01" id="txtCreditLimit" path="dblCreditLimit" cssClass="longTextBox" />
				</td>
			
			</tr>
			<tr>
<!-- 				<td> -->
<!-- 					<label>CreditAllowed</label> -->
<!-- 				</td> -->
				

				<td><s:checkbox  label="CreditAllowed" colspan="3" id="chkCreditAllowed" path="strCreditAllowed"  value="" /></td>
				<td><s:checkbox label="Black List this corporate"  id="chkBlackList" path="strBlackList" value="" /></td>
				<td colspan="1"></td>
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
