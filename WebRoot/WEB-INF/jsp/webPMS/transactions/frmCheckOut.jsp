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
	
	function funValidateData()
	{
		var table=document.getElementById("tblRoomDtl");
		var rowCount=table.rows.length;
		if(rowCount>0)
		{
			return true;
		}
		else
		{
			alert("Please Selet Room Detail.");
			return false;
		}
	}
	
	
	function funResetFields()
	{
		$('#tblRoomDtl tbody > tr').remove();
	}
	
	
	function fillTableRow(index,obj)
	{
		var table=document.getElementById("tblRoomDtl");
		var rowCount=table.rows.length;
		var row=table.insertRow();
	   
 	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" name=\"listCheckOutRoomDtlBeans["+(rowCount)+"].strRoomNo\" id=\"strRoomNo."+(rowCount)+"\" value='"+obj.strRoomNo+"' >";
        row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" name=\"listCheckOutRoomDtlBeans["+(rowCount)+"].strRegistrationNo\" id=\"strRegistrationNo."+(rowCount)+"\"  value='"+obj.strRegistrationNo+"' >";
		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" name=\"listCheckOutRoomDtlBeans["+(rowCount)+"].strFolioNo\" id=\"strFolioNo."+(rowCount)+"\"  value='"+obj.strFolioNo+"' >";
		row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" name=\"listCheckOutRoomDtlBeans["+(rowCount)+"].strGuestName\" id=\"strGuestName."+(rowCount)+"\"  value='"+obj.strGuestName+"' >";
		row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-right: 5px;width: 100%;   text-align: right;\" name=\"listCheckOutRoomDtlBeans["+(rowCount)+"].dblAmount\" id=\"dblAmount."+(rowCount)+"\"  value='"+obj.dblAmount+"' >";
		row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" name=\"listCheckOutRoomDtlBeans["+(rowCount)+"].dteCheckInDate\" id=\"dteCheckInDate."+(rowCount)+"\"  value='"+obj.dteCheckInDate+"' >";
		row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" name=\"listCheckOutRoomDtlBeans["+(rowCount)+"].dteCheckOutDate\" id=\"dteCheckOutDate."+(rowCount)+"\"  value='"+obj.dteCheckOutDate+"' >";
		row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" name=\"listCheckOutRoomDtlBeans["+(rowCount)+"].strCorporate\" id=\"strCorporate."+(rowCount)+"\" value='"+obj.strCorporate+"' >";
	}
	
	
	function funGetRoomDtl(roomCode)
	{
		$('#tblRoomDtl tbody > tr').remove();
		
		var searchUrl=getContextPath()+"/getRoomDtlList.html?roomCode="+roomCode;
		$.ajax({
			
			url:searchUrl,
			type :"GET",
			dataType: "json",
	        success: function(response)
	        {
	 		   $.each(response, function(index,obj)
	 		   {
	 			   fillTableRow(index,obj);
	 		   });
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
	
	
	function funSetRoomMasterData(roomCode)
	{
		 var searchUrl=getContextPath()+"/loadRoomMasterData.html?roomCode="+roomCode;
		$.ajax({
			
			url:searchUrl,
			type :"GET",
			dataType: "json",
	        success: function(response)
	        {
	        	if(response.strRoomCode=='Invalid Code')
	        	{
	        		alert("Invalid Room Code");
	        		$("#strSearchTextField").val('');
	        	}
	        	else
	        	{
	        		$("#strSearchTextField").val(response.strRoomDesc);
	        		funGetRoomDtl(response.strRoomCode);
	        		$("#strRoomNo").val(response.strRoomCode);
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

	
	/* set date values */
	function funSetDate(id,responseValue)
	{
		var id=id;
		var value=responseValue;
		var date=responseValue.split(" ")[0];
		
		var y=date.split("-")[0];
		var m=date.split("-")[1];
		var d=date.split("-")[2];
		
		$(id).val(d+"-"+m+"-"+y);		
	}	
	
	
//set date
/*
	$(document).ready(function(){
		$("#dteCheckOutDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#dteCheckOutDate").datepicker('setDate', 'today');
	});	
	*/
	
//set time
/*
    setInterval(timer, 1000);
    function timer() 
    {
		var dateObj = new Date();
	    $('#tmeCheckOutTime').val(dateObj.toLocaleTimeString());
    }
    */
    
	/**
	* Success Message After Saving Record
	**/
	$(document).ready(function()
	{
		var message='';
		<%if (session.getAttribute("success") != null) 
		{
			if(session.getAttribute("successMessage") != null)
			{%>
				message='<%=session.getAttribute("successMessage").toString()%>';
			<%
			   	session.removeAttribute("successMessage");
			}
			boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
			session.removeAttribute("success");
			if (test) 
			{
				%>
				alert("Data Save successfully\n\n"+message);
				<%
			}
		}%>
		
		 var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
		  var dte=pmsDate.split("-");
		  $("#txtPMSDate").val(dte[2]+"-"+dte[1]+"-"+dte[0]);
	});
	
	
	
	/**
		* Success Message After Saving Record
	**/

	function funSetData(code)
	{
		switch(fieldName)
		{
			case "checkInRooms":
			 	funSetRoomMasterData(code);
			 	break;
		}
	}
		
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>Check Out</label>
	</div>

<br/>
<br/>

	<s:form name="CheckOut" method="POST" action="saveCheckOut.html">

		<table class="masterTable">
		
			
			<tr>
			    <td colspan="2">
					<!-- <s:radiobutton id="strSearchTypePAX"   path="strSearchType"      style="margin-right:5px;"/>PAX
					<s:radiobutton id="strSearchTypeGroup" path="strSearchType"      style="margin-left: 20px;margin-right:5px;" />Group
					-->
					
					<s:radiobutton id="strSearchTypeRoom" path="strSearchType" style="margin-left: 20px;margin-right:5px;" />Room
					<s:input id="strSearchTextField" path="strSearchTextField" readonly="true" style="margin-left: 20px;width: 312px;background-position: 300px 2px;" cssClass="searchTextBox" ondblclick="funHelp('checkInRooms')"/>
					<s:input type="hidden" id="strRoomNo" path="strRoomNo" />
				</td>
			</tr>
			<tr>
				<td><td>
			</tr>
		</table>
		
		<table class="masterTable">
			<tr>
				<td style="width: 65px;"><label >Check Out</label></td>
	 <td style="width: 65px;"><s:input type="text" id="dteCheckOutDate" path="dteCheckOutDate" value="${PMSDate}" required="true" disabled="true" class="calenderTextBox" cssStyle="color: black;"/></td>
				<td><s:input type="text" id="tmeCheckOutTime" path="tmeCheckOutTime" value="${PMSDate}" readonly="true" disabled="true" cssClass="simpleTextBox"  style="width: 75px;border: 0px; color: black; "/></td>
			</tr>
			<!-- 
			<tr>
				<td colspan="3">
					<s:checkbox path="" id="" value="N"  label="Show Today's Departures"></s:checkbox>
				</td>
			</tr>
			 -->
		</table>
	
		<br />
		<!-- Generate Dynamic Table   -->
		<div class="dynamicTableContainer" style="height: 200px;">
			<table style="height: 28px; border: #0F0; width: 100%;font-size:11px; font-weight: bold;">
				<tr bgcolor="#72BEFC" style="height: 24px;">
					<!-- col1   -->
					<td  style="width: 65px;" align="center">Room No.</td>
					<!-- col1   -->
					
					<!-- col2   -->
					<td  style=" width:85px;" align="center">Registration No.</td>
					<!-- col2   -->
					
					<!-- col3   -->
					<td style="width: 65px;" align="center">Folio No.</td>
					<!-- col3   -->
					
					<!-- col4   -->
					<td style="width: 250px;" align="center">Guest Name</td>
					<!-- col4   -->
					
					<!-- col5   -->
					<td  style="width: 60px;"align="center" >Amount</td>
					<!-- col5   -->
					
					<!-- col6  -->
					<td  style="width: 75px;"align="center">Check-In Date</td>
					<!-- col6  -->
					
					<!-- col7   -->
					<td  style="width: 85px;"align="center">Check-Out Date</td>
					<!-- col7   -->
					
					
					<!-- col8   -->
					<td style="width: 70px;"align="center">Corporate</td>
					<!-- col8   -->
					
					<!-- col10   -->
					<td ></td>
					<!-- col10   -->
									
				</tr>
			</table>
			<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 200px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblRoomDtl" style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll" class="transTablex col9-center">
					<tbody>
						<!-- col1   -->
						<col style="width: 65px">
						<!-- col1   -->
						
						<!-- col2   -->
						<col  style="width: 85px;" >
						<!-- col2   -->
						
						<!-- col3   -->
						<col style="width: 65px;">
						<!-- col3   -->
						
						<!-- col4   -->
						<col style="width: 250px;">
						<!-- col4   -->
						
						<!-- col5   -->
						<col style="width: 60px;" >
						<!-- col5   -->
						
						<!-- col6   -->
						<col style="width: 75px;">
						<!-- col6   -->
						
						<!-- col7   -->
						<col style="width: 85px;">
						<!-- col7   -->
						
						<!-- col8   -->
						<col style="width: 70px;">
						<!-- col8   -->
						
						<!-- col10   -->
						<col >
						<!-- col10   -->					
					</tbody>
					<%-- <c:forEach items="${command.listReqDtl}" var="reqdtl"
						varStatus="status">
						<tr>
							<td><input name="listReqDtl[${status.index}].strProdCode"
								value="${reqdtl.strProdCode}" /></td>
							<td>${reqdtl.strPartNo}</td>
							<td>${reqdtl.strProdName}</td>
							<td><input name="listReqDtl[${status.index}].dblQty"
								value="${reqdtl.dblQty}" /></td>
							<td><input name="listReqDtl[${status.index}].dblUnitPrice"
								value="${reqdtl.dblUnitPrice}" /></td>
							<td><input name="listReqDtl[${status.index}].dblTotalPrice"
								value="${reqdtl.dblTotalPrice}" /></td>
							<td><input name="listReqDtl[${status.index}].strRemarks"
								value="${reqdtl.strRemarks}" /></td>
							<td><input type="Button" value="Delete" class="deletebutton"
								onClick="Javacsript:funDeleteRow(this)" /></td>
						</tr>

					</c:forEach> --%>
				</table>
			</div>
		</div>
		<!-- Generate Dynamic Table   -->
		
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funValidateData()"/>
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
