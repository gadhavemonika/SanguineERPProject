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
<script type="text/javascript">

var members,propertyName,locationName;

	//Set header Master Or Transaction
	$(document).ready(function () {
		strHeadingType='<%=request.getParameter("strHeadingType") %>'
		$("#strHeader").text(strHeadingType);
		funLoadProperty('All');
	});
	
	//Get Project Path
	function getContextPath() 
	{
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	
	//Pagination Used
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
	    var newcontent = '<table id="tblFromDtl" class="transTablex col2-center" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td>Module Name</td><td>Select<input type="checkbox" id="chkALL" onclick="funSelectAll();"/></td></tr>';
	  
	    
	    // Iterate through a selection of the content and build an HTML string
	    for(var i=page_index*items_per_page;i<max_elem;i++)
	    {
	        newcontent += '<tr><td><input readonly=\"readonly\" class="Box" size="35%" value="'+ members[i].strFormDesc+'"/></td>';
	        if(members[i].strDelete=="true")
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
	
	//Update Selection 
	function UpdateItem(obj)
	{
		var index = obj.id.split('.')[1];
		if( members[index].strDelete=="true")
		 {
			members[index].strDelete="false"
		 }
		else
		{
			members[index].strDelete="true"
		}
	}
	
	//Select All Form
	function funSelectAll()
	{
		var x =document.getElementById("chkALL").checked;
		if(x==true)
			{
				for(var i=0;i<members.length;i++)
				{
					members[i].strDelete="true";
				}
				flagSelectAll=true;
			}
		else
			{
				for(var i=0;i<members.length;i++)
				{
					members[i].strDelete="false";
				}
				flagSelectAll=false;
			}
		
		showTable();
	}
	
	//Loding All Module
	function funOnLoad()
	{
		var strType = $("#strHeader").text();
		if(strType=='Transaction')
			{
			document.all["transactionRow" ].style.display = 'block';
			document.all["lblPropCode"].style.display = 'block';
 			document.all["cmbProperty"].style.display = 'block';
			document.all["lblLocCode"].style.display = 'block';
			document.all["cmbLoction"].style.display = 'block';
			
			}
		
			searchUrl = getContextPath()+ "/frmExciseFillActionList.html?strHeadingType="+strType;
	
			$.ajax({
				type : "GET",
				url : searchUrl,
				dataType : "json",
				success : function(response) {
					 members=response;
					 showTable();
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
	
	//After submit Return Selected Module List
	function funSubmit_click()
	{
		var alldata = {members:members, propertyName:propertyName, locationName:locationName};
		window.returnValue = alldata;
		window.close();	
	}

	//Reset the field
	function funResetFields()
	{
		location.reload(true); 
	}
	
	function funLoadProperty(property)
	{
		searchUrl = getContextPath()+ "/loadExcisePropertyName.html?propName="+property;
		//alert(searchUrl);
		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				
				$.each(response, function(i, items) 
						{ 
						  $('#cmbProperty').append( $('<option></option>').val(i).html(items) );
						});
				var propName=$('#cmbProperty').find('option:selected').text();
				//alert(propName);
				propertyName=propName;
				funLoctionName(propName);
				
				
				//alert(response);
				
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
	
	function funLoctionName(propName)
	{
		
		searchUrl = getContextPath()+ "/loadExciseLocName.html?propName="+propName;
		//alert(searchUrl);
		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				
				$.each(response, function(i, items) 
						{ 
						  $('#cmbLoction').append( $('<option></option>').val(i).html(items) );
						});
						
				locationName=$('#cmbLoction').find('option:selected').text();
				//alert(response);
				
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
	
</script>
<style type="text/css">

</style>

<title>Insert title here</title>
<%-- <tab:tabConfig /> --%>
</head>
<body onload="funOnLoad()">

	<div id="formHeading">
		<label id="strHeader"></label>
	</div>
	<div>
		<form action="frmSaveData.html" method="POST">
			<br />
		
			<dl id="Searchresult"></dl>
			
			<table  class="masterTable">
			<tr id="transactionRow"   style="display:none">
				<td><label id="lblPropCode" style="display:none">Property</label></td>
				<td><select id="cmbProperty"  path="strPropName" style="display:none" class="BoxW124px"></select></td>
				<td><label id="lblLocCode" style="display:none">Location</label></td>
				<td><select id="cmbLoction"  path="strLocName" style="display:none" class="BoxW124px"></select></td>
			</tr>
			
			</table>
			
			
		<div id="Pagination" class="pagination"></div>
			
			<br>
			<br>
			<p align="center">
			<input type="button" value="Submit" class="form_button" onclick="funSubmit_click();" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields();" /><br/></p>
		</form>
	</div>
</body>
</html>