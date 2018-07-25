<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="sp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="default.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>YIELD MASTER</title>
	<script type="text/javascript">
				
		var fieldName;
	
		 $(document).ready(function () 
		{
			 $( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
			 $("#txtFromDate" ).datepicker('setDate', 'today');
			 $( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
			 $("#txtToDate" ).datepicker('setDate', 'today');
			 $(document).ajaxStart(function(){
				    $("#wait").css("display","block");
				  });
				  $(document).ajaxComplete(function(){
				    $("#wait").css("display","none");
				  });
			 
		});
		
		function funResetFields()
		{
			location.reload(true); 	
	    }
		
		function funHelp(transactionName)
		{
			fieldName=transactionName;
			if(transactionName=='childcode')
			{
				if($("#txtParentCode").val()=='')
				{
					alert('Please Select parent item Code');
					return false;
				}
				/* if($("#txtParentQty").val()=='0.0')
				{
					alert('Please Enter parent item quantity');
					return false;
				}	 */			
			}
	        //window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=", 'window', 'width=600,height=600');
	    //    window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:300px;")
	        window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:300px;")
	    }
		
	 	function funSetBom(code)
		{
			document.YieldForm.action="frmYieldMaster1.html?BOMCode="+code;
			document.YieldForm.submit();
		} 
		
		function funSetChild(code)
		{
			searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
			//alert(searchUrl);
			$.ajax({
		        type: "GET",
		        url: searchUrl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strProdCode!="Invalid Code")
		        		{
				        	$("#txtChildCode").val(response.strProdCode);	        		
			        		$("#lblItemName").text(response.strProdName);
			        		$("#lblUOM").text(response.strRecipeUOM);
			        		$("#txtQty").focus();
		        		}
		        	else
		        		{
		        			alert("Invalid Child Code");
		        			$("#txtChildCode").val('');
		        			$("#txtChildCode").focus();
		        			return false;
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
		
		function funSetProduct(code)
		{
			searchUrl=getContextPath()+"/loadProductData.html?prodCode="+code;
			//alert(searchUrl);
			$.ajax({
		        type: "GET",
		        url: searchUrl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strParentCode!="Invalid Product Code")
		        		{
				        	$("#txtParentCode").val(response.strParentCode);
			        		$("#txtParentCodeName").val(response.strParentName);
			        		$("#txtPOSItemCode").val(response.strPartNo);
			        		$("#txtType").val(response.strProdType);
			        		$("#txtSGCode").val(response.strSGCode);
			        		$("#txtSGName").val(response.strSGName);			        		
			        		$("#cmbProcessName").val(response.strProcessName);
			        		$("#txtUOM").val(response.strUOM);
			        		//$("#txtParentQty").focus();
			        		funGetImage(code);
		        		}
		        	else
		        		{
		        			alert("Invalid Parent Product Code");
		        			$("#txtParentCode").val("");
		        			$("#txtParentCode").focus();
		        			return false;
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
		
		
		function funSetData(code)
		{			
			switch (fieldName) 
			{			   
			   case 'yieldcode':
			    	funSetBom(code);
			        break;
			   
			   case 'childcode':
			    	funSetChild(code);
			        break;
			        
			   case 'productmaster':
			    	funSetProduct(code);
			        break;
			}
		}
				
		function btnAdd_onclick() 
		{			
			
			if($("#txtChildCode").val().trim().length==0)
		    {
				  	alert("Please Enter Child Product Code Or Search");
			     	$('#txtProdCode').focus();
			     	return false; 
		    }
			
		    if(($("#txtQty").val() == 0) || ($("#txtQty").val().trim().length==0  ))
			 {
					  alert("Please Enter Quantity ");
				       $('#txtQty').focus();
				       return false; 
			}			     	 
			else
		    {
				var strProdCode=$("#txtChildCode").val();
				if(funDuplicateProduct(strProdCode))
	            	{
						funAddRow();
	            	}
			}
		 
		}
		
		function funDuplicateProduct(strProdCode)
		{
		    var table = document.getElementById("tblChild");
		    var rowCount = table.rows.length;		   
		    var flag=true;
		    if(rowCount > 0)
		    	{
				    $('#tblChild tr').each(function()
				    {
					    if(strProdCode==$(this).find('input').val())// `this` is TR DOM element
	    				{
					    	alert("Already added "+ strProdCode);
		    				flag=false;
	    				}
					});
				    
		    	}
		    return flag;
		  
		}
		
		function funAddRow() 
		{
			var childCode = document.getElementById("txtChildCode").value;
		    var qty = document.getElementById("txtQty").value;
		    var uom = document.getElementById("lblUOM").innerHTML;		    
		    var itemName = document.getElementById("lblItemName").innerHTML;
		    //var qtyConversion = document.getElementById("txtQtyConversion").value;
		    //alert("qtyConversion\t"+qtyConversion);
		    var table = document.getElementById("tblChild");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input type=\"text\" class=\"Box id\" size=\"10%\" name=\"listBomDtlModel["+(rowCount)+"].strChildCode\" id=\"txtChildCode."+(rowCount)+"\" value="+childCode+" ondblclick=\"funHelp('childcode')\">";		    
		    row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"70%\" name=\"listBomDtlModel["+(rowCount)+"].strProdName\"  id=\"lblItemName."+(rowCount)+"\" value='"+itemName+"' >";
		    row.insertCell(2).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" class=\"inputText-Auto\" name=\"listBomDtlModel["+(rowCount)+"].dblQty\" id=\"txtQty."+(rowCount)+"\" value="+qty+">";
		    row.insertCell(3).innerHTML= "<input class=\"Box\" size=\"6%\" name=\"listBomDtlModel["+(rowCount)+"].strUOM\" id=\"lblUOM."+(rowCount)+"\" value="+uom+">";
		    row.insertCell(4).innerHTML= '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
		    fillChildImage(childCode);
		    funResetProductFields();
		    
		    return false;
		}
		 
		function funDeleteRow(obj) 
		{
			var index = $(obj).closest('tr').index();
			var icode = $(obj).closest("tr").find("input[type=text]").val();
		    $("#childProdImages #"+icode).remove();
		    var table = document.getElementById("tblChild");
		    table.deleteRow(index);
		}
			
		
		function funResetProductFields()
		{
			$("#txtChildCode").val('');
		    $("#txtQty").val('');
		    document.getElementById("lblUOM").innerHTML='';
		    document.getElementById("lblItemName").innerHTML='';
		    //$("#txtQtyConversion").val('1');
		}
		
		
		$(function()
		{
			$('a#baseUrl').click(function() 
			{
				if($("#txtYieldCode").val().trim()=="")
				{
					alert("Please Select Yield Code");
					return false;
				}
				window.open('attachDoc.html?transName=frmYieldMaster.jsp&formName=Yield Master&code='+$('#txtYieldCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			});
		});
		
		
		/* function funUpdateConversionQty1(object)
		{			
			var index=object.parentNode.parentNode.rowIndex-2;
			var parentQty=$("#txtParentQty").val();
			var childQty=document.getElementById("txtQty."+index).value;
			document.getElementById("txtQtyConversion."+index).value=childQty/parentQty;
		}
		
		function funUpdateConversionQty()
		{
			var conversionQty=$("#txtQty").val()/$("#txtParentQty").val();
			$("#txtQtyConversion").val(conversionQty);
		} */
		
		function funGetImage(prodCode)
		{
			searchUrl=getContextPath()+"/getProdImage.html?prodCode="+prodCode;
			$("#itemImage").attr('src', searchUrl);
			
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
		$(function()
		{
			$('#txtChildCode').blur(function ()
			{
				var code=$("#txtChildCode").val();
				if (code.trim().length > 0 && code !="?" && code !="/")
				{					   
					funSetChild(code);
				}
			});
			$('#txtParentCode').blur(function ()
			{
				var code=$('#txtParentCode').val();			
				if (code.trim().length > 0 && code !="?" && code !="/")
				{
					
					funSetProduct(code);
				}
			});
		});
		
		
		function funApplyNumberValidation(){
			$(".numeric").numeric();
			$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
			$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
			$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
		    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit,negative: false});
		}
		
		function fillChildImage(imageCode){
			searchUrl=getContextPath()+"/getProdImage.html?prodCode="+imageCode;
			$('#childProdImages').append('<div class=\"mainMenuIcon\" id="'+imageCode+'"><img  src="'+searchUrl+'" width=\"100px\" height=\"100px\"/><div>');
		}
		
		function setImages_ChildProd(){
			var table = document.getElementById("tblChild");
			var rowCount = table.rows.length;
			if(rowCount > 0)
	    	{
				$('#tblChild tr').each(function() 
					    {
							var imageCode=$(this).find('.id').val();
							fillChildImage(imageCode);
						});	
	    	}
		}
		
		$(function()
		{
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
	</script>
</head>

<body>
<div id="formHeading">
<!-- 		<label>Recipe Master</label> -->
		<label>Yield Master</label>
	</div>
	<s:form name="YieldForm" method="POST" action="saveYieldMaster.html?saddr=${urlHits}">
	<input type="hidden" value="${urlHits}" name="saddr">
		<br>
			<table  class="transTable">
				<tr>
			        <th align="right" colspan="5"> <a id="baseUrl" href="#">Attach Documents</a> &nbsp; &nbsp; &nbsp; &nbsp;</th>
			    </tr>
				
			    <tr>
			        <td><label >Yield Code</label></td>
			        <td><s:input id="txtYieldCode" path="strBOMCode" readonly="true" ondblclick="funHelp('yieldcode')" cssClass="searchTextBox"/></td>			        
			        <td><label>Parent Product</label></td>
			        <td ><s:input id="txtParentCode" required="true" path="strParentCode"  ondblclick="funHelp('productmaster')" cssClass="searchTextBox"/>
			        <s:input type="text" id="txtParentCodeName" cssStyle="width:100%" path="strParentName" readonly="true" class="Box"/></td>
			        <td></td>
			    </tr>
				    
			    <tr>
				    <td><label>POS Item Code</label></td>
				    <td><s:input id="txtPOSItemCode" path="strPOSItemCode" readonly="true" class="Box"/></td>
				    <td ><label>Type</label></td>
				    <td ><s:input path="strType" id="txtType" readonly="true" class="Box"></s:input></td>
				    <td rowspan="5" width="13%" style="background-color: #C0E4FF;border: 1px solid black;"><img id="itemImage" src="" width="134px" height="127px" alt="Item Image"  ></td>
				</tr>
				
				<tr>
				    <td><label>Sub Group Code</label></td>
				    <td><s:input id="txtSGCode" path="strSGCode" readonly="true"  class="Box"/></td>
				    <td><label>Name</label></td>
				    <td ><s:input id="txtSGName" path="strSGName" readonly="true"  class="Box"/></td>
				</tr>
				
				<tr>
					<td><label>Process</label></td>
				    <td><s:select id="cmbProcessName" path="strProcessName" items="${processList}" cssClass="BoxW124px"/></td>
				   <s:input type="hidden" id="txtProcessCode" path="strProcessCode"/>
				   <td><label>UOM</label></td>
				    <td><s:input id="txtUOM" path="strUOM" readonly="true"  cssClass="Box"/></td>
				    <%-- <td><label>Quantity</label></td>
				    <td><s:input type="text"  class="decimal-places numberField" id="txtParentQty" path="dblQty" /></td> --%>
				</tr>		
				
				
				<tr>
				    <td><label>Date Valid From</label></td>
				    <td><s:input id="txtFromDate" required="true" name="txtDtFromDate" path="dtValidFrom" cssClass="calenderTextBox"/></td>
				
				    <td><s:label path="dtValidTo">Date Valid To</s:label></td>
				    <td><s:input id="txtToDate" required="true" name="txtToDate" path="dtValidTo" cssClass="calenderTextBox" /></td>				    
				</tr>
				
				<tr>
				<td><label>Method</label></td>
					<td><s:textarea cssStyle="width:100%" id="txtMethod" path="strMethod" /></td>
				<td colspan="2"></td>
				
				</tr>
			</table>
			<div style="margin-bottom: 30px;min-height: 300px">
			<div style="width: 60%;height:300px;background-color: #C0E3FF; float: left;margin-left: 26px;">
			<table id="tblChild1" class="transTableMiddle2">
				<tr>
					<td><label>Child Code</label></td>
					<td><input id="txtChildCode" ondblclick="funHelp('childcode')" class="searchTextBox"></input></td>
					<td><label id="lblItemName"></label></td>
				</tr>
					
				<tr>
					<td><label>Qty</label></td>
					<td><input id="txtQty" type="text" class="decimal-places numberField" ></input></td>
					<td>
						<label>UOM</label>&nbsp;&nbsp;&nbsp;<span id="lblUOM"></span>
					</td>
					<td></td>
					<td><input id="btnAdd" type="button" value="Add" class="smallButton" onclick="return btnAdd_onclick();"></input></td>
				</tr>
			</table>
			
			<div class="dynamicTableContainer"  style="width:100%;height: 235px">
			<table style="height:28px;border:#0F0;width:100%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC" >
				<td width="6%">Child Code</td><!--  COl1   -->				
				<td width="30%">Name</td><!--  COl2   -->
				<td width="6%">Qty</td><!--  COl3   -->
				<td width="6%">UOM</td><!--  COl5   -->
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
		<table id="tblChild" style="width:100%;border:
			#0F0;table-layout:fixed;overflow:scroll" class="transTablex col7-center">
		<tbody>    
		<col style="width:6%"><!--  COl1   -->		
		<col style="width:30%"><!--  COl2   -->
		<col style="width:6%"><!--  COl3   -->
		<col style="width:6%"><!--  COl4   -->
		<col style="width:4%"><!--  COl7   -->
			<c:forEach items="${command.listBomDtlModel}" var="recipe" varStatus="status">
				<tr>
					<td><input type="text" class="Box id" size="10%" name="listBomDtlModel[${status.index}].strChildCode" value="${recipe.strChildCode}" readonly="readonly"/></td>					
					<td><input class="Box" size="80%" name="listBomDtlModel[${status.index}].strProdName" value="${recipe.strProdName}" readonly="readonly"/></td>
					<td><input type="number" step="any" required="required" style="text-align: right;" class="inputText-Auto" name="listBomDtlModel[${status.index}].dblQty" value="${recipe.dblQty}"/></td>
					<td><input class="Box" size="6%" name="listBomDtlModel[${status.index}].strUOM" value="${recipe.strUOM}" readonly="readonly"/></td>
					<td><input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)"></td>
				</tr>
			</c:forEach>
	</tbody>
	
	</table>
	
	</div>				
	</div>	
	</div>
	
	<div id="childProdImages" style="width: 35%;height:300px; background-color: inherit; float: left;margin-left: 0px;overflow-x: hidden;     overflow-y: scroll; border-width: 1px; border-style: solid; border-color: #c3c5c7;">
	
	</div>
	</div>
	
		<p align="center">
			<input type="submit" id="formsubmit" value="Submit" onclick="return funValidateFields()"class="form_button" />
			<input type="button" value="Reset"
				onclick="funResetFields();" class="form_button" />
		</p>
		<br><br>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
	</s:form>
	<script type="text/javascript">
	setParrentImage();
	function setParrentImage(){
		if($("#txtParentCode").val().length >0){
			funGetImage($("#txtParentCode").val());	
		}
	}
	
	setImages_ChildProd();
	</script>
	<script type="text/javascript">funApplyNumberValidation();</script>
</body>
</html>