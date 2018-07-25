<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="sp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="default.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Excise Recipe Master</title>
	<script type="text/javascript">
	var fieldName;
	
		 $(document).ready(function (){
			 $( "#txtFromDate" ).datepicker({ dateFormat: 'yy-mm-dd' });
			 $("#txtFromDate" ).datepicker('setDate', 'today');
			 $( "#txtToDate" ).datepicker({ dateFormat: 'yy-mm-dd' });
			 $("#txtToDate" ).datepicker('setDate', 'today');
			 $(document).ajaxStart(function(){
				    $("#wait").css("display","block");
				  });
				  $(document).ajaxComplete(function(){
				    $("#wait").css("display","none");
				  });
				  
				  
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

		});
		 
	
		function funResetFields()
		{
			location.reload(true); 	
	    }
		
		function funHelp(transactionName)
		{
			fieldName = transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
	    }
		
		
		
		function funSetData(code)
		{	
			switch (fieldName) 
			{			   
			   case 'POSItemWeb-Service':
			    	funSetParentData(code);
			        break;
			   
			   case 'childCode':
			    	funSetChildData(code);
			        break;
			        
			   case 'RecipeCode':
				   funSetRecipeData(code);
				   break;
			}
		}
		
		function funSetParentData(code)
		{
			$("#txtChildCode").focus();
			searchUrl=getContextPath()+"/loadPOSItemMasterData.html?itemCode="+code;
			
			$.ajax({
		        type: "GET",
		        url: searchUrl,
		        dataType: "json",
		        success: function(response)
		        {
// 		        	if(response.strBrandCode =="Invalid Code")
// 		        		{
// 		        			alert("Invalid Brand Code");
// 				        	$("#txtParentCode").val('');
// 			        		$("#txtParentCode").focus();
// 		        		}
// 		        	else
// 		        		{
		        		
		        			$("#txtParentCode").val(code);
		        			$("#txtParentName").val(response[1]);
		        			$("#txtPName").text(response[1]);		        			
// 		        			$("#txtParentSize").text(response.strSizeName);
		        			
// 		        		}
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
		
		function funSetChildData(code)
		{
			$("#txtChildQty").focus();
			searchUrl=getContextPath()+"/loadExciseBrandMasterData.html?brandCode="+code;
			
			$.ajax({
		        type: "GET",
		        url: searchUrl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strBrandCode =="Invalid Code")
		        		{
		        			alert("Invalid Brand Code");
				        	$("#txtChildCode").val('');
			        		$("#txtChildCode").focus();
		        		}
		        	else
		        		{
		        			$("#txtChildCode").val(response.strBrandCode);
		        			$("#txtChildName").text(response.strBrandName);
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
		
		
				
		function btnAdd_onclick() 
		{			
			
			if($("#txtChildCode").val().trim().length==0)
		    {
				  	alert("Please Enter Child Product Code Or Search");
			     	$('#txtProdCode').focus();
			     	return false; 
			 	}else if(($("#txtChildQty").val().trim() == 0) || ($("#txtChildQty").val().trim().length==0  ))
			 	{
					  alert("Please Enter Quantity ");
				       $('#txtChildQty').focus();
				       return false; 
			 	}else{
					var strBrandCode=$("#txtChildCode").val();
					if(!($("#txtChildCode").val()==$("#txtParentCode").val()))
						{
					if(funDuplicateBrand(strBrandCode)){
						funAddRow();
            	}
				}else{
					alert("Parent and Child Brand are Same");
							
						}
			}
		 
		}
		
		function funDuplicateBrand(strBrandCode)
		{
		    var table = document.getElementById("tblChild");
		    var rowCount = table.rows.length;		   
		    var flag=true;
		    if(rowCount > 0)
		    	{
				    $('#tblChild tr').each(function()
				    {
					    if(strBrandCode==$(this).find('input').val())// `this` is TR DOM element
	    				{
					    	alert("Already added "+ strBrandCode);
		    				flag=false;
	    				}
					});
				    
		    	}
		    return flag;
		  
		}
		
		function funAddRow() 
		{
			var childCode = $("#txtChildCode").val();
			var childName = $("#txtChildName").text();
		    var qty = $("#txtChildQty").val();
				    
		    var table = document.getElementById("tblChild");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input type=\"text\"  readonly=\"readonly\" class=\"Box id\" size=\"30%\" name=\"objclsExciseRecipeMasterDtlModel["+(rowCount)+"].strBrandCode\" id=\"strChildCode."+(rowCount)+"\" value="+childCode+" />";		    
		    row.insertCell(1).innerHTML= "<input class=\"Box\"  readonly=\"readonly\" size=\"100%\" id=\"strChildName."+(rowCount)+"\" value='"+childName+"' />";
		    row.insertCell(2).innerHTML= "<input type=\"text\" required = \"required\" size=\"25%\" style=\"text-align: right;\" class=\"decimal-places numberField inputText-Auto\" name=\"objclsExciseRecipeMasterDtlModel["+(rowCount)+"].dblQty\" id=\"strChildQty."+(rowCount)+"\" value="+qty+" />";
		    row.insertCell(3).innerHTML= '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
		   
		    funResetChildFields();
		    funApplyNumberValidation();
		    
		    return false;
		}
		 
		function funDeleteRow(obj) 
		{
			var index = $(obj).closest('tr').index();
			var icode = $(obj).closest("tr").find("input[type=text]").val();
		    var table = document.getElementById("tblChild");
		    table.deleteRow(index);
		}
			
		
		function funResetChildFields()
		{
			$("#txtChildCode").val('');
			$("#txtChildName").text('');
		    $("#txtChildQty").val('');
		}
		
		function funSetRecipeData(code) {	
			$("#txtChildCode").focus();
			funResetChildFields();
			funClearBrandRowData()
			var searchURL = getContextPath()+ "/loadExciseRecipeMasterData.html?recipeCode=" + code;
			$("#txtRecipeCode").val(code);
			$.ajax({
				type : "GET",
				url : searchURL,
				dataType : "json",
				success : function(response) {
					if (response.strRecipeCode == 'Invalid Code') {
						alert("Invalid Recipe Code Please Select Again");
						$("#txtRecipeCode").val('');
						$("#txtRecipeCode").focus();

					} else {
						$("#txtRecipeCode").val(response.strRecipeCode);
						$("#txtParentCode").val(response.strParentCode);
						$("#txtPName").text(response.strParentName);
	        			$("#txtParentName").val(response.strParentName);
	        			$("#txtFromDate").val(response.dtValidFrom);
	        			$("#txtToDate").val(response.dtValidTo);
	        			$("#txtParentSize").text(response.strParentSize);
						
						funSetRecipeDtlData(response.objclsExciseRecipeMasterDtlModel);						
					}
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
		
		function funSetRecipeDtlData(response){
			$.each(response, function(i,item)
      	    	 {
					funfillBrandRow(response[i]);
				       	    	   			       	    	                                           
       	    	 });			
		}
		
		
		function funfillBrandRow(brandData){
			
			 var table = document.getElementById("tblChild");
			 var rowCount = table.rows.length;
			 var row = table.insertRow(rowCount);
		
				row.insertCell(0).innerHTML= "<input type=\"text\"  readonly=\"readonly\" class=\"Box id\" size=\"30%\" name=\"objclsExciseRecipeMasterDtlModel["+(rowCount)+"].strBrandCode\" id=\"strChildCode."+(rowCount)+"\" value="+brandData.strBrandCode+" />";		    
		    	row.insertCell(1).innerHTML= "<input class=\"Box\"  readonly=\"readonly\" size=\"100%\" id=\"strChildName."+(rowCount)+"\" value='"+brandData.strBrandName+"' />";
			    row.insertCell(2).innerHTML= "<input type=\"text\" required = \"required\" size=\"25%\" style=\"text-align: right;\" class=\" decimal-places numberField inputText-Auto\" name=\"objclsExciseRecipeMasterDtlModel["+(rowCount)+"].dblQty\" id=\"strChildQty."+(rowCount)+"\" value="+brandData.dblQty+" />";
			    row.insertCell(3).innerHTML= '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
			    funApplyNumberValidation();
		}
		
		function funClearBrandRowData(){
			$("#tblChild tr").remove();
		}		
		
		function funValidateFields() {
			if (!fun_isDate($("#txtFromDate").val())) {
				alert('Invalid Date');
				$("#txtFromDate").focus();
				return false;
			}
			if (!fun_isDate($("#txtToDate").val())) {
				alert('Invalid Date');
				$("#txtToDate").focus();
				return false;
			}

			var table = document.getElementById("tblChild");
			var rowCount = table.rows.length;
			if (rowCount == 0) {
				alert('Please Add Child Products');
				return false;
			}else {
				return true;
			}

		}	
		
		function funApplyNumberValidation(){
				$(".numeric").numeric();
				$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
				$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
				$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
			    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit,negative: false});
		}
		
		$(function(){
				$('#txtRecipeCode').blur(function () 
						{
						 var code=$('#txtRecipeCode').val();
						 if (code.trim().length > 0 && code !="?" && code !="/"){							   
							 funSetRecipeData(code);
						   }
						});
				
				$('#txtParentCode').blur(function () 
						{
						 var code=$('#txtParentCode').val();
						 if (code.trim().length > 0 && code !="?" && code !="/"){							   
							 funSetParentData(code);
						   }
						});
				
				$('#txtChildCode').blur(function () 
						{
						 var code=$('#txtChildCode').val();
						 if (code.trim().length > 0 && code !="?" && code !="/"){							   
							 funSetChildData(code);
						   }
						});
				
				$("form").submit(function(){
					
					var table = document.getElementById("tblChild");
				    var rowCount = table.rows.length;
				    
					if($("#txtParentCode").val()=='')
					{
						alert("Please Enter Parent Brand Or Search");
						return false;
					} else if(rowCount==0)
					{
						alert("Please Add Brand in Grid");
						return false;
					}   
							
			}); 
		});		
		
	</script>
</head>

<body>
<div id="formHeading">
		<label>Excise Recipe Master</label>
	</div>
	<s:form name="exciserecipeForm" method="POST" action="saveExciseRecipeMaster.html?saddr=${urlHits}">
		<br>
			<table  class="transTable">
				<tr>
			        <th align="right" colspan="5"> <a id="baseUrl" href="#">Attach Documents</a> &nbsp; &nbsp; &nbsp; &nbsp;</th>
			    </tr>
				
			    <tr>
			        <td width="90px"><label >Recipe Code</label></td>
			        <td width="130px"><s:input id="txtRecipeCode" path="strRecipeCode" ondblclick="funHelp('RecipeCode');" cssClass="searchTextBox"/></td>			        
					<td></td>
					<td></td>
					<td></td>	      
		      	</tr>
		      	<tr>
			        <td><label>Parent Brand</label></td>
			        <td><s:input id="txtParentCode" required="true" path="strParentCode"  ondblclick="funHelp('POSItemWeb-Service');" cssClass="searchTextBox"/>
			        <td width="180px">Name &nbsp;<label id="txtPName"></label></td>	
			        <td width="80px">Size &nbsp;<s:label  id="txtParentSize" path="strParentSize" /></td>	
			        <td><s:input type="hidden" path="strParentName" id="txtParentName" /></td>
		        </tr>
				
				<tr>
				    <td><label>Date Valid From</label></td>
				    <td><s:input id="txtFromDate" required="true" name="txtDtFromDate" path="dtValidFrom" cssClass="calenderTextBox"/></td>
					<td></td>
				    <td><s:label path="dtValidTo">Date Valid To</s:label></td>
				    <td><s:input id="txtToDate" required="true" name="txtToDate" path="dtValidTo" cssClass="calenderTextBox" /></td>				    
					
				</tr>
				</table>
				<br>
			<table class="transTableMiddle">
				<tr>
					<td><label>Child Code</label></td>
					<td>
						<input id="txtChildCode" ondblclick="funHelp('childCode');" class="searchTextBox" />
					</td>
					<td><label id="txtChildName"></label></td>
			
					<td><label>Qty</label></td>
					<td>
						<input id="txtChildQty" type="text" class="BoxW116px positive-integer" /><label>   ML</label>
					</td>
<!-- 					<td><label>ML</label></td> -->
<!-- 					<td> -->
					</td>
					<td>
				</td>
					
					
					<td></td>					
					<td><input id="btnAdd" type="button" value="Add" class="smallButton" onclick="return btnAdd_onclick();" /></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>
			
			<div class="dynamicTableContainer" >
			<table style="height:28px;border:#0F0;width:100%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC" >
				<td width="15%">Child Code</td><!--  COl1   -->				
				<td width="60%">Name</td><!--  COl2   -->
				<td width="10%">Qty</td><!--  COl3   -->
				<td width="5%">Delete</td><!--  COl6   -->
			</tr>
			</table>
			<div style="background-color: #a4d7ff;
    border: 1px solid #ccc;
    display: block;
    height: 190px;
    margin: auto;
    overflow-x: hidden;
    overflow-y: scroll;
    width: 100%;">
	<table id="tblChild" style="width:100%;border:#0F0;table-layout:fixed;overflow:scroll" class="transTablex col7-center">
		<tbody>    
			<col style="width:17%"><!--  COl1   -->		
			<col style="width:68%"><!--  COl2   -->
			<col style="width:11%"><!--  COl3   -->
			<col style="width:3%"><!--  COl4   -->
		</tbody>
	
	</table>
	
	</div>				
	</div>	
	
	
		<p align="center">
			<input type="submit" id="formsubmit" value="Submit" class="form_button" />
			<input type="reset" value="Reset" class="form_button" />
		</p>
		<br><br>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
	</s:form>
	<script type="text/javascript">funApplyNumberValidation();</script>
</body>
</html>