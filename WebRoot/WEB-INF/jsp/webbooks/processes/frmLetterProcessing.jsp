<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<spring:url value="/resources/css/jquery.classyedit.css" var="classyEditCSS" />
<spring:url value="/resources/js/jquery.classyedit.js" var="classyEditJS" />
	
<link href="${classyEditCSS}" rel="stylesheet" />
<script src="${classyEditJS}"></script>    

<title></title>
<style type="text/css">
	
	#txtLetterName
	{
		editable: false;
		required: true;
		readonly: true;
	}

	#tblOutStandingMonths td
	{
		 padding: 10px 67px 3px 10px;
		 background: #c0e4ff;
		 
	}	

</style>
<script type="text/javascript">
	var fieldName;	
	

	$(document).ready(function()
	{
		$(".classy-editor").ClassyEdit();
	});
	
	/* generate Letter processing slip and call report */
    function funGenerateLetterProcessingSlip()
	{
		var letterCode='';
		var fromDate='';
		var toDate='';		
		
		<%
			if(session.getAttribute("letterCode")!=null)
			{
				%>letterCode='<%= session.getAttribute("letterCode").toString()%>';
				<%	
				session.removeAttribute("letterCode");
			}
			if(session.getAttribute("fromDate")!=null)
			{
				%>fromDate='<%= session.getAttribute("fromDate").toString()%>';
				<%		
				session.removeAttribute("fromDate");
			}
			if(session.getAttribute("toDate")!=null)
			{
				%>toDate='<%= session.getAttribute("toDate").toString()%>';
				<%				
				session.removeAttribute("toDate");
			}			
		%>			
		
		var isOk=confirm("Do You Want to Generate Slip?");
		if(isOk)
		{
			window.open(getContextPath()+"/rptLetterProcessingSlip.html?letterCode="+letterCode+"&fromDate="+fromDate+"&toDate="+toDate,'_blank');
		}	
	}

	function funValidate()
	{
		
		
		if($("#txtLetterName").val().trim().length<1)
		{
			alert("Please Select Letter.");
			$("#txtLetterName").focus();
			return false;
		}	
		else
		{
			
			var queryCondition="";

			var excludeMembers=$("#txtExcludeMembers").val();
			    
			if(excludeMembers.length==0)
			{
				queryCondition=$("#cmbParameters").val()+" "+$("#cmbOperations").val()+" '"+$("#txtValue").val()+"'";   
			}
			else
			{
				queryCondition=$("#cmbParameters").val()+" "+$("#cmbOperations").val()+" '"+$("#txtValue").val()+"' AND Customer_Code NOT IN ("+excludeMembers+") ";
			}	 
			
			$("#txtCondition").val(queryCondition);
				
			funValidateAndSubmitForm();			
		}	
			
	}
	
	/* To check sql syntax of criteria */
	function funValidateAndSubmitForm()
	{		
		var sqlQuery="";
		
	    var strCriteria=$("#cmbParameters").val()+" "+$("#cmbOperations").val()+" '"+$("#txtValue").val()+"'";	    
	    var excludeMembers=$("#txtExcludeMembers").val();
	    
	    if(excludeMembers.length==0)
	    {
	    	sqlQuery="select Customer_Code,Member_Full_Name from dbwebmms.vwdebtormemberdtl where "+strCriteria;	   
	    }
	    else
	    {
	    	sqlQuery="select Customer_Code,Member_Full_Name from dbwebmms.vwdebtormemberdtl where "+strCriteria+" and Customer_Code not in ("+excludeMembers+")";
	    }	    	    	  	   	    	  
	   	    
		var searchurl=getContextPath()+"/checkSQLQueryWithParameters.html?sqlQuery="+sqlQuery;
				
		$.ajax({
	        type: "GET",
	        url: searchurl,
	        dataType: "json",
	        success: function(response)
	        {          	            
	        	if(response.result=="true")
		        {		         
	        		// alert(response.result);		
		          	 $( "#formLetterProc" ).submit();		          
		        }	   
		        else
		        {
		           alert(response.result);		           
		        }	
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
	
	/* when Enter key is press on textArea */
	$(document).ready(function()
	{
		$("#txtLetter").keypress(function(event)
		{ 
		    var keyCode = event.keyCode;   
		    if(keyCode==13)
		    {
		    	var strCriteria=$("#txtLetter").val();	
		    	$("#txtLetter").val(strCriteria+"\n");
		    	$(".editor").text(strCriteria+"\n");
		    }	
		});
	});
	
	function onViewORHideDtlsLinkClicked()
	{
		var linkText=$("#linkHideOrShowParamDtls").text();
		if(linkText=="Hide Parameter Details")
		{
			$("#linkHideOrShowParamDtls").text("Show Parameter Details");
			$("#divParamDetails").hide();
		}
		else
		{
			$("#linkHideOrShowParamDtls").text("Hide Parameter Details");
			$("#divParamDetails").show();
		}
	}

	/**
	* Success Message After Saving Record
	**/
	 $(document).ready(function()
	 {
		 
		 $("#txtDrDate").datepicker({
				dateFormat : 'dd-mm-yy'
			});
			$("#txtDrDate").datepicker('setDate', 'today');

			$("#txtCrDate").datepicker({
				dateFormat : 'dd-mm-yy'
			});
			$("#txtCrDate").datepicker('setDate', 'today');
		 
		 
		 
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
			
			funGenerateLetterProcessingSlip();
			
		<%
		}}%>

	}); 
	
	 function funSetDebtorMasterData(debtorCode)
		{
		   
			var searchurl=getContextPath()+"/loadSundryDebtorMasterData.html?debtorCode="+debtorCode;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strDebtorCode=='Invalid Code')
				        	{
				        		alert("Invalid Debtor Code");				        		
				        	}
				        	else
				        	{					        	    			        	    				        	   	
				        		var excludeMembers=$("#txtExcludeMembers").val();
				        		if(excludeMembers.trim().length>0)
				        		{
				        			$("#txtExcludeMembers").val(excludeMembers+",'"+response.strDebtorCode+"' ");
				        		}
				        		else
				        		{
				        			$("#txtExcludeMembers").val("'"+response.strDebtorCode+"'");
				        		}	
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
	
	 /* To Set Letter Data */
		function funSetLetterData(letterCode)
		{		
			var searchurl=getContextPath()+"/loadLetterMasterData.html?letterCode="+letterCode;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strLetterCode=='Invalid Code')
				        	{
				        		alert("Invalid Letter Code");
				        		$("#txtLetterCode").val('');
				        	}
				        	else
				        	{
				        		$("#txtLetterCode").val(response.strLetterCode);
					        	$("#txtLetterName").val(response.strLetterName);
					        	$("#txtLetterName").focus();
					        	$("#txtLetter").val(response.strArea);
					        	$(".editor").text(response.strArea);
					        	
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

	function funSetData(code)
	{
		switch(fieldName)
		{
			case "letterCode":
				funSetLetterData(code);
				break;
			case "debtorCode":
			     funSetDebtorMasterData(code);			    
				 break;		
		}
	}

	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>LetterProcessing</label>
	</div>

<br/>
<br/>

	<s:form id="formLetterProc" name="LetterProcessing" method="POST" action="saveLetterProcessing.html">

		<table class="masterTable">
			<tr>
			    <td style="width: 12%"><label>Letter Code</label></td>			    
			    <td style="width: 17%"><s:input id="txtLetterCode" path="strLetterCode" readonly="true" required="true" ondblclick="funHelp('letterCode')" cssClass="searchTextBox"/></td>			        			        			    
			    <td><s:input id="txtLetterName"  path="strLetterName" readonly="true"    cssClass="longTextBox" cssStyle="width:90%"/></td>	
			    <td style="width: 20%"><a id="linkHideOrShowParamDtls" href="#" onclick="onViewORHideDtlsLinkClicked()">Hide Parameter Details</a></td>					  		        			  
			</tr>	
			<tr>
				<td style="width: 12%"><label>Letter Subject</label></td>
			    <td colspan="2"><s:textarea id="txtLetterSubject" path="" cssClass="longTextBox"  style="width: 35%; height:50px; resize:none; overflow-y:scroll; "  /></td>
			    <td><s:hidden id="txtCondition" path="strCondition"/></td>				
			</tr>
		</table>	
		<br />
		<div id="divParamDetails" style="width: 80%;padding-left: 10%" >
			<table class="transTablex" style="width: 100%">
				<tr>
					<th style="width: 40%">Parameters</th>
					<th>Operations</th>
					<th style="width: 40%">Value</th>
					<th>Condition</th>
				</tr>
				<tr>
					<td><s:select id="cmbParameters" path="" items="${listParameters}" cssClass="BoxW124px" style="width: 99%"></s:select></td>
					<td>
						<s:select id="cmbOperations" path=""  cssClass="BoxW124px" style="width: 99%">
						<s:option value="<"/>
						<s:option value="<="/>
						<s:option value="="/>
						<s:option value=">"/>
						<s:option value=">="/>
						</s:select>
					</td>
					<td><s:input id="txtValue" path=""  cssClass="longTextBox" required="true" cssStyle="width:98%"/></td>
					<td>
						<s:select id="cmbCondition" path=""  cssClass="BoxW124px" style="width: 99%">
						<s:option value="NONE"/>
						<s:option value="AND"/>
						<s:option value="OR"/>						
						</s:select>
					</td>
				</tr>
			</table>
		</div>
		<br>
		<table class="masterTable">			
			<tr>
				<td style="width: 25%"><label>Reminder Status Update Log</label></td>
			    <td colspan="3"><s:select id="cmbReminderStatusUpdateLog" path="" items="${listReminderStatusUpdateLog}" cssClass="BoxW124px"></s:select></td>				
			</tr>
			<tr>
				<td style="width: 25%"><label>Letter Via Email</label></td>
			    <td colspan="3"><s:checkbox id="chkLetterViaEmail" path="" value="N"/></td>				
			</tr>
			<tr style="height: 120px">
				<td style="width: 25%"><label>Outstanding For The Months</label></td>	
				<td colspan="3">
					<div>
						<table id="tblOutStandingMonths">
							<tr>
								<td><s:checkbox id="chkJan" label="January" path="" value="N" /></td>
								<td><s:checkbox id="chkFeb" label="February" path="" value="N"/></td>
								<td><s:checkbox id="chkMar" label="March" path="" value="N"/></td>
								<td><s:checkbox id="chkApr" label="April" path="" value="N"/></td>
							</tr>
							<tr>
								<td><s:checkbox id="chkMay" label="May" path="" value="N"/></td>
								<td><s:checkbox id="chkJun" label="June" path="" value="N"/></td>
								<td><s:checkbox id="chkJul" label="July" path="" value="N"/></td>
								<td><s:checkbox id="chkAug" label="August" path="" value="N"/></td>
							</tr>
							<tr>
								<td><s:checkbox id="chkSept" label="September" path="" value="N"/></td>
								<td><s:checkbox id="chkOct" label="October" path="" value="N"/></td>
								<td><s:checkbox id="chkNov" label="November" path="" value="N"/></td>
								<td><s:checkbox id="chkDec" label="December" path="" value="N"/></td>
							</tr>
						</table>
					</div>
				</td>		   			
			</tr>
			<tr>
				<td style="width: 25%"><label>Exclude Members</label></td>	
				<td colspan="3"><s:textarea id="txtExcludeMembers" path="" cssClass="longTextBox"  style="width: 98%; height:75px; resize:none; overflow-y:scroll; " ondblclick="funHelp('debtorCode')" /></td>
			</tr>
			<tr>
				<td><label>Dr Date</label></td>	
			    <td><s:input type="text" id="txtDrDate" class="calenderTextBox" path="dteDrDate"  /></td>
			    <td><label>Cr Date</label></td>	
			    <td><s:input type="text" id="txtCrDate" class="calenderTextBox" path="dteCrDate" /></td>
			</tr>
		</table>	
		<br>
		<div id="divViewConainer" style="padding-left: 10%;" >
			<s:textarea id="txtLetter" path="strLetter" style="width: 75%; height: 300px; resize: none;" class="classy-editor" />					    						
		</div>
		<br />
		<br />
		<p align="center">
			<input id="btnSubmit" type="button" value="Submit" tabindex="3" class="form_button"  onclick="return funValidate()"/>
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
		<br />
		<br />
	</s:form>
</body>
</html>
