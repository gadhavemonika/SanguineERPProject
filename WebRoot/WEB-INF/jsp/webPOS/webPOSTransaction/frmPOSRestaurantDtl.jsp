<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
		  
		  $("form").submit(function(event){
			  if($("#txtAreaName").val().trim()=="")
				{
					alert("Please Enter Area Name");
					return false;
				}
			  else{
				  flg=funCallFormAction();
				  return flg;
			  }
			});
		}); 




	var tblMenuItemDtl_MAX_ROW_SIZE=100;
	var tblMenuItemDtl_MAX_COL_SIZE=4;
	var gMobileNo="";
	var gCRMInterface="${gCRMInterface}";
	var gCustAddressSelectionForBill="${gCustAddressSelectionForBill}";
	var gPrintType="${gPrintType}";
	var gCustomerCode="";
	var fieldName="";
		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{	       
			fieldName=transactionName;
	       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
		function funSetData(code)
		{

			switch(fieldName)
			{
			case "POSAreaMaster":
				funTxtAreaClicked(code);
				break;
			
			case "POSCustomerMaster":
				funSetCustomerDataForHD(code);
				break;
			case "NewCustomer":
				funSetCustomerDataForHD(code);
				break;
			
			}
		}
		function funSetCustomerDataForHD(code)
		{
			code=code.trim();
			var searchurl=getContextPath()+"/loadPOSCustomerMasterData.html?POSCustomerCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	$("#CustomerName").text(response.strCustomerName);
				        	gCustomerCode=response.strCustomerCode;
					        	
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
		function funTxtAreaClicked(code)
		{
			var searchurl=getContextPath()+"/loadPOSAreaMasterData.html?POSAreaCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        		
					        	$("#lblAreaName").text(response.strAreaName);
					        	funLoadTableForArea(code);
					        
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
		
		function funLoadTableForArea(code)
		{
			$('#tblTableDtl').empty();
			var searchurl=getContextPath()+"/funLoadTableForArea.html?areaCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	var tblTableDtl=document.getElementById('tblTableDtl');
							
				    		var insertCol=0;
				    		var insertTR=tblTableDtl.insertRow();
				    		
				    		
				    		
				    		$.each(response.tableDtl, function(i, obj) 
				    		{									
				    												
				    				if(insertCol<tblMenuItemDtl_MAX_COL_SIZE)
				    				{
				    					var col=insertTR.insertCell(insertCol);
				    					col.innerHTML = "<td><input type=\"button\" id="+obj.strTableNo+" value='"+obj.strTableName+"&#x00A;&#x00A;"+obj.intPaxNo+"'    style=\"width: 100px;height: 100px; background: #ff0d0d;\"  onclick=\"funTableClicked(this)\" /></td>";
				    					
				    					insertCol++;
				    				}
				    				else
				    				{					
				    					insertTR=tblTableDtl.insertRow();									
				    					insertCol=0;
				    									
				    					var col=insertTR.insertCell(insertCol);
				    					col.innerHTML = "<td><input type=\"button\" id="+obj.strTableNo+" value='"+obj.strTableName+"&#x00A;&#x00A;"+obj.intPaxNo+"'     style=\"width: 100px;height: 100px; background: #ff0d0d;\"  onclick=\"funTableClicked(this)\" /></td>";
				    					
				    					insertCol++;
				    				}							
				    			
				    			  
				    		});
					        
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
		
		function funTableClicked(objTableButton)
		{
			funRemoveTableRows("tblBillItemDtl");
			var tblMenuItemDtl=document.getElementById('tblTableDtl');
		
			var selctedTableNo=objTableButton.id;
			
			var jsonArrForTableDtl=${command.jsonArrForTableDtl};	
			
			
			$.each(jsonArrForTableDtl, function(i, obj) 
			{									
				if(obj.strTableNo==selctedTableNo)
				{									
					$("#txtTableName").val(obj.strTableName);	
					$("#txtPaxNo").val(obj.intPaxNo);			
				}
			});
			
			var searchurl=getContextPath()+"/funFillItemTableDtl.html?tableNo="+selctedTableNo;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	$("#txtWaiterName").val(response.strWaiterName);	
				        	$("#txtTotal").val(response.total);
				        	
				        	$.each(response.itemDtl, function(i, obj) 
			        				{									
			        					funFillItemDtl(obj);
			        				});
					        
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
		
		function funFillItemDtl(obj)
		{	
			var tblTableDtl=document.getElementById('tblBillItemDtl');
			var rowCount = tblTableDtl.rows.length;
			var insertRow = tblTableDtl.insertRow(rowCount);
					
		    var col1=insertRow.insertCell(0);
		    var col2=insertRow.insertCell(1);
		    var col3=insertRow.insertCell(2);
		    col1.innerHTML = "<input readonly=\"readonly\" size=\"32px\"  class=\"itemName\"    style=\"text-align: left; \"   name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].strItemName\" id=\"strItemName."+(rowCount)+"\" value='"+obj.strItemName+"' />";
		    col2.innerHTML = "<input readonly=\"readonly\" size=\"3.5px\"   class=\"itemQty\"      style=\"text-align: right;\"  name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].dblQuantity\" id=\"dblQuantity."+(rowCount)+"\" value='"+obj.dblItemQuantity+"' />";
		    col3.innerHTML = "<input readonly=\"readonly\" size=\"4px\"   class=\"itemAmt\"      style=\"text-align: right; \"  name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].dblAmount\" id=\"dblAmount."+(rowCount)+"\" value='"+obj.dblAmount+"' />";
		}
	
		function funRemoveTableRows(tableId)
		{
			var table = document.getElementById(tableId);
			var rowCount = table.rows.length;
			while(rowCount>1)
			{
				table.deleteRow(1);
				rowCount--;
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
		
		function funCustomerBtnClicked()
		{
			var tblTableDtl=document.getElementById('tblBillItemDtl');
			var rowCount = tblTableDtl.rows.length;
			if(rowCount==1)
				{
				alert("Please Select Table");
				return;
				}
			if(gCRMInterface=="SQY")
				{
				var strMobNo = prompt("Enter Mobile number", "");
				}
			else
	        {
	            funNewCustomerButtonPressed();
	        }
		}
		
		function funNewCustomerButtonPressed()
		{
			if (gCRMInterface=="SQY")
	        {
				 var strMobNo = prompt("Enter Mobile number", "");
				 
	        }
			else if (gCRMInterface=="PMAM")
	        {
				 var strMobNo = prompt("Enter Mobile number", "");
				 if(strMobNo.trim().length>0)
					 funSetCustMobileNo(strMobNo);
				 
	       	}
			else
	        {
				 var strMobNo = prompt("Enter Mobile number", "");
				 if(strMobNo.trim().length>0)
				 {
					 funSetCustMobileNo(strMobNo);
				 }
	       	}
		}

		function  funSetCustMobileNo(strMobNo)
		{
			gMobileNo=strMobNo;
			var totalBillAmount = 0.00;
			  if ($("#txtTotal").val().trim().length > 0)
	          {
				  totalBillAmount = parseFloat($("#txtTotal").val());
	          }
			 if (strMobNo.trim().length == 0)
	         {
				 funHelp('POSCustomerMaster');
	         }
			 else
				 funCheckCustomer(strMobNo);
		}
		function funCheckCustomer(strMobNo)
		{		
			var searchurl=getContextPath()+"/funCheckCustomer.html?strMobNo="+strMobNo;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	 if (response.flag)
				             {
				        		 if(gCustAddressSelectionForBill=="Y")
				 				{
				 				 	window.open("frmHomeDeliveryAddress.html?strMobNo="+gMobileNo+"","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
				 				} 
				        		 else
				        		 gCustomerCode=response.strCustomerCode;
				        		 $("#CustomerName").text(response.strCustomerName);
				             }	
				        	 else
				        		 funCustomerMaster(strMobNo);
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
		
		 function funCustomerMaster(strMobNo)
			{
				 fieldName="NewCustomer";
				 <%session.setAttribute("frmName", "frmPOSRestaurantBill");%>

				
				window.open("frmPOSCustomerMaster.html","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
			}
		
		 function funCheckKOTClicked()
			{
			 var tblTableDtl=document.getElementById('tblBillItemDtl');
				var rowCount = tblTableDtl.rows.length;
				if(rowCount==1)
					{
					alert("Please Select Table");
					return;
					}
				
		            if (gPrintType=="Text File")
		            {
		              //  clsTextFileGeneratorForPrinting ob = new clsTextFileGeneratorForPrinting();
		               // ob.fun_CkeckKot_TextFile(globalTableNo, txtWaiterNo.getText().trim());
		            }
		        
		       
		    }
		
		 function funHomeBtnclicked()
			{
				var loginPOS="${loginPOS}";
				var tblTableDtl=document.getElementById('tblBillItemDtl');
				var rowCount = tblTableDtl.rows.length;
				if(rowCount>1)
		        {
		           if(confirm( "Do you want to end Transaction?"))
		        	   window.location ="frmGetPOSSelection.html?strPosCode="+loginPOS;
				else
		            return;
		        } 
		        else
		        {
		        	window.location ="frmGetPOSSelection.html?strPosCode="+loginPOS;
		        }	
		    }
		 
		 function funDoneBtnclicked()
			{
			 var tblTableDtl=document.getElementById('tblBillItemDtl');
				var rowCount = tblTableDtl.rows.length;
				if(rowCount==1)
					{
					alert("Please Select Table");
					return;
					} 
		        else
		        {
		        	window.location ="frmPOSRestaurantDtl.html";
		        }	
		    }
</script>
</head>

<body>

	<div id="formHeading">
		<label>Make Bill</label>
	</div>

	<br />
	<br />

	<s:form name="frmMakeBill" method="POST" commandName="command"
		action="saveKOT.html">



		<div id="divMain"
			style="border: 1px solid #ccc; height: 520px; width: 785px; margin-left: 70px;">
			<table>

				<tr>
					<td>

						<table>
							<tr>


								<td><label>Bill No:</label> <input type="text"
									id="txtBillNo" readonly="true" class="BoxW124px" /></td>

								<td><label>Table No:</label> <input type="text"
									id="txtTableNo" readonly="true" class="BoxW124px" /></td>
							</tr>
							<tr>
								<td><label>Date:</label> <input type="text" id="txtDate"
									readonly="true" class="BoxW124px" /></td>
							</tr>
						</table>
						<div id="divItemTable"
							style="border: 1px solid #ccc; height: 400px; overflow-x: auto; overflow-y: auto; width: 324px;">

							<table id="tblItemTable" cellpadding="0" cellspacing="0">

							</table>
						</div>



					</td>

					<td>
					<div id="divSettlement"
							style="border: 1px solid #ccc; top: 10px; height: 437px; overflow-x: auto; overflow-y: auto; width: 453px;">

							<table id="tblSettlementDtl" cellpadding="0" cellspacing="7">

							</table>
					</div></td>


				</tr>
			</table>
		</div>


		<div style="text-align: right; margin-left: 250px;">

			<table id="tblFooterButtons" cellpadding="0" cellspacing="5">
				<tr>

					<td><input type="button" id="Customer" value="Customer"
						style="width: 100px; height: 35px; white-space: normal;"
						onclick="funCustomerBtnClicked()" /></td>
					<td><input type="button" id="Done" value="Done"
						style="width: 100px; height: 35px; white-space: normal;"
						onclick="funDoneBtnclicked()" /></td>
				</tr>
			</table>

		</div>
		</div>

	</s:form>
</body>
</html>