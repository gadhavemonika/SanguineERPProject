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
    var fieldName,textValue2="",selectedRowIndex=0,posCode="";
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
			case "PhysicalStock":
				funLoadPhysicalStockDetails(code);
				break;
		}
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
		        		$("#txtPSPCode").val(response.strPSPCode);
		        		posCode=(response.strPOSCode);
	        		   /* $.each(response.listPSPDtl,function(i,item)
					     {
	        			   funFillGrid(item.strItemName,item.strItemCode,item.dblPhyStk,item.dblCompStk,item.dblVariance,item.dblVairanceAmt)
						 });
	        		   */
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
 	
 	
 	
 	
 	
 	
/* 	
 	
 	
 	
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
	 */

		
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
	
	
	

	
	
/*	function funGetSelectedRowIndex(obj)
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
	

	*/
	
	
		
</script>



</head>
<body onload="funLoadData()">
       
     <div id="formHeading">
		<label>Stock Adjustment</label>
			</div>
		<br/>
<br/>


	<s:form name="Stock Adjustment" method="POST" action="savePhysicalStock.html?saddr=${urlHits}" >
	   
	   
	   <div style=" width: 60%; margin-left:250px; height: 600px;background-color: #a4d7ff;">
	     <br>
	    <table class=transFormTable>
			<tr>
			
			    <td>
				    <label>Phy Stk Posting No :</label>
					<s:input id="txtPSPCode" name="txtPSPCode" path="strPSPCode" cssClass="BoxW124px" ondblclick="funHelp('PhysicalStock')"/>
				</td>
				
			    <td>
				</td>
				 
				 <td>
				    <label>Generate Entry :</label>
					<s:select id="cmbType" name="cmbType" path="strType" cssClass="BoxW124px" items="${typeList}" ></s:select>
				</td>
			
			
			</tr>
			
			<tr>
				 <td  >
				    <label>Bill No :</label>
					<s:label id="lblBillNo" path="strBillNo" />
					<s:input type="hidden" id="txtBillNo" path="strViewBillNo"/>
				</td>
				
				 <td>
				</td>
				 
				 <td>
				    <label>StockIn Code :</label>
					<s:label id="lblStockinCode" path="strStockinCode"/>
					<s:input type="hidden" id="txtStockInCode" path="strViewStockinCode"/>
				</td>
					
				
				
			</tr>
		   </table>
			<div style=" background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 450px; margin: auto; overflow-x: scroll; overflow-y: scroll; width: 90%;">
					<table id="tblData"
							style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
							class="transTablex  col3-right col4-right col5-right">
							<tr >
						<td style="border: 1px  white solid;width:15%"><label>Item Code</label></td>
						<td style="border: 1px  white solid;width:55%"><label>Item Name</label></td>
						<td style="border: 1px  white solid;width:10%"><label>Variance</label></td>
						<td style="border: 1px  white solid;width:10%"><label>Sale Rate</label></td>
						<td style="border: 1px  white solid;width:10%"><label>Sale Amt</label></td>
						<td style="border: 1px  white solid;width:0%"><label></label></td>	
						
					</tr>
					</table>
			</div>
			
			<table class=transFormTable>
					<tr>
						<td style="width:15%"><label></label></td>
						<td style="width:55%"><label></label></td>
						<td style="width:10%"><label></label></td>
						<td style="width:10%"><label>Total</label></td>
						<td style="width:10%">
						<s:label id="lblTotalSaleAmt" path="strStockOutAmt"/>
						<s:input type="hidden" id="txtTotalSaleAmt" path="strSaleAmt"/>
						</td>
					
					</tr>
			     </table>
			
			
		</div>

		<br />
		<br />
		<p align="center">
			   <input type="submit" value="Generate Sale Entry"	class="long_form_button"  id="btnSale" /> 
				<input type=button value="Generate StockIn Entry" class="long_form_button"   id="btnStockin"/> 
		</p>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 40%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
	   
	   
		
	</s:form> 

 
</body>
</html>