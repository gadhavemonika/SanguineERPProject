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

	
	$(document).ready(function() 
			{		
				$("#txtSRDate").datepicker({ dateFormat: 'yy-mm-dd' });
					$("#txtSRDate" ).datepicker('setDate', 'today');
					
					$("#txtSCDCDate").datepicker({ dateFormat: 'yy-mm-dd' });
					$("#txtSCDCDate" ).datepicker('setDate', 'today');
					
					$("#txtInRefDate").datepicker({ dateFormat: 'yy-mm-dd' });
					$("#txtInRefDate" ).datepicker('setDate', 'today');
				});	
	
	
	$(document).ready(function() {
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
		
		
		
		var code='';
		<%if(null!=session.getAttribute("rptSRCode")){%>
		code='<%=session.getAttribute("rptSRCode").toString()%>';
		<%session.removeAttribute("rptSRCode");%>
		var isOk=confirm("Do You Want to Generate Slip?");
		if(isOk){
		window.open(getContextPath()+"/openRptSubContractorGRNSlip.html?rptSRCode="+code,'_blank');
		}
		<%}%>

		});
	
	
	$(function()
			{
				$("#txtSRCode").blur(function() 
					{
					var code=$('#txtSRCode').val();
					if(code.trim().length > 0 && code !="?" && code !="/")
					{
						funSetSubContractorGRNData(code);
					}
				});
				
				$("#txtSCCode").blur(function() 
						{
							var code=$('#txtSCCode').val();
							if(code.trim().length > 0 && code !="?" && code !="/")
							{
								 funSetCustomer(code);							}
						});
		
				$("#txtLocCode").blur(function() 
						{
							var code=$('#txtLocCode').val();
							if(code.trim().length > 0 && code !="?" && code !="/")
							{
								funSetLocation(code);
							}
						});
				$("#txtProdCode").blur(function() 
						{
							var code=$('#txtProdCode').val();
							if(code.trim().length > 0 && code !="?" && code !="/")
							{
								funSetProduct(code);
							}
						});
				
			});

	function funSetData(code){

		switch(fieldName){
		
		case 'locationmaster':
	    	funSetLocation(code);
	        break;
		
		case 'productmaster':
	    	funSetProduct(code);
	        break;
	        
		case 'subContractor':
			   funSetCustomer(code);
		        break;    
		        
		case 'JOCode' :
			$("#txtJWCode").val(code);
			break;
			
		case 'SCDNCode' :
			$("#txtSCDCCode").val(code);
			funSetExpectedProductData(code);
			break;
		
		case 'SCGRNCode' :
			funSetSubContractorGRNData(code);
			break;
			
	/* 	case 'DNCode' : 
			funSetDNData(code);
			break;	 */
			

		}
	}
	
	
	 function funSetExpectedProductData(code)
		{
			funRemoveAllRows();
			$("#txtProdCode").focus();
			searchUrl = getContextPath() + "/loadExpRetProducts.html?DNCode=" +code;
			$.ajax({
				type : "GET",
				url : searchUrl,
				dataType : "json",
				success : function(response) {
					if (response.strDNCode == 'Invalid Code') {
							alert("Invalid Delivary Note Code");
							$("#txtCode").val("");
							$("#txtCode").focus();
						} else {
							//$("#txtCode").val(response.strDNCode);
							var data1=response;
		        			$.each(data1, function(i,item) {
	       						funFillDNRow(data1[0]);
	       	       	    	 });
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
	
	
	function funSetLocation(code) {
		var searchUrl = "";
		searchUrl = getContextPath()
				+ "/loadLocationMasterData.html?locCode=" + code;

		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				if (response.strLocCode == 'Invalid Code') {
					alert("Invalid Location Code");
					$("#txtLocCode").val('');
					$("#lblLocName").text("");
					$("#txtLocCode").focus();
				} else {
					$("#txtLocCode").val(response.strLocCode);						
					$("#lblLocName").text(response.strLocName);
					$("#txtProdCode").focus();
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
	
	
	function funSetProduct(code)
	{
			var searchUrl="";
			var supp="";
			searchUrl=getContextPath()+"/loadProductDataForTrans.html?prodCode="+code+"&suppCode="+supp;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if(response!="Invalid Product Code")
				    	{
							$("#txtProdCode").val(response[0][0]);
							$("#lblProdName").text(response[0][1]);
							$("#txtPrice").val(response[0][3]);
							
							$("#txtWeight").val(response[0][7]);
							$("#txtQtyRecived").focus();
					     }
				    	else
				    		{
				    		  alert("Invalid Product Code");
				    		  $("#txtProdCode").val('') 
				    		  $("#txtProdCode").focus();
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
	
	
	function funSetCustomer(code)
	{
		
		gurl=getContextPath()+"/loadSubContractorMasterData.html?partyCode=";
		$.ajax({
	        type: "GET",
	        url: gurl+code,
	        dataType: "json",
	        success: function(response)
	        {
	        	
	        		if('Invalid Code' == response.strPCode){
	        			alert("Invalid Sub Contractor Code");
	        			$("#txtSCCode").val('');
	        			$("#txtSCCode").focus();
	        			
	        		}else{
	        			
	        			$("#txtSCCode").val(response.strPCode);
						$("#lblSCName").text(response.strPName);
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
	
	
	function funSetReturnProductData(code) {
		var searchUrl="";
		searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
		$.ajax
		({
	        type: "GET",
	        url: searchUrl,
		    dataType: "json",
		    success: function(response)
		    {
		    	if('Invalid Code' == response.strProdCode){
		    		alert('Invalid Product Code');
			    	$("#txtExpProdCode").val('');
			    	$("#lblExpProdName").val('');
			    	$("#txtExpProdCode").focus();
		    	}
		    	else{
			    	$("#txtExpProdCode").val(response.strProdCode);
			    	$("#lblExpProdName").text(response.strProdName);
			    	
			    	$("#txtExpPrice").val(response.dblCostRM);
			    	$("#txtExpWeight").val(response.dblWeight);
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
		
		if($("#txtProdCode").val().length<=0)
			{
				$("#txtProdCode").focus();
				alert("Please Enter Product Code Or Search");
				return false;
			}	
		
		if($("#txtScrapReturned").val().length<=0)
		{
			$("#txtScrapReturned").focus();
			alert("Please Enter Scrap Returned");
			return false;
		}
		
		
		
		
	    if($("#txtQtyRecived").val().trim().length==0 || $("#txtQtyRecived").val()== 0)
	        {		
	          alert("Please Enter Quantity Recived");
	          $("#txtQtyRecived").focus();
	          return false;
	       } 
	    else
	    	{
	    	 var strProdCode=$("#txtProdCode").val();
	    	 if(funDuplicateProduct(strProdCode))
	    		 {
	    		 	funAddProductRow();
	    		 }
	    	}
	}
	
	
	function funDuplicateProduct(strProdCode)
	{
	    var table = document.getElementById("tblProdDet");
	    var rowCount = table.rows.length;		   
	    var flag=true;
	    if(rowCount > 0)
	    	{
			    $('#tblProdDet tr').each(function()
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


	function funAddProductRow() 
	{
		var table = document.getElementById("tblProdDet");
		
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    var strDNCode="";
	    var strDNProdCode="";
	    var strDNProdName="";
	    var strProcess="";
	    var dblQty="";
	    var dblWeight="";
	    
	    
	    var strProdCode = $("#txtProdCode").val().trim();
		var strProdName=$("#lblProdName").text();
		var dblQtyReceivable=$("#txtQtyReceiveable").val();	
	    var dblDCQty = $("#txtQtyDC").val();
	    
	    var dblDCWt=$("#txtDCWt").val();
	    var dblQtyReceived=$("#txtQtyRecived").val();
	    parseFloat(dblQtyReceived).toFixed(maxQuantityDecimalPlaceLimit);
	    
	    var dblWtUnit=$("#txtWeight").val();
	    var dblTotWt=dblQtyReceived*dblWtUnit;
	    var dblUnitPrice=$("#txtPrice").val();
	    
	    var dblTotPrice=dblUnitPrice*dblQtyReceived;
	    
	    
	    
	    var dblQtyReject=$("#txtQtyReject").val();
	    
	    var dblAcceptQty=dblQtyReceived-dblQtyReject;
	    
// 	    var dblDiff="0.00";
// 	    var dblDiffPer="0.00";
	    
	    var dblDiff =(parseFloat(dblDCQty)- parseFloat(dblQtyReceived));
	    
	    var dblDiffPer = (parseFloat(dblDiff)*100)/(parseFloat(dblQtyReceived));
	    
	    var strScrapReturned=$("#txtScrapReturned").val();
	    
	    var strRemarks=$("#txtRemarks").val();
	    var dblWtReceivable=dblQtyReceivable*dblWtUnit;
	    
	    row.insertCell(0).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].strProdCode\" type=\"text\"  required = \"required\" style=\"text-align: right;\" \size=\"7%\" class=\"Box\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";
	    row.insertCell(1).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].strProdName\" type=\"text\" required =\"required\" style=\"text-align: left;\" size=\"29%\" class=\"Box\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"' />";	    
	    row.insertCell(2).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblQtyRbl\" type=\"text\" required = \"required\"  class=\" totalValueCell\" style=\"text-align: right;\" size=\"4%\" id=\"txtQtyReceivable."+(rowCount)+"\" value='"+dblQtyReceivable+"' />";
	    row.insertCell(3).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblDCQty\" size=\"3%\" required = \"required\" class=\"decimal-places-amt inputText-Auto\" id=\"txtDCQty."+(rowCount)+"\" required = \"required\" value='"+dblDCQty+"' onblur=\"Javacsript:funAcceptQty(this)\">";
	    row.insertCell(4).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblDCWt\" size=\"3%\" style=\"text-align: right;\" id=\"txtDCWt."+(rowCount)+"\" required = \"required\" value='"+dblDCWt+"'/>";
	    row.insertCell(5).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblQtyReceived\" size=\"3%\" style=\"text-align: right;\"  id=\"txtQtyReceived."+(rowCount)+"\" value='"+dblQtyReceived+"' required = \"required\" onblur=\"Javacsript:funAcceptQty(this)\">";
	    row.insertCell(6).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblWeight\" size=\"3%\" style=\"text-align: right;\" id=\"txtWtUnit."+(rowCount)+"\" value='"+dblWtUnit+"' required = \"required\"  onblur=\"Javacsript:funAcceptQty(this)\" >";
	    row.insertCell(7).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblTotWt\" size=\"3%\" style=\"text-align: right;\" class=\"Box totalValueWt\" id=\"txtTotWt."+(rowCount)+"\" required = \"required\" value='"+dblTotWt+"' />";
	    row.insertCell(8).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblPrice\" size=\"7%\" style=\"text-align: right;\" id=\"txtUnitPrice."+(rowCount)+"\" value='"+dblUnitPrice+"' required = \"required\" onblur=\"Javacsript:funAcceptQty(this)\">";
	    row.insertCell(9).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblTotPrice\" size=\"3%\" style=\"text-align: right;\" class=\"Box totalValuePrice\" id=\"txtTotPrice."+(rowCount)+"\" required = \"required\" value='"+dblTotPrice+"'/>";
	    row.insertCell(10).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblQtyRej\" size=\"3%\" style=\"text-align: right;\" id=\"txtQtyReject."+(rowCount)+"\" value='"+dblQtyReject+"' required = \"required\" onblur=\"Javacsript:funAcceptQty(this)\">";
	    row.insertCell(11).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblAcceptQty\" size=\"3%\" style=\"text-align: right;\" id=\"txtAcceptQty."+(rowCount)+"\" value='"+dblAcceptQty+"' required = \"required\" onblur=\"Javacsript:funAcceptQty(this)\">";
	    row.insertCell(12).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblDiff\" size=\"3%\" class=\"Box\" id=\"txtDiff."+(rowCount)+"\" value='"+dblDiff+"'/>";
	    row.insertCell(13).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblDiffPer\" size=\"3%\" class=\"Box\" id=\"txtDiffPer."+(rowCount)+"\"s value='"+dblDiffPer+"'/>";
	    row.insertCell(14).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblScrap\" size=\"4%\" style=\"text-align: right;\" id=\"txtScrapReturned."+(rowCount)+"\" value='"+strScrapReturned+"'/>";
	    row.insertCell(15).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].strRemarks\" size=\"7%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
	    row.insertCell(16).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblWtReceivable\" size=\"4%\" class=\"Box\" id=\"txtWtReceivable."+(rowCount)+"\" value='"+dblWtReceivable+"'/>";
	    row.insertCell(17).innerHTML= '<input  class="deletebutton" size=\"2%\" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';	
	    
// 	    funClearProduct();
	    funGetTotal();
	    $("#txtProdCode").focus();
	    return false;
	}
	
	function funAcceptQty(object)
	{
		var index=object.parentNode.parentNode.rowIndex;
		var Qty=document.getElementById("txtDCQty."+index).value;
		var QtyRecived=document.getElementById("txtQtyReceived."+index).value;
		var QtyRej = document.getElementById("txtQtyReject."+index).value;
		
		acceptQty=document.getElementById("txtAcceptQty."+index).value; ;
		document.getElementById("txtAcceptQty."+index).value=parseFloat(acceptQty);
		
		var qtyAccept = document.getElementById("txtAcceptQty."+index).value;
		var diff =(parseFloat(Qty)- parseFloat(qtyAccept));
		document.getElementById("txtDiff."+index).value=parseFloat(diff);
		
		var diffPer = (parseFloat(diff)*100)/(parseFloat(Qty));
		document.getElementById("txtDiffPer."+index).value=parseFloat(diffPer);
		var unitPrice = document.getElementById("txtUnitPrice."+index).value;
		document.getElementById("txtTotPrice."+index).value = parseFloat(qtyAccept)*parseFloat(unitPrice);
		var unitWt = document.getElementById("txtWtUnit."+index).value; 
		document.getElementById("txtTotWt."+index).value = parseFloat(qtyAccept)*parseFloat(unitWt);
		
		
		funGetTotal();
		
	}
	
	function funShowFieled()
	{
		funRemoveAllRows();
		var agianst = $("#cmbAgainst").val();
		if(agianst=="Delivery Note")
			{
			document.all["txtSCDCCode"].style.display = 'block';
			
			}else
				{
				document.all["txtSCDCCode"].style.display = 'none';
				}
	}
	
	function funGetTotal()
	{
		
		var totPrice=0.00;
		var totWeight=0.00;
		var totQty=0.00;
		
		$('#tblProdDet tr').each(function() {
		    var totalPrice = $(this).find(".totalValueCell").val();
		    var	totalWeight = $(this).find(".totalValueWt").val();
		    var totalQty = $(this).find(".totalValuePrice").val();
		    
		    totPrice=parseFloat(totPrice)+parseFloat(totalPrice);
		    totWeight=parseFloat(totWeight)+parseFloat(totalWeight);
		    totQty=parseFloat(totQty)+parseFloat(totalQty);
		    
		 });
		
		$("#txtTotQty").val(totPrice);
		$("#txtTotWt").val(totWeight);
		$("#txtTotAmt").val(totQty);
		
	}
	
	function funDeleteRow(obj) 
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblProdDet");
	    table.deleteRow(index);
	}
	


	function funHelp(transactionName)
	{
		fieldName=transactionName;
		if(transactionName === "SCDNCode"){
			var subconCode = $("#txtSCCode").val();
			if(subconCode.trim().length >0)
			{
			//	window.showModalDialog("searchform.html?formname="+transactionName+"&strSubConCode="+subconCode+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
				window.open("searchform.html?formname="+transactionName+"&strSubConCode="+subconCode+"&searchText=","","dialogHeight:600px;dialogWidth:600px;top=500,left=500");
			}else{
				alert("Please Select Sub-Contractor ");
				$('#txtSCCode').focus()
			}
		}else{
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;top=500,left=500");
		}
	}
	
	
	function funSetSubContractorGRNData(code)
	{
		gurl=getContextPath()+"/loadSubContractorGRNData.html?docCode="+code;
		$.ajax({
	        type: "GET",
	        url: gurl,
	        dataType: "json",
	        success: function(response)
	        {		        	
	        		if('Invalid Code' == response.strSRCode){
	        			alert("Invalid SubContractorGRND Code");
	        			$("#txtSRCode").val('');
	        			$("#txtSRCode").focus();
	        			
	        		}else{	
	        			funRemoveAllRows();
	        			$('#txtSRCode').val(response.strSRCode);
	        			$('#txtNo').val(response.strSRNo);
	        			$('#txtSRDate').val(response.dteSRDate);
	        			$('#txtSCCode').val(response.strSCCode);
	        			$('#lblSCName').text(response.strSuppName);
	        			$('#txtSCDCCode').val(response.strSCDCCode);
	        			$('#txtSCDCDate').val(response.dteSCDCDate);
	        			$('#cmbAgainst').val(response.strAgainst);
	        			$('#txtCode').val(response.strNo);
	        			$('#txtLocCode').val(response.strLocCode);
	        			$('#lblLocName').text(response.strLocName);
	        			$('#txtVehNo').val(response.strVehNo);
	        			$('#txtInRefNo').val(response.strInRefNo);
	        			$('#txtInRefDate').val(response.dteInRefDate);
	        			$('#txtTotQty').val(response.dblTotQty);
	        			$('#txtTotWt').val(response.dblTotWt);
	        			$('#txtTotAmt').val(response.dblTotAmt);
	        			$('#txtJWCode').val(response.strJWCode);
	        			$('#cmbVerRemark').val(response.strVerRemark);
	        			$('#cmbPartDel').val(response.strPartDel);
	        			$('#txtDispAction').val(response.strDispAction);
	        			$('#txtTimeInOut').val(response.strTimeInOut);
	        			$('#txtMInBy').val(response.strMInBy);
	        			
	        			$.each(response.listSCGRNDtl, function(i,item)
		       	       	    	 {
		       	        			
	        						funfillSCGRNDataRow(response.listSCGRNDtl[i]);
		       	       	    	                                           
		       	       	    	 });
	        			
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
 	
	
	
	function funfillSCGRNDataRow(SCGRNDtl)
	{
		var table = document.getElementById("tblProdDet");
		
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    var strProdCode = SCGRNDtl.strProdCode;
		var strProdName=SCGRNDtl.strProdName;
		
		
		 var dblQtyReceivable = SCGRNDtl.dblQtyRbl;
		    parseFloat(dblQtyReceivable).toFixed(maxQuantityDecimalPlaceLimit);
		                                 
		    var dblDCQty =SCGRNDtl.dblDCQty ;
		    
		    var dblDCWt  =SCGRNDtl.dblDCWt;
		    
		    var dblQtyReceived =SCGRNDtl.dblAcceptQty ;
		    
		    var dblWtUnit =SCGRNDtl.dblWeight ;
		    
		    var dblTotWt =dblWtUnit*dblQtyReceivable ;
		    
		    var dblUnitPrice =SCGRNDtl.dblPrice ;
		    
		    var dblTotPrice=dblUnitPrice*dblQtyReceived;
		    
		    var dblQtyReject =SCGRNDtl.dblQtyRej ;
		    
		    var dblAcceptQty=SCGRNDtl.dblAcceptQty;
		    
		    var dblDiff =(parseFloat(dblDCQty)- parseFloat(dblQtyReceived));
		    
		    var dblDiffPer = (parseFloat(dblDiff)*100)/(parseFloat(dblQtyReceived));
		    
		    var strScrapReturned =SCGRNDtl.dblScrap ;
		    
		    var strRemarks =SCGRNDtl.strRemarks;
		    
		    var dblWtReceivable=dblQtyReceivable*dblWtUnit;
		
// 	    var dblQtyReceivable = SCGRNDtl.dblQtyRbl;
// 	    parseFloat(dblQtyReceivable).toFixed(maxQuantityDecimalPlaceLimit);
	                                 
// 	    var dblDCQty =SCGRNDtl.dblDCQty ;
	    
// 	    var dblDCWt  =SCGRNDtl.dblDCWt ;
	    
// 	    var dblQtyReceived =SCGRNDtl.dblQtyReceived ;
	    
// 	    var dblWtUnit =SCGRNDtl.dblWeight ;
	    
// 	    var dblTotWt =SCGRNDtl.dblTotWt ;
	    
// 	    var dblUnitPrice =SCGRNDtl.dblPrice ;
	    
// 	    var dblTotPrice=dblUnitPrice*dblQtyReceived;
	    
// 	    var dblQtyReject =SCGRNDtl.dblQtyRej ;
	    
// 	    var dblAcceptQty=dblQtyReceived-dblQtyReject;
	    
// 	    var dblDiff="0.00";
	    
// 	    var dblDiffPer="0.00";
	    
// 	    var strScrapReturned =SCGRNDtl.dblScrap ;
	    
// 	    var strRemarks =SCGRNDtl. strRemarks;
	    
// 	    var dblWtReceivable=dblQtyReceivable*dblWtUnit;
	    
		row.insertCell(0).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].strProdCode\" type=\"text\"  required = \"required\" style=\"text-align: right;\" \size=\"7%\" class=\"Box\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";
	    
	    row.insertCell(1).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].strProdName\" type=\"text\" required =\"required\" style=\"text-align: left;\" size=\"29%\" class=\"Box\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"' />";	    
	    
	    row.insertCell(2).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblQtyRbl\" readonly=\"readonly\" class=\"Box totalValueCell\" style=\"text-align: right;\" size=\"4%\" id=\"txtQtyReceivable."+(rowCount)+"\" value='"+dblQtyReceivable+"' />";
	    row.insertCell(3).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblDCQty\" size=\"3%\" class=\"decimal-places-amt inputText-Auto\" id=\"txtDCQty."+(rowCount)+"\" value='"+dblDCQty+"' onblur=\"Javacsript:funAcceptQty(this)\">";
	    
	    row.insertCell(4).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblDCWt\" size=\"3%\" style=\"text-align: right;\" id=\"txtDCWt."+(rowCount)+"\" value='"+dblDCWt+"'/>";
	    row.insertCell(5).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblQtyReceived\" size=\"3%\" style=\"text-align: right;\"  id=\"txtQtyReceived."+(rowCount)+"\" value='"+dblQtyReceived+"' onblur=\"Javacsript:funAcceptQty(this)\">";
	    row.insertCell(6).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblWeight\" size=\"3%\" style=\"text-align: right;\" id=\"txtWtUnit."+(rowCount)+"\" value='"+dblWtUnit+"' onblur=\"Javacsript:funAcceptQty(this)\">";
	    row.insertCell(7).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblTotWt\" size=\"3%\" style=\"text-align: right;\" class=\"Box totalValueWt\" id=\"txtTotWt."+(rowCount)+"\" value='"+dblTotWt+"'/>";
	    row.insertCell(8).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblPrice\" size=\"7%\" style=\"text-align: right;\" id=\"txtUnitPrice."+(rowCount)+"\" value='"+dblUnitPrice+"' onblur=\"Javacsript:funAcceptQty(this)\">";
	    row.insertCell(9).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblTotPrice\" size=\"3%\" style=\"text-align: right;\" class=\"Box totalValuePrice\" id=\"txtTotPrice."+(rowCount)+"\" value='"+dblTotPrice+"'/>";
	    row.insertCell(10).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblQtyRej\" size=\"3%\" style=\"text-align: right;\" id=\"txtQtyReject."+(rowCount)+"\" value='"+dblQtyReject+"'onblur=\"Javacsript:funAcceptQty(this)\">";
	    row.insertCell(11).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblAcceptQty\" size=\"3%\" style=\"text-align: right;\" id=\"txtAcceptQty."+(rowCount)+"\" value='"+dblAcceptQty+"' onblur=\"Javacsript:funAcceptQty(this)\">";
	    row.insertCell(12).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblDiff\" size=\"3%\" class=\"Box\" id=\"txtDiff."+(rowCount)+"\" value='"+dblDiff+"'/>";
	    row.insertCell(13).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblDiffPer\" size=\"3%\" class=\"Box\" id=\"txtDiffPer."+(rowCount)+"\" value='"+dblDiffPer+"'/>";
	    row.insertCell(14).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblScrap\" size=\"4%\" style=\"text-align: right;\" id=\"txtScrapReturned."+(rowCount)+"\" value='"+strScrapReturned+"'/>";
	    row.insertCell(15).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].strRemarks\" size=\"7%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
	    row.insertCell(16).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblWtReceivable\" size=\"4%\" class=\"Box\" id=\"txtWtReceivable."+(rowCount)+"\" value='"+dblWtReceivable+"'/>";
	    row.insertCell(17).innerHTML= '<input  class="deletebutton" size=\"2%\" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';	
	    
	    
	   
// 	    funClearProduct();
	    funGetTotal();
	    $("#txtProdCode").focus();
	    return false;
	}
	
	function funRemoveAllRows() 
    {
		 var table = document.getElementById("tblProdDet");
		 var rowCount = table.rows.length;			   
		//alert("rowCount\t"+rowCount);
		for(var i=rowCount-1;i>=0;i--)
		{
			table.deleteRow(i);						
		} 
    }
	
	function funCallFormAction(actionName,object) 
	{
		var flg=true;
		var table = document.getElementById("tblProdDet");
		var rowCount = table.rows.length;	
		
		if ($("#txtSODate").val()=="") 
		    {
			 alert('Invalid Date');
			 $("#txtSODate").focus();
			 return false;  
		   }
		
		 if($("#txtSCCode").val()=="")
			{
				alert("Please Enter Sub Contractor Code");
				$("#txtSCCode").focus();
				return false;
			}
		 if ($("#txtCPODate").val()=="") 
		    {
			 alert('Invalid Date');
			 $("#txtCPODate").focus();
			 return false;  
		   }
	  if($("#txtLocCode").val()=="")
		{
			alert("Please Enter LocationCode");
			$("#txtLocCode").focus();
			return false;
		}
	  if ($("#txtFulmtDate").val()=="") 
	    {
		 alert('Invalid Date');
		 $("#txtFulmtDate").focus();
		 return false;  
	   }
	  if(rowCount<1)
		{
			alert("Please Add Product in Grid");
			return false;
		}
	  
	  else
		{
			return true;
			
		}
	}
	
	
	
	function funFillDNRow(DNData)
	{
		
		var table = document.getElementById("tblProdDet");
		
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    var strProdCode = DNData.strProdCode;
		var strProdName=DNData.strProdName;
		
	    var dblQtyReceivable = DNData.dblQtyRbl;
	    parseFloat(dblQtyReceivable).toFixed(maxQuantityDecimalPlaceLimit);
	                                 
	    var dblDCQty =DNData.dblDCQty ;
	    
	    var dblDCWt  =DNData.dblDCWt;
	    
	    var dblQtyReceived =DNData.dblAcceptQty ;
	    
	    var dblWtUnit =DNData.dblWeight ;
	    
	    var dblTotWt =dblWtUnit*dblQtyReceivable ;
	    
	    var dblUnitPrice =DNData.dblPrice ;
	    
	    var dblTotPrice=dblUnitPrice*dblQtyReceived;
	    
	    var dblQtyReject =DNData.dblQtyRej ;
	    
	    var dblAcceptQty=DNData.dblAcceptQty;
	    
	    var dblDiff=DNData.dblDiff;
	    
	    var dblDiffPer=DNData.dblDiffPer;
	    
	    var strScrapReturned =DNData.dblScrap ;
	    
	    var strRemarks =DNData.strRemarks;
	    
	    var dblWtReceivable=dblQtyReceivable*dblWtUnit;
	    
		row.insertCell(0).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].strProdCode\" type=\"text\"  required = \"required\" style=\"text-align: right;\" \size=\"7%\" class=\"Box\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";
	    
	    row.insertCell(1).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].strProdName\" type=\"text\" required =\"required\" style=\"text-align: left;\" size=\"29%\" class=\"Box\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"' />";	    
	    
	    row.insertCell(2).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblQtyRbl\" readonly=\"readonly\" class=\"Box totalValueCell\" style=\"text-align: right;\" size=\"4%\" id=\"txtQtyReceivable."+(rowCount)+"\" value='"+dblQtyReceivable+"' />";
	    row.insertCell(3).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblDCQty\" size=\"3%\" class=\"decimal-places-amt inputText-Auto\" id=\"txtDCQty."+(rowCount)+"\" value='"+dblDCQty+"' onblur=\"Javacsript:funAcceptQty(this)\">";
	    
	    row.insertCell(4).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblDCWt\" size=\"3%\" style=\"text-align: right;\" id=\"txtDCWt."+(rowCount)+"\" value='"+dblDCWt+"'/>";
	    row.insertCell(5).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblQtyReceived\" size=\"3%\" style=\"text-align: right;\"  id=\"txtQtyReceived."+(rowCount)+"\" value='"+dblQtyReceived+"' onblur=\"Javacsript:funAcceptQty(this)\">";
	    row.insertCell(6).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblWeight\" size=\"3%\" style=\"text-align: right;\" id=\"txtWtUnit."+(rowCount)+"\" value='"+dblWtUnit+"' onblur=\"Javacsript:funAcceptQty(this)\">";
	    row.insertCell(7).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblTotWt\" size=\"3%\" style=\"text-align: right;\" class=\"Box totalValueWt\" id=\"txtTotWt."+(rowCount)+"\" value='"+dblTotWt+"'/>";
	    row.insertCell(8).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblPrice\" size=\"7%\" style=\"text-align: right;\" id=\"txtUnitPrice."+(rowCount)+"\" value='"+dblUnitPrice+"' onblur=\"Javacsript:funAcceptQty(this)\">";
	    row.insertCell(9).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblTotPrice\" size=\"3%\" style=\"text-align: right;\" class=\"Box totalValuePrice\" id=\"txtTotPrice."+(rowCount)+"\" value='"+dblTotPrice+"'/>";
	    row.insertCell(10).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblQtyRej\" size=\"3%\" style=\"text-align: right;\" id=\"txtQtyReject."+(rowCount)+"\" value='"+dblQtyReject+"'onblur=\"Javacsript:funAcceptQty(this)\">";
	    row.insertCell(11).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblAcceptQty\" size=\"3%\" style=\"text-align: right;\" id=\"txtAcceptQty."+(rowCount)+"\" value='"+dblAcceptQty+"' onblur=\"Javacsript:funAcceptQty(this)\">";
	    row.insertCell(12).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblDiff\" size=\"3%\" class=\"Box\" id=\"txtDiff."+(rowCount)+"\" value='"+dblDiff+"'/>";
	    row.insertCell(13).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblDiffPer\" size=\"3%\" class=\"Box\" id=\"txtDiffPer."+(rowCount)+"\" value='"+dblDiffPer+"'/>";
	    row.insertCell(14).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblScrap\" size=\"4%\" style=\"text-align: right;\" id=\"txtScrapReturned."+(rowCount)+"\" value='"+strScrapReturned+"'/>";
	    row.insertCell(15).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].strRemarks\" size=\"7%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
	    row.insertCell(16).innerHTML= "<input name=\"listSCGRNDtl["+(rowCount)+"].dblWtReceivable\" size=\"4%\" class=\"Box\" id=\"txtWtReceivable."+(rowCount)+"\" value='"+dblWtReceivable+"'/>";
	    row.insertCell(17).innerHTML= '<input  class="deletebutton" size=\"2%\" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';	
	    
	    
	    
// 	    funClearProduct();
	    funGetTotal();
	    $("#txtProdCode").focus();
	    return false;
	}
	
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>Sub Contractor GRN</label>
	</div>

<br/>
<br/>

	<s:form name="SubContractorGRN" method="POST" action="saveSubContractorGRN.html">
	<input type="hidden" value="${urlHits}" name="saddr">

	
		<table class="transTable">
		
		<tr>
									<td><label>SC Return Code</label></td>
									<td ><s:input path="strSRCode" id="txtSRCode"
											ondblclick="funHelp('SCGRNCode')"
											cssClass="searchTextBox" /></td>
											<td><s:input colspan="3" type="text" class="BoxW116px"
												id="txtNo" path="strSRNo" cssClass="BoxW124px" /></td>
									
									<td ><label>SR Date</label>
									<td colspan="2"><s:input path="dteSRDate" id="txtSRDate"
											 required="required"
											cssClass="calenderTextBox" /></td>
											
								</tr>
								<tr>
									<td ><label>SC Code</label></td>
									<td ><s:input path="strSCCode" id="txtSCCode"
											ondblclick="funHelp('subContractor')"
											cssClass="searchTextBox" /></td>
										<td colspan="4"><label id="lblSCName"
										class="namelabel"></label></td>	
								</tr>
								<tr>
									<td><label>Against</label></td>
									<td>
										<s:select id="cmbAgainst" path="strAgainst" items="${againstList}" cssClass="BoxW124px" onchange="funShowFieled()" />
									</td>
									<td colspan="2">
										<s:input id="txtSCDCCode" path="strSCDNCode" style="display:none" ondblclick="funHelp('SCDNCode')" class="searchTextBox" />
									</td>		
									
									<td ><label>SC DN Date</label>
										<td>
											<s:input path="dteSCDCDate" id="txtSCDCDate" cssClass="calenderTextBox" />
										</td>
								</tr>
								<tr>
								<td><label>Location Code</label></td>
									<td><s:input type="text" id="txtLocCode" path="strLocCode"
											cssClass="searchTextBox" ondblclick="funHelp('locationmaster');" /></td>
									<td><label id="lblLocName"></label></td>
									
								<td><label>Vechile No</label></td>
									<td colspan="8"><s:input id="txtVehNo" type="text" path="strVehNo"
											class="BoxW116px" /></td>	
								
								</tr>
								<tr>
								<td><label>Inward Ref No</label></td>
									<td ><s:input id="txtInRefNo" type="text" path="strInRefNo"
											class="BoxW116px" /></td>	
											
								<td ><label>Inward Ref Date</label>
									<td colspan="3"> <s:input path="dteInRefDate" id="txtInRefDate"
											cssClass="calenderTextBox" /></td>	
								
								</tr>
								
								<tr>
									<td ><label>Product</label></td>
									<td ><input id="txtProdCode"
										ondblclick="funHelp('productmaster')" class="searchTextBox" /></td>
									<td align="left" colspan="5"><label id="lblProdName"
										class="namelabel"></label></td>

								</tr>
								<tr>
									<td><label>Unit Price</label></td>
									<td><input id="txtPrice" type="text"
										class="decimal-places numberField" /></td>
									<td><label>Wt/Unit</label></td>
									<td ><input type="text" id="txtWeight"
										class="decimal-places numberField" /></td>
									
									<td ><label>Qty Recived</label></td>
									<td ><input id="txtQtyRecived"
										type="text" class="decimal-places numberField" style="width:60%"/></td>
									
									
								</tr>
								
								<tr>
								
								<td ><label>Qty Receiveable</label></td>
									<td ><input id="txtQtyReceiveable"
										type="text" class="decimal-places numberField" style="width:60%"/></td>
										
									<td width="100px"><label>Qty Reject</label></td>
									<td width="150px"><input id="txtQtyReject"
										type="text" class="decimal-places numberField" style="width:60%"/></td>	
									
								
								<td><label>Wt Receivable</label></td>
									<td ><input type="text" id="txtWtReceivable"
										class="decimal-places numberField" /></td>
								
										
														
								</tr>
								<tr>
								
								<td ><label>Qty DN</label></td>
									<td width="150px"><input id="txtQtyDC"
										type="text" class="decimal-places numberField" style="width:60%" /></td>
								
								<td ><label>DN Wt</label></td>
									<td ><input id="txtDCWt"
										type="text" class="decimal-places numberField" style="width:60%"/></td>
								
											
								
								<td ><label>Scrap Returned</label></td>
									<td ><input id="txtScrapReturned"
										type="text" class="decimal-places numberField" style="width:60%"/></td>
								<tr>
								
									<td><label>Weight</label></td>
									<td ><input id="txtInRefNo" type="text" 
											class="BoxW116px" /></td>	
								
								<td><label>Remarks</label></td>
								<td><input id="txtRemarks" class="longTextBox" style="width:100%" /></td>
									<td colspan="2"><input type="button" value="Add" class="smallButton"
										onclick="return btnAdd_onclick()" /></td>
								</tr>
								
								
		</table>
		
		
		
		<div class="dynamicTableContainer" style="height: 300px; width: 964px;">
								<table
									style="height: 20px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold; width: 140%">
									
										<tr bgcolor="#72BEFC">
										
<!-- 										<td colspan="7" style="text-align: center ;" >Delivery Note Details</td> -->
										
										
										<!--  COl7   -->
										<td width="5%" rowspan="2">Prod Code</td>
										<!--  COl8   -->
										<td width="10%" rowspan="2">Prod Name</td>
										<!--  COl9   -->
										<td width="4%" rowspan="2">Qty <br>Receivable</td>
										<!--  COl10   -->
										<td width="3%" rowspan="2">DN Qty</td>
										<!--  COl11   -->
										<td width="3%" rowspan="2">DN Wt</td>
										<!--  COl12   -->
										<td width="3%" rowspan="2">Qty Received</td>
										<!--  COl13   -->
										<td width="3%" rowspan="2">Wt/ Unit</td>
										<!--  COl14   -->
										<td width="3%" rowspan="2">Total Wt</td>
										<!--  COl15   -->
										<td width="4%" rowspan="2">Unit Price</td>
										<!--  COl16   -->
										<td width="3%" rowspan="2">Total Price</td>
										<!--  COl17   -->
										<td width="3%" rowspan="2">Qty Reject</td>
										<!--  COl18   -->
										<td width="3%" rowspan="2">Qty Accept</td>
										<!--  COl19   -->
										<td width="3%" rowspan="2">Difference</td>
										<!--  COl20   -->
										<td width="3%" rowspan="2">Diff %</td>
										<!--  COl21   -->
										<td width="3%" rowspan="2">Scrap Returned</td>
										<!--  COl22   -->
										<td width="7%" rowspan="2">Remarks</td>
										<!--  COl23   -->
										<td width="4%" rowspan="2">Wt Receivable</td>
										<!--  COl24   -->
										<td width="3%" rowspan="2">Delete</td>
										
<!-- <!-- 										<td colspan="17"></td></tr> --> 
<!-- 										<tr bgcolor="#72BEFC"> -->
<!-- 											<td width="5%">DN Code</td> -->
<!-- 											 COl1   -->
<!-- 											<td width="5%">Product Code</td> -->
<!-- 											 COl2   -->
<!-- 											<td width="10%">Name</td> -->
<!-- 											 COl3  										 -->
<!-- 											<td width="5%">Process</td> -->
<!-- 											 COl4   -->
<!-- 											<td width="4%">Qty</td> -->
<!-- 											 COl5   -->
<!-- 											<td width="4%">Weight</td> -->
<!-- 											 COl6   -->
										
									</tr>
								</table>
								<div
									style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%; width: 140%">
									<table id="tblProdDet"
										style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll; width: 100%"
										class="transTablex col15-center">
										<tbody>
										<col style="width: 4%">
										<!--  COl0   -->
										<col style="width: 7.5%">
										<!--  COl1   -->
										<col style="width: 3.5%">										
										<!--  COl2   -->
										<col style="width: 2.5%">
										<!--  COl3   -->
										<col style="width: 2.5%">
										<!--  COl4   -->
										<col style="width: 3%">
										<!--  COl5   -->
										<col style="width: 2.5%">
										<!--  COl6   -->
										<col style="width: 2.5%">
										<!--  COl7  -->
										<col style="width: 3.2%">
										<!--  COl8  -->
										<col style="width: 2.5%"> 
										<!--  COl9   -->
										<col style="width: 2.5%">
										<!--  COl10   -->
										<col style="width: 2.5%">
										<!--  COl11   -->
										<col style="width: 3%">
										<!--  COl12   -->
										<col style="width: 3%">
										<!--  COl13   -->
										<col style="width: 2.5%">
										<!--  COl14   -->
										<col style="width: 5%">
										<!--  COl15   -->
										<col style="width: 3.5%">
										<!--  COl16   -->
										<col style="width: 2%">
										<!--  COl17   -->
<%-- 										<col style="width: 3%"> --%>
<!-- 										 COl19   -->
<%-- 										<col style="width: 3%"> --%>
<!-- 										 COl20   -->
<%-- 										<col style="width: 3%"> --%>
<!-- 										 COl21   -->
<%-- 										<col style="width: 4%"> --%>
<!-- 										 COl22   -->
<%-- 										<col style="width: 7%"> --%>
<!-- 										 COl23  -->
<%-- 										<col style="width: 4%"> --%>
<!-- 										 COl24  -->
										
										
										</tbody>

									</table>
								</div>

							</div>
				<br>
				
					<table class="transTable">
						<tr>
							<td ><label>Total Quanty</label></td>
										
										<td><s:input type="text" id="txtTotQty"
												path="dblTotQty" readonly="true"
												cssClass="decimal-places-amt numberField" /></td>
										
						<td><label>Total Weight</label></td>
										
										<td><s:input type="text" id="txtTotWt"
												path="dblTotWt" readonly="true"
												cssClass="decimal-places-amt numberField" /></td>
									
						
							<td><label>Total Amount</label></td>
									
										<td><s:input type="text" id="txtTotAmt"
												path="dblTotAmt" readonly="true"
												cssClass="decimal-places-amt numberField" /></td>
						<%-- 	<td><label>Job Work</label></td>
										<td><s:input type="text" id="txtJWCode" path="strJWCode"
												cssClass="searchTextBox" ondblclick="funHelp('JOCode');" /></td>
										<td><label id="lblJWName"></label></td>	 --%>
										<td colspan="4"></td>
						</tr>
						
						<tr>	
						
						<td><label>Verification Remark</label></td>
										<td><s:select id="cmbVerRemark" name="cmbVerRemark"
												path="strVerRemark" cssClass="BoxW124px">
												<option value="Accept">Accept</option>
												<option value="Accept with Devistion">Accept with Devistion</option>
												<option value="Send For Rectification">Send For Rectification</option>
												<option value="Accept after Rectification">Accept after Rectification</option>
												<option value="Under Inspection/Accept">Under Inspection/Accept</option>
											
											
											</s:select></td>
						
							
							
							<td ><label>Party Delivery</label></td>
										<td><s:select id="cmbPartDel" name="cmbPartDel"
												path="strPartDel" cssClass="BoxW124px">
												<option value="N">No</option>
												<option value="Y">Yes</option>
											</s:select></td>	
							
							<td ><label>Disposal Action</label></td>
	 							<td colspan="7"><s:textarea id="txtDispAction" cols="50" rows="3" 
	 									path="strDispAction"  
										style="width: 85%" /></td>
							
							</tr>				
						
						
						<tr>
						<td><label>Time In</label></td>
											<td ><s:input id="txtTimeInOut" path="strTimeInOut" type="text" 
												class="BoxW116px" /></td>	
						<td colspan="2">Matrial brought in by</td>
						<td colspan="7" ><s:input id="txtMInBy" path="strMInBy" type="text" 
												class="BoxW116px" /></td>
						
						</tr>
										
					</table>
	
		

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3"  class="form_button" 
			onclick="return funCallFormAction('submit',this)" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
		<br/>
		<br/>

	</s:form>
</body>
</html>
					