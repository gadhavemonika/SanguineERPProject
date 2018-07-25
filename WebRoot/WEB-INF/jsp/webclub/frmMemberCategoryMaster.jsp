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
	 $(document).ready(function()
					{
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
	    //    window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	        window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	        
	    }
	 
	 function funResetFields()
		{
			location.reload(true); 
		}
	 
	 
	 function funSetData(code)
		{
			$("#txtCatCode").val(code);
			var searchurl=getContextPath()+"/loadWebClubMemberCategoryMaster.html?catCode="+code;
			//alert(searchurl);
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strGCode=='Invalid Code')
				        	{
				        		alert("Invalid Category Code");
				        		$("#txtCatCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtCatCode").val(code);
					        	$("#strCatName").val(response.strCatName);
					        	$("#txtGroupCategoryCode").val(response.strGroupCategoryCode);
					        	$("#txtTenure").val(response.strTenure);
					        	$("#cmbVotingRight").val(response.strVotingRights);
					        	$("#txtRCode").val(response.strRuleCode);
					        	
					        	$("#txtCreditLimit").val(response.intCreditLimit);
					        	$("#txtRemarks").val(response.strRemarks);
					        	$("#txtCreditAmt").val(response.dblCreditAmt);
					        	$("#txtDiscountAmt").val(response.dblDisAmt);
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
	<label>Member Category Master</label>
	</div>
	<div>
	<s:form name="frmMemberCategoryMaster" action="saveWebClubMemberCategoryMaster.html?saddr=${urlHits}" method="POST">
		<br>
			<table class="masterTable">
				
			<tr>
				<td width="18%">Member Category Code</td>
				<td width="10%"><s:input id="txtCatCode" path="strCatCode"
						cssClass="searchTextBox" ondblclick="funHelp('WCCatMaster')" />
			
				
				<s:input type="text" id="txtMCName" 
						name="txtMCName" path="strCatName" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> <s:errors path=""></s:errors></td>
			</tr>
			<tr>
				<td width="18%"">Member Group Category Code</td>
				<td><s:input id="txtGroupCategoryCode" path="strGroupCategoryCode"
						cssClass="searchTextBox" ondblclick="" />
			
				
				<s:input type="text" id="txtMSCName" 
						name="txtMSCName" path="" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> <s:errors path=""></s:errors></td>
			</tr>
			
			<tr>
					<td width="18%"">Tenure</td>
					<td><s:input type="text" id="txtTenure" 
						name="txtTenure" path="strTenure" required="true"
						cssClass="longTextBox" style="width: 118px;"  />&nbsp;&nbsp;&nbsp;(Years)</td>
			</tr>
			
			<tr>
				<td width="18%"">Rule Code</td>
				<td><s:input id="txtRCode" path="strRuleCode"
						cssClass="searchTextBox" ondblclick="" />	
				<s:input type="text" id="txtRName" 
						name="txtRName" path="" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> <s:errors path=""></s:errors></td>
			</tr>
			<tr>
					<td width="18%"><label>Credit Limit</label></td>
					<td width="100%"><s:input id="txtCreditLimit" path="intCreditLimit" required="required" 
					class="decimal-places numberField" type="text"></s:input></td>
			
			</tr>	 
			
			<tr>
					<td><label>Voting Rights</label></td>
					<td><s:select id="cmbVotingRight" name="cmbVotingRight" path="strVotingRights" cssClass="BoxW124px" >
				 <option value="N">No</option>
 				 <option value="Y">Yes</option>
				 </s:select></td>
			</tr>
			
			<tr>
				<td><label>Remark</label></td>
				<td><s:textarea path="strRemarks" id="txtRemarks" style="width: 400px; height: 37px" /></td>
			</tr>
			<tr>
					<td><label>Credit Amount</label></td>
					<td><s:input id="txtCreditAmt" path="dblCreditAmt" required="required" 
					class="decimal-places numberField" type="text"></s:input></td>
			
			</tr>
		 	<tr>
					<td><label>Discount Amount</label></td>
					<td><s:input id="txtDiscountAmt" path="dblDisAmt" required="required" 
					class="decimal-places numberField" type="text"></s:input></td>
			
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