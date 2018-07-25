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
$(document).ready(function() 
		{		
			$(".tab_content").hide();
			$(".tab_content:first").show();
	
			$("ul.tabs li").click(function() {
				$("ul.tabs li").removeClass("active");
				$(this).addClass("active");
				$(".tab_content").hide();
				var activeTab = $(this).attr("data-state");
				$("#" + activeTab).fadeIn();
			});
			
				var startDate="${startDate}";
				var arr = startDate.split("/");
				Dat=arr[2]+"-"+arr[1]+"-"+arr[0];
			    $("#txtFromDate").datepicker({ dateFormat: 'yy-mm-dd' });
				$("#txtFromDate" ).datepicker('setDate', Dat);
				$("#txtFromDate").datepicker();	
				
				$("#txtToDate").datepicker({ dateFormat: 'yy-mm-dd' });
				$("#txtToDate" ).datepicker('setDate', 'today');
				$("#txtToDate").datepicker();	
					
					
		});



function funHelp(transactionName)
{
	fieldName=transactionName;
    
   // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
   
    window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
}
function funSetData(code)
{
	switch(fieldName)
	{
	
	case 'locationFrom':
	      funSetLocationFrom(code)
	      break;
	case 'locationTo':
	      funSetLocationTo(code)
	      break;
	
	}
	}
	
	function funSetLocationFrom(code)
	{
		
		var searchUrl="";
		searchUrl=getContextPath()+"/loadLocationFrom.html?locCode="+code;			
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	
					    	
					    	if(response.strLocCode=='Invalid Code')
					       	{
					       		alert("Invalid Location Code");
					       		$("#txtFromLocation").val('');
					       		$("#lblFromLocation").text("");
					       		$("#txtFromLocation").focus();
					       	}
					       	else
					       	{
					    	$("#txtFromLocation").val(response.strLocCode);
			        		$("#lblFromLocation").text(response.strLocName);	
			        		$("#txtFromLocation").focus();
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
	
	
	function funSetLocationTo(code)
	{
		
		var searchUrl="";
		searchUrl=getContextPath()+"/loadLocationTo.html?locCode="+code;			
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	if(response.strLocCode=='Invalid Code')
			       	{
			       		alert("Invalid Location Code");
			       		$("#txtToLocation").val('');
			       		$("#lblToLocation").text("");
			       		$("#txtToLocation").focus();
			       	}
			       	else
			       	{
			    	$("#txtToLocation").val(response.strLocCode);
	        		$("#lblToLocation").text(response.strLocName);	
	        		$("#txtToLocation").focus();
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
<body>
	<div id="formHeading">
		<label>Issue Listing Indigeous</label>
	</div>
	<s:form name="frmIssueListingIndigeous" method="GET"
		action="rptIssueListingIndigeous.html" >
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		<table class="transTable">


			<tr>
				<td><label>From Date</label>
				<td><s:input path="dtFromDate" id="txtFromDate"
						required="required" cssClass="calenderTextBox" /></td>
			
			</tr><tr>	<td><label>To Date</label>
				<td><s:input path="dtToDate" id="txtToDate" required="required"
						cssClass="calenderTextBox" /></td>
			</tr>


			<tr>
			

				<td width="110px"><label>From Location</label></td>
				<td><s:input path="strDocCode" id="txtFromLocation"
						ondblclick="funHelp('locationFrom')" cssClass="searchTextBox"></s:input>
					<label id=lblFromLocation></label></td>
                  </tr>
                  <tr> <td><label>To Location</label></td>
				<td><s:input path="strDocCode" id="txtToLocation"
						ondblclick="funHelp('locationTo')" cssClass="searchTextBox" /><label id="lblToLocation"></label>
					</td>




			</tr>



		</table>
		<br>
		<p align="center">
			<input type="submit" value="Submit"
				onclick=""
				class="form_button" /> &nbsp; &nbsp; &nbsp; <a
				STYLE="text-decoration: none"
				href="frmIssueListingIndigeous.html?saddr=${urlHits}"><input
				type="button" id="reset" name="reset" value="Reset"
				class="form_button" /></a>
		</p>
		<br>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
	</s:form>

</body>
</html>