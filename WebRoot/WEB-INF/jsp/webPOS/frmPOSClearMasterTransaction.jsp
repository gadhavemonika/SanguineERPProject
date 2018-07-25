<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Clear Master</title>
<script type="text/javascript">

var members="",moduleType="";
var chkAllSelected="";

//Set header Master Or Transaction
$(document).ready(function () {
	var POSDate="${POSDate}"
		var startDate="${POSDate}";
	  	var Date = startDate.split(" ");
		var arr = Date[0].split("-");
		Dat=arr[2]+"-"+arr[1]+"-"+arr[0];	
		$("#txtdteFromDate").datepicker({ dateFormat: 'dd-mm-yy'  });
		$("#txtdteFromDate" ).datepicker('setDate', Dat);
		$("#txtdteFromDate").datepicker();
		$("#txtdteToDate").datepicker({ dateFormat: 'dd-mm-yy'  });
		$("#txtdteToDate" ).datepicker('setDate', Dat);
	strHeadingType='<%=request.getParameter("strHeadingType") %>'
	$("#strHeader").text(strHeadingType);
	moduleType = $("#strHeader").text(strHeadingType);
// 	funLoadProperty('All');
});


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
						%>alert("Data "+message);
						window.returnValue = "saved";
						window.close();
						<%
					}
				}%>
			});


	
	
	//After submit Return Selected Module List
	function funSubmit_click()
	{
		var posName = $('#cmbPOSName').find('option:selected').text();
		alert($('#cmbPOSName').find('option:selected').text());
		var fromDate = $("#txtdteFromDate").val();
		var toDate = $("#txtdteToDate").val();
		
		var alldata = {members:members,posName:posName,fromDate:fromDate,toDate:toDate,chkAllSelected:chkAllSelected};
		window.returnValue = alldata;
		window.close();	
	}
	function funOnLoad()
	{
		funLoadMastersData();
		funLoadPosName();
	}

	
	function funLoadMastersData()
	{
		var strType = $("#strHeader").text();
		if(strType=='Transaction')
		{
		document.all["transactionRow" ].style.display = 'block';
		document.all["lblPosName"].style.display = 'block';
			document.all["cmbPOSName"].style.display = 'block';
		document.all["lblFromDate"].style.display = 'block';
		document.all["txtdteFromDate"].style.display = 'block';
		document.all["lblToDate"].style.display = 'block';
		document.all["txtdteToDate"].style.display = 'block';
		
		}
		var searchurl=getContextPath()+"/loadMastersData.html?strHeadingType="+strType;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        
			        success: function (response) {
			        	
			        	members = response.masterDtl;
			        	showTable();
// 			            	$.each(response.masterDtl, function(i, item) {
			            		
// 			            		funfillMastersGrid(item.moduleName,item.flag);

// 							});
			    
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
	    var newcontent = '<table id="tblMasters" class="transTablex col2-center" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td>Module Name</td><td>Select<input type="checkbox" id="chkALL" onclick="funSelectAll();"/></td></tr>';
	  
	    
	    // Iterate through a selection of the content and build an HTML string
	    for(var i=page_index*items_per_page;i<max_elem;i++)
	    {
	        newcontent += '<tr><td><input readonly=\"readonly\" class="Box" size="35%" value="'+ members[i].moduleName+'"/></td>';
	        if(members[i].flag=="true")
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
	
	//Select All Form
	function funSelectAll()
	{
		var x =document.getElementById("chkALL").checked;
		chkAllSelected = x;
		if(x==true)
			{
				for(var i=0;i<members.length;i++)
				{
					members[i].flag="true";
				}
				flagSelectAll=true;
			}
		else
			{
				for(var i=0;i<members.length;i++)
				{
					members[i].flag="false";
				}
				flagSelectAll=false;
			}
		
		showTable();
	}
	
	function UpdateItem(obj)
	{
		var index = obj.id.split('.')[1];
		if( members[index].flag=="true")
		 {
			members[index].flag="false"
		 }
		else
		{
			members[index].flag="true"
		}
	}
	
	function funLoadPosName()
	{
		searchUrl = getContextPath()+ "/loadPosName.html";
		//alert(searchUrl);
		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				
				$.each(response, function(i, items) 
						{ 
						  $('#cmbPOSName').append( $('<option></option>').val(i).html(items) );
						});
				var propName=$('#cmbPOSName').find('option:selected').text();
				
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
				<td><label id="lblPosName">POS Name</label> 
				 <select id="cmbPOSName" name="cmbPOSName"  class="BoxW124px"></select></td>
				<td><label id="lblFromDate">From Date</label> 
				<input id="txtdteFromDate" name="txtdteFromDate" class="calenderTextBox" /></td>

				<td><label id="lblToDate">To Date</label> 
				<input id="txtdteToDate" name="txtdteToDate" class="calenderTextBox" />
				</td>
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
