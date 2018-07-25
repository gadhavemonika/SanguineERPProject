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
	$("#txtLocCode").val("${locationCode}");
	$("#lblLocName").text("${locationName}"); 
	
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
	<%if(null!=session.getAttribute("rptDCCode")){%>
	code='<%=session.getAttribute("rptDCCode").toString()%>';
	<%session.removeAttribute("rptDCCode");%>
	var isOk=confirm("Do You Want to Generate Slip?");
	if(isOk){
		invoiceformat='<%=session.getAttribute("invoieFormat").toString()%>';
		if(invoiceformat=="Format 3 Inv Ret")
			{
// 			window.open(getContextPath()+"/openRptDCRetailSlip.html?rptDCCode="+code,'_blank');
			window.open(getContextPath()+"/openRptDCSlip.html?rptDCCode="+code,'_blank');
			}else{
		window.open(getContextPath()+"/openRptDCSlip.html?rptDCCode="+code,'_blank');
	}
	}
	<%}%>

	});


		
$(document).ready(function() 
		{	
	var sgData ;
	var prodData;
	
			$(".tab_content").hide();
			$(".tab_content:first").show();
	
			$("ul.tabs li").click(function() {
				$("ul.tabs li").removeClass("active");
				$(this).addClass("active");
				$(".tab_content").hide();
				var activeTab = $(this).attr("data-state");
				$("#" + activeTab).fadeIn();
			});
			
			
			 $("#txtDCDate").datepicker({ dateFormat: 'yy-mm-dd' });
				$("#txtDCDate" ).datepicker('setDate', 'today');
				$("#txtDCDate").datepicker();
				
				 $("#txtAginst").datepicker({ dateFormat: 'yy-mm-dd' });
					$("#txtAginst" ).datepicker('setDate', 'today');
					$("#txtAginst").datepicker();
					
					 $("#txtWarrPeriod").datepicker({ dateFormat: 'yy-mm-dd' });
						$("#txtWarrPeriod" ).datepicker('setDate', 'today');
						$("#txtWarrPeriod").datepicker();
						
						 $("#txtWarraValidity").datepicker({ dateFormat: 'yy-mm-dd' });
							$("#txtWarraValidity" ).datepicker('setDate', 'today');
							$("#txtWarraValidity").datepicker();		
							$("#txtCustCode").focus();
			
		});
		

			$(function(){
			
				/**
				 * Checking Authorization
				**/
				var flagOpenFromAuthorization="${flagOpenFromAuthorization}";
				if(flagOpenFromAuthorization == 'true'){
					funSetDeliveryChallanData("${authorizationDCCode}");
				}
			});
		
		function funHelp(transactionName)
		{
			fieldName = transactionName;
			//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;top=500,left=500");
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
				    	
				 case 'productProduced':
				    	funSetProduct(code);
				        break;
				        
				 case 'deliveryChallan':   
					 funSetDeliveryChallanData(code);
					 break;
					 
				 case 'salesorder' :
					  $('#txtCode').val(code);
					  break;
					  
				 case 'VehCode' : 
					 funSetVehCode(code);
					 break;	
						
				 case 'subgroup':
				     funSetSubGroup(code);
				     break;
				        
				 case'invoiceForDC':
				    $('#txtCode').val(code);
				    break;
				    	
				}
			}
		 	
		 	function funSetSubGroup(code)
			{
				$("#hidSubGroupCode").val(code);
				$.ajax({
				        type: "GET",
				        url: getContextPath()+"/loadSubGroupMasterData.html?subGroupCode="+code,
				        dataType: "json",
				        success: function(response)
				        {
					        	$("#txtSubGroup").val(response.strSGName);
						},
				        error: function(e)
				        {				        	
				        	alert("Invalid SubGroup Code");
			        		$("#txtSubgroupCode").val('');
				        }
			      });
			}
		 	
		 	function funSetVehCode(code){

				$.ajax({
					type : "GET",
					url : getContextPath()+ "/LoadVehicleMaster.html?docCode=" + code,
					dataType : "json",
					success : function(response){ 
						
						if('Invalid Code' == response.strVehCode){
		        			alert("Invalid Vehicle Code");
		        			$("#txtVehCode").val('');
		        			$("#txtVehCode").focus();
		        		}else{
		        			
		        			$("#txtVehNo").val(response.strVehNo);
		        			
		        		
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
				$("#cmbSettlement").focus();
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
	        			$("#txtPartyCode").val('');
	        			$("#txtPartyCode").focus();
	        			
	        		}else{			   
	        			$("#txtCustCode").val(response.strPCode);
						$("#lblCustomerName").text(response.strPName);
						       
						
						$("#txtSAddress1").val(response.strSAdd1);
						$("#txtSAddress2").val(response.strSAdd2);
						$("#txtSCity").val(response.strSCity);
						$("#txtSState").val(response.strSState);
						$("#txtSPin").val(response.strSPin);
						$("#txtSCountry").val(response.strSCountry);
						$("#txtLocCode").focus();
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
				    		var dblStock=funGetProductStock(response[0][0]);
				    		//alert(dblStock);
				    		$("#spStock").text(dblStock);
							$("#hidProdCode").val(response[0][0]);
							$("#txtProdName").val(response[0][1]);
							$("#txtPrice").val(response[0][3]);
							//$("#cmbUOM").val(response[0][2]);
							$("#txtWeight").val(response[0][7]);
							$("#hidProdType").val(response[0][6]);
							$("#hidPartNo").val(response[0][8]);
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
	
	
	function funSetDeliveryChallanData(code)
	{
		gurl=getContextPath()+"/loadDeliveryChallanHdData.html?dcCode="+code;
		$.ajax({
	        type: "GET",
	        url: gurl,
	        dataType: "json",
	        success: function(response)
	        {		        	
	        		if('Invalid Code' == response.strDCCode){
	        			alert("Invalid Delivery Challan Code");
	        			$("#txtCode").val('');
	        			$("#txtCode").focus();
	        			
	        		}else{	
	        			funRemoveAllRows();
	        			$('#txtDCCode').val(response.strDCCode);
	        			$('#txtDCDate').val(response.dteDCDate);
	        			$('#txtAginst').val(response.strAgainst);
	        			$('#cmbAgainst').val(response.strAgainst);
	        			if(response.strAgainst=="Sales Order")
	        			{
	        			document.all["txtCode"].style.display = 'block';
	        			document.all["btnFill"].style.display = 'block';
	        			
	        			}else
	        				{
	        				document.all["txtCode"].style.display = 'none';
	        				document.all["btnFill"].style.display = 'none';
	        				}
	        			$('#txtPONo').val(response.strPONo);
	        			$('#txtLocCode').val(response.strLocCode);
	        			$('#lblLocName').text(response.strLocName);
	        			$('#txtVehNo').val(response.strVehNo);
	        			$('#txtWarrPeriod').val(response.strWarrPeriod);
	        			$('#txtWarraValidity').val(response.strWarraValidity);
	        			$('#txtNarration').val(response.strNarration);
	        			$('#txtPackNo').val(response.strPackNo);
	        			$('#txtDktNo').val(response.strDktNo);
	        			$('#txtMInBy').val(response.strMInBy);
	        			$('#txtTimeOut').val(response.strTimeInOut);
	        			$('#txtReaCode').val(response.strReaCode);
	        			$('#cmbSettlement').val(response.strSettlementCode);
	        			
	        			$("#txtCode").val(response.strSOCode);
						$("#txtSODate").val(response.dteSODate);

						$("#txtCustCode").val(response.strCustCode);
						$("#lblCustomerName").text(response.strCustName);
					
						$("#txtSAddress1").val(response.strSAdd1);
						$("#txtSAddress2").val(response.strSAdd2);
						$("#txtSCity").val(response.strSCity);
						$("#txtSState").val(response.strSState);
						$("#txtSPin").val(response.strSPin);
						$("#txtSCountry").val(response.strSCountry);

						$.each(response.listclsDeliveryChallanModelDtl, function(i,item)
		       	       	    	 {
		       	        			
		       	       	    	    funfillDCDataRow(response.listclsDeliveryChallanModelDtl[i]);
		       	       	    	                                           
		       	       	    	 });
						
						

//							$('#').val(response.);
						
//							$('#').val(response.);
//							$('#').val(response.);
//							$('#').val(response.);
						
						
						
						
						
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
		
		if($("#txtProdName").val().length<=0)
			{
				$("#txtProdName").focus();
				alert("Please Enter Product Code Or Search");
				return false;
			}			
	    if($("#txtQty").val().trim().length==0 || $("#txtQty").val()== 0)
	        {		
	          alert("Please Enter Quantity");
	          $("#txtQty").focus();
	          return false;
	       } 
	    else
	    	{
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
	    var packingNo = $("#txtPackingNo").val()
		if(packingNo=="")
	    {
	  		packingNo=1;
	    }
	    strProdCode=strProdCode+packingNo;
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
		var strProdName=$("#txtProdName").val().trim();
		var strProdType=$("#hidProdType").val();
		var strPartNo=$("#hidPartNo").val();
	    var dblQty = $("#txtQty").val();
	    parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	    var dblWeight=$("#txtWeight").val();
	    var dblTotalWeight=dblQty*dblWeight;
	    
	  	var packingNo= $("#txtPackingNo").val();
	  	if(packingNo=="")
	    {
	  		packingNo=1;
	    }
	    var strSerialNo = $("#txtSerialNo").val();
	    var strInvoiceable = $("#cmbInvoiceable").val();
	    var strRemarks=$("#txtRemarks").val();
	    var dblPcketQty=$("#txtPcketQty").val();
	    if(dblPcketQty=="")
	    {
	    	//dblPcketQty=0;
	    	dblPcketQty=dblQty;
	    }
	    var noOfPacket= dblQty/dblPcketQty;
	    noOfPacket= parseInt(noOfPacket);
// 	 	if(noOfPacket.contains("."))
// 		 {
// 	     noOfPacket= noOfPacket.split(".")[0];
// 		 }
	    for(var i=0;i<noOfPacket;i++)
		{	
	    	var prodPckNo=strProdCode+packingNo
		row = table.insertRow(rowCount);
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" type=\"hidden\" class=\"Box\" style=\"display:none;\"  id=\"strPropNLocCode."+(rowCount)+"\" value='"+prodPckNo+"' />";
	    row.insertCell(1).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
	    row.insertCell(2).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strPartNo\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtPartNo."+(rowCount)+"\" value='"+strPartNo+"'/>";
	    row.insertCell(3).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"35%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
	    row.insertCell(4).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strProdType\" readonly=\"readonly\" class=\"Box\" size=\"9%\" id=\"txtProdTpye."+(rowCount)+"\" value='"+strProdType+"'/>";
	 
	    row.insertCell(5).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].dblQty\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtQty."+(rowCount)+"\" value="+dblPcketQty+" onblur=\"Javacsript:funUpdatePrice(this)\">";
	    row.insertCell(6).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].dblWeight\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtWeight."+(rowCount)+"\" value="+dblWeight+" >";
	    row.insertCell(7).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].dblTotalWeight\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" \size=\"3.9%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+dblTotalWeight+"'/>";
	    row.insertCell(8).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strPktNo\" type=\"text\"  required = \"required\"  class=\"Box\" \size=\"5%\" id=\"txtPktNo."+(rowCount)+"\" value="+packingNo+" >";
	    row.insertCell(9).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strRemarks\" size=\"10%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
	    row.insertCell(10).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strInvoiceable\" readonly=\"readonly\" class=\"Box\"  size=\"6%\" id=\"txtInvoiceable."+(rowCount)+"\" value="+strInvoiceable+" >";
	    row.insertCell(11).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strSerialNo\" type=\"text\"   class=\"Box\" size=\"5%\" id=\"txtSerialNo."+(rowCount)+"\" value="+strSerialNo+" >";	    
// 	    row.insertCell(10).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].dblPacketQty\" type=\"text\"  required = \"required\"  class=\"Box\" \size=\"5%\" id=\"txtPktNo."+(rowCount)+"\" value="+dblPcketQty+" >";
	 
	 	row.insertCell(12).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';		 
	 	
	 	packingNo=parseFloat(packingNo)+1;
	 	rowCount=rowCount+1;
		}
		if(!(dblQty%dblPcketQty==0))
		{
			
			noOfPacket=dblQty % dblPcketQty;
			var prodPckNo=strProdCode+packingNo
			    row = table.insertRow(rowCount);
			    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" type=\"hidden\" class=\"Box\" style=\"display:none;\"  id=\"strPropNLocCode."+(rowCount)+"\" value='"+prodPckNo+"' />";
			    row.insertCell(1).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
			    row.insertCell(2).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strPartNo\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtPartNo."+(rowCount)+"\" value='"+strPartNo+"'/>";
			    row.insertCell(3).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"35%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
			    row.insertCell(4).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strProdType\" readonly=\"readonly\" class=\"Box\" size=\"9%\" id=\"txtProdTpye."+(rowCount)+"\" value='"+strProdType+"'/>";
			    row.insertCell(5).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].dblQty\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtQty."+(rowCount)+"\" value="+noOfPacket+" onblur=\"Javacsript:funUpdatePrice(this)\">";
			    row.insertCell(6).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].dblWeight\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtWeight."+(rowCount)+"\" value="+dblWeight+" >";
			    row.insertCell(7).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].dblTotalWeight\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" \size=\"3.9%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+dblTotalWeight+"'/>";
			    row.insertCell(8).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strPktNo\" type=\"text\"  required = \"required\"  class=\"Box\" \size=\"5%\" id=\"txtPktNo."+(rowCount)+"\" value="+packingNo+" >";
			    row.insertCell(9).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strRemarks\" size=\"10%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
			    row.insertCell(10).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strInvoiceable\" readonly=\"readonly\" class=\"Box\"  size=\"6%\" id=\"txtInvoiceable."+(rowCount)+"\" value="+strInvoiceable+" >";
			    row.insertCell(11).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strSerialNo\" type=\"text\"    class=\"Box\" size=\"5%\" id=\"txtSerialNo."+(rowCount)+"\" value="+strSerialNo+" >";	    
// 			    row.insertCell(10).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].dblPacketQty\" type=\"text\"  required = \"required\"  class=\"Box\" \size=\"5%\" id=\"txtPktNo."+(rowCount)+"\" value="+noOfPacket+" >";
			 
			 	row.insertCell(12).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';		
			 	
			 	packingNo=parseFloat(packingNo)+1;
		}
		
		$("#txtPackingNo").val(packingNo);
	    $("#txtSubGroup").focus();
	    funClearProduct();
	    funGetTotal();
	    return false;
	}
	
	function funUpdatePrice(object)
	{
		var index=object.parentNode.parentNode.rowIndex;
		var Qty=document.getElementById("txtQty."+index).value;
		var price=document.getElementById("txtPrice."+index).value;
		var discount=document.getElementById("txtDiscount."+index).value;
		var ItemPrice;
		ItemPrice=(parseFloat(Qty)*parseFloat(price))-parseFloat(discount);
		
		document.getElementById("txtAmount."+index).value=parseFloat(ItemPrice);
		funGetTotal();
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
	
	function funfillDCDataRow(DCDtl)
	{
		var table = document.getElementById("tblProdDet");
		
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    var strProdCode = DCDtl.strProdCode;
		var strProdName=DCDtl.strProdName;
		var strProdType=DCDtl.strProdType;	
	    var dblQty = DCDtl.dblQty;
	    parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	    var dblWeight=DCDtl.dblWeight;
	    var dblTotalWeight=dblQty*dblWeight;
	    
	  	var packingNo= DCDtl.strPktNo;
	    var strSerialNo = DCDtl.strSerialNo;
	    var strInvoiceable = DCDtl.strInvoiceable;
	    var strRemarks=DCDtl.strRemarks;
	   var  prodPckNo=strProdCode+packingNo;
	   var strPartNo=$("#hidPartNo").val();
	   
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" type=\"hidden\" class=\"Box\" style=\"display:none;\"  id=\"strPropNLocCode."+(rowCount)+"\" value='"+prodPckNo+"' />";
	    row.insertCell(1).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
	    row.insertCell(2).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strPartNo\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtPartNo."+(rowCount)+"\" value='"+strPartNo+"'/>";
	    row.insertCell(3).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"35%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
	    row.insertCell(4).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strProdType\" readonly=\"readonly\" class=\"Box\" size=\"9%\" id=\"txtProdTpye."+(rowCount)+"\" value='"+strProdType+"'/>";
	 
	    row.insertCell(5).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].dblQty\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtQty."+(rowCount)+"\" value="+dblQty+" onblur=\"Javacsript:funUpdatePrice(this)\">";
	    row.insertCell(6).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].dblWeight\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtWeight."+(rowCount)+"\" value="+dblWeight+" >";
	    row.insertCell(7).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].dblTotalWeight\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" \size=\"3.9%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+dblTotalWeight+"'/>";
	    row.insertCell(8).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strPktNo\" type=\"text\"   class=\"Box\" \size=\"5%\" id=\"txtPktNo."+(rowCount)+"\" value="+packingNo+" >";
	    row.insertCell(9).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strRemarks\" size=\"10%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
	    row.insertCell(10).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strInvoiceable\" readonly=\"readonly\" class=\"Box\"  size=\"6%\" id=\"txtInvoiceable."+(rowCount)+"\" value="+strInvoiceable+" >";
	    row.insertCell(11).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strSerialNo\" type=\"text\"    class=\"Box\" size=\"5%\" id=\"txtSerialNo."+(rowCount)+"\" value="+strSerialNo+" >";	    
	    
	 
	 	row.insertCell(12).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';		    
	    
	    
	 	 $("#txtProdName").focus();
		    funClearProduct();
		    funGetTotal();
		    return false;
	    
	  
	
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
		
		 if($("#txtCustCode").val()=="")
			{
				alert("Please Enter CustomerCode");
				$("#txtCustCode").focus();
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
	
		
	function funClearProduct()
	{
		$("#txtSubGroup").val("");
		$("#txtProdName").val("");
		$("#strUOM").val("");
		$("#lblProdName").text("");
		$("#txtQty").val("");
		$("#txtPrice").val("");
		
		$("#txtRemarks").val("");
		$("#txtWeight").val("");
		$("#txtDiscount").val(0);
	}
	
	function funApplyNumberValidation(){
		$(".numeric").numeric();
		$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
		$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
		$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
	    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
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
	
	function funShowSOFieled()
	{
		
		var agianst = $("#cmbAgainst").val();
		if(agianst=="Direct")
			{
			document.all["txtCode"].style.display = 'none';
			document.all["btnFill"].style.display = 'none';
			
			}else
				{
				document.all["txtCode"].style.display = 'block';
				document.all["btnFill"].style.display = 'block';
				}
		
	}
	
	function btnFill_onclick()
	{
		var currValue='<%=session.getAttribute("currValue").toString()%>';
		var code =$('#txtCode').val();
		 var  cmbAgainst=$('#cmbAgainst').val();
		if( cmbAgainst=="Sales Order")
		{
			
			funRemoveAllRows();
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
			        			$("#txtCode").val('');
			        			$("#txtCode").focus();
			        			
			        		}else{	
			        			funRemoveAllRows();
// 			        			 $("#txtCode").val(response.strSOCode);
			        	    	 $("#txtCustCode").val(response.strCustCode);
			        	    
			        			 $("#lblCustomerName").text(response.strcustName);
			        			 $("#txtSODate").val(response.dteSODate);
			        			 $('#txtLocCode').val(response.strLocCode);
				        			$('#lblLocName').text(response.strLocName);
				        			$('#cmbSettlement').val(response.strSettlementCode);
								
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
			
		}else
		{
		  	funRemoveAllRows();
		  	var currValue='<%=session.getAttribute("currValue").toString()%>';
			gurl=getContextPath()+"/loadInvoiceHdData.html?invCode="+code;
			
			$.ajax({
		        type: "GET",
		        url: gurl,
		        dataType: "json",
		        success: function(response)
		        {		        	
		       		if('Invalid Code' == response.strInvCode){
		       			alert("Invalid  Invoice Code");
		       			$("#txtCode").val('');
		       			$("#txtCode").focus();
		       			
		       		}else{	
		       			$('#txtLocCode').val(response.strLocCode);
		       			$('#lblLocName').text(response.strLocName);
						$("#txtCustCode").val(response.strCustCode);
						$("#lblCustomerName").text(response.strCustName);
						$("#txtDiscPer").val(response.dblDiscount);
						
						$.each(response.listclsInvoiceModelDtl, function(i,item)
						{
							funfillProductRowInvoiceReturn(response.listclsInvoiceModelDtl[i],currValue);
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
	
	function funfillProductRowInvoiceReturn(listInvoiceDtl)
	{
		var table = document.getElementById("tblProdDet");
		
		 var rowCount = table.rows.length;
	     var row = table.insertRow(rowCount);
		 var strProdCode = listInvoiceDtl.strProdCode;
	     var strProdName=listInvoiceDtl.strProdName;
		 var strProdType=listInvoiceDtl.strProdType;	
		 var dblQty = listInvoiceDtl.dblQty;
	  parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	  
		 var dblWeight=listInvoiceDtl.dblWeight;
		 
		 if(dblWeight==null)
	    	{
		    	dblWeight=0;
	    	}
		 
		 var dblTotalWeight=dblQty*dblWeight;
		 if(dblTotalWeight==null)
	   	{
		    	dblTotalWeight=0;
	   	}
	   
		 var packingNo= listInvoiceDtl.strPktNo;
	  	 if(packingNo==null)
	    	{
	  		packingNo="";
	    	}
	    var strSerialNo = listInvoiceDtl.strSerialNo;
	    if(strSerialNo==null)
    	{
	    	strSerialNo="";
    	}
	    var strInvoiceable = listInvoiceDtl.strInvoiceable;
	    if(strInvoiceable==null)
    	{
	    	strInvoiceable="N";
    	}
	    var strRemarks=listInvoiceDtl.strRemarks;
	    if(strRemarks==null)
    	{
	    	strRemarks="";
    	}
	    
	    var strPartNo=listInvoiceDtl.strPartNo;
	    if(strPartNo==null)
    	{
	    	strPartNo="";
    	}
	    
	    var prodPckNo=listInvoiceDtl.strPropNLocCode;
	    if(prodPckNo==null)
	    {
	    	prodPckNo="";
	    }
	    
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" type=\"hidden\" class=\"Box\"  style=\"display:none;\"  id=\"strPropNLocCode."+(rowCount)+"\" value='"+prodPckNo+"' />";
	    row.insertCell(1).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
	    row.insertCell(2).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strPartNo\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtPartNo."+(rowCount)+"\" value='"+strPartNo+"'/>";
	    row.insertCell(3).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"35%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
	    row.insertCell(4).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strProdType\" readonly=\"readonly\" class=\"Box\" size=\"9%\" id=\"txtProdTpye."+(rowCount)+"\" value='"+strProdType+"'/>";
	 
	    row.insertCell(5).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].dblQty\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtQty."+(rowCount)+"\" value="+dblQty+" onblur=\"Javacsript:funUpdatePrice(this)\">";
	    row.insertCell(6).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].dblWeight\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtWeight."+(rowCount)+"\" value="+dblWeight+" >";
	    row.insertCell(7).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].dblTotalWeight\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" \size=\"3.9%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+dblTotalWeight+"'/>";
	    row.insertCell(8).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strPktNo\" type=\"text\"    class=\"Box\" \size=\"5%\" id=\"txtPktNo."+(rowCount)+"\" value="+packingNo+" >";
	    row.insertCell(9).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strRemarks\" size=\"10%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
	    row.insertCell(10).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strInvoiceable\" readonly=\"readonly\" class=\"Box\"  size=\"6%\" id=\"txtInvoiceable."+(rowCount)+"\" value="+strInvoiceable+" >";
	    row.insertCell(11).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strSerialNo\" type=\"text\"    class=\"Box\" size=\"5%\" id=\"txtSerialNo."+(rowCount)+"\" value="+strSerialNo+" >";	    
	    
	 
	 	row.insertCell(12).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';		    
	    
	    
	 	 $("#txtSubGroup").focus();
		    funClearProduct();
		    funGetTotal();
		    return false;

	}
	
	
	function funfillSalesOrderDataRow(listSODtl)
	{
		
		var table = document.getElementById("tblProdDet");
		
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    var strProdCode = listSODtl.strProdCode;
		var strProdName=listSODtl.strProdName;
		var strProdType=listSODtl.strProdType;	
	    var dblQty = listSODtl.dblQty;
	    parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	    var dblWeight=listSODtl.dblWeight;
	    if(dblWeight==null)
    	{
	    	dblWeight=0;
    	}
	    var dblTotalWeight=dblQty*dblWeight;
	    if(dblTotalWeight==null)
    	{
	    	dblTotalWeight=0;
    	}
	    
	  	var packingNo= listSODtl.strPktNo;
	  	 if(packingNo==null)
	    	{
	  		packingNo="";
	    	}
	    var strSerialNo = listSODtl.strSerialNo;
	    if(strSerialNo==null)
    	{
	    	strSerialNo="";
    	}
	    var strInvoiceable = listSODtl.strInvoiceable;
	    if(strInvoiceable==null)
    	{
	    	strInvoiceable="N";
    	}
	    var strRemarks=listSODtl.strRemarks;
	    if(strRemarks==null)
    	{
	    	strRemarks="";
    	}
	    
	    var strPartNo=listSODtl.strPartNo;
	    if(strPartNo==null)
    	{
	    	strPartNo="";
    	}
	    
	    var prodPckNo=listSODtl.strPropNLocCode;
	    if(prodPckNo==null)
	    {
	    	prodPckNo="";
	    }
	    
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" type=\"hidden\" class=\"Box\"  style=\"display:none;\"  id=\"strPropNLocCode."+(rowCount)+"\" value='"+prodPckNo+"' />";
	    row.insertCell(1).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
	    row.insertCell(2).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strPartNo\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtPartNo."+(rowCount)+"\" value='"+strPartNo+"'/>";
	    row.insertCell(3).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"35%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
	    row.insertCell(4).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strProdType\" readonly=\"readonly\" class=\"Box\" size=\"9%\" id=\"txtProdTpye."+(rowCount)+"\" value='"+strProdType+"'/>";
	 
	    row.insertCell(5).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].dblQty\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtQty."+(rowCount)+"\" value="+dblQty+" onblur=\"Javacsript:funUpdatePrice(this)\">";
	    row.insertCell(6).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].dblWeight\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtWeight."+(rowCount)+"\" value="+dblWeight+" >";
	    row.insertCell(7).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].dblTotalWeight\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" \size=\"3.9%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+dblTotalWeight+"'/>";
	    row.insertCell(8).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strPktNo\" type=\"text\"    class=\"Box\" \size=\"5%\" id=\"txtPktNo."+(rowCount)+"\" value="+packingNo+" >";
	    row.insertCell(9).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strRemarks\" size=\"10%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
	    row.insertCell(10).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strInvoiceable\" readonly=\"readonly\" class=\"Box\"  size=\"6%\" id=\"txtInvoiceable."+(rowCount)+"\" value="+strInvoiceable+" >";
	    row.insertCell(11).innerHTML= "<input name=\"listclsDeliveryChallanModelDtl["+(rowCount)+"].strSerialNo\" type=\"text\"    class=\"Box\" size=\"5%\" id=\"txtSerialNo."+(rowCount)+"\" value="+strSerialNo+" >";	    
	    
	 
	 	row.insertCell(12).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';		    
	    
	    
	 	 $("#txtSubGroup").focus();
		    funClearProduct();
		    funGetTotal();
		    return false;
		
	}
	
	$(function()
			{
				$("#txtDCCode").blur(function() 
						{
							var code=$('#txtDCCode').val();
							if(code.trim().length > 0 && code !="?" && code !="/")
							{
								funSetDeliveryChallanData(code);
							}
						});
				
				$('#txtCustCode').blur(function () {
					var code=$('#txtCustCode').val();
					if (code.trim().length > 0 && code !="?" && code !="/"){
						   funSetCuster(code);
					   }
					});
				
				$('#txtLocCode').blur(function () {
					var code=$('#txtLocCode').val();
					if (code.trim().length > 0 && code !="?" && code !="/"){
						funSetLocation(code);
					   }
					});
				
				$('#txtProdName').blur(function () {
					var code=$('#hidProdCode').val();
					if (code.trim().length > 0 && code !="?" && code !="/"){								  
						funSetProduct(code);
					   }
					});
				
				
			});
	
	
	
	
	
	
	$(document).ready(function()
			{
				$(function() {
					
					$("#txtSubGroup").autocomplete({
					source: function(request, response)
					{
						var searchUrl=getContextPath()+"/AutoCompletGetSubgroupNameForDC.html";
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
						source: function(request, response)
						{
							var sgName= $("#txtSubGroup").val();
							var custCode=$("#txtCustCode").val();
							var searchUrl=getContextPath()+"/AutoCompletGetProdNamewithSubgroup.html";
							$.ajax({
							url: searchUrl,
							type: "POST",
							data: { term: request.term,sgName:sgName,custcode:custCode},
							dataType: "json",
							 
								success: function(data) 
								{
									prodData=data;
									response($.map(data, function(v,i)
									{
										return {
											label: v.strProdName,
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
	       		$('#txtLocCode').focus();
	       		}
	    	
	    }
	    
	    if(controller=='Location' && key==13 || controller=='Location' && key==9)
	    {
	    	if($("#txtLocCode").val().trim().length==0 || $("#txtLocCode").val()== 0)
	        {		
		          alert("Please Select Location");
		          $("#txtLocCode").focus();
	          return false;
	       	}else
	       		{
	    		document.getElementById('cmbSettlement').focus();
	       		}
	    }
	    
	    if(controller=='Settlement' && key==13 || controller=='Settlement' && key==9)
	    {
	    	document.getElementById('txtSubGroup').focus();
	    }
	    
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
	    
	 	    	$.each(prodData, function(i,item)
	 			    	{
	 				var prodName = $("#txtProdName").val();
	 						if(prodName==item.strProdName)
	 							{
	 							$('#hidProdCode').val(item.strProdCode);
	 							$('#txtWeight').val(item.dblWeight);
	 							$('#txtRemarks').val(item.strRemark);
	 							$('#lblUOM').text(item.strUOM);
	 							}
	 			    	});
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
		       	}else{
		       		funAddProductRow();
			    	$("#txtSubGroup").focus();
		       		
		       	}
	    	
	    }
	}
	
		function funChangeCombo() {
			$("#txtSubGroup").focus();
			}
	
$(document).keypress(function(e) {
	  if(e.keyCode == 120) {
		  if(funCallFormAction())
			  {
			 	 document.forms["SOForm"].submit();
			  }
		 
	  }
	});
	
/**
 * Get product stock passing value product code
 */
function funGetProductStock(strProdCode)
{
	var searchUrl="";	
	var locCode=$("#txtLocCode").val();
	var dblStock="0";
	var strDCDate=$("#txtDCDate").val();
	strDCDate=strDCDate.split("-")[2]+"-"+strDCDate.split("-")[1]+"-"+strDCDate.split("-")[0];
//	searchUrl=getContextPath()+"/getProductStock.html?prodCode="+strProdCode+"&locCode="+locCode+"&strMISDate="+strDCDate;	
	searchUrl=getContextPath()+"/getProductStockInUOM.html?prodCode="+strProdCode+"&locCode="+locCode+"&strTransDate="+strDCDate+"&strUOM=RecUOM";
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



function funHelp1(){
	
	var agianst = $("#cmbAgainst").val();
	if(agianst=="Sales Order")
		{
		funHelp('salesorder');
		}
	else if(agianst=="Invoice")
	   {
		funHelp('invoiceForDC');
	   }	
}



</script>

</head>
<body onload="funOnLoad();">
	<div id="formHeading">
		<label>Delivery Challan</label>
	</div>
	<s:form name="SOForm" method="POST"
		action="saveDeliveryChallan.html?saddr=${urlHits}">
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		<table
			style="border: 0px solid black; width: 100%; height: 100%; margin-left: auto; margin-right: auto; background-color: #C0E4FF;">
			<tr>
				<td>

					<div id="tab_container" style="height:600px">
						<ul class="tabs">
							<li class="active" data-state="tab1"
								style="width: 100px; padding-left: 55px">Delivery Challan</li>
							<li data-state="tab2" style="width: 100px; padding-left: 55px">Address</li>

						</ul>

						<div id="tab1" class="tab_content">
							<table class="transTable">
								<tr>
									<th align="right" colspan="9"><a id="baseUrl" href="#">
											Attach Documents </a></th>
								</tr>

								<tr>
									<td width="100px"><label>DC Code</label></td>
									<td  colspan="3"><s:input path="strDCCode" id="txtDCCode"
											ondblclick="funHelp('deliveryChallan')"
											cssClass="searchTextBox" /></td>

									<td width="100px"><label>DC Date</label>
									<td><s:input path="dteDCDate" id="txtDCDate"
											required="required" cssClass="calenderTextBox" /></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td><label>Against</label></td>
									<td><s:select id="cmbAgainst" path="strAgainst"
											items="${againstList}" cssClass="BoxW124px" onchange="funShowSOFieled()"/></td>
											<td colspan="1"><s:input id="txtCode" path="strSOCode" style="display:none" ondblclick="funHelp1('')" class="searchTextBox"></s:input></td>
<%-- 									<td><s:input id="txtSOCode" path="strSOCode" --%>
<%-- 											ondblclick="funHelp('salesorder')" style="display:none" class="searchTextBox"></s:input></td> --%>
									<td  colspan="1"><input type="Button" id="btnFill" value="Fill"
										onclick="return btnFill_onclick()" style="display:none"  class="smallButton" /></td>

									<td width="100px"><label>Date</label>
									<td><s:input path="" id="txtAginst"
											cssClass="calenderTextBox" /></td>
								</tr>

								<tr>
									<td><label>Customer Code</label></td>
									<td  colspan="1"><s:input path="strCustCode" id="txtCustCode"
											ondblclick="funHelp('custMasterActive')" cssClass="searchTextBox" /></td>
									<td colspan="2"><label id="lblCustomerName"
										class="namelabel"></label></td>

									<td><label>PO NO</label></td>
									<td><s:input id="txtPONo" type="text" path="strPONo"
											class="BoxW116px" /></td>
								</tr>

								<tr>

									<td><label>Location Code</label></td>
									<td><s:input type="text" id="txtLocCode" path="strLocCode"
											cssClass="searchTextBox"
											ondblclick="funHelp('locationmaster');" onkeypress="funGetKeyCode(event,'Location')" /></td>
									<td colspan="2"><label id="lblLocName"></label></td>

									<td><label>Vechile No</label></td>
									<td ><s:input id="txtVehNo" type="text" path="strVehNo"
											cssClass="searchTextBox" ondblclick="funHelp('VehCode');"/></td>
								</tr>
								<tr>
								<td width="100px"><label>Settlement</label>
								<td><s:select id="cmbSettlement" path="strSettlementCode"
											items="${settlementList}" cssClass="BoxW124px" onkeypress="funGetKeyCode(event,'Settlement')" onchange="funChangeCombo()"/></td>
											
									<td width="100px"><label>Warrenty Start Date</label>
									<td><s:input path="strWarrPeriod" id="txtWarrPeriod"
											cssClass="calenderTextBox" /></td>

									<td width="100px"><label>Warranty Validity</label>
									<td><s:input path="strWarraValidity" id="txtWarraValidity"
											cssClass="calenderTextBox" /></td>

								</tr>

								<tr>
									<!-- <td width="100px"><label>Product</label></td>
									<td><input id="txtProdCode"
										ondblclick="funHelp('productProduced')" class="searchTextBox" /></td>
									<td align="left" colspan="2"><label id="lblProdName" class="namelabel"></label></td>
 -->							<td width="100px"><label>Sub-Group</label></td>
									<td><input id="txtSubGroup" style="width:80%;text-transform: uppercase;"  name="SubgroupName" class="searchTextBox" ondblclick="funHelp('subgroup')" 
									onkeypress="funGetKeyCode(event,'SubGroup')"
									/></td>
									<input type="hidden" id="hidSubGroupCode" />
									<td width="100px"><label>Product</label></td>
									<td><input id="txtProdName"
										ondblclick="funHelp('productProduced')" class="searchTextBox" onkeypress="funGetKeyCode(event,'Product')" /></td>
									
								
								<td>	<input type="hidden" id="hidProdCode" /></td>
								<input type="hidden" id="hidPartNo" /></td>
									<td  width="10%"><label>Stock</label></td><td  width="10%"><label id="spStock" class="namelabel"></label><span id="spStockUOM"></span></td>



								</tr>
								<tr>

									<td><label>Wt/Unit</label></td>
									<td><input type="text" id="txtWeight"
										class="decimal-places numberField" /></td>
									<td style="width: 115px"><label>Quantity</label></td>
									<td><input id="txtQty" type="text"
										class="decimal-places numberField" style="width: 60%" onkeypress="funGetKeyCode(event,'AddBtn')"  /><label id="lblUOM"></label></td>

									<td><label>Invoiceable</label></td>
									<td><s:select id="cmbInvoiceable" name="cmbInvoiceable"
											path="" cssClass="BoxW124px">
											<option value="N">No</option>
											<option value="Y">Yes</option>
										</s:select></td>

								</tr>

								<tr>
								<td><label>Packing No</label></td>
									<td><input id="txtPackingNo" type="text" class="BoxW116px" /></td>

									<td><label>One Packet Qty</label></td>
									<td><input id="txtPcketQty" type="text" class="BoxW116px" /></td>
									<td><label>Remarks</label></td>
									<td><input id="txtRemarks" class="longTextBox"
										style="width: 100%" /></td>
									<td><input type="button" value="Add" class="smallButton"
										onclick="return btnAdd_onclick()" /></td>
								</tr>
							</table>

							<div class="dynamicTableContainer" style="height: 300px;">
								<table
									style="height: 20px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
									<tr bgcolor="#72BEFC">
										<td width="5%">Product Code</td>
										<!--  COl1   -->
										<td width="5%">Part No</td>
										
										<td width="18%">Product Name</td>
										<!--  COl2   -->
										<td width="6%">Product Type</td>
										<!--  COl3   -->
										<td width="5%">Qty</td>
										<!--  COl4   -->
										<td width="4%">Wt/Unit</td>
										<!--  COl5   -->
										<td width="4%">Total Wt</td>
										<!--  COl6   -->
										<td width="5%">Packing No</td>
										<!--  COl7   -->
										<td width="6%">Remarks</td>
										<!--  COl8   -->
										<td width="3%">Invoice</td>
										<!--  COl9   -->
										<td width="4%">Serial No</td>
										<!--  COl10   -->
										
										<td width="3%">Delete</td>
										<!--  COl11   -->
									</tr>
								</table>
								<div
									style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
									<table id="tblProdDet"
										style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
										class="transTablex col15-center">
										<tbody>
										
										<col style="width:0%">
										<col style="width: 5%">
										<!--  COl1   -->
										<col style="width: 5%">
										
										<col style="width: 18%">
										<!--  COl2   -->
										<col style="width: 6%">
										<!--  COl3   -->
										<col style="width: 5%">
										<!--  COl4   -->
										<col style="width: 4%">
										<!--  COl5   -->
										<col style="width: 4%">
										<!--  COl6   -->
										<col style="width: 5%">
										<!--  COl7   -->
										<col style="width: 6%">
										<!--  COl8  -->
										<col style="width: 3%">
										<!--  COl9  -->
										<col style="width: 4%">
										<!--  COl10   -->
									
										<col style="width: 2.5%">
										<!--  COl11   -->
										
										<!--  COl11   -->

										</tbody>

									</table>
								</div>

							</div>


							<table class="transTable">
								<tr>
									<td><label>Narration</label></td>
									<td><s:textarea id="txtNarration" path="strNarration"
											Cols="10" rows="2" style="width:80%" /></td>
									<td><label>Pack No</label></td>
									<td><s:input id="txtPackNo" path="strPackNo" type="text"
											class="BoxW116px" /></td>
											<td><label>Serial No</label>&nbsp;&nbsp<s:input id="txtSerialNo" path="strSerialNo" type="text" class="BoxW116px" /></td>
								</tr>

								<tr>
									<td><label>Docket No of Courier</label></td>
									<td><s:input id="txtDktNo" path="strDktNo" type="text"
											class="BoxW116px" /></td>

									<td><label>Material Sent Out By</label></td>
									<td><s:input id="txtMInBy" path="strMInBy" type="text"
											class="BoxW116px" /></td>
								</tr>
								<tr>
									<td><label>Time Out</label></td>
									<td><s:input id="txtTimeOut" path="strTimeInOut" type="text"
											class="BoxW116px" /></td>
									<td><label>Reason Code</label></td>
									<td><s:input id="txtReaCode" path="strReaCode" type="text"
											class="BoxW116px" /></td>
								</tr>


							</table>

						</div>
						<div id="tab2" class="tab_content">
							<table class="transTable">
								<tr>
									<th colspan="2" align="left"><label>Ship To</label></th>
								</tr>

								<tr>
									<td width="120px"><label>Address Line 1</label></td>
									<td><s:input path="strSAdd1" id="txtSAddress1"
											cssClass="longTextBox" /></td>
								</tr>

								<tr>

									<td><label>Address Line 2</label></td>
									<td><s:input path="strSAdd2" id="txtSAddress2"
											cssClass="longTextBox" /></td>
								</tr>

								<tr>

									<td><label>City</label></td>
									<td><s:input path="strSCity" id="txtSCity"
											cssClass="BoxW116px" /></td>
								</tr>

								<tr>

									<td><label>State</label></td>
									<td><s:input path="strSState" id="txtSState"
											cssClass="BoxW116px" /></td>
								</tr>

								<tr>

									<td><label>Country</label></td>
									<td><s:input path="strSCtry" id="txtSCountry"
											cssClass="BoxW116px" /></td>
								</tr>

								<tr>

									<td><label>Pin Code</label></td>
									<td><s:input path="strSPin" id="txtSPin"
											class="positive-integer BoxW116px" /></td>
								</tr>
							</table>
						</div>

					</div>
				</td>
			</tr>
		</table>
	<br>

		<div align="center">
			<input type="submit" value="Submit"
				onclick="return funCallFormAction('submit',this)"
				class="form_button" /> &nbsp; &nbsp; &nbsp; <input type="button"
				id="reset" name="reset" value="Reset" class="form_button" />
		</div>
		<s:input type="hidden" id="hidProdType" path="strProdType"></s:input>
		
		
		<br>
		
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
	</s:form>
	<script type="text/javascript">
		funApplyNumberValidation();
	</script>
</body>


</html>