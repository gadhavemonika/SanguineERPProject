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
			
			 var startDate="${startDate}";
			 var arr = startDate.split("/");
			 Dat=arr[2]+"-"+arr[1]+"-"+arr[0];
			 $("#txtFromDate").datepicker({ dateFormat: 'yy-mm-dd' });
			 $("#txtFromDate" ).datepicker('setDate', Dat);
			 $("#txtFromDate").datepicker();	
				
			 $("#txtToDate").datepicker({ dateFormat: 'yy-mm-dd' });
			 $("#txtToDate" ).datepicker('setDate', 'today');
			 $("#txtToDate").datepicker();	
					
			 $("#txtSRTillDate").datepicker({ dateFormat: 'yy-mm-dd' });
			 $("#txtSRTillDate" ).datepicker('setDate', 'today');
			 $("#txtSRTillDate").datepicker();
				
					
		});



function funHelp(transactionName)
{
	fieldName=transactionName;
    
 //   window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
}
function funSetData(code)
{
	switch(fieldName)
	{
	
	case 'subContractor':
	      funSetSubContractor(code)
	      break;
	case 'DNCode':
		  funSetDeliveryNoteCode(code)
		  break;
	}
	}
	
	function funSetSubContractor(code)
	{
		
		var searchUrl="";
		searchUrl=getContextPath()+"/loadsubContractorJobWorkRegister.html?subContractor="+code;			
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	if(response.strLocCode=='Invalid Code')
			       	{
			       		alert("Invalid Location Code");
			       		$("#txtSubContractor").val('');
			       		$("#lblSubContractor").text("");
			       		$("#txtSubContractor").focus();
			       	}
			       	else
			       	{
			    	$("#txtSubContractor").val(response.strPCode);
	        		$("#lblSubContractor").text(response.strPName);	
	        		$("#txtSubContractor").focus();
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
	
	
	function funSetDeliveryNoteCode(code)
	{
		
		var searchUrl="";
		searchUrl=getContextPath()+"/loadDeliveryNoteCodeJobWorkRegister.html?deliveryNote="+code;			
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	if(response.strPCode=='Invalid Code')
			       	{
			       		alert("Invalid Location Code");
			       		$("#txtDNCode").val('');
			       		$("#lblDN").text("");
			       		$("#txtDNCode").focus();
			       	}
			       	else
			       	{
			    	funSetSubContractor(response.strSCCode)
	        		$("#txtDNCode").val(response.strDNCode);
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
		<label>Job Work Register</label>
	</div>
	<s:form name="frmJobWorkRegister" method="GET"
		action="rptJobWorkRegisterReport.html" >
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		<table class="transTable">



			<tr>
				<td><label>From Date</label>
				<td><s:input path="dtFromDate" id="txtFromDate"
						required="required" cssClass="calenderTextBox" /></td>
				<td><label>To Date</label>
				<td><s:input path="dtToDate" id="txtToDate" required="required"
						cssClass="calenderTextBox" /></td>
			</tr>


			<tr>
				<td><label>Sub Contractor Permission Type</label></td>
				<td coplspan="3"><s:select id="cmbDocType" path="strDocType"
						cssClass="BoxW124px">
						<s:option value="Permitted">Permitted</s:option>
						<s:option value="General">General</s:option>
						<s:option value="UCC">UCC</s:option>
						<s:option value="ALL">ALL</s:option>


					</s:select></td>

				<td width="110px"><label>Sub Contractor</label></td>
				<td><s:input path="strSCCode" id="txtSubContractor"
						ondblclick="funHelp('subContractor')" cssClass="searchTextBox"></s:input>
					<label id=lblSubContractor>All Sub Contractor</label></td>

			</tr>
			<tr>
				<td><label>Delivery Note Type</label></td>
				<td><s:select id="cmbDNType" path="strProdType"
						cssClass="BoxW124px">
						<s:option value="Job Work">Job Work</s:option>
						<s:option value="General">General</s:option>
						<s:option value="ALL">ALL</s:option>

					</s:select></td>



				<td><label>Delivery Note Code</label></td>
				<td><s:input path="strDocCode" id="txtDNCode"
						ondblclick="funHelp('DNCode')" cssClass="searchTextBox" /><label
					id="lblDN">All Delivery Notes</label></td>




			</tr>
			<tr>
				<td><label>Report Type</label></td>
				<td colspan="3"><s:select id="cmbDocType" path="strExportType"
						cssClass="BoxW124px">
						<s:option value="PDF">PDF</s:option>
						<s:option value="XLS">EXCEL</s:option>
						<s:option value="HTML">HTML</s:option>
						<s:option value="CSV">CSV</s:option>

					</s:select></td>
			</tr>

<!-- 			<tr> -->
<!-- 				<td><label>From Date</label> -->
<%-- 				<td><s:input path="dtFromDate" id="txtFromDate" --%>
<%-- 						required="required" cssClass="calenderTextBox" /></td> --%>
<!-- 				<td><label>To Date</label> -->
<%-- 				<td><s:input path="dtToDate" id="txtToDate" required="required" --%>
<%-- 						cssClass="calenderTextBox" /></td> --%>
<!-- 			</tr> -->


<!-- 			<tr> -->
<!-- 				<td><label>Sub Contractor Permission Type</label></td> -->
<%-- 				<td coplspan="3"><s:select id="cmbDocType" path="strDocType" --%>
<%-- 						cssClass="BoxW124px"> --%>
<%-- 						<s:option value="Permitted">Permitted</s:option> --%>
<%-- 						<s:option value="General">General</s:option> --%>
<%-- 						<s:option value="UCC">UCC</s:option> --%>
<%-- 						<s:option value="ALL">ALL</s:option> --%>


<%-- 					</s:select></td> --%>

<!-- 				<td width="110px"><label>Sub Contractor</label></td> -->
<%-- 				<td><s:input path="strSCCode" id="txtSubContractor" --%>
<%-- 						ondblclick="funHelp('subContractor')" cssClass="searchTextBox"></s:input> --%>
<!-- 					<label id=lblSubContractor>All Sub Contractor</label></td> -->

<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td><label>Delivery Note Type</label></td> -->
<%-- 				<td><s:select id="cmbDNType" path="strProdType" --%>
<%-- 						cssClass="BoxW124px"> --%>
<%-- 						<s:option value="Job Work">Job Work</s:option> --%>
<%-- 						<s:option value="General">General</s:option> --%>
<%-- 						<s:option value="ALL">ALL</s:option> --%>

<%-- 					</s:select></td> --%>






<!-- 				<td><label>Delivery Note Code</label></td> -->
<%-- 				<td><s:input path="strDocCode" id="txtDNCode" --%>
<%-- 						ondblclick="funHelp('DNCode')" cssClass="searchTextBox" /><label --%>
<!-- 					id="lblDN">All Delivery Notes</label></td> -->




<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td><label>Order By</label></td> -->
<%-- 				<td><s:select id="cmbDNType" path="strProdType" --%>
<%-- 						cssClass="BoxW124px"> --%>
<%-- 						<s:option value="Date">Date</s:option> --%>
<%-- 						<s:option value="G">G</s:option> --%>
<%-- 						<s:option value="A">A</s:option> --%>

<%-- 					</s:select></td> --%>
<!-- 				<td><label>SR Till Date</label> -->
<%-- 				<td><s:input path="dtExpFromDate" id="txtSRTillDate" --%>
<%-- 						required="required" cssClass="calenderTextBox" /></td> --%>

<!-- 			</tr> -->
<!-- 			<tr> -->


<!-- 				<td><label>Month Wise Job Work</label> -->
				
<!-- 				<td colspan="4"> -->
<!-- 			<label>Month Wise Job Work</label>&nbsp&nbsp&nbsp&nbsp<input type="checkbox" name="vehicle" value="Car" path="strProdType" -->
<!-- 					id="txtMonthWise"> -->
<!-- 				</td> -->
<!--             </tr> -->
            
<!--             <tr> -->
<!-- 				<td><label>Report Type</label></td> -->
<%-- 				<td colspan="3"><s:select id="cmbDocType" path="strExportType" --%>
<%-- 						cssClass="BoxW124px"> --%>
<%-- 						<s:option value="PDF">PDF</s:option> --%>
<%-- 						<s:option value="XLS">EXCEL</s:option> --%>
<%-- 						<s:option value="HTML">HTML</s:option> --%>
<%-- 						<s:option value="CSV">CSV</s:option> --%>

<%-- 					</s:select></td> --%>
<!-- 			</tr> -->
            
            
		</table>
		<br>
		<p align="center">
			<input type="submit" value="Submit"
				onclick=""
				class="form_button" /> &nbsp; &nbsp; &nbsp; <a
				STYLE="text-decoration: none"
				href="frmJobWorkRegister.html?saddr=${urlHits}"><input
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