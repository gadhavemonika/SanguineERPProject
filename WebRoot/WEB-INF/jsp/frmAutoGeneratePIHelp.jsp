<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jQuery.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>
	
<script type="text/javascript" src="<spring:url value="/resources/js/pagination.js"/>"></script>
        <!-- Load data to paginate -->
<link rel="stylesheet" href="<spring:url value="/resources/css/pagination.css"/>" />

<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:url value="/resources/css/design.css"/>" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Auto Generate PI Help</title>


<script type="text/javascript">

var members;
var flagSelectAll=false;

	//Ajax Wait Image display
	$(document).ready(function(){
		  $(document).ajaxStart(function(){
		    $("#wait").css("display","block");
		  });
	  $(document).ajaxComplete(function(){
	    $("#wait").css("display","none");
	  });
	  
	});
	
	
	//Getting Project path
	function getContextPath() 
	{
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}

	//Fill SubGroup after changing Group Code
	function funFillCombo(code)
	{
		var searchUrl=getContextPath()+"/loadSubGroupCombo.html?code="+code;
		$.ajax({
	        type: "GET",
	        url: searchUrl,	        		
	        dataType: "json",
	        success: function(response)	        
	        {	      
		           var html = '<option value="ALL">ALL</option>';        
				   $.each(response, function(key, value)
				   {				
					 html += '<option value="' + key + '">'+ value + '</option>';
				   });
				   html += '</option>';
				   $('#strSGCode').html(html);
			},
	        error: function(e)
	        {
	          	alert('Error:=' + e);
	        }
	  });
	}
	
	//Changing TextField Value After selection Combo Box
	function funOnChange()
	{
		if(document.getElementById("cmbIndentOn").value=="Minimum Level")
	    {
	        document.all("txtIOCode").style.visibility="hidden";      
	    }
		else
		{
			document.all("txtIOCode").style.visibility="";
		}
	}

	//Open Help
	function funHelp(transactionName)
	{
		fieldName=transactionName;
		//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=", "window", "dialogHeight:600px;dialogWidth:500px;dialogLeft:450px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=", "window", "dialogHeight:600px;dialogWidth:500px;dialogLeft:450px;");
	}
	
	function funHelp1()
	{
		
		var indentOn = $("#cmbIndentOn").val();
		if(indentOn=='Sales Order')
		{
			transactionName='salesorder';
			fieldName=transactionName;
			window.open("searchform.html?formname="+transactionName+"&searchText=", "window", "dialogHeight:600px;dialogWidth:500px;dialogLeft:450px;");
		}
		if(indentOn=='MiProduction Order')
		{
			transactionName='PendingMaterialReq';
			fieldName=transactionName;
			window.open("searchform.html?formname="+transactionName+"&searchText=", "window", "dialogHeight:600px;dialogWidth:500px;dialogLeft:450px;");
		}
		if(indentOn=='Minimum Level')
		{
			transactionName='PendingMaterialReq';
			fieldName=transactionName;
			window.open("searchform.html?formname="+transactionName+"&searchText=", "window", "dialogHeight:600px;dialogWidth:500px;dialogLeft:450px;");
		}
		
		if(indentOn=='Requisition')
		{
			transactionName='PendingMaterialReq';
			fieldName=transactionName;
			window.open("searchform.html?formname="+transactionName+"&searchText=", "window", "dialogHeight:600px;dialogWidth:500px;dialogLeft:450px;");
		}
		
		//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=", "window", "dialogHeight:600px;dialogWidth:500px;dialogLeft:450px;");
		
	}
	
	//Set Data after Selecting help Data 
	function funSetData(code)
	{
		switch (fieldName)
		{
		    case 'locationmaster':
		    	funSetLocation(code);
		        break;
		        
		    case 'suppcode':			    	
		    	funSetSupplier(code);		
		    	
		    case  'PendingMaterialReq' :
		    	$("#txtIOCode").val(code);
		    //	funSetPendingReq(code);
		     break;
		    
		    case 'salesorder' :
		    	$("#txtIOCode").val(code);
		    	break;
		    	
		       
		   
		}
		
	}
	
	//Set Supplier Data
	function funSetSupplier(code)
	{
		$("#txtSuppCode").val(code);
		searchUrl=getContextPath()+"/loadSupplierMasterData.html?partyCode="+code;	
		$.ajax({
					type: "GET",
			        url: searchUrl,	        		
			        dataType: "json",        
			        success: function(response)	        
			        {
			        	$("#lblSuppName").text(response.strPName); 
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

	//Set Location Data
	function funSetLocation(code)
	{
		var searchUrl=getContextPath()+"/loadLocationMasterData.html?locCode="+code;
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	 
			    	$("#txtLocCode").val (response.strLocCode);
		        	$('#lblLocName').text(response.strLocName);
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
	
	 //Get and Display Data on basis of Selection Criteria
	 function btnShow_onclick()
	 {
		//funRemoveProductRows();
		var LocCode=$("#txtLocCode").val();	
		var strGCode=$("#strGCode").val();
		var strSGCode= $('#strSGCode').val();
		var strSuppCode=$("#txtSuppCode").val();
		var oICode= $("#txtIOCode").val();
		if($("#cmbIndentOn").val()=="Minimum Level")
			{
				if(LocCode!="")
				{
					var searchUrl=getContextPath()+"/loadAutoPIData.html?LocCode="+LocCode+"&strGCode="+strGCode+"&strSGCode="+strSGCode+"&strSuppCode="+strSuppCode;
					//alert(searchUrl);
					$.ajax({				
				    	type: "GET",
				        url: searchUrl,
				        dataType: "json",
				        success: function(response)
				        {	 
				        	if(response!="")
			        		{
				        		 members=response;
				        		 showTable();
			        		}
			        	else
			        		{
			        			alert("No Record Found");
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
				else
					{
						alert("Please Select Location");
						return false;
					}
			}
		
		if($("#cmbIndentOn").val()=="Requisition")
		{
			var searchUrl=getContextPath()+"/loadAutoRequitionPIData.html?LocCode="+LocCode+"&strGCode="+strGCode+"&strSGCode="+strSGCode+"&strSuppCode="+strSuppCode+"&strReqCode="+oICode;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	 
				    	if(response!="")
		        		{
			        		 members=response;
			        		// showTable();
			        		 showTableForSalesOrder();
		        		}
		        	else
		        		{
		        			alert("No Record Found");
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
		
		if($("#cmbIndentOn").val()=="Sales Order")
		{
				gurl=getContextPath()+"/loadAutoSalesOrderForPI.html?soCode="+oICode;
				$.ajax({
			        type: "GET",
			        url: gurl,
			        dataType: "json",
			        success: function(response)
			        {		        	
			        		if(response.length==0){
			        			alert("Invalid SO Code");
			        			$("#txtIOCode").val('');
			        			$("#txtIOCode").focus();
			        		}else{	
			        			if(response!="")
				        		{
					        		 members=response;
					        		 showTableForSalesOrder();
				        		}
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
		
	}
	 
	//Pagination Data
	 function showTableForSalesOrder()
		{
			var optInit = getOptionsFromFormForSalesOrder();
		    $("#Pagination").pagination(members.length, optInit);
		    
		}

		//var items_per_page = 15;
		function getOptionsFromFormForSalesOrder(){
		    var opt = {callback: pageselectCallbackForSalesOrder};
			opt['items_per_page'] = items_per_page;
			opt['num_display_entries'] = 10;
			opt['num_edge_entries'] = 3;
			opt['prev_text'] = "Prev";
			opt['next_text'] = "Next";
		    return opt;
		}
		function pageselectCallbackForSalesOrder(page_index, jq){
		    // Get number of elements per pagionation page from form
		    var max_elem = Math.min((page_index+1) * items_per_page, members.length);
		    var newcontent = '<table id="tblProdDet" class="transTablex col9-center" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td>Prod Code</td><td>Prod Name</td><td>Unit Price</td><td>Qty</td><td></td><td></td><td></td><td>Select<input type="checkbox" id="chkALL" onclick="funSelectAll();"/></td></tr>';
																																		   
		    // Iterate through a selection of the content and build an HTML string
		    for(var i=page_index*items_per_page;i<max_elem;i++)
		    {
		        newcontent += '<tr><td><input readonly=\"readonly\" class="Box" size="9.9%" value="'+ members[i].strProdCode+ '"/></td>';
		        newcontent += '<td><input readonly="readonly" class="Box" size=\"54%" value="'+ members[i].strProdName + '"/></td>';
		        newcontent += '<td><input readonly="readonly" class="Box" style="text-align: right;" size="5%" value="'+ members[i].dblRate + '"/></td>';
		        newcontent += '<td><input readonly="readonly" class="Box" style="text-align: right;" size="5%" value="'+ members[i].dblQty + '"/></td>';
		        newcontent += '<td><input readonly="readonly" class="Box" style="text-align: right;" size="5%" value=""/></td>';
		        newcontent += '<td><input readonly="readonly" class="Box" style="text-align: right;" size="5%" value=""/></td>';
		        newcontent += '<td></td>';
		        
		        if(members[i].strChecked=="true")
		        {
		        	newcontent += '<td><input type="checkbox" class="check" id="cbSel.'+i+'" checked="checked" onclick="UpdateItem(this)" /></td></tr>';
		        }
		        else
		        {
		        	newcontent += '<td><input type="checkbox" class="check" id="cbSel.'+i+'" onclick="UpdateItem(this)" /></td></tr>';
		        }
		        
		        
		    }
		     newcontent += '</table>';
		    // Replace old content with new content
		    $('#Searchresult').html(newcontent);
		    
		    if(flagSelectAll==true)
		    	{
		    		document.getElementById("chkALL").checked = true;
		    	}
	    	else
		    	{
		    		document.getElementById("chkALL").checked = false;
		    	}
		    // Prevent click eventpropagation
		    return false;
		}

		/* function UpdateSalesItem(obj)
		{
			var index = obj.id.split('.')[1];
			//alert('Update this value = '+obj.value+' into session object at index = '+index+' using ajax');
			if( members[index].strChecked=="true")
			 {
				members[index].strChecked="false"
			 }
			else
			{
				members[index].strChecked="true"
			}
			 
			var searchUrl=getContextPath()+"/updateAutoPISessionValue.html?chkIndex="+index;
			//alert(searchUrl);
			$.ajax({				
		    	type: "POST",
		        url: searchUrl,
		        dataType: "text",
		        success: function(response)
		        {	
		        		        			        		
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
		} */
	 
	 
	//Pagination Data
	 function showTable()
		{
			var optInit = getOptionsFromForm();
		    $("#Pagination").pagination(members.length, optInit);
		    
		}

		var items_per_page = 15;
		function getOptionsFromForm(){
		    var opt = {callback: pageselectCallback};
			opt['items_per_page'] = items_per_page;
			opt['num_display_entries'] = 10;
			opt['num_edge_entries'] = 3;
			opt['prev_text'] = "Prev";
			opt['next_text'] = "Next";
		    return opt;
		}
		function pageselectCallback(page_index, jq){
		    // Get number of elements per pagionation page from form
		    var max_elem = Math.min((page_index+1) * items_per_page, members.length);
		    var newcontent = '<table id="tblProdDet" class="transTablex col9-center" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td>Prod Code</td><td>Prod Name</td><td>Unit Price</td><td>Avail Stock</td><td>ReOrder level</td><td>ReOrder Qty</td><td>Against</td><td>Select<input type="checkbox" id="chkALL" onclick="funSelectAll();"/></td></tr>';
																																		   
		    // Iterate through a selection of the content and build an HTML string
		    for(var i=page_index*items_per_page;i<max_elem;i++)
		    {
		        newcontent += '<tr><td><input readonly=\"readonly\" class="Box" size="9.9%" value="'+ members[i].strProdCode+ '"/></td>';
		        newcontent += '<td><input readonly="readonly" class="Box" size=\"54%" value="'+ members[i].strProdName + '"/></td>';
		        newcontent += '<td><input readonly="readonly" class="Box" style="text-align: right;" size="5%" value="'+ members[i].dblRate + '"/></td>';
		        newcontent += '<td><input readonly="readonly" class="Box" style="text-align: right;" size="5%" value="'+ members[i].strInStock + '"/></td>';
		        newcontent += '<td><input readonly="readonly" class="Box" style="text-align: right;" size="5%" value="' + members[i].dblMinLevel + '"/></td>';
		        newcontent += '<td><input readonly="readonly" class="Box" style="text-align: right;" size="5%" value="' + members[i].dblReOrderQty + '"/></td>';
		        newcontent += '<td></td>';
		        
		        if(members[i].strChecked=="true")
		        {
		        	newcontent += '<td><input type="checkbox" class="check" id="cbSel.'+i+'" checked="checked" onclick="UpdateItem(this)" /></td></tr>';
		        }
		        else
		        {
		        	newcontent += '<td><input type="checkbox" class="check" id="cbSel.'+i+'" onclick="UpdateItem(this)" /></td></tr>';
		        }
		        
		        
		    }
		     newcontent += '</table>';
		    // Replace old content with new content
		    $('#Searchresult').html(newcontent);
		    
		    if(flagSelectAll==true)
		    	{
		    		document.getElementById("chkALL").checked = true;
		    	}
	    	else
		    	{
		    		document.getElementById("chkALL").checked = false;
		    	}
		    // Prevent click eventpropagation
		    return false;
		}
		
		//Select All Product after clicking select all check box
		function funSelectAll()
		{
			var x =document.getElementById("chkALL").checked;
			if(x==true)
				{
					for(var i=0;i<members.length;i++)
					{
						members[i].strChecked="true";
					}
					flagSelectAll=true;
				}
			else
				{
					for(var i=0;i<members.length;i++)
					{
						members[i].strChecked="false";
					}
					flagSelectAll=false;
				}
			if($("#cmbIndentOn").val()=="Sales Order")
				{
					showTableForSalesOrder();
				}
			if($("#cmbIndentOn").val()=="Requisition")
			{
				showTableForSalesOrder();
			}else
				{
					showTable();
				}
			
			var searchUrl=getContextPath()+"/UpdateAutoPIListSelectAll.html";
			$.ajax({				
		    	type: "POST",
		        url: searchUrl,
		        dataType: "text",
		        success: function(response)
		        {	
		        		        			        		
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
		
		// Update data on the basis of Selection 
		function UpdateItem(obj)
		{
			var index = obj.id.split('.')[1];
			//alert('Update this value = '+obj.value+' into session object at index = '+index+' using ajax');
			if( members[index].strChecked=="true")
			 {
				members[index].strChecked="false"
			 }
			else
			{
				members[index].strChecked="true"
			}
			 
			var searchUrl=getContextPath()+"/updateAutoPISessionValue.html?chkIndex="+index;
			//alert(searchUrl);
			$.ajax({				
		    	type: "POST",
		        url: searchUrl,
		        dataType: "text",
		        success: function(response)
		        {	
		        		        			        		
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
		
	 // After Closing the form Populateing data in Purchase Indent Form
	 function btnClose_onclick()
	 {
		if(document.getElementById("tblProdDet") !== null)
		{
		 $.ajax({				
			 	type: "POST",
			    url: $("#autoPI").attr("action"),
			    data:$("#autoPI").serialize(),
			    async: false,
			    context: document.body,
			    dataType: "json",
			    success: function(response)
			    {	
						str=response;
						window.returnValue = str;
						window.close();
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
    	else
    		{
    		window.close();	
    		}
		
	 }
		
	 
	 
// 	function funOnLoad()
// 	{
// 		funOnChange();
// 	}
	
	
</script>
</head>
<body >
<div style="width: 100%; height: 40px; background-color: #458CCA">
		<p align="center" style="padding-top: 5px;color: white">Auto Generated PI</p>
	</div>
<s:form id="autoPI" name="autoPI" action="fillAutoPIData.html" >

<!--Start Of Filter Table  -->
<div
		style="width: 100%; min-height:450px; height:100%;  overflow-y: auto; padding-bottom: auto;">
		<table class="masterTable" style="width: 100%" >
				<tr>
					<td><label>Group</label></td>
					<td><s:select path="strGCode" items="${command.group}" id="strGCode" onchange="funFillCombo(this.value);" cssClass="BoxW124px"></s:select></td>
					<td><label>SubGroup</label></td>
					<td> <s:select path="strSGCode" items="${command.subGroup}" id="strSGCode" cssClass="BoxW124px"> </s:select></td>
				</tr>
		
				<tr>
					<td><label>Supplier</label></td>
					<td><input id="txtSuppCode" ondblclick="funHelp('suppcode')" class="searchTextBox"></td>
					<td colspan="3"><label id="lblSuppName"></label></td>
				</tr>
				
				<tr>
					<td><label>Indent On</label></td>
					<td><select id="cmbIndentOn" onchange="funOnChange()" class="BoxW200px">
					<option value="Sales Order">Sales Order</option>
					<option value="MiProduction Order">Production Order</option>
					<option value="Minimum Level" selected>Minimum Level</option>
					<option value="Requisition">Requisition</option>
					</select></td>
					<td colspan="2"><input id="txtIOCode" class="searchTextBox" ondblclick="funHelp1()"/></td>
				</tr>
				<tr>
					<td>Location</td>
					<td><input type="text" id="txtLocCode" ondblclick="funHelp('locationmaster')" class="searchTextBox" ></input>
					<label id="lblLocName"></label></td>
					<td><input type="button" id="SHOW" value="Show" class="smallButton" onclick="btnShow_onclick();"></td>
					 <td style="text-align: center;" >
	                    <input id="btnClose" type="Button" value="Close" class="smallButton" onclick="btnClose_onclick()" />
	                    </td>
				</tr>
		
				
		</table>
		<dl id="Searchresult"></dl>
		<div id="Pagination" class="pagination"></div>
		
		<!--End of Filter Table  -->
				<!--Start of Result table  -->
				<%-- <table style="width:100%;font-size:11px;
	font-weight: bold;">
							<tr bgcolor="#75c0ff">
								<td style="width: 8%; height: 16px;" align="center">Prod Code</td>
								<td style="width: 20%; height: 16px;" align="center">Prod name</td>								
								<td style="width: 5%; height: 16px;" align="center">Avail Stock</td>
								<td style="width: 5%; height: 16px;" align="center">Min level</td>
								<td style="width: 5%; height: 16px;" align="center">Order Qty</td>
								<td style="width: 5%; height: 16px;" align="center">Against</td>
								<td style="width: 2%; height: 16px;">Select<input type="checkbox" id="chkALL" /></td>
							</tr>
		
				</table>
		 		<div style="width: 100%;height:400px;overflow: auto;">
		   			<table class="transTablex" style="width: 100%" id="tblProdDet">
		                        <tbody>
		                            <col style="width: 8%" >
		                            <col style="width: 20%">
		                            <col style="width: 5%">
		                            <col style="width: 5%">
		                            <col style="width: 5%">
		                            <col style="width: 10%">
		                            <col style="width: 4.5%">		                           
		                        </tbody>
                        </table>
                </div> --%>
               
					</div>
			<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:50%;left:45%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
		
<!--End Of Result Table  -->
</s:form>

</body>
</html>