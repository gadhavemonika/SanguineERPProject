<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MISRegisterReport</title>

<script type="text/javascript">

$(document).ready(function() 
		{		
		
				var startDate="${startDate}";
				var arr = startDate.split("/");
				Date1=arr[0]+"-"+arr[1]+"-"+arr[2];
			    $("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
			    $("#txtFromDate" ).datepicker('setDate', Date1);
			    $("#txtFromDate").datepicker();	
			
			    $("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
				$("#txtToDate" ).datepicker('setDate', 'today');
				$("#txtToDate").datepicker();	
				
    	    			
  	    			$('#txtLocCode').keyup(function()
  	    	    			{
  	    						tablename='#tblToloc';
  	    	    				searchTable($(this).val(),tablename);
  	    	    			});
  	    			$('#txtFrmLocCode').keyup(function()
  	    	    			{
  	    						tablename='#tblFromloc';
  	    	    				searchTable($(this).val(),tablename);
  	    	    			});
				
				 funSetAllLocationAllPrpoerty();;
				 
				
			
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
									funfillToLocationGrid(response[i].strLocCode,response[i].strLocName);
									funfillFromLocationGrid(response[i].strLocCode,response[i].strLocName);
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
			
			  
			    //Fill To Location Data
			    function funfillToLocationGrid(strLocCode,strLocationName)
				{
					
					 	var table = document.getElementById("tblToloc");
					    var rowCount = table.rows.length;
					    var row = table.insertRow(rowCount);
					    
					    row.insertCell(0).innerHTML= "<input id=\"cbToLocSel."+(rowCount)+"\" name=\"ToLocthemes\" type=\"checkbox\" class=\"ToLocCheckBoxClass\"  checked=\"checked\" value='"+strLocCode+"' />";
					    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"strToLocCode."+(rowCount)+"\" value='"+strLocCode+"' >";
					    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\"strToLocName."+(rowCount)+"\" value='"+strLocationName+"' >";
				}
			    
			  
			    
			    //Fill From Location Data
			    function funfillFromLocationGrid(strLocCode,strLocationName)
				{
					
					 	var table = document.getElementById("tblFromloc");
					    var rowCount = table.rows.length;
					    var row = table.insertRow(rowCount);
					    
					    row.insertCell(0).innerHTML= "<input id=\"cbFromLocSel."+(rowCount)+"\" name=\"FromLocthemes\" type=\"checkbox\" class=\"FromLocCheckBoxClass\"  checked=\"checked\" value='"+strLocCode+"' />";
					    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"strFromLocCode."+(rowCount)+"\" value='"+strLocCode+"' >";
					    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\"strFromLocName."+(rowCount)+"\" value='"+strLocationName+"' >";
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
			
			
			

function funHelp(transactionName)
{
	fieldName = transactionName;
//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	
}

		 	function funSetData(code)
			{
		 		switch (fieldName) 
 				{
 					
 				    case 'locationmaster':
 				    	funSetToLocation(code);
 				        break;
 				        
 				        
 				}
			}
	
	//Select Location When Clicking Select All Check Box
	 $(document).ready(function () 
				{
										
					$("#chkToLocALL").click(function () {
					    $(".ToLocCheckBoxClass").prop('checked', $(this).prop('checked'));
					});
					
					$("#chkFromLocALL").click(function () {
					    $(".FromLocCheckBoxClass").prop('checked', $(this).prop('checked'));
					});
					
					
					
				});
	
	
	//Submit Data after clicking Submit Button with validation 
	   function btnSubmit_Onclick()
	    {
	
	            var strToLocCode="";
				 
				 $('input[name="ToLocthemes"]:checked').each(function() {
					 if(strToLocCode.length>0)
						 {
						 strToLocCode=strToLocCode+","+this.value;
						 }
						 else
						 {
							 strToLocCode=this.value;
						 }
					 
					});
				 if(strToLocCode=="")
				 {
				 	alert("Please Select To Location");
				 	return false;
				 }
				 $("#hidToLocCodes").val(strToLocCode);
				
		    	
		   // }
	    
	    var strFromLocCode="";
		 
		 $('input[name="FromLocthemes"]:checked').each(function() {
			 if(strFromLocCode.length>0)
				 {
				 strFromLocCode=strFromLocCode+","+this.value;
				 }
				 else
				 {
					 strFromLocCode=this.value;
				 }
			 
			});
		 if(strFromLocCode=="")
		 {
		 	alert("Please Select To Location");
		 	return false;
		 }
		 $("#hidFrmLocCodes").val(strFromLocCode);
		
   	document.forms["frmMaterialIssueRegisterReport"].submit();
   	}
</script>

</head>
<body onload="funOnLoad();">
	<div id="formHeading">
	Material Issue Register Report
	</div>
	<s:form name="frmMaterialIssueRegisterReport" method="POST"
		action="rptMISRegisterReport.html" >
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		<table class="transTable">
			<tr>
				<td><label>From Date</label>
				<td><s:input path="dtFromDate" id="txtFromDate" required="required" cssClass="calenderTextBox" /></td>
				<td ><label>To Date</label>
				<td><s:input path="dtToDate" id="txtToDate" required="required" cssClass="calenderTextBox" /></td>
			</tr>
			<tr>
				<td><label>Report Type</label></td>
				<td colspan="4">
					<s:select id="cmbDocType" path="strDocType" cssClass="BoxW124px">

						<s:option value="XLS">EXCEL</s:option>

					</s:select>
				</td>
			</tr>
			
			<tr>
			<td colspan="2">FromLocation&nbsp;&nbsp;&nbsp;
			<input type="text" id="txtFrmLocCode" 
			 style="width: 35%;background-position: 150px 2px;"  Class="searchTextBox" placeholder="Type to search"  ></input>
			<label id="lblFrmLocName"></label></td>
			
			<td colspan="2">ToLocation&nbsp;&nbsp;&nbsp;
			<input type="text" id="txtLocCode" 
			 style="width: 35%;background-position: 150px 2px;"  Class="searchTextBox" placeholder="Type to search"  ></input>
			<label id="lblLocName"></label></td>
			
			
			
			
			</tr>
			
			<tr>
			<td colspan="2">
						<div
							style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; overflow-x: hidden; overflow-y: scroll;">

							<table id="" class="masterTable"
								style="width: 100%; border-collapse: separate;">
								<tbody>
									<tr bgcolor="#72BEFC">
										<td width="15%"><input type="checkbox" checked="checked" 
										id="chkFromLocALL"/>Select</td>
										<td width="25%">From Location Code</td>
										<td width="65%">From Location Name</td>

									</tr>
								</tbody>
							</table>
							<table id="tblFromloc" class="masterTable"
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
										id="chkToLocALL"/>Select</td>
										<td width="25%">To Location Code</td>
										<td width="65%">To Location Name</td>

									</tr>
								</tbody>
							</table>
							<table id="tblToloc" class="masterTable"
								style="width: 100%; border-collapse: separate;">

								<tr bgcolor="#72BEFC">
									

								</tr>
							</table>
						</div>
				</td>
				
			
			</tr>
			
			

		</table>
		
		<p align="center">
			<input type="submit" value="Submit"
				onclick="return btnSubmit_Onclick()"
				class="form_button" /> &nbsp; &nbsp; &nbsp; <a
				STYLE="text-decoration: none"
				href="frmMaterialIssueRegisterReport.html?saddr=${urlHits}"><input
				type="button" id="reset" name="reset" value="Reset"
				class="form_button" /></a>
		</p>
		<br>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
			
			<s:input type="hidden" id="hidToLocCodes" path="strToLoc"></s:input>	
			<s:input type="hidden" id="hidFrmLocCodes" path="strFromLoc"></s:input>	
			
		</div>
	</s:form>
	<script type="text/javascript">
	
	</script>
</body>
</html>