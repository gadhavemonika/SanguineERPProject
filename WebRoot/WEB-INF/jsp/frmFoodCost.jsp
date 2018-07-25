 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="default.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Food Cost</title>
    <style>
  #tblGroup tr:hover , #tblSubGroup tr:hover, #tblToloc tr:hover{
	background-color: #72BEFC;
	
}
</style>
    <script type="text/javascript">
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
    			$('#txtLocCode').keyup(function()
    	    			{
    						tablename='#tblLoc';
    	    				searchTable($(this).val(),tablename);
    	    			});
    			
    			$('#txtPropertyCode').keyup(function()
    	    			{
    						tablename='#tblProperty';
    	    				searchTable($(this).val(),tablename);
    	    			});
    			
    		});

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
    $(document).ready(function() 
    		{
    	$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});
    		 	
    		});
    
      $(function() 
    		{
	    	   var startDate="${startDate}";
				//alert(startDate);
				var arr = startDate.split("/");
				Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
	    	  
    			$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
    			$("#txtFromDate" ).datepicker('setDate', Dat); 
    			
    			$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
    			$("#txtToDate" ).datepicker('setDate', 'today'); 
    			 funRemRows("tblProperty");
    			 funRemRows("tblLoc");
    			 funSetAllPrpoerty();
    			 funGetGroupData();
    			 
    		});	
      
      $(function() {

			$('#txtFromLocCode').blur(function() {
				var code = $('#txtFromLocCode').val();
				if(code.trim().length > 0 && code !="?" && code !="/"){
					funSetFromLocation(code);
					$('#txtFromLocCode').val('');
				}
			});

		});
    
   
      
      function funSetAllPrpoerty() {
			var searchUrl = "";
			searchUrl = getContextPath()+ "/loadAllProperty.html";
	//alert(searchUrl);
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
					
					
						$.each(response, function(i,item)
						 		{
									funfillAllPropertyGrid(response[i].propertyCode,response[i].propertyName);
								});
						funPropertyChkOnClick();
					
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
      
      function funPropertyChkOnClick()
		{
			var table = document.getElementById("tblProperty");
		    var rowCount = table.rows.length;  
		    var strPropCodes="";
		    for(no=0;no<rowCount;no++)
		    {
		        if(document.all("cbPropSel."+no).checked==true)
		        	{
		        	
		        		if(strPropCodes.length>0)
		        			{
		        			 strPropCodes=strPropCodes+","+document.all("strPropCode."+no).value;
		        			}
		        		else
		        			{
		        			strPropCodes=document.all("strPropCode."+no).value;
		        			}
		        	}
		    }
		   // alert(strPropCodes);
		    funGetLocData(strPropCodes);
		   
		}
      
      function funGetLocData(strPropCodes)
		{
    	  strPropCodes = strPropCodes.split(",");
			var count=0;
			funRemRows("tblLoc");
			for (ci = 0; ci < strPropCodes.length; ci++) 
			 {
				var searchUrl = getContextPath() + "/loadLocationForProperty.html?propCode="+ strPropCodes[ci];
				
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
						$.each(response, function(i,item) { 
							
							funfillLocationGrid(response[i][1],response[i][0]);
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
      
     
	     
      function funfillAllPropertyGrid(strPropertyCode,strPropertyName)
		{
			
			 	var table = document.getElementById("tblProperty");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    
			    row.insertCell(0).innerHTML= "<input id=\"cbPropSel."+(rowCount)+"\" name=\"Propthemes\" type=\"checkbox\" class=\"PropCheckBoxClass\"  checked=\"checked\" value='"+strPropertyCode+"' onclick=\"funPropertyChkOnClick()\" />";
			    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"strPropCode."+(rowCount)+"\" value='"+strPropertyCode+"' >";
			    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\"strPropName."+(rowCount)+"\" value='"+strPropertyName+"' >";
		}
      
		    function funfillLocationGrid(strLocCode,strLocationName)
			{
				 	var table = document.getElementById("tblLoc"); 
				    var rowCount = table.rows.length;
				    var row = table.insertRow(rowCount);
				    
				    row.insertCell(0).innerHTML= "<input id=\"cbLocSel."+(rowCount)+"\" name=\"Locthemes\" type=\"checkbox\" class=\"LocCheckBoxClass\"  checked=\"checked\" value='"+strLocCode+"' />";
				    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"strLocCode."+(rowCount)+"\" value='"+strLocCode+"' >";
				    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\"strLocName."+(rowCount)+"\" value='"+strLocationName+"' >";
			}
		   
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
			function funfillGroupGrid(strGroupCode,strGroupName)
			{
				
				 	var table = document.getElementById("tblGroup");
				    var rowCount = table.rows.length;
				    var row = table.insertRow(rowCount);
				    
				    row.insertCell(0).innerHTML= "<input id=\"cbGSel."+(rowCount)+"\" type=\"checkbox\" class=\"GCheckBoxClass selected \" checked=\"checked\" onclick=\"funGroupChkOnClick()\"/>";
				    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box selected\" size=\"15%\" id=\"strGCode."+(rowCount)+"\" value='"+strGroupCode+"' >";
				    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box selected\" size=\"50%\" id=\"strGName."+(rowCount)+"\" value='"+strGroupName+"' >";
			}
				
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
			function funfillSubGroup(strSGCode,strSGName) 
			{
				var table = document.getElementById("tblSubGroup");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    
			    row.insertCell(0).innerHTML= "<input id=\"cbSGSel."+(rowCount)+"\" type=\"checkbox\" checked=\"checked\" name=\"SubGroupthemes\" value='"+strSGCode+"' class=\"SGCheckBoxClass\" />";
			    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"15%\" id=\"strSGCode."+(rowCount)+"\" value='"+strSGCode+"' >";
			    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"strSGName."+(rowCount)+"\" value='"+strSGName+"' >";
			}
			
			
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
							
							$("#chkALLProperty").click(function () {
							    $(".PropCheckBoxClass").prop('checked', $(this).prop('checked'));
							    funPropertyChkOnClick();
							});
							
						});
					 
			 
	   function btnSubmit_Onclick()
	    {
		    
	    	if($("#txtFromDate").val()=='')
		    {
		    	alert("Please enter From Date");
		    	return false;
		    }
		    if($("#txtToDate").val()=='')
		    {
		    	alert("Please enter To Date");
		    	return false;
		    }   
		    
		    else
		    {
		    	var strLocCode="";
		    	 var strPropCodes="";
		    	$('input[name="Propthemes"]:checked').each(function()
						  {
							 if(strPropCodes.length>0)
								 {
								     strPropCodes=strPropCodes+","+this.value;
								 }
								 else
								 {
									 strPropCodes=this.value;
								 }
							 
						 });
		    	
		    	 $("#hidPropCodes").val(strPropCodes);
		    	
				 $('input[name="Locthemes"]:checked').each(function()
				  {
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
					 	alert("Please Select From Location");
					 	return false;
					 }
				 
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
				 $("#hidSubCodes").val(strSubGroupCode);
		    	document.forms["frmFoodCost"].submit();
		    
		    
// 		    var fromDate=$("#txtFromDate").val();
// 			var toDate=$("#txtToDate").val();
			
// 			var fd=fromDate.split("-")[0];
// 			var fm=fromDate.split("-")[1];
// 			var fy=fromDate.split("-")[2];
			
// 			var td=toDate.split("-")[0];
// 			var tm=toDate.split("-")[1];
// 			var ty=toDate.split("-")[2];
		    
// 		    $("#txtFromDate").val(fd+"-"+fm+"-"+fy);
// 			$("#txtToDate").val(td+"-"+tm+"-"+ty);
			
		    var against=$("#cmbType").val();
			if(against=='Detail')
			{
				document.frmFoodCost.action="rptFoodCost.html";
// 				window.open(getContextPath()+"/rptFoodCost.html");
			}else{
				document.frmFoodCost.action="rptFoodCostSummary.html";
			}
			
			document.frmFoodCost.submit();
			
		  }
		    
	    } 
	    
	    function funResetFields()
		{
			location.reload(true); 
		}
	</script>    
  </head>
  	
	<body id="costissue" onload="funOnload();">
	<div id="formHeading">
<!-- 		<label>Food Cost</label> -->
		<label>Cost Analysis</label>
	</div>
		<s:form name="frmFoodCost" method="POST" action="rptFoodCost.html" target="_blank">
	   		<br />
	   		<table class="transTable">
			    <tr>
					<td width="10%"><label>From Date :</label></td>
					<td colspan="1" width="10%"><s:input id="txtFromDate" path="dtFromDate" value="" readonly="readonly" cssClass="calenderTextBox"/></td>
					<td width="10%"><label>To Date :</label></td>
					<td colspan="1"><s:input id="txtToDate" path="dtToDate" value="" readonly="readonly" cssClass="calenderTextBox"/>
					</td>
				</tr>
			    </table>
				<br>
			<table class="transTable">
			<tr><td width="49%">Property&nbsp;&nbsp;&nbsp;<input type="text" id="txtPropertyCode" 
			Class="searchTextBox"></input>
			<td width="49%">Location&nbsp;&nbsp;&nbsp;
			<input type="text" id="txtLocCode" 
			 style="width: 50%;background-position: 240px 2px;"  Class="searchTextBox" placeholder="Type to search"></input>
			<label id="lblLocName"></label></td></tr>
			<tr>
			
				<td style="padding: 0 !important;">
						<div
							style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; overflow-x: hidden; overflow-y: scroll;">

							<table id="" class="masterTable"
								style="width: 100%; border-collapse: separate;">
								<tbody>
									<tr bgcolor="#72BEFC">
										<td width="15%"><input type="checkbox" id="chkALLProperty"
											checked="checked" />Select</td>
										<td width="25%">Property Code</td>
										<td width="65%">Property Name</td>

									</tr>
								</tbody>
							</table>
							<table id="tblProperty" class="masterTable"
								style="width: 100%; border-collapse: separate;">

								<tr bgcolor="#72BEFC">
									<td width="15%"></td>
									<td width="25%"></td>
									<td width="65%"></td>

								</tr>
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
										<td width="15%"><input type="checkbox" checked="checked" 
										id="chkLocALL"/>Select</td>
										<td width="25%">Location Code</td>
										<td width="65%">Location Name</td>

									</tr>
								</tbody>
							</table>
							<table id="tblLoc" class="masterTable"
								style="width: 100%; border-collapse: separate;">

								<tr bgcolor="#72BEFC">
									<td width="15%"></td>
									<td width="25%"></td>
									<td width="65%"></td>

								</tr>
							</table>
						</div>
				</td>
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
				<td width="10%"><label>Report Type :</label></td>
				<td colspan="3"><s:select id="cmbDocType" path="strDocType"
						cssClass="BoxW124px">
						<s:option value="PDF">PDF</s:option>
						<s:option value="XLS">EXCEL</s:option>
						<s:option value="HTML">HTML</s:option>
						<s:option value="CSV">CSV</s:option>
					</s:select>
				</td>
			
				<td>Report Type</td>
				<td colspan="5"> 
				   <select id="cmbType" class="BoxW124px">
				      <option value="Detail">Detail</option>
					  <option value="Summary">Summary</option>
				  </select>
				</td>
			
			</tr>

		</table>

		<br>
			<p align="center">
				 <input type="button" value="Submit" onclick="return btnSubmit_Onclick();" class="form_button" />
				 <input type="button" value="Reset" class="form_button" onclick="funResetFields()"/>			     
			</p>  
			<s:input type="hidden" id="hidPropCodes" path="strPropertyCode"></s:input>
			<s:input type="hidden" id="hidLocCodes" path="strLocationCode"></s:input>
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