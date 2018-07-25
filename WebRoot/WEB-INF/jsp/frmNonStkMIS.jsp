<%@ page language="java" contentType="text/html;charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jQuery.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/validations.js"/>"></script>

<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:url value="/resources/css/design.css"/>" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:url value="/resources/css/jquery-ui.css"/>" />


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Non Stockable Material Issue Slip</title>
<%-- <script type="text/javascript">
    // function PopIt() { return "Are you sure you want to leave?"; }
     function UnPopIt()  
     {  <%session.removeAttribute("rptGRNCode");%> } 
     	window.onbeforeunload = UnPopIt;
		//$("a").click(function(){ window.onbeforeunload = UnPopIt; });
     
</script> --%>

<script type="text/javascript">

/*On form Load :vikash 16 jan 2015*/
 $(document).ready(function () {    
    $(document).ajaxStart(function(){
	    $("#wait").css("display","block");
	  });
	  $(document).ajaxComplete(function(){
	    $("#wait").css("display","none");
	  });
	  var maxQuantityDecimalPlaceLimit=parseInt('<%=session.getAttribute("qtyDecPlace").toString()%>');
		var maxAmountDecimalPlaceLimit=parseInt('<%=session.getAttribute("amtDecPlace").toString()%>');
}); 
 function funApplyNumberValidation(){
		$(".numeric").numeric();
		$(".integer").numeric(false, function() {alert("Integers only"); this.value = ""; this.focus(); });
		$(".positive").numeric({ negative: false }, function() {alert("No negative values"); this.value = ""; this.focus(); });
		$(".positive-integer").numeric({ decimal: false, negative: false }, function() {alert("Positive integers only"); this.value = ""; this.focus(); });
	    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
	}
 function getContextPath() 
	{
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
 var rowNo,fieldName,Flow;
 function funHelp1(Locrow,transactionName)
	{
		fieldName=transactionName;
		rowNo=Locrow;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;top=500,left=500")
    }
 function funHelp(transactionName)
	{
		fieldName=transactionName;
		if(transactionName=='locationmaster1')
		{
			transactionName="locationmaster";
		}
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;top=500,left=500")
 }
 function funSetData(code)
	{
	
		switch (fieldName) 
		{
		    case 'locationmaster':
		    	funSetLocation(code);
		        break;
		    case 'locationmaster1':
		    	funSetLocation1(code);
		    	break;
		    case 'grnproduct':
		    	funSetProduct(code);
		        break;
		}
	}
 function funSetLocation1(code)
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
			       		$("#txtLocCode").val("");
			       		$("#lblLocName").text("");
			       		return false;
			       	}
			       	else
			       	{
			       		$("#txtLocCode").val(response.strLocCode);
			       		$("#lblLocName").text(response.strLocName);
			    		
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
		searchUrl=getContextPath()+"/loadGRNProductData.html?prodCode="+code;
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
				    	$("#txtProdName").text('');
				    	$("#hidFromLocCode").val(''); 
						$("#hidFromLocName").val(''); 
				    	$("#txtProdCode").focus();
			    	}else{
			    	$("#txtProdCode").val(response.strProdCode);
			    	$("#txtProdName").text(response.strProdName);
					$("#txtUnitPrice").val(response.dblCostRM);
					$("#hidFromLocCode").val(response.strToLocCode); 
					$("#hidFromLocName").val(response.strToLocName);
					$("#hidGRNQty").val(response.dblGRNQty);
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
			       		document.all("txtLocToCode."+rowNo).value="";
			       		document.all("txtLocToName."+rowNo).value="";
			       		rowNo="";
			       		return false;
			       	}
			       	else
			       	{
			    		document.all("txtLocToCode."+rowNo).value=response.strLocCode;
			       		document.all("txtLocToName."+rowNo).value=response.strLocName;
			    		
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
	 if($("#txtLocCode").val().trim().length==0)
	    {
			  	alert("Please Enter Location Code Or Search");
			  	//alert("Please Enter Product Code Or Search");
		     	$('#txtLocCode').focus();
		     	return false; 
	    }
	 if($("#txtProdCode").val().trim().length==0)
	    {
			  	alert("Please Enter Product Code Or Search");
			  	//alert("Please Enter Product Code Or Search");
		     	$('#txtProdCode').focus();
		     	return false; 
	    }
		
	    if(($("#txtProdQty").val() == 0) || ($("#txtProdQty").val().trim().length==0  ))
		 {
				  alert("Please Enter Quantity ");
			       $('#txtProdQty').focus();
			       return false; 
		}	
	    else
	    {
	    	if(funCheckIssueQtyForGRN())
	    	{
	    		funAddProductRow();
	    	}
	    	
	    }
 }
 function funAddProductRow() 
	{	
	 
		var strProdCode = $("#txtProdCode").val();
		var strProdName=$("#txtProdName").text();
	    var dblQty=$("#txtProdQty").val();
	    var dblGRNQty=$("#hidGRNQty").val();	
	    var dblCostRM=$("#txtUnitPrice").val();
	    var strFromLocCode=$("#hidFromLocCode").val();
	    var strFromLocName=$("#hidFromLocName").val();
	    var strToLocCode=$("#txtLocCode").val();
	    var strToLocName=$("#lblLocName").text(); 
	    var dblPrice=parseFloat(dblQty) * parseFloat(dblCostRM);
	    dblPrice=parseFloat(dblPrice);
	    
	    var table = document.getElementById("tblProdDet");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listItems["+(rowCount)+"].strReqCode\"     value='' />";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"8%\"  name=\"listItems["+(rowCount)+"].dtReqDate\"      value='' />";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box prodCode\" size=\"8%\"  id=\"txtProdCode."+(rowCount)+"\" name=\"listItems["+(rowCount)+"].strProdCode\"    value='"+strProdCode+"' />";
	    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"32%\" name=\"listItems["+(rowCount)+"].strProdName\"    value='"+strProdName+"' />";
	    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"8%\"  name=\"listItems["+(rowCount)+"].strFromLocCode\" value='"+strFromLocCode+"' />";
	    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"31%\" name=\"listItems["+(rowCount)+"].strFromLocName\" value='"+strFromLocName+"'/>";
	    row.insertCell(6).innerHTML= "<input required=\"required\" class=\"Box\" size=\"8%\" id=\"txtLocToCode."+(rowCount)+"\" name=\"listItems["+(rowCount)+"].strToLocCode\" value='"+strToLocCode+"' onblur=\"funGetLocation("+(rowCount)+");\"/><input type=\"button\" value=... onclick=\"funHelp1("+(rowCount)+",'locationmaster')\">";
		row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"31%\" id=\"txtLocToName."+(rowCount)+"\" name=\"listItems["+(rowCount)+"].strToLocName\" value='"+strToLocName+"' />";
		row.insertCell(8).innerHTML= "<input readonly=\"readonly\" class=\"Box\" class=\"decimal-places\" style=\"text-align: right\" size=\"6%\" name=\"listItems["+(rowCount)+"].dblCostRM\" id=\"dblUnitPrice."+(rowCount)+"\"  value='"+dblCostRM+"' />";
		row.insertCell(9).innerHTML= "<input readonly=\"readonly\" class=\"Box\" class=\"decimal-places\" style=\"text-align: right\" size=\"6%\" name=\"listItems["+(rowCount)+"].dblPrice\" id=\"dblTotalPrice."+(rowCount)+"\" value='"+dblPrice+"' />";
		row.insertCell(10).innerHTML= "<input readonly=\"readonly\" class=\"Box\" class=\"decimal-places\" style=\"text-align: right\" size=\"6%\"  id=\"dblMRQty."+(rowCount)+"\"  value=0 />";
		row.insertCell(11).innerHTML= "<input required=\"required\" class=\"decimal-places MISQtyCell\" style=\"text-align: right\" size=\"6%\" name=\"listItems["+(rowCount)+"].dblQty\" id=\"dblMISQty."+(rowCount)+"\"  value='"+dblQty+"' onblur=\"funMISQtyTotal(this)\"/>";
		row.insertCell(12).innerHTML= "<input readonly=\"readonly\" class=\"Box\" class=\"decimal-places\" style=\"text-align: right\" size=\"8%\" id=\"dblPenQty."+(rowCount)+"\"  value=0.00 />";
		row.insertCell(13).innerHTML= "<input readonly=\"readonly\" class=\"Box\" class=\"decimal-places\" style=\"text-align: right\" size=\"6%\" id=\"dblGRNQty."+(rowCount)+"\"  name=\"listItems["+(rowCount)+"].dblGRNQty\" value='"+dblGRNQty+"' />";
		funCalculateTotalQty();
		funClearProdData();
	    return false;
	}
 function funClearProdData()
 {
	$("#txtProdCode").val('');
 	$("#txtProdName").text('');
    $("#txtProdQty").val('');
 	$("#txtUnitPrice").val('');
 	$("#hidFromLocCode").val(''); 
	$("#hidFromLocName").val(''); 
	$("#hidGRNQty").val('');
 	$("#txtProdCode").focus();
 }
	 function funGetLocation(row)
	 {
		 rowNo=row;
		 strLocCode=document.all("txtLocToCode."+row).value
		 if(strLocCode.trim().length!=0)
			 {
		 		funSetLocation(strLocCode);
			 }
	 }
 
	 function checkMISQty(i)
	 {         
		 if(Flow=='Against')
			 {
	            var dblMRQty=parseFloat(document.all("dblMRQty."+i).value);
			    var dblMISQty=parseFloat(document.all("dblMISQty."+i).value);
			    if( dblMISQty > dblMRQty)
			    {
			    	document.all("dblMISQty."+i).value='';
			        alert("Issue Quantity Can not be Greater than Requisition Qty");
			        
			        return false;
			    }
			    else
			    {
			        return true;
			    }
			 }
		 if(Flow=='Direct')
			 {
			 	var dblGRNQty=parseFloat(document.all("dblGRNQty."+i).value);
			 	var totalMISQty=0;
			 	var strProdCode=document.all("txtProdCode."+i).value;
				$('#tblProdDet tr').each(function()
				 {
					if(strProdCode==$(this).find(".prodCode").val())// `this` is TR DOM element
			    		{
						 	var MISQtyCell = $(this).find(".MISQtyCell").val();   
						 	totalMISQty=parseFloat(MISQtyCell)+totalMISQty;
			    		}
				 });
				//alert("dblGRNQty"+dblGRNQty);
				//alert("totalMISQty"+totalMISQty);
				if(parseFloat(totalMISQty) > parseFloat(dblGRNQty) )
					{
						 document.all("dblMISQty."+i).value='';
						 alert("Issue Quantity Can not be Greater than GRN Qty");
						 return false;
					}
						    
			 }
			 
	  }
 
	 function funCheckIssueQtyForGRN()
	 {
		 var flag=true;
		 var ProdQty=$("#txtProdQty").val();
		 var dblGRNQty=parseFloat($("#hidGRNQty").val());
		 var totalMISQty=0;
		 var strProdCode=$("#txtProdCode").val();
			$('#tblProdDet tr').each(function()
			 {
				if(strProdCode==$(this).find(".prodCode").val())// `this` is TR DOM element
		    		{
					 	var MISQtyCell = $(this).find(".MISQtyCell").val();   
					 	totalMISQty=parseFloat(MISQtyCell)+totalMISQty;
		    		}
			 });
			//alert("totalMISQty"+totalMISQty);
			//alert("dblGRNQty"+dblGRNQty);
			
			totalMISQty=parseFloat(totalMISQty)+parseFloat(ProdQty);
			if(parseFloat(totalMISQty) > parseFloat(dblGRNQty) )
				{
					 $("#txtProdQty").focus();
					 alert("Issue Quantity Can not be Greater than GRN Qty");
					 flag=false;
				}
			return flag;	    
	 }
	 
	 
	 function funMISQtyTotal(object)
	 {
		 var index=object.parentNode.parentNode.rowIndex;
		// alert(Flow);
		 if(Flow!='Direct')
			 {
			     var dblQtyTot=0;     
			     var dblTotalAmt=0;
			     
			     var table = document.getElementById("tblProdDet");
				 var rowCount = table.rows.length;	
				 for(i=0;i<rowCount;i++)
					{
					     dblPenQty=(parseFloat(document.getElementById("dblMRQty."+i).value))- (parseFloat(document.getElementById("dblMISQty."+i).value));
					     dblQtyTot = (parseFloat(dblQtyTot,2)) + parseFloat(document.all("dblMISQty."+i).value);
					     document.getElementById("dblPenQty."+i).value=dblPenQty;
					     dblUnitPrice=(parseFloat(document.getElementById("dblUnitPrice."+i).value));
					     dblMISQty=(parseFloat(document.getElementById("dblMISQty."+i).value));	     
					     document.getElementById("dblTotalPrice."+i).value=dblUnitPrice * dblMISQty;
					     dblTotalAmt=(parseFloat(dblTotalAmt)+ parseFloat(document.getElementById("dblTotalPrice."+i).value));
					}	
					
					document.getElementById("txtMISQtyTotal").value=(dblQtyTot);
					document.getElementById("txtTotalAmount").value=dblTotalAmt;
					//funTotalAmt();
					checkMISQty(index);
	   		}
		 if(Flow=='Direct')
		 {	
			 var dblQtyTot=0;     
		     var dblTotalAmt=0;
		     
		     var table = document.getElementById("tblProdDet");
			 var rowCount = table.rows.length;	
			 for(i=0;i<rowCount;i++)
				{
				     dblQtyTot = (parseFloat(dblQtyTot,2)) + parseFloat(document.all("dblMISQty."+i).value);
				     dblUnitPrice=(parseFloat(document.getElementById("dblUnitPrice."+i).value));
				     dblMISQty=(parseFloat(document.getElementById("dblMISQty."+i).value));	     
				     document.getElementById("dblTotalPrice."+i).value=dblUnitPrice * dblMISQty;
				     dblTotalAmt=(parseFloat(dblTotalAmt)+ parseFloat(document.getElementById("dblTotalPrice."+i).value));
				}	
				
				document.getElementById("txtMISQtyTotal").value=(dblQtyTot);
				document.getElementById("txtTotalAmount").value=dblTotalAmt;
			    checkMISQty(index);
		 }
	 }
	 function funTotalAmt()
	 {
	     var dblTotalAmt=0
	     var table = document.getElementById("tblProdDet");
		 var rowCount = table.rows.length;	
			for(i=0;i<rowCount.length;i++)
			{
				//alert(document.getElementById("dblTotalPrice."+i).value);
			    dblTotalAmt=(parseFloat(dblTotalAmt)+ parseFloat(document.getElementById("dblTotalPrice."+i).value));
			    
			}
	     document.getElementById("txtTotalAmount").value=dblTotalAmt;
	 }
	 
	 

 function funCalculateTotalQty()
 {
	 var table = document.getElementById("tblProdDet");
	 var rowCount = table.rows.length;	
	 var dblQtyTot=0;
	 var dblTotalAmt=0;
	 for(i=0;i<rowCount;i++)
		{
		    dblQtyTot=parseFloat(dblQtyTot) + parseFloat(document.getElementById("dblMISQty."+i).value);
		    dblTotalAmt=parseFloat(dblTotalAmt)+ parseFloat(document.getElementById("dblTotalPrice."+i).value);
		}	
	     document.getElementById("txtMISQtyTotal").value=dblQtyTot;
	     document.getElementById("txtTotalAmount").value=dblTotalAmt;
	      //funTotalAmt();
 }
 function funOnLoad()
	{	 
	 
	 Flow='${flow}';
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
			<%session.removeAttribute("GRNCode");%>
			window.close();
		<%
		}}%>
 }
 $(function()
 {
 	$("#txtLocCode").val("${locationCode}");
	$("#lblLocName").text("${locationName}");
 });
</script>

</head>
<body onload="funOnLoad();">
<div style="width: 100%; height: 40px; background-color: #458CCA">
		<p align="center"  style="padding-top: 5px;color: white">Non Stockable Material Issue Slip for <Strong>GRN No. ${GRNCode}</Strong></p>
	</div>
<s:form name="NonStkMIS" method="POST" action="saveNonStlMIS.html" modelAttribute="setUpAttribute" target="_self">
<div >
		<table class="transTableMiddle" style="width: 100%">
			<tr>
				<td width="10%">Location</td>
		        <td width="10%"><input id="txtLocCode"  value="${locationCode}" ondblclick="funHelp('locationmaster1')" Class="searchTextBox"/></td>
			    <td colspan="7"><label id="lblLocName"></label></td>
			  </tr>
			  <tr>
				<td  width="120px"><label>Product Code</label></td>
				<td width="130px"><input id="txtProdCode" name="txtProdCode" 
					ondblclick="funHelp('grnproduct')" class="searchTextBox" /></td>
				<!-- 		        <td><label>Pos Item Code:</label> -->
				<!-- 		        <label id="spPartNo" class="namelabel"></label></td> -->
				<td width="130px"><label>Product Name</label></td>
				<td colspan="3"><label id="txtProdName" class="namelabel"></label></td>
				<td><input type="hidden" id="hidFromLocCode"/></td> 
				<td><input type="hidden" id="hidFromLocName"/></td>
				<td><input type="hidden" id="hidGRNQty"/></td>
			</tr>
			

			<tr>
				<td><label>Qty</label></td>
				<td><input id="txtProdQty" type="text" class="decimal-places numberField" /></td>
				<td>Unit Price</td>
				<td width="10%"><input id="txtUnitPrice"
					name="txtUnitPrice" disabled="disabled" type="number" step="any"
					class="BoxW116px right-Align decimal-2-places" /></td>
				<td><input type="Button" value="Add" onclick="return btnAdd_onclick()" class="smallButton" /></td>
				<td colspan="4"></td>
			</tr>

		</table>
<table style="width: 100%;font-size:11px;
	font-weight: bold;">
			<tr bgcolor="#75c0ff">
				<td align="left" style="width: 6%; height: 16px;">MR Code</td><!--  COl1   -->
				<td align="left" style="width: 5%; height: 16px;">Date</td><!--  COl2   -->
				<td align="left" style="width: 5%; height: 16px;">Product Code</td><!--  COl3   -->
				<td align="left" style="width: 15%; height: 16px;">Product Name</td><!--  COl4   -->	
				<td align="left" style="width: 5%; height: 16px">Loc By Code</td><!--  COl6   -->			
				<td align="left" style="width: 15%; height: 16px">Location By</td><!--  COl5   -->
				<td align="left" style="width: 5%; height: 16px">Loc On Code</td><!--  COl8   -->
				<td align="left" style="width: 15%; height: 16px">Location On</td><!--  COl7   -->
				<td align="left" style="width: 4%; height: 16px">Unit Price</td><!--  COl9   -->
				<td align="left" style="width: 4%; height: 16px">Total Price</td><!--  COl10   -->
				<td align="left" style="width: 4%; height: 16px">MR Qty</td><!--  COl11   -->
				<td align="left" style="width: 4%; height: 16px">MIS Qty</td><!--  COl12   -->
				<td align="left" style="width: 5%; height: 16px;">Pending Qty</td><!--  COl13   -->
				<td align="left" style="width: 4%; height: 16px;">GRN Qty</td><!--  COl14   -->
			</tr>
		</table>
		<div style="width: 100%; height: 300px; overflow: auto;">
			<table  class="transTablex col9-center" style="width:100%;font-size:11px;
	font-weight: bold;" id="tblProdDet">
				<tbody>
					<col style="width: 6%"><!--  COl1   -->
					<col style="width: 5%"><!--  COl2   -->
					<col style="width: 5%"><!--  COl3   -->
					<col style="width: 15%"><!-- COl4   -->					
					<col style="width: 15%"><!--  COl5   -->
					<col style="width: 5%"><!--  COl6   -->
					<col style="width: 15%"><!--  COl7   -->
					<col style="width: 5%"><!--  COl8   -->
					<col style="width: 4%"><!--  COl9   -->
					<col style="width: 4%"><!--  COl10   -->
					<col style="width: 4%"><!--  COl11   -->
					<col style="width: 4%"><!--  COl12   -->
					<col style="width: 5%"><!--  COl13   -->
					<col style="width: 4%"><!--  COl14   -->
				<%-- <c:out  value="${fn:length(nonStkMISList)}" /> --%>
				<c:forEach items="${nonStkMISList}" var="NonStkMIS" varStatus="status">

				<tr>
					<td><input readonly="readonly" class="Box" size="10%" name="listItems[${status.index}].strReqCode" value="${NonStkMIS.strReqCode}" /></td>
					<td><input readonly="readonly" class="Box" size="8%" name="listItems[${status.index}].dtReqDate" value="${NonStkMIS.dtReqDate}" /></td>
					<td><input readonly="readonly" class="Box prodCode" id="txtProdCode.${status.index}"  size="8%" name="listItems[${status.index}].strProdCode" value="${NonStkMIS.strProdCode}"/></td>
					<td><input readonly="readonly" class="Box" size="32%" name="listItems[${status.index}].strProdName" value="${NonStkMIS.strProdName}" /></td>
					<td><input readonly="readonly" class="Box" size="8%" name="listItems[${status.index}].strFromLocCode" value="${NonStkMIS.strFromLocCode}" /></td>
					<td><input readonly="readonly" class="Box" size="31%" name="listItems[${status.index}].strFromLocName" value="${NonStkMIS.strFromLocName}" /></td>
					<c:if test="${NonStkMIS.strToLocCode ==''}">
					<td><input required="required" class="Box" size="8%" id="txtLocToCode.${status.index}" name="listItems[${status.index}].strToLocCode" value="${NonStkMIS.strToLocCode}" onblur="funGetLocation(${status.index});"/><input type="button" value="..." onclick="funHelp1(${status.index},'locationmaster')"></td>
					</c:if>
					<c:if test="${NonStkMIS.strToLocCode !=''}">
					<td><input readonly="readonly" class="Box" size="8%" name="listItems[${status.index}].strToLocCode" value="${NonStkMIS.strToLocCode}" /></td>
					</c:if>
					<td><input readonly="readonly" class="Box" size="31%" id="txtLocToName.${status.index}" name="listItems[${status.index}].strToLocName" value="${NonStkMIS.strToLocName}" /></td>
					<td><input readonly="readonly" class="Box" style="text-align: right" size="6%" name="listItems[${status.index}].dblCostRM" id="dblUnitPrice.${status.index}"  value="${NonStkMIS.dblCostRM}"/></td>
					<td><input readonly="readonly" class="Box" style="text-align: right" size="6%" name="listItems[${status.index}].dblPrice" id="dblTotalPrice.${status.index}" value="${NonStkMIS.dblPrice}"/></td>
					<c:if test="${NonStkMIS.strToLocCode ==''}">
					<td><input readonly="readonly" class="Box" style="text-align: right" size="6%"  id="dblMRQty.${status.index}"  value="0.00" /></td>
					</c:if>
					<c:if test="${NonStkMIS.strToLocCode !=''}">
					<td><input readonly="readonly" class="Box" style="text-align: right" size="6%"  id="dblMRQty.${status.index}"  value="${NonStkMIS.dblQty}" /></td>
					</c:if>
					<td><input required="required" type="text" class="decimal-places MISQtyCell" style="text-align: right" size="6%" name="listItems[${status.index}].dblQty" id="dblMISQty.${status.index}"  value="${NonStkMIS.dblQty}" onblur="funMISQtyTotal(this)"/></td>
					<td><input readonly="readonly" class="Box" style="text-align: right" size="8%" id="dblPenQty.${status.index}"  value="0.00" /></td>
					<td><input readonly="readonly" id="dblGRNQty.${status.index}"  class="Box" style="text-align: right" size="6%" name="listItems[${status.index}].dblGRNQty" value="${NonStkMIS.dblGRNQty}"/></td>
				</tr>
			</c:forEach> 
				</tbody>
				</table>
				
				</div>
				
			<table class="transTablex" style="width:100%;background-color:#d8edff">
				<tr bgcolor="#75c0ff">
					<td style="width: 25%"></td>
					<td style="height: 4px;width:52%; text-align: right">Total Amt</td>
					<td style="width: 5%">
					<s:input id="txtTotalAmount" type="text"  path="dblTotalAmt" value="0" readonly="true" cssStyle="width: 80%;text-align: right" /></td>
					<td style="width: 5%;text-align: right;">Total Qty</td>
					<td style="width: 5%"><input id="txtMISQtyTotal" type="text" readonly="readonly"
						 style="width: 80%; text-align: right;" />
				   </td>
				  <td style="width: 10%"></td>
				</tr>
				<tr bgcolor="#a6d1f6">
					<td width="10%" style="height: 61px">Narration</td>
					<td width="10%" colspan="5">
					<s:textarea class="textbox"
							id="txtNarration" cols="20" rows="3" style="width: 50%" path="strNarration" ></s:textarea></td>
					<td><s:input type="hidden" id="hidGRNCode" path="strGRNCode" /></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>

					<td></td>
				</tr>
			</table>
			<script type="text/javascript">
				funCalculateTotalQty();
				</script>
			
		<p align="center">
			<input type="submit" value="submit" class="form_button"  />
			 <input type="button" value="Reset" class="form_button" onclick="funResetFields()" />
		</p>
		
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
				
</div>
</s:form>
</body>
</html>