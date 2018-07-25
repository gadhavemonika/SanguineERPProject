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
			alert("Please Select Doc No.");
		}
		else
		{
			flag=true;
			
		
			var fromDate=$("#dteFromDate").val();
			var toDate=$("#dteToDate").val();
			
			var fd=fromDate.split("-")[0];
			var fm=fromDate.split("-")[1];
			var fy=fromDate.split("-")[2];
			
			var td=toDate.split("-")[0];
			var tm=toDate.split("-")[1];
			var ty=toDate.split("-")[2];
			
			
			
			var billNo=$("#strBillNo").val();
			var against=$("#cmbType").val();
			if(against=='Reservation')
			{
				window.open(getContextPath()+"/rptProvisionBillPrinting.html?fromDate="+fromDate+"&toDate="+toDate+"&docNo="+billNo+"&against="+against+ "");
			}else{
			window.open(getContextPath()+"/rptProvisionBillPrinting.html?fromDate="+fromDate+"&toDate="+toDate+"&docNo="+billNo+"&against="+against+ "");
		    }	
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
			alert("Data Save successfully\n\n"+message);
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
	
	function funSetData(code)
	{
		switch(fieldName)
		{
			case "reservationForBill":
				funSetBillNo(code);
				break;
				
			case "checkInForProvisionBill":
				funSetBillNo(code);
				break;
		}
	}

	function funHelp(transactionName)
	{
		fieldName=transactionName;
		var fromDate=$("#dteFromDate").val();
		var toDate=$("#dteToDate").val();
		var fDate=fromDate.split("-");
	    var tDate=toDate.split("-");
	    fromDate=fDate[2]+"-"+fDate[1]+"-"+fDate[0];
	    toDate=tDate[2]+"-"+tDate[1]+"-"+tDate[0];
		var type=$("#cmbType").val();
		if(type=='Reservation')
		{
		window.open("searchform.html?formname="+transactionName+"&fromDate="+fromDate+"&toDate="+toDate+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		}else{
			transactionName="checkInForProvisionBill";
		    fieldName=transactionName;
		    window.open("searchform.html?formname="+transactionName+"&fromDate="+fromDate+"&toDate="+toDate+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		}
		}
</script>

</head>
<body>

	<div id="formHeading">
	<label>Provision Bill</label>
	</div>

<br/>
<br/>

	<s:form name="ProvisionBill" method="GET" action="">

		<table class="masterTable">
		
		   <tr>
				<td><label>From Date</label></td>
				<td><s:input type="text" id="dteFromDate" path="dteFromDate" required="true" class="calenderTextBox" /></td>
				<td><label>To Date</label></td>
				<td><s:input type="text" id="dteToDate" path="dteToDate" required="true" class="calenderTextBox" /></td>				
			</tr>
			
	       <tr>
				<td><label>Doc No.</label></td>
				<td ><s:input id="strBillNo" path="strBillNo"  cssClass="searchTextBox" ondblclick="funHelp('reservationForBill')"/></td>													
				<td> Against</td>
				<td> 
				   <select id="cmbType" class="BoxW124px">
				      <option value="Reservation">Reservation</option>
					  <option value="CheckIn">CheckIn</option>
				  </select></td>
		  </tr>
			
			
		
			
			
		</table>
					
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funValidateFields()" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>						
	</s:form>
</body>
</html>
