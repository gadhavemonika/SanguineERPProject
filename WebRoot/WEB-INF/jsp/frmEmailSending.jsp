 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jQuery.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:url value="/resources/css/design.css"/>" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Email</title>
<script type="text/javascript">
//From Close after Pressing ESC Button
	window.onkeyup = function (event) {
		if (event.keyCode == 27) {
			window.close ();
		}
	}
</script>
<script type="text/javascript">
	var fieldName="";
	$(document).ready(function(){
		  $(document).ajaxStart(function(){
		    $("#wait").css("display","block");
		  });
		  $(document).ajaxComplete(function(){
		    $("#wait").css("display","none");
		  });
		 
		});
	 
	function funOnLoad()
	{
		 var strPOCode='<%=request.getParameter("POCode") %>' 
			  $("#txtPOCode").val(strPOCode);
	}
	/**
	 * Open help windows
	 */
	function funHelp(transactionName)
	{
		fieldName = transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1100px;dialogLeft:200px;")
	window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1100px;dialogLeft:200px;")
		
	}
	function funSetData(code)
	{
		switch (fieldName)
		{
		    case 'purchaseorder':
		    	$("#txtPOCode").val(code);
		        break;
		}
	}


	//Sending Email With Validation After Clicking Submit Button
	function btnSubmit_onClick() 
	{
		if($("#txtPOCode").val().trim().length==0)
			{
				alert("Please Enter PO Code or Search");
				$("#txtPOCode").focus();
				return false;
			}
		else
			{
				funSendEmail();
			}
	}
	function funSendEmail()
	{
		var form = $('#frmEmail');
		$.ajax({
				type: "POST",
			    url: $("#frmEmailSending").attr("action"),
			    data:$("#frmEmailSending").serialize(),
			    async: false,
			    context: document.body,
			    dataType: "text",
		        success: function(response)
		        {
		        	alert(response);
		        	window.close();
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
	function btnClose_onClick()
	{
		window.close();
	}
</script>
</head>
<body onload="funOnLoad();">
<div id="formHeading">
		<label>Send E-mail</label>
	</div>
	<br>
        <form method="post" name="frmEmailSending" id="frmEmailSending" action="sendPOEmail.html" >
            <table class="transTable">
                <tr>
                    <td width="100px"><label>PO Code</label></td>
					<td width="140px"><input name="strPOCode" id="txtPOCode" ondblclick="funHelp('purchaseorder')"
						Class="searchTextBox"></input>
					</td>
                </tr>
                <tr>
                    <td>Subject:</td>
                    <td><input type="text" name="subject" id="txtsubject" size="65" class="longTextBox"/></td>
                </tr>
                <tr>
                    <td>Message:</td>
                    <td><textarea cols="50" rows="10" name="message"></textarea></td>
                </tr>
               <!--  <tr>
                    <td>Attach file:</td>
                    <td><input type="file" name="attachFile" size="60" /></td>
                </tr>   -->          
                <tr>
                    <td colspan="2" align="center">
                        <input type="button" value="Send" class="form_button"  onclick="return btnSubmit_onClick() " />
                        <input type="button" value="Close" class="form_button"  onclick="btnClose_onClick() " />
                    </td>
                </tr>
            </table>
        </form>
</body>
</html>