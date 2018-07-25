<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
 <script type="text/javascript">
 	
 
 	var fieldName="";
 	 $(document).ready(function () {
		  $('input#txtCustomerCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtAddress').mlKeyboard({layout: 'en_US'});
		  $('input#txtMobileNo').mlKeyboard({layout: 'en_US'});
		  $('input#txtExternalCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtCustomerName').mlKeyboard({layout: 'en_US'});
		  $('input#txtEmailId').mlKeyboard({layout: 'en_US'});
		  $('input#strCustomerType').mlKeyboard({layout: 'en_US'});
		  $('input#txtDOB').mlKeyboard({layout: 'en_US'});
		  $('input#txtArea').mlKeyboard({layout: 'en_US'});
		  $('input#strGender').mlKeyboard({layout: 'en_US'});
		  $('input#txtBuldingCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtAnniversary').mlKeyboard({layout: 'en_US'});
		  $('input#txtStreetName').mlKeyboard({layout: 'en_US'});
		  $('input#txtLandmark').mlKeyboard({layout: 'en_US'});
		  $('input#txtPinCode').mlKeyboard({layout: 'en_US'});
		  $('input#strCity').mlKeyboard({layout: 'en_US'});
		  $('input#txtOfficeBuildingCode').mlKeyboard({layout: 'en_US'});
		  $('input#strOfficeCity').mlKeyboard({layout: 'en_US'});
		  $('input#txtOfficeBuildingName').mlKeyboard({layout: 'en_US'});
		  $('input#txtOfficeNo').mlKeyboard({layout: 'en_US'});
		  $('input#txtOfficeStreetName').mlKeyboard({layout: 'en_US'});
		  $('input#txtOfficeArea').mlKeyboard({layout: 'en_US'});
		  $('input#txtOfficePinCode').mlKeyboard({layout: 'en_US'});
		  $('input#strOfficeState').mlKeyboard({layout: 'en_US'});
		
		  
		});  
 	 
 	 // Calender Date Picker
 	 $(function() {
         $( "#txtAnniversary" ).datepicker();   
         $( "#txtDOB" ).datepicker();   
         
    }); 
		
 	
		// Success Message After Saving Record
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
					%>alert("Data Saved \n\n"+message);<%
				}
				if (session.getAttribute("frmName") != null) 
				{
					if(session.getAttribute("frmName").equals("frmPOSRestaurantBill"))
		            {
						session.removeAttribute("frmName");
		            %>
		            
						funPreviousForm(message);
		           <% }
				}
			}%>
			

			  $("form").submit(function(event){
				  if (mobilenumber())
					  {				  					  
				  if($("#txtExternalCode").val().trim()=="")
					{
						alert("Please External Code");
						return false;
					}
				 
				 
				  else{
					  flg=funCallFormAction();
					  return flg;
				  }
					  }
				  else
					  {
					  return false;
					  }
				  			 
					 
				});
		});
 	
		function mobilenumber() {
			var flg=true;
	    if(document.getElementById('txtMobileNo').value != ""){

	       var y = document.getElementById('txtMobileNo').value;
	       if(isNaN(y)||y.indexOf(" ")!=-1)
	       {
	          alert("Invalid Mobile No.");
	          document.getElementById('txtMobileNo').focus();
	          flg=false;
	       }

	       if (y.length>10 || y.length<10)
	       {
	            alert("Mobile No. should be 10 digit");
	            document.getElementById('txtMobileNo').focus();
	            flg=false; 
	       }
	       if (!(y.charAt(0)=="9" || y.charAt(0)=="8" || y.charAt(0)=="7"))
	       {
	            alert("Mobile No. should start with 9 ,8 or 7 ");
	            document.getElementById('txtMobileNo').focus();
	            flg=false;
	       }

	    }
	    return flg;
	    	

	    }

 	 
 	 
		 function funCallFormAction() 
			{
				var flg=true;
				
				var strCustCode=$('#txtCustomerCode').val();
				
					var code = $('#txtExternalCode').val();
					 $.ajax({
					        type: "GET",
					        url: getContextPath()+"/checkExternalNo.html?strExternalNo="+code+"&strCustCode="+strCustCode,
					        async: false, 
					        dataType: "text",
					        success: function(response)
					        {
					        	if(response=="false")
					        		{
					        			alert("External Code Already Exist!");
					        			$('#txtExternalCode').focus();
					        			flg= false;
						    		}
						    	else
						    		{
						    			flg=true;
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
				
				
				
				return flg;
			}
 	 
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
 	
 	
 	
 	
 	
 	function funHelp(transactionName)
 
	{	
 		fieldName=transactionName;
 		// window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
    }
 	
		
		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(Customer Code)
		**/
		function funSetDataCustomer(code)
		{
			$("#txtCustomerCode").val(code);
			var searchurl=getContextPath()+"/loadPOSCustomerMasterData.html?POSCustomerCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strCustomerTypeMasterCode=='Invalid Code')
				        	{
				        		alert("Invalid Customer  Code");
				        		$("#txtCustomerCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtCustomerCode").val(response.strCustomerCode);
					        	$("#txtExternalCode").val(response.strExternalCode);
					        	$("#txtMobileNo").val(response.intlongMobileNo);
					        	$("#txtEmailId").val(response.strEmailId);
					        	$("#txtCustomerName").val(response.strCustomerName);
					        	$("#txtCustomerName").focus();
					        	$("#txtCustomerType").val(response.strCustomerType);
					        	$("#txtDOB").val(response.dteDOB);
					        	$("#txtBuldingCode").val(response.strBuldingCode);
					        	$("#txtArea").val(response.strBuildingName);
					        	$("#txtGender").val(response.strGender);	
					        	$("#txtAnniversary").val(response.dteAnniversary);
					        	$("#txtAddress").val(response.strArea);
					        	$("#txtStreetName").val(response.strStreetName);
					        	$("#txtLandmark").val(response.strLandmark);
					        	$("#txtPinCode").val(response.intPinCode);
					        	$("#strCity").val(response.strCity);
					        	$("#strState").val(response.strState);
					        	$("#txtOfficeBuildingCode").val(response.strOfficeBuildingCode);
					        	$("#txtOfficeBuildingName").val(response.strOfficeBuildingName);
					        	$("#txtOfficeNo").val(response.strOfficeNo);
					        	$("#txtOfficeStreetName").val(response.strOfficeStreetName);
					        	$("#txtOfficeCity").val(response.strOfficeCity);
					        	$("#txtOfficeArea").val(response.strOfficeArea);
					        	$("#txtOfficeState").val(response.strOfficeState);
					        	$("#txtOfficePinCode").val(response.strOfficePinCode);
					        	
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
		function funSetDataBuilding(code)
		{
			$("#txtBuldingCode").val(code);
			$("#txtOfficeBuildingCode").val(code);
			var searchurl=getContextPath()+"/loadPOSCustomerAreaMasterData.html?POSCustomerAreaCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strCustomerAreaCode=='Invalid Code')
				        	{
				        		alert("Invalid Building  Code");
				        		$("#txtBuldingCode").val('');
				        	}
				        	else
				        	{
					        	//$("#txtBuldingCode").val(response.strCustomerAreaCode);
					        	$("#txtArea").val(response.strCustomerAreaName);
					        	
					        	$("#txtOfficeBuildingName").val(response.strCustomerAreaName);
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
	
		
		function funSetData(code)
		{
			
			switch (fieldName)
			{
				case 'POSCustomerMaster':
					funSetDataCustomer(code);						
				break;	
				
				case 'POSCustomerAreaMaster':
					funSetDataBuilding(code);					
				break;
				
					
			}
			
		}
		

		$(function() {
			  $('#staticParent').on('keydown', '#txtMobileNo', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()});
			  $('#staticParent').on('keydown', '#txtExternalCode', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()});
			  $('#staticParent').on('keydown', '#txtPinCode', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()});
			  $('#staticParent').on('keydown', '#txtOfficePinCode', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()});
			})

	function funPreviousForm(value) {
		window.opener.funSetData(value);
		window.close();
	}
			
			
</script>  

</head>
<body>

	<div id="formHeading">
	<label>Customer Master</label>
	</div>

<br/>
<br/><br> 
		<br>
		<div id="tab_container" style="height: 405px">
				<ul class="tabs">
					<li data-state="tab1" style="width: 12%; padding-left: 2%;margin-left: 10%; " class="active" >Customer</li>
					<li data-state="tab2" style="width: 10%; padding-left: 1%">Office</li>
			
				</ul>
 <div id="staticParent"> 							
<div id="tab1" class="tab_content" style="height: 400px">
	<s:form name="POSCustomerMaster" method="POST" action="savePOSCustomerMaster.html?saddr=${urlHits}">

		<table class="masterTable">
			<tr>
				<td>
					<label>Customer Code</label>
				</td>
				<td colspan="1">
					<s:input  type="text" id="txtCustomerCode" path="strCustomerCode" cssClass="searchTextBox" ondblclick="funHelp('POSCustomerMaster');"/>
				</td>
			<td>
					<label>ExternalCode</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtExternalCode" path="strExternalCode" cssClass="BoxW116px" />
				</td>
				
			</tr>
			
			<tr>
				<td>
					<label>Contact No</label>
				</td>
				<td>
					<s:input  type="text" class="numeric" id="txtMobileNo" path="intlongMobileNo" cssClass="BoxW116px"  />
				</td>
			<td>
					<label>EmailId</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtEmailId" path="strEmailId" cssClass="BoxW116px"  />
				</td>
				
			</tr>
			<tr>
				<td>
					<label>Customer Name</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtCustomerName" path="strCustomerName" cssClass="BoxW116px"  />
				</td>
				<td>
					<label>Gender</label>
				</td>
				<td>
					
					 <s:select id="txtGender" path="strGender" cssClass="BoxW124px" >
				    			<option selected="selected" value="Female">Female</option>
				    			<option value="Male">Male</option>
			        		</s:select>
				</td>
			
				
			</tr> 
			 <tr>
				<td>
					<label>Customer Type</label>
				</td>
				<td>
					<s:select id="txtCustomerType" path="strCustomerType" items="${CustomerType}"  cssClass="BoxW124px" />
					
				</td>
				
				<td>
					<label>DOB</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtDOB" path="dteDOB" cssClass="calenderTextBox" />
				</td>
			
				
			</tr>
			 
			
			<tr>
				 <td>
					<label>Area</label>
				</td> 
			
				<td colspan="1">
					<s:input  type="text" id="txtBuldingCode" path="strBuldingCode" cssClass="searchTextBox" ondblclick="funHelp('POSCustomerAreaMaster');"  />
			
					<s:input colspan="3" type="text" id="txtArea" path="strBuildingName" cssClass="BoxW124px" />
				</td>
				
				  <td>
                    <label>Anniversary</label>
                </td>
                <td colspan="1">
                    <s:input  type="text" id="txtAnniversary" path="dteAnniversary" cssClass="calenderTextBox"  />
                </td>
                
			</tr>
			
			
			<tr>
				<td>
					<label>Address/Flat No.</label>
				</td>
				<td colspan="3">
					<s:input  type="text" id="txtAddress" path="strArea" cssClass="longTextBox" />
				</td>
			</tr> 
			<tr>
				<td>
					<label>Street Name</label>
				</td>
				<td colspan="3">
					<s:input  type="text" id="txtStreetName" path="strStreetName" cssClass="BoxW116px" />
				</td>
		
				
			</tr>
			<tr>
				<td>
					<label>Landmark</label>
				</td>
				<td colspan="3">
					<s:input  type="text" id="txtLandmark" path="strLandmark" cssClass="BoxW116px" />
				</td>
			</tr>
			
			
			<tr>
				<td>
					<label>PinCode</label>
				</td>
				<td colspan="3">
					<s:input  type="text" class="numeric" id="txtPinCode" path="intPinCode" cssClass="BoxW116px" />
				</td>
			</tr>
			
			 <tr>
				<td>
					<label>City</label>
				</td>
				<td colspan="3">
					<s:select id="txtCity" path="strCity" items="${cityName}"  cssClass="BoxW124px" />
				</td>
				
			</tr> 
			<tr> 
			<td>
					<label>State</label>
				</td>
				<td colspan="3">
					<s:select id="txtState" path="strState" items="${stateName}"  cssClass="BoxW124px" />
				</td>
				</tr> 
		 	</table>
           </div>
           </div>
			
			<div id="staticParent"> 
			<div id="tab2" class="tab_content" style="height: 400px">
									
					<table class="masterTable">
			<tr>
				<td>
					<label>Building Name/Flat No.</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtOfficeBuildingCode" path="strOfficeBuildingCode" cssClass="searchTextBox" ondblclick="funHelp('POSCustomerAreaMaster');"/>
				
					<s:input colspan="3" type="text" id="txtOfficeBuildingName" path="strOfficeBuildingName" cssClass="BoxW116px" />
				</td>
				
				
				
				<td>
					<label>Office No</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtOfficeNo" path="strOfficeNo" cssClass="BoxW116px" />
				</td>
			
			
				
			</tr> 
			
			<tr>
				<td>
					<label>StreetName</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtOfficeStreetName" path="strOfficeStreetName" cssClass="BoxW116px"  />
				</td>
				<td>
					<label> City</label>
				</td>
				<td>
					<s:select id="txtOfficeCity" path="strOfficeCity" items="${cityName}"  cssClass="BoxW124px" />
				</td>
				
			</tr>
			
			<tr>
			<td>
					<label> Area</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtOfficeArea" path="strOfficeArea" cssClass="BoxW116px" />
				</td>
				
			
				<td>
					<label> State</label>
				</td>
				<td>
					<s:select id="txtOfficeState" path="strOfficeState" items="${stateName}"  cssClass="BoxW124px" />
					
				</td>
			
				</tr>
				<tr>
			<td >
			</td >
			<td >
			</td >
			
			<td >
					<label> PinCode</label>
				</td >
				<td colspan="3">
					<s:input  type="text" id="txtOfficePinCode" path="strOfficePinCode" cssClass="BoxW116px" />
				</td>
			
			</tr>
			
		</table>
		</div>
		</div>
	</div>
             
		
	
		 <p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p> 
<!-- </div> -->
	</s:form>
	
</body>
</html>
