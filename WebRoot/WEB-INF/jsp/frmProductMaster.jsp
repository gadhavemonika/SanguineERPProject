<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" /> -->
<title>PRODUCT MASTER</title>
<style>
.ui-autocomplete {
    max-height: 200px;
    overflow-y: auto;
    /* prevent horizontal scrollbar */
    overflow-x: hidden;
    /* add padding to account for vertical scrollbar */
    padding-right: 20px;
}
/* IE 6 doesn't support max-height
 * we use height instead, but this forces the menu to always be this tall
 */
* html .ui-autocomplete {
    height: 200px;
}
</style>

<script type="text/javascript">
$(document).ready(function(){
	 resetForms('productMasterForm');
	   $("#txtProdName").focus();	
	   $(document).ajaxStart(function(){
		    $("#wait").css("display","block");
		  });
		  $(document).ajaxComplete(function(){
		    $("#wait").css("display","none");
		  });
		  
		  funGetAllLocationOnload();  
});

$(document).ready(function()
	{
		$(function() {
			
			$("#txtProdName").autocomplete({
			source: function(request, response)
			{
				var searchUrl=getContextPath()+"/AutoCompletGetproductName.html";
				$.ajax({
				url: searchUrl,
				type: "POST",
				data: { term: request.term },
				dataType: "json",
				 
					success: function(data) 
					{
						response($.map(data, function(v,i)
						{
							return {
								label: v,
								value: v
								};
						}));
					}
				});
			}
		});
		});
	});

</script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".tab_content").hide();
		$(".tab_content:first").show();
		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();
			var activeTab = $(this).attr("data-state");
			$("#" + activeTab).fadeIn();
			$("#txtLastDate").datepicker();
			$("#txtLastDate" ).datepicker('setDate', 'today');
			
			
		});
	});
</script>
	<script type="text/javascript">
	
	//Define Global Variable
	var listRowCustTabGrid=0;
	
		var fieldName,gAttName,gAttValueCode,gProcessName;
		$(function()
		{
			$("#txtLastDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
			//$("#txtLastDate" ).datepicker("setDate", new Date());
		});
		
		function funDuplicateSupplier(strSupplierCode)
		{
		    var table = document.getElementById("tblProdSupp");
		    var rowCount = table.rows.length;		   
		    var flag=true;
		    if(rowCount > 0)
		    	{
				    $('#tblProdSupp tr').each(function()
				    {
					    if(strSupplierCode==$(this).find('input').val())// `this` is TR DOM element
	    				{
					    	alert("Supplier Allready added "+ strSupplierCode);
		    				flag=false;
	    				}
					});
				    
		    	}
		    return flag;
		}
		function btnSupplierAdd_onclick() 
	    {	
			var strSupplierCode=$("#txtSupplierId").val();
		   	 if(funDuplicateSupplier(strSupplierCode))
			 {
		   		funAddSuppRow();
			 }
	    }
		function funAddSuppRow() 
		{
			var supplierId = $("#txtSupplierId").val();
			if(supplierId=='')
		    {
		    	alert("Please select Supplier Id");
		    	$("#txtSupplierId").focus();
		    	return false;
		    }
		    var supplierName = $("#txtSupplierName").val();
		    var lastCost = $("#txtLastCost").val();
		    var lastDate = $("#txtLastDate").val();
		    var UOM = $("#txtSuppUOM").val();
		    var leadTimeDays = $("#txtLeadTimeDays").val();
		    var suppItemCode = $("#txtSuppItemCode").val();
		    var desc = $("#txtDesc").val();
		    var maxQty = $("#txtMaxQty").val();
		    var def = "N";
		    if($("#chkDefault").prop('checked'))
			{
		    	def = "Y";
		    	$("#defaultSupplier").val(supplierId);
		    	
			}
		    
		    var table = document.getElementById("tblProdSupp");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"Box\" name=\"listProdSupp["+(rowCount)+"].strSuppCode\" id=\"txtSuppCode."+(rowCount)+"\" value="+supplierId+">";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listProdSupp["+(rowCount)+"].strSuppName\" id=\"txtSuppName."+(rowCount)+"\" value='"+supplierName+"'>";
		    row.insertCell(2).innerHTML= "<input class=\"inputText-Auto\" style=\"text-align: right;\" type=\"number\" step=\"any\" required = \"required\" name=\"listProdSupp["+(rowCount)+"].dblLastCost\" id=\"txtLastCost."+(rowCount)+"\" value="+lastCost+">";
		    row.insertCell(3).innerHTML= "<input size=\"8%\" type=\"text\" name=\"listProdSupp["+(rowCount)+"].strUOM\" id=\"txtSuppUOM."+(rowCount)+"\" value="+UOM+">";
		    row.insertCell(4).innerHTML= "<input size=\"9%\" type=\"text\" name=\"listProdSupp["+(rowCount)+"].dtLastDate\" id=\"txtLastDate."+(rowCount)+"\" value="+lastDate+">";
		    row.insertCell(5).innerHTML= "<input size=\"6%\" type=\"text\" name=\"listProdSupp["+(rowCount)+"].strLeadTime\" id=\"txtLeadTime."+(rowCount)+"\" value="+leadTimeDays+">";
		    row.insertCell(6).innerHTML= "<input class=\"inputText-Auto\" style=\"text-align: right;\" type=\"number\" step=\"any\" required = \"required\" name=\"listProdSupp["+(rowCount)+"].dblMaxQty\" id=\"txtMaxQty."+(rowCount)+"\" value="+maxQty+">";
		    row.insertCell(7).innerHTML= "<input size=\"6%\" type=\"text\" name=\"listProdSupp["+(rowCount)+"].strSuppPartNo\" id=\"txtSuppItemCode."+(rowCount)+"\" value="+suppItemCode+">";
		    row.insertCell(8).innerHTML= "<input size=\"8%\" type=\"text\" name=\"listProdSupp["+(rowCount)+"].strSuppPartDesc\" id=\"txtDesc."+(rowCount)+"\" value="+desc+" >";
		    if(def == 'Y'){
		    	row.insertCell(9).innerHTML= "<input size=\"2%\" type=\"radio\" name=\"listProdSupp.strDefault\" onClick=\"Javacsript:funSetDefaultSupp(this)\" id=\"txtDefault."+(rowCount)+"\" value="+def+" checked=\"checked\">";
		    }else{
		    	row.insertCell(9).innerHTML= "<input size=\"2%\" type=\"radio\" name=\"listProdSupp.strDefault\" onClick=\"Javacsript:funSetDefaultSupp(this)\" id=\"txtDefault."+(rowCount)+"\" value="+def+">";
		    }
		    
		    
		    row.insertCell(10).innerHTML= '<input type="button"  value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRowForSupplier(this)">';
		    funResetSupplierFields();
		    return false;
		}
		
		function funSetDefaultSupp(obj){
			var suppCode = $(obj).closest("tr").find("input[type=text]").val();
			$("#defaultSupplier").val(suppCode);
			
		}
		function funAddSuppRow1(supplierId,supplierName,lastCost,lastDate,UOM
				,leadTimeDays,suppItemCode,desc,maxQty,def) 
		{
		    var table = document.getElementById("tblProdSupp");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"Box\" name=\"listProdSupp["+(rowCount)+"].strSuppCode\" id=\"txtSuppCode."+(rowCount)+"\" value="+supplierId+">";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listProdSupp["+(rowCount)+"].strSuppName\" id=\"txtSuppName."+(rowCount)+"\" value='"+supplierName+"'>";
		    row.insertCell(2).innerHTML= "<input class=\"inputText-Auto\" style=\"text-align: right;\" type=\"number\" step=\"any\" required = \"required\" name=\"listProdSupp["+(rowCount)+"].dblLastCost\" id=\"txtLastCost."+(rowCount)+"\" value="+lastCost+">";
		    row.insertCell(3).innerHTML= "<input size=\"8%\" type=\"text\" name=\"listProdSupp["+(rowCount)+"].strUOM\" id=\"txtSuppUOM."+(rowCount)+"\" value="+UOM+">";
		    row.insertCell(4).innerHTML= "<input size=\"9%\" type=\"text\" name=\"listProdSupp["+(rowCount)+"].dtLastDate\" id=\"txtLastDate."+(rowCount)+"\" value="+lastDate+">";
		    row.insertCell(5).innerHTML= "<input size=\"8%\" type=\"text\" name=\"listProdSupp["+(rowCount)+"].strLeadTime\" id=\"txtLeadTime."+(rowCount)+"\" value="+leadTimeDays+">";
		    row.insertCell(6).innerHTML= "<input class=\"inputText-Auto\" style=\"text-align: right;\" type=\"number\" step=\"any\" required = \"required\" name=\"listProdSupp["+(rowCount)+"].dblMaxQty\" id=\"txtMaxQty."+(rowCount)+"\" value="+maxQty+">";
		    row.insertCell(7).innerHTML= "<input size=\"6%\" type=\"text\" name=\"listProdSupp["+(rowCount)+"].strSuppPartNo\" id=\"txtSuppItemCode."+(rowCount)+"\" value="+suppItemCode+">";
		    row.insertCell(8).innerHTML= "<input size=\"6%\" type=\"text\" name=\"listProdSupp["+(rowCount)+"].strSuppPartDesc\" id=\"txtDesc."+(rowCount)+"\" value="+desc+">";
		    if(def == 'Y'){
		    	$("#defaultSupplier").val(supplierId);
		    	row.insertCell(9).innerHTML= "<input size=\"2%\" type=\"radio\" onClick=\"Javacsript:funSetDefaultSupp(this)\" name=\"listProdSupp.strDefault\" id=\"txtDefault."+(rowCount)+"\" value="+def+" checked=\"checked\">";		    	
		    }else{
		    	row.insertCell(9).innerHTML= "<input size=\"2%\" type=\"radio\" onClick=\"Javacsript:funSetDefaultSupp(this)\" name=\"listProdSupp.strDefault\" id=\"txtDefault."+(rowCount)+"\" value="+def+">";
		    }
		    row.insertCell(10).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRowForSupplier(this)">';		    
		    return false;
		}
		
		
		function funResetSupplierFields()
		{
			$("#txtSupplierId").val('');
		    $("#txtSupplierName").val('');
		    $("#txtLastCost").val('0.00');		   
		    $("#txtSuppUOM").val('');
		    $("#txtLeadTimeDays").val('');
		    $("#txtSuppItemCode").val('');
		    $("#txtSuppItemCode").val('');
		    $("#txtDesc").val('');
		    $("#txtMaxQty").val('0');
		    $("#chkDefault").attr('checked', false);
		    document.elementById("txtLastDate").value=Date();
		    
		}
		
		function funDeleteRowForSupplier(obj)
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblProdSupp");
		    table.deleteRow(index);
		}
		
		function funAddAttRow()
		{
		    var attCode = $("#txtAttrCode").val();
		    if(attCode=='')
		    {
		    	alert("Please select Attribute Code");
		    	$("#txtAttrCode").focus();
		    	return false;
		    }
		    var attValue = $("#txtAttrValue").val();
		    var attName = $("#txtAttrName").val();
		    		    		    
		    var table = document.getElementById("tblProdAtt");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listProdAtt["+(rowCount-1)+"].strAttCode\" id=\"txtAttCode."+(rowCount-1)+"\" value="+attCode+">";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"40%\" name=\"listProdAtt["+(rowCount-1)+"].strAttName\" id=\"txtAttName."+(rowCount-1)+"\" value='"+attName+"'>";		    
		    row.insertCell(2).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" size=\"6%\" name=\"listProdAtt["+(rowCount-1)+"].dblAttValue\" id=\"txtAttValue."+(rowCount-1)+"\" value="+attValue+">";
		    row.insertCell(3).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRowForAttribute(this)">';
		    row.insertCell(4).innerHTML= "<input type=\"hidden\" name=\"listProdAtt["+(rowCount-1)+"].strAVCode\" id=\"txtAVCode."+(rowCount-1)+"\" value="+gAttValueCode+">";		    
		    funResetAttributeFields();
		    return false;
		}
		
		
		function funAddAttRow1(attCode,attValue,attName,attValueCode)
		{   		    		    
		    var table = document.getElementById("tblProdAtt");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);		    
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listProdAtt["+(rowCount-1)+"].strAttCode\" id=\"txtAttCode."+(rowCount-1)+"\" value="+attCode+">";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listProdAtt["+(rowCount-1)+"].strAttName\" id=\"txtAttName."+(rowCount-1)+"\" value="+attName+">";		    
		    row.insertCell(2).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" size=\"6%\" name=\"listProdAtt["+(rowCount-1)+"].dblAttValue\" id=\"txtAttValue."+(rowCount-1)+"\" value="+attValue+">";
		    row.insertCell(3).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRowForAttribute(this)">';
		    row.insertCell(4).innerHTML= "<input type=\"hidden\" name=\"listProdAtt["+(rowCount-1)+"].strAVCode\" id=\"txtAVCode."+(rowCount-1)+"\" value="+attValueCode+">";		    
		    funResetAttributeFields();
		    return false;
		}
		
		
		function funResetAttributeFields()
		{
			$("#txtAttrCode").val('');
			$("#txtAttrCode").focus();
		    $("#txtAttrValue").val('0.00');
		    $("#txtAttrName").val('');
		}
		
		function funDeleteRowForAttribute(obj)
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblProdAtt");
		    table.deleteRow(index);
		}
		
		
		function funAddProcessRow()
		{
		    var processCode = $("#txtProcessCode").val();
		    $("#txtProcessCode").focus();
		    if(processCode=='')
		    {
		    	alert("Please select Process Code");
		    	$("#txtProcessCode").focus();
		    	return false;
		    }
		    var processWt = $("#txtProcessWt").val();
		    var cycleTime = $("#txtCycleTime").val();
		    		    		    
		    var table = document.getElementById("tblProdProcess");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listProdProcess["+(rowCount-1)+"].intLevel\" id=\"intLevel."+(rowCount-1)+"\" value=1>";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\"name=\"listProdProcess["+(rowCount-1)+"].strProcessCode\" id=\"txtProcessCode."+(rowCount-1)+"\" value="+processCode+">";
		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listProdProcess["+(rowCount-1)+"].strProcessName\" id=\"txtProcessName."+(rowCount-1)+"\" value="+gProcessName+">";
		    row.insertCell(3).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" name=\"listProdProcess["+(rowCount-1)+"].dblWeight\" id=\"txtWeight."+(rowCount-1)+"\" value="+processWt+">";
		    row.insertCell(4).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" name=\"listProdProcess["+(rowCount-1)+"].dblCycleTime\" id=\"txtCycleTime."+(rowCount-1)+"\" value="+cycleTime+">";
		    row.insertCell(5).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRowForProcess(this)">';
		    funResetProcessFields();
		    return false;
		}
		
		
		function funAddProcessRow1(intLevel,processCode,processName,processWt,cycleTime)
		{		    		    		    
		    var table = document.getElementById("tblProdProcess");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    row.insertCell(0).innerHTML= "<input  readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listProdProcess["+(rowCount-1)+"].intLevel\" id=\"intLevel."+(rowCount-1)+"\" value='"+intLevel+"'>";
		    row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listProdProcess["+(rowCount-1)+"].strProcessCode\" id=\"txtProcessCode."+(rowCount-1)+"\" value="+processCode+">";
		    row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listProdProcess["+(rowCount-1)+"].strProcessName\" id=\"txtProcessName."+(rowCount-1)+"\" value="+processName+">";
		    row.insertCell(3).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" name=\"listProdProcess["+(rowCount-1)+"].dblWeight\" id=\"txtWeight."+(rowCount-1)+"\" value="+processWt+">";
		    row.insertCell(4).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" name=\"listProdProcess["+(rowCount-1)+"].dblCycleTime\" id=\"txtCycleTime."+(rowCount-1)+"\" value="+cycleTime+">";
		    row.insertCell(5).innerHTML= '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRowForProcess(this)">';		    
		    return false;
		}
		
		
		function funResetProcessFields()
		{
			$("#txtProcessCode").val('');
			$("#txtProcessCode").focus();
		    $("#txtProcessWt").val('0');
		    $("#txtCycleTime").val('0');
		}
		
		function funDeleteRowForProcess(obj)
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblProdProcess");
		    table.deleteRow(index);
		}
		
		
		function funAddCharRow()
		{
			

		    var processCode = $("#txtCharProcessCode").val();
		    if(processCode=='')
		    {
		    	alert("Please select Process Code");
		    	$("#txtCharProcessCode").focus();
		    	return false;
		    }
		    var processName = $("#lblCharProcesName").text();
		    var charCode = $("#txtCharacteristics").val();
		    var charName = $("#lblCharName").text();
		    var tolerance = $("#txtTollerance").val();
		    var spec = $("#txtCharSpecification").val();
		    var guageNo = $("#txtGaugeNo").val();
		    		    		    
		    var table = document.getElementById("tblProdChar");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input name=\"listProdChar["+(rowCount)+"].strProcessCode\" class=\"Box\" id=\"txtProcessCode."+(rowCount)+"\" value="+processCode+">";
		    row.insertCell(1).innerHTML= "<input name=\"listProdChar["+(rowCount)+"].strProcessName\" class=\"Box\" id=\"txtProcessName."+(rowCount)+"\" value="+processName+">";
		    row.insertCell(2).innerHTML= "<input name=\"listProdChar["+(rowCount)+"].strCharCode\" class=\"Box\" id=\"txtCharCode."+(rowCount)+"\" value="+charCode+">";
		    row.insertCell(3).innerHTML= "<input name=\"listProdChar["+(rowCount)+"].strCharName\" class=\"Box\" id=\"txtCharName."+(rowCount)+"\" value="+charName+">";
		    row.insertCell(4).innerHTML= "<input name=\"listProdChar["+(rowCount)+"].strTollerance\" class=\"Box\" id=\"txtTolerance."+(rowCount)+"\" value="+tolerance+">";
		    row.insertCell(5).innerHTML= "<input name=\"listProdChar["+(rowCount)+"].strSpecf\" class=\"Box\" id=\"txtSpec."+(rowCount)+"\" value="+spec+">";
		    row.insertCell(6).innerHTML= "<input name=\"listProdChar["+(rowCount)+"].strGaugeNo\" class=\"Box\" id=\"strGaugeNo."+(rowCount)+"\" value="+guageNo+" >";
		    row.insertCell(7).innerHTML= '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRowForCharacter(this)">';
		    funResetCharFields();
		    return false;
		}
			
		function funResetCharFields()
		{
			$("#txtCharProcessCode").val('');
			$("#txtCharProcessCode").focus();
		    $("#lblCharProcesName").text('');
		    $("#txtCharacteristics").val('');
		    $("#lblCharName").text('');
		    $("#txtTollerance").val('');
		    $("#txtCharSpecification").val('');
		    $("#txtGaugeNo").val('');
		}
		
		
		
		
		function funDeleteRowForCharacter(obj)
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblProdChar");
		    table.deleteRow(index);
		}
		
		function funDuplicateReOrderLocation(strReOrderLocCode)
		{
		    var table = document.getElementById("tblReOrderLevel");
		    var rowCount = table.rows.length;		   
		    var flag=true;
		    if(rowCount > 0)
		    	{
				    $('#tblReOrderLevel tr').each(function()
				    {
					    if(strReOrderLocCode==$(this).find('input').val())// `this` is TR DOM element
	    				{
					    	alert("Location Allready added "+ strReOrderLocCode);
		    				flag=false;
	    				}
					});
				    
		    	}
		    return flag;
		}
		function btnReOrderLvl_Click()
		{
			var strReOrderLocCode=$("#txtROLocationCode").val();
       		 if(funDuplicateReOrderLocation(strReOrderLocCode))
       		 {
       			funAddReOrderRow();
       		 }
		}
		function funAddReOrderRow()
		{			
		    var locCode = $("#txtROLocationCode").val();
		    if(locCode=='')
		    {
		    	alert("Please Select Location");
		    	$("#txtROLocationCode").focus();
		    }
		    
		    var locName = $("#txtROLocationName").text();
		    var reOrderLvl = $("#txtReOrderLvl").val();
		    var reOrderQty = $("#txtReOrderQty").val();
		    var price = $("#txtPrice").val();
		    		    		    
		    var table = document.getElementById("tblReOrderLevel");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\"  size=\"10%\" name=\"listReorderLvl["+(rowCount)+"].strLocationCode\" id=\"txtLocationCode."+(rowCount)+"\" value="+locCode+">";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" name=\"listReorderLvl["+(rowCount)+"].strLocationName\" id=\"txtLocationName."+(rowCount)+"\" value='"+locName+"'>";
		    row.insertCell(2).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;width:100%\" size=\"100%\" name=\"listReorderLvl["+(rowCount)+"].dblReOrderLevel\" id=\"txtReOrderLvl."+(rowCount)+"\" value="+reOrderLvl+">";
		    row.insertCell(3).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;width:100%\" size=\"100%\" name=\"listReorderLvl["+(rowCount)+"].dblReOrderQty\" id=\"txtReOrderLvl."+(rowCount)+"\" value="+reOrderQty+">";
		    row.insertCell(4).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;width:100%\" size=\"100%\" name=\"listReorderLvl["+(rowCount)+"].dblPrice\" id=\"txtPrice."+(rowCount)+"\" value="+price+">";
		    row.insertCell(5).innerHTML= '<input type="button" value = "Delete"  class="deletebutton" onClick="funDeleteRowForReOrderLvl(this)">';
		    funResetReOrderFields();
		    return false;
		}
		
		
		
		function funAddReOrderRow1(locCode,locName,reOrderLvl,reOrderQty,price)
		{    		    
		    var table = document.getElementById("tblReOrderLevel");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\"  size=\"10%\" name=\"listReorderLvl["+(rowCount)+"].strLocationCode\" id=\"txtLocationCode."+(rowCount)+"\" value='"+locCode+"'>";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\"  size=\"45%\" name=\"listReorderLvl["+(rowCount)+"].strLocationName\" id=\"txtLocationName."+(rowCount)+"\" value='"+locName+"'>";
		    row.insertCell(2).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;width:100%\" size=\"100%\" name=\"listReorderLvl["+(rowCount)+"].dblReOrderLevel\" id=\"txtReOrderLvl."+(rowCount)+"\" value="+reOrderLvl+">";
		    row.insertCell(3).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;width:100%\" size=\"100%\"  name=\"listReorderLvl["+(rowCount)+"].dblReOrderQty\" id=\"txtReOrderLvl."+(rowCount)+"\" value="+reOrderQty+">";
		    row.insertCell(4).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;width:100%\" size=\"100%\"  name=\"listReorderLvl["+(rowCount)+"].dblPrice\" id=\"txtPrice."+(rowCount)+"\" value="+price+">";
		    row.insertCell(5).innerHTML= '<input type="button" class="deletebutton"  value = "Delete" onClick="funDeleteRowForReOrderLvl(this)">';
		    
		    return false;
		}
		
		
		function funResetReOrderFields()
		{
			$("#txtROLocationCode").val('');
			$("#txtROLocationCode").focus();
			$("#txtROLocationName").val('');
		    $("#txtReOrderLvl").val('0');
		    $("#txtReOrderQty").val('0');
		    $("#txtPrice").val('0');
		}		
		
		function funDeleteRowForReOrderLvl(obj)
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblReOrderLevel");
		    table.deleteRow(index);
		}
		
		
		function funHelp(transactionName)
		{
			fieldName=transactionName;
			if(fieldName=='processCodeOnCharTab' || fieldName=='processCodeOnProcessTab')
			{
				transactionName='processcode';
			}
			if(fieldName=='locationForReOrder')
			{
				transactionName='locationmaster';
			}			
	       
	      //  window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1050px;dialogLeft:200px;")
	    
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1050px;dialogLeft:200px;")
		
		}
		
		
		function funSetData(code)
		{
			switch (fieldName)
			{
			    case 'prodmasterPropwise':
			    	funSetProduct(code);
			    	
			        break;
			        
			    case 'suppcode':
			    	funSetSupplier(code);
			        break;
			        
			    case 'subgroup':
			    	funSetSubGroup(code);
			        break;
			        
			    case 'locationmaster':
			    	funSetLocation(code);
			        break;
			        
			    case 'locationForReOrder':
			    	funSetLocationOnReOrder(code);
			        break;
			        
			    case 'attributemaster':
			    	funSetAttribute(code);
			        break;
			        
			    case 'charCode':
			    	funSetCharacteristics(code);
			        break;
			        
			    case 'processCodeOnCharTab':
			    	funSetProcessOnCharTab(code);
			        break;
			        
			    case 'processCodeOnProcessTab':
			    	funSetProcessOnProcessTab(code);
			        break;
			        
			    case 'custMaster' :
			    	funSetCustomer(code);
			    	 break;
			    	
			    case 'manufactureMaster' :
			    	funSetManufacture(code);
			    	 break;
			         
			        
			}
		}
		
		function funSetManufacture(code)
		{
			$("#txtManufacturerCode").val(code);
			var searchurl=getContextPath()+"/loadManufactureMasterData.html?code="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strAreaCode=='Invalid Code')
				        	{
				        		alert("Invalid Manufacturer Code");
				        		$("#txtManufacturerCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtManufacturerCode").val(code);
					        	$("#lblManufacturerName").text(response.strManufacturerName);
					        	
					        	
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
		
		function funSetCharacteristics(code)
		{
			var searchUrl=getContextPath()+"/loadCharData.html?charCode="+code;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {   		 	
				    	$("#txtCharacteristics").val(response.strCharCode);
				    	$("#lblCharName").text(response.strCharName);
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
		
		
		function funSetProcessOnProcessTab(code)
		{
			var searchUrl=getContextPath()+"/loadProcessData1.html?processCode="+code;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	//alert(response.strProcessCode);
				    	$("#txtProcessCode").val(response.strProcessCode);
				    	gProcessName=response.strProcessName;
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
		
		function funSetProcessOnCharTab(code)
		{
			var searchUrl=getContextPath()+"/loadProcessData1.html?processCode="+code;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	$("#txtCharProcessCode").val(response.strProcessCode);
				    	$("#lblCharProcesName").text(response.strProcessName);
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
				
		function funSetAttribute(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadAttrMaster.html?attCode="+code;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if('Invalid Code' == response.strAttCode){
				    		alert('Invalid Code');
				    		$("#txtAttrCode").val('');
					    	$("#txtAttrName").val('');
					    	$("#txtAttrCode").focus();
				    	}else{
				    		$("#txtAttrCode").val(response.strAttCode);
					    	$("#txtAttrName").val(response.strAttName);
					    	gAttValueCode=response.strAVCode;
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
				       		$("#txtLocCode").val('');
				       		$("#txtLocName").text('');
				       		$("#txtLocCode").focus();
				       	}
				       	else
				       	{
				    	$("#txtLocCode").val(response.strLocCode);
				    	$("#txtLocName").text(response.strLocName);
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
		
		
		function funSetLocationOnReOrder(code)
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
				       		$("#txtROLocationCode").val('');
				       		$("#txtROLocationName").text('');
				       		$("#txtROLocationCode").focus();
				       	}
				       	else
				       	{
				    	$("#txtROLocationCode").val(response.strLocCode);
				    	$("#txtROLocationName").text(response.strLocName);
				    	$("#txtReOrderLvl").focus();
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
		
		
		function funSetSubGroup(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadSubGroupMasterData.html?subGroupCode="+code;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if('Invalid Code' == response.strSGCode){
				    		alert('Invalid Code');
				    		$("#txtSubGroupCode").val('');
				    		$("#txtSubGroupCode").focus();
				    	}else{
				    	$("#txtSubGroupCode").val(code);
				    	$("#txtSubGroupName").text(response.strSGName);
				    	
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
		
		function funSetSupplier(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadSupplierMasterData.html?partyCode="+code;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if('Invalid Code' == response.strPCode){
				    		alert('Invalid Code');
				    		$("#txtSupplierId").val('');
					        $("#txtSupplierName").val('');
					        $("#txtSupplierId").focus();
				    	}
				    	else
				    	{
					      	$("#txtSupplierId").val(response.strPCode);
					        $("#txtSupplierName").val(response.strPName);
					        $("#txtLastCost").focus();
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
		
		
		function funCheckProductCount(code)
		{
			var searchUrl1="";
			searchUrl1=getContextPath()+"/getProductCount.html?prodCode="+code;
			
			$.ajax
			({
		        type: "GET",
		        url: searchUrl1,
			    dataType: "json",
			    success: function(response)
			    {
			    	
			    	if(parseInt(response)>0)
			    	{
			    		$("#txtProdName").prop("readonly", true);
			    	}
			    	else
			    	{
			    		$("#txtProdName").prop("readonly", false);
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
			searchUrl=getContextPath()+"/loadProductMasterDataforDblCostRM.html?prodCode="+code;
			$.ajax
			({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	//alert(response.strUOM);
			    	if('Invalid Code' == response.strProdCode){
			    		alert('Invalid Product Code');
				    	$("#txtProdCode").val('');
				    	$("#txtProdName").val('');
				    	$("#txtProdCode").focus();
			    	}
			    	else
			    	{
				    	$("#txtProdCode").val(response.strProdCode);
				    	$('#txtProdCode').attr('readonly', true);
				    	//funCheckProductCount(response.strProdCode);
				    	$("#txtProdName").val(response.strProdName);
				    	if('<%=session.getAttribute("NameChangeInProdMast").toString()%>'=='N')
				    		{
				    			$('#txtProdName').attr('readonly', true);
				    		}
				    	$("#txtPartNo").val(response.strPartNo);
				    	$("#txtUOM").val(response.strUOM.toUpperCase());
				    	$("#txtSubGroupCode").val(response.strSGCode);
				    	if(response.strSGCode !=""){
				    		funSetSubGroup(response.strSGCode);
				    	}
				    	$("#txtCostRM").val(response.dblCostRM);
				    	$("#txtCostManu").val(response.dblCostManu);
				    	$("#txtLocCode").val(response.strLocCode);
				    	if(response.strLocCode!="")
				    		{
				    			funSetLocation(response.strLocCode);
				    		}
				    	$("#txtOrderUptoLvl").val(response.dblOrduptoLvl);
				    	$("#txtReorderLvl").val(response.dblReorderLvl);
				    	$("#txtProdType").val(response.strProdType);
				    	$("#cmbCalAmtOn").val(response.strCalAmtOn);
				    	$("#txtWeight").val(response.dblWeight);
				    	$("#txtWtUOM").val(response.strWtUOM);
				    	$("#txtBatchQty").val(response.dblBatchQty);
				    	$("#txtMaxLvl").val(response.dblMaxLvl);
				    	$("#txtBinNo").val(response.strBinNo);
				    	$("#cmbClass").val(response.strClass);
				    	$("#txtTariffNo").val(response.strTariffNo);
				    	$("#txtListPrice").val(response.dblListPrice);
				    	$("#cmbTaxIndicator").val(response.strTaxIndicator);
				    	$("#txtDelPeriod").val(response.intDelPeriod);			    	
				    	$("#txtBomCal").val(response.strBomCal);
				    	$("#txtUnitPrice").val(response.dblUnitPrice);
				    	$("#txtDesc").val(response.strDesc);
				    	$("#txtSpecification").val(response.strSpecification);
				    	$("#txtRecievedUOM").val(response.strReceivedUOM.toUpperCase());
				    	$("#txtRecievedConversionRatio").val(response.dblReceiveConversion);
				    	$("#txtIssueUOM").val(response.strIssueUOM.toUpperCase());
				    	$("#txtIssueConversionRatio").val(response.dblIssueConversion);
				    	$("#txtRecipeUOM").val(response.strRecipeUOM.toUpperCase());
				    	$("#txtRecipeConversionRatio").val(response.dblRecipeConversion);
				    	$("#txtCustItemCode").val(response.dblRecipeConversion);
				    	$("#txtYieldPer").val(response.dblYieldPer);
				    	$("#txtProdNameMarathi").val(response.strProdNameMarathi);
				    	$("#txtManufacturerCode").val(response.strManufacturerCode);
				    	
				    	toggle("txtProdNameMarathi");
				    	if(response.strNotInUse=='Y')
				    	{
				    		document.getElementById("chkNotInUse").checked=true;
				    	}
				    	else
				    	{
				    		document.getElementById("chkNotInUse").checked=false;
				    	}
				    	
				    	if(response.strExpDate=='Y')
				    	{
				    		document.getElementById("chkExpDate").checked=true;
				    	}
				    	else
				    	{
				    		document.getElementById("chkExpDate").checked=false;
				    	}
				    	
				    	if(response.strLotNo=='Y')
				    	{
				    		document.getElementById("chkLotNo").checked=true;
				    	}
				    	else
				    	{
				    		document.getElementById("chkLotNo").checked=false;
				    	}
				    	
				    	if(response.strRevLevel=='Y')
				    	{
				    		document.getElementById("chkRevLevel").checked=true;
				    	}
				    	else
				    	{
				    		document.getElementById("chkRevLevel").checked=false;
				    	}
				    	
				    	if(response.strSlNo=='Y')
				    	{
				    		document.getElementById("chkSlNo").checked=true;
				    	}
				    	else
				    	{
				    		document.getElementById("chkSlNo").checked=false;
				    	}
				    	
				    	if(response.strExceedPO=='Y')
				    	{
				    		document.getElementById("chkExceedPO").checked=true;
				    	}
				    	else
				    	{
				    		document.getElementById("chkExceedPO").checked=false;
				    	}
				    	
				    	if(response.strStagDel=='Y')
				    	{
				    		document.getElementById("chkStagDel").checked=true;
				    	}
				    	else
				    	{
				    		document.getElementById("chkStagDel").checked=false;
				    	}
				    	if(response.strForSale=='Y')
				    	{
				    		document.getElementById("chkForSale").checked=true;
				    	}
				    	else
				    	{
				    		document.getElementById("chkForSale").checked=false;
				    	}
				    	
				    	if(response.strNonStockableItem=='Y')
				    	{
				    		document.getElementById("strNonStockableItem").checked=true;
				    	}
				    	else
				    	{
				    		document.getElementById("strNonStockableItem").checked=false;
				    	}
				    	$("#txtBarCode").val(response.strBarCode);
				    	$("#txtMRP").val(response.dblMRP);
				    	
				    	if(response.strPickMRPForTaxCal=='Y')
				    	{
				    		document.getElementById("chkPickMRPForTaxCal").checked=true;
				    	}
				    	else
				    	{
				    		document.getElementById("chkPickMRPForTaxCal").checked=false;
				    	}
				    	
				    	if(response.strExciseable=='Y')
				    	{
				    		document.getElementById("chkExciseable").checked=true;
				    	}
				    	else
				    	{
				    		document.getElementById("chkExciseable").checked=false;
				    	}
				    	
				    	if(response.strComesaItem=='Y')
				    		document.getElementById("chkComesaRegionItem").checked=true;
				    	
				    	else
				    		document.getElementById("chkComesaRegionItem").checked=false;
				    	
				    	
				    	funGetImage(code);
				    	funSetProdSupplierData(code);
				    	funSetProdAttributeData(code);
				    	funSetProdProcessData(code);
				    	funSetProdReorderData(code);
				    	funGetBatchData(code);
				    	funLoadCustomerData(code)
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
		
		
		function funGetImage(prodCode)
		{
			searchUrl=getContextPath()+"/getProdImage.html?prodCode="+prodCode;
			$("#itemImage").attr('src', searchUrl);
			
		}
		
		
		
		function funSetProdSupplierData(prodCode)
		{
			searchUrl=getContextPath()+"/loadSupplierData.html?prodCode="+prodCode;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funRemSuppProdRows();
				    	$.each(response, function(i,item)
				    	{
				    		funAddSuppRow1(response[i].strSuppCode,response[i].strSuppName,response[i].dblLastCost,response[i].dtLastDate
				    				,response[i].strSuppUOM,response[i].strLeadTime,response[i].strSuppPartNo,response[i].strSuppPartDesc
				    				,response[i].dblMaxQty,response[i].strDefault);
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
		
		
		function funRemSuppProdRows()
	    {
		/* 	var table = document.getElementById("tblProdSupp");
			var rowCount = table.rows.length;
			for(var i=rowCount-1;i>=1;i--)
			{
				table.deleteRow(i);
			} */
			$('#tblProdSupp tbody > tr').remove();
	    }
		
		function funSetProdAttributeData(prodCode)
		{
			searchUrl=getContextPath()+"/loadAttrData.html?prodCode="+prodCode;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funRemAttrProdRows();
				    	$.each(response, function(i,item)
				    	{
				    		funAddAttRow1(response[i].strAttCode,response[i].dblAttValue,response[i].strAttName,response[i].strAVCode);
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
		
		
		function funRemAttrProdRows()
	    {
			var table = document.getElementById("tblProdAtt");
			var rowCount = table.rows.length;
			for(var i=rowCount-1;i>=1;i--)
			{
				table.deleteRow(i);
			}
	    }
		
		
		function funSetProdProcessData(prodCode)
		{
			searchUrl=getContextPath()+"/loadProdProcessData.html?prodCode="+prodCode;
			//alert(searchUrl);
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funRemProcessProdRows();
				    	$.each(response, function(i,item)
				    	{
				    		funAddProcessRow1(response[i].intLevel,response[i].strProcessCode,response[i].strProcessName,response[i].dblWeight,response[i].dblCycleTime);
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
		
		function funRemProcessProdRows()
	    {
			var table = document.getElementById("tblProdProcess");
			var rowCount = table.rows.length;
			for(var i=rowCount-1;i>=1;i--)
			{
				table.deleteRow(i);
			}
	    }
		
		
		function funSetProdReorderData(prodCode)
		{
			searchUrl=getContextPath()+"/loadProdReorderData.html?prodCode="+prodCode;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funRemReorderProdRows();
				    	$.each(response, function(i,item)
				    	{
				    		funAddReOrderRow1(response[i].strLocationCode,response[i].strLocationName,response[i].dblReOrderLevel,response[i].dblReOrderQty,response[i].dblPrice);
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
		function funGetBatchData(prodCode)
		{
			searchUrl=getContextPath()+"/loadProdBatchData.html?prodCode="+prodCode;
			//alert(searchUrl);
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funRemProdBatchRows();
				    	$.each(response, function(i,item)
				    	{
				    		//alert(response.strBatchCode);
				    		funAddBatchRow(response[i].strBatchCode,response[i].strManuBatchCode,response[i].strTransCode,response[i].dblQty,response[i].dtExpiryDate);
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
		
		function funAddBatchRow(BatchCode,ManuBatchCode,GRNCode,penQty,ExpiryDate)
		{    		    
		    var table = document.getElementById("tblBatch");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\"  size=\"10%\"  id=\"txtBatchCode."+(rowCount)+"\" value='"+BatchCode+"'>";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\"  style=\"text-align: left\"size=\"80%\" id=\"txtManuBatchCode."+(rowCount)+"\" value='"+ManuBatchCode+"'>";
		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\"  size=\"14%\" id=\"txtGRNCode."+(rowCount)+"\" value="+GRNCode+">";
		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;width:77%\" size=\"80%\" id=\"txtPendQty."+(rowCount)+"\" value="+parseFloat(penQty).toFixed(maxQuantityDecimalPlaceLimit)+">";
		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\"  size=\"7%\" id=\"txtExpDate."+(rowCount)+"\" value="+ExpiryDate+">";
		    return false;
		}
		
		function funRemProdBatchRows()
	    {
			var table = document.getElementById("tblBatch");			
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
	    }
		
		function funRemReorderProdRows()
	    {
			var table = document.getElementById("tblReOrderLevel");			
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
	    }
		
		function funGetAllLocation()
		{			
			var isOk=confirm("Do You Want to Select All Location?");
			if(isOk)
			{
				prodCode=$('#txtProdCode').val();
				var searchUrl=getContextPath()+"/loadProdReorderAllLocation.html?prodCode="+prodCode;
				//alert(searchUrl);
				$.ajax({
				        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funRemReorderProdRows();
				    	$.each(response, function(i,item)
				    	{
				    		funAddReOrderRow1(response[i].strLocationCode,response[i].strLocationName,response[i].dblReOrderLevel,response[i].dblReOrderQty,response[i].dblPrice);
				    	});
				    },
					error: function(e)
				    {
				       	alert('Error:=' + e);
				    }
			      });
			}
		}
		
		$(document).ready(function()
		{
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
		});
		
		$(function()
				{
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
					
					$('#txtProdName').blur(function () {
						 var strProdName=$('#txtProdName').val();
					      //alert(strProdName);
					      var st = strProdName.replace(/\s{2,}/g, ' ');
					      $('#txtProdName').val(st);
						});
					
					$('#txtSubGroupCode').keydown(function(e){
						 if (e.which == 9){
							 
						      var code=$('#txtSubGroupCode').val();
						  	if (code.trim().length > 0) {
						  		funSetSubGroup(code);
						  	}
						     
						  }
					});
					
					$('#txtLocCode').keydown(function(e){
						 if (e.which == 9){
							 
						      var code=$('#txtLocCode').val();
						  	if (code.trim().length > 0) {
						  		funSetLocation(code);
						  	}
						     
						  }
					});
					
					$('#txtSupplierId').keydown(function(e){
						 if (e.which == 9){
							 
						      var code=$('#txtSupplierId').val();
						  	if (code.trim().length > 0) {
						  		funSetSupplier(code);
						  	}
						     
						  }
					});
					
					
					
					$('#txtAttrCode').keydown(function(e){
						 if (e.which == 9){
							 
						      var code=$('#txtAttrCode').val();
						  	if (code.trim().length > 0) {
						  		funSetAttribute(code);
						  	}
						     
						  }
					});
					
					
					$('#txtProcessCode').keydown(function(e){
						 if (e.which == 9){
							 
						      var code=$('#txtProcessCode').val();
						  	if (code.trim().length > 0) {
						  		funSetProcessOnProcessTab(code);
						  	}
						     
						  }
					});
					
					$('#txtROLocationCode').keydown(function(e){
						 if (e.which == 9){
							 
						      var code=$('#txtROLocationCode').val();
						  	if (code.trim().length > 0) {
						  		funSetLocationOnReOrder(code);
						  	}
						     
						  }
					});
					
				});
		
		function funApplyNumberValidation(){
			$(".numeric").numeric();
			$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
			$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
			$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
		    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
		    $(".decimal-places-amt").numeric({ decimalPlaces: maxAmountDecimalPlaceLimit, negative: false });
		}
		
		$(function()
				{			
					$('#baseUrl').click(function() 
					{  
						if($("#txtProdCode").val().trim()=="")
						{
							alert("Please Select Product  Code");
							return false;
						}
						window.open('attachDoc.html?transName=frmProductMaster.jsp&formName=Product Master&code='+$('#txtProdCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
					});
				});
		
		function funCallFormAction(actionName,object) 
		{
			var flg=true;
			if( $('#txtProdName').val().trim()=="")
			{
				alert("Please Enter Product Name");
				//$('#txtProdName').focus();
				return false;
			}
			if( $('#txtSubGroupCode').val().trim()=="")
			{
				alert("Please Select SubGroup Code");
				//$('#txtSubGroupCode').focus();
				return false;
			}
			/* if( $('#txtLocCode').val().trim()=="")
			{
				alert("Please Select Location Code");
				$('#txtLocCode').focus();
				return false;
			} */
			if($('#txtUOM').val().trim()=="")
			{
				alert("Please Select UOM");
				$('#txtUOM').focus();
				return false;
			}
			
			if($('#txtProdCode').val()=='')
			{
				var code = $('#txtProdName').val().trim();
				 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/checkProdName.html?prodName="+code,
				        async: false,
				        dataType: "text",
				        success: function(response)
				        {
				        	if(response=="true")
				        		{
				        			alert("Product Name Already Exist!");
				        			$('#txtProdName').focus();
				        			flg= false;
					    		}
					    	else
					    		{
					    			flg=true;
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
			//alert("flg"+flg);
			return flg;
		}
		function funResetFields()
		{
			location.reload(true); 
		}
		 function funShowImagePreview(input)
		 {
			 if (input.files && input.files[0])
			 {
				 var filerdr = new FileReader();
				 filerdr.onload = function(e) 
				 {
				 $('#itemImage').attr('src', e.target.result);
				 }
				 filerdr.readAsDataURL(input.files[0]);
			 }
			 
			
		 }
		 function funUOMChange(object)
		 {
			 var index=object.parentNode.parentNode.rowIndex;
			 var strUOM=object.value;
			 $("#txtRecievedUOM").val(strUOM.toUpperCase());
		 }
		 
		 function funRecivedUOMChange(object)
		 {
			 var index=object.parentNode.parentNode.rowIndex;
			 var strUOM=object.value;
			 $("#txtUOM").val(strUOM.toUpperCase());
		 }
		 
		 function funGetAllLocationOnload()
			{			
				
					prodCode=$('#txtProdCode').val();
					var searchUrl=getContextPath()+"/loadProdReorderAllLocation.html?prodCode="+prodCode;
					//alert(searchUrl);
					$.ajax({
					        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	funRemReorderProdRows();
					    	$.each(response, function(i,item)
					    	{
					    		funAddReOrderRow1(response[i].strLocationCode,response[i].strLocationName,response[i].dblReOrderLevel,response[i].dblReOrderQty,response[i].dblPrice);
					    	});
					    },
						error: function(e)
					    {
					       	alert('Error:=' + e);
					    }
				      });
				
			} 
		 
		 
    function btnCust_Click()
    {
    	var searchUrl = "";
		searchUrl = getContextPath()+ "/loadAllCustomer.html";
		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			beforeSend : function(){
				 $("#wait").css("display","block");
		    },
		    complete: function(){
		    	 $("#wait").css("display","none");
		    },
			success : function(response) {
				if (response.strPCode == 'Invalid Code') {
					alert("Invalid Customer Code");
					
				} else
				{
					$.each(response, function(i,item)
					 		{
						funfillCustGrid(response[i].strPCode,response[i].strPName);
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
    		 
    
    function funfillCustGrid(strCustCode,strCustName)
	{
		
		 	var table = document.getElementById("tblCust");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    rowCount=listRowCustTabGrid;
		    // onClick=Javacsript:funLoadAllProduct('"+strCustCode+"')
		   
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" name=\"listProdCustMargin["+(rowCount)+"].strSuppCode\" id=\"strCustCode."+(rowCount)+"\" value='"+strCustCode+"' >";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"50%\"  id=\"strCName."+(rowCount)+"\" value='"+strCustName+"' >";
		    row.insertCell(2).innerHTML= "<input type=\"text\"  size=\"20%\" required = \"required\" style=\"text-align: right;width:50%\"  name=\"listProdCustMargin["+(rowCount)+"].dblStandingOrder\" id=\"dblMargin."+(rowCount)+"\" value='1' >";
		    row.insertCell(3).innerHTML= "<input type=\"text\"  size=\"20%\" required = \"required\" style=\"text-align: right;width:50%\" name=\"listProdCustMargin["+(rowCount)+"].dblMargin\" id=\"dblMargin."+(rowCount)+"\" value='0' >";
		    row.insertCell(4).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="funDeleteRowForCust(this)">';
	
		    listRowCustTabGrid++;
	}
		 
    
    function funDeleteRowForCust(obj)
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblCust");
	    table.deleteRow(index);
	}
    
    
    function funAddCust()
    {
    	
    	var table = document.getElementById("tblCust");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    rowCount=listRowCustTabGrid;
	    
	    var strCustCode=$('#txtCustCode').val();
	    var strCustName=$('#lblCustName').text();
	    var dblStandingOrder=$('#txtStandingOrder').val();
	    var dblMargin=$('#txtMargin').val();
	    
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" name=\"listProdCustMargin["+(rowCount)+"].strSuppCode\" id=\"strCustCode."+(rowCount)+"\" value='"+strCustCode+"' >";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"50%\"  id=\"strCName."+(rowCount)+"\" value='"+strCustName+"' >";
	    row.insertCell(2).innerHTML= "<input type=\"text\"  size=\"20%\" required = \"required\" style=\"text-align: right;width:50%\"  name=\"listProdCustMargin["+(rowCount)+"].dblStandingOrder\" id=\"dblStandingOrder."+(rowCount)+"\" value='"+dblStandingOrder+"' >";
	    row.insertCell(3).innerHTML= "<input type=\"text\"  size=\"20%\" required = \"required\" style=\"text-align: right;width:50%\" name=\"listProdCustMargin["+(rowCount)+"].dblMargin\" id=\"dblMargin."+(rowCount)+"\" value='"+dblMargin+"' >";
	    row.insertCell(4).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="funDeleteRowForCust(this)">';
	    
	    listRowCustTabGrid++;
	    funRemCustomerFields();
    }
    
    
    
    function funSetCustomer(code)
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
	        			$("#txCustCode").val('');
	        			$("#txCustCode").focus();
	        			
	        		}else{
	        			$("#txtCustCode").val(response.strPCode);
						$("#lblCustName").text(response.strPName);
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
	
    function funRemCustomerRows()
    {
		var table = document.getElementById("tblCust");			
		var rowCount = table.rows.length;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
    }
    
    function funRemCustomerFields()
    {
    	$('#txtCustCode').val("");
	    $('#lblCustName').text("");
	    
	    $('#txtStandingOrder').val("1");
	    $('#txtMargin').val("0");
    }
    
    
    function funLoadCustomerData(prodCode)
    {
    	//var prodCode=$('#txtProdCode').val();
    	
    	gurl=getContextPath()+"/loadCustomerData.html?prodCode="+prodCode;
		$.ajax({
	        type: "GET",
	        url: gurl,
	        dataType: "json",
	        success: function(response)
	        {		      
	        	var count=0;
	        	$.each(response, function(i,item)
					{	
	        		count=i;
			        	funSetCustGrid(response[i].strSuppCode,response[i].strSuppName,
			        			response[i].dblStandingOrder,response[i].dblMargin);
			        	 
			        	
			        	
					});	 	
	        	listRowCustTabGrid=count+i;	
	        		
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
    
    
    function funSetCustGrid(strCustCode,strCustName,dblStandingOrder,dblMargin)
    {
    	
    	var table = document.getElementById("tblCust");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	   	rowCount =listRowCustTabGrid;
	       
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" name=\"listProdCustMargin["+(rowCount)+"].strSuppCode\" id=\"strCustCode."+(rowCount)+"\" value='"+strCustCode+"' >";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"50%\"  id=\"strCName."+(rowCount)+"\" value='"+strCustName+"' >";
	    row.insertCell(2).innerHTML= "<input type=\"text\"  size=\"20%\" required = \"required\" style=\"text-align: right;width:50%\"  name=\"listProdCustMargin["+(rowCount)+"].dblStandingOrder\" id=\"dblStandingOrder."+(rowCount)+"\" value='"+dblStandingOrder+"' >";
	    row.insertCell(3).innerHTML= "<input type=\"text\"  size=\"20%\" required = \"required\" style=\"text-align: right;width:50%\" name=\"listProdCustMargin["+(rowCount)+"].dblMargin\" id=\"dblMargin."+(rowCount)+"\" value='"+dblMargin+"' >";
	    row.insertCell(4).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="funDeleteRowForCust(this)">';
	    
	    listRowCustTabGrid++;
	    
    }
    
    
    
		 
	</script>
	

</head>
<body>
<div id="formHeading">
		<label>Product Master</label>
	</div>
	<s:form name="productMasterForm" method="POST" action="saveProductMaster.html?saddr=${urlHits}" enctype="multipart/form-data">	
	
	<br>
		<table
			style="border: 0px solid black; width: 100%; height: 100%; margin-left: auto; margin-right: auto; background-color: #C0E4FF;">
			<tr>
				<td>
				
				<div id="tab_container" style="height: 435px">
						<ul class="tabs">
							<li class="active" data-state="tab1"
								style="width: 6%; padding-left: 1.2%">General</li>
							<li data-state="tab2" style="width: 6%; padding-left: 2%">Tracking</li>
							<li data-state="tab3" style="width: 8%; padding-left: 1%">Specification</li>
							<li data-state="tab4" style="width: 7%; padding-left: 1.5%">Supplier</li>
							<li data-state="tab5" style="width: 6%; padding-left: 1.8%">Custom</li>
							<li data-state="tab6"  style="width: 6%; padding-left: 1.8%">Process</li>
							<li data-state="tab7"  style="width: 9%; padding-left: 1.5%">Characteristics</li>
							<li data-state="tab8"  style="width: 8%; padding-left: 1.3%">Conversion</li>
							<li data-state="tab9" style="width: 9%; padding-left: 1.3%">Re-Order Level</li>
							<li data-state="tab10" style="width: 4%; padding-left: 1.3%">Batch</li>
							<li data-state="tab11" style="width: 6%; padding-left: 1.3%">Customer</li>
						</ul>
						<!--General Tab  Start-->
						<div id="tab1" class="tab_content" style="height: 400px">
							<br> <br>
								<table class="masterTable">				
				<tr>
			        <th align="right" colspan="5"> <a id="baseUrl" href="#">Attach Documents</a>&nbsp; &nbsp; &nbsp; &nbsp;  </th>
			         
			    </tr>
			    
			    	<tr>
				        <td  width="15%"><label >Code </label></td>
				        <td width="8%"><s:input  id="txtProdCode" name="prodCode" path="strProdCode" value="" ondblclick="funHelp('prodmasterPropwise')" cssClass="searchTextBox"/></td>				    
				    	<td width="18%"><label >Name </label></td>
				        <td colspan="2"><s:input type="text" id="txtProdName" cssStyle="width:80%;text-transform: uppercase;"  name="prodName" path="strProdName"   cssClass="BoxW116px" /></td>
			   		</tr>
			    	
			    	<tr>
				        <td><label >POS Item Code  </label></td>
				        <td><s:input type="text" id="txtPartNo" name="partNo" path="strPartNo"  cssClass="BoxW116px"/></td>
				    	<td><label >UOM</label></td>
				        <td><s:select id="txtUOM" name="txtUOM"
							path="strUOM" items="${uomList}"  cssClass="BoxW124px" onchange="funUOMChange(this)"/><%-- <s:input id="txtUOM" name="uom" path="strUOM" cssClass="BoxW116px" /> --%></td>
				        <td></td>
				       
			   		</tr>
			    
			    	<tr>
				        <td><label >Sub-Group Code</label></td>
				        <td ><s:input id="txtSubGroupCode" name="subGroupCode" path="strSGCode" ondblclick="funHelp('subgroup')" autocomplete="off" cssClass="searchTextBox"/></td>
				       <td><label id="txtSubGroupName"></label></td>
				        <td></td>
				       <td rowspan="9" width="25%" style="background-color: #C0E4FF;border: 1px solid black;"><img id="itemImage" src="" width="196px" height="219px" alt="Item Image"  ></td>
			   		</tr>
				    
			    	<tr>
				        <td><label >Purchase Price  </label></td>
				        <td><s:input id="txtCostRM" name="costRM" path="dblCostRM" cssClass="decimal-places-amt numberField"/></td>
				    	<td><label >Cost of Manf/Unit</label></td>
				        <td><s:input id="txtCostManu" name="costManu" path="dblCostManu" cssClass="decimal-places-amt numberField" /></td>
				       
			  		</tr>
			   
			   		<tr>
				        <td><label >Location Code </label></td>
				        <td><s:input id="txtLocCode" name="locCode" autocomplete="off" path="strLocCode" ondblclick="funHelp('locationmaster')"  cssClass="searchTextBox"/></td>
			    		<td><label id="txtLocName"></label></td>
			    	</tr>
				    
				 	<tr>
				        <td><label >Reorder Qty  </label></td>
				        <td><s:input id="txtOrderUptoLvl" name="orduptoLvl" path="dblOrduptoLvl" cssClass="decimal-places numberField"/></td>
				    	<td><label >Minimum Level</label></td>
				        <td><s:input id="txtReorderLvl" name="reorderLvl" path="dblReorderLvl" cssClass="decimal-places-amt numberField"/></td>
			  		</tr>
				  
				  	<tr>
				        <td><label >Item Type</label></td>
				        <td><s:select id="txtProdType" name="prodType" path="strProdType" items="${typeList}" cssClass="BoxW124px"/></td>
				    	<td><label >Calulation of amount On</label></td>
				        <td><s:select id="cmbCalAmtOn" name="calAmtOn" path="strCalAmtOn" items="${calAmtOnList}" cssClass="BoxW124px"/></td>
			   		</tr>
			   
			  	 	<tr>
				        <td><label >Weight</label></td>
				        <td><s:input id="txtWeight" name="weight" path="dblWeight" cssClass="decimal-places numberField"/></td>
				    	<td><label >Wt UOM</label></td>
				        <td><s:input id="txtWtUOM" name="wtUOM" path="strWtUOM" cssClass="decimal-places numberField"/></td>
			  		</tr>
			   
			  		<tr>
				        <td><label >Quantity in a Batch</label></td>
				        <td><s:input id="txtBatchQty" name="batchQty" path="dblBatchQty" cssClass="decimal-places numberField"/></td>
				    	<td><label >Maximum Level</label></td>
				    	
				        <td><s:input id="txtMaxLvl" name="maxLvl" path="dblMaxLvl" cssClass="decimal-places numberField" /></td>
				       
			  	 	</tr>
			   
			   		<tr>
				        <td><label >bin No.</label></td>
				        <td><s:input id="txtBinNo" name="binNo" path="strBinNo" cssClass="BoxW116px"/></td>
				    	<td><label >Class</label></td>
				        <td><s:select id="cmbClass" name="class" path="strClass" items="${classList}" cssClass="BoxW48px" /></td>
			   		
			   		</tr>
			   
			  		<tr>
				        <td><label >Tariff No.</label></td>
				        <td><s:input id="txtTariffNo" name="tariffNo" path="strTariffNo" cssClass="BoxW116px"/></td>
				    	<td><label >List Price</label></td>
				        <td><s:input id="txtListPrice" name="listPrice" path="dblListPrice" cssClass="decimal-places-amt numberField"/></td>
				        
			  		</tr>
			  		
			  		<tr>
				        <td><label>Product Image</label></td>
				        <td ><input  id="prodImage" name="prodImage"  type="file" accept="image/gif,image/png,image/jpeg" onchange="funShowImagePreview(this);" /></td>
				        
				        <td><label>Product Bar Code</label></td>
				        <td><s:input id="txtBarCode" name="barCode" path="strBarCode"  cssClass="BoxW116px" style="width: 90%"/></td>
			    	</tr>
			    	
			    	<tr>
				        <td><label>Product MRP</label></td>
				        <td><s:input id="txtMRP" name="MRP" path="dblMRP"  cssClass="BoxW116px" style="width: 49%"/></td>
				        <td><label>Standard/Sale Price</label></td>
									<td><s:input id="txtUnitPrice" name="unitPrice"
											path="dblUnitPrice" cssClass="decimal-places-amt numberField"/></td>
			    	</tr>
			    	
			   <tr>
			      <td><label>Product Name Marathi</label></td>
				     <%--   <td>
				        <script type="text/javascript">
            				CreateHindiTextBox("txtProdNameMarathi",25);
        				</script> --%> 
        				<td><s:input id="txtProdNameMarathi" name="txtProdNameMarathi" path="strProdNameMarathi"  cssClass="BoxW116px" style="width: 90%;font-size: 16px;font-family: shivaji01;"/></td>
<!-- 				        </td> -->
			   
				<td width="150px">Manufacturer Code</td>
				<td><s:input id="txtManufacturerCode" path="strManufacturerCode"
						cssClass="searchTextBox jQKeyboard form-control" readonly="true" ondblclick="funHelp('manufactureMaster')" /></td>
			   <td width="18px"><label id="lblManufacturerName" > </label></td>
			
			</tr>
			    	
			</table>
							
						</div>						
						<!--General Tab  End-->
						
						
						
						<!--Tracking tab Start  -->						
						<div id="tab2" class="tab_content" style="height: 400px">
							<br> <br>
							<table class="masterTable">
								<tr>
								<th colspan="5"></th>
								</tr>
								<tr>
									
									<td width="18%"><label>Item Not In Use</label></td>
									<td  width="18%"><s:checkbox id="chkNotInUse" name="notInUse"
											path="strNotInUse" value="" /></td>
									
									<td  width="18%"><label>Expiry Date</label></td>
									<td><s:checkbox id="chkExpDate" name="expDate"
											path="strExpDate" value="" /></td>
									</tr>
									<tr>
									<td><label>Batch No</label></td>
									<td><s:checkbox id="chkLotNo" name="lotNo" path="strLotNo"
											value="" /></td>
									
									<td><label>Revision Level</label></td>
									<td><s:checkbox id="chkRevLevel" name="revLevel"
											path="strRevLevel" value="" /></td>
									</tr>
								<tr>
									
									<td><label>Serial No.</label></td>
									<td><s:checkbox id="chkSlNo" name="slno" path="strSlNo"
											value="" /></td>
									
									<td><label>Don't Allowed Exceed PO</label></td>
									<td><s:checkbox id="chkExceedPO" name="exceedPO"
											path="strExceedPO" value="" /></td>
									
								</tr>
								<tr>
								<td><label>Item For Sale</label></td>
								<td><s:checkbox id="chkForSale" name="forSale"
											path="strForSale" value="" /></td>
									<td><label>Stagger Delivery Not Allowed</label></td>
									<td><s:checkbox id="chkStagDel" name="stagDel"
											path="strStagDel" value="" /></td>
								</tr>
								<tr>
									<td><label>Tax Indicator</label></td>
									<td><s:select id="cmbTaxIndicator" name="taxIndicator"
											path="strTaxIndicator" items="${taxIndicatorList}"  cssClass="BoxW48px"/></td>
									<td><label>Delivery Period</label></td>
									<td><s:input id="txtDelPeriod" name="delPeriod"
											path="intDelPeriod" cssClass="decimal-places numberField"/></td>
								</tr>

								<tr>
									
									<td><label>BOM Calculation Up to</label></td>
									<td><s:select id="txtBomCal" name="bomCal"
											path="strBomCal" items="${bomCalList}"  cssClass="BoxW124px"/></td>
											
									<td><label>Customer Item Code</label></td>
									<td><s:input id="txtCustItemCode" name="" path="" cssClass="BoxW116px"/></td>
								</tr>

								<tr>
									<td><label>Non Stockable Item</label></td>		
									<td><s:checkbox id="strNonStockableItem" path="strNonStockableItem" value="Y"></s:checkbox></td>		
									<td><label>Yield Percent</label></td>
									<td>
										<s:input id="txtYieldPer" name="yieldPer" path="dblYieldPer" type="text" step="any" class="decimal-places numberField"/>
									</td>
								</tr>
								<tr>
								<td><label>Pick MRP Price For Tax Calculation</label></td>
									<td><s:checkbox id="chkPickMRPForTaxCal" path="strPickMRPForTaxCal" value="Y"></s:checkbox></td>
								<td><label>Exciseable</label></td>
									<td><s:checkbox id="chkExciseable" path="strExciseable" value="Y"></s:checkbox></td>
								
								</tr>
								
								<tr>
									<td><label>Description</label></td>
									<td><s:textarea id="txtDesc" name="Desc" path="strDesc"/></td>
									<td colspan="3"></td>
								</tr>
								
								<tr>
									<td><label>Comesa Region Product</label></td>
									<td><s:checkbox id="chkComesaRegionItem" path="strComesaItem" value="Y"></s:checkbox></td>
								</tr>
								
							</table>

						</div>
						<!--Tracking tab End  -->
						
						
						
						<!-- Specification Tab Start -->
						<div id="tab3" class="tab_content" style="height: 400px">
							<br> <br>
							<table class="masterTable">
								<tr>
									<th align="left" style="font-weight: normal;"> Product Specification</th>
								</tr>
								
								<tr>
									<td style="padding-left: 0px"><s:textarea id="txtSpecification" name="Specification"
											path="strSpecification" cssStyle="width:100%;height:200px;border:1px solid;background-color:inherit;padding-left:01px;text-transform: uppercase;" /></td>
								</tr>
							</table>

						</div>
						<!--Specification Tab End  -->
						
						
						
						<!-- Supplier Tab Start -->
						<div id="tab4" class="tab_content" style="height: 400px">
							<br> <br>
							<input type="hidden" name="defaultSupplier" id="defaultSupplier" value="">
							<table class="transTable">
							<tr>
							<th colspan="6"></th>
							</tr>
								<tr>
									<td width="13%"><label>Supplier ID</label></td>
									<td width="13%"><input type="text" id="txtSupplierId" name="supplierId"
										ondblclick="funHelp('suppcode')"  class="searchTextBox"/></td>
									<td width="13%"><label>Supplier Name</label></td>
									<td colspan="2"><input type="text" id="txtSupplierName" name="supplierName" readonly="readonly"   class="longTextBox"/></td>
								</tr>

								<tr>
									<td><label>Last Cost</label></td>
									<td><input type="text" id="txtLastCost" name="lastCost" value="0.00"
										class="decimal-places-amt numberField" /></td>
									<td><label>UOM</label></td>
									<td colspan="2"><input type="text" id="txtSuppUOM" name="suppUOM" class="BoxW116px"/></td>
								</tr>

								<tr>
									<td><label>Last Date</label></td>
									<td><input type="text" id="txtLastDate" class="calenderTextBox"/></td>
									<td><label>Lead Time in Days</label></td>
									<td colspan="2"><input type="text" id="txtLeadTimeDays" name="LeadTimeInDays"  class="BoxW116px"/></td>
								</tr>

								<tr>
									<td><label>Supplier Item Code </label></td>
									<td><input type="text" id="txtSuppItemCode" name="supplierItemCode" class="BoxW116px"></td>
									<td><label>Description</label></td>
									<td colspan="2"><input type="text" id="txtDesc" name="description"  class="longTextBox"/></td>
								</tr>

								<tr>
									<td><label>Maximum Quantity </label></td>
									<td><input type="text" id="txtMaxQty" value="0" class="decimal-places-amt numberField" /></td>
									<td><label>Default</label></td>
									<td><input type="checkbox" id="chkDefault" value="true"
										name="default" /></td>
										<td><input id="btnAddSupp" type="button" value="Add"
										onclick="return btnSupplierAdd_onclick()"  class="smallButton" /></td>
								</tr>
							</table>
							
							<div class="dynamicTableContainer" >
						<table  style="height:20px;border:#0F0;width:100%;font-size:11px;
			font-weight: bold;">	
		
							<tr bgcolor="#72BEFC" >
							<td width="4%">Suppler Code</td><!--  COl1   -->
							<td width="15%">Suppler Name</td><!--  COl2   -->
							<td width="4%">Last Cost</td><!--  COl3   -->
							<td width="4%">UOM</td><!--  COl4   -->
							<td width="4%">Last Date</td><!--  COl5   -->
							<td width="4%">Lead Time</td><!--  COl6   -->
							<td width="4%">Max Qty</td><!--  COl7   -->	
							<td width="4%">Item Code</td><!--  COl8   -->
							<td width="6%">Description</td><!--  COl9   -->	
							<td width="2%">Default</td><!--  COl10   -->	
							<td width="4%">Delete</td><!--  COl11   -->
		
							</tr>
						</table>
						
						<div style="background-color:  	#a4d7ff;
					    border: 1px solid #ccc;
					    display: block;
					    height: 150px;
					    margin: auto;
					    overflow-x: hidden;
					    overflow-y: scroll;
					    width: 100%;">
					   <table id="tblProdSupp" style="width:100%;border:
						#0F0;table-layout:fixed;overflow:scroll" class="transTablex ">
							<tbody>    
							<col style="width:4.5%"><!--  COl1   -->
							<col style="width:14.5%"><!--  COl2   -->
							<col style="width:4%"><!--  COl3   -->
							<col style="width:4%"><!--  COl4   -->
							<col style="width:4%"><!--  COl5   -->
							<col style="width:4%"><!--  COl6   -->
							<col style="width:4%"><!--  COl7   -->	
							<col style="width:4%"><!--  COl8   -->	
							<col style="width:6.5%"><!--  COl9   -->	
							<col style="width:3.5%"><!--  COl10   -->	
							<col style="width:3%"><!--  COl11   -->
								<%-- <c:forEach items="${command.listProdSupp}" var="prodSupp"
									varStatus="status">
									<tr>
										<td><input
											name="listProdSupp[${status.index}].strSuppCode"
											value="${prodSupp.strSuppCode}" readonly="readonly" /></td>
										<td><input
											name="listProdSupp[${status.index}].strSuppName"
											value="${prodSupp.strSuppName}" readonly="readonly" /></td>
										<td><input
											name="listProdSupp[${status.index}].dblLastCost"
											value="${prodSupp.dblLastCost}" readonly="readonly" /></td>
										<td><input
											name="listProdSupp[${status.index}].strSuppUOM"
											value="${prodSupp.strSuppUOM}" /></td>
										<td><input
											name="listProdSupp[${status.index}].dtLastDate"
											value="${prodSupp.dtLastDate}" /></td>
										<td><input
											name="listProdSupp[${status.index}].strLeadTime"
											value="${prodSupp.strLeadTime}" /></td>
										<td><input name="listProdSupp[${status.index}].dblMaxQty"
											value="${prodSupp.dblMaxQty}" /></td>
										<td><input
											name="listProdSupp[${status.index}].strSuppPartNo"
											value="${prodSupp.strSuppPartNo}" /></td>
										<td><input
											name="listProdSupp[${status.index}].strSuppPartDesc"
											value="${prodSupp.strSuppPartDesc}" /></td>
										<td><input type="radio"
											name="listProdSupp.strDefault"
											value="${prodSupp.strDefault}" onClick="Javacsript:funSetDefaultSupp(this)"/></td>
										<td><input type="button" value="Delete" class="deletebutton"
											onClick="funDeleteRowForSupplier(this)"></td>
									</tr>
								</c:forEach> --%>
							</tbody>
							</table>
							</div>
						
						</div>
							

						</div>
						<!--Supplier Tab End  -->
						
						
						
						<!--Custom Tab Start  -->
						<div id="tab5" class="tab_content" style="height: 400px">
							<br> <br>
							<table class="masterTable">
							<tr><th colspan="3"></th></tr>
								<tr>
									<td width="12%"><label>Attribute </label></td>
									<td width="15%"><input type="text" id="txtAttrCode"
										ondblclick="funHelp('attributemaster')"  class="searchTextBox"/></td>
									<td><input type="text" id="txtAttrName" readonly="readonly" class="longTextBox"/></td>
									
								</tr>
								<tr>
								<td><label>Value</label></td>
									<td><input type="text" id="txtAttrValue" name="value" value="0.00" class="decimal-places-amt numberField"/></td>
									<td><input id="btnAddAtt" type="button" value="Add"
										onclick="return funAddAttRow();" class="smallButton"></input></td>
								</tr>
							</table>
							
							<table class="masterTable"  id="tblProdAtt" style="width:80%" >
								<tr >
									<td style="border: 1px white solid;width:10%"><label>Attribute Code</label></td>
									<td style="border: 1px  white solid;width:50%"><label>Attribute Name</label></td>
									<td style="border: 1px white solid;width:10%"><label>Value</label></td>
									<td style="border: 1px  white solid;width:10%"><label>Delete</label></td>
								</tr>

								<c:forEach items="${command.listProdAtt}" var="prodAtt"
									varStatus="status">
									<tr>
										<td><input name="listProdAtt[${status.index}].strAttCode"
											value="${prodAtt.strAttCode}" readonly="readonly" /></td>
										<td><input name="listProdAtt[${status.index}].strAttName"
											value="${prodAtt.strAttName}" readonly="readonly" /></td>
										<td><input
											name="listProdAtt[${status.index}].dblAttValue"
											value="${prodAtt.dblAttValue}" /></td>
										<td><input type="hidden"
											name="listProdAtt[${status.index}].strAVCode"
											value="${prodAtt.strAVCode}" readonly="readonly" /></td>
										<td><input type="button" value="Delete"
											onClick="funDeleteRowForAttribute(this)" class="deletebutton"></td>
									</tr>
								</c:forEach>
							</table>

						</div>
						<!--Custom Tab End  -->
						


						<!--Process Tab Start  -->
						<div id="tab6" class="tab_content" style="height: 400px">
							<br> <br>
							<table class="masterTable">
							<tr><th colspan="4"></th></tr>
								<tr>
									<td width="13%"><label>Process </label></td>
									<td width="15%"><input type="text" id="txtProcessCode"
										ondblclick="funHelp('processCodeOnProcessTab')"
										readonly="readonly" class="searchTextBox"/></td>
									
									<td width="12%"><label>Weight</label></td>
									<td><input  id="txtProcessWt" class="decimal-places numberField" value="0" /></td>
									
								</tr>
								<tr>
								<td><label>Cycle Time</label></td>
									<td><input type="text" id="txtCycleTime" class="BoxW116px"/></td>
									<td colspan="2"><input id="btnAddProcess" type="button" value="Add"
										onclick="return funAddProcessRow()" class="smallButton"></input></td>
								</tr>
							</table>

							<table class="masterTable" id="tblProdProcess" style="width:80%">
								<tr>
									<td style="border: 1px white solid;width: 5%"><label>Level</label></td>
									<td style="border: 1px white solid;width: 10%"><label>Process Code</label></td>
									<td style="border: 1px white solid;width: 20%"><label>Process Name</label></td>
									<td style="border: 1px white solid;width: 5%"><label>Weight</label></td>
									<td style="border: 1px white solid;width: 5%"><label>Cycle Time</label></td>
									<td style="border: 1px white solid;width: 4%"><label>Delete</label></td>
								</tr>
								 <tr>
										<td><input
											name="listProdProcess[0].intLevel"
											value="1" readonly="readonly" class="Box" size="5%" /></td>
										<td><input
											name="listProdProcess[0].strProcessCode"
											value="PR000001" readonly="readonly" class="Box" width="10%" /></td>
										<td><input
											name="listProdProcess[0].strProcessName"
											value="Production" readonly="readonly" class="Box" width="80%"/></td>
										<td><input
											name="tblProdProcess[0].dblWeight"
											value="0" readonly="readonly" type="number" step="any"
											required="required" style="text-align: right;" width="2%"/></td>
										<td><input
											name="tblProdProcess[0].dblCycleTime"
											value="0" type="number" step="any" required="required" width="2%"/></td>
										<td><input type="button" value="Delete"
											onClick="funDeleteRowForProcess(this)" class="deletebutton" width="4%"></td>
									</tr>
							<%-- 	<c:forEach items="${command.listProdProcess}" var="prodProcess"
									varStatus="status">
									<tr>
										<td><input
											name="listProdProcess[${status.index}].intLevel"
											value="${prodProcess.intLevel}" readonly="readonly" /></td>
										<td><input
											name="listProdProcess[${status.index}].strProcessCode"
											value="${prodProcess.strProcessCode}" readonly="readonly" /></td>
										<td><input
											name="listProdProcess[${status.index}].strProcessName"
											value="${prodProcess.strProcessName}" readonly="readonly" /></td>
										<td><input
											name="tblProdProcess[${status.index}].dblWeight"
											value="${prodProcess.dblWeight}" readonly="readonly" /></td>
										<td><input
											name="tblProdProcess[${status.index}].dblCycleTime"
											value="${prodProcess.dblCycleTime}" /></td>
										<td><input type="button" value="Delete"
											onClick="funDeleteRowForProcess(this)" class="deletebutton"></td>
									</tr>
								</c:forEach> --%>
							</table>

						</div>
						<!-- Process Tab End -->
						
						
						
						<!-- Characteristics Tab Start  -->
						<div id="tab7" class="tab_content" style="height: 400px">
						<br> <br>
							<table class="transTable">
							<tr><th colspan="7"></th></tr>
								<tr>
									<td width="13%"><label>Process </label></td>
									<td width="15%"><input type="text" id="txtCharProcessCode"
										ondblclick="funHelp('processCodeOnCharTab')"
										readonly="readonly" class="searchTextBox"/></td>
									<td style="width: 20%"><label id="lblCharProcesName"></label></td>	
									
									
									<td style="width: 11%" ><label>Characteristics</label></td>
									<td style="width: 12%"><input  id="txtCharacteristics"  class="BoxW116px" ondblclick="funHelp('charCode')" /></td>	
									<td colspan="2"><label id ="lblCharName"></label></td>
															
<!-- 									<td><label>Tollerance/Method of Inspection</label></td> -->
<!-- 									<td><input  id="txtTollerance"  class="BoxW116px" /></td> -->
<!-- 									<td colspan="2"><input id="btnAddProcess" type="button" value="Add" -->
<!-- 										onclick="return funAddCharRow()" class="smallButton"></input></td> -->
								</tr>
								<tr>
								<td><label>Specification</label></td>
									<td colspan="2"><input type="text" id="txtCharSpecification" class="BoxW116px"/></td>
									
									<td><label>Gauge No</label></td>
									<td  colspan="2"><input type="text" id="txtGaugeNo" class="BoxW116px"/></td>
									
								</tr>
								
								<tr>
								<td><label>Tollerance/Method of Inspection</label></td>
									<td><input  id="txtTollerance"  class="BoxW116px" /></td>
									<td colspan="2"><input id="btnAddProcess" type="button" value="Add"
										onclick="return funAddCharRow()" class="smallButton"></input></td>
								</tr>
								
								
							</table>
						<br>
						<div class="dynamicTableContainer" >
						<table  style="height:20px;border:#0F0;width:100%;font-size:11px;font-weight: bold;">	
		
							<tr bgcolor="#72BEFC" >
							<td width="4%">Process Code</td><!--  COl1   -->
							<td width="10%">Process Name</td><!--  COl2   -->
							<td width="4%">Char Code</td><!--  COl3   -->
							<td width="9%">Char Name</td><!--  COl4   -->
							<td width="3%">Tollerance/Method of Inspection</td><!--  COl5   -->
							<td width="1%">Specification</td><!--  COl6   -->
							<td width="2%">Gauge No</td><!--  COl7   -->	
							<td width="1%">Delete</td><!--  COl8   -->
		
							</tr>
						</table>
						
						<div style="background-color:  	#a4d7ff; border: 1px solid #ccc; display: block; height: 150px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
					   <table id="tblProdChar" style="width:100%;border: #0F0;table-layout:fixed;overflow:scroll" class="transTablex ">
							<tbody>    
							<col style="width:3%"><!--  COl1   -->
							<col style="width:7.3%"><!--  COl2   -->
							<col style="width:3%"><!--  COl3   -->
							<col style="width:6.5%"><!--  COl4   -->
							<col style="width:4%"><!--  COl5   -->
							<col style="width:2%"><!--  COl6   -->
							<col style="width:2%"><!--  COl7   -->	
							<col style="width:1%"><!--  COl8   -->	
							</tbody>
							</table>
							</div>
						
						</div>
						
						
						</div>
						<!-- Characteristics Tab End -->
						
						

						
						<!-- Conversion Tab Start -->
						<div id="tab8" class="tab_content" style="height: 400px">
							<br> <br>
							<table class="masterTable">
								<tr><th colspan="4"></th></tr>
								<tr>
									<td width="12%"><label>Recieved UOM </label></td>
									<td width="15%">
									<s:select id="txtRecievedUOM" name="recievedUOM"
										path="strReceivedUOM" items="${uomList}" onchange="funRecivedUOMChange(this)"  cssClass="BoxW124px"/>
								</td>
									<td width="20%"><label>Received Conversion Ratio</label></td>
									<td>
									<s:input name="recievedConversionRatio" path="dblReceiveConversion" value="1.0" readonly="true" cssClass="decimal-places-amt numberField"/>
									</td>
								</tr>
								<tr>
									<td><label>Issue UOM </label></td>
									<td>
									<s:select id="txtIssueUOM"  name="issueUOM"
										path="strIssueUOM" items="${uomList}"  cssClass="BoxW124px"/>
									<td><label>Issue Conversion Ratio</label></td>
									<td><s:input id="txtIssueConversionRatio"
											name="issueConversionRatio" path="dblIssueConversion" value="1.0" cssClass="decimal-places-amt numberField"/></td>
								</tr>
								<tr>
									<td><label>Recipe UOM </label></td>
									<td>
										<s:select id="txtRecipeUOM"  name="recipeUOM"
										path="strRecipeUOM" items="${uomList}"  cssClass="BoxW124px"/>
										
									</td>
									<td><label>Recipe Conversion Ratio</label></td>
									<td><s:input id="txtRecipeConversionRatio"
											name="recipeConversionRatio" path="dblRecipeConversion" value="1.0" cssClass="decimal-places-amt numberField" /></td>
								</tr>
								<%-- <tr>
									<td><label>Physical Stock UOM </label></td>
									<td>
										<s:select id="txtPhyStkUOM"  name="PhyStkUOM"
										path="strPhyStkUOM" items="${uomList}"  cssClass="BoxW124px"/>
									</td>
									<td><label>Physical Stock Conversion Ratio</label></td>
									<td><s:input id="txtPhyStkConversionRatio"
											name="PhyStkConversionRatio" path="dblPhyStkConversion" cssClass="decimal-places-amt numberField"  value="1.0"/></td>
								</tr> --%>
							</table>

						</div>
						<!--Conversion Tab End  -->
						


						<!--ReOrder Level Tab Start  -->
						<div id="tab9" class="tab_content" style="height: 400px">
							<br> <br>
							<table class="masterTable">
							<tr><th colspan="5"></th></tr>
								<tr>
									<td width="12%"><label>Location</label></td>
									<td width="15%"><input type="text" id="txtROLocationCode"
										ondblclick="funHelp('locationForReOrder')" class="searchTextBox"/></td>
									<td><label id="txtROLocationName"></label></td>
									<td ><input type="button" value="Select All Location" style="background-color :#a6d1f6;height:30px;width :42%" id="btnShowAllLocation"   onclick="return funGetAllLocation();"></input></td>
								</tr>
								<tr>
									<td><label>Reorder Level</label></td>
									<td><input  id="txtReOrderLvl" class="decimal-places-amt numberField" value="0" /></td>
									<td><label>Reorder Qty</label></td>
									<td><input id="txtReOrderQty" class="decimal-places-amt numberField" value="0" /></td>
									<td><input id="txtPrice" class="decimal-places-amt numberField" value="0" /></td>
									
								</tr>
								<tr>
								<td align="left" colspan="5"><input id="btnAddReorder" type="button" value="Add"
										onclick="return btnReOrderLvl_Click()" class="smallButton"></input></td>
								</tr>
								
							</table>
							<table class="masterTable">
								<tr>
									<th style="border: 1px white solid;width: 10%"><label>Location</label></th>
									<th style="border: 1px white solid;width: 30%"><label>Location Name</label></th>
									<th style="border: 1px white solid;width: 10%"><label>Reorder Level</label></th>
									<th style="border: 1px white solid;width: 10%"><label>Reorder Qty</label></th>
									<th style="border: 1px white solid;width: 10%"><label>Price</label></th>
									<th style="border: 1px white solid;width: 5%"><label>Delete</label></th>									
								</tr>	
								</table>
							<div class="dynamicTableContainer" style="height: 246px;width: 80%; overflow-y:scroll;border: 1px solid #c0e2fe ">
							<table id="tblReOrderLevel" class="masterTable" style="width: 100%">
																			
								<c:forEach items="${command.listReorderLvl}" var="reorderLvl"
									varStatus="status">
 									<tr>
										<td><input readonly="readonly" class="Box"  size="10%"
											name="listReorderLvl[${status.index}].strLocationCode"
											value="${reorderLvl.strLocationCode}" /></td>
										<td style="width: 40%;"><input readonly="readonly" class="Box" size="77%"
											name="listReorderLvl[${status.index}].strLocationName"
											value="${reorderLvl.strLocationName}"  /></td>
										<td width="10%"><input type="number" step="any" required = "required" style="text-align: right;width:80%" size="100%"
											name="listReorderLvl[${status.index}].dblReOrderLevel"
											value="${reorderLvl.dblReOrderLevel}" /></td>
										<td width="10%"><input type="number" step="any" required ="required" style="text-align: right;width:60%" size="100%"
											name="listReorderLvl[${status.index}].dblReOrderQty"
											value="${reorderLvl.dblReOrderQty}" /></td>
										<td width="10%"><input type="number" step="any" required ="required" style="text-align: right;width:60%" size="100%"
											name="listReorderLvl[${status.index}].dblPrice"
											value="${reorderLvl.dblPrice}" /></td>	
										<td width="5%"><input type="button" value="Delete" class="deletebutton"
											onClick="funDeleteRowForReorderLvl(this)"  class="deletebutton"></td>
									</tr>
								</c:forEach>	
								</table>	
								</div>					
						</div>
						<!--Reorder Level Tab End  -->
						
						<!--Batch Tab Start  -->
						<div id="tab10" class="tab_content" style="height: 400px">
							<br> <br>
							
							<table class="masterTable">
								<tr>
									<th style="border: 1px white solid;width: 10%"><label>Batch Code</label></th>
									<th style="border: 1px white solid;width: 50%"><label>Manufacture Batch Code</label></th>
									<th style="border: 1px white solid;width: 13%"><label>GRN Code</label></th>
									<th style="border: 1px white solid;width: 10%"><label>Pending Qty</label></th>
   									<th style="border: 1px white solid;width: 10%"><label>Expiry Date</label></th>								
								</tr>	
								</table>
							<div class="dynamicTableContainer" style="height: 246px;width: 80%; overflow-y:scroll;border: 1px solid #c0e2fe ">
							<table id="tblBatch" class="masterTable" style="width: 100%">
 									<tr>
										<td width="10%"></td>
										<td width="50%"></td>
										<td width="10%"></td>
										<td width="10%"></td>
										<td width="10%"></td>
									</tr>
								
								</table>	
								</div>					
						</div>
						<!--Reorder Level Tab End  -->
						
						
						<div id="tab11" class="tab_content" style="height: 400px">
							<br> <br>
							
							<table class="masterTable" >
								<tr> 
									<td ><label>Customer Code</label></td>
 									<td> <s:input id="txtCustCode" name="txtPartyCode"  path="strPCode" ondblclick="funHelp('custMaster')"   cssClass="searchTextBox"/></td> 
			        				<td ><label id="lblCustName"></label></td>
			        				
			        				<td ><label>Standing Order</label></td>
			        				<td><input  id="txtStandingOrder" class="decimal-places-amt numberField" value="1" /></td>
			        				</tr>
			        				
			        				<tr>
			        				<td ><label>Margin %</label></td>
			        				<td><input  id="txtMargin" class="decimal-places-amt numberField" value="0" /></td>
			        				
			        				 <td align="left" ><input id="btnAddCust" type="button" value="Add"
										onclick="funAddCust()" class="smallButton" ></input></td>
				        			
				        			
				        			
				        			<td ></td>	
				        				 <td colspan="1"><input id="btnAddAllCust" type="button" value="Add All Customer"
										onclick="return btnCust_Click()" style="background-color :#a6d1f6;height:30px;width :100%" ></input></td>
								</tr>
								</table>
								
					<div class="dynamicTableContainer" >
						<table  style="height:20px;border:#0F0;width:100%;font-size:11px;font-weight: bold;">	
		
							<tr bgcolor="#72BEFC" >
							<td width="10%">Customer Code</td><!--  COl1   -->
							<td width="20%">Customer Name</td><!--  COl2   -->
							<td width="10%">Standing Order</td><!--  COl3   -->
							<td width="10%">Product Margin%</td><!--  COl4   -->								
							<td width="1%">Delete</td><!--  COl5   -->
		
							</tr>
						</table>
						
						<div style="background-color:  	#a4d7ff; border: 1px solid #ccc; display: block; height: 150px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
					   <table id="tblCust" style="width:100%;border: #0F0;table-layout:fixed;overflow:scroll" class="transTablex ">
							<tbody>    
							<col style="width:10%"><!--  COl1   -->
							<col style="width:20%"><!--  COl2   -->
							<col style="width:10%"><!--  COl3   -->
							<col style="width:10%"><!--  COl4   -->
							<col style="width:1%"><!--  COl5   -->	
							</tbody>
							</table>
							</div>
						
						</div>
								
							
							
						</div>	
						
					</div>
					
				</td>
			</tr>
		</table>
		
		<p align="center">
			<input type="submit" value="Submit" id="formsubmit" onclick="return funCallFormAction('submit',this);" class="form_button" /> 
			<input type="reset" value="Reset" class="form_button" onclick=" return funResetFields()" />
		</p>
		
		
		
	<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
</s:form>
<script type="text/javascript">funApplyNumberValidation();</script>

</body>
</html>