<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	var fieldName;

	$(function() 
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

	function funSetData(code){

		switch(fieldName){

			case 'RouteCode' : 
				funSetRouteCode(code);
				break;
		}
	}


	function funSetRouteCode(code){

		$.ajax({
			type : "POST",
			url : getContextPath()+ "/LoadRouteMaster.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 
				
				if('Invalid Code' == response.strVehCode){
        			alert("Invalid Route Code");
        			$("#txtRouteCode").val('');
        			$("#txtRouteCode").focus();
        		}else{
        			
        			$("#txtRouteCode").val(response.strRouteCode);
        			$("#txtRouteName").val(response.strRouteName);
        			$("#txtDesc").val(response.strDesc);
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
	<label>Route Master</label>
	</div>

<br/>
<br/>

	<s:form name="RouteMaster" method="POST" action="saveRouteMaster.html?saddr=${urlHits}">

		<table class="masterTable">
			<tr>
				<td>
					<label>Route Code</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtRouteCode" path="strRouteCode" cssClass="searchTextBox" ondblclick="funHelp('RouteCode');"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>Route Name</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtRouteName" path="strRouteName" cssClass="BoxW124px" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Description</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtDesc" path="strDesc" cssClass="BoxW124px" />
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
