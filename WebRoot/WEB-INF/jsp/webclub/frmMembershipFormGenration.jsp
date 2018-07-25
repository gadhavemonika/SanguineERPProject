<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8"/>
		<script type="text/javascript">
		
		/**
		 * Global variable
		 */
		var fieldName;
	 $(document).ready(function()
					{
		 
		 $("#txtdtMemberFromDate").datepicker({ dateFormat: 'yy-mm-dd' });
			$("#txtdtMemberFromDate" ).datepicker('setDate', 'today');
			$("#txtdtMemberFromDate").datepicker();
			
	        $("#txtdtMemberToDate").datepicker({ dateFormat: 'yy-mm-dd' });
	        $("#txtdtMemberToDate" ).datepicker('setDate', 'today');
	        $("#txtdtMemberToDate").datepicker();
	        
			var message='';
			<%if (session.getAttribute("success") != null) {
				            if(session.getAttribute("successMessage") != null){%>
				            message='<%=session.getAttribute("successMessage").toString()%>';
				            <%
				            session.removeAttribute("successMessage");
				            }
							boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
							session.removeAttribute("success");
							if (test) {
							%>	
				alert("Data Save successfully\n\n"+message);
			<%
			}}%>

		});
	 
	 function funHelp(transactionName)
		{	 
		 fieldName=transactionName;
	    //    window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	        window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	        
	    }
	 
	 function funResetField()
		{
			location.reload(true); 
			
// 			 $("#txtFormNo").val("");
//      		$("#txtProspectName").val("");
//      		 $("#txtdtMemberFromDate").datepicker({ dateFormat: 'yy-mm-dd' });
// 			$("#txtdtMemberFromDate" ).datepicker('setDate', 'today');
// 			//$("#txtdtMemberFromDate").datepicker();

// 	        $("#txtdtMemberToDate").datepicker({ dateFormat: 'yy-mm-dd' });
// 	        $("#txtdtMemberToDate" ).datepicker('setDate', 'today');
	       // $("#txtdtMemberToDate").datepicker();
		}
	 
	 function funSetData(code)
		{
			switch (fieldName) 
			{

			    case 'WCMemberForm':
			    	funSetFormNo(code);
			        break;  
			}
		}
	 
	 function funSetFormNo(code)
		{
			$("#txtFormNo").val(code);
			var searchurl=getContextPath()+"/loadMemberFormNoData.html?formNo="+code;
			//alert(searchurl);
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strFormNo=='invalid Code')
				        	{
				        		alert("Invalid Form No");
				        		$("#txtFormNo").val('');
				        		
				        	}
				        	else
				        	{
					        	$("#txtFormNo").val(code);
					        	$("#txtProspectName").val(response.strProspectName);
					        	$("#txtdtMemberFromDate").val(response.dteCreatedDate.split(" ")[0]);
					        	$("#txtdtMemberToDate").val(response.dtePrintDate.split(" ")[0]);
// 					        	$("#txtdtMemberFromDate").val(response.dteCreatedDate);
// 					        	$("#txtdtMemberToDate").val(response.dtePrintDate);
					        	$("#txtProspectName").focus();
					        	
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
		
		
</head>
<body >
<div id="formHeading">
	<label>Membership Form Generation</label>
	</div>
	<div>
	<s:form name="frmMembershipFormGenration" action="saveMembershipFormGenration.html?saddr=${urlHits}" method="POST">
		<br>
			<table class="masterTable">
			<tr>
					<td width="20%">From No.</td>
				
					<td colspan="2"><s:input type="text" id="txtFormNo" 
						name="txtFormNo" path="strFormNo" 
						cssStyle="width: 15%; text-transform: uppercase;" cssClass="longTextBox" ondblclick="funHelp('WCMemberForm')" /> <s:errors path=""></s:errors></td>
						
			</tr>
			<tr>
				<td width="15%">Prospect Name</td>
				
				<td colspan="2"><s:input type="text" id="txtProspectName" 
						name="txtProspectName" path="strProspectName" required="true"
						cssStyle="width: 35%; text-transform: uppercase;" cssClass="longTextBox"  /> <s:errors path=""></s:errors></td>
			</tr>
			
			<tr >
				<td width="18%" >Member Category Code</td>
				<td width="18%"><input id="txtMemberCode" 
						cssClass="searchTextBox" disabled /></td>
			
				
				<td ><input type="text" id="txtMCName" 
						name="txtMCName"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox" disabled /> </td>
			</tr>
			<tr>
				<td><label>Form Generation Date</label></td>
			    <td><s:input id="txtdtMemberFromDate" name="txtdtMemberFromDate" path="dteGenration"  cssClass="calenderTextBox" /></td>
				
				<td><label>Form Issue Date</label>&nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
			    <s:input id="txtdtMemberToDate" name="txtdtMemberToDate" path="dteFormIssue"  cssClass="calenderTextBox" /></td>
		</tr>
			<tr>
			<td><input type=radio id="" name="print" >Generate</td>
			<td><input type=radio id="" name="print" >Print</td>
			
			</tr>
		 
		 
		 </table>
		 
		<br>
		<p align="center">
			<input type="submit" value="Submit"
				onclick=""
				class="form_button" /> &nbsp; &nbsp; &nbsp; <input type="reset"
				value="Reset" class="form_button" onclick="funResetField()" />
		</p>
		<br><br>
	
	</s:form>
</div>

	
</body>
</html>