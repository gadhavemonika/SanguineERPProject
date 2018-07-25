<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

<script type="text/javascript">
	$(document).ready(function() 
		{
		 $('input#txtSettelmentCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtSettelmentDesc').mlKeyboard({layout: 'en_US'});
		  $('input#txtConversionRatio').mlKeyboard({layout: 'en_US'});
		  $('input#txtAccountCode').mlKeyboard({layout: 'en_US'});
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
			  if($("#txtSettelmentDesc").val().trim()=="")
				{
					alert("Please Enter Settlement Name");
					return false;
				}
			 
			 
			  else{
				  flg=funCallFormAction();
				  return flg;
			  }
			});
	});
</script>
<script type="text/javascript">

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
	
	var fieldName;

	function funSetData(code){

		switch(fieldName){

			case 'POSSettlementMaster' : 
				funSetSettlement(code);
				break;
			case 'WebBooksAcountMaster':
				$("#txtAccountCode").val(code);
				break;
		}
	}


	function funSetSettlement(code){

			$("#txtSettelmentCode").val(code);
			var searchurl=getContextPath()+"/loadPOSSettlementMasterData.html?settlementCode="+code;		
			 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strTableNo=='Invalid Code')
			        	{
			        		alert("Invalid Group Code");
			        		$("#txtSettelmentCode").val('');
			        	}
			        	else
			        	{
				        	$("#txtSettelmentDesc").val(response.strSettelmentDesc);
				        	$("#cmbSettlementType").val(response.strSettelmentType);
				        	$("#cmbApplicable").val(response.strApplicable);
				        	$("#txtConversionRatio").val(response.dblConversionRatio);
				        	$("#txtSettelmentDesc").focus();
				        	if(response.strBilling=='Y')
			        		{
				        		$("#chkBilling").prop('checked',true);
			        		}
				        	
				        	if(response.strAdvanceReceipt=='Y')
			        		{
				        		$("#chkAdvanceReceipt").prop('checked',true);
			        		}
				        	
				        	if(response.strBillPrintOnSettlement=='Y')
			        		{
				        		$("#chkBillPrintOnSettlement").prop('checked',true);
			        		}
				        	
				        	$("#txtAccountCode").val(response.strAccountCode);
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


	function funHelp(transactionName)
	{
		fieldName=transactionName;
		  window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	}
	




	function funCallFormAction() 
		{
			var flg=true;
			
			var name = $('#txtSettelmentDesc').val();
			var code= $('#txtSettelmentCode').val();
			
				 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/checkSettlementName.html?name="+name+"&code="+code,
				        async: false,
				        dataType: "text",
				        success: function(response)
				        {
				        	if(response=="false")
				        		{
				        			alert("Settlement Name Already Exist!");
				        			$('#txtSettelmentDesc').focus();
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
	<label>Settlement Master</label>
	</div>

<br/>
<br/>

	<s:form name="POSSettlementMaster" method="POST" action="savePOSSettlementMaster.html">
	<table
				style="border: 0px solid black; width: 70%; margin-left: auto; margin-right: auto;background-color:#C0E4FF;">
				<tr>
					<td>
						<div id="tab_container">
							<ul class="tabs">
								<li class="active" data-state="tab1">General</li>
								<li data-state="tab2">Linkup</li>
								
				
							</ul>
							<br /> <br />

							<!--  Start of Generals tab-->

							<div id="tab1" class="tab_content">
								<table  class="masterTable">
																		
								<tr>
				<td>
					<label>Settlement Code</label>
				</td>
				<td>	<s:input colspan="3" type="text" id="txtSettelmentCode" path="strSettelmentCode" cssClass="searchTextBox jQKeyboard form-control" ondblclick="funHelp('POSSettlementMaster')" />
				
			
				</td>
				</tr>
				<tr>
				<td>
					<label>Settlement Name</label>
				</td>
				<td>	<s:input colspan="" type="text" id="txtSettelmentDesc" path="strSettelmentDesc" cssClass="longTextBox jQKeyboard form-control" />
				</td>
			</tr>
			<tr>
			<td><label>Settlement Type</label></td>
				<td><s:select id="cmbSettlementType" name="cmbSettlementType" path="strSettelmentType" cssClass="BoxW124px" >
				<option value="Cash">Cash</option>
				 <option value="Credit Card">Credit Card</option>
				 <option value="Debit Card">Debit Card</option>
 				 <option value="Credit">Credit</option>
 				 <option value="Coupon">Coupon</option>
 				 <option value="Complementary">Complementary</option>
 				 <option value="Gift Voucher">Gift Voucher</option>
 				 <option value="Loyalty Points">Loyalty Points</option>
 				  <option value="Member">Member</option>
 				   <option value="Room">Room</option>
				 </s:select></td>
				
			</tr>
			<tr>
				<td><label>Applicable For</label></td>
				<td><s:checkbox element="li" id="chkBilling" path="strBilling"
									value="Yes" />&nbsp;&nbsp;&nbsp;<label>Billing</label>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<s:checkbox element="li" id="chkAdvanceReceipt" path="strAdvanceReceipt"
									value="Yes" />&nbsp;&nbsp;&nbsp;<label>Advance Receipt</label>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<s:checkbox element="li" id="chkBillPrintOnSettlement" path="strBillPrintOnSettlement"
									value="Yes" />&nbsp;&nbsp;&nbsp;<label>Bill Print On Settlement</label></td>
									<td></td>
			</tr>
								
			<tr>
			<td><label>Applicable</label></td>
				<td><s:select id="cmbApplicable" name="cmbApplicable" path="strApplicable" cssClass="BoxW124px" >
				<option value="Y">Yes</option>
				 <option value="N">No</option>
				
				 </s:select></td>
				
			</tr>
				
			<tr>
			
			<td><label>Conversion Rate</label></td>
				
				<td><s:input colspan="" type="text" id="txtConversionRatio" value="1"
						name="txtConversionRatio" path="dblConversionRatio" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox jQKeyboard form-control" /> 
		       </td>
		       <td></td>
		       <td></td>
			</tr>
						</table>
							</div>
							<!--  End of  Generals tab-->


							<!-- Start of Settlement tab -->

							<div id="tab2" class="tab_content">
						<table  class="masterTable">
																		
									<tr>
				<td width="140px">Account Code</td>
				<td><s:input id="txtAccountCode" path="strAccountCode"
						cssClass="searchTextBox jQKeyboard form-control" ondblclick="funHelp('WebBooksAcountMaster')" /></td>
			</tr>
			
						</table>
			</div>
							<!-- End of Settlement tab -->


							

						</div>
					</td>
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
