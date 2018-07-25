<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Day End Flash Report</title>
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

.cell {
	background: inherit;
	border: 0 solid #060006;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	outline: 0 none;
	padding-left: 0;
	width: 60px
}

.header {
	background: inherit;
	border: 0 solid #060006;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: bold;
	outline: 0 none;
	padding-left: 0;
	width: 60px
}
</style>
<script type="text/javascript">
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

	$("#execute").click(function(event) {
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
			if(CalculateDateDiff(fromDate,toDate)){
				fDate=fromDate;
				tDate=toDate;
				funFetchColNames();
			}
		}
	});

});


function funLoadTableData()
{
	funSetDate();
	var fromDate = $("#txtFromDate").val();
	var toDate = $("#txtToDate").val();
	fDate=fromDate;
	tDate=toDate;
	funDeleteTableAllRows();
	funFetchColNames();
	
}

function funDeleteTableAllRows()
{
	$('#tblDayEndFlash tbody').empty();
	$('#tblTotal tbody').empty();
	var table = document.getElementById("tblDayEndFlash");
	var rowCount1 = table.rows.length;
	if(rowCount1==0){
		return true;
	}else{
		return false;
	}
}

/* function funDeleteTableAllRows()
{
	$('#tblDayEndFlash tbody').empty();
	$('#tblTotal tbody').empty();
	var table = document.getElementById("tblDayEndFlash");
	var table1 = document.getElementById("tblTotal");
	var rowCount1 = table.rows.length;
	var rowCount2 = table1.rows.length;
	if(rowCount1==0 && rowCount2==0){
		return true;
	}else{
		return false;
	}
} */

function funSetDate()
{
	
	var searchurl=getContextPath()+"/getPOSDate.html";
	 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	/* var dateTime=response.POSDate;
		        	var date=dateTime.split(" ");
		        	$("#txtFromDate").val(date[0]);
		        	$("#txtToDate").val(date[0]); */
		        	
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

		

function funFetchColNames() {
	
	var posName=$('#cmbPOSName').val();
	var gurl = getContextPath()+"/loadDayEndFlash.html";
	var abc;
	
	$.ajax({
		type : "GET",
		data:{ fromDate:fDate,
				toDate:tDate,
				posName:posName,
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
			 
			$.each(response.List,function(i,item){
	            	
					funFillTableCol(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9],item[10],item[11],item[12],item[13],item[14],item[15],item[16],item[17],item[18],item[19]);
            	});
				
				funFillTotalCol(response.totalList);
        	
			}
			
		}
});
}

		
	
		

		 	function funFillTableCol(item0,item1,item2,item3,item4,item5,item6,item7,item8,item9,item10,item11,item12,item13,item14,item15,item16,item17,item18,item19)
			{
				var table = document.getElementById("tblDayEndFlash");
				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);

			      /*row.insertCell(0).innerHTML= "<input   class=\"Box \" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+strBillNo+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; */
			      row.insertCell(0).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item0+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; 
			      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item1+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtCompStk."+(rowCount)+"\" value='"+item2+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtPhyStk."+(rowCount)+"\" value='"+item3+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(4).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtVariance."+(rowCount)+"\" value='"+item4+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      /* row.insertCell(5).innerHTML= "<input   class=\"Box \" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+dblBillAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; */
			      row.insertCell(5).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" id=\"txtDate."+(rowCount)+"\" value='"+item5+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(6).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item6+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(7).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtCompStk."+(rowCount)+"\" value='"+item7+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(8).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtPhyStk."+(rowCount)+"\" value='"+item8+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(9).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtVariance."+(rowCount)+"\" value='"+item9+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(10).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtVariance."+(rowCount)+"\" value='"+item10+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(11).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtVariance."+(rowCount)+"\" value='"+item11+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(12).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtVariance."+(rowCount)+"\" value='"+item12+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(13).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtVariance."+(rowCount)+"\" value='"+item13+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(14).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtVariance."+(rowCount)+"\" value='"+item14+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(15).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtVariance."+(rowCount)+"\" value='"+item15+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(16).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtVariance."+(rowCount)+"\" value='"+item16+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(17).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtVariance."+(rowCount)+"\" value='"+item17+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(18).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtVariance."+(rowCount)+"\" value='"+item18+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      row.insertCell(19).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtVariance."+(rowCount)+"\" value='"+item19+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
			      

			}

		 	
		 	function funFillTotalCol(rowData) 
			{
				var table = document.getElementById("tblTotal");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    
			    for(var i=0;i<rowData.length;i++)
			    	 {
			   		
			 	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList["+(rowCount)+"].strCol"+(i)+"\" id=\"rowList["+(rowCount)+"].strCol"+(i)+"\" value='"+rowData[i]+"' />";
			   		
			   		 }
				
			  
			}
			

</script>


</head>

<body onload="funLoadTableData()">
	<div id="formHeading">
		<label>Day End Flash Report</label>
	</div>
	<br />
	<br />
	<s:form name="POSDayEndFlashReport" method="POST" action="rptPOSDayEndFlashReport.html?saddr=${urlHits}"
		target="_blank">
		<div>
			<div>

				<table class="masterTable" style="margin-left: 100px;">
                 <tr>
					<td width="140px">POS Name</td>
					<td colspan="3"><s:select id="cmbPOSName" name="cmbPOSName" path="strPOSName" cssClass="BoxW124px" items="${posList}" >
					
					 </s:select></td>
				
						
						<td><label>From Date</label></td>
						<td><s:input id="txtFromDate" required="required" path="fromDate" pattern="\d{1,2}-\d{1,2}-\d{4}"
								cssClass="calenderTextBox" /></td>

						<td><label>To Date</label></td>
						<td><s:input id="txtToDate" required="required" path="toDate"
								pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox" /></td>

					</tr>
					

				</table>
			</div>
			
			</table>
						
						<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 400px; margin-left: 100px; overflow-x: scroll; width: 80%;">
						
									<table id="tblCol"
										style="width: 200%; border: #0F0; table-layout: fixed; overflow: scroll"
										class="transTablex col4-center">
										<th >
										  
          
            						<th style="border: 1px white solid;width:50%"><label>POS</label></th>
									<th style="border: 1px white solid;width:50%"><label>POS Date</label></th>
									<th style="border: 1px  white solid;width:30%"><label>HD Amt</label></th>
									<th style="border: 1px  white solid;width:50%"><label>Dining Amt</label></th>
									<th style="border: 1px  white solid;width:30%"><label>Take Away</label></th>
									<th style="border: 1px  white solid;width:30%"><label>Total Sale</label></th>
									<th style="border: 1px  white solid;width:30%"><label>Float</label></th>
									<th style="border: 1px  white solid;width:30%"><label>Cash</label></th>
									<th style="border: 1px  white solid;width:40%"><label>Advance</label></th>
										<th style="border: 1px  white solid;width:40%"><label>Transfer In</label></th>
										<th style="border: 1px  white solid;width:60%"><label>Total Receipt</label></th>
										<th style="border: 1px  white solid;width:10%"><label>Pay</label></th>
										<th style="border: 1px  white solid;width:40%"><label>With Drawal</label></th>
										<th style="border: 1px  white solid;width:30%"><label>Tranf Out</label></th>
									
									<th style="border: 1px  white solid;width:30%"><label>Refund</label></th>
									<th style="border: 1px  white solid;width:30%"><label>Total Pay</label></th>
									<th style="border: 1px  white solid;width:50%"><label>Cash In Hand</label></th>
									<th style="border: 1px  white solid;width:50%"><label>No Of Bill</label></th>
									<th style="border: 1px  white solid;width:50%"><label>No Of Voided Bill</label></th>
									<th style="border: 1px  white solid;width:50%"><label>No Of Modify Bil</label></th>
								</th>
								</table>
								
								
									
									<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 550px; margin: auto; overflow-x: hidden; overflow-y: hidden; width: 200%;">
									<table id="tblDayEndFlash"
										style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
										class="transTablex col11-center">	
														    
											<col style="width:50%"><!--  COl1   -->
											<col style="width:50%"><!--  COl2   -->
											<col style="width:30%"><!--  COl3   -->
											<col style="width:50%"><!--  COl4   -->
											<col style="width:30%"><!--  COl5   -->
											<col style="width:30%"><!--  COl6   -->
											<col style="width:30%"><!--  COl7   -->
											<col style="width:30%"><!--  COl8   -->
											<col style="width:40%"><!--  COl9   -->
											<col style="width:40%"><!--  COl10   -->
											<col style="width:60%"><!--  COl11   -->
											<col style="width:10%"><!--  COl12   -->
											<col style="width:40%"><!--  COl13   -->
											<col style="width:30%"><!--  COl14   -->
											<col style="width:30%"><!--  COl15   -->
											<col style="width:30%"><!--  COl16   -->
											<col style="width:50%"><!--  COl17   -->
											<col style="width:50%"><!--  COl18  -->
											<col style="width:50%"><!--  COl19  -->
											<col style="width:50%"><!--  COl20  -->
											 
																			
															
									</table>
									</div>
									
								</div>
	
		</table>		
		</div>
	</div>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 80px; margin-left: 100px; overflow-x: scroll; overflow-y: scroll; width: 80%;">
				
	
				<table id="tblTotal" class="transTablex"
					style="width: 120%; ">
					
											<col style="width:50%"><!--  COl1   -->
											<col style="width:50%"><!--  COl2   -->
											<col style="width:30%"><!--  COl3   -->
											<col style="width:50%"><!--  COl4   -->
											<col style="width:30%"><!--  COl5   -->
											<col style="width:30%"><!--  COl6   -->
											<col style="width:30%"><!--  COl7   -->
											<col style="width:30%"><!--  COl8   -->
											<col style="width:40%"><!--  COl9   -->
											<col style="width:40%"><!--  COl10   -->
											<col style="width:60%"><!--  COl11   -->
											<col style="width:10%"><!--  COl12   -->
											<col style="width:40%"><!--  COl13   -->
											<col style="width:30%"><!--  COl14   -->
											<col style="width:30%"><!--  COl15   -->
											<col style="width:30%"><!--  COl16   -->
											<col style="width:50%"><!--  COl17   -->
											<col style="width:50%"><!--  COl18  -->
											<col style="width:50%"><!--  COl19  -->
											<col style="width:50%"><!--  COl20  -->
			
				</table>
			</div>
		</div>
		<br />
		<br />
		<p align="center">
			<input type="button" value="Execute" class="form_button"id="execute" /> 
				<input type="submit" value="Export"	class="form_button" id="export" /> 
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