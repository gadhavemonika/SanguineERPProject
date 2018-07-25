<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GROUP MASTER</title>
<style>
.ui-autocomplete {
    max-height: 200px;
    overflow-y: auto;
    /* prevent horizontal scrollbar */
    overflow-x: hidden;
    /* add padding to account for vertical scrollbar */
    padding-right: 20px;
}
/* IE 6 doesn't support max-height
 * we use height instead, but this forces the menu to always be this tall
 */
* html .ui-autocomplete {
    height: 200px;
}
</style>
<script>
var homeDeliveryType="";
$(document).ready(function() {
	
	
	var POSDate="${POSDate}";
  	
	$("#txtdteFromDate").datepicker({ dateFormat: 'yy-mm-dd'  });
	$("#txtdteFromDate" ).datepicker('setDate', '2016-08-01');
	$("#txtdteFromDate").datepicker();
	$("#txtdteToDate").datepicker({ dateFormat: 'yy-mm-dd'  });
	$("#txtdteToDate" ).datepicker('setDate', POSDate);
	
	});


function funLoadCustomerHistory()
{
	var fromDate = ($("#txtdteFromDate").val());
	var toDate=($("#txtdteToDate").val());
	if(fromDate.length==0)
		fromDate='2016-08-01';
	if(toDate.length==0)
		toDate="${POSDate}";
	funRemoveTableRows();
	var gurl = getContextPath() + "/loadCustomerHistory.html";
	$.ajax({
		type : "GET",
		data:{ fromDate:fromDate,
				toDate:toDate,
			 },
		url : gurl,
		dataType : "json",
		success : function(response) {
			
				$.each(response,function(i,item){
	            	funFillBillDtl(item.strBillNo,item.billDate,item.billTime,item.grandTotal);
	            	$.each(item.billItemDtl,function(j,obj){
	            		funFillBillItemDtl(obj.strItemCode,obj.strItemName,obj.dblQuantity,obj.dblAmount);
	            	});
	            	
            	});
	        	
			
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
function funFillBillDtl(strBillNo,billDate,billTime,grandTotal)
{
	var table = document.getElementById("tblitemDtl");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);
	  
	row.insertCell(0).innerHTML= "<input type=\"hidden\" style=\"text-align: left; color:blue;\"   readonly=\"readonly\" class=\"Box \"  >";
    row.insertCell(1).innerHTML= "<input   readonly=\"readonly\" style=\"text-align: left; color:blue;\"  class=\"Box \"   value='"+strBillNo+"'>";
    row.insertCell(2).innerHTML= "<input  style=\"text-align: left; color:blue;\"   readonly=\"readonly\" class=\"Box \"  value='"+billDate+"'>";
    row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" style=\"text-align: right; color:blue;\"  class=\"Box \"   value='"+billTime+"'>";
    row.insertCell(4).innerHTML= "<input style=\"text-align: right; color:blue;\"   readonly=\"readonly\" class=\"Box \"  value='"+grandTotal+"'>";
	  

}
function funFillBillItemDtl(strItemCode,strItemName,dblQuantity,dblAmount)
{
	var table = document.getElementById("tblitemDtl");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);
	  
	row.insertCell(0).innerHTML= "<input type=\"hidden\" name=\"strItemCode\"  readonly=\"readonly\" class=\"Box \" id=\"strItemCode."+(rowCount)+"\" value='"+strItemCode+"'>";
    row.insertCell(1).innerHTML= "<input name=\"strItemName\" readonly=\"readonly\" class=\"Box \"  id=\"strDesc."+(rowCount)+"\" value='"+strItemName+"'>";
    row.insertCell(2).innerHTML= "<input name=\"dblItemQuantity\"  readonly=\"readonly\" style=\"text-align: right;\" class=\"Box \"  id=\"dblQty."+(rowCount)+"\" value='"+dblQuantity+"'>";
    row.insertCell(3).innerHTML= "<input name=\"dblAmount\"  readonly=\"readonly\" style=\"text-align: right;\" class=\"Box \"  id=\"dblAmount."+(rowCount)+"\" value='"+dblAmount+"'>";
    row.insertCell(4).innerHTML= "<input type=\"checkbox\"   id=\"chkPosApplicable."+(rowCount)+"\" value='"+true+"'>";
	  

}
function funOkbtnClick()
{
	var arr=new Array();
	
	var i=0;
	var total=0.00;
	var table = document.getElementById("tblitemDtl");
	var rowCount = table.rows.length;
	 $('#tblitemDtl tr').each(function() {
		 var code=$(this).find("input[type='hidden']").val();
		  var checkbox = $(this).find("input[type='checkbox']");

		    if( checkbox.prop("checked") ){
		    	code=code+"#"+$(this).find("input[name='strItemName']").val()+"#"+$(this).find("input[name='dblItemQuantity']").val()+"#"+$(this).find("input[name='dblAmount']").val();
		    	arr[i++]=code;
		    	total=total+parseFloat($(this).find("input[name='dblAmount']").val());
		    } 
		   
			 });
	 
	 funPreviousForm(arr,total);
}
function funRemoveTableRows()
{
	var table = document.getElementById("tblitemDtl");
	var rowCount = table.rows.length;
	while(rowCount>0)
	{
		table.deleteRow(0);
		rowCount--;
	}
}

function funPreviousForm(arr,total) {
	window.opener.funAddCustomerHistory(arr,total);
	window.close();
}
</script>
</head>
<body onload="funLoadCustomerHistory()">
	<div id="formHeading">
		<label>Customer History</label>
	</div>
	<br />
	<br />
	<s:form name="CustomerHistory" method="POST"
		action="loadCustomerHistory.html?saddr=${urlHits}"
		target="_blank">
		<div>
			<div>

				<table class="masterTable" style="margin: auto;">

					<tr>
						<td>
					<label>From Date</label>
					<s:input id="txtdteFromDate" name="txtdteFromDate" path="dteFromDate"   cssClass="calenderTextBox"  />
				</td>
				<td>
					<label>To Date</label>
					<s:input id="txtdteToDate" name="txtdteToDate" path="dteToDate"  cssClass="calenderTextBox"  />
				</td>

				<td>
					<input type="button" onclick="funLoadCustomerHistory()" value="Execute" tabindex="3" class="form_button" /> 
				</td>

				<td>
					<input  type="button" onclick="funOkbtnClick()"  value="OK" class="form_button" />
				</td>
 						
					</tr>
					

				</table>
			</div>
			<table border="1" class="myTable" style="width:80%;margin: auto;" >
										<thead>
										<tr>
											
											<th style="width:40% border: #c0c0c0 1px solid; background: #78BEF9;">Description</th>
											<th style="width:20% border: #c0c0c0 1px solid; background: #78BEF9;">Qty</th>
											<th style="width:20% border: #c0c0c0 1px solid; background: #78BEF9;">Amount</th>
											<th style="width:20% border: #c0c0c0 1px solid; background: #78BEF9;">Select</th>
										</tr>
										
										</thead>
			</table>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 500px; margin: auto; overflow-y: scroll; width: 80%;">
				
				<table id="tblitemDtl" class="transTablex"
					style="width: 100%; ">
					<tbody>    
							<col style="width:0%"><!--  COl1   -->
							<col style="width:40%"><!--  COl1   -->
							<col style="width:20%"><!--  COl2   -->
							<col style="width:20%"><!--  COl3   -->	
							<col style="width:20%"><!--  COl3   -->									
					</tbody>
				</table>
				
			</div>
			
		</div>
		
	</s:form>

</body>
</html>