<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Receipt Issue Consolidated</title>
</head>
<script type="text/javascript">
	/**
	 * Ready Function for Ajax Waiting and reset form
	 */
    $(document).ready(function() 
    		{
    			$(document).ajaxStart(function()
    		 	{
    			    $("#wait").css("display","block");
    		  	});
    		 	
    			$(document).ajaxComplete(function()
    			{
    			    $("#wait").css("display","none");
    			});	
    		});
    
    /**
	 * Ready Function for Initialization Text Field with default value 
	 * Set Date in date picker
	 * Getting session value
	 */
      $(function() 
    		{
	    	   var startDate="${startDate}";
				var arr = startDate.split("/");
				Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
	    	  
    			$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
    			$("#txtFromDate" ).datepicker('setDate', Dat); 
    			$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
    			$("#txtToDate" ).datepicker('setDate', 'today'); 
    			$("#cmbProperty").val('<%=session.getAttribute("propertyCode").toString()%>');
    			$("#lblLocName").text('<%=session.getAttribute("locationName").toString()%>');
    		});	
      
    var fieldName="";
    /**
	 * Open help windows
	 */
    function funHelp(transactionName)
  	{
      	fieldName = transactionName;
      	var propCode=$("#cmbProperty").val();
  	//	window.showModalDialog("searchform.html?formname="+transactionName+"&propCode="+propCode+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
  		window.open("searchform.html?formname="+transactionName+"&propCode="+propCode+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
  	}
      
    /**
	 * Set Data after selecting form Help windows
	 */
    function funSetData(code)
  	{
  		switch (fieldName) 
  		{
  		    case 'PropertyWiseLocation':
  		    	funSetLocation(code);
  		        break;
  		}
  	}
    
     /**
	  * Set location data
	 **/
      function funSetLocation(code) {
  		var searchUrl = "";
  		searchUrl = getContextPath()
  				+ "/loadLocationMasterData.html?locCode=" + code;

  		$.ajax({
  			type : "GET",
  			url : searchUrl,
  			dataType : "json",
  			success : function(response) {
  				if (response.strLocCode == 'Invalid Code') {
  					alert("Invalid Location Code");
  					$("#txtLocCode").val('');
  					$("#lblLocName").text("");
  					$("#txtLocCode").focus();
  				} else {
  					$("#txtLocCode").val(response.strLocCode);						
  					$("#lblLocName").text(response.strLocName);
  				}
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
     
      /**
	   * Checking Validation before submiting the data
	  **/
      function btnSubmit_Onclick() 
		{	
    	    var spFromDate=$("#txtFromDate").val().split('-');
			var spToDate=$("#txtToDate").val().split('-');
			var FromDate= new Date(spFromDate[2],spFromDate[1]-1,spFromDate[0]);
			var ToDate = new Date(spToDate[2],spToDate[1]-1,spToDate[0]);
			if(!fun_isDate($("#txtFromDate").val())) 
		    {
				 alert('Invalid From Date');
				 $("#txtFromDate").focus();
				 return false;  
		    }
		   if(!fun_isDate($("#txtToDate").val())) 
		   {
				 alert('Invalid To Date');
				 $("#txtToDate").focus();
				 return false;  
		   }
			if(ToDate<FromDate)
			{
					alert("To Date Should Not Be Less Than Form Date");
			    	$("#txtToDate").focus();
					return false;		    	
			}
			if($("#txtLocCode").val().trim().length==0)
				{
					alert("Please Select Location");
					return false;
				}
      		document.forms["frmReceiptIssueConsolidated"].submit();
		}
     </script>
<body>
<div id="formHeading">
		<label>Receipt Issue Consolidated</label>
	</div>
<s:form name="frmReceiptIssueConsolidated" method="POST" action="rptReceiptIssueConsolidated.html" target="_blank">
	   		<br />
	   		<table class="masterTable">
			    <tr>
					<td width="10%"><label>From Date :</label></td>
					<td colspan="1" width="10%"><s:input id="txtFromDate" path="dtFromDate" required="true" readonly="readonly" cssClass="calenderTextBox"/></td>
					<td width="10%"><label>To Date :</label></td>
					<td colspan="1"><s:input id="txtToDate" path="dtToDate" required="true" readonly="readonly" cssClass="calenderTextBox"/>
					</td>	
				</tr>
				<tr>
					<td width="10%">Property Code</td>
					<td width="20%">
						<s:select id="cmbProperty" name="propCode" path="strPropertyCode" cssClass="longTextBox" cssStyle="width:100%" onchange="funChangeLocationCombo();">
			    			<s:options items="${listProperty}"/>
			    		</s:select>
					</td>
						
					<td width="5%"><label>Location</label></td>
					<td colspan="2">
					<s:input id="txtLocCode" path="strLocationCode" required="required" value="${locationCode}"
				     ondblclick="funHelp('PropertyWiseLocation')" cssClass="searchTextBox"/>
				      <label id="lblLocName" ></label>
					</td>
				</tr>
			<tr>
				<td width="10%"><label>Report Type</label></td>
				<td colspan="3"><s:select id="cmbDocType" path="strDocType"
						cssClass="BoxW124px">
						<s:option value="PDF">PDF</s:option>
						<s:option value="XLS">EXCEL</s:option>
						<s:option value="HTML">HTML</s:option>
						<s:option value="CSV">CSV</s:option>
					</s:select></td>
			</tr>
			
		</table>
			<br>
			<p align="center">
				 <input type="button" value="Submit" class="form_button" onclick="return btnSubmit_Onclick();"/>
				 <input type="button" value="Reset" class="form_button" onclick="funResetFields()"/>			     
			</p>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
		</s:form>
</body>
</html>