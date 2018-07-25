<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GROUP MASTER</title>
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
$(document).ready(function() {


	var message='';
	<%if (session.getAttribute("success") != null) 
	{
		
		boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
		session.removeAttribute("success");
		if (test) 
		{
			%>alert("Data Saved \n\n"+message);<%
		}
	}%>
	    
	    $("#cmbBusyTbl").change(function() {
	    	funFetchKOTData();
	        	});
	  
	   

        $("form").submit(function(event){
			   funValidate(); 
			});
});
var myMap = new Map();
var arrKOT = {};
var previousKOT="";
var flaag=false;
var prevIndex="";
var prevCellIndex="";
var flg=false;
function funGetSelectedRowIndex(obj,tableId)
{
	
	 var tableName = document.getElementById(tableId);
	var index = obj.parentNode.parentNode.rowIndex;
	 var cellIndex=obj.parentNode.cellIndex;
	var tblname=tableName.rows[index].cells[cellIndex].innerHTML;
	var btnClassName=tblname.split('class="');
	var btnBackground=btnClassName[1].split('value=');
	var btnType=btnBackground[0].substring(0, (btnBackground[0].length-2));
    var data=btnBackground[1].split('onclick=');
	 var code=data[0].substring(1, (data[0].length-2));
	var selectedTableName=data[1].split('this,');
	
	if(previousKOT=="")
		{
		 prevIndex=index;
			prevCellIndex=cellIndex;
	tableName.rows[index].cells[cellIndex].innerHTML="<input type=\"text\"  class=\"transForm_SelectedBtn \" value='"+code+"'onclick=\"funGetSelectedRowIndex(this,'"+tableId+"')\"/>";
	funFetchKOTItemsDtl(code);
	previousKOT=code;
		}
	else if (previousKOT==code){
		
		 tableName.rows[prevIndex].cells[prevCellIndex].innerHTML="<input type=\"text\"  class=\"transForm_button \" value='"+previousKOT+"'onclick=\"funGetSelectedRowIndex(this,'"+tableId+"')\"/>";
			funRemoveTableRows("tblItem");
			 previousKOT="";
		}
	 else
		 {
		 tableName.rows[prevIndex].cells[prevCellIndex].innerHTML="<input type=\"text\"  class=\"transForm_button \" value='"+previousKOT+"'onclick=\"funGetSelectedRowIndex(this,'"+tableId+"')\"/>";
	 prevIndex=index;
		prevCellIndex=cellIndex;
		tableName.rows[index].cells[cellIndex].innerHTML="<input type=\"text\"  class=\"transForm_SelectedBtn \" value='"+code+"'onclick=\"funGetSelectedRowIndex(this,'"+tableId+"')\"/>";
	funFetchKOTItemsDtl(code);
	previousKOT=code;
		 }
	
}
function funValidate()
{
	/* if(KOTNo.trim()=="")
		{
		alert("Select table from Open Tables" );
		return false;
		}
	else if(selectedtblName.trim()=="")
	{
		alert("Select table from All Tables" );
		return false;
	}
	else */
		{
		funGetKOTItemMap();
		  var content=JSON.stringify(arrKOT);
		$("#txtTableNo").val(arrKOT);
		document.frmMoveKOTItems.action="saveMoveKOTItemsToTable.html";
		document.frmMoveKOTItems.submit();
		
		}
}

function funGetKOTItemMap()
{		
	
	var searchurl=getContextPath()+"/getKOTItemMap.html";
	 $.ajax({
		        type: "POST",
		        url: searchurl,
		        data:arrKOT,
		        dataType: "json",
		        success: function(response)
		        {	
		        	
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


function funFillTables()
{
	funFetchKOTData();
	
}

function funFetchKOTData()
{
	funRemoveKOTTableRows();
	funRemoveTableRows("tblItem");
	var tableNo=$("#cmbBusyTbl").val();
	
	var searchurl=getContextPath()+"/loadOpenKOTsForMoveKOTItem.html";		
	 $.ajax({
	        type: "GET",
	        data:{ tableNo:tableNo,
	        	
			},
	        url: searchurl,
	        dataType: "json",
	        async: false,
	        success: function(response)
	        {
	        	
	 
	        	 funFillKOTTable(response.KOTList);
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


function funFetchKOTItemsDtl(KOTNo)
{
	var arr=new Array();
 	funRemoveTableRows("tblItem");
	if(myMap.has(KOTNo))
		arr=myMap.get(KOTNo);
	var posCode=$("#cmbPOSName").val();
	var searchurl=getContextPath()+"/loadKOTItemsDtl.html";		
	 $.ajax({
	        type: "GET",
	        data:{ 
	        	KOTNo:KOTNo,
			},
	        url: searchurl,
	        dataType: "json",
	        async: false,
	        success: function(response)
	        {
	        	
	        	if(myMap.has(KOTNo))
	        	{
	        		arr=myMap.get(KOTNo);
		    
		    	$.each(response, function(i,item)
				{			
		    		
		    		funAddTableData2(item.strItemCode,item.strItemName,item.dblItemQuantity,item.dblAmount,arr);
		    		
			  	});
	        	}
	        	else
	        	{
	        	
		    	$.each(response, function(i,item)
				{			
		    		
		    		funAddTableData(item.strItemCode,item.strItemName,item.dblItemQuantity,item.dblAmount);
		    		
		    		
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


function funAddTableData(strItemCode,strItemName,dblItemQuantity,dblAmount)
{
	var table = document.getElementById("tblItem");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);
	  row.insertCell(0).innerHTML= "<input type=\"hidden\" name=\"strItemCode\" readonly=\"readonly\" class=\"Box \" size=\"1%\" value='"+strItemCode+"'>";
      row.insertCell(1).innerHTML= "<input name=\"strItemName\" readonly=\"readonly\" class=\"Box \" size=\"26%\" value='"+strItemName+"'>";
	  row.insertCell(2).innerHTML= "<input name=\"dblItemQuantity\" readonly=\"readonly\" class=\"Box \" size=\"19%\" value='"+dblItemQuantity+"'>";
	  row.insertCell(3).innerHTML= "<input name=\"dblAmount\" readonly=\"readonly\" class=\"Box \" size=\"19%\" value='"+dblAmount+"'>";
	  row.insertCell(4).innerHTML= "<input type=\"checkbox\" name=\"listSettlementDtl["+(rowCount)+"].strApplicableYN\" size=\"20%\" value='"+true+"'>";

}
function funAddTableData2(strItemCode,strItemName,dblItemQuantity,dblAmount,list)
{
	var flag=false;
	var table = document.getElementById("tblItem");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);
	  row.insertCell(0).innerHTML= "<input type=\"hidden\" name=\"strItemCode\" readonly=\"readonly\" class=\"Box \" size=\"1%\" value='"+strItemCode+"'>";
      row.insertCell(1).innerHTML= "<input name=\"strItemName\" readonly=\"readonly\" class=\"Box \" size=\"26%\" value='"+strItemName+"'>";
	  row.insertCell(2).innerHTML= "<input name=\"dblItemQuantity\" readonly=\"readonly\" class=\"Box \" size=\"19%\" value='"+dblItemQuantity+"'>";
	  row.insertCell(3).innerHTML= "<input name=\"dblAmount\" readonly=\"readonly\" class=\"Box \" size=\"19%\" value='"+dblAmount+"'>";
	  
	   $.each(list,function(i,item){
		   var itemCode=item.split("#");
     		if(itemCode[0]==strItemCode)
       		{
	        	flag=true;
	        	
	        	  row.insertCell(4).innerHTML= "<input type=\"checkbox\" name=\"listSettlementDtl["+(rowCount)+"].strApplicableYN\" size=\"20%\" checked=\"checked\">";
       		
       		}		            				            	
          
     	});
	
	  if(!flag)
 		{
		  row.insertCell(4).innerHTML= "<input type=\"checkbox\" name=\"listSettlementDtl["+(rowCount)+"].strApplicableYN\" size=\"20%\" value='"+true+"'>";
 		}
}
function funRemoveTableRows(tblId)
{
	var arr=new Array();
	var flag=false;
	var i=0;
	var table = document.getElementById(tblId);
	var rowCount = table.rows.length;
	 $('#tblItem tr').each(function() {
		 var code=$(this).find("input[type='hidden']").val();
		  var checkbox = $(this).find("input[type='checkbox']");

		    if( checkbox.prop("checked") ){
		    	code=code+"#"+$(this).find("input[name='strItemName']").val()+"#"+$(this).find("input[name='dblItemQuantity']").val()+"#"+$(this).find("input[name='dblAmount']").val();
		    	arr[i++]=code;
		    	flag=true;
		    } 
		   
			 });
	 
	 if(flag)
		 myMap.set(previousKOT,arr);
	 if(flaag)
	 {
		myMap.clear();
		flaag=false;
	 }
	while(rowCount>0)
	{
		table.deleteRow(0);
		rowCount--;
	}
}

function funRemoveKOTTableRows()
{
	
	var table = document.getElementById("tblKOT");
	var rowCount = table.rows.length;
	
	while(rowCount>0)
	{
		table.deleteRow(0);
		rowCount--;
	}
}
function funFillKOTTable(list)
{
	var table = document.getElementById("tblKOT");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);
	var j=1;
	var k=0;

	for(var i=0;i<list.length;i++){
    
      row.insertCell(k).innerHTML= "<input type=\"text\"  class=\"transForm_button \" value='"+list[i]+"'onclick=\"funGetSelectedRowIndex(this,'tblKOT')\"/>";
	 k++;
	 j++;
      if(k==4)
      {
    	  k=0;
    	  var rowCount = table.rows.length;
    		var row = table.insertRow(rowCount);
      }
      if(i==list.length-1)
    	  {
    	  if(k==1)
    		  {
    		  row.insertCell(1).innerHTML= "<input type=\"text\"  class=\"transFormDisable_button \" disabled>";
    		  row.insertCell(2).innerHTML= "<input type=\"button\"  class=\"transFormDisable_button \" disabled>";
    		  row.insertCell(3).innerHTML= "<input type=\"button\"  class=\"transFormDisable_button \" disabled>";
    		  }
    	  else if(k==2)
		  {
		  row.insertCell(2).innerHTML= "<input type=\"button\"  class=\"transFormDisable_button \" disabled>";
		  row.insertCell(3).innerHTML= "<input type=\"button\"  class=\"transFormDisable_button \" disabled>";
		  }
    	  else if(k==3)
		  {
    		  row.insertCell(3).innerHTML= "<input type=\"button\"  class=\"transFormDisable_button \" disabled>";
    	  } 
    	  }
	}

	
}
function btnAdd_onclick() 
{
	var flag=false;
	var i=0;
	var arr=new Array();
	 $('#tblItem tr').each(function() {
		 var code=$(this).find("input[type='hidden']").val();
		  var checkbox = $(this).find("input[type='checkbox']");

		    if( checkbox.prop("checked") ){
		    	code=code+"#"+$(this).find("input[name='strItemName']").val()+"#"+$(this).find("input[name='dblItemQuantity']").val()+"#"+$(this).find("input[name='dblAmount']").val();
		    	arr[i++]=code;
		    	flag=true;
		    } 
		   
			 });
	 if(flag)
		 myMap.set(previousKOT,arr);
	
	
		 if(myMap.size == 0)
	    {
			alert("Please select Item to Move");
	   		
	       	return false;
		}
		else if($("#cmbTable").val()=="All")
	    {
			alert("Please select New Table to move Items.");
	   		
	       	return false;
		}
		else 
		
		{
			
			 
					var mapIter = myMap.values();
					var mapIterKey = myMap.keys();
					var len=myMap.size;
					
					for(var i=0;i<len;i++)
						{
					var arr1=mapIter.next().value;
					var kot=mapIterKey.next().value;
					
					var arrkotItems=[];
					for(var j=0;j<arr1.length;j++)
					{
					var itmDtl=arr1[j].split("#");
					funAddRow(itmDtl[0],itmDtl[1],itmDtl[2],itmDtl[3]);
					
					var jObj={
							strItemCode:itmDtl[0],
							strItemName:itmDtl[1],
							dblItemQuantity:itmDtl[2],
							dblAmount:itmDtl[3]
					};
					arrkotItems.push(jObj);
					}
					arrKOT[kot]=arrkotItems;
						}
				flaag=true;
		}
	
		function funAddRow(strItemCode,strItemName,dblItemQuantity,dblAmount) 
		{var table = document.getElementById("tblMoveItem");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		  row.insertCell(0).innerHTML= "<input type=\"hidden\" name=\"itemDtlList["+(rowCount)+"].strItemCode\" readonly=\"readonly\" class=\"Box \" size=\"1%\" value='"+strItemCode+"'>";
	      row.insertCell(1).innerHTML= "<input name=\"itemDtlList["+(rowCount)+"].strItemName\" readonly=\"readonly\" class=\"Box \" size=\"35%\" value='"+strItemName+"'>";
		  row.insertCell(2).innerHTML= "<input name=\"itemDtlList["+(rowCount)+"].dblItemQuantity\" readonly=\"readonly\" class=\"Box \" size=\"35%\" value='"+dblItemQuantity+"'>";
		  row.insertCell(3).innerHTML= "<input name=\"itemDtlList["+(rowCount)+"].dblAmount\" readonly=\"readonly\" class=\"Box \" size=\"29%\" value='"+dblAmount+"'>";
		 

		   
		}
}
</script>


</head>

<body>
	<div id="formHeading">
		<label>Area Master</label>
	</div>
	<s:form name="frmMoveKOTItems" method="POST" action="">

		<br />
		<br />
		<table class="masterTable" style="margin-left: auto;  width: 74.5%;">

			 <tr>
			  <td><s:select id="cmbBusyTbl" name="cmbBusyTbl" path="strBusyTbl" items="${busyTblList}" cssClass="BoxW124px" />
				</td><td><label>Open KOTs</label></td>
			<td><s:select id="cmbTable" name="cmbTable" path="strTableNo" items="${tableList}" cssClass="BoxW124px" />
				</td><td><label>All Tables</label></td>
			
						</tr>
			 </table>
		<div style="margin-left:160px;">
		
		<div style="width: 40%; float:left; margin-left:auto;">
		<div style=" width: 100%;  background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 250px;
				    			overflow-x: hidden; overflow-y: scroll; ">
		<table id="tblKOT" class="transFormTable" style="width:100%">
		</table>	
		</div>
		<table border="1" class="myTable" style="width:100%;margin: auto;"  >
										
										<tr>
										<td style="width:25% border: #c0c0c0 1px solid; background: #78BEF9;">Description</td>
										<td style="width:25% border: #c0c0c0 1px solid; background: #78BEF9;">Quantity</td>
										<td style="width:25% border: #c0c0c0 1px solid; background: #78BEF9;">Amount</td>
										<td style="width:25% border: #c0c0c0 1px solid; background: #78BEF9;">Select</td>
										</tr>
					</table>
		<div style=" width: 100%;  background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 250px;
				    				overflow-x: hidden; overflow-y: scroll; ">
			
		<table id="tblItem" class="transTablex col5-center" style="width: 100%;">
									<tbody>    
									<col style="width:1%"><!--  COl1   -->
											<col style="width:25%"><!--  COl1   -->
											<col style="width:25%"><!--  COl2   -->
											<col style="width:25%"><!--  COl3   -->	
											<col style="width:24%"><!--  COl3   -->								
									</tbody>							
									</table>
		</div>		
		</div>
		<div style=" width: 45%; float:left;">
		<table border="1" class="myTable" style="width:100%;margin: auto;"  >
										
										<tr>
										<td style="width:36.5% border: #c0c0c0 1px solid; background: #78BEF9;">Description</td>
										<td style="width:36.5% border: #c0c0c0 1px solid; background: #78BEF9;">Quantity</td>
										<td style="width:28% border: #c0c0c0 1px solid; background: #78BEF9;">Amount</td>
										</tr>
					</table>
		<div style=" background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 450px;
				    			overflow-x: hidden; overflow-y: scroll; ">
		<table id="tblMoveItem" class="transTablex col5-center" style="width:100%">
		<tbody>    
									<col style="width:1%"><!--  COl1   -->
											<col style="width:35%"><!--  COl1   -->
											<col style="width:35%"><!--  COl2   -->
											<col style="width:29%"><!--  COl3   -->	
																		
									</tbody>
		</table>
		</div>
		<div>
		<br>
		
		<p align="center">
		
			<input id="btnAdd" type="button" class="form_button" value="OK" onclick="return btnAdd_onclick();"></input>
				<input type="submit" value="Save" tabindex="3" class="form_button"/> 
				<s:input type="hidden" id="txtTableNo" path="jObjKOTItemList" />
		</p>
		</div>
	</div>
	
		</div>
			
		
		
	</s:form>

</body>
</html>
