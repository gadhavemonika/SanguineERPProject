<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="sp"%>
<!DOCTYPE html>

<html>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.0/jquery-ui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Material Production</title>
<script type="text/javascript">
		$(function() {
			$("#txtPDDate").datepicker();
		
		});
				
		
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
				$("#txtLocCode").val(code);			
				searchUrl=getContextPath()+"/loadLocationMasterData.html?LocCode="+code;
			}
			
			if(fieldName == 'productmaster')
			{			
				$("#txtProdCode").val(code);
				searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
				
			}
			if(fieldName == 'Production')
			{
				$("#txtPDCode").val(code);
				document.PD.action="Production1.html?PDCode="+code;
				document.PD.submit();
			}
			if(fieldName=='ProdWorkOrder')
				{
					$("#txtWOCode").val(code);
					document.MIS.action="ProdWorkOrder.html?WoCode="+code;
					document.MIS.submit();
				}
			if(fieldName != 'Production' && fieldName!='ProdWorkOrder')
			{
				$.ajax({			
		        	type: "GET",
			        url: searchUrl,
			        dataType: "json",
			        success: function(response)
			        {			        	
			        	if(fieldName == 'locby')
						{
			        		$("#spLocName").text(response.strLocName); 
						}
						
						if(fieldName == 'productmaster')
						{
							$("#spProdName").text(response.strProdName);	
							$("#txtPrice").val(response.dblCostRM);
							
						}
												
					},
			        error: function(e)
			        {
			          	alert('Error121212: ' + e);
			        }
		      });	
			}
		}
		
		function btnAdd_onclick() 
	    {	   
	        if($("#txtProdCode").val()!="")
	        {
	           if($("#txtQtyProd").val()!="" && $("#txtQtyProd").val()!= 0 )
	           {		
	        	      funAddProductRow();
	                  funClearProduct();
	           } 
	            else
	            {
	            	alert("Please Enter Quantity");
	                $("#txtQtyProd").focus();
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
			var strProdName=$("#spProdName").text();
			var strProcess="Process";
		    var dblQtyProd = $("#txtQtyProd").val();
		    var dblwt = $("#txtWt").val();
		    var dblPrice=$("#txtPrice").val();
		    var dtlQtyRej=$("#txtQtyRej").val();
		    var dblAcceptedQty=dblQtyProd-dtlQtyRej;		   
		    var strAcctualTime=$("#txtActTime").val();
		    var strProdChar="Char";
		    var dtlTotWt=dblQtyProd*dblwt;
		    
		    var strUOM=funGetProductDetails(strProdCode);
			
		    var table = document.getElementById("tblProdDet");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		   
		    row.insertCell(0).innerHTML= "<input name=\"listProductionDtl["+(rowCount-1)+"].strProdCode\" id=\"txtProdCode."+(rowCount-1)+"\" value="+strProdCode+" >";   
		    row.insertCell(1).innerHTML= strProdName;
		    row.insertCell(2).innerHTML= strUOM;
		    row.insertCell(3).innerHTML=" <input type=\"hidden\" name=\"listProductionDtl["+(rowCount-1)+"].strProcessCode\"  id=\"strProcessCode."+(rowCount-1)+"\" value="+strProcess+">";
		    row.insertCell(4).innerHTML= "<input name=\"listProductionDtl["+(rowCount-1)+"].dblQtyProd\" id=\"txtQtyProd."+(rowCount-1)+"\" value="+dblQtyProd+">";
		    row.insertCell(5).innerHTML= "<input name=\"listProductionDtl["+(rowCount-1)+"].dblWeight\" id=\"txtWt."+(rowCount-1)+"\" value="+dblwt+">";
		    row.insertCell(6).innerHTML= "<input name=\"listProductionDtl["+(rowCount-1)+"].dtlTotWt\" id=\"dtlTotWt."+(rowCount-1)+"\" value="+dtlTotWt+">";
		    row.insertCell(7).innerHTML= "<input type=\"hidden\" name=\"listProductionDtl["+(rowCount-1)+"].dblPrice\"  id=\"dblPrice."+(rowCount-1)+"\" value="+dblPrice+">";
		    row.insertCell(8).innerHTML= "<input name=\"listProductionDtl["+(rowCount-1)+"].dblQtyRej\" id=\"txtQtyRej."+(rowCount-1)+"\" value="+dtlQtyRej+">";
		    row.insertCell(9).innerHTML= "<input name=\"listProductionDtl["+(rowCount-1)+"].dblAcceptedQty\" id=\"dblAcceptedQty."+(rowCount-1)+"\" value="+dblAcceptedQty+">";	    
		    row.insertCell(10).innerHTML= "<input name=\"listProductionDtl["+(rowCount-1)+"].dblActTime\" id=\"txtActTime."+(rowCount-1)+"\" value="+strAcctualTime+">";
		    row.insertCell(11).innerHTML=" <input type=\"hidden\" name=\"listProductionDtl["+(rowCount-1)+"].strProdChar\"  id=\"strProdChar."+(rowCount-1)+"\" value="+strProdChar+">";
		    row.insertCell(12).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
		    
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
			$("#spProdName").text("");
			$("#txtQtyProd").val("");
			$("#txtWt").val("");
			$("#txtPrice").val("");
			$("#txtQtyRej").val("");
			$("#txtWt").val("");
			$("#txtMacCode").val("");
			$("#spMacName").text("");
			$("#txtStaffCode").val("");
			$("#txtActTime").val("");
		}
		function funGetProductDetails(strProductCode)
		{	
			searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+strProductCode;
			$.ajax({			
	        	type: "GET",
		        url: searchUrl,
		        dataType: "json",
		        success: function(response)
		        {			        	
		        	return response.strUOM;							
										
				},
		        error: function(e)
		        {
		          	alert('Error121212: ' + e);
		        }
	      });	
		}
		

		$(function()
				{
					$("#txtWOCode").blur(function()
					{
						if(!$("#txtWOCode").val()=='')
						{
							funGetProduct($('#txtWOCode').val());
							fieldName="ProdWorkOrder";
						}					
					});
				    
					$('a#baseUrl').click(function() {
						if($("#txtWOCode").value=="")
						{
							alert("Please Eneter WorkOrder Code or Search");
							return false;
						}
					    $(this).attr('target', '_blank');
					});
				});
		
		$(function()
				{
						$("#txtLocCode").blur(function()
						{
							if(!$("#txtLocCode").val()=='')
								{
								funGetLocationName("txtLocCode","spLocName");
								}
						});
				
						$('a#baseUrl').click(function()
						{
							if($("#txtLocCode").value=="")
							{
								alert("Please Select Location or Search");
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
		
		function funGetProduct(strWorkOrderCode)
		{
			if(strWorkOrderCode!="")
				{
								
				}
		}
		function funResetField()
		{
			funRemProdRows();
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
</script>
</head>
<body>
<s:form name="PD" action="savePD.html" method="POST">
<table>
	<tr>
	<td>
		<table style="cellspacing:0;cellpadding:0; width:100%; border:0;">			  
			     <tr>
				   <td  width="150" bgcolor="#6eb9f7">Material Production</td>
				   <%-- <td><s:input type="hidden" id="prodID" name="prodID" path="intId" value="${command.intId}"/></td> --%>
	               <td><s:input type="hidden" id="userCreated" name="userCreated" path="strUserCreated" value="${command.strUserCreated}"/></td>
	 				<td><s:input type="hidden" id="dateCreated" name="dateCreated" path="dtCreatedDate" value="${command.dtCreatedDate}"/></td>
	            	<td align="right"><span> <a id="baseUrl" href="docs.html?MISCode=">Attatch Documents </a> </span></td>
			     </tr>			   
		</table>	
	<tr>
	<td>
		<table>
		<tr>
			<td><label>Production Code:</label></td>
    		<td width="20%" >
    		<s:input id="txtPDCode" ondblclick="funHelp('Production')" style="width: 90%" type="text" path="strPDCode" value="${command.strPDCode}" ></s:input></td>
    		<td width="20%"></td>
		    <td  width="15%">Date</td>
		    <td><s:input id="txtPDDate" name="txtPDDate"  path="dtPDDate" value="${command.dtPDDate}" style="width: 90%" type="text"  ></s:input></td>
    		</tr>
    		<tr>
			    <td><label>Location:</label></td>
			    <td><s:input id="txtLocCode" path="strLocCode" value="${command.strLocCode}" ondblclick="return funHelp('locby');" style="width: 90%" type="text"  ></s:input></td>
			    <td><span id="spLocName"></span></td>
			    <td><label>Work Order Code:</label></td>
			    <td width="20%" ><s:input id="txtWOCode" path="strWOCode" value="${command.strWOCode}" ondblclick="" style="width: 90%" type="text" ></s:input></td>
			    <td  width="15%"></td>
			</tr>
			<tr>
    		  <td bgcolor="#a6d1f6" colspan="7" >
    			<table style="width:100%; backbackground-color:#c0e2ff">
		    			<tr>
		                    <td style="width:10%"><label>Product:</label></td>
		                    <td style="width:40%" colspan="4" >
		                        <input id="txtProdCode" ondblclick="funHelp('productmaster');" style="width: 33%" type="text" ></input>
		                        &nbsp;&nbsp;&nbsp;<span  id="spProdName"></span>
		                    </td>
		        			<td style="width: 3%">
		                		<input id="btnChar" disabled="disabled" onclick="return btnChar_onclick()"  type="button" value="..." />
		                    </td>
		                    <td style="width:10%;display:none;" ><label>Price/Unit:</label></td>
		                    <td style="width:10%; height: 28px;display:none;" >
		                        <input id="txtPrice" style="width: 90%" type="text" ></input>
		                    </td>
		                    <td style="width:10%"><label>Wt/Unit:</label></td>
		                    <td style="width:10%; height: 28px;" >&nbsp;
		                    	<input id="txtWt"  ondblclick="" style="width: 90%" type="text"  ></input>
		                    </td>		                    
		                </tr>      
			        	<tr >
				            <td style="width: 10%"><label>Process:</label></td>
				            <td colspan="2" >
				                <select id="cmbProcess"  style="width:90%" onchange="">			                    
				                </select>
				            </td>
				           		            
			          </tr>
			          <tr>
				            <td style="width: 10%"><label>Quantity Produced:</label></td>
				            <td colspan="2"><input id="txtQtyProd" style="width: 90%" type="number" /></td>
				            <td><label>Quantity Rejected:</label> </td>
				            <td colspan="2"><input id="txtQtyRej" style="width: 90%" type="number" /></td>
				            <td><label> Actual Time taken:</label></td>
				            <td><input id="txtActTime"  ondblclick="" style="width: 90%" type="number"  /></td>
				            <td></td>
				            <td><input id="btnAdd"  type="button" value="Add"  onclick="return btnAdd_onclick()" /></td>
			        </tr>
			        <tr>
	        			<td colspan="7" style="height: 257px" >	                    
		                    <div style="width: 155%;height:250px;overflow: auto;">
			                    <table style="width:200%; backgrbackground-color:#d8edff">
			                        <tr bgcolor="#75c0ff" >
			                            <td style="width: 5%"  align="left">Prod Code</td>
			                            <td style="width: 10%" align="left">Prod Name</td>
			                            <td style="width: 5%"  align="left">UOM</td>
			                            <td style="width: 10%" align="left" >Process</td>
			                            <td style="width: 4%"  align="right">Qty Produced</td>
			                            <td style="width: 5%"  align="right">Wt/Unit</td>
						                <td style="width: 8%"  align="right">Total&nbsp; Wt</td>
						                <td style="width: 7%;  display:none;" align="right">Price / Unit</td>
						                <td style="width: 7%;  display:none;" align="right">Total Price</td>
						                <td style="width: 5%"  align="right">Qty Rejected</td>
		                                <td style="width: 5%"  align="right" >Qty Accepted</td>		                               
		                                <td style="width:10%">Actual Time</td>
		                                <td style="width: 5%" align="center">Delete</td>
	                        		</tr>
	                        	</table>
	                        	<table style="width:200%; backgrbackground-color:#d8edff" id="tblProdDet">
		                        <tr >
		                            <td style="width: 5%" align="left"></td>
		                            <td style="width: 10%" align="left"></td>
		                            <td style="width: 5%" align="left"></td>
		                            <td style="width: 10%" align="left" ></td>
		                            <td style="width: 4%" align="right"></td>
		                            <td style="WIDTH: 5%" align="right"></td>
		                            <td style="WIDTH: 8%" align="right"></td>
		                            <td style="WIDTH: 7%; display:none;" align="right"></td>
		                            <td style="WIDTH: 7%; display:none;" align="right"></td>
		                            <td style="width: 5%" align="right"></td>		                            
		                            <td style="width: 5%"></td>		                            
		                            <td style="width: 10%"></td>
		                            <td style="width: 5%" align="center"></td>
		                        </tr>   
		                        
		                        <c:forEach items="${command.listProductionDtl}" var="pddtl" varStatus="status">		                          	
									 <tr>						
										<td><input name="listProductionDtl[${status.index}].strProdCode" value="${pddtl.strProdCode}"/></td>										
										<td><input name="listProductionDtl[${status.index}].strProdName" value="${pddtl.strProdName}"/></td>
										<td><input name="listProductionDtl[${status.index}].strUOM" value="${pddtl.strUOM}"/></td>
										<td><input name="listProductionDtl[${status.index}].strProcessCode" value="${pddtl.strProcessCode}"/></td>
										<td><input name="listProductionDtl[${status.index}].dblQtyProd" value="${pddtl.dblQtyProd}"/></td>
										<td><input name="listProductionDtl[${status.index}].dblWeight" value="${pddtl.dblWeight}"/></td>
										<td><input  value="${pddtl.dblWeight * pddtl.dblQtyProd}"/></td>
										<td><input name="listProductionDtl[${status.index}].dblQtyRej" value="${pddtl.dblQtyRej}"/></td>
										<td><input name="listProductionDtl[${status.index}].dblPrice" value="${pddtl.dblPrice}" type="hidden"/></td>				
										<td><input name="listProductionDtl[${status.index}].dblActTime" value="${pddtl.dblActTime}" /></td>
										
										<td><input type="Button" value="Delete"/></td>
									 </tr>
							   	</c:forEach>                       
	                         </table>
                        </div>
                     </td>
    			</tr>
               </table>
              </td>
		   </tr>
		   
		</table>
	</td>
	</tr>

</table>
<div>
			<table>
			 <tr>
				    <td colspan="2"><input type="submit" value="Submit" /></td>
				    <td colspan="2"><input type="reset" value="Reset" onclick="funResetField()"/></td>
			    </tr>	
			</table>
		</div>
</s:form>
</body>
</html>