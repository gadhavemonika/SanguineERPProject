<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
	<script type="text/javascript" src="<spring:url value="/resources/js/jQuery.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>	
	<script type="text/javascript" src="<spring:url value="/resources/js/validations.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/pagination.js"/>"></script>
        <!-- Load data to paginate -->
	<link rel="stylesheet" href="<spring:url value="/resources/css/pagination.css"/>" />
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
var PndNonStkData;
var GRNCode="";
$(function() 
		{
			$(document).ajaxStart(function()
			{
			    $("#wait").css("display","block");
			});
			$(document).ajaxComplete(function()
			{
				$("#wait").css("display","none");
			});
			
			
			$("#lblLocName").text('<%=session.getAttribute("locationName").toString()%>');
			
			funCallPendingNonStockable();	
			
			$('#txtLocCode').blur(function() {
				var code = $('#txtLocCode').val();
				if(code.trim().length > 0 && code !="?" && code !="/"){
					funSetLocation(code);
				}
			});
			
			
			$("#btnExecute").click(function( event )
			{
				funCallPendingNonStockable();	
			});
					
		});
		
	function showTable()
	{
		var optInit = getOptionsFromForm();
	    $("#Pagination").pagination(PndNonStkData.length, optInit);
	   
	}
	
	var items_per_page = 10;
	function getOptionsFromForm(){
	    var opt = {callback: pageselectCallback};
		opt['items_per_page'] = items_per_page;
		opt['num_display_entries'] = 10;
		opt['num_edge_entries'] = 3;
		opt['prev_text'] = "Prev";
		opt['next_text'] = "Next";
	    return opt;
	}	
	
	function pageselectCallback(page_index, jq)
	{
	    // Get number of elements per pagionation page from form
	    var max_elem = Math.min((page_index+1) * items_per_page, PndNonStkData.length);
	    var newcontent="";
	    newcontent = '<table id="tblPndNonStkable" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">GRN Code</td><td id="labld2">Date</td><td id="labld3">Supplier Name</td><td id="labld4">Location Name</td><td id="labld5">Bill No.</td><td id="labld6">Narration</td><td id="labld7">Tax Amt</td><td id="labld8">Total Amt</td></tr>';
		    	// Iterate through a selection of the content and build an HTML string
			    for(var i=page_index*items_per_page;i<max_elem;i++)
			    {
			    	GRNCode=PndNonStkData[i][0];
			    	newcontent += '<tr><td><a href="#" onclick="funCheckGRNCode()" >'+PndNonStkData[i][0]+'</td>';
			        newcontent += '<td>'+PndNonStkData[i][1]+'</td>';
			        newcontent += '<td>'+PndNonStkData[i][2]+'</td>';
			        newcontent += '<td>'+PndNonStkData[i][3]+'</td>';
			        newcontent += '<td>'+PndNonStkData[i][4]+'</td>';
			        newcontent += '<td>'+PndNonStkData[i][5]+'</td>';
			        newcontent += '<td align="right">'+PndNonStkData[i][6]+'</td>';
			        newcontent += '<td align="right">'+PndNonStkData[i][7]+'</td></tr>';
			    }
	    newcontent += '</table>';
	   // Replace old content with new content
	   $('#Searchresult').html(newcontent);
	  
	   // Prevent click eventpropagation
	   return false;
	}
	
	function funCheckGRNCode()
	{
		var searchUrl=getContextPath()+"/CheckGRNCode.html?GRNCode="+GRNCode;
		//alert(searchUrl);
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "text",
			    success: function(response)
			    {
			    	//alert(response);
			    	if(response=="false")
			    		{
			    			window.open("frmNonStkMIS.html?GRNCode="+GRNCode,'_blank');
			    		}
			    	else
			    		{
			    			alert("Issue Already done");
			    			funCallPendingNonStockable();	
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
	
	function funCallPendingNonStockable()
	{			
		var locCode=$("#txtLocCode").val();
		var searchUrl=getContextPath()+"/loadPendingNonStockable.html?locCode="+locCode;
		//alert(searchUrl);
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	
			    	PndNonStkData=response;
			    	showTable();
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
	 //   window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;top=500,left=500")
	}
	function funSetData(code)
	{
		switch (fieldName) 
		{
		    case 'locationmaster':
		    	funSetLocation(code);
		        break;
		}
	}
	function funSetLocation(code)
	{
		var searchUrl="";
		searchUrl=getContextPath()+"/loadLocationMasterData.html?locCode="+code;			
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	if(response.strLocCode=='Invalid Code')
			       	{
			       		alert("Invalid Location Code");
			       		$("#txtLocCode").val('');
			       		$("#lblLocName").text("");
			       		$("#txtLocCode").focus();
			       	}
			       	else
			       	{
			    	$("#txtLocCode").val(response.strLocCode);
	        		$("#lblLocName").text(response.strLocName);	
	        		$("#txtProdCode").focus();
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
<body>
<div id="formHeading">
		<label>Pending Non Stockable MIS</label>
	</div>
	<br>
	<s:form>
			<table class="transTable">			
			    <tr>
					<td width="10%"><label >Location Code</label></td>
					<td width="9%">
					<s:input id="txtLocCode" path="strDocCode" value="${locationCode}" ondblclick="funHelp('locationmaster')" cssClass="searchTextBox" cssStyle="width:70px;background-position: 60px 4px;"/>
					</td >
					<td width="25%"><label id="lblLocName" ></label>
					</td>
					<td><input id="btnExecute" type="button" class="form_button1" value="EXECUTE"/></td>
				</tr>
			</table>
		<br>
			<dl id="Searchresult" style="padding-left: 26px;overflow:auto;width: 95%"></dl>
		<div id="Pagination" class="pagination" style="padding-left: 26px;"></div>
		
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
	</s:form>
	
</body>
</html>