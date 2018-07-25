<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GROUP MASTER</title>
<style>
.ui-autocomplete {
    max-height: 200px;
    overflow-y: auto;
    /* prevent horizontal scrollbar */
    overflow-x: hidden;
    /* add padding to account for vertical scrollbar */
    padding-right: 20px;
}
/* IE 6 doesn't support max-height
 * we use height instead, but this forces the menu to always be this tall
 */
* html .ui-autocomplete {
    height: 200px;
}
</style>
<script type="text/javascript">

/*On form Load It Reset form :Ritesh 22 Nov 2014*/
 $(document).ready(function () {
		  $('input#txtAreaCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtAreaName').mlKeyboard({layout: 'en_US'});
		  
		
			
			$("#txtFromDate").datepicker({ dateFormat: 'yy-mm-dd' });
			$("#txtFromDate" ).datepicker('setDate', 'today');
			$("#txtFromDate").datepicker();
			
	        $("#txtToDate").datepicker({ dateFormat: 'yy-mm-dd' });
	        $("#txtToDate" ).datepicker('setDate', 'today');
	        $("#txtToDate").datepicker();
	            
		  
		  $("form").submit(function(event){
			  if($("#txtAreaName").val().trim()=="")
				{
					alert("Please Enter Area Name");
					return false;
				}
			  else{
				 
				  return true;
			  }
			});
		}); 


 var selectedRowIndex=0;

	/**
	* Reset The Group Name TextField
	**/

	var fieldName;
	var menuCode;
	var menuName;
	var childMenuCode;
	var childMenuName;
	

	function funSetData(code){

		switch(fieldName){

			case 'POSRecipeMaster' : 
				funSetRecipe(code);
				break;
			case 'MenuItemForPrice':
				funSetMenuName(code);
			break;
			case 'MenuItemForRecipeChild':
				funSetChildMenuName(code);
			
				break;
		}
	}

	function funSetRecipe(code){

		$("#txtRecipeCode").val(code);
		var searchurl=getContextPath()+"/loadPOSRecipeMasterData.html?recipeCode="+code;		
		 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strRecipeCode=='Invalid Code')
		        	{
		        		alert("Invalid Delivery Boy Code");
		        		$("#txtRecipeCode").val('');
		        	}
		        	else
		        	{
		        		$("#txtMenuCode").val(response.strItemCode);
		        		
			        	$("#lblMenuName").val(response.strItemName);
			        	var date=response.dteFromDate.split(" ");
			        	$("#txtFromDate").val(date[0]);
			        	date=response.dteToDate.split(" ");
			        	$("#txtToDate").val(date[0]);
			        	$("#txtChildItemName").focus();
			        	funRemoveTableRows();
				    	$.each(response.listChildItemDtl, function(i,item)
						{			
				    		funAddRow1(item.strItemCode,item.strItemName,item.dblQuantity);
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

	function funSetMenuName(code)
	{
		
		var searchurl=getContextPath()+"/loadItemCodeForPromotionMaster.html?ItemCode="+code;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strItemCode=='Invalid Code')
			        	{
			        		alert("Invalid Customer Area Code");
			        		$("#txtMenuCode").val('');
			        	}
			        	else
			        	{
			        		
			        		menuCode=response.strItemCode;
			        		menuName=response.strItemName;
				        	$("#txtMenuCode").val(menuCode);
				        	$("#lblMenuName").val(menuName);
				        	
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
	
	function funSetChildMenuName(code)
	{
		
		var searchurl=getContextPath()+"/loadItemCodeForPromotionMaster.html?ItemCode="+code;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strItemCode=='Invalid Code')
			        	{
			        		alert("Invalid Customer Area Code");
			        		$("#txtMenuCode").val('');
			        	}
			        	else
			        	{
			        		
			        		
				        
				        	childMenuCode=response.strItemCode;
				        	childMenuName=response.strItemName;
				        	
				        	$("#txtChildItemName").val(childMenuName);
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
		* Open Help
		**/
		function funHelp(transactionName)
		{
			fieldName=transactionName;
			
	       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(Group Code)
		**/
		
		function btnAdd_onclick() 
		{
			
			
				if($("#txtMenuCode").val()=="") 
			    {
					alert("Please select Menu Item");
			   		
			       	return false;
				}
				else if($("#txtChildItemName").val()=="")
			    {
					alert("Please Select Child Item!");
			   		
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
			var quantity=$("#txtQuantity").val();
		    var table = document.getElementById("tblDeliveryCharges");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		   
		    if(funDuplicateItem(childMenuCode))
		    {
		    row.insertCell(0).innerHTML= "<input class=\"Box\" name=\"listChildItemDtl["+(rowCount)+"].strItemCode\" size=\"30%\"  id=\"txtAreaCode."+(rowCount)+"\" value='"+childMenuCode+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		    row.insertCell(1).innerHTML= "<input class=\"Box\" name=\"listChildItemDtl["+(rowCount)+"].strItemName\" size=\"40%\"  id=\"txtAreaName."+(rowCount)+"\" value='"+childMenuName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		    row.insertCell(2).innerHTML= "<input class=\"Box\" name=\"listChildItemDtl["+(rowCount)+"].dblQuantity\" size=\"30%\"  id=\"txtIncentives."+(rowCount)+"\" value='"+quantity+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		    }	
		    
		   
		}


		function funAddRow1(areaCode,areaName,incentive)
		{		    		    
		    var table = document.getElementById("tblDeliveryCharges");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    	
		    
		   
		    row.insertCell(0).innerHTML= "<input class=\"Box\" name=\"listChildItemDtl["+(rowCount)+"].strItemCode\" size=\"50%\"  id=\"txtAreaCode."+(rowCount)+"\" value='"+areaCode+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		    row.insertCell(1).innerHTML= "<input class=\"Box\" name=\"listChildItemDtl["+(rowCount)+"].strItemName\" size=\"50%\"  id=\"txAareaName."+(rowCount)+"\" value='"+areaName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";	    	    
		    row.insertCell(2).innerHTML= "<input class=\"Box\" name=\"listChildItemDtl["+(rowCount)+"].dblQuantity\" size=\"50%\"  id=\"txtToTime."+(rowCount)+"\" value='"+incentive+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		    
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
		function funDuplicateItem(areaCode)
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
		
		
		
		
		/**
		*  Check Validation Before Saving Record
		**/
		
		function funResetFields()
		{

			location.reload(true); 
	    }	
	
</script>


</head>

<body >
	<div id="formHeading">
		<label>Recipe Master</label>
	</div>
	<s:form name="AreaForm" method="POST" action="savePOSRecipeMaster.html?saddr=${urlHits}">

		<br />
		<br />
		<table class="masterTable">

			<tr>
				<td width="140px">Recipe Code</td>
				<td><s:input id="txtRecipeCode" path="strRecipeCode"
						cssClass="searchTextBox jQKeyboard form-control" readonly="true" onclick="funHelp('POSRecipeMaster')" /></td>
			<td colspan="2"></td> 
			</tr>
			<tr>
				<td><label>Menu Name</label></td>
				<td><s:input colspan="3" type="text" id="txtMenuCode" 
						name="txtMenuCode" path="strItemCode" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox jQKeyboard form-control" onclick="funHelp('MenuItemForPrice')" /> 
		
		<td colspan="2"><s:input  type="text" id="lblMenuName" 
						name="lblMenuName" path="strItemName" readonly="true"
						cssClass="longTextBox"  /> 
			</td>
		</tr>
		<tr>
		<td><label>From Date</label></td>
						<td><s:input id="txtFromDate" required="required"
								path="dteFromDate" 
								cssClass="calenderTextBox" /></td>

						<td><label>To Date</label></td>
						<td><s:input id="txtToDate" required="required" path="dteToDate"
								cssClass="calenderTextBox" /></td>
 		
 						
		</tr>
			<tr>
			<td><label>Child Item Name</label></td>
				<td><input  type="text" id="txtChildItemName" 
						name="txtChildItemName" 
						 class="longTextBox jQKeyboard form-control" onclick="funHelp('MenuItemForRecipeChild')" /> 
			
			
			<td><label>Quantity</label></td>
				<td><input  type="number" id="txtQuantity" 
						name="txtQuantity"  value=0
						 class="longTextBox jQKeyboard form-control"  /> 
				
			</tr>
			
			<tr>
			
			 <td colspan=""><input id="btnAdd" type="button" class="form_button" value="Add" onclick="return btnAdd_onclick();"></input>
			  <td colspan=""><input id="btnRemove" type="button" class="form_button" value="Remove" onclick="return btnRemove_onclick();"></input>
			</td>
			<td>
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/></td>
			</tr>
						</table>
						
						<table style="width: 80%;" class="transTablex col5-center">
								<tr>
									<td style="width:30%">Item Code</td>
									<td style="width:40%">Item Name</td>
									<td style="width:30%">Quantity</td>
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
		
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button"/> 
			<input type="button" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
	</s:form>

</body>
</html>

