<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ATTRIBUTE VALUE MASTER</title>
	
<script type="text/javascript">
$(document).ready(function(){
	 resetForms('frmAttrValMaster');
	   $("#txtAttValueName").focus();	
});
</script>	
	<script type="text/javascript">
	
		var fieldName;
		
		function funResetFields()
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
         
            $("#txtAttValueName").focus();
			$("#lblAttName").text("");
	    }
		
	
		function funHelp(transactionName)
		{
			fieldName=transactionName;	    
	        //window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    	window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		}
		
		function funSetAttVal(code)
		{
			$("#txtAttValueCode").val(code);
			var gurl=getContextPath()+"/loadAttributeValueMasterData.html?attValueCode=";
			$.ajax({
			        type: "GET",
			        url: gurl+code,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strAVCode=='Invalid Code')
			        	{
			        		alert("Invalid Att Value Code");
			        		$("#txtAttValueCode").val('');
			        	}
			        	else
			        	{
				        	$("#txtAttValueName").val(response.strAVName);
				        	$("#txtAttValueDesc").val(response.strAVDesc);
				        	funSetAttribute(response.strAttCode);
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
		
		function funSetAttribute(code)
		{
			$("#txtAttCode").val(code);
			var gurl=getContextPath()+"/loadAttributeMasterData.html?attCode=";
			$.ajax({
		        type: "GET",
		        url: gurl+code,
		        dataType: "json",
		        success: function(response)
		        {
		        	$("#txtAttCode").val(response.strAttCode);
		        	$("#lblAttName").text(response.strAttName);
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
			switch (fieldName) 
			{
			   case 'attributevaluemaster':
			    	funSetAttVal(code);
			        break;
			   
			   case 'attributemaster':
			    	funSetAttribute(code);
			        break;
			}
		}
		
		$(function()
		{
			$("#txtAttValueName").focus();			
				    
			$('a#baseUrl').click(function() 
			{  
				if($("#txtAttValueCode").val().trim()=="")
				{
					alert("Please Select Attribute Value Code");
					return false;
					 
					
				}
				window.open('attachDoc.html?transName=frmAttributeValueMaster.jsp&formName=Attribute Value Master&code='+$('#txtAttValueCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			  //	$(this).attr('target', '_blank');
			});
			
			
		$('#txtAttValueCode').keydown(function(e) {
					var code = $('#txtAttValueCode').val();
					if (code.trim().length > 0 && code !="?" && code !="/"){
						funSetAttVal(code);
					}
				
			});

		});
	</script>
	
</head>
<body onload="funResetFields()">
<div id="formHeading">
		<label>Attribute Value Master</label>
	</div>
	<s:form name="frmAttrValMaster" method="POST" action="saveAttributeValueMaster.html?saddr=${urlHits}">
		<br />
		<br />
		<table class="masterTable">
				
			<tr>
		        <th align="right" colspan="2"> <a id="baseUrl" href="#"> Attach Documents </a> &nbsp; &nbsp; &nbsp;
						&nbsp; </th>
		    </tr>
		    	
		    <tr>
		        <td width="150px"><label>Attribute Value Code</label></td>
		        <td><s:input id="txtAttValueCode" name="txtAttValueCode" path="strAVCode" ondblclick="funHelp('attributevaluemaster')"  cssClass="searchTextBox"/></td>
		    </tr>
		    	
		    <tr>
		        <td>
		        	<label>Name</label>
		        </td>
		        <td>
		        	<s:input type="text" id="txtAttValueName" size="80px" name="txtAttValueName" path="strAVName" required="true" cssClass="longTextBox"/>
		        	<s:errors path="strAVName"></s:errors>
		        </td>
		    </tr>
			    
		    <tr>
			    <td>
			    	<label>Attribute code</label>
			    </td>
			    <td>
			    	<s:input typr="text" id="txtAttCode" name="txtAttCode" path="strAttCode" ondblclick="funHelp('attributemaster')" required="true" cssClass="searchTextBox"/>
			    	<s:errors path="strAttCode"></s:errors>
			    	<label id="lblAttName"></label>
			    </td>
			</tr>
			    
			<tr> 
			    <td><label>Description</label></td>
		        <td><s:input id="txtAttValueDesc"  name="txtAttValueDesc" path="strAVDesc"  cssClass="longTextBox"/></td>
			</tr>
			   	
			<tr>
			    <td ></td>
			    <td ></td>
		    </tr>
		    
		</table>
		<br /><br />
		
		<p align="center">
			<input type="submit" value="Submit"  class="form_button"
				onclick="return funValidateFields()" /> 
				<input type="reset"
				value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
		
		
	</s:form>
</body>
</html>