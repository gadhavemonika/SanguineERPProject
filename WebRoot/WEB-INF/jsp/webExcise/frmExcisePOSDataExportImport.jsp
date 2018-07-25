<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
 <script type="text/javascript" src="<spring:url value="/resources/js/jQuery.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/validations.js"/>"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/design.css"/>" /> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Excel Import Export</title>
<script type="text/javascript">
 var transactionformName="";
 
function getContextPath() 
{
	return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

function funExport()
{
	window.location.href=getContextPath()+"/ExcisePOSDataExport.html";
}

function funValidateFile() 
{	
	var value=$("#File").val();
	var Extension=value.split(".");
	//alert("Extension\t"+Extension[1]);
	var ext=Extension[1];
	if(ext=="xls" || ext =="xlsx" )
		{
		 return true;
		}
	else
	{
		alert("Invalid File Plz Use File in xls form");
		return false;
		
	}
}

function funSubmit()
{
	if(funValidateFile())
		{
			var jForm = new FormData();    
		    jForm.append("file", $('#File').get(0).files[0]);
		    searchUrl=getContextPath()+"/saveExcisePOSDataExportImport.html?formname="+transactionformName;	
	        $.ajax({
	            url : searchUrl,
	            type: "POST",
                data: jForm,
                mimeType: "multipart/form-data",
                contentType: false,
                cache: false,
                processData: false,
	            success : function(response) 
	            {
	            	if(response !=null && response!=""){
		            	alert("Data Successfully Save");
		            	$("form").trigger("reset");
	            	}else{
	            		alert("Data Not Saved Please Check Excel File");
	            		$("form").trigger("reset");
	            	}
	            },
	            error: function(jqXHR, exception)
				{
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
	}
	
	$(document).ready(function(){
		
		transactionformName='ExcisePOSDataExcelExportImport';
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
				}else{
					%>	
					alert(message);
				<%
				}}%>
		
	});

</script>
</head>
<body>
<s:form name="ExcelImportExport" id="uploadExcel" method="POST" action="" enctype="multipart/form-data" >
<br>
<br>
	<table class="transTable">
	   <tbody>
		    <tr>
		    <td></td>
		    <td></td>
			    <td class="content" bgcolor="#a6d1f6">Export Excel File</td>
			    <td><input type="button" id="btnExport" value="Export" class="form_button1" onclick="funExport();"/></td>
		     <td></td> 
		     <td></td>
		    </tr>
		    <tr>
		     	<td></td>
		     	<td></td>
		    	<td><input type="file" id="File"  Width="50%" accept="application/vnd.ms-excel"  /></td>   
		    	<td></td> 
	    	 	<td></td>
	    	  	<td></td>
		    </tr>
	    </tbody>
	</table>
    <p align="center">
			<input id="btnSubmit" type="button" value="Submit" onclick="return funSubmit();"/>
			&nbsp; &nbsp; &nbsp;
			 <input id="btnReset" type="reset" value="Reset" class="form_button" />
	</p>
</s:form>
</body>
</html>