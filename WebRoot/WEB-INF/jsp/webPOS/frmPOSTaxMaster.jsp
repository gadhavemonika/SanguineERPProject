<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
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
</style>
<script type="text/javascript">
	$(document).ready(function() {
		
			$('input#txtTaxDesc').mlKeyboard({layout: 'en_US'});
		  	$('input#txtTaxShortName').mlKeyboard({layout: 'en_US'});
		 	$('input#txtAmount').mlKeyboard({layout: 'en_US'});
		  	$('input#txtPercent').mlKeyboard({layout: 'en_US'});
		
		  	$("#txtdteValidFrom").datepicker({ dateFormat: 'yy-mm-dd' });
			$("#txtdteValidFrom" ).datepicker('setDate', 'today');
			$("#txtdteValidFrom").datepicker();
			
	        $("#txtdteValidTo").datepicker({ dateFormat: 'yy-mm-dd' });
	        $("#txtdteValidTo" ).datepicker('setDate', 'today');
	        $("#txtdteValidTo").datepicker();
	            

		$(".tab_content").hide();
		$(".tab_content:first").show();

		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();

			var activeTab = $(this).attr("data-state");
			$("#" + activeTab).fadeIn();
		});
		
		$("form").submit(function(event){
			  if($("#txtTaxDesc").val().trim()=="")
				{
					alert("Please Enter tax description.");
					return false;
				}
			  if($("#txtTaxDesc").val().length > 30)
				{
					alert("Tax Description length must be less than 30.");
					return false;
				}
			  if($("#txtTaxShortName").val().length > 20)
				{
					alert("Tax Description length must be less than 20.");
					return false;
				}
			 
			  if(CalculateDateDiff())
				{
					return false;
				}
			  else{
				var flag=funChekTable();
				return flag;
			  }
			});
	});
	
	
	function CalculateDateDiff() {
		var fromDate = $("#txtdteValidFrom").val();
		var toDate = $("#txtdteValidTo").val();


		var frmDate= fromDate.split('-');
	    var fDate = new Date(frmDate[0],frmDate[1],frmDate[2]);
	    
	    var tDate= toDate.split('-');
	    var t1Date = new Date(tDate[0],tDate[1],tDate[2]);

    	var dateDiff=t1Date-fDate;
  		 if (dateDiff >= 0 ) 
  		 {
         	return false;
         }else{
        	 alert("Invalid date");
        	 return true;
         }
	}


var field;


	/**
	* Reset The Group Name TextField
	**/
	function funResetFields()
	{

		location.reload(true); 
    }
	function funChekTable()
	{
		var flag=false;
		
		
		
		
		  $('#tblPOS tr').each(function() {
			  var checkbox = $(this).find("input[type='checkbox']");

			    if( checkbox.prop("checked") ){
			    	 flag= true;
			    } 
				 });
		  
		  if(!flag)
			  {
			  alert("Please select atleast POS Code");
			  return flag;
			  }
		  
		  
		  flag=funCheckSettlement();
		  if(flag)
			  flag=funCheckGroup();
		  return flag;
	}
	function funCheckSettlement()
	{
		 var flag=false;
		  $('#tblSettlement tr').each(function() {
			  var checkbox = $(this).find("input[type='checkbox']");
			  if( checkbox.prop("checked") ){
			    	 flag= true;
			    }
				 });
		  
		  if(!flag)
			  {
				 alert("Please select atleast one Settlement Code");
				 return flag;
			  }
		  return flag;
	}
	
	function funCheckGroup()
	{
		 var flag=false;
		  $('#tblGroup tr').each(function() {
			  var checkbox = $(this).find("input[type='checkbox']");
			  if( checkbox.prop("checked") ){
			    	 flag= true;
			    }
				 });
		  
		  if(!flag)
			  {
				 alert("Please select atleast one Group Code");
				 return flag;
			  }
		  return flag;
	}
		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{	   field= transactionName.split(".") ;
		 
	       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	       window.open("searchform.html?formname="+field[0]+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
		
		
		function funSetData(code)
		{
			switch(field[1])
			{
		case 'a' : 
			funSetTaxData(code);
			break;
		case 'b' : 
			$("#txtAccountCode").val(code);
			break;


		}
		}
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
		});
		
		function funSetTaxData(code){

			$("#txtTaxCode").val(code);
			var searchurl=getContextPath()+"/loadPOSTaxMasterData.html?taxCode="+code;		
			 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strTaxCode=='Invalid Code')
			        	{
			        		alert("Invalid Group Code");
			        		$("#txtTaxCode").val('');
			        	}
			        	else
			        	{
				        	$("#cmbTaxType").val(response.strTaxType);
				        	$("#txtTaxDesc").val(response.strTaxDesc);
				        	$("#txtTaxShortName").val(response.strTaxShortName);
				        	$("#txtAmount").val(response.dblAmount);
				        	$("#cmbTaxOnSP").val(response.strTaxOnSP);
				        	$("#txtPercent").val(response.dblPercent);
				        	$("#cmbTaxOnGD").val(response.strTaxOnGD);
				        	$("#cmbTaxCalculation").val(response.strTaxCalculation);
				        	if(response.strTaxRounded=='Y')
			        		{
				        		$("#chkTaxRounded").prop('checked',true);
			        		}
				        	
				        	$("#cmbTaxIndicator").val(response.strTaxIndicator);
				        	
				        	
				        	if(response.strTaxOnTax=='Y')
			        		{
				        		$("#chkTaxOnTax").prop('checked',true);
			        		}
				        	
				        	var dateTime=response.dteValidFrom
				        	var date=dateTime.split(" ");
				        	$("#txtdteValidFrom").val(date[0]);
				        	
				        	 dateTime=response.dteValidTo
				        	 date=dateTime.split(" ");
				        	$("#txtdteValidTo").val(date[0]);
				        	
				        	$("#cmbItemType").val(response.strItemType);
				        	if(response.strHomeDelivery=='Y')
			        		{
				        		$("#chkHomeDelivery").prop('checked',true);
			        		}
				        	if(response.strDinningInn=='Y')
			        		{
				        		$("#chkDinningInn").prop('checked',true);
			        		}
				        	if(response.strTakeAway=='Y')
			        		{
				        		$("#chkTakeAway").prop('checked',true);
			        		}
			        		$("#txtAccountCode").val(response.strAccountCode);
			        		
			        		//fill Settle Table
			        		funLoadSettlementDataForUpdate(response.listSettlementCode);
			        		
			        		//fill Group Table
			        		funLoadGroupDataForUpdate(response.listGroupCode);
			        		
				        	//fill POS table
				        
				        	funLoadPOSDataForUpdate(response.listPOSCode);
				        	
				        	
				        	//fill tax table
				        	
				        	funLoadTaxDataForUpdate(response.listTaxOnTaxCode);
				        	
				        	
				        	//fill Area table
				        	
				        	funLoadAreaDataForUpdate(response.listAreaCode);
				        	
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
		function funLoadTableData()
		{
			//funSetDate();
			document.getElementById("txtAmount").disabled = true;
			document.getElementById("cmbItemType").disabled = true;
			
			funLoadPOSData();
			funLoadSettlementData();
			funLoadGroupData();
			funLoadTaxData();
			funLoadAreaData();
			
		}
		
		function funLoadPOSData()
		{

			var searchurl=getContextPath()+"/LoadPOSData.html";
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        
				        success: function (response) {
				        	funRemoveTableRows("tblPOS");
				           // for (var i in response){		            	
				            	$.each(response,function(i,item){
				            	
				            		funfillPOSDetail(item.strPosCode,item.strPosName);
				            				            				            	
					            	 
				            	});
				    
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
		function funLoadPOSDataForUpdate(list)
		{

			var searchurl=getContextPath()+"/LoadPOSData.html";
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        
				        success: function (response) {
				        	funRemoveTableRows("tblPOS");
				           // for (var i in response){		            	
				            	$.each(response,function(i,item){
				            	
				            		funfillPOSDetailForUpdate(item.strPosCode,item.strPosName,list);
				            				            				            	
					            	 
				            	});
				    
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
		function funfillPOSDetail(strPOSCode,strPOSName)
		{
			var table = document.getElementById("tblPOS");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			  
			 row.insertCell(0).innerHTML= "<input type=\"hidden\" name=\"listPOSCode["+(rowCount)+"].strPosCode\" size=\"0%\" readonly=\"readonly\" class=\"Box \" id=\"txtPosCode."+(rowCount)+"\" value='"+strPOSCode+"'>";
		      row.insertCell(1).innerHTML= "<input name=\"listPOSCode["+(rowCount)+"].strPosName\" size=\"50%\" readonly=\"readonly\" class=\"Box \"  id=\"txtPosName."+(rowCount)+"\" value='"+strPOSName+"'>";
		      row.insertCell(2).innerHTML= "<input type=\"checkbox\" name=\"listPOSCode["+(rowCount)+"].strApplicableYN\" size=\"50%\" id=\"chkPosApplicable."+(rowCount)+"\" value='"+true+"'>";
			  

		}
		
		function funfillPOSDetailForUpdate(strPOSCode,strPOSName,list)
		{
			var flag=false;
		
			var table = document.getElementById("tblPOS");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			  
			 row.insertCell(0).innerHTML= "<input type=\"hidden\" name=\"listPOSCode["+(rowCount)+"].strPosCode\" size=\"0%\" readonly=\"readonly\" class=\"Box \" id=\"txtPosCode."+(rowCount)+"\" value='"+strPOSCode+"'>";
		      row.insertCell(1).innerHTML= "<input name=\"listPOSCode["+(rowCount)+"].strPosName\" size=\"50%\" readonly=\"readonly\" class=\"Box \"  id=\"txtPosName."+(rowCount)+"\" value='"+strPOSName+"'>";
		    
			  
		      $.each(list,function(i,item){
	          		if(item.strPosCode==strPOSCode)
		        		{
			        	flag=true;
			        	  row.insertCell(2).innerHTML= "<input type=\"checkbox\" name=\"listPOSCode["+(rowCount)+"].strApplicableYN\" size=\"50%\" id=\"chkApplicable."+(rowCount)+"\" checked=\"checked\">";
	          			
		        		
		        		}		            				            	
		           
	          	});
			
			  if(!flag)
	      		{
				  row.insertCell(2).innerHTML= "<input type=\"checkbox\" name=\"listPOSCode["+(rowCount)+"].strApplicableYN\" size=\"50%\" id=\"chkApplicable."+(rowCount)+"\" value='"+true+"'>";
	      		}
		}
		function funLoadSettlementData()
		{

			var searchurl=getContextPath()+"/LoadSettlmentData.html";
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        
				        success: function (response) {
				        	funRemoveTableRows("tblSettlement");
				           // for (var i in response){		            	
				            	$.each(response,function(i,item){
				            	
				            		funfillSettlementDetail(item.strSettlementCode,item.strSettlementDesc);
				            				            				            	
					            	 
				            	});
				    
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

		function funLoadSettlementDataForUpdate(list)
		{

			var searchurl=getContextPath()+"/LoadSettlmentData.html";
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        
				        success: function (response) {
				        	funRemoveTableRows("tblSettlement");
				           // for (var i in response){		            	
				            	$.each(response,function(i,item){
				            	
				            		funfillSettlementDetailForUpdate(item.strSettlementCode,item.strSettlementDesc,list);
				            				            				            	
					            	 
				            	});
				    
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
		function funfillSettlementDetail(strSettlementCode,strSettlementDesc)
		{
			var table = document.getElementById("tblSettlement");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);

		      row.insertCell(0).innerHTML= "<input name=\"listSettlementCode["+(rowCount)+"].strSettlementCode\" readonly=\"readonly\" class=\"Box \"  id=\"txtSettlementCode."+(rowCount)+"\" value='"+strSettlementCode+"'>";
		      row.insertCell(1).innerHTML= "<input name=\"listSettlementCode["+(rowCount)+"].strSettlementDesc\" readonly=\"readonly\" class=\"Box \"  id=\"txtSettlementDesc."+(rowCount)+"\" value='"+strSettlementDesc+"'>";
		      row.insertCell(2).innerHTML= "<input type=\"checkbox\" name=\"listSettlementCode["+(rowCount)+"].strApplicableYN\"  id=\"chkSettleApplicable."+(rowCount)+"\" value='"+true+"'>";

		}
		
		function funfillSettlementDetailForUpdate(strSettlementCode,strSettlementDesc,list)
		{
			var flag=false;
			var table = document.getElementById("tblSettlement");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);

		      row.insertCell(0).innerHTML= "<input name=\"listSettlementCode["+(rowCount)+"].strSettlementCode\" readonly=\"readonly\" class=\"Box \" id=\"txtSettlementCode."+(rowCount)+"\" value='"+strSettlementCode+"'>";
			  row.insertCell(1).innerHTML= "<input name=\"listSettlementCode["+(rowCount)+"].strSettlementDesc\" readonly=\"readonly\" class=\"Box \" id=\"txtSettlementDesc."+(rowCount)+"\" value='"+strSettlementDesc+"'>";
			  $.each(list,function(i,item){
	          		if(item.strSettlementCode==strSettlementCode)
		        		{
			        	flag=true;
			        	  row.insertCell(2).innerHTML= "<input type=\"checkbox\" name=\"listSettlementCode["+(rowCount)+"].strApplicableYN\"  id=\"chkApplicable."+(rowCount)+"\" checked=\"checked\">";
	          			
		        		
		        		}		            				            	
		           
	          	});
			
			  if(!flag)
	      		{
				  row.insertCell(2).innerHTML= "<input type=\"checkbox\" name=\"listSettlementCode["+(rowCount)+"].strApplicableYN\"  id=\"chkApplicable."+(rowCount)+"\" value='"+true+"'>";
	      		}
		}
		function funLoadGroupData()
		{

			var searchurl=getContextPath()+"/LoadGroupData.html";
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        
				        success: function (response) {
				        	funRemoveTableRows("tblGroup");
				           // for (var i in response){		            	
				            	$.each(response,function(i,item){
				            	
				            		funfillGroupDetail(item.strGroupCode,item.strGroupName);
					            	 
				            	});
				    
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

		function funLoadGroupDataForUpdate(list)
		{

			var searchurl=getContextPath()+"/LoadGroupData.html";
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        
				        success: function (response) {
				        	funRemoveTableRows("tblGroup");
				           // for (var i in response){		            	
				            	$.each(response,function(i,item){
				            	
				            		funfillGroupDetailForUpdate(item.strGroupCode,item.strGroupName,list);
				            	});
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
		function funfillGroupDetail(strGroupCode,strGroupName)
		{
			var table = document.getElementById("tblGroup");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);

		      row.insertCell(0).innerHTML= "<input name=\"listGroupCode["+(rowCount)+"].strGroupCode\" readonly=\"readonly\" class=\"Box \" id=\"strGroupCode."+(rowCount)+"\" value='"+strGroupCode+"'>";
		      row.insertCell(1).innerHTML= "<input name=\"listGroupCode["+(rowCount)+"].strGroupName\" readonly=\"readonly\" class=\"Box \" id=\"strGroupName."+(rowCount)+"\" value='"+strGroupName+"'>";
		      row.insertCell(2).innerHTML= "<input type=\"checkbox\" name=\"listGroupCode["+(rowCount)+"].strApplicableYN\" id=\"chkSettleApplicable."+(rowCount)+"\" value='"+true+"'>";

		}
		
		function funfillGroupDetailForUpdate(strGroupCode,strGroupName,list)
		{
			var flag=false;
			var table = document.getElementById("tblGroup");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);

		      row.insertCell(0).innerHTML= "<input name=\"listGroupCode["+(rowCount)+"].strGroupCode\" readonly=\"readonly\" class=\"Box \" id=\"strGroupCode."+(rowCount)+"\" value='"+strGroupCode+"'>";
			  row.insertCell(1).innerHTML= "<input name=\"listGroupCode["+(rowCount)+"].strGroupName\" readonly=\"readonly\" class=\"Box \" id=\"strGroupName."+(rowCount)+"\" value='"+strGroupName+"'>";
			  $.each(list,function(i,item){
	          		if(item.strGroupCode==strGroupCode)
		        		{
			        	flag=true;
			        	  row.insertCell(2).innerHTML= "<input type=\"checkbox\" name=\"listGroupCode["+(rowCount)+"].strApplicableYN\" id=\"chkApplicable."+(rowCount)+"\" checked=\"checked\">";
	          			
		        		
		        		}		            				            	
		           
	          	});
			
			  if(!flag)
	      		{
				  row.insertCell(2).innerHTML= "<input type=\"checkbox\" name=\"listGroupCode["+(rowCount)+"].strApplicableYN\" id=\"chkApplicable."+(rowCount)+"\" value='"+true+"'>";
	      		}
		}
		function funLoadTaxData()
		{

			var searchurl=getContextPath()+"/LoadTaxData.html";
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        
				        success: function (response) {
				        	funRemoveTableRows("tblTax");
				           // for (var i in response){		            	
				            	$.each(response,function(i,item){
				            	
				            		funfillTaxDetail(item.strTaxCode,item.strTaxDesc);
				            				            				            	
					            	 
				            	});
				    
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
		function funLoadTaxDataForUpdate(list)
		{

			var searchurl=getContextPath()+"/LoadTaxData.html";
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        
				        success: function (response) {
				        	funRemoveTableRows("tblTax");
				           // for (var i in response){		            	
				            	$.each(response,function(i,item){
				            	
				            		funfillTaxDetailForUpdate(item.strTaxCode,item.strTaxDesc,list);
				            				            				            	
					            	 
				            	});
				    
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
		
		function funfillTaxDetail(strTaxCode,strTaxDesc)
		{
			var table = document.getElementById("tblTax");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);

		      row.insertCell(0).innerHTML= "<input name=\"listTaxOnTaxCode["+(rowCount)+"].strTaxCode\" readonly=\"readonly\" class=\"Box \" size=\"37.5%\" id=\"txtTaxCode."+(rowCount)+"\" value='"+strTaxCode+"'>";
		      row.insertCell(1).innerHTML= "<input name=\"listTaxOnTaxCode["+(rowCount)+"].strTaxDesc\" readonly=\"readonly\" class=\"Box \" size=\"37.5%\" id=\"txtTaxDesc."+(rowCount)+"\" value='"+strTaxDesc+"'>";
		      row.insertCell(2).innerHTML= "<input type=\"checkbox\" name=\"listTaxOnTaxCode["+(rowCount)+"].strApplicableYN\" size=\"25%\" id=\"chkTaxApplicable."+(rowCount)+"\" value='"+true+"'>";

		}
		function funfillTaxDetailForUpdate(strTaxCode,strTaxDesc,list)
		{
			var flag=false;
		
			var table = document.getElementById("tblTax");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);

		      row.insertCell(0).innerHTML= "<input name=\"listTaxOnTaxCode["+(rowCount)+"].strTaxCode\" readonly=\"readonly\" class=\"Box \" size=\"37.5%\" id=\"txtTaxCode."+(rowCount)+"\" value='"+strTaxCode+"'>";
		      row.insertCell(1).innerHTML= "<input name=\"listTaxOnTaxCode["+(rowCount)+"].strTaxDesc\" readonly=\"readonly\" class=\"Box \" size=\"37.5%\" id=\"txtTaxDesc."+(rowCount)+"\" value='"+strTaxDesc+"'>";
		      

		      $.each(list,function(i,item){
	          		if(item.strTaxCode==strTaxCode)
		        		{
			        	flag=true;
			        	  row.insertCell(2).innerHTML= "<input type=\"checkbox\" name=\"listTaxOnTaxCode["+(rowCount)+"].strApplicableYN\" size=\"25%\" id=\"chkApplicable."+(rowCount)+"\" checked=\"checked\">";
	          			
		        		
		        		}		            				            	
		           
	          	});
			
			  if(!flag)
	      		{
				  row.insertCell(2).innerHTML= "<input type=\"checkbox\" name=\"listTaxOnTaxCode["+(rowCount)+"].strApplicableYN\" size=\"25%\" id=\"chkApplicable."+(rowCount)+"\" value='"+true+"'>";
	      		}
		}
		function funLoadAreaData()
		{

			var searchurl=getContextPath()+"/LoadAreaData.html";
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        
				        success: function (response) {
				        	funRemoveTableRows("tblArea");
				           // for (var i in response){		            	
				            	$.each(response,function(i,item){
				            	
				            		funfillAreaDetail(item.strAreaCode,item.strAreaName);
				            				            				            	
					            	 
				            	});
				    
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
		function funLoadAreaDataForUpdate(list)
		{

			var searchurl=getContextPath()+"/LoadAreaData.html";
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        
				        success: function (response) {
				        	funRemoveTableRows("tblArea");
				           // for (var i in response){		            	
				            	$.each(response,function(i,item){
				            	
				            		funfillAreaDetailForUpdate(item.strAreaCode,item.strAreaName,list);
				            				            				            	
					            	 
				            	});
				    
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
		function funfillAreaDetail(strAreaCode,strAreaName)
		{
			var table = document.getElementById("tblArea");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);

		      row.insertCell(0).innerHTML= "<input name=\"listAreaCode["+(rowCount)+"].strAreaCode\" readonly=\"readonly\" class=\"Box \" size=\"37.5%\" id=\"txtAreaCode."+(rowCount)+"\" value='"+strAreaCode+"'>";
		      row.insertCell(1).innerHTML= "<input name=\"listAreaCode["+(rowCount)+"].strAreaName\" readonly=\"readonly\" class=\"Box \" size=\"37.5%\" id=\"txtAreaName."+(rowCount)+"\" value='"+strAreaName+"'>";
		      row.insertCell(2).innerHTML= "<input type=\"checkbox\" name=\"listAreaCode["+(rowCount)+"].strApplicableYN\" size=\"25%\" id=\"chkAreaApplicable."+(rowCount)+"\" value='"+true+"'>";

		}
		function funfillAreaDetailForUpdate(strAreaCode,strAreaName,list)
		{
			var flag=false;
		
			var table = document.getElementById("tblArea");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);

		      row.insertCell(0).innerHTML= "<input name=\"listAreaCode["+(rowCount)+"].strAreaCode\" readonly=\"readonly\" class=\"Box \" size=\"37.5%\" id=\"txtAreaCode."+(rowCount)+"\" value='"+strAreaCode+"'>";
		      row.insertCell(1).innerHTML= "<input name=\"listAreaCode["+(rowCount)+"].strAreaName\" readonly=\"readonly\" class=\"Box \" size=\"37.5%\" id=\"txtAreaName."+(rowCount)+"\" value='"+strAreaName+"'>";
		      $.each(list,function(i,item){
	          		if(item.strAreaCode==strAreaCode)
		        		{
			        	flag=true;
			        	  row.insertCell(2).innerHTML= "<input type=\"checkbox\" name=\"listAreaCode["+(rowCount)+"].strApplicableYN\" size=\"25%\" id=\"chkApplicable."+(rowCount)+"\" checked=\"checked\">";
	          			
		        		}		            				            	
		           
	          	});
			
			  if(!flag)
	      		{
				  row.insertCell(2).innerHTML= "<input type=\"checkbox\" name=\"listAreaCode["+(rowCount)+"].strApplicableYN\" size=\"25%\" id=\"chkApplicable."+(rowCount)+"\" value='"+true+"'>";
	      		}
		}
		function funRemoveTableRows(tableId)
		{
			var table = document.getElementById(tableId);
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
		}
	function funCombo()
	{
		if(document.getElementById("cmbTaxType").value=="Fixed Amount")
		{	document.getElementById("txtAmount").disabled = false;
		    document.getElementById("txtPercent").disabled = true;
		}
		if(document.getElementById("cmbTaxType").value=="Percent")
		{	document.getElementById("txtAmount").disabled = true;
		    document.getElementById("txtPercent").disabled = false;
		}
		if(document.getElementById("cmbTaxIndicator").value!="")
		{	document.getElementById("cmbItemType").disabled = false;
		    
		}
		if(document.getElementById("cmbTaxIndicator").value=="")
		{	document.getElementById("cmbItemType").disabled = true;
			document.getElementById("cmbItemType").value="Both";
		}
		 
		if(document.getElementById("chkTaxOnTax").checked)
			{
			document.getElementById("tblTax").disabled = false;
			}
		
	}
		
</script>


</head>

<body onload="funLoadTableData()">
	<div id="formHeading">
		<label>Tax Master</label>
	</div>
	<s:form name="POSForm" method="POST" action="savePOSTaxMaster.html?saddr=${urlHits}">

		<br />
		<br />
		<table
				style="border: 0px solid black; width: 85%; height: 130%;  margin-left: auto; margin-right: auto;background-color:#C0E4FF;">
				
				<tr>
					<td>
						<div id="tab_container" >
							<ul class="tabs">
								<li class="active" data-state="tab1">Tax Details 1</li>
								<li data-state="tab2">Tax Details 2</li>
								<li data-state="tab3">Tax Details 3</li>
								<li data-state="tab4">Linkup</li>
				
							</ul>
							<br /> <br />

							<!--  Start of Generals tab-->

							<div id="tab1" class="tab_content">
								<table  class="masterTable">
																		
									<tr>
				<td width="140px">Tax Code</td>
				<td><s:input id="txtTaxCode" path="strTaxCode"
						cssClass="searchTextBox" readonly="true" ondblclick="funHelp('POSTaxMaster.a')" /></td>
			
				<td><label>Tax Type</label></td>
				<td><s:select id="cmbTaxType" name="cmbTaxType" path="strTaxType" cssClass="BoxW124px" onclick="funCombo()" >
				<option value="Percent">Percent</option>
				 <option value="Fixed Amount">Fixed Amount</option>
 				 
				 </s:select>
		       </td>
		       </tr>
			<tr>
			<td><label>Tax Description</label></td>
				<td><s:input colspan="3" type="text" id="txtTaxDesc" 
						name="txtTaxDesc" path="strTaxDesc" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /></td>
						<td></td><td></td>
				
			</tr>
		
			<tr>
			<td><label>Tax Short Name</label></td>
				<td><s:input colspan="3" type="text" id="txtTaxShortName" 
						name="txtTaxShortName" path="strTaxShortName" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /></td>
				
			
			<td><label>Amount</label></td>
				
				<td><s:input colspan="3" type="text" id="txtAmount" 
						name="txtAmount" path="dblAmount" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
			</tr>
			
			<tr>
			<td><label>Tax On S/P</label></td>
				
				<td>
					<s:select id="cmbTaxOnSP" name="cmbTaxOnSP" path="strTaxOnSP" cssClass="BoxW124px" >
				<option value="Sales">Sales</option>
				 <option value="Purchase">Purchase</option>
 				
				 </s:select> 	
		       </td>
		      
		       
		       <td><label>Percent</label></td>
		       <td><s:input colspan="3" type="text" id="txtPercent" 
						name="txtPercent" path="dblPercent" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
			</tr>
			
			</table>
			<br>
				<table border="1" class="myTable" style="width:40%;margin: auto;"  >
										
										<tr>
										
										<td style="width:60%; border: #c0c0c0 1px solid; background: #78BEF9;">POS Name</td>
										<td style="width:40%; border: #c0c0c0 1px solid; background: #78BEF9;">Select</td>
										</tr>
					</table>
					<div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 100px;
				    				margin:auto;overflow-x: hidden; overflow-y: scroll;width: 40%;">
										<table id="tblPOS" class="transTablex col5-center" style="width:100%;">
										<tbody>    
												<col style="width:0%"><!--  COl1   -->
												<col style="width:60%"><!--  COl2   -->
												<col style="width:40%"><!--  COl3   -->
																				
										</tbody>							
										</table>
								</div>
									
								<br>
									<table  class="masterTable">
									<tr>
									<td><label>Valid From</label></td>
									<td ><s:input id="txtdteValidFrom" name="txtdteValidFrom" path="dteValidFrom"  cssClass="calenderTextBox" /></td>
									<td><label>Valid To</label></td>
									<td ><s:input id="txtdteValidTo" name="txtdteValidTo" path="dteValidTo"  cssClass="calenderTextBox" /></td>
									</tr>
									</table>
			
							</div>
							<!--  End of  tax details1 tab-->




							<!-- Start of tax details2 tab -->

							<div id="tab2" class="tab_content">
							<table  class="masterTable">
																		
				<tr>
				<td><label>Tax On G/D</label></td>
				<td><s:select id="cmbTaxOnGD" name="cmbTaxOnGD" path="strTaxOnGD" cssClass="BoxW124px" >
				<option value="Gross">Gross</option>
				 <option value="Discount">Discount</option>
 				
				 </s:select>
		       </td>
		       
				<td><label>Tax Calculation</label></td>
				<td><s:select id="cmbTaxCalculation" name="cmbTaxCalculation" path="strTaxCalculation" cssClass="BoxW124px" >
				
				 <option value="Backward">Backward</option>
 				 <option value="Forward">Forward</option>
				 </s:select>
		       </td>
		       </tr>
		       
			<tr>
			 <td><label>Tax Rounded</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkTaxRounded" path="strTaxRounded" value="Yes" />
		       </td>
						
				<td><label>Tax Indicator</label></td>
				<td><s:select id="cmbTaxIndicator" name="cmbTaxIndicator" path="strTaxIndicator" cssClass="BoxW124px" onclick="funCombo()" >
				<option value=""></option><option value="A">A</option><option value="B">B</option><option value="C">C</option>
				 <option value="D">D</option><option value="E">E</option><option value="F">F</option><option value="G">G</option>
 				 <option value="H">H</option><option value="I">I</option><option value="J">J</option><option value="K">K</option>
 				 <option value="L">L</option><option value="M">M</option><option value="N">N</option><option value="O">O</option>
				 <option value="P">P</option><option value="Q">Q</option><option value="R">R</option><option value="S">S</option>
 				 <option value="T">T</option><option value="U">U</option><option value="V">V</option><option value="W">W</option>
 				  <option value="X">X</option><option value="Y">Y</option><option value="Z">Z</option>
				 </s:select>
		       </td>
			</tr>
		
			<tr>
			<td><label>Tax On Tax</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkTaxOnTax" path="strTaxOnTax" value="Yes" onclick="funCombo()" />
		       </td>
			<td></td>
				
			<td></td>
			</tr>
			
			<tr>
			<td><label>TAX Applicable On</label></td>
			</tr>
			</table>
			<table cellspacing=10px; style="margin-left:110px; width:80%">
			<tr>
			<td>
				<table border="1" class="myTable"   >
										
										<tr>
											<td style="width:36.5%; border: #c0c0c0 1px solid; background: #78BEF9;">Set Code</td>
											<td style="width:36.5%; border: #c0c0c0 1px solid; background: #78BEF9;">Settlement Name</td>
											<td style="width:28%; border: #c0c0c0 1px solid; background: #78BEF9;">Applicable</td>
										</tr>
										
					</table>
					<div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 120px;
			    		overflow-x: hidden; overflow-y: scroll;">
					<table id="tblSettlement" class="transTablex col5-center" style="width:100%;">
							<tbody>    
											<col style="width:37.5%"><!--  COl1   -->
											<col style="width:37.5%"><!--  COl2   -->
											<col style="width:25%"><!--  COl3   -->								
									</tbody>	 							
									</table>
									
									</div>
				</td>
				<td>	
						
					<table border="1" class="myTable"  >
										
					<tr>
						<td style="width:36.5%; border: #c0c0c0 1px solid; background: #78BEF9;">Group Code</td>
						<td style="width:36.5%; border: #c0c0c0 1px solid; background: #78BEF9;">Group Name</td>
						<td style="width:28%; border: #c0c0c0 1px solid; background: #78BEF9;">Applicable</td>
					</tr>
										
					</table>
					<div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 120px;
			    		overflow-x: hidden; overflow-y: scroll;">
					<table id="tblGroup" class="transTablex col5-center" style="width:100%;">
								 <tbody>    
											<col style="width:37.5%"><!--  COl1   -->
											<col style="width:37.5%"><!--  COl2   -->
											<col style="width:25%"><!--  COl3   -->								
									</tbody>								
									</table>
									
									</div>
									</td>
									</tr>
					</table>		
							</div>
							<!-- End of tax details2 tab -->


							<!-- Start of tax details3 Tab -->

						<div id="tab3" class="tab_content">
							<table class="masterTable">
							<tr>
							
									<td ><label >Tax On Tax</label></td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									
									<td><label>Tax Type</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:select
											id="cmbItemType" name="cmbItemType" path="strItemType"
											cssClass="BoxW124px">
											
											<option value="Both">Both</option>
											<option value="Food">Food</option>
											<option value="Liquor">Liquor</option>
										</s:select></td>
								
								
								
							</tr>
							</table>
							
							<table border="1" class="myTable"
								style="width: 60%; margin-left:10%;">
								
									<tr>
										<td style="width:36.5%; border: #c0c0c0 1px solid; background: #78BEF9;">Tax Code</td>
										<td style="width:36.5%; border: #c0c0c0 1px solid; background: #78BEF9;">Tax Name</td>
										<td style="width:28%; border: #c0c0c0 1px solid; background: #78BEF9;">Select</td>
									</tr>
								
							</table>
							<div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 85px;
			    				margin-left: 10%;overflow-x: hidden; overflow-y: scroll;width: 60%;">
									<table id="tblTax" class="transTablex col5-center" style="width: 100%;">
									<tbody>    
											<col style="width:37.5%"><!--  COl1   -->
											<col style="width:37.5%"><!--  COl2   -->
											<col style="width:25%"><!--  COl3   -->								
									</tbody>							
									</table>
							</div>
							
							<table class="masterTable">
								<tr style="background-color:#C0E4FF;">
									<td><label>Tax On</label></td>
									<td><s:checkbox element="li" id="chkHomeDelivery" path="strHomeDelivery"
											value="Yes" />&nbsp;&nbsp;&nbsp;<label>Home Delivery</label></td>
									<td><s:checkbox element="li" id="chkDinningInn" path="strDinningInn"
											value="Yes" />&nbsp;&nbsp;&nbsp;<label>Dinning Inn</label></td>
									<td><s:checkbox element="li" id="chkTakeAway" path="strTakeAway"
											value="Yes" />&nbsp;&nbsp;&nbsp;<label>Take Away</label></td>
								</tr>
								
							</table>
							
							<table class="masterTable">
							<tr>
								<td><label>Select Area</label>
								</tr>
							</table>
									
									<table border="1" class="myTable"
								style="width: 60%; margin-left:10%;">
								
									<tr>
										<td style="width:36.5%; border: #c0c0c0 1px solid; background: #78BEF9;">Area Code</td>
										<td style="width:36.5%; border: #c0c0c0 1px solid; background: #78BEF9;">Area Name</td>
										<td style="width:28%; border: #c0c0c0 1px solid; background: #78BEF9;">Select</td>
									</tr>
								
							</table>
							<div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 85px;
			    				margin-left: 10%;overflow-x: hidden; overflow-y: scroll;width: 60%;">
									<table id="tblArea" class="transTablex col5-center" style="width: 100%;">
									<tbody>    
											<col style="width:37.5%"><!--  COl1   -->
											<col style="width:37.5%"><!--  COl2   -->
											<col style="width:25%"><!--  COl3   -->								
									</tbody>							
									</table>
							</div>
									
							

						</div>

						<!-- End  of tax details3  Tab -->


							<!-- Start of tax details4  Tab -->
							<div id="tab4" class="tab_content">
										<table  class="masterTable">
																		
									<tr>
				<td width="140px">Account Code</td>
				<td><s:input id="txtAccountCode" path="strAccountCode"
						cssClass="searchTextBox" ondblclick="funHelp('WebBooksAcountMaster.b')" /></td>
			</tr>
			
						</table>
							</div>
							<!-- End  of tax details4  Tab -->



					

						</div>
					</td>
				</tr>
			</table>
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button"/> 
			<input type="button" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
	</s:form>

</body>
</html>