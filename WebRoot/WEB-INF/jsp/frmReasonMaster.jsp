<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reason Master</title>

<script type="text/javascript">
$(document).ready(function(){
	 resetForms('reasonForm');
	    $("#txtReasonName").focus();	
});
</script>

<script type="text/javascript">
	function funHelp(transactionName) {
		
	//	 window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	}

	function funSetData(code) {
		
			$.ajax({
					type : "GET",
					url : getContextPath()
							+ "/loadReasonMasterData.html?reasonCode=" + code,
					dataType : "json",
					success : function(response) {
						if('Invalid Code' == response.strReasonCode){
							alert("Invalid reason Code");
							$("#txtReasonCode").val('');
							$("#txtReasonName").val('');
							$("#txtReasonCode").focus();
						}else{
							$("#txtReasonCode").val(response.strReasonCode);
							$("#txtReasonName").val(response.strReasonName);
							$("#txtReasonDesc").val(response.strReasonDesc);
							$("#txtExpiryDate").val(response.dtExpiryDate.split(" ")[0]);
							if (response.strStockAdj == 'Y') {
								$("#chkStockAdj").attr('checked', true);
							} else {
								$("#chkStockAdj").attr('checked', false);
							}
							document.getElementById("chkStocktra").checked = response.strStocktra == 'Y' ? true
									: false;
							document.getElementById("chkNonConf").checked = response.strNonConf == 'Y' ? true
									: false;
							document.getElementById("chkFollowUps").checked = response.strFollowUps == 'Y' ? true
									: false;
							document.getElementById("chkCorract").checked = response.strCorract == 'Y' ? true
									: false;
							document.getElementById("chkPrevAct").checked = response.strPrevAct == 'Y' ? true
									: false;
							document.getElementById("chkResAlloc").checked = response.strResAlloc == 'Y' ? true
									: false;
							document.getElementById("tchkDelcha").checked = response.strDelcha == 'Y' ? true
									: false;
							document.getElementById("chkLeadMaster").checked = response.strLeadMaster == 'Y' ? true
									: false;
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

	$(function() {
		$('a#baseUrl').click(function() {
			if ($("#txtReasonCode").val().trim() == "") {
				alert("Please Select Reason Code");
				return false;
			}
 
			 window.open('attachDoc.html?transName=frmReasonMaster.jsp&formName=Reason Master&code='+$('#txtReasonCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
 
		});
		
		
		$('#txtReasonCode').blur(function(){
			 var code=$('#txtReasonCode').val();			     
			 if (code.trim().length > 0 && code !="?" && code !="/") {
			  		funSetData(code);
			  	}			 
		});
		
		$("#txtExpiryDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		var dt = new Date();
		var currentDateTime=dt.getDay()+"-"+(dt.getMonth()+1)+"-"+(dt.getYear()+1901);
		$("#txtExpiryDate" ).datepicker('setDate', currentDateTime);
	});
	
	function funResetFields()
	{
		$("#txtReasonName").focus();
    }
	
	function onLoadFunction()
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

	}
	function funvalidate()
	{
		if($('#txtReasonCode').val()=='')
		{
			var code = $('#txtReasonName').val().trim();
			 $.ajax({
			        type: "GET",
			        url: getContextPath()+"/checkReasonName.html?reasonName="+code,
			        async: false,
			        dataType: "text",
			        success: function(response)
			        {
			        	if(response=="true")
			        		{
			        			alert("Reason Name Already Exist!");
			        			$('#txtReasonName').focus();
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
		}
		//alert("flg"+flg);
		return flg;
	}
</script>
</head>

<body onload="onLoadFunction()">
	<div id="formHeading">
		<label>Reason Master</label>
	</div>
	<s:form method="POST" name="reasonForm" action="savereasonmaster.html?saddr=${urlHits}">
		<br />
		<br />
		<table class="masterTable">
			<tr>
				<th align="right" colspan="4"><a id="baseUrl"
					href="#"> Attach Documents</a>&nbsp;  &nbsp;  &nbsp;  &nbsp;</th>
			</tr>
			
			
			<tr>
				<td colspan="2" width="15%"><label>Reason Code </label></td>
				<td colspan="2"><s:input path="strReasonCode" 
						ondblclick="funHelp('reason')" id="txtReasonCode" 
						cssClass="searchTextBox" /></td>
			</tr>
			<tr>
				<td colspan="2"><label>Reason Name</label></td>
				<td colspan="2"><s:input path="strReasonName"
					cssStyle="text-transform: uppercase;"	id="txtReasonName" cssClass="longTextBox" required="true" /></td>
			</tr>
			<tr>
				<td colspan="2"><label>Description</label></td>
				<td colspan="2"><s:input path="strReasonDesc"
					cssStyle="text-transform: uppercase;"	id="txtReasonDesc" cssClass="longTextBox" /></td>
			</tr>
			<tr>
				<td colspan="2"><label>Expiry Date</label></td>
				<td colspan="2">
					<s:input id="txtExpiryDate" name="txtExpiryDate" path="dtExpiryDate"  cssClass="calenderTextBox" />
				</td>
			</tr>
		</table>
		
		<table class="masterTable" >
			<tr>
				<th colspan="6" align="center"><s:label path="">APPLICABLE FOR</s:label></th>
			</tr>

			<tr>
				<td width="150px"><label>Stock Adjustment</label></td>
				<td><s:checkbox element="li" id="chkStockAdj"
						path="strStockAdj" value="true" /></td>
				
				<td width="150px"><label>Stock Transfer</label></td>
				<td><s:checkbox element="li" id="chkStocktra"
						path="strStocktra" value="true" /></td>

				<td width="150px"><label>Non Conference</label></td>
				<td><s:checkbox element="li" id="chkNonConf" path="strNonConf"
						value="true" /></td>
			</tr>



			<tr>
				
				<td><label>Follow ups</label></td>
				<td><s:checkbox element="li" id="chkFollowUps"
						path="strFollowUps" value="true" /></td>
				<td><label>Corrective Active</label></td>
				<td><s:checkbox element="li" id="chkCorract" path="strCorract"
						value="true" /></td>

				<td><label>Preventive Action</label></td>
				<td><s:checkbox element="li" id="chkPrevAct" path="strPrevAct"
						value="true" /></td>
			</tr>

			<tr>
				<td><label>Resource Blocking</label></td>
				<td><s:checkbox element="li" id="chkResAlloc"
						path="strResAlloc" value="true" /></td>
				<td><label>Delivery Challan</label></td>
				<td><s:checkbox element="li" id="tchkDelcha" path="strDelcha"
						value="true" /></td>
				<td><label>Lead Master</label></td>
				<td><s:checkbox element="li" id="chkLeadMaster"
						path="strLeadMaster" value="true" /></td>
			</tr>

			

		</table>
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funvalidate();" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()" />
		</p>
	</s:form>
</body>
</html>