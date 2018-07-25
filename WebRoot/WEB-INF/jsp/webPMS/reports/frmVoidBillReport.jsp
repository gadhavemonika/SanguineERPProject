<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Void Bill Report</title>
</head>

<script>


	function funValidateFields()
	{
		var flag=false;
			flag=true;
			
			var fromDate=$("#dteFromDate").val();
			var toDate=$("#dteToDate").val();
			
			var fd=fromDate.split("-")[0];
			var fm=fromDate.split("-")[1];
			var fy=fromDate.split("-")[2];
			
			var td=toDate.split("-")[0];
			var tm=toDate.split("-")[1];
			var ty=toDate.split("-")[2];
			
			$("#dteFromDate").val(fy+"-"+fm+"-"+fd);
			$("#dteToDate").val(ty+"-"+tm+"-"+td);
			
			var against=$("#cmbType").val();
			if(against=='Detail')
			{
				window.open(getContextPath()+"/voidBillReportDetail.html?fromDate="+fy+"-"+fm+"-"+fd+"&toDate="+ty+"-"+tm+"-"+td+"&against="+against+ "");
			}else{
				window.open(getContextPath()+"/voidBillReportSummary.html?fromDate="+fy+"-"+fm+"-"+fd+"&toDate="+ty+"-"+tm+"-"+td+"&against="+against+ "");
			}
			
		return flag;
	}


//set date
		$(document).ready(function(){
			
			var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
			
			$("#dteFromDate").datepicker({
				dateFormat : 'dd-mm-yy'
			});
			$("#dteFromDate").datepicker('setDate', pmsDate);	
			
			
			$("#dteToDate").datepicker({
				dateFormat : 'dd-mm-yy'
			});
			$("#dteToDate").datepicker('setDate', pmsDate);	
		});
		
		
		
		
</script>
<body>
	<div id="formHeading">
		<label>Void Bill Report </label>
	</div>
	<s:form name="frmVoidBillReport" method="GET" action="" >
		
	<table class="masterTable">
		
			<tr>
				<td><label>From Date</label></td>
				<td><s:input type="text" id="dteFromDate" path="dteFromDate" required="true" class="calenderTextBox" /></td>
				<td><label>To Date</label></td>
				<td><s:input type="text" id="dteToDate" path="dteToDate" required="true" class="calenderTextBox" /></td>				
			</tr>
			<tr>
				<td>Void Bill Type</td>
				<td colspan="5"> 
				   <select id="cmbType" class="BoxW124px">
				      <option value="Detail">Detail</option>
					  <option value="Summary">Summary</option>
				  </select>
				</td>
			</tr>
		</table>
		<br>
	<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funValidateFields()" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>	
	</s:form>

</body>
</html>