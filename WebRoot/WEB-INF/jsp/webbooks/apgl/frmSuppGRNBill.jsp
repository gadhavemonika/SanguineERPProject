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

	$(function() 
	{
		$("#txtVoucherDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtVoucherDate").datepicker('setDate', 'today');
		$("#txtBillDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtBillDate").datepicker('setDate', 'today');
		$("#txtDueDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtDueDate").datepicker('setDate', 'today');
		
		$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
		$("#txtFromDate" ).datepicker('setDate', 'today'); 
		
		$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
		$("#txtToDate" ).datepicker('setDate', 'today'); 
	});
	
	$(document).ready(function()
			{
				$(".tab_content").hide();
				$(".tab_content:first").show();

				$("ul.tabs li").click(function() 
				{
					$("ul.tabs li").removeClass("active");
					$(this).addClass("active");
					$(".tab_content").hide();
					var activeTab = $(this).attr("data-state");
					$("#" + activeTab).fadeIn();
				});
				
				//Ajax Wait 
			 	$(document).ajaxStart(function()
			 	{
				    $("#wait").css("display","block");
			  	});
			 	
				$(document).ajaxComplete(function()
				{
				    $("#wait").css("display","none");
				});	 
		
			
					var message='';
					<%if (session.getAttribute("success") != null) {
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
					}}%>

				
			});
	

	function funSetData(code){

		switch(fieldName){

			case 'SCCode' : 
				funSetVoucherNo(code);
				break;
			case 'suppLinkedWeb-Service' : 
				funSetSuppCode(code);
				break;
			case 'accountCode' :
				funSetAccountDetails(code);
				break;
				
			case 'SCCodeslip' : 
				funSetVoucherNoSlip(code);
				break;
				
		}
	}


	function funSetVoucherNo(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadSundryCreditorBillData.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 
				funRemoveAccountRows("tblGRN");
				funRemoveAccountRows("tblSundaryCr");
				if(response.strVoucherNo=='Invalid Supp Code')
	        	{
	        		alert("Invalid Supplier Code");
	        		$("#txtVoucherNo").val('');
	        	}else
	        		{
	        		$("#txtVoucherNo").val(response.strVoucherNo);
	        		$("#txtSuppCode").val(response.strSuppCode);
		        	$("#txtSuppName").val(response.strSuppName);
		        	$("#hidAcCode").val(response.strAcCode);
		        	$("#hidAcName").val(response.strAcName);
		        	
		        	$("#txtBillNo").val(response.strBillNo);
		        	$("#txtBillDate").val(response.dteBillDate);
		        	$("#txtDueDate").val(response.dteDueDate);
		        	$("#txtTotalAmount").val(response.dblTotalAmount);
		        	$("#txtVoucherDate").val(response.dteVoucherDate);
		        	$("#txtNarration").val(response.strNarration);
		        	
		        	$.each(response.listSGBillGRNDtl, function(i, item) {
						
						funLoadGRNTable(item.strGRNCode,item.strGRNBIllNo,item.dblGRNAmt,item.dteGRNDate,item.dteBillDate,item.dteGRNDueDate,item.strSelected);
					});	
		        	
		        	$.each(response.listSGBillAccDtl, function(i, item) {

		        		funGetAccountRow(item.strACCode,item.strACName,item.strCrDr,item.dblDrAmt,item.dblCrAmt,item.strNarration,item.strCreditorCode,item.strCreditorName);
					
		        	});	
	        		
	        	}
				
				
			},
			error : function(jqXHR, exception){
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

	function funSetSuppCode(code){

		$.ajax({
			type : "POST",
			url : getContextPath()+ "/loadLinkedSuppCode.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 
				funRemoveAccountRows("tblGRN");
				if(response.strSuppCode=='Invalid Supp Code')
	        	{
	        		alert("Invalid Supplier Code");
	        		$("#txtSuppCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtSuppCode").val(response.strSuppCode);
		        	$("#txtSuppName").val(response.strSuppName);
		        	
		        	$("#hidCreditorCode").val(response.strCreditorCode);
		        	$("#hidCreditorName").val(response.strCreditorName);
		        	/* $("#hidAcCode").val(response.strAcCode);
		        	$("#hidAcName").val(response.strAcName);
		        	
		        	funGetUnBilledGRN(response.strSuppCode,response.strClientCode); */
	        	}
			},
			error : function(jqXHR, exception){
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
	
	// Function to set account details from help	
	function funSetAccountDetails(code){
		
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadAccontCodeAndName.html?accountCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtACCode").val('');
	        	}
	        	else
	        	{
	        				$("#hidCreditor").val(response.strCreditor);
		        			$("#txtACCode").val(response.strAccountCode);
				        	$("#txtACName").val(response.strAccountName);
	        			
	        	}
			},
			error : function(jqXHR, exception){
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
	
	function funResetAccFields()
	{
		$("#txtACCode").val("");
		$("#txtACName").val("");
		$("#txtTotalAmount").val("");
		$("#txtNarration").val("");
	}


	function funFillAccRow()
	{
		var strCreditorCode="";   
		var strCreditorName="";
		var dblDr ="0";
		var dblCr ="0";  
		var strAccNo = $("#txtACCode").val();
		var strAccName = $("#txtACName").val();
		var strCrDr = $("#cmbCrDr").val();
		if(strCrDr=="Dr")
			{
			 		dblDr = $("#txtAmount").val();
			}else
				{
					dblCr = $("#txtAmount").val();
				}
		
		var strRemark = $("#txtRemark").val();
		
		if($("#hidCreditor").val()=='Yes')
		{
			strCreditorCode=$("#hidCreditorCode").val(); 
			strCreditorName=$("#hidCreditorName").val(); 
		}
		
		funGetAccountRow(strAccNo,strAccName,strCrDr,dblDr,dblCr,strRemark,strCreditorCode,strCreditorName);
	}
		
	function funGetAccountRow(strAccNo,strAccName,strCrDr,dblDr,dblCr,strRemark,strCreditorCode,strCreditorName)
	{
		
		    var table = document.getElementById("tblSundaryCr");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input class=\"Box\" size=\"10%\" name=\"listSGBillAccDtl["+(rowCount)+"].strACCode\" id=\"txtAccNo."+(rowCount)+"\" value='"+strAccNo+"' >";
		    row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"15%\" name=\"listSGBillAccDtl["+(rowCount)+"].strACName\" id=\"txtACCName."+(rowCount)+"\" value='"+strAccName+"'>";		    	    
		    row.insertCell(2).innerHTML= "<input class=\"Box\" size=\"10%\" name=\"listSGBillAccDtl["+(rowCount)+"].strCreditorCode\" id=\"txtCreditorCode."+(rowCount)+"\" value='"+strCreditorCode+"' >";
		    row.insertCell(3).innerHTML= "<input class=\"Box\" size=\"15%\" name=\"listSGBillAccDtl["+(rowCount)+"].strCreditorName\" id=\"txtCreditorName."+(rowCount)+"\" value='"+strCreditorName+"'>";		    	    
		    
		    
		    row.insertCell(4).innerHTML= "<input class=\"Box\" size=\"7%\" name=\"listSGBillAccDtl["+(rowCount)+"].strCrDr\" id=\"txtCrDr."+(rowCount)+"\" value='"+strCrDr+"'>";
		    row.insertCell(5).innerHTML= "<input class=\"Box totalDrAmtCell\"  required = \"required\" style=\"text-align: right;\" size=\"8%\" name=\"listSGBillAccDtl["+(rowCount)+"].dblDrAmt\" id=\"txtDrAmt."+(rowCount)+"\" value="+dblDr+">";
		    row.insertCell(6).innerHTML= "<input class=\"Box totalCrAmtCell\"  required = \"required\" style=\"text-align: right;\" size=\"8%\" name=\"listSGBillAccDtl["+(rowCount)+"].dblCrAmt\" id=\"txtCrAmt."+(rowCount)+"\" value="+dblCr+">";		    
		    row.insertCell(7).innerHTML= "<input class=\"Box\" size=\"20%\" name=\"listSGBillAccDtl["+(rowCount)+"].strNarration\" id=\"txtRemark."+(rowCount)+"\" value='"+strRemark+"'>";
		    row.insertCell(8).innerHTML= '<input type="button" size=\"2%\" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteTaxRow(this)" >';
		    funTotalCrDrSundry();
		    funTotalCrGRN();
		    return false;
	}
	
	
	function funTotalCrDrSundry()
	{
			var finalCr=0.00;
			var finalDr=0.00;
			var totAmt=$("#txtTotalAmount").val();
			
			$('#tblSundaryCr tr').each(function() {
			    var totalDrCell = $(this).find(".totalDrAmtCell").val();
			    var totalCrCell = $(this).find(".totalCrAmtCell").val();
			   
			    finalDr+=parseFloat(totalDrCell);
			    finalCr+=parseFloat(totalCrCell);
			   		  
			 });

			var bal=finalCr-finalDr;
			$("#txtTotDebitAmt").val(finalDr);
			$("#txtTotCreditAmt").val(finalCr);
			$("#txtBalAmt").val(bal);
		//	$("#txtTotalAmount").val(finalCr);
			
	
	}

	function funRemoveAccountRows(tblID)
	{
		var table = document.getElementById(tblID);
		var rowCount = table.rows.length;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	
	function funDeleteTaxRow(obj) 
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblSundaryCr");
		table.deleteRow(index);
		
	}
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	function funGetUnBilledGRN(strSuppCode,strClientCode)
	{ 
		var fromDate = $("#txtFromDate").val();
		var toDate = $("#txtToDate").val();	
		var abc="";
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadUnBillGrn.html?strSuppCode="+strSuppCode+"&fromDate="+fromDate+"&toDate="+toDate,
			dataType : "json",
			success : function(response){ 
				$.each(response, function(i, item) {
					
						funLoadGRNTable(response[i].strGRNCode,response[i].strGRNBIllNo,response[i].dblGRNAmt,response[i].dteGRNDate,response[i].dteBillDate,response[i].dteGRNDueDate,"No");
				});	
	        
			},
			error : function(jqXHR, exception){
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
	
	function funLoadGRNTable(strGRNCode,strGRNBIllNo,dblGRNAmt,dteGRNDate,dteBillDate,dteGRNDueDate,isSelected)
	{
		 var table = document.getElementById("tblGRN");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    if(isSelected=="Tick")
		    	{
		    	 row.insertCell(0).innerHTML= "<input id=\"cbGRNSel."+(rowCount)+"\" name=\"listSGBillGRNDtl["+(rowCount)+"].strSelected\" type=\"checkbox\" class=\"PropCheckBoxClass\" checked=\"checked\"   value='Tick' onclick=\"funGRNChkOnClick()\" />";
		    	}else
		    	{
		    		 row.insertCell(0).innerHTML= "<input id=\"cbGRNSel."+(rowCount)+"\" name=\"listSGBillGRNDtl["+(rowCount)+"].strSelected\" type=\"checkbox\" class=\"PropCheckBoxClass\"   value='Tick' onclick=\"funGRNChkOnClick()\" />";	
		    	}
		   
		    row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"22%\" name=\"listSGBillGRNDtl["+(rowCount)+"].strGRNCode\" id=\"txtGRNCode."+(rowCount)+"\" value='"+strGRNCode+"' >";
		    row.insertCell(2).innerHTML= "<input class=\"Box\" size=\"10%\" name=\"listSGBillGRNDtl["+(rowCount)+"].strGRNBIllNo\"  id=\"txtGRNBIllNo."+(rowCount)+"\" value='"+strGRNBIllNo+"'>";		    	    
		    row.insertCell(3).innerHTML= "<input class=\"Box totalGRNAmtCell\" size=\"10%\" style=\"text-align: right;\" size=\"8%\" name=\"listSGBillGRNDtl["+(rowCount)+"].dblGRNAmt\" id=\"txtGRNAmt."+(rowCount)+"\" value='"+dblGRNAmt+"'>";
		    row.insertCell(4).innerHTML= "<input class=\"Box\"  required = \"required\"  size=\"15%\" name=\"listSGBillGRNDtl["+(rowCount)+"].dteGRNDate\" id=\"txtGRNDate."+(rowCount)+"\" value="+dteGRNDate+">";
		    row.insertCell(5).innerHTML= "<input class=\"Box\"  required = \"required\"  size=\"15%\" name=\"listSGBillGRNDtl["+(rowCount)+"].dteBillDate\" id=\"txtBillDate."+(rowCount)+"\" value="+dteBillDate+">";		    
		    row.insertCell(6).innerHTML= "<input class=\"Box\" required = \"required\"  size=\"15%\" name=\"listSGBillGRNDtl["+(rowCount)+"].dteGRNDueDate\" id=\"txtGRNDueDate."+(rowCount)+"\" value="+dteGRNDueDate+">";
		 //   row.insertCell(7).innerHTML= '<input type="button" size=\"2%\" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteTaxRow(this)" >';
		   
	}
	
	
	
	function funTotalCrGRN()
	{
		var finalCr=0.00;
		var table = document.getElementById("tblGRN");
	    var rowCount = table.rows.length; 
		var no=0;
				$('#tblGRN tr').each(function() {

					if(document.all("cbGRNSel."+no).checked==true)
			    	{
				    var totalCrCell = $(this).find(".totalGRNAmtCell").val();
				    finalCr+=parseFloat(totalCrCell);
			    	}
					no++;
				 });
	    	
	   
		$("#txtTotalAmount").val(finalCr);
	}
	
	
	
	
	function funGRNChkOnClick()
	{
		var table = document.getElementById("tblGRN");
	    var rowCount = table.rows.length;  
	    var strGRNCodes="";
	    var grnAmt=0.0;
	    for(no=0;no<rowCount;no++)
	    {
	        if(document.all("cbGRNSel."+no).checked==true)
	        	{
	        		if(strGRNCodes.length>0)
	        			{
	        				strGRNCodes=strGRNCodes+","+document.all("txtGRNCode."+no).value;
	        				var tempAmt = document.all("txtGRNAmt."+no).value;
	        				grnAmt=parseFloat(grnAmt)+parseFloat(tempAmt);
	        			    
	        			}
	        		else
	        			{
	        				strGRNCodes=document.all("txtGRNCode."+no).value;
	        				var tempAmt = document.all("txtGRNAmt."+no).value;
	        				grnAmt=parseFloat(tempAmt)
	        			}
	        	}
	    } 
	    var strAccNo=$("#txtACCode").val();
	    var strAccName=$("#txtACName").val();
	    var strCreditorCode=$("#hidCreditorCode").val();
	    var strCreditorName=$("#hidCreditorName").val();
	    funRemoveAccountRows("tblSundaryCr");
	    funGetAccountRow(strAccNo,strAccName,"Cr","0.0",grnAmt,"AutoGenrate Amount",strCreditorCode,strCreditorName);
	    funLoadUnBilledAcc(strGRNCodes)
	   
	}
	
	
	function funLoadUnBilledAcc(strGRNCode)
	{
		
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadGrnWiseAccountAmt.html?strGRNCode="+strGRNCode,
			dataType : "json",
			success : function(response){ 
				$.each(response, function(i, item) {
						funGetAccountRow(response[i].strACCode,response[i].strACName,response[i].strCrDr, 
								response[i].dblDrAmt,response[i].dblCrAmt,response[i].strNarration,response[i].strCreditorCode,response[i].strCreditorName);
				});	
	        
			},
			error : function(jqXHR, exception){
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
	
	
	function funValidateFields() 
	{
// 		var table = document.getElementById("tblProduct");
// 	    var rowCount = table.rows.length;
	    var bal=$("#txtBalAmt").val();
	    
	    if($("#txtBillNo").val().trim().length==0)
	    	{
		    	alert("Please Enter Bill No.");
				$("#txtBillNo").focus();
				return false;
	    	}
	    
	    if($("#txtSuppCode").val().trim().length==0)
    	{
	    	alert("Please Select Supplier");
			$("#txtSuppCode").focus();
			return false;
    	}
	    
	   /*  if($("#hidAcCode").val().trim().length==0)
    	{
	    	alert("Please Select Valid Supplier ");
			$("#txtSuppCode").focus();
			return false;
    	} */
	    
	    if(parseFloat(bal)!=0)
    	{
	    	alert("Total Balance must be Zero");
			return false;
    	}else
    	{
    		return true;
    	}
	    
	}
	
	
	function funShowGRN()
	{
		
		if($("#hidCreditor").val()=='Yes')
			{
				var strSuppCode = $("#txtSuppCode").val();
		    	var strSuppName = $("#txtSuppName").val();
		    	funGetUnBilledGRN(strSuppCode,"");
			}else
				{
					alert("Please Select Sundry Creditor Account");
				}
		
		
	}
	
	
	
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>Sundary Creditor Bill</label>
	</div>

<br/>
<br/>

	<s:form name="SuppGRNBill" method="POST" action="saveSuppGRNBill.html">

		<table class="transTable">
			<tr>
				<td>
					<label>Voucher No</label>
				</td>
				<td>
					<s:input  type="text" id="txtVoucherNo" path="strVoucherNo" cssClass="searchTextBox" ondblclick="funHelp('SCCode');"/>
				</td>
				
				<td>
					<label>Supp Code</label>
				</td>
				<td>
					<s:input  type="text" id="txtSuppCode" path="strSuppCode" cssClass="searchTextBox" ondblclick="funHelp('suppLinkedWeb-Service');"/>
				</td>
				<td><s:input  type="text" id="txtSuppName" readonly="readonly" path="strSuppName" cssClass="BoxW124px" /></td>
			</tr>
		
			<tr>
				<td>
					<label>Bill No</label>
				</td>
				<td>
					<s:input  type="text" id="txtBillNo" path="strBillNo" cssClass="BoxW124px" />
				</td>
				
				<td>
					<label>Bill Date</label>
				</td>
				<td colspan="2">
					<s:input  type="text" id="txtBillDate" path="dteBillDate" cssClass="calenderTextBox" />
				</td>
				
			</tr>
			
			<tr>
				<td>
					<label>Due Date</label>
				</td>
				<td>
					<s:input  type="text" id="txtDueDate" path="dteDueDate" cssClass="calenderTextBox" />
				</td>
				<td>
					<label>Total Amount</label>
				</td>
				<td colspan="2">
					<s:input   type="number" step="0.01" style="text-align: right;width: 33%;" id="txtTotalAmount" readonly="readonly" path="dblTotalAmount" cssClass="longTextBox" value="0.00" />
				</td>
				
			</tr>
			<tr>
				<td>
					<label>Voucher Date</label>
				</td>
				<td>
					<s:input  type="text" id="txtVoucherDate" path="dteVoucherDate" cssClass="calenderTextBox" />
				</td>
				
				<td>
					<label>Narration</label>
				</td>
				<td colspan="2">
					<s:textarea id="txtNarration" path="strNarration"  />
				</td>
			</tr>
		
		</table>
		<br>
	<table style="border: 0px solid black; width: 100%; height: 100%; margin-left: auto; margin-right: auto; background-color: #C0E4FF;">
			
			<tr>
				<td>	
					<div id="tab_container" style="height:420px">
								<ul class="tabs">
									<li class="active" data-state="tab1"
										style="width: 100px; padding-left: 55px">Details</li>
									<li data-state="tab2" style="width: 100px; padding-left: 55px">GRN</li>
								</ul>
								<div id="tab1" class="tab_content" style="height: 390px">
		
									<table  class="transTable">
									<tr>
									<td><label>Account Code</label></td>
										<td><input type="text" id="txtACCode"
											Class="searchTextBox" ondblclick="funHelp('accountCode');" /></td>
										<td><input type="text" id="txtACName"
											Class="BoxW124px" readonly="readonly" style="width: 250px;"/></td>	
										<td><label>Cr/Dr</label></td>
										<td><select id="cmbCrDr" class="BoxW124px">
												<option value="Cr">Cr</option>
												<option value="Dr">Dr</option>
										</select></td>
										</tr>
										<tr>
		
										<td><label>Amount</label></td>
										<td><input type="number" step="0.01" id="txtAmount" readonly="readonly" style="text-align: right;"
											Class="BoxW124px" /></td>
										
										<td><label>Remark</label></td>
										
										<td><input type="text" id="txtRemark" Class="BoxW124px" />  </td>	
											
										<td><input type="Button" value="Add"
											onclick="return funFillAccRow()" class="smallButton" /></td>
									
									 </tr>
									
									<tr>
									<td colspan="6">	
									<div class="dynamicTableContainer" style="height: 300px;">
									<table
										style="height: 28px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
										<tr bgcolor="#72BEFC">
											<td style="width:8%;">Account Code</td>
											<td style="width:10%;">Account Name</td>
											<td style="width:8%;">Creditor Code</td>
											<td style="width:10%;">Creditor Name</td>
											<td style="width:8%;">D/C</td>
											<td style="width:8%;">Debit Amt</td>
											<td style="width:8%;">Credit Amt</td>
											<td style="width:18%;">Narration</td>
											<td style="width:2%;"></td>
											
										</tr>
									</table>
									
									<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
										<table id="tblSundaryCr"
											style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
											class="transTablex col8-center">
											<tbody>
												<col style="width:8%">
												<col style="width:11%">
												<col style="width:8%">
												<col style="width:10%">
												<col style="width:8.5%">
												<col style="width:10%">
												<col style="width:10%">
												<col style="width:18%">
												<col style="width:2%">
												
												
											</tbody>
										</table>
									</div>
								</div>
								</td>
								</tr>
								
								<tr>
								<td><label>Total Debit Amount</label></td>
								
								<td ><input type="text" id="txtTotDebitAmt"
											Class="BoxW124px" readonly="readonly" style="width: 100px;"/></td>
											<td><label>Total Credit Amount</label></td>
								<td><input type="text" id="txtTotCreditAmt"
											Class="BoxW124px" readonly="readonly" style="width: 100px;"/></td>
								<td><label>Balance Amount</label></td>
								<td><input type="text" id="txtBalAmt"
											Class="BoxW124px" readonly="readonly" style="width: 100px;"/></td>
								</tr>
									</table>
									
								
							</div>	
							
							
							<div id="tab2" class="tab_content" style="height: 390px">
							<table class="transTable">
							<tr> 
							<td ><label>From Date </label></td>
							<td ><input id="txtFromDate"  value="" readonly="readonly" Class="calenderTextBox"/></td>
							<td ><label>To Date </label></td>
							<td ><input id="txtToDate"  value="" readonly="readonly" Class="calenderTextBox"/></td>
							<td ><input type="Button" value="Show"
											onclick="return funShowGRN()" class="smallButton" /></td>
							
							
							</tr>
							<tr>
							<td colspan="5">
							<div
							style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 350px; overflow-x: hidden; overflow-y: scroll;">

							<table id="" class="masterTable"
								style="width: 100%; border-collapse: separate;">
								<thead>
									<tr bgcolor="#72BEFC">
										<td width="5%"><input type="checkbox" checked="checked" 
										id="chkGRNALL"/>Select</td>
										<td width="20%">GRN Code</td>
										<td width="10%">Bill No</td>
										<td width="10%">Amount</td>
										<td width="20%">GRN Date</td>
										<td width="20%">Bill Date</td>
										<td width="20%">Due Date</td>

									</tr>
								</thead>
							</table>
							<table id="tblGRN" class="masterTable"
								style="width: 100%; border-collapse: separate;">

								<tr bgcolor="#72BEFC">
									

								</tr>
							</table>
						</div>
							
							
							
							
						</td>	
						</tr>	
						</table>	
				      </div>
							
							
							
							
				</div>
			</td>
			</tr>
			</table>
				
				

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funValidateFields();" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
		
		<s:input type="hidden" id="hidAcCode" path="strAcCode" ></s:input>
		<s:input type="hidden" id="hidAcName" path="strAcName" ></s:input>
		<s:input type="hidden" id="hidCreditorCode" path="strCreditorCode" ></s:input>
		<s:input type="hidden" id="hidCreditorName" path="strCreditorName" ></s:input>
		<input type="hidden" id="hidCreditor"  ></input>
		
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>

	</s:form>
</body>
</html>
