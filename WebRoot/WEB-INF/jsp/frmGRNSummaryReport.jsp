 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="default.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Cost Of Issue</title>
    <style>
  #tblGroup tr:hover , #tblSubGroup tr:hover, #tblloc tr:hover{
	background-color: #72BEFC;
	
}
</style>
    <script type="text/javascript">
    
 		 //Serching on Table when user type in text field
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
    			$('#txtSuppCode').keyup(function()
    	    			{
    						tablename='#tblSupp';
    	    				searchTable($(this).val(),tablename);
    	    			});
    			$('#txtLocCode').keyup(function()
    	    			{
    						tablename='#tblloc';
    	    				searchTable($(this).val(),tablename);
    	    			});	
    			
    		});

           //Searching on table on the basis of input value and table name
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
    		
	    var fieldName="";
	    //Ajax Wait Image display
	    $(document).ready(function() 
    		{
    			$(document).ajaxStart(function()
    		 	{
    			    $("#wait").css("display","block");
    		  	});
    			$(document).ajaxComplete(function(){
    			    $("#wait").css("display","none");
    			  });
    		});
    
	    //Set Start Date in date picker
        $(function() 
    		{
	        	var startDate="${startDate}";
				var arr = startDate.split("/");
				Date1=arr[0]+"-"+arr[1]+"-"+arr[2];   	  
    			$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
    			$("#txtFromDate" ).datepicker('setDate', Date1); 
    			
    			$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
    			$("#txtToDate" ).datepicker('setDate', 'today'); 
    			
    			 var strPropCode='<%=session.getAttribute("propertyCode").toString()%>';
    			 
    			 var locationCode ='<%=session.getAttribute("locationCode").toString()%>';

     			 funSetAllLocationAllPrpoerty();
    			 funGetGroupData();
    			 funSetAllSupplier();
    			 
    			 
    		});	
      

    
	  //Open Help
      function funHelp(transactionName)
		{
    	  	fieldName=transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
		}
	  
	  //Set data After Seletion of Help
      function funSetData(code)
		{
			switch (fieldName) 
			{

			    case 'suppcode':
			    	funSetSupplier(code);
			        break;  
			}
		}
      
   
    //Get and Set All Location on the basis of all Property
      function funSetAllLocationAllPrpoerty() {
			var searchUrl = "";
			searchUrl = getContextPath()+ "/loadAllLocationForAllProperty.html";
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
				success : function(response) {
					if (response.strLocCode == 'Invalid Code') {
						alert("Invalid Location Code");
						$("#txtFromLocCode").val('');
						$("#lblFromLocName").text("");
						$("#txtFromLocCode").focus();
					} else
					{
						$.each(response, function(i,item)
						 		{
							funfillLocationGrid(response[i].strLocCode,response[i].strLocName);
								});
						
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
    
      //Fill  Location Data
	    function funfillLocationGrid(strLocCode,strLocationName)
		{
			
			 	var table = document.getElementById("tblloc");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    
			    row.insertCell(0).innerHTML= "<input id=\"cbToLocSel."+(rowCount)+"\" name=\"Locthemes\" type=\"checkbox\" class=\"LocCheckBoxClass\"  checked=\"checked\" value='"+strLocCode+"' />";
			    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"strToLocCode."+(rowCount)+"\" value='"+strLocCode+"' >";
			    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\"strToLocName."+(rowCount)+"\" value='"+strLocationName+"' >";
		}
	  
	  
		     
	      //Get and set Supplier  Data 
	      function funSetAllSupplier() {
				var searchUrl = "";
				searchUrl = getContextPath()+ "/loadAllSupplier.html";
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
					success : function(response) {
						if (response.strSuppCode == 'Invalid Code') {
							alert("Invalid Supplier Code");
							
						} else
						{
							$.each(response, function(i,item)
							 		{
								funfillSuppGrid(response[i].strPCode,response[i].strPName);
									});
							
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
		

				
		    //Fill Supplier Data
		    function funfillSuppGrid(strSuppCode,strSuppName)
			{
				
				 	var table = document.getElementById("tblSupp");
				    var rowCount = table.rows.length;
				    var row = table.insertRow(rowCount);
				    
				    row.insertCell(0).innerHTML= "<input id=\"cbSuppSel."+(rowCount)+"\" name=\"Suppthemes\" type=\"checkbox\" class=\"SuppCheckBoxClass\"  checked=\"checked\" value='"+strSuppCode+"' />";
				    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"strSuppCode."+(rowCount)+"\" value='"+strSuppCode+"' >";
				    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\"strSName."+(rowCount)+"\" value='"+strSuppName+"' >";
			}
		    //Remove All Row from Grid Passing Table Id as a parameter
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
		    
		    //Get All Group data 
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
		    
		    //Fill Group Data
			function funfillGroupGrid(strGroupCode,strGroupName)
			{
				
				 	var table = document.getElementById("tblGroup");
				    var rowCount = table.rows.length;
				    var row = table.insertRow(rowCount);
				    
				    row.insertCell(0).innerHTML= "<input id=\"cbGSel."+(rowCount)+"\" type=\"checkbox\"  name=\"GCodethemes\" class=\"GCheckBoxClass selected \" checked=\"checked\" value='"+strGroupCode+"' onclick=\"funGroupChkOnClick()\"/>";
				    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box selected\" size=\"15%\" id=\"strGCode."+(rowCount)+"\" value='"+strGroupCode+"' >";
				    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box selected\" size=\"50%\" id=\"strGName."+(rowCount)+"\" value='"+strGroupName+"' >";
			}
				
			//Select All Group
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
			
			//Geting SubGroup Data On the basis of Selection Group
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
			
			//Fill SubGroup Data
			function funfillSubGroup(strSGCode,strSGName) 
			{
				var table = document.getElementById("tblSubGroup");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    
			    row.insertCell(0).innerHTML= "<input id=\"cbSGSel."+(rowCount)+"\" type=\"checkbox\" checked=\"checked\" name=\"SubGroupthemes\" value='"+strSGCode+"' class=\"SGCheckBoxClass\" />";
			    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"15%\" id=\"strSGCode."+(rowCount)+"\" value='"+strSGCode+"' >";
			    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"strSGName."+(rowCount)+"\" value='"+strSGName+"' >";
			}
			
			//Select All Group,SubGroup,From Location, To Location When Clicking Select All Check Box
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
							$("#chkSuppALL").click(function () {
							    $(".SuppCheckBoxClass").prop('checked', $(this).prop('checked'));
							});
							
							
							$("#chkLocALL").click(function () {
							    $(".LocCheckBoxClass").prop('checked', $(this).prop('checked'));
							});
							
						
							
						});
					 
			 
	   //Submit Data after clicking Submit Button with validation 
	   function btnSubmit_Onclick()
	    {
		    var strGCode="";
				 
				 $('input[name="GCodethemes"]:checked').each(function() {
					 if(strGCode.length>0)
						 {
						 strGCode=strGCode+","+this.value;
						 }
						 else
						 {
							 strGCode=this.value;
						 }
					 
					});
				 if(strGCode=="")
				 {
				 	alert("Please Select Group Code");
				 	return false;
				 }
				 $("#hidGCode").val(strGCode);
				
				 
				 var strSGCode="";
				 
				 $('input[name="SubGroupthemes"]:checked').each(function() {
					 if(strSGCode.length>0)
						 {
						 strSGCode=strSGCode+","+this.value;
						 }
						 else
						 {
							 strSGCode=this.value;
						 }
					 
					});
				 if(strSGCode=="")
				 {
				 	alert("Please Select SubGroup");
				 	return false;
				 }
				 $("#hidSubCodes").val(strSGCode);
					
				
				 
				 var strSuppCode="";
					 
					 $('input[name="Suppthemes"]:checked').each(function() {
						 if(strSuppCode.length>0)
							 {
							 strSuppCode=strSuppCode+","+this.value;
							 }
							 else
							 {
								 strSuppCode=this.value;
							 }
						 
						});
						
						
// 					 if(strSuppCode=="")
// 					 {
// 					 	alert("Please Select To Supplier");
// 					 	return false;
// 					 }
					 $("#hidSuppCodes").val(strSuppCode);
					 
					 
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
					 $("#hidLocCodes").val(strLocCode);
					 
					 
					 
					 
					 
					 
		    	document.forms["frmGRNSummaryReport"].submit();
		    }
	  
	    
	   //Reset All Filed After Clicking Reset Button
	    function funResetFields()
		{
			location.reload(true); 
		}
	</script>    
  </head>
  	
	<body id="GRNSummaryReport" onload="funOnload();">
	<div id="formHeading">
		<label>GRN Summary Report</label>
	</div>
		<s:form name="frmGRNSummaryReport" method="POST" action="rptGRNSummaryReport.html" target="_blank">
	   		<br />
	   		<table class="transTable">
			    <tr>
					<td width="10%"><label>From Date :</label></td>
					<td colspan="1" width="10%"><s:input id="txtFromDate" path="dtFromDate" required="true" readonly="readonly" cssClass="calenderTextBox"/></td>
					<td width="10%"><label>To Date :</label></td>
					<td colspan="1"><s:input id="txtToDate" path="dtToDate" required="true" readonly="readonly" cssClass="calenderTextBox"/>
					</td>
				</tr>
			    </table>
				<br>
			<table class="transTable">
			
			<tr>
			
					
			</tr>
		</table>
		<br>
		<table class="transTable">
		<tr>
		<td colspan="2">Group&nbsp;&nbsp;&nbsp;
			<input type="text"   style="width: 35%;background-position: 150px 2px;"  Class="searchTextBox" placeholder="Type to search"  
			id="searchGrp" >
		 </td>
		 <td colspan="2">Sub Group&nbsp;&nbsp;&nbsp;&nbsp;
		  		 <input type="text" id="searchSGrp" 
		  		  style="width: 35%;background-position: 150px 2px;"  Class="searchTextBox" placeholder="Type to search" >
		 </td>
		 	
		  </tr>
			
			<tr>
				<td colspan="2">
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
						<td  colspan="2">
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
				</td></tr><tr>
				<td colspan="2">Supplier&nbsp;&nbsp;&nbsp;<input style="width: 35%; background-position: 150px 2px;" type="text" id="txtSuppCode" 
			 Class="searchTextBox" placeholder="Type to search"></input>
			<label id="lblSuppName"></label></td>
			
			<td colspan="2">Location&nbsp;&nbsp;&nbsp;
			<input type="text" id="txtLocCode" 
			 style="width: 35%;background-position: 150px 2px;"  Class="searchTextBox" placeholder="Type to search"  ></input>
			<label id="lblLocName"></label></td>
			
			</tr><tr>
						<td colspan="2">
						<div
							style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; overflow-x: hidden; overflow-y: scroll;">

							<table id="" class="masterTable"
								style="width: 100%; border-collapse: separate;">
								<tbody>
									<tr bgcolor="#72BEFC">
										<td width="15%"><input type="checkbox" checked="checked" 
										id="chkSuppALL"/>Select</td>
										<td width="25%">To Supplier Code</td>
										<td width="65%">To Supplier Name</td>

									</tr>
								</tbody>
							</table>
							<table id="tblSupp" class="masterTable"
								style="width: 100%; border-collapse: separate;">

								<tr bgcolor="#72BEFC">
									

								</tr>
							</table>
						</div>
				</td>
				
				<td colspan="2">
						<div
							style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; overflow-x: hidden; overflow-y: scroll;">

							<table id="" class="masterTable"
								style="width: 100%; border-collapse: separate;">
								<tbody>
									<tr bgcolor="#72BEFC">
										<td width="15%"><input type="checkbox" checked="checked" 
										id="chkLocALL"/>Select</td>
										<td width="25%"> Location Code</td>
										<td width="65%"> Location Name</td>

									</tr>
								</tbody>
							</table>
							<table id="tblloc" class="masterTable"
								style="width: 100%; border-collapse: separate;">

								<tr bgcolor="#72BEFC">
									

								</tr>
							</table>
						</div>
				</td>
				
				
				
			
		</table>
		
		<br>
		<table class="transTable">
			<tr>
				<td width="10%"><label>Report Type :</label></td>
				<td colspan="3"><s:select id="cmbDocType" path="strDocType"
						cssClass="BoxW124px">
<%-- 						<s:option value="PDF">PDF</s:option> --%>
						<s:option value="XLS">EXCEL</s:option>
<%-- 						<s:option value="HTML">HTML</s:option> --%>
<%-- 						<s:option value="CSV">CSV</s:option> --%>
					</s:select></td>
			</tr>

		</table>
		

		<br>
			<p align="center">
				 <input type="button" value="Submit" onclick="return btnSubmit_Onclick();" class="form_button" />
				 <input type="button" value="Reset" class="form_button" onclick="funResetFields()"/>			     
			</p>  
			
			<s:input type="hidden" id="hidGCode" path="strGCode"></s:input>
			<s:input type="hidden" id="hidSubCodes" path="strSGCode"></s:input>
			<s:input type="hidden" id="hidSuppCodes" path="strSuppCode"></s:input>
			<s:input type="hidden" id="hidLocCodes" path="strLocationCode"></s:input>
			
			
			<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
		</s:form>
	</body>
</html>