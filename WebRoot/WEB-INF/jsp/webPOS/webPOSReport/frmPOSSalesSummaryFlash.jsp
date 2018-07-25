<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sales Summary Flash</title>
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

	 $(function() 
	    			{		
		 var POSDate="${POSDate}"
			    var startDate="${POSDate}";
			  	var Date = startDate.split(" ");
				var arr = Date[0].split("-");
				Dat=arr[2]+"-"+arr[1]+"-"+arr[0];	
				$("#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
				$("#txtFromDate" ).datepicker('setDate', Dat); 
				$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
				$("#txtToDate" ).datepicker('setDate', Dat); 
    				
	    			
    				funExecute();
	    			}); 

	 
	 function funExecute(){
		 
		 var fromDate = $('#txtFromDate').val();
		 var toDate = $('#txtToDate').val();
		 var payMode = $('#cmbPaymentMode').val();
		 var posName = $('#cmbPOSName').val();
		 var reportType=$('#cmbDocType').val();
		 $('#tblsalesSumFlash tbody').empty();		 
		 funFillTableData(fromDate,toDate,payMode,posName,reportType); 
		 
	 }
		
	 
	 
	 function funFillTableData(fromDate,toDate,payMode,posName,reportType)
	 {
		 
		 
		 var searchurl=getContextPath()+"/loadColumnData.html?payMode=" + payMode;
		 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        
		        success: function (response) {
		        
		                	
// 		            	$.each(response,function(i,item){
		            	    
		            		funfillColumnData(response,reportType);
		            				            				            	
			            	 
// 		            	});
		    
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
	       
 		 funFillTableRow(fromDate,toDate,payMode,posName,reportType);
	 }
	
	 function funfillColumnData(colData,reportType)
	 {

		
			var table = document.getElementById("tblsalesSumFlash");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
			if(reportType=='Daily')  
			{
		    row.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(rowCount)+"\" value=PosCode >";
		    row.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(rowCount)+"\" value=PosName >";
		    row.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(rowCount)+"\" value=PosDate >";
			}else{
				row.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(rowCount)+"\" value=PosCode >";
			    row.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(rowCount)+"\" value=Month >";
			    row.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(rowCount)+"\" value=Year >";	
				
			}
		    
		    for(var i=0;i<colData.length;i++){
			row.insertCell(i+3).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(rowCount)+"\" value='"+colData[i]+"' >";
					
		}
	 }

	 
	 function funFillTableRow(fromDate,toDate,payMode,posName,reportType){
		 
		 var searchurl=getContextPath()+"/loadPaymentData.html?fromDate=" + fromDate+"&toDate="+toDate+"&payMode="+payMode+"&posName="+posName+"&reportType="+reportType;
		 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        
		        success: function (response) {
		        
		                	
// 		            	$.each(response,function(i,item){
		            	    
		            		funfillRowData(response);
		            				            				            	
			            	 
// 		            	});
		    
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
// 	 function funfillRowData(dteBillDate,strPosName,strSettelmentDesc,strPOSCode,dblSettlementAmt)
 function funfillRowData(data)
	 {
 	 	
	    var tableHeaderData = document.getElementById("tblsalesSumFlash");
		var colCount=tableHeaderData.rows[0].cells.length;
		var table = document.getElementById("tblsalesSumFlash");
	 	var rowCount = table.rows.length;
	 	
	 	
	 	var row ;
		
		for(var cnt=0;cnt<data.length;cnt++)
		{
			row = table.insertRow(rowCount);
	 	  for(var i=0;i<colCount;i++)
	 	  {
	 		row.insertCell(i).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(cnt)+"\" value='"+data[cnt]+"'>";
	 		cnt++;
	 	  }
	 	 rowCount++;
	 	 cnt--;
		}
		
		var table = document.getElementById("tblsalesSumFlash");
		var col=table.rows[0].cells.length;
	 	var rowCoun = table.rows.length;
	 	var row1 = table.insertRow(rowCoun);
	 	row1.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(j)+"\" value='"+ +"'>";
		row1.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(j)+"\" value='"+ +"'>";
		row1.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(j)+"\" value='Total'>";
	 	for(var j=3;j<col;j++)
		{
			var amount=0.0;
			
		   for(var i=1;i<rowCoun;i++)
		   {
			 
				  var description=table.rows[i].cells[j].innerHTML;
				  var data=description.split('value');
				  var dblAount=data[1].split('=');
				  var amountData=parseFloat(dblAount[1].substring(1,dblAount[1].length-2));
				  amount=amount+amountData;
		   }
		   row1.insertCell(j).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(j)+"\" value='"+amount+"'>";
		}
		
	 }
		
		 	 	 

	 
	</script>






</head>

<body>
	<div id="formHeading">
		<label>Sales Summary Flash</label>
	</div>
	<s:form name="SalesSummaryFlash" method="POST"
		action="rptsalesFlashSummary.html?saddr=${urlHits}" target="_blank">

		<br />
		<br />
		<table class="masterTable">

			<tr>
				<td width="140px">POS Name</td>
				<td colspan="3"><s:select id="cmbPOSName" name="cmbPOSName"
						path="strPOSName" cssClass="BoxW124px" items="${posList}">

					</s:select></td>
			</tr>
			<tr>
				<td><label>From Date</label></td>
				<td><s:input id="txtFromDate" required="required"
						path="fromDate" pattern="\d{1,2}-\d{1,2}-\d{4}"
						cssClass="calenderTextBox" /></td>
				<td><label>To Date</label></td>
				<td><s:input id="txtToDate" required="required" path="toDate"
						pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox" /></td>

			</tr>
			<tr>
				<td><label>Payment Mode</label></td>
				<td><s:select id="cmbPaymentMode" name="cmbReportType"
						path="strPayMode" cssClass="BoxW124px" items="${payModeList}"></s:select></td>

				<td><label>Report Type</label></td>
				<td><s:select id="cmbDocType" path="strDocType"
						cssClass="BoxW124px">
						<s:option value="Daily">Daily</s:option>
						<s:option value="Monthly">Monthly</s:option>

					</s:select></td>
			</tr>

			<tr>


			</tr>


		</table>
		<br />
		<br/>
		<!-- 		<div style="width:100%;overflow-x:auto !important ; overflow-y:auto !important;"> -->
		<!-- 			<table id="tblsalesSumFlash" class="transTablex" style="width:100%; text-align:center !important;"> -->

		<%-- 			<c:forEach items="${payModeList}" var="draw">	 --%>
		<%-- 						<td >${draw}</td> --%>



		<%-- 			</c:forEach>	 --%>


		<!-- 			</table> -->
		<!-- 		</div> -->

<!-- 		<div class="dynamicTableContainer" style="height: 300px;"> -->
<!-- 			<table id="tblsalesSumFlash" -->
<!-- 				style="height: 28px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;"> -->

<!-- 				<tr bgcolor="#72BEFC"> -->
<!-- 					<td>POS</td> -->
<!-- 					<td>POS Name</td> -->
<!-- 					<td>POS Date</td> -->

<%-- 					<c:forEach items="${payModeList}" var="draw"  varStatus = "status"> --%>
						

<%-- 						<c:if test="${status.first}"> --%>
						        
<%-- 						    </c:if> --%>
<%-- 						    <c:if test="${!status.first}"> --%>
<%-- 						        <td id=${draw}>${draw}</td> --%>
<%-- 							</c:if> --%>
<%-- 					</c:forEach> --%>
<!-- 				</tr> -->
<!-- 			</table> -->

<!-- 							<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;"> -->
<!-- 								<table id="" -->
<!-- 									style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll" -->
<!-- 									class="transTablex col8-center"> -->
<!-- 									<tbody> -->
<%-- 										<col style="width: 140px;"> --%>
<%-- 										<col style="width: 140px;"> --%>
<%-- 										<col style="width: 50px;"> --%>
<%-- 										<col style="width: 0px;"> --%>
<%-- 										<col style="width: 0px;"> --%>
<%-- 										<col style="display:none;"> --%>
<!-- 									</tbody> -->
<!-- 								</table> -->
<!-- 							</div> -->
		
		<br />
		<div style="width:100%;overflow-x:auto !important ; overflow-y:auto !important;">
			<table id="tblsalesSumFlash" class="transTablex" style="width:100%; text-align:center !important;">
			</table>
		</div>
		<br />
		<p align="center">
			<input type="button" value="Execute" onClick="funExecute()"
				tabindex="3" class="form_button" /> <input type="submit"
				value="Submit" tabindex="3" class="form_button" /> <input
				type="reset" value="Reset" class="form_button"
				onclick="funResetFields()" />
		</p>
		
	</s:form>

</body>
</html>