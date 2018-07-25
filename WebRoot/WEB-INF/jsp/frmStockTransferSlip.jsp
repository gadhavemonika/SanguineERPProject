<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Web Stocks</title>
    <script type="text/javascript">
    /**
	 * Ready Function for Initialize textField with default value
	 * And Set date in date picker 
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
    		});
    
    var fieldName="";
    
    /**
	 * Open Help windows
	 */
    function funHelp(transactionName)
	{
    	fieldName=transactionName;
    	if(transactionName=="stktransfercode1")
		{
// 			transactionName="stktransfercode";
			transactionName="stktransfercodeslip";
		}
    	if(transactionName=="FromLocation")
		{
			transactionName="locationmaster";
		}
    	if(transactionName=="ToLocation")
		{
			transactionName="locationmaster";
		}
		// window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
    }
    /**
	 * Set Data after selecting form Help windows
	 */
    function funSetData(code)
	{
    	switch(fieldName)
    	{
    		case "stktransfercode":
    			$("#txtFromSTCode").val(code);
    			
    		break;
    		
    		case "stktransfercode1":
        		$("#txtToSTCode").val(code);
        		
        	break;
        	
    		case 'FromLocation':
				funSetLocationFrom(code);
				break;
	
			case 'ToLocation':
				funSetLocationTo(code);
				break;
				
			case "stktransfercodeslip":
    			$("#txtFromSTCode").val(code);
    		break;
	
    	}
	}
    /**
	 * Set Location From Data after selecting form Help windows
	 */
    function funSetLocationFrom(code) {
		var searchUrl = "";
		searchUrl = getContextPath() + "/loadLocationMasterData.html?locCode="
				+ code;
		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				if (response.strLocCode == 'Invalid Code') {
					alert("Invalid Location Code");
					$("#txtLocFrom").val('');
					$("#lblLocFrom").text("");
					$("#txtLocFrom").focus();
				} else {
					$("#txtLocFrom").val(response.strLocCode);
					$("#lblLocFrom").text(response.strLocName);
					$("#txtLocTo").focus();
					strLocationType = response.strType;
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
	 * Set Location To Data after selecting form Help windows
	 */
	function funSetLocationTo(code) {
		var searchUrl = "";
		searchUrl = getContextPath() + "/loadLocationMasterData.html?locCode="
				+ code;
		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				if (response.strLocCode == 'Invalid Code') {
					alert("Invalid Location Code");
					$("#txtLocTo").val('');
					$("#lblLocTo").text("");
					$("#txtLocTo").focus();
				} else {
					$("#txtLocTo").val(response.strLocCode);
					$("#lblLocTo").text(response.strLocName);
					$("#txtProdCode").focus();

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


			if($("#cmbDocType").val()=="XLS")
	    	{
	    		flag=false;
		    	var reportType=$("#cmbDocType").val();
				var locCodeFrom=$("#txtLocFrom").val();
				var locCodeTo=$("#txtLocTo").val();
				var param1=reportType+","+locCodeFrom+","+locCodeTo;

				document.forms[0].action =  "rpttransferslipExcel.html?param1="+param1;
	    	}
			else
				{
					 document.forms[0].action = "rpttransferslip.html";
				}
	    }
	</script>
    
  </head>
  
	<body>
	<div id="formHeading">
		<label>Stock Transfer Slip</label>
	</div>
	<br />
	<br />
		<s:form name="frmStockTransferSlip" method="POST" action="rpttransferslip.html" target="_blank">
	   		<table class="masterTable">
			<tr><th colspan="8"></th></tr>
	   		<tr>
				<td width="15%"><label id="lblFromDate">From Date</label></td>
				<td width="10%"><s:input id="txtFromDate" name="fromDate"
						path="dtFromDate" cssClass="calenderTextBox" required="true"/> 
						<s:errors path="dtFromDate"></s:errors></td>
				<td width="15%"></td>
				<td width="10%"><label id="lblToDate">To Date</label></td>
				<td colspan="3"><s:input id="txtToDate" name="toDate"
						path="dtToDate" cssClass="calenderTextBox" required="true"/>
						<s:errors path="dtToDate"></s:errors></td>
						
			</tr>
			<tr>
				<td><label>From Location </label></td>
				<td colspan="2"><s:input id="txtLocFrom" name="txtLocFrom"
						path="strFromLocCode" ondblclick="funHelp('FromLocation')"
						cssClass="searchTextBox" /> <label id="lblLocFrom"
					Class="namelabel"></label></td>

				<td width="10%"><label>To Location </label></td>
				<td  colspan="3"><s:input id="txtLocTo" name="txtLocTo" path="strToLocCode"
						ondblclick="funHelp('ToLocation');" cssClass="searchTextBox" /> <label
					id="lblLocTo" Class="namelabel"></label></td>
			</tr>
			    <tr>
			        <td  width="15%"><label >From Stock Transfer Code</label></td>
			        <td>
			        	<s:input id="txtFromSTCode" path="strFromDocCode" ondblclick="funHelp('stktransfercodeslip')" cssClass="searchTextBox" cssStyle="width:150px;background-position: 136px 4px;"/>
			        </td>
			        <td></td>
			        <td width="15%"><label >To Stock Transfer Code</label></td>
			        <td colspan="5" >
			        	<s:input id="txtToSTCode" path="strToDocCode" ondblclick="funHelp('stktransfercode1')" cssClass="searchTextBox" cssStyle="width:150px;background-position: 136px 4px;"/>
			        </td>
			    </tr>
			    <tr>
			      
			      	<td><label>Report Type</label></td>
					<td colspan="8">	<s:select id="cmbDocType" path="strDocType" cssClass="BoxW124px">
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
				<input type="submit" value="Submit"  class="form_button" onclick="return funCallFormAction('submit',this)"  />
				 <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/>
			</p>
		</s:form>
	</body>
</html>