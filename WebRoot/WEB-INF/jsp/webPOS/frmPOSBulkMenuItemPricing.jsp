<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Zone Master</title>
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

table.one {

	    float:left;
	    
	}

	table.two   {

	    float:left;
	}
</style>
<script type="text/javascript">
  
 	 var StkFlashData;

 	
	/**
	* Success Message After Saving Record
	**/
	$(document).ready(function()
	{
		var message='';
		<%if (session.getAttribute("success") != null) 
		{
			if(session.getAttribute("successMessage") != null)
			{%>
				message='<%=session.getAttribute("successMessage").toString()%>';
			    <%
			    session.removeAttribute("successMessage");
			}
			boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
			session.removeAttribute("success");
			if (test) 
			{
				%>alert("Data Saved \n\n"+message);<%
			}
		}%>

		/* $("#execute").click(function(event) {
	 		
	 		if(funDeleteTableAllRows()){
	 			funFetchColNames();
	 			
	 		}
	 	});
	 	
		$("#update").click(function(event) {
	 		funUpdate();
	 	}); */


	});
	
	function funBtnExecuteClick()
	{
		
		$(document).ajaxStart(function()
 		{
 		    $("#wait").css("display","block");
 		});
 			$(document).ajaxComplete(function()
 		{
 			$("#wait").css("display","none");
 		});
 			funFetchColNames();
 		
	}
	
	function funBtnExportClick()
	{
		funExport();
	}
	function funChangePrice()
	{
	alert("In change");
	}
	

	 function funExport(){
		 
		 	
		 	var strPosName=$('#txtPOSCode').val();
		 	var strArea=$('#txtArea').val();
		 	var strCostCenter=$('#txtCostCenter').val();
		 	var strMenuHead=$('#txtMenuHead').val();
		 	var strsortBy=$('#txtSortBy').val();
			var strExpriedItem=$('#cmbExpriedItem').val();
			var param1=strPosName+","+strArea+","+strCostCenter+","+strMenuHead+","+strsortBy+","+strExpriedItem;	
			
		// alert("export");
		 
		 window.location.href=getContextPath()+"/exportPOSBulkItemPricingReport.html?param1="+param1;
		
	/* 	 var gurl = getContextPath()+"/exportPOSBulkItemPricingReport.html";
		 $.ajax({
		 		type : "POST",
		 		data:{ 
	 				posName:posName,
	 				area:area,
	 				costCenter:costCenter,
	 				menuHead:menuHead,
	 				sortBy:sortBy,
	 				expriedItem:expriedItem,
	 				
	 			},
		 	
		 		url : gurl,
		 		dataType : "json",
		 		success : function(response) {
		 		 	alert("data updated");
		 			
		 		}
		 }); */
	 }
	
	
	/* function funDeleteTableAllRows()
	 {
	 	$('#tbldata tbody').empty();
	 	
	
	 	var table = document.getElementById("tbldata");
	 	var rowCount1 = table.rows.length;
	 
	 	
			if(rowCount1==0){
	 			return true;
	 			}
			else{
	 			return false;
	 			}
	 		}
	 	 */
	 

	//Apply Validation on Number TextFiled
	function funApplyNumberValidation() 
	{
		$(".numeric").numeric();
		$(".integer").numeric(false, function() {
			alert("Integers only");
			this.value = "";
			this.focus();
		});
		$(".positive").numeric({
			negative : false
		}, function() {
			alert("No negative values");
			this.value = "";
			this.focus();
		});
		$(".positive-integer").numeric({
			decimal : false,
			negative : false
		}, function() {
			alert("Positive integers only");
			this.value = "";
			this.focus();
		});
		$(".decimal-places").numeric({
			decimalPlaces : maxQuantityDecimalPlaceLimit,
			negative : false
		});
		$(".decimal-places-amt").numeric({
			decimalPlaces : maxAmountDecimalPlaceLimit,
			negative : false
		});
	}
	 	 function resetdata()
	 	 {
	 		//window.location.reload(true);
	 		 window.location.href=window.location.href;
	 		
	 	 }
	 		

	 function funFetchColNames(){
		
	 	var posName=$('#txtPOSCode').val();
	 	var area=$('#txtArea').val();
	 	var costCenter=$('#txtCostCenter').val();
	 	var menuHead=$('#txtMenuHead').val();
	 	var sortBy=$('#txtSortBy').val();
		var expriedItem=$('#cmbExpriedItem').val();
	 	
	 	var gurl = getContextPath()+"/loadBulkItemPricingMaster.html";
	 	
	 	
	 	$.ajax({
	 		type : "POST",
	 		data:{ 
	 				posName:posName,
	 				area:area,
	 				costCenter:costCenter,
	 				menuHead:menuHead,
	 				sortBy:sortBy,
	 				expriedItem:expriedItem,
	 				
	 			},
	 		url : gurl,
	 		dataType : "json",
	 		success : function(response) {
	 		 	if (response== 0) 
	 			{
	 				alert("Data Not Found");
	 			} 
	 			else 
	 			{ 
	 				//funFillheaderCol(response.arrListHeader);
	 				
	 				StkFlashData=response.List;
				    	showTable();
	 		 		 
				 			/* 	$.each(response.List,function(i,item)
					 					{
					 				funFillTableCol(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9],item[10],item[11],item[12],item[13],item[14],item[15],item[16],item[17],item[18],item[19],item[20],item[21],item[22]);
						        		}); */			 			 											 		
	 			}
	 			
	 		}
	 });
		
	 	
	 	function showTable()
		{
			var optInit = getOptionsFromForm();
		    $("#Pagination").pagination(StkFlashData.length, optInit);	
	 
		    
		}
	
		var items_per_page = 10;
		function getOptionsFromForm()
		{
		    var opt = {callback: pageselectCallback};
			opt['items_per_page'] = items_per_page;
			opt['num_display_entries'] = 10;
			opt['num_edge_entries'] = 3;
			opt['prev_text'] = "Prev";
			opt['next_text'] = "Next";
		    return opt;
		}
	 	
		
		function pageselectCallback(page_index, jq)
		{
		    // Get number of elements per pagionation page from form
		    var max_elem = Math.min((page_index+1) * items_per_page, StkFlashData.length);
		    var newcontent="";
		  		    	
			   	//newcontent = '<table id="tblStockFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">Property Name</td><td id="labld2"> Product Code</td><td id="labld3"> Product Name</td>	<td id="labld4"> Location </td>	<td id="labld5"> Group</td><td id="labld6"> Sub Group</td><td id="labld7"> UOM</td><td id="labld8"> Bin No</td><td id="labld9"> Unit Price</td><td id="labld10"> Opening Stock</td><td id="labld11"> GRN</td><td id="labld12"> SCGRN</td><td id="labld13"> Stock Transfer In</td><td id="labld14"> Stock Adj In</td><td id="labld15"> MIS In</td><td id="labld16"> Qty Produced</td><td id="labld17"> Sales Return</td><td id="labld18"> Material Return</td><td id="labld19"> Purchase Return</td><td id="labld20"> Delivery Note</td><td id="labld21"> Stock Trans Out</td><td id="labld22"> Stock Adj Out</td><td id="labld23"> MIS Out</td><td id="labld24"> Qty Consumed</td><td id="labld25"> Sales</td><td id="labld26">Closing Stock</td><td id="labld27">Value</td><td id="labld28">Issue UOM Stock</td><td id="labld29">Issue Conversion</td><td id="labld30">Issue UOM </td><td id="labld31">Part No</td></tr>';
			   	newcontent = '<table id="tblStockFlash"  class="transTablex" style="  width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">Item Code</td><td id="labld2">Item Name</td><td id="labld3">Menu Name</td>	<td id="labld4"> Popular </td>	<td id="labld5"> PriceSunday</td><td id="labld6">PriceMonday</td><td id="labld7"> PriceTuesday</td><td id="labld8"> PriceWednesday</td><td id="labld9">PriceThursday</td><td id="labld10"> PriceFriday</td><td id="labld11"> PriceSaturday</td><td id="labld12"> FromDate</td><td id="labld13">ToDate</td><td id="labld14"> TimeFrom</td><td id="labld15"> AMPMFrom</td><td id="labld16"> TimeTo</td><td id="labld17"> AMPMTo</td><td id="labld18">CostCenter</td><td id="labld19"> TextColor</td><td id="labld20"> Area</td><td id="labld21"> SubMenuHeadCode</td><td id="labld22">HourlyPricing</td><td id="labld23">Is Expired</td></tr>';
			   	// Iterate through a selection of the content and build an HTML string
			    for(var i=page_index*items_per_page;i<max_elem;i++)
			    {		
			    	// newcontent += '<tr><td><form:input path="listdata[(StkFlashData[i][0])].ItemCode" value='+StkFlashData[i][0]+'/></td>';
			    	
			         newcontent += '<tr><td><input class="Box"   path="listdata['+(StkFlashData[i][0])+'].ItemCode" value='+StkFlashData[i][0]+'></td>';
			        newcontent += '<td><input class="Box"   path="listdata['+(StkFlashData[i][1])+'].ItemName" value='+StkFlashData[i][1]+'></td>';			       
			        newcontent += '<td><input class="Box"  path="listdata['+(StkFlashData[i][2])+'].MenuName" value='+StkFlashData[i][2]+'></td>';
			        newcontent += '<td><input class="Box"   path="listdata['+("StkFlashData[i][3]")+'].Popular" value='+StkFlashData[i][3]+'></td>';
			        newcontent += '<td><input class="Box"  style="text-align:right;" name=listdata['+(StkFlashData[i][4])+'].PriceSunday value='+StkFlashData[i][4]+'></td>';
			        newcontent += '<td><input class="Box"  style="text-align:right;" name=listdata['+(StkFlashData[i][5])+'].PriceMonday value='+StkFlashData[i][5]+'></td>';
			        newcontent += '<td><input class="Box"  style="text-align:right;" name=listdata['+(StkFlashData[i][6])+'].PriceTuesday value='+StkFlashData[i][6]+'></td>';
			        newcontent += '<td><input class="Box"  style="text-align:right;" name=listdata['+(StkFlashData[i][7])+'].PriceWednesday value='+StkFlashData[i][7]+'></td>';
			        newcontent += '<td><input class="Box"  style="text-align:right;" name=listdata['+(StkFlashData[i][8])+'].PriceThursday value='+StkFlashData[i][8]+'></td>';
			        newcontent += '<td><input class="Box"  style="text-align:right;" name=listdata['+(StkFlashData[i][9])+'].PriceFriday value='+StkFlashData[i][9]+'></td>'; 
			        newcontent += '<td><input class="Box"  style="text-align:right;" name=listdata['+(StkFlashData[i][10])+'].PriceSaturday value='+StkFlashData[i][10]+'></td>';
			        newcontent += '<td><input class="Box"  path="listdata['+(StkFlashData[i][11])+'].FromDate" value='+StkFlashData[i][11]+'></td>';
			        newcontent += '<td><input class="Box"  path="listdata['+(StkFlashData[i][12])+'].ToDate" value='+StkFlashData[i][12]+'></td>';
			        newcontent += '<td><input class="Box"   path="listdata['+(StkFlashData[i][13])+'].TimeFrom" value='+StkFlashData[i][13]+'></td>';			        			        					        		    
			        newcontent += '<td><input class="Box"   path="listdata['+(StkFlashData[i][14])+'].AMPMFrom" value='+StkFlashData[i][14]+'></td>';
			        newcontent += '<td><input class="Box"   path="listdata['+(StkFlashData[i][15])+'].TimeTo" value='+StkFlashData[i][15]+'></td>';
			        newcontent += '<td><input class="Box"   path="listdata['+(StkFlashData[i][16])+'].AMPMTo" value='+StkFlashData[i][16]+'></td>';
			        newcontent += '<td><input class="Box"   path="listdata['+(StkFlashData[i][17])+'].CostCenter" value='+StkFlashData[i][17]+'></td>';
			        newcontent += '<td><input class="Box"   path="listdata['+(StkFlashData[i][18])+'].TextColor" value='+StkFlashData[i][18]+'></td>';
			        newcontent += '<td><input class="Box"   path="listdata['+(StkFlashData[i][19])+'].Area" value='+StkFlashData[i][19]+'></td>';
			        newcontent += '<td><input class="Box"   path="listdata['+(StkFlashData[i][20])+'].SubMenuHeadCode" value='+StkFlashData[i][20]+'></td>';
			        newcontent += '<td ><input class="Box"  path="listdata['+(StkFlashData[i][21])+'].HourlyPricing" value='+StkFlashData[i][21]+'></td>';			  
			        newcontent += '<td><input class="Box"   path="listdata['+(StkFlashData[i][22])+'].IsExpired" value='+StkFlashData[i][22]+'></td></tr>';
			  
			    }			   
		    		   		    		    			    		    	
		    
		    
		    newcontent += '</table>';
		    $('#Searchresult').html(newcontent);
		    return false;
		}
	
	 
/*		      function funFillTableCol(item0,item1,item2,item3,item4,item5,item6,item7,item8,item9,item10,item11,item12,item13,item14,item15,item16,item17,item18,item19,item20,item21,item22)
				{
					var table = document.getElementById("tbldata");
					var rowCount = table.rows.length;
					var row = table.insertRow(rowCount);

					
				      row.insertCell(0).innerHTML= "<input name=\"listTableData["+(rowCount)+"].ItemCode\" readonly=\"readonly\"  type=text  class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item0+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; 
				      row.insertCell(1).innerHTML= "<input  name=\"listTableData["+(rowCount)+"].ItemName\"readonly=\"readonly\"   type=text  class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item1+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(2).innerHTML= "<input  name=\"listTableData["+(rowCount)+"].MenuName\"  class=\"Box\"  id=\"txtCompStk."+(rowCount)+"\" value='"+item2+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(3).innerHTML= "<input  name=\"listTableData["+(rowCount)+"].Popular\"  class=\"Box\"  id=\"txtPhyStk."+(rowCount)+"\" value='"+item3+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(4).innerHTML= "<input name=\"listTableData["+(rowCount)+"].PriceSunday\"  type=text class=\"Box \"  id=\"txtVariance."+(rowCount)+"\" value='"+item4+"' onkeyup=\"this.value=this.value.replace(/[^\d]/,\"/>";
				      row.insertCell(5).innerHTML= "<input name=\"listTableData["+(rowCount)+"].PriceMonday\"  type=text   class=\"Box \" id=\"txtDate."+(rowCount)+"\" value='"+item5+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(6).innerHTML= "<input  name=\"listTableData["+(rowCount)+"].PriceTuesday\"  type=text class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item6+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(7).innerHTML= "<input  name=\"listTableData["+(rowCount)+"].PriceWednesday\"  type=text class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item7+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(8).innerHTML= "<input  name=\"listTableData["+(rowCount)+"].PriceThursday\"  type=text class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item8+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(9).innerHTML= "<input  name=\"listTableData["+(rowCount)+"].PriceFriday\"  type=text class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item9+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(10).innerHTML= "<input name=\"listTableData["+(rowCount)+"].PriceSaturday\"  type=text  class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item10+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(11).innerHTML= "<input name=\"listTableData["+(rowCount)+"].FromDate\"  type=text  class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item11+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(12).innerHTML= "<input name=\"listTableData["+(rowCount)+"].ToDate\"  type=text  class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item12+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(13).innerHTML= "<input name=\"listTableData["+(rowCount)+"].TimeFrom\"  type=text class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item13+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(14).innerHTML= "<input name=\"listTableData["+(rowCount)+"].AMPMFrom\"  type=text class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item14+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(15).innerHTML= "<input  name=\"listTableData["+(rowCount)+"].TimeTo\" type=text  class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item15+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(16).innerHTML= "<input name=\"listTableData["+(rowCount)+"].AMPMTo\" type=text class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item16+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(17).innerHTML= "<input  name=\"listTableData["+(rowCount)+"].CostCenter\" type=text  class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item17+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(18).innerHTML= "<input  name=\"listTableData["+(rowCount)+"].TextColor\"  class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item18+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(19).innerHTML= "<input  name=\"listTableData["+(rowCount)+"].Area\"  class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item19+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
				      row.insertCell(20).innerHTML= "<input name=\"listTableData["+(rowCount)+"].SubMenuHeadCode\"  readonly=\"readonly\"  class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item20+"' onclick=\"return isNumber(event)\"/>";
				      row.insertCell(21).innerHTML= "<input name=\"listTableData["+(rowCount)+"].HourlyPricing\"  readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item21+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; 
				      row.insertCell(22).innerHTML= "<input  readonly=\"readonly\" class=\"Box \"  id=\"txtDate."+(rowCount)+"\" value='"+item22+"' onclick=\"funGetSelectedRowIndex(this)\"/>";     
				
				}	 */ 
	
		
	 }

</script>
</head>
<body>

	<div id="formHeading">
	<label>Bulk Item Pricing Master</label>
	</div>

<br/>
<br/>

	<s:form name="BulkPricingMaster" method="POST" action="updateBulkItemPricingMasterMaster.html?saddr=${urlHits}" target="_blank">

		<table class="masterTable"
		style="width: 1000px;">
			<tr>
				<td>
					<label>Pos Code</label>
				</td>
				<td>
					<s:select id="txtPOSCode" path="strPOSName" items="${posList}" cssClass="BoxW124px" />
				</td>
			
				<td>
					<label>Area</label>
				</td>
				<td>
					<s:select id="txtArea" path="strArea" items="${areaList}" cssClass="BoxW124px" />
				</td>
			
				<td>
					<label>Cost Center</label>
				</td>
				<td>
					<s:select id="txtCostCenter" path="strCostCenter" items="${costCenterList}" cssClass="BoxW124px" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Menu Head</label>
				</td>
				<td>
					<s:select id="txtMenuHead" path="strMenuHead" items="${MenuHeadlist}" cssClass="BoxW124px" />
				</td>
			
				<td>
					<label>Sort BY</label>
				</td>
				<td>
					<s:select id="txtSortBy" path="strSortBy" cssClass="BoxW124px" >
						<option selected="selected" value="NONE">NONE</option>
			        			 <option value="Item Code">Item Code</option>
					 			<option value="Item Name">Item Name</option>
					 			<option value="POS">POS </option>
					 			<option value="Area">Area </option>
					 			<option value="Menu Head">Menu Head</option>
					 			<option value="Cost Center">Cost Center</option>
	       
			        			
		         			</s:select>
				</td>
		
				<td>
					<label>Expired Item</label>
				</td>
				<td>
			
				 <s:select id="cmbExpriedItem" path="strExpiredItem" cssClass="BoxW124px">
				    			<option selected="selected" value="YES">YES</option>
			        			<option value="NO">NO</option>
		         			</s:select>
				</td>
			</tr>
			<tr>
			
			<td ><input id="execute" type="button" value="Execute" class="form_button" onclick="funBtnExecuteClick()"/></td>
			<td><input id="changeprice" type="button" value="Change Price" class="form_button" onclick="funChangePrice()"/></td>
			<td><input id="update" type="submit" value="Update" class="form_button" /></td>
			<td><input id="export" type="button" value="Export" class="form_button" onclick="funBtnExportClick()" /></td>
			<td><input id="reset" type="reset" value="Reset" class="form_button" onclick="resetdata()" /></td>
			<td colspan="2"></td>
			</tr>
		</table>

    	 <dl id="Searchresult" style="width: 1000px; overflow-x: scroll; overflow-y: hidden;  margin-left: 175px; overflow:auto;"></dl> 
		<div id="Pagination" class="pagination" style="width: 1000px; margin-left: 175px;">
			
		</div>
    <%-- <div style="background-color: rgb(164, 215, 255); border: 1px solid rgb(204, 204, 204); height: 450px;  overflow-x: scroll; margin-left: 135px; overflow-y: scroll;  float: left; width: 80%;">
    <table  class="two transTablex col4-center" 
    style="width: 300%; border: #0F0; table-layout: fixed; overflow: scroll">
 
        <tr>
        	<th style="border: 1px white solid;width:20%"><label>Item code</label></th>
			<th style="border: 1px white solid;width:40%"><label>Item Name </label></th>
           	<th style="border: 1px white solid;width:50%"><label>Menu Name</label></th>
			<th style="border: 1px white solid;width:30%"><label>Popular</label></th>
			<th style="border: 1px white solid;width:30%"><label>PriceSunday</label></th>
			
			<th style="border: 1px white solid;width:30%"><label>PriceMonday</label></th>
			<th style="border: 1px white solid;width:30%"><label>PriceTuesday</label></th>
			<th style="border: 1px white solid;width:30%"><label>PriceWednesday</label></th>
			<th style="border: 1px white solid;width:30%"><label>PriceThursday</label></th>
			<th style="border: 1px white solid;width:30%"><label>PriceFriday</label></th>
			
			<th style="border: 1px white solid;width:30%"><label>PriceSaturday</label></th>
			<th style="border: 1px white solid;width:30%"><label>FromDate</label></th>
			<th style="border: 1px white solid;width:30%"><label>ToDate</label></th>
			<th style="border: 1px white solid;width:30%"><label>TimeFrom</label></th>
			
			<th style="border: 1px white solid;width:30%"><label>AMPMFrom</label></th>
			<th style="border: 1px white solid;width:30%"><label>TimeTo</label></th>
			<th style="border: 1px white solid;width:30%"><label>AMPMTo</label></th>
			<th style="border: 1px white solid;width:30%"><label>CostCenter</label></th>
			<th style="border: 1px white solid;width:30%"><label>TextColor</label></th>
			<th style="border: 1px white solid;width:30%"><label>Area</label></th>
			<th style="border: 1px white solid;width:30%"><label>SubMenuHeadCode</label></th>
			<th style="border: 1px white solid;width:30%"><label>HourlyPricing</label></th>
			<th style="border: 1px white solid;width:30%"><label>Is Expired</label></th>
		
		newcontent = '<table id="tblStockFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">Item Code</td><td id="labld2">Item Name</td><td id="labld3">Menu Name</td>	<td id="labld4"> Popular </td>	<td id="labld5"> PriceSunday</td><td id="labld6">PriceMonday</td><td id="labld7"> PriceTuesday</td><td id="labld8"> PriceWednesday</td><td id="labld9">PriceThursday</td><td id="labld10"> PriceFriday</td><td id="labld11"> PriceSaturday</td><td id="labld12"> FromDate</td><td id="labld13">ToDate</td><td id="labld14"> TimeFrom</td><td id="labld15"> AMPMFrom</td><td id="labld16"> TimeTo</td><td id="labld17"> AMPMTo</td><td id="labld18">CostCenter</td><td id="labld19"> TextColor</td><td id="labld20"> Area</td><td id="labld21"> SubMenuHeadCode</td><td id="labld22">HourlyPricing</td><td id="labld23">Is Expired</td></tr>';						
	       </tr>
    </table>
    <div contenteditable="true"  >
    	<table id="tbldata"  class="two transTablex col4-center" 
    style="width: 300%; border: #0F0; table-layout: fixed; overflow: scroll">
										    
										    	<col style="width:20%" ><!--  COl1   -->
										    	<col style="width:40%" ><!--  COl1   -->
												<col style="width:50%" ><!--  COl1   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl1   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
											
												
												
																				
																	
										</table>
										</div>
</div>
 --%>
 <div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
		<br />
		<br />
	

	</s:form>
</body>

</html>
