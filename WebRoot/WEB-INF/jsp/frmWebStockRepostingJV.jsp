<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reposting JV</title>
</head>

<script type="text/javascript">

 		
 		$(document).ready(function() 
 		{

			$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtFromDate").datepicker('setDate','today');
			$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtToDate").datepicker('setDate', 'today');
			
		});	
 		
 	
 // Load Billwise Data for Flash Dispaly
	function funLoadTransactionList()
	{
		funRemoveRows();
		var fdate = $("#txtFromDate").val();
		var tdate = $("#txtToDate").val();
		var fdateSp= fdate.split("-");
		fdate = fdateSp[2]+"-"+fdateSp[1]+"-"+fdateSp[0];
		var tdateSp= tdate.split("-");
		tdate = tdateSp[2]+"-"+tdateSp[1]+"-"+tdateSp[0];
		
		var strForm = $("#cmbAgainst").val();
		
		var searchUrl = getContextPath()+ "/loadDocListForReposting.html?fromDate="+ fdate+"&toDate="+tdate+"&strFromName="+strForm;
		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				funFillTable(response);
				
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
	//Remove Display Data of Table
	function funRemoveRows()
	{
		var table = document.getElementById("tblTranList");
		var rowCount = table.rows.length;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}


	function funFillTable(resp)
	{
		for(var i=0;i<resp.length;i++)
		{
		 	var data=resp[i];
			var table = document.getElementById("tblTranList");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		
		    row.insertCell(0).innerHTML= "<input id=\"cbSel."+(rowCount)+"\" type=\"checkbox\"  />";
		    row.insertCell(1).innerHTML= "<input name=\"StrInvCode["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"10%\" id=\"StrInvCode."+(rowCount)+"\" value='"+data.field1+"'>";
		    row.insertCell(2).innerHTML= "<input name=\"DteInvDate["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"DteInvDate."+(rowCount)+"\" value='"+data.field2+"'>";
		    row.insertCell(3).innerHTML= "<input name=\"strSerialNo["+(rowCount)+"]\" readonly=\"readonly\" class=\"Box\" size=\"10%\" id=\"strSerialNo."+(rowCount)+"\" value='"+data.field7+"'>";
		   
		    row.insertCell(4).innerHTML= "<label size=\"10\">"+data.field3+"</label>";
		    
		    row.insertCell(5).innerHTML= "<input name=\"DblSubTotalAmt["+(rowCount)+"]\" id=\"DblSubTotalAmt."+(rowCount)+"\" readonly=\"readonly\" style=\"text-align: right;\" size=\"11%\" class=\"Box\" value="+parseFloat(data.field4).toFixed(maxQuantityDecimalPlaceLimit)+">"; 
		    row.insertCell(6).innerHTML= "<input name=\"DblTaxAmt["+(rowCount)+"]\" id=\"DblTaxAmt."+(rowCount)+"\" readonly=\"readonly\" style=\"text-align: right;\" size=\"11%\" class=\"Box\" value="+parseFloat(data.field5).toFixed(maxQuantityDecimalPlaceLimit)+">";
		    row.insertCell(7).innerHTML= "<input name=\"DblTotalAmt["+(rowCount)+"]\" id=\"DblTotalAmt."+(rowCount)+"\" readonly=\"readonly\" style=\"text-align: right;\" size=\"12%\" class=\"Box\" value="+parseFloat(data.field6).toFixed(maxQuantityDecimalPlaceLimit)+">";
		    
		    row.insertCell(8).innerHTML= "<label size=\"8\" id=\"creditorCode."+(rowCount)+"\">"+data.field8+"</label>";
		    row.insertCell(9).innerHTML= "<label size=\"12\" id=\"creditorName."+(rowCount)+"\">"+data.field9+"</label>";
		    row.insertCell(10).innerHTML= "<label size=\"8\" id=\"accCode."+(rowCount)+"\">"+data.field10+"</label>";
		    
		    row.insertCell(11).innerHTML= "<label size=\"12\" id=\"accName."+(rowCount)+"\">"+data.field11+"</label>";
		    
	   }
	}
	
	function funRepostTransaction()
	{
	
		var strForm = $("#cmbAgainst").val();
		var docCode=funGetDocCodeList();
		 if(docCode.length==0){
			alert('Select Transaction to repost');
			return false;
		} 
		
		var searchUrl = getContextPath()+ "/repostTransaction.html?docCode="+ docCode+"&strFromName="+strForm;

		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "text",
			success : function(response) {
				alert(response);
				funRemoveRows();
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
	//Check Which PO is selected
	function funCheckUncheck()
	{
		var table = document.getElementById("tblTranList");
		var rowCount = table.rows.length;
		
	    for (var i=0;i<rowCount;i++)
	    {
	        if(document.all("chkALL").checked==true)
	        {
	        	
	        	document.all("cbSel."+i).checked=true; 
	        }
	        else
	        {
	        	document.all("cbSel."+i).checked=false;  
	        }
	    }
		
	}
	function funGetDocCodeList(){
		var table = document.getElementById("tblTranList");
	    var rowCount = table.rows.length;  
	    var strDocCode="";
	    for(no=0;no<rowCount;no++)
	    {
	    	 if(document.all("cbSel."+no).checked==true)
	        	{
	        		var debCode=document.all("creditorCode."+no).textContent;
	        		var accCode=document.all("accCode."+no).textContent;
	        		if(accCode=='' || debCode==''){
	        			alert('Account Linkup Not Found for '+strDocCode);
	        			break;
	        		}
	        		strDocCode=strDocCode+document.all("StrInvCode."+no).value+",";
	        	}
	    }
	    strDocCode=strDocCode.substring(strDocCode,strDocCode.length-1)    
	    return strDocCode;
	}
 </script>
<body>
<div id="formHeading">
		<label>Reposting JV</label>
	</div>
	<s:form action="" method="GET" 
	s="frmRepostingJv" target="_blank">
		<br>
		<div>
	
			<table class="transTable">
				<tr>
				    <td><label id="lblFromDate">From Date</label></td>
			        <td>
			            <s:input id="txtFromDate" name="fromDate" path="dteFromDate" cssClass="calenderTextBox"/>
			        </td>
				        
			        <td><label id="lblToDate">To Date</label></td>
			        <td>
			            <s:input id="txtToDate" name="toDate" path="dteToDate" cssClass="calenderTextBox"/>
			        </td>
			        
			   </tr>
			   <tr>
			   		<td><label >Transaction Type</label></td>
			        <td>
			        	<select id="cmbAgainst" class="BoxW124px" path="strAgainst" >
			        		<option value="GRN">GRN</option>
			        		<option value="Purchase Return">Purchase Return</option>
			        	</select>
			       </td>
			       <td></td><td></td>		        
			   </tr>
			   <tr>
					<td><input id="btnExecute" type="button" class="form_button" value="Excute" onclick="funLoadTransactionList()" /></td>
					<td></td>
					<td><input id="btnPosting" type="button" class="form_button" value="Post" onclick="funRepostTransaction()" /></td>
					<td></td> 
				</tr>
				</table>
					
		</div>
		<br/>
		<br/>
		<div id="divDocList" class="dynamicTableContainer"
			style="height: 400px; overflow-x: scroll; overflow-y: scroll;">
			<!-- <div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 330px; margin: auto;  width: 100%;">
			</div> -->
			<table style="width: 120%; border: #0F0;   overflow-x: scroll; overflow-y: scroll;"
				class="transTablex col15-center">
				<tr bgcolor="#72BEFC">
					<td width="2%">Select<input type="checkbox" id="chkALL" onclick="funCheckUncheck()" /></td>
					<td width="8%">Doc Code</td>
					
					<td width="6%">Date</td>
					<td width="9%">JV No</td>
					<td width="12%">Supplier Name</td>
					
					<td width="8%">SubTotal</td>
					<td width="8%">Tax Amount</td>
					<td width="8%">Grand Total</td>
					
					<td width="8%">Creditor Code</td>
					<td width="12%">Creditor Name</td>
					<td width="8%">Link Acc Code</td>
					
					<td width="12%">Link Acc Name</td>
				</tr>
			</table>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 330px; margin: auto;  width: 120%;">
				<table id="tblTranList"
					style="width: 100%; border: #0F0;  overflow-x: scroll; overflow-y: scroll;"
					class="transTablex col15-center">
					<tbody>
					<col style="width: 3%">
					<col style="width: 8%">
					<!--  COl1   -->
					<col style="width: 6%">
					<!--  COl2   -->
					<col style="width: 9%">
					<!--  COl3   -->
					<col style="width: 11%">
					<!--  COl4   -->
					<col style="width: 8%">
					<!--  COl4   -->
					<col style="width: 8%">
					
					<!--COl6   -->
					<col style="width: 8%">
					<!-- COl7   -->
					<col style="width: 8%">
					<!--  COl8   -->
					<col style="width: 12%">
					<col style="width: 8%">
					<col style="width: 12%">
					<!--  COl9   -->
					
					</tbody>

				</table>

			</div>

		</div>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
		
	</s:form>
	
</body>
</html>