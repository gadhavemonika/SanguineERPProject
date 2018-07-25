<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock Flash Report</title>
<style>
.disabled_button{
    background:url(../images/big1.png) no-repeat;
   background-size: 96px 24px;
    cursor:pointer;
    border:none;
    width:100px;
    height:24px;
    color: #fff;
    font-weight: normal;
    background-color: #c0c0c0;
}
</style>
<script type="text/javascript">
var reOrderStockList=0;
var jsonObj = [];
$(function() 
		{		
			$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtFromDate" ).datepicker('setDate', 'today');
			$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtToDate" ).datepicker('setDate', 'today');
			
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
	
	

	document.getElementById("production").disabled = true;
	
	$("#production").click(function(event) 
	{
		funGenerateProductionEntry();
		
		/*type="production";
		var gurl = getContextPath()+"/generateProductionStockEntry.html";
		var abc;
		 $.ajax({
		        type: "POST",
		        url: gurl,
		        dataType: "json",
		        data:
		        {
		        	type:type,
		        	ListData:itemList,
		        },
		        success: function(response)
		        {
			 	if (response== 0) 
				{
					alert("Data Not Found");
				} 
				else 
				{ 
					
				}
			}
	    });	
		 */
	});
	
	$("#view").click(function(event) 
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
	$('#tblStockFlashReport tbody').empty();
	$('#tblStockFlashHeader tbody').empty();
	$('#tblTotal tbody').empty();
	var table = document.getElementById("tblStockFlashReport");
	var rowCount1 = table.rows.length;
	if(rowCount1==0){
		return true;
	}else{
		return false;
	}
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


function funFillheaderCol(rowData,reportType) 
{
	var table = document.getElementById("tblStockFlashHeader");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    
    
    var rowItem=rowData.split("#");
    row.insertCell(0).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  size=\"12%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[0]+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; 
    row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  size=\"13%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[1]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  size=\"25%\" id=\"txtCompStk."+(rowCount)+"\" value='"+rowItem[2]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+rowItem[3]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    row.insertCell(4).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+rowItem[4]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    row.insertCell(5).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[5]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    row.insertCell(6).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[6]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    row.insertCell(7).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[7]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    if(reportType=="Not Stock")
	{
       row.insertCell(8).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+rowItem[8]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
       row.insertCell(9).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[9]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
       row.insertCell(10).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[10]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
       row.insertCell(11).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[11]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
    	   
	}
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
	var type=$('#cmbType').val();
	var reportType=$('#cmbReportType').val();
	var groupwise=$('#cmbGroupwise').val();
	var showStockWith=$('#cmbViewWith').val();
	var showZeroBalYN=$('#cmbShowZeroBal').val();		 
	var gurl = getContextPath()+"/loadPOSStockFlash.html";
	var abc;
	
	$.ajax({
		type : "GET",
		data:{  fromDate:$('#txtFromDate').val(),
				toDate:$('#txtToDate').val(),
				posName:posName,
				type:type,
				reportType:reportType,
				groupwise:groupwise,
				showStockWith:showStockWith,
				showZeroBalStockYN:showZeroBalYN,
				showZeroBalStockYN:showZeroBalYN,
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
				
				if(reportType=="Stock")
				{
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
					funFillheaderCol(row,"stock");
					
					 $.each(response.listDetails,function(i,item)
					  {
		            	
						 funFillStockTableCol(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7]);
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
						 funFillTotalCol(totalRow,"stock");
					  }
					 
					 
								
				}
				else
				{
					if(response.listDetails.length==0)
					  {
						document.getElementById("production").disabled = true;
					  }
					else
					 {
						document.getElementById("production").disabled = false;
					 }	
						
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
					
					funFillheaderCol(row,"Not Stock");
					
					 $.each(response.listDetails,function(i,item)
					  {
				            	
						 funFillReorderStockTableCol(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9],item[10],item[11]);
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
						 funFillTotalCol(totalRow,"Not Stock");
					  }
					 
					 
				}	
				
			   
				
			}
			
		}
});
}

		
	
		

		 	function funFillStockTableCol(item0,item1,item2,item3,item4,item5,item6,item7)
			{
				var table = document.getElementById("tblStockFlashReport");
				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);

			      row.insertCell(0).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  size=\"12%\" id=\"txtDate."+(rowCount)+"\" value='"+item0+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; 
			      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  size=\"13%\" id=\"txtDate."+(rowCount)+"\" value='"+item1+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  size=\"25%\" id=\"txtCompStk."+(rowCount)+"\" value='"+item2+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+item3+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(4).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+item4+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(5).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+item5+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(6).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+item6+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(7).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+item7+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			}   
		 	
		 	function funFillReorderStockTableCol(item0,item1,item2,item3,item4,item5,item6,item7,item8,item9,item10,item11)
			{
				var table = document.getElementById("tblStockFlashReport");
				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);

			      row.insertCell(0).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  size=\"12%\" id=\"txtDate."+(rowCount)+"\" value='"+item0+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; 
			      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  size=\"13%\" id=\"txtDate."+(rowCount)+"\" value='"+item1+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  size=\"25%\" id=\"txtCompStk."+(rowCount)+"\" value='"+item2+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+item3+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(4).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+item4+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(5).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+item5+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(6).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+item6+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(7).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+item7+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(8).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+item8+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(9).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+item9+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(10).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+item10+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(11).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+item11+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			} 
		 	
		 	function funFillTotalCol(rowData,reportType) 
			{
				var table = document.getElementById("tblTotal");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    var rowItem=rowData.split("#");
			    var blankData="  ";
			    
			    if(reportType=="Not Stock")
				{
			    	  row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  size=\"25%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[0]+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; 
					  row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"text-align: right\" size=\"16%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[1]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
					  row.insertCell(2).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].strItemName\" readonly=\"readonly\" class=\"Box \"  style=\"text-align: right\" size=\"16%\" id=\"txtCompStk."+(rowCount)+"\" value='"+rowItem[2]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
					  row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"16%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+rowItem[3]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
					  row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"16%\" id=\"txtVariance."+(rowCount)+"\" value='"+rowItem[4]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
					  row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"16%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[5]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
					  row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"16%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[6]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(7).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].dblCompStk\" readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"16%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[7]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(8).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"16%\" id=\"txtVariance."+(rowCount)+"\" value='"+rowItem[8]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			          row.insertCell(9).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"16%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[9]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			       
			    	   
				}
			    else
			    {
			    	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  size=\"25%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[0]+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; 
					    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"text-align: right\" size=\"16%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[1]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
					    row.insertCell(2).innerHTML= "<input name=\"listPSPDtl["+(rowCount)+"].strItemName\" readonly=\"readonly\" class=\"Box \"  style=\"text-align: right\" size=\"16%\" id=\"txtCompStk."+(rowCount)+"\" value='"+rowItem[2]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
					    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"16%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+rowItem[3]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
					    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"16%\" id=\"txtVariance."+(rowCount)+"\" value='"+rowItem[4]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
					    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box \" style=\"text-align: right\" size=\"16%\" id=\"txtDate."+(rowCount)+"\" value='"+rowItem[5]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
					   
			    }	
			    
			}
			

</script>


</head>

<body onload="funLoadTableData()">
	<div id="formHeading">
		<label>Stock Flash Report</label>
	</div>
	<br />
	<br />
	<s:form name="POSStockFlashReport" method="POST" action="rptPOSStockFlashReport.html?saddr=${urlHits}" target="_blank">
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
								
						<td width="80px">Type</td>
					    <td colspan="3"><s:select id="cmbType" name="cmbType" path="strType" cssClass="BoxW124px" items="${typeList}" ></s:select>
					    </td>		

					</tr>
					
					 <tr>
					    <td width="80px">Report Type</td>
					    <td colspan="3"><s:select id="cmbReportType" name="cmbReportType" path="strReportType" cssClass="BoxW124px" items="${reportTypeList}" >
					    </s:select></td>
				
					    <td >Group Wise</td>
					    <td ><s:select id="cmbGroupwise" name="cmbGroupwise" path="strGroupName" cssClass="BoxW124px" items="${groupList}" >
					    </s:select></td>
					    
					    <td >Show Stock With</td>
					    <td ><s:select id="cmbViewWith" name="cmbViewWith" path="strViewBy" cssClass="BoxW124px" items="${viewByList}" >
					    </s:select></td>
					     		
						<td width="80px">Show 0 Bal Stock</td>
					    <td colspan="3"><s:select id="cmbShowZeroBal" name="cmbShowZeroBal" path="strDocType" cssClass="BoxW124px" items="${showZeroBalList}" ></s:select>
					    <br>
					    <br>
					    </td>	
					    	

					</tr>
			

				</table>
			</div>
			
			
				 <div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 500px; margin-left: 130px; overflow-x: scroll; overflow-y: scroll; width: 80%;">
				
				
						<table id="tblStockFlashHeader" class="transTablex"
							style="width: 100%; text-align: center !important; ">
					
					    </table>
						
						<table id="tblStockFlashReport" class="transTablex"
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
			    <input type="button" value="View" class="form_button"id="view" /> 
			    <input type="button" value="Print" class="form_button"id="print" /> 
				<input type="submit" value="Export"	class="form_button"  id="export" /> 
				<input type=button value="Production" class="form_button"   id="production"/> 
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