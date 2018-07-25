<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ATTRIBUTE VALUE MASTER</title>
<script type="text/javascript">
$(document).ready(function(){
	 resetForms('attributemasterForm');
	   $("#txtAttName").focus();	
});
</script>
	<script type="text/javascript">
	
		var fieldName;
		function funResetFields()
		{
			
			$("#txtAttName").focus();
			$("#lblParentAttName").text("");
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
		        	if(response.strAttCode=='Invalid Code')
		        	{
		        		alert("Invalid Att Value Code");
		        		$("#txtAttCode").val('');
		        		$("#txtAttName").focus();
		        	}
		        	else
		        	{
		        		$("#txtAttName").val(response.strAttName);
		        		$("#cmbAttType").val(response.strAttType);
		        		$("#txtAttDesc").val(response.strAttDesc);
		        		if(!response.strPAttCode=='')
		        		{
		        			funSetParentAttribute(response.strPAttCode);	
		        		}
		        		 $("#txtAttName").focus();	
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
		
		function funSetParentAttribute(code)
		{
			var gurl=getContextPath()+"/loadAttributeMasterData.html?attCode=";
			$.ajax({
		        type: "GET",
		        url: gurl+code,
		        dataType: "json",
		        success: function(response)
		        {
		        	$("#txtPAttCode").val(response.strAttCode);
		        	$("#lblParentAttName").text(response.strAttName);
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
		
		
		
		function funHelp(transactionName)
		{
			fieldName=transactionName;
	       // window.showModalDialog("searchform.html?formname=attributemaster&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	         window.open("searchform.html?formname=attributemaster&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    } 
		
		
		function funSetData(code)
		{
			switch (fieldName) 
			{
			   case 'attributemaster':
			    	funSetAttribute(code);
			        break;
			        
			   case 'parentattr':
				   funSetParentAttribute(code);
				   break;
			}
		}
			
		
		$(function()
		{
			$('a#baseUrl').click(function() 
			{
				if($("#txtAttCode").val()=="")
				{
					alert("Please Select Attribute Code");
					return false;
				}
				
				//window.open('attachDoc.html?transName=frmAttributeMaster.jsp&formName=Attribute Master&code='+$("#txtAttCode").val(),"","Height:600px;Width:800px;Left:300px;");
				window.open('attachDoc.html?transName=frmAttributeMaster.jsp&formName=Attribute Master&code='+$("#txtAttCode").val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			    //$(this).attr('target', '_blank');
			});
			$('#txtAttCode').blur(function () {
				 var code=$('#txtAttCode').val();
				 if(code.trim().length > 0 && code !="?" && code !="/"){
				       	  funSetAttribute(code);  
				      }				   
				});
		});
	</script>

</head>
<body onload="funResetFields()">
<div id="formHeading">
		<label id="formName">Attribute Master</label>
	</div>
	<s:form name="attributemasterForm" method="POST" id="frmAttributeMaster.jsp" action="saveAttributeMaster.html?saddr=${urlHits}">
	
		<br />
		<br />
		<table class="masterTable">
		
			<tr>
		        <th align="right" colspan="2"> <a id="baseUrl" href="#"> Attach Documents</a>  &nbsp; &nbsp; &nbsp;
						&nbsp;</th>
		    </tr>
		    <tr>
		        <td width="150px"><label>Attribute Code</label></td>
		        <td><s:input id="txtAttCode" name="txtAttCode" path="strAttCode" ondblclick="funHelp('attributemaster')"  cssClass="searchTextBox"/></td>
		    </tr>
		    	
		    <tr>
		        <td >
		        	<label>Name</label>
		        </td>
		        <td>
		        	<s:input type="text" id="txtAttName" name="txtAttName" path="strAttName" required="true"  cssClass="BoxW116px"/>
		        	<s:errors path="strAttName"></s:errors>
		        </td>
		    </tr>
			    
		    <tr>
			    <td >
			    	<label>Attribute Type</label>
			    </td>
			    
			    <td>
			    	<s:select id="cmbAttType" name="cmbAttType" path="strAttType" items="${listAttType}" cssClass="BoxW124px"/>			    	
			    	<s:errors path="strAttType"></s:errors>
			    </td>
			    
			  <tr > 
			    <td><label>Description</label></td>
		        <td><s:input id="txtAttDesc" name="txtAttDesc" path="strAttDesc"  cssClass="longTextBox"  /></td>
			</tr>
			
			<tr> 
			    <td ><label>Parent Attribute Code</label></td>
		        <td>
		        	<s:input id="txtPAttCode" name="txtPAttCode" path="strPAttCode" cssClass="searchTextBox" ondblclick="funHelp('parentattr')"/>
		        	<label id="lblParentAttName"></label>
		        </td>
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