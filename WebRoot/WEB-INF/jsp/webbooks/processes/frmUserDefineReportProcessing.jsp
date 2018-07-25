<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

	<script>
	
	function funSetData(code)
	{
		switch (fieldName) 
		{
		   case 'userDefinedReportCode':
			   funSetReportName(code);
		        break;
		        
		}
	}
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
    }
	
	
	function funSetReportName(code)
	{
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadUserDefinedReportMasterData.html?userDefinedCode=" + code,
			dataType : "json",
			success : function(response)
			{
				funRemoveAllRows();
				$("#txtReportCode").val(response.strReportId);
				$("#lblUserName").text(response.strReportName);
				
			},
			error : function(e){
			}
		});
	}
	
	</script>
</head>
	<body>
	<div id="formHeading">
		<label>User Defined Report Master</label>
	</div>
	<br/>
    <br/>
		<s:form id="frmUserDefinedReportProcess" method="POST" action="getUserDefinedReportProcess.html?saddr=${urlHits}">
			
		    <table class="masterTable" style="width: 95%;">
			    <tr>
			        <td ><label>Report ID</label></td>
			        <td><s:input id="txtReportCode" path="strReportId" ondblclick="funHelp('userDefinedReportCode');" class="searchTextBox"/></td>
			        <td><label id="lblUserName"></label></td>			    			    			    		    
			    </tr>
			</table>
			</s:form>
			
			<input id="btnSubmit" type="submit" value="SUBMIT" class="form_button" onclick="return funGetVaildation()" />

</body>
</html>