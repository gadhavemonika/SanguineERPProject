<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
var fieldName;

/**
* Open Help
**/
function funHelp(transactionName)
{
	fieldName=transactionName;
	window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
    //window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
}

$(function() 
		{
			var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
			
			$("#txtDOB").datepicker('setDate', pmsDate);
			var d = new Date();
			var year = d.getFullYear() - 0;
			var date=pmsDate.split
			var dateArr = pmsDate.split('-');
			d.setFullYear(year);
			$('#txtDOB').datepicker({ changeYear: true, changeMonth: true, yearRange: '1920:' + year + '',  maxDate: 0 ,defaultDate: d,dateFormat: 'dd-mm-yy'});
			//$("#txtDOB").datepicker({ dateFormat: 'dd-mm-yy' });
			
			
					
			$("#txtPassportExpiryDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtPassportExpiryDate").datepicker('setDate', pmsDate);
			
			$("#txtPassportIssueDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtPassportIssueDate").datepicker('setDate', pmsDate);
			
			$("#txtAnniversaryDte").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtAnniversaryDte").datepicker('setDate', pmsDate);
			
		});

function funSetData(code)
{

		switch(fieldName)
		{
		   case 'guestCode' : 
			   funSetGuestCode(code);
				break;
			
		}
}



/**
*   Attached document Link
**/
$(function()
{

	$('a#baseUrl').click(function() 
	{
		if($("#txtGuestCode").val().trim()=="")
		{
			alert("Please Select Guest Code");
			return false;
		}
	   window.open('attachDoc.html?transName=frmGuestMaster.jsp&formName=Guest Master&code='+$('#txtGuestCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
	});

});

function funSetGuestCode(code)
{
	var searchurl=getContextPath()+"/loadGuestCode.html?guestCode="+code;
	 $.ajax({
	        type: "GET",
	        url: searchurl,
	        dataType: "json",
	        success: function(response)
	        {
	        	if(response.strGuestCode=='Invalid Code')
	        	{
	        		alert("Invalid Walikn No");
	        		$("#txtGuestCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtGuestCode").val(response.strGuestCode);
		        	$("#cmbGuestPrefix").val(response.strGuestPrefix);
		        	$("#txtFirstName").val(response.strFirstName);
		        	$("#txtMiddleName").val(response.strMiddleName);
		        	$("#txtLastName").val(response.strLastName);
		        	$("#cmbGender").val(response.strGender);
		        	$("#txtDOB").val(response.dteDOB);
		        	$("#txtDesignation").val(response.strDesignation);
		        	$("#txtAddress").val(response.strAddress);
		        	$("#cmbCity").val(response.strCity);
		        	$("#cmbState").val(response.strState);
		        	$("#cmbCountry").val(response.strCountry);
		        	$("#txtNationality").val(response.strNationality);
		        	$("#txtPinCode").val(response.intPinCode);
		        	$("#txtMobileNo").val(response.lngMobileNo);
		        	$("#txtFaxNo").val(response.lngFaxNo);
		        	$("#txtEmailId").val(response.strEmailId);
		        	$("#txtPANNo").val(response.strPANNo);
		        	$("#txtArrivalFrom").val(response.strArrivalFrom);
		        	$("#txtProceedingTo").val(response.strProceedingTo);
		        	$("#txtStatus").val(response.strStatus);
		        	$("#txtVisitingType").val(response.strVisitingType);
		        	$("#txtPassportNo").val(response.strPassportNo);
		        	$("#txtPassportIssueDate").val(response.dtePassportIssueDate);
		        	$("#txtPassportExpiryDate").val(response.dtePassportExpiryDate);
		        	$("#txtGSTNo").val(response.strGSTNo);
		        	$("#txtAnniversaryDte").val(response.dteAnniversaryDate);
		        	$("#txtUIDNo").val(response.strUIDNo);
		        
		        	$("#cmbDefaultAddr").val(response.strDefaultAddr);
		        	
		        	$("#txtAddressLocal").val(response.strAddressLocal);
		        	$("#cmbCityLocal").val(response.strCityLocal);
		        	$("#cmbStateLocal").val(response.strStateLocal);
		        	$("#cmbCountryLocal").val(response.strCountryLocal);
		        	$("#txtPinCodeLocal").val(response.intPinCodeLocal);
		        	
		        	$("#txtAddressPermanent").val(response.strAddrPermanent);
		        	$("#cmbCityPermanent").val(response.strCityPermanent);
		        	$("#cmbStatePermanent").val(response.strStatePermanent);
		        	$("#cmbCountryPermanent").val(response.strCountryPermanent);
		        	$("#txtPinCodePermanent").val(response.intPinCodePermanent);
		        	
		        	$("#txtAddressOfc").val(response.strAddressOfc);
		        	$("#cmbCityOfc").val(response.strCityOfc);
		        	$("#cmbStateOfc").val(response.strStateOfc);
		        	$("#cmbCountryOfc").val(response.strCountryOfc);
		        	$("#txtPinCodeOfc").val(response.intPinCodeOfc);
		        	
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

	function  funCallFormAction(actionName,object)
	{
		var flg=true;
		
		if($('#txtFirstName').val().trim().length==0)
		{
			alert("Guest name should be empty!!");
			flg=false;
		}
		else
		{
			var mobileNo =  $("#txtMobileNo").val();
			if(mobileNo=="0")
				{
				  alert("Zero is not Valid Mobile Number");
				  flg= false;
				}else if(mobileNo!="0")
				{
					if($("#txtGuestCode").val()==""){
						
						var mobilecount = funGetGuestMobileNo(mobileNo)
					    if(mobilecount>0)
						   {
						   	 alert("Mobile Number Already Exist for Another Guest");
						   	 flg=false;
						   }
					}	
					
					var pattern = /^[\s()+-]*([0-9][\s()+-]*){6,20}$/;
					if (pattern.test(mobileNo)) 
					{
						flg=true;
						
						 var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

					        if (reg.test($('#txtEmailId').val()) == false) 
					        {
					            alert('Invalid Email Address');
					            return false;
					        }
					        else
					        {
					        	if($('#txtDOB').val().trim().length==0)
					    		{
					        		 alert('DOB should not be empty!!');
							         return false;
					    		}
					        }
						if($("#txtPANNo").val()=="")
						{}
						else
						{
							var panVal = $('#txtPANNo').val();
							var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
							if(regpan.test(panVal))
							{
							   flg=true;
							}
							else // invalid pan card number
							{
								alert("Enter Valid PAN No!!");
								flg=false;
							}
						}	
					}
					else
					{
						alert("Invalid Mobile No");
						flg=false;
					}	
				}
		   	
			
			
			/*var regmob = /^\d{10}$/;
			if(regmob.test($('#txtMobileNo').val()))
			{
			   flg=true;
			}
			else // invalid pan card number
			{
				alert("Enter Valid Mobile No!!");
				flg=false;
			}
			*/
			
		  
		}	
		
		
		return flg;
	}
	
	function funGetGuestMobileNo(mobileNo)
	{
		var returnVal =0;
		var searchurl=getContextPath()+"/checkGuestMobileNo.html?mobileNo="+mobileNo;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        async:false,
			        success: function(response)
			        {
			        	returnVal = response;
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
		 return returnVal;
	}
	


</script>
</head>
<body>
	<div id="formHeading">
		<label>Guest Master</label>
	</div>
	
<s:form name="Guest" method="GET" action="saveGuestMaster.html?" >
	
	<br>
	<table class="masterTable">
		
		   <tr>
				<th align="right" colspan="6"><a id="baseUrl"
					href="#"> Attach Documents</a>&nbsp; &nbsp; &nbsp;
						&nbsp;</th>
			</tr>
			
			
			   <tr>
			           <td><label>Guest Code</label></td>
				       <td><s:input id="txtGuestCode" path="strGuestCode" cssClass="searchTextBox" ondblclick="funHelp('guestCode')" /></td>				
			           <td colspan="4"></td>
			      </tr>
			
		          <tr>
						 <td>
							  <label>GuestPrefix</label>
						</td>
						<td>
							 <s:select id="cmbGuestPrefix" path="strGuestPrefix" cssClass="BoxW124px">
				    		<s:options items="${prefix}"/>
				    	    </s:select>
					   </td>
						<td>
							<label>FirstName</label>
						</td>
						<td>
							 <s:input colspan="3" type="text" id="txtFirstName" path="strFirstName" cssClass="longTextBox" />
								
						</td>
							
						<td>
								<label>MiddleName</label>
						</td>
						<td>
							 <s:input colspan="3" type="text" id="txtMiddleName" path="strMiddleName" cssClass="longTextBox" />
							
						</td>
							
							
				</tr>
				
				
				<tr>
					    <td>
					       <label>LastName</label>
						</td>
						<td>
					       <s:input colspan="3" type="text" id="txtLastName" path="strLastName" cssClass="longTextBox" />
						</td>
					
						 <td>
							 <label>Gender</label>
					     </td>
						  <td>
							<s:select id="cmbGender" path="strGender" cssClass="BoxW124px">
				    		<s:options items="${gender}"/>
				    	     </s:select>
						  </td>
							<td>
								<label>DOB</label>
							</td>
							<td>
							        <s:input colspan="3" type="text" id="txtDOB" path="dteDOB" cssClass="calenderTextBox" />
							</td>
						
				</tr>
				<tr>
					<td>
						<label>MobileNo</label>
					</td>
					<td>
					      <s:input colspan="3" type="text" id="txtMobileNo"  path="intMobileNo" cssClass="longTextBox" onblur="fun1(this);" />
					</td>
					<td colspan="2">
						<label>EmailId</label>&nbsp; &nbsp; &nbsp;
					   <s:input type="email" placeholder="Enter a valid email address" id="txtEmailId" path="strEmailId" cssClass="longTextBox" />
					</td>
					<td>
					   <label>PANNo</label>
					</td>
					<td>
					   <s:input colspan="3" type="text" id="txtPANNo" path="strPANNo" cssClass="longTextBox" />
					</td>
				</tr>
				<tr>
						<td>
							<label>PassportNo</label>
						</td>
						<td>
						    <s:input colspan="3" type="text" id="txtPassportNo" path="strPassportNo" cssClass="longTextBox" />
						</td>
						<td>
							<label>PassportIssueDate</label>
						</td>
						<td>
							 <s:input colspan="3" type="text" id="txtPassportIssueDate" path="dtePassportIssueDate" cssClass="calenderTextBox" />
						</td>
						<td>
							<label>PassportExpiryDate</label>
						</td>
						<td>
						      <s:input colspan="3" type="text" id="txtPassportExpiryDate" path="dtePassportExpiryDate" cssClass="calenderTextBox" />
						</td>
				</tr>
				<tr>		
					<td><label>Nationality</label></td>
					<td>
					   <s:input colspan="3" type="text" id="txtNationality" path="strNationality" cssClass="longTextBox" />
					</td>
					<td><label>ArrivalFrom</label></td>
					<td>
					    <s:input colspan="3" type="text" id="txtArrivalFrom" path="strArrivalFrom" cssClass="longTextBox" />
					</td>
					<td><label>ProceedingTo</label></td>
					<td><s:input colspan="3" type="text" id="txtProceedingTo" path="strProceedingTo" cssClass="longTextBox" />
					</td>
				</tr>
				<tr>
				<td><label>Status</label></td>
					<td><s:input colspan="3" type="text" id="txtStatus" path="strStatus" cssClass="longTextBox" />
					</td>
				<td><label>VisitingType</label></td>
				<td>
			      <s:input colspan="3" type="text" id="txtVisitingType" path="strVisitingType" cssClass="longTextBox" />
				</td>
				<td colspan="2"></td>
				</tr>
					
				<tr>
						<td>
							<label>GST No.</label>
						</td>
						<td>
						      <s:input colspan="3" type="text" id="txtGSTNo" path="strGSTNo" cssClass="longTextBox" />
						</td>
						<td>
							<label>Anniversary Date</label>
						</td>
						<td>
						    <s:input colspan="3" type="text" id="txtAnniversaryDte" path="dteAnniversaryDate" cssClass="calenderTextBox" />
						</td>
						<td>
							<label>UID No.</label>
						</td>
						<td>
						    <s:input colspan="3" type="text" id="txtUIDNo" path="strUIDNo" cssClass="longTextBox" />
							
						</td>
				</tr>					
				<tr>
					<td><label>Default Address</label></td>
					<td>
						<s:select id="cmbDefaultAddr" path="strDefaultAddr" cssClass="BoxW124px">
			    		<s:option value="Local">Local</s:option><s:options/>
			    		<s:option value="Permanent">Permanent</s:option><s:options/>
			    		<s:option value="Office">Office</s:option><s:options/>
			    	     </s:select>
					  </td>
					<td colspan="6"></td>
				</tr>
				<tr>
				<td>
					<div style="background: #6FB9F6;text-align: left;border-bottom-right-radius: 0.5em;
					width: 100px;PADDING-RIGHT: 5px;PADDING-LEFT: 5px;FONT-WEIGHT: bold;FONT-SIZE: 13px;
					PADDING-BOTTOM: 5px;COLOR: #ffffff;PADDING-TOP: 5px;FONT-FAMILY: trebuchet ms, Helvetica, sans-serif;">
						<label>Local Address</label>
					</div>
				</td>
				<td colspan="5"></td>
				</tr>
				<tr style="height: 25px">
						<td>
							<label>Address</label>
						</td>
						<td colspan ="2"><s:textarea id="txtAddressLocal" path="strAddressLocal" 
						 cssStyle="width:75%;height:35px;border:1px solid;
						 background-color:inherit;padding-left:01px;
						 text-transform: uppercase;" /></td>
					     <td>
						   <label>City</label>
						<s:select id="cmbCityLocal" path="strCityLocal" cssClass="BoxW124px">
			    			<s:options items="${listCity}"/>
			    		</s:select>
						 	
						</td>
						<td>
						<label>State</label>
						 <s:select id="cmbStateLocal" path="strStateLocal" cssClass="BoxW124px">
			    			<s:options items="${listState}"/>
			    		</s:select>
			    		<td>
							<label>Country</label>
						 <s:select id="cmbCountryLocal" path="strCountryLocal" cssClass="BoxW124px">
			    			<s:options items="${listCountry}"/>
			    		</s:select>
						
						</td>
				</tr>
				<tr>
				 	 <td>
						   <label>PinCode</label>
					</td>
					<td>
					<s:input  type="text" id="txtPinCodeLocal" path="intPinCodeLocal" cssClass="longTextBox" />
					</td>
					<td colspan="4" ></td>
			  </tr>
				<tr>
				<td>
					<div style="background: #6FB9F6;text-align: left;border-bottom-right-radius: 0.5em;
					width: 100px;PADDING-RIGHT: 5px;PADDING-LEFT: 5px;FONT-WEIGHT: bold;FONT-SIZE: 13px;
					PADDING-BOTTOM: 5px;COLOR: #ffffff;PADDING-TOP: 5px;FONT-FAMILY: trebuchet ms, Helvetica, sans-serif;">
						<label>Permanent Address</label>
					</div>
				</td>
				<td colspan="5"></td>
				</tr>
				<tr style="height: 25px">
						<td>
							<label>Address</label>
						</td>
						<td colspan ="2"><s:textarea id="txtAddressPermanent" path="strAddrPermanent" 
						 cssStyle="width:75%;height:35px;border:1px solid;
						 background-color:inherit;padding-left:01px;
						 text-transform: uppercase;" /></td>
					     <td>
						   <label>City</label>
						<s:select id="cmbCityPermanent" path="strCityPermanent" cssClass="BoxW124px">
			    			<s:options items="${listCity}"/>
			    		</s:select>
						 	
						</td>
						<td>
						<label>State</label>
						 <s:select id="cmbStatePermanent" path="strStatePermanent" cssClass="BoxW124px">
			    			<s:options items="${listState}"/>
			    		</s:select>
			    		<td>
							<label>Country</label>
						 <s:select id="cmbCountryPermanent" path="strCountryPermanent" cssClass="BoxW124px">
			    			<s:options items="${listCountry}"/>
			    		</s:select>
						
						</td>
				</tr>
				<tr>
				 	 <td>
						   <label>PinCode</label>
					</td>
					<td>
					<s:input  type="text" id="txtPinCodePermanent" path="intPinCodePermanent" cssClass="longTextBox" />
					</td>
					<td colspan="4" ></td>
			  </tr>
				  <tr>
				<td >
					<div style="background: #6FB9F6;text-align: left;border-bottom-right-radius: 0.5em;
					width: 100px;PADDING-RIGHT: 5px;PADDING-LEFT: 5px;FONT-WEIGHT: bold;FONT-SIZE: 13px;
					PADDING-BOTTOM: 5px;COLOR: #ffffff;PADDING-TOP: 5px;FONT-FAMILY: trebuchet ms, Helvetica, sans-serif;">
						<label>Office Address</label>
					</div>
				</td>
				<td colspan="5"></td>
				</tr>
				<tr style="height: 25px">
						<td>
							<label>Address</label>
						</td>
						<td colspan ="2"><s:textarea id="txtAddressOfc" path="strAddressOfc" 
						 cssStyle="width:75%;height:35px;border:1px solid;
						 background-color:inherit;padding-left:01px;
						 text-transform: uppercase;" /></td>
					     <td>
						   <label>City</label>
						<s:select id="cmbCityOfc" path="strCityOfc" cssClass="BoxW124px">
			    			<s:options items="${listCity}"/>
			    		</s:select>
						 	
						</td>
						<td>
						<label>State</label>
						 <s:select id="cmbStateOfc" path="strStateOfc" cssClass="BoxW124px">
			    			<s:options items="${listState}"/>
			    		</s:select>
			    		<td>
							<label>Country</label>
						 <s:select id="cmbCountryOfc" path="strCountryOfc" cssClass="BoxW124px">
			    			<s:options items="${listCountry}"/>
			    		</s:select>
						
						</td>
				</tr>
				  <tr>
				 	 <td>
					   <label>PinCode</label>
					</td>
					<td>
					<s:input colspan="3" type="text" id="txtPinCodeOfc" path="intPinCodeOfc" cssClass="longTextBox" />
					</td>
					<td colspan="4" ></td>
				  </tr>
				  <tr>
				  <td colspan="6"></td>
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