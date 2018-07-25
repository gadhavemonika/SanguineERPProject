<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
//Press ESC button to Close Form
	window.onkeyup = function (event) 
	{
		if (event.keyCode == 27) {
			window.close ();
		}
	}
</script>
<script type="text/javascript">
    // document.getElementsById("btnImport").style.visibility="hidden";
   // document.getElementById("btnImport").style.display="none";
    var fieldName,textValue2="",selectedRowIndex=0,modifiedRow=0,saleTotal=0,stkOutTotal=0;
 	var myMap = new Map();
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
				
			case "PhysicalStock":
				funLoadPhysicalStockDetails(code);
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
		        		$("#txtItemCode").val(response.strItemCode);
		        	    $("#txtExternalCode").val(response.strExternalCode);
			        	$("#txtItemName").val(response.strItemName);
			        	
			        
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
 	
 	
 	
 	
 	function funLoadPhysicalStockDetails(code)
	{
		var searchurl=getContextPath()+"/loadPhysicalStockDetails.html?PSPCode="+code;
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
		        		$("#lblPhysicalStockNo").text(response.strPSPCode);
		        		$("#txtPhysicalStockNo").val(response.strPSPCode);
		        		$("#cmbReason").val(response.strReason);
	        		    $.each(response.listPSPDtl,function(i,item)
					     {
	        			   funFillGrid(item.strItemName,item.strItemCode,item.dblPhyStk,item.dblCompStk,item.dblVariance,item.dblVairanceAmt)
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
	    var phyStkQty = $("#txtQty").val();
	    var externalCode = $("#txtExternalCode").val();
	    funAddRow(itemName,itemCode,phyStkQty);
	}
 	
 	function funAddRow(itemName,itemCode,phyStkQty)
	{
 		var compStock=0,variance=0,varianceAmt=0,cnt=1,found="No",purchaseRate=0,saleRate=0;
	    if(myMap.size>0)
		{
	    	if(myMap.has(itemCode))
	    	 {
	    		var value=myMap.get(itemCode);
	    		var dataArr = value.split('#');
	    		compStock=compStock+parseInt(dataArr[0]);
	    		var qty=parseInt(phyStkQty)+parseInt(dataArr[1]);
	    		variance=(qty-compStock);
	    		varianceAmt=varianceAmt+parseInt(dataArr[3]);
	    		myMap.set(itemCode, compStock+"#"+qty+"#"+variance+"#"+varianceAmt+"#"+parseInt(dataArr[4]));
	    		var table = document.getElementById("tblData");
	    		var rowCount = table.rows.length;
	    		table.deleteRow(parseInt(dataArr[4]));
	    		var row = table.insertRow(parseInt(dataArr[4]));
	    		
    		    row.insertCell(0).innerHTML= "<input name=\"listPSPDtl["+(parseInt(dataArr[4]))+"].strItemName\" readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"txtItemName."+(rowCount)+"\" value='"+itemName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    		    row.insertCell(1).innerHTML= "<input name=\"listPSPDtl["+(parseInt(dataArr[4]))+"].dblCompStk\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"15%\" id=\"txtCompStock."+(rowCount)+"\" value='"+compStock+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    		    row.insertCell(2).innerHTML= "<input name=\"listPSPDtl["+(parseInt(dataArr[4]))+"].dblPhyStk\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtPhyStock."+(rowCount)+"\" value='"+qty+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    		    row.insertCell(3).innerHTML= "<input name=\"listPSPDtl["+(parseInt(dataArr[4]))+"].dblVariance\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtVariance."+(rowCount)+"\" value='"+variance+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    		    row.insertCell(4).innerHTML= "<input name=\"listPSPDtl["+(parseInt(dataArr[4]))+"].dblVairanceAmt\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtVarianceAmt."+(rowCount)+"\" value='"+varianceAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    		    row.insertCell(5).innerHTML= "<input name=\"listPSPDtl["+(parseInt(dataArr[4]))+"].strItemCode\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"0%\" id=\"txtItemCode."+(rowCount)+"\" value="+itemCode+" onclick=\"funGetSelectedRowIndex(this)\"/>";		    
    		    found="Yes";
    		    funResetFields();
	    	 }
	    	cnt++;
	    }
	    if(found=="No")
	    {    
	    	var searchurl=getContextPath()+"/getItemStock.html?ItemCode="+itemCode;
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
			        		compStock=response.dblStock;
			        		purchaseRate=response.dblPurchaseRate;
			        		saleRate=response.dblSaleRate;	
			        		variance=(phyStkQty-compStock);
			        		 if(variance>0)
			                 {
			        			 purchaseRate=purchaseRate*variance;
			                     stkOutTotal=stkOutTotal+purchaseRate;
			                     varianceAmt=purchaseRate;
			                 }
			                 else if(variance<0)
			                 {
			                     saleRate=saleRate*(-variance);
			                     saleTotal=saleTotal+saleRate;
			                     varianceAmt=saleRate;
			                 }
			                 else if(variance==0)
			                 {
			                	 variance=0;
			                     saleRate=saleRate*variance;
			                     saleTotal=saleTotal+saleRate;
			                     varianceAmt=saleRate;
			                 }
			        		modifiedRow++;
			        		var qty=parseInt(phyStkQty);
			        		var table = document.getElementById("tblData");
		        		    var rowCount = table.rows.length;
		        		    var row = table.insertRow(modifiedRow);
		        		    myMap.set(itemCode, compStock+"#"+phyStkQty+"#"+variance+"#"+varianceAmt+"#"+modifiedRow);
		        		    row.insertCell(0).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].strItemName\" readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"txtItemName."+(rowCount)+"\" value='"+itemName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		        		    row.insertCell(1).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblCompStk\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"15%\" id=\"txtCompStock."+(rowCount)+"\" value='"+compStock+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		        		    row.insertCell(2).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblPhyStk\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtPhyStock."+(rowCount)+"\" value='"+qty+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		        		    row.insertCell(3).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblVariance\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtVariance."+(rowCount)+"\" value='"+variance+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		        		    row.insertCell(4).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblVairanceAmt\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtVarianceAmt."+(rowCount)+"\" value='"+varianceAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		        		    row.insertCell(5).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].strItemCode\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"0%\" id=\"txtItemCode."+(rowCount)+"\" value="+itemCode+" onclick=\"funGetSelectedRowIndex(this)\"/>";		    
		        		    funResetFields();
		        		    $("#lblTotalSaleAmt").val(saleTotal);
		        		    $("#lblTotalStockOutAmt").val(stkOutTotal);
		        		    $("#txtTotalSaleAmt").val(saleTotal);
		        		    $("#txtTotalStockOutAmt").val(stkOutTotal);
		        		    
		        		    
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
	    
	}
 	
 	
 	
 	function funFillGrid(itemName,itemCode,phyStkQty,compStkQty,variance,varianceAmt)
	{
 		var purchaseRate=0,saleRate=0;
 		var searchurl=getContextPath()+"/getItemStock.html?ItemCode="+itemCode;
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
		        		purchaseRate=response.dblPurchaseRate;
		        		saleRate=response.dblSaleRate;	
		        		if(variance>0)
		                 {
		        			 purchaseRate=purchaseRate*variance;
		                     stkOutTotal=stkOutTotal+purchaseRate;
		                     varianceAmt=purchaseRate;
		                 }
		                 else if(variance<0)
		                 {
		                     saleRate=saleRate*(-variance);
		                     saleTotal=saleTotal+saleRate;
		                     varianceAmt=saleRate;
		                 }
		                 else if(variance==0)
		                 {
		                	 variance=0;
		                     saleRate=saleRate*variance;
		                     saleTotal=saleTotal+saleRate;
		                     varianceAmt=saleRate;
		                 }
		        	}
		        	modifiedRow++;
			 		var table = document.getElementById("tblData");
				    var rowCount = table.rows.length;
				    var row = table.insertRow(modifiedRow);
				    myMap.set(itemCode, compStkQty+"#"+phyStkQty+"#"+variance+"#"+varianceAmt+"#"+modifiedRow);
				    row.insertCell(0).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].strItemName\" readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"txtItemName."+(rowCount)+"\" value='"+itemName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				    row.insertCell(1).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblCompStk\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"15%\" id=\"txtCompStock."+(rowCount)+"\" value='"+compStkQty+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				    row.insertCell(2).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblPhyStk\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtPhyStock."+(rowCount)+"\" value='"+phyStkQty+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				    row.insertCell(3).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblVariance\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtVariance."+(rowCount)+"\" value='"+variance+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				    row.insertCell(4).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblVairanceAmt\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtVarianceAmt."+(rowCount)+"\" value='"+varianceAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				    row.insertCell(5).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].strItemCode\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"0%\" id=\"txtItemCode."+(rowCount)+"\" value="+itemCode+" onclick=\"funGetSelectedRowIndex(this)\"/>";		    
				    funResetFields();
				    $("#lblTotalSaleAmt").text(saleTotal);
        		    $("#lblTotalStockOutAmt").text(stkOutTotal);
        		    $("#txtTotalSaleAmt").val(saleTotal);
        		    $("#txtTotalStockOutAmt").val(stkOutTotal);
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
    

 	function funDeleteRow()
	{
 		var table = document.getElementById("tblData");
	    table.deleteRow(selectedRowIndex);
	}
 	
 	function funResetFields()
 	{
 		$("#txtQty").val("1");
 		$("#txtItemCode").val("");
	    $("#txtExternalCode").val("");
    	$("#txtItemName").val("");
    	//$("#txtPhysicalStockNo").val("");
    	selectedRowIndex=0;
 		textValue2="";
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
				  var compStk=table.rows[selectedRowIndex].cells[1].innerHTML; 
				  var phyStk=table.rows[selectedRowIndex].cells[2].innerHTML; 
				  var itemCode=table.rows[selectedRowIndex].cells[5].innerHTML;
				  var itemName1=table.rows[selectedRowIndex-1].cells[0].innerHTML;
				  var compStk1=table.rows[selectedRowIndex-1].cells[1].innerHTML; 
				  var phyStk1=table.rows[selectedRowIndex-1].cells[2].innerHTML; 
				  var itemCode1=table.rows[selectedRowIndex-1].cells[5].innerHTML;
				  funMoveRowUp(itemName,compStk,phyStk,itemCode,selectedRowIndex,itemName1,compStk1,phyStk1,itemCode1);
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
					  var compStk=table.rows[selectedRowIndex].cells[1].innerHTML; 
					  var phyStk=table.rows[selectedRowIndex].cells[2].innerHTML; 
					  var itemCode=table.rows[selectedRowIndex].cells[5].innerHTML;
					  var itemName1=table.rows[selectedRowIndex+1].cells[0].innerHTML;
					  var compStk1=table.rows[selectedRowIndex+1].cells[1].innerHTML; 
					  var phyStk1=table.rows[selectedRowIndex+1].cells[2].innerHTML; 
					  var itemCode1=table.rows[selectedRowIndex+1].cells[5].innerHTML;
					  funMoveRowDown(itemName,compStk,phyStk,itemCode,selectedRowIndex,itemName1,compStk1,phyStk1,itemCode1);
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
	
	
	function funMoveRowDown(itemName,compStk,phyStk,itemCode,rowCount,itemName1,compStk1,phyStk1,itemCode1)
	{
		var table = document.getElementById("tblData");
	    table.deleteRow(rowCount);
	    var row = table.insertRow(rowCount+1);
	    row = table.rows[rowCount+1];
		
		var nameArr = itemName.split('value=');
		var name=nameArr[1].split('onclick=');
		var ItemName=name[0].substring(1, (name[0].length-2));
		var cmpStkArr = compStk.split('value=');
		var cmpStk=cmpStkArr[1].split('onclick=');
		var computerStock=cmpStk[0].substring(1, (cmpStk[0].length-2));
		var phyStkArr = phyStk.split('value=');
		var phyStock=phyStkArr[1].split('onclick=');
		var physicalStock=phyStock[0].substring(1, (phyStock[0].length-2));
		var itemCodeArr = itemCode.split('value=');
		var code=itemCodeArr[1].split('onclick=');
		var ItemCode=code[0].substring(1, (code[0].length-2));
		var variance=(physicalStock-computerStock);
		
		row.insertCell(0).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].strItemName\" readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"txtItemName."+(rowCount)+"\" value='"+ItemName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(1).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblCompStk\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"15%\" id=\"txtCompStock."+(rowCount)+"\" value='"+computerStock+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(2).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblPhyStk\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtPhyStock."+(rowCount)+"\" value='"+physicalStock+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(3).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblVariance\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtVariance."+(rowCount)+"\" value='"+variance+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(4).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblVairanceAmt\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtVarianceAmt."+(rowCount)+"\" value='"+0+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(5).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].strItemCode\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"0%\" id=\"txtItemCode."+(rowCount)+"\" value="+ItemCode+" onclick=\"funGetSelectedRowIndex(this)\"/>";		    
		
		  row = table.rows[rowCount+1];
		  row.style.backgroundColor='#ffd966';
		  selectedRowIndex=rowCount+1;
	}
	
	function funMoveRowUp(itemName,compStk,phyStk,itemCode,rowCount,itemName1,compStk1,phyStk1,itemCode1)
	{
		var table = document.getElementById("tblData");
	    table.deleteRow(rowCount);
	    var row = table.insertRow(rowCount-1);
	    row = table.rows[rowCount-1];
		
	    var nameArr = itemName.split('value=');
		var name=nameArr[1].split('onclick=');
		var ItemName=name[0].substring(1, (name[0].length-2));
		var cmpStkArr = compStk.split('value=');
		var cmpStk=cmpStkArr[1].split('onclick=');
		var computerStock=cmpStk[0].substring(1, (cmpStk[0].length-2));
		var phyStkArr = phyStk.split('value=');
		var phyStock=phyStkArr[1].split('onclick=');
		var physicalStock=phyStock[0].substring(1, (phyStock[0].length-2));
		var itemCodeArr = itemCode.split('value=');
		var code=itemCodeArr[1].split('onclick=');
		var ItemCode=code[0].substring(1, (code[0].length-2));
		var variance=(physicalStock-computerStock);
		
		row.insertCell(0).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].strItemName\" readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"txtItemName."+(rowCount)+"\" value='"+ItemName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(1).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblCompStk\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"15%\" id=\"txtCompStock."+(rowCount)+"\" value='"+computerStock+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(2).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblPhyStk\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtPhyStock."+(rowCount)+"\" value='"+physicalStock+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(3).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblVariance\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtVariance."+(rowCount)+"\" value='"+variance+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(4).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblVairanceAmt\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"11%\" id=\"txtVarianceAmt."+(rowCount)+"\" value='"+0+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    row.insertCell(5).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].strItemCode\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:100%\" size=\"0%\" id=\"txtItemCode."+(rowCount)+"\" value="+ItemCode+" onclick=\"funGetSelectedRowIndex(this)\"/>";		   
	    row = table.rows[rowCount-1];
		row.style.backgroundColor='#ffd966';
		selectedRowIndex=rowCount-1;
	}

	 //Get Project Path
	function getContextPath() 
	{
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	
	function funExportFile()
	{
		window.location.href=getContextPath()+"/PhysicalStockPostingExcelExport.html";
    }
	
	function funEnableImportButton()
	{
		if(funValidateFile())
		{
			document.getElementById("btnImport").style.display="block";
		}
	}
	
	
	
	function funValidateFile() 
	{	
		var value=$("#File").val();
		var Extension=value.split(".");
		var ext=Extension[1];
		if(ext=="xls" || ext =="xlsx" )
			{
			 return true;
			}
		else
		{
			alert("Invalid File");
			return false;
			
		}
	}
	
	
	//After Submit Button
	function funImportFile()
	{
		if(funValidateFile())
			{
				var jForm = new FormData();    
			    jForm.append("file", $('#File').get(0).files[0]);
			    searchUrl=getContextPath()+"/PhysicalStockExcelExportImport.html";	
		        $.ajax({
		           // url : $("#uploadExcel").attr('action'),
		            url : searchUrl,
		            type: "POST",
	                data: jForm,
	                mimeType: "multipart/form-data",
	                contentType: false,
	                cache: false,
	                processData: false,
	                dataType: "json",
		            success : function(response) 
		            {
		            	if(response[0]=="Invalid Excel File")
		            		{
		            			alert(response[1]);
		            		}
		            	else
		            		{
								$.each(response.listPSPDtl,function(i,item)
								{
									funAddRow(item.strItemName,item.strItemCode,item.dblPhyStk);
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
		        //funLoadData();
			}
	}
	
	function funLoadData()
	{
		document.getElementById("btnImport").style.display="none";
	}
	
	
		
</script>



</head>
<body onload="funLoadData()">
       
     <div id="formHeading">
		<label>Physical Stock Posting</label>
			</div>
		<br/>
<br/>


	<s:form name="Physical Stock Posting" method="POST" action="savePhysicalStock.html?saddr=${urlHits}" >
	   <div>
	   
	     <div style=" width: 50%; height: 500px;float:left;background-color: #a4d7ff; "> 
	     <br>
            <div style=" background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 400px; margin: auto; overflow-x: scroll; overflow-y: scroll; width: 90%;">
					<table id="tblData"
							style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
							class="transTablex col2-right col3-right col4-right col5-right">
							<tr >
						<td style="border: 1px  white solid;width:55%"><label>Description</label></td>
						<td style="border: 1px  white solid;width:15%"><label>Comp Stk</label></td>
						<td style="border: 1px  white solid;width:10%"><label>Phy Stk</label></td>
						<td style="border: 1px  white solid;width:10%"><label>Variance</label></td>
						<td style="border: 1px  white solid;width:10%"><label>Var Amt</label></td>
						<td style="border: 1px  white solid;width:0%"><label></label></td>	
						
					</tr>
					</table>
					
			</div>
			<table class=transFormTable>
					<tr>
						 <td>
							<label>Variance Value</label>
						</td> 
					
						<td colspan="4">
						    <s:label id="lblTotalSaleAmt" path="strItemName"/>
							<s:input type="hidden" id="txtTotalSaleAmt" path="strSaleAmt" />
						</td>
						<td colspan="4">
						    <s:label id="lblTotalStockOutAmt" path="strItemCode"/>
							<s:input type="hidden" id="txtTotalStockOutAmt" path="strStockOutAmt" />
						</td>
					</tr>
			     </table>
			     
			     <div>
			     
			       <p align="center">
			        <input id="btnUp" type="button" class="smallButton" value="Up" onclick="funMoveSelectedRow(1);"></input>
			        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        <input id="btnDown" type="button" class="smallButton" value="Down" onclick="funMoveSelectedRow(0);"></input>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="btnDelete" type="button" class="smallButton" value="Delete" onclick="funDeleteRow();"></input>
				   </p>
			     </div>
            
        </div>
		<div style=" width: 50%; height: 500px; float:right; border-collapse: separate; overflow-x: hidden; overflow-y: scroll; background-color: #C0E2FE;">
		    <br>
		   <table class=transFormTable>
			
			<tr>
				 <td>
					<label>Physical Stock No.</label>
					<s:label id="lblPhysicalStockNo" path="strPSPCode"/>
				    <s:input type="hidden" id="txtPhysicalStockNo" path="strPSPCode" />
				</td> 
			</tr>
			 
			 <tr>
				 <td >
					<label>Item Name</label>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    <s:input  type="text" id="txtItemName" path="strItemName" cssStyle="text-transform: uppercase;" cssClass="longTextBox jQKeyboard form-control"   ondblclick="funHelp('POSItemList')"/>
			    </td>
			</tr>
			<tr>
				 <td>
					<label>External Code</label>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    <s:input  type="text" id="txtExternalCode" path="strExternalCode" cssClass="BoxW124px" />
			    </td>
			</tr>
			
			 <tr>
			 <td>Quantity
			     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			     <s:input id="txtQty" path="dblQty"  required="true" cssStyle="text-transform: uppercase;" cssClass="BoxW116px" value="1" />
				 </td>
			</tr>
			
			 <tr>
				 <td >
				    <label>Enter Remark</label>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    <s:input  type="text" id="txtRemark" path="strRemark" cssClass="longTextBox jQKeyboard form-control"   required="true"/>
				</td>
			</tr>
			 
			 
			<tr>
			     <td>
			        <label>Select Reason</label>
			            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				        <s:select id="cmbReason" items="${reasonList}" name="cmbReason"  cssClass="BoxW124px" path="strReason">
						</s:select>
					<s:input type="hidden" id="txtItemCode" path="strItemCode" />
			        
			    </td>
			</tr>
				
			<tr><td ></td></tr>
			<tr>
			   <td>
			      <div style=" background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 50px; width: 50%;">
						<table id="tblCalculator"
							style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
							class="transTablex">
						<tr >
							<td style="border: 1px  white solid;width:25%"> <input id="btnSeven" type="button" class="smallButton" value="7" onclick="funSetQty(7);"></input></td>
							<td style="border: 1px  white solid;width:25%"><input id="btnEight" type="button" class="smallButton" value="8" onclick="funSetQty(8);"></input></td></td>
							<td style="border: 1px  white solid;width:25%"><input id="btnNine" type="button" class="smallButton" value="9" onclick="funSetQty(9);"></input></td></td>
						</tr>
						 <tr >
							<td style="border: 1px  white solid;width:25%"><input id="btnFour" type="button" class="smallButton" value="4" onclick="funSetQty(4);"></input></td></td>
							<td style="border: 1px  white solid;width:25%"><input id="btnFive" type="button" class="smallButton" value="5" onclick="funSetQty(5);"></input></td></td>
							<td style="border: 1px  white solid;width:25%"><input id="btnSix" type="button" class="smallButton" value="6" onclick="funSetQty(6);"></input></td></td>
						</tr>
						 <tr >
							<td style="border: 1px  white solid;width:25%"><input id="btnOne" type="button" class="smallButton" value="1" onclick="funSetQty(1);"></input></td></td>
							<td style="border: 1px  white solid;width:25%"><input id="btnTwo" type="button" class="smallButton" value="2" onclick="funSetQty(2);"></input></td></td>
							<td style="border: 1px  white solid;width:25%"><input id="btnThree" type="button" class="smallButton" value="3" onclick="funSetQty(3);"></input></td></td>
						</tr>
						<tr >
							<td style="border: 1px  white solid;width:25%"><input id="btnDot" type="button" class="smallButton" value="." onclick="funSetQty('dot');"></input></td></td>
							<td style="border: 1px  white solid;width:25%"><input id="btnZero" type="button" class="smallButton" value="0" onclick="funSetQty(0);"></input></td></td>
							<td style="border: 1px  white solid;width:25%"><input id="btnClear" type="button" class="smallButton" value="c" onclick="funSetQty('clear');"></input></td></td>
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
			  &nbsp;&nbsp;
			 <input id="btnExport" type="button" class="smallButton" value="Export" onclick="funExportFile();"></input>
			 <input type="file" id="File"  Width="5%" accept="application/vnd.ms-excel" onchange="funEnableImportButton();" >
			 </td>
			 <td>
			 <input id="btnImport" type="button" class="smallButton" value="Import" onclick="funImportFile();"></input>
			  &nbsp;&nbsp;
			</td>
			</tr>	
			<tr>
			<tr><td ></td></tr>
			<td>
			<br>
			 <input type="button" value="Modify" tabindex="3" class="form_button" ondblclick="funHelp('PhysicalStock')"/> 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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