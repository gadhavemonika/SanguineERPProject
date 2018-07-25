<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GROUP MASTER</title>
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
	border: #c0c0c0 1px solid;
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


	
	$(document).ready(function() {
		var message='';
		<%if (session.getAttribute("success") != null) {
            if(session.getAttribute("successMessage") != null){%>
            message='<%=session.getAttribute("successMessage").toString()%>';
            <%
            	session.removeAttribute("successMessage");
            }
			boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
			session.removeAttribute("success");
			if (test) {
			%>	
			alert(message);
		<%
		}}%>		

		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});

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
				
	    			
				funFetchColNames();
	    			}); 
		$("[type='reset']").click(function(){
			location.reload(true);
		});
		
		$("form").submit(function(event){
			var table = document.getElementById("tblBillWiseSettlement");
			var rowCount = table.rows.length;
			if (rowCount > 2){
				$("#txtFromDate").val(fDate);
				$("#txtToDate").val(tDate);
				return true;
			} else {
				alert("Data Not Available");
				return false;
			}
		});

		$("#btnSubmit").click(function(event) {
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
	
	
	function funDeleteTableAllRows()
	{
		$('#tblBillWiseSettlement tbody').empty();
		$('#tblTotal tbody').empty();
		var table = document.getElementById("tblBillWiseSettlement");
		var rowCount1 = table.rows.length;
		if(rowCount1==0){
			return true;
		}else{
			return false;
		}
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
        	 return false
         }
	}

	
	function funFetchColNames() {
		var operationType=$('#cmbOperationType').val();
		var settlementName=$('#cmbSettleName').val();
		var posName=$('#cmbPOSName').val();
		var viewBy=$('#cmbViewBy').val();
			var gurl = getContextPath() + "/loadBillwiseSettlementSalesSummary.html";
	
		$.ajax({
			type : "GET",
			data:{ fromDate:fDate,
					toDate:tDate,
					viewBy:viewBy,
					operationType:operationType,
					settlementName:settlementName,
					posName:posName,
					
				},
			url : gurl,
			dataType : "json",
			success : function(response) {
				if (response.RowCount == 0  ) {
					alert("Data Not Found");
				} else {
					
					//Add Sub Category Headers
					funAddHeaderRow(response.Header);
					
					//Add Size Names And Headers
					
					$.each(response,function(i,item){
						if(i<response.RowCount)				
							funAddUnderLine(item,"tblBillWiseSettlement");
						});
					
					
					
				}//Else block Of Response
				
				funAddTotalHeaderRow(response.Header);
				funAddUnderLine(response.Total,"tblTotal");
			},
			error : function(jqXHR, exception) {
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
	
	
	
 	function funAddHeaderRow(rowData){
		var table = document.getElementById("tblBillWiseSettlement");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
		    for(var i=0;i<rowData.length;i++){
		    	if(i==0){
	    			 row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"colRow["+(rowCount)+"].strCol"+(i)+"\" value='"+rowData[i]+"' />";
	    		 } else {
		    			row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"colRow["+(rowCount)+"].strCol"+(i)+"\" value='"+rowData[i]+"' />";
		    			
		    		}
				}
		} 
	
 	function funAddTotalHeaderRow(rowData){
		var table = document.getElementById("tblTotal");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    var viewBy=$('#cmbViewBy').val();
	    var cnt=0;
	    if(viewBy=="ITEM'S GROUP WISE")
	    	cnt=rowData.length-1;
	    else
	    	cnt=rowData.length;
		    for(var i=0;i<cnt;i++){
		    	if(i==0){
	    			 row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"colRow["+(rowCount)+"].strCol"+(i)+"\" value=\"Total \" />";
	    		 }
		    	else if(i==1){
	    			 row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"colRow["+(rowCount)+"].strCol"+(i)+"\" value=\" \" />";
	    		 }
		    	else if(i==2){
	    			 row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"colRow["+(rowCount)+"].strCol"+(i)+"\" value=\"POS \" />";
	    		 }else {
		    			row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"colRow["+(rowCount)+"].strCol"+(i)+"\" value='"+rowData[i]+"' />";
		    			
		    		}
				}
		} 
		
	
	function funAddUnderLine(rowData,tableId) 
	{
		var table = document.getElementById(tableId);
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    var item;
	    for(var i=0;i<rowData.length;i++)
	    	 {
	   			if(i>2)
	   				 item=Math.round(rowData[i]);
	   			else
	   			 item=rowData[i];
	 	   		row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList["+(rowCount)+"].strCol"+(i)+"\" id=\"rowList["+(rowCount)+"].strCol"+(i)+"\" value='"+item+"' />";
	   		
	   		 }
		
	  
	}
	
	
</script>


</head>

<body>
	<div id="formHeading">
		<label>POS Bill Wise Settlement Sales Summary Flash</label>
	</div>
	<br />
	<br />
	<s:form name="POSBillWiseSettlementSalesSummeryFlash" method="POST"
		action="rptPOSBillWiseSettlementSalesSummeryFlash.html?saddr=${urlHits}"
		target="_blank">
		<div>
			<div>

				<table class="masterTable" style="margin:auto;">

					<tr>
						<td width="140px">POS Name</td>
						<td><s:select id="cmbPOSName" name="cmbPOSName"
								path="strPOSName" cssClass="BoxW124px" items="${posList}">

							</s:select></td>
						<td><label>From Date</label></td>
						<td><s:input id="txtFromDate" required="required"
								path="fromDate" pattern="\d{1,2}-\d{1,2}-\d{4}"
								cssClass="calenderTextBox" /></td>

						<td><label>To Date</label></td>
						<td><s:input id="txtToDate" required="required" path="toDate"
								pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox" /></td>
					<td><label>View By</label></td>
						<td><s:select id="cmbViewBy" path="strViewBy"
								cssClass="BoxW124px">
								<s:option value="ITEM'S GROUP WISE">ITEM'S GROUP WISE</s:option>
								<s:option value="NONE">NONE</s:option>

							</s:select></td>
					</tr>
					<tr>
						<td width="140px">Settlement Name</td>
							<td><s:select id="cmbSettleName" name="cmbSettleName"
								path="strSettlementName" cssClass="BoxW124px" items="${settlementList}">

							</s:select></td>
						

						<td><label>Operation Type</label></td>
						<td><s:select id="cmbOperationType" path="strOperationType"
								cssClass="BoxW124px">
								<s:option value="All">All</s:option>
								<s:option value="Dine In">Dine In</s:option>
								<s:option value="Direct Biller">Direct Biller</s:option>
								<s:option value="Home Delivery">Home Delivery</s:option>
								<s:option value="Take Away">Take Away</s:option>

							</s:select></td>
						<td></td>
						<td></td><td></td>
						<td></td>
					</tr>

				</table>
			</div>
			
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 500px; margin:auto; overflow-x: scroll; overflow-y: scroll; width: 80%;">
				
				<table id="tblBillWiseSettlement" class="transTablex"
					style="width: 100%; text-align: center !important;">
				</table>
				
			</div>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 100px; margin:auto; overflow-x: scroll; overflow-y: scroll; width: 80%;">
				
	
				<table id="tblTotal" class="transTablex"
					style="width: 100%; text-align: center !important;">
				</table>
			</div>
		</div>
		<br />
		<br />
		<p align="center">
			<input type="button" value="Display" class="form_button"
				id="btnSubmit" /> <input type="submit" value="Export"
				class="form_button" id="submit" /> <input type="reset" value="Reset"
				class="form_button" id="btnReset" />

		</p>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 50%; left: 50%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>

	</s:form>

</body>
</html>