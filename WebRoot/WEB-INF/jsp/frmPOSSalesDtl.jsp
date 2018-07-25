<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	var fieldName,flgSACode,linkedPOSYN='No';

	$(function() 
			{
		<%if (session.getAttribute("fail") != null) 
		{%>
			alert("Data Already Present\n\n");
// 			session.removeAttribute("fail");
		<%}%>
		
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
		
		var code='';
		<%if(null!=session.getAttribute("rptSACode")){%>
		code='<%=session.getAttribute("rptSACode").toString()%>';
		<%session.removeAttribute("rptSACode");%>
		var isOk=confirm("Do You Want to Generate Slip?");
		if(isOk){
		window.open(getContextPath()+"/openRptStockAdjustmentSlip.html?rptSACode="+code,'_blank');
		}
		<%}%>
		
		$("#txtLocCode").val("${locationCode}");
    	$("#lblLocName").text("${locationName}");
		
	});
	
	
	
	$(function() 
	{
		$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$( "#txtPostSADate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtFromDate" ).datepicker('setDate', 'today');
		$("#txtToDate" ).datepicker('setDate', 'today');
		$("#txtPostSADate" ).datepicker('setDate', 'today');
		
		$("#btnLoadLinkup").click(function( event )
		{
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var locCode=$("#txtLocCode").val();
			
			if(locCode=='')
			{
				alert("Please Enter Location");
				return;
			}
			
			if(fromDate=='')
			{
				alert("Please Enter From Date");
				return;
			}
			if(toDate=='')
			{
				alert("Please Enter To Date");
				return;
			}
			funFillPOSLinkUpTable(locCode,fromDate,toDate);
		});
		
				
	});
	
	function funCheckTable()
	{
		if(flgSACode==true)
		{
			alert("Data Already Saved!!!!");
			return false;
		}
	}


	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	function funSetData(code)
	{
		switch (fieldName) 
		{
		    case 'locationmaster':
		    	funSetLocation(code);
		        break;
		}
	}
	
	
	function funSetLocation(code)
	{
		var searchUrl="";
		searchUrl=getContextPath()+"/loadLocationMasterData.html?locCode="+code;
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	if(response.strLocCode=='Invalid Code')
			       	{
			       		alert("Invalid Location Code");
			       		$("#txtLocCode").val('');
			       		$("#lblLocName").text("");
			       		$("#txtLocCode").focus();
			       	}
			       	else
			       	{
				    	$("#txtLocCode").val(response.strLocCode);
		        		$("#lblLocName").text(response.strLocName);
		        		$("#txtProdCode").focus();
			       	}
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
	
	
	function funFillPOSLinkUpTable(code,fromDate,toDate)
	{
		flgSACode=false;
		var searchUrl="";
		var param=code+","+fromDate+","+toDate;
		searchUrl=getContextPath()+"/loadPOSLinkUpData.html?param="+param;
		//alert(searchUrl);
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	    	funDeleteTableAllRows();
					    	$.each(response, function(i,item)
							{
					    		//alert(response[i][0]);
					    		funAddRow(response[i][0],response[i][1],response[i][2],response[i][3],response[i][7],response[i][4],response[i][5],response[i][9],response[i][6],response[i][8]);
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
	
	function funAddRow(posItemCode,posItemName,wsItemCode,wsItemName,type,saCode,qty,billDate,dblRate,posCode)
	{
		//alert(qty);
		var table = document.getElementById("tblPOSLinkUp");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    if(saCode!='')
	    	flgSACode=true;
	    
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listPOSSalesDtl["+(rowCount)+"].strPOSItemCode\" id=\"txtPOSItemCode."+(rowCount)+"\" value='"+posItemCode+"' />";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listPOSSalesDtl["+(rowCount)+"].strPOSItemName\" id=\"txtPOSItemName."+(rowCount)+"\" value='"+posItemName+"' />";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listPOSSalesDtl["+(rowCount)+"].strWSItemCode\" id=\"txtWSItemCode."+(rowCount)+"\" value='"+wsItemCode+"' />";
	    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listPOSSalesDtl["+(rowCount)+"].strWSItemName\" id=\"txtWSItemName."+(rowCount)+"\" value='"+wsItemName+"' />";
	    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listPOSSalesDtl["+(rowCount)+"].strProdType\" id=\"strWSItemType."+(rowCount)+"\" value='"+type+"' />";
	    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listPOSSalesDtl["+(rowCount)+"].strSACode\" id=\"txtSACode."+(rowCount)+"\" value='"+saCode+"' />";
	    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" style=\"text-align:right\" class=\"Box\" size=\"8%\" name=\"listPOSSalesDtl["+(rowCount)+"].dblQuantity\" id=\"txtQty."+(rowCount)+"\" value='"+parseFloat(qty)+"' />";
	    row.insertCell(7).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';	    
	    row.insertCell(8).innerHTML= "<input readonly=\"readonly\" style=\"text-align:right\" class=\"Box\" size=\"0%\" name=\"listPOSSalesDtl["+(rowCount)+"].dteBillDate\" id=\"txtBillDate."+(rowCount)+"\" value='"+billDate+"' />";
	    row.insertCell(9).innerHTML= "<input readonly=\"readonly\" style=\"text-align:right\" class=\"Box\" size=\"0%\" name=\"listPOSSalesDtl["+(rowCount)+"].dblRate\" id=\"txtRate."+(rowCount)+"\" value='"+parseFloat(dblRate)+"' />";
	    row.insertCell(10).innerHTML= "<input readonly=\"readonly\" style=\"text-align:right\" class=\"Box\" size=\"0%\" name=\"listPOSSalesDtl["+(rowCount)+"].strPOSCode\" id=\"txtPOSCode."+(rowCount)+"\" value='"+posCode+"' />";
	}
	
	
	function funDeleteRow(obj) 
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblPOSLinkUp");
	    table.deleteRow(index);
	}
	
	
	function funDeleteTableAllRows()
	{
		$("#tblPOSLinkUp tr").remove();
		/* var table = document.getElementById("tblPOSLinkUp");
		var rowCount = table.rows.length;
		for(var i=0;i<rowCount;i++)
		{
			table.deleteRow(i);
		} */
	}
	function funResetFields()
	{
		location.reload(true); 
	}
	
	
	function funOpenExportImport()			
	{
		var transactionformName="frmPOSSalesSheet";
		var locCode = $("#txtLocCode").val();
		if(locCode.length==0)
			{
				alert("Please! Select Location First");
				return false;
			}
	//	var exportUOM= $('#cmbConversionUOM').val();
       // response=window.showModalDialog("frmExcelExportImport.html?formname="+transactionformName+"&exportUOM="+exportUOM,"","dialogHeight:500px;dialogWidth:500px;dialogLeft:500px;dialogTop:100px");
           response=window.open("frmExcelExportImport.html?formname="+transactionformName,"","dialogHeight:500px;dialogWidth:500px;dialogLeft:500px;dialogTop:100px");
      
           var timer = setInterval(function ()
   			    {
   				if(response.closed)
   					{
   						funCheckPOSLinkedToLocation();
   						if(linkedPOSYN=="Yes")
   						{	
	   						if (response.returnValue != null)
	   						{
	   							response=response.returnValue;
	   							alert("Data Imported Sucessfully Please! Load Linkup Data");
	   							
	//    							   $.each(response[0], function(i,item)
	//    									{
	//    										funAddRow(item.strPOSItemCode,item.strPOSItemName,"","","","",item.dblQuantity,item.dblRate,item.POSCode);
	//    									}); 
									
	   							funFillPOSLinkUpTable(locCode,response[1],response[2]);
	   							$("#txtFromDate").val(response[1]);
	   							$("#txtToDate").val(response[2]);
	   						}
   						}
   						clearInterval(timer);
   					}
   			    }, 500);   
           
           
        
	}
	
	
	function funCallFormAction(actionName,object)
	{
		var flg = true;
		var fromDate=$("#txtFromDate").val();
		var toDate=$("#txtToDate").val();
		var locCode=$("#txtLocCode").val();
		
		if(locCode=='')
		{
			
			alert("Please Enter Location");
			flg= false;
		} 
			
		if(fromDate=='')
		{
			alert("Please Enter From Date");
			flg= false;
		}
		if(toDate=='')
		{
			alert("Please Enter To Date");
			flg= false;
		}
		return flg;
	}
	
	
	function funCheckPOSLinkedToLocation()
	{
		
		var searchUrl="";
		var locCode=$("#txtLocCode").val();;
		searchUrl=getContextPath()+"/checkPOSLinkedToLocation.html?locCode="+locCode;
		//alert(searchUrl);
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "text",
			    async :false,
			    success: function(response)
			    {
			    	    if(response == "NotLinked")
			    	    	{
			    	    	  alert("POS is Not Linked To Location");
			    	    	  linkedPOSYN ="No";
			    	    	}
			    	    else
			    	    {
			    	    	linkedPOSYN ="Yes";
			    	    }
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
	

</script>

</head>
<body>

	<div id="formHeading">
	<label>POS Sales Data Posting</label>
	</div>

<br/>
<br/>

	<s:form name="POSSalesDtl" method="POST" action="savePOSSalesDtl.html">

		<table class="transTable">
			<tr>
			<th colspan="5"></th>
				<th align="right"><a onclick="funOpenExportImport()"
					href="javascript:void(0);">Export/Import</a>&nbsp; &nbsp; &nbsp;
<!-- 					&nbsp;<a id="baseUrl" href="#">Attatch Documents</a>&nbsp; &nbsp;  -->
				</th>
			</tr>
			
			
			<tr>
				<td width="10%"><label>Location Code</label></td>
			    <td colspan="5"><s:input type="text" id="txtLocCode" name="locCode" path="strLocCode" class="searchTextBox" ondblclick="funHelp('locationmaster')" required="true"/> 
			    <label id="lblLocName" class="namelabel"></label></td>
			</tr>
			
			<tr>
				<td>
					<label>From Date</label>
				</td>
				<td>
					<input type="text" id="txtFromDate" class="calenderTextBox" />
				</td>
				
				<td width="10%">
					<label>To Date</label>
				</td>
				<td>
					<input type="text" id="txtToDate" class="calenderTextBox" />
				</td>
				
				<td >
					<label>Post SA Date</label>
				</td>
				<td>
					<s:input type="text" id="txtPostSADate" class="calenderTextBox" path="dtePostSADate" />
				</td>
				
				
			</tr>
			
		</table>
		
		<div class="dynamicTableContainer" style="height: 300px;">
			<table
				style="height: 28px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
				<tr bgcolor="#72BEFC">
				<td style="width:7%;">POS Item Code</td>
				<td style="width:25%;">POS Item Name</td>
				<td style="width:9%;">WS Item Code</td>
				<td style="width:25%;">WS Item Name</td>
				<td style="width:10%;">WS Item Type</td>
				<td style="width:10%;">SA Code</td>	
				<td style="width:8%;">Qty</td>	
				<td style="width:6%;">Delete</td>				
			</tr>
		</table>
		<div
				style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
					<table id="tblPOSLinkUp"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col8-center">
					<tbody>
					<col style="width:7.5%">					
					<col style="width:26%">
					<col style="width:9.5%">
					<col style="width:26%">
					<col style="width:11%">
					<col style="width:10%">
					<col style="width:8%;text-align:right">
					<col style="width:5%">
					<col style="width:0%">
					<col style="width:0%">
					<col style="width:0%">
					</tbody>
				</table>
			</div>
		</div>
		<br />
		<p align="center">
<!-- 			<input type="button" value="Check Linkup" tabindex="3" class="form_button" id="btnCheckLinkup"/> -->
			<input type="button" value="Load Linkup" tabindex="3" class="form_button" id="btnLoadLinkup"/>
			<input type="submit" value="submit" tabindex="3" class="form_button" id="btnGenerateSA" />
			<input type="button" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
