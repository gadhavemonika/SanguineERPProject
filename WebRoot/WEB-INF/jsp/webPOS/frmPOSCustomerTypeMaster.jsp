<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>



<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Type Master</title>
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
 	var fieldName;
 	 $(document).ready(function () {
	  $('input#txtcustomerTypemasterCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtcustomerType').mlKeyboard({layout: 'en_US'});
		  $('input#txtcustomerDiscount').mlKeyboard({layout: 'en_US'});
		}); 
 	

 	
 	function funHelp(transactionName)
	{	       
       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
    }
 	
		
		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(Group Code)
		**/
		function funSetData(code)
		{
			$("#txtcustomerTypemasterCode").val(code);
			var searchurl=getContextPath()+"/loadPOSCustomerTypeMasterData.html?POSCustomerTypeCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strCustomerTypeMasterCode=='Invalid Code')
				        	{
				        		alert("Invalid Customer Type Code");
				        		$("#txtcustomerTypemasterCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtcustomerTypemasterCode").val(response.strCustomerTypeMasterCode);
					        	$("#txtcustomerType").val(response.strCustomerType);
					        	$("#txtcustomerType").focus();
					        	$("#txtcustomerDiscount").val(response.dblDiscount);
					        	
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
			
			 $("form").submit(function(event){
				  if($("#txtcustomerType").val().trim()=="")
					{
						alert("Please Enter Zone Name");
						return false;
					}
				  if($("#txtcustomerType").val().length > 30)
					{
						alert("Customer Type length must be less than 30");
						return false;
					}
				 
				  else{
					  flg=funCallFormAction();
					  return flg;
				  }
				});
		});
		
		 function funCallFormAction() 
			{
				var flg=true;
				
				var code=$('#txtcustomerTypemasterCode').val();
				
					var name = $('#txtcustomerType').val();
					 $.ajax({
					        type: "GET",
					        url: getContextPath()+"/checkCustomerTypeName.html?strCustomerType="+name+"&strTypeMasterCode"+code,
					        async: false,
					        dataType: "text",
					        success: function(response)
					        {
					        	if(response=="false")
					        		{
					        			alert("Customer Type  Already Exist!");
					        			$('#txtcustomerType').focus();
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
	  	
	  	
 	
		$(function() {
			  $('#staticParent').on('keydown', '#txtcustomerDiscount', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()});
			})

 	 </script>

</head>
<body>
       
     <div id="formHeading">
		<label>Customer Type</label>
	</div>
	
	<s:form name="customertype" method="POST" action="savePOSCustomerTypeMaster.html?saddr=${urlHits}" >
<div id="staticParent">
		<br />
		<br />
		<div id="jquery-script-menu">

		</div>

			<table class="masterTable">

			<tr>
				<td width="140px">Customer Type Code  </td>
				<td><s:input id="txtcustomerTypemasterCode" path="strCustomerTypeMasterCode"
						cssClass="searchTextBox" ondblclick="funHelp('POSCustomerTypeMaster')" />
						</td>
			</tr>
			<tr>
				<td width="140px">Customer Type   </td>
				<td>
				 <s:input colspan="3" type="text" id="txtcustomerType"  path="strCustomerType" cssClass="BoxW116px" required="true" /></td>
			</tr>
			<tr>
				<td width="140px">Discount %   </td>
				<td><s:input colspan="3" type="number"  value="0.0" id="txtcustomerDiscount" path="dblDiscount" 
				  cssClass="BoxW116px" /></td>
			</tr>
			
		</table>
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button"/> 
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
		</div>
	</s:form>  
       
       
       
</body>
</html>