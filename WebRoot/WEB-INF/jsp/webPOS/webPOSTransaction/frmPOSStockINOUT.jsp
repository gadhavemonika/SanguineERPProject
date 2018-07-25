<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">

 	var fieldName,textValue2="",selectedRowIndex=0,modifiedRow=0,totalAmount=0,totalQty=0;
 	var myMap = new Map(); 
 
 /*On form Load It Reset form :Ritesh 22 Nov 2014*/
 	$(function() 
	{		
		$("#txtPurchaseBillDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtPurchaseBillDate" ).datepicker('setDate', 'today');
		
	}); 
 	 
 	function funHelp(transactionName)
	{
 	   fieldName=transactionName;
       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
    }
 	
 	
 	function funSetData(code)
	{
		switch(fieldName)
		{
			case "POSItemList":
				funLoadPOSItemList(code);
				break;
				
			case "StockIn":
				funLoadStockInDetails(code);
				break;
		}
	}
 	
 	function funLoadPOSItemList(code)
	{
		var searchurl=getContextPath()+"/loadItemList.html?ItemCode="+code;
		 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strItemCode=='Invalid Code')
		        	{
		        		alert("Invalid Item Code Code");
		        		$("#txtItemName").val('');
		        	}
		        	else
		        	{
		        		if(myMap.has(code))
			   	    	 {
		        			var value=myMap.get(code);
			   	    		var dataArr = value.split('#');
			   	    		qty=parseInt(dataArr[0]);
			   	    		purchaseRate=parseInt(dataArr[1]);
			   	    		amount=parseInt(dataArr[2]);
		        			$("#txtItemCode").val(response.strItemCode);
			        	    $("#txtExternalCode").val(response.strExternalCode);
				        	$("#txtItemName").val(response.strItemName);
			   	    		$("#txtPurchaseRate").val(parseInt(dataArr[1]));
			   	    		document.getElementById("txtPurchaseRate").readOnly = true;;
			   	    		myMap.set(code, qty+"#"+purchaseRate+"#"+amount+"#"+parseInt(dataArr[3]));
			   	    	 }
		        		else
		        		{
		        			$("#txtItemCode").val(response.strItemCode);
			        	    $("#txtExternalCode").val(response.strExternalCode);
				        	$("#txtItemName").val(response.strItemName);
		        		}
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
 	
 	
 function funLoadStockInDetails(code)
	{
		var searchurl=getContextPath()+"/loadStockInDetails.html?StockInCode="+code;
		 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strPSPCode=='Invalid Code')
		        	{
		        		alert("Invalid Item Code Code");
		        		$("#txtPhysicalStockNo").val('');
		        	}
		        	else
		        	{
		        		$("#lblStkInCode").text(response.strStkInCode);
		        		$("#txtStkInCode").val(response.strStkInCode);
		        		$("#cmbReason").val(response.strReasonCode);
		        		$("#txtPurchaseBillDate").val(response.dtePurchaseBillDate);
		        		$("#txtPurchaseBillNo").val(response.strPurchaseBillNo);
	        		    $.each(response.listOfItem,function(i,item)
					     {
	        		       funFillGrid(item.strItemName,item.strItemCode,item.dblQuantity,item.dblPurchaseRate,item.dblAmount,"")
						 });
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
 	
 	
 	
 	
 	
 	
 	
 	
	function funSetQty(text)
	{	       
		if(text=="clear")
		{
			$("#txtQty").val("");
			textValue2="";
		}
		else if(text=="dot")
		{
			textValue2=textValue2+".";
			$("#txtQty").val(textValue2);
		}
		else
		{
			textValue2=textValue2+text;
			$("#txtQty").val(textValue2);
		}
		
    }
 	
	function funAddRowDetails()
	{
		if($("#txtItemName").val()==null)
		{
			return true;
			alert("Enter ItemName...");
		}
		
		if($("#dblQty").val()==0)
		{
			return true;
			alert("Qty should not be 0...");
		}
		
		var itemName = $("#txtItemName").val();
	    var itemCode = $("#txtItemCode").val();
	    var qty = $("#txtQty").val();
	    var purchaseRate=$("#txtPurchaseRate").val();
	    var externalCode = $("#txtExternalCode").val();
	    var amount=(purchaseRate*qty);
	    funAddRow(itemName,itemCode,qty,purchaseRate,amount,externalCode);
	}
 	
 	function funAddRow(itemName,itemCode,qty,purchaseRate,amount,externalCode)
	{
 		var cnt=1,found="No";
	    if(myMap.size>0)
		{
	    	if(myMap.has(itemCode))
	    	 {
	    		var value=myMap.get(itemCode);
   	    		var dataArr = value.split('#');
   	    		totalAmount=totalAmount+parseInt(amount);
   			    totalQty=totalQty+parseInt(qty);
   			    qty=parseInt(qty)+parseInt(dataArr[0]);
   	    		purchaseRate=parseInt(dataArr[1]);
   	    		amount=parseInt(amount)+parseInt(dataArr[2]);
   	    		 
   	    		myMap.set(itemCode, qty+"#"+purchaseRate+"#"+amount+"#"+parseInt(dataArr[3]));
	    		var table = document.getElementById("tblData");
	    		var rowCount = table.rows.length;
	    		table.deleteRow(parseInt(dataArr[3]));
	    		var row = table.insertRow(parseInt(dataArr[3]));
	    		
    		    row.insertCell(0).innerHTML= "<input name=\"listOfItem["+(parseInt(dataArr[3]))+"].strItemName\" readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"txtItemName."+(rowCount)+"\" value='"+itemName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    		    row.insertCell(1).innerHTML= "<input name=\"listOfItem["+(parseInt(dataArr[3]))+"].dblQuantity\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"15%\" id=\"txtQty."+(rowCount)+"\" value='"+qty+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    		    row.insertCell(2).innerHTML= "<input name=\"listOfItem["+(parseInt(dataArr[3]))+"].dblPurchaseRate\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtPurchaseRate."+(rowCount)+"\" value='"+purchaseRate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    		    row.insertCell(3).innerHTML= "<input name=\"listOfItem["+(parseInt(dataArr[3]))+"].dblAmount\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtAmount."+(rowCount)+"\" value='"+amount+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    		    row.insertCell(4).innerHTML= "<input name=\"listOfItem["+(parseInt(dataArr[3]))+"].strItemCode\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"0%\" id=\"txtItemCode."+(rowCount)+"\" value="+itemCode+" onclick=\"funGetSelectedRowIndex(this)\"/>";		    
    		    found="Yes";
    		    funResetFields();
	    	 }
	    	cnt++;
	    }
	    if(found=="No")
	    {    
	    	modifiedRow++;
    		var qty=parseInt(qty);
    		var table = document.getElementById("tblData");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(modifiedRow);
		    totalAmount=totalAmount+amount;
		    totalQty=totalQty+qty;
		    myMap.set(itemCode, qty+"#"+purchaseRate+"#"+amount+"#"+modifiedRow);
		    row.insertCell(0).innerHTML= "<input name=\"listOfItem["+(rowCount)+"].strItemName\" readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"txtItemName."+(rowCount)+"\" value='"+itemName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		    row.insertCell(1).innerHTML= "<input name=\"listOfItem["+(rowCount)+"].dblQuantity\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"15%\" id=\"txtQty."+(rowCount)+"\" value='"+qty+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		    row.insertCell(2).innerHTML= "<input name=\"listOfItem["+(rowCount)+"].dblPurchaseRate\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtPurchaseRate."+(rowCount)+"\" value='"+purchaseRate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		    row.insertCell(3).innerHTML= "<input name=\"listOfItem["+(rowCount)+"].dblAmount\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtAmount."+(rowCount)+"\" value='"+amount+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		    row.insertCell(4).innerHTML= "<input name=\"listOfItem["+(rowCount)+"].strItemCode\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"0%\" id=\"txtItemCode."+(rowCount)+"\" value="+itemCode+" onclick=\"funGetSelectedRowIndex(this)\"/>";		    
		    funResetFields();
	    }	
	    $("#lblTotalQty").text(totalQty);
	    $("#txtTotalQty").val(totalQty);
	    $("#lblTotalAmount").text(totalAmount);
	    $("#txtTotalAmount").val(totalAmount);
	}
 	
 	
 	
 	function funFillGrid(itemName,itemCode,qty,purchaseRate,amount,externalCode)
	{
 		var cnt=1,found="No";
 		var qty=parseInt(qty);
		var table = document.getElementById("tblData");
	    var rowCount = table.rows.length;
	    modifiedRow++;
	    var row = table.insertRow(modifiedRow);
	    totalAmount=totalAmount+amount;
	    totalQty=totalQty+qty;
	    myMap.set(itemCode, qty+"#"+purchaseRate+"#"+amount+"#"+modifiedRow);
	    row.insertCell(0).innerHTML= "<input name=\"listOfItem["+(rowCount)+"].strItemName\" readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"txtItemName."+(rowCount)+"\" value='"+itemName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(1).innerHTML= "<input name=\"listOfItem["+(rowCount)+"].dblQuantity\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"15%\" id=\"txtQty."+(rowCount)+"\" value='"+qty+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(2).innerHTML= "<input name=\"listOfItem["+(rowCount)+"].dblPurchaseRate\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtPurchaseRate."+(rowCount)+"\" value='"+purchaseRate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(3).innerHTML= "<input name=\"listOfItem["+(rowCount)+"].dblAmount\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtAmount."+(rowCount)+"\" value='"+amount+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(4).innerHTML= "<input name=\"listOfItem["+(rowCount)+"].strItemCode\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"0%\" id=\"txtItemCode."+(rowCount)+"\" value="+itemCode+" onclick=\"funGetSelectedRowIndex(this)\"/>";		    
	    funResetFields();
	    $("#lblTotalQty").text(totalQty);
	    $("#txtTotalQty").val(totalQty);
	    $("#lblTotalAmount").text(totalAmount);
	    $("#txtTotalAmount").val(totalAmount);
	    
	}
 
 	function funDeleteRow()
	{
 		var table = document.getElementById("tblData");
	    table.deleteRow(selectedRowIndex);
	}
 	
 	function funResetFields()
 	{
 		$("#txtQty").val("1");
 		$("#txtPurchaseRate").val("0");
 		$("#txtItemCode").val("");
	    $("#txtExternalCode").val("");
    	$("#txtItemName").val("");
    //	$("#txtStkInCode").val("");
    	selectedRowIndex=0;
 		textValue2="";
 		document.getElementById("txtPurchaseRate").readOnly = false;;
 	}
	 

		
	/**
	* Success Message After Saving Record
	**/
	$(document).ready(function()
	{
		var message='';
		<%if (session.getAttribute("success") != null) 
		{
			if(session.getAttribute("successMessage") != null)
			{%>
				message='<%=session.getAttribute("successMessage").toString()%>';
			    <%
			    session.removeAttribute("successMessage");
			}
			boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
			session.removeAttribute("success");
			if (test) 
			{
				%>alert("Data Saved \n\n"+message);<%
			}
		}%>
	});
	
	
	
	function funMoveSelectedRow(count)
	{
		if(count==1)
			{
				if (selectedRowIndex == 0)
				{
					//do nothing
				}
				else
				{
				  var table = document.getElementById("tblData");
				  var itemName=table.rows[selectedRowIndex].cells[0].innerHTML;
				  var qty=table.rows[selectedRowIndex].cells[1].innerHTML; 
				  var purchaseRate=table.rows[selectedRowIndex].cells[2].innerHTML; 
				  var itemCode=table.rows[selectedRowIndex].cells[4].innerHTML;
				  var itemName1=table.rows[selectedRowIndex-1].cells[0].innerHTML;
				  var qty1=table.rows[selectedRowIndex-1].cells[1].innerHTML; 
				  var purchaseRate1=table.rows[selectedRowIndex-1].cells[2].innerHTML; 
				  var itemCode1=table.rows[selectedRowIndex-1].cells[4].innerHTML;
				  funMoveRowUp(itemName,qty,purchaseRate,itemCode,selectedRowIndex,itemName1,qty1,purchaseRate1,itemCode1);
				}
				
			}
			else
			{
				var table = document.getElementById("tblData");
				var rowCount = table.rows.length;
				if(rowCount>0)
				{
					var table = document.getElementById("tblData");
					
					  var itemName=table.rows[selectedRowIndex].cells[0].innerHTML;
					  var qty=table.rows[selectedRowIndex].cells[1].innerHTML; 
					  var purchaseRate=table.rows[selectedRowIndex].cells[2].innerHTML; 
					  var itemCode=table.rows[selectedRowIndex].cells[4].innerHTML;
					  var itemName1=table.rows[selectedRowIndex+1].cells[0].innerHTML;
					  var qty1=table.rows[selectedRowIndex+1].cells[1].innerHTML; 
					  var purchaseRate1=table.rows[selectedRowIndex+1].cells[2].innerHTML; 
					  var itemCode1=table.rows[selectedRowIndex+1].cells[4].innerHTML;
					  funMoveRowDown(itemName,qty,purchaseRate,itemCode,selectedRowIndex,itemName1,qty1,purchaseRate1,itemCode1);
				}
				
			}
	}
	
	
	function funGetSelectedRowIndex(obj)
	{
		 var index = obj.parentNode.parentNode.rowIndex;
		 var table = document.getElementById("tblData");
		 if((selectedRowIndex>0) && (index!=selectedRowIndex))
		 {
			 if(selectedRowIndex%2==0)
			 {
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#A3D0F7';
				 selectedRowIndex=index;
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#ffd966';
				 row.hilite = true;
			 }
			 else
			 {
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#C0E4FF';
				 selectedRowIndex=index;
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#ffd966';
				 row.hilite = true;
	         }
			
		 }
		 else
		 {
			 selectedRowIndex=index;
			 row = table.rows[selectedRowIndex];
			 row.style.backgroundColor='#ffd966';
			 row.hilite = true;
		 }
	}
	
	
	function funMoveRowDown(itemName,qty,purchaseRate,itemCode,rowCount,itemName1,qty1,purchaseRate1,itemCode1)
	{
		var table = document.getElementById("tblData");
	    table.deleteRow(rowCount);
	    var row = table.insertRow(rowCount);
	    row = table.rows[rowCount];
		
		var nameArr = itemName.split('value=');
		var name=nameArr[1].split('onclick=');
		var ItemName=name[0].substring(1, (name[0].length-2));
		var qtyArr = qty.split('value=');
		var quatity=qtyArr[1].split('onclick=');
		var Qty=quatity[0].substring(1, (quatity[0].length-2));
		var purchaseRateArr = purchaseRate.split('value=');
		var rate=purchaseRateArr[1].split('onclick=');
		var Rate=rate[0].substring(1, (rate[0].length-2));
		
		var amount=parseInt(Rate)*parseInt(Qty);
		var itemCodeArr = itemCode.split('value=');
		var code=itemCodeArr[1].split('onclick=');
		var ItemCode=code[0].substring(1, (code[0].length-2));
		
		row.insertCell(0).innerHTML= "<input name=\"listOfItem["+rowCount+"].strItemName\" readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"txtItemName."+(rowCount)+"\" value='"+ItemName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(1).innerHTML= "<input name=\"listOfItem["+rowCount+"].dblQuantity\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"15%\" id=\"txtQty."+(rowCount)+"\" value='"+Qty+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(2).innerHTML= "<input name=\"listOfItem["+rowCount+"].dblPurchaseRate\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtPurchaseRate."+(rowCount)+"\" value='"+Rate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(3).innerHTML= "<input name=\"listOfItem["+rowCount+"].dblAmount\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtAmount."+(rowCount)+"\" value='"+amount+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(4).innerHTML= "<input name=\"listOfItem["+rowCount+"].strItemCode\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"0%\" id=\"txtItemCode."+(rowCount)+"\" value="+ItemCode+" onclick=\"funGetSelectedRowIndex(this)\"/>";		    
	    
	    row = table.rows[rowCount+1];
	    row.style.backgroundColor='#ffd966';
	    selectedRowIndex=rowCount+1;
	}
	
	function funMoveRowUp(itemName,qty,purchaseRate,itemCode,rowCount,itemName1,qty1,purchaseRate1,itemCode1)
	{
		var table = document.getElementById("tblData");
	    table.deleteRow(rowCount);
	    var row = table.insertRow(rowCount);
	    row = table.rows[rowCount];
		
	    var nameArr = itemName.split('value=');
		var name=nameArr[1].split('onclick=');
		var ItemName=name[0].substring(1, (name[0].length-2));
		var qtyArr = qty.split('value=');
		var quatity=qtyArr[1].split('onclick=');
		var Qty=quatity[0].substring(1, (quatity[0].length-2));
		var purchaseRateArr = purchaseRate.split('value=');
		var rate=purchaseRateArr[1].split('onclick=');
		var Rate=rate[0].substring(1, (rate[0].length-2));
		var amount=parseInt(Rate)*parseInt(Qty);
		var itemCodeArr = itemCode.split('value=');
		var code=itemCodeArr[1].split('onclick=');
		var ItemCode=code[0].substring(1, (code[0].length-2));
		
		row.insertCell(0).innerHTML= "<input name=\"listOfItem["+rowCount+"].strItemName\" readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"txtItemName."+(rowCount)+"\" value='"+ItemName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(1).innerHTML= "<input name=\"listOfItem["+rowCount+"].dblQuantity\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"15%\" id=\"txtQty."+(rowCount)+"\" value='"+Qty+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(2).innerHTML= "<input name=\"listOfItem["+rowCount+"].dblPurchaseRate\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtPurchaseRate."+(rowCount)+"\" value='"+Rate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(3).innerHTML= "<input name=\"listOfItem["+rowCount+"].dblAmount\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtAmount."+(rowCount)+"\" value='"+amount+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(4).innerHTML= "<input name=\"listOfItem["+rowCount+"].strItemCode\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"0%\" id=\"txtItemCode."+(rowCount)+"\" value="+ItemCode+" onclick=\"funGetSelectedRowIndex(this)\"/>";		    
	    row = table.rows[rowCount-1];
		row.style.backgroundColor='#ffd966';
		selectedRowIndex=rowCount-1;
	}


	
		
</script>


</head>
<body>
       
     <div id="formHeading">
		<label>Stock In</label>
			</div>
		<br/>
<br/>

<s:form name="Stock In" method="POST" action="saveStockIn.html?saddr=${urlHits}" >
	   <div>
	   
	     <div style=" width: 50%; height: 500px;float:left;background-color: #a4d7ff; "> 
	     <br>
            <div style=" background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 420px; margin: auto; overflow-x: scroll; overflow-y: scroll; width: 90%;">
					<table id="tblData"
							style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
							class="transTablex col2-right col3-right col4-right col5-right">
							<tr >
						<td style="border: 1px  white solid;width:45%"><label>Description</label></td>
						<td style="border: 1px  white solid;width:15%"><label>Qty</label></td>
						<td style="border: 1px  white solid;width:20%"><label>Purchase Rate</label></td>
						<td style="border: 1px  white solid;width:10%"><label>Amount</label></td>
						<td style="border: 1px  white solid;width:0%"><label></label></td>	
						
					</tr>
					</table>
					
			</div>
			<BR>
			<table class=transFormTable>
			
			      <tr>
					    <td>
						 <input id="btnUp" type="button" class="smallButton" value="Up" onclick="funMoveSelectedRow(1);"></input>
			             &nbsp;&nbsp;&nbsp;
			             <input id="btnDown" type="button" class="smallButton" value="Down" onclick="funMoveSelectedRow(0);"></input>
					     &nbsp;&nbsp;&nbsp;
					     <input id="btnDelete" type="button" class="smallButton" value="Delete" onclick="funDeleteRow();"></input>
					     &nbsp;&nbsp;&nbsp;
					     <label>Total Qty</label>
						 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						 &nbsp;&nbsp;&nbsp;
						 <s:label id="lblTotalQty" path="strExternalCode"/>
						 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						 &nbsp;&nbsp;&nbsp;
						 &nbsp;&nbsp;&nbsp;
						 <label>Total Amount</label>
						 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						 &nbsp;&nbsp;&nbsp;
						<s:label id="lblTotalAmount" path="strExternalCode"/>   
						</td>
					</tr>
			
					
			     </table>
			    
            
        </div>
		<div style=" width: 50%; height: 500px; float:right; border-collapse: separate; overflow-x: hidden; overflow-y: scroll; background-color: #C0E2FE;">
		    <br>
		   <table class=transFormTable>
		  
			<tr>
				 <td>
					<label>Stock IN No.</label>
					<s:label id="lblStkInCode" path="strExternalCode"/>
				    <s:input type="hidden" id="txtStkInCode" path="strStkInCode" />
				     <s:input type="hidden" id="txtTotalQty" path="strTotalQty" />
				    <s:input type="hidden" id="txtTotalAmount" path="strTotalAmount" />
				</td>
			</tr>
			
			 <tr>
				 <td >
				 <BR>
				    <label>Bill No</label>
				    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    <s:input  type="text" id="txtPurchaseBillNo" path="strPurchaseBillNo" cssClass="BoxW124px" required="true"/>
				    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    <s:input id="txtPurchaseBillDate" required="required" path="dtePurchaseBillDate" pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox"/>
				</td>
			</tr>
			 
			 
			<tr>
			     <td>
			     <BR>
			        <label>Select Reason</label>
			            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				        <s:select id="cmbReason" items="${reasonList}" name="cmbReason"  cssClass="BoxW124px" path="strReasonCode">
						</s:select>
					<s:input type="hidden" id="txtItemCode" path="strItemCode" />
			        
			    </td>
			</tr>
			 
			 <tr>
				 <td >
				 <BR>
					<label>Item Name</label>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    <s:input  type="text" id="txtItemName" path="strItemName" cssStyle="text-transform: uppercase;" cssClass="longTextBox jQKeyboard form-control"   ondblclick="funHelp('POSItemList')"/>
			    </td>
			</tr>
			<tr>
				 <td>
				 <BR>
					<label>External Code</label>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    <s:input  type="text" id="txtExternalCode" path="strExternalCode" cssClass="BoxW124px" />
			    </td>
			</tr>
			
			 <tr>
			
			 <td> <BR>
			     Quantity:
			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			     <s:input id="txtQty" path="dblQty"  required="true" cssStyle="text-transform: uppercase;" cssClass="BoxW62px" value="1" />
			     &nbsp;&nbsp;&nbsp;&nbsp;
			     Purchase Rate:
			     &nbsp;&nbsp;&nbsp;&nbsp;
			     <s:input id="txtPurchaseRate" path="dblQty"  required="true" cssStyle="text-transform: uppercase;" cssClass="BoxW62px" value="0" />
				 </td>
			</tr>
			
			
				
			<tr>
			   <td>
			      <BR>
			       <BR>
			      <div style=" background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 50px; width: 50%;">
						<table id="tblCalculator"
							style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
							class="transTablex">
						<tr >
							<td style="border: 1px  white solid;width:25%"> <input id="btnSeven" type="button" class="smallButton" value="7" onclick="funSetQty(7);"></input></td>
							<td style="border: 1px  white solid;width:25%"><input id="btnEight" type="button" class="smallButton" value="8" onclick="funSetQty(8);"></input></td>
							<td style="border: 1px  white solid;width:25%"><input id="btnNine" type="button" class="smallButton" value="9" onclick="funSetQty(9);"></input></td>
						</tr>
						 <tr >
							<td style="border: 1px  white solid;width:25%"><input id="btnFour" type="button" class="smallButton" value="4" onclick="funSetQty(4);"></input></td>
							<td style="border: 1px  white solid;width:25%"><input id="btnFive" type="button" class="smallButton" value="5" onclick="funSetQty(5);"></input></td>
							<td style="border: 1px  white solid;width:25%"><input id="btnSix" type="button" class="smallButton" value="6" onclick="funSetQty(6);"></input></td>
						</tr>
						 <tr >
							<td style="border: 1px  white solid;width:25%"><input id="btnOne" type="button" class="smallButton" value="1" onclick="funSetQty(1);"></input></td>
							<td style="border: 1px  white solid;width:25%"><input id="btnTwo" type="button" class="smallButton" value="2" onclick="funSetQty(2);"></input></td>
							<td style="border: 1px  white solid;width:25%"><input id="btnThree" type="button" class="smallButton" value="3" onclick="funSetQty(3);"></input></td>
						</tr>
						<tr >
							<td style="border: 1px  white solid;width:25%"><input id="btnDot" type="button" class="smallButton" value="." onclick="funSetQty('dot');"></input></td>
							<td style="border: 1px  white solid;width:25%"><input id="btnZero" type="button" class="smallButton" value="0" onclick="funSetQty(0);"></input></td>
							<td style="border: 1px  white solid;width:25%"><input id="btnClear" type="button" class="smallButton" value="c" onclick="funSetQty('clear');"></input></td>
						</tr>
					</table>
			</div>
			</td>
			<tr><td ></td></tr>
			<tr><td ></td></tr>
			<tr><td ></td></tr>
			<tr><td ></td></tr>
			  <tr>
			<td>
			
			 <input id="btnAdd" type="button" class="smallButton" value="OK" onclick="funAddRowDetails();"></input>
			  &nbsp;&nbsp;&nbsp;&nbsp;
			 <input id="btnModifyStkin" type="button" class="smallButton" value="Modify" ondblclick="funHelp('StockIn')"></input>
			</td>
			</tr>	
			
			<tr><td ></td></tr>
			<tr>
			<td>
			<BR>
			 <input type="submit" value="Submit" tabindex="3" class="form_button"/> 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
			</td>
			</tr>	
					
			</table>
			<br>
		</div>
		
	   </div>
	   <div style=" width: 50%; height: 400px;">
	       <label></label>
	   </div>
        
		<br>
	  <br>
	
		
	</s:form> 
</body>
</html>