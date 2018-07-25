<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<script type="text/javascript">
$(document).ready(function() 
		{		
			$(".tab_content").hide();
			$(".tab_content:first").show();
	
			$("ul.tabs li").click(function() {
				$("ul.tabs li").removeClass("active");
				$(this).addClass("active");
				$(".tab_content").hide();
				var activeTab = $(this).attr("data-state");
				$("#" + activeTab).fadeIn();
			});
			
			$("#txtFromLocation").val("${locationCode}");
			$("#lblFromLocation").text("${locationName}"); 
			
			 $("#txtFromDate").datepicker({ dateFormat: 'yy-mm-dd' });
				$("#txtFromDate" ).datepicker('setDate', 'today');
				$("#txtFromDate").datepicker();	
				
				 $("#txtToDate").datepicker({ dateFormat: 'yy-mm-dd' });
					$("#txtToDate" ).datepicker('setDate', 'today');
					$("#txtToDate").datepicker();	
					
					
		});



function funHelp(transactionName)
{
	fieldName=transactionName;
    
   // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
    window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;top=500,left=500")
}
function funSetData(code)
{
	switch(fieldName)
	{
	
	case 'locationFrom':
	      funSetLocationFrom(code)
	      break;
	case 'productmaster':
    	  funSetProduct(code);
    	  break;
	
	}
	}
	
	function funSetLocationFrom(code)
	{
		
		var searchUrl="";
		searchUrl=getContextPath()+"/loadLocationFromCRM.html?locCode="+code;			
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	
					    	
					    	if(response.strLocCode=='Invalid Code')
					       	{
					       		alert("Invalid Location Code");
					       		$("#txtFromLocation").val('');
					       		$("#lblFromLocation").text("");
					       		$("#txtFromLocation").focus();
					       	}
					       	else
					       	{
					    	$("#txtFromLocation").val(response.strLocCode);
			        		$("#lblFromLocation").text(response.strLocName);	
			        		$("#txtFromLocation").focus();
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
	
	
	function funSetProduct(code)
	{
		
		var searchUrl="";
		searchUrl=getContextPath()+"/loadProductMasterSockLedger.html?prodCode="+code;			
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	if(response.strProdCode=='Invalid Code')
			       	{
			       		alert("Invalid Location Code");
			       		$("#txtProdCode").val('');
			       		$("#lblProdName").text("");
			       		$("#txtProdCode").focus();
			       	}
			       	else
			       	{
			    	$("#txtProdCode").val(response.strProdCode);
	        		$("#lblProdName").text(response.strProdName);	
	        		$("#txtProdCode").focus();
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



</script>
<body>
	<div id="formHeading">
		<label>Stock Report</label>
	</div>
	<s:form name="frmStockLedgerReportCRM" method="GET"
		action="rptStockLedger.html" >
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		<table class="transTable">


			<tr>
				<td><label>From Date</label>
				<td colspan="3"><s:input path="dtFromDate" id="txtFromDate"
						required="required" cssClass="calenderTextBox" /></td>
			
			</tr><tr>	<td><label>To Date</label>
				<td  colspan="3"><s:input path="dtToDate" id="txtToDate" required="required"
						cssClass="calenderTextBox" /></td>
			</tr>


			<tr>
			

				<td width="110px"><label>From Location</label></td>
				<td  colspan="3"><s:input path="strDocCode" id="txtFromLocation"
						ondblclick="funHelp('locationFrom')" cssClass="searchTextBox"></s:input>
					<label id=lblFromLocation></label></td>
                  </tr>
            <tr>
				        <td  width="110px"><label >Product </label></td>
				        <td  colspan="3"><s:input  id="txtProdCode" name="prodCode" path="strProdCode" value="" ondblclick="funHelp('productmaster')" cssClass="searchTextBox"/></td>				    
				    	<td ><label id="lblProdName" > </label></td>
				        
			   		</tr>



		</table>
		<br>
		<p align="center">
			<input type="submit" value="Submit"
				onclick=""
				class="form_button" /> &nbsp; &nbsp; &nbsp; <a
				STYLE="text-decoration: none"
				href="frmStockLedgerReportCRM.html?saddr=${urlHits}"><input
				type="button" id="reset" name="reset" value="Reset"
				class="form_button" /></a>
		</p>
		<br>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
	</s:form>

</body>
</html>