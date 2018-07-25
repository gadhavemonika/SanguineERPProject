<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="sp"%>
<!DOCTYPE >
<html>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.0/jquery-ui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>MIS</title>
	<script type="text/javascript">
	$(function() {
		$("#txtMISDate").datepicker();
	
	});
		
	
	var fieldName="";
	function funCallFormAction(actionName,object) 
	{
		if (actionName == 'submit') 
		{
			document.forms[0].action = "saveMIS.html";
		}		
	}
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.showModalDialog("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
    }	
	
	function funSetData(code)
	{
// 		
		var searchUrl="";
		if(fieldName == 'locby')
		{
			$("#txtLocFrom").val(code);			
			searchUrl=getContextPath()+"/loadLocationMasterData.html?LocCode="+code;
		}
		if(fieldName == 'locon')
		{			
			$("#txtLocTo").val(code);
			searchUrl=getContextPath()+"/loadLocationMasterData.html?LocCode="+code;
		}
		if(fieldName == 'productmaster')
		{			
			$("#txtProdCode").val(code);
			searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
			
		}
		if(fieldName == 'MIS')
		{
			$("#txtMISCode").val(code);
			document.MIS.action="MIS1.html?MISCode="+code;
			document.MIS.submit();
		}
		if(fieldName='MISRequisition')
			{
				$("#txtReqCode").val(code);
				document.MIS.action="MISReq.html?ReqCode="+code;
				document.MIS.submit();
			}
		if(fieldName != 'MIS' && fieldName!='MISRequisition')
		{
			$.ajax({			
	        	type: "GET",
		        url: searchUrl,
		        dataType: "json",
		        success: function(response)
		        {			        	
		        	if(fieldName == 'locby')
					{
		        		$("#lblLocFrom").text(response.strLocName); 
					}
					if(fieldName == 'locon')
					{
						$("#lblLocTo").text(response.strLocName);
					}
					if(fieldName == 'productmaster')
					{
						$("#spProdName").text(response.strProdName);
						$("#spPosItemCode").text(response.strPartNo);
					}
				},
		        error: function(e)
		        {
		          	alert('Error121212: ' + e);
		        }
	      });	
		}
	}
	
	$(function()
			{
				$("#txtReqCode").blur(function()
				{
					if(!$("#txtReqCode").val()=='')
					{
						funSetData($('#txtReqCode').val());
						fieldName="MISRequisition";
					}					
				});
			    
				$('a#baseUrl').click(function() {
					if($("#txtReqCode").value=="")
					{
						alert("Please Eneter Requisition Code or Search");
						return false;
					}
				    $(this).attr('target', '_blank');
				});
			});
	
	
	$(function()
			{
				$("#txtMISCode").blur(function()
				{
					if(!$("#txtMISCode").val()=='')
					{
						fieldName ="MIS";
						funSetData($('#txtMISCode').val());						
					}					
				});
			    
				$('a#baseUrl').click(function() {
					if($("#txtMISCode").value=="")
					{
						alert("Please Select MIS Code");
						return false;
					}
				    $(this).attr('target', '_blank');
				});
			});

	
			$(function()
			{
					$("#txtLocFrom").blur(function()
					{
						if(!$("#txtLocFrom").val()=='')
						{
							funGetLocationName("txtLocFrom","lblLocFrom");
						}
					});
			
					$('a#baseUrl').click(function()
					{
						if($("#txtLocFrom").value=="")
						{
							alert("Please Select Location From or Search");
							return false;
						}
					 $(this).attr('target', '_blank');
				});
			});
	
	
			$(function()
			{
				$("#txtLocTo").blur(function()
				{
					if(!$("#txtLocTo").val()=='')
					{
						funGetLocationName("txtLocTo","lblLocTo");
					}
				});
			
				$('a#baseUrl').click(function(){
					if($("#txtLocTo").value=="")
						{
							alert("Please Select Location To or Search");
							return false;
						}
					 $(this).attr('target', '_blank');
				});
			});
	
	
	function funGetLocationName(txtFieldName,lblFieldName)
	{
		code=document.getElementById(txtFieldName).value;	
		$.ajax({			
        	type: "GET",
	        url: getContextPath()+"/loadLocationMasterData.html?LocCode="+code,
	        dataType: "json",
	        success: function(response)
	        {			        	
	        	document.getElementById(lblFieldName).innerHTML=response.strLocName;
	        },
			error: function(e)
	        {
	          	alert("Invalid Location");
	          	document.getElementById(txtFieldName).value="";
	          	document.getElementById(lblFieldName).innerHTML="";
	          	$('#'+txtFieldName).focus();
	        }
		});
		
	}
	 	function btnAdd_onclick() 
	    {	    
	        if($("#txtProdCode").val()!="")
	        {
	        	        	 	
	           if($("#txtProdQty").val()!="" && $("#txtProdQty").val()!= 0)
	           {		
	        	      funAddProductRow();
	                  funClearProduct();
	           } 
	            else
	            {
	            	alert("Please Enter Quantity");
	                $("#txtProdQty").focus();
	                return false;
	            }
	       }
	       
	         else
	         {
	        	 alert("Please Enter Product Code Or Search");
	             $("#txtProdCode").focus() ; 
	             return false;
	         }
	    }
	 

		function funAddProductRow() 
		{			
			var strProdCode =$("#txtProdCode").val();
			var strPartNo=$("#spPosItemCode").text();
			var strProdName=$("#spProdName").text();
		    var dblProdQty = $("#txtProdQty").val();
		    var strRemarks = $("#txtRemarks").val();
		   
		    var table = document.getElementById("tblProdDet");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		   
		    row.insertCell(0).innerHTML= "<input name=\"listMISDtl["+(rowCount-1)+"].strProdCode\" id=\"txtProdCode."+(rowCount-1)+"\" value="+strProdCode+" >";		  
		    row.insertCell(1).innerHTML= "<input name=\"listMISDtl["+(rowCount-1)+"].strPartNo\" value="+strPartNo+" id=\"strPartNo."+(rowCount-1)+"\" >";
		    row.insertCell(2).innerHTML= strProdName;
		    row.insertCell(3).innerHTML= "<input name=\"listMISDtl["+(rowCount-1)+"].dblQty\" id=\"txtProdQty."+(rowCount-1)+"\" value="+dblProdQty+">";		    
		    row.insertCell(4).innerHTML= "<input name=\"listMISDtl["+(rowCount-1)+"].strRemarks\" id=\"txtRemarks."+(rowCount-1)+"\" value="+strRemarks+">";		    
		    row.insertCell(5).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
		    return false;
		}
		 
		function funDeleteRow(obj) 
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblProdDet");
		    table.deleteRow(index);
		}
		
		function funClearProduct()
		{
			$("#txtProdCode").val("");
			$("#spPosItemCode").text("");
			$("#spProdName").text("");
			$("#txtProdQty").val("");
			$("#txtRemarks").val("");
		}
		
		function funOnChange()
		{
						if($("#cmbAgainst").val()=="Direct1")
						 {
						 	$('#txtWoQty').css('visibility','hidden');
						 	$("#txtReqCode").css('visibility','hidden');
						 	$("#spQty").css('visibility','hidden');
						 	$("#btnFill").css('visibility','hidden');
						 }
					 else if($("#cmbAgainst").val()=="WorkOrder1")
						 {
						    $('#txtWoQty').css('visibility','visible');
						 	$("#txtReqCode").css('visibility','visible');
						 	$("#spQty").css('visibility','visible');
						 	$("#btnFill").css('visibility','visible');
						 }
					 else
						 {
						    $("#txtReqCode").css('visibility','visible');
						 	$('#txtWoQty').css('visibility','hidden');
						 	$("#spQty").css('visibility','hidden');
						 	$("#btnFill").css('visibility','visible');
						 } 
		}
		
		function funResetField()
		{
			funRemProdRows();
			$("#lblLocFrom").text("");			
			$("#lblLocTo").text(""); 			
			$("#spProdName").text("");
			$("#spPosItemCode").text("");
		}
		function funRemProdRows()
	    {
			 var table = document.getElementById("tblProdDet");
			 var rowCount = table.rows.length;			   
			
			for(var i=rowCount-1;i>=1;i--)
			{
				table.deleteRow(i);
			}
	    }
		function funOnload()
		{
			funOnChange();
		}
		function funOpenAgainst()
		{
			if($("#cmbAgainst").val()=="WorkOrder1")
				{
					//funHelp("WorkOrderST");
				}
			else
				{
					funHelp("MISRequisition");
					fieldName="MISRequisition";
				}
		}
		
</script>
	
</head>
<body onload="funOnload();">
<s:form name="MIS" action="saveMIS.html" method="POST"> 
<table style="cellspacing:0; cellpadding:0; width:100%; border:0">
    
       		<tr>
               <td  width="150" bgcolor="#6eb9f7">Material Issue Slip</td>
               <td><s:input type="hidden" id="misID" name="misID" path="intId" value="${command.intId}"/></td>
               <td><s:input type="hidden" id="userCreated" name="userCreated" path="strUserCreated" value="${command.strUserCreated}"/></td>
 				<td><s:input type="hidden" id="dateCreated" name="dateCreated" path="dtCreatedDate" value="${command.dtCreatedDate}"/></td>
            	<td align="right"><span> <a id="baseUrl" href="docs.html?MISCode="> Attatch Documents </a> </span></td>
            </tr>
      
</table>

<table>
 		<tr> 				
 			    <td><label >MIS Code:</label></td>
		        <td><s:input id="txtMISCode" name="txtMISCode" path="strMISCode" value="${command.strMISCode}"  ondblclick="funHelp('MIS')"/></td>
		        <td></td>
		        <td><label>MIS Date:</label></td>
		        <td><s:input id="txtMISDate" name="txtMISDate" path="dtMISDate" value="${command.dtMISDate}"/></td>
		    </tr>
		    	
		    <tr>
		        <td><s:label path="strLocFrom" >Location By:</s:label></td>
		        <td><s:input id="txtLocFrom" name="txtLocFrom" path="strLocFrom" value="${command.strLocFrom}" ondblclick="funHelp('locby')"/></td>
		        <td><s:label path="strLocFrom" id="lblLocFrom" ></s:label></td>
		        <td><s:label path="strLocTo" >Location To:</s:label></td>
		        <td><s:input id="txtLocTo" name="txtLocTo" path="strLocTo" value="${command.strLocTo}" ondblclick="funHelp('locon')"/></td>
		        <td><s:label id="lblLocTo" path="strLocTo"></s:label></td>		
			</tr>  
		    <tr>
		        <td><label>Against:</label></td>
		       	<td><s:select id="cmbAgainst" onchange="funOnChange();" name="cmbAgainst" style="width: 65%" path="strAgainst">
                     <s:option value="Direct1">Direct</s:option>
                     <s:option value="WorkOrder1">Work Order</s:option>
                     <s:option value="Requisition1">Requisition</s:option>
                   </s:select></td>
		       <td> <s:input id="txtReqCode" name="txtReqCode" path="strReqCode" ondblclick="funOpenAgainst();"/></td>
		       <td><span id="spQty"> Qty:</span></td>
		       <td><s:input id="txtWoQty" name="txtWoQty" path="" value="" type="number"/></td>
		       <td colspan="2"><input type="Button" id="btnFill" value="Fill" onclick="return btnFill_onclick()"/></td>
		    </tr>
		    
		    <tr>
		        <td><label>Product Code:</label></td>
		        <td><input id="txtProdCode" name="txtProdCode"  ondblclick="funHelp('productmaster')"/></td>
		        <td><label>Pos Item Code:</label>
		        <span id="spPosItemCode" ></span></td>
		        <td><label>Stock:</label><span id="spStock"></span></td>
		        <td><label  >Product Name:</label></td>
		        <td><span id="spProdName" ></span></td>
		      
		    </tr>
		    
		    <tr>
		        <td><label>Qty:</label></td>
		        <td><input id="txtProdQty" name="txtProdQty" type="number"/></td>
		        <td></td>
		        <td><label >Remarks:</label></td>
		        <td><input id="txtRemarks" name="txtRemarks" /></td>		        
		        <td colspan="2"><input type="Button" value="Add" onclick="return btnAdd_onclick()"/></td>
		    </tr>	    
			<tr></tr>
			<tr></tr>
		</table>	
		
				<table style="width:100%" >
						<tr bgcolor="#75c0ff">
		                    <td style="width: 16%; height: 16px;" align="left" >Product &nbsp;Code</td>
		                    <td style="width: 16%; height: 16px;" align="left" >Pos Item Code</td>
		                    <td style="width: 16%; height: 16px;" align="left" >Prod name</td>
		                    <td style="width: 16%; height: 16px;" align="right" >Qty</td>
		                    <td class="content" style="width: 16%; height: 16px" align="right">Remarks</td>
		                    <td style="width: 16%; height: 16px;" align="center" >Delete</td>
		               </tr>
		        </table >
		               <div id="divProduct" style="width: 100%;height:200px; overflow:scroll;">
				            <table style="background:#d8edff;overflow: scroll;" id="tblProdDet" >				
					           <tr>
					               <td style="width: 16%" align="left"></td>
						           <td style="width: 16%" align="left"></td>
						           <td style="width: 16%" align="left"></td>
						           <td style="width: 16%" align="left"></td>
						           <td style="width: 16%" align="right"></td>
						           <td style="width: 16%" align="left"></td>
						       </tr>   
								   <c:forEach items="${command.listMISDtl}" var="misdtl" varStatus="status">
									 <tr>						
										<td><input name="listMISDtl[${status.index}].strProdCode" value="${misdtl.strProdCode}"/></td>
										<td><input name="listMISDtl[${status.index}].strPartNo" value="${misdtl.strPartNo}"/></td>
										<td><input name="listMISDtl[${status.index}].strProdName" value="${misdtl.strProdName}"/></td>						
										<td><input name="listMISDtl[${status.index}].dblQty" value="${misdtl.dblQty}"/></td>
										<td><input name="listMISDtl[${status.index}].strRemarks" value="${misdtl.strRemarks}"/></td>
										<td><input type="Button" value="Delete"/></td>
									 </tr>
							   	</c:forEach>            
		        			</table>			
					</div>
		<div>
			<table>
			 <tr>
				    <td colspan="2"><input type="submit" value="Submit" onclick="funCallFormAction('submit',this)"/></td>
				    <td colspan="2"><input type="reset" value="Reset" onclick="funResetField()"/></td>
			    </tr>	
			</table>
		</div>

</s:form>
</body>
</html>