<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

<script type="text/javascript">
	var fieldName;
	
	
	function funCheckDuplicatePricing()
	{
		var isDuplicate=false;
		
		var itemCode=$("#txtItemCode").val();
		var posCode=$("#txtPosCode").val();
		var areaCode=$("#txtAreaCode").val();
		var hourlyPricing=$("#txtHourlyPricing").prop('checked');
		
		var searchurl=getContextPath()+"/checkDuplicateItemPricing.html?itemCode="+itemCode+"&posCode="+posCode+"&areaCode="+areaCode+"&hourlyPricing="+hourlyPricing+"";
		
		 $.ajax({
		        type: "GET",
		        url: searchurl,
		        async: false,		        
		        dataType: "json",
		        success: function(response)
		        {			        	
		        		if(response)
		        		{
		        			alert("Price Already Created.");
		        			isDuplicate=false;
		        		}
		        		else
		        		{
		        			isDuplicate=true;
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
		 		
		 return isDuplicate;
	}
		
	function funOnSubmitValidation()
	{
		var submit=false;	
		
		var itemCode=$("#txtItemCode").val();
		if(itemCode=="")
		{
			submit=false;
			alert("Please Select Item Code");
		}		
		else if(fieldName=="POSMenuItemPricingMaster")
		{
			submit=true;
		}
		else if(fieldName=="POSMenuItemMaster" || $("longPricingId").val()==undefined || $("longPricingId").val()=="")
		{
			var submit2=funCheckDuplicatePricing();
			
			submit=submit2;
		}
		
		return submit;
	}
	
	$(function()
	{
		$("#txtPriceMonday").keydown(function (e)
		{
			if (e.which == 9)
			{
				var priceMonday=$("#txtPriceMonday").val();
				$("#txtPriceMonday").val(priceMonday);				
				$("#txtPriceTuesday").val(priceMonday);
				$("#txtPriceWednesday").val(priceMonday);
				$("#txtPriceThursday").val(priceMonday);
				$("#txtPriceFriday").val(priceMonday);
				$("#txtPriceSaturday").val(priceMonday);
				$("#txtPriceSunday").val(priceMonday);
			}
		});
	});
	

	//Apply Validation on Number TextFiled
	function funApplyNumberValidation() 
	{
		$(".numeric").numeric();
		$(".integer").numeric(false, function() {
			alert("Integers only");
			this.value = "";
			this.focus();
		});
		$(".positive").numeric({
			negative : false
		}, function() {
			alert("No negative values");
			this.value = "";
			this.focus();
		});
		$(".positive-integer").numeric({
			decimal : false,
			negative : false
		}, function() {
			alert("Positive integers only");
			this.value = "";
			this.focus();
		});
		$(".decimal-places").numeric({
			decimalPlaces : maxQuantityDecimalPlaceLimit,
			negative : false
		});
		$(".decimal-places-amt").numeric({
			decimalPlaces : maxAmountDecimalPlaceLimit,
			negative : false
		});
	}
	
	
	$(function() 
	{
		$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtFromDate" ).datepicker('setDate', 'today');
		$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtToDate" ).datepicker('setDate', 'today');
		
		$("#txtFromDate").datepicker();
		$("#txtToDate").datepicker();

	}); 

	$(function()
	{


		$("#txtTimeFrom").timepicker();
		$("#txtTimeTo").timepicker();
		
		$('#txtTimeFrom').timepicker();
		$('#txtTimeTo').timepicker();
		
		$('#txtTimeFrom').timepicker({
		        'timeFormat':'h:i:A'
		});
		$('#txtTimeTo').timepicker({
		        'timeFormat':'h:i:A'
		});
		
		/* $('#txtTimeFrom').timepicker('setTime', new Date());
		$('#txtTimeTo').timepicker('setTime', new Date());  */
	 
	});
	
	function funDisableHourlyPricing(flag) 
	{
		$("#txtTimeFrom").prop("disabled", !flag);
		$("#txtTimeTo").prop("disabled", !flag);
		if(flag)
		{
			$('#txtTimeFrom').timepicker('setTime', new Date());
			$('#txtTimeTo').timepicker('setTime', new Date());  
		}
		else
		{
			$("#txtTimeFrom").val("");
			$("#txtTimeTo").val("");
		}
	} 
		
	function funSetCityCode(code)
	{

		$.ajax({
			type : "POST",
			url : getContextPath()+ "/loadWSCityCode.html?docCode=" + code,
			dataType : "json",
			success : function(response)
			{
	        	if(response.strCityCode=='Invalid Code')
	        	{
	        		alert("Invalid City Code");
	        		$("#txtCityCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtCityCode").val(code);
		        	$("#txtCityName").val(response.strCityName);
		        	funSetStateCode(response.strStateCode);
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

	//set popular  Y/N
	function funPopularClicked()
	{
		var popularTF=$("#txtPopular").prop('checked');				
	}
	   					
	//set Hourly Pricing  Y/N
	function funHourlyPricingClicked()
	{
		var hourlyPricingYN=$("#txtHourlyPricing").prop('checked');
		
		funDisableHourlyPricing(hourlyPricingYN);
	}

	

	function funSetDataToCreateItemPrice(itemCode)
	{
		$("#txtItemCode").val(itemCode);
		var searchurl=getContextPath()+"/loadDataToCreateItemPrice.html?itemCode="+itemCode;
		 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strItemCode=='Invalid Code')
		        	{
		        		alert("Invalid Item Code Code");
		        		$("#txtItemCode").val('');
		        	}
		        	else
		        	{
		        	
		        		$("#txtItemCode").val(response.strItemCode);			        	
			        	$("#txtItemName").val(response.strItemName);
			        	$("#txtItemName").focus();			        				        
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
	
	function funSetDataToUpdateItemPrice(longPricingId)
	{
		
		var searchurl=getContextPath()+"/loadDataToUpdateItemPrice.html?longPricingId="+longPricingId;
		 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strItemCode=='Invalid Code')
		        	{
		        		alert("Invalid Item Code Code");
		        		$("#txtItemCode").val('');
		        	}
		        	else
		        	{
		        	
		        		$("#txtItemCode").val(response.strItemCode);			        	
			        	$("#txtItemName").val(response.strItemName);
			        	$("#txtItemName").focus();	
			        	$("#txt").val(response.strItemName);
			        	$("#txtPosCode").val(response.strPosCode);
			        	$("#txtMenuCode").val(response.strMenuCode);
			        	$("#txtSubMenuHeadCode").val(response.strSubMenuHeadCode);
			        	$("#txtAreaCode").val(response.strAreaCode);
			        	$("#txtCostCenterCode").val(response.strCostCenterCode);
			        	$("#txtTextColor").val(response.strTextColor);
			        	$("#txtPopular").prop('checked',response.strPopular);			        	
			        	$("#txtFromDate" ).val(response.dteFromDate);
			        	$("#txtToDate").val(response.dteToDate);
			        	$("#txtHourlyPricing").prop('checked',response.strHourlyPricing);
			        	
			        	if(response.strHourlyPricing)
			        	{
			        		$("#txtTimeFrom").prop("disabled", !response.strHourlyPricing);
				    		$("#txtTimeTo").prop("disabled", !response.strHourlyPricing);
				    						    			
				    		$('#txtTimeFrom').val(response.tmeTimeFrom+":"+response.strAMPMFrom);
			    			$('#txtTimeTo').val(response.tmeTimeTo+":"+response.strAMPMTo);
			        	}
			        	else
			        	{
			        		$("#txtTimeFrom").val("");
			    			$("#txtTimeTo").val("");
			        	}
			        	
			        	$("#txtPriceMonday").val(response.strPriceMonday);				
						$("#txtPriceTuesday").val(response.strPriceTuesday);
						$("#txtPriceWednesday").val(response.strPriceWednesday);
						$("#txtPriceThursday").val(response.strPriceThursday);
						$("#txtPriceFriday").val(response.strPriceFriday);
						$("#txtPriceSaturday").val(response.strPriceSaturday);
						$("#txtPriceSunday").val(response.strPriceSunday);
						$("#longPricingId").val(response.longPricingId);
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
				%>alert("Data Saved Successfully\n\n"+message);<%
			}
		}%>
	});

	
	function funSetData(code)
	{

		switch(fieldName)
		{

			case 'POSMenuItemMaster' : 
				funSetDataToCreateItemPrice(code);
				break;
				
			case 'POSMenuItemPricingMaster' : 
				funSetDataToUpdateItemPrice(code);
				break;
		}
		
	}


	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>Pricing Master</label>
	</div>

<br/>
<br/>

	<s:form name="PricingMaster" method="POST" action="savePricingMaster.html">

		<table class="masterTable">
			<tr>
				<td style="width: 87px;">
					<label>Item Code</label>
				</td>
				<td>
					<s:input type="text" id="txtItemCode" placeholder="Create Item Price" path="strItemCode" cssClass="searchTextBox"   readonly="true" ondblclick="funHelp('POSMenuItemMaster');"  />
					<s:input type="hidden" id="longPricingId"  path="longPricingId"  />
				</td>
				 <td>
                	<input id="btnUpdateItemPrice"  class="big1Button" type="button" value="Update Item Price" onclick="funHelp('POSMenuItemPricingMaster');"  />
                </td>
				<td >
					<label>Item Name</label>
				</td>
				<td colspan="6">
					<s:input  type="text" id="txtItemName" path="strItemName" size="300px" cssClass="longTextBox"  cssStyle="text-transform: uppercase;" required="true" />
				</td>				
			</tr>
			<tr>
				<td>
					<label>POS</label>
				</td>
				<td colspan="2">
					<s:select id="txtPosCode" path="strPosCode" items="${mapPOSName}"  cssClass="BoxW124px" style="width: 247px;" required="true" />
				</td>			
				<td>
					<label>Menu</label>
				</td>
				<td colspan="2">
					<s:select id="txtMenuCode" path="strMenuCode" items="${mapMenuHeadName}"  cssClass="BoxW124px" style="width: 200px;" required="true"  />
				</td>
				<td colspan="2">
					<label>Sub Menu Head</label>
				</td>
				<td colspan="2">
					<s:select id="txtSubMenuHeadCode" path="strSubMenuHeadCode" items="${mapSubMenuHeadName}"  cssClass="BoxW124px"  />
				</td>
				
			</tr>
			<tr>
				<td>
					<label>Area</label>
				</td>
				<td colspan="2">
					<s:select id="txtAreaCode" path="strAreaCode" items="${mapAreaName}"  cssClass="BoxW124px" style="width: 247px;" required="true" />
				</td>
				<td>
					<label>Cost Center</label>
				</td>
				<td colspan="2">
					<s:select id="txtCostCenterCode" path="strCostCenterCode" items="${mapCostCenterName}"  cssClass="BoxW124px" style="width: 200px;" required="true" />
				</td>	
				<td style="width: 55px;">
					<label>Item Color</label>
				</td>
				<td>
					<s:select id="txtTextColor" path="strTextColor" items="${mapColours}"  cssClass="BoxW124px"/>
				</td>
				<td>
					<label>Popular</label>
				</td>
				<td>
					<s:checkbox id="txtPopular" path="strPopular"  onclick="funPopularClicked()" />
				</td>		
				
			</tr>	
			
			<tr>
				<td>
					<label>From Date</label>
				</td>
				<td>
					<s:input  type="text" id="txtFromDate" path="dteFromDate" cssClass="calenderTextBox" required="true" />
				</td>
			
				<td>
					<label>To Date</label>
				</td>
				<td>
					<s:input  type="text" id="txtToDate" path="dteToDate" cssClass="calenderTextBox" required="true"  />
				</td>
				<td colspan="8"></td>
			</tr>
			
			<tr>
				<td>
					<label>Hourly Pricing</label>
				</td>
				<td>
					<s:checkbox id="txtHourlyPricing" path="strHourlyPricing" onclick="funHourlyPricingClicked()" />
				</td>			
				<td>
					<label>Time From</label>
				</td>				
				<td><s:input type="text" id="txtTimeFrom" path="tmeTimeFrom" cssClass="calenderTextBox" disabled="true"/></td>						
				<td>
					<label>Time To</label>
				</td>
				<td><s:input type="text" id="txtTimeTo" path="tmeTimeTo" cssClass="calenderTextBox" disabled="true" /></td>		
				<td></td>
				<td></td>
				<td></td>	
				<td></td>			
			</tr>	
			<tr>
				<th colspan="10"><label>Pricing</label></th>
			</tr>			
			<tr>
				<td>
					<label >Monday</label>
				</td>
				<td>
					<s:input  type="text" id="txtPriceMonday" path="strPriceMonday" cssClass="decimal-places-amt numberField"   required="true" />					
				</td>				
				<td>
					<label>Tuesday</label>
				</td>
				<td>
					<s:input  type="text" id="txtPriceTuesday" path="strPriceTuesday" cssClass="decimal-places-amt numberField" required="true" />
				</td>
			
				<td>
					<label>Wednesday</label>
				</td>
				<td>
					<s:input  type="text" id="txtPriceWednesday" path="strPriceWednesday" cssClass="decimal-places-amt numberField" required="true" />
			
				<td>
					<label>Thursday</label>
				</td>
				<td>
					<s:input type="text" id="txtPriceThursday" path="strPriceThursday" cssClass="decimal-places-amt numberField" required="true" />
				</td>
				<td></td>
				<td></td>		
			</tr>
			<tr>
				<td>
					<label>Friday</label>
				</td>
				<td>
					<s:input  type="text" id="txtPriceFriday" path="strPriceFriday" cssClass="decimal-places-amt numberField" required="true" />
				</td>
		
				<td>
					<label>Saturday</label>
				</td>
				<td>
					<s:input  type="text" id="txtPriceSaturday" path="strPriceSaturday" cssClass="decimal-places-amt numberField" required="true" />
				</td>
			
				<td>
					<label>Sunday</label>
				</td>
				<td>
					<s:input  type="text" id="txtPriceSunday" path="strPriceSunday" cssClass="decimal-places-amt numberField"  required="true" />
				</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>						
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick='return funOnSubmitValidation()' />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
	
	<script type="text/javascript">
	funApplyNumberValidation();	
	</script>


</html>
