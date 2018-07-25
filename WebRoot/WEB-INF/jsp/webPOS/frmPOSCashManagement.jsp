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
				else
				{
					%>alert(" This User has already entered rolling amount");<%
				}
			}%>
			
			$("form").submit(function(event){
				
				  if($("#txtAmount").val().trim()=="")
					{
						alert("Please Enter Amount");
						return false;
					}
				  if($("#txtAmount").val()<=0.0)
					{
						alert("Enter Amount Greater Then 0");
						return false;
					}
				 
				  else{
					  return true;
				  }
				});
		});


function funSetDate()
{
	
	var searchurl=getContextPath()+"/getPOSDate.html";
	 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	/* var dateTime=response.POSDate;
		        	var date=dateTime.split(" ");
		        	$("#txtFromDate").val(date[0]);
		        	$("#txtToDate").val(date[0]); */
		        	
		        var date = new Date(response.POSDate);
		        var	dateTime=date.getDate()  + '-' + (date.getMonth() + 1)+ '-' +  date.getFullYear();
		        var posDate=dateTime.split(" ");
		        $("#txtTransDate").val(posDate[0]);

	        	
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

</script>

</head>
<body onload="funSetDate()">

	<div id="formHeading">
	<label>Cash Managment Transcation</label>
	</div>

<br/>
<br/>

	<s:form name="Cash Managment Transcation" method="POST" action="savePOSCashManagmentTranscation.html?saddr=${urlHits}">

		<table class="masterTable">
			<tr>
				<td>
					<label>Transcation ID</label>
				</td>
				<td>
					<s:input colspan="3" type="text"   id="txtTransID" path="strTransID" cssClass="BoxW124px" readonly="true" />
				</td>
			
				<td>
					<label>Date</label>
				</td>
				<td>
					<s:input colspan="3" type="text" readonly="true" id="txtTransDate" path="dteTransDate" cssClass="BoxW124px" required="true"/>
				</td>s
			</tr>
			<tr>
				<td>
					<label>Transcation Type</label>
				</td>
				<td>
				 <s:select id="txtTransType" path="strTransType" class="BoxW124px" required="true">
						   <option selected="selected" value="Rolling">Rolling</option>
						   <option value="Float">Float</option>
						   <option value="Transfer In">Transfer In</option>
						   <option value="Refund">Refund</option>
						   <option value="Withdrawal">Withdrawal</option>
						   <option value="Payments">Payments</option>
						   <option value="Transfer Out">Transfer Out</option>
						   
					</s:select>
					
				</td>
			
				<td>
					<label>Amount</label>
				</td>
				<td>
					<s:input colspan="3" type="number" step="0.01" id="txtAmount" path="dblAmount" cssClass="BoxW124px" required="true"/>
				</td>
			</tr>
			
			<tr>
				<td>
					<label>Reason Code</label>
				</td>
				<td>
					<s:select id="txtReasonCode" path="strReasonCode" items="${ReasonNameList}"  cssClass="BoxW124px" required="true"/>
				</td>
			
				<td>
					<label>Currency Type</label>
				</td>
				<td>
						 <s:select id="txtCurrencyType" path="strCurrencyType" class="BoxW124px" required="true">
						   <option selected="selected" value="CASH">CASH</option>
						   <option value="COUPAN">COUPAN</option>
						   					   
					</s:select>
					
				</td>
			</tr>
			
			<tr>
				<td>
					<label>Remarks</label>
				</td>
				<td colspan="3">
					<s:input  type="text" id="txtRemarks" path="strRemarks" cssClass="longTextBox" />
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
