<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="sp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>MATERIAL REQUISITION</title>

<script type="text/javascript">
 	
 	/**
	 * Ready Function for Ajax Waiting
	 */
	$(document).ready(function() {
		resetForms('StandardRequisition');
		$("#txtProdCode").focus();
		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});

	});
</script>
  
<script type="text/javascript">

/**
 * Global Variable
 */
var fieldName,strLocationType,listRow=0,showReqVal="",showReqStk="";	

	/**
	 * Ready Function for Initialize textField with default value
	 * And Set date in date picker 
	 * And Getting session Value
	 * Success Message after Submit the Form
	 */
		$(function() 
				{
					$("#txtReqDate").datepicker({ dateFormat: 'dd-mm-yy' });		
					$("#txtReqDate").datepicker('setDate', 'today');
				    var dates = $("#txtReqiredDate").datepicker({ 
				    	minDate: "0",
				    	maxDate: "+2Y", 
				    	defaultDate: "+1w", 
				    	dateFormat: 'dd-mm-yy', 
				    	numberOfMonths: 1, 
				    	onSelect: function(date) 
				    	{
				    		for(var i = 0; i < dates.length; ++i) 
				    		{	 
				    			if(dates[i].id < this.id)
				    				$(dates[i]).datepicker('option', 'maxDate', date);
				    			else if(dates[i].id > this.id) $(dates[i]).datepicker('option', 'minDate', date); 
				    			}
				    		}
				       });
		    		$("#txtReqiredDate" ).datepicker('setDate', 'today');
					showReqVal='<%=session.getAttribute("ShowReqVal").toString()%>';
					showReqStk='<%=session.getAttribute("ShowReqStk").toString()%>';
					strChangeUOM='<%=session.getAttribute("changeUOM").toString()%>';
					if(strChangeUOM!="N")
						{
							$("#cmbUOM").prop("disabled", false);
						}
					else
						{
							$("#cmbUOM").prop("disabled", true);
						}
					
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
									alert("Data Save successfully\n\n"+message,function(){});
						<%
						}
						else
						{
							%>	
							alert(message);
				<%}
				}%>
				
				
					
						<%if (session.getAttribute("success1") != null) {
				            if(session.getAttribute("successMessage1") != null){%>
				            message='<%=session.getAttribute("successMessage1").toString()%>';
				            <%
				            session.removeAttribute("successMessage1");
				            }
							boolean test1 = ((Boolean) session.getAttribute("success1")).booleanValue();
							session.removeAttribute("success1");
							if (test1) {
							%>	
							alert(message);
						<%
						}}%>
						var code='';
						<%if(null!=session.getAttribute("rptReqCode")){%>
						code='<%=session.getAttribute("rptReqCode").toString()%>';
						<%session.removeAttribute("rptReqCode");%>
						var isOk=confirm("Do You Want to Generate Slip?",'');
						if(isOk){
						window.open(getContextPath()+"/openRptMaterialReqSlip.html?rptReqCode="+code,'_blank');
						}
						<%}%>
						
						var flagOpenFromAuthorization="${flagOpenFromAuthorization}";
						if(flagOpenFromAuthorization == 'true')
						{
							funSetReqData("${authorizationReqCode}");
						}
						$("#txtLocBy").val("${locationCode}");
				    	$("#lblLocBy").text("${locationName}");
				    	strLocationType="${locationType}";
				    	var loc=$("#txtLocBy").val();
				    	if(loc!='')
				    	{
				    		funLoadStandReqByLocation(loc);
				    	}
						
				});
	
	/**
	 * Number field Validation
	 */
	function funApplyNumberValidation()
	{
		$(".numeric").numeric();
		$(".integer").numeric(false, function() {alert("Integers only"); this.value = ""; this.focus(); });
		$(".positive").numeric({ negative: false }, function() {alert("No negative values"); this.value = ""; this.focus(); });
		$(".positive-integer").numeric({ decimal: false, negative: false }, function() {alert("Positive integers only"); this.value = ""; this.focus(); });
	    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
	}
	
		
		/**
		 * Open Help Form 
		 */
		function funHelp(transactionName)
		{
			fieldName=transactionName;
		
			if("productInUse"==transactionName)
				{
					if($("#txtLocOn").val()=="")
						{
							alert("Please Select To Location");
							return false;
						}
					else
						{
					//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1100px;dialogLeft:200px;")
						window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px,dialogWidth:1100px,top=500,left=500")
						
						}
				}
			else if("ProductionOrder"==transactionName)
				{
				//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
					window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px,dialogWidth:1000px,top=500,left=500")
				}
			
			else
				{
					//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
					window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600,dialogWidth:1000,top=500,left=500")
				}
			 
	    }
		
		/**
		 * Location Help Open based on "Only Store or other Location Type"
		 */
		function funLocationOnOpen(transactionName)
		   {
				fieldName=transactionName;
				//alert("strLocationType"+strLocationType);
		        if(strLocationType=="Stores")
		        {
		        //	window.showModalDialog("searchform.html?formname=LocationToAllPropertyStore&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
		        	window.open("searchform.html?formname=LocationToAllPropertyStore&searchText=","","dialogHeight:600px,dialogWidth:1000px,dialogLeft:200px")		        }
		        else if(strLocationType=="Cost Center" || strLocationType=="Profit Center")
		        {
		        //	window.showModalDialog("searchform.html?formname=StoreLocationTo&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
		        window.open("searchform.html?formname=StoreLocationTo&searchText=","","dialogHeight:600px,dialogWidth:1000px,dialogLeft:200px,")
		        }
		        else
		        {
		        //	window.showModalDialog("searchform.html?formname=locon&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		        	window.open("searchform.html?formname=locon&searchText=","","dialogHeight:600px,dialogWidth:600px,dialogLeft:400px,")
		        }
		   }
		
		/**
		 * Set Data after selecting form Help windows
		 */
		function funSetData(code)
		{					
			var searchUrl="";			
			switch (fieldName)
			{
				case 'locby':
					funSetLocationBy(code);					
				break;				
				case 'locon':
					funSetLocationOn(code);					
				break;				
				case 'productInUse':
					funSetProduct(code);				
					break;					
				case 'MaterialReq':
					//alert(fieldName);
					funSetReqData(code);
	 				break;
				case 'ProductionOrder':
					$("#txtWOCode").val(code);
					break;	
	 				
			}
				
		}
		
		function funHelpAgainst()
		{
			if($("#cmbAgainst").val()=="Work Order")
			 {
				funHelp('');
			 }
			if($("#cmbAgainst").val()=="Production Order")
			{
				funHelp('ProductionOrder');
			}
		}
		
		/**
		 * Get and set Location By Data Based on passing Location Code
		 */
		function funSetLocationBy(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadLocationMasterData.html?locCode="+code;			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	$("#txtLocBy").val(response.strLocCode);
				    	$("#lblLocBy").text(response.strLocName);
		        		strLocationType=response.strType;
		        		funLoadStandReqByLocation(response.strLocCode);
		        		
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
		
		
		function funLoadStandReqByLocation(strLocation)
		{
			
			var searchUrl="";
			searchUrl=getContextPath()+"/loadStandardReqData.html?locCode="+strLocation;			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
						funAddProdDtailData(response);
						
		        		
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
		
		
		function funAddProdDtailData(data)
		{
			$('#tblProdDet tbody').empty()
			  var table = document.getElementById("tblProdDet");
			  
			    for(var i=0;i<data.length;i++)
			    {
			    var StandReqData=data[i];
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);

	 			row.insertCell(0).innerHTML= "<input class=\"Box\" size=\"10%\" name=\"listReqDtl["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value='"+StandReqData[0]+"' />";
				row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"60%\" name=\"listReqDtl["+(rowCount)+"].strProdName\" id=\"strProdName."+(rowCount)+"\" value='"+StandReqData[1]+"' />";
				row.insertCell(2).innerHTML= "<input class=\"Box\" readonly=\"readonly\" size=\"5%\" name=\"listReqDtl["+(rowCount)+"].strUOM\" id=\"strUOM."+(rowCount)+"\" value='"+StandReqData[2]+"' />";
			    row.insertCell(3).innerHTML= "<input type=\"text\"  required = \"required\" required = \"required\" name=\"listReqDtl["+(rowCount)+"].dblQty\" id=\"dblQty."+(rowCount)+"\" value="+StandReqData[3]+" onblur=\"funUpdatePrice(this);\"  class=\"decimal-places inputText-Auto QtyCell\">";	    
			    row.insertCell(4).innerHTML= "<input type= type=\"text\" readonly=\"readonly\" required = \"required\" name=\"listReqDtl["+(rowCount)+"].dblUnitPrice\" id=\"dblUnitPrice."+(rowCount)+"\" value="+parseFloat(StandReqData[4]).toFixed(parseInt(maxAmountDecimalPlaceLimit))+"  class=\"decimal-places inputText-Auto\">";		    
			    row.insertCell(5).innerHTML= "<input type= readonly=\"true\" class=\"Box totalValueCell\" style=\"text-align: right;\" size=\"10%\" name=\"listReqDtl["+(rowCount)+"].dblTotalPrice\" id=\"dblTotalPrice."+(rowCount)+"\" value="+parseFloat(StandReqData[5]).toFixed(parseInt(maxAmountDecimalPlaceLimit))+">";
			    row.insertCell(6).innerHTML= "<input size=\"17%\" name=\"listReqDtl["+(rowCount)+"].strRemarks\" id=\"txtRemarks."+(rowCount)+"\" value='"+StandReqData[6]+"'>";		    
			    row.insertCell(7).innerHTML= '<input type="button" value = "Delete"  class="deletebutton" onClick="Javacsript:funDeleteRow(this)">'; 
			    }  
		}
		/**
		 * Get and set location on data based on passing location code
		 */
		function funSetLocationOn(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadLocationMasterData.html?locCode="+code;			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	$("#txtLocOn").val(response.strLocCode);
				    	$("#lblLocOn").text(response.strLocName);
		        		
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
		
		/**
		 * Get and set prodcut data based on product code
		 */
		function funSetProduct(code)
		{
			var searchUrl="";
			//alert(showReqStk);
			searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
			//alert(searchUrl);
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if('Invalid Code' == response.strProdCode){
				    		alert('Invalid Product Code');
					    	$("#txtProdCode").val('');
					    	$("#spProdName").text('');
					    	$("#txtProdCode").focus();
				    	}
				    	else
				    	{
				    		
					    	$("#txtProdCode").val(response.strProdCode);
					    	$("#spProdName").text(response.strProdName);
	 						$("#hidNonStkableItem").val(response.strNonStockableItem);
	 						if(showReqVal=="N")
	 				    	{
	 							$("#txtUnitPrice").val("0");
	 							$("#txthidUnitPrice").val(response.dblCostRM);
	 				    	}
	 						else
	 						{
	 							$("#txtUnitPrice").val(response.dblCostRM);
	 						}
							$("#cmbUOM").val(response.strIssueUOM);
							$("#txtProdQty").focus();
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
		
		/**
		 * Get product stock passing value product code
		 */
		function funGetProductStock(strProdCode)
		{
			var searchUrl="";	
			var locCode=$("#txtLocOn").val();
			var dblStock="0";
			searchUrl=getContextPath()+"/getProductStock.html?prodCode="+strProdCode+"&locCode="+locCode;	
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
			return dblStock;
		}
		
		/**
		 * Get and set Requisition Data
		 */
		function funSetReqData(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadReqData.html?ReqCode="+code;
			//alert(searchUrl);
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if(response.strReqCode!="Invalid Requisition Code")
				    		{
						    	$("#txtreqCode").val(response.strReqCode);
						    	funFillData(response);
				    		}
				    	else
				    		{
				    			alert("Invalid Requisition Code");
				    			$("#txtreqCode").val('');
				    			$("#txtreqCode").focus();
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
		
		/**
		 * Set data in Textfield  
		 */
		function funFillData(response)
		{
										
        				$("#txtreqCode").val(response.strReqCode);
        				$("#txtReqDate").val(response.dtReqDate);
        				$("#txtLocBy").val(response.strLocBy); 
        				$("#lblLocBy").text(response.strLocByName);
        				$("#txtLocOn").val(response.strLocOn);
        				$("#lblLocOn").text(response.strLocOnName);
        				$("#cmbAgainst").val(response.strAgainst);
        				$("#txtWOCode").val(response.strWoCode);
        				$("#txtWoQty").val(response.dblWOQty);
        				if(response.strCloseReq=='Y')
        					{
        						$("#cbCloseReq").prop('checked',true);
        					}
        				else
        					{
        						$("#cbCloseReq").prop('checked',false);	
        					}				        				
        				$("#txtNarration").val(response.strNarration);
        				$("#hidAuthorise").val(response.strAuthorise);
        				$("#hidReqFrom").val(response.strReqFrom);
        				$("#txtProdCode").focus();
        				
        				funGetProdData1(response.clsRequisitionDtlModel);
		}
		
		
		function funGetProdData1(response)
		{
			var count=0;
		        	funRemoveProductRows();
					$.each(response, function(i,item)
                    {		
						count=i;
						funfillProdRow(response[i].strProdCode,response[i].strPartNo,response[i].strProdName,response[i].dblQty,response[i].dblUnitPrice,response[i].dblTotalPrice,response[i].strRemarks,response[i].strUOM);
                                                   
                    });
			listRow=count+1;
			funCalSubTotal();
				
		}
		
		/**
		 * Filling product data in grid
		 */
		function funfillProdRow(strProdCode,strPosItemCode,strProdName,dblQty,dblUnitPrice,dblTotalPrice,strRemarks,strUOM)
		{	   
		    var table = document.getElementById("tblProdDet");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    dblQty=  parseFloat(dblQty).toFixed(parseInt(maxQuantityDecimalPlaceLimit));
		    dblUnitPrice = parseFloat(dblUnitPrice).toFixed(parseInt(maxAmountDecimalPlaceLimit));
		    dblTotalPrice = parseFloat(dblTotalPrice).toFixed(parseInt(maxAmountDecimalPlaceLimit));
		    //alert(dblTotalPrice);
 			row.insertCell(0).innerHTML= "<input class=\"Box\" size=\"10%\" name=\"listReqDtl["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";
			row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"60%\" name=\"listReqDtl["+(rowCount)+"].strProdName\" id=\"strProdName."+(rowCount)+"\" value='"+strProdName+"' />";
			row.insertCell(2).innerHTML= "<input class=\"Box\" readonly=\"readonly\" size=\"5%\" name=\"listReqDtl["+(rowCount)+"].strUOM\" id=\"strUOM."+(rowCount)+"\" value='"+strUOM+"' />";
		    row.insertCell(3).innerHTML= "<input type=\"text\"  required = \"required\" required = \"required\" name=\"listReqDtl["+(rowCount)+"].dblQty\" id=\"dblQty."+(rowCount)+"\" value="+parseFloat(dblQty).toFixed(parseInt(maxAmountDecimalPlaceLimit))+" onblur=\"funUpdatePrice(this);\"  class=\"decimal-places inputText-Auto QtyCell\">";	    
		    if(showReqVal=="N")
	    	{
		    	 row.insertCell(4).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" required = \"required\" name=\"listReqDtl["+(rowCount)+"].dblUnitPrice\" id=\"dblUnitPrice."+(rowCount)+"\" value="+parseFloat(dblUnitPrice).toFixed(parseInt(maxAmountDecimalPlaceLimit))+"  class=\"decimal-places inputText-Auto\">";		    
				 row.insertCell(5).innerHTML= "<input type=\"hidden\" readonly=\"true\" class=\"Box totalValueCell\" style=\"text-align: right;\" size=\"10%\" name=\"listReqDtl["+(rowCount)+"].dblTotalPrice\" id=\"dblTotalPrice."+(rowCount)+"\" value="+parseFloat(dblTotalPrice).toFixed(parseInt(maxAmountDecimalPlaceLimit))+">";
	    	}
		    else
		    {
		    	 row.insertCell(4).innerHTML= "<input type=\"text\" readonly=\"readonly\" required = \"required\" name=\"listReqDtl["+(rowCount)+"].dblUnitPrice\" id=\"dblUnitPrice."+(rowCount)+"\" value="+parseFloat(dblUnitPrice).toFixed(parseInt(maxAmountDecimalPlaceLimit))+"  class=\"decimal-places inputText-Auto\">";		    
				 row.insertCell(5).innerHTML= "<input readonly=\"true\" class=\"Box totalValueCell\" style=\"text-align: right;\" size=\"10%\" name=\"listReqDtl["+(rowCount)+"].dblTotalPrice\" id=\"dblTotalPrice."+(rowCount)+"\" value="+parseFloat(dblTotalPrice).toFixed(parseInt(maxAmountDecimalPlaceLimit))+">";
		    }
		    row.insertCell(6).innerHTML= "<input size=\"17%\" name=\"listReqDtl["+(rowCount)+"].strRemarks\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'>";		    
		    row.insertCell(7).innerHTML= '<input type="button" value = "Delete"  class="deletebutton" onClick="Javacsript:funDeleteRow(this)">'; 
		   
		}
		
		/**
		 * Check validation before adding product data in grid
		 */
		function btnAdd_onclick() 
		{			
			
			if($("#txtProdCode").val().trim().length==0)
		    {
				  	alert("Please Enter Product Code Or Search");
				  	//alert("Please Enter Product Code Or Search");
			     	$('#txtProdCode').focus();
			     	return false; 
		    }
			
		    if(($("#txtProdQty").val().trim().length==0  ))
			 {
					  alert("Please Enter Quantity ");
				       $('#txtProdQty').focus();
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

		/**
		 * Check duplicate record in grid
		 */
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
					    	 funClearReqData();
		    				flag=false;
	    				}
					});
				    
		    	}
		    return flag;
		  
		}
		
			/**
			 * Adding Product Data in grid 
			 */
			function funAddProductRow() 
			{			
				var dblUnitPrice=0;
				var strProdCode = $("#txtProdCode").val().trim();
				var strProdName=$("#spProdName").text();
				var strUOM=$("#cmbUOM").val();
				var strNonStkableItem=$("#hidNonStkableItem").val();
				var dblProdQty = $("#txtProdQty").val();
				var dblProdQty1=parseFloat(dblProdQty).toFixed(parseInt(maxQuantityDecimalPlaceLimit));
				if(showReqVal=="N")
			    {
				   	dblUnitPrice=$("#txthidUnitPrice").val();
			    }
				else
			    {
				   	 dblUnitPrice=$("#txtUnitPrice").val();
			    }
				var dblTotalPrice=parseFloat($("#txtProdQty").val()) * parseFloat(dblUnitPrice);
				dblTotalPrice=parseFloat(dblTotalPrice).toFixed(parseInt(maxAmountDecimalPlaceLimit));
				var strRemarks = $("#txtRemarks").val();
				     
				var table = document.getElementById("tblProdDet");
				var rowCount1 = table.rows.length;
				var row = table.insertRow(rowCount1);
				var rowCount=rowCount1;
				row.insertCell(0).innerHTML= "<input class=\"Box\" size=\"10%\" name=\"listReqDtl["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";
				row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"60%\" name=\"listReqDtl["+(rowCount)+"].strProdName\" id=\"strProdName."+(rowCount)+"\" value='"+strProdName+"' />";
				row.insertCell(2).innerHTML= "<input class=\"Box\" readonly=\"readonly\" size=\"5%\" name=\"listReqDtl["+(rowCount)+"].strUOM\" id=\"strUOM."+(rowCount)+"\" value='"+strUOM+"' />";
				row.insertCell(3).innerHTML= "<input type=\"text\" required = \"required\" name=\"listReqDtl["+(rowCount)+"].dblQty\" id=\"dblQty."+(rowCount)+"\"  value="+dblProdQty1+" onblur=\"funUpdatePrice(this);\"  class=\"decimal-places inputText-Auto QtyCell\" >";	    
				if(showReqVal=="N")
				{
				   	row.insertCell(4).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" required = \"required\" name=\"listReqDtl["+(rowCount)+"].dblUnitPrice\" id=\"dblUnitPrice."+(rowCount)+"\"  value="+dblUnitPrice+"  class=\"decimal-places inputText-Auto\">";		    
				    row.insertCell(5).innerHTML= "<input type=\"hidden\" readonly=\"true\"  class=\"Box totalValueCell\" style=\"text-align: right;\" size=\"10%\" name=\"listReqDtl["+(rowCount)+"].dblTotalPrice\" id=\"dblTotalPrice."+(rowCount)+"\"  value="+dblTotalPrice+">";
				}
				else
				{
				    row.insertCell(4).innerHTML= "<input type=\"text\" readonly=\"readonly\" required = \"required\" name=\"listReqDtl["+(rowCount)+"].dblUnitPrice\" id=\"dblUnitPrice."+(rowCount)+"\"  value="+dblUnitPrice+"  class=\"decimal-places inputText-Auto\">";		    
					row.insertCell(5).innerHTML= "<input class=\"Box totalValueCell\" style=\"text-align: right;\" size=\"10%\" name=\"listReqDtl["+(rowCount)+"].dblTotalPrice\" id=\"dblTotalPrice."+(rowCount)+"\" readonly=\"true\"  value="+dblTotalPrice+">";
				}
					
				row.insertCell(6).innerHTML= "<input size=\"17%\" name=\"listReqDtl["+(rowCount)+"].strRemarks\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'>";		    
				row.insertCell(7).innerHTML= '<input type="button" value = "Delete"  class="deletebutton" onClick="Javacsript:funDeleteRow(this)">'; 
	 			row.insertCell(8).innerHTML= "<input type=\"hidden\" size=\"0%\" name=\"listReqDtl["+(rowCount)+"].strNonStockableItem\"  value='"+strNonStkableItem+"' />";
	 			listRow++;
	 			
	 			funClearReqData();
				funCalSubTotal();
				funApplyNumberValidation();
				return false;
			}
			
			/**
			 * Delete a particular record from a grid
			 */
			function funDeleteRow(obj)  
			{
			    var index = obj.parentNode.parentNode.rowIndex;
			    var table = document.getElementById("tblProdDet");
			    table.deleteRow(index);
			    funCalSubTotal();
			}
			
			/**
			 * Remove all product from grid
			 */
			function funRemoveProductRows() 
		    {
				 var table = document.getElementById("tblProdDet");
				 var rowCount = table.rows.length;			   
				//alert("rowCount\t"+rowCount);
				for(var i=rowCount-1;i>=0;i--)
				{
					table.deleteRow(i);						
				} 
		    }
			
			/**
			 * Calcutating subtotal
			 */
			function funCalSubTotal()
		    {
				funApplyNumberValidation();
		        var dblQtyTot=0;		        
		        var subtot=0;
		        $('#tblProdDet tr').each(function() {
				    var totalvalue = $(this).find(".totalValueCell").val();
				    var qty=$(this).find(".QtyCell").val();
				    subtot = parseFloat(subtot + parseFloat(totalvalue));
				    dblQtyTot = parseFloat(dblQtyTot) + parseFloat(qty);
				    
				 });
								
				$("#txtTotalAmount").val(parseFloat(subtot).toFixed(parseInt(maxAmountDecimalPlaceLimit)));  	
				$("#txtTotalQty").val(parseFloat(dblQtyTot).toFixed(parseInt(maxQuantityDecimalPlaceLimit)));
				
		    }
			
			/**
			 * Update total price when user change the qty 
			 */
			function funUpdatePrice(object)
			{
				var index=object.parentNode.parentNode.rowIndex;
				var price=parseFloat(document.getElementById("dblUnitPrice."+index).value)*parseFloat(object.value);
				
				document.getElementById("dblTotalPrice."+index).value=price.toFixed(parseInt(maxAmountDecimalPlaceLimit));				
				funCalSubTotal();
			}
			
			/**
			 * Clear textfiled after adding data in textfield
			 */
			function funClearReqData()
			{
				document.getElementById("txtProdCode").value="";
// 				document.getElementById("spPartNo").innerHTML="";
				document.getElementById("spProdName").innerHTML="";
				document.getElementById("txtProdQty").value="";
				document.getElementById("txtUnitPrice").value="";
				document.getElementById("txtRemarks").value="";
				$("#spStock").text("");
	    		$("#spStockUOM").text("");
	    		document.getElementById("txtProdCode").focus();
			}

			/**
			 * Update Issue UOM in product master 
			 */
			function funUpdateIssueUOM()
			{
				var strProdCode = $("#txtProdCode").val();				
				var strUOM=$("#cmbUOM").val();
				if(strUOM!="")
				{
				var transaction="Req";
				var searchUrl=getContextPath()+"/updateUOMData.html?strProdCode="+strProdCode+"&strUOM="+strUOM+"&transaction="+transaction;
				$.ajax({			
		        	type: "GET",
			        url: searchUrl,
			        dataType: "text",
			        success: function(response)
			        {	
			        	//alert(response);
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
			
						
			/**
			 * Checking Validation before submiting the data
			 */
			function funCallFormAction(actionName,object) 
			{	
				if(!fun_isDate($("#txtReqDate").val())){
						alert('Invalid Requisition Date');
			            $("#txtReqDate").focus();
			            return false;
				 }
				if(!fun_isDate($("#txtReqiredDate").val()))
			    {
					alert('Invalid Required Date');
					$("#txtReqiredDate").focus();
			    	return false;
			    } 
				 var spReqDate=$("#txtReqDate").val().split('-');
				 var spRequiredDate=$("#txtReqiredDate").val().split('-');
				 var ReqDate= new Date(spReqDate[2],spReqDate[1]-1,spReqDate[0]);
				 var RequiredDate = new Date(spRequiredDate[2],spRequiredDate[1]-1,spRequiredDate[0]);
				 if(RequiredDate<ReqDate)
				    {
				    	alert("Required Date Should Not Be Less Than Requisition Date");
				    	$("#txtReqiredDate").focus();
						return false;		    	
				    }
				var table = document.getElementById("tblProdDet");
				var rowCount = table.rows.length;
				
				if($("#txtLocBy").val()=='')
				{
					alert("Please Enter Location By or Search");
					$("#txtLocBy").focus();
					return false;
				}
				else if($("#txtLocOn").val()=='')
				{
					alert("Please Enter Location To or Search");
					$("#txtLocOn").focus();
					return false;
				}
				else if($("#txtLocBy").val()==$("#txtLocOn").val())
				{
					alert("Location By and Location On cannot be same");
					return false;
				}
				else if(rowCount==0)
				{
					alert("Please Add Product in Grid");
					return false;
				}
				 if(  $("#cmbAgainst").val() == null || $("#cmbAgainst").val().trim().length == 0 )
				 {
				 		alert("Please Select Against");
						return false;
				 }
				
				
				else
				{
					return true;
					
				}
			}
			
			/**
			 * Combo box change event
			 */
			function funOnChange()
			{
				
						 if($("#cmbAgainst option:selected").text()=="Direct")
							 {
							 	$('#txtWOCode').css('visibility','hidden');
							 	$("#txtWoQty").css('visibility','hidden');
							 	$("#lblQty").css('visibility','hidden');
							 	$("#btnFill").css('visibility','hidden');
							 }
						 else if($("#cmbAgainst").val()=="Work Order")
							 {
							    $('#txtWOCode').css('visibility','visible');
							 	$("#txtWoQty").css('visibility','visible');
							 	$("#lblQty").css('visibility','visible');
							 	$("#btnFill").css('visibility','visible');
							 }else if($("#cmbAgainst").val()=="Production Order")
							{
								 $('#txtWOCode').css('visibility','visible')
								 $("#btnFill").css('visibility','visible');
								 $("#lblQty").css('visibility','hidden');
								 $("#txtWoQty").css('visibility','hidden');
							}
								 
						 
			}
			
			/**
			 * Open Autogenerate Requisition data 
			 */
			function funOpenAutoGeneratedReq()			
			{
		     //   response=window.showModalDialog("frmAutoGenReq.html","","dialogHeight:800px;dialogWidth:850px;dialogLeft:250px;");
		        response=window.open("frmAutoGenReq.html","","dialogHeight:800px;dialogWidth:850px;top=500,left=500");
		        var count=0;
		      /*   if(response!=null && response!="null")
		        	{
				        funRemoveProductRows();	
				        $.each(response, function(i,item)
			               { //alert(response[i].strUOM);
							 funfillProdRow(response[i].strProdCode,response[i].strPartNo,response[i].strProdName,response[i].dblOrderQty,response[i].dblPrice,response[i].dblOrderQty*response[i].dblPrice,"",response[i].strUOM);                
						   });
				        listRow=count+1;
			        	funCalSubTotal();	
		        	} */
		        
		        
		        
		        var timer = setInterval(function ()
					    {
						if(response.closed)
							{
								if (response.returnValue != null)
								{
									if(null!=response)
							        {
										response=response.returnValue;
							        	
							        	 funRemoveProductRows();	
							        	
								    	$.each(response, function(i,item)
										{		
								    		 funfillProdRow(response[i].strProdCode,response[i].strPartNo,response[i].strProdName,response[i].dblOrderQty,response[i].dblPrice,response[i].dblOrderQty*response[i].dblPrice,"",response[i].strUOM);
										});  
								    	listRow=count+1;
							        	funCalSubTotal();
							        }
				
								}
								clearInterval(timer);
							}
					    }, 500);
		        
		        
		        
			}
			
			
					/**
					 *  Ready function for Textfield on blur event
					 */
					$(function()
					{
						$("#txtreqCode").blur(function() 
								{
									var code=$('#txtreqCode').val();
									if(code.trim().length > 0 && code !="?" && code !="/")
									{
										funSetReqData(code);
									}
								});
						
						$('#txtLocOn').blur(function () {
							var code=$('#txtLocOn').val();
							if (code.trim().length > 0 && code !="?" && code !="/"){
								   funGetLocationName("txtLocOn","lblLocOn");
							   }
							});
						
						$('#txtLocBy').blur(function () {
							var code=$('#txtLocBy').val();
							if (code.trim().length > 0 && code !="?" && code !="/"){
								   funGetLocationName("txtLocBy","lblLocBy");
							   }
							});
						
						
// 						$('#txtProdCode').blur(function () {
// 							var code=$('#txtProdCode').val();
// 							if (code.trim().length > 0 && code !="?" && code !="/"){								  
// 								   funSetProduct(code);
// 							   }
// 							});

					$('#txtProdCode').keydown(function(e){
				var code=$('#txtProdCode').val();
				 if (e.which == 9){
				  	if (code.trim().length > 0) {
				  		funSetProduct(code);
				  	}
				  }
				if(e.which == 13)
					{
						if(code.trim().length > 8 )
							{
							funSetProduct(code);
							}
					}
			});

// 			$('#txtProdCode').blur(function() {
// 				var code = $('#txtProdCode').val();
// 				if ($("#cmbAgainst").val() == 'Purchase Order')
// 				{
// 					var POCode=$('#cmbPODoc').val();
// 					if(POCode.trim().length > 0)
// 						{
// 							if (code.trim().length > 0)
// 							{
// 								funSetProductForPO(code);
// 							}
// 						}
// 				}
// 				else
// 				{
// 					if(code.trim().length > 0 && code !="?" && code !="/"){
// 					funSetProduct(code);
// 					}
// 				}
// 			});
						
						/**
						 * Attached document Link
						 */
						$('a#baseUrl').click(function() 
								{
									if($("#txtreqCode").val().trim() == "")
									{
										alert("Please Select Requsition Code");
										return false;
									}
									window.open('attachDoc.html?transName=frmMaterialReq.jsp&formName=Material Requisition&code='+$("#txtreqCode").val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
								});
						
					});
			
			
			/**
			 * Geting and set Location Name passing value(textfiled id and label name)
			 */
			function funGetLocationName(txtFieldName,lblFieldName)
			{
				code=document.getElementById(txtFieldName).value;	
				var searchUrl=getContextPath()+"/loadLocationMasterData.html?locCode="+code;
				//alert(searchUrl);
				$.ajax({			
		        	type: "GET",
			        url: searchUrl,
			        dataType: "json",
			        success: function(response)
			        {	
			        	if(response.strLocCode=='Invalid Code')
				       	{
				       		alert("Invalid Location Code");
				       		$("#"+txtFieldName).val('');
				       		$('#'+txtFieldName).focus();
				       	}
				       	else
				       	{				       		
			        		document.getElementById(lblFieldName).innerHTML=response.strLocName;
			        		if(txtFieldName=="txtLocBy")
			        			{
			        				$('#txtLocOn').focus();
			        			}
			        		else
			        			{
			        				$("#txtProdCode").focus();
			        			}
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
			
			/**
			 * Reset function  
			 */
			function funResetFields()
			{
				location.reload(true); 				
		    }
			
			
			
			function btnFill_onclick()
			{
				if($("#txtWOCode").val()=="")
					{
						alert("Please Select Production Code !");
					}else{
						var code = $("#txtWOCode").val();
						funGetOPData(code);
					}
			}
			

		
// 		function funGetChildNodes(opCode,code)
// 		{
	function funGetOPData(code)
		{
			funRemoveProductRows() ;
		 	var searchUrl=getContextPath()+"/getOPChildNodes.html?OPCode="+code;
// 		 			+ "&ProdCode=" + code;
	    	
	    	$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {				    	
			    	$.each(response, function(i,itemDtl)
					{				    		
			    		funfillProdRow(itemDtl[0],"",itemDtl[1],itemDtl[3],itemDtl[4],itemDtl[5],'',itemDtl[2]);
						//funfillProdRow(strProdCode,strPosItemCode,strProdName,dblQty,dblUnitPrice,dblTotalPrice,strRemarks,strUOM)
						
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
	
	
	
	
			
	</script>

</head>
<body>
<div id="somediv"></div>
	<div id="formHeading">
		<label id="lblFormHeadingName">Standard Requisition</label>
	</div>
	<s:form name="StandardRequisition" method="POST" action="saveStandardRequisition.html?saddr=${urlHits}">
		<br>
		<s:input path="strAuthorise" type="hidden" id="hidAuthorise"  name="strAuthorise" value=""></s:input>
		<table class="transTable">
		</table>
		<table class="transTable">
			<tr>
				<td width="120px"><s:label path="strReqCode">Requisition Code</s:label></td>
				<td><s:input id="txtreqCode" name="txtreqCode"
						path="strReqCode" value="${matreq.strReqCode}"
						ondblclick="funHelp('MaterialReq')" cssClass="searchTextBox" /></td>
				<td></td>

 			</tr>

			<tr>
				<td><label>Location By</label></td>
				<td><s:input id="txtLocBy" name="txtLocBy" path="strLocBy" required = "true"
						value="${matreq.strLocBy}" ondblclick="funHelp('locby')"
						cssClass="searchTextBox" /></td>
				<td><label id="lblLocBy" class="namelabel"></label></td>

		</table>
		<table class="transTableMiddle">
			<tr>
				<td  width="120px"><label>Product Code</label></td>
				<td width="130px"><input id="txtProdCode" name="txtProdCode"
					ondblclick="funHelp('productInUse')" class="searchTextBox" /></td>
				<!-- 		        <td><label>Pos Item Code:</label> -->
				<!-- 		        <label id="spPartNo" class="namelabel"></label></td> -->
				
				<td colspan="9" id="spProdName" style="font-size: 12px;"></td>

			</tr>

			<tr>
				<td>Qty</td>
				<td ><input id="txtProdQty" type="text" class="decimal-places numberField"  /></td>
				<td width="5%">UOM</td>
				        <td width="5%"><s:select id="cmbUOM" name="cmbUOM"
						path="strUOM" items="${uomList}"   cssClass="BoxW124px"/></td>
				<td><label>Unit Price</label> </td>
				<td width="120px"><input id="txtUnitPrice"
					name="txtUnitPrice" disabled="disabled" type="number" step="any"
					class="BoxW116px right-Align decimal-2-places" /></td>
				<td><input id="txthidUnitPrice" type="hidden" class="decimal-places numberField"  /></td>
				<td>Remarks &nbsp;&nbsp;<input id="txtRemarks"
					name="txtRemarks" class="remarkTextBox" /></td>

				<td><input type="Button" value="Add"
					onclick="return btnAdd_onclick()" class="smallButton" /></td>
<td></td>
			</tr>

		</table>

		<div class="dynamicTableContainer" style="height: 300px;">

			<table style="height: 28px; border: #0F0; width: 100%;font-size:11px;
	font-weight: bold;">
				<tr bgcolor="#72BEFC" style="height: 24px;">
					<td width="4%"  style="padding-left: 4px">Prod Code</td>
					<!-- col1   -->
					<!-- 					<th width="12%">Pos Item Code</th>col2   -->
					<td width="18%" style="padding-left: 4px">Prod name</td>
					<!-- col3   -->
					<td width="3%" style="padding-left: 4px">UOM</td>
					<!-- col4   -->
					<td width="4%" align="right"style="padding-right: 4px">Qty</td>
					<!-- col5   -->
					<td width="4%"  align="right" style="padding-right: 4px">Unit Price</td>
					<!-- col6   -->
					<td width="4%"  align="right" style="padding-right: 4px">Total Price</td>
					<!-- col7  -->
					<td width="10%" align="left"  style="padding-left: 4px">Remarks</td>
					<!-- col8   -->
					<td width="5%" align="center">Delete</td>
					<td width="1%" align="center" style="display: none"></td>
					<!-- col9   -->
				</tr>
			</table>

			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">

				<table id="tblProdDet"
					style="width: 104%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col8-center">

					<tbody>
					<col style="width: 7%">
					<!-- col1   -->
					<%-- 					<col style="width: 11.1%"><!-- col2   --> --%>
					<col style="width: 32%">
					<!-- col3   -->
					<col style="width: 6%">
					<!-- col4   -->
					<col style="width: 7%">
					<!-- col5   -->
					<col style="width: 6%">
					<!-- col6   -->
					<col style="width: 8%">
					<!-- col7   -->
					<col style="width: 10%">
					<!-- col8   -->
					<col style="width: 15%">
					<!-- col9   -->
					<col style="width: 3%">
					<!-- col10   -->

					</tbody>
					
				</table>


			</div>
		</div>



		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit"
							onclick="return funCallFormAction('submit',this)"
				class="form_button" /> 
				<input type="button" value="Reset"
				onclick="funResetFields();" class="form_button" />
		</p>
<br><br>
	<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
			 <s:input type="hidden" id="hidReqFrom"   path="strReqFrom"   /> 
	</s:form>
	
	<script type="text/javascript">
    funApplyNumberValidation();
    funOnChange();
    </script>
</body>
</html>