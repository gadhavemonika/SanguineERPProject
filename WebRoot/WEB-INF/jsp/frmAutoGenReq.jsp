<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
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
<title>Auto Generate Requisition</title>
<script type="text/javascript">
	// After Presssing ESC button on Key Board Close the Form
	window.onkeyup = function (event) {
		if (event.keyCode == 27) {
			window.close ();
		}
	}
</script>
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

	//Fill SubGroup after changing Group Code
	function funFillCombo(code) {
		var searchUrl = getContextPath() + "/loadSubGroupCombo.html?code="
				+ code;
		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				var html = '<option value="ALL">ALL</option>';
				$.each(response, function(key, value) {
					html += '<option value="' + key + '">' + value
							+ '</option>';
				});
				html += '</option>';
				$('#strSGCode').html(html);
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

   //Open Help
	function funHelp(transactionName)
	{
		fieldName=transactionName;
		//retval=window.showModalDialog("frmAutoGenReq.html","","dialogHeight:800px;dialogWidth:800px;dialogLeft:250px;");
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=", 'window', "dialogHeight:600px;dialogWidth:600px;dialogLeft:300px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=", 'window', "dialogHeight:600px;dialogWidth:600px;dialogLeft:300px;");
	
	}	
	
	function getContextPath() 
	{
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	
	//Set Data after Selecting help Data 
	function funSetData(code)
	{	switch (fieldName)
		{
	    case 'locationmaster':
	    	funSetLocation(code);
	        break;
		}
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
	        	//alert(response);
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
		
	
	//Get and Display Data on basis of Selection Criteria
	function btnShow_onclick()
	{
		//funRemoveProductRows() ;
		var LocCode=$("#txtLocCode").val();
		var strGCode=$("#strGCode").val();
		var strSGCode= $('#strSGCode').val();
		var strSuppCode="";
		if(LocCode!="")
			{
			var searchUrl=getContextPath()+"/loadAutoReqData.html?LocCode="+LocCode+"&strGCode="+strGCode+"&strSGCode="+strSGCode+"&strSuppCode="+strSuppCode;			
			//alert(searchUrl);	
			$.ajax({				
			    	type: "GET",
			        url: searchUrl,
			        dataType: "json",
			        async: false,
			      
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
	    var newcontent = '<table id="tblProdDet" class="transTablex col9-center" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td>Product Code</td><td>Product Name</td><td>UOM</td><td>Available Stock</td><td>ReOrder level</td><td>ReOrder Qty</td><td>Open Req</td><td>Order Qty</td><td>Rate</td><td>Select<input type="checkbox" id="chkALL" onclick="funSelectAll();"/></td></tr>';
	  
	    
	    // Iterate through a selection of the content and build an HTML string
	    for(var i=page_index*items_per_page;i<max_elem;i++)
	    {
	        newcontent += '<tr><td><input readonly=\"readonly\" class="Box" size="9.9%" value="'+ members[i].strProdCode+ '"/></td>';
	        newcontent += '<td><input readonly="readonly" class="Box" size=\"54%" value="'+ members[i].strProdName + '"/></td>';
	        newcontent += '<td><input readonly="readonly" class="Box" size=\"5%" value="'+ members[i].strUOM + '"/></td>';
	        newcontent += '<td><input readonly="readonly" class="Box" style="text-align: right;" size="5%" value="'+ members[i].availStock + '"/></td>';
	        newcontent += '<td><input readonly="readonly" class="Box" style="text-align: right;" size="5%" value="' + members[i].dblReOrderLevel + '"/></td>';
	        newcontent += '<td><input readonly="readonly" class="Box" style="text-align: right;" size="5%" value="' + members[i].dblReOderQty + '"/></td>';
	        newcontent += '<td><input readonly="readonly" class="Box" style="text-align: right;" size="5%" value="' + members[i].openReq + '"/></td>';
	        newcontent += '<td><input readonly="readonly" class="Box" style="text-align: right;" size="5%" value="'+ members[i].dblOrderQty+ '"/></td>';
	        newcontent += '<td><input readonly="readonly" class="Box" style="text-align: right;" size="5%" value="' + members[i].dblPrice + '"/></td>';
	        
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
		
		showTable();
		
		// Update data on the basis of Selection 
		var searchUrl=getContextPath()+"/UpdateAutoReqListSelectAll.html";
		$.ajax({				
	    	type: "POST",
	        url: searchUrl,
	        dataType: "text",
	        success: function(response)
	        {	
	        		       // alert(response);			        		
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
	
	function UpdateItem(obj)
	{
		var index = obj.id.split('.')[1];
		if( members[index].strChecked=="true")
		 {
			members[index].strChecked="false"
		 }
		else
		{
			members[index].strChecked="true"
		}
		 
		var searchUrl=getContextPath()+"/updateAutoReqSessionValue.html?chkIndex="+index;
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


	// After Closing the form Populateing data in Requisition Form
	function btnClose_onclick() 
	{
		if(document.getElementById("tblProdDet") !== null)
		{
		var searchUrl=getContextPath()+"/fillAutoReqData.html";
		$.ajax({				
	    	type: "POST",
	        url: searchUrl,
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

	
</script>
</head>
<body>
<div style="width: 100%; height: 40px; background-color: #458CCA">
		<p align="center"  style="padding-top: 5px;color: white">Auto Generate Requisition</p>
	</div>
	<s:form id="autoReq" name="autoReq" action="fillAutoReqData.html" >
	<div
		style="width: 100%; min-height:450px; height:100%;  overflow-y: auto; padding-bottom: auto;">
		<table  class="masterTable" style="width: 100%">
			<tr>
				<td><label>Group</label></td>
				<td><s:select path="strGCode" items="${command.group}"
						id="strGCode" onchange="funFillCombo(this.value);" cssClass="BoxW124px"></s:select></td>
				<td><label>SubGroup</label></td>
				<td><s:select path="strSGCode" items="${command.subGroup}"
						id="strSGCode" cssClass="BoxW124px">
					</s:select></td>
			</tr>
			<tr>

				<td><label>Location By:</label></td>
				<td><input id="txtLocCode" name="txtLocCode"
					ondblclick="funHelp('locationmaster')"  class="searchTextBox" />
					<label id="lblLocName"></label>
				</td>
				<td><input id="btnShow" type="Button" value="Show"
					onclick="return btnShow_onclick()"  class="smallButton"/>
				</td>
				<td style="text-align: center;"><input id="btnClose" onclick="btnClose_onclick();"
					type="button" value="Close"  class="smallButton" />
				</td>
			</tr>
			
		</table>

	<dl id="Searchresult"></dl>
		<div id="Pagination" class="pagination"></div>
		<div id="wait" style="display:none;width:80px;height:80px;border:0px solid black;position:absolute;top:45%;left:45%;padding:2px;">
		<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="80px" height="80px" /></div>
		</div>
	</s:form>
</body>
</html>