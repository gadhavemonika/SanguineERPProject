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
	
//set Room Data
	function funSetRoomMasterData(roomCode)
	{
		var searchUrl=getContextPath()+"/loadRoomDetails.html?roomNo="+roomCode;
		$.ajax({
			
			url:searchUrl,
			type :"GET",
			dataType: "json",
	        success: function(response)
	        {
	        	if(response.strRoomCode=='Invalid Code')
	        	{
	        		alert("Invalid Room Code");
	        		$("#txtRoomNo").val('');
	        	}
	        	else
	        	{
	        		
	        		$("#txtRoomNo").val(response[0][0]);
	        		$("#txtRoomDesc").val(response[0][1]);
	        		$("#txtRegNo").val(response[0][2]);
	        		var guestName=response[0][3]+" "+response[0][4]+" "+response[0][5];
	        		$("#txtName").val(guestName);
	        		$("#txtActualPostingAmt").val(response[1]);
	        		$("#hidOriginalPostingAmt").val(response[1]);
	        		$("#hidRoomTariff").val(response[2]);
	        		$("#hidPackageAmt").val(response[3]);
	        		if(response[3]=='0')
	        		  {
	        			$("#lblCheckPkgAmt").text("Without Package Amt");
	        		  }
	        		else
	        		  {
	        			$("#lblCheckPkgAmt").text("With Package Amt");
	        		  }	
	        		if(response[1]=='0')
	        		{
	        			document.getElementById("txtActualPostingAmt").readOnly = true;
	        		}
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


	function funSetData(code)
	{
		switch(fieldName)
		{
			case "roomCodeForFolio":
				funSetRoomMasterData(code);
				break;
		}
	}
	
	
	function funValidateData()
	{
		var flg=false;
		
		if($("#txtActualPostingAmt").val()=='0')
		{
			alert("Posting amount should be greater than 0");
		}
		else
		{
			flg=true;
		}	
		return flg;
	}
	
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	
	$(document).ready(function(){
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
			}
		}%>
		
		 var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
		  var dte=pmsDate.split("-");
		  $("#txtPMSDate").val(dte[2]+"-"+dte[1]+"-"+dte[0]);
	});
	
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>Post Room Tariff</label>
	</div>

<br/>
<br/>

	<s:form name="PostRoomTerrif" method="POST" action="postRoomTerrif.html">

		<table class="masterTable">
		<tr>
			<!-- 
			    <td><label>Select Folio </label></td>
			    <td colspan="4">
			    	<s:select id="cmbFolioType" path="strFolioType"  cssClass="BoxW124px">
						<option value="Post Single Room Terrif" selected>Post Single Room Terrif</option>
						<option value="Post All Room Terrif">Post All Room Terrif</option>
					</s:select>
			    </td>
			</tr>
			-->
			
			<tr>
			    <td><label>Room</label></td>
				<td><s:input id="txtRoomNo" path="strRoomNo" ondblclick="funHelp('roomCodeForFolio')" cssClass="searchTextBox" /></td>
				<td><s:input id="txtRoomDesc" path="strRoomDesc" readonly="true" cssClass="longTextBox" /></td>
				<td><s:input id="txtActualPostingAmt" path="dblActualPostingAmt"  cssClass="longTextBox"/></td>
				<td><label id="lblCheckPkgAmt"></label></td>
				<td></td>
			</tr>
			
			<tr>
			    <td><label>Registration No</label></td>
				<td><s:input id="txtRegNo" path="strRegistrationNo" readonly="true" cssClass="longTextBox"/></td>
				<td colspan="3"></td>
			</tr>
			
			<tr>
			    <td><label>Name</label></td>
				<td><s:input id="txtName" path="strGuestName" readonly="true" cssClass="longTextBox"  /></td>
			    <td colspan="3"></td>
			</tr>
		</table>
		
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funValidateData()"/>
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
		<s:input type="hidden" id="hidOriginalPostingAmt" path="dblOriginalPostingAmt"></s:input>
		<s:input type="hidden" id="hidPackageAmt" path="dblPackageAmt"></s:input>
		<s:input type="hidden" id="hidRoomTariff" path="dblRoomTerrif"></s:input>
	
	</s:form>
</body>
</html>
