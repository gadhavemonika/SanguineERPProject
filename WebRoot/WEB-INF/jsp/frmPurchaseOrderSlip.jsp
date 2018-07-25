<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Purchase Order Slip</title>
<script type="text/javascript">
/**
 * Ready Function for Initialize textField with default value
 * And Set date in date picker 
 * And Getting session Value
 */
	$(function() 
		{		
			var startDate="${startDate}";
			var arr = startDate.split("/");
			Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
			$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtFromDate" ).datepicker('setDate', Dat);
			$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtToDate" ).datepicker('setDate', 'today');
			
			$('#txtSuppCode').blur(function() {
				var code = $('#txtSuppCode').val();
				if(code.trim().length > 0 && code !="?" && code !="/"){
					funSetSupplier(code);
					$('#txtSuppCode').val('');
				}
			});
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
	 * Open Help Form 
	 */
	function funHelp(transactionName)
	{
		fieldName = transactionName;
		if(transactionName=="Topurchaseorder")
		{
//			transactionName="purchaseorder";
		transactionName="purchaseorderslip";
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
		
		case "purchaseorder":
			
			$("#txtFromPOCode").val(code); 
			funGetDate(code,"dtFromPODate");
			break;
			
		case "Topurchaseorder":
			$("#txtToPOCode").val(code);
			funGetDate(code,"dtToPODate");
			break;
			
	 	case 'suppcode':
	    	funSetSupplier(code);
	        break;
	        
	 	case "purchaseorderslip":
			$("#txtFromPOCode").val(code); 
			funGetDate(code,"dtFromPODate");
			break;
		}
	}
	
	/**Get PO Data
	 */
	function funGetDate(code,filedId)
	{
		var searchUrl="";
		searchUrl=getContextPath()+"/getPODate.html?POCode="+code;
		$.ajax({
	        type: "GET",
	        url: searchUrl,
		    dataType: "text",
		    success: function(response)
		    {
		    	$("#"+filedId).val(response);
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
	  * Fill Supplier data in grid
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
		 * Remove All Row from Grid
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
				 /**
				  * set selected supplier code in hiddentext field
				 **/		
				 $("#hidSuppCode").val(strSuppCode);
				 document.forms[0].action = "rptPurchaseOrderSlip.html";
			}
	}
</script>
</head>
<body>
<div id="formHeading">
		<label>Purchase Order Slip</label>
	</div>

<s:form method="GET" action="rptPurchaseOrderSlip.html" target="_blank">
<br />
<br />
<table class="masterTable">
	<tr><th colspan="8"></th></tr>
	<tr>
				<td width="10%"><label id="lblFromDate">From Date</label></td>
				<td width="10%"><s:input id="txtFromDate" name="fromDate"
						path="dtFromDate" cssClass="calenderTextBox" required="true" /> <s:errors
						path="dtFromDate"></s:errors></td>
						<td></td>
				<td width="10%"><label id="lblToDate">To Date</label></td>
				<td colspan="3"><s:input id="txtToDate" name="toDate"
						path="dtToDate" cssClass="calenderTextBox" required="true"/> <s:errors
						path="dtToDate"></s:errors></td>
						
			</tr>
		</table>
		<br>
		<table class="masterTable">
			<tr>
				<td width="49%">Supplier&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 <input id="txtSuppCode"  ondblclick="funHelp('suppcode')" Class="searchTextBox" />
				<label id="txtSuppName"></label>
				
				</td>
			</tr>
			<tr>
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
		<table class="masterTable">
			<tr>
				<td width="15%">Purchase Order Code</td>
			<td width="1%" colspan="2">		
				<s:input type ="text" path="strFromDocCode" id="txtFromPOCode" name="strFromPOCode" readonly="true" placeholder="From PO Code"  class="searchTextBox" style="width: 150px;background-position: 136px 4px;" ondblclick="funHelp('purchaseorderslip')"/>
			</td> 
				<td><input type ="text" id="dtFromPODate" name="dtFromPODate" readonly="readonly" class="BoxW116px" /> </td>
				<td width="0%" colspan="2">
				<s:input type ="text" path="strToDocCode" id="txtToPOCode" name="strToPOCode" readonly="true" placeholder="To PO Code"  class="searchTextBox" style="width: 150px;background-position: 136px 4px;" ondblclick="funHelp('Topurchaseorder')"/> 
				<td><input type ="text" id="dtToPODate" name="dtToPODate" readonly="readonly" class="BoxW116px" /> </td>
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
</s:form>

</body>
</html>