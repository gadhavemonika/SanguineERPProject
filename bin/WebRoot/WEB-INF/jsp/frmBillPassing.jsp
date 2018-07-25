<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.0/jquery-ui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bill Passing</title>

<script type="text/javascript">


	var fieldName;
	$(function() {
		$("#dtBillDate").datepicker();
		$("#dtPassDate").datepicker();

	});
	
	
	function funHelp(transactionName) {

		fieldName = transactionName;

		window.showModalDialog("searchform.html?formname=" + transactionName,
				'window', 'width=600,height=600');
	}
	function funSetData(code)
	{
		var searchUrl="";
		
		
		
		if(fieldName=='suppcode'){
			searchUrl=getContextPath()+"/loadSupplierMasterData.html?partyCode="+code;
			
		}
		else if(fieldName=='BillPassing'){

			document.BillPassingForm.action="loadBillPassingData.html?billNo="+code;
			document.BillPassingForm.submit();
		}
		if(fieldName !='BillPassing'){
			$.ajax({				
		        type: "GET",
		        url: searchUrl,	        		
		        dataType: "json",		        
		        success: function(response)	        
		        {
		        if(fieldName=='suppcode'){
		        	$("#strSuppCode").val(response.strPCode);
		        	$("#strSupplierName").text(response.strPName);
		        	
		        	}
		       
		        	
				},
		        error: function(e)
		        {
		          	alert('Error:=' + e);
		        }
		  });
			
		}
	}
	function btnAdd_onclick() 
	{		   
		if(document.all("strGRNCode").value!="")
	    {
	    	if(document.all("strGRNCode").value!="" )
	        {
	    		funAddRow();
	        	funClearFields();
			} 
	        else
	        {
	        	alert("Please Enter GRN CODE");
	        	document.all("dblQty").focus();
	            return false;
			}
		}
		else
	    {
			alert("Please Enter Enter GRN CODE");
	    	document.all("strGRNCode").focus() ; 
	        return false;
		}  
	}
	
	function funClearFields() {
		$("#strGRNCode").val("");
		$("#dtGRNDate").text("");
		$("#strChallanNo").text("");
		$("#dblGRNAmt").text("");
		$("#dblAdjustAmt").val("");
		$("#dblFreight").val("");
		$("#dblSurcharge").val("");		
	}
	
	function funAddRow() 
	{
		
		var strGRNCode = $("#strGRNCode").val();		
		var dtGRNDate=$("#dtGRNDate").text();				
	    var strChallanNo = $("#strChallanNo").text();	    
	    var dblGRNAmt=$("#dblGRNAmt").text();	    
	    var dblAdjustAmt=$("#dblAdjustAmt").val();	  
	    var dblFreight=$("#dblFreight").val();
	    var dblSurcharge=$("#dblSurcharge").val();

	    var table = document.getElementById("tblbillpassingdtl");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    row.insertCell(0).innerHTML= "<input name=\"listclsBillPassDtlModel["+(rowCount-1)+"].strGRNCode\" id=\"strGRNCode."+(rowCount-1)+"\" value="+strGRNCode+" >";			  		   	  
	   
	    row.insertCell(1).innerHTML= "<input name=\"listclsBillPassDtlModel["+(rowCount-1)+"].dtGRNDate\" id=\"dtGRNDate."+(rowCount-1)+"\" value="+dtGRNDate+">";		    	    
	    row.insertCell(2).innerHTML= "<input name=\"listclsBillPassDtlModel["+(rowCount-1)+"].strChallanNo\" id=\"strChallanNo."+(rowCount-1)+"\" value="+strChallanNo+">";
	   	   
	    row.insertCell(3).innerHTML= "<input name=\"listclsBillPassDtlModel["+(rowCount-1)+"].dblAdjustAmt\" id=\"dblAdjustAmt."+(rowCount-1)+"\" value="+dblAdjustAmt+">";	
	    row.insertCell(4).innerHTML= "<input name=\"listclsBillPassDtlModel["+(rowCount-1)+"].dblFreight\" id=\"dblFreight."+(rowCount-1)+"\" value="+dblFreight+">";
	    row.insertCell(5).innerHTML= "<input name=\"listclsBillPassDtlModel["+(rowCount-1)+"].dblSurcharge\" id=\"dblSurcharge."+(rowCount-1)+"\" value="+dblSurcharge+">";
	    
	    row.insertCell(6).innerHTML= "<input name=\"listclsBillPassDtlModel["+(rowCount-1)+"].dblGRNAmt\" id=\"dblGRNAmt."+(rowCount-1)+"\" value="+dblGRNAmt+">";
	    row.insertCell(7).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
	    return false;
	}
	function funDeleteRow(obj) 
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblbillpassingdtl");
	    table.deleteRow(index);
	}
	
</script>
</head>
<body>
	<label>Bill Passing</label>
	<s:form name="BillPassingForm" action="saveBillPasingData.html">

		<table>
			<tr>
				<td><label>Bill No</label></td>
				<td><s:input path="strBillNo" ondblclick="funHelp('BillPassing')"/></td>
				<td><label>Date</label></td>
				<td><s:input path="dtBillDate" id="dtBillDate"/></td>
			</tr>

			<tr>
				<td><label>Against</label></td>
				<td><s:select path="strAgainst">
						<s:option value="GRN">GRN</s:option>
						<s:option value="SubContractor GRN">SubContractor GRN</s:option>
					</s:select></td>
				<td><label> Supplier </label></td>
				<td><s:input path="strSuppCode" ondblclick="funHelp('suppcode')"/></td>
				<td><label id="strSupplierName"></label></td>
				<td><label>Purchase Vouch No</label></td>
				<td><s:input path="strPVno" /></td>
			</tr>

			<tr>
				<td><label>Bill Amount</label></td>
				<td><s:input path="dblBillAmt" /></td>
				<td><label>Passing Date</label></td>
				<td><s:input path="dtPassDate" id="dtPassDate"/></td>
			</tr>

			<tr>
				<td><label>Narration</label></td>
				<td><s:input path="strNarration" /></td>
				<td><label>Currency</label></td>
				<td><s:select path="strCurrency">
						<s:option value="Rs">Rs</s:option>
						<s:option value="$">$</s:option>
					</s:select></td>
			</tr>

			<tr>
				<td><label>GRN Code</label></td>
				<td><input id="strGRNCode"></td>
				<td><label>Date</label></td>
				<td><label id="dtGRNDate"></label></td>
				<td><label>Challan No</label></td>
				<td><label id="strChallanNo"></label></td>
				<td><label>Value</label></td>
				<td><label id="dblGRNAmt"></label></td>
				</tr>
				<tr>
				<td><label>Adjustment</label></td>
				<td><input id="dblAdjustAmt"></td>
				<td><label>Freight</label></td>
				<td><input id="dblFreight"></td>
				<td><label>Surcharge</label></td>
				<td><input id="dblSurcharge"></td>
				<td><input type="button" value="Add"
					onclick="return btnAdd_onclick()" /></td>
			</tr>
		</table>
		<table>
			<tr bgcolor="#75c0ff">
				<td style="width: 16%; height: 16px;" align="left">Code</td>
				<td style="width: 16%; height: 16px;" align="left">Date</td>
				<td style="width: 16%; height: 16px;" align="right">Challan No</td>
				<td style="width: 16%; height: 16px;" align="left">Adjustment</td>
				<td style="width: 16%; height: 16px;" align="left">Freight</td>
				<td style="width: 16%; height: 16px;" align="left">Surcharge</td>
				<td style="width: 16%; height: 16px;" align="left">Value</td>
				<td style="width: 16%; height: 16px;" align="center">Delete</td>
			</tr>
		</table>
		<div id="divProduct"
			style="width: 100%; bgcolor: #d8edff; overflow: scroll;">
			<table id="tblbillpassingdtl" style="overflow: scroll;">
				<tr>
					<td style="width: 16%" align="left"></td>
					<td style="width: 16%" align="left"></td>
					<td style="width: 16%" align="left"></td>
					<td style="width: 16%" align="right"></td>
					<td style="width: 16%" align="left"></td>
					<td style="width: 16%" align="left"></td>
					<td style="width: 16%" align="left"></td>
					<td style="width: 16%" align="left"></td>

				</tr>
				<c:forEach items="${command.listclsBillPassDtlModel}" var="op"
					varStatus="status">
					<tr>
						<td><input
							name="listclsBillPassDtlModel[${status.index}].strGRNCode"
							value="${op.strGRNCode}" /></td>

						<td><input
							name="listclsBillPassDtlModel[${status.index}].dtGRNDate"
							value="${op.dtGRNDate}" /></td>

						<td><input
							name="listclsBillPassDtlModel[${status.index}].strChallanNo"
							value="${op.strChallanNo}" /></td>

						<td><input
							name="listclsBillPassDtlModel[${status.index}].dblGRNAmt"
							value="${op.dblGRNAmt}" /></td>

						<td><input
							name="listclsBillPassDtlModel[${status.index}].dblAdjustAmt"
							value="${op.dblAdjustAmt}" /></td>


						<td><input
							name="listclsBillPassDtlModel[${status.index}].dblFreight"
							value="${op.dblFreight}" /></td>


						<td><input
							name="listclsBillPassDtlModel[${status.index}].dblSurcharge"
							value="${op.dblSurcharge}" /></td>

						<td><input type="Button" value="Delete" /></td>
					</tr>
				</c:forEach>


			</table>
		</div>
<input type="submit" value="Submit" />
		<input type="reset" value="Reset" />
	</s:form>
</body>
</html>