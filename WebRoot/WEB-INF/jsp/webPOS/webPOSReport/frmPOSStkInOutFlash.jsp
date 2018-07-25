<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock In Out Flash Report</title>
<style>

</style>
<script type="text/javascript">
var reOrderStockList=0,transactionType="",selectedItemCode="";
var jsonObj = [];
$(function() 
		{		
			$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtFromDate" ).datepicker('setDate', 'today');
			$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtToDate" ).datepicker('setDate', 'today');
			
		}); 
		
function funHelp(searchType)
{
	if(searchType=="StockInOutDetails")
	{
		fieldName=searchType;
		if(document.getElementById("cmbReportType").value=="Stock In")
		{	
			transactionType="StockIn";
		    window.open("searchform.html?formname="+transactionType+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		}
		else
	    {
			transactionType="StockOut";
		    window.open("searchform.html?formname="+transactionType+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
	}
	else
	{
		fieldName=searchType;
		if(document.getElementById("cmbType").value=="Item wise")
		{	
			transactionType="POSItemList";
		    window.open("searchform.html?formname="+transactionType+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		}
		 
	}	
}

function funSetData(code)
{
	switch(fieldName)
	{
		case "StockInOutDetails":
			funLoadStockInOutDetails(code);
			break;
			
		case "Type":
			funLoadPOSItemList(code,transactionType);
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
	        		selectedItemCode=response.strItemCode;
	        	    $("#txtViewType").val(response.strItemName);
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


function funLoadStockInOutDetails(code)
{
	if(transactionType=="StockIn")
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
		        	}
		        	else
		        	{
		        		$("#txtOperationType").val(response.strStkInCode);
		        		$("#cmbReason").val(response.strReasonCode);
		        		
	        		  /*  $.each(response.listOfItem,function(i,item)
					     {
	        		       funFillGrid(item.strItemName,item.strItemCode,item.dblQuantity,item.dblPurchaseRate,item.dblAmount,"")
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
	else
	{
		var searchurl=getContextPath()+"/loadStockOutDetails.html?StockOutCode="+code;
		 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strPSPCode=='Invalid Code')
		        	{
		        		alert("Invalid Item Code Code");
		        	}
		        	else
		        	{
		        		$("#txtOperationType").val(response.strStkOutCode);
		        		$("#cmbReason").val(response.strReasonCode);
		        		
	        		  /*  $.each(response.listOfItem,function(i,item)
					     {
	        		       funFillGrid(item.strItemName,item.strItemCode,item.dblQuantity,item.dblPurchaseRate,item.dblAmount,"")
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
	
	

	$("#close").click(function(event) 
	{
		
		
	});
	
	$("#show").click(function(event) 
			{
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
				if(funDeleteTableAllRows()){
					if(CalculateDateDiff(fromDate,toDate))
					{
						var callingTime="second";
						fDate=fromDate;
						tDate=toDate;
						funFetchColNames(callingTime);
					}
				}
			});

});


function funLoadTableData()
{
	var callingTime="first";
	funSetDate();
	var fromDate =$('#txtFromDate');
	var toDate =$('#txtToDate').val();
	var data= $("#txtFromDate").val();
	fDate=fromDate;
	tDate=toDate;
	funDeleteTableAllRows();
	funFetchColNames(callingTime);
	
	
}

function funDeleteTableAllRows()
{
	$('#tblStockInOutFlashReport tbody').empty();
	$('#tblStockInOutFlashHeader tbody').empty();
	$('#tblTotal tbody').empty();
	var table = document.getElementById("tblStockInOutFlashReport");
	var rowCount1 = table.rows.length;
	if(rowCount1==0){
		return true;
	}else{
		return false;
	}
}

function funResetFields()
	{
		$("#txtOperationType").val("");
		$("#txtViewTypE").val("");
		selectedItemCode="";
	}
 



  function funGenerateProductionEntry()
  {
	type="production";
	 var searchurl=getContextPath()+"/generateProductionStockEntry.html";
	 $.ajax({
		        type: "POST",
		        url: searchurl,
		        data:{
		        	type:type,
		        },
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response==0)
		        	{
		        		alert("No data");
		        		
		        	}
		        	else
		        	{
			        	
		        	} 
				},
		
	      });
   }





function funFillheaderCol(rowData) 
{
	var table = document.getElementById("tblStockInOutFlashHeader");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    
    
    var rowItem=rowData.split("#");
    row.insertCell(0).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  size=\"30%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[0]+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; 
    row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[1]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+rowItem[2]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+rowItem[3]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    row.insertCell(4).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+rowItem[4]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    row.insertCell(5).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  size=\"15%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[5]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    row.insertCell(6).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  size=\"15%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[6]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    
    row = table.rows[0];
	row.style.backgroundColor='#87CEFA';
	row.style.height ='40px';
	row.style.fontSize = "20px";
  
}

function funSetDate()
{
	
	var searchurl=getContextPath()+"/getPOSDate.html";
	 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        var date = new Date(response.POSDate);
		        var	dateTime=date.getDate()  + '-' + (date.getMonth() + 1)+ '-' +  date.getFullYear();
		        var posDate=dateTime.split(" ");
		        $("#txtFromDate").val(posDate[0]);
	        	$("#txtToDate").val(posDate[0]);
	        	
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
    	 return false;
     }
}

		

function funFetchColNames(callingTime) {
	var posName=$('#cmbPOSName').val();
	var operationTypeCode=$('#txtOperationType').val();
	var operationType=$('#cmbReportType').val();
	var searchValue="";
	searchValue=selectedItemCode;
	var viewType=$('#cmbType').val();	
	var reasonName=$('#cmbReason').val();	
	var gurl = getContextPath()+"/loadPOSStockInOutFlash.html";
	var abc;
	
	$.ajax({
		type : "GET",
		data:{  fromDate:$('#txtFromDate').val(),
				toDate:$('#txtToDate').val(),
				posName:posName,
				viewType:viewType,
				operationType:operationType,
				operationTypeCode:operationTypeCode,
				searchValue:searchValue,
				reasonName:reasonName,
				time:callingTime,
			},
		url : gurl,
		dataType : "json",
		success : function(response) {
		 	if (response== 0) 
			{
				alert("Data Not Found");
			} 
			else 
			{ 
				var row="",totalRow="";
				
				$.each(response.listHeader,function(i,item)
				 {
	            	if(row=="")
            		{
	            		row=item;
            		}
	            	else
	            	{
	            		row=row+"#"+item;
	            	}	
            	 });
				 funFillheaderCol(row);
						
				 $.each(response.listDetails,function(i,item)
				  {
	            	
					 funFillStockTableCol(item[0],item[1],item[2],item[3],item[4],item[5],item[6]);
            	  });
						 
				 $.each(response.totalList,function(i,item)
				 {
	            	if(totalRow=="")
            		{
	            		totalRow=item;
            		}
	            	else
	            	{
	            		totalRow=totalRow+"#"+item;
	            	}	
            	 });
				 if(response.listDetails.length>0)
				  {
					 funFillTotalCol(totalRow);
				  }
			}
			
		}
});
}

		
	
		

		 	function funFillStockTableCol(item0,item1,item2,item3,item4,item5,item6,item7)
			{
				var table = document.getElementById("tblStockInOutFlashReport");
				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);

			      row.insertCell(0).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  size=\"30%\" id=\"txtDate."+(rowCount)+"\" value='"+item0+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; 
			      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\"  size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+item1+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\"  size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+item2+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\"  size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+item3+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(4).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+item4+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(5).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  size=\"15%\" id=\"txtDate."+(rowCount)+"\" value='"+item5+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(6).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  size=\"15%\" id=\"txtDate."+(rowCount)+"\" value='"+item6+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			     
			}   
		 	
		   function funFillTotalCol(rowData) 
			{
				var table = document.getElementById("tblTotal");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    var rowItem=rowData.split("#");
			    var blankData="         ";
			   
			    row.insertCell(0).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].strItemName\" readonly=\"readonly\" class=\"Box\" size=\30%\" id=\"txtItemName."+(rowCount)+"\" value='"+rowItem[0]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			    row.insertCell(1).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblCompStk\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right\" size=\"15%\" id=\"txtCompStock."+(rowCount)+"\" value='"+rowItem[1]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			    row.insertCell(2).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblPhyStk\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right\" size=\"15%\" id=\"txtPhyStock."+(rowCount)+"\" value='"+rowItem[2]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			    row.insertCell(3).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblVariance\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right\" size=\"15%\" id=\"txtVariance."+(rowCount)+"\" value='"+rowItem[3]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			    row.insertCell(4).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblCompStk\" readonly=\"readonly\" class=\"Box\"  size=\"15%\" id=\"txtCompStock."+(rowCount)+"\" value='"+blankData+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			    row.insertCell(5).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblPhyStk\" readonly=\"readonly\" class=\"Box\"  size=\"15%\" id=\"txtPhyStock."+(rowCount)+"\" value='"+blankData+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			    row.insertCell(6).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblVariance\" readonly=\"readonly\" class=\"Box\"  size=\"15%\" id=\"txtVariance."+(rowCount)+"\" value='"+blankData+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			    row.insertCell(7).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblCompStk\" readonly=\"readonly\" class=\"Box\"  size=\"15%\" id=\"txtCompStock."+(rowCount)+"\" value='"+blankData+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			    row.insertCell(8).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblCompStk\" readonly=\"readonly\" class=\"Box\"  size=\"5%\" id=\"txtCompStock."+(rowCount)+"\" value='"+blankData+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			}
			

</script>


</head>

<body onload="funLoadTableData()">
	<div id="formHeading">
		<label>Stock In Out Flash Report</label>
	</div>
	<br />
	<br />
	<s:form name="POSStockInOutFlashReport" method="POST" action="rptPOSStockInOutFlashReport.html?saddr=${urlHits}" target="_blank">
		<div>
			<div>

				<table class="masterTable" style="margin-left: 130px;">
                 <tr>
					<td >POS Name</td>
					<td ><s:select id="cmbPOSName" name="cmbPOSName" path="strPOSName" cssClass="BoxW124px" items="${posList}" >
					</s:select></td>
				
					<td><label>From Date</label></td>
					<td><s:input id="txtFromDate" required="required" path="fromDate" pattern="\d{1,2}-\d{1,2}-\d{4}"
							cssClass="calenderTextBox" /></td>

					<td><label>To Date</label></td>
					<td><s:input id="txtToDate" required="required" path="toDate"
							pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox" /></td>
					
				    <td >Type</td>			
				    <td ><s:select id="cmbType" name="cmbType" path="strType" cssClass="BoxW124px" items="${typeList}" ></s:select>
				    <td><s:input id="txtViewType" name="txtViewType" path="strViewType" cssClass="BoxW124px" ondblclick="funHelp('Type')"/></td>
				   	
					</tr>
					
					 <tr>
					 
					  <td width="80px" ><s:select id="cmbReportType" name="cmbReportType" path="strReportType" cssClass="BoxW124px" items="${operationTypeList}" >
					  </s:select></td>
					  <td ><s:input id="txtOperationType" name="txtOperationType" path="strOperationType" cssClass="BoxW124px" ondblclick="funHelp('StockInOutDetails')"/></td>
					   
				      <td width="80px">Reason :</td>
					  <td colspan="9" ><s:select id="cmbReason" name="cmbReason" path="strReasonCode" cssClass="BoxW124px" items="${reasonList}"  >
					  </s:select>    </td>	
					
					</tr>
			

				</table>
			</div>
			
			
				 <div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 500px; margin-left: 130px; overflow-x: scroll; overflow-y: scroll; width: 80%;">
				
				
						<table id="tblStockInOutFlashHeader" class="transTablex"
							style="width: 100%; text-align: center !important; ">
					
					    </table>
						
						<table id="tblStockInOutFlashReport" class="transTablex"
							style="width: 100%; text-align: center !important;">
						</table>
				
				  </div>	
			
			    <div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 70px; margin-left: 130px; overflow-x: scroll; overflow-y: hidden; width: 80%;">
				
						<table id="tblTotal" class="transTablex"
						   style="width: 100%; text-align:">
					    <col style="width:60%"><!--  COl1   -->
					   </table>
				
			   </div>
						
	
			
		</div>

		<br />
		<br />
		<p align="center">
			    <input type="button" value="Show" class="form_button"id="show" /> 
			    <input type="submit" value="Export"	class="form_button"  id="export" />
				<input type="reset" value="Reset"class="form_button" id="reset" />

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