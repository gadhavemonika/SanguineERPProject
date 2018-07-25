<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
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
<script>
var homeDeliveryType="";
$(document).ready(function() {
	
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
			%>alert("Data Saved \n\n"+message);
			funPreviousForm();
			<%}
		
	}%>
	
		
			$('input#txtTaxDesc').mlKeyboard({layout: 'en_US'});
		  	$('input#txtTaxShortName').mlKeyboard({layout: 'en_US'});
		 	$('input#txtAmount').mlKeyboard({layout: 'en_US'});
		  	$('input#txtPercent').mlKeyboard({layout: 'en_US'});
		

		$(".tab_content").hide();
		$(".tab_content:first").show();

		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();

			var activeTab = $(this).attr("data-state");
			$("#" + activeTab).fadeIn();
		});
		
		$("form").submit(function(event){
			if(homeDeliveryType=="Temp")
				{
				 if($("#txtTempAddress").val().trim()=="")
					{
						alert("Please Enter Temp Address");
						return false;
					}
				}
			});
	});

function funPreviousForm() {
	window.opener.funCall();
	window.close();
}
	function funBtnClicked(objBtn)
	{
		homeDeliveryType=objBtn.id;
	}
</script>
</head>

<body >
	<div id="formHeading">
		<label>Tax Master</label>
	</div>
	<s:form name="POSForm" method="POST" action="updateHomeDeliveryAddress.html">

		<br />
		<br />
	
		<table  cellpadding="0" cellspacing="3" class="table table-striped table-bordered table-hover" Style=" margin-left:150px; width: 85%; height: 130%;">
									 <tr><td><input type="button" id="Home" value="Home" style="width: 100px;height: 35px; " onclick="funBtnClicked(this)"/></td>
									 <td><input type="button" id="Office" value="Office" style="width: 100px;height: 35px; " onclick="funBtnClicked(this)"/></td>
									 <td><input type="button" id="Temp" value="Temp" style="width: 100px;height: 35px; " onclick="funBtnClicked(this)"/></td>
									 <td><input type="Submit" id="Ok" value="OK" style="width: 100px;height: 35px; "/></td>
									 </tr>	 
									</table>
							<table class="masterTable" Style="width: 85%; height: 130%; ">
								<tr>
								<td>
					<label>Customer No</label>
				</td>
				<td >
					<s:input  type="text"  id="txtTableName" path="strHomeMobileNo" cssStyle="text-transform: uppercase; " cssClass="longTextBox jQKeyboard form-control" />
					</td>
				<td>
					<label>Customer Name</label>
				</td>
				<td>
					<s:input  type="text"  id="txtTableName" path="strHomeCustomerName" cssStyle="text-transform: uppercase; " cssClass="longTextBox jQKeyboard form-control" /> 
				
				</td>
								</tr>
						</table>
		<table
				style="border: 0px solid black; width: 85%; height: 130%;  margin-left: auto; margin-right: auto;background-color:#C0E4FF;">
				
				<tr>
					<td>
						<div id="tab_container" >
							<ul class="tabs">
								<li class="active" data-state="tab1">Home Address</li>
								<li data-state="tab2">Office Address</li>
								<li data-state="tab3">Temporary Address </li>
								
				
							</ul>
							<br /> <br />

							<!--  Start of Generals tab-->

							<div id="tab1" class="tab_content">
								<table  class="masterTable">
					
			<tr>
			<td><label>Address/Flat No.</label></td>
				<td colspan="3"><s:input type="text" id="txtTaxDesc" 
						name="txtTaxDesc" path="strHomeAddress" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /></td>
						
				
			</tr>
		
			<tr>
			<td><label>Street Name</label></td>
				<td colspan="3"><s:input type="text" id="txtTaxShortName" 
						name="txtTaxShortName" path="strHomeStreetName" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /></td>
				
			</tr>
			<tr>
			<td><label>LandMark</label></td>
				
				<td colspan="3"><s:input  type="text" id="txtAmount" 
						name="txtAmount" path="strHomeLandmark"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
			</tr>
			
			<tr>
			
		      
		       
		       <td><label>Pin Code</label></td>
		       <td><s:input type="text" id="txtPercent" 
						name="txtPercent" path="strHomePinCode"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
		        <td ><s:input  type="text" id="txtPercent" 
						name="txtPercent" path="strHomeCity" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
		        <td ><s:input  type="text" id="txtPercent" 
						name="txtPercent" path="strHomeState" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
			</tr>
			
			</table>
			
			
							</div>
							<!--  End of  tax details1 tab-->




							<!-- Start of tax details2 tab -->

							<div id="tab2" class="tab_content">
											<table  class="masterTable">
					
			<tr>
			<td><label>Address/Flat No.</label></td>
				<td colspan="3"><s:input type="text" id="txtTaxDesc" 
						name="txtTaxDesc" path="strOfficeCustAddress"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /></td>
						
			</tr>
		
			<tr>
			<td><label>Street Name</label></td>
				<td colspan="3"><s:input type="text" id="txtTaxShortName" 
						name="txtTaxShortName" path="strOfficeStreetName" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /></td>
				
			</tr>
			<tr>
			<td><label>LandMark</label></td>
				
				<td colspan="3"><s:input type="text" id="txtAmount" 
						name="txtAmount" path="strOfficeLandmark" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
			</tr>
			
			<tr>
			
		      
		       
		       <td><label>Pin Code</label></td>
		       <td ><s:input  type="text" id="txtPercent" 
						name="txtPercent" path="strOfficePinCode" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
		        <td ><s:input  type="text" id="txtPercent" 
						name="txtPercent" path="strOfficeCity" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
		        <td ><s:input  type="text" id="txtPercent" 
						name="txtPercent" path="strOfficeState" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
			</tr>
			
			</table>							</div>
							<!-- End of tax details2 tab -->


							<!-- Start of tax details3 Tab -->

						<div id="tab3" class="tab_content">
												<table  class="masterTable">
					
			<tr>
			<td><label>Temp Address</label></td>
				<td colspan="3"><s:textarea  id="txtTempAddress" 
						path="strTempCustAddress"  style="height:100px"
						 cssClass="longTextBox"  /></td>
						
			</tr>
		
			<tr>
			<td><label>Street Name</label></td>
				<td colspan="3"><s:input  type="text" id="txtTaxShortName" 
						name="txtTaxShortName" path="strTempStreetName" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /></td>
				
			</tr>
			<tr>
			<td><label>LandMark</label></td>
				
				<td colspan="3"><s:input  type="text" id="txtAmount" 
						name="txtAmount" path="strTempLandmark" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
			</tr>
			
			<tr>
			
		      
		      
			</tr>
			
			</table>
						</div>

						<!-- End  of tax details3  Tab -->


						

					

						</div>
					</td>
				</tr>
			</table>
		<br />
		<br />
	
	</s:form>

</body>
</html>