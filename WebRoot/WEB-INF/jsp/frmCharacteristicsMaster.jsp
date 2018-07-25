<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">
$(document).ready(function(){
	funResetFields();	
});
</script>	
<script type="text/javascript">
function funResetFields(){
	resetForms('characteristicsForm');
	   $("#CharacteristicsName").focus();
}
function funHelp(transactionName)
{
	fieldName=transactionName;
   
    //window.showModalDialog("searchform.html?formname=charCode&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
    window.open("searchform.html?formname=charCode&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
}

function funSetData(Code)
{
	document.getElementById("CharacteristicsCode").value=Code;
	
	 $.ajax({
	        type: "GET",
	        url: getContextPath()+"/loadCharData.html?charCode="+Code,
	        dataType: "json",
	        success: function(resp){
	          // we have the response
	         document.getElementById("CharacteristicsCode").value=resp.strCharCode;
	         document.getElementById("CharacteristicsName").value=resp.strCharName;
	        },
	        error: function(e){
	        	document.getElementById("CharacteristicsCode").value=Code;
	          alert('Error121212: ' + e);
	        }
	      });
}

$(function()
		{
		$('#CharacteristicsCode').blur(function() {			
					var code = $('#CharacteristicsCode').val();
					if (code.trim().length > 0 && code !="?" && code !="/"){
						funSetData(Code);
					}
				
			});
		
		
		$('a#baseUrl').click(function() 
		{
			if($("#CharacteristicsCode").val()=="")
			{
				alert("Please Select Characteristics Code");
				return false;
			}
			
			//window.open('attachDoc.html?transName=frmAttributeMaster.jsp&formName=Attribute Master&code='+$("#txtAttCode").val(),"","Height:600px;Width:800px;Left:300px;");
			window.open('attachDoc.html?transName=frmCharacteristicsMaster.jsp&formName=Characteristics Master&code='+$("#CharacteristicsCode").val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		    //$(this).attr('target', '_blank');
		});

		});
	
		
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
</script>

<title>Insert title here</title>
</head>
<body onload="onLoadFun();">
<div id="formHeading">
		<label>Characteristics Master</label>
	</div>
	<s:form name="characteristicsForm" method="POST" action="saveCharMaster.html?saddr=${urlHits}">
		
		<br />
		<br />
		<table class="masterTable">
			<tr>
		        <th align="right" colspan="2"> <a id="baseUrl" href="#">Attach Documents</a>  &nbsp; &nbsp; &nbsp;
						&nbsp;</th>
		    </tr>
			<tr>
				<td width="150px"><s:label path="">Characteristics Code </s:label></td> 
				<td><s:input path="strCharCode" id="CharacteristicsCode"  ondblclick="funHelp('characteristics')" cssClass="searchTextBox"/></td>
			</tr>
			<tr>
				<td><s:label path="">Name </s:label></td> 
				<td><s:input path="strCharName" id="CharacteristicsName" required="true"   cssClass="longTextBox"/></td>
			</tr>
			<tr>
				<td><s:label path="">Type</s:label></td>
				<td><s:select path="strCharType" cssClass="BoxW124px">
					<s:option value="Text">Text</s:option>
					<s:option value="Integer">Integer</s:option>
					<s:option value="Decimal">Decimal</s:option>
				</s:select>
				</td>
			</tr>

			<tr>
				<td><s:label path="">Description</s:label> </td>
				<td><s:input path="strCharDesc" cssClass="longTextBox"/></td>

			</tr>
			<tr>
				<td colspan="2"></td>
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