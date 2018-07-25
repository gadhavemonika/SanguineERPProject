<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Void Bill</title>
<script type="text/javascript">

	$(function(){
	var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
		
		//$("#txtBillDate").timepicker();
		//$("#txtBillDate").datepicker({ dateFormat: 'dd-mm-yy' });
		//$("#txtBillDate").datepicker('setDate', pmsDate);
		$("#txtBillDate").val(pmsDate);
		
		document.getElementById("txtBillDate").readOnly = true;
		document.getElementById("txtFolioNo").readOnly = true;
		document.getElementById("txtGuestName").readOnly = true;
		document.getElementById("txtRoomName").readOnly = true;
		document.getElementById("txtTotalAmt").readOnly = true;
		//document.getElementById("cmbReason").readOnly = true;
		$("#cmbReason").attr('disabled',true);
		document.getElementById("txtRemark").readOnly = true;
		document.getElementById("tblChangeBillDetails").readOnly = true;
	});
	
	function funHelp(transactionName)
	{
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;top=500,left=500")		
	}
	function funSetData(code)
	{
		$("#txtBillNo").val(code);
		document.all["divReason"].style.display = 'block';
		funLoadVoidBillData(code);
	}
	function funLoadVoidBillData(code){
		var searchUrl=getContextPath()+ "/loadVoidBill.html?strBillNo=" + code;;
		$.ajax({
			type :"GET",
			url : searchUrl,
			dataType : "json",
			async: false,
			success: function(response){
				$("#txtFolioNo").val(response[0].strFolioNo);
				var billDate=response[0].strBilldate.split(" ")[0].split("-");
				
				$("#txtBillDate").val(billDate[2]+"-"+billDate[1]+"-"+billDate[0]);
				$("#txtGuestName").val(response[0].strGuestName);
				$("#txtRoomNo").val(response[0].strRoomNo);
				$("#txtRoomName").val(response[0].strRoomName);
				//$("#txtExtraBed").val(response[0].strExtraBed);
				
				$("#txtTotalAmt").val(response[0].dblTotalAmt);
				$("#txtGRNNo").val(response[0].strGSTNo);
				$("#txtCompanyName").val(response[0].strCompanyName);
				funRemoveRows();
				$.each(response, function(i,item)
				{
					funFillBillTable(response[i].strFolioNo,response[i].strDocNo,response[i].strMenuHead,response[i].dblIncomeHeadPrice,response[i].strRevenueCode);
				});
				
			},
			error : function(jqXHR, exception)
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
	
	function funFillBillTable(strFolioNo,docNo,incomeHead,incomeHeadPrice,strRevenueCode){
		 	var table = document.getElementById("tblChangeBillDetails");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
	
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" style=\"text-align: left;width:100%\" name=\"listVoidBilldtl["+(rowCount)+"].strFolioNo\" id=\"strFolioNo."+(rowCount)+"\" value='"+strFolioNo+"' />";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" style=\"text-align: left;width:100%\" name=\"listVoidBilldtl["+(rowCount)+"].strRevenueCode\" id=\"strRevenueCode."+(rowCount)+"\" value='"+strRevenueCode+"' />";
		   /*  row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" style=\"text-align: left;width:100%\" name=\"listVoidBilldtl["+(rowCount)+"].strDocNo\" id=\"strDocNo."+(rowCount)+"\" value='"+docNo+"' />"; */
		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" style=\"text-align: left;width:100%\" name=\"listVoidBilldtl["+(rowCount)+"].strMenuHead\" id=\"strMenuHead."+(rowCount)+"\" value='"+incomeHead+"' />";
		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" style=\"text-align: right;width:100%\" name=\"listVoidBilldtl["+(rowCount)+"].dblIncomeHeadPrice\" id=\"dblIncomeHeadPrice."+(rowCount)+"\" value='"+incomeHeadPrice+"' />";
		    //row.insertCell(4).innerHTML= "<input type=\"button\" class=\"deletebutton\" size=\"2%\" style=\"text-align: center;width:100%\" value = \"Delete\" onClick=\"Javacsript:funDeleteRow(this)\"/>";
		    row.insertCell(4).innerHTML= "<input type=\"button\" class=\"deletebutton\" size=\"2%\" style=\"text-align: center;width:100%\" value = \"Delete\" />";
	}
	//Delete a All record from a grid
	function funRemoveRows()
	{
		var table = document.getElementById("tblChangeBillDetails");
		var rowCount = table.rows.length;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	//Function to Delete Selected Row From Grid
	function funDeleteRow(obj)
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblChangeBillDetails");
	    table.deleteRow(index);
	}

	function funSaveBill(){
		if($("#txtBillNo").val()==""){
			alert("Select Bill No");
			return false;
		}
		var table = document.getElementById("tblChangeBillDetails");
		var rowCount = table.rows.length;
		if(rowCount<0){
			alert(" No Folio Present");
			return false;
		}
		if($("#cmbReason").val==""){
			alert("select reason");
			return false;
		}
		if($("#txtRemark").val==""){
			alert("Enter Valid Remark");
			return false;
		}
		
		if($("#txtGRNNo").val()==""){
			alert("GRN No should not be blank");
			return false;
		}
		
		
	}


	/**
	* Success Message After Saving Record
	**/
	 $(document).ready(function()
	{
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
</script>
</head>
<body>


	<div id="formHeading">
	<label>Change GST No</label>
	</div>

<br/>
<br/>

	<s:form name="frmChangeGSTNoOnBill" method="POST" action="updateGSTNoOnBill.html?saddr=${urlHits}">
		
		<table class="transTable">
		
			<tr>
				<td><label>Bill No.</label></td>
				<td><s:input id="txtBillNo" path="strBillNo"  cssClass="searchTextBox" ondblclick="funHelp('billNo')"/></td>
				<td><label>Folio No</label></td>	
				<td><s:input type="readonly" id="txtFolioNo" path="strFolioNo"  cssClass="longTextBox"/></td>
				<td><label>Bill Date</label></td>
		        <td><s:input type="readonly" id="txtBillDate" required="required" path="strBilldate" pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox"/></td>
			</tr>
			
			<tr>
				<td><label>Guest Name</label></td>	
				<td><s:input type="readonly" id="txtGuestName" path="strGuestName"  cssClass="longTextBox"/></td>
				<td><label>Room No</label></td>	
				<td><s:input type="readonly" id="txtRoomName" path="strRoomName"  cssClass="longTextBox"/></td>
				
				<td><s:input type="hidden" id="txtRoomNo" path="strRoomNo"/></td>
				<td><s:input type="hidden" id="txtVoidType" path="strVoidType"/></td>

			</tr>
			
			<tr>
				<td><label>Total Amount</label></td>	
				<td><s:input type="readonly" id="txtTotalAmt" path="dblTotalAmt"  cssClass="longTextBox"/></td>
				<td><label>GST No</label></td>	
				<td><s:input id="txtGRNNo" path="strGSTNo"  cssClass="longTextBox"/></td>
				<td><label>Company Name</label></td>	
				<td><s:input id="txtCompanyName" path="strCompanyName"  cssClass="longTextBox"/></td>
				<td colspan="4">
			</tr>
			<tr>
			<td colspan="6">
			<div id="divReason" style="display:none;">
				<label>Reason</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 <s:select type="readonly" id="cmbReason" path="strReason" cssClass="BoxW124px">
    			 <s:options items="${listReason}"/>
    			</s:select>
    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			<label>Remark</label>
    			<s:input type="readonly" id="txtRemark" path="strRemark"  cssClass="longTextBox"/>
			</div>
			</td>
			</tr>
			<tr>
			<td colspan="6">
			
			</td>
			</tr>
		</table>
		<div class="dynamicTableContainer" style="height: 300px;">
				<table style="height: 28px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
					<tr bgcolor="#72BEFC">
					
						<td style="width:8%;">Folio</td>
						<td style="width:7.5%;">Revenue Code</td>
						<!-- <td style="width:7%;">Doc Code</td> -->
						<td style="width:14%;">Income Head</td>
						<td style="width:9%; text-align: center;">Total</td>
						<td style="width:3%;">Delete</td>
						
					</tr>
				</table>
		
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblChangeBillDetails"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col8-center">
					<tbody>
						<col style="width: 8.5%;">
						<col style="width: 8%;">
					<%-- 	<col style="width: 7%;"> --%>
						<col style="width: 15%;">
						<col style="width: 9%;">
						<col style="width: 3%;">
					</tbody>
				</table>
			</div>
		</div>
				
		<br/>
		<br/>
		<p align="center">
			<input type="submit" value="Update"  tabindex="3" class="form_button"  onclick="return funSaveBill()"/>
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>		
		
	</s:form>
</body>
</html>