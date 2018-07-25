<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

 <!-- start Tree configuration -->
  	

    <!-- End Tree configuration -->

<title>Sales Order</title>

<script type="text/javascript">

var reportName="",transactionName="",fieldName="";
var gurl="",orderCode="",txtAgainst="";
var cnt=0;
var listRow=2
	$(document).ready(function() {
		
		$( "#txtSOCode" ).blur(function() {
			 var code=$('#txtSOCode').val();
			 txtAgainst=$("#txtAgainst").val();
			 if (code.trim().length > 0 && code !="?" && code !="/"){							   
				 setSalesOrderData(code);
	   		}
			
		});
		
		/* $( "#txtChildProdQty" ).blur(function() {
			 var code=$('#txtChildProdQty').val();
			 txtAgainst=$("#txtChildProdQty").val();
			 if (code.trim().length > 0 && code !="?" && code !="/"){							   
				 funSetProductData(code);
	   		}
			
		});
		 */
		
		<%if (session.getAttribute("success") != null) {
			            if(session.getAttribute("orderCode") != null){%>
			            orderCode='<%=session.getAttribute("orderCode").toString()%>';
			            <%
			           		 session.removeAttribute("orderCode");
		           			 session.removeAttribute("success");
			            }%>	
			            alert("Data Save successfully");
			<%
			}%>
			
			if(orderCode.length >0){
				$( "#txtSOCode" ).val(orderCode);
				$( "#txtSOCode" ).trigger('blur');
			}
		
	});
	

	function funSetData(code) {
		
		switch(fieldName){

		  case 'salesorder':
			  	setSalesOrderData(code);
				break;
		       
		   case 'ProductionOrder':
			   	setProdcutionOrderData(code);
				break;
		       
		   case 'ServiceOrder':
			   setServiceOrderData(code);
				break;
				
		   case 'productmaster':
		    	funSetProductData(code);
		        break;
		}
		
	}
	
	
	function funSetProductData(code)
	{
			$("#txtQty").focus();
			var searchUrl="";
			searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
			$.ajax ({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response) {
			    	if('Invalid Code' == response.strProdCode){
				    		alert('Invalid Product Code');
					    	$("#txtChildProdCode").val('');
					    	$("#txtChildProdName").val('');
					    	$("#txtChildProdCode").focus();
			    	} else{
			    		
					    	$("#txtChildProdCode").val(response.strProdCode);
					    	$("#txtChildProdName").val(response.strProdName);
					    	$("#dblChildWt").val(response.dblWeight);
			    	
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
		if($("#txtChildProdCode").val().trim().length==0)
	    {
			  	alert("Please Enter Product Code Or Search");
		     	$('#txtChildProdCode').focus();
		     	return false; 
		 	}else{
				var strChildProdCode=$("#txtChildProdCode").val();
				if(funDuplicateProduct(strChildProdCode)){
					
					var strChildProdName = $("#txtChildProdName").val();
					var dblQty = $("#txtChildProdQty").val();
					var strRemarks = $("#txtReamark").val();
					var dblWeight = $("#dblChildWt").val();
					
					if($("#txtChildProdQty").val().trim().length==0){
						alert("Please Enter Quantity");
				     	$('#txtChildProdQty').focus();
				     	return false; 
					}else{
						if($("#txtParentCode").val().trim().length >0 && $("#txtParentProcessCode").val().trim().length >0){
							 funAddProductRow(strChildProdCode,strChildProdName,dblQty,strRemarks,dblWeight)
						}else{
							alert("No Parent Available");
					     	$('#txtParentCode').focus();
					     	return false; 
						}
					}
					 
					$("#txtChildProdCode").focus();
					funApplyNumberValidation();
        	}
		}
	 
	}
	
	function funDuplicateProduct(strChildProdCode)
	{
	    var table = document.getElementById("tblProductDtl");
	    var rowCount = table.rows.length;		   
	    var flag=true;
	    if(rowCount > 0)
	    	{
			    $('#tblProductDtl tr:gt(1)').each(function()
			    {
				    if(strChildProdCode==$(this).find('input').val() )// `this` is TR DOM element
    				{
				    	alert("Already added "+ strChildProdCode);
	    				flag=false;
    				}
				});
			    
	    	}
	    return flag;
	  
	}
	
	function setSalesOrderData(SOcode){
		
		$("#txtParentCode").focus();
		orderCode=SOcode;
		$("#test1").empty();
		cnt=0;
    	gurl=getContextPath()+"/loadOrderTree.html?orderCode="+SOcode+"&strAgainst="+txtAgainst;
		$.ajax({
	        type: "GET",
	        url: gurl,
	        success: function(response)
	        {		        	
        		if(response.orderCode <=0){
        			alert("Invalid Sales Order Code");
        			$("#txtSOCode").val('');
        			$("#txtSOCode").focus();
        			
        		}else{		
        			orderCode=SOcode;
        			$("#txtSOCode").val(response.orderCode);
        			$("#txtSODate").val(response.orderDate);
        			
        			$('#test1 ul').remove();
       				tree = drawTree(response.tree);
			    	$('#test1').html(tree);
			    	if(tree !=null){
			    		make_tree_menu('0');
			    	}

        		}
        	}
		});
		
	}
	
	function drawTree(response){
		
		 tree = '<ul id="'+parseInt(cnt)+'">';
			cnt++;
		
    	$.each(response, function(value,subNode)
		{
    		if(!(jQuery.type(subNode ) === "string")){
    			
    			tree+='<li>';
    			if(parseInt(cnt)>1){
    				
    				var temp=value.split("#");
    				tree+='<input type="checkbox" id="'+temp[1]
					+'" class="checkBox" value="'+temp[1]+'" onClick="funLoadProductsInTable(this);">&nbsp;&nbsp;'+temp[0];
    			}else{
    				var temp=value.split("#");
    				tree+='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+temp[0];
    			}
   				tree+=drawTree(subNode);
    		}
    		else{
    			var temp=value.split("#");
    			tree+='<li>';
				tree+='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+temp[0];;
    			tree+='</li>';
    		}
    	});
    	
    	tree+='</ul>';
    	return tree;
	}
	
	function funLoadProductsInTable(object){
		
		 if (object.checked) {
			 
			 funClearProductTable();
			 
			 $('#test1 .checkBox').not(object).prop('checked', false);  
			 
			 var productCode=object.defaultValue;
			 $("#salesordertree").empty();
		    	txtAgainst=$("#txtAgainst").val();
		    	gurl=getContextPath()+"/loadOrderProducts.html?orderCode="+orderCode+"&strAgainst="+txtAgainst+"&productCode="+productCode;
				$.ajax({
			        type: "GET",
			        url: gurl,
			        success: function(response)
			        {		        	
		        		if(response.listChildProduct.length <=0 ){
		        			alert("Invalid Product For This Sales Order");
		        			$("#txtSOCode").val('');
		        			$("#txtSOCode").focus();
		        			
		        		}else{		
		        			
		        			$("#txtParentCode").val(response.strParentCode);
		        			$("#txtParentName").text(response.strParentName);
		        			
		        			$("#txtParentProcessCode").val(response.strParentProcessCode);
		        			$("#txtParentProcessName").text(response.strParentProcessName);
		        			
		        			$.each(response.listChildProduct, function(i,item){
								var objModel = response.listChildProduct[i];
								funAddProductRow(objModel.strChildCode,objModel.strChildName,
										objModel.dblQty,objModel.strRemarks,objModel.dblWeight);
		 	       	    	 });
		        			
		        			showImage(response.strParentCode);
		        			funApplyNumberValidation();
								
		        		}
		        	}
				}); 
			 
	    }
		
	}
	
	
	 function funAddProductRow(strChildCode,strChildName,dblQty,strRemarks,dblWeight){
			
			var table = document.getElementById("tblProductDtl");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    var rowCount = listRow;
		    
		    row.insertCell(0).innerHTML= "";    
		    
		 	row.insertCell(1).innerHTML= "<input name=\"listChildProduct["+(rowCount)+"].strChildCode\" readonly=\"readonly\" class=\"Box txtChildCode \" size=\"10%\" id=\"txtChildCode."
		 										+(rowCount)+"\" value='"+strChildCode+"' />";		  		   	  
		   
			row.insertCell(2).innerHTML= "<input name=\"listChildProduct["+(rowCount)+"].strChildName\" readonly=\"readonly\" class=\"Box txtChildName\" size=\"27%\" id=\"txtChildName."
	   										 	+(rowCount)+"\" value='"+strChildName+"'/>";
		    
		 	row.insertCell(3).innerHTML= "<input name=\"listChildProduct["+(rowCount)+"].dblQty\" type=\"text\"  required = \"required\" style=\"text-align: right;\" size=\"8%\" class=\"decimal-places numberField txtQty\" id=\"txtQty."
		   	 									+(rowCount)+"\" value='"+dblQty+"' />";
		    
			row.insertCell(4).innerHTML= "<input name=\"listChildProduct["+(rowCount)+"].strRemarks\"  readonly=\"readonly\" class=\"Box\" size=\"30%\" id=\"txtRemarks."
		   										+(rowCount)+"\" value='"+strRemarks+"'/>";
		 
			row.insertCell(5).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteProductRow(this)">';
			
			row.insertCell(6).innerHTML= "<input type=\"hidden\" name=\"listChildProduct["+(rowCount)+"].dblWeight\" id=\"dblWeight."
												+(rowCount)+"\" value='"+dblWeight+"'/>";    
		 	 
		 	listRow++;
		}
	 
	 function funClearProductTable(){

			$("#txtParentCode").val("");
			$("#txtParentName").text("");
			
			$("#txtParentProcessCode").val("");
			$("#txtParentProcessName").text("");
		 
			$("#tblProductDtl tr:gt(1)").detach();
	 }
	
	 	
 	function funDeleteProductRow(obj) {
			var index = obj.parentNode.parentNode.rowIndex;
			var table = document.getElementById("tblProductDtl");
			table.deleteRow(index);
		}

	function setProdcutionOrderData(){
		
	}
	
	function setServiceOrderData(){
		
	}
	

	function funHelp(transactionName) {
		fieldName = transactionName;
	//	window.showModalDialog("searchform.html?formname=" + transactionName+ "&searchText=", "","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname=" + transactionName+ "&searchText=", "","dialogHeight:600px;dialogWidth:600px;top=500,left=500");
	}
	
	function funHelp1()
	{
		funSetTransCodeHelp();
		fieldName = transactionName;
      //  window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;top=500,left=500");
    }
	
	function funSetTransCodeHelp()
	{
		txtAgainst=$("#txtAgainst").val();
		switch (txtAgainst) 
		{
		   case 'salesOrder':
			   reportName="loadExciseSalesMasterData.html";
			   transactionName='salesorder';
		       break;
		       
		   case 'productionOrder':
			   reportName="loadOPData.html";
			   transactionName='ProductionOrder';
		       break;
		       
		   case 'serviceOrder':
			   reportName="loadExciseBrandOpeningMasterData.html";
			   transactionName='ServiceOrder';
		       break;
		}
	}
	

	function showImage(productCode) {
		var searchUrl = getContextPath()+"/loadSalesOrderBOMImage.html?productCode="+productCode+"&strAgainst="+txtAgainst;
			$("#prodImage").attr('src', searchUrl);
	}
	
	/* function showImage(SOcode,txtAgainst) {
		var searchUrl="";
		if(txtAgainst.toString() == 'salesOrder'){
			searchUrl=getContextPath()+"/loadSalesOrderBOMImage.html?SOcode="+SOcode+"&strAgainst="+txtAgainst;
			$("#prodImage").attr('src', searchUrl);
		}else if(txtAgainst.toString() == 'productionOrder'){
			 searchUrl=getContextPath()+"/loadSalesOrderBOMImage.html?SOcode="+SOcode+"&strAgainst="+txtAgainst;
			$("#prodImage").attr('src', searchUrl);
		}else if(txtAgainst.toString() == 'serviceOrder'){
			 searchUrl=getContextPath()+"/loadSalesOrderBOMImage.html?SOcode="+SOcode+"&strAgainst="+txtAgainst;
			$("#prodImage").attr('src', searchUrl);
		}else{
			$("#prodImage").attr('alt', "No Image");
		}
		
	} */
	
	
	function funApplyNumberValidation(){
		$(".numeric").numeric();
		$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
		$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
		$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
	    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit,negative: false});
	}
	
	
</script>

</head>
<body>
	<div id="formHeading">
		<label>Sales Order BOM</label>
	</div>
	
	<br />
	<br />

	<s:form name="SoBom" method="POST" action="saveSOBOM.html">
		<table class="transTable">
			<tr>
				<th align="right" colspan="9"><a id="baseUrl" href="#">Attach
						Documents </a></th>
			</tr>
		</table>
		<div id="masterdiv1" class="transTable" style="overflow: hidden !important;">
			
			<div id="firstchild2" class="transTable"
				style="float: left; width: 84%; margin: auto;">
				<table class="transTable"
					style="margin: 0px; padding: 0px; height: 160px; width: 100%;">
					<tr>
						<td><label>Against</label></td>
						<td><s:select id="txtAgainst" path="strAgainst"
								cssClass="BoxW124px">
								<s:option value="salesOrder">Sales Order</s:option>
								<s:option value="productionOrder">Production Order</s:option>
								<s:option value="serviceOrder">Service Order</s:option>
							</s:select></td>

						<td><label>SO Code</label></td>
						<td><s:input path="strSOCode" id="txtSOCode" 
								ondblclick="funHelp1()" cssClass="searchTextBox" /></td>
						<td><label>Sale Order Date</label></td>
						<td><s:input path="dteSODate" id="txtSODate" readonly="true"
								required="required" class="BoxW116px" /></td>
					</tr>
				</table>
			</div>
			<div id="secondchild1"
				style="width: 15.7%; height: 160px; border: 1px solid; float: right;">
				<img id="prodImage" alt="No Image" src=""
					style="width: 100%; height: 100%;" align="middle">
			</div>
		</div>
		<br />
		
		<div id="masterdiv2" style="width: 100%;">
			<div id="firstchild2" style="width: 300px;">
				<div id="test1" class="menu" style="color: blue !important;"></div>
			</div>
			<div id="secondchild2" style="width: auto; margin: 10px;">
				<div style=" display: block; height: 250px; margin: auto;">
					<table>
						<tr>
							<td width="175px"><label>Parent Product</label></td>
							<td></td>
							<td><s:input path="strParentCode" id="txtParentCode" 
								readonly="true" cssClass="BoxW116px" />
							</td>
							<td></td>
							<td colspan="2"><label id="txtParentName"></label>
							<td>
						</tr>
						<tr>
						<td><label>Parent Proess Code</label></td>
							<td></td>
							<td>
								<s:input path="strParentProcessCode" id="txtParentProcessCode" 
								 	readonly="true" cssClass="BoxW116px" />
							</td>
							<td></td>
							<td>
								<s:label id="txtParentProcessName"
									path="strParentProcessName"></s:label>
							</td>
						</tr>
					</table>
					<br/>

					<table id="tblProductDtl"
							style="width:inherit; border: #0F0; table-layout: fixed; overflow: scroll"
							class="transTablex col15-center">
						<tr bgcolor="#72BEFC">
							<th width="1%"></th>
								<!--  COl1   -->
							<th width="15%">Child Code</th>
							<!--  COl2   -->
							<th width="25%">Child Name</th>
							<!--  COl   -->
							<th width="5%">Quantity</th>
							<!--  COl3   -->
							<th width="30%">Remark</th>
							<!--  COl4   -->
							<th width="5%">Delete</th>
							<!--  COl5   -->
							<th width="1%"></th>
							<!--  COl6   -->
						</tr>
						<!-- 		</table>
				
				<table id="tblJobOrderDtl"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col15-center">-->
						<tr>
							<td></td>
							<td><input id="txtChildProdCode"
								ondblclick="funHelp('productmaster')" class="searchTextBox" />
							</td>

							<td><input id="txtChildProdName" readonly="readonly" class="longTextBox" style="width: 95% !important;" /></td>

							<td><input id="txtChildProdQty" class="decimal-places numberField" /></td>

							<td><input id="txtReamark" class="longTextBox" style="width: 95% !important;" /></td>

							<td><input id="btnAdd" type="button" class="smallButton"
									value="Add" onclick="return btnAdd_onclick()"></td>
							<td><input type="hidden" id="dblChildWt" value="1"/></td>
						</tr>
					</table>
				</div>

			</div>
		</div>
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" class="form_button" /> <input
				type="reset" value="Reset" class="form_button"
				onclick="funResetFields()" />
		</p>

	</s:form>
	<script type="text/javascript">
		funApplyNumberValidation();
	</script>
</body>
</html>