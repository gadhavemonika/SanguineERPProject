 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="default.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Consolidated Receipt value Store Wise BreakUP</title>
     <style>
   #tblloc tr:hover{
	background-color: #72BEFC;
	
}
</style>
    <script type="text/javascript">
    
  //Ajax Wait Image display
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
    
   //Serching on Location when user type in location text field
    $(document).ready(function()
    		{
    			var tablename='';
    			$('#txtToLocCode').keyup(function()
    	    			{
    						tablename='#tblloc';
    	    				searchTable($(this).val(),tablename);
    	    			});
    			
    		});
   
    //Set Start Date in date picker
      $(function() 
    		{
	    	   var startDate="${startDate}";
				var arr = startDate.split("/");
				Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
	    	  
    			$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
    			$("#txtFromDate" ).datepicker('setDate', Dat); 
    			
    			$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
    			$("#txtToDate" ).datepicker('setDate', 'today'); 
    			funRemRows();
    			funSetAllLocationAllPrpoerty();
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
    
      //Get and set All Location on the basis of all Property
      function funSetAllLocationAllPrpoerty() {
			var searchUrl = "";
			searchUrl = getContextPath()
					+ "/loadAllLocationForAllProperty.html";
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
    
      
      
      var fieldName="";
      //Open Help
      function funHelp(transactionName)
		{
  	  		fieldName=transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
		}
      
    //Set data on the basis of selecting Help
    function funSetData(code)
		{
			switch (fieldName) 
			{
			    case 'locationmaster':
			    	funSetLocation(code);
			        break;
			}
		}
    
    //Set Location Data
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
	   
	    
 		//Filling Location Grid
	    function funfillLocationGrid(strLocCode,strLocationName)
		{
			
			 	var table = document.getElementById("tblloc");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    
			    row.insertCell(0).innerHTML= "<input id=\"cbLocSel."+(rowCount)+"\" name=\"Locthemes\" type=\"checkbox\" class=\"LocCheckBoxClass\"  checked=\"checked\" value='"+strLocCode+"' />";
			    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"strLocCode."+(rowCount)+"\" value='"+strLocCode+"' >";
			    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\"strLocName."+(rowCount)+"\" value='"+strLocationName+"' >";
		}
			
	    //Remove all Row from Location Grid
	    function funRemRows() 
		{
			var table = document.getElementById("tblloc");
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
		}
	    
	  //Submit Data after clicking Submit Button with validation 
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
		    	document.forms["frmConsReceiptValStoreWiseBreskUPReport"].submit();
		    }
	    } 
	  
	   //Select All Location code on click Select All Combo Box
	   $(document).ready(function () 
				{
					$("#chkLocALL").click(function () {
					    $(".LocCheckBoxClass").prop('checked', $(this).prop('checked'));
					});
					
				});
	   
		//Reset All Filed After Clicking Reset Button
	    function funResetFields()
		{
			location.reload(true); 
		}
	</script>    
  </head>
  	
	<body>
	<div id="formHeading">
		<label>Consolidated Receipt value Store Wise BreakUP</label>
	</div>
		<s:form name="frmConsReceiptValStoreWiseBreskUPReport" method="POST" action="rptConsReceiptValStoreWiseBreskUPReport.html" target="_blank" >
	   		<br />
	   		<table class="transTable">
			    <tr>
					<td width="10%"><label>From Date :</label></td>
					<td width="10%" colspan="1"><s:input id="txtFromDate" path="dtFromDate" required="true" readonly="readonly" cssClass="calenderTextBox"/></td>
					<td width="10%"><label>To Date :</label></td>
					<td colspan="1"><s:input id="txtToDate" path="dtToDate" required="true" readonly="readonly" cssClass="calenderTextBox"/>
					</td>	
				</tr>
			    </table>
			    
			    <br>
			<table id="" class="transTable" >
				<tr><td colspan="5">Location&nbsp;&nbsp;&nbsp;
				<input type="text" id="txtToLocCode" 
					ondblclick="funHelp('locationmaster')" Class="searchTextBox" placeholder="Type to search"
					style="width: 25%;background-position: 240px 2px;"  ></input>
					<label id="lblToLocName"></label>
				</td></tr>
			</table>
		<div
			style="background-color: #a4d7ff; width: 95%; margin-left: 28px; border: 1px solid #ccc; display: block; height: 250px; overflow-x: hidden; overflow-y: scroll;">
			
			<table id="" class="masterTable"
				style="width: 100%; border-collapse: separate;">
				<tbody>
				
					<tr bgcolor="#72BEFC"> 
						<td width="6%"><input type="checkbox" id="chkLocALL" checked="checked"
							/>Select</td>
						<td width="25%">Location Code</td>
						<td width="65%">Location Name</td>

					</tr>
				</tbody>
			</table>
			<table id="tblloc" class="masterTable"
				style="width: 100%; border-collapse: separate;">

				<tr bgcolor="#72BEFC">
					<td width="6%"></td>
					<td width="23%"></td>
					<td width="65%"></td>

				</tr>
			</table>
		</div>
		<br>
			    <table class="transTable">
			    <tr>
					<td width="10%"><label>Report Type :</label></td>
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
			<s:input type="hidden" id="hidLocCodes" path="strFromLocCode"></s:input>
			<p align="center">
				 <input type="button" value="Submit" onclick="return formSubmit();" class="form_button" />
				 <input type="button" value="Reset" class="form_button" onclick="funResetFields()"/>			     
			</p>
			<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
		</s:form>
	</body>
</html>