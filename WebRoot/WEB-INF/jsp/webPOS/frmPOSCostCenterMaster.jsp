<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cost Center Master</title>
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
var fieldName="";
 $(document).ready(function () {
	  $('input#txtCostCenterCode').mlKeyboard({layout: 'en_US'});
	  $('input#txtCostCenterName').mlKeyboard({layout: 'en_US'});
		
	  $('input#txtLabelOnKOT').mlKeyboard({layout: 'en_US'});
	  
	  $("form").submit(function(event){
		  if($("#txtCostCenterName").val().trim()=="")
			{
				alert("Please Enter Cost Center Name");
				return false;
			}
		  if($("#txtCostCenterName").val().length > 20)
			{
				alert("Cost Center Name length must be less than 20");
				return false;
			}
		  else{
			  flg=funCallFormAction();
			  return flg;
		  }
		});

		$("#cmbPrinterPort").val("");
	    $("#cmbSecondaryPrinterPort").val("");
	
	});  
 

 var field;


 	/**
 	* Reset The Cost Center Name TextField
 	**/
 	function funResetFields()
 	{
 		$("#txtPOSCode").focus();
 		
     }
     
 	
 	
 		/**
 		* Open Help
 		**/
 		function funHelp(transactionName)
 		{	   field= transactionName.split(".") ;
 		
 		transactionName=field[0];
 	       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
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
				});

	//Initialize tab Index or which tab is Active

	function funHelp(transactionName)
	{
		fieldName=transactionName;
 		// window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
    }
	
	/**
	* Get and Set data from help file and load data Based on Selection Passing Value(Cost Center Code)
	**/
	function funSetData(code)
	{
		$("#txtCostCenterCode").val(code);
		var searchurl=getContextPath()+"/loadCostCenterMasterData.html?POSCostCenterCode="+code;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strCustomerTypeMasterCode=='Invalid Code')
			        	{
			        		alert("Invalid Cost Center Code");
			        		$("#txtCostCenterCode").val('');
			        	}
			        	else
			        	{
				        	$("#txtCostCenterCode").val(response.strCostCenterCode);
				  		    $("#txtCostCenterName").val(response.strCostCenterName);
				        	$("#txtCostCenterName").focus();
				            	$("#txtLabelOnKOT").val(response.strLabelOnKOT);
				        	
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
	*  Check Validation Before Saving Record
	**/
	function funCallFormAction() 
	{
		var flg=true;
		
// 		if($('#txtCostCenterCode').val()=='')
// 		{
			var costCenterCode = $('#txtCostCenterCode').val();
			var costCenterName = $('#txtCostCenterName').val();
			 $.ajax({
			        type: "GET",
			        url: getContextPath()+"/checkCostCenterName.html?strCostCenterCode="+costCenterCode+"&strCostCenterName="+costCenterName,
			        async: false,
			        dataType: "text",
			        success: function(response)
			        {
			        	if(response=="false")
			        		{
			        			alert("This Cost Center Name is Already Exist!");
			        			$('#txtCostCenterName').focus();
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
	
	
	
	function funTestPrint(printerval)
	{

			 var searchurl=getContextPath()+"/testPrint.html";
			 $.ajax({
				        type: "POST",
				        data:{ printerval : printerval,
			 	        	
			 			},
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	
				            	        	
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
	</script>

</head>
<body> 

	<div id="formHeading">
	<label>Cost Center Master</label>
	</div>

<br/>
<br/>

	<s:form name="Cost Center Master" method="POST" action="saveCostCenterMaster.html?saddr=${urlHits}">

		<table class="masterTable">
			<tr>
				<td>
					<label>Cost Center Code</label>
				</td>
				<td>
					<s:input  type="text" id="txtCostCenterCode" path="strCostCenterCode" cssClass="searchTextBox" ondblclick="funHelp('POSCostCenterMaster');"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>Cost Center Name</label>
				</td>
				<td>
					<s:input  type="text" id="txtCostCenterName" path="strCostCenterName"  cssClass="BoxW116px" required="true"  />
				</td>
			</tr>
			<tr>
			<td>
					<label>Primary Printer</label>
				</td>
				<td><s:select id="cmbPrinterPort" name="cmbPrinterPort" items="${printerList}" path="strPrinterPort" cssClass="BoxW124px" >
				
 				
				 </s:select> &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<%-- 				 <s:input  type="hidden" id="txtPrimaryPrinterName" path="strPrimaryPrinterName"  cssClass="BoxW116px" /> &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; --%>
				<input id="primaryPrinter" type="button" value="TEST" class="form_button" />
				</td>
<!-- 				<td> -->
<%-- 					<s:input type="text" id="txtPrimaryPrinterName" path="strPrimaryPrinterName"  cssClass="BoxW116px" required="true"/> --%>
<!-- 				</td> -->
			</tr>
			<tr>
			<td><label> Secondary Printer </label></td>
				
				<td><s:select id="cmbSecondaryPrinterPort" name="cmbSecondaryPrinterPort" items="${printerList}" path="strSecondaryPrinterPort" cssClass="BoxW124px" >
				
 				
				 </s:select> &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<%-- 				  <s:input  type="hidden" id="txtSecondaryPrinterName" path="strSecondaryPrinterName"  cssClass="BoxW116px" /> &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; --%>
				<input id="secondaryPrinter" type="button" value="TEST" class="form_button"/>
				</td>
				
				
			</tr>
			
			<tr>
				<td><label>Print On Both Printers</label></td>
				<td><s:input type="checkbox"  id="chkPrintOnBothPrinters"  path="strPrintOnBothPrinters" style="width: 3%" cssClass="BoxW116px" ></s:input>
				</td>
			</tr>
			<tr>
				<td>
					<label>Label On KOT</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtLabelOnKOT" path="strLabelOnKOT" value="KOT" cssClass="BoxW116px" required="true"/>
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
