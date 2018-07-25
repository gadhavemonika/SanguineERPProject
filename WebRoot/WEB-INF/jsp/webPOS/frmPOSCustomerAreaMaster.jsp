<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
var fieldName="";
 	 $(document).ready(function () {
	      $('input#txtCustomerAreaCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtCustomerAreaName').mlKeyboard({layout: 'en_US'});
		  $('input#txtcustomerAddress').mlKeyboard({layout: 'en_US'});
		  $('input#txtcustomerHomeDeliveryCharges').mlKeyboard({layout: 'en_US'});
		  $('input#txtcustomerZone').mlKeyboard({layout: 'en_US'});
		  $('input#txtcustomerDeliveryBoyPayOut').mlKeyboard({layout: 'en_US'});
		  $('input#txtcustomerHelperPayOut').mlKeyboard({layout: 'en_US'});
		  $('input#txtAmount').mlKeyboard({layout: 'en_US'});
		  $('input#txtAmount1').mlKeyboard({layout: 'en_US'});
		  $('input#txtDeliveryCharges').mlKeyboard({layout: 'en_US'});
		  
		}); 
 	    	 
		
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
	
	  	 function funDeleteTableAllRows()
		 {
		 	$('#tbldata tbody').empty();
		 	var table = document.getElementById("tbldata");
		 	var rowCount1 = table.rows.length;
		 	if(rowCount1==0){
		 		return true;
		 	}else{
		 		return false;
		 	}
		 }
 	 
 	 
 	function funHelp(transactionName)
	{	   
 		fieldName=transactionName;
       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
    }
 	function funAddRow()
	{
 		 
	 
		if(!funCheckNull($("#txtAmount").val(),"From Product"))
		{
			$("#txtAmount").focus();
			return false;
		} 
		
		if(!funCheckNull($("#txtAmount1").val(),"To Product"))
		{
			$("#txtAmount1").focus();
			return false;
		} 
		
		if(!funCheckNull($("#txtDeliveryCharges").val(),"Delivery Charges"))
		{
			$("#txtDeliveryCharges").focus();
			return false;
		} 
		
		if(!funCheckNull($("#txtCustomerType").val(),"Customer Type"))
		{
			$("#txtCustomerType").focus();
			return false;
		}
		
	/* 	if(!funValidateNumeric($("#txtAmount").val()))
		{
			$("#txtAmount").focus();
			return false;
		} */
		
	    if(tableDataValidation())
    	{
		   var table = document.getElementById("tbldata");
		  var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount); 
		var fromAmount = $("#txtAmount").val();
	    var toAmount = $("#txtAmount1").val();
	    var deliveryCharges = $("#txtDeliveryCharges").val();
	    var customerType = $("#txtCustomerType").val();
	
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\"  size=\"50%\" name=\"listCustAreaAmount["+(rowCount)+"].dblAmount\" id=\"dblAmount\" value='"+fromAmount+"'>";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box abc\"  size=\"50%\" name=\"listCustAreaAmount["+(rowCount)+"].dblAmount1\" id=\"dblAmount1\" value='"+toAmount+"'>";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listCustAreaAmount["+(rowCount)+"].dblDeliveryCharges\" id=\"dblDeliveryCharges."+(rowCount)+"\" value='"+deliveryCharges+"'>";
	    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listCustAreaAmount["+(rowCount)+"].strCustomerType\" id=\"strCustomerType\" value='"+customerType+"'>";
	    /* row.insertCell(0).innerHTML= "<input name=\"listCustAreaAmount["+(rowCount)+"].dblAmount\" readonly=\"readonly\" class=\"decimal-places-amt\" size=\"15%\" id=\"txtAmount."+(rowCount)+"\" value="+fromAmount+">";		    
	    row.insertCell(1).innerHTML= "<input name=\"listCustAreaAmount["+(rowCount)+"].dblAmount1\" readonly=\"readonly\" class=\"decimal-places-amt\" size=\"30%\" id=\"txtAmount1."+(rowCount)+"\" value='"+toAmount+"'/>";
	    row.insertCell(2).innerHTML= "<input name=\"listCustAreaAmount["+(rowCount)+"].dblDeliveryCharges\" readonly=\"readonly\" class=\"Box\" size=\"30%\" id=\"txtDeliveryCharges."+(rowCount)+"\" value='"+deliveryCharges+"'/>";
	    row.insertCell(3).innerHTML= "<input name=\"listCustAreaAmount["+(rowCount)+"].strCustomerType\" readonly=\"readonly\" class=\"Box\" size=\"30%\" id=\"txtCustomerType."+(rowCount)+"\" value='"+customerType+"'/>"; */
	    row.insertCell(4).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
	    //funApplyNumberValidation();
	    //funResetFields();
	    	}
	    return false;
	}
 	function funDeleteRow(obj)
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tbldata");
	    table.deleteRow(index);
	}
	 
		
		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(Group Code)
		**/
		function funSetDataArea(code)
		{
			if(funDeleteTableAllRows())
				{
			$("#txtCustomerAreaCode").val(code);
			var searchurl=getContextPath()+"/loadPOSCustomerAreaMasterData.html?POSCustomerAreaCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strCustomerTypeMasterCode=='Invalid Code')
				        	{
				        		alert("Invalid Customer Area Code");
				        		$("#txtCustomerAreaCode").val('');
				        	}
				        	
				        	else
				        	{
					        	$("#txtCustomerAreaCode").val(code);
					        	$("#txtCustomerAreaName").val(response.strCustomerAreaName);
					        	$("#txtCustomerAreaName").focus();
					        	$("#txtcustomerAddress").val(response.strAddress);
					        	$("#txtcustomerHomeDeliveryCharges").val(response.strHomeDeliveryCharges);
					        	$("#txtcustomerZone").val(response.strZone);
					        	$("#txtcustomerDeliveryBoyPayOut").val(response.dblDeliveryBoyPayOut);
					        	$("#txtcustomerHelperPayOut").val(response.strHelperPayOut);
					        	
					        	 $.each(response.listCustAreaAmount,function(i,item){
					        	funLoadCustomerDetail(item.dblAmount,item.dblAmount1,item.dblDeliveryCharges,item.strCustomerType)
					        	 })
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
		}
		
		function tableDataValidation()
		{
			var flg=true;
			 var amount;
			 var amount1;
			 var custType;
			var fromAmount = $("#txtAmount").val();
		    var toAmount = $("#txtAmount1").val();
		    var deliveryCharges = $("#txtDeliveryCharges").val();
		    var customerType = $("#txtCustomerType").val();	
		   	    
		     			
			 $('#tbldata tr').each(function() {
			
				 amount=$(this).find("input[id='dblAmount']").val();
				 amount1=$(this).find("input[id='dblAmount1']").val();
				 custType=$(this).find("input[id='strCustomerType']").val();
						 
				 
			 });
			 if(fromAmount<=0.0 ||toAmount<=0.0 ||deliveryCharges<=0.0)
		    	{
		    	alert("Enter value Greater then 0");
		    	flg=false;
		    	}
			 if(!(amount1<fromAmount && amount1<toAmount) && customerType==custType)
				 {
				 	alert("The Amount is Already Exist");
			    	flg=false;
				 }
		  /*   var table = document.getElementById("tbldata");
			  var rowCount = table.rows.length; */
		  
		/*   $('#tbldata tr').each(function() {
				 var dblAmount=$(this).find("input[class='dblAmount']").val();
				 var dblAmount1=$(this).find("input[class='dblAmount1']").val();
				 //alert("Amount already present");				 
		  });
		   */
		  return flg;

		}
		
		
		function funLoadCustomerDetail(item0,item1,item2,item3)
		{
			var flag=false;
			var table = document.getElementById("tbldata");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);

		     
			      row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listCustAreaAmount["+(rowCount)+"].dblAmount\"  value='"+item0+"'>";
			      row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\"  name=\"listCustAreaAmount["+(rowCount)+"].dblAmount1\"  value='"+item1+"'>";	      	          					        				        				            				        		           	        			
				  row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listCustAreaAmount["+(rowCount)+"].dblDeliveryCharges\"  value='"+item2+"'>";
				  row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listCustAreaAmount["+(rowCount)+"].strCustomerType\" value='"+item3+"'>";
				  row.insertCell(4).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';	      				
		
		}
		
		function funSetDataZone(code)
		{
			$("#txtcustomerZone").val(code);
			var searchurl=getContextPath()+"/loadPOSZoneMasterData.html?POSZoneCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strZoneCode=='Invalid Code')
				        	{
				        		alert("Invalid Zone Code");
				        		$("#txtcustomerZone").val('');
				        	}
				        	else
				        	{
					        	$("#txtcustomerZone").val(response.strZoneCode);
					        
			
					        	
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
			}%>
		});

		
		

	  	function funSetData(code)
		{
			
			switch (fieldName)
			{
				case 'POSCustomerAreaMaster':
					funSetDataArea(code);						
				break;	
				
				case 'POSZoneMaster':
					funSetDataZone(code);					
				break;
				
					
			}
		}
	
 	
</script>



</head>
<body>
       
     <div id="formHeading">
		<label>Customer Area</label>
			</div>
		<br/>
<br/>
<br> 
		<br>
<s:form name="customerArea" method="POST" action="savePOSCustomerAreaMaster.html?saddr=${urlHits}" >
	<div id="tab_container" style="height: 505px">
				<ul class="tabs">
					<li data-state="tab1" style="width: 12%; padding-left: 2%;margin-left: 10%; " class="active" >Area Master</li>
					<li data-state="tab2" style="width: 10%; padding-left: 1%">Delivery Charges</li>
			
				</ul>
				
				<div id="tab1" class="tab_content" style="height: 400px">
	

		<div id="jquery-script-menu">

		</div>

			<table class="masterTable">

			<tr>
				<td width="140px">Area Code  </td>
				<td><s:input id="txtCustomerAreaCode" path="strCustomerAreaCode"
						cssClass="searchTextBox" ondblclick="funHelp('POSCustomerAreaMaster')" /></td>
			</tr>
			<tr>
				<td width="140px">Area Name   </td>
				<td><s:input id="txtCustomerAreaName" path="strCustomerAreaName"  required="true"
				 cssStyle="text-transform: uppercase;" cssClass="BoxW116px" /></td>
			</tr>
			<tr>
				<td width="140px">Address </td>
				<td><s:input id="txtcustomerAddress" path="strAddress" required="true"
				 cssStyle="text-transform: uppercase;" cssClass="longTextBox" /></td>
			</tr>
			
			<tr>
				<td width="140px">Home Delivery Charges </td>
				<td><s:input id="txtcustomerHomeDeliveryCharges" path="strHomeDeliveryCharges" 
				 cssStyle="text-transform: uppercase;" cssClass="BoxW116px" /></td>
			</tr>
			
			<tr>
				<td width="140px">Zone </td>
				<td><s:input id="txtcustomerZone" path="strZone" 
				 cssStyle="text-transform: uppercase;" 	cssClass="searchTextBox" ondblclick="funHelp('POSZoneMaster')"/></td>
			</tr>
			
			<tr>
				<td width="140px">Delivery Boy Pay Out </td>
				<td><s:input id="txtcustomerDeliveryBoyPayOut" path="dblDeliveryBoyPayOut" 
				 cssStyle="text-transform: uppercase;" cssClass="BoxW116px" /></td>
			</tr>
			
			<tr>
				<td width="140px">Helper Pay Out  </td>
				<td><s:input id="txtcustomerHelperPayOut" path="strHelperPayOut" 
				 cssStyle="text-transform: uppercase;" cssClass="BoxW116px" /></td>
			</tr>
			 	</table>
           </div>
		
		<div id="tab2" class="tab_content" style="height: 400px">
		
			<table class="masterTable">
		<tr>
				 <td>
					<label>Amount</label>
				</td> 
			
				<td colspan="4">
					<s:input  type="number" id="txtAmount" path="dblAmount" cssClass="BoxW124px"  />
			
					<s:input colspan="3" type="number" id="txtAmount1" path="dblAmount1" cssClass="BoxW124px"  />
				</td>
			</tr>
			
			 <tr>
			 <td colspan="2">Delivery Charges  </td>
				<td  >
				<s:input id="txtDeliveryCharges" type="number" path="dblDeliveryCharges" 
				 cssStyle="text-transform: uppercase;" cssClass="BoxW116px" />
				 </td>
				<td >
					<label>Customer Type</label>
				</td>
				<td >
					
					 <s:select id="txtCustomerType" path="strCustomerType" cssClass="BoxW124px" >
				    			<option selected="selected" value="Member">Member</option>
			        		</s:select>
				</td>
				</tr>
				
			<tr><td colspan="5"></td></tr>
			<tr>
			
			 <td colspan="5">
			 <input id="btnAdd" type="button" class="smallButton" value="Add" onclick="funAddRow();"></input>
			    &nbsp;&nbsp;&nbsp;&nbsp;
			    &nbsp;&nbsp;&nbsp;&nbsp;
			
			<input type="reset" value="Reset" class="smallButton" onclick="funResetFields()"/></td>
			</tr>
						
			</table>
						<br>
						<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 80%;">
									<table id="tblData1"
										style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
										class="transTablex col4-center">
										<th >
									<th style="border: 1px white solid;width:30%"><label>From Bill Amount</label></th>
									<th style="border: 1px  white solid;width:30%"><label>To Bill Amount</label></th>
									<th style="border: 1px  white solid;width:30%"><label>Delivery Charges</label></th>
										<th style="border: 1px  white solid;width:30%"><label>Customer Type</label></th>
									<th style="border: 1px  white solid;width:10%"><label>Delete</label></th>
								</th>
								</table>
								
								
									
									<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 110%;">
					<table id="tbldata"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">	
									    
											<col style="width:25%"><!--  COl1   -->
											<col style="width:25%"><!--  COl2   -->
											<col style="width:25%"><!--  COl3   -->
											<col style="width:25%"><!--  COl4   -->
											<col style="width:18%"><!--  COl5   -->
											
																			
															
									</table>
									</div>
									
								</div>
	
		</table>		
		</div>
	</div>
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button"/> 
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
</s:form>  
       
       
       
</body>
</html>