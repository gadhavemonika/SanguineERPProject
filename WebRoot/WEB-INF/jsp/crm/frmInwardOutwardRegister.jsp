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
					
					
		});
		
		function funCallToHelp()
		{
			
			var type=$("#cmbPartyType").val();
			switch(type){
			
		case"Supplier":
			funHelp('suppcode');
			break;
			
		case"Sub Contractor":
			funHelp('subContractor');
			break;
		
		case"Customer":
			funHelp('custMaster');
			break;
			
			}
			
		}
		function funHelp(transactionName)
		{
			
			fieldName=transactionName;
	        
	       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
	        window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;top=500,left=500")
	    }
		
		function funSetData(code)
		{
			funSetPartyType(code);

		}
		function funSetPartyType(code)
		{
			
			var searchUrl="";
			searchUrl=getContextPath()+"/loadPartyType.html?partyType="+code;			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if(response.strPCode=='Invalid Code')
				       	{
				       		alert("Invalid Location Code");
				       		$("#txtpartyType").val('');
				       		$("#lblPartytype").text("");
				       		$("#txtpartyType").focus();
				       	}
				       	else
				       	{
				    	$("#txtpartyType").val(response.strPCode);
		        		$("#lblPartytype").text(response.strPName);	
		        		$("#txtpartyType").focus();
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
		<label>Inward Outward Register</label>
	</div>
	<s:form name="frmInwardOutwardRegister" method="GET"
		action="rptInwardOutwardRegister.html" >
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		<table class="transTable">


			<tr>
				<td><label>From Date</label>
				<td><s:input path="dtFromDate" id="txtFromDate"
						required="required" cssClass="calenderTextBox" /></td>
				<td width="10px"><label>To Date</label>
				<td ><s:input path="dtToDate" id="txtToDate" required="required"
						cssClass="calenderTextBox" /></td>
			</tr>


			<tr>
				<td><label>Report Type</label></td>
				<td colspan="3"><s:select id="cmbReportType" path="strDocType"
						 cssClass="BoxW124px">
						 <s:option value="Inward Register">Inward Register</s:option>
						 <s:option value="Outward Registe">Outward Register</s:option>
						

					</s:select></td>
			</tr>	
			<tr>
				<td><label>Party Type</label></td>
				<td><s:select id="cmbPartyType" path="strProdType"
						cssClass="BoxW124px" onchange="">
					 <s:option value="Select">--Select--</s:option>
					  <s:option value="Supplier">Supplier</s:option>
					   <s:option value="Sub Contractor">Sub Contractor</s:option>	
					   <s:option value="Customer">Customer</s:option>	

					</s:select></td>
			
	    <td><s:input type="text" id="txtpartyType" path="" cssClass="searchTextBox"  ondblclick="funCallToHelp()"></s:input></td>
	    <td><label id=lblPartytype></label></td>
			</tr>




		</table>
		<br>
		<p align="center">
			<input type="submit" value="Submit"
				onclick="funSetType()"
				class="form_button" /> &nbsp; &nbsp; &nbsp; <a
				STYLE="text-decoration: none"
				href="frmInwardOutwardRegister.html?saddr=${urlHits}"><input
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