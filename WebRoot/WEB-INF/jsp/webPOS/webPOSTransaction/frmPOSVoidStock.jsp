<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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



.header {
	border: white 1px solid;
	background: #78BEF9;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: bold;
	outline: 0 none;
	padding-left: 0;
	width: 100%;
	height:100%
}
</style>
<script type="text/javascript">
 	var fieldName,textValue2="",selectedRowIndex=0,delTableNo="",delbillNo="",delItemcode="", delAmount="",bDate="",delModItemcode="";
 
 	
 	$(function() 
 	{
 		funFillGrid();
 		
 	});
 	

    $(document).ready(function ()
    {

       $("form").submit(function(event){
    	   if($("#txtstkInOutCode").text().trim()=="")
    		{
    		alert("Select Item." );
    		return false;
    		} 
    	   else
			  funValidate();
			});

      
    });

    
		
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
	

	

	
	function funFillGrid()
	{
		var searchUrl="";
		var transType=$("#cmbTransType").val();
		$('#tblDataFillGrid tbody').empty()
		var table = document.getElementById("tblDataFillGrid");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		if(transType=="PS Posting")
		{
			document.all["cmbstkInReason"].style.display = 'none';
			document.all["cmbstkOutReason"].style.display = 'none';
			document.all["cmbPSPReason"].style.display = 'block';
			row.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \"  size=\"15%\" value='PSP Code' >";
			row.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" size=\"15%\" value='Stock In Code' >";
			row.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" size=\"15%\" value='Stock Out Code' >";
			row.insertCell(3).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" size=\"15%\" value='Bill No'>";
			row.insertCell(4).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" size=\"15%\" value='Stock In Amt' >";
			row.insertCell(5).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" size=\"15%\" value='Sale Amt' >";
		}
		if(transType=="Stock In")
		{
			document.all["cmbstkInReason"].style.display = 'block';
			document.all["cmbstkOutReason"].style.display = 'none';
			document.all["cmbPSPReason"].style.display = 'none';
			row.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" size=\"15%\" id=\"KOT NO.\" value='Stock In Code' >";
			row.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" size=\"15%\" id=\"TB Name\" value='Stock In Date' >";
			row.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" size=\"15%\" id=\"Waiter Name\" value='Reason Name' >";
			row.insertCell(3).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" size=\"15%\" id=\"Take Away\" value='User Created' >";
		}
		if(transType=="Stock Out")
			{
				document.all["cmbstkInReason"].style.display = 'none';
				document.all["cmbstkOutReason"].style.display = 'block';
				document.all["cmbPSPReason"].style.display = 'none';
				row.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" size=\"15%\" id=\"KOT NO.\" value='Stock Out Code' >";
				row.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" size=\"15%\" id=\"TB Name\" value='Stock Out Date' >";
				row.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" size=\"15%\" id=\"Waiter Name\" value='Reason Name' >";
				row.insertCell(3).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" size=\"15%\" id=\"Take Away\" value='User Created' >";
			}
	  
	   
	    
	    searchUrl=getContextPath()+"/fillVoidStockGridData.html?";
		$.ajax({
		        type: "GET",
		        url: searchUrl,
		        async:false,
		        data:"transType="+transType,
			    success: function(response)
			    {
			    		funAddFullRow(response);
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
		
		var table = document.getElementById("tblDataFillGrid");
		var rowCount = table.rows.length;
	    for(var i=0;i<data.length;i++){
	    	row = table.insertRow(rowCount);
	    	var rowData=data[i];
	    	
	    	for(var j=0;j<rowData.length;j++){
	    		row.insertCell(j).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+rowData[j]+"\" value='"+rowData[j]+"' onclick=\"funGetSelectedRowData(this)\"/>";
	    	}
	    	rowCount++;
	    }
	}
	
	
	
	
     function funGetSelectedRowData(obj)
     {
    	 var index = obj.parentNode.parentNode.rowIndex;
     	 var tableName = document.getElementById("tblDataFillGrid");
     	var rowCount = tableName.rows.length;
     	if(selectedRowIndex%2==0)
		 {
		 if(rowCount==selectedRowIndex)
			 {
			 selectedRowIndex=index;
			 row = tableName.rows[selectedRowIndex];
			 row.style.backgroundColor='#ffd966';
			 row.hilite = true;
			 }
		 else{
			 row = tableName.rows[selectedRowIndex];
			 row.style.backgroundColor='#A3D0F7';
			 selectedRowIndex=index;
			 row = tableName.rows[selectedRowIndex];
			 row.style.backgroundColor='#ffd966';
			 row.hilite = true;
		 }
		 }
		 else
		 {
			 if(rowCount==selectedRowIndex)
			 {
				 selectedRowIndex=index;
				 row = tableName.rows[selectedRowIndex];
				 row.style.backgroundColor='#ffd966';
				 row.hilite = true;
			 }
			 else
				 {
			 row = tableName.rows[selectedRowIndex];
			 row.style.backgroundColor='#C0E4FF';
			 selectedRowIndex=index;
			 row = tableName.rows[selectedRowIndex];
			 row.style.backgroundColor='#ffd966';
			 row.hilite = true;
				 }
        }
		
	 
	
    
       	var stkCode= tableName.rows[index].cells[0].innerHTML; 
        var btnBackground=stkCode.split('value=');
        var billData=btnBackground[1].split("onclick");
        var code=billData[0].substring(1, (billData[0].length-2));
    	
        $("#txtstkInOutCode").text(code);
        stkCode= tableName.rows[index].cells[1].innerHTML; 
        btnBackground=stkCode.split('value=');
        billData=btnBackground[1].split("onclick");
        var date=billData[0].substring(1, (billData[0].length-2));
    	
        $("#txtDateTime").text(date);
        
        stkCode= tableName.rows[index].cells[3].innerHTML; 
        btnBackground=stkCode.split('value=');
        billData=btnBackground[1].split("onclick");
        var user=billData[0].substring(1, (billData[0].length-2));
    	
        $("#txtUser").text(user);
       
        var transType=$("#cmbTransType").val();
        $('#tblData tbody').empty()
		var table = document.getElementById("tblData");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
        if(transType=="PS Posting")
		{
			row.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \"  size=\"15%\" value='PSP Code' >";
			row.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" size=\"15%\" value='Stock In Code' >";
			row.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" size=\"15%\" value='Stock Out Code' >";
			row.insertCell(3).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" size=\"15%\" value='Bill No'>";
			row.insertCell(4).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" size=\"15%\" value='Stock In Amt' >";
		}
        else
			{
				row.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \" id=\"KOT NO.\" value='Description' >";
				row.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \"  id=\"TB Name\" value='Qty' >";
				row.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"header \"  id=\"Waiter Name\" value='Amount' >";
			}
	  
	  
        searchUrl=getContextPath()+"/fillStockDtlData.html?code="+code+"&transType="+transType;
		$.ajax({
		        type: "GET",
		        url: searchUrl,
		        async:false,
			    success: function(response)
			    {
					if(transType=="PS Posting")
						{
			    			$.each(response, function(i,item)
							{
			    				funAddPSPostingRowDtl(item.strItemName,item.dblCompStk,item.dblPhyStk,item.dblVariance,item.dblVairanceAmt);
							});
						}
					else
					{
		    			$.each(response, function(i,item)
						{
		    				funAddStockRowDtl(item.strItemName,item.dblQuantity,item.dblAmount);
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
     
     function funAddPSPostingRowDtl(strItemName,dblCompStk,dblPhyStk,dblVariance,dblVairanceAmt){
 		var table = document.getElementById("tblData");
 		var rowCount = table.rows.length;
 		var row = table.insertRow(rowCount);
 		row.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value="+strItemName+">";
 		row.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"TB Name\" value="+dblCompStk+" >";
 		row.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"Waiter Name\" value="+dblPhyStk+" >";
 		row.insertCell(3).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"Take Away\" value="+dblVariance+" >";
 		row.insertCell(4).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"Take Away\" value="+dblVairanceAmt+" >";

 	}
     
     function funAddStockRowDtl(strItemName,dblQuantity,dblAmount){
  		var table = document.getElementById("tblData");
  		var rowCount = table.rows.length;
  		var row = table.insertRow(rowCount);
  		row.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" id=\"KOT NO.\" value='"+strItemName+"''>";
  		row.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" id=\"TB Name\" value="+dblQuantity+" >";
  		row.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" id=\"Waiter Name\" value="+dblAmount+" >";

  		
  	}
     


     function funValidate()
     {
     	 
     	
     		
     		var stkCode=$("#txtstkInOutCode").text();
     		
     		document.frmVoidStock.action="voidStock.html?stkCode="+ stkCode;
     		document.frmVoidStock.submit();
     		
     		
     }
 	

   
</script>



</head>
<body>
       
     <div id="formHeading">
		<label>Void Stock</label>
			</div>
		<br/>
<br/>-


<s:form name="frmVoidStock" method="POST" commandName="command" action="voidStock.html" >			
		<table>
		<tr>
		<td>
					<table style="margin-left: 70px;" >
				<tr>
				<td>
					<label id="lblstkInOutCode">Stock In Code</label>
				</td>
				<td>
				&nbsp;&nbsp;&nbsp;<label  id="txtstkInOutCode" ></label>  
				</td>
				<td>
				&nbsp;&nbsp;&nbsp;&nbsp;<label>User Created</label>
				</td>
				<td >
					&nbsp;&nbsp;&nbsp;<label id="txtUser" ></label>
				</td>
				</tr>
				<tr>
				<td>
					<label >Date & Time</label>
				</td>
				<td>
					&nbsp;&nbsp;&nbsp;<label id="txtDateTime" > </label>
				</td>
				<td>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	<label >Reason</label>&nbsp;
				</td>
				
				 <td><s:select id="cmbstkInReason" path="strVoidStkInReasonCode" cssClass="BoxW124px" items="${stkInReasonList}">
				</s:select><s:select id="cmbstkOutReason"  path="strVoidStkOutReasonCode"  cssStyle="display:none;" cssClass="BoxW124px" items="${stkOutReasonList}">
				</s:select><s:select id="cmbPSPReason"  path="strVoidPSPReasonCode"  cssStyle="display:none;" cssClass="BoxW124px" items="${stkOutReasonList}">
				</s:select></td>
								</tr>
								</table>
								</td>
								<td>		
					<table>
				 <tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>
				<label>Transaction Type</label></td>
				
				<td>
				<s:select id="cmbTransType" path="strTransType" cssClass="BoxW124px" onchange="funFillGrid()">
				<option value="Stock In">Stock In</option>
				 <option value="Stock Out">Stock Out</option>
				  <option value="PS Posting">PS Posting</option>
 				 </s:select> 
		       </td>
		      
				</tr>
				</table>	
				
		</td>
		</tr>
		</table>
			<div id="divMain" style=" border: 1px solid #ccc; height: 480px;  width: 820px; margin-left: 70px; " >				
				<table  >
				<tr>
				<td>
			
							
							<div id="divBillItemDtl" style=" border: 1px solid #ccc; height: 400px;  overflow-x: auto; overflow-y: auto; width: 355px;">
								
					<table id="tblData"
							style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
							class="transTablex col2-right col3-right col4-right col5-right">
							<tbody>    
									<col style="width:60%"><!--  COl1   -->
									<col style="width:20%"><!--  COl2   -->
									<col style="width:20%"><!--  COl3   -->								
							</tbody>
					<tr >
						<td class="header" style="width:60%"><label>Description</label></td>
						<td class="header" style="width:20%"><label>Qty</label></td>
						<td class="header" style="width:20%"><label>Amount</label></td>
					</tr>
					</table>
					
							</div>
						
																
				<table>
				<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label >Sub Total</label>
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>
					<label id="txtSubTotal" ></label> 
				</td>
				</tr>
				
				<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label >Tax</label>
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>
					<label id="txtTax" ></label>
				</td>
				</tr>
				
				<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label >TOTAL</label>
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>
					<input  type="text"  id="txtTotal" readonly="true" style="width:70px;" class="longTextBox jQKeyboard form-control"  /> 
				</td>
				</tr>
				</table>
							
			</td>		
							
			<td>
				
						<div id="divItemDtl" style=" border: 1px solid #ccc;  height: 450px;  overflow-x: auto; overflow-y: auto; width: 453px;">									
						<table id="tblDataFillGrid"
							style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
							class="transTablex col2-right col3-right col4-right col5-right">
							<tr >
					</tr>
					</table>
					</div>	
					<div style=" height: 30px; width: 453px;">
					</div>
					</td>
						
					
					</tr>
				</table>
			</div>
		 
		 <p align="center">
			<input type="submit" value="Void" tabindex="3" class="form_button" />
		
		</p>
			<!-- <div style="text-align: right; margin-left: 250px;">
				
				 	<table id="tblFooterButtons"  cellpadding="0" cellspacing="5"  > class="table table-striped table-bordered table-hover"				 																																	
							<tr>							
																
										<td><input  type="button" id="Home"  value="Home"  style="width: 100px;height: 35px; white-space: normal;"   onclick="funHomeBtnclicked()"/></td>
										 </tr>																																				 									   				   									   									   						
					</table>			
			</div>
			 -->
		 
		

	</s:form>

<br /><br />       
 
</body>
</html>