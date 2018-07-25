<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

<spring:url value="/resources/css/jquery.classyedit.css" var="classyEditCSS" />
<spring:url value="/resources/js/jquery.classyedit.js" var="classyEditJS" />
	
<link href="${classyEditCSS}" rel="stylesheet" />
<script src="${classyEditJS}"></script>    


<style type="text/css">
	#divFieldSelection
	{
		 width:300px;
		 height: 300px;	
		 background-color: white;
		 overflow: scroll;		 
	}
	#divCriteriaConainer 
	{
		width: 488px; 
		height: 300px;
		background-color: white;		
	}	
	#divFieldSelection  a:link
	{
    	color: #0000FF;
    	text-decoration: none;
	}
</style>

<script type="text/javascript">
	var fieldName;
	

	$(document).ready(function()
	{
		$(".classy-editor").ClassyEdit();
	});
	
	/* when Enter key is press on textArea */
	$(document).ready(function()
	{
		$("#txtArea").keypress(function(event)
		{ 
		    var keyCode = event.keyCode;   
		    if(keyCode==13)
		    {
		    	var strCriteria=$("#txtArea").val();	
		    	$("#txtArea").val(strCriteria+"\n");
		    }	
		});
	});
	
	/* To add selected field to text criteria */
	function funCriteriaFieldSelected(selectedField)
	{
	    var a=$(selectedField).text();	   	    
	    var strCriteria=$("#txtArea").val();	    
	    $("#txtArea").val(strCriteria+" {"+a+"}");
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
				        	if(response.strReminderYN.toUpperCase()=="Y")				        		
				        	{
				        		$("#chkReminderLetter").prop('checked',true);
				        		$("#chkReminderLetter").val("Y");
				        	}
				        	else
				        	{
				        		$("#chkReminderLetter").prop('checked',false);
				        		$("#chkReminderLetter").val("N");	
				        	}
				        	$("#cmbReminderLetter").val(response.strReminderLetter);
				        	if(response.strIsCircular.toUpperCase()=="Y")				        		
				        	{
				        		$("#chkIsCircular").prop('checked',true);
				        		$("#chkIsCircular").val("Y");
				        	}
				        	else
				        	{
				        		$("#chkIsCircular").prop('checked',false);
				        		$("#chkIsCircular").val("N");	
				        	}
				        	$("#cmbLetterProcessOn").val(response.strView);
				        	$("#txtArea").val(response.strArea);
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

	/* To Set CheckBox Value To Y/N */
	function funSetCheckBoxValueYN(currentCheckBox)
	{			    
	    if($(currentCheckBox).prop("checked") == true)
	    {           
	    	$(currentCheckBox).val("Y");	
        }
	    if($(currentCheckBox).prop("checked") == false)
        {         
	    	$(currentCheckBox).val("N");	
        }	    	    
	}	
	
	/**
	* Success Message After Saving Record
	**/
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

	function funSetData(code)
	{
		switch(fieldName)
		{
			case "letterCode":
				funSetLetterData(code);
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
	<label>LetterMaster</label>
	</div>

<br/>
<br/>

	<s:form name="LetterMaster" method="POST" action="saveLetterMaster.html">

		<table class="masterTable">
			<tr>
			    <td style="width: 125px"><label>Letter Code</label></td>
			    <td style="width: 125px"><s:input id="txtLetterCode" path="strLetterCode" readonly="true" ondblclick="funHelp('letterCode')" cssClass="searchTextBox"/></td>			        			        
			    <td><s:input id="txtLetterName" path="strLetterName" required="true" cssClass="longTextBox" cssStyle="width:68%"/></td>					  		        			  
			</tr>	
			<tr>
			    <td><label>Reminder Letter</label></td>
			    <td><s:checkbox id="chkReminderLetter" path="strReminderYN" value="N" onclick="funSetCheckBoxValueYN(this)"/></td>			        			        
			    <td><s:select id="cmbReminderLetter" path="strReminderLetter" items="${listReminderLetter}" cssClass="BoxW124px" /></td>		    		        			   
			</tr>
			<tr>
			    <td><label>Circular/Notice Letter</label></td>
			    <td colspan="2"><s:checkbox id="chkIsCircular" path="strIsCircular" value="N" onclick="funSetCheckBoxValueYN(this)"/></td>			        			        			    			    		        			  
			</tr>			
			<tr>
			    <td><label>Letter Process On</label></td>
			    <td colspan="2"><s:select id="cmbLetterProcessOn" path="strView" items="${listLetterProcessOn}" cssClass="BoxW200px" /></td>			        			        			    			    		        			  
			</tr>			
		</table>
		<br />
		<br />
		<!--  -->
		<div id="divFieldSelectionAndDesigning">
        	<table class="masterTable">
        		<tr>
        			<td style="padding-left: 0px;  width: 300px; height: 0px;">
						<div id="divFieldSelection">
							<c:forEach var="fieldCriteria" items="${listVMemberDebtorDtlColumnNames}">
								<a href="#" class="fieldCriteriaLink" ondblclick='funCriteriaFieldSelected(this)'>${fieldCriteria}</a><br>
						    </c:forEach>
						</div>
					</td>  
					<td></td>  
					<td style="width: 488px;">
						<div id="divViewConainer" >
							<s:textarea id="txtArea" path="strArea" style="width: 485px; height: 300px; resize: none;" />					    						
						</div>
					</td>         			
        		</tr>             				
        	</table>
        </div>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
