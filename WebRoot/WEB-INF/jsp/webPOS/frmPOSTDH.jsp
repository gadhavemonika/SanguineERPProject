<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="True" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TDH</title>
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
 	var fieldName;
 	/*  $(document).ready(function () {
	      $('input#txtTDHCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtTDHOnMenuHead').mlKeyboard({layout: 'en_US'});
		  $('input#txtFreeQuantity').mlKeyboard({layout: 'en_US'});
		  $('input#txtDescription').mlKeyboard({layout: 'en_US'});			  
		});  */
 	       

 	function funHelp(transactionName,number)
	{	  fieldName=number;  
	
       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
    }
 	
		function funHelp1(transactionName,number,code)
		{	
			fieldName=number;
	       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	       window.open("searchform.html?formname="+transactionName+"&strMenuCode="+code+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
	 
	    function funAddCourse()
	    {
	    	var code =$("#txtMenuHead").val();	
	    	if(code=='')
	    		{
	    		alert("Select Menu Head");
	    		}
	    	else
	    		{	    	 	
		    	funHelp1('POSTDHOnItem','four',code);
	    		}
	    	
	    	 
	    } 
	
	/**
	*set the item comboBox
	**/
	function funSetMenuHeadData(code)
	{
		//code="M000004";
		$("#txtTDHOnMenuHead").val(code);
		 var searchurl=getContextPath()+"/loadPOSTDHOnItemData.html?strMenuCode="+code;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.List==0)
			        	{
			        		alert("Data Not present");
			        		
			        	}
			        	else
			        	{
			        		 document.getElementById("txtTDHOnItem").disabled = false;  
			        		 $('#txtTDHOnItem').empty();
			        		 $('#txtTDHOnItem').append( $('<option></option>').val("--Select--").html("--Select--"));
			        		$.each(response.List, function(index, item) {
				         		 $('#txtTDHOnItem').append( $('<option></option>').val(item).html(item));
				         	   
				         	});
			        		
			        	} 
					},
			
		      });
	}
	
	function funSetMenuItemData(code)
	{
		$("#txtMenuHead").val(code);
	}
	
	function funSetTableData(code)
	{
	
		 var searchurl=getContextPath()+"/loadPOSLoadTableData.html?strItemCode="+code;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.List==0)
			        	{
			        		alert("Invalid Menu Code");
			        		
			        	}
			        	else
			        	{			
			 	            	funFillTableCol(response.List,code);
			             				        		
			        	} 
					},
			
		      });
	}
	

 	function funFillTableCol(ItemName,code)
	{	
 		
 		 
		var table = document.getElementById("tblData");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

 		
 	    var Qty = $("#txtMaxItemQuantity").val();
 	    var MenuCode = $("#txtMenuHead").val();
 	   
	      
	      row.insertCell(0).innerHTML= "<input   name=\"listTDHDtl["+(rowCount)+"].strItemCode\" readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+code+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; 
	      row.insertCell(1).innerHTML= "<input  name=\"listTDHDtl["+(rowCount)+"].strItemName\" readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+ItemName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(2).innerHTML= "<input   name=\"listTDHDtl["+(rowCount)+"].strDefaultYN\" type=\"checkbox\" readonly=\"readonly\" class=\"Box \" />";
	      row.insertCell(3).innerHTML= "<input   name=\"listTDHDtl["+(rowCount)+"].intSubItemQty\" readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+Qty+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(4).innerHTML= "<input   name=\"listTDHDtl["+(rowCount)+"].strSubItemMenuCode\" readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+MenuCode+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(5).innerHTML= '<input  type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
	}

	function funDeleteRow(obj)
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblData");
	    table.deleteRow(index);
	}
	function funLoadTDHData(code)
	{
		if(funDeleteTableAllRows())
			{
		$("#txtTDHCode").val(code);
		 var searchurl=getContextPath()+"/loadPOSTDHData.html?strTDHCode="+code;
		
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response==0)
			        	{
			        		alert("Invalid TDH Code");
			        		
			        	}
			        	else
			        	{   
			        		
			        		  $("#txtTDHOnItem").show();
				        	$("#txtDescription").val(response.strDescription);
				        	$("#txtTDHOnMenuHead").val(response.strTDHOnMenuHead);
				       
				        	 $('#txtTDHOnItem').append( $('<option></option>').val(response.strTDHOnItem).html(response.strTDHOnItem));
				        	  
				        	 document.getElementById("txtTDHOnItem").disabled = true;   
				        	$("#txtFreeQuantity").val(response.strMaxItemQuantity);
				        	
				
				        	if(response.strchkApplicable=='Y')
				        	{
				        		$("#chkApplicable").attr('checked', true);
				        	}
				        	
				        	else
				        	{
				        		$("#chkApplicable").attr('unchecked', false);
				        	}
				        	 document.getElementById("txtTDHOnItem").disabled = false; 
				        	$.each(response.List,function(i,item){
			 	            	funFill(item[0],item[1],item[2],item[3],item[4]);
			             		});
				         	   				         
			        	} 
					},
			
		      });
	}
	}
 	

 	function funFill(item0,item1,item2,item3,item4 )
	{	
 		
 		 
		var table = document.getElementById("tblData");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

	      
	      row.insertCell(0).innerHTML= "<input  name=\"listTDHDtl["+(rowCount)+"].strItemCode\"  readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item0+"' />"; 
	      row.insertCell(1).innerHTML= "<input name=\"listTDHDtl["+(rowCount)+"].strItemName\" readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item1+"' />";
	      row.insertCell(2).innerHTML= "<input   name=\"listTDHDtl["+(rowCount)+"].strDefaultYN\"  type=\"checkbox\"   class=\" ample \" readonly=\"readonly\"  id=\"chkApplicableDy."+(rowCount)+"\" class=\"Box \" value='"+item2+"' />";
	      if(item2=='Y')
      	{
      		$(".ample").attr('checked', true);
      	}
      	
      	else
      	{
      		$(".ample").attr('unchecked', false);
      	}    
	            
	      row.insertCell(3).innerHTML= "<input  name=\"listTDHDtl["+(rowCount)+"].intSubItemQty\" readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item3+"'/>";
	      row.insertCell(4).innerHTML= "<input  name=\"listTDHDtl["+(rowCount)+"].strSubItemMenuCode\"   readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item4+"' />";
	      row.insertCell(5).innerHTML= '<input  type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
	}
	
	
  	 function funhide()
  	 {
  		 var data=$("#txtTDHOn").val();
  		 if(data=="Select")
  			 {
  			 alert("please Select TDH On");
  			 }
  		 else
  			 {
  			funHelp('POSMenuHeadMaster','first');
  	  	   $("#txtTDHOnItem").show();
  			 }
  		
  	 }
  	 
 	
 	 function funSetData(code)
	{
		
		switch (fieldName)
		{
			case 'first':
				funSetMenuHeadData(code);						
			break;	
			
			case 'two':
				funSetMenuItemData(code);					
			break;
			
			case 'three':
				funLoadTDHData(code);
			break;
			
			case 'four':
				funSetTableData(code);
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
 		  
 		 $("#txtTDHOnItem").hide(); 
 		 $("#tablediv").hide();
 		$("#tblDebitCardFlashHeader").hide(); 
		 $("#AddCourse").hide();
 		 $("#txtMenuHead").hide(); 
 		 $("#txtMaxItemQuantity").hide(); 
 		 $("#tblDebitCardFlashReport").hide(); 
 		 $("#tablediv").hide(); 
 		 
 		$("#lblMaxItemQuantity").hide(); 
		 $("#lblMenuHead").hide(); 
		 
		 
		  $("form").submit(function(event){
			  data=$("#txtTDHOn").val();
			  if(data=="--select--")
				  {
				  alert("please Select TDH On");
				  }
			  else
				  {
			  if(data=="Item")
				  {
				 	var table = document.getElementById("tblData");
				 	var rowCount1 = table.rows.length;
				  if(rowCount1<1)
					{
						alert("Please Add Course");
						return false;
					}
				  else{
					  flg=funCallFormAction();
					  return flg;
				  }
				  }
		  }
			});
 	});
 	
 	function changeFunc()
 	{
 		var data=$("#txtTDHOn").val();
 		if(data=="Item")
 			{
 			 $("#tablediv").show();
 			 $("#tblDebitCardFlashHeader").show(); 
 			 $("#AddCourse").show(); 
 			 $("#txtMenuHead").show(); 
 			 $("#txtMaxItemQuantity").show(); 
 			 $("#tblDebitCardFlashReport").show(); 
 	 		$("#lblMaxItemQuantity").show(); 
 			 $("#lblMenuHead").show();
 			 $("#tblData").show();
 			 $("#tblModifierHeader").hide(); 
 			 $("#tblModifierData").hide(); 
 			}
 		else
 			{
 			 $("#tablediv").show();
 			 $("#tblModifierHeader").show(); 
 			 $("#tblDebitCardFlashReport").show(); 
 			 $("#tblModifierData").show()
 			  $("#tblData").hide();
 			
 			 $("#tblDebitCardFlashHeader").hide(); 
 			$("#AddCourse").hide(); 
			 $("#txtMenuHead").hide(); 
			 $("#txtMaxItemQuantity").hide(); 
			 $("#lblMaxItemQuantity").hide(); 
 			 $("#lblMenuHead").hide();
 			}
 			 $("#txtTDHCode").val("");
 		 $("#txtDescription").val("");
 		 $("#txtTDHOnMenuHead").val("");
 		 $("#txtFreeQuantity").val("0");
 		$("#chkApplicable").attr('unchecked', false);
 		 $("#txtMenuHead").val("");
 		 $("#txtMaxItemQuantity").val("1");
 		 $("#tblData").empty();
 		 $("#tblModifierData").empty();
 		
 		 $('#txtTDHOnItem').empty();
 		 
 		   // strTDHOnItem  
 		       	 
 		 
 	}
 	function funCallFormAction() 
	{
		var flg=true;
		
		var strTDHCode=$('#txtTDHCode').val();
		
			var strItemCode = $('#txtTDHOnItem').val();
			 $.ajax({
			        type: "GET",
			        url: getContextPath()+"/checkTDHItem.html",
			        data:{ strItemCode:strItemCode,
			        	strTDHCode:strTDHCode,
		 			},
			        async: false,
			        dataType: "text",
			        success: function(response)
			        {
			        	if(response=="false")
			        		{
			        			alert("TDH Is Already Applied!");
			        			
			        			flg= false;
				    		}
				    	else
				    		{
				    		alert("its nt in data base");
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
 	function funCheck()
 	{
 		  if($("#txtTDHOnMenuHead").val().trim()=="")
			{			 
 			alert("Please  Select THD on Menu");
 			
			}
 		  else
 			  {
 			 funHelp('POSMenuHeadMaster','two');
 			  }
 	}
	
	function funVal()
	{
		 data=$("#txtTDHOn").val();
		 if(data=="--select--")
			{			 
			alert("Please  Select THD on");
			
			}
		  else
			  {
		funHelp('POSLoadTDHData','three');
			  }
	}
 	
	function funDeleteTableAllRows()
	 {
	 	$('#tblData tbody').empty();
	 	
	 	var table = document.getElementById("tblData");
	 	var rowCount1 = table.rows.length;
	 	if(rowCount1==0){
	 		return true;
	 	}else{
	 		return false;
	 	}
	 }
	function funResetFields()
	{
		$('#tblData tbody').empty();
		$('#tblModifierData tbody').empty();
		 document.getElementById("txtTDHOnItem").disabled = true;  
		 $('#txtTDHOnItem').empty();
		 $('#txtTDHOnItem').hide();
		 
	}
	
	
	function funGetTableData()
	{
		var strIteamName= $('#txtTDHOnItem').val();
		if($('#txtTDHOn').val()=="Modifier")
		{
			 var searchurl=getContextPath()+"/loadTableDataTDHOnItem.html?strIteamName="+strIteamName;
				
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response==0)
				        	{
				        		alert("Invalid TDH Code");
				        		
				        	}
				        	else
				        	{   				        						        		  
					        	$.each(response.List,function(i,item){
				 	            	funFill(item[0],item[1],item[2],item[3],item[4]);
				             		});
					         	   				         
				        	} 
						},
				
			      });
		}
	}

</script>
</head>
<body>

	<div id="formHeading">
	<label>TDH</label>
	</div>

<br/>
<br/>

	<s:form name="TDH" method="POST" action="savePOSTDH.html?saddr=${urlHits}">

		<table class="masterTable">
		
		<tr>
				<td>
					<label>TDH ON</label>
				</td>
				<td  colspan="3">
				 <s:select id="txtTDHOn" onchange="changeFunc()" path="strTDHOn" class="BoxW124px" required="true">
						   <option selected="selected" value="--select--">--select--</option>
						   	   <option value="Item">Item</option>
						   <option value="Modifier">Modifier</option>
						  
					</s:select>
					
				</td>
				</tr>
			<tr>
				<td>
					<label>T.D.H Code</label>
				</td>
				<td>
					<s:input  type="text" id="txtTDHCode"  path="strTDHCode" cssClass="searchTextBox" ondblclick="funVal()"/>
				</td>
		
				<td>
					<label>Description</label>
				</td>
				<td>
					<s:input  type="text" id="txtDescription"  path="strDescription" cssClass="BoxW116px" />
				</td>
			</tr>
				<tr>
				<td>
					<label>TDH On Menu Head</label>
				</td>
				<td>
					<s:input  type="text" id="txtTDHOnMenuHead"  path="strTDHOnMenuHead" cssClass="searchTextBox" ondblclick="funhide()"/>
				</td>
			
				<td>
					<label>TDH on Item</label>
				</td>
				<td>
					<s:select id="txtTDHOnItem" path="strTDHOnItem" cssClass="BoxW124px" onchange="funGetTableData()"/>
				</td>
			
			</tr>
			<tr>
				<td>
					<label>Free Quantity</label>
				</td>
				<td colspan="1">
					<s:input  type="number" value="0"  id="txtFreeQuantity"  path="strFreeQuantity" cssClass="BoxW116px" />
				</td>
			<td ><s:input type="checkbox"  id="chkApplicable" path="strchkApplicable" ></s:input>&nbsp;&nbsp;Applicable </td>
				<td></td>	</tr>
				
				</tr>
			<tr>
			<td> 
					<label id="lblMenuHead">Menu Head</label>
				</td>
				<td>
					<s:input  type="text" id="txtMenuHead"  path="strMenuHead" cssClass="searchTextBox" ondblclick="funCheck()"/>
				</td>
		
				<td>
					<label id="lblMaxItemQuantity">Max Item Quantity</label>
				</td>
				<td colspan="1">
					<s:input  type="number" value="1" id="txtMaxItemQuantity"  path="strMaxItemQuantity" cssClass="BoxW116px" />
				</td>
				</tr>
		
		</table>
		<div id="tablediv" 
				style="background-color: #a4d7ff; border: 1px solid #ccc; overflow-y: scroll; display: block; height: 300px; margin-left: 135px; width: 80%;">
				
				
				<table id="tblDebitCardFlashHeader" class="transTablex"
					style="width: 100%; text-align: center !important; ">
			
			
									<th style="border: 1px white solid;width:10%"><label>Item Code</label></th>
									<th style="border: 1px  white solid;width:10%"><label>Menu Item</label></th>								
									<th style="border: 1px  white solid;width:10%"><label>Default</label></th>
									<th style="border: 1px  white solid;width:10%"><label>Max QTY</label></th>
									<th style="border: 1px  white solid;width:10%"><label>Menu Code</label></th>
									<th style="border: 1px  white solid;width:5%"><label>Delete</label></th>
									
								</th>
				</table>
				
				<table id="tblModifierHeader" class="transTablex"
					style="width: 100%; text-align: center !important; ">
			
			
									<th style="border: 1px white solid;width:10%"><label>Modifier Code</label></th>
									<th style="border: 1px  white solid;width:10%"><label>Modifier Name</label></th>								
									<th style="border: 1px  white solid;width:10%"><label>Quantity</label></th>
									
									
								</th>
				</table>
				
				
				
				
				<table id="tblData" class="transTablex"
					style="width: 100%; text-align: center !important;">
					
											<col style="width:10%"><!--  COl2   -->
											<col style="width:10%"><!--  COl3   -->
											<col style="width:10%"><!--  COl4   -->
											<col style="width:10%"><!--  COl5   -->
											<col style="width:10%"><!--  COl6   -->
											<col style="width:5%"><!--  COl7   -->
											
				</table>
				
				<table id="tblModifierData" class="transTablex"
					style="width: 100%; text-align: center !important;">
					
											<col style="width:10%"><!--  COl2   -->
											<col style="width:10%"><!--  COl3   -->
											<col style="width:10%"><!--  COl4   -->
				</table>
				
				
				</div>	

		<br />
		<br />
		<p align="center">
			<input type="button" value="Add Course"  class="form_button" id="AddCourse" onclick="funAddCourse()"/>
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
