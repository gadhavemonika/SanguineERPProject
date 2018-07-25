<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">

	/**
	 * Ready Function for tab index
	 */
	$(document).ready(function() {
		$(".tab_content").hide();
		$(".tab_content:first").show();

		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();

			var activeTab = $(this).attr("data-state");
			$("#" + activeTab).fadeIn();
		});
		//$('#tabDirect').trigger('click');
		
		/**
		 * Ready Function for Ajax Waiting and reset form
		 */
		$(document).ajaxStart(function(){
		    $("#wait").css("display","block");
		  });
		  $(document).ajaxComplete(function(){
		    $("#wait").css("display","none");
		  });
		  
	});
	
</script>
<script type="text/javascript">
	var fieldName;

	/**
	 * Ready Function for Initialize textField with default value
	 * And Set date in date picker 
	 */
	$(function() {
		$("#txtWODate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtWODate" ).datepicker('setDate', 'today');
		
		var code='<%=session.getAttribute("locationCode").toString()%>';
		funSetFromLocation(code);
		
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
		
		
		
// 		/**
// 		 * Checking Authorization
// 		 */
// 		var flagOpenFromAuthorization="${flagOpenFromAuthorization}";
// 		if(flagOpenFromAuthorization == 'true')
// 		{
// 			funGetStockTransferData("${authorizationSTCode}");
// 		}
		
		var code='';
		<%if(null!=session.getAttribute("rptStockTranferCode")){%>
		code='<%=session.getAttribute("rptStockTranferCode").toString()%>';
		<%session.removeAttribute("rptStockTranferCode");%>
		var isOk=confirm("Do You Want to Generate Slip?");
		if(isOk){
		window.open(getContextPath()+"/openRptStockTransferSlip.html?rptStockTranferCode="+code,'_blank');
		}
		<%}%>
		

	});
	
	/**
	 * On load Function for hiding the Textfield
	 */
	function funOnload()
	{
		//$("#txtSOCodeRO").css('visibility','hidden');
        $("#txtOPCode").css('visibility','hidden');      
        $("#btnGenerate").css('visibility','hidden');
		$("#submit").css('visibility','hidden');
		$("#reset").css('visibility','hidden');
	}
	
	/**
	 * Filling process grid
	 */
	function funAddRow(strProcessCode,strProcessName,strStatus,dblpendingQty) 
	{	
		funRemAllRows("tblProdDet");
	    var table = document.getElementById("tblProdDet");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
		
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].strProcessCode\" id=\"txtProcessCode."+(rowCount)+"\" value='"+strProcessCode+"' >";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].strProcessName\" id=\"txtProcessName."+(rowCount)+"\" value='"+strProcessName+"'>";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].strStatus\" id=\"txtProcessStatus."+(rowCount)+"\" value='"+strStatus+"'>";
	    row.insertCell(3).innerHTML= "<input type=\"number\" step=\"any\" readonly=\"readonly\" required = \"required\" style=\"text-align: right;width:100%\" size=\"10%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].dblPendingQty\" id=\"txtPendingQty."+(rowCount)+"\" value='"+dblpendingQty+"' > ";
	    row.insertCell(4).innerHTML= '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
	    return false;
	}
	/**
	 * Filling Work order grid
	 */
	function funAddWoRow(WoCode,prodCode,prodName,pendingQty,status) 
	{	
		funRemAllRows("tblWODet");
	    var table = document.getElementById("tblWODet");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	   
	    row.insertCell(0).innerHTML= "<input id=\"cbGSel."+(rowCount)+"\" type=\"checkbox\" class=\"GCheckBoxClass\" checked=\"checked\" name=\"WOthemes\" value='"+WoCode+"' onclick=\"funWOChkOnClick()\"/>";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].strWOCode\" id=\"strWOCode."+(rowCount)+"\" value="+WoCode+" >";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].strProdCode\" id=\"strProdCode."+(rowCount)+"\" value="+prodCode+" >";
	    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].strProdName\" id=\"strProdName."+(rowCount)+"\" value="+prodName+">";
	    row.insertCell(4).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;width:100%\" size=\"10%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].dblQty\" id=\"txtPendingQty."+(rowCount)+"\" value="+pendingQty+">";
	    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listclsWorkOrderDtlModel["+(rowCount-1)+"].strStatus\" id=\"txtStatus."+(rowCount)+"\" value="+status+">";
	    row.insertCell(6).innerHTML= '<input type="button" value = "Show" ">';
	    return false;
	}
	/**
	 * Remove all product from grid
	 */
	function funRemAllRows(tableId)
    {
		 var table = document.getElementById(tableId);
		 var rowCount = table.rows.length;			   
		
		for(var i=rowCount-1;i>=1;i--)
		{
			table.deleteRow(i);
		}
    }
	
	/**
	 * Delete a particular record from a grid
	 */
	function funDeleteRow(obj) 
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblProdDet");
	    table.deleteRow(index);
	}
	
	function funWOChkOnClick()
	{
		var table = document.getElementById("tblWODet");
	    var rowCount = table.rows.length;  
	    var strWOCodes="";
	    for(no=0;no<rowCount;no++)
	    {
	        if(document.all("cbGSel."+no).checked==true)
	        	{
	        		if(strWOCodes.length>0)
	        			{
	        			strWOCodes=strWOCodes+","+document.all("strWOCode."+no).value;
	        			}
	        		else
	        			{
	        			strWOCodes=document.all("strWOCode."+no).value;
	        			}
	        	}
	    }
		
	}
	
	
	/**
	 * Open help windows
	 */
	 
	 
	 function funHelp1()
	{
		 var against = $("#cmbAgainst").val();
			if(against=='Sales Order')
			{
				transactionName='salesorder';
				funHelp(transactionName);
			}else{
				transactionName='OPCodeForWO';
				funHelp(transactionName);
			}	
		
	}
	function funHelp(transactionName)
	{          
     
			
	fieldName = transactionName;
//	 window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		
	}

	/**
	 * Set Data after selecting form Help windows
	 */
	function funSetData(code)
	{
		var searchUrl="";
		switch(fieldName)
		{
			case'productmaster':
				funSetProduct(code);
			break;
			
			case'WorkOrder':
				funGetWorkOrderData(code);
			break;
			
			case 'OPCodeForWO':
				$('#txtOPCode').val(code);
				funCheckProductionOrder();
				break;
				
			case 'locby':
		    	funSetFromLocation(code);
		        break;	
		        
			case 'locon':
		    	funSetToLocation(code);
		        break;    
			case 'salesorder':
				$('#txtOPCode').val(code);
				funCheckProductionOrder(code);
		        break;  
		}
	}
	/**
	 * Set Product Data after selecting form Help windows
	 */
	function funSetProduct(code)
	{
		var searchUrl="";		
		searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
		//alert(searchUrl);
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	$("#txtProdCode").val(response.strProdCode);
			    	$("#spProdName").text(response.strProdName);
			    	funGetProdProcessDet(response.strProdCode);
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
	 * Set Product process Data
	 */
	function funGetProdProcessDet(strProdCode)
	{
		var searchUrl="";		
		searchUrl=getContextPath()+"/loadProcessData.html?prodCode="+strProdCode;	
		//alert(searchUrl);
		$.ajax({
		        type: "GET",
		        url: searchUrl,
		        async: false,
			    dataType: "json",
			    success: function(response)
			    {
			    	funRemRows();
			    	$.each(response, function(i,item)
			    	{
			    		//alert(response[i].strProcessCode);
			    		funAddRow(response[i].strProcessCode,response[i].strProcessName,response[i].strStatus,response[i].dblPendingQty);
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
	
	
	/**
	 * Get and Set work order Data after selecting form Help windows
	 */
	function funGetWorkOrderData(code)
	{
		$("#txtWOCode").val(code);
		searchUrl=getContextPath()+"/WorkOrderHdData.html?woCode="+code;
		$.ajax({
	        type: "GET",
	        url: searchUrl,
		    dataType: "json",
		    success: function(response)
		    {
		    	$.each(response, function(i,item)
                {
			    	funfillWorkOrderData(response[i].strWOCode,response[i].dtWODate,response[i].strProdCode,response[i].dblQty,response[i].strStatus);
			    	funGetProcessDet(response[i].strWOCode,response[i].strProdCode);
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
	
	/**
	 * Checking Production order
	 */
	function funCheckProductionOrder()
	{
		var code=$('#txtOPCode').val();
		searchUrl=getContextPath()+"/checkProductionOrder.html?OPCode="+code;
			//alert(searchUrl);
		$.ajax({
	        type: "GET",
	        url: searchUrl,
		    dataType: "json",
		    success: function(response)
		    {
		    	if(response!=null && response!="")
		    		{
		    		 $("#btnGenerate").css('visibility','hidden');
			    		funRemAginstOPRows();
				    	$.each(response, function(i,item)
		                {
				    		funFillAgainstOPData(response[i][0],response[i][1],response[i][2],response[i][3],response[i][4],response[i][5],response[i][6]);
		                });	
		    		}
		    	else
		    		{
		    			$("#btnGenerate").css('visibility','visible');
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
	 * Getting work order form Production Data 
	 */
	function funGetWorkOrderOPData()
	{
		var code=$('#txtOPCode').val();
		var strLocCode=$('#txtFromLocCode').val();
		 var against = $("#cmbAgainst").val();
		if(strLocCode=="")
			{
				alert("Please Select Issue Location");
				return false;
			}
		
		searchUrl=getContextPath()+"/GenearteWOAgainstOPData.html?OPCode="+code +"&strLocCode="+strLocCode+"&against="+against;
		$.ajax({
	        type: "GET",
	        url: searchUrl,
		    dataType: "json",
		    success: function(response)
		    {
		    	$("#btnGenerate").css('visibility','hidden');
		    	funRemAginstOPRows();
		    	$.each(response, function(i,item)
                {
		    		funFillAgainstOPData(response[i][0],response[i][1],response[i][2],response[i][3],response[i][4],response[i][5],response[i][6]);
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
	/**
	 * Filling Work order grid against Production order
	 */
	function funFillAgainstOPData(strWOCode,strProdCode,strProdName,strUOM,dblQty,strStatus,strStage)
	{
		var table = document.getElementById("tblWODet");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    row.insertCell(0).innerHTML= "<input id=\"cbGSel."+(rowCount)+"\" type=\"checkbox\" class=\"GCheckBoxClass\" checked=\"checked\" name=\"WOthemes\" value='Tick' onclick=\"funWOChkOnClick()\"/>";
// 	    row.insertCell(1).innerHTML= strWOCode;
// 		row.insertCell(2).innerHTML= strProdCode;		  
// 		row.insertCell(3).innerHTML= strProdName;
// 		row.insertCell(4).innerHTML= strUOM;
// 	    row.insertCell(5).innerHTML= dblQty; 
// 	    row.insertCell(6).innerHTML= strStatus;
// 	    row.insertCell(7).innerHTML= strStage;
	    
	    
	    row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box\" size=\"15%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].strWOCode\" id=\"strWOCode."	+ (rowCount) + "\" value=" + strWOCode + " >";
		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' >";   
		row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].strProdName\" id=\"strProdName."+(rowCount)+"\" value='"+strProdName+"' >";
	    row.insertCell(4).innerHTML=  "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].strUOM\" id=\"strUOM."+(rowCount)+"\" value='"+strUOM+"' >";
	    row.insertCell(5).innerHTML=   "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].dblQty\" id=\"txtdblQty."+(rowCount)+"\" value='"+dblQty+"' >";;
	    row.insertCell(6).innerHTML=   "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].strStatus\" id=\"txtStatus."+(rowCount)+"\" value='"+strStatus+"' >";;
	    row.insertCell(7).innerHTML=   "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].strStage\" id=\"txtstrStage."+(rowCount)+"\" value='"+strStage+"' >";;
	   
	    
	}
	/**
	 * Set value in Textfield
	 */
	function funfillWorkOrderData(strWOCode,dtWODate,strProdCode,dblQty,strStatus)
	{
		$('#txtWODate').val(dtWODate);
		$('#txtProdCode').val(strProdCode);
		$('#txtQty').val(dblQty);
		$('#strStatus').text(strStatus);
		 
	}
	/**
	 * Get Process for Product Data 
	 */
	function funGetProcessDet(strWOCode,strProdCode)
	{
		searchUrl=getContextPath()+"/GetProcessDet.html?woCode="+strWOCode+"&prodCode="+strProdCode;
		$.ajax({
	        type: "GET",
	        url: searchUrl,
		    dataType: "json",
		    success: function(response)
		    {
		    	funRemRows();
				$.each(response, function(i,item)
                {
					//alert(response.strProcessCode);
		    		funFillProcessDet(response[i].strProcessCode,response[i].strProcessName,response[i].strStatus,response[i].dblPendingQty);
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
	/**
	 * Filling Process data in grid
	 */
	function funFillProcessDet(strProcessCode,strProcessName,strStatus,dblPendingQty)
	{
		var table = document.getElementById("tblProdDet");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
		row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].strProcessCode\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProcessCode+"' >";//+strProcessCode+"</label>";			  
		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].strProcessName\" id=\"txtProcessName."+(rowCount)+"\" value='"+strProcessName+"' >";//+strProcessName+"</label>";
		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].strStatus\" id=\"txtProdName."+(rowCount)+"\" value='"+strStatus+"' >";//+strStatus+"</label>";   
		row.insertCell(3).innerHTML= "<input type=\"number\" readonly=\"readonly\" step=\"any\" required = \"required\" style=\"text-align: right;width:100%\" size=\"10%\" name=\"listclsWorkOrderDtlModel["+(rowCount)+"].dblPendingQty\" id=\"dblQty."+(rowCount)+"\" value='"+dblPendingQty+"' >";//+dblPendingQty+"</label>";
	    row.insertCell(4).innerHTML= '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">'; 
	   
	}
	
	function funRemRows() 
    {
		 var table = document.getElementById("tblProdDet");
		 var rowCount = table.rows.length;			   
		
		for(var i=rowCount-1;i>=0;i--)
		{
			table.deleteRow(i);
		}
    }
	/**
	 * Remove all product from grid
	 */
	function funRemAginstOPRows() 
    {
		 var table = document.getElementById("tblWODet");
		 var rowCount = table.rows.length;			   
		
		for(var i=rowCount-1;i>=0;i--)
		{
			table.deleteRow(i);
		}
    }
	
	/**
	 * On blur event in textfield
	 */
	$(function()
			{
				$("#txtWOCode").blur(function()
				{
					if(!$("#txtWOCode").val()=='')
					{
						fieldName = 'WorkOrder';
						funSetData($('#txtWOCode').val());							
					}					
				});
			    
				$('a#baseUrl').click(function() {
					if($("#txtWOCode").value=="")
					{
						alert("Please Eneter Work Order Code or Search");
						return false;
					}
				    $(this).attr('target', '_blank');
				});
			});
	/**
	 * On change event in combo box
	 */
	function funAgainstChange()
    {
        funRemWORows();
        $("#tdCode").text($("#cmbAgainst").val()+" Code");
        $("#txtSOCodeRO").val("");
        $("#txtOPCode").val("");
    }
	/**
	 * Remove all product from grid
	 */
	function funRemWORows()
    {
		 var table = document.getElementById("tblWODet");
		 var rowCount = table.rows.length;			   
		
		for(var i=rowCount-1;i>=1;i--)
		{
			table.deleteRow(i);
		}
    }
	
	function funGetWODetails()
	{
		if($("#txtOPCode").val()!='')
			{
				fieldName="SOWiseWO";
				funSetData($("#txtOPCode").val());
			}
	}
	
	function funReset()
	{
		funRemRows();
	}
	
	/**
	 * Load Function 
	 * And Getting session Value
	 * Success Message after Submit the Form
	 */
	function funOnLoad()
	{
		
    }
	
	/**
	 * Checking Validation before submiting the data
	 */
	function funValidate()
	{
		//alert("hi");
		
		var table = document.getElementById("tblWODet");
		    var rowCount = table.rows.length;
		
		if(  $("#cmbAgainst").val() == null || $("#cmbAgainst").val().trim().length == 0 )
		 {
		 		alert("Please Select Against");
				return false;
		 }
		
		if(rowCount<1)
		{
			alert("Please Add Work Order in Grid");
			$("#txtOPCode").focus();
			return false;
		}
		
		
		if($('#txtQty').val()==0 || $('#txtQty').trim().length==0)
		{
			alert("Please Enter Quantity");
			return false;
		}
		
		var status =funCheckWorkOrder(code);
		if(status=='Yes')
			{
			alert("Production Started !!!!");
				return false;
			}
		
// 		if(($("#cmbAgainst").val() == "Production")  )
// 			{
			
// 			}
		
		return true;
	}
	
	//Get and Set From location data
	function funSetFromLocation(code) {
		var searchUrl = "";
		searchUrl = getContextPath()
				+ "/loadLocationMasterData.html?locCode=" + code;

		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				if (response.strLocCode == 'Invalid Code') {
					alert("Invalid From Location Code");
					$("#txtFromLocCode").val('');
					$("#lblFromLocName").text("");
					$("#txtFromLocCode").focus();
				} else {
					$("#txtFromLocCode").val(response.strLocCode);						
					$("#lblFromLocName").text(response.strLocName);
					$("#txtFromProdCode").focus();
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
	
	
	
	//Get and Set To location data
	function funSetToLocation(code) {
		var searchUrl = "";
		searchUrl = getContextPath()
				+ "/loadLocationMasterData.html?locCode=" + code;

		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				if (response.strLocCode == 'Invalid Code') {
					alert("Invalid From Location Code");
					$("#txtToLocCode").val('');
					$("#lblToLocName").text("");
					$("#txtToLocCode").focus();
				} else {
					$("#txtToLocCode").val(response.strLocCode);						
					$("#lblToLocName").text(response.strLocName);
					$("#txtToProdCode").focus();
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
	
	
	
	function funCheckWorkOrder(code)
	{
		var strvar="";
		var searchUrl = "";
		searchUrl = getContextPath()
				+ "/checkWorkOrder.html?wOCode=" + code;

		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				if (response == 'No') {
					strvar='No';
				} else {
					strvar='Yes';
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
		
		return strvar;
		
	}
	
	
	
	
	
	
	
</script>

</head>
<body onload="funOnLoad();">
<div id="formHeading">
		<label>Work Order</label>
	</div>
	<s:form name="WorkOrder" action="saveWorkOrder.html?saddr=${urlHits}" method="POST">
<br/>
		<table
			style="border: 0px solid black; width: 100%; height: 100%; margin-left: auto; margin-right: auto; background-color: #C0E4FF;">
			<tr>
				<td>				
				<div id="tab_container" style="height: 500px">
							<ul class="tabs">
								 <li class="active" data-state="tab1" style="width: 100px;padding-left: 55px">PO Wise</li>
								<li class="active" id="tabDirect" data-state="tab2"  style="width: 100px;padding-left: 55px">Direct</li>
							</ul>
							<div id="tab1" class="tab_content" style="height: 370px" >
							
                            <table class="transTable">
                           <tr><th colspan="6"></th></tr>
                            <tr>
                                <td width="6%" id="tdAgainst" >Against</td>
                                <td  width="150px">
	                                <s:select style="width:100%" id="cmbAgainst" path="strAgainst"  onchange="funAgainstChange()" class="simpleTextBox">
		                                <option value="Production Order">Production Order</option>
		                                <option value="Sales Order">Sales Order</option>
	                                </s:select> 
	                               
                                </td>
                                
                                <td width="100px"><label>&nbsp; &nbsp; &nbsp; &nbsp;From Location</label></td>
								<td><s:input id="txtFromLocCode" path="strFromLocCode" required="required"  ondblclick="funHelp('locby')" cssClass="searchTextBox"/></td>
                                  <td ><label id ="lblFromLocName"></label></td>
                                
                              <td width="5%" id="tdCode" >Code</td>
                                <td  width="170px">
                                <s:input id="txtOPCode" path="strSOCode" style="width:100%" ondblclick="funHelp1('OPCodeForWO')"  type="text" class="simpleTextBox"></s:input>
                                </td>
                                <td>
                                    &nbsp; &nbsp;<input id="btnGenerate"  class="smallButton" type="button" value="Generate" onclick='funGetWorkOrderOPData()'  />
                           		</td>
                           		<!-- <td>
                                    <input id="btnBack"  class="smallButton" type="button" value="Back" onclick="history.back()"  />
                                </td>  -->                         
                            </tr>
                              <tr><td colspan="8"></td></tr>
                            </table>
                           
                                    
                   <table style="width: 95%;height:28px;margin-left: auto;margin-right: auto;font-size: 11px; 
                   font-weight: bold;">
					<tr bgcolor="#75c0ff">
                        <tr style="background-color:#75c0ff">
                        <td width="5%"><input type="checkbox" id="chkGALL"
											checked="checked" onclick="funCheckUncheck()" />Select</td>
                            <td  align="left" style="width: 10%; height: 16px;">
                                Work Order No.</td>
                            <td align="left" style="width: 10%; height: 16px;">
                                Product Code</td>
                            <td align="left" style="width: 25%; height: 16px;">
                                Product Name</td>
                             <td align="left" style="width: 5%; height: 16px;">
                                UOM</td>    
                             <td align="left" style="width: 10%; height: 16px;">
                                 Quantity</td>
                            <td align="left" style="width: 20%; height: 16px;">Status
                            </td>
                            <td align="left" style="width: 15%; height: 16px;">&nbsp; Stages</td>
                        </tr>
                        </table>
                        
                       <div id="divProduct" style="width: 95%;height:200px;margin-left: auto;
					        margin-right: auto; bgcolor: #d8edff; overflow: scroll;">
                        <table style="width:100%;border: #0F0;table-layout:fixed;overflow:scroll"
                               class="transTablex col5-center" id="tblWODet">
                        <tbody>
                        	<col align="left" style="width: 5%">   
                            <col align="left" style="width: 10%">                            
                            <col align="left" style="width: 10%">                            
                            <col align="left" style="width: 25.1%">
                            <col align="left" style="width: 5%">                            
                            <col align="left" style="width: 10%">
                            <col align="left" style="width: 20%">                            
                            <col align="left" style="width: 13%">
                        </tbody>
                        </table>
                   		 </div>
                   	
                   	<br>
                   	
                   	<table class="transTable">
                   	
                   	<tr>
                   	
                   	<td width="100px"><label>&nbsp; &nbsp; &nbsp; &nbsp;To Location</label></td>
								<td width="20%"><s:input id="txtToLocCode" path="strToLocCode" required="required"  ondblclick="funHelp('locon')" cssClass="searchTextBox"/></td>
                                  <td ><label id ="lblToLocName"></label></td>
                   	
                   	</tr>
                   	
                   
                   	</table>	 
                   		 
							
				</div>
							
							
			<div id="tab2" class="tab_content">
			<table style="width:100% ;height:100%;text-align:center ;border: 0px solid black;">
							<tr>
							<td>
							<table class="transTable">
											<tr><th colspan="6"></th></tr>
											<tr>
												<td width="50px"><label>WO Code</label></td>
												<td WIDTH="50PX"><s:input path="strWOCode" id="txtWOCode"
														value="${command.strWOCode}"
														ondblclick="funHelp('WorkOrder')" cssClass="searchTextBox"/></td>
												<td></td>
												<td width="50px">Date</td>
												<td width="50px"><s:input path="dtWODate" id="txtWODate"
														value="${command.dtWODate}" cssClass="calenderTextBox"/></td>
												
												<td></td>		
											</tr>
											<tr>
												<td><label>Product</label></td>
												<td><s:input id="txtProdCode"
														ondblclick="funHelp('productmaster');"
														value="${command.strProdCode}" path="strProdCode"  cssClass="searchTextBox"></s:input></td>
												<td align="left" width="30%"><span id="spProdName"></span></td>
												<td>Quantity</td>
												<td><s:input id="txtQty" path="dblQty" value="1.00" cssStyle="width:92%" cssClass="BoxW116px right-Align"></s:input></td>
												
											</tr>
											<tr>
												<td><label>Status</label></td>
												<td colspan="5" align="left"><label id="strStatus" style="float: left;"></label></td>
											</tr>
										</table>
									</td>
							</tr>
							</table>
				

				<table style="width: 95%;height:28px;margin-left: auto;
	margin-right: auto;font-size: 11px; font-weight: bold;">
					<tr bgcolor="#75c0ff">
						<td style="width: 20%; height: 16px;" align="left">Process Code</td>
						<td style="width: 30%; height: 16px;" align="left">Process Name</td>
						<td style="width: 30%; height: 16px;" align="left">Status</td>
						<td style="width: 10%; height: 16px;" align="left">Pending Qty</td>
						<td style="width: 10%; height: 16px;" align="left">Delete</td>
					</tr>
				</table>

				<div id="divProduct" style="width: 95%;height:200px;margin-left: auto;
					margin-right: auto; bgcolor: #d8edff; overflow: scroll;">
					<table  style="width:100%;border: #0F0;table-layout:fixed;overflow:scroll" class="transTablex col5-center" id="tblProdDet"
						style="overflow: scroll;">
						<tbody>    
							<col style="width:20%"><!--  COl1   -->
							<col style="width:30%"><!--  COl2   -->
							<col style="width:30%"><!--  COl3   -->
							<col style="width:10%"><!--  COl4   -->
							<col style="width:8.5%"><!--  COl5   -->
							</tbody>

					<c:forEach items="${command.listclsWorkOrderDtlModel}" var="wo"	varStatus="status">
						<tr>
							<td><input name="listclsWorkOrderDtlModel[${status.index}].strProcessCode"	value="${wo.strProcessCode}" /></td>
							<td><input name="listclsWorkOrderDtlModel[${status.index}].strProcessName"	value="${wo.strProcessName}" /></td>
							<td><input name="listclsWorkOrderDtlModel[${status.index}].strStatus"	value="${wo.strStatus}" /></td>
							<td><input name="listclsWorkOrderDtlModel[${status.index}].dblPendingQty" value="${wo.dblPendingQty}" /></td>
							<td><input type="Button" value="Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)" /></td>
						</tr>
						</c:forEach>
					</table>
				</div>
					<br>
<!-- 			<p align="center"> -->
<!-- 			<input type="submit"  value="Submit" onclick="return funValidate();" class="form_button" /> -->
<!-- 			&nbsp; &nbsp; &nbsp; <input type="reset" id="reset" name="reset" value="Reset"  onclick="funReset();" -->
<!-- 				class="form_button"  /> -->
<!-- 		</p><br><br>		 -->
				</div>
			</div>
			
			
			<p align="center">
			<input type="submit"  value="Submit" onclick="return funValidate();" class="form_button" />
			&nbsp; &nbsp; &nbsp; <input type="reset" id="reset" name="reset" value="Reset"  onclick="funReset();"
				class="form_button"  />
		</p><br><br>
				
				</td>
			</tr>

		</table>
	

	<!-- 	<input type="submit"  value="Submit" />
		<input type="reset" id="reset" name="reset" value="Reset" /> -->
		
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
	</s:form>
</body>
</html>