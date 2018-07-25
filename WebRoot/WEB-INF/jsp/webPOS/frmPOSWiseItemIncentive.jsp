<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>POSWise Item Incentive</title>
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
 	<%-- var fieldName;
 	
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
		
		  $("form").submit(function(event){
			  if($("#txtZoneName").val().trim()=="")
				{
					alert("Please Enter Zone Name");
					return false;
				}
			  if($("#txtZoneName").val().length > 30)
				{
					alert("Zone Name length must be less than 30");
					return false;
				}
			 
			  else{
				  flg=funCallFormAction();
				  return flg;
			  }
			});
	});
	  	
  	 --%>
  	 function funExecute()
  	 {
  		if(funDeleteTableAllRows()){
				funFetchColNames();
			}
  	 }
  	 
	 function funDeleteTableAllRows()
	 {
	 	$('#tblListData tbody').empty();
	 	
	 	var table = document.getElementById("tblListData");
	 	var rowCount1 = table.rows.length;
	 	if(rowCount1==0){
	 		return true;
	 	}else{
	 		return false;
	 	}
	 }
	 
  	 function funFetchColNames() {
 	 	
 	 	var POSCode=$('#cmbPOSName').val();
 	 	
 	 	var gurl = getContextPath()+"/loadPOSWiseItemIncentiveData.html";
 	 	
 	 	
 	 	$.ajax({
 	 		type : "POST",
 	 		data:{ 
 	 			POSCode:POSCode,
 	 				
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
 	 			
 				 		$.each(response.list,function(i,item){
 		            	funFillTableCol(item[0],item[1],item[2],item[3],item[4]);
 	             		});
 	 			}
 	 		}
 	 			
 	 });
 	 }
  	 
  	function funFillTableCol(item0,item1,item2,item3,item4)
	{
		var table = document.getElementById("tblListData");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		var comboitem="";
		if(item2=="Amt" || item2=="amt")
			{
			comboitem="per ";
			}
		else
			{
			comboitem="amt ";
			}
			

	      /*row.insertCell(0).innerHTML= "<input   class=\"Box \" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+strBillNo+"' onclick=\"funGetSelectedRowIndex(this)\"/>"; */
	      row.insertCell(0).innerHTML= "<input    readonly=\"readonly\" class=\"Box \"  name=\"listItemIncentive["+(rowCount)+"].strItemCode\" id=\"txtDate."+(rowCount)+"\" value='"+item0+"' />"; 
	      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" name=\"listItemIncentive["+(rowCount)+"].strItemName\" id=\"txtDate."+(rowCount)+"\" value='"+item1+"' />";
	      row.insertCell(2).innerHTML= "<select  readonly=\"readonly\" class=\"Box \" name=\"listItemIncentive["+(rowCount)+"].strIncentiveType\"  id=\"txtCompStk\" value='"+item2+"'onclick=\"creatBrandOptions(this)\"> <OPTION>"+item2 +"</OPTION><OPTION>"+comboitem+"</OPTION></SELECT>";
	      row.insertCell(3).innerHTML= "<input style=\"text-align: right; width: 80%;\"  class=\"Box \" name=\"listItemIncentive["+(rowCount)+"].strIncentiveValue\" id=\"txtPhyStk."+(rowCount)+"\" value='"+item3+"' />";
	      row.insertCell(4).innerHTML= "<input   type=\"hidden\" size=\"0\" class=\"Box \" name=\"listItemIncentive["+(rowCount)+"].strPOSCode\" id=\"txtPhyStk."+(rowCount)+"\" value='"+item4+"' />";
	   
	}
/*   	 <select name="combo" id="combo"></select>  type=\"hidden\"  */  
		  /* function val(e)
		  {-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()
			  }
	 */
	 var count=0;
	 function creatBrandOptions(item){
		   
		        // $('<option value="abc">'+a+'</option>').appendTo('#txtCompStk');
    var select = document.getElementById('txtCompStk');
   // if(count=0)
    	
		if(item==amt)
			{
		    select.options[select.options.length] = new Option('per', 'per');
			}
		else if(item==per)
			{
			  select.options[select.options.length] = new Option('amt', 'amt');
			} 
    	
	}
	 function selectComboBox()
	 {
		
		 innerHTML="<select id=\"cmbPOSName\"   items=\"${posList}\" >"
	 }

	 function funResetFields()
	 {
			$('#tblListData tbody').empty();	 
	 }
</script>
</head>
<body>

	<div id="formHeading">
	<label>POSWise Item Incentive</label>
	</div>

<br/>
<br/>

	<s:form name="POSWiseItemIncentive" method="POST" action="savePOSWiseItemIncentive.html?saddr=${urlHits}">

		<table class="masterTable">
			<tr>
				<td width="140px">POS Name</td>
				<td colspan="3"><s:select id="cmbPOSName" name="cmbPOSName" path="strPOSName" cssClass="BoxW124px" items="${posList}" >
					
				 </s:select></td>
				   <td>
				  	 <input type="button" value="Execute" tabindex="3" class="form_button"  id="execute" onclick="funExecute()"/>
				   </td>
				 <td>
				 <input type="submit" value="Save" tabindex="3" class="form_button" />
				 </td>
				  <td>
				 <input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
				 </td>
				
			</tr>
			
			
		</table>

<table id="tblListHeader" class="transTablex"
					style="width: 80%;  text-align: center !important; ">
			
			<th style="border: 1px white solid;width:10%">Item Code<label></label></th>
			<th style="border: 1px white solid;width:10%">Item Name<label></label></th>
			<th style="border: 1px white solid;width:10%">Incentive Type<label></label></th>
			<th style="border: 1px white solid;width:11%">Incentive Value<label></label></th>
		
			
			
				</table>
				
				
				
				
				<table id="tblListData" class="transTablex"
					style="width: 80%; background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 400px; text-align: center !important;">
				
				
											<col style="width:10%"><!--  COl1   -->
											<col style="width:10%"><!--  COl2   -->
											<col style="width:10%"><!--  COl3   -->
											<col style="width:10%"><!--  COl4   -->
											<col style="width:1%"><!--  COl4   -->
											
											
				</table>
	</s:form>
</body>
</html>
