<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
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

var customerCode = "";
function funCkeckValidation()
{
	
}





//var strStatus;
/*On form Load It Reset form :Ritesh 22 Nov 2014*/
 $(document).ready(function () {
	
		  $('input#cmbCardTypeCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtCustomerName').mlKeyboard({layout: 'en_US'});


		  $("form").submit(function(event)
		{
			  
			  if($("#cmbOperation").val().trim()=="Register")
				{
				  if($("#txtSwipeCardNo").val().trim()=="")
					{
					alert("Please Swipe the Card");
					return false;
					}
			  	
			  if($("#cmbCardTypeCode").val().trim()=="")
				{
					alert("Please Select Card Type");
					return false;
				}
			  if($("#txtCustomerName").val().trim()=="")
				{
					alert("Please Select Custemer");
					return false;
				}
// 			  flg=funChekCustomerForCard($("#txtCustomerName").val());
// 			  return flg;
			  flg=funForRegisterCard($("#cmbCardTypeCode").val())
			  return flg;
			  
// 			  else
// 			  {
// 				  flg=funCallFormAction();
// 				  return flg;
// 			  }			  
				}
			  else 
			  {
				  if($("#txtSwipeCardNo").val().trim()=="")
					  {
						alert("Please Enter Card No.");
						return false;
			  } 
		}
// 			  else  
// 			{		
//
				  flg=funCallFormAction();
				  return flg;
//flg=funChekCustomerForCard($("#txtCustomerName").val());
// 				  return flg;
// 			  }
			});
			
		}); 


	/**
	* Reset The Group Name TextField
	**/
	function funResetFields()
	{
		$("#txtAreaName").focus();
		
    }
	
	
		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{	       
	       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
		
		function funSetData(code)
		{
			customerCode = code;
			
			$("#txtCustomerCode").val(code);
		
			var searchurl=getContextPath()+"/loadPOSCustomerMasterData.html?POSCustomerCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strCardTypeCode=='Invalid Code')
				        	{
				        		alert("Invalid Area Code");
				        		$("#cmbCardTypeCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtCustomerName").val(response.strCustomerName);
					        	$("#txtCustomerName").focus();
					        	$("#cmbOperation").val(response.strOperation);
					        	
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
		


	/* 	$(function() 
				{
					document.all[ 'txtSwipeCardNo' ].style.display = 'none';
			 		
			 		
				}); */

		$(function()
		{
			/**
			* On Blur Event on TextField
			**/

			
			$('#cmbCardTypeCode').blur(function () {
				 var strAName=$('#cmbCardTypeCode').val();
			      var st = strAName.replace(/\s{2,}/g, ' ');
			      $('#cmbCardTypeCode').val(st);
				});
			
		});
		
	
		/**
		* Success Message After Saving Record
		**/
		$(document).ready(function()
		{
			var message='';
			<%if (session.getAttribute("success") != null) {
				if (session.getAttribute("successMessage") != null) {%>
					message='<%=session.getAttribute("successMessage").toString()%>';
<%session.removeAttribute("successMessage");
				}
				boolean test = ((Boolean) session.getAttribute("success"))
						.booleanValue();
				session.removeAttribute("success");
				if (test) {%>
	alert("Data Saved \n\n" + message);
<%}
			}%>
	});

	function funCombo() {

		/* document.all["txtSwipeCardNo"].style.display = 'block';*/

		document.all["txtSwipeCardNo"].style.disabled = 'false';

		$('#txtSwipeCardNo').focus();

	}

	function funCheckCardString() {
		
		var cardString = $('#txtSwipeCardNo').val();
		var cardNo = "";
		$.ajax({
			type : "GET",
			url : getContextPath()
					+ "/checkCardString.html?cardString=" + cardString,
			dataType : "json",
			success : function(response) 
			{
				cardNo = response.strCardNo;
				if(cardNo!='')
				{
					$('#txtCustomerName').val(response.strCustomerName);
				}	
				else
				{
					$('#txtCustomerName').val("");
					$('#cmbOperation').val("Register");
				}	
				
				
				
			},
			error : function(jqXHR, exception) {
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

	function funRegisterCard() {
		var strCardTypeCode = $('#cmbCardTypeCode').val();
	}

	/**
	 *  Check Validation Before Saving Record
	 **/
	function funCallFormAction() {
		var flg = false;		
		var code = $('#txtSwipeCardNo').val();		
		if(code=='')			
		{
			alert('Please select card type');
		}
		else
		{
			$.ajax({
				type : "GET",
				url : getContextPath()
						+ "/checkRegisterCardName.html?strCardTypeCode=" + code,
				async : false,
				dataType : "text",
				success : function(response) {
					if (response == true) {
						alert("This Card Is Already Register!");
						$('#cmbCardTypeCode').focus();
						flg = false;
					}
	
					else {
						flg = true;
					}
				},
				error : function(jqXHR, exception) {
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
<body>

	<div id="formHeading">
		<label>POS Register Debit Card</label>
	</div>

	<br />
	<br />

	<s:form name="POS Register Debit Card" method="POST"
		action="savePOSRegisterDebitCardMaster.html?saddr=${urlHits}">

		<table class="masterTable">
			<tr>
				<td><label>Card Type Code</label></td>
				<td colspan="2"><s:select id="cmbCardTypeCode"
						name="cmbCardTypeCode" path="strCardTypeCode"
						items="${mapCardType}" cssClass="BoxW124px" /></td>
			</tr>
			<tr>
				<td><label>Card Number</label></td>

				<td style="width: 160px;"><s:input type="password"
						id="txtSwipeCardNo" name="txtSwipeCardNo" path="strCardString"
						required="true" cssClass="BoxW124px" autocomplete="false"
						onblur="funCheckCardString()" /> <%-- style="display:none" --%>
				</td>

				<td>
					<%-- <s:input  type="password" id="txtCardNo" name="txtCardNo" path="strCardNo" required="true" cssClass="BoxW124px"  /> --%>

					<input type="button" value="Swipe" class="form_button"
					onclick="funCombo()" />
				</td>
			</tr>
			<tr>
				<td><label>CustomerName</label></td>
				<td colspan="2"><s:input type="text" id="txtCustomerName"
						name="txtCustomerName" path="strCustomerName"
						ondblclick="funHelp('POSCustomerMaster');" cssClass="searchTextBox" />
				</td>
				<td><s:input type="hidden" id="txtCustomerCode"
 						name="txtCustomerName" path="strCustomerCode"
 						 cssClass="BoxW124px" /> 
				</td>
			</tr>
			<tr>
				<td><label>Operation</label></td>
				<td colspan="2"><s:select id="cmbOperation" name="cmbOperation"
						path="strOperation" items="${mapCardOperation}"
						cssClass="BoxW124px">


					</s:select></td>
			</tr>
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<!-- //onclick="funCkeckValidation()" -->
			<input type="reset" value="Reset" class="form_button"
				onclick="funResetFields()" />
		</p>

	</s:form>
</body>
</html>
