<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
 <head>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jQuery.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/validations.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Web Stocks</title>
    <style>
  #tblGroup tr:hover , #tblSubGroup tr:hover, #tblToloc tr:hover{
	background-color: #72BEFC;
}
</style>
    <script type="text/javascript">
    
    /**
	 * Ready Function for Initialize textField with default value
	 * And Set date in date picker 
	 **/
    $(function() 
    		{
    	    	var startDate="${startDate}";
    			var arr = startDate.split("/");
    			Dat=arr[0]+"-"+arr[1]+"-"+arr[2];	
    	    	$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
    			$("#txtFromDate" ).datepicker('setDate', Dat);
    			$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
    			$("#txtToDate" ).datepicker('setDate', 'today');
    			
    			funGetGroupData();
    			$('#txtProdCode').blur(function () {
    				var code=$('#txtProdCode').val();
    				if (code.trim().length > 0 && code !="?" && code !="/"){								  
    					   funSetProduct(code);
    				   }
    				});
    			
    		});
    
    /**
	 * Ready Function for Searching in Table
	 */
    $(document).ready(function()
    		{
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
	     * Get project path
	    **/
	    function getContextPath() 
	    {
	   	 return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	    }
    
	    /**
		 * Open help form
		 */
    	var fieldName;
	    function funHelp(transactionName)
		{
	    	fieldName=transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=", 'window', "dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;");
	    	window.open("searchform.html?formname="+transactionName+"&searchText=", 'window', "dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;");
	    }
	    
	    function funSetData(code)
		{
	    	switch (fieldName) 
			{       
			    case 'productmaster':
			    	funSetProduct(code);
			        break;
			        
			    case 'productmasterslip':
			    	funSetProduct(code);
			        break; 
			}
		}
	    
	    /**
		 * Set Data after selecting form Help windows
		 */
	    function funSetProduct(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    beforeSend : function(){
						 $("#wait").css("display","block");
				    },
				    complete: function(){
				    	 $("#wait").css("display","none");
				    },
				    success: function(response)
				    {
				    	if('Invalid Code' == response.strProdCode){
				    		alert('Invalid Product Code');
					    	$("#txtProdCode").val('');
					    	$("#spProdName").text('All Product');
					    	$("#txtProdCode").focus();
				    	}
				    	else
				    	{
					    	$("#txtProdCode").val(response.strProdCode);
					    	$("#spProdName").text(response.strProdName);
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
		 * Set All Group Data
		 */
	    function funGetGroupData()
		{
			var searchUrl = getContextPath() + "/loadAllGroupData.html";
			
			$.ajax({
				type : "GET",
				url : searchUrl,
				dataType : "json",
				beforeSend : function(){
					 $("#wait").css("display","block");
			    },
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
		 * Filling Group data in Grid
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
		 * After Select Group Data get the SubGroup Data
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
		 * Getting SubGroup Based on Group Code Passing Value(Group Code)
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
					beforeSend : function(){
						 $("#wait").css("display","block");
				    },
				    complete: function(){
				    	 $("#wait").css("display","none");
				    },
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
		 * Filling SubGroup data in grid
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
		     * Remove All Row from Table Passing Value(Table Id)
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
			 * Select All Group, SubGroup
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
			  * Checking Validation when user Click On Submit Button
		     **/
			 function btnSubmit_OnClick() 
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
					    	alert("To Date is not < Form Date");
					    	$("#txtToDate").focus();
							return false;		    	
					}
				    else
					{
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
							 $("#hidSubCodes").val(strSubGroupCode);
							 return true;
						}
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
  	
	<body onload="onLoad();">
	<div id="formHeading">
		<label>Product Wise Supplier Rate History </label>
	</div>
		<s:form name="frmProdWiseSuppRateHis" method="POST" action="rptProdWiseSuppHis.html" target="_blank">
		<br>
	   		<table class="transTable">
			    <tr>
					<td width="10%"><label>From Date</label></td>
					<td  width="10%" colspan="1"><s:input id="txtFromDate" path="dtFromDate" required="true"  readonly="readonly" cssClass="calenderTextBox"/></td>
					<td width="10%"><label>To Date</label></td>
					<td colspan="1"><s:input id="txtToDate" path="dtToDate" required="true" readonly="readonly" cssClass="calenderTextBox "/></td>				
				</tr>
				<tr>
					<td width="10%"><label>Product Code</label></td>
					<td width="10%"><s:input id="txtProdCode" path="strDocCode" value="" cssClass="searchTextBox"  readonly="readonly" ondblclick="funHelp('productmasterslip');" cssStyle="width:150px;background-position: 136px 4px;"/></td>
					<td colspan="3" id="spProdName" style="font-size: 12px;">All Product</td>
				</tr>
				</table>
				
				<br>
			<table class="transTable">
		<tr>
		<td width="49%">Group&nbsp;&nbsp;&nbsp;
			<input type="text"  style="width: 50%;background-position: 240px 2px;" 
			id="searchGrp" placeholder="Type to search" Class="searchTextBox">
		 </td>
		 <td width="49%">Sub Group&nbsp;&nbsp;&nbsp;&nbsp;
		  		 <input type="text" id="searchSGrp" 
		  		 style="width: 50%;background-position: 240px 2px;" 
		  		 Class="searchTextBox" placeholder="Type to search">
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
				<table class="transTable">
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
				
				<tr>
				<td colspan="4"></td>
									
				</tr>
			  
			</table>
			<br>
			<p align="center">
					<input type="submit" value="Submit" onclick="return btnSubmit_OnClick();" class="form_button"/>
			 		<input type="button" value="Reset" class="form_button" onclick="funResetFields();"/>
			</p>
			<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
			<s:input type="hidden" id="hidSubCodes" path="strSGCode"></s:input>
		</s:form>
	</body>
</html>