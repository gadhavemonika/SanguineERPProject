<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

<script type="text/javascript">
	$(document).ready(function() 
		{
		 $('input#txtTableNo').mlKeyboard({layout: 'en_US'});
		  $('input#txtTableName').mlKeyboard({layout: 'en_US'});
		  $('input#txtPaxNo').mlKeyboard({layout: 'en_US'});
		  
		$(".tab_content").hide();
		$(".tab_content:first").show();

		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();

			var activeTab = $(this).attr("data-state");
			$("#" + activeTab).fadeIn();
		});
		
		
		  $("#tblMaster").click(function(event){
			  if($("#txtTableName").val().trim()=="")
				{
					alert("Please Enter Table Name");
					return false;
				}
			  if($("#txtTableName").val().length > 20)
				{
					alert("Table Name length must be less than 20");
					return false;
				}
			 
			  else{
				  flg=funCallFormAction();
				  return flg;
			  }
			});
		  
		  $("#tblSequence").click(function(event){
			  funSubmitSequence();
			});
	});
</script>
<script type="text/javascript">

var selectedRowIndex=0;

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
	
	var fieldName;
	function funSubmitSequence()
	{
	document.POSTableMaster.action="savePOSTableSequence.html";
	document.POSTableMaster.submit();
	}

	function funSetData(code){

		switch(fieldName){

			case 'POSTableMaster' : 
				funSetTableNo(code);
				break;
		}
	}


	function funSetTableNo(code){

			$("#txtTableNo").val(code);
			var searchurl=getContextPath()+"/loadPOSTableMasterData.html?tableCode="+code;		
			 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strTableNo=='Invalid Code')
			        	{
			        		alert("Invalid Group Code");
			        		$("#txtTableNo").val('');
			        	}
			        	else
			        	{
				        	$("#txtPaxNo").val(response.intPaxCapacity);
				        	$("#txtTableName").val(response.strTableName);
				        	$("#txtAreaName").val(response.strAreaName);
				        	$("#txtWaiterName").val(response.strWaiterName);
				        	$("#txtTableName").focus();
				        	if(response.strOperational=='Y')
			        		{
				        		$("#chkOperational").prop('checked',true);
			        		}
				        	
				        	$("#txtPOSCode").val(response.strPOSCode);
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


	function funHelp(transactionName)
	{
		fieldName=transactionName;
		  window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
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
				  var table = document.getElementById("tblTable");
				  var tableNo=table.rows[selectedRowIndex].cells[1].innerHTML;
				  var tableName=table.rows[selectedRowIndex].cells[2].innerHTML; 
				  var areaName=table.rows[selectedRowIndex].cells[3].innerHTML;
				  var posName=table.rows[selectedRowIndex].cells[4].innerHTML;
				  
				  var tableNo1=table.rows[selectedRowIndex-1].cells[1].innerHTML;
				  var tableName1=table.rows[selectedRowIndex-1].cells[2].innerHTML;
				  var areaName1=table.rows[selectedRowIndex-1].cells[3].innerHTML; 
				  var posName1=table.rows[selectedRowIndex-1].cells[4].innerHTML; 
				  funMoveRowUp(tableNo,tableName,areaName,posName,selectedRowIndex,tableNo1,tableName1,areaName1,posName1);
				}
				
			}
			else
			{
				var table = document.getElementById("tblTable");
				var rowCount = table.rows.length;
				if(rowCount>0)
				{
					var table = document.getElementById("tblTable");
					
					
					
					 var tableNo=table.rows[selectedRowIndex].cells[1].innerHTML;
					  var tableName=table.rows[selectedRowIndex].cells[2].innerHTML; 
					  var areaName=table.rows[selectedRowIndex].cells[3].innerHTML;
					  var posName=table.rows[selectedRowIndex].cells[4].innerHTML;
					  
					  var tableNo1=table.rows[selectedRowIndex+1].cells[1].innerHTML;
					  var tableName1=table.rows[selectedRowIndex+1].cells[2].innerHTML;
					  var areaName1=table.rows[selectedRowIndex+1].cells[3].innerHTML; 
					  var posName1=table.rows[selectedRowIndex+1].cells[4].innerHTML; 
					  funMoveRowDown(tableNo,tableName,areaName,posName,selectedRowIndex,tableNo1,tableName1,areaName1,posName1);
				}
				
			}
	}


	function funGetSelectedRowIndex(obj)
	{
		 var index = obj.parentNode.parentNode.rowIndex;
		 var table = document.getElementById("tblTable");
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

	function funMoveRowUp(tableNo,tableName,areaName,posName,rowCount,tableNo1,tableName1,areaName1,posName1)
	{
		var table = document.getElementById("tblTable");
	    table.deleteRow(rowCount);
	    var row = table.insertRow(rowCount-1);
	    row = table.rows[rowCount-1];
		
		var codeArr = tableNo.split('value=');
		var code=codeArr[1].split('onclick=');
		var menuCode=code[0].substring(1, (code[0].length-2));
		var nameArr = tableName.split('value=');
		var name=nameArr[1].split('onclick=');
		var menuName=name[0].substring(1, (name[0].length-2));
		
		var areaArr = areaName.split('value=');
		var area=areaArr[1].split('onclick=');
		var AreaName=area[0].substring(1, (area[0].length-2));
		
		var posArr = posName.split('value=');
		var pos=posArr[1].split('onclick=');
		var PosName=pos[0].substring(1, (pos[0].length-2));
		
		  row.insertCell(0).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].intSequence\" readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(rowCount)+"\" value='"+(rowCount)+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row.insertCell(1).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strTableNo\" readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtMenuHeadCode."+(rowCount)+"\" value='"+menuCode+"'onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row.insertCell(2).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strTableName\" readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtMenuHeadName."+(rowCount)+"\" value='"+menuName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row.insertCell(3).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strAreaName\" readonly=\"readonly\" class=\"Box \" size=\"14%\" id=\"txtAreaName."+(rowCount)+"\" value='"+AreaName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row.insertCell(4).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strPOSCode\" readonly=\"readonly\" class=\"Box \" size=\"34%\" id=\"txtPOSCode."+(rowCount)+"\" value='"+PosName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		 
		  
		  row = table.rows[rowCount-1];
		  row.style.backgroundColor='#ffd966';
		  selectedRowIndex=rowCount-1;
		    
		 var nextcodeArr = tableNo1.split('value=');
	     var nextcode=nextcodeArr[1].split('onclick=');
		 var nextmenuCode=nextcode[0].substring(1, (nextcode[0].length-2));
		 var nextnameArr = tableName1.split('value=');
		 var nextname=nextnameArr[1].split('onclick=');
		 var nextmenuName=nextname[0].substring(1, (nextname[0].length-2));  
		 
		 var nxtAreaArr = areaName1.split('value=');
			var nxtArea=nxtAreaArr[1].split('onclick=');
			var nxtAreaName=nxtArea[0].substring(1, (nxtArea[0].length-2));
			
			var nxtPosArr = posName1.split('value=');
			var nxtPos=nxtPosArr[1].split('onclick=');
			var nxtPosName=nxtPos[0].substring(1, (nxtPos[0].length-2));
		 var row1 = table.insertRow(rowCount+1);
		 
		  row1.insertCell(0).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].intSequence\" readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+(rowCount+1)+"\" value='"+(rowCount+1)+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row1.insertCell(1).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strTableNo\" readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtMenuHeadCode."+(rowCount)+"\" value='"+nextmenuCode+"'onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row1.insertCell(2).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strTableName\" readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtMenuHeadName."+(rowCount)+"\" value='"+nextmenuName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row1.insertCell(3).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strAreaName\" readonly=\"readonly\" class=\"Box \" size=\"14%\" id=\"txtAreaName."+(rowCount)+"\" value='"+nxtAreaName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row1.insertCell(4).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strPOSCode\" readonly=\"readonly\" class=\"Box \" size=\"34%\" id=\"txtPOSCode."+(rowCount)+"\" value='"+nxtPosName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		 
		  
		  table.deleteRow(rowCount);
	}



	function funMoveRowDown(tableNo,tableName,areaName,posName,rowCount,tableNo1,tableName1,areaName1,posName1)
	{
		var table = document.getElementById("tblTable");
	    table.deleteRow(rowCount);
	    var row = table.insertRow(rowCount+1);
	    row = table.rows[rowCount+1];
		
		var codeArr = tableNo.split('value=');
		var code=codeArr[1].split('onclick=');
		var menuCode=code[0].substring(1, (code[0].length-2));
		var nameArr = tableName.split('value=');
		var name=nameArr[1].split('onclick=');
		var menuName=name[0].substring(1, (name[0].length-2));
		
		var areaArr = areaName.split('value=');
		var area=areaArr[1].split('onclick=');
		var AreaName=area[0].substring(1, (area[0].length-2));
		
		var posArr = posName.split('value=');
		var pos=posArr[1].split('onclick=');
		var PosName=pos[0].substring(1, (pos[0].length-2));
		  row.insertCell(0).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].intSequence\" readonly=\"readonly\" class=\"Box \" size=\"14%\" id=\""+(rowCount+2)+"\" value='"+(rowCount+2)+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row.insertCell(1).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strTableNo\" readonly=\"readonly\" class=\"Box \" size=\"14%\" id=\"txtMenuHeadCode."+(rowCount)+"\" value='"+menuCode+"'onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row.insertCell(2).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strTableName\" readonly=\"readonly\" class=\"Box \" size=\"14%\" id=\"txtMenuHeadName."+(rowCount)+"\" value='"+menuName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row.insertCell(3).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strAreaName\" readonly=\"readonly\" class=\"Box \" size=\"14%\" id=\"txtAreaName."+(rowCount)+"\" value='"+AreaName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row.insertCell(4).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strPOSCode\" readonly=\"readonly\" class=\"Box \" size=\"34%\" id=\"txtPOSCode."+(rowCount)+"\" value='"+PosName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		 
		  
		  
		  row = table.rows[rowCount+1];
		  row.style.backgroundColor='#ffd966';
		  selectedRowIndex=rowCount+1;
		  
		var nextcodeArr = tableNo1.split('value=');
	    var nextcode=nextcodeArr[1].split('onclick=');
		var nextmenuCode=nextcode[0].substring(1, (nextcode[0].length-2));
		var nextnameArr = tableName1.split('value=');
		var nextname=nextnameArr[1].split('onclick=');
		var nextmenuName=nextname[0].substring(1, (nextname[0].length-2));  
		
		
		var nxtAreaArr = areaName1.split('value=');
		var nxtArea=nxtAreaArr[1].split('onclick=');
		var nxtAreaName=nxtArea[0].substring(1, (nxtArea[0].length-2));
		
		var nxtPosArr = posName1.split('value=');
		var nxtPos=nxtPosArr[1].split('onclick=');
		var nxtPosName=nxtPos[0].substring(1, (nxtPos[0].length-2));
		var row1 = table.insertRow(rowCount);
		 
		  row1.insertCell(0).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].intSequence\"readonly=\"readonly\" class=\"Box \" size=\"14%\" id=\""+(rowCount+1)+"\" value='"+(rowCount+1)+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row1.insertCell(1).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strTableNo\" readonly=\"readonly\" class=\"Box \" size=\"14%\" id=\"txtMenuHeadCode."+(rowCount)+"\" value='"+nextmenuCode+"'onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row1.insertCell(2).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strTableName\" readonly=\"readonly\" class=\"Box \" size=\"14%\" id=\"txtMenuHeadName."+(rowCount)+"\" value='"+nextmenuName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row1.insertCell(3).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strAreaName\" readonly=\"readonly\" class=\"Box \" size=\"14%\" id=\"txtAreaName."+(rowCount)+"\" value='"+nxtAreaName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row1.insertCell(4).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strPOSCode\" readonly=\"readonly\" class=\"Box \" size=\"34%\" id=\"txtPOSCode."+(rowCount)+"\" value='"+nxtPosName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		 
		  
		  table.deleteRow(rowCount+1);
		  
		  
	}
	function funfillTableDetail(sequence,strTableNo,strTableName,strAreaName,strPOSCode)
	{
		var table = document.getElementById("tblTable");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

		  row.insertCell(0).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].intSequence\" readonly=\"readonly\" class=\"Box \" size=\"14%\" id=\"txtsequence."+(rowCount)+"\" value='"+sequence+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(1).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strTableNo\" readonly=\"readonly\" class=\"Box \" size=\"14%\" id=\"txtTableNo."+(rowCount)+"\" value='"+strTableNo+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row.insertCell(2).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strTableName\" readonly=\"readonly\" class=\"Box \" size=\"14%\" id=\"txtTableName."+(rowCount)+"\" value='"+strTableName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row.insertCell(3).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strAreaName\" readonly=\"readonly\" class=\"Box \" size=\"14%\" id=\"txtAreaName."+(rowCount)+"\" value='"+strAreaName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		  row.insertCell(4).innerHTML= "<input name=\"listTableDtl["+(rowCount)+"].strPOSCode\" readonly=\"readonly\" class=\"Box \" size=\"34%\" id=\"txtPOSCode."+(rowCount)+"\" value='"+strPOSCode+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		 
		 

	}
	function funLoadTableData()
	{

		var searchurl=getContextPath()+"/LoadTableData.html";
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        
			        success: function (response) {
			        	funRemoveTableRows("tblTable");
			           // for (var i in response){		            	
			            	$.each(response,function(i,item){
			            	
			            		funfillTableDetail(i+1,item.strTableNo,item.strTableName,item.strAreaName,item.strPOSCode);
			            				            				            	
				            	 
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
	 
	 function funCallFormAction() 
		{
			var flg=true;
			var name = $('#txtTableName').val();
			var code= $('#txtTableNo').val();
			
			
				 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/checkTableName.html?name="+name+"&code="+code,
				        async: false,
				        dataType: "text",
				        success: function(response)
				        {
				        	if(response=="false")
				        		{
				        			alert("Table Name Already Exist!");
				        			$('#txtTableName').focus();
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
			
			return flg;
		}

</script>

</head>
<body onload="funLoadTableData()">

	<div id="formHeading">
	<label>Table Master</label>
	</div>

<br/>
<br/>

	<s:form name="POSTableMaster" method="POST" action="savePOSTableMaster.html?saddr=${urlHits}">
	<table
				style="border: 0px solid black; width: 70%; margin-left: auto; margin-right: auto;background-color:#C0E4FF;">
				<tr>
					<td>
						<div id="tab_container">
							<ul class="tabs">
								<li class="active" data-state="tab1">Table Master</li>
								<li data-state="tab2">Table Sequence</li>
								
				
							</ul>
							<br /> <br />

							<!--  Start of Generals tab-->

							<div id="tab1" class="tab_content">
								<table  class="masterTable">
																		
								<tr>
				<td>
					<label>TableNo</label>
				&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<s:input colspan="" type="text" id="txtTableNo" path="strTableNo" cssClass="searchTextBox jQKeyboard form-control" ondblclick="funHelp('POSTableMaster')" />
				
			
				</td>
				<td>
					<label>TableName</label>
				
				&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<s:input colspan="" type="text" id="txtTableName" path="strTableName" cssClass="longTextBox jQKeyboard form-control" />
				</td>
			</tr>
			<tr>
				<td>
					<label>PAX Capacity</label>
				&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
					<s:input colspan="" type="text" id="txtPaxNo" path="intPaxCapacity" cssStyle="width: 60px;" cssClass="longTextBox jQKeyboard form-control" />
				</td>
				<td>
					<label>Operational</label>
				&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					
					<s:input type="checkbox" id="chkOperational" path="strOperational"  />
					
				
				</td>
			</tr>
			<tr>
				<td>
					<label>POS Name</label>
				&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<s:select colspan="4" id="txtPOSCode" path="strPOSCode"  style="width:60%" items="${posList}" cssClass="BoxW124px"/>
					
				</td>
				<td></td>
			</tr>	
			<tr>
				<td>
					<label>Area Name</label>
				&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<s:select colspan="4" id="txtAreaName" path="strAreaName" style="width:60%" items="${areaList}" cssClass="BoxW124px">
					
					</s:select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>Waiter Name</label>
				&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<s:select colspan="4" id="txtWaiterName" path="strWaiterName" style="width:60%" items="${waiterList}" cssClass="BoxW124px">
					
					</s:select>
				</td>
				<td></td>
			</tr>
			
			
						</table>
						<br /><br /><br /><br /><br />
		<br />
		<p align="center">
			<input type="submit" id="tblMaster" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
							</div>
							<!--  End of  Generals tab-->


							<!-- Start of Settlement tab -->

							<div id="tab2" class="tab_content">
							<table style="width: 80%; margin: auto;"  class="transTablex col5-center">
								<tr>
									<td style="width:14%">Sequence No.</td>
									<td style="width:14%">Table Code</td>
									<td style="width:14%">Table Name</td>
									<td style="width:14%">Area</td>
									<td style="width:40%">POS</td>
									
								</tr>							
							</table>
							
							<div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 150px;
			    				margin: auto;overflow-x: hidden; overflow-y: scroll;width: 80%;">
									<table id="tblTable" class="transTablex col5-center" style="width: 100%;">
									
									<tbody>    
											<col style="width:14%"><!--  COl1   -->
											<col style="width:14%"><!--  COl2   -->
											<col style="width:14%"><!--  COl3   -->	
											<col style="width:14%"><!--  COl4   -->
											<col style="width:40%"><!--  COl5   -->								
									</tbody>							
									</table>
			</div >
			<div style="margin-left:400px;" >
			<img src="../${pageContext.request.contextPath}/resources/images/imgMoveUp.png" onclick="funMoveSelectedRow(1)">
			 <img  src="../${pageContext.request.contextPath}/resources/images/imgMoveDown.png" onclick="funMoveSelectedRow(0)">
			</div>
			<br />
		
		<p align="center">
			<input type="submit" id="tblSequence" value="Submit" tabindex="3" class="form_button" />
			
		</p>
			
			</div>
			
					</td>
				</tr>
			</table>
	


	</s:form>
</body>
</html>
