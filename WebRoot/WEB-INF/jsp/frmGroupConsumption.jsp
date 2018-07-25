 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="default.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Group Consumption Consolidated</title>
    <script type="text/javascript">
     
    //Ajax Waiting
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
    
      //Initialize from Data with default Value
      $(function() 
    		{
	    	   var startDate="${startDate}";
				var arr = startDate.split("/");
				Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
	    	  
    			$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
    			$("#txtFromDate" ).datepicker('setDate', Dat); 
    			
    			$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
    			$("#txtToDate" ).datepicker('setDate', 'today'); 
    			funSetAllLocationAllPrpoerty();
    			funGetGroupData();
    			funRemRows("tblloc");
    		});	
      
      //Get location Data fro All Property
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
     /*  $(function() {

			$('#txtLocCode').blur(function() {
				var code = $('#txtLocCode').val();
				if(code.trim().length > 0 && code !="?" && code !="/"){
					funSetLocation(code);
					$('#txtLocCode').val('');
				}
			});
		}); */
		
	  //Searching in table when user type on text filed
      $(document).ready(function()
      		{
      	var tablename='';
      			$('#searchGrp').keyup(function()
      			{
      				tablename='#tblGroup';
      				searchTable($(this).val(),tablename);
      			});
      			$('#txtLocCode').keyup(function()
    	    			{
    						tablename='#tblloc';
    	    				searchTable($(this).val(),tablename);
    	    			});
    			
      		});

		//Searching Logic
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
     /*  function funHelp(transactionName)
		{
  	  		fieldName=transactionName;
			window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
		} */
		
	//Set Data when user Select from help
    function funSetData(code)
		{
			switch (fieldName) 
			{
			    case 'locationmaster':
			    	funSetLocation(code);
			        break;
			}
		}
		
	//Get Location data
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
	   
	   //Filling Group data in grid
		function funfillGroupGrid(strGroupCode,strGroupName)
		{
			 	var table = document.getElementById("tblGroup");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    row.insertCell(0).innerHTML= "<input id=\"cbGSel."+(rowCount)+"\" type=\"checkbox\" class=\"GCheckBoxClass\" name=\"Groupthemes\" checked=\"checked\"  value='"+strGroupCode+"' />";
			    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"15%\" id=\"strGCode."+(rowCount)+"\" value='"+strGroupCode+"' >";
			    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"strGName."+(rowCount)+"\" value='"+strGroupName+"' >";
		}
		 
		    
	   //Filling Lcoation data in grid
		function funfillLocationGrid(strLocCode,strLocationName)
		{
				var table = document.getElementById("tblloc");
				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);
				
				row.insertCell(0).innerHTML= "<input id=\"cbLocSel."+(rowCount)+"\" name=\"Locthemes\" type=\"checkbox\" class=\"LocCheckBoxClass\"  checked=\"checked\" value='"+strLocCode+"' />";
				row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"strLocCode."+(rowCount)+"\" value='"+strLocCode+"' >";
				row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\"strLocName."+(rowCount)+"\" value='"+strLocationName+"' >";
		}
		
	   //Remove all row from table(Grid) Pass parameter table ID
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
	   
	   //Check Which Location and Group is Selected 
		$(document).ready(function () 
				{
					 $("#chkLocALL").click(function () {
						    $(".LocCheckBoxClass").prop('checked', $(this).prop('checked'));
						});
					$("#chkGALL").click(function () 
					{
					    $(".GCheckBoxClass").prop('checked', $(this).prop('checked'));
					    funGroupChkOnClick();
					  
					});
					
				});
		
	   
	    //Check Validation when User Submit the Data
		function formSubmit()
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
		    else
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
				 $("#hidLocCodes").val(strLocCode);
				 var strGCode="";
				 
				 $('input[name="Groupthemes"]:checked').each(function() {
					 if(strGCode.length>0)
						 {
						 strGCode=strGCode+","+this.value;
						 }
						 else
						 {
							 strGCode=this.value;
						 }
					 
					});
				 $("#hidGCode").val(strGCode);
				 
		    	document.forms["frmGroupConsumption"].submit();
		    }
	    } 
	    
	    //Reset field  
	    function funResetFields()
		{
			location.reload(true); 
		}
	</script>    
  </head>
  	
	<body onload="funOnload();">
	<div id="formHeading">
		<label>Group Consumption Consolidated</label>
	</div>
		<s:form name="frmGroupConsumption" method="GET" action="rptGroupConsumption.html" target="_blank">
	   		<br />
	   		<table class="transTable">
			    <tr>
					<td width="10%"><label>From Date :</label></td>
					<td colspan="1" width="10%"><s:input id="txtFromDate" path="dtFromDate" required="true" readonly="readonly" cssClass="calenderTextBox"/></td>
					<td width="10%"><label>To Date :</label></td>
					<td colspan="1"><s:input id="txtToDate" path="dtToDate"  readonly="readonly" required="true" cssClass="calenderTextBox"/>
					</td>	
				</tr>
				</table>
				
				<br>
				<table class="transTable">
				<tr><td width="49%">Location&nbsp;&nbsp;&nbsp;
				<input type="text" id="txtLocCode" 
					ondblclick="funHelp('locationmaster')"
						style="width: 50%;background-position: 225px 2px;"  class="searchTextBox" placeholder="Type to search"> 
					<label id="lblToLocName"></label>
				</td>
				
				<td width="49%">Group&nbsp;&nbsp;&nbsp;
			<input type="text"  style="width: 50%;background-position: 225px 2px;" 
			id="searchGrp" placeholder="Type to search" Class="searchTextBox">
		 </td></tr>
			<tr>
				<td style="padding: 0 !important;">
					<div
						style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; overflow-x: hidden; overflow-y: scroll;">

						<table id="" class="masterTable"
							style="width: 100%; border-collapse: separate;">
							<tbody>
								<tr bgcolor="#72BEFC">
									<td width="15%"><input type="checkbox" id="chkLocALL" checked="checked"
										/>Select</td>
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
				</td>
				<td style="padding: 0 !important;">
					<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; overflow-x: hidden; overflow-y: scroll;">
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
			</tr>
		</table>
		<br>
		<table class="transTable">
			<tr>
				<td width="10%"><label>Report Type</label></td>
				<td colspan="3"><s:select id="cmbDocType" path="strDocType"
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
				 <input type="button" value="Submit" onclick="return formSubmit();" class="form_button" />
				 <input type="button" value="Reset" class="form_button" onclick="funResetFields()"/>			     
			</p>
			<s:input type="hidden" id="hidLocCodes" path="strFromLocCode"></s:input>
		<s:input type="hidden" id="hidGCode" path="strGCode"></s:input>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
		</s:form>
	</body>
</html>