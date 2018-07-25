<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	var fieldName;
    
	
	function funValidateFields()
	{
		var flag=false;
		if($("#strBillNo").val().trim().length==0)
		{
			alert("Please Select Bill No.");
		}
		else
		{
			flag=true;
			
			/*
			var fromDate=$("#dteFromDate").val();
			var toDate=$("#dteToDate").val();
			
			var fd=fromDate.split("-")[0];
			var fm=fromDate.split("-")[1];
			var fy=fromDate.split("-")[2];
			
			var td=toDate.split("-")[0];
			var tm=toDate.split("-")[1];
			var ty=toDate.split("-")[2];
			
			$("#dteFromDate").val(fy+"-"+fm+"-"+fd);
			$("#dteToDate").val(ty+"-"+tm+"-"+td);*/
			
			var billNo=$("#strBillNo").val();
			var fromDate="";
			var toDate="";
			//window.open(getContextPath()+"/rptBillPrinting.html?fromDate="+fy+"-"+fm+"-"+fd+"&toDate="+ty+"-"+tm+"-"+td+"&billNo="+billNo+"");
			window.open(getContextPath()+"/rptBillPrinting.html?fromDate="+fromDate+"&toDate="+toDate+"&billNo="+billNo+"");
		}	
		
		return flag;
	}
	
	function funSetBillNo(billNo)
	{
		$("#strBillNo").val(billNo);
	}	
	
	/**
	* Success Message After Saving Record
	**/
	 $(document).ready(function()
				{
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
		
		 var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
		  var dte=pmsDate.split("-");
		  $("#txtPMSDate").val(dte[2]+"-"+dte[1]+"-"+dte[0]);

	});
	/**
		* Success Message After Saving Record
	**/
	
	/* set date values */
	
	/*
	function funSetDate(id,responseValue)
	{
		var id=id;
		var value=responseValue;
		var date=responseValue.split(" ")[0];
		
		var y=date.split("-")[0];
		var m=date.split("-")[1];
		var d=date.split("-")[2];
		
		$(id).val(d+"-"+m+"-"+y);		
	}*/
	
	//set date
	$(document).ready(function(){
		
		/*
		$("#dteFromDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#dteFromDate").datepicker('setDate', 'today');
		
		
		$("#dteToDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#dteToDate").datepicker('setDate', 'today');
		*/
	});
	
	function funSetData(code)
	{
		switch(fieldName)
		{
			case "billNo":
				$("#strBillNo").val(code);
				break;
		}
	}

	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>Split Bill</label>
	</div>

<br/>
<br/>

	<s:form name="SplitBill" method="POST" action="splitingBill.html?saddr=${urlHits}">

		<table class="masterTable">
		
			<tr>
				<td><label>Bill No.</label></td>
				<td colspan="3"><s:input id="strBillNo" path="strBillNo"  cssClass="searchTextBox" ondblclick="funHelp('billNo')"/></td>													
			</tr>
			
			<tr>
				<td><label>Split Type</label></td>
<%-- 				<td colspan="3"><select id="strBillNo"   cssClass="searchTextBox" > --%>
<!-- 				<option value="Liquor">Liquor</option> -->
<!-- 				<option value="Food">Food</option> -->
				
<%-- 				</select> --%>
				<td><s:input type="checkbox" name="splitType" path="strSplitType" value="ROOM"/>&nbsp;&nbsp;ROOM&nbsp;&nbsp;
				<s:input type="checkbox"  name="splitType" path="strSplitType" value="FOOD"></s:input>&nbsp;&nbsp;FOOD&nbsp;&nbsp;
				<s:input type="checkbox"  name="splitType" path="strSplitType" value="LIQUOR"></s:input>&nbsp;&nbsp;LIQUOR&nbsp;&nbsp;
				<s:input type="checkbox"  name="splitType" path="strSplitType" value="GUEST WISE"></s:input>&nbsp;&nbsp;GUEST WISE&nbsp;&nbsp;
				</td>													
			</tr>
		
			
		</table>
					
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button"  />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>						
	</s:form>
</body>
</html>
