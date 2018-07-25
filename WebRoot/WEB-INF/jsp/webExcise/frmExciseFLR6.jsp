<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

 <style type="text/css">
 .cell{
 	background: inherit;
    border: 0 solid #060006;
    font-family: Arial,Helvetica,sans-serif;
    font-size: 11px;
    font-weight: bold;
    outline: 0 none;
    padding-left: 0;
    width: 30px
 }
 
  .header{
 	background: inherit;
    border: 0 solid #060006;
    font-family: Arial,Helvetica,sans-serif;
    font-size: 11px;
    font-weight: bold;
    outline: 0 none;
    padding-left: 0;
    width: 80px
 }
 
 </style>
 
<script type="text/javascript">

	var fDate,tDate;
	var inPeg;

	$(document).ready(function() {
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
			alert(message);
		<%
		}}%>		

		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});

		$("#txtFromDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#txtFromDate").datepicker('setDate', 'today');

		$("#txtToDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#txtToDate").datepicker('setDate', 'today');
		
		$("[type='reset']").click(function(){
			location.reload(true);
		});

		$("#btnSubmit").click(function(event) {
			var fromDate = $("#txtFromDate").val();
			var toDate = $("#txtToDate").val();

			if (fromDate.trim() == '' && fromDate.trim().length == 0) {
				alert("Please Enter From Date");
				return false;
			}
			if (toDate.trim() == '' && toDate.trim().length == 0) {
				alert("Please Enter To Date");
				return false;
			}
			
			var strFromBillNo = $("#strFromBillNo").val();
			var strToBillNo = $("#strToBillNo").val();
			
			if (strFromBillNo.trim() == '' && strFromBillNo.trim().length == 0) {
				alert("Please Select From Bill");
				return false;
			}
			if (strToBillNo.trim() == '' && strToBillNo.trim().length == 0) {
				alert("Please Select To Bill");
				return false;
			}
			
			strFromBillNo= parseInt(strFromBillNo);
			strToBillNo= parseInt(strToBillNo);
			if(strFromBillNo<=strToBillNo){
				if(funDeleteTableAllRows()){
					if(CalculateDateDiff(fromDate,toDate)){
						fDate=fromDate;
						tDate=toDate;
						inPeg=$('#txtInPeg').val();
						funFetchColNames(strFromBillNo,strToBillNo);
					}
				}
				
			}else{
				alert("Please Check From Bill And To Bill No's.");
			}
			
		});

	});
	
	function funDeleteTableAllRows()
	{
		$('#tblExciseFLRReport_1 tbody').empty();
		$('#tblExciseFLRSummary').empty();
		
		var table = document.getElementById("tblExciseFLRReport_1");
		var rowCount1 = table.rows.length;
		if(rowCount1==0){
			return true;
		}else{
			return false;
		}
	}
	
	function CalculateDateDiff(fromDate,toDate) {

		var frmDate= fromDate.split('-');
	    var fDate = new Date(frmDate[2],frmDate[1],frmDate[0]);
	    
	    var tDate= toDate.split('-');
	    var t1Date = new Date(tDate[2],tDate[1],tDate[0]);

    	var dateDiff=t1Date-fDate;
  		 if (dateDiff >= 0 ) 
  		 {
         	return true;
         }else{
        	 alert("Please Check From Date And To Date");
        	 return false
         }
	}

	
	function funFetchColNames(strFromBillNo,strToBillNo) {
		var gurl = getContextPath() + "/loadFLR6Columns.html";
		var licenceCode=$('#cmbLicenceCode').val();
		$.ajax({
			type : "GET",
			data:"fromDate="+fDate+"&toDate="+tDate+"&strFromBillNo="+strFromBillNo+"&strToBillNo="+strToBillNo+"&inPeg="+inPeg+"&licenceCode="+licenceCode,
			url : gurl,
			dataType : "json",
			success : function(response) {
				if (response.Data1 == '' || response.Data1 == undefined) {
					alert("Data Not Found");
				} else {
					funAddHeaderRow1(response.Header1,response.SubCatCode1,response.CatSizeLenght1);
					funAddDataRows1(response.SizeName1);
// 					funAddUnderLine1(response.Header1);
					
					 $.each(response.Data1, function(i,item)
					{
						funAddDataRows1(response.Data1[i]);
					});
					
					 if(response.Total1.length>0){
						
						funAddUnderLine1(response.Total1);
						funAddDataRows1(response.Total1);
					}
					 
				 	if(response.Summary1.length>0){
						funAddBlankRows1(response.Total1);
						funAddBlankRows1(response.Total1);
					}
					
					$.each(response.Summary1, function(i,item)
					{
						funAddDataRows1(response.Summary1[i]);
					});
					
					if(response.Data2 != undefined){
						funDrawTable2(response);
					}
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
	
	
	
 	function funAddHeaderRow1(rowData,catCode,sizeList){
		var table = document.getElementById("tblExciseFLRReport_1");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
		    for(var i=0;i<rowData.length;i++){
		    	if(i==0){
		    		row.insertCell(i).innerHTML= "<input type=\"hidden\"readonly=\"readonly\" value='"+rowData[i]+"' />";	
		    	}else if(i==1){
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"headerList1["+(rowCount)+"].strCol"+i+"\" value='"+rowData[i]+"' />";
		    	}else if(i==2){
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"Box\" name=\"headerList1["+(rowCount)+"].strCol"+i+"\" value='"+rowData[i]+"' />";
		    	}else if(i==3){
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"Box\" name=\"headerList1["+(rowCount)+"].strCol"+i+"\" value='"+rowData[i]+"' />";
		    	}else if(i==4){
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"Box\" name=\"headerList1["+(rowCount)+"].strCol"+i+"\" value='"+rowData[i]+"' />";
		    	}else if(i==5){
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"Box\" name=\"headerList1["+(rowCount)+"].strCol"+i+"\" value='"+rowData[i]+"' />";
		    	}else{
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"headerList1["+(rowCount)+"].strCol"+i+"\" value='"+rowData[i]+"' />";
		    		var temp=catCode[i].trim();
	    			var colSpan=0;
	    			if(sizeList[temp]>0){
	    				colSpan=sizeList[temp];
	    			}
			    	$('#tblExciseFLRReport_1 tr:eq('+rowCount+') td:eq('+i+')').attr('colspan',colSpan);
		    	}
			}
	} 
	
	
	function funAddDataRows1(rowData) 
	{
		var table = document.getElementById("tblExciseFLRReport_1");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    for(var i=0;i<rowData.length;i++){
	   		 if(i==0){
	   		    	row.insertCell(i).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" value='"+rowData[0]+"' />";
	   		 } else if(i==1){
	   			 if(rowData[i].trim()=="`"){
	 	   			row.insertCell(i).innerHTML = "<input type=\"hidden\" readonly=\"readonly\" class=\"cell\" name=\"rowList1["+(rowCount)+"].strRow"+i+"\" value='"+rowData[i]+"' />";
	   			 }else{
	 	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList1["+(rowCount)+"].strRow"+i+"\" value='"+rowData[i]+"' />";
	   			 }
	   		 } else if(i==2){
	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"Box\" name=\"rowList1["+(rowCount)+"].strRow"+i+"\" value='"+rowData[i]+"' />";
	   		 } else if(i==3){
	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"rowList1["+(rowCount)+"].strRow"+i+"\" value='"+rowData[i]+"' />";
	   		 } else if(i==4){
	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"rowList1["+(rowCount)+"].strRow"+i+"\" value='"+rowData[i]+"' />";
	   		 } else if(i==5){
	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"rowList1["+(rowCount)+"].strRow"+i+"\" value='"+rowData[i]+"' />";
	   		 } else {
	   				row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList1["+(rowCount)+"].strRow"+i+"\" value='"+rowData[i]+"' />";
	   		 }
		}
	}
	
	function funAddUnderLine1(rowData) 
	{
		var table = document.getElementById("tblExciseFLRReport_1");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    for(var i=0;i<rowData.length;i++){
	   		 if(i==0){
	   		    	row.insertCell(i).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" value='"+' '+"' />";
	   		 } else if(i==1){
	 	   			row.insertCell(i).innerHTML = "<input type=\"hidden\" readonly=\"readonly\" class=\"cell\" name=\"rowList1["+(rowCount)+"].strRow"+i+"\" value='"+'`'+"' />";
	   		 } else if(i==2){
	   				row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"Box\" name=\"rowList1["+(rowCount)+"].strRow"+i+"\" value='"+' '+"' />";
	   		 } else if(i==3){
	   				row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"rowList1["+(rowCount)+"].strRow"+i+"\" value='"+' '+"' />";
	   		 } else if(i==4){
	   				row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"rowList1["+(rowCount)+"].strRow"+i+"\" value='"+' '+"' />";
	   		 } else if(i==5){
	   				row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"rowList1["+(rowCount)+"].strRow"+i+"\" value='"+' '+"' />";
	   		 } else {
	   			if(rowData[i].trim() !=""){
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList1["+ (rowCount)+"].strRow"+i+"\" value='"+'----'+"' />";
	    		}else{
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList1["+ (rowCount)+"].strRow"+i+"\" value='"+' '+"' />";
	    		}
	   		 }
		}
// 	    $('#tblExciseFLRReport_1 tr:eq('+rowCount+')').hide();
	}
	
	
	function funAddBlankRows1(rowData) 
	{
		var table = document.getElementById("tblExciseFLRReport_1");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    for(var i=0;i<rowData.length;i++){
	   		 if(i==0){
	   		    	row.insertCell(i).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" value='"+ +"' />";
	   		 } else if(i==1){
	 	   			row.insertCell(i).innerHTML = "<input type=\"hidden\" readonly=\"readonly\" class=\"cell\" name=\"rowList1["+(rowCount)+"].strRow"+i+"\" value='"+'`'+"' />";
	   		 } else {
	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList1["+(rowCount)+"].strRow"+i+"\" value='"+' '+"' />";
	   		 }
		}
	}
	
	
	function funDrawTable2(response){
		
		funAddHeaderRow2(response.Header2,response.SubCatCode2,response.CatSizeLenght2);
		funAddDataRows2(response.SizeName2);
//			funAddUnderLine2(response.Header2);
		
		 $.each(response.Data2, function(i,item)
		{
			funAddDataRows2(response.Data2[i]);
		});
		
		 if(response.Total2.length>0){
			
			funAddUnderLine2(response.Total2);
			funAddDataRows2(response.Total2);
		}
		 
	 	if(response.Summary2.length>0){
			funAddBlankRows2(response.Total2);
			funAddBlankRows2(response.Total2);
			funAddBlankRows2(response.Total2);
		}
		
		$.each(response.Summary2, function(i,item)
		{
			funAddDataRows2(response.Summary2[i]);
		});
		
	}
	
	function funAddHeaderRow2(rowData,catCode,sizeList){
		var table = document.getElementById("tblExciseFLRReport_2");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
		    for(var i=0;i<rowData.length;i++){
		    	if(i==0){
		    		row.insertCell(i).innerHTML= "<input type=\"hidden\"readonly=\"readonly\" value='"+rowData[i]+"' />";	
		    	}else if(i==1){
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"headerList2["+(rowCount)+"].strCol"+i+"\" value='"+rowData[i]+"' />";
		    	}else if(i==2){
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"Box\" name=\"headerList2["+(rowCount)+"].strCol"+i+"\" value='"+rowData[i]+"' />";
		    	}else if(i==3){
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"Box\" name=\"headerList2["+(rowCount)+"].strCol"+i+"\" value='"+rowData[i]+"' />";
		    	}else if(i==4){
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"Box\" name=\"headerList2["+(rowCount)+"].strCol"+i+"\" value='"+rowData[i]+"' />";
		    	}else if(i==5){
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"Box\" name=\"headerList2["+(rowCount)+"].strCol"+i+"\" value='"+rowData[i]+"' />";
		    	}else{
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"headerList2["+(rowCount)+"].strCol"+i+"\" value='"+rowData[i]+"' />";
		    		var temp=catCode[i].trim();
	    			var colSpan=0;
	    			if(sizeList[temp]>0){
	    				colSpan=sizeList[temp];
	    			}
			    	$('#tblExciseFLRReport_2 tr:eq('+rowCount+') td:eq('+i+')').attr('colspan',colSpan);
		    	}
			}
	} 
	
	
	function funAddDataRows2(rowData) 
	{
		var table = document.getElementById("tblExciseFLRReport_2");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    for(var i=0;i<rowData.length;i++){
	   		 if(i==0){
	   		    	row.insertCell(i).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" value='"+rowData[0]+"' />";
	   		 } else if(i==1){
	   			 if(rowData[i].trim()=="`"){
	 	   			row.insertCell(i).innerHTML = "<input type=\"hidden\" readonly=\"readonly\" class=\"cell\" name=\"rowList2["+(rowCount)+"].strRow"+i+"\" value='"+rowData[i]+"' />";
	   			 }else{
	 	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList2["+(rowCount)+"].strRow"+i+"\" value='"+rowData[i]+"' />";
	   			 }
	   		 } else if(i==2){
	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"Box\" name=\"rowList2["+(rowCount)+"].strRow"+i+"\" value='"+rowData[i]+"' />";
	   		 } else if(i==3){
	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"rowList2["+(rowCount)+"].strRow"+i+"\" value='"+rowData[i]+"' />";
	   		 } else if(i==4){
	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"rowList2["+(rowCount)+"].strRow"+i+"\" value='"+rowData[i]+"' />";
	   		 } else if(i==5){
	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"rowList2["+(rowCount)+"].strRow"+i+"\" value='"+rowData[i]+"' />";
	   		 } else {
	   				row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList2["+(rowCount)+"].strRow"+i+"\" value='"+rowData[i]+"' />";
	   		 }
		}
	}
	
	function funAddUnderLine2(rowData) 
	{
		var table = document.getElementById("tblExciseFLRReport_2");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    for(var i=0;i<rowData.length;i++){
	   		 if(i==0){
	   		    	row.insertCell(i).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" value='"+' '+"' />";
	   		 } else if(i==1){
	 	   			row.insertCell(i).innerHTML = "<input type=\"hidden\" readonly=\"readonly\" class=\"cell\" name=\"rowList2["+(rowCount)+"].strRow"+i+"\" value='"+'`'+"' />";
	   		 } else if(i==2){
	   				row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"Box\" name=\"rowList2["+(rowCount)+"].strRow"+i+"\" value='"+' '+"' />";
	   		 } else if(i==3){
	   				row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"rowList2["+(rowCount)+"].strRow"+i+"\" value='"+' '+"' />";
	   		 } else if(i==4){
	   				row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"rowList2["+(rowCount)+"].strRow"+i+"\" value='"+' '+"' />";
	   		 } else if(i==5){
	   				row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"rowList2["+(rowCount)+"].strRow"+i+"\" value='"+' '+"' />";
	   		 } else {
	   			if(rowData[i].trim() !=""){
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList2["+ (rowCount)+"].strRow"+i+"\" value='"+'----'+"' />";
	    		}else{
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList2["+ (rowCount)+"].strRow"+i+"\" value='"+' '+"' />";
	    		}
	   		 }
		}
// 	    $('#tblExciseFLRReport_2 tr:eq('+rowCount+')').hide();
	}
	
	
	function funAddBlankRows2(rowData) 
	{
		var table = document.getElementById("tblExciseFLRReport_2");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    for(var i=0;i<rowData.length;i++){
	   		 if(i==0){
	   		    	row.insertCell(i).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" value='"+ +"' />";
	   		 } else if(i==1){
	 	   			row.insertCell(i).innerHTML = "<input type=\"hidden\" readonly=\"readonly\" class=\"cell\" name=\"rowList2["+(rowCount)+"].strRow"+i+"\" value='"+'`'+"' />";
	   		 } else {
	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList2["+(rowCount)+"].strRow"+i+"\" value='"+' '+"' />";
	   		 }
		}
	}
	
	function funHelp(transactionName)
	{
		var txtFromDate = $("#txtFromDate").val();
		var txtToDate = $("#txtToDate").val();
		var licenceCode = $("#cmbLicenceCode").val();
		txtToDate
		if(CalculateDateDiff(txtFromDate,txtToDate)){
			fieldName=transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&licenceCode="+licenceCode+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
			window.open("searchform.html?formname="+transactionName+"&txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&licenceCode="+licenceCode+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
		}
	}
	
	function funSetData(code)
	{
		switch (fieldName) 
		{
		   case 'fromBillNo':
		    	funSetFromData(code);
		        break;
		        
		   case 'toBillNo':
		    	funSetToData(code);
		        break;
		}
	}
	
	function funSetFromData(code){
		$("#strFromBillNo").val(code.toString().trim());
	}
	
	function funSetToData(code){
		$("#strToBillNo").val(code.toString().trim());
	}
	
	function funApplyNumberValidation(){
		$(".numeric").numeric();
		$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
		$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
		$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
	    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
	}
	
	
	$(function(){
		$("form").submit(function(event){
			var table = document.getElementById("tblExciseFLRReport_1");
			var rowCount = table.rows.length;
					
			if (rowCount > 2){
				$("#txtFromDate").val(fDate);
				$("#txtToDate").val(tDate);
					return true;
				} else {
					alert("Data Not Available");
					return false;
				}
			});
		});
	
</script>

</head>
<body>
	<div id="formHeading">
	<label>Excise FLR-6 Report</label>
	</div>

<br/>
<br/>

	<s:form name="FLR6Report" method="POST" action="saveFLR6Data.html?saddr=${urlHits}" target="_blank">
	<div>
	<table class="masterTable">
			<tr>
				<td>
					<label>From Date</label>
				</td>
				<td>
					<s:input type="text" id="txtFromDate" class="calenderTextBox" path="strFromDate" />
				</td>
				
				<td>
					<label>To Date</label>
				</td>
				<td>
					<s:input type="text" id="txtToDate" class="calenderTextBox" path="strToDate"/>
				</td>
				
				<td>
					<label>Unit Type</label>
				</td>
				
				<td>
				<s:select id="txtInPeg" path="strInPeg" cssClass="BoxW124px">
					<s:option value="Peg">PegWise</s:option>
					<s:option value="ML">MLWise</s:option>
				</s:select>
				</td>
				
			</tr>
			<tr>
			<td>
					<label>Licence Number</label>
				</td>
				<td ><s:select id="cmbLicenceCode" items="${listLicence}" path="strLicenceCode" cssClass="BoxW124px"></s:select></td>
				<td>
					<label>From Bill No.</label>
				</td>
				<td>
					<s:input type="text" id="strFromBillNo" readonly="true" cssClass="searchTextBox numeric positive-integer" path="strFromBillNo" ondblclick="funHelp('fromBillNo')" />
				</td>
				
				<td>
					<label>To Bill No.</label>
				</td>
				<td>
					<s:input type="text" id="strToBillNo" readonly="true" cssClass="searchTextBox numeric positive-integer" path="strToBillNo" ondblclick="funHelp('toBillNo')" />
				</td>
			
				
			</tr>
			<tr>
				<td>
					<label>Export Format</label>
				</td>
				<td>
					<s:select id="txtExportType" path="strExportType" cssClass="BoxW124px">
						<s:option value="PDF">PDF</s:option>
						<s:option value="EXCEL">EXCEL</s:option>
					</s:select>
				</td>
				<td colspan="3">
				<td>
			</tr>
		</table>	
		<br />
		<br />
		<div style="width:100%;overflow-x:auto !important ; overflow-y:auto !important;">
			<table id="tblExciseFLRReport_1" class="transTablex" style="width:100%; text-align:center !important;">
			</table>
		</div>
		<br />
		<br />
		
		<div style="width:100%;overflow-x:auto !important ; overflow-y:auto !important;">
			<table id="tblExciseFLRReport_2" class="transTablex" style="width:100%; text-align:center !important;">
			</table>
		</div>
		<br />
		<br />
		
		<p align="center">
			<input type="button" value="Display" class="form_button" id="btnSubmit"/>
			<input type="submit" value="Export" class="form_button" id="submit"/>
			<input type="reset" value="Reset" class="form_button" id="btnReset"/>
			
		</p>
		</div>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"	width="60px" height="60px" />
		</div>

	</s:form>
	<script type="text/javascript">
  		funApplyNumberValidation();
	</script>
</body>
</html>
