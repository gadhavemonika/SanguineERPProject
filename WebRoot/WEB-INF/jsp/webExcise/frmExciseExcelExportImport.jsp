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
//Press ESC button to Close Form
	window.onkeyup = function (event) {
		if (event.keyCode == 27) {
			window.close ();
		}
	}
</script>
<script type="text/javascript">

var transactionformName;
var LicenceNo="";

//Get Project Path
	function getContextPath() 
	{
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}

    //Check From to where Link Click to Open
	function funExport()
	{
		switch(transactionformName)
		{
			case "frmExcisePhyStkPosting":
				var unitType=$("#txtInPeg").val();
				window.location.href=getContextPath()+"/ExcisePhyStkPostExcelExport.html?strInPeg="+unitType;
				break;
			case "ExcisePOSSaleDataExcelExportImport":
				window.location.href=getContextPath()+"/ExcisePOSSaleDataExport.html";
				break;
			case "ExciseBrandPriceExcelList":
				alert(getContextPath()+"/BrandPriceExcelSheet.html");
				window.location.href=getContextPath()+"/BrandPriceExcelSheet.html";
				break;	
			case "ExciseOpeningStockExportImport":
				window.location.href=getContextPath()+"/ExciseOpeningStockExport12.html";
				break;	
		}
	}

    //Check File is Excel or another format
	function funValidateFile() 
	{	
		var value=$("#File").val();
		var Extension=value.split(".");
		var ext=Extension[1];
		if(ext=="xls" || ext =="xlsx" )
			{
			 return true;
			}
		else
		{
			alert("Invalid File");
			return false;
			
		}
	}

    //After Submit Button
	function funSubmit()
	{
		if(funValidateFile()){
			
			$("#wait").css("display", "block");
			
			var jForm = new FormData();    
		    jForm.append("file", $('#File').get(0).files[0]);
		    inPeg= $("#txtInPeg").val();
		    searchUrl = getContextPath()+"/SaveExciseExcelExportImport.html?formname="+transactionformName;
	        $.ajax({
	            url : searchUrl,
	            type: "POST",
                data: jForm,
                mimeType: "multipart/form-data",
                contentType: false,
                cache: false,
                processData: false,
                dataType: "json",
	            success : function(response) 
	            {
	            	if(response[0]=="Invalid Excel File"){
	            			alert(response[1]);
            		} else if(response[0]=="Item Not Linked"){
            			alert(response[1]);
            		} else{
            			$("#wait").css("display", "none");
						window.returnValue = response;
						window.close();
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

    

    $(function(){
    		transactionformName='<%=request.getParameter("formname") %>'
    		<%-- 		LicenceNo='<%=request.getParameter("LicenceNo") %>' --%>
   				$("#formname").val(transactionformName);
    });

</script>
</head>
<body>

<s:form name="uploadExcel" id="uploadExcel" method="POST" action="SaveExciseExcelExportImport.html" enctype="multipart/form-data" >
<br>
<br>
	<table class="masterTable">
	   <tbody>
		    <tr>
			    <td class="content" bgcolor="#a6d1f6">
			    	<label>Export Excel File</label>
		    	</td>
			    <td>
				    <input type="button" id="btnExport" value="Export" class="form_button1" onclick="funExport();"/>
		    	</td>
		    	<td></td>
		    	<td>Input Unit</td> 
	    		<td>
	    			<select id="txtInPeg" class="BoxW124px" name="strInPeg">
						<option value="Peg">PegWise</option>
						<option value="ML">MLWise</option>
					</select>
				</td>
		    </tr>
		    <tr>
		    	<td colspan="5">
		    		<input type="file" id="File"  Width="50%" accept="application/vnd.ms-excel"  />
	    		</td>   
		    </tr>
	    </tbody>
	</table>
	<br>
    <p align="center">
		<input id="btnSubmit" type="button" class="form_button" value="Submit" onclick="return funSubmit();"/>
			&nbsp; &nbsp; &nbsp;
		<input id="btnReset" type="reset" value="Reset" class="form_button" onclick="funResetField()" />
	</p>
		<br><br>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
			<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
		</div>
		
</s:form>
</body>
</html>