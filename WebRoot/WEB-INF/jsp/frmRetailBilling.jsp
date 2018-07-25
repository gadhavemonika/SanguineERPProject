<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sales Order</title>

<script type="text/javascript">

$(document).ready(function() {
	var sgData ;
	var prodData;
	$("#txtLocCode").val("${locationCode}");
	$("#lblLocName").text("${locationName}"); 
//	document.getElementById('txtCustCode').focus();	
//	$("#txtCustCode").val('C000001');
	$("#txtCustCode").focus();
	
	$(".tab_content").hide();
	$(".tab_content:first").show();

	$("ul.tabs li").click(function() {
		$("ul.tabs li").removeClass("active");
		$(this).addClass("active");
		$(".tab_content").hide();
		var activeTab = $(this).attr("data-state");
		$("#" + activeTab).fadeIn();
	});
	
	var dayEndDate='<%=session.getAttribute("dayEndDate").toString()%>';
		$("#txtInvDate").val(dayEndDate.split("-")[2]+"-"+dayEndDate.split("-")[1]+"-"+dayEndDate.split("-")[0]);
		$("#txtExpriyDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtExpriyDate" ).datepicker('setDate', 'today');
	 /* $("#txtInvDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtInvDate" ).datepicker('setDate', 'today');
		$("#txtInvDate").datepicker(); */
		
	/* 	$('#txtCustCode').blur(function () {
			var code=$("#txtCustCode").val();;
			if (code.trim().length > 0 && code !="?" && code !="/"){					   
				funSetCuster(code);
				
			   }
		}); */
	
	var message='';
	var invoiceformat='';
	<%if (session.getAttribute("success") != null) {
		            if(session.getAttribute("successMessage") != null){%>
		            message='<%=session.getAttribute("rptInvCode").toString()%>';
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
	
	
	<%if(null!=session.getAttribute("rptInvCode")){%>
	
	code='<%=session.getAttribute("rptInvCode").toString()%>';
<%-- 							dccode='<%=session.getAttribute("rptDcCode").toString()%>'; --%>
	dccode='';
	<%if(null!=session.getAttribute("rptInvDate")){%>
	invDate='<%=session.getAttribute("rptInvDate").toString()%>';
	invoiceformat='<%=session.getAttribute("invoieFormat").toString()%>';
<%-- 							invoiceformat='<%=session.getAttribute("invoieFormat").toString()%>'; --%>
	<%session.removeAttribute("rptInvCode");%>
	<%session.removeAttribute("rptInvDate");%>
	<%session.removeAttribute("rptDcCode");%>
	var isOk=confirm("Do You Want to Generate Slip?");
	if(isOk){
		
	/* 	if(invoiceformat=="Format 1")
			{
			window.open(getContextPath()+"/openRptInvoiceSlip.html?rptInvCode="+code,'_blank');
			window.open(getContextPath()+"/openRptInvoiceProductSlip.html?rptInvCode="+code+"&rptInvDate="+invDate,'_blank');
			window.open(getContextPath()+"/rptTradingInvoiceSlip.html?rptInvCode="+code+"&rptInvDate="+invDate,'_blank');
		}
	else if(invoiceformat=="Format 2")
		{
		window.open(getContextPath()+"/rptInvoiceSlipFromat2.html?rptInvCode="+code+"&rptInvDate="+invDate,'_blank');
		window.open(getContextPath()+"/rptInvoiceSlipNonExcisableFromat2.html?rptInvCode="+code+"&rptInvDate="+invDate,'_blank');
		window.open(getContextPath()+"/rptDeliveryChallanInvoiceSlip.html?strDocCode="+dccode,'_blank');
		}
	
	else if(invoiceformat == 'Format 3 Inv Ret'){
		window.open(getContextPath()+"/openRptInvoiceRetailReport.html?rptInvCode="+code,'_blank');
	    }
	else if(invoiceformat == 'Format 4 Inv Ret'){ */
		
		
		if(invoiceformat=="RetailNonGSTA4"){
			window.open(getContextPath()+"/openRptInvoiceRetailNonGSTReport.html?rptInvCode="+code,'_blank');
		    }else
		    	{
		    	window.open(getContextPath()+"/opentxtInvoice.html?rptInvCode="+code,'_blank');
		    	}
		
		
	}
	
//	}
//		var isOk=confirm("Do You Want to Generate Product Detail Slip?");
//		if(isOk){
//			window.open(getContextPath()+"/openRptInvoiceProductSlip.html?rptInvCode="+code+"&rptInvDate="+invDate,'_blank');
//				}
	<%}%><%}%>
	
	
	
});
 
		
		$(document).keypress(function(e) {
		  if(e.keyCode == 120) {
			  if(funCallFormAction())
				  {
					document.forms["RetailForm"].submit();
				  }
			 
		  }
		});
		
		function funCallBarCodeProduct(code)
		{
				var locCode= $('#txtLocCode').val();
				var suppCode="";
				var billdate="";
				var searchUrl = getContextPath()+ "/loadProductDataWithTax.html?prodCode=" +code +"&locCode="+locCode+"&suppCode="+suppCode+"&billDate="+billDate;
				
				$.ajax({
						type : "GET",
						url : searchUrl,
						dataType : "json",
						success : function(response) {

							if ('Invalid Code' == response.strProdCode) {
								alert('Invalid Product Code');
								$("#hidProdCode").val('');
								$("#lblProdName").text('');
								$("#txtProdCode").focus();
							} else {
								
								$('#hidProdCode').val(response.strProdCode);
								$("#txtProdName").val(response.strProdName);
	 							$('#txtRate').val((parseFloat(response.dblUnitPrice)/parseFloat(currValue)).toFixed(maxQuantityDecimalPlaceLimit));
	 							$('#txtMRP').val((parseFloat(response.dblUnitPrice)/parseFloat(currValue)).toFixed(maxQuantityDecimalPlaceLimit));
// 	 							$('#txtMRP').val((parseFloat(response.dblMRP)/parseFloat(currValue)).toFixed(maxQuantityDecimalPlaceLimit));
	 							$('#lblUOM').text(response.strUOM);
	 							 
	 							$('#hidPreInvPrice').val((parseFloat(response.prevUnitPrice)/parseFloat(currValue)).toFixed(maxQuantityDecimalPlaceLimit));
	 							$('#hidPrevInvCode').val(item.prevInvCode);
	 							
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


	function funCallFormAction()
		{
			var table = document.getElementById("tblProdDet");
		    var rowCount = table.rows.length;
		    
		    
		    var tableSettle = document.getElementById("tblSettleDet");
		    var rowCountSettle = tableSettle.rows.length;
		    
		    if($("#txtCustCode").val().trim().length==0)
		    	{
			    	alert("Please Select Customer or Search");
					$("#txtCustCode").focus();
					return false;
		    	}
		    
		 	if($("#txtLocCode").val().trim().length==0)
			{
				alert("Please Enter Location Code or Search");
				$("#txtLocCode").focus();
				return false;
			}
		 	if(rowCount<1)
			{
				alert("Please Add Product in Grid");
				$("#txtProdCode").focus();
				return false;
			}
		 	
		 	if(rowCountSettle<1)
			{
				alert("Please Add Settlement in Grid");
				$("#tab2").focus();
				return false;
			}
		 	
		
		 	return true;
		}
	
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
	
	$("#txtPartyCode").focus();
});
		
		function funHelp(transactionName)
		{
			var sgCode = "";
			if(!($("#hidSubGroupCode").val().trim().length==0) && !($("#hidSubGroupCode").val()== 0))
				{
					sgCode = $("#hidSubGroupCode").val();
				}
			 
			
			fieldName = transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		window.open("searchform.html?formname="+transactionName+"&searchText="+sgCode,"","dialogHeight:600px;dialogWidth:600px;top=500,left=500")
			
		}

		 	function funSetData(code)
			{
				switch (fieldName)
				{
				
				 case 'locationmaster':
				    	funSetLocation(code);
				        break;
				        
				 case 'custMasterActive' :
				    	funSetCuster(code);
				    	break;  
				    	
				/*  case 'productmaster':
				    	funSetProduct(code);
				        break; */
				        
				 case 'invoiceRetail':
					 funSetInvoiceData(code);
				        break;  
				        
				 case 'productProduced' :        
					 funSetProduct(code);
					 break;
				        
				}
			}
		 	
		 	
		 	
		 	function funSetProduct(code)
			{
					var searchUrl="";
					var cust="";
					$('#cmbUOM').empty();
					searchUrl=getContextPath()+"/loadProductDataForRetailling.html?prodCode="+code+"&custCode="+cust;
					$.ajax({
					        type: "GET",
					        url: searchUrl,
						    dataType: "json",
						    success: function(response)
						    {
						    	if(response.strProdCode!="Invalid Product Code")
						    	{
						    		var currValue='<%=session.getAttribute("currValue").toString()%>';
						    								    	
						    		var dblStock=funGetProductStock(response.strProdCode);
							    	$("#spStock").text(dblStock);
									$("#hidProdCode").val(response.strProdCode);
									$("#txtProdName").val(response.strProdName);
// 									$("#txtRate").val((parseFloat(response.dblUnitPrice)/parseFloat(currValue)).toFixed(maxQuantityDecimalPlaceLimit));
// 									$("#txtMRP").val((response.dblMRP)/parseFloat(currValue).toFixed(maxQuantityDecimalPlaceLimit));
									
									$("#txtRate").val((response.dblUnitPrice).toFixed(maxQuantityDecimalPlaceLimit)/currValue);
// 									$("#txtMRP").val((response.dblMRP).toFixed(maxQuantityDecimalPlaceLimit));
									$("#txtMRP").val((response.dblUnitPrice).toFixed(maxQuantityDecimalPlaceLimit)/currValue);
									
									$("#cmbUOM").append('<option value='+response.dblReceiveConversion+'>'+response.strReceivedUOM+'</option><option value='+response.dblRecipeConversion+'>'+response.strRecipeUOM+'</option>');
									
									
									$("#txtQty").focus();
							     }
						    	else
						    		{
						    		  alert("Invalid Product Code");
						    		  $("#txtProdName").val('') 
						    		  $("#txtProdName").focus();
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

		 	
		 	function funSetCuster(code)
			{
				gurl=getContextPath()+"/loadPartyMasterData.html?partyCode=";
				$.ajax({
			        type: "GET",
			        url: gurl+code,
			        dataType: "json",
			        success: function(response)
			        {		        	
			        		if('Invalid Code' == response.strPCode){
			        			alert("Invalid Customer Code");
			        			$("#txtCustCode").val('');
			        			$("#txtCustCode").focus();
			        		}else{			   
			        			$("#txtCustCode").val(response.strPCode);
								$("#lblCustomerName").text(response.strPName);
								
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
		var spStock = $("#spStock").text();
		if($("#hidProdCode").val().length<=0)
			{
				$("#txtProdName").focus();
				alert("Please Enter Product Or Search");
				return false;
			}			
	    if($("#txtQty").val().trim().length==0 || $("#txtQty").val()== 0)
	        {		
	          alert("Please Enter Quantity");
	          $("#txtQty").focus();
	          return false;
	       } 
	    if(parseFloat(spStock)<=0 || parseFloat($("#txtQty").val())>parseFloat(spStock)  )
	    		{
	    			alert("Please Add Stock");
	    			return false;
	    		}
	    else{
	    	 var strProdCode=$("#hidProdCode").val();
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
	    var strProdCode = $("#hidProdCode").val().trim();
		var strProdName=$("#txtProdName").val();
		// var strUOM=$("#lblUOM").text();	
	    var dblQty = $("#txtQty").val();
	    parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	    var dblRate=$("#txtRate").val();
	    var dblAmount=dblQty*dblRate;
	    var dblMRP=$("#txtMRP").val();
	    
	    var strCustCode=$("#txtCustCode").val();
	    
	    var prevInvCode=$("#hidPrevInvCode").val();
	    var prevProdrice=$("#hidPreInvPrice").val();
	    
	    var dblUOMConversion= $("#cmbUOM").val();
	 	var strUOM= $("#cmbUOM option:selected").text();;
		
	    row.insertCell(0).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box\" size=\"6%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
	    row.insertCell(1).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"15%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
	    row.insertCell(2).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strUOM\" readonly=\"readonly\" class=\"Box\" size=\"6%\" id=\"txtUOM."+(rowCount)+"\" value='"+strUOM+"'/>";
	 
	    row.insertCell(3).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblQty\" type=\"text\"  required = \"required\" size=\"6%\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtQty."+(rowCount)+"\" value="+dblQty+" onblur=\"Javacsript:funUpdatePrice(this)\">";
	    row.insertCell(4).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblRate\" type=\"text\"  required = \"required\" size=\"6%\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtRate."+(rowCount)+"\" value="+dblRate+" >";
	    row.insertCell(5).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblMRP\" type=\"text\"  required = \"required\" size=\"6%\"  class=\"Box\" style=\"text-align: right;\"  id=\"txtMRP."+(rowCount)+"\" value="+dblMRP+" >";
	    row.insertCell(6).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblAmount\" readonly=\"readonly\" class=\"Box totalValueCell\" style=\"text-align: right;\" \size=\"9%\" id=\"txtAmount."+(rowCount)+"\"   value='"+dblAmount+"'/>";
	    row.insertCell(7).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';		    
	    row.insertCell(8).innerHTML= "<input type=\"hidden\" \size=\"0%\" name=\"listclsInvoiceModelDtl["+(rowCount)+"].strCustCode\"  value ="+strCustCode+" />";
	    row.insertCell(9).innerHTML= "<input readonly=\"readonly\" class=\"Box prevInvCode\" style=\"text-align: left;\" \size=\"11%\" id=\"prevInvCode."+(rowCount)+"\"   value='"+prevInvCode+"'/>";
	 	row.insertCell(10).innerHTML= "<input readonly=\"readonly\" class=\"Box prevProdrice\" style=\"text-align: right;\" \size=\"3.9%\" id=\"prevProdrice."+(rowCount)+"\"   value='"+prevProdrice+"'/>";
	 	row.insertCell(11).innerHTML= "<input type=\"hidden\" \size=\"0%\" name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblUOMConversion\"  value ="+dblUOMConversion+" />";
	 	
	    if($('#txtInvCode').val().length>0)
    	{
    		funDeleteAllRowSettle();
    	}
	 	
	 	$("#txtSubGroup").focus();
	    funClearProduct();
	    funGetTotal();
	    return false;
	}
	
	function funUpdatePrice(object)
	{
		var index=object.parentNode.parentNode.rowIndex;
		var Qty=document.getElementById("txtQty."+index).value;
		var Rate=document.getElementById("txtRate."+index).value;
		
		var ItemPrice;
		ItemPrice=(parseFloat(Qty)*parseFloat(Rate));
		
		document.getElementById("txtAmount."+index).value=parseFloat(ItemPrice);
	//	funGetTotal();
	}
	
	function funGetTotal()
	{
		
		var subtotal=0.00;
		var extraChange=0.00;
		var finalAmt=0.00;
		
		$('#tblProdDet tr').each(function() {
		    var totalPriceCell = $(this).find(".totalValueCell").val();
		    subtotal=parseFloat(subtotal)+parseFloat(totalPriceCell);
		 });
		
		$("#txtSubTotal").val(subtotal);
		$("#txtActualAmt").val(subtotal);
		$("#txtSettlementAmt").val(subtotal);
		
		
		var disc=$("#txtDisc").val();
		subtotal=parseFloat(subtotal)-parseFloat(disc);
		extraChange=$("#txtExtraCharges").val();
		subtotal=parseFloat(subtotal)+parseFloat(extraChange);
		$("#txtFinalAmt").val(subtotal);
		
		
		  
		
	}
	
	
		
	function funDeleteRow(obj) 
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblProdDet");
	    table.deleteRow(index);
	}

	function funClearProduct()
	{
		$("#txtProdName").val("");
		$("#hidProdCode").val("");
		$("#lblUOM").text("");
		$("#txtSubGroup").val("");
		$("#hidSubGroupCode").val("");
		$("#txtQty").val("");
		$("#txtRate").val("");
		$("#txtMRP").val("");
		 
	}
	

	function funResetFields()
	{
		location.reload(true); 
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
	

	
	function btnFill_onclick()
	{
		var code =$('#txtSOCode').val();
		if(code.toString.lenght!=0 || code==null)
			{
		
				gurl=getContextPath()+"/SalesOrderHdData.html?soCode="+code;
				$.ajax({
			        type: "GET",
			        url: gurl,
			        dataType: "json",
			        success: function(response)
			        {		        	
			        		if('Invalid Code' == response.strSOCode){
			        			alert("Invalid Customer Code");
			        			$("#txtSOCode").val('');
			        			$("#txtSOCode").focus();
			        			
			        		}else{	
			        			funRemoveAllRows();
// 			        			 $("#txtSOCode").val(response.strSOCode);
			        	    	 $("#txtCustCode").val(response.strCustCode);
			        	    
			        			 $("#lblCustomerName").text(response.strcustName);
			        			 $("#txtSODate").val(response.dteSODate);
			        			 $('#txtLocCode').val(response.strLocCode);
				        			$('#lblLocName').text(response.strLocName);
			        			
								
								$.each(response.listSODtl, function(i,item)
			       	       	    	 {
			       	        			
			       	       	    	    funfillSalesOrderDataRow(response.listSODtl[i]);
			       	       	    	                                           
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
		
	}
	
	

	$(document).ready(function()
		{
			$(function() {
				
				$("#txtSubGroup").autocomplete({
				source: function(request, response)
				{
					var searchUrl=getContextPath()+"/AutoCompletGetSubgroupName.html";
					$.ajax({
					url: searchUrl,
					type: "POST",
					data: { term: request.term },
					dataType: "json",
					 
						success: function(data) 
						{
							sgData=data;
							response($.map(data, function(v,i)
							{
							//	$('#hidSubGroupCode').val(   );
								return {
									label: v.strSGName,
									value: v.strSGName
									
									};
									
									
							}));
							
						}
					});
				},
				minLength: 0,
			    minChars: 0,
			    max: 12,
			    autoFill: true,
			    mustMatch: true,
			    matchContains: false,
			    scrollHeight: 220,
			}).on('focus', function(event) {
			    var self = this;
				  //  var sgName= $("#txtSubGroup").val();
				    $(self).autocomplete( "search", '');
				});
				 
				 
			});
		});
	
	$(document).ready(function()
			{
				$(function() {
					
					$("#txtProdName").autocomplete({
						source: function(request,response)
					 {
							var sgName= $("#txtSubGroup").val();
							var custCode=$("#txtCustCode").val();
							var searchUrl=getContextPath()+"/AutoCompletGetProdNamewithSubgroup.html";
							$.ajax({
							url: searchUrl,
							type: "POST",
							data: { term: request.term,sgName:sgName,custcode:custCode },
							dataType: "json",
							 
								success: function(data) 
								{
									prodData=data;
									response($.map(data, function(v,i)
									{
										return {
											label: v.strProdName+"                   "+v.strUOM+"                       "+v.dblCostRM,
											value: v.strProdName
											
											};
											
									}));
									
								}
							});
						},
						 	minLength: 0,
						    minChars: 0,
						    max: 12,
						    autoFill: true,
						    mustMatch: true,
						    matchContains: false,
						    scrollHeight: 220,
					
					}).on('focus', function(event) {
					    var self = this;
					  //  var sgName= $("#txtSubGroup").val();
					    $(self).autocomplete( "search", '');
					});
					
				});
			});
	
	function funGetKeyCode(event,controller) {
		
	    var key = event.keyCode;
	    
	    if(controller=='Customer' && key==13 || controller=='Customer' && key==9)
	    {
	    	if($("#txtCustCode").val().trim().length==0 || $("#txtCustCode").val()== 0)
	        {		
		          alert("Please Select Customer");
		          $("#txtCustCode").focus();
	          return false;
	       	}else
	       		{
	       		  //	$('#txtLocCode').focus();
	       			$('#txtSubGroup').focus();
	       		}
	    	
	    }
	    
	   /*  if(controller=='Location' && key==13 || controller=='Location' && key==9)
	    {
	    	if($("#txtLocCode").val().trim().length==0 || $("#txtLocCode").val()== 0)
	        {		
		          alert("Please Select Location");
		          $("#txtLocCode").focus();
	          return false;
	       	}else
	       		{
	    			$('#txtSubGroup').focus();
	       		}
	    } */
	    
	    
	    if(controller=='SubGroup' && key==13 || controller=='SubGroup' && key==9)
	    {
	    	$.each(sgData, function(i,item)
			    	{
				var sgName = $("#txtSubGroup").val();
						if(sgName==item.strSGName)
							{
								$('#hidSubGroupCode').val(item.strSGCode);
								$('#txtProdName').focus();
							}
			    	});
	    	
	    }
	 
	    if(controller=='Product' && key==13 || controller=='Product' && key==9)
	    {
	    	var prodNameOrBarCode = $("#txtProdName").val();
				if($("#txtSubGroup").val().trim().length==0)
					{
						funCallBarCodeProduct(prodNameOrBarCode);
						$("#txtQty").focus();
					}else
						{
							$.each(prodData, function(i,item)
				 			    	{
				 				
				 						if(prodNameOrBarCode==item.strProdName)
				 							{
				 							$('#hidProdCode').val(item.strProdCode);
				 							$('#txtRate').val(item.dblUnitPrice);
				 							$('#txtMRP').val(item.dblMRP);
				 							$('#lblUOM').text(item.strUOM);
				 							 
				 							$('#hidPreInvPrice').val(item.prevUnitPrice);
				 							$('#hidPrevInvCode').val(item.prevInvCode);
				 							
				 							}
				 			    	});
				 	    	document.getElementById('txtRate').focus();
						
						}
		    			
	    }
	    if(controller=='Rate' && key==13 || controller=='Rate' && key==9)
	    {
	    	document.getElementById('txtMRP').focus();
	    }
	    if(controller=='MRP' && key==13 || controller=='MRP' && key==9)
	    {
	    	document.getElementById('txtQty').focus();
	    }
	    
	    if(controller=='Qty' && key==13 || controller=='Qty' && key==9)
	    {
	    	document.getElementById('btnAdd').focus();
	    }
	    
	    if(controller=='AddBtn' && key==13)
	    	{
		    	if($("#txtQty").val().trim().length==0 || $("#txtQty").val()== 0)
		        {		
		          alert("Please Enter Quantity");
		          $("#txtQty").focus();
		          return false;
		       	}else
	       		{
		       		funAddProductRow();
			    	$("#txtSubGroup").focus();
	       		}
	    	}
	  
	}
	
	
	
	function funSetInvoiceData(code)
	{
		$("#txtInvCode").val(code);
		gurl=getContextPath()+"/loadRetailInvoiceHdData.html?invCode="+code;
		$.ajax({
	        type: "GET",
	        url: gurl,
	        dataType: "json",
	        success: function(response)
	        {		        	
	        		if('Invalid Code' == response.strInvCode){
	        			alert("Invalid  Invoice Code");
	        			$("#txtSOCode").val('');
	        			$("#txtSOCode").focus();
	        			
	        		}else{	
	        			funRemoveAllRows();
	        		
	        			var currValue='<%=session.getAttribute("currValue").toString()%>';
	        			$('#txtLocCode').val(response.strLocCode);
	        			$('#lblLocName').text(response.strLocName);
	        		
						$("#txtCustCode").val(response.strCustCode);
						$("#lblCustomerName").text(response.strCustName);
						$("#txtSubTotal").val(response.dblSubTotalAmt/currValue);
						$('#hidSettlementCode').val(response.strSettlementCode);
					
						$.each(response.listclsInvoiceModelDtl, function(i,item)
		       	       	    	 {
							funAddProductDtlRow(response.listclsInvoiceModelDtl[i],currValue);
		       	       	    		
		       	       	    	 });
						
						$.each(response.listInvsettlementdtlModel, function(i,item)
		       	       	    	 {
									funSetSettlementRow(response.listInvsettlementdtlModel[i],currValue);
		       	       	    		
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
 	
	function funAddProductDtlRow(objInvDtl,currValue) 
	{
		var table = document.getElementById("tblProdDet");
		 var dblUOMConversion=objInvDtl.dblUOMConversion;
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    var strProdCode = objInvDtl.strProdCode;
		var strProdName=objInvDtl.strProdName;
		 var strUOM=objInvDtl.strUOM;	
	    var dblQty = (parseFloat(objInvDtl.dblQty)* parseFloat(dblUOMConversion)).toFixed(maxQuantityDecimalPlaceLimit);
	    parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	    var dblRate=objInvDtl.dblUnitPrice/currValue;
	    var dblAmount=dblQty*dblRate;
	    var dblMRP=(parseFloat(objInvDtl.dblMRP)* parseFloat(dblUOMConversion)).toFixed(maxQuantityDecimalPlaceLimit)/currValue;
	    
	    var strCustCode=objInvDtl.strCustCode;
	    var prevInvCode=objInvDtl.prevInvCode;
	    var prevProdrice=objInvDtl.prevUnitPrice/currValue;
	   	
		
	    row.insertCell(0).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box\" size=\"6%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
	    row.insertCell(1).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"15%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
	    row.insertCell(2).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strUOM\" readonly=\"readonly\" class=\"Box\" size=\"6%\" id=\"txtUOM."+(rowCount)+"\" value='"+strUOM+"'/>";
	 
	    row.insertCell(3).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblQty\" type=\"text\"  required = \"required\" size=\"6%\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtQty."+(rowCount)+"\" value="+dblQty+" onblur=\"Javacsript:funUpdatePrice(this)\">";
	    row.insertCell(4).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblRate\" type=\"text\"  required = \"required\" size=\"6%\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtRate."+(rowCount)+"\" value="+dblRate+" >";
	    row.insertCell(5).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblMRP\" type=\"text\"  required = \"required\" size=\"6%\"  class=\"Box\" style=\"text-align: right;\"  id=\"txtMRP."+(rowCount)+"\" value="+dblMRP+" >";
	    row.insertCell(6).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblAmount\" readonly=\"readonly\" class=\"Box totalValueCell\" style=\"text-align: right;\" \size=\"9%\" id=\"txtAmount."+(rowCount)+"\"   value='"+dblAmount+"'/>";
	    row.insertCell(7).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';		    
	    row.insertCell(8).innerHTML= "<input type=\"hidden\" \size=\"0%\" name=\"listclsInvoiceModelDtl["+(rowCount)+"].strCustCode\"  value ="+strCustCode+" />";
	    row.insertCell(9).innerHTML= "<input readonly=\"readonly\" class=\"Box prevInvCode\" style=\"text-align: left;\" \size=\"11%\" id=\"prevInvCode."+(rowCount)+"\"   value='"+prevInvCode+"'/>";
	 	row.insertCell(10).innerHTML= "<input readonly=\"readonly\" class=\"Box prevProdrice\" style=\"text-align: right;\" \size=\"3.9%\" id=\"prevProdrice."+(rowCount)+"\"   value='"+prevProdrice+"'/>";
	 	row.insertCell(11).innerHTML= "<input type=\"hidden\" \size=\"0%\" name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblUOMConversion\"  value ="+dblUOMConversion+" />";
	 	
	 	$("#txtSubGroup").focus();
	    funClearProduct();
	    funGetTotal();
	    return false;
	}
	
	/* $(function() {
		

	 	$('#hidProdCode').keydown(function(e){
	var code=$('#hidProdCode').val();
	if (e.which == 9){
	  	if (code.trim().length > 8) {
	  		funSetProduct(code);
	  	}
	  	
	}
	if(e.which == 13)
	{
		if(code.trim().length > 8 )
		{
			 funCallBarCodeProduct(code)
		}else if (code.trim().length > 0)
  		{
  			funSetProduct(code);
  		}
	}
 
	});
	 	
	}); */
	
	
	function funGetProductStock(strProdCode)
	{
		var searchUrl="";	
		var locCode=$("#txtLocCode").val();
		var dblStock="0";
		var strInvDate=$("#txtInvDate").val();
		
		searchUrl=getContextPath()+"/getProductStockInUOM.html?prodCode="+strProdCode+"&locCode="+locCode+"&strTransDate="+strInvDate+"&strUOM=RecUOM";
		//alert(searchUrl);		
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    async: false,
			    success: function(response)
			    {
			    	dblStock= response;
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
		return Math.round(dblStock * 100) / 100;
	}
	
 function funUOMChange()
	{
	 	var conValue= $("#cmbUOM").val();
	 	var uomValue= $("#cmbUOM").text();
	 	
	 	var rate =$("#txtRate").val();
	 	var mrp =$("#txtMRP").val();
	 	//$('#cmbUOM option').eq(0).val();
	 	
	 	if(($("#cmbUOM")[0].selectedIndex)==1)
	 		{
	 			var spStock =$("#spStock").text();
		 			$("#spStock").text((parseFloat(spStock)*parseFloat(conValue)).toFixed(maxQuantityDecimalPlaceLimit));
			 	 	$("#txtRate").val((parseFloat(rate)/parseFloat(conValue)).toFixed(maxQuantityDecimalPlaceLimit));
			 	 	$("#txtMRP").val((parseFloat(rate)/parseFloat(conValue)).toFixed(maxQuantityDecimalPlaceLimit));
			 	 	
// 			 	 	$("#txtMRP").val((parseFloat(mrp)/parseFloat(conValue)).toFixed(maxQuantityDecimalPlaceLimit));
	 		}else
	 			{
	 			var conValue = $('#cmbUOM option').eq(1).val();
	 			var spStock =$("#spStock").text();
		 			$("#spStock").text((parseFloat(spStock)/parseFloat(conValue)).toFixed(maxQuantityDecimalPlaceLimit));
			 	 	
		 		 	$("#txtRate").val((parseFloat(rate)*parseFloat(conValue)).toFixed(maxQuantityDecimalPlaceLimit));
// 		 		 	$("#txtMRP").val((parseFloat(mrp)*parseFloat(conValue)).toFixed(maxQuantityDecimalPlaceLimit));
		 		 	$("#txtMRP").val((parseFloat(rate)*parseFloat(conValue)).toFixed(maxQuantityDecimalPlaceLimit));
		 		 	
	 			}
	 	
	
	 	
	}
	
	 function funSettlement()
	 {
		
		 if(($("#cmbSettlement")[0].selectedIndex)==0)
			 {
				 document.all[ 'rowCard' ].style.display = 'none';
			 }else
				 {
				 document.all[ 'rowCard' ].style.display = 'block';
				 }
	 }
	 
	 
	 $(document).ready(function() {
		 
		 
		 $('#txtPaidAmt').blur(function () 
					{
			 			var actualAmt = $('#txtActualAmt').val();
			 			var settleAmt = $('#txtSettlementAmt').val();
			 			var paidAmt = $('#txtPaidAmt').val();
			 			
			 			/* if(parseFloat(settleAmt)>parseFloat(paidAmt))
			 				{
			 					var settAmt= parseFloat(settleAmt)-parseFloat(paidAmt);
			 					$('#txtSettlementAmt').val(settAmt);
			 				} */
			 			
			 			
			 			if(parseFloat(paidAmt)>parseFloat(actualAmt))
			 				{
			 					var refAmt= parseFloat(paidAmt)-parseFloat(actualAmt);
			 					$('#txtRefundAmt').val(refAmt);	
			 				}
			 			
			 			
						
					});
		 
	 });
	 
	 function btnAddSettle_onclick() 
		{
		 var SettlementCode = $("#cmbSettlement").val();
		 if(funDuplicateSettle(SettlementCode))
			 {
			 	funAddSettlementRow();
			 }
		}
	 
	 function funAddSettlementRow() 
		{
			var table = document.getElementById("tblSettleDet");
			
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    var SettlementCode = $("#cmbSettlement").val();
		    var SettlementName = $("#cmbSettlement option:selected").text();
		    var actualAmt = $('#txtActualAmt').val();
 			var settleAmt = $('#txtSettlementAmt').val();
 			var strCardName = $('#txtCardName').val();
 			var strExpriyDate ='';
 			if($("#cmbSettlement option:selected").text()!='Cash')
 				{
 					strExpriyDate =$('#txtExpriyDate').val();
 				}
 			
 			var paidAmt = $('#txtPaidAmt').val();
 			var refAmt = $('#txtRefundAmt').val();
 			var remark = $('#txtRemark').val();
			
		    row.insertCell(0).innerHTML= "<input name=\"listInvsettlementdtlModel["+(rowCount)+"].strSettlementCode\" readonly=\"readonly\" class=\"Box\" size=\"3%\" id=\"txtSettlementCode."+(rowCount)+"\" value='"+SettlementCode+"' />";		  		   	  
		    row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box\" size=\"6%\" id=\"txtSettlementName."+(rowCount)+"\" value='"+SettlementName+"'/>";
		    row.insertCell(2).innerHTML= "<input name=\"listInvsettlementdtlModel["+(rowCount)+"].dblActualAmt\" readonly=\"readonly\" class=\"Box\"  style=\"text-align: right;\" size=\"6%\" id=\"txtActualAmt."+(rowCount)+"\" value='"+actualAmt+"'/>";
		 
		    row.insertCell(3).innerHTML= "<input name=\"listInvsettlementdtlModel["+(rowCount)+"].settleAmt\" type=\"text\" class=\"Box\"   size=\"6%\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtSettleAmt."+(rowCount)+"\" value="+settleAmt+" />";
		    row.insertCell(4).innerHTML= "<input name=\"listInvsettlementdtlModel["+(rowCount)+"].dblPaidAmt\" type=\"text\" class=\"Box totalPaidValueCell\"   size=\"6%\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtPaidAmt."+(rowCount)+"\" value="+paidAmt+" />";
		    row.insertCell(5).innerHTML= "<input name=\"listInvsettlementdtlModel["+(rowCount)+"].strCardName\" type=\"text\"  size=\"6%\"  class=\"Box\"   id=\"txtCardName."+(rowCount)+"\" value='"+strCardName+"' />";
		    row.insertCell(6).innerHTML= "<input name=\"listInvsettlementdtlModel["+(rowCount)+"].strExpiryDate\" readonly=\"readonly\" class=\"Box totalValueCell\"  \size=\"6%\" id=\"txtExpiryDate."+(rowCount)+"\"   value='"+strExpriyDate+"'/>";
		    row.insertCell(7).innerHTML= "<input name=\"listInvsettlementdtlModel["+(rowCount)+"].dblRefundAmt\" type=\"text\"   size=\"6%\"  class=\"Box totalRefValueCell\" style=\"text-align: right;\"  id=\"txtRefAmt."+(rowCount)+"\" value="+refAmt+" />";
		    row.insertCell(8).innerHTML= "<input name=\"listInvsettlementdtlModel["+(rowCount)+"].strRemark\" readonly=\"readonly\" class=\"Box totalValueCell\"  \size=\"6%\" id=\"txtRemark."+(rowCount)+"\"   value='"+remark+"'/>";
		    row.insertCell(9).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRowSettle(this)">';		    
		  
		    
		    if((parseFloat(actualAmt)-parseFloat(paidAmt))>0)
				{
					var settAmt= parseFloat(actualAmt)-parseFloat(paidAmt);
					$('#txtSettlementAmt').val(settAmt);
				}
		    
		    $('#txtPaidAmt').val('');
		    
		    funGetTotalSettle();
		    
		}
	 
	 function funDeleteRowSettle(obj) 
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblSettleDet");
		    table.deleteRow(index);
		}
	
	 function funDuplicateSettle(strSettleCode)
		{
		    var table = document.getElementById("tblSettleDet");
		    var rowCount = table.rows.length;		   
		    var flag=true;
		    if(rowCount > 0)
		    	{
				    $('#tblSettleDet tr').each(function()
				    {
					    if(strSettleCode==$(this).find('input').val())// `this` is TR DOM element
	    				{
					    	alert("Already added Settlement "+ strSettleCode);
		    				flag=false;
	    				}
					});
				    
		    	}
		    return flag;
		}
	 
	 function funDeleteAllRowSettle() 
		{
			$('#txtSettlementAmt').val($('#txtActualAmt').val());
			$('#txtRefundAmt').val('0');
		 
		    var table = document.getElementById("tblSettleDet");
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
		}
	 
	 function funGetTotalSettle()
		{
			
			var totPaid=0.00;
			var totRef=0.00;
			
			
			$('#tblSettleDet tr').each(function() {
			    var totalPaidPriceCell = $(this).find(".totalPaidValueCell").val();
			    totPaid=parseFloat(totPaid)+parseFloat(totalPaidPriceCell);
			 });
			
			$('#tblSettleDet tr').each(function() {
			    var totalRefPriceCell = $(this).find(".totalRefValueCell").val();
			    totRef=parseFloat(totRef)+parseFloat(totalRefPriceCell);
			 });
			var actulAmt = $('#txtActualAmt').val()
			if((totPaid-totRef)==actulAmt)
				{
					$('#txtSettlementAmt').val('');
				}
			
		}	
	 
	 function funSetSettlementRow(objSettlement,currValue) 
		{
			var table = document.getElementById("tblSettleDet");
			
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    var SettlementCode = objSettlement.strSettlementCode;
		    var SettlementName = objSettlement.strSettlementName;
		    var actualAmt = objSettlement.dblActualAmt;
			var settleAmt = objSettlement.dblSettlementAmt;
			var strCardName = objSettlement.strCardName;
			var strExpriyDate =objSettlement.strExpiryDate;
			
			
			var paidAmt = objSettlement.dblPaidAmt;
			var refAmt = objSettlement.dblRefundAmt;
			var remark = objSettlement.strRemark;
			
		    row.insertCell(0).innerHTML= "<input name=\"listInvsettlementdtlModel["+(rowCount)+"].strSettlementCode\" readonly=\"readonly\" class=\"Box\" size=\"3%\" id=\"txtSettlementCode."+(rowCount)+"\" value='"+SettlementCode+"' />";		  		   	  
		    row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box\" size=\"6%\" id=\"txtSettlementName."+(rowCount)+"\" value='"+SettlementName+"'/>";
		    row.insertCell(2).innerHTML= "<input name=\"listInvsettlementdtlModel["+(rowCount)+"].dblActualAmt\" readonly=\"readonly\" class=\"Box\"  style=\"text-align: right;\" size=\"6%\" id=\"txtActualAmt."+(rowCount)+"\" value='"+actualAmt+"'/>";
		 
		    row.insertCell(3).innerHTML= "<input name=\"listInvsettlementdtlModel["+(rowCount)+"].settleAmt\" type=\"text\" class=\"Box\"   size=\"6%\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtSettleAmt."+(rowCount)+"\" value="+settleAmt+" />";
		    row.insertCell(4).innerHTML= "<input name=\"listInvsettlementdtlModel["+(rowCount)+"].dblPaidAmt\" type=\"text\" class=\"Box totalPaidValueCell\"   size=\"6%\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtPaidAmt."+(rowCount)+"\" value="+paidAmt+" />";
		    row.insertCell(5).innerHTML= "<input name=\"listInvsettlementdtlModel["+(rowCount)+"].strCardName\" type=\"text\"  size=\"6%\"  class=\"Box\"   id=\"txtCardName."+(rowCount)+"\" value='"+strCardName+"' />";
		    row.insertCell(6).innerHTML= "<input name=\"listInvsettlementdtlModel["+(rowCount)+"].strExpiryDate\" readonly=\"readonly\" class=\"Box totalValueCell\"  \size=\"6%\" id=\"txtExpiryDate."+(rowCount)+"\"   value='"+strExpriyDate+"'/>";
		    row.insertCell(7).innerHTML= "<input name=\"listInvsettlementdtlModel["+(rowCount)+"].dblRefundAmt\" type=\"text\"   size=\"6%\"  class=\"Box totalRefValueCell\" style=\"text-align: right;\"  id=\"txtRefAmt."+(rowCount)+"\" value="+refAmt+" />";
		    row.insertCell(8).innerHTML= "<input name=\"listInvsettlementdtlModel["+(rowCount)+"].strRemark\" readonly=\"readonly\" class=\"Box totalValueCell\"  \size=\"6%\" id=\"txtRemark."+(rowCount)+"\"   value='"+remark+"'/>";
		    row.insertCell(9).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRowSettle(this)">';		    
		  
		}
	 
</script>

</head>
<body onload="funOnLoad();">
	<div id="formHeading">
		<label>Retail Billing</label>
	</div>
	<s:form name="RetailForm" method="POST"
		action="saveRetailingBillng.html?saddr=${urlHits}">
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		
		<div id="tab_container" style="height: 600px">
						<ul class="tabs">
							<li class="active" data-state="tab1"
								style="width: 100px; padding-left: 55px">Retail Billing</li>
							<li data-state="tab2" style="width: 100px; padding-left: 55px">Settlement</li>
						</ul>

						<div id="tab1" class="tab_content">
						<table
							style="border: 0px solid black; width: 100%; height: 100%; margin-left: auto; margin-right: auto; background-color: #C0E4FF;">
							<tr>
								<td>
								<table class="transTable">
									<tr>
										<th align="right" colspan="9"><a id="baseUrl" href="#">
												Attach Documents </a></th>
									</tr>
	
									<tr>
									<td width="100px"><label>Inv. Code</label></td>
									<td  colspan="3"><s:input path="strInvCode" id="txtInvCode"
											ondblclick="funHelp('invoiceRetail')" 
											cssClass="searchTextBox" /></td>

									<td width="100px"><label>Inv Date</label>
									<td><s:input path="dteInvDate" id="txtInvDate"
											required="required" readonly="true" cssClass="calenderTextBox" /></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								

								<tr>
									<td><label>Customer Code</label></td>
									<td  colspan="1"><s:input path="strCustCode" id="txtCustCode"
											ondblclick="funHelp('custMasterActive')" cssClass="searchTextBox" onkeypress="funGetKeyCode(event,'Customer')" /></td>
									<td colspan="5"><label id="lblCustomerName"
										class="namelabel"></label></td>

								</tr>

								<tr>

									<td><label>Location Code</label></td>
									<td><s:input type="text" id="txtLocCode" path="strLocCode"
											cssClass="searchTextBox" 
											 readonly="true" /></td>
									<td colspan="2"><label id="lblLocName"></label></td>

									<%-- <td><label>Vechile No</label></td>
									<td ><s:input id="txtVehNo" type="text" path="strVehNo"
											cssClass="searchTextBox" ondblclick="funHelp('VehCode');"/></td> --%>
								</tr>
							

								<tr>
								
								<td width="100px"><label>Sub-Group</label></td>
									<td ><input id="txtSubGroup" cssStyle="width:80%;text-transform: uppercase;"  name="SubgroupName" class="searchTextBox" 
									style="width: 98%;background-position: 220px 2px;" placeholder="Type to search Subgroup"
									onkeypress="funGetKeyCode(event,'SubGroup')"
									/></td>
									<input type="hidden" id="hidSubGroupCode" />
								
									<td width="100px"><label>Product</label></td>
									<td colspan="2"><input id="txtProdName"
										ondblclick="funHelp('productProduced')" class="searchTextBox" onkeypress="funGetKeyCode(event,'Product')" 
										style="width: 90%;background-position: 280px 2px;" 
										 placeholder="Type to search Product" /></td>
									 
									 <td><label id="lblUOM"></label></td>
									<input type="hidden" id="hidProdCode" />
								</tr>
								<tr>

									<td><label>Rate</label></td>
									<td><input type="text" id="txtRate"
										class="decimal-places numberField" 
										onkeypress="funGetKeyCode(event,'Rate')" /></td>
									
									<td><label>PRP</label></td>
									<td><input type="text" id="txtMRP"
										class="decimal-places numberField" 
										onkeypress="funGetKeyCode(event,'MRP')" readonly="readonly" /></td>	
									
									<td><label>Stock</label></td>
									<td  width="10%">
									<label id="spStock" class="namelabel"></label>
									<span id="spStockUOM"></span>
									</td>
								
								</tr>
								
								<tr>
								
								
									<td style="width: 115px"><label>Quantity</label></td>
									<td><input id="txtQty" type="text"
										class="decimal-places numberField" style="width: 60%" 
										onkeypress="funGetKeyCode(event,'AddBtn')" /></td>
								
								<td ><label>UOM</label></td>
								<td><select id="cmbUOM" class="BoxW116px" onchange="funUOMChange()"></select></td>
								
								<td><input id="btnAdd" type="button" value="Add" 
									 onclick="return btnAdd_onclick();" class="smallButton" 
									 onkeypress="funGetKeyCode(event,'Qty')" ></input></td>
								
								</tr>
								

							</table>

							<div class="dynamicTableContainer" style="height: 300px;">
								<table
									style="height: 20px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
									<tr bgcolor="#72BEFC">
										<td width="5%">Product Code</td>
										<!--  COl1   -->
										<td width="15%">Product Name</td>
										<!--  COl2   -->
										<td width="6%">UOM</td>
										<!--  COl3   -->
										<td width="6%">Qty</td>
										<!--  COl4   -->
										<td width="6%">Rate</td>
										<!--  COl5   -->
										<td width="6%">MRP</td>
										<!--  COl6   -->
										<td width="6%">Amount</td>
										<!--  COl7   -->
										<td width="3%">Delete</td>
										<!--  COl8   -->
										<td width="15%">Prev Bill</td>
										<!--  COl9   -->
										<td width="3%">Prev Price</td>
										<!--  COl10   -->
									</tr>
								</table>
								<div
									style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
									<table id="tblProdDet"
										style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
										class="transTablex col15-center">
										<tbody>
										<col style="width: 5%">
										<!--  COl1   -->
										<col style="width: 15%">
										<!--  COl2   -->
										<col style="width: 6%">
										<!--  COl3   -->
										<col style="width: 6%">
										<!--  COl4   -->
										<col style="width: 6%">
										<!--  COl5   -->
										<col style="width: 6%">
										<!--  COl6   -->
										<col style="width: 6%">
										<!--  COl7  -->
										<col style="width: 3%">
										<!--  COl8   -->
										<col style="width: 0%">
										<!--  COl9   -->
										
										<col style="width: 15%">
										<!--  COl8   -->
										<col style="width: 4%">
										<col style="width: 0%">

										</tbody>

									</table>
								</div>

							</div>
				</td>
			</tr>
			
		</table>
		<table class="transTable">

			<tr>
			<td></td>
			<td></td>
			<td></td>
			<td ></td>
			</tr>
			<tr>
			<td ><label>Sub Total</label></td>
			<td><input id="txtSubTotal" type="text" readonly="readonly" class="BoxW116px" /></td>
			</tr>
		</table>
		
		</div>
		<div id="tab2" class="tab_content">
		
		<table class="transTable">
		
		<tr>
			<td><label>Settlement Type</label></td>
			<td><s:select id="cmbSettlement" Class="BoxW116px" path="strSettlementCode" items="${hmSettlement}" onchange="funSettlement()"></s:select></td>
			<td ><label>Actual Amount</label></td>
			<td colspan="2"><input id="txtActualAmt"  type="text" readonly="readonly"   class="BoxW116px decimal-places numberField"  /></td>
		
		</tr>
		<tr>
			<td ><label>Settlement Amount</label></td>
			<td><input id="txtSettlementAmt"   type="text" readonly="readonly" class="BoxW116px decimal-places numberField" /></td>
			
			<td><label>Paid Amount</label></td>
			<td colspan="2"><input id="txtPaidAmt"  type="text"   class="BoxW116px decimal-places numberField"  /></td>
			
			
		
		
		</tr>
		<tr id="rowCard" style="display: none"> 
			<td><label>Card Name</label></td>
			<td><input id="txtCardName"  type="text"  class="BoxW116px" /></td>
			<td><label>Expriy Date</label></td>
			<td colspan="2"><input id="txtExpriyDate"  type="text" Class="calenderTextBox" /></td>
		</tr>
		<tr>
		<td><label>Refund Amount</label></td>
			<td><input id="txtRefundAmt"  type="text"  readonly="readonly" value="0"  class="BoxW116px decimal-places numberField"  /></td>
			<td><label>Remark</label></td>
			<td><textarea id="txtRemark"  type="text"  class="BoxW116px" ></textarea></td>
			<td><input id="btnAddSettle" type="button" value="Add" 
									 onclick="return btnAddSettle_onclick();" class="smallButton" ></input>&nbsp;&nbsp;&nbsp;
									 
									 <input id="btnResetSettle" type="button" value="Reset" 
									 onclick="return funDeleteAllRowSettle();" class="smallButton" ></input>
									
									 </td>
		</tr>	
		
		</table>
		
		<div class="dynamicTableContainer" style="height: 300px;">
								<table
									style="height: 20px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
									<tr bgcolor="#72BEFC">
										<!--  COl0   -->
										<td width="3%">Settlement Code</td>
										<!--  COl1   -->
										<td width="5%">Settement Name</td>
										<!--  COl2   -->
										<td width="6%">Actual Amt</td>
										<!--  COl3   -->
										<td width="6%">Settlement Amt</td>
										<!--  COl4   -->
										<td width="6%">Paid Amt</td>
										<!--  COl5   -->
										<td width="6%">Card Name</td>
										<!--  COl6   -->
										<td width="6%">Expiry date</td>
										<!--  COl7   -->
										<td width="6%">Refund Amt</td>
										<!--  COl8   -->
										<td width="6%">Remark</td>
										<!--  COl9   -->
										<td width="2%">Delete</td>
										
										
										
							</tr>
							</table>
							
							<div
									style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
									<table id="tblSettleDet"
										style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
										class="transTablex col15-center">
										<tbody>
										<col style="width: 5%">
										<!--  COl1   -->
										<col style="width: 5%">
										<!--  COl2   -->
										<col style="width: 6%">
										<!--  COl3   -->
										<col style="width: 6%">
										<!--  COl4   -->
										<col style="width: 6%">
										<!--  COl5   -->
										<col style="width: 6%">
										<!--  COl6   -->
										<col style="width: 6%">
										<!--  COl7   -->
										<col style="width: 6%">
										<!--  COl8  -->
										<col style="width: 6%">
										<!--  COl9   -->
										<col style="width: 3%">
							</table>
							</div>	
							</div>		
		
		
		</div>
		</div>
		
		
	<br>

		<div align="center">
			<input type="submit" value="Submit"
				onclick="return funCallFormAction()"
				class="form_button" /> &nbsp; &nbsp; &nbsp; <input type="button"
				id="reset" name="reset" value="Reset" class="form_button" />
		</div>
		<s:input type="hidden" id="hidProdType" path="strProdType"></s:input>
<%-- 		<s:input type="hidden" id="hidSettlementCode" path="strSettlementCode"></s:input> --%>
		
		
		<br>
		
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
		<input type="hidden" id="hidPrevInvCode" ></input>	
			<input type="hidden" id="hidPreInvPrice" ></input>
	</s:form>
	<script type="text/javascript">
	//	funApplyNumberValidation();
	</script>
</body>
</html>