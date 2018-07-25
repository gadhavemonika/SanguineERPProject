<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

<script type="text/javascript">
	var fieldName;
	
	//Initialize tab Index or which tab is Active
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
			
		$(document).ajaxStart(function(){
		    $("#wait").css("display","block");
		});
		$(document).ajaxComplete(function(){
		   	$("#wait").css("display","none");
		});
	});
	
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
	        		$("#txtRoomCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtRoomCode").val(response.strRoomCode);
	        		$("#txtRoomDesc").val(response.strRoomDesc);
	        		$("#cmbRoomType").val(response.strRoomTypeCode);
	        		funSetRoomType(response.strRoomTypeCode);
	        		$("#txtFloorCode").val(response.strFloorCode);
	        		$("#txtBedType").val(response.strBedType);
	        		$("#txtFurniture").val(response.strFurniture);
	        		$("#txtExtraBed").val(response.strExtraBed);
	        		$("#txtUpholstery").val(response.strUpholstery);
	        		$("#txtLocation").val(response.strLocation);
	        		$("#txtColourScheme").val(response.strColourScheme);
	        		$("#txtPolishType").val(response.strPolishType);
	        		$("#txtGuestAmenities").val(response.strGuestAmenities);
	        		$("#txtInterConnectRooms").val(response.strInterConnectRooms);
	        		$("#txtBathTypeCode").val(response.strBathTypeCode);
	        		
	        		if(response.strAccountCode!="" && response.strAccountCode!="NA" )
	        			{
	        				funSetAccountCode(response.strAccountCode);
	        			}
	        		
	        		
	        		if(response.strProvisionForSmokingYN=="Y")
	        		{
	        			$("#rdbProvisionForSmokingYN").prop('checked',true);
	        		}
	        		else
	        		{
	        			$("#rdbProvisionForSmokingYN").prop('checked',false);
	        		}
	        		if(response.strDeactiveYN=="Y")
	        		{
	        			$("#rdbDeactiveYN").prop('checked',true);
	        		}
	        		else
	        		{
	        			$("#rdbDeactiveYN").prop('checked',false);
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
	
	
	
	function funSetRoomType(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadRoomTypeMasterData.html?roomCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strRoomTypeCode=='Invalid Code')
	        	{
	        		alert("Invalid Room Type");
	        		$("#txtRoomType").val('');
	        	}
	        	else
	        	{
	        		$("#txtRoomType").val(response.strRoomTypeCode);
	        		$("#lblRoomType").text(response.strRoomTypeDesc);
	        	}
			},
			error : function(e){
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
	
	
	
	/**
	linked account code
	**/
	function funSetAccountCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/getAccountMasterDtl.html?accountCode=" + code,
			dataType : "json",			
			success : function(response)
			{
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtAccountCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtAccountCode").val(response.strAccountCode);
	        		$("#txtAccountName").val(response.strAccountName);
	        	}
			},
			error : function(jqXHR, exception)
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
	
	
	
	/**
	* Get and Set data from help file and load data Based on Selection Passing Value(Extra Bed Code)
	**/
	
	function funSetExtraBed(code)
	{
		$("#txtExtraBed").val(code);
		var searchurl=getContextPath()+"/loadExtraBedMasterData.html?extraBedCode="+code;
		$.ajax({
			type: "GET",
			url: searchurl,
			dataType: "json",
			success: function(response)
			{
				if(response.strExtraBedTypeCode=='Invalid Code')
			    {
			    	alert("Invalid ExtraBed Code");
			    	$("#txtExtraBed").val('');
			    }
			    else
			    {
			    	$("#lblExtraBed").text(response.strExtraBedTypeDesc);					
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
	
	
	

	/**
	* Success Message After Saving Record
	**/
	$(document).ready(function()
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

	function funSetData(code) 
	{

		switch (fieldName) 
		{
			case "roomForMaster":
				 funSetRoomMasterData(code);
				 break;
			
			case "roomType":
				funSetRoomType(code);
				break;
				
			case "extraBed":
				funSetExtraBed(code);
				break;	
				
			case "accountCode":
				funSetAccountCode(code);
				break;
				
			case "floormaster":
				funSetFloorMaster(code);
				break;
				
			case "bathType":
				funSetBathType(code);
				break;
		}
	}

	function funHelp(transactionName)
	{
		fieldName = transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		//window.showModalDialog("searchform.html?formname=" + transactionName + "&searchText=", "","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	function funSetFloorMaster(code)
	{
		$("#txtFloorCode").val(code);
		var searchurl=getContextPath()+"/loadFloorMasterData.html?floorCode="+code;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strFloorCode=='Invalid Code')
			        	{
			        		alert("Invalid Business Code");
			        		$("#txtFloorCode").val('');
			        	}
			        	else
			        	{	
			        		$("#txtFloorCode").val(response.strFloorCode);
				        	$("#lblFloorName").text(response.strFloorName);
				        	
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
	
	function funSetBathType(code)
	{
		$("#txtBathTypeCode").val(code);
		var searchurl=getContextPath()+"/loadBathTypeMasterData.html?bathTypeCode="+code;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strBathTypeCode=='Invalid Code')
			        	{
			        		alert("Invalid Bath Code");
			        		$("#txtBathTypeCode").val('');
			        	}
			        	else
			        	{
			        		$("#txtBathTypeCode").val(response.strBathTypeCode);
				        	$("#lblBathTypeDesc").text(response.strBathTypeDesc);
				     
				        	
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
		<label>Room Master</label>
	</div>

	<br />
	<br />

	<s:form name="RoomMaster" method="POST" action="saveRoomMaster.html">


		<div id="tab_container" style="height: 405px">
				<ul class="tabs">
					<li data-state="tab1" style="width: 6%; padding-left: 2%;margin-left: 10%; " class="active" >General</li>
					<li data-state="tab2" style="width: 8%; padding-left: 1%">LinkUp</li>
				</ul>
							
				<!-- General Tab Start -->
				<div id="tab1" class="tab_content" style="height: 400px">
					<br> 
					<br>					
					<table class="masterTable">
						<tr>
						    <td><label>Room Code</label></td>
						    <td><s:input id="txtRoomCode" path="strRoomCode"  ondblclick="funHelp('roomForMaster')" cssClass="searchTextBox"/></td>			        			        
						    <td colspan="2"><s:input id="txtRoomDesc" path="strRoomDesc" required="true" cssClass="longTextBox"  style="width: 316px"/></td>			    		        			   
						</tr>
						
						<tr>
						    <td><label>Room Type</label></td>
						    <td><s:input id="txtRoomType" path="strRoomType"  ondblclick="funHelp('roomType')" cssClass="searchTextBox"/></td>
						    <td><label id="lblRoomType"></label></td>
						    <td colspan="1"></td>
						</tr>
						
						<tr>
							<td><label>Floor Code</label></td>
							<td><s:input id="txtFloorCode" type="text" path="strFloorCode" cssClass="searchTextBox" ondblclick="funHelp('floormaster')" />
								<label id="lblFloorName"></label>
							</td>
						    <td><label>Bed Type</label></td>			    
							<td><s:input id="txtBedType" path="strBedType"  cssClass="longTextBox" style="width: 190px"/></td>
						
						</tr>
						
						<tr>				
							<td><label>Furniture</label></td>
							<td><s:input id="txtFurniture" path="strFurniture"  cssClass="longTextBox" style="width: 190px"/></td>
							<td><label>Upholstery</label></td>
							<td><s:input id="txtUpholstery" path="strUpholstery"  cssClass="longTextBox" style="width: 190px"/></td>
						</tr>
						<tr>
						    <td><label>Extra Bed</label></td>
						    <td><s:input id="txtExtraBed" path="strExtraBed"  ondblclick="funHelp('extraBed')" cssClass="searchTextBox"/></td>
							<td><label id="lblExtraBed"></label></td>
						     <td colspan="1"></td>
						</tr>
						<tr>
						    <td><label>Location</label></td>
							<td><s:input id="txtLocation" path="strLocation"  cssClass="longTextBox" style="width: 190px"/></td>
							
							<td><label>Bath Type</label></td>
							<td><s:input id="txtBathTypeCode" type="text" path="strBathTypeCode" cssClass="searchTextBox" ondblclick="funHelp('bathType')" />
								<label id="lblBathTypeDesc"></label>
							</td>
						</tr>
						
						<tr>
						    <td><label>Colour Scheme</label></td>
							<td><s:input id="txtColourScheme" path="strColourScheme"  cssClass="longTextBox" style="width: 190px"/></td>
							<td><label>Polish Type</label></td>
							<td><s:input id="txtPolishType" path="strPolishType"  cssClass="longTextBox" style="width: 190px"/></td>
						</tr>
						<tr>
						    <td><label>Guest Amenities</label></td>
							<td><s:input id="txtGuestAmenities" path="strGuestAmenities"  cssClass="longTextBox" style="width: 190px"/></td>
							<td><label>Interconnect Rooms</label></td>
							<td><s:input id="txtInterConnectRooms" path="strInterConnectRooms"  cssClass="longTextBox" style="width: 190px"/></td>
						</tr>
						<tr>
						    <td><label>Provision For Smoking</label></td>
							<td>
								<s:radiobutton id="rdbProvisionForSmokingYN" path="strProvisionForSmokingYN" value="Y"  />Yes 
								<s:radiobutton id="rdbProvisionForSmokingYN" path="strProvisionForSmokingYN" value="N" checked="checked" style="margin-left: 20px;" />No
							</td>
							<td><label>Deactive</label></td>
							<td>
								<s:radiobutton id="rdbDeactiveYN" path="strDeactiveYN" value="Y" />Yes 
								<s:radiobutton id="rdbDeactiveYN" path="strDeactiveYN" value="N" checked="checked" style="margin-left: 20px;" />No
							</td>
						</tr>
					</table>
		</div>
						<!--General Tab End  -->
						
						
			<!-- Linkedup Details Tab Start -->
			<div id="tab2" class="tab_content" style="height: 400px">
			<br> 
			<br>			
				<table class="masterTable">
						<tr>
						    <td><label>Account Code</label></td>
						    <td><s:input id="txtAccountCode" path="strAccountCode" readonly="true" ondblclick="funHelp('accountCode')" cssClass="searchTextBox"/></td>
						    <td colspan="2"><s:input id="txtAccountName" path="" readonly="true" cssClass="longTextBox"  style="width: 316px"/></td>			        			        						    			    		        			  
						</tr>
				</table>
			</div>
			
		</div>
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()" />
		</p>

	</s:form>
</body>
</html>
