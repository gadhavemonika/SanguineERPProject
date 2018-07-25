<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>REASON MASTER</title>
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
    

		  $('input#txtReasonCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtReasonName').mlKeyboard({layout: 'en_US'});
}); 


 	/**
	* Reset The REASON Name TextField
	**/
	function funResetFields()
	{
	
		
		$("#chkCashManagement").attr('checked', false);
		$("#chkUnsettleBill").attr('checked', false);
		$("#chkComplementary").attr('checked', false);
		$("#chkDiscount").attr('checked', false);
		$("#chkNonChargableKOT").attr('checked', false);
		$("#chkVoidAdvanceOrder").attr('checked', false);
		$("#chkReprint").attr('checked', false); 
		$("#chkStockIn").attr('checked', false);
		
 		$("#chkStockOut").attr('checked', false);
		$("#chkVoidBill").attr('checked', false);
		$("#chkModifyBill").attr('checked', false);
		$("#chkPSP").attr('checked', false);
		$("#chkVoidKOT").attr('checked', false);
		$("#chkVoidStockIn").attr('checked', false);
		$("#chkVoidStockOut").attr('checked', false); 
 } 
	
	
		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{	       
	       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
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
			
		/* 	$("[type='reset']").click(function(){
				$("#chkStockIn").attr('unchecked', false);
			}); */
		});
		
		
		
		
		
		
		
		
		
		
		
		
		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(Reason Code)
		**/
		function funSetData(code)
		{
			$("#txtReasonCode").val(code);
			var searchurl=getContextPath()+"/loadPOSReasonMasterData.html?POSReasonCode="+code;
			 $.ajax({                        
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strGCode=='Invalid Code')
				        	{
				        		alert("Invalid Reason Code");
				        		$("#txtReasonCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtReasonCode").val(response.strReasonCode);
					        	$("#txtReasonName").val(response.strReasonName);
					        	$("#txtReasonName").focus();
					        	$("#cmbTransferType").val(response.strTransferType);
					        	$("#cmbTransferEntry").val(response.strTransferEntry);
					        	if(response.strStkIn=='Y')
					        	{
					        		$("#chkStockIn").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkStockIn").attr('unchecked', false);
					        	}
					        	if(response.strStkOut=='Y')
					        	{
					        		$("#chkStockOut").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkStockOut").attr('unchecked', false);
					        	}
					        	if(response.strVoidBill=='Y')
					        	{
					        		$("#chkVoidBill").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkVoidBill").attr('unchecked', false);
					        	}
					        	if(response.strModifyBill=='Y')
					        	{
					        		$("#chkModifyBill").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkModifyBill").attr('unchecked', false);
					        	}
					        	if(response.strPSP=='Y')
					        	{
					        		$("#chkPSP").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkPSP").attr('unchecked', false);
					        	}
					        	if(response.strKot=='Y')
					        	{
					        		$("#chkVoidKOT").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkVoidKOT").attr('unchecked', false);
					        	}
					        	if(response.strCashMgmt=='Y')
					        	{
					        		$("#chkCashManagement").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkCashManagement").attr('unchecked', false);
					        	}
					        	if(response.strVoidStkIn=='Y')
					        	{
					        		$("#chkVoidStockIn").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkVoidStockIn").attr('unchecked', false);
					        	}
					        	if(response.strVoidStkOut=='Y')
					        	{
					        		$("#chkVoidStockOut").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkVoidStockOut").attr('unchecked', false);
					        	}
					        	if(response.strUnsettleBill=='Y')
					        	{
					        		$("#chkUnsettleBill").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkUnsettleBill").attr('unchecked', false);
					        	}
					        	if(response.strComplementary=='Y')
					        	{
					        		$("#chkComplementary").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkComplementary").attr('unchecked', false);
					        	}
					        	if(response.strDiscount=='Y')
					        	{
					        		$("#chkDiscount").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkDiscount").attr('unchecked', false);
					        	}
					        	if(response.strNCKOT=='Y')
					        	{
					        		$("#chkNonChargableKOT").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkNonChargableKOT").attr('unchecked', false);
					        	}
					        	if(response.strVoidAdvOrder=='Y')
					        	{
					        		$("#chkVoidAdvanceOrder").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkVoidAdvanceOrder").attr('unchecked', false);
					        	}
					        	if(response.strReprint=='Y')
					        	{
					        		$("#chkReprint").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkReprint").attr('unchecked', false);
					        	}
					        	
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
		
		
		$(function()
		{
			
			$('#txtReasonCode').blur(function() 
			{
					var code = $('#txtReasonCode').val();
					if (code.trim().length > 0 && code !="?" && code !="/")
					{				
						funSetData(code);							
					}
			});
			
			$('#txtReasonName').blur(function () {
				 var strReasonName=$('#txtReasonName').val();
			      var st = strReasonName.replace(/\s{2,}/g, ' ');
			      $('#txtReasonName').val(st);
				});
			
		});
	
		
	
	
		
		
		
		/**
		*  Check Validation Before Saving Record
		**/
		
		function funCallFormAction(actionName,object) 
		{
			var flg=true;
			
			if($('#txtReasonCode').val()=='')
			{
				var code = $('#txtReasonName').val();
				 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/checkPOSReasonName.html?reasonName="+code,
				        async: false,
				        dataType: "text",
				        success: function(response)
				        {
				        	if(response=="true")
				        		{
				        			alert("Reason Name Already Exist!");
				        			$('#txtReasonName').focus();
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
			
			
			return flg;
		}
		
</script>


</head>

<body onload="funOnLoad();">
	<div id="formHeading">
		<label>Reason Master</label>
	</div>
	<s:form name="reasonForm" method="POST" action="savePOSReasonMaster.html?saddr=${urlHits}">
    <br />
		<br />
		<table class="masterTable">

			<tr>
				<td width="140px">Reason Code</td>
				<td><s:input id="txtReasonCode" path="strReasonCode"
						cssClass="searchTextBox" ondblclick="funHelp('POSReasonMaster')" /></td>
			</tr>
			<tr>
				<td><label>Reason Name</label></td>
				<td><s:input colspan="3" type="text" id="txtReasonName" 
						name="txtReasonName" path="strReasonName" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
			</tr>
			
			<tr>
				<td><label>Transaction</label></td>
				<td ><s:input type="checkbox"  id="chkStockIn"  path="strStkIn" style="width: 3%"></s:input>Stock In
				     <s:input type="checkbox"  id="chkStockOut" path="strStkOut" style="width: 3%" ></s:input>Stock Out
				     <s:input type="checkbox"  id="chkVoidBill" path="strVoidBill" style="width: 3%"  ></s:input>Void Bill
				     <s:input type="checkbox"  id="chkModifyBill" path="strModifyBill" style="width: 3%" ></s:input>Modify Bill
				     <s:input type="checkbox"  id="chkPSP" path="strPSP" style="width: 3%" ></s:input>PSP
				     <s:input type="checkbox"  id="chkVoidKOT" path="strKot"  style="width: 3%" ></s:input>Void KOT
				     <s:input type="checkbox"  id="chkVoidStockIn" path="strVoidStkIn" style="width: 3%"  ></s:input>Void Stock In
				     <s:input type="checkbox"  id="chkVoidStockOut" path="strVoidStkOut" style="width: 3%"  ></s:input>Void Stock Out
				</td>
				
			</tr>
			
			<tr>
				<td><label></label></td>
				<td ><s:input type="checkbox"  id="chkCashManagement" path="strCashMgmt"  style="width: 3%"></s:input>Cash Management
				     <s:input type="checkbox"  id="chkUnsettleBill" path="strUnsettleBill" style="width: 3%" ></s:input>Unsettle Bill
				     <s:input type="checkbox"  id="chkComplementary" path="strComplementary" style="width: 3%" ></s:input>Complementary
				     <s:input type="checkbox"  id="chkDiscount" path="strDiscount" style="width: 3%"  ></s:input>Discount
				     <s:input type="checkbox"  id="chkNonChargableKOT" path="strNCKOT" style="width: 3%"  ></s:input>Non Chargable KOT
				     <s:input type="checkbox"  id="chkVoidAdvanceOrder" path="strVoidAdvOrder" style="width: 3%" ></s:input>Void Advance Order
				     <s:input type="checkbox"  id="chkReprint" path="strReprint" style="width: 3%"  ></s:input>Reprint
				</td>
				
			</tr>
			
			<tr>
				<td><label>Transfer Type</label></td>
				<td>
				    <s:select id="cmbTransferType" path="strTransferType" class="BoxW48px" style="width:20%">
						   <option selected="selected" value="Purchase">Purchase</option>
						   <option value="Purchase Return">Purchase Return</option>
						   <option value="Other">Other</option>
					</s:select>
					
					 
				</td>
						
			</tr>
			
			<tr>
				<td><label>Transfer Entry</label></td>
				<td>
				    <s:select id="cmbTransferEntry" path="strTransferEntry" class="BoxW48px" style="width:20%">
				           <option value="Other">Other</option>
				   </s:select>
				</td> 
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