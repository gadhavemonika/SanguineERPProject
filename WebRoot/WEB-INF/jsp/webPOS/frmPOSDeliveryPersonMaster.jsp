<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	$(document).ready(function() 
		{
		 $('input#txtTableNo').mlKeyboard({layout: 'en_US'});
		  $('input#txtTableName').mlKeyboard({layout: 'en_US'});
		  $('input#txtPaxNo').mlKeyboard({layout: 'en_US'});
		  
		$(".tab_content").hide();
		$(".tab_content:first").show();

		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();

			var activeTab = $(this).attr("data-state");
			$("#" + activeTab).fadeIn();
		});
		
		
		  $("form").submit(function(event){
			  if($("#txtDPName").val().trim()=="")
				{
					alert("Please Enter Delivery Boy Name");
					return false;
				}
			 
			 
			  else{
				  flg=funCallFormAction();
				  return flg;
			  }
			});
		    var table = document.getElementById("tblDeliveryCharges");
		    var rowCount = table.rows.length;
		  if(rowCount<0)
		  		document.getElementById("btnRemove").disabled = true;  
		  
	});
	
	 
</script>
<script type="text/javascript">

var selectedRowIndex=0;
var areaCode, areaName;
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
	
	var fieldName;

	

	function funSetData(code){

		switch(fieldName){

			case 'POSDeliveryBoyMaster' : 
				funSetDeliveryBoyDtl(code);
				break;
			case 'POSCustomerAreaMaster':
			funSetCustomerArea(code);
				break;
		}
	}

	function funSetCustomerArea(code)
	{
		$("#txtAreaCode").val(code);
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
			        		$("#txtAreaCode").val('');
			        	}
			        	else
			        	{
			        		
				        	areaCode=code;
				        	areaName=response.strCustomerAreaName;
				        	
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
	
	function funSetDeliveryBoyDtl(code){

			$("#txtDPCode").val(code);
			var searchurl=getContextPath()+"/loadPOSDeliveryBoyMasterData.html?dpCode="+code;		
			 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strTableNo=='Invalid Code')
			        	{
			        		alert("Invalid Delivery Boy Code");
			        		$("#txtDPCode").val('');
			        	}
			        	else
			        	{
				        	$("#txtDPName").val(response.strDPName);
				        	
				        	$("#cmbOperational").val(response.strOperational);
				        
				        	$("#txtDPName").focus();
				        	funRemoveTableRows();
					    	$.each(response.listDeliveryBoyCharges, function(i,item)
							{			
					    		funAddRow1(item.strAreaCode,item.strAreaName,item.dblIncentives);
					    	});
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


	function funHelp(transactionName)
	{
		fieldName=transactionName;
		  window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	}
	
	function btnAdd_onclick() 
	{
		
		
			if($("#txtAreaCode").val()=="") 
		    {
				alert("Please select Area");
		   		
		       	return false;
			}
			else if($("#txtPayOut").val()=="")
		    {
				alert("Please Enter Incentive.");
		   		
		       	return false;
			}
			else
			{
				funAddRow();
			}
		
	}
	
	function btnRemove_onclick() 
	{
		
		
		var table = document.getElementById("tblDeliveryCharges");
	    table.deleteRow(selectedRowIndex);
		
	}

	
	function funAddRow() 
	{
		var incentives=$("#txtPayOut").val();
	    var table = document.getElementById("tblDeliveryCharges");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	   
	    if(funDuplicateArea(areaCode))
	    {
	    row.insertCell(0).innerHTML= "<input class=\"Box\" name=\"listDeliveryBoyCharges["+(rowCount)+"].strAreaCode\" size=\"30%\"  id=\"txtAreaCode."+(rowCount)+"\" value='"+areaCode+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(1).innerHTML= "<input class=\"Box\" name=\"listDeliveryBoyCharges["+(rowCount)+"].strAreaName\" size=\"40%\"  id=\"txtAreaName."+(rowCount)+"\" value='"+areaName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(2).innerHTML= "<input class=\"Box\" name=\"listDeliveryBoyCharges["+(rowCount)+"].dblIncentives\" size=\"30%\"  id=\"txtIncentives."+(rowCount)+"\" value='"+incentives+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    }	
	}


	function funAddRow1(areaCode,areaName,incentive)
	{		    		    
	    var table = document.getElementById("tblDeliveryCharges");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    	
	    
	   
	    row.insertCell(0).innerHTML= "<input class=\"Box\" name=\"listDeliveryBoyCharges["+(rowCount)+"].strAreaCode\" size=\"50%\"  id=\"txtAreaCode."+(rowCount)+"\" value='"+areaCode+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(1).innerHTML= "<input class=\"Box\" name=\"listDeliveryBoyCharges["+(rowCount)+"].strAreaName\" size=\"50%\"  id=\"txAareaName."+(rowCount)+"\" value='"+areaName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";	    	    
	    row.insertCell(2).innerHTML= "<input class=\"Box\" name=\"listDeliveryBoyCharges["+(rowCount)+"].dblIncentives\" size=\"50%\"  id=\"txtToTime."+(rowCount)+"\" value='"+incentive+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    
	}

	 function funRemoveTableRows()
		{
			var table = document.getElementById("tblDeliveryCharges");
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
		}
	//Check Duplicate Product in grid
	function funDuplicateArea(areaCode)
	{
	    var table = document.getElementById("tblDeliveryCharges");
	    var rowCount = table.rows.length;
	    var flag=true;
	    if(rowCount > 0)
    	{
		    $('#tblDeliveryCharges tr').each(function()
		    {
			    if(areaCode==$(this).find('input').val())// `this` is TR DOM element
				{
			    	alert("Already added "+ areaCode);
			    	funResetFields();
    				flag=false;
				}
			});
	    }
	    return flag;
	}
	
	function funGetSelectedRowIndex(obj)
	{
		 var index = obj.parentNode.parentNode.rowIndex;
		 var table = document.getElementById("tblDeliveryCharges");
		 if((selectedRowIndex>0) && (index!=selectedRowIndex))
		 {
			 if(selectedRowIndex%2==0)
			 {
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#A3D0F7';
				 selectedRowIndex=index;
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#ffd966';
				 row.hilite = true;
			 }
			 else
			 {
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#C0E4FF';
				 selectedRowIndex=index;
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#ffd966';
				 row.hilite = true;
	         }
			
		 }
		 else
		 {
			 selectedRowIndex=index;
			 row = table.rows[selectedRowIndex];
			 row.style.backgroundColor='#ffd966';
			 row.hilite = true;
		 }
		 
		
	}
	
	function funCallFormAction() 
		{
			var flg=true;
			var name = $('#txtDPName').val();
			var code= $('#txtDPCode').val();
			
	
				 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/checkDPName.html?name="+name+"&code="+code,
				        async: false,
				        dataType: "text",
				        success: function(response)
				        {
				        	if(response=="false")
				        		{
				        			alert("Delivery Boy Name Already Exist!");
				        			$('#txtDPName').focus();
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
</script>

</head>
<body>

	<div id="formHeading">
	<label>Delivery Boy Master</label>
	</div>

<br/>
<br/>

	<s:form name="savePOSDeliveryBoyMaster" method="POST" action="savePOSDeliveryBoyMaster.html?saddr=${urlHits}">
	<table
				style="border: 0px solid black; width: 70%; margin-left: auto; margin-right: auto;background-color:#C0E4FF;">
				<tr>
					<td>
						<div id="tab_container">
							<ul class="tabs">
								<li class="active" data-state="tab1">Delivery Boy</li>
								<li data-state="tab2">Delivery Charges</li>
								
				
							</ul>
							<br /> <br />

							<!--  Start of Generals tab-->

							<div id="tab1" class="tab_content">
								<table  class="masterTable">
																		
								<tr>
				<td>
					<label>Delivery Boy Code</label>
				</td>
				<td>	<s:input colspan="3" type="text" id="txtDPCode" path="strDPCode" cssClass="searchTextBox jQKeyboard form-control" ondblclick="funHelp('POSDeliveryBoyMaster')" />
				
			
				</td>
				</tr>
				<tr>
				<td>
					<label>Delivery Boy Name</label>
				</td>
				<td>	<s:input colspan="" type="text" id="txtDPName" path="strDPName" cssClass="longTextBox jQKeyboard form-control" />
				</td>
			</tr>
				
			<tr>
			<td><label>Operational</label></td>
				<td><s:select id="cmbOperational" name="cmbOperational" path="strOperational" cssClass="BoxW124px" >
				<option value="Y">Yes</option>
				 <option value="N">No</option>
				
				 </s:select></td>
				
			</tr>
			</table>
							</div>
							<!--  End of  Generals tab-->


							<!-- Start of Settlement tab -->

							<div id="tab2" class="tab_content">
						<table  class="masterTable">
																		
									<tr>
				<td width="140px">Area Code</td>
				<td><input id="txtAreaCode" 
						class="searchTextBox" ondblclick="funHelp('POSCustomerAreaMaster')" /></td>
			</tr>
													
			<tr>
				<td width="140px">Pay Out</td>
				<td><input type="number" id="txtPayOut" 
						class="longTextBox jQKeyboard form-control"/></td>
			</tr>
			<tr>
			
			 <td colspan=""><input id="btnAdd" type="button" class="smallButton" value="Add" onclick="return btnAdd_onclick();"></input>
			  <td colspan=""><input id="btnRemove" type="button" class="smallButton" value="Remove" onclick="return btnRemove_onclick();"></input>
			 &nbsp;&nbsp;&nbsp;&nbsp;
			  &nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/></td>
			</tr>
						</table>
						
						<table style="width: 80%;" class="transTablex col5-center">
								<tr>
									<td style="width:30%">Area Code</td>
									<td style="width:40%">Area Name</td>
									<td style="width:30%">Incentives</td>
								</tr>							
							</table>
							<div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 150px;
			    				margin: auto;overflow-x: hidden; overflow-y: scroll;width: 80%;">
									<table id="tblDeliveryCharges" class="transTablex col5-center" style="width: 100%;">
									<tbody>    
											<col style="width:30%"><!--  COl1   -->
											<col style="width:40%"><!--  COl2   -->
											<col style="width:30%"><!--  COl2   -->
									</tbody>							
									</table>
							</div>	
			</div>
							<!-- End of Settlement tab -->


							

						</div>
					</td>
				</tr>
			</table>
	

		

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
