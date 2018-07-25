 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<link rel="stylesheet" type="text/css" href="default.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Web Stocks</title>
    <script type="text/javascript">
    var fieldName;
    
	    /**
		 *  Ajax Waiting
		**/
   		 $(document).ready(function() 
    		{
    			$(document).ajaxStart(function()
    		 	{
    			    $("#wait").css("display","block");
    		  	});
    		 	
    			$(document).ajaxComplete(function()
    			{
    			    $("#wait").css("display","none");
    			});	
    		});
    
		   	/**
		 	 *  Get Session value
		 	**/
    		$(function() 
    	    	{
    				funRemRows("tblloc");
    				var locationCode ='<%=session.getAttribute("locationCode").toString()%>';
    				funSetLocation(locationCode);
    	    		funGetGroupData();
    	    		$('#txtProdCode').blur(function () {
    	     		var code=$('#txtProdCode').val();
    	     			if (code.trim().length > 0 && code !="?" && code !="/"){								  
    	     					  funSetProduct(code);
    	     				 }
    	     			});
    	    	});	
    	 
    	/**
    	 *  Open Help from
    	**/
    	function funHelp(transactionName)
		{
			fieldName=transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
	    }
    	
    	/**
    	*  Get Data from help Selection 
    	**/
		function funSetData(code)
		{
			switch(fieldName)
			{
			    case 'productmaster':
			    	funSetProduct(code);
			        break;
			    case 'locationmaster':
			    	funSetLocation(code);
			        break;
			        
			    case 'productmasterslip':
			    	funSetProduct(code);
			        break;
				
			}
		}
		
    	/**
    		Get and Set Product Data Passing value(Product Code)
    	**/
    	function funSetProduct(code)
		{
    		var suppCode="";
    		var billdate="";
			var locCode=$("#txtToLocCode").val();
			var searchUrl="";
			searchUrl=getContextPath()+"/loadProductDataWithTax.html?prodCode="+code +"&locCode="+locCode+"&suppCode="+suppCode+"&billDate="+billDate;
			$.ajax
			({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	if('Invalid Code' == response.strProdCode){
			    		alert('Invalid Product Code');
				    	$("#txtProdCode").val('');
				    	$("#lblProdName").text('All Product');
				    	$("#txtProdCode").focus();
			    	}
			    	else
			    	{
				    	$("#txtProdCode").val(response.strProdCode);
			        	$("#lblProdName").text(response.strProdName);
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
			Get and set Location Data Passing value(Location Code)
		**/
		 function funSetLocation(code) {
				var searchUrl = "";
				searchUrl = getContextPath()
						+ "/loadLocationMasterData.html?locCode=" + code;

				$.ajax({
					type : "GET",
					url : searchUrl,
					dataType : "json",
					success : function(response) {
						if (response.strLocCode == 'Invalid Code') {
							alert("Invalid Location Code");
							$("#txtToLocCode").val('');
							$("#lblToLocName").text("");
							$("#txtToLocCode").focus();
						} else
						{
							funfillLocationGrid(response.strLocCode,response.strLocName);
						}
					},
					error : function(jqXHR, exception) {
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
	 			Filling Location Grid
	 		**/
		    function funfillLocationGrid(strLocCode,strLocationName)
			{
				 	var table = document.getElementById("tblloc");
				    var rowCount = table.rows.length;
				    var row = table.insertRow(rowCount);
				    
				    row.insertCell(0).innerHTML= "<input id=\"cbLocSel."+(rowCount)+"\" name=\"Locthemes\" type=\"checkbox\" class=\"LocCheckBoxClass\"  checked=\"checked\" value='"+strLocCode+"' />";
				    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"strLocCode."+(rowCount)+"\" value='"+strLocCode+"' >";
				    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\"strLocName."+(rowCount)+"\" value='"+strLocationName+"' >";
			}
		
		   /**
    		* Remove All Row from Table Passing Value(Table Id)
    	   **/
		   function funRemRows(tablename) 
			{
				var table = document.getElementById(tablename);
				var rowCount = table.rows.length;
				while(rowCount>0)
				{
					table.deleteRow(0);
					rowCount--;
				}
			}
		   
		   	/**
   				Get and set All Group Data
   			**/
		    function funGetGroupData()
			{
				var searchUrl = getContextPath() + "/loadAllGroupData.html";
				
				$.ajax({
					type : "GET",
					url : searchUrl,
					dataType : "json",
					success : function(response) {
						funRemRows("tblGroup");
						$.each(response, function(i,item)
				 		{
							funfillGroupGrid(response[i].strGCode,response[i].strGName);
						});
						funGroupChkOnClick();
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
				Filling Group Data in Grid
			**/
			function funfillGroupGrid(strGroupCode,strGroupName)
			{
				
				 	var table = document.getElementById("tblGroup");
				    var rowCount = table.rows.length;
				    var row = table.insertRow(rowCount);
				    
				    row.insertCell(0).innerHTML= "<input id=\"cbGSel."+(rowCount)+"\" type=\"checkbox\" class=\"GCheckBoxClass\" checked=\"checked\" onclick=\"funGroupChkOnClick()\"/>";
				    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"15%\" id=\"strGCode."+(rowCount)+"\" value='"+strGroupCode+"' >";
				    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"strGName."+(rowCount)+"\" value='"+strGroupName+"' >";
			}
				
			/**
			 * After Select Group Data get the SubGroup Data
			**/
			function funGroupChkOnClick()
			{
				var table = document.getElementById("tblGroup");
			    var rowCount = table.rows.length;  
			    var strGCodes="";
			    for(no=1;no<rowCount;no++)
			    {
			        if(document.all("cbGSel."+no).checked==true)
			        	{
			        		if(strGCodes.length>0)
			        			{
			        				strGCodes=strGCodes+","+document.all("strGCode."+no).value;
			        			}
			        		else
			        			{
			        			strGCodes=document.all("strGCode."+no).value;
			        			}
			        	}
			    }
			    funGetSubGroupData(strGCodes);
			   
			}
			
			/**
				Getting  SubGroup Based on Group Code Passing Value(Group Code)
			**/
			function funGetSubGroupData(strGCodes)
			{
				strCodes = strGCodes.split(",");
				var count=0;
				funRemRows("tblSubGroup");
				for (ci = 0; ci < strCodes.length; ci++) 
				 {
					var searchUrl = getContextPath() + "/loadSubGroupCombo.html?code="+ strCodes[ci];
					$.ajax({
						type : "GET",
						url : searchUrl,
						dataType : "json",
						success : function(response)
						{
							$.each(response, function(key, value) {
								
								funfillSubGroup(key,value);
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
				
			}
			
			/**
				Filling SubGroup Data in Grid
			**/
			function funfillSubGroup(strSGCode,strSGName) 
			{
				var table = document.getElementById("tblSubGroup");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    
			    row.insertCell(0).innerHTML= "<input id=\"cbSGSel."+(rowCount)+"\" type=\"checkbox\" checked=\"checked\" name=\"SubGroupthemes\" value='"+strSGCode+"' class=\"SGCheckBoxClass\" />";
			    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"15%\" id=\"strSGCode."+(rowCount)+"\" value='"+strSGCode+"' >";
			    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"strSGName."+(rowCount)+"\" value='"+strSGName+"' >";
			}
			
			/**
			*  Ready function for Select All Group, SubGroup and Location
			**/
			$(document).ready(function () 
					{
						$("#chkSGALL").click(function ()
						{
						    $(".SGCheckBoxClass").prop('checked', $(this).prop('checked'));
						});
						
						$("#chkGALL").click(function () 
						{
						    $(".GCheckBoxClass").prop('checked', $(this).prop('checked'));
						    funGroupChkOnClick();
						  
						});
						
						$("#chkLocALL").click(function () {
						    $(".LocCheckBoxClass").prop('checked', $(this).prop('checked'));
						});
						
					});
		 
			/**
			* Checking Validation when user Click On Submit Button
			**/
			function btnSubmit_Onclick()
		    {
				var strLocCode="";
					 
					 $('input[name="Locthemes"]:checked').each(function() {
						 if(strLocCode.length>0)
							 {
							 strLocCode=strLocCode+","+this.value;
							 }
							 else
							 {
								 strLocCode=this.value;
							 }
						 
						});
					 if(strLocCode=="")
						 {
						 	alert("Please Select Location");
						 	return false;
						 }
					 /**
					  * Set selected Location in hidden textfield
					 **/
					 $("#hidLocCodes").val(strLocCode);
					 
					 var strSubGroupCode="";
					 
					 $('input[name="SubGroupthemes"]:checked').each(function() {
						 if(strSubGroupCode.length>0)
							 {
							 	strSubGroupCode=strSubGroupCode+","+this.value;
							 }
							 else
							 {
								 strSubGroupCode=this.value;
							 }
						 
						});
					 /**
					  * Set selected subgroup in hidden textfield
					 **/
					 $("#hidSubCodes").val(strSubGroupCode);
			    	document.forms["frmlocWiseprodectSlip"].submit();
			    }
		     
			/**
		     * Reset from
		    **/
			function funResetFields()
			{
				location.reload(true); 
			}
    </script>
  </head>

<body>
	<div id="formHeading">
		<label>LocationWise Product Slip </label>
	</div>
	<br />
	<s:form name="frmlocWiseprodectSlip" method="GET"
		action="rptLocationWiseProductSlip.html" target="_blank">
		<table class="transTable">
			<tr>
				<th colspan="5"></th>
			</tr>
			<tr>
				<td width="10%"><label>Product Code</label></td>
				<td colspan="5"><s:input id="txtProdCode" path="strProdCode"
						ondblclick="funHelp('productmasterslip')" cssClass="searchTextBox"
						cssStyle="width:150px;background-position: 136px 4px;" /> <label
					id="lblProdName" style="font-size: 12px;">All Product</label></td>
			</tr>
		</table>

		<br>
		<table class="transTable">
			<tr>
				<td>Location&nbsp;&nbsp;&nbsp;
			<input type="text" id="txtToLocCode" 
			ondblclick="funHelp('locationmaster')" Class="searchTextBox"></input>
			<label id="lblToLocName"></label></td>
			</tr>
			<tr>
				<td style="padding: 0 !important;">
					<div class="transTablex">
						<div
							style="background-color: #a4d7ff; width: 49%; float: left; border: 1px solid #ccc; display: block; height: 150px; overflow-x: hidden; overflow-y: scroll;">

							<table id="" class="masterTable"
								style="width: 100%; border-collapse: separate;">
								<tbody>
									<tr bgcolor="#72BEFC">
										<td width="15%"><input type="checkbox" id="chkLocALL"
											checked="checked" />Select</td>
										<td width="25%">Location Code</td>
										<td width="65%">Location Name</td>

									</tr>
								</tbody>
							</table>
							<table id="tblloc" class="masterTable"
								style="width: 100%; border-collapse: separate;">

								<tr bgcolor="#72BEFC">
									<td width="15%"></td>
									<td width="25%"></td>
									<td width="65%"></td>

								</tr>
							</table>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<br>
		<table class="transTable">
			<tr>
				<td width="49%">Group</td>
				<td width="49%">Sub Group</td>
			</tr>
			<tr></tr>
			<tr>
				<td style="padding: 0 !important;">
					<div
						style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; overflow-x: hidden; overflow-y: scroll;">
						<table id="" class="masterTable"
							style="width: 100%; border-collapse: separate;">
							<tbody>
								<tr bgcolor="#72BEFC">
									<td width="15%"><input type="checkbox" id="chkGALL"
										checked="checked" onclick="funCheckUncheck()" />Select</td>
									<td width="20%">Group Code</td>
									<td width="65%">Group Name</td>

								</tr>
							</tbody>
						</table>
						<table id="tblGroup" class="masterTable"
							style="width: 100%; border-collapse: separate;">
							<tbody>
								<tr bgcolor="#72BEFC">
									<td width="15%"></td>
									<td width="20%"></td>
									<td width="65%"></td>

								</tr>
							</tbody>
						</table>
					</div>
				</td>
				<td style="padding: 0 !important;">
					<div
						style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; overflow-x: hidden; overflow-y: scroll;">

						<table id="" class="masterTable"
							style="width: 100%; border-collapse: separate;">
							<tbody>
								<tr bgcolor="#72BEFC">
									<td width="15%"><input type="checkbox" id="chkSGALL"
										checked="checked" onclick="funCheckUncheckSubGroup()" />Select</td>
									<td width="25%">Sub Group Code</td>
									<td width="65%">Sub Group Name</td>

								</tr>
							</tbody>
						</table>
						<table id="tblSubGroup" class="masterTable"
							style="width: 100%; border-collapse: separate;">
							<tbody>
								<tr bgcolor="#72BEFC">
									<td width="15%"></td>
									<td width="25%"></td>
									<td width="65%"></td>

								</tr>
							</tbody>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<br>
		<table class="transTable">
			<tr>
				<td width="10%"><label>Report Type</label></td>
				<td colspan="5"><s:select id="cmbDocType" path="strDocType"
						cssClass="BoxW124px">
						<s:option value="PDF">PDF</s:option>
						<s:option value="XLS">EXCEL</s:option>
						<s:option value="HTML">HTML</s:option>
						<s:option value="CSV">CSV</s:option>
					</s:select></td>
			</tr>
		</table>
		<br>
		<p align="center">
			<input type="submit" value="Submit" class="form_button"	onclick="return btnSubmit_Onclick();"/>
			<input type="button" value="Reset" class="form_button" onclick="funResetFields()" />
		</p>
		<s:input type="hidden" id="hidLocCodes" path="strToLocCode"></s:input>
		<s:input type="hidden" id="hidSubCodes" path="strSGCode"></s:input>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
	</s:form>
</body>
</html>