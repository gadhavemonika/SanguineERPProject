<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  	<link rel="stylesheet" type="text/css" href="default.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Slow Moving Items</title>
<style>
  #tblGroup tr:hover , #tblSubGroup tr:hover, #tblToloc tr:hover{
	background-color: #72BEFC;
	
}
</style>
    <script type="text/javascript">
    /**
	 * Global variable
	 */
    var loggedInProperty="",loggedInLocation="";
    		
    /**
	 * Ready Function for Ajax Waiting and reset form
	 */
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
	 * Ready Function for Initialization Text Field with default value 
	 * Set Date in date picker
	 * Getting session value
	 */
	 
    $(document).ready(function() 
			{
				var startDate="${startDate}";
				var arr = startDate.split("/");
				Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
				$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
				$("#txtFromDate").datepicker('setDate',Dat);
				$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
				$("#txtToDate").datepicker('setDate', 'today');
				loggedInProperty="${LoggedInProp}";
				loggedInLocation="${LoggedInLoc}";
				
				$("#cmbProperty").val(loggedInProperty);
			    $("#lblLocName").text('<%=session.getAttribute("locationName").toString()%>');
			    funGetGroupData();
			    
			    /**
				 * Ready Function for Searching in Table
				 */
			    var tablename='';
    			$('#searchGrp').keyup(function()
    			{
    				tablename='#tblGroup';
    				searchTable($(this).val(),tablename);
    			});
    			$('#searchSGrp').keyup(function()
    	    			{
    						tablename='#tblSubGroup';
    	    				searchTable($(this).val(),tablename);
    	    			});
    			$('#txtToLocCode').keyup(function()
    	    			{
    						tablename='#tblToloc';
    	    				searchTable($(this).val(),tablename);
    	    			});
			});
    
    /**
	 * Function for Searching in Table Passing value(inputvalue and Table Id) 
	 */
    function searchTable(inputVal,tablename)
	{
		var table = $(tablename);
		table.find('tr').each(function(index, row)
		{
			var allCells = $(row).find('td');
			if(allCells.length > 0)
			{
				var found = false;
				allCells.each(function(index, td)
				{
					var regExp = new RegExp(inputVal, 'i');
					if(regExp.test($(td).find('input').val()))
					{
						found = true;
						return false;
					}
				});
				if(found == true)$(row).show();else $(row).hide();
			}
		});
	}
			
    /**
	 * Open help form
	 */
    var fieldName="";
    function funHelp(transactionName)
	{
    	fieldName = transactionName;
    	var propCode=$("#cmbProperty").val();
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&propCode="+propCode+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
		window.open("searchform.html?formname="+transactionName+"&propCode="+propCode+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
	}
    /**
	 * Set Data after selecting form Help windows
	 */
    function funSetData(code)
	{
		switch (fieldName) 
		{
		    case 'PropertyWiseLocation':
		    	funSetLocation(code);
		        break;
		}
	}
    /**
	 * Set Location data passing value location code
	 */
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
					$("#txtLocCode").val('');
					$("#lblLocName").text("");
					$("#txtLocCode").focus();
				} else {
					$("#txtLocCode").val(response.strLocCode);						
					$("#lblLocName").text(response.strLocName);
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
	 * Apply Number Validation on textfield
	 */
		function funApplyNumberValidation(){
			$(".numeric").numeric();
			$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
			$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
			$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
		    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
		}
		/**
		 * Set Group data
		 */
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
			 * Filling Group data
			 */
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
			*  Get subgroup data based on selected group
			**/
			function funGroupChkOnClick()
			{
				var table = document.getElementById("tblGroup");
			    var rowCount = table.rows.length;  
			    var strGCodes="";
			    for(no=0;no<rowCount;no++)
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
			*  Get subgroup data based passing value subgroup codes
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
			*  Filling subGroup data in grid
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
			 *  Remove all rows from grid passing value table Id
			**/
			function funRemRows(tableName) 
			{
				var table = document.getElementById(tableName);
				var rowCount = table.rows.length;
				while(rowCount>0)
				{
					table.deleteRow(0);
					rowCount--;
				}
			}
			
			 /**
			*  Select All From Group and subGroup When User Click On Select All option in Check Box
			**/
			 $(document).ready(function () 
				{
					$("#chkSGALL").click(function () {
					    $(".SGCheckBoxClass").prop('checked', $(this).prop('checked'));
					});
					
					$("#chkGALL").click(function () 
					{
					    $(".GCheckBoxClass").prop('checked', $(this).prop('checked'));
					    funGroupChkOnClick();
					});
					
				});
			 
			 /**
			  *  Check Validation Before Submit The Form
			 **/
			 function Submit_onClick()
			 {
				 var spFromDate=$("#txtFromDate").val().split('-');
				 var spToDate=$("#txtToDate").val().split('-');
				 var FromDate= new Date(spFromDate[2],spFromDate[1]-1,spFromDate[0]);
				 var ToDate = new Date(spToDate[2],spToDate[1]-1,spToDate[0]);
				 if(!fun_isDate($("#txtFromDate").val())) 
				    {
						 alert('Invalid From Date');
						 $("#txtFromDate").focus();
						 return false;  
				    }
				 if(!fun_isDate($("#txtToDate").val())) 
				   {
						 alert('Invalid To Date');
						 $("#txtToDate").focus();
						 return false;  
				   }
				 if(ToDate<FromDate)
					{
					 		alert("To Date Should Not Be Less Than Form Date");
					    	$("#txtToDate").focus();
							return false;		    	
					}
				 
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
				  *  Set All Selected subgroup In hidden TextField 
				 **/
				 $("#hidSubCodes").val(strSubGroupCode);
				 
			 }
			 /**
			  * Reset the form
			 **/
			 function funResetFields()
				{
					location.reload(true); 
				}
 </script>
 </head>
<body onload="funOnLoad()">
<div id="formHeading">
		<label>Slow Moving Items Report</label>
	</div>
	<s:form action="rptSlowMovingItemsReport.html" method="POST" name="frmSlowMovingItemsReport" target="_blank">
	
	<br>
	
			<table class="transTable">
			<tr><th colspan="7"></th></tr>
			<tr>
				    <td><label id="lblFromDate">From Date</label></td>
			        <td>
			            <s:input id="txtFromDate" name="fromDate" path="dtFromDate" cssClass="calenderTextBox"/>
			        	<s:errors path="dtFromDate"></s:errors>
			        </td>
				        
			        <td><label id="lblToDate">To Date</label></td>
			        <td>
			            <s:input id="txtToDate" name="toDate" path="dtToDate" cssClass="calenderTextBox"/>
			        	<s:errors path="dtToDate"></s:errors>
			        </td>
			       
				</tr>
				<tr>
					<td width="10%">Property Code</td>
					<td width="20%">
						<s:select id="cmbProperty" name="propCode" path="strPropertyCode" cssClass="longTextBox" cssStyle="width:100%" onchange="funChangeLocationCombo();">
			    			<s:options items="${listProperty}"/>
			    		</s:select>
					</td>
						
					<td width="5%"><label>Location</label></td>
					<td colspan="2">
<%-- 						<s:select id="cmbLocation" name="locCode" path="strLocationCode" cssClass="longTextBox" cssStyle="width:180px;" > --%>
<%-- 			    			<s:options items="${listLocation}"/> --%>
<%-- 			    		</s:select> --%>
					<s:input id="txtLocCode" path="strLocationCode" required="required" value="${locationCode}"
				     ondblclick="funHelp('PropertyWiseLocation')" cssClass="searchTextBox"/>
				      <label id="lblLocName"></label>
					</td>
				</tr>
				</table>
				<br>
			<table class="transTable">
		<tr><td width="49%">Group&nbsp;&nbsp;&nbsp;
		<input type="text"  style="width: 50%;background-position: 240px 2px;" 
		id="searchGrp" placeholder="Type to search" Class="searchTextBox">
		 	</td>
		 	     <td width="49%">Sub Group&nbsp;&nbsp;&nbsp;&nbsp;
		  		 <input type="text" id="searchSGrp" style="width: 50%;background-position: 240px 2px;" Class="searchTextBox" placeholder="Type to search">
		    </td>
		  </tr>
			<tr></tr>
			<tr>
				<td style="padding: 0 !important;">
						<div
							style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; overflow-x: hidden; overflow-y: scroll;">
							<table id="" class="display"
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
				<table  class="transTable">
				<tr>
					<td><label>Percentage</label></td>
				<td colspan="3"><s:input type="text" required="true" id="txtPercentage" path="dblPercentage" cssStyle="width:9%" class="decimal-places numberField"></s:input>
				
				</tr>		
				<tr>
				<td width="10%"><label>Report Type</label></td>
					<td colspan="3">
						<s:select id="cmbDocType" path="strDocType" cssClass="BoxW124px">
				    		<s:option value="PDF">PDF</s:option>
				    		<s:option value="XLS">EXCEL</s:option>
				    		<s:option value="HTML">HTML</s:option>
				    		<s:option value="CSV">CSV</s:option>
				    	</s:select>
			</td>
	</tr>
			</table>
			<br>
			<p align="center">
				 <input type="submit" value="Submit" class="form_button" onclick="return Submit_onClick()"/>
				 <input type="button" value="Reset" class="form_button" onclick="funResetFields()"/>			     
			</p>
			<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
		 <s:input type="hidden" id="hidSubCodes" path="strSGCode"></s:input>
	</s:form>
	<script type="text/javascript">
	funApplyNumberValidation();
	//funChangeLocationCombo();
	</script>
</body>
</html>