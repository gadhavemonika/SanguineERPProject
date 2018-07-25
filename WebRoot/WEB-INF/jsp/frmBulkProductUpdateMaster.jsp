<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

	<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib" %>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8"/>
	<link rel="stylesheet" type="text/css" href="<spring:url value="/resources/css/Accordian/jquery-ui-1.8.9.custom.css "/>" />	
	<script type="text/javascript" src="<spring:url value="/resources/js/Accordian/jquery.multi-accordion-1.5.3.js"/>"></script>	
		
	<style type="text/css">
		/*demo page css*/
		body{ font: 62.5% "Trebuchet MS", sans-serif; margin: 0px;}
	</style>
	
<title>BulkPriceUpdation</title>
<script type="text/javascript">
	
	var productMasterData="",gridHelpRow ="",fieldName="";
   //Set tab which have Active on form loding
	$(document).ready(function()
	{
		//Ajax Wait 
	 	$(document).ajaxStart(function()
	 	{
		    $("#wait").css("display","block");
	  	});
	 	
		$(document).ajaxComplete(function()
		{
		    $("#wait").css("display","none");
		});	
		
		 
		  $("#btnAdd").click(function(event){
			  funSaveProduct();
			});
	});
	
   
	function funSaveProduct()
	{
	document.bulkProduct.action="saveProduct.html?saddr=${urlHits}";
	document.bulkProduct.submit();
	}
</script>

	<script type="text/javascript">
	
		
		
		//Remove all Row from productGrid
		function funRemoveProductRows()
		{
			var table = document.getElementById("tblProduct");
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
		}
		
		
		//Fill Product Grid from Against 
		function funFillTableSubGroupProduct(strProdCode,strProdName,strSGCode,strSGName,dblCostRM,strLocCode,strLocName,dblWeight,dblMRP,strProdType,
											strTaxIndicator,strNonStockableItem,dblYieldPer,strExciseable,strNotInUse,strPickMRPForTaxCal,strBarCode,strReceivedUOM,
											dblReceiveConversion,strIssueUOM,dblIssueConversion,strRecipeUOM,dblRecipeConversion) 
		{
			
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input name=\"listProdModel["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box prodCode\" size=\"5%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";
		    row.insertCell(1).innerHTML= "<input name=\"listProdModel["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"12%\" id=\"lblProdName."+(rowCount)+"\" value='"+strProdName+"' />";
		    
		    row.insertCell(2).innerHTML= "<input name=\"listProdModel["+(rowCount)+"].strSGCode\"    required = \"required\" size=\"7%\" style=\"background-position: 70px 2px;;width:100%\" readonly=\"readonly\" class=\"searchTextBox\" id=\"strSGCode."+(rowCount)+"\" value='"+strSGCode+"' />"; 
		   	row.insertCell(3).innerHTML= "<input type=\"text\"  required = \"required\" size=\"6%\" style=\"width:100%\" class=\"Box\" id=\"strSGName."+(rowCount)+"\" value='&nbsp;&nbsp;"+strSGName+"'>";
		    row.insertCell(4).innerHTML= "<input name=\"listProdModel["+(rowCount)+"].dblCostRM\" type=\"text\"  required = \"required\" style=\"text-align: right;width:100%\" size=\"6%\" class=\"decimal-places inputText-Auto\" id=\"dblCostRM."+(rowCount)+"\" value='"+dblCostRM+"' />";
		    row.insertCell(5).innerHTML= "<input name=\"listProdModel["+(rowCount)+"].strLocCode\" type=\"text\"   style=\"background-position: 70px 2px;;width:100%\" size=\"7%\" class=\"searchTextBox\" id=\"strLocCode."+(rowCount)+"\" value='"+strLocCode+"'  />";
		   	row.insertCell(6).innerHTML= "<input type=\"text\"  required = \"required\" style=\"width:100%\" size=\"6%\" class=\"Box\" id=\"strLocName."+(rowCount)+"\" value='&nbsp;&nbsp;"+strLocName+"'>";
		   
		   	row.insertCell(7).innerHTML= "<input name=\"listProdModel["+(rowCount)+"].dblWeight\"   size=\"4%\" style=\"text-align: right;\"  id=\"dblWeight."+(rowCount)+"\" value="+dblWeight+" />";
		    row.insertCell(8).innerHTML= "<input name=\"listProdModel["+(rowCount)+"].dblMRP\" type=\"text\"  required = \"required\" style=\"text-align: right;width:100%\" size=\"4%\" class=\"decimal-places inputText-Auto \"id=\"dblMRP."+(rowCount)+"\" value='"+dblMRP+"' />";
		    row.insertCell(9).innerHTML= "<select name=\"listProdModel["+(rowCount)+"].strProdType\" class=\"BoxW48px\" style=\"width: 100%;\" readonly=\"readonly\" id=\"cmbProdType."+(rowCount)+"\"  >   '"+funGetProdType(StrProdType)+"'";
		    
		    row.insertCell(10).innerHTML= "<input name=\"listProdModel["+(rowCount)+"].strTaxIndicator\" type=\"checkbox\"   size=\"4%\" class=\"inputText-Auto\" id=\"strTaxIndicator."+(rowCount)+"\" value='"+strTaxIndicator+"'>";
		    row.insertCell(11).innerHTML= "<input name=\"listProdModel["+(rowCount)+"].strNonStockableItem\"  type=\"checkbox\" class=\"inputText-Auto\"   size=\"4%\"  id=\"strNonStockableItem."+(rowCount)+"\" value='"+strNonStockableItem+"'  >";
		    row.insertCell(12).innerHTML= "<input name=\"listProdModel["+(rowCount)+"].dblYieldPer\" type=\"box\"  required = \"required\" style=\"text-align: right;width:100%\"  size=\"4%\" class=\"decimal-places inputText-Auto\" id=\"dblYieldPer."+(rowCount)+"\" value='"+dblYieldPer+"'  >";
		    row.insertCell(13).innerHTML= "<input name=\"listProdModel["+(rowCount)+"].strExciseable\" type=\"checkbox\" class=\"inputText-Auto\" readonly=\"readonly\"  size=\"4%\" id=\"strExciseable."+(rowCount)+"\" value='"+strExciseable+"' >";
		    row.insertCell(14).innerHTML= "<input name=\"listProdModel["+(rowCount)+"].strNotInUse\"  type=\"checkbox\" class=\"inputText-Auto\" size=\"4%\"  id=\"txtRemark."+(rowCount)+"\" value='"+strNotInUse+"'>";
			
		    row.insertCell(15).innerHTML= "<input name=\"listProdModel["+(rowCount)+"].strPickMRPForTaxCal\" type=\"checkbox\"  size=\"4%\" class=\"inputText-Auto\" id=\"strPickMRPForTaxCal."+(rowCount)+"\" value='"+strPickMRPForTaxCal+"'>";
			row.insertCell(16).innerHTML= "<input name=\"listProdModel["+(rowCount)+"].strBarCode\" type=\"text\" class=\"Box \" size=\"4%\"  id=\"txtIssueLocation."+(rowCount)+"\" value='"+strBarCode+"' >";
	        row.insertCell(17).innerHTML= "<select name=\"listProdModel["+(rowCount)+"].strReceivedUOM\" class=\"Box\" style=\"width:100%\"  id=\"strReceivedUOM."+(rowCount)+"\" value='"+strReceivedUOM+"' >";
		    row.insertCell(18).innerHTML= "<input name=\"listProdModel["+(rowCount)+"].dblReceiveConversion\"   size=\"4%\" id=\"dblReceiveConversion."+(rowCount)+"\" value="+dblReceiveConversion+">";
		    row.insertCell(19).innerHTML= "<select name=\"listProdModel["+(rowCount)+"].strIssueUOM\" style=\"width:100%\"  class=\"inputText-Auto\" id=\"strIssueUOM."+(rowCount)+"\" value='"+strIssueUOM+"'>";
		    row.insertCell(20).innerHTML= "<input name=\"listProdModel["+(rowCount)+"].dblIssueConversion\" type=\"text\" size=\"4%\"  id=\"txtIssueConversion."+(rowCount)+"\"  value = '"+dblIssueConversion+"' >";
			row.insertCell(21).innerHTML= "<select name=\"listProdModel["+(rowCount)+"].strRecipeUOM\" style=\"width:100%\"  id=\"txtRecipeUOM."+(rowCount)+"\"  value = '"+strRecipeUOM+"' >";
			row.insertCell(22).innerHTML= "<input name=\"listProdModel["+(rowCount)+"].dblRecipeConversion\" type=\"text\" size=\"4%\"  id=\"txtRecipeConverstion."+(rowCount)+"\"  value = '"+dblRecipeConversion+"' >";
		   
		}
		
		
		
		
		function funRemoveProductRows() 
	    {
			 var table = document.getElementById("tblProduct");
			 var rowCount = table.rows.length;			   
			//alert("rowCount\t"+rowCount);
			for(var i=rowCount-1;i>=0;i--)
			{
				table.deleteRow(i);						
			} 
	    }
		
		
		
		
		//Open purchase help
		function funOpenHelp()
		{
			if ($("#cmbAgainst").val() == 'Purchase Order')
			{
				var POCode=$("#cmbPODoc").val();
				//alert(POCode);
				if(POCode.trim().length>0)
				{
					fieldName = "prodforPO";
					transactionName=fieldName;
				//	window.showModalDialog("searchform.html?formname="+transactionName+"&POCode="+POCode+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
					window.open("searchform.html?formname="+transactionName+"&POCode="+POCode+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;top=500,left=500")
				}
			}
			else
			{
				funHelp("productInUse");
			}
		}
		
	
		//Open issue Location help form
		function funHelp1(row,transactionName)
		{
			Locrow=row;
			fieldName = transactionName;
			if(transactionName=='IssueLoc1')
			{
				transactionName="locationmaster";
			}
			// window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
			 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;top=500,left=500")
		}
		
		//Set data on the basis of help or selection 
		function funSetData(code)
		{
			switch (fieldName) 
			{
			 			        
			    case 'locationmaster':
			    	funSetLocation(code);
			        break;
			        
			    case 'productInUse':
			    	funSetProduct(code);
			        break;
			        
			    case 'suppcode':
			    	funSetSupplier(code);
			        break;
			    
			  
			}
		}
		
		function funLoadSGProduct()
		{
			var sgCode = $("#cmbSubGroup").val();
			$.ajax({
				type : "GET",
				url : getContextPath()
						+ "/loadSubGroupWiseProduct.html?sgCode=" + sgCode,
				dataType : "json",
				success : function(response) {
				//	funRemoveProductRows();
					
					productMasterData = response;
					showTable();
					$.each(response, function(i,item)
		               {		
					
						/* funFillTableSubGroupProduct(response[i].strProdCode,response[i].strProdName,response[i].strSGCode,response[i].strSGName,response[i].dblCostRM,response[i].strLocCode,response[i].strLocName,response[i].dblWeight,response[i].dblMRP,response[i].strProdType,
								response[i].strTaxIndicator,response[i].strNonStockableItem,response[i].dblYieldPer,response[i].strExciseable,response[i].strNotInUse,response[i].strPickMRPForTaxCal,response[i].strBarCode,response[i].strReceivedUOM,
								response[i].dblReceiveConversion,response[i].strIssueUOM,response[i].dblIssueConversion,response[i].strRecipeUOM,response[i].dblRecipeConversion);
					 */
					});
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
		
		function showTable()
		{
			var optInit = getOptionsFromForm();
		    $("#Pagination").pagination(productMasterData.length, optInit);	
		    
		}
		
		var items_per_page = 20;
		function getOptionsFromForm()
		{
		    var opt = {callback: pageselectCallback};
			opt['items_per_page'] = items_per_page;
			opt['num_display_entries'] = 20;
			opt['num_edge_entries'] = 3;
			opt['prev_text'] = "Prev";
			opt['next_text'] = "Next";
		    return opt;
		}
		
		function pageselectCallback(page_index, jq)
		{
		    // Get number of elements per pagionation page from form
		    var max_elem = Math.min((page_index+1) * items_per_page, productMasterData.length);
		    var newcontent="";
		    var rowCount=0;
		   		    	
			   	newcontent = '<table id="tblProductPag" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;">'
			   	+'<tr bgcolor="#75c0ff">'
			   	+'<td width="10px">Product Code</td><td width="22px">Product Name</td><td width="30px">SGCode</td><td width="6px">SGName       </td>'
			   	+'<td width="2px">Pur.Price</td><td width="23px">LocCode</td><td width="20px">LocName</td><td width="8px">Weight</td>'
			   	+'<td width="4px">Product MRP</td><td width="5px">Item Type</td><td width="4px">Tax Indi</td>'
			   	+'<td width="4px">Non Skkable Item</td><td width="4px">Yield %</td><td width="4px">Exciseable</td>'
			   	+'<td width="4px">Item Not In Use</td><td width="4px">Pick MRP Tax Cal.</td><td width="4px">Bar Code</td>'
			   	+'<td width="4px">Recv.UOM </td><td width="4px">Recv. Conv</td><td width="4px">Iss.UOM</td>'
			   	+'<td width="4px">Iss.Conv</td><td width="4px">Recipe UOM </td><td width="4x">Recip. Conv</td>'
			   	+'</tr>';
			   	
			   	// Iterate through a selection of the content and build an HTML string
			    for(var i=page_index*items_per_page;i<max_elem;i++)
			    {
			    
			        newcontent += '<tr><td>'+ "<input name=\"listProdModel["+(i)+"].strProdCode\" readonly=\"readonly\" class=\"Box prodCode\" size=\"10px\" id=\"txtProdCode."+(i)+"\" value='"+productMasterData[i].strProdCode+"' /></td>";
			        newcontent += '<td>'+ "<input name=\"listProdModel["+(i)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"22px\" id=\"lblProdName."+(i)+"\" value='"+productMasterData[i].strProdName+"' /></td>";
				    
// 			        newcontent += '<td>'+ "<input name=\"listProdModel["+(rowCount)+"].strSGCode\"    required = \"required\" size=\"10%\" style=\"background-position: 70px 2px;;width:100%\" readonly=\"readonly\" class=\"searchTextBox\" id=\"strSGCode."+(rowCount)+"\" value='"+productMasterData[rowCount].strSGCode+"' /></td>"; 
			        newcontent += '<td>'+ "<input name=\"listProdModel["+(i)+"].strSGCode\"    required = \"required\"  style=\"background-position: 70px 2px;;width:80px\" size=\"10px\" readonly=\"readonly\"  class=\"searchTextBox\" id=\"strSGCode."+(i)+"\" value='"+productMasterData[i].strSGCode+"' ondblclick=\"Javacsript:funHelp1('subgroup',"+(i)+")\"/></td>";
			                          
			        newcontent += '<td>'+ "<input type=\"text\"  required = \"required\" size=\"8px\" style=\"width:100px\" size=\"10px\" class=\"Box\" id=\"strSGName."+(i)+"\" value='&nbsp;&nbsp;"+productMasterData[i].strSGName+"'></td>";
			        newcontent += '<td>'+ "<input name=\"listProdModel["+(i)+"].dblCostRM\" type=\"text\"  required = \"required\" style=\"text-align: right;width:40px\"class=\"decimal-places inputText-Auto\" id=\"dblCostRM."+(i)+"\" value='"+productMasterData[i].dblCostRM+"' /></td>";
			      
			        
// 			        newcontent += '<td>'+ "<input name=\"listProdModel["+(rowCount)+"].strLocCode\" type=\"text\"  required = \"required\" style=\"background-position: 70px 2px;;width:100%\" size=\"10%\" class=\"searchTextBox\" id=\"strLocCode."+(rowCount)+"\" value='"+productMasterData[rowCount].strLocCode+"'  /></td>";
			        newcontent += '<td>'+ "<input name=\"listProdModel["+(i)+"].strLocCode\" type=\"text\"  style=\"background-position: 70px 2px;;width:80px\" size=\"10px\" class=\"searchTextBox\" id=\"strLocCode."+(i)+"\" value='"+productMasterData[i].strLocCode+"'   ondblclick=\"Javacsript:funHelp1('locationmaster',"+(i)+")\"/></td>";
			        
			        
			        newcontent += '<td>'+ "<input type=\"text\"  required = \"required\" style=\"width:90px\" size=\"20px\" class=\"Box\" id=\"strLocName."+(i)+"\" value='&nbsp;&nbsp;"+productMasterData[i].strLocName+"'></td>";
				   
			        newcontent += '<td>'+ "<input name=\"listProdModel["+(i)+"].dblWeight\" style=\"width:40px;text-align: right;\"  size=\"4px\" id=\"dblWeight."+(i)+"\" value="+productMasterData[i].dblWeight+" /></td>";
			        newcontent += '<td>'+ "<input name=\"listProdModel["+(i)+"].dblMRP\" type=\"text\"  required = \"required\" style=\"text-align: right;width:50px\" size=\"4px\" class=\"decimal-places inputText-Auto \"id=\"dblMRP."+(i)+"\" value='"+productMasterData[i].dblMRP+"' /></td>";
			        newcontent += '<td>'+ "<select name=\"listProdModel["+(i)+"].strProdType\" class=\"BoxW48px\" style=\"width: 100px;\" readonly=\"readonly\" id=\"cmbProdType."+(i)+"\"  >'"+funGetProdType(productMasterData[i].strProdType)+"'</td>"; 
// 			        <option value='"+productMasterData[rowCount].strProdType+"'>"+productMasterData[rowCount].strProdType+"</option> ></td>";
				    
// 			        newcontent += '<td>'+ "<input name=\"listProdModel["+(rowCount)+"].strTaxIndicator\" type=\"checkbox\"   size=\"4%\" class=\"inputText-Auto\" id=\"strTaxIndicator."+(rowCount)+"\" value='"+productMasterData[rowCount].strTaxIndicator+"'></td>";
 				    newcontent += '<td>'+ "<select name=\"listProdModel["+(i)+"].strTaxIndicator\"   class=\"BoxW48px\"  style=\"width: 40px;\" readonly=\"readonly\" id=\"strTaxIndicator."+(i)+"\" >'"+funGetTaxIndicator(productMasterData[i].strTaxIndicator)+"'></td>";
			        newcontent += '<td>'+ "<input name=\"listProdModel["+(i)+"].strNonStockableItem\"  type=\"checkbox\" class=\"inputText-Auto\"   size=\"4px\"  id=\"strNonStockableItem."+(i)+"\" value='"+productMasterData[i].strNonStockableItem+"'  ></td>";
			        newcontent += '<td>'+ "<input name=\"listProdModel["+(i)+"].dblYieldPer\" type=\"box\"  required = \"required\" style=\"text-align: right;width:40px\"  size=\"4px\" class=\"decimal-places inputText-Auto\" id=\"dblYieldPer."+(i)+"\" value='"+productMasterData[i].dblYieldPer+"'  ></td>";
			        newcontent += '<td>'+ "<input name=\"listProdModel["+(i)+"].strExciseable\" type=\"checkbox\" class=\"inputText-Auto\" readonly=\"readonly\"  size=\"4px\" id=\"strExciseable."+(i)+"\" value='"+productMasterData[i].strExciseable+"' ></td>";
			        newcontent += '<td>'+ "<input name=\"listProdModel["+(i)+"].strNotInUse\"  type=\"checkbox\" class=\"inputText-Auto\" size=\"4px\"  id=\"txtRemark."+(i)+"\" value='"+productMasterData[i].strNotInUse+"'></td>";
					
			        newcontent += '<td>'+ "<input name=\"listProdModel["+(i)+"].strPickMRPForTaxCal\" type=\"checkbox\"  size=\"4px\" class=\"inputText-Auto\" id=\"strPickMRPForTaxCal."+(i)+"\" value='"+productMasterData[i].strPickMRPForTaxCal+"'></td>";
			        newcontent += '<td>'+ "<input name=\"listProdModel["+(i)+"].strBarCode\" type=\"text\" class=\"Box \" size=\"4px\"  id=\"txtIssueLocation."+(i)+"\" value='"+productMasterData[i].strBarCode+"' ></td>";
			        newcontent += '<td>'+ "<select name=\"listProdModel["+(i)+"].strReceivedUOM\" class=\"Box\" style=\"width:50px\"  id=\"strReceivedUOM."+(i)+"\"  "+getProductTypeUOM(productMasterData[i].strReceivedUOM)+"  ";
			        newcontent += '<td>'+ "<input name=\"listProdModel["+(i)+"].dblReceiveConversion\"   size=\"4px\"  style=\"width:30px;text-align: right;\" id=\"dblReceiveConversion."+(i)+"\" value="+productMasterData[i].dblReceiveConversion+"></td>";
			        newcontent += '<td>'+ "<select name=\"listProdModel["+(i)+"].strIssueUOM\" style=\"width:40px\"  class=\"Box inputText-Auto\" id=\"strIssueUOM."+(i)+"\" "+getProductTypeUOM(productMasterData[i].strIssueUOM)+" ";
			        newcontent += '<td>'+ "<input name=\"listProdModel["+(i)+"].dblIssueConversion\" type=\"text\" style=\"width:30px;text-align: right;\" size=\"4px\"  id=\"txtIssueConversion."+(i)+"\"  value = '"+productMasterData[i].dblIssueConversion+"' ></td>";
			        newcontent += '<td>'+ "<select name=\"listProdModel["+(i)+"].strRecipeUOM\" style=\"width:50px\" class=\"Box\"  id=\"txtRecipeUOM."+(i)+"\"  "+getProductTypeUOM(productMasterData[i].strRecipeUOM)+"  ";
			        newcontent += '<td>'+ "<input name=\"listProdModel["+(i)+"].dblRecipeConversion\" type=\"text\" size=\"4px\" style=\"width:30px;text-align: right;\" id=\"txtRecipeConverstion."+(i)+"\"  value = '"+productMasterData[i].dblRecipeConversion+"' ></td></tr>";
			       
			        rowCount++;
			    }
			    newcontent += '</table>';
			    $('#Searchresult').html(newcontent);
			    return false;
		   	}




		    
		function getProductTypeUOM(strUOM)
		{
			var retOption="";
			$.ajax({
				type : "GET",
				url : getContextPath()
						+ "/loadUOMList.html",
				dataType : "json",
				async : false,
				success : function(response) {
				
					$.each(response, function(i,item)
		               {		
							if(response[i]==strUOM)
								{
								retOption+= "<option value='"+response[i]+"' selected>"+response[i]+"</option> ";
								}
							else
								{
								retOption+= "<option value='"+response[i]+"' >"+response[i]+"</option> ";
								}
		               });
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
			retOption+="  > </td>";
			return retOption;
		}
		function funGetProdType( StrProdType)
		{
			var type="";
			
			var strType=["Inventory","Produced","Tools","Service","Labour","Overhead","Scrap","Non-Inventory","Trading"]
			
			 type += "<option value="+StrProdType+" selected>"+StrProdType+"</option>";
			for(var i=0;i<strType.length;i++)
			{
				if(strType[i]!=StrProdType)
				 type += "<option value="+strType[i]+">"+strType[i]+"</option>";
			}

			return type;
		}
		
		
		function funGetTaxIndicator(taxIndicator){
			
			var indicator="";
			var strIndicator=["","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"];
			
			if(taxIndicator=="" || taxIndicator=="selected")
			{
				indicator += "<option value='' selected></option>";
			}else{
				indicator += "<option value="+taxIndicator+" selected>"+taxIndicator+"</option>";
			}
			 for(var i=0;i<strIndicator.length;i++)
			{
				 if(taxIndicator=="selected")
				 {
					 taxIndicator=""; 
				 }
				if(strIndicator[i]!=taxIndicator)
				{
				 if(strIndicator[i]=="" )
				  {
					 
					 indicator += "<option value=''></option>";
					}else{
						indicator += "<option value="+strIndicator[i]+">"+strIndicator[i]+"</option>";
					}
			}
			}
			
			
			
			 return indicator;
		}
		
		$(document).ready(function()
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
						alert(message);
					<%
					}}%>
				});
		
		
		
		
		function funHelp1(transactionName,row)
		{
			gridHelpRow=row;
			fieldName=transactionName;
			window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		}
		

		function funSetData(code)
		{
			switch (fieldName)
			{
			
			    case 'subgroup':
			    	funSetSubGroup(code);
			        break;
			        
			    case 'locationmaster':
			    	funSetLocation(code);
			        break;
		}
		}
			
			function funSetSubGroup(code)
			{
				var searchUrl="";
				searchUrl=getContextPath()+"/loadSubGroupMasterData.html?subGroupCode="+code;
				
				$.ajax({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	if('Invalid Code' == response.strSGCode){
					    		alert('Invalid Code');
					    		document.getElementById("strSGCode."+gridHelpRow).value='';						
				    			document.getElementById("strSGName."+gridHelpRow).value='';
					    	}else{
					    	
					    	document.getElementById("strSGCode."+gridHelpRow).value=response.strSGCode;						
			    			document.getElementById("strSGName."+gridHelpRow).value=response.strSGName;
					    	
					    	
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
		
			function funSetLocation(code)
			{
				var searchUrl="";
				searchUrl=getContextPath()+"/loadLocationMasterData.html?locCode="+code;
				
				$.ajax({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	if(response.strLocCode=='Invalid Code')
					       	{
					       		alert("Invalid Location Code");
					       		
					       		
					       		document.getElementById("strLocCode."+gridHelpRow).value='';						
				    			document.getElementById("strLocName."+gridHelpRow).value='';
					       	}
					       	else
					       	{
					    
					    	
					    	document.getElementById("strLocCode."+gridHelpRow).value=response.strLocCode;						
			    			document.getElementById("strLocName."+gridHelpRow).value=response.strLocName;
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
			
			
			
	</script>
</head>

<body>
<div id="formHeading">
		<label>Bulk Products Update</label>
	</div>
	<div>
	<s:form name="bulkProduct" method="POST" action="updateBulkProduct.html?saddr=${urlHits}">
		<br>
	<div id="multiAccordion">		
	<h3><a href="#">Create Product</a></h3>	
	<div style="background: #D8EDFF">
		<table  class="transTable">
			  <tr>
			  <td><label>Product Name</label></td>
			 <td><s:input type="text" id="txtProductName" 
						path="strProdName" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox jQKeyboard form-control"  /> 
			</td>
			  <td>
				<label>Sub Group</label>
				</td>
				<td>
				<s:select id="cmbSubGrp" path="strSGCode" items="${mpsubGroup}" cssClass="BoxW124px" />
				</td>
				<td>
				<label>Manufacturer</label>
				</td>
			<td><s:select id="cmbManufacture" path="strDesc" items="${manufacturerList}" cssClass="BoxW124px" />
				</td>
				</tr>
				
				<tr>
			  <td><label>Purchase Price</label></td>
			    <td><s:input id="txtCostRM" name="costRM" path="dblCostRM" cssClass="decimal-places-amt numberField"/></td>
			  <td><label>MRP</label></td>
			  <td><s:input id="txtMRP" name="MRP" path="dblMRP"  cssClass="decimal-places-amt numberField"/></td>
			  
			   <td><label>Unit Price</label></td>
			<td><s:input id="txtUnitPrice" name="unitPrice" path="dblUnitPrice" cssClass="decimal-places-amt numberField"/></td>
				</tr>
			<tr>
			  <td><label>Weight/Unit</label></td>
			  <td><s:input id="txtWeight" name="weight" path="dblWeight" cssClass="decimal-places numberField"/></td>
			  <td><label >UOM</label></td>
			  <td><s:select id="txtUOM" name="txtUOM" path="strUOM" items="${uomList}"  cssClass="BoxW124px" onchange="funUOMChange(this)"/><%-- <s:input id="txtUOM" name="uom" path="strUOM" cssClass="BoxW116px" /> --%></td>
			  <td><label>Tax Indicator</label></td>
			  <td><s:select id="cmbTaxIndicator" name="taxIndicator" path="strTaxIndicator" items="${taxIndicatorList}"  cssClass="BoxW48px"/></td>
			</tr>
				<tr>	
				<td><label>Item For Sale</label></td>
				<td colspan="3"><s:checkbox id="chkForSale" name="forSale" path="strForSale" value="" /></td>
										       
				        <td colspan="2"><input id="btnAdd" type="submit" value="Create Product"   class="form_button" ></input></td>
			  </tr>			  
			  </table>
			  <br>
			  <br>
		</div>
		<h3><a href="#">Update Products</a></h3>	
		<div style="background: #D8EDFF">	  
		<table  class="transTable">
			<tr>
			<td>
				<label>Sub Group</label>
			
				 <select  id="cmbSubGroup" class="BoxW48px"  style="width: 150px;" >
									<c:forEach var="sub" items="${mpsubGroup}">
										<option value=${sub.key} >${sub.value}</option>
									</c:forEach>
				</select>
			
			</td>
			<td>
			<input id="btnExcuete" type="button" value="Execute" onclick="funLoadSGProduct()" class="form_button"  ></input>
			<td>
			
			
			</td>
			
			</tr>
			<tr>
			</tr>
			</table>
			<table style="border: 0px solid black; width: 100%; height: 100%; margin-left: auto; margin-right: auto; background-color: #C0E4FF;">
			<%-- <tr>
			
				<td>
					<div class="dynamicTableContainer" style="height:450px ">
							<table  style="height:20px;border:#0F0;width:160%;font-size:11px;
								font-weight: bold;">	
							
							<tr bgcolor="#72BEFC" >
								<td width="5%">Product Code</td><!--  COl1   -->
								<td width="12%">Product Name</td><!--  COl2   -->
								<td width="7%">SGCode</td><!--  COl3  -->
								<td width="6%">SGName</td><!--  COl4   -->
								<td width="3%">Pur.Price</td><!--  COl5   -->
								<td width="6%">LocCode</td><!--  COl6   -->
								<td width="6%">LocName</td><!--  COl7   -->
								<td width="4%">Weight</td><!--  COl8 --> 
								<td width="4%">Product MRP</td><!--  COl9  -->
								<td width="5%">Item Type</td><!--  COl10  -->			
								<td width="4%">Tax Indi</td><!--  COl11   -->
								<td width="4%">Non Skkable Item</td><!--  COl12   -->
 								<td width="4%">Yield Percent</td><!--  COl13   -->
								<td width="4%">Exciseable</td><!--  COl14   -->
 								<td width="4%">Item Not In Use</td><!--  COl15   -->
							
								<td width="4%">Pick MRP Tax Cal.</td><!--  COl16   -->
								<td width="4%">Bar Code</td><!-- COl17   -->
								<td width="4%">Recv.UOM </td><!--  COl18   -->
								<td width="4%">Recv.Conv</td><!--  COl19   -->
								<td width="4%">Iss.UOM</td><!--  COl20   -->
								<td width="4%">Iss.Conv</td><!--  COl121   -->
								<td width="4%">Recipe UOM </td><!-- COl22   -->
								<td width="4%">Recip.Conv</td><!--  COl23   -->
								
							</tr>
							</table>
							<div style="background-color:  	#a4d7ff; border: 1px solid #ccc;display: block;height: 384px;
							margin: auto;overflow-x: hidden;overflow-y: scroll;width: 160%;">
					    
					    
					    <table id="tblProduct" style="width:100%;border:#0F0;table-layout:fixed;overflow:scroll; 
					    height:430px;" class="transTablex col20-center">
						<tbody>    
						<col style="width:5%"><!--  COl1   -->
						<col style="width:12%"><!--  COl2   -->
						<col style="width:6.5%"><!--  COl3   -->
						<col style="width:6.1%"><!--  COl4   -->
						<col style="width:3%"><!--  COl5   -->
						<col style="width:6%"><!--  COl6   -->
						<col style="width:6%"><!--  COl7   -->
						<col style="width:4%"><!--  COl8   -->
						<col style="width:4%"><!--  COl9 -->
						<col style="width:5%"><!--  COl10   -->
						<col style="width:4%"><!--  COl11   -->
						<col style="width:4%"><!--  COl12   -->
						<col style="width:4%"><!--  COl13   -->
						<col style="width:4%"><!--  COl14   -->
						<col style="width:4%"><!--  COl15   -->
						
						<col style="width:4%"><!--  COl16   -->	
						<col style="width:4%"><!--  COl7   -->
						<col style="width:4%"><!--  COl8   -->
						<col style="width:4%"><!--  COl19   -->	
						<col style="width:4%"><!--  COl20   -->	
						<col style="width:4%"><!--  COl21   -->		
						<col style="width:4%"><!--  COl22   -->
						<col style="width:4%;"><!--  COl23  -->						
						
						
					
						</tbody>
						</table>
					    </div>
							
						</div>								
						
						
					</td>
				</tr> --%>
				
				<tr>
				
				<td>
				<dl id="Searchresult" style="width: 100%; margin-left: 26px; overflow:auto;"></dl>
				<div id="Pagination" class="pagination" style="width: 80%;margin-left: 26px;">
		
				</div>
				
				</td>
				
				</tr>
				
				
			</table>
		
		<br><p align="center">
			<input type="submit" value="Update"
				onclick="return funValidateFields();"
				class="form_button" id="btnSaveGRN"/>&nbsp; &nbsp; &nbsp;
				 <input id="btnReset" type="reset" value="Reset"
				class="form_button" onclick="funResetFields()" />
		</p><br><br>
		</div>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
		</div> 
	</s:form>
	</div>
	
	
	<script type="text/javascript">
		$(function(){
			$('#multiAccordion').multiAccordion({
// 				active: [1, 2],
				click: function(event, ui) {
				},
				init: function(event, ui) {
				},
				tabShown: function(event, ui) {
				},
				tabHidden: function(event, ui) {
				}
				
			});
			
			$('#multiAccordion').multiAccordion("option", "active", [1]);  // in this line [0,1,2] wirte then these index are open
		});
	</script>
</body>
</html>