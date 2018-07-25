<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Purchase Return Slip</title>
<script type="text/javascript">
	/**
	 * And Set date in date picker 
	 * And Getting session Value
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
			
			$('#txtSuppCode').blur(function() {
				var code = $('#txtSuppCode').val();
				if(code.trim().length > 0 && code !="?" && code !="/"){
					funSetSupplier(code);
					$('#txtSuppCode').val('');
				}
			});
			funRemRows("tblloc");
			funRemRows("tblSupp");
		});
	
	/**
	 * Reset form
	 */
	function funResetFields()
	{
		location.reload(true);
	}


	/**
	 * Open Help windows
	 */
	function funHelp(transactionName)
	{
		fieldName = transactionName;
		if(transactionName=="FromPurchaseReturn")
		{
//				transactionName="PurchaseReturn";
			transactionName="PurchaseReturnslip";
		}
	if(transactionName=="ToPurchaseReturn")
	{
//			transactionName="PurchaseReturn";
		transactionName="PurchaseReturnslip";
	}
	//	 window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
		 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
	}
	
	/**
	 * Set Data after selecting form Help windows
	 */
	function funSetData(code)
	{
		switch(fieldName)
		{
		
		case "FromPurchaseReturn":
			
			$("#txtFromPRCode").val(code); 
			break;
			
		case "ToPurchaseReturn":
			$("#txtToPRCode").val(code);
			break;
			
	 	case 'suppcode':
	    	funSetSupplier(code);
	        break;
	        
	 	 case 'locationmaster':
		    	funSetLocation(code);
		        break;
		}
	}
	
	/**
	 * Set Location Data after selecting Help windows
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
	 * Set Supplier Data after selecting Help windows
	 */
	function funSetSupplier(code) {
		var searchUrl = "";
		searchUrl = getContextPath()
				+ "/loadSupplierMasterData.html?partyCode=" + code;

		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				if ('Invalid Code' == response.strPCode) {
					alert('Invalid Code');
					$("#txtSuppCode").val('');
					$("#txtSuppName").text('');
					$("#txtSuppCode").focus();
				} else {
					funfillSupplier(response.strPCode,response.strPName);
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
	 * Fill location grid
	 */
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
	  * Fill supplier grid
	 **/
	 function funfillSupplier(strSuppCode,strSuppName) 
		{
			var table = document.getElementById("tblSupp");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input id=\"cbSuppSel."+(rowCount)+"\" type=\"checkbox\" checked=\"checked\" name=\"suppthemes\" value='"+strSuppCode+"' class=\"suppCheckBoxClass\" />";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"15%\" id=\"strSuppCode."+(rowCount)+"\" value='"+strSuppCode+"' >";
		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"strSuppName."+(rowCount)+"\" value='"+strSuppName+"' >";
		}
	    
	 /**
	  * Remove all rows from grid
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
		 * Select All Supplier
		 */
	 	$(document).ready(function () 
			{
				$("#chkSuppALL").click(function ()
				{
				    $(".suppCheckBoxClass").prop('checked', $(this).prop('checked'));
				});
										
			});
		
	 	/**
	 	 * Checking Validation before submiting the data
	 	**/
		function funCallFormAction(actionName,object) 
		{	
			if (actionName == 'submit') 
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
				 
				 var strSuppCode="";
				 $('input[name="suppthemes"]:checked').each(function() {
					 if(strSuppCode.length>0)
						 {
						 strSuppCode=strSuppCode+","+this.value;
						 }
						 else
						 {
							 strSuppCode=this.value;
						 }
					 
					});
				 $("#hidSuppCode").val(strSuppCode);
				 
				 document.forms[0].action = "rptPurchaseReturnSlip.html";
			}
		
	}
</script>
</head>
<body>
<div id="formHeading">
		<label>Purchase Return Slip</label>
	</div>

<s:form method="POST" action="rptPurchaseReturnSlip.html" target="_blank">
<br />
<br />
<table class="transTable">
	<tr><th colspan="8"></th></tr>
	<tr>
				<td width="10%"><label id="lblFromDate">From Date</label></td>
				<td width="10%"><s:input id="txtFromDate" name="fromDate"
						path="dtFromDate" cssClass="calenderTextBox" required="true"/> <s:errors
						path="dtFromDate"></s:errors></td>
						<td></td>
				<td width="10%"><label id="lblToDate">To Date</label></td>
				<td colspan="3"><s:input id="txtToDate" name="toDate"
						path="dtToDate" cssClass="calenderTextBox" required="true"/> <s:errors
						path="dtToDate"></s:errors></td>
						
			</tr>
		</table>
		<br>
		<table class="transTable">
			<tr>
				<td width="49%">Location&nbsp;&nbsp;&nbsp;
					<input type="text" id="txtLocCode" 
					ondblclick="funHelp('locationmaster')" Class="searchTextBox"></input>
					<label id="lblToLocName"></label>
			    </td>
				<td width="49%">Supplier&nbsp;&nbsp;&nbsp;
				 <input id="txtSuppCode"  ondblclick="funHelp('suppcode')" Class="searchTextBox" />
				<label id="txtSuppName"></label>
				
				</td>
			</tr>
			<tr>
				<td style="padding: 0 !important;">
					<div class="transTablex">
						<div
							style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; overflow-x: hidden; overflow-y: scroll;">

							<table id="" class="masterTable"
								style="width: 100%; border-collapse: separate;">
								<tbody>
									<tr bgcolor="#72BEFC">
										<td width="15%"><input type="checkbox" id="chkLocALL"/>Select</td>
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
				<td style="padding: 0 !important;">
					<div
						style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; overflow-x: hidden; overflow-y: scroll;">

						<table id="" class="masterTable"
							style="width: 100%; border-collapse: separate;">
							<tbody>
								<tr bgcolor="#72BEFC">
									<td width="15%"><input type="checkbox" id="chkSuppALL"
										onclick="funCheckUnchecksupp()" />Select</td>
									<td width="25%">Supplier Code</td>
									<td width="65%">Supplier Name</td>

								</tr>
							</tbody>
						</table>
						<table id="tblSupp" class="masterTable"
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
				<td width="15%">Purchase Return Code</td>
			<td width="10%">		
				<s:input type ="text" path="strFromDocCode" id="txtFromPRCode" name="strFromPRCode" readonly="true" placeholder="From PR Code"  class="searchTextBox" style="width: 150px;background-position: 136px 4px;" ondblclick="funHelp('FromPurchaseReturn')"/>
			</td> 
			<td>
				<s:input type ="text" path="strToDocCode" id="txtToPRCode" name="strToPRCode" readonly="true" placeholder="To PR Code"  class="searchTextBox" style="width: 150px;background-position: 136px 4px;" ondblclick="funHelp('ToPurchaseReturn')"/>
				</td> 
			</tr>
		<tr>
		<td><label>Report Type</label></td>
					<td colspan="8">
						<s:select id="cmbDocType" path="strDocType" cssClass="BoxW124px">
				    		<s:option value="PDF">PDF</s:option>
				    		<s:option value="XLS">EXCEL</s:option>
				    		<s:option value="HTML">HTML</s:option>
				    		<s:option value="CSV">CSV</s:option>
				    	</s:select>
			</td>
	</tr>
	<tr>
		<td colspan="8"></td>
		
	</tr>
</table>
<br>
		<p align="center">
			<input type="submit" value="Submit" onclick="return funCallFormAction('submit',this)"  class="form_button"/>
			 <input type="button" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
		<s:input type="hidden" id="hidSuppCode" path="strSuppCode"></s:input>
		<s:input type="hidden" id="hidLocCodes" path="strLocationCode"></s:input>
</s:form>

</body>
</html>