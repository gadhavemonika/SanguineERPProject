<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reprint Documents</title>
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

<script type="text/javascript">
var operationType="";
var clickedBtn="";
var btnSelected="";
$(document).ready(function() 
{
	document.all[ 'divKOTAndDina' ].style.display = 'block';
	var posName="${posName}";
	
	if(clickedBtn=="")
	{
	document.all[posName].style.backgroundColor = "#595959";
	clickedBtn=posName;
	}
	else if(clickedBtn==posName)
	{
         $(this).css('background-color', '#6FB9F6');
         clickBtn="";
    } 
	else 
	{
	document.all[clickedBtn].style.backgroundColor = "#6FB9F6";
	$(this).css('background-color', '#595959');
     clickedBtn=posName;
    }  
	
	var operationType;
	var operationType="Bill";
	
	
	if(btnSelected=="")
	{
	document.all[operationType].style.backgroundColor = "#595959";
	btnSelected=operationType;
	}
	else if(btnSelected==operationType)
	{
         $(this).css('background-color', '#6FB9F6');
         btnSelected="";
    } 
	else 
	{
	document.all[btnSelected].style.backgroundColor = "#6FB9F6";
	$(this).css('background-color', '#595959');
	btnSelected=operationType;
    }  
	funOnLoad(operationType);
	
	
	    $('.transForm_button').click(function() 
    {
    	
     	
    	btnId = this.id;
		
    	if((btnId=="KOT")||(btnId=="Bill")||(btnId=="DayEnd")||(btnId=="Dina")||(btnId=="DirectBiller"))
    	{	
    	if(btnSelected=="")
    	{
    		$(this).css('background-color', '#595959');
    		btnSelected=btnId;
    	}
    	
    	else if(btnSelected==btnId)
    	{
             $(this).css('background-color', '#6FB9F6');
             btnSelected="";
        } 
    	else 
    	{
			document.all[btnSelected].style.backgroundColor = "#6FB9F6";
            $(this).css('background-color', '#595959');
            btnSelected=btnId;
        }  
    	var operationType;
    	operationType=$(this).val();
    	
    	if(operationType=="DayEnd")
    	{
    		document.all[ 'divBill' ].style.display = 'none';
    		document.all[ 'divDirectBiller' ].style.display = 'none';
    		document.all[ 'divKOTAndDina' ].style.display = 'none';
    		document.all[ 'divDayEnd' ].style.display = 'block';
    		
    		$("#divDayEnd").datepicker({ dateFormat: 'yy-mm-dd' });
    		$("#divDayEnd" ).datepicker('setDate', 'today');
    		$("#divDayEnd").datepicker();
    		
    	}
    	funOnLoad(operationType);
    	}
    	else
    	{
    		if(clickedBtn=="")
        	{
        		$(this).css('background-color', '#595959');
        		clickedBtn=btnId;
        	}
        	
        	else if(clickedBtn==btnId)
        	{
                 $(this).css('background-color', '#6FB9F6');
                 clickBtn="";
            } 
        	else 
        	{
    			document.all[clickedBtn].style.backgroundColor = "#6FB9F6";
                $(this).css('background-color', '#595959');
             	clickedBtn=btnId;
            }  
    	}	
    });
    
    $('.divDayEnd').click(function() 
    {
    	document.all[ 'divDayEnd' ].style.display = 'block';
    });
    
    $('#form_button').click(function() 
    {
    	
    });
   
   
});
/**
* Open Help
**/
function funHelp(code,transactionType,kotFor)
{	       
   // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
   window.open("frmViewData.html?code="+code+"&transactionType="+transactionType+"&kotFor="+kotFor+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
//    window.open("frmViewData.html", 'window', 'width=200,height=100');
}

function funGetSelectedRowIndex(obj,tableId)
{
	var code="";
	if(operationType=="DayEnd")
	{
		transactionType="DayEnd";
		code=$("#divDayEnd").val();
		kotFor="DayEnd";
	}
	else
	{	
	var tableName = document.getElementById(tableId);
	var index = obj.parentNode.parentNode.rowIndex;
	 var cellIndex=obj.parentNode.cellIndex;
	var tblname=tableName.rows[index].cells[cellIndex].innerHTML;
	var btnClassName=tblname.split('class="');
	var btnBackground=btnClassName[1].split('value=');
	var btnType=btnBackground[0].substring(0, (btnBackground[0].length-2));
    var data=btnBackground[1].split('onclick=');
	 code=data[0].substring(1, (data[0].length-2));
	var selectedTableName=data[1].split('this,');
	
	
	if(tableId=="tblKOTAndDinaDtl")
	{
		transactionType="KOT";
		kotFor="Dina";
	}	
	else if(tableId=="tblDirectBillerDtl")
	{
		transactionType="KOT";
		kotFor="DirectBiller";	
	}	
	else if(tableId=="tblBillDtl")
	{
		transactionType="Bill";
		kotFor="Bill";
	}	
	}
 	//tableName.rows[index].cells[cellIndex].innerHTML="<input type=\"text\"  class=\"transForm_redbutton \" value='"+code+"' />";
// 	var previousKOT="";
	//funViewButtonPressed(code,transactionType,kotFor);
	funHelp(code,transactionType,kotFor);
}

function funViewButtonPressed(code,transactionType,kotFor)
{
	var searchurl=getContextPath()+"/funViewButtonPressed.html";
	 $.ajax({
	        type: "GET",
	        data:{ transactionType : transactionType,
	        	kotFor : kotFor,
	        	code : code,
			},
	        url: searchurl,
	        dataType: "json",
	        async: false,
	        success: function(response)
	        {
	        	
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

function funOnLoad(oprType)
{
 	var oprBtnClicked="";
 	// 	kotFor=$(this).val();
	//funRemoveTableRows("");
	 operationType = oprType;
	if((operationType=="KOT"))
	{
		kotFor="Dina";
	}
	else if(operationType=="Dina")
	{
		operationType="KOT";
		kotFor="Dina";
	}
	else if(operationType=="DirectBiller")
	{
		operationType="KOT";
		kotFor="DirectBiller";	
	}
// 	else if(operationType=="DayEnd")
// 	{
// 		document.all[ 'divBill' ].style.display = 'none';
// 		document.all[ 'divDirectBiller' ].style.display = 'none';
// 		document.all[ 'divKOTAndDina' ].style.display = 'none';
// 		document.all[ 'divDayEnd' ].style.display = 'block';
		
// 		$("#divDayEnd").datepicker({ dateFormat: 'yy-mm-dd' });
// 		$("#divDayEnd" ).datepicker('setDate', 'today');
// 		$("#divDayEnd").datepicker();
		
// 	}
	else if(operationType=="Bill")
	{
		kotFor="Bill";
		operationType="Bill";
	  
         
	}
	else
	{
		operationType="DayEnd";
		document.all[ 'divDayEnd' ].style.display = 'block';
	}
	funForAllTableDtl(operationType,kotFor);
}	
function funForAllTableDtl(operationType,kotFor)
{
	var searchurl=getContextPath()+"/loadFunExecute.html";
	 $.ajax({
	        type: "GET",
	        data:{ operationType : operationType,
	        	kotFor : kotFor,
			},
	        url: searchurl,
	        dataType: "json",
	        async: false,
	        success: function(response)
	        {
	        	funRemoveTableRows("tblKOTAndDinaDtl");
	        	funRemoveTableRows("tblDirectBillerDtl");
	        	funRemoveTableRows("tblBillDtl");
	        	
	        	oprType=response.strOpr;
	        	if((oprType == "KOT")||(oprType == "Dina"))
	        	{
	        		document.all[ 'divBill' ].style.display = 'none';
	        		document.all[ 'divDirectBiller' ].style.display = 'none';
	        		document.all[ 'divKOTAndDina' ].style.display = 'block';
	        		document.all[ 'divDayEnd' ].style.display = 'none';
	        		
	        	 $.each(response.AllTblData, function(i,item)
	 					{			
	        		 	funFillTableForKOTAndDina(item.KOTNo,item.Time,item.WaiterName,item.TableName,item.PaxNo,item.UserCreated,item.Amount);
	 			   
	 				  	});
	        	}
	        	else if(oprType == "DirectBiller")
	        	{
	        		document.all[ 'divBill' ].style.display = 'none';
	        		document.all[ 'divDirectBiller' ].style.display = 'block';
	        		document.all[ 'divKOTAndDina' ].style.display = 'none';
	        		document.all[ 'divDayEnd' ].style.display = 'none';
	        		
	        		 $.each(response.AllTblData, function(i,item)
	 	 					{	
	        		funFillTableForDirectBiller(item.BillNo,item.Time,item.POS,item.TotalAmount);
	 	 					}); 
	        	}
	        	else
	        	{
	        		document.all[ 'divBill' ].style.display = 'block';
	        		document.all[ 'divDirectBiller' ].style.display = 'none';
	        		document.all[ 'divKOTAndDina' ].style.display = 'none';
	        		document.all[ 'divDayEnd' ].style.display = 'none';
	        		
	        		 $.each(response.AllTblData, function(i,item)
		 	 					{	
	        		funFillTableForBill(item.BillNo,item.TableName,item.Time,item.TotalAmount);
		 	 					}); 
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

function funFillTableForKOTAndDina(KOTNo,Time,WaiterName,TableName,PaxNo,UserCreated,Amount)
{
	var table = document.getElementById("tblKOTAndDinaDtl");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

      row.insertCell(0).innerHTML= "<input name=\"listForTableDtl["+(rowCount)+"].KOTNo\" readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtKOTNo."+(rowCount)+"\" value='"+KOTNo+"' onclick=\"funGetSelectedRowIndex(this,'tblKOTAndDinaDtl')\" >";
	  row.insertCell(1).innerHTML= "<input name=\"listForTableDtl["+(rowCount)+"].Time\" readonly=\"readonly\" class=\"Box \" size=\"7%\" id=\"txtTime."+(rowCount)+"\" value='"+Time+"' >";
	  row.insertCell(2).innerHTML= "<input name=\"listForTableDtl["+(rowCount)+"].WaiterName\" readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtWaiterName."+(rowCount)+"\" value='"+WaiterName+"' >";
	  row.insertCell(3).innerHTML= "<input name=\"listForTableDtl["+(rowCount)+"].TableName\" readonly=\"readonly\" class=\"Box \" size=\"7%\" id=\"txtTableName."+(rowCount)+"\" value='"+TableName+"' >";	  
	  row.insertCell(4).innerHTML= "<input name=\"listForTableDtl["+(rowCount)+"].PaxNo\" readonly=\"readonly\" class=\"Box \" size=\"2%\" id=\"txtPaxNo."+(rowCount)+"\" value='"+PaxNo+"' >";
	  row.insertCell(5).innerHTML= "<input name=\"listForTableDtl["+(rowCount)+"].UserCreated\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtUserCreated."+(rowCount)+"\" value='"+UserCreated+"' >";
	  row.insertCell(6).innerHTML= "<input name=\"listForTableDtl["+(rowCount)+"].Amount\" readonly=\"readonly\" class=\"Box \" size=\"7%\" id=\"txtAmount."+(rowCount)+"\" value='"+Amount+"' >";
	  
}

function funFillTableForDirectBiller(BillNo,Time,POS,TotalAmount)
{
	var table = document.getElementById("tblDirectBillerDtl");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

      row.insertCell(0).innerHTML= "<input name=\"listForTableDtl["+(rowCount)+"].BillNo\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtBillNo."+(rowCount)+"\" value='"+BillNo+"' onclick=\"funGetSelectedRowIndex(this,'tblDirectBillerDtl')\">";
	  row.insertCell(1).innerHTML= "<input name=\"listForTableDtl["+(rowCount)+"].Time\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtTime."+(rowCount)+"\" value='"+Time+"'>";
	  row.insertCell(2).innerHTML= "<input name=\"listForTableDtl["+(rowCount)+"].POS\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtPOS."+(rowCount)+"\" value='"+POS+"'>";
	  row.insertCell(3).innerHTML= "<input name=\"listForTableDtl["+(rowCount)+"].TotalAmount\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtTotalAmount."+(rowCount)+"\" value='"+TotalAmount+"'>";	  
	  
}

function funFillTableForBill(BillNo,TableName,Time,TotalAmount)
{
	var table = document.getElementById("tblBillDtl");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

      row.insertCell(0).innerHTML= "<input name=\"listForTableDtl["+(rowCount)+"].BillNo\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtBillNo."+(rowCount)+"\" value='"+BillNo+"' onclick=\"funGetSelectedRowIndex(this,'tblBillDtl')\">";
	  row.insertCell(1).innerHTML= "<input name=\"listForTableDtl["+(rowCount)+"].TableName\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtTableName."+(rowCount)+"\" value='"+TableName+"'>";
	  row.insertCell(2).innerHTML= "<input name=\"listForTableDtl["+(rowCount)+"].Time\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtTime."+(rowCount)+"\" value='"+Time+"'>";
	  row.insertCell(3).innerHTML= "<input name=\"listForTableDtl["+(rowCount)+"].TotalAmount\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtTotalAmount."+(rowCount)+"\" value='"+TotalAmount+"'>";	  
	  
}
function funRemoveTableRows(tableId)
{
	var table = document.getElementById(tableId);
	var rowCount = table.rows.length;
	while(rowCount>0)
	{
		table.deleteRow(0);
		rowCount--;
	}
}
</script>
</head>
<body>

	<div id="formHeading">
		<label>Reprint Documents</label>
	</div>
	<s:form name="ReprintDocuments" method="GET"	action="frmViewData.html?saddr=${urlHits}" target="_blank">
	<br>
	<br>

	<div style="width:29%;height:600px;margin:auto;float:left">
	<div style="margin-left: 30px;width:100%;height:100px;float:left;padding: 2px;">
	<c:forEach items="${posList}" var="posList1">
	
		<input type="button" id="${posList1}" value="${posList1}" tabindex="3" class="transForm_button " style="margin-bottom:2px" onclick=""/>	
		
	</c:forEach>
	<br><br><br><br/>
	</div>
	
	<div style="margin-left: 30px;width:100%;height:400px;float:left;padding: 2px;">
	<br><br><br><br>
	<input type="button" id="KOT" name="KOT" value="KOT" tabindex="3" class="transForm_button " style="margin-bottom:2px"/>
	<input type="button" id="Bill" name="Bill" value="Bill" tabindex="3" class="transForm_button " style="margin-bottom:2px"/>
	<input type="button" id="DayEnd" name="DayEnd" value="DayEnd" tabindex="3" class="transForm_button "  style="margin-bottom:2px"/>
	<br><br>
	<div style="width:100%;height:400px;margin:auto;float:left">
	<input type="button" id="Dina" name="Dina" value="Dina" tabindex="3" class="transForm_button "  style="margin-bottom:2px"/>
	<input type="button" id="DirectBiller" name="DirectBiller" value="DirectBiller" tabindex="3" class="transForm_button " style="margin-bottom:2px"/>
	</div>
	</div>
	</div>
	
	<div id="divKOTAndDina" style="width:70%;margin-bottom:5px;background-color: #a4d7ff;display: none; overflow-x: hidden; overflow-y: scroll;float:right;margin-right: 4px;height:600px;border:0px solid">
	<table border="1" class="myTable" style="width: 100%;margin: auto;" >
										<thead>
										<tr>
											<th style="width:10%">KOT No</th>
											<th style="width:7%">Time</th>
											<th style="width:15%">Waiter Name</th>
											<th style="width:7%">Table Name</th>
											<th style="width:2%">PaxNo</th>
											<th style="width:12%">User Created</th>
											<th style="width:7%">Amount</th>
										</tr>
										
										</thead>
	</table>
<!-- 	<table id="tblAllTableDtl" class="transTablex" style="width:100%;"> -->
	<div style="background-color: #a4d7ff;display: block; height: 500px;
			    				margin: auto;width: 100%;">
									<table id="tblKOTAndDinaDtl" class="transTablex col5-center" style="width: 100%;">
									<tbody>    
											<col align="right" style="width:10%"><!--  COl1   -->
											<col style="width:7%"><!--  COl2   -->
											<col style="width:15%"><!--  COl3   -->	
											<col style="width:7%"><!--  COl4   -->	
											<col style="width:3%"><!--  COl5   -->	
											<col style="width:12%"><!--  COl6   -->	
											<col style="width:7%"><!--  COl7   -->	
																		
									</tbody>							
									</table>
<!-- 	</table> -->
	</div>
</div>

<div id="divDirectBiller" style="width:70%;margin-bottom:5px;background-color: #a4d7ff;display: none; overflow-x: hidden; overflow-y: scroll;float:right;margin-right: 4px;height:600px;border:0px solid">
	<table border="1" class="myTable" style="width: 100%;margin: auto;" >
										<thead>
										<tr>
											<th style="width:12%">BillNo</th>
											<th style="width:12%">Time</th>
											<th style="width:12%">POS</th>
											<th style="width:12%">TotalAmount</th>
											
										</tr>
										
										</thead>
	</table>
<!-- 	<table id="tblAllTableDtl" class="transTablex" style="width:100%;"> -->
	<div style="background-color: #a4d7ff;display: block; height: 500px;
			    				margin: auto;width: 100%;">
									<table id="tblDirectBillerDtl" class="transTablex col5-center" style="width: 100%;">
									<tbody>    
											<col style="width:12%"><!--  COl1   -->
											<col style="width:12%"><!--  COl2   -->
											<col style="width:12%"><!--  COl3   -->	
											<col style="width:12%"><!--  COl4   -->	
																											
									</tbody>							
									</table>
<!-- 	</table> -->
	</div>
</div>

<div id="divBill" style="width:70%;margin-bottom:5px;background-color: #a4d7ff;display: none; overflow-x: hidden; overflow-y: scroll;float:right;margin-right: 4px;height:600px;border:0px solid">
	<table border="1" class="myTable" style="width: 100%;margin: auto;" >
										<thead>
										<tr>
											<th style="width:12%">BillNo</th>
											<th style="width:12%">TableName</th>
											<th style="width:12%">Time</th>
											<th style="width:12%">TotalAmount</th>
											
										</tr>
										
										</thead>
	</table>
<!-- 	<table id="tblAllTableDtl" class="transTablex" style="width:100%;"> -->
	<div style="background-color: #a4d7ff;display: block; height: 500px;
			    				margin: auto;width: 100%;">
									<table id="tblBillDtl" class="transTablex col5-center" style="width: 100%;">
									<tbody>    
											<col style="width:12%"><!--  COl1   -->
											<col style="width:12%"><!--  COl2   -->
											<col style="width:12%"><!--  COl3   -->	
											<col style="width:12%"><!--  COl4   -->	
																											
									</tbody>							
									</table>
<!-- 	</table> -->
	</div>
</div>
<div style="width:70%;height:600px;margin:auto;margin-top:50px">
<center>
<div id="divDayEnd" style="width:50%;margin-bottom:5px;display: none; float:right;margin-right: 4px;height:300px;border:0px solid">

</div></center></div>
		<br/>
		<br/>
		<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<p align="center" >
			<input type="button" value="View"  class="form_button" onclick="funGetSelectedRowIndex(this,this)"/>
			<input type="reset" value="Print" class="form_button" onclick="" />
			<input type="button" value="Close" class="form_button" onclick="" />
		</p>
		
	</s:form>
</body>
</html>