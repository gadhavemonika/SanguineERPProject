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
	
	$(function() 
	{
		 $( "#reqDate" ).datepicker();
		 
	});
	
		var fieldName;
	
		function funResetFields()
		{
	    }
					
		function funCallFormAction(actionName,object) 
		{	
			
			if(document.getElementById("txtLocBy").value==document.getElementById("txtLocOn").value)
				{
				alert("Location By and Location On cannot be same");

				}
			
		//	if($("#txtLocBy").value== $("#txtLocOn"))
			//{
				//$("#txtLocBy").value="";
				//$("#txtLocOn").value="";
				//$("#txtLocBy").focus();
			//}
				
				if (actionName == 'submit') 
				{
				
				
				document.forms[0].action = "saveMaterialReq.html";
				}
		}
		
		function funHelp(transactionName)
		{
			fieldName=transactionName;
			window.showModalDialog("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
	    }		
		
		function funSetData(code)
		{			
			var searchUrl="";
			if(fieldName == 'locby')
			{
				document.getElementById("txtLocBy").value=code;
				searchUrl=getContextPath()+"/loadLocationMasterData.html?LocCode="+code;
			}
			if(fieldName == 'locon')
			{
				
				document.getElementById("txtLocOn").value=code;
				searchUrl=getContextPath()+"/loadLocationMasterData.html?LocCode="+code;
			}
			if(fieldName == 'productmaster')
			{				
				document.getElementById("txtProdCode").value=code;
				searchUrl=getContextPath()+"/loadProductMasterData.html?ProdCode="+code;				
			}
			if(fieldName == 'MaterialReq')
			{
				document.getElementById("txtreqCode").value=code;				
				searchUrl=getContextPath()+"/loadReqHdData.html?ReqCode="+code;
			}
			
			$.ajax({				
			        	type: "GET",
				        url: searchUrl,
				        dataType: "json",
				        success: function(response)
				        {				        	
				        	if(fieldName == 'locby')
							{
				        		document.getElementById("lblLocBy").innerHTML=response.strLocName;
							}
				        	else if(fieldName == 'locon')
							{
								document.getElementById("lblLocOn").innerHTML=response.strLocName;
							}
				        	else if(fieldName == 'productmaster')
							{
								document.getElementById("spProdName").innerHTML=response.strProdName;
								document.getElementById("spPosItemCode").innerHTML=response.strPartNo;
							}							
				        	else if(fieldName == 'MaterialReq')
							{								
							}
						},
				        error: function(e)
				        {
				          	alert('Error121212: ' + e.toString);
				        }
			      });
		}
		
		function btnAdd_onclick() 
		{			
			if(!funCheckNull($("#txtreqCode").val(),"Requisition Code and Product Details "))
			{
				$("#txtreqCode").focus();
				return false;
			}
						
			if(document.all("txtProdCode").value!="")
		    {
		    	if(document.all("txtProdQty").value!="" && document.all("txtProdQty").value != 0 )
		        {
		            funAddProductRow();
		        	funClearProduct();
				}
		        else
		        {
		        	document.all("txtProdQty").focus();
		            return false;
				}
			}
			else
		    {
		    	document.all("txtProdCode").focus() ;
		        return false;
			}
		}
		 

			function funAddProductRow() 
			{				
				var strProdCode = document.getElementById("txtProdCode").value;
				var strPosItemCode=document.getElementById("spPosItemCode").innerHTML;
				var strProdName=document.getElementById("spProdName").innerHTML;
			    var dblProdQty = document.getElementById("txtProdQty").value;
			    var strRemarks = document.getElementById("txtRemarks").value;
			    
			    var table = document.getElementById("tblProdDet");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			   
			    row.insertCell(0).innerHTML= "<input name=\"listReqDtl["+(rowCount-2)+"].strProdCode\" id=\"txtProdCode."+(rowCount-2)+"\" value="+strProdCode+" >";			  
			    row.insertCell(1).innerHTML= "<input name=\"listReqDtl["+(rowCount-2)+"].spPosItemCode\" value="+strPosItemCode+" id=\"spPosItemCode."+(rowCount-2)+"\" >";
			    row.insertCell(2).innerHTML= strProdName;
			    row.insertCell(3).innerHTML= "<input name=\"listReqDtl["+(rowCount-2)+"].dblQty\" id=\"txtProdQty."+(rowCount-2)+"\" value="+dblProdQty+">";			    
			    row.insertCell(4).innerHTML= "<input name=\"listReqDtl["+(rowCount-2)+"].strRemarks\" id=\"txtRemarks."+(rowCount-2)+"\" value="+strRemarks+">";			    
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
				document.getElementById("txtProdCode").value="";
				document.getElementById("spPosItemCode").innerHTML="";
				document.getElementById("spProdName").innerHTML="";
				document.getElementById("txtProdQty").value="";
				document.getElementById("txtRemarks").value="";
			}
		 
	</script>

</head>
<body>
	<s:form name="matReq" method="POST" action="saveMaterialReq.html" >
		<table>
		
		    <tr>
		        <td><s:label path="strReqCode" >Requisition Code:</s:label></td>
		        <td><s:input name="txtreqCode" path="strReqCode" value="${matreq.strReqCode}"  ondblclick="funHelp('MaterialReq')"/></td>
		        <td></td>
		        <td><s:label path="dtReqDate" >Requisition Date:</s:label></td>
		        <td><s:input id="reqDate" name="reqDate" path="dtReqDate" value="${matreq.dtReqDate}"/></td>
		    </tr>
		    	
		    <tr>
		        <td><s:label path="strLocBy" >Location By:</s:label></td>
		        <td><s:input id="txtLocBy" name="txtLocBy" path="strLocBy" value="${matreq.strLocBy}" ondblclick="funHelp('locby')"/></td>
		        <td><s:label path="strLocBy" id="lblLocBy" ></s:label></td>
		        <td><s:label path="strLocOn" >Location To:</s:label></td>
		        <td><s:input id="txtLocOn" name="txtLocOn" path="strLocOn" value="${matreq.strLocOn}" ondblclick="funHelp('locon')"/></td>
		        <td><s:label id="lblLocOn" path="strLocOn"></s:label></td>
			</tr>
		    <tr>
		        <td><s:label path="strAgainst" >Against:</s:label></td>
		       	<td><s:select id="cmbAgainst"  onchange="" style="width: 65%" path="strAgainst">
                     <s:option value="Direct" selected="selected">Direct1</s:option>
                     <s:option value="Work Order">Work Order1</s:option>
                   </s:select></td>
		       <td> <s:input id="" name="" path="" value=""/></td>
		       <td><s:label path="" >Qty:</s:label></td>
		       <td><s:input id="txtWoQty" name="txtWoQty" path="" value=""/></td>
		       <td colspan="2"><input type="Button" value="Fill" onclick="return btnFill_onclick()"/></td>
		    </tr>
		    
		    <tr>
		        <td><label>Product Code:</label></td>
		        <td><s:input id="txtProdCode" name="txtProdCode" path="strProdCode" value="${matreq.strProdCode}" ondblclick="funHelp('productmaster')"/></td>
		        <td><label>Pos Item Code:</label>
		        <span id="spPosItemCode" ></span></td>
		        <td><s:label path="" >Product Name:</s:label></td>
		        <td><span id="spProdName" ></span></td>
		      
		    </tr>
		    
		    <tr>
		        <td><label>Qty:</label></td>
		        <td><s:input id="txtProdQty" name="txtProdQty" path="" value="${matreq.dblQty}"/></td>
		        <td></td>
		        <td><label >Remarks:</label></td>
		        <td><s:input id="txtRemarks" name="txtRemarks" path="" value="${matreq.strRemarks}"/></td>
		        
		        <td colspan="2"><input type="Button" value="Add" onclick="return btnAdd_onclick()"/></td>
		    </tr>	    
			<tr></tr>
			<tr></tr>
		</table>
		
		<div id="divProduct">
				<table width="100%" bgcolor="#d8edff" id="tblProdDet" style="overflow: scroll;">
						<tr bgcolor="#75c0ff">
		                    <td style="width: 16%; height: 16px;" align="left" >Product &nbsp;Code</td>
		                    <td style="width: 16%; height: 16px;" align="left" >Pos Item Code</td>
		                    <td style="width: 16%; height: 16px;" align="left" >Prod name</td>
		                    <td style="width: 16%; height: 16px;" align="right" >Qty</td>
		                    <td class="content" style="width: 16%; height: 16px" align="right">Remarks</td>
		                    <td style="width: 16%; height: 16px;" align="center" >Delete</td>
		               </tr>				
		               <tr>
			                 <td style="width: 16%" align="left"></td>
			                      <td style="width: 16%" align="left"></td>
			                      <td style="width: 16%" align="left"></td>
			                      <td style="width: 16%" align="right"></td>
			                      <td style="width: 16%" align="left"></td>
			                      <td style="width: 16%" align="center"></td>
			           </tr>                 
                
                 <c:forEach items="${matreq.listReqDtl}" var="reqdtl" varStatus="status">
					<tr>
						<sp:bind path="reqdtl.listReqDtl[${status.index}].strProdCode">
							<td><input name="listReqDtl[${status.index}].strProdCode" value="${matreq.strProdCode}"/></td>
						</sp:bind>
						
						<sp:bind path="reqdtl.listReqDtl[${status.index}].dblQty">
							<td><input name="listReqDtl[${status.index}].dblQty" value="${matreq.dblQty}"/></td>
						</sp:bind>
						
						<sp:bind path="reqdtl.listReqDtl[${status.index}].strRemarks">
							<td><input name="listReqDtl[${status.index}].strRemarks" value="${matreq.strRemarks}"/></td>
						</sp:bind>
						<td><input type="Button" value="Add"/></td>
					</tr>
			   </c:forEach>
        </table>			
		</div>
		<div>
			<table>
			 <tr>
				    <td colspan="2"><input type="submit" value="Submit" onclick="funCallFormAction('submit',this)"/></td>
			    </tr>	
			</table>
		</div>
	</s:form>
</body>
</html>