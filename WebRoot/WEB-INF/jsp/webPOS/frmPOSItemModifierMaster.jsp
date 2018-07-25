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
	 $(document).ready(function () {
		  $('input#txtRate').mlKeyboard({layout: 'en_US'});
		  $('input#txtModifierCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtModifierName').mlKeyboard({layout: 'en_US'});
		  $('textarea#txtModifierDescription').mlKeyboard({layout: 'en_US'});
		  
		  $('input#rdbDeselectAll').prop('checked', false);
		  $('input#rdbSelectAll').prop('checked', false);
		
		  $("form").submit(function(event){
			  if($("#txtModifierName").val().trim()=="")
				{
					alert("Please Enter Modifier Name");
					return false;
				}
			  else{
				  flg=funCallFormAction();
				  return flg;
			  }
			});
		 		
		}); 

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
		
	 
	 function funGetItemCode(value) {
			window.opener.funSetData(value);
			window.close();
		}
	
	function funSetData(code){
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadModifierCode.html?ModCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strModifierCode=='Invalid Code')
	        	{
	        		alert("Invalid Group Code");
	        		$("#txtModifierCode").val('');
	        	}
	        	else
	        	{
	               	$("#txtModifierCode").val(response.strModifierCode);
		        	$("#txtModifierName").val(response.strModifierName);
		        	$("#txtModifierName").focus();
		        	$("#txtModifierGroup").val(response.strModifierGroup);
		        	$("#txtModifierDescription").val(response.strModifierDescription);
		        	$("#txtRate").val(response.dblRate);
		        	
		        	if(response.strChargable=='y')
		        	{
		        		$("#chkChargable").attr('checked', true);
		        	}
		        	else  
		        	{
		        		$("#chkChargable").attr('unchecked', false);
		        	}
		        	
		        	if(response.strApplicable=='y')
		        	{
		        		$("#chkApplicable").attr('checked', true);
		        	}
		        	else
		        	{
		        		$("#chkApplicable").attr('unchecked', false);
		        	}
		        	 
		        		        						        	
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
	function funLoadMenuHeadData()
	{

		var searchurl=getContextPath()+"/LoadMenuDetails.html";
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        
			        success: function (response) {
			        	funRemoveProductRows("tblMenuDet");
			            	$.each(response,function(i,item){
			            		funfillMenuDetail(response[i].strMenuCode,response[i].strMenuName);
			            	   
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

	function funfillMenuDetail(strMenuCode,strMenuName)
	{
		var table = document.getElementById("tblMenuDet");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"strMenuCode."+(rowCount)+"\" value='"+strMenuCode+"' onclick=\"funGetSelectedRowIndex('"+strMenuCode+"')\"/>";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" id=\"strMenuName."+(rowCount)+"\" value='"+strMenuName+"' onclick=\"funGetSelectedRowIndex('"+strMenuCode+"')\"/>";

	}
	
	function funfillItemDetail(strItemName,strItemCode,StrMenuCode)
	{
		var table = document.getElementById("tblItemDet");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		var rate=document.getElementById('txtRate').value;
		
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listObjItemBean["+(rowCount)+"].strItemName\" id=\"strItemName."+(rowCount)+"\" value='"+strItemName+"'>";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"15%\" name=\"listObjItemBean["+(rowCount)+"].strItemCode\" id=\"strItemCode."+(rowCount)+"\" value='"+strItemCode+"'>";
	/*     row.insertCell(2).innerHTML= "<input id=\"strSelect."+(rowCount)+"\" type=\"checkbox\" class=\"GCheckBoxClass\" name\"listObjItemBean["+(rowCount)+"].strSelect\" value='"+rate+"' >"; */
	    row.insertCell(2).innerHTML= "<input id=\"cbItemSel."+(rowCount)+"\" type=\"checkbox\" class=\"GCheckBoxClass\" name=\"listObjItemBean["+(rowCount)+"].strSelect\" size=\"15%\" value=\"Tick\" />";
	    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align:right;\" size=\"16%\"name=\"listObjItemBean["+(rowCount)+"].dblPurchaseRate\" id=\"dblPurchaseRate."+(rowCount)+"\" value='"+rate+"'>";
	    row.insertCell(4).innerHTML= "<input id=\"cbDefMod."+(rowCount)+"\" type=\"checkbox\" class=\"GCheckBoxClass\"  name=\"DefMod\" size=\"20%\"  value='Tick' >";
	     
	}

	//Remove Table data when pass a table ID as parameter
	function funRemoveProductRows(tableName)
			{
				var table = document.getElementById(tableName);
				var rowCount = table.rows.length;
				while(rowCount>0)
				{
					table.deleteRow(0);
					rowCount--;
				}
			}


	function funGetSelectedRowIndex(menuCode)
	{
		
		var searchurl=getContextPath()+"/loadMenuWiseItemDetail.html?MenuCode="+menuCode;
		 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function (response) {
		        	funRemoveProductRows("tblItemDet");
		            	$.each(response,function(i,item){
		            		funfillItemDetail(response[i].strItemName,response[i].strItemCode,menuCode);
		            	   
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
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	function funSelectAllChkBox()
	{
		 $('input#rdbDeselectAll').prop('checked', false);
		
		 /*  $('ItemSel').prop('checked', true);
		 $('input#cbItem.'+i).prop('checked', true);  */
		 
		 var table = document.getElementById("tblItemDet");
		 var rowCount = table.rows.length;
		 for(i=0; i<rowCount; i++)
			 $("#tblItemDet tr:eq("+i+") td:eq(2) input:checkbox").prop("checked", true);	 
	}
	function funDeSelectAllChkBox()
	{
		 $('input#rdbSelectAll').prop('checked', false);
		 var table = document.getElementById("tblItemDet");
		 var rowCount = table.rows.length;
		 for(i=0; i<rowCount; i++)
			 $("#tblItemDet tr:eq("+i+") td:eq(2) input:checkbox").prop("checked", false);
	}
	
	function btnApply_onclick()
	{
		var rate=document.getElementById('txtRate').value;
		
		 var table = document.getElementById("tblItemDet");
		 var rowCount = table.rows.length;
		 for(i=0; i<rowCount; i++)
			 $("#tblItemDet tr:eq("+i+") td:eq(3)").text(rate);
		 /* append(rate) */
	}
	function funResetFields()
	{
		$("#txtModifierName").focus();
		$("#txtModifierCode").val('');
		$("#txtModifierName").val('');
		$("#strModifierGroup").val('');
		$("#txtModifierDescription").val('');
		$("#txtRate").val('');
	    funRemoveProductRows("tblItemDet");
	}

	
	
	function funCallFormAction(actionName,object) 
	{
		var flg=true;
		
		
			var name = $('#txtModifierName').val();
			var code=$('#txtModifierCode').val();
			 $.ajax({
			        type: "GET",
			        url: getContextPath()+"/checkModName.html?modName="+name+"&modCode="+code,
			        async: false,
			        dataType: "text",
			        success: function(response)
			        {
			        	if(response=="false")
			        		{
			        			alert("Modifier Name Already Exist!");
			        			$('#txtModifierName').focus();
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
<body onload="funLoadMenuHeadData()">

	<div id="formHeading">
	<label>Item Modifier Master</label>
	</div>

<br/>
<br/>
	<s:form name="ItemModifierMaster" method="POST" action="saveItemModifierMaster.html?saddr=${urlHits}"> 
		<table class="masterTable">
			<tr> 
				<td>  
					<label>Modifier Code</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtModifierCode" path="strModifierCode" cssClass="searchTextBox" ondblclick="funHelp('POSItemModifierMaster');"/>
				</td>
				<td>
					<label>Modifier Name</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtModifierName" path="strModifierName" cssClass="longTextBox" />
				</td>
				
			</tr>
			<tr>
				<td>
					<label>Modifier Group</label>
				</td>
				<td>
					<s:select id="txtModifierGroup" path="strModifierGroup" items="${ModifierGroup}"  cssClass="BoxW124px"/>
				</td>
				<td>
					<label>Modifier Description</label>
				</td>
				<td>
					<s:textarea id="txtModifierDescription" path="strModifierDescription" class="txtTextArea" />
				</td>
			</tr> 
			
			<tr>
				<td>
					<label>Rate</label>
				</td>
				<td>
				<%-- 	<s:input colspan="3" id="txtRate" path="dblRate" type="number" min="0" class="longTextBox" style="width: 38%;text-align: right;"/> --%>
					<s:input colspan="3" id="txtRate" path="dblRate" type="text" min="0" step="1" class="longTextBox" style="width: 38%; text-align: right;"/>
					
				</td>
				<td></td>
				<td></td>
				
			</tr>
			<tr>
		   <td></td>
			<td ></td>
		<td></td>
		<td></td>
		</tr>
			<tr>
			<td>
			<input type="Button" value="Apply" onclick="btnApply_onclick()" class="smallButton" /></td>
			
				<td>
					<label>Applicable</label>
					<s:input type="checkbox"  id="chkApplicable" path="strApplicable"  style="width: 8%"></s:input>
				</td>
				<td>
					<label>chargeable</label>
					<s:input type="checkbox"  id="chkChargable" path="strChargable" style="width: 8%"></s:input>
				</td>
				<td></td>
			</tr>
	
		<tr>
		<td></td><td></td>
		<td></td>
		<td></td>
		</tr>
		<!-- </table>
	<br>
		<table class="masterTable"> -->
		 <!-- style="width: 70%; border-collapse: collapse; overflow: auto;
				margin-left: auto; margin-right: auto; font-size:11px;font-weight: bold;"> -->
		<tr>
		<td></td>
		<td></td>
		
		</tr>
		<tr>
		<!-- <td width=120px></td> -->
		<td>
		<div id="tableLoad" class="" style="width: 220px; height: 300px;" >
			<table style="height: 20px; border: #0F0;width: 98%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="38%">MenuCode</td>	
					<td width="60%">Menu Name</td>
				</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px;width: 95%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
					<table id="tblMenuDet"
					style="width:95% ; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>				
					<col id="cl" style="width:38%">
					<col style="width:60%">
					</tbody>
				</table>
			</div>
		</div>
		</td>
		<!-- <td width=10px></td> -->
		<td colspan="3">
		<div id="tableLoad" class="" style="width:99%; height: 300px; ">
			<table style="height: 20px; border: #0F0;width: 98%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="37%">Item Name </td>					
					<td width="12%">Item Code</td>	
					<td width="12%">Select </td>
					<td width="12%">Rate </td>	
					<td width="17%">Default Modifier</td>
					<!-- <td width="0px">MenuCode</td> -->
				</tr>
			</table>
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px;width: 655px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
					<table id="tblItemDet"
					style="width: 98%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:37%">					
					<col style="width:12%">
					<col style="width:12%">
					<col style="width:12%">
					<col style="width:17%"> 
					<%-- <col style="width:0px"> --%>
					
					<%-- <c:forEach items="${command.listObjItemBean}" var="recipe" varStatus="status">
					<tr>
					<td><input type="text" class="Box id" size="10%" name="listObjItemBean[${status.index}].strItemName" value="${recipe.strItemName}" readonly="readonly"/></td>
					<td><input type="text" class="Box id" size="10%" name="listObjItemBean[${status.index}].strItemCode" value="${recipe.strItemCode}" readonly="readonly"/></td>					
					<td><input type="number" step="any" required="required" style="text-align: right;" class="listObjItemBean[${status.index}].dblPurchaseRate" value="${recipe.dblPurchaseRate}"/></td>
				
					<td><input class="Box" size="6%" name="listBomDtlModel[${status.index}].strUOM" value="${recipe.strUOM}" readonly="readonly"/></td>
					<td><input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)"></td>
				
				 </tr>
					</c:forEach> --%>
					</tbody>
				</table>
			</div>
		</div>
		</td>
		</tr>
	</table>
	
		<table class="masterTable">
		<tr>
		<td></td>
		<td></td>
		<td style="padding-left: 50%">
			<s:radiobutton id="rdbSelectAll" path="strSelectAll" value="Y" onclick="funSelectAllChkBox()"  />Select All
			<s:radiobutton id="rdbDeselectAll" path="strDeselectAll" value="Y" onclick="funDeSelectAllChkBox()" />Deselect All
					
		</tr>
		</table>
		<br>
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
