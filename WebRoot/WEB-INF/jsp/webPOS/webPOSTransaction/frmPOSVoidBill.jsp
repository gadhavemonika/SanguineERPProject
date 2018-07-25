<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
 	var fieldName,textValue2="",selectedRowIndex=0,delTableNo="",delbillNo="",delItemcode="", delAmount="",bDate="",delModItemcode="";
 
 	
 	$(function() 
 	{
 	
 		funFillGrid();
 		
    
 		
 	});
 	

    $(document).ready(function ()
    {
       $("#btnShowSimple").click(function (e)
       {
          ShowDialog(false);
          e.preventDefault();
       });

       $("#btnShowModal").click(function (e)
       {
          ShowDialog(true);
          e.preventDefault();
       });

       $("#btnClose").click(function (e)
       {
          HideDialog();
          e.preventDefault();
       });

       $("#btnSubmit").click(function (e)
       {
          var brand = $("#brands input:radio:checked").val();
          $("#output").html("<b>Your favorite mobile brand: </b>" + brand);
          HideDialog();
          e.preventDefault();
       });

    });

    function ShowDialog(modal)
    {
       $("#overlay").show();
       $("#dialog").fadeIn(300);

       if (modal)
       {
          $("#overlay").unbind("click");
       }
       else
       {
          $("#overlay").click(function (e)
          {
             HideDialog();
          });
       }
    }

    function HideDialog()
    {
       $("#overlay").hide();
       $("#dialog").fadeOut(300);
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
				  var description=table.rows[selectedRowIndex].cells[0].innerHTML;
				  var qty=table.rows[selectedRowIndex].cells[1].innerHTML; 
				  var amt=table.rows[selectedRowIndex].cells[2].innerHTML; 
				  var itemCode=table.rows[selectedRowIndex].cells[3].innerHTML;
				  var description1=table.rows[selectedRowIndex-1].cells[0].innerHTML;
				  var qty1=table.rows[selectedRowIndex-1].cells[1].innerHTML; 
				  var amt1=table.rows[selectedRowIndex-1].cells[2].innerHTML; 
				  var itemCode1=table.rows[selectedRowIndex-1].cells[3].innerHTML;
				  var kot=table.rows[selectedRowIndex].cells[4].innerHTML;
				  var kot1=table.rows[selectedRowIndex-1].cells[4].innerHTML;
				  
				  funMoveRowUp(description,qty,amt,itemCode,kot,selectedRowIndex,description1,qty1,amt1,itemCode1,kot1);
				}
				
			}
			else
			{
				var table = document.getElementById("tblData");
				var rowCount = table.rows.length;
				if(rowCount>0)
				{
					  var table = document.getElementById("tblData");
					  var description=table.rows[selectedRowIndex].cells[0].innerHTML;
					  var qty=table.rows[selectedRowIndex].cells[1].innerHTML; 
					  var amt=table.rows[selectedRowIndex].cells[2].innerHTML; 
					  var itemCode=table.rows[selectedRowIndex].cells[3].innerHTML;
					  var description1=table.rows[selectedRowIndex-1].cells[0].innerHTML;
					  var qty1=table.rows[selectedRowIndex-1].cells[1].innerHTML; 
					  var amt1=table.rows[selectedRowIndex-1].cells[2].innerHTML; 
					  var itemCode1=table.rows[selectedRowIndex-1].cells[3].innerHTML;
					  var kot=table.rows[selectedRowIndex].cells[4].innerHTML;
					  var kot1=table.rows[selectedRowIndex-1].cells[4].innerHTML;
					
					  funMoveRowDown(description,qty,amt,itemCode,kot,selectedRowIndex,description1,qty1,amt1,itemCode1,kot1);
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
	
	
	function funMoveRowDown(description,qty,amt,itemCode,kot,rowCount,description1,qty1,amt1,itemCode1,kot1)
	{
		var table = document.getElementById("tblData");
	    table.deleteRow(rowCount);
	    var row = table.insertRow(rowCount+1);
	    row = table.rows[rowCount+1];
		
		var nameArr = description.split('value=');
		var name=nameArr[1].split('onclick=');
		var Description=name[0].substring(1, (name[0].length-2));
		var qtyArr = qty.split('value=');
		var Qty=qtyArr[1].split('onclick=');
		var quantity=Qty[0].substring(1, (Qty[0].length-2));
		var amtArr = amt.split('value=');
		var amtStock=amtArr[1].split('onclick=');
		var Amount=amtStock[0].substring(1, (amtStock[0].length-2));
		var itemCodeArr = itemCode.split('value=');
		var code=itemCodeArr[1].split('onclick=');
		var ItemCode=code[0].substring(1, (code[0].length-2));
		
		var kotArr = kot.split('value=');
		var kotcode=kotArr[1].split('onclick=');
		var kotNo=kotcode[0].substring(1, (kotcode[0].length-2));
		if(document.all("cbSGSel."+rowCount).checked==true)
		{
			
		}
		
		row.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\""+Description+"\" value='"+Description+"'  onclick=\"funGetSelectedRowIndex(this)\"/>";
		row.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+quantity+"\" value='"+quantity+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		row.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+Amount+"\" value='"+Amount+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		row.insertCell(3).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+ItemCode+"\" value='"+ItemCode+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		row.insertCell(4).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+kotNo+"\" value='"+kotNo+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		
		
		
		
	
		  row = table.rows[rowCount+1];
		  row.style.backgroundColor='#ffd966';
		  selectedRowIndex=rowCount+1;
		  
		
		var nextNameArr = description1.split('value=');
		var nextName=nextNameArr[1].split('onclick=');
		var nextDescription=nextName[0].substring(1, (nextName[0].length-2));
		var nextQtyArr = qty1.split('value=');
		var nextQty=nextQtyArr[1].split('onclick=');
		var nextquantity=nextQty[0].substring(1, (nextQty[0].length-2));
		var nextAmtArr = amt1.split('value=');
		var nextAmtStock=nextAmtArr[1].split('onclick=');
		var nextAmount=nextAmtStock[0].substring(1, (nextAmtStock[0].length-2));
		var nextItemCodeArr = itemCode1.split('value=');
		var nextCode=nextItemCodeArr[1].split('onclick=');
		var nextItemCode=nextCode[0].substring(1, (nextCode[0].length-2));
		
		var nextkotArr = kot1.split('value=');
		var nextkot=nextkotArr[1].split('onclick=');
		var nextkotNo=nextkot[0].substring(1, (nextkot[0].length-2));
		var row1 = table.insertRow(rowCount);
		
		if(document.all("cbSGSel."+rowCount).checked==true)
		{
			
		}
		row1.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\""+nextDescription+"\" value='"+nextDescription+"'  onclick=\"funGetSelectedRowIndex(this)\"/>";
		row1.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+nextquantity+"\" value='"+nextquantity+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		row1.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+nextAmount+"\" value='"+nextAmount+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		row1.insertCell(3).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+nextItemCode+"\" value='"+nextItemCode+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		row.insertCell(4).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+nextkotNo+"\" value='"+nextkotNo+"' onclick=\"funGetSelectedRowIndex(this)\"/>";	    
		table.deleteRow(rowCount+1);
		  
		  
	}
	
    function funMoveRowUp(description,qty,amt,itemCode,kot,rowCount,description1,qty1,amt1,itemCode1,kot1)
	{
		var table = document.getElementById("tblData");
	    table.deleteRow(rowCount);
	    var row = table.insertRow(rowCount-1);
	    row = table.rows[rowCount-1];
		
		var nameArr = description.split('value=');
		var name=nameArr[1].split('onclick=');
		var Description=name[0].substring(1, (name[0].length-2));
		var qtyArr = qty.split('value=');
		var Qty=qtyArr[1].split('onclick=');
		var quantity=Qty[0].substring(1, (Qty[0].length-2));
		var amtArr = amt.split('value=');
		var amtStock=amtArr[1].split('onclick=');
		var Amount=amtStock[0].substring(1, (amtStock[0].length-2));
		var itemCodeArr = itemCode.split('value=');
		var code=itemCodeArr[1].split('onclick=');
		var ItemCode=code[0].substring(1, (code[0].length-2));
		var kotArr = kot.split('value=');
		var kotcode=kotArr[1].split('onclick=');
		var kotNo=kotcode[0].substring(1, (kotcode[0].length-2));
		

		row.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\""+Description+"\" value='"+Description+"'  onclick=\"funGetSelectedRowIndex(this)\"/>";
		row.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+quantity+"\" value='"+quantity+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		row.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+Amount+"\" value='"+Amount+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		row.insertCell(3).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+ItemCode+"\" value='"+ItemCode+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		row.insertCell(4).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+kotNo+"\" value='"+kotNo+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		row.insertCell(5).innerHTML= "<input id=\"cbSGSel."+(rowCount)+"\" type=\"checkbox\" checked=\""+checkBox+"\" name=\"BillGroupthemes\" value='"+rowData[3]+"' class=\"SGCheckBoxClass\" />";
			   
	    row = table.rows[rowCount-1];
		row.style.backgroundColor='#ffd966';
		selectedRowIndex=rowCount-1;
		    
		var nextNameArr = description1.split('value=');
		var nextName=nextNameArr[1].split('onclick=');
		var nextDescription=nextName[0].substring(1, (nextName[0].length-2));
		var nextQtyArr = qty1.split('value=');
		var nextQty=nextQtyArr[1].split('onclick=');
		var nextquantity=nextQty[0].substring(1, (nextQty[0].length-2));
		var nextAmtArr = amt1.split('value=');
		var nextAmtStock=nextAmtArr[1].split('onclick=');
		var nextAmount=nextAmtStock[0].substring(1, (nextAmtStock[0].length-2));
		var nextItemCodeArr = itemCode1.split('value=');
		var nextCode=nextItemCodeArr[1].split('onclick=');
		var nextItemCode=nextCode[0].substring(1, (nextCode[0].length-2)); 
		var nextkotArr = kot1.split('value=');
		var nextkot=nextkotArr[1].split('onclick=');
		var nextkotNo=nextkot[0].substring(1, (nextkot[0].length-2));
		var row1 = table.insertRow(rowCount+1);
		 
		row1.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\""+nextDescription+"\" value='"+nextDescription+"'  onclick=\"funGetSelectedRowIndex(this)\"/>";
		row1.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+nextquantity+"\" value='"+nextquantity+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		row1.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+nextAmount+"\" value='"+nextAmount+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		row1.insertCell(3).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+nextItemCode+"\" value='"+nextItemCode+"' onclick=\"funGetSelectedRowIndex(this)\"/>";		    
		row1.insertCell(4).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+nextkotNo+"\" value='"+nextkotNo+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		row1.insertCell(5).innerHTML= "<input id=\"cbSGSel."+(rowCount)+"\" type=\"checkbox\" checked=\""+checkBox+"\" name=\"BillGroupthemes\" value='"+rowData[3]+"' class=\"SGCheckBoxClass\" />";
		table.deleteRow(rowCount);
	}

	
	function funFillGrid()
	{
	    var searchUrl="";
	    var tableName="";
	   
	    
	    searchUrl=getContextPath()+"/fillBillGridData.html?";
		$.ajax({
		        type: "GET",
		        url: searchUrl,
		        async:false,
		        data:"tableName="+tableName,
			    success: function(response)
			    {
			    	$.each(response, function(i,item)
					{
			    		funAddFullRow(response);
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
	
	function funAddFullRow(data){
		$('#tblDataFillGrid tbody').empty()
		var table = document.getElementById("tblDataFillGrid");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		row.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value=Bill No >";
		row.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"TB Name\" value=BillDate >";
		row.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"Waiter Name\" value=Amount >";
		row.insertCell(3).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"Take Away\" value=TableName >";
		
		rowCount++;
	    for(var i=0;i<data.length;i++){
	    	row = table.insertRow(rowCount);
	    	var rowData=data[i];
	    	
	    	for(var j=0;j<rowData.length;j++){
	    		
	    		row.insertCell(j).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+rowData[j]+"\" value='"+rowData[j]+"' onclick=\"funGetSelectedRowData(this)\"/>";
	    	}
	    	rowCount++;
	    }
		
// 	  	$("#lblBillNo").text(billNo);
    	
//     	$("#lblTax").text(taxAmt);
//     	$("#lblSubTotlal").text(subTotalAmt);
//     	$("#lblTotal").text(totalAmount);
		
	}
	
	
	
	
     function funGetSelectedRowData(obj)
     {
    	
    	var index = obj.parentNode.parentNode.rowIndex;
    	var tableName = document.getElementById("tblDataFillGrid");
       	var dataBilNo= tableName.rows[index].cells[0].innerHTML; 
        var btnBackground=dataBilNo.split('value=');
        var billData=btnBackground[1].split("onclick");
        var bill=billData[0].substring(1, (billData[0].length-2));
    	
        var dataTableName= tableName.rows[index].cells[3].innerHTML; 
        var btnBackgroundTableName=dataTableName.split('value=');
        var tabledata=btnBackgroundTableName[1].split("onclick");
        delTableNo=tabledata[0].substring(1, (tabledata[0].length-2));
     delbillNo=bill
        $("#lblBillNo").text(bill);
        searchUrl=getContextPath()+"/fillBillDtlData.html?";
		$.ajax({
		        type: "GET",
		        url: searchUrl,
		        async:false,
		        data:"billNo="+bill,
			    success: function(response)
			    {
					
			    	$.each(response, function(i,item)
					{
	
			    		funAddFullRowBillDtl(response.listFillGrid,bill,response.totalAmount,response.taxAmt,response.userCreated,response.subTotalAmt);
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
     
     function funAddFullRowBillDtl(data,bill,totalAmount,taxAmt,userCreated,subTotalAmt){
 		$('#tblData tbody').empty()
 		var table = document.getElementById("tblData");
 		var rowCount = table.rows.length;
 		var row = table.insertRow(rowCount);
 		row.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value=Description>";
 		row.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"TB Name\" value=BillDate >";
 		row.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"Waiter Name\" value=Amount >";
 		row.insertCell(3).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"Take Away\" value=Modifier >";
 		row.insertCell(4).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"Take Away\" value=KOT NO >";
//  		row.insertCell(5).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"\" value=Select >";
 		rowCount++;
 	    for(var i=0;i<data.length;i++){
 	    	row = table.insertRow(rowCount);
 	    	var rowData=data[i];
 	    	
 	    	for(var j=0;j<rowData.length;j++){
 	    		
 	    		row.insertCell(j).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+rowData[j]+"\" value='"+rowData[j]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";

 	    		 
 	    	}
//  	    	row.insertCell(5).innerHTML= "<input id=\"cbSGSel."+(rowCount)+"\" type=\"checkbox\" checked=\"checked\" name=\"BillGroupthemes\" value='"+rowData[3]+"' class=\"SGCheckBoxClass\" />";
 	    	rowCount++;
 	    }
	  	$("#lblBillNo").text(bill);
	  	$("#lblUserCreated").text(userCreated);
    	$("#lblTax").text(taxAmt);
    	$("#lblSubTotlal").text(subTotalAmt);
    	$("#lblTotal").text(totalAmount);
 		
 		
 		
 	}
     


 	function funDeleteRow()
	{
	    var taxAmt=$("#lblTax").text();
	  
	
	    var table = document.getElementById("tblData");
	    var delItemName="";
	    var count=$('#tblData tr').length-1;
	    var delRowNo="";
	    var i=selectedRowIndex;
// 	    for(var i=1;i<=count;i++)
// 	    {
//          var a="";
//         if(document.all("cbSGSel."+i).checked==true)
//         	{
//         	if(delRowNo=="")
//         	{
//         		delRowNo="del"+i;String userCode,String posDate,String selectedReasonDesc,String remark,String itemName,String tempItemCode, 
//      			   String billNo ,String itemCodeForVoid,String modItemCode,String itemCode,String StrPOSCode,double totalBillQty,String strClientCode,String tableNo)
//         	}else{
        		delRowNo=delRowNo+"del"+i;
//         	}
        	var tableName = document.getElementById("tblData");
	       	var itemcode= tableName.rows[i].cells[3].innerHTML; 
	       	
	        var btnBackground=itemcode.split('value=');
	        var iCode=btnBackground[1].split("onclick");
	        delItemcode=iCode[0].substring(1, (iCode[0].length-2));
	        
   	var modItemcode= tableName.rows[i].cells[4].innerHTML; 
	       	
	        var btnBackmodItemcode=modItemcode.split('value=');
	        var mCode=btnBackmodItemcode[1].split("onclick");
	        delModItemcode=mCode[0].substring(1, (mCode[0].length-2));
	        
  	var itemName= tableName.rows[i].cells[0].innerHTML; 
	       	
	        var btnBackgrounditemName=itemName.split('value=');
	        var iName=btnBackgrounditemName[1].split("onclick");
	        delItemName=iName[0].substring(1, (iName[0].length-2));
	        
	        
   	var amount= tableName.rows[i].cells[2].innerHTML; 
	       	
	        var btnBackgroundamount=amount.split('value=');
	        var amountnext=btnBackgroundamount[1].split("onclick");
	        delAmount=amountnext[0].substring(1, (amountnext[0].length-2));
	        
   	var qty= tableName.rows[i].cells[1].innerHTML; 
	       	
	        var btnBackgroundqty=qty.split('value=');
	        var delQunatity=btnBackgroundqty[1].split("onclick");
	         var quantity=delQunatity[0].substring(1, (delQunatity[0].length-2));

//         	}
// 	    }
// 	   var delreow= delRowNo.split("del");
	  
// 	   for(var i=1;i<delreow.length;i++) {
		   
		   
		   table.deleteRow(selectedRowIndex);
// 	   }
	   var remarks = prompt("Enter Remarks", "");
    	var reasonCode=$("#cmbDocType").val();
      
    	searchUrl=getContextPath()+"/voidItem.html?";
		$.ajax({
		        type: "GET",
		        url: searchUrl,
		        async:false,
		        data:"delItemcode="+delItemcode+"&delbillNo="+delbillNo+"&delTableNo="+delTableNo+"&remarks="+remarks+
		        "&reasonCode="+reasonCode+"&quantity="+quantity+"&delAmount="+delAmount+"&delItemName="+delItemName+"&taxAmt="+taxAmt+"&delModItemcode="+delModItemcode,
		        
			    success: function(response)
			    {
					if(response)
						{
					alert("Void Kot SucessFully");
						}},
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
 	
 	function funFullVoidBill()
 	{
 		var billNo=$("#lblBillNo");
 	   var remarks = prompt("Enter Remarks", "");
   	   var reasonCode=$("#cmbDocType").val();
   	
   	searchUrl=getContextPath()+"/fullVoidBillButtonClick.html?";
	$.ajax({
	        type: "GET",
	        url: searchUrl,
	        async:false,
	        data:"billNo="+delbillNo+"&remarks="+remarks+
	        "&reasonCode="+reasonCode,
	        
		    success: function(response)
		    {
		    	funNextFillGrid();
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
     

 	function funNextFillGrid()
 	{
 		$('#tblData tbody').empty()

 		var table = document.getElementById("tblData");
 		var rowCount = table.rows.length;

 		var row = table.insertRow(rowCount);
 		row.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\"Description\" value=Description >";
 		row.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"Quantity\" value=Qty >";
 		row.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"Amount\" value= Amount>";
 		row.insertCell(3).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"Item Code\" value=Item Code >";
 		row.insertCell(4).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"\" value=Select >";
 	    
 		funFillGrid();
 	}
   
</script>



</head>
<body>
       
     <div id="formHeading">
		<label>POS Void Bill </label>
			</div>
		<br/>
<br/>-


	<s:form name="Void Bill" method="POST" action="" >
	   <div>
	   <div>
	   <table>
	 <tr> <td>
					<label>Bill No.</label>
					&nbsp;&nbsp;
					<label id="lblBillNo" />
					

			    </td>
			    <td>
					<label>User </label>
					&nbsp;&nbsp;<label id="lblUserCreated"  />

			    </td>
			    </tr>
			    <tr>  
			 
			 <td>
			 	&nbsp;&nbsp;<label>Reson</label></td>
			    <td>

				 <s:select path="strReson" items="${listReson}"
							id="cmbDocType"  cssClass="longTextBox" cssStyle="width:300px"></s:select>

				    	
			    
			    </td>
			    </tr>
			    </table>
	   </div>
	     <div style=" width: 50%; height: 500px;float:left;background-color: #a4d7ff; "> 
	     <br>
            <div style=" background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 400px; margin: auto; overflow-x: scroll; overflow-y: scroll; width: 90%;">
					<table id="tblData"
							style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
							class="transTablex col2-right col3-right col4-right col5-right">
							<tr >
						<td style="border: 1px  white solid;width:55%"><label>Description</label></td>
						<td style="border: 1px  white solid;width:15%"><label>Qty</label></td>
						<td style="border: 1px  white solid;width:10%"><label>Amount</label></td>
						<td style="border: 1px  white solid;width:10%"><label>Select</label></td>
						
						
					</tr>
					</table>
					
			</div>
			<table class=transFormTable >
					
					<tr>
					<td colspan="1"></td>
					<td colspan="1"></td>
					<td colspan="1"></td>
					<td colspan="1"></td>
					<td colspan="1"></td>
					<td colspan="1"></td>
						 <td >
							<label>SubTotal</label>
						 <td >	<label id="lblSubTotlal"/></td>
						</td>
					</tr>
						<tr >
						<td colspan="1"></td>
					
					<td colspan="1"></td>
					<td colspan="1"></td>
					<td colspan="1"></td>
					<td colspan="1"></td>
					<td colspan="1"></td>
						 <td colspan="1" >
							<label >Tax</label>
						</td>
							<td><label id="lblTax" />
						</td>
					</tr>
					
			     </table>
			     
			     <div>
			     <table class=transFormTable margin-left: auto; margin-right: auto;width: 70%;font-size:11px;
							font-weight: bold;">
			       <tr><td >
			        <input id="btnUp" type="button" class="smallButton" value="Up" onclick="funMoveSelectedRow(1);" ></input></td>
			       
			        <td><input id="btnDown" type="button" class="smallButton" value="Down" onclick="funMoveSelectedRow(0);"></input></td>
			         <td>
							<label >Total</label></td>
						<td><label id=lblTotal></label></td>
						
			        </tr>
					
					<tr><td><input id="btnDelete" type="button" class="smallButton" value="Item Void" onclick="funDeleteRow();"></input></td>
					<td><input id="btnDone type="button" class="smallButton"   value="FullVoidKot" onclick="funFullVoidBill();"></input></td>
				   </tr>

				   </table>
			     </div>
            
        </div>
		<div style=" width: 50%; height: 500px; float:right; border-collapse: separate; overflow-x: hidden; overflow-y: scroll; background-color: #C0E2FE;">
		    <br>
		   <table class=transFormTable>
			
			<tr>
				 
			
				<td colspan="4">
			
					
				    <input type="text" class="menusearchTextBox" id="txtSearch" style="margin-top: 20px; height: 28px;width: 218px;" ng-model="searchKeyword"></input>
		       
				</td>
			</tr>
			</table>
			<tr>
			
			
			   <div style=" background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 400px; margin: auto; overflow-x: scroll; overflow-y: scroll; width: 90%;">
					<table id="tblDataFillGrid"
							style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
							class="transTablex col2-right col3-right col4-right col5-right">
							<tr >

					</tr>
					</table>
				
					
			</div>
			
			</tr>
			
			<br>
		</div>
		
	   </div>

        
		<br>
	  <br>
	
		
	</s:form> 

    
<br /><br />       


 
</body>
</html>