<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

 <style type="text/css">
 .cell{
 	background: inherit;
    border: 0 solid #060006;
    font-family: Arial,Helvetica,sans-serif;
    font-size: 11px;
    font-weight: bold;
    outline: 0 none;
    padding-left: 0;
    width: 40px
 }
 
  .header{
 	background: inherit;
    border: 0 solid #060006;
    font-family: Arial,Helvetica,sans-serif;
    font-size: 11px;
    font-weight: bold;
    outline: 0 none;
    padding-left: 0;
    width: 80px
 }
 
 </style>
 
<script type="text/javascript">

	var tDate;

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
		
		$("form").submit(function(event){
			var table = document.getElementById("tblExciseChataiReport");
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
					var difference=parseInt(dateDiff(fromDate,toDate));
					var tempFromDate=fromDate;
					funFetchColNames(tempFromDate,tempFromDate);
						for(var i=0;i<difference;i++){
							tempFromDate=getTomorrow(tempFromDate)
							funFetchColNames(tempFromDate,tempFromDate);
						}
					
				}
			}
		});

	});
	
	function dateDiff(fromDate,toDate) {

		var dateDiff=0;
		var frmDate= fromDate.split('-');
	    var fDate = new Date(frmDate[2],frmDate[1],frmDate[0]);

	    var tDate= toDate.split('-');
	    var t1Date = new Date(tDate[2],tDate[1],tDate[0]);

    	dateDiff=t1Date-fDate;
   	 	var days = dateDiff/1000/60/60/24;
   		return days;
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
	
	function getTomorrow(fromDate) {
		
		var frmDate= fromDate.split('-');
        var date = new Date(frmDate[2], frmDate[1] - 1, frmDate[0]);
        date.setDate(date.getDate() + 1);
        return $.datepicker.formatDate('dd-mm-yy', date);
	}
	
	function funDeleteTableAllRows()
	{
		$('#tblExciseChataiReport tbody').empty();
		
		var table = document.getElementById("tblExciseChataiReport");
		var rowCount1 = table.rows.length;
		if(rowCount1==0){
			return true;
		}else{
			return false;
		}
	}
	
	function funFetchColNames(fromDate,toDate) {
		var inPeg=$('#txtInPeg').val();
		var gurl = getContextPath() + "/loadChataiReportColumns.html";
		 $.ajax({
			type : "GET",
			data:{ fromDate:fromDate,
					toDate:toDate,
					inPeg:inPeg
				},
			url : gurl,
			dataType : "json",
			async:false,
			success : function(response) {
				if (response.SizeName == '' || response.SizeName == undefined ) {
					alert("Data Not Found");
				} else {
					
					//Add Sub Category Headers
					var table = document.getElementById("tblExciseChataiReport");
  					var rowCount = parseInt(table.rows.length);
					if(rowCount<=0){
						funAddHeaderRow(response.Header,response.SubCatCode,response.CatSizeLenght);
						funAddDateInTable(fromDate);
					}else{
						funAddDateInTable(fromDate);
					}
					
					//Add Size Names And Headers
					funAddDataRows(response.SizeName);					
					funAddUnderLine(response.SizeName);
					
					funAddDataRows(response.Opening);
					funAddDataRows(response.Purchase);
					funAddDataRows(response.Total);
					funAddDataRows(response.Sales);
					funAddDataRows(response.Closing);
					funAddBlankRow();
				}//Else block Of Response
					 
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
	
	function funAddDateInTable(fromDate) 
	{
		var table = document.getElementById("tblExciseChataiReport");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
		row.insertCell(0).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"rowList["+(rowCount)+"].strRow"+(1)+"\" value= 'Date:-"+fromDate+"' />";
	}
	
	function funAddBlankRow() 
	{
		var table = document.getElementById("tblExciseChataiReport");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
		row.insertCell(0).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"rowList["+(rowCount)+"].strRow"+(1)+"\" value='"+ +"' />";
	}
	
 	function funAddHeaderRow(rowData,catCode,sizeList){
		var table = document.getElementById("tblExciseChataiReport");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
		    for(var i=0;i<rowData.length;i++){
		    	if(i==0){
	    			 row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"colRow["+(rowCount)+"].strCol"+(i+1)+"\" value='"+rowData[i]+"' />";
	    		 } else {
		    			row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"colRow["+(rowCount)+"].strCol"+(i+1)+"\" value='"+rowData[i]+"' />";
		    			var temp=catCode[i].trim();
	    				var colSpan=0;
	    				if(sizeList[temp]>0){
	    					colSpan=sizeList[temp];
    					}
			    		$('#tblExciseChataiReport tr:eq('+rowCount+') td:eq('+i+')').attr('colspan',colSpan);
		    		}
				}
		} 
	
	
	function funAddDataRows(rowData) 
	{
		var table = document.getElementById("tblExciseChataiReport");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    for(var i=0;i<rowData.length;i++){
	   		  if(i==0){
	   			 if(rowData[i].trim()=="`"){
	 	   			row.insertCell(i).innerHTML = "<input type=\"hidden\" readonly=\"readonly\" class=\"header\" name=\"rowList["+(rowCount)+"].strRow"+(i+1)+"\" value='"+rowData[i]+"' />";
	   			 }else{
	 	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"header\" name=\"rowList["+(rowCount)+"].strRow"+(i+1)+"\" value='"+rowData[i]+"' />";
	   			 }
	   		 } else {
	   				row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList["+(rowCount)+"].strRow"+(i+1)+"\" value='"+rowData[i]+"' />";
	   		 }
		}
	}
	
	function funAddUnderLine(rowData) 
	{
		var table = document.getElementById("tblExciseChataiReport");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    for(var i=0;i<rowData.length;i++){
	   		if(i==0){
	 	   			row.insertCell(i).innerHTML = "<input type=\"hidden\" readonly=\"readonly\" class=\"cell\" name=\"rowList["+(rowCount)+"].strRow"+(i+1)+"\" value='"+'`'+"' />";
	   		 } else {
	   			if(rowData[i].trim() !=""){
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList["+ (rowCount)+"].strRow"+(i+1)+"\" value='"+'----'+"' />";
	    		}else{
		    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList["+ (rowCount)+"].strRow"+(i+1)+"\" value='"+' '+"' />";
	    		}
	   		 }
		}
	    $('#tblExciseChataiReport tr:eq('+rowCount+')').hide();
	}
	
	function funAddFullUnderLineRows(rowData) 
	{
		var table = document.getElementById("tblExciseChataiReport");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    for(var i=0;i<rowData.length;i++){
	   		 if(i==0){
	   		    	row.insertCell(i).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" value='"+ +"' />";
	   		 } else {
	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList["+(rowCount)+"].strRow"+(i+1)+"\" value='"+'----'+"' />";
	   		 }
		}
	    
	    $('#tblExciseChataiReport tr:eq('+rowCount+')').hide();
	}
	
</script>

</head>
<body>
	<div id="formHeading">
	<label>Chatai Report</label>
	</div>

<br/>
<br/>

	<s:form name="ChataiReport" method="POST" action="saveChataiReportData.html?saddr=${urlHits}" target="_blank">
	<div>
	<table class="masterTable">
			<tr>
				<td>
					<label>From Date</label>
				</td>
				<td>
					<s:input type="text" id="txtFromDate" class="calenderTextBox" path="strFromDate" required="required" />
				</td>
				
				<td>
					<label>To Date</label>
				</td>
				<td>
					<s:input type="text" id="txtToDate" class="calenderTextBox" path="strToDate" required="required"/>
				</td>
				
				<td>
					<label>Unit Type</label>
				</td>
				<td>
				<s:select id="txtInPeg" path="strInPeg" cssClass="BoxW124px">
					<s:option value="Peg">PegWise</s:option>
					<s:option value="ML">MLWise</s:option>
				</s:select>
				</td>
								
			</tr>
		</table>		
		<br />
		<br />
		<div style="width:100%;overflow-x:auto !important ; overflow-y:auto !important;">
			<table id="tblExciseChataiReport" class="transTablex" style="width:100%; text-align:center !important;">
			</table>
		</div>
		<br />
		<br />
		<p align="center">
			<input type="button" value="Display" class="form_button" id="btnSubmit"/>
			<input type="submit" value="Print" class="form_button" id="submit"/>
			<input type="reset" value="Reset" class="form_button" id="btnReset"/>
			
		</p>
		</div>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>

	</s:form>
</body>
</html>
