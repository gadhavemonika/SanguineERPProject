<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Send Bulk SMS</title>

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


var isSelected="";
var myMap = new Map();

 function funOnLoad()
 {

 	document.all[ 'divCustomerDtl' ].style.display = 'block';
 	document.all[ 'divSmsDtl' ].style.display = 'none';
	
 }

function funFillCustomerTable()
{ 
// 	if ($("#txtSMS").val().trim()=="") 
// 	{
//        alert("Please Enter The Message..");
//        return false;
//     }
	
	isSelected="execute";
	
	 var custTypeCode=$("#cmbCustomerType").val();
 	 var areaCode=$("#cmbArea").val();
 	 var txtSms = $("#txtSms").val();
 	 
 	 
 	 
 	 var isDOBChecked;
 	 var isDOBSelected;
 	if ($('input.checkbox_check').prop('checked')) {
 		//alert("checked");
 		
 		isDOBSelected="true";
 	}
 	else
 	{
 		//alert("unchecked");
 		isDOBSelected=false;
 	}	
 	
 	var searchurl=getContextPath()+"/funFillCustomerTable.html";
	 $.ajax({
		        type: "POST",
		        data:{ custTypeCode : custTypeCode,
		        	areaCode : areaCode,
		        	txtSms : txtSms,
		        	isDOBSelected : isDOBSelected,
	 			},
		        url: searchurl,
		        dataType: "json",
		        async:false,
		        success: function(response)
		        {
		        	if(txtSms=='')
		        	 {
		        		 alert("Please Enter Message");
		        		 
		        	 }
		        	else
		        	{	
		        	funRemoveTableRows("tblCustomer");
		        	var list="";
		        	list=response.customerTblData;
		        	$.each(list, function(i, item) 
		        	{
		        		funFillCustomerTableData(item.strCustomerName,item.longMobileNo,item.dteDOB,item.strCustType,item.strBuildingName,item.noOfTimesVisited,item.txtSms);
			        				
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

function funRemoveTableRows(tableId)
{
	var table = document.getElementById(tableId);
	var rowCount = table.rows.length;
	while(rowCount>0)
	{
		table.deleteRow(0);
		rowCount--;
	}
}

function funFillCustomerTableData(strCustomerName,longMobileNo,dteDOB,strCustType,strBuildingName,noOfTimesVisited,txtSms)
{
	var table = document.getElementById("tblCustomer");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);
	
	var txtSms = $("#txtSms").val();

	row.insertCell(0).innerHTML= "<input name=\"customerTblData["+(rowCount)+"].strCustomerName\" readonly=\"readonly\" class=\"Box \" size=\"26%\" id=\"customerName\" value='"+strCustomerName+"'  >";
	row.insertCell(1).innerHTML= "<input name=\"customerTblData["+(rowCount)+"].longMobileNo\" readonly=\"readonly\" class=\"Box \" size=\"21%\" id=\"mobileNo\" value='"+longMobileNo+"' >";
	row.insertCell(2).innerHTML= "<input name=\"customerTblData["+(rowCount)+"].dteDOB\" readonly=\"readonly\" class=\"Box \" size=\"16%\" id=\"dteDOB\" value='"+dteDOB+"' >";
	row.insertCell(3).innerHTML= "<input name=\"customerTblData["+(rowCount)+"].strCustType\" readonly=\"readonly\" class=\"Box \" size=\"7%\" id=\"custType\" value='"+strCustType+"' >";
	row.insertCell(4).innerHTML= "<input name=\"customerTblData["+(rowCount)+"].strBuildingName\" readonly=\"readonly\" class=\"Box \" size=\"20%\" id=\"buildingName\" value='"+strBuildingName+"' >";
	row.insertCell(5).innerHTML= "<input name=\"customerTblData["+(rowCount)+"].noOfTimesVisited\" readonly=\"readonly\" style=\"text-align:right;\" class=\"Box \" size=\"7%\" id=\"noOfTimesVisited\" value='"+noOfTimesVisited+"' >";
	row.insertCell(6).innerHTML= "<input name=\"customerTblData["+(rowCount)+"].txtSms\" readonly=\"readonly\" class=\"Box \" size=\"20%\" id=\"txtSMS\" value='"+txtSms+"'>";
}


//After Submit Button
function funImportFile()
{
	
	isSelected="import";
	document.all[ 'divCustomerDtl' ].style.display = 'none';
	document.all[ 'divSmsDtl' ].style.display = 'block';
	if(funValidateFile())
		{
			var jForm = new FormData();    
		    jForm.append("file", $('#File').get(0).files[0]);
		    searchUrl=getContextPath()+"/SendBulkSmsImport.html";	
	        $.ajax({
	           // url : $("#uploadExcel").attr('action'),
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
	            	funRemoveTableRows("tblSmsDtl");
	            	if(response[0]=="Invalid Excel File")
	            		{
	            			alert(response[1]);
	            		}
	            	else
	            		{
							$.each(response.listSmsDtl,function(i,item)
							{
								funAddRow(item.strCustomerName,item.strMobileNumber);
							});
							alert("Data Imported Successfully");
	            		   
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

function funAddRow(strCustomerName,strMobileNumber)
{
		
		 var txtSms = $("#txtSms").val();
		 
		 var table = document.getElementById("tblSmsDtl");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			
		    row.insertCell(0).innerHTML= "<input name=\"listSmsDtl["+(rowCount)+"].strMobileNumber\" readonly=\"readonly\" style=\"text-align: center\" class=\"Box\" size=\"13%\" id=\"mobileNo\" value='"+strMobileNumber+"'  >";
			row.insertCell(1).innerHTML= "<input name=\"listSmsDtl["+(rowCount)+"].strCustomerName\" readonly=\"readonly\" class=\"Box\" size=\"36%\" id=\"customerName\" value='"+strCustomerName+"' >";
			row.insertCell(2).innerHTML= "<input name=\"listSmsDtl["+(rowCount)+"].txtSms\" readonly=\"readonly\" style=\"text-align: right\" class=\"Box\" size=\"28%\" id=\"sms\" value='"+txtSms+"' >";
    	
}

function funSendTestSMS()
{
	var txtTestMobileNo = $("#txtTestMobileNo").val();
	
	 var txtSms = $("#txtSms").val();
	 
		if (txtSms=='')
        {
            alert("Please Enter The Message..");
          
        }
        if (txtTestMobileNo=='')
        {
            alert("Please Enter The Test Mobile Number.");
           
        }
//         if (!txtTestMobileNo.getText().matches("\\d{10}"))
//         {
//             new frmOkPopUp(null, "Please Enter Valid Mobile Number.", "Error", 1).setVisible(true);
//             return;
//         }
	 
	 
	var searchurl=getContextPath()+"/funSendBulkSMS.html";
	 $.ajax({
		        type: "POST",
		        data:{ txtTestMobileNo : txtTestMobileNo,
		        	txtSms : txtSms,
	 			},
		        url: searchurl,
		        dataType: "json",
		        async:false,
		        success: function(response)
		        {
		        	if (response.returnResult=="true")
		            {
		                alert("Test SMS Sent To :" + txtTestMobileNo + ".");
		               
		            }
		            else
		            {
		              alert("Unable To Send SMS To :" + txtTestMobileNo + ".");
		               
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


// function funSendSMS()
// {
// 	funFillCustomerList();
	
// }
var arrKOTItemDtlList=new Array();
function funSendSMS()
{
	var i=1;
	var result="";
	var rowCount="";
	if(isSelected=="import")
	{
		rowCount = $('#tblSmsDtl tr').length;
	
	 $('#tblSmsDtl tr').each(function() {
		 var code=$(this).find("input[id='mobileNo']").val();
		
		    	code=code+"_"+$(this).find("input[id='customerName']").val()+"_"+$(this).find("input[id='sms']").val();
		    	
		    	if(i > 0)
	    		{
	    	arrKOTItemDtlList[i-1]=code;
	    	
	    		}
				i++;
		   
			 });
	}
	else
	{
		rowCount = $('#tblCustomer tr').length;
		 $('#tblCustomer tr').each(function() {
			 var code=$(this).find("input[id='mobileNo']").val();
			
			    	code=code+"_"+$(this).find("input[id='customerName']").val()+"_"+$(this).find("input[id='txtSMS']").val();
			    	
			    	if(i > 0)
		    		{
		    	arrKOTItemDtlList[i-1]=code;
		    	
		    		}
					i++;
			   
				 });
	}	
	 
	 var searchurl=getContextPath()+"/funSendSMS.html?arrKOTItemDtlList="+arrKOTItemDtlList;
	 $.ajax({
		        type: "POST",
		        url: searchurl,
		        dataType: "json",
		        async:false,
		        success: function(response)
		        {
		        	result=response.returnResult;
		        	if(rowCount > 0)
		        	{
		            if (response.returnResult=="true")
		            {
		               alert("Message Sent Successfully.");
		                    
	                 }
		        	}
		            else
		            {
		               alert("No Customer Is Selected.");
		               
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

function setfilename(val)
{
  var fileName = val.substr(val.lastIndexOf("\\")+1, val.length);
 document.getElementById("txtSelectFile").value = fileName;
}
</script>
</head>
<body onload="funOnLoad()">
	<div id="formHeading">
		<label>Send Bulk SMS</label>
	</div>
	<s:form name="SendBulkSMSForm" method="POST" action="">

		<br />
		<br />
		<table class="masterTable">

			<tr>
			<td><label>Customer Type:</label></td>
				<td width="150px"><s:select id="cmbCustomerType" name="cmbCustomerType" path="strCustomerType" items="${mapCustomerType}" cssClass="BoxW124px" />
				</td>
				
			
			<td ><label>Area:</label>&nbsp;&nbsp;
			<s:select id="cmbArea" name="cmbArea" path="strArea" items="${mapCusomerArea}" cssClass="BoxW124px" />
			</td>
			
			<td><label>DOB:</label>&nbsp;&nbsp;
				<s:input type="checkbox"  id="chkDob"  name="chkDob" path="strDob" style="width: 3%;height:2px;" cssClass="BoxW124px" ></s:input>
				</td>	
			
			<td colspan="2"><label>Test Mobile No. :</label>&nbsp;&nbsp;
				<s:input id="txtTestMobileNo" name="txtTestMobileNo" path="strTestMobileNo"
						cssStyle="text-transform: uppercase;width: 65%;" cssClass="longTextBox" />
				</td>
				
			</tr>
			<tr>
				<td><label>SMS   :</label></td>
				<td colspan="4"><s:textarea colspan="20" rowspan="5"  id="txtSms" 
						name="txtSms" path="strSms" 
						style="height:30px;width:740px" cssClass="longTextBox"  /> 
<%-- 		<s:textarea  id="txtAreaSMSApi"  --%>
<%-- 						path="strAreaSMSApi"  style="height:30px" --%>
<%-- 						 cssClass="longTextBox"  /> --%>
				</td>
				
				
			
				<td>
				<input type="button" value="EXECUTE" tabindex="3" class="form_button" onclick="funFillCustomerTable()"/> 
				</td>
				
				
				
			</tr>
			<tr >
				<td><label>Select  File  :</label></td>
				<td colspan="3"><s:input colspan="3" type="text" id="txtSelectFile" 
						name="txtSelectFile" path="strSelectFile"
						style="width:500px;height:20px" cssClass="longTextBox"  /> 
		
				</td>
				
				<td>
				 <input type="file" id="File"  Width="5%"  onchange="setfilename(this.value);"></td>
				 <td>
				 <input id="btnImport" type="button" tabindex="3" class="form_button" value="Import" onclick="funImportFile();"/>
			  &nbsp;&nbsp;
				</td>		
				
				
				
			<tr>
		</table>
<div id="divCustomerDtl" style="width:100%;margin-bottom:5px;display: block; overflow-x: hidden; float:right;margin-right: 4px;height:600px;border:0px solid">		
		<table border="1" class="myTable" style="width:80%;margin: auto;"  >
										
										<tr>
										<td style="width:22.2%">Customer Name</td>
										<td style="width:17.8%">Mobile Number</td>
										<td style="width:14.3%">DOB</td>
										<td style="width:12%">Customer Type</td>
										<td style="width:15.5%">Area</td>
										<td style="width:5%">Visited</td>
										<td style="width:28%">SMS</td>
										</tr>
					</table>
					<div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 300px;
				    				margin:auto;overflow-x: hidden; overflow-y: scroll;width: 80%;"> 
										<table id="tblCustomer" class="transTablex col5-center" style="width:100%;">
										<tbody>    
												<col style="width:26%"><!--  COl1   -->
												<col style="width:21%"><!--  COl2   -->
												<col style="width:16%"><!--  COl3   -->
												<col style="width:16%"><!--  COl4   -->
												<col style="width:29%"><!--  COl5   -->
																				
										</tbody>							
										</table>
								</div>
	</div>
	
	<div id="divSmsDtl" style="width:100%;margin-bottom:5px;display: block; overflow-x: hidden; float:right;margin-right: 4px;height:600px;border:0px solid">							
	<table border="1" class="myTable" style="width:80%;margin: auto;"  >
										
										<tr>
										<td style="width:12%">Mobile Number</td>
										<td style="width:33%">Customer Name</td>
										
										<td style="width:28%">SMS</td>
										</tr>
					</table>
					<div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 300px;
 				    				margin:auto;overflow-x: hidden; overflow-y: scroll;width: 80%;">
										<table id="tblSmsDtl" class="transTablex col5-center" style="width:100%;">
										<tbody>    
												<col style="width:13%"><!--  COl1   -->
												<col style="width:36%"><!--  COl2   -->
												
												<col style="width:29%"><!--  COl5   -->
																				
										</tbody>							
										</table>
								</div>			
	</div>							
		
		<p align="center">
			<input type="button" value="SEND TEST SMS" tabindex="3" class="form_button" onclick="funSendTestSMS()"/> 
			<input type="button" value="SEND SMS" tabindex="3" class="form_button" onclick="funSendSMS()"/> 
			<input type="submit" value="Submit" tabindex="3" class="form_button"/> 
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
	</s:form>

</body>
</html>