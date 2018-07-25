<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="sp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Delivery Schedule</title>

<script type="text/javascript">


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


	var code='';
	<%
	if(null!=session.getAttribute("rptDSCode"))
	{%>
		code='<%=session.getAttribute("rptDSCode").toString()%>';
		<%session.removeAttribute("rptDSCode");%>
		var isOk=confirm("Do You Want to Generate Slip?");
		if(isOk)
		{
			window.open(getContextPath()+"/openRptDSSlip.html?rptDSCode="+code,'_blank');
		}
		
				
		
	<%}%>
	
		});

$(function()
		{	

   			$("#txtDSDate").datepicker({ dateFormat: 'dd-mm-yy' });		
			$("#txtDSDate" ).datepicker('setDate', 'today');		
			$(document).ajaxStart(function(){
			    $("#wait").css("display","block");
			  });
			  $(document).ajaxComplete(function(){
			    $("#wait").css("display","none");
			  });
			  date=$("#txtDSDate").val();	 
			  
			  var  locationCode='<%=session.getAttribute("locationCode").toString()%>';
			  var  locationName='<%=session.getAttribute("locationName").toString()%>';
			  $("#txtLocCode").val(locationCode);	
			  $("#lblLocName").text(locationName);	
			  
		});
		
		
	/**
	 * Checking Validation before submiting the data
	 */
	function funCallFormAction(actionName,object) 
	{	
	
		if(!fun_isDate($("#txtDSDate").val())){
			 alert('Invalid Date');
	            $("#txtDSDate").focus();
	            return false;
		}
		var table = document.getElementById("tblDeliverySchedule");
	    var rowCount = table.rows.length;
	}
 
	/**
	 * Reset function  
	 */
	function funResetFields()
	{
		location.reload(true); 
	} 	
	
	
	/**
	 * Open help window
	 */
	var fieldName="";	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
		 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;top=500,left=500")
	
		}	
	
	
	/**
	 * Set Data after selecting form Help windows
	 */
	function funSetData(code)
	{					
		var searchUrl="";			
		switch (fieldName)
		{
			case 'dsPOData':
					$('#txtDocCode').val(code);		
			break;
			
			case 'locationmaster':
				funSetLocationCode(code);					
			break;
			
			case 'dscode':
				funSetDSCode(code);					
			break;
			
			case 'dsPOData':
				funOpenPOForDS(code);					
			break;
			
		}
	}
	
	
	function funfillbutton(){
		var code=$('#txtDocCode').val();
		
		if(code.length>0){
			funSetPOCode(code);
		}
		else{
			alert("Please fill DS Code");
		}			
	}
	
	/**
	 * Get and set PO code
	 */
	function funSetPOCode(code)
	{
		var searchUrl="";
		//alert(showReqStk);
		searchUrl=getContextPath()+"/loadPOForDSRest.html?poCode="+code;
		//alert(searchUrl);
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	if('Invalid Code' == response.strPOCode){
			    		alert('Invalid PO Code');
				    	$("#txtDocCode").val('');

			    	}
			    	else
			    	{
			    		funRemoveProductRows();
			    		$.each(response, function(i,item)
			                    {	
			    		funAddDSRow(item.strProdCode, item.strProdName,item.dblQty, item.strUOM,
			    				item.dblUnitPrice, item.dblTotalPrice, '');
			                    });
				    	
			    	}
 				},
			    error: function(jqXHR, exception) {
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
	
	

		function funAddDSRow(strProdCode, strProdName,dblQty, strUOM, dblUnitPrice, dblTotalPrice,remark) 
		{			
			
		     
		    var table = document.getElementById("tblDeliverySchedule");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input class=\"Box\" size=\"8%\" name=\"listObjDeliveryScheduleModuledtl["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";
		    row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"60%\" name=\"listObjDeliveryScheduleModuledtl["+(rowCount)+"].strProdName\" id=\"strProdName."+(rowCount)+"\" value='"+strProdName+"' />";
		    row.insertCell(2).innerHTML= "<input class=\"Box\" readonly=\"readonly\" size=\"8%\" name=\"listObjDeliveryScheduleModuledtl["+(rowCount)+"].strUOM\" id=\"strUOM."+(rowCount)+"\" value='"+strUOM+"' />";
		    row.insertCell(3).innerHTML= "<input type=\"text\" required = \"required\" size=\"7%\" name=\"listObjDeliveryScheduleModuledtl["+(rowCount)+"].dblQty\" id=\"dblQty."+(rowCount)+"\"  value="+dblQty+" onblur=\"funUpdatePrice(this);\"  class=\"decimal-places inputText-Auto QtyCell\" >";	    
		    row.insertCell(4).innerHTML= "<input type=\"Box\" readonly=\"readonly\" size=\"7%\" required = \"required\" name=\"listObjDeliveryScheduleModuledtl["+(rowCount)+"].dblUnitPrice\" id=\"dblUnitPrice."+(rowCount)+"\"  value="+dblUnitPrice+"  class=\"decimal-places inputText-Auto\">";		    
			row.insertCell(5).innerHTML= "<input class=\"Box totalValueCell\" style=\"text-align: right;\" size=\"7%\" name=\"listObjDeliveryScheduleModuledtl["+(rowCount)+"].dblTotalPrice\" id=\"dblTotalPrice."+(rowCount)+"\" readonly=\"true\"  value="+dblTotalPrice+">";
			row.insertCell(6).innerHTML= "<input type=\"Box\" size=\"8%\" name=\"listObjDeliveryScheduleModuledtl["+(rowCount)+"].strRemarks\"  value='"+remark+"' />";
			row.insertCell(7).innerHTML= '<input type="button" value = "Delete"  class="deletebutton" onClick="Javacsript:funDeleteRow(this)">'; 
			 
		    return false;	
	}
		
		/**
		 * Delete a particular record from a grid
		 */
		function funDeleteRow(obj)  
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblDeliverySchedule");
		    table.deleteRow(index);
	
		}
		
		/**
		 * Remove all product from grid
		 */
		function funRemoveProductRows() 
	    {
			 var table = document.getElementById("tblDeliverySchedule");
			 var rowCount = table.rows.length;			   
			//alert("rowCount\t"+rowCount);
			for(var i=rowCount-1;i>=0;i--)
			{
				table.deleteRow(i);						
			} 
	    }
		
		
		/**
		 * Get and set Location By Data Based on passing Location Code
		 */
		function funSetLocationCode(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadLocationMasterData.html?locCode="+code;			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	$("#txtLocCode").val(response.strLocCode);
				    	$("#lblLocName").text(response.strLocName);
		        		
				    },
				    error: function(jqXHR, exception) {
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
		 * Update total price when user change the qty 
		 */
		function funUpdatePrice(object)
		{
			var index=object.parentNode.parentNode.rowIndex;
			var price=parseFloat(document.getElementById("dblUnitPrice."+index).value)*parseFloat(object.value);
			
			document.getElementById("dblTotalPrice."+index).value=price.toFixed(parseInt(maxAmountDecimalPlaceLimit));				
			funCalSubTotal();
		}
		
		/**
		 * Calcutating subtotal
		 */
		function funCalSubTotal()
	    {
			
	        var dblQtyTot=0;		        
	        var subtot=0;
	        $('#tblDeliverySchedule tr').each(function() {
			    var totalvalue = $(this).find(".totalValueCell").val();
			    var qty=$(this).find(".QtyCell").val();
			    subtot = parseFloat(subtot + parseFloat(totalvalue));
			    dblQtyTot = parseFloat(dblQtyTot) + parseFloat(qty);
			    
			 });
							
// 			$("#txtTotalAmount").val(parseFloat(subtot).toFixed(parseInt(maxAmountDecimalPlaceLimit)));  	
// 			$("#txtTotalQty").val(parseFloat(dblQtyTot).toFixed(parseInt(maxQuantityDecimalPlaceLimit)));
			
	    }
		
		
		/**
		 * Get and set DS Code
		 */
		function funSetDSCode(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadDSCode.html?dsCode="+code;			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	$("#txtDSCode").val(response.strDSCode);
				    	$("#txtDSDate").val(response.dteDSDate);
				    	$("#cmbAgainst").val(response.strAgainst);
				    	$("#txtLocCode").val(response.strLocCode);
				    	$("#lblLocName").text(response.strLocName);
				    	$("#txtDocCode").val(response.strPOCode);
				    	
				    	if("N"!=response.strCloseDS)
				    	{
				    		$("#chkCloseDS").prop('checked', true);
				    	}
				    	else
				    	{
				    		$("#chkCloseDS").prop('checked', false);
				    	}

				    	funRemoveProductRows();
			    		$.each(response.listObjDeliveryScheduleModuledtl, function(i,item)
			                    {	
			    		funAddDSRow(item.strProdCode, item.strProdName,item.dblQty, item.strUOM,
			    				item.dblUnitPrice, item.dblTotalPrice, '');
			                    });
		        		
				    },
				    error: function(jqXHR, exception) {
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
		
		function funOpenPOForDS(transactionName)
		{
			fieldName = transactionName;
			if(fieldName=='dsPOData')
			{
			//	var retval=window.showModalDialog("frmPIforPO.html","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
				var retval=window.open("frmPOForDS.html","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		       //alert(retval);
			
				var timer = setInterval(function ()
					    {
						if(retval.closed)
							{
								if (retval.returnValue != null)
								{
									strVal=retval.returnValue.split("#")
									 $("#txtDocCode").val(strVal[0]);
								}
								clearInterval(timer);
							}
					    }, 500);
			
			

			}
		}
		
</script>

</head>
<body>

<div id="somediv"></div>
	<div id="formHeading">
		<label id="lblFormHeadingName">Delivery Schedule</label>
	</div>
	<s:form name="DeliverySchedule" method="POST" action="saveDeliverySchedule.html?saddr=${urlHits}">
		<br>
		
	
		<table class="transTable">
			<tr>
				<td width="120px"><s:label path="strDSCode">DS Code</s:label></td>
				<td><s:input id="txtDSCode" name="txtDSCode" path="strDSCode"
					ondblclick="funHelp('dscode')" cssClass="searchTextBox" ></s:input></td>
			
				<td width="120px">DS Date</td>
			        <td colspan="5"><s:input id="txtDSDate" name="txtDSDate" type="text" required="required" path="dteDSDate"  pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox" ></s:input></td>
 			</tr>

			 <tr>
				    <td><label>Against</label></td>
				    <td>
				    	<s:select id="cmbAgainst" path="strAgainst" cssClass="BoxW124px">
				    	<s:option value="PO">PO</s:option>
						</s:select>
					</td>
					<td colspan="4"><s:input id="txtDocCode" name="txtDocCode" path="strPOCode" cssClass="searchTextBox" ondblclick="funOpenPOForDS('dsPOData')" ></s:input></td>
					
					<td colspan="4"><input type="Button" value="Fill"  class="smallButton" onclick="return funfillbutton();"/></td>
					
					
			</tr>
			<tr>
		
				<td width="120px"><s:label path="strLocCode">Location</s:label></td>
				<td><s:input id="txtLocCode" name="txtLocCode" path="strLocCode" required = "true" 
				 ondblclick="funHelp('locationmaster')" cssClass="searchTextBox" ></s:input></td>
				<td colspan="9"><label id="lblLocName" class="namelabel" path="strLocName"></label></td>
				
			</tr>
			
		</table>
			
			<div class="dynamicTableContainer"  style="height:300px;">
		
		<table  style="height:28px;border:#0F0;font-size:11px;
	font-weight: bold;width: 100%;" >
		
		<tr bgcolor="#72BEFC" >
		<td width="8%">Product Code</td>
		<td width="40%">Product Name</td>
		<td width="10%">UOM</td>
		<td width="10%">Quantity</td>
		<td width="10%">Price</td>
		<td width="10%">Amount</td>
		<td width="10%">Remarks</td>
		<td width="10%">Delete</td>
		</tr>
		</table>
		<div style="background-color:  	#C0E2FE;
    border: 1px solid #ccc;
    display: block;
    height: 255px;
    margin: auto;
    overflow-x: hidden;
    overflow-y: scroll;
    width: 100%;">
		<table id="tblDeliverySchedule"  style="width:100%;border:
#0F0;table-layout:fixed;overflow:scroll" class="transTablex col6-center">
	<tbody>    
	 <col style="width:7%">
        <col style="width:33%">
        <col style="width:8%">
        <col style="width:8%">
        <col style="width:8%">
        <col style="width:8%">
        <col style="width:8%">
        <col style="width:2%">
	
    </tbody>
</table>
		</div>
		</div>
		
		<table class="transTableMiddle">
				<tr>
				<td><label id="lblCloseDS">Close DS</label></td>
									<td><s:checkbox element="li" id="chkCloseDS"
											path="strCloseDS" value="Y" /></td>
					<td>Narration</td>
		            <td><s:textarea id="txtNarration" path="strNarration"></s:textarea></td>
					
				</tr>	
			</table>
			<br />
			
		<br />	
		
		<p align="center">
			<input type="submit" value="Submit"  class="form_button" onclick="return funCallFormAction('submit',this)"/>
				 <input type="button"   value="Reset" class="form_button" onclick="funResetFields()" />
		</p>
		<br />
		<br />					

</s:form>
</body>
</html>