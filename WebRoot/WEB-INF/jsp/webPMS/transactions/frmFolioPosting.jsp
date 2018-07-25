<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	
	var fieldName;
    var totalAmt=0.00;
	
//reset fields
	function funResetFields()
	{
		var folioText=document.getElementById("strFolioNo");
		folioText.disabled=false;
	}
	
	
//set folio Data
	function funSetFolioData(folioNo)
	{
		$("#strFolioNo").val(folioNo);
		var folioText=document.getElementById("strFolioNo");
		folioText.disabled=true;
				
		/* var searchUrl=getContextPath()+"/loadFolioData.html?folioNo="+folioNo;
		$.ajax({
			
			url:searchUrl,
			type :"GET",
			dataType: "json",
	        success: function(response)
	        {
	        	if(response.strFolioNo=='Invalid Code')
	        	{
	        		alert("Invalid Folio No.");
	        		$("#strFolioNo").val('');
	        	}
	        	else
	        	{					        	    
	        		$("#strFolioNo").val(response.strFolioNo);	        			        	
	        	}
			},
			error: function(jqXHR, exception) 
			{
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
		}); */
	}


//set Reservation Data
	function funSetIncomeHeadData(incomeCode)
	{
		$("#strIncomeHead").val(incomeCode);
		var searchUrl=getContextPath()+"/loadIncomeHeadMasterData.html?incomeCode="+incomeCode;
		$.ajax({
			
			url:searchUrl,
			type :"GET",
			dataType: "json",
	        success: function(response)
	        {
	        	if(response.strIncomeHeadCode=='Invalid Code')
	        	{
	        		alert("Invalid Reservation No.");
	        		$("#strIncomeHead").val('');
	        	}
	        	else
	        	{
	        		$("#strIncomeHead").val(response.strIncomeHeadCode);
	        		$("#lblIncomeHeadName").text(response.strIncomeHeadDesc);
	        	}
			},
			error: function(jqXHR, exception) 
			{
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
	
	
//remove income head
	function funRemoveRow(selectedRow,tableId)
	{
	    var rowIndex = selectedRow.parentNode.parentNode.rowIndex;
	    switch(tableId)
	    {
	    	case 'tblIncomeHeadDtl':
	    		document.getElementById("tblIncomeHeadDtl").deleteRow(rowIndex);
	    		break;
	    }
	}
	
	
//calculate totals
	function funCalculateTotals()
	{
		var totalAmt=0.00;
		var table=document.getElementById("tblIncomeHeadDtl");
		var rowCount=table.rows.length;
		if(rowCount>0)
		{
		    for(var i=0;i<rowCount;i++)
		    {
		    	var containsAccountCode=table.rows[i].cells[2].innerHTML;
		       	totalAmt=totalAmt+parseFloat($(containsAccountCode).val());
		    }
		   	totalAmt=parseFloat(totalAmt).toFixed(maxAmountDecimalPlaceLimit);
		}
		$("#dblTotalAmt").text(totalAmt);
	}

	
//check duplicate value
	function funChechDuplicate(incomeHeadCode)
	{
		var flag=false;
		var table=document.getElementById("tblIncomeHeadDtl");
		var rowCount=table.rows.length;
		if(rowCount>0)
		{
		    for(var i=0;i<rowCount;i++)
		    {
		       var containsAccountCode=table.rows[i].cells[0].innerHTML;
		       var addedIHCode=$(containsAccountCode).val();
		       if(addedIHCode==incomeHeadCode)
		       {
		    	   flag=true;
		    	   break;
		       }
		    }
		}
		return flag;
	}
	
	
//add income head
	function funAddIncomeHeadRow()
	{
		var incomeHeadCode=$("#strIncomeHead").val();
		var incomeHeadName=$("#lblIncomeHeadName").text();
		var amount=$("#dblIncomeHeadAmt").val();
		
		var flag=false;
		flag=funChechDuplicate(incomeHeadCode);
		if(flag)
		{
			alert("Already Added.");
		}
		else
		{
			var table=document.getElementById("tblIncomeHeadDtl");
			var rowCount=table.rows.length;
			var row=table.insertRow();
			
	 	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width:80%;\" name=\"listIncomeHeadBeans["+(rowCount)+"].strIncomeHeadCode\" id=\"strIncomeHeadCode."+(rowCount)+"\" value='"+incomeHeadCode+"' >";
	 	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width:70%;\" name=\"listIncomeHeadBeans["+(rowCount)+"].strIncomeHeadDesc\" id=\"strIncomeHeadDesc."+(rowCount)+"\" value='"+incomeHeadName+"' >";
	        row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-right: 5px;width:50%;text-align: right;\" name=\"listIncomeHeadBeans["+(rowCount)+"].dblAmount\" id=\"dblAmount."+(rowCount)+"\"  value='"+amount+"' >";
			row.insertCell(3).innerHTML= "<input type=\"button\" value=\"Delete\" style=\"padding-right: 5px;width:80%;text-align: right;\" class=\"deletebutton\" onclick=\"funRemoveRow(this,'tblIncomeHeadDtl')\" />";
					
		//calculate totals
			funCalculateTotals();
			
			$("#strIncomeHead").val('');
			$("#dblIncomeHeadAmt").val('');
		}
	}
	
	//
	function funAddRow()
	{
		var flag=false;
		
		if($("#strFolioNo").val().trim().length==0)
		{
			alert("Please Select Folio No.");
		}
		else if($("#strIncomeHead").val().trim().length==0)
		{
			alert("Please Select Income Head.");
		}
		else if($("#dblIncomeHeadAmt").val().trim().length==0)
		{
			alert("Please Enter Income Head Amount.");
		}
		else
		{
			flag=true;
			funAddIncomeHeadRow();
		}		
		return flag;
	}
	
	function funValidateFields()
	{
		var flag=true;
		var table=document.getElementById("tblIncomeHeadDtl");
		var rowCount=table.rows.length;
		if($("#strFolioNo").val().trim().length==0)
		{
			alert("Please Select Folio No.");	
			flag=false;
		}
		else if(rowCount<1)
		{
			alert("Please add income head in grid");	
			flag=false;
		}
		else
		{
			var folioText=document.getElementById("strFolioNo");
			folioText.disabled=false;
		}		
		return flag;
	}

	/**
	* Success Message After Saving Record
	**/
	$(document).ready(function()
	{
		var message='';
		<%if (session.getAttribute("success") != null) 
		{
			if(session.getAttribute("successMessage") != null){%>
				message='<%=session.getAttribute("successMessage").toString()%>';
			    <%
			    session.removeAttribute("successMessage");
			}
			boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
			session.removeAttribute("success");
			if (test) {
			%>
				alert("Data Save successfully\n\n"+message);
			<%
			}
		}%>
		
		 var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
		  var dte=pmsDate.split("-");
		  $("#txtPMSDate").val(dte[2]+"-"+dte[1]+"-"+dte[0]);
	});
	/**
		* Success Message After Saving Record
	**/
	
	
	function funSetData(code)
	{
		switch(fieldName)
		{
			case "incomeHead":
				funSetIncomeHeadData(code);
				break;
			
			case "folioNoForNoPost":
				funSetFolioData(code);
				break;
		}
	}

	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>Folio Posting</label>
	</div>

<br/>
<br/>

	<s:form name="Folio Posting" method="POST" action="saveFolioPosting.html">

		<table class="masterTable">
		
			<tr>
			    <td><label>Transaction id.</label></td>  
			    <td colspan="4"><s:input id="" path=""   readonly="true" ondblclick="funHelp('transactionId')" cssClass="searchTextBox"/></td>
			</tr>
			
			<tr>
			    <td><label>Folio No.</label></td>
			    <td colspan="4"><s:input id="strFolioNo" path="strFolioNo" readonly="true"  ondblclick="funHelp('folioNoForNoPost')" cssClass="searchTextBox"/></td>
			</tr>
			<tr>
			    <td><label>Income Head</label></td>
			    <td><s:input id="strIncomeHead" path=""  readonly="true"  ondblclick="funHelp('incomeHead')" cssClass="searchTextBox"/></td>
			    <td><label id="lblIncomeHeadName"></label></td>
			</tr>
			
			<tr>
			    <td><label>Amount</label></td>
			    <td><s:input id="dblIncomeHeadAmt" path=""   class="decimal-places-amt numberField" value="0" placeholder="amt"  /></td>
			    <td><input type="button" value="Add"  class="smallButton" onclick='return funAddRow()'/></td>
			</tr>
			
		</table>
		
		<br/>
		<!-- Generate Dynamic Table   -->		
		<div class="dynamicTableContainer" style="height: 200px; width: 80%">
			<table style="height: 28px; border: #0F0; width: 100%;font-size:11px; font-weight: bold;">
				<tr bgcolor="#72BEFC" style="height: 24px;">
					<!-- col1   -->
					<td align="center" style="width: 30.6%">Income Head Code</td>
					<!-- col1   -->
					
					<!-- col2   -->
					<td align="center" style="width: 30.6%">Income Head Name</td>
					<!-- col2   -->
					
					<!-- col3   -->					
					<td align="center" style="width: 30.6%">Amount</td>
					<!-- col4   -->
					
					<!-- col4   -->
					<td align="center">Delete</td>
					<!-- col4   -->									
				</tr>
			</table>
			<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 200px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblIncomeHeadDtl" style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll" class="transTablex col3-center">
					<tbody>
						<!-- col1   -->
						<col width="100%">
						<!-- col1   -->
						
						<!-- col2   -->
						<col width="100%" >
						<!-- col2   -->
						
						<!-- col3   -->
						<col align="right"  width="100%" >
						<!-- col3   -->
						
						<!-- col4   -->
						<col  width="10%">
						<!-- col4   -->
					</tbody>
				</table>
			</div>			
		</div>		
		<div class="dynamicTableContainer" style="height: 25px; width: 80%;overflow-x: hidden;">
			<table class="transTable" style="margin: 0px;">
				<tr>
					<td style="width: 63%;"><label>Totals</label></td>
					<td><label id ="dblTotalAmt">0.00</label></td>										
				</tr>				
			</table>
		</div>
		<!-- Generate Dynamic Table   -->
		
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funValidateFields()"/>
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
