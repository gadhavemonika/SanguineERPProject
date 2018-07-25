<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function getContextPath() {
	return window.location.pathname.substring(0, window.location.pathname
			.indexOf("/", 2));
	
}

var deletedString="";

	function deleteRow() {

		var table = document.getElementById("tblDoc");
		var rowCount = table.rows.length;
		if (rowCount > 0) {
			$('#tblDoc tr').each(function() {
				if ($(this).find('.del').is(":checked"))// `this` is TR DOM element
				{
					deletedString=deletedString+","+$(this).find('.del').val();
				}
			});
		}
		alert(deletedString);
	}
	
	
	function funDeleteRow(obj) 
	{
		 var index = obj.parentNode.parentNode.rowIndex;
		var t = document.getElementById('tblDoc');

		var val1 = $(t.rows[index].cells[1]).text(); 
		funDeleteSelectedAttachment(val1);
		
	}
	
	
	function funDeleteSelectedAttachment(name)
	{
		
		var code=$("#code").val();
		var searchUrl=getContextPath()+"/deleteAttachment.html?AttachmentName="+name+"&dcode="+code;
			$.ajax({
		        type: "POST",
		        url: searchUrl,
			    success: function(response)
			    {	if(response){
			    		location.reload();
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
<body style="height: 100%">
<div style="width: 100%;height: 100%;background-color:#D8EDFF ">
<div id="formHeading">
		<label id="formName" >Export </label>
	</div>
	<br><br>
	<s:form method="GET" action="saveImageAdvOrd.html"
		enctype="multipart/form-data">
		<input type="hidden" value="<c:out value="${transactionName}" />" name="transactionName">
		<table class="masterTable">
			<tr>
			<th colspan="4" align="left" style="color: white">${ordetype} - Download Image</th>
			</tr>
			<tr><td colspan="4"> &nbsp; </td></tr>
			<tr>

				<td>From Date</td>
				
				<td><input type="hidden" name="fromDate" id="fromDate" 
					value="<c:out value="${fromDate}"/>" /> <c:out value="${fromDate}" /></td>
		
	
	           <td>To Date</td>
				
				<td><input type="hidden" name="toDate" id="toDate" 
					value="<c:out value="${toDate}"/>" /> <c:out value="${toDate}" /></td>		
			
				</tr>
				<tr>
				<td><input type="hidden" name="ordetype" id="ordetype" 
					value="<c:out value="${ordetype}"/>" /> <c:out value="${ordetype}" /></td>	
			
				
				<td><input type="hidden" name="strGrp" id="strGrp" 
					value="<c:out value="${strGCode}"/>" /> </td>	
				
				
				<td><input type="hidden" name="strSGrp" id="strSGrp" 
					value="<c:out value="${strSGCode}"/>" /> </td>	
				<td colspan="1"></td>
				</tr>
				
				
				
				
		</table>

		<table  class="masterTable">
			<%-- <tr>
				 <td><s:label path="strActualFileName">Name</s:label></td>
				<td><s:input path="strActualFileName" /></td> 
			</tr> --%>
<!-- 			<tr> -->
<%-- 				<td><s:label path="binContent">Document</s:label></td> --%>
<!-- 				<td><input type="file" name="file" id="file_upload"></input></td> -->
<!-- 			</tr> -->
			<tr>
				<td><input type="submit" value="Export" /></td>
			</tr>
		</table>
	</s:form> 

	<br />

<br><br>
</div>
</body>
</html>