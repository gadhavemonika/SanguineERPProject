<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<script type="text/javascript">

	var fieldName="";
	
	$(document).ready(function() {	
		
		$("#txtDate").datepicker({ dateFormat: 'yy-mm-dd' });
		$("#txtDate" ).datepicker('setDate', 'today');
		
		$("#txtId").focus();
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
				alert(message);
			<%
			}}%>
				
			$('#txtId').blur(function () {
				 var code=$('#txtId').val();
				 if (code.trim().length > 0 && code !="?" && code !="/"){							   
				 	funSetOneDaypass(code);
			   }
			});
				
				$("form").submit(function(){
					
					var fromNo= $("#txtFromNo").val();	
	        		var toNo= $("#txtToNo").val();	
	        		if(parseInt(fromNo) > parseInt(toNo)){
	        			alert("From Number is Greater than To Number");
	        			return false;
	        		}else{
	        			return true;
	        		}
				});
		});

		
		function funHelp(transactionName)
		{
			fieldName=transactionName;
		    
		 //   window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
		    window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
		}
		function funSetData(code)
		{
			switch(fieldName)
			{
			
			case 'oneDayPass':
			      funSetOneDaypass(code)
			      break;
		
			}
	}
		
		function funSetOneDaypass(code)
		{
			var searchUrl="";
			$("#txtFromNo").focus();
			searchUrl=getContextPath()+"/loadOneDaypass.html?oneDayId="+code  ;			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if(response.intId==0)
				       	{
				       		alert("Invalid Code");
				       		$("#txtId").val('');
				       		$("#txtId").focus();
				       	}
				       	else
				       	{
					        $("#txtId").val(response.intId);
					    	$("#txtDate").val(response.dteDate);
			        		$("#txtFromNo").val(response.intFromNo);	
			        		$("#txtToNo").val(response.intToNo);	
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
		
		
		function funApplyNumberValidation(){
			$(".numeric").numeric();
			$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
			$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
			$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
		    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
		}
</script>
<body>
<div id="formHeading">
		<label>One Day Pass</label>
	</div>
	
<br>
<br>
<br><br>
	<s:form name="frmExciseOneDayPass" method="GET" action="saveOneDayPass.html?saddr=${urlHits}">
		<input type="hidden" value="${urlHits}" name="saddr">
		<table class="transTable">
			<tr>
				<td>
					<label>Id</label>
				</td>
				<td>
					<s:input path="intId" id="txtId" cssClass="searchTextBox numeric positive-integer" ondblclick="funHelp('oneDayPass')" /> 
				<td>
					<label>Date</label>
				</td>
				<td>
					<s:input path="dteDate" id="txtDate" required="required" cssClass="calenderTextBox" />
				</td>
			</tr>
			<td>
				<label>From No</label>
			</td>

			<td>
				<s:input path="intFromNo" id="txtFromNo" cssClass="longTextBox BoxW124px numeric positive-integer" />
			</td>
			<td>
				<label>To No</label>
			</td>
			<td>
				<s:input type="text" path="intToNo" id="txtToNo" cssClass=" BoxW124px numeric positive-integer " required="true" />
			</td>
			</tr>
		</table>
		<br />
		<br />
		<br/>
		<br/>
		<p align="center">
			<input type="submit" value="Submit" class="form_button" />
			<input type="reset" value="Reset" class="form_button" />
		</p>

	</s:form>
	<script type="text/javascript">
		funApplyNumberValidation();
	</script>
</body>
</html>