<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
$(document).ready(function() 
		{
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



			function funHelp(transactionName)
			{
				fieldName=transactionName;
			    
			  //  window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
			   window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
			}
			
			
			
			
			function funSetData(code)
			{
				switch (fieldName) 
				{		   
				   case 'subContractor':
					   $("#txtJOCode").val(code);
				        break;
				   
				}
			}
			
			
		
			
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>


</head>
<body onload="funOnLoad();">
	<div id="formHeading">
		<label>Delivery Note List</label>
	</div>
	<s:form name="DeliveryNoteList" method="GET"
		action="rptDeliveryNoteList.html" >
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		<table class="transTable">
								<tr>
                                   
											
											
											<td width="100px"><label>From Date</label>
									<td><s:input path="dtFromDate" id="txtFromDate"
											required="required" cssClass="calenderTextBox" /></td>
								</tr>
							    <tr>			
							        <td width="100px"><label>To Date</label>
									<td><s:input path="dtToDate" id="txtToDate"
											 required="required"
											cssClass="calenderTextBox  " /></td>
											
																										
								</tr>
<!-- 								<tr> -->
								

<!-- 							<td><label>Sub Contractor Permission Type</label></td> -->
<%-- 							<td ><s:select id="cmbDocType" path="strDocType" --%>
<%-- 									cssClass="BoxW124px"> --%>
<%-- 									<s:option value="PDF">All</s:option> --%>
<%-- 									<s:option value="XLS">Permitted</s:option> --%>
<%-- 									<s:option value="HTML">General</s:option> --%>
						
<%-- 					                </s:select></td> --%>



					
<!-- 							 	</tr> -->
								<tr>
									<td width="170px"><label>Sub-Contractor</label></td>
									<td><s:input path="strDocCode" id="txtJOCode"
											ondblclick="funHelp('subContractor')"  cssClass="BoxW116px"
											/></td>
									
								   								<tr>
									<td><label>Export Type</label></td>
								<td ><s:select id="cmbType" path="strDocType"
										cssClass="BoxW124px">
										<s:option value="PDF">PDF</s:option>
										<s:option value="EXCEL">EXCEL</s:option>

									</s:select></td>
									
																										
								</tr>																		
								
								</tr>
								

		</table>
		<br>
		<p align="center">
			<input type="submit" value="Submit"
				onclick="return funCallFormAction('submit',this)"
				class="form_button" /> &nbsp; &nbsp; &nbsp; <a
				STYLE="text-decoration: none"
				href="frmDeliveryNoteList.html?saddr=${urlHits}"><input
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