<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="default.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Material Issue Slip</title>
<script type="text/javascript">

	/**
	 * Ready Function for Searching in Table
	 */
	$(document).ready(function()
		{
			var tablename='';
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
		
	var fieldName;
	
	/**
	 * Ready Function for Initialization Text Field with default value 
	 * Set Date in date picker
	 * Getting session value
	 */
	$(function() 
			{	
				var startDate="${startDate}";
				var arr = startDate.split("/");
				Date1=arr[0]+"-"+arr[1]+"-"+arr[2];
				$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
				$("#txtFromDate" ).datepicker('setDate', Date1);
				$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
				$("#txtToDate" ).datepicker('setDate', 'today');
				 var strPropCode='<%=session.getAttribute("propertyCode").toString()%>';
    			 funRemRows("tblFromloc");
    			 funRemRows("tblToloc");
    			 var locationCode ='<%=session.getAttribute("locationCode").toString()%>';
    			 funSetFromLocation(locationCode);
    			 funSetAllLocationAllPrpoerty();
			});
	
	/**
	* Reset Field Click On Reset Button
	**/
	function funResetFields() {
		$("#txtMISCode").val('');
		$("#txtFromMISCode").val('');
	}

	/**
	*  Open Help Form
	**/
	function funHelp(transactionName) {
		fieldName = transactionName;
		if (transactionName == "ToMIS") {
// 			transactionName = "MIS";
			transactionName = "MISslip";
		}
// 		window.showModalDialog("searchform.html?formname=" + transactionName
// 				+ "&searchText=", "",
// 				"dialogHeight:600px;dialogWidth:1100px;dialogLeft:200px;")
window.open("searchform.html?formname=" + transactionName
				+ "&searchText=", "",
				"dialogHeight:600px;dialogWidth:1100px;dialogLeft:200px;")
	}

	/**
	* 	Get and Set from Help From windows
	**/
	function funSetData(code) {
		switch (fieldName) 
		{
			case 'StoreLocationTo':
				funSetFromLocation(code);
				break;
	
			case "MIS":
	
				$("#txtFromMISCode").val(code);
				break;
	
			case "ToMIS":
				$("#txtTOMISCode").val(code);
				break;
				
			case "MISslip":
				$("#txtFromMISCode").val(code);
				break;	
		}
	}
	
	/**
	* Get and Set All Location All Property
	**/
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
						funfillToLocationGrid(response[i].strLocCode,response[i].strLocName);
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
	
	/**
	*  Get and Set From Location Data Passing Value(Location Code)  
	**/
	function funSetFromLocation(code) {
		var searchUrl = "";
		searchUrl = getContextPath()
				+ "/loadLocationMasterData.html?locCode=" + code;

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
					funfillFromLocationGrid(response.strLocCode,response.strLocName);
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
		*  Filling Location From Grid 
		**/
	    function funfillFromLocationGrid(strLocCode,strLocationName)
		{
			 	var table = document.getElementById("tblFromloc"); 
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    
			    row.insertCell(0).innerHTML= "<input id=\"cbFromLocSel."+(rowCount)+"\" name=\"FromLocthemes\" type=\"checkbox\" class=\"FromLocCheckBoxClass\"  checked=\"checked\" value='"+strLocCode+"' />";
			    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"strFromLocCode."+(rowCount)+"\" value='"+strLocCode+"' >";
			    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\"strFromLocName."+(rowCount)+"\" value='"+strLocationName+"' >";
		}
	    /**
		*  Filling Location To Grid 
		**/
	    function funfillToLocationGrid(strLocCode,strLocationName)
		{
			
			 	var table = document.getElementById("tblToloc");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    
			    row.insertCell(0).innerHTML= "<input id=\"cbToLocSel."+(rowCount)+"\" name=\"ToLocthemes\" type=\"checkbox\" class=\"ToLocCheckBoxClass\"  checked=\"checked\" value='"+strLocCode+"' />";
			    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"strToLocCode."+(rowCount)+"\" value='"+strLocCode+"' >";
			    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\"strToLocName."+(rowCount)+"\" value='"+strLocationName+"' >";
		}
			
	    /**
		*  Remove All Row From Grid 
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
		*  Select All From Location and To Location When User Click On Select All Check Box
		**/
	    $(document).ready(function () 
				{
					$("#chkFromLocALL").click(function () {
					    $(".FromLocCheckBoxClass").prop('checked', $(this).prop('checked'));
					});
					
					$("#chkToLocALL").click(function () {
					    $(".ToLocCheckBoxClass").prop('checked', $(this).prop('checked'));
					});
					
				});
	    
	    /**
		*  Check Validation Before Submit The Form
		**/
	    function funCallFormAction(actionName,object) 
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
			    var strFromLocCode="";
				$('input[name="FromLocthemes"]:checked').each(function()
					  {
						 if(strFromLocCode.length>0)
							 {
							 	strFromLocCode=strFromLocCode+","+this.value;
							 }
							 else
							 {
								 strFromLocCode=this.value;
							 }
						 
					 });
					
				 /**
				  *  Set All Selected From Location In hidden TextField 
				 **/
				 $("#hidFromLocCodes").val(strFromLocCode);
				 
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
				 
				 /**
				  *  Set All Selected To Location In hidden TextField 
				 **/
				 $("#hidToLocCodes").val(strToLocCode);
				 document.forms[0].action = "rptMISSlip.html";
		    }
	
</script>
</head>
<body>
	<div id="formHeading">
		<label id="lblFormHeadingName" >Material Issue Slip</label>
	</div>
	<s:form name="MISSlip" method="POST" action="rptMISSlip.html"
		target="_blank">

		<br />
		<table class="transTable">
		<tr><th colspan="8"></th></tr>
			<tr>
				<td width="10%"><label id="lblFromDate">From Date</label></td>
				<td width="20%"><s:input id="txtFromDate" name="fromDate"
						path="dtFromDate" cssClass="calenderTextBox" required="true"/> <s:errors
						path="dtFromDate"></s:errors></td>
						<td width="15%"></td>
				<td width="10%"><label id="lblToDate">To Date</label></td>
				<td width="10%"><s:input id="txtToDate" name="toDate"
						path="dtToDate" cssClass="calenderTextBox" required="true"/> <s:errors
						path="dtToDate"></s:errors></td>
						<td></td>
						<td></td>
						
			</tr>
			</table>
			<br>
		<table class="transTable">
			<tr><td width="49%">From Location&nbsp;&nbsp;&nbsp;<input type="text" id="txtFromLocCode" 
			ondblclick="funHelp('StoreLocationTo')" Class="searchTextBox"></input>
			<label id="lblFromLocName"></label></td>
			<td width="49%">To Location&nbsp;&nbsp;&nbsp;
			<input type="text" id="txtToLocCode" 
			 style="width: 50%;background-position: 225px 2px;"  class="searchTextBox" placeholder="Type to search"></input>
			<label id="lblToLocName"></label></td></tr>
			<tr>
			
				<td style="padding: 0 !important;">
						<div
							style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; overflow-x: hidden; overflow-y: scroll;">

							<table id="" class="masterTable"
								style="width: 100%; border-collapse: separate;">
								<tbody>
									<tr bgcolor="#72BEFC">
										<td width="15%"><input type="checkbox" id="chkFromLocALL"
											checked="checked" />Select</td>
										<td width="25%">From Location Code</td>
										<td width="65%">From Location Name</td>

									</tr>
								</tbody>
							</table>
							<table id="tblFromloc" class="masterTable"
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
										id="chkToLocALL"/>Select</td>
										<td width="25%">To Location Code</td>
										<td width="65%">To Location Name</td>

									</tr>
								</tbody>
							</table>
							<table id="tblToloc" class="masterTable"
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
			<%-- <tr>
				<td><label>From Location </label></td>
				<td colspan="2"><s:input id="txtLocFrom" name="txtLocFrom"
						path="strFromLocCode" ondblclick="funHelp('StoreLocationTo')"
						cssClass="searchTextBox" /> <label id="lblLocFrom"
					Class="namelabel"></label></td>

				<td><label>To Location </label></td>
				<td colspan="3"><s:input id="txtLocTo" name="txtLocTo" path="strToLocCode"
						ondblclick="funHelp('locon');" cssClass="searchTextBox" /> <label
					id="lblLocTo" Class="namelabel"></label></td>
			</tr> --%>
			<tr>
				<td width="10%">From MIS Code</td>
				<td width="1%" colspan="2"><s:input type="text"
						path="strFromDocCode" id="txtFromMISCode" name="strMISCode"
						readonly="true" placeholder="From MIS Code" class="searchTextBox"
						style="width: 118px;background-position: 104px 2px;"
						ondblclick="funHelp('MISslip')" /></td>
				<td width="10%">To MIS Code</td>
				<td width="0%" colspan="4"><s:input type="text"
						path="strToDocCode" id="txtTOMISCode" name="strMISCode"
						readonly="true" placeholder="To MIS Code" class="searchTextBox"
						style="width: 118px;background-position: 104px 2px;"
						ondblclick="funHelp('ToMIS')" /></td>

			</tr>
			<tr>
				<td><label>Report Type</label></td>
				<td colspan="8"><s:select id="cmbDocType" path="strDocType"
						cssClass="BoxW124px">
						<s:option value="PDF">PDF</s:option>
						<s:option value="XLS">EXCEL</s:option>
						<s:option value="HTML">HTML</s:option>
						<s:option value="CSV">CSV</s:option>
					</s:select></td>
			</tr>
			<tr>
				<td colspan="8"></td>

			</tr>
		</table>
		<br>
		<p align="center">
			<input type="submit" value="Submit"	onclick="return funCallFormAction('submit',this)" 
			class="form_button" />
			 <input type="button" value="Reset"
			class="form_button" onclick="funResetFields()" />
		</p>
			<s:input type="hidden" id="hidFromLocCodes" path="strFromLocCode"></s:input>
			<s:input type="hidden" id="hidToLocCodes" path="strToLocCode"></s:input>
	</s:form>
</body>
</html>