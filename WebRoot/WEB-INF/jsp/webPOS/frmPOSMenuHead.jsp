<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Menu Head</title>
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

//for Tabs

var fieldName,searchForm,selectedRowIndex=0;

//Initialize tab Index or which tab is Active
$(document).ready(function() 
{		
	$(".tab_content").hide();
	$(".tab_content:first").show();

	$("ul.tabs li").click(function() {
		$("ul.tabs li").removeClass("active");
		$(this).addClass("active");
		$(".tab_content").hide();
		var activeTab = $(this).attr("data-state");
		$("#" + activeTab).fadeIn();
	});
		
	$(document).ajaxStart(function(){
	    $("#wait").css("display","block");
	});
	$(document).ajaxComplete(function(){
	   	$("#wait").css("display","none");
	});
	
	 $("form").submit(function(event){
		  if($("#txtMenuHeadName").val().trim()=="")
			{
				alert("Please Enter Menu Head Name");
				return false;
			}
		  else{
			  flg=funCallFormAction();
			  return flg;
		  }
		});
});

/*On form Load It Reset form :Ritesh 22 Nov 2014*/
$(document).ready(function () {
	
	$('input#txtMenuHeadCode').mlKeyboard({layout: 'en_US'});
	$('input#txtMenuHeadName').mlKeyboard({layout: 'en_US'});
	$('input#txtSubMenuHeadCode').mlKeyboard({layout: 'en_US'});
	$('input#txtSubMenuHeadName').mlKeyboard({layout: 'en_US'});
	$('input#txtSubMenuHeadName').mlKeyboard({layout: 'en_US'});
	$('input#strSubMenuHeadShortName').mlKeyboard({layout: 'en_US'});
	
   $("#txtMenuHeadName").focus();
   //$("#tab3").click(function funSetTable());
    $("#t3").on("click", function(){
       $("#tableLoad").load(funLoadMenuHeadData());
     }); 
   
   }); 


/**
* Reset The Group Name TextField
**/
function funResetFields()
{
	$("#txtMenuHeadName").focus();
	$("#cmbOperational").val('N');
}

function funMoveSelectedRow(count)
{
	if(count==1)
		{
			if (selectedRowIndex == 0)
			{
				//do nothing
			}
			else
			{
			  var table = document.getElementById("tblMenuDet");
			  var menuHeadCode=table.rows[selectedRowIndex].cells[1].innerHTML;
			  var menuHeadName=table.rows[selectedRowIndex].cells[2].innerHTML; 
			  var menuHeadCode1=table.rows[selectedRowIndex-1].cells[1].innerHTML;
			  var menuHeadName1=table.rows[selectedRowIndex-1].cells[2].innerHTML; 
			  funMoveRowUp(menuHeadCode,menuHeadName,selectedRowIndex,menuHeadCode1,menuHeadName1);
			}
			
		}
		else
		{
			var table = document.getElementById("tblMenuDet");
			var rowCount = table.rows.length;
			if(rowCount>0)
			{
				var table = document.getElementById("tblMenuDet");
				var menuHeadCode=table.rows[selectedRowIndex].cells[1].innerHTML;
				var menuHeadName=table.rows[selectedRowIndex].cells[2].innerHTML; 
				var menuHeadCode1=table.rows[selectedRowIndex+1].cells[1].innerHTML;
				var menuHeadName1=table.rows[selectedRowIndex+1].cells[2].innerHTML; 
				funMoveRowDown(menuHeadCode,menuHeadName,selectedRowIndex,menuHeadCode1,menuHeadName1);
			}
			
		}
}


function funGetSelectedRowIndex(obj)
{
	 var index = obj.parentNode.parentNode.rowIndex;
	 var table = document.getElementById("tblMenuDet");
	 if((selectedRowIndex>0) && (index!=selectedRowIndex))
	 {
		 if(selectedRowIndex%2==0)
		 {
			 row = table.rows[selectedRowIndex];
			 row.style.backgroundColor='#A3D0F7';
			 selectedRowIndex=index;
			 row = table.rows[selectedRowIndex];
			 row.style.backgroundColor='#ffd966';
			 row.hilite = true;
		 }
		 else
		 {
			 row = table.rows[selectedRowIndex];
			 row.style.backgroundColor='#C0E4FF';
			 selectedRowIndex=index;
			 row = table.rows[selectedRowIndex];
			 row.style.backgroundColor='#ffd966';
			 row.hilite = true;
         }
		
	 }
	 else
	 {
		 selectedRowIndex=index;
		 row = table.rows[selectedRowIndex];
		 row.style.backgroundColor='#ffd966';
		 row.hilite = true;
	 }
	 
	
}


function funLoadMenuHeadData()
{

	var searchurl=getContextPath()+"/loadMenuHeadData.html";
	 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        
		        success: function (response) {
		        	funRemoveProductRows();
		           // for (var i in response){		            	
		            	$.each(response,function(i,item){
		            	
		            		funfillMenuDetail(response[i].strMenuHeadCode,response[i].strMenuHeadName);
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

function funfillMenuDetail(strMenuHeadCode,strMenuHeadName)
{
	var table = document.getElementById("tblMenuDet");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

      row.insertCell(0).innerHTML= "<input name=\"listMenuMasterDtl["+(rowCount)+"].sequenceNo\" readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(rowCount)+"\" value='"+(rowCount+1)+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	  row.insertCell(1).innerHTML= "<input name=\"listMenuMasterDtl["+(rowCount)+"].strMenuHeadCode\" readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtMenuHeadCode."+(rowCount)+"\" value='"+strMenuHeadCode+"'onclick=\"funGetSelectedRowIndex(this)\"/>";
	  row.insertCell(2).innerHTML= "<input name=\"listMenuMasterDtl["+(rowCount)+"].strMenuHeadName\" readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtMenuHeadName."+(rowCount)+"\" value='"+strMenuHeadName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	
}


function funMoveRowUp(strMenuHeadCode,strMenuHeadName,rowCount,strMenuHeadCode1,strMenuHeadName1)
{
	var table = document.getElementById("tblMenuDet");
    table.deleteRow(rowCount);
    var row = table.insertRow(rowCount-1);
    row = table.rows[rowCount-1];
	
	var codeArr = strMenuHeadCode.split('value=');
	var code=codeArr[1].split('onclick=');
	var menuCode=code[0].substring(1, (code[0].length-2));
	var nameArr = strMenuHeadName.split('value=');
	var name=nameArr[1].split('onclick=');
	var menuName=name[0].substring(1, (name[0].length-2));
	
	listMenuMasterDtl[row].strMenuHeadName=menuName;
	listMenuMasterDtl[row].strMenuHeadCode=menuCode;
	
	  row.insertCell(0).innerHTML= "<input name=\ readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(rowCount)+"\" value='"+(rowCount)+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	  row.insertCell(1).innerHTML= "<input name=\ readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtMenuHeadCode."+(rowCount)+"\" value='"+menuCode+"'onclick=\"funGetSelectedRowIndex(this)\"/>";
	  row.insertCell(2).innerHTML= "<input name=\ readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtMenuHeadName."+(rowCount)+"\" value='"+menuName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	  row = table.rows[rowCount-1];
	  row.style.backgroundColor='#ffd966';
	  selectedRowIndex=rowCount-1;
	    
	 var nextcodeArr = strMenuHeadCode1.split('value=');
     var nextcode=nextcodeArr[1].split('onclick=');
	 var nextmenuCode=nextcode[0].substring(1, (nextcode[0].length-2));
	 var nextnameArr = strMenuHeadName1.split('value=');
	 var nextname=nextnameArr[1].split('onclick=');
	 var nextmenuName=nextname[0].substring(1, (nextname[0].length-2));  
	 var row1 = table.insertRow(rowCount+1);
	 
	  row1.insertCell(0).innerHTML= "<input name=\ readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(rowCount+1)+"\" value='"+(rowCount+1)+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	  row1.insertCell(1).innerHTML= "<input name=\ readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtMenuHeadCode."+(rowCount)+"\" value='"+nextmenuCode+"'onclick=\"funGetSelectedRowIndex(this)\"/>";
	  row1.insertCell(2).innerHTML= "<input name=\ readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtMenuHeadName."+(rowCount)+"\" value='"+nextmenuName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	  table.deleteRow(rowCount);
}



function funMoveRowDown(strMenuHeadCode,strMenuHeadName,rowCount,strMenuHeadCode1,strMenuHeadName1)
{
	var table = document.getElementById("tblMenuDet");
    table.deleteRow(rowCount);
    var row = table.insertRow(rowCount+1);
    row = table.rows[rowCount+1];
	
	var codeArr = strMenuHeadCode.split('value=');
	var code=codeArr[1].split('onclick=');
	var menuCode=code[0].substring(1, (code[0].length-2));
	var nameArr = strMenuHeadName.split('value=');
	var name=nameArr[1].split('onclick=');
	var menuName=name[0].substring(1, (name[0].length-2));
	
	  row.insertCell(0).innerHTML= "<input name=\ readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(rowCount+2)+"\" value='"+(rowCount+2)+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	  row.insertCell(1).innerHTML= "<input name=\ readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtMenuHeadCode."+(rowCount)+"\" value='"+menuCode+"'onclick=\"funGetSelectedRowIndex(this)\"/>";
	  row.insertCell(2).innerHTML= "<input name=\ readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtMenuHeadName."+(rowCount)+"\" value='"+menuName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	  row = table.rows[rowCount+1];
	  row.style.backgroundColor='#ffd966';
	  selectedRowIndex=rowCount+1;
	  
	var nextcodeArr = strMenuHeadCode1.split('value=');
    var nextcode=nextcodeArr[1].split('onclick=');
	var nextmenuCode=nextcode[0].substring(1, (nextcode[0].length-2));
	var nextnameArr = strMenuHeadName1.split('value=');
	var nextname=nextnameArr[1].split('onclick=');
	var nextmenuName=nextname[0].substring(1, (nextname[0].length-2));  
	var row1 = table.insertRow(rowCount);
	 
	  row1.insertCell(0).innerHTML= "<input name=\"listMenuMasterDtl["+(rowCount)+"].sequenceNo\" readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(rowCount+1)+"\" value='"+(rowCount+1)+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	  row1.insertCell(1).innerHTML= "<input name=\"listMenuMasterDtl["+(rowCount)+"].strMenuHeadCode\" readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtMenuHeadCode."+(rowCount)+"\" value='"+nextmenuCode+"'onclick=\"funGetSelectedRowIndex(this)\"/>";
	  row1.insertCell(2).innerHTML= "<input name=\"listMenuMasterDtl["+(rowCount)+"].strMenuHeadName\" readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtMenuHeadName."+(rowCount)+"\" value='"+nextmenuName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	  table.deleteRow(rowCount+1);
	  
}

//Remove Table data when pass a table ID as parameter
function funRemoveProductRows()
		{
			var table = document.getElementById("tblMenuDet");
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
		}

	/**
	* Open Help
	**/
	function funHelp(transactionName)
	{	    
		searchForm=transactionName;
       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
    }
	
	/**
	* Get and Set data from help file and load data Based on Selection Passing Value(Group Code)
	**/
	function funSetData(code)
	{
		switch (searchForm)
		{
		case 'POSMenuHeadMaster':
	    	funSetMenuHeadCode(code);
			break;
	        
	    case 'POSSubMenuHeadMaster':			    	
	    	funSetSubMenuHeadCode(code)
	        break;
	        
		}
	}
		
	function funSetSubMenuHeadCode(code)
	{
		$("#txtSubMenuHeadCode").val(code);
		var searchurl=getContextPath()+"/loadPOSSubMenuHeadMasterData.html?POSSubMenuHeadCode="+code;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strSubMenuHeadCode=='Invalid Code')
			        	{
			        		alert("Invalid Sub Menu Code");
			        		$("#txtSubMenuHeadCode").val('');
			        	}
			        	else
			        	{
			        		
			        		$("#txtSubMenuHeadCode").val(response.strSubMenuHeadCode);
				        	$("#txtSubMenuHeadName").val(response.strSubMenuHeadName);
				        	$("#txtSubMenuHeadName").focus();
				        	$("#txtMenuHeadCodeInSub").val(response.strMenuHeadCode);
				        	$("#txtSubMenuHeadShortName").val(response.strSubMenuHeadShortName);
				        	$("#cmbSubMenuOperational").val(response.strSubMenuOperational);
			        	}
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
	function funSetMenuHeadCode(code)
	{
		$("#txtMenuHeadCode").val(code);
		var searchurl=getContextPath()+"/loadPOSMenuHeadMasterData.html?POSMenuHeadCode="+code;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strMenuHeadCode=='Invalid Code')
			        	{
			        		alert("Invalid Menu Code");
			        		$("#txtMenuHeadCode").val('');
			        		
			        	}
			        	else
			        	{
			        		$("#txtMenuHeadCodeInSub").val(response.strMenuHeadCode);
				        	$("#txtMenuHeadCode").val(response.strMenuHeadCode);
				        	$("#txtMenuHeadName").val(response.strMenuHeadName);
				        	$("#txtMenuHeadName").focus();
				        	$("#cmbOperational").val(response.strOperational);
				        	$("#txtMenuHeadCodeInSub").val(response.strMenuHeadCode);
				        	
			        	}
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
	
	
	/**
	*  Check Validation Before Saving Record
	**/
	function funCallFormAction(actionName,object) 
	{
		var flg=true;
		
		 if($('#txtMenuHeadName').val()!='')
		{ 
			var menuName = $('#txtMenuHeadName').val();
			var code= $('#txtMenuHeadCode').val();
			var checkUrl =getContextPath()+"/checkMenuName.html?menuName="+menuName+"&menuCode="+code;
			 $.ajax({
			        type: "GET",
			        url: checkUrl,
			        async: false,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response==false)
			        		{
			        			alert("Menu Name Already Exist!");
			        			$('#txtMenuHeadName').focus();
			        			flg= false;
				    		}
				    	else
				    		{
				    			flg=true;
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
		 if($('#txtSubMenuHeadName').val()!='')
		{ 
			var code = $('#txtSubMenuHeadCode').val();
			var name= $('#txtSubMenuHeadName').val();
			 $.ajax({
			        type: "GET",
			        url: getContextPath()+"/checkSubMenuName.html?subMenuName="+name+"&subMenuCode="+code,
			        async: false,
			        dataType: "text",
			        success: function(response)
			        {
			        	if(response==false)
			        		{
			        			alert("Sub Menu Name Already Exist!");
			        			$('#txtSubMenuHeadName').focus();
			        			flg= false;
				    		}
				    	else
				    		{
				    			flg=true;
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
		
		
		return flg;
	}

</script>
</head>
<body>

<div id="formHeading">
		<label>Menu Head Master</label>
	</div>

	<br />
	<br />
	<s:form name="MenuHead" method="POST" action="saveMenuHeadMaster.html?saddr=${urlHits}">
		<br> 
		<br>
		<div id="tab_container" style="height: 405px">
				<ul class="tabs">
					<li data-state="tab1" style="width: 12%; padding-left: 2%;margin-left: 10%; " class="active" >Menu Head Master</li>
					<li data-state="tab2" style="width: 10%; padding-left: 1%">Sub Menu Head</li>
					<li data-state="tab3" id="t3" style="width: 16%; padding-left: 1%">Menu Head Sequence</li>
				</ul>
							
				<!-- Menu Head Master Tab Start -->
				<div id="tab1" class="tab_content" style="height: 400px">
				
				<br> 
					<br>					
					<table class="masterTable">
					<tr>
					<td></td>
					<td></td>
					<td></td>
					</tr>
						<tr>
							<td width="20%"><label>Menu Head Code</label></td>
							<td width="40%"><s:input id="txtMenuHeadCode" path="strMenuHeadCode" cssClass="searchTextBox" ondblclick="funHelp('POSMenuHeadMaster')" /></td>				
							<td></td>
						</tr>
						<tr>
			    			<td width="20%"><label>Menu Head Name</label></td>
							<td width="40%"><s:input id="txtMenuHeadName" path="strMenuHeadName" cssClass="longTextBox" /></td>				
			    		<td></td>
						</tr>
						<tr>
							<td><label>Operational</label></td>
				 				<td>
				 				<s:select id="cmbOperational" path="strOperational" cssClass="BoxW124px">
				    			<option selected="selected" value="Y">Yes</option>
			        			<option value="N">No</option>
		         			</s:select>
							</td>
							<td></td>
						</tr>
				<tr></tr>
				<tr></tr>
				<tr>
				<td><s:input colspan="3" type="hidden"  id="txtOperationType" value="N" name="txtOperationType" path="strOperationType"/>
				<td></td> 
				<td></td>
			</tr>
						</table>
				
						</div>
						<!-- Menu Head Master Tab End -->
				
				<!--Sub Menu Head Master Tab Start -->
				
				<div id="tab2" class="tab_content" style="height: 400px">
				<br> 
					<br>					
					<table class="masterTable">
					<tr>
					<td></td>
					<td></td>
					<td></td>
					</tr>		 
						<tr>
							<td width="20%"><label>Sub Menu Head Code</label></td>
							<td width="40%"><s:input id="txtSubMenuHeadCode" path="strSubMenuHeadCode" cssClass="searchTextBox" ondblclick="funHelp('POSSubMenuHeadMaster')" /></td>				
							<td></td>
						</tr>
						<tr>
			    			<td width="20%"><label>Sub Menu Head Name</label></td>
							<td width="40%"><s:input id="txtSubMenuHeadName" path="strSubMenuHeadName" cssClass="longTextBox" /></td>				
			    		<td></td>
						</tr>
						<tr>
			    			<td width="20%"><label>Sub Menu Head Short Name</label></td>
							<td width="40%"><s:input id="txtSubMenuHeadShortName" path="strSubMenuHeadShortName" cssClass="longTextBox" /></td>				
			    		<td></td>
						</tr>
						<tr>
							<td width="20%"><label>MenuHead Code</label></td>
							<td width="40%"><s:input id="txtMenuHeadCodeInSub" path="strMenuHeadCodeInSub" cssClass="searchTextBox" ondblclick="funHelp('POSMenuHeadMaster')" /></td>				
							<td></td>
						</tr>
						<tr>
							<td><label>Operational</label></td>
				 				<td>
				 				<s:select id="cmbSubMenuOperational" path="strSubMenuOperational" cssClass="BoxW124px">
				    			<option selected="selected" value="Y">Yes</option>
			        			<option value="N">No</option>
		         			</s:select>
							</td>
							<td></td>
						</tr>
				<tr></tr>
				<tr></tr>
							<tr>
				<td><s:input colspan="3" type="hidden"  id="txtOperationType" value="N" name="txtOperationType" path="strOperationType"/>
				<td></td> 
				<td></td>
			</tr>
				<%-- <s:hidden path="listMenuMasterDtl" id="hiddenlist"/> --%>
						</table>
						<p align="center">
						<br>
	</div>	
						
				<!--Sub Menu Head Master Tab End -->
				
				<!-- Menu Head Sequence Tab Start -->
				
				
	<div id="tab3" class="tab_content" style="height: 400px" onload="funLoadMenuHeadData()">   
					<br> 
					<br>
					<table class="">
					<tr></tr>
					<tr>
					<td width="120px"></td>
					<td>
					
			<div id="tableLoad" align="center" class="" style="width: 400px; height: 300px; margin-left: 80px;">
			<table style="height: 20px; border: #0F0;width: 100%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="85px"><label style ="text-align:center;"> Sequence No.</label></td>					
					<td width="115px">Menu Head Code</td>	
					<td width="130px">Menu Head Name</td>
				</tr>
			</table>
			
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 263px;width: 350px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
					<table id="tblMenuDet"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:85px">					
					<col style="width:115px">
					<col style="width:130px">
					
					</tbody>
				</table>
			</div>
			
			<div>
			 <img  src="../${pageContext.request.contextPath}/resources/images/imgMoveUp.png" onclick="funMoveSelectedRow(1)">
			 <img  src="../${pageContext.request.contextPath}/resources/images/imgMoveDown.png" onclick="funMoveSelectedRow(0)">
			</div>
			
		</div>
</td>
		</tr>
				</table>		
		</div>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:45%;left:45%;padding:2px;">
					<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
				</div>
</div>
<p align="center">

		<input type="submit" value="Submit" tabindex="3" class="form_button" />  
		<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
	</p>
			 </s:form>
<!-- onclick="return funCallFormAction('submit',this)" -->
</body>
</html>