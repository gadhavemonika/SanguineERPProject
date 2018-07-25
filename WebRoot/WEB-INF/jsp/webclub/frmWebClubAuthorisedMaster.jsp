<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

 <!-- start Tree configuration -->
  	

    <!-- End Tree configuration -->

<title>Authorised master</title>

<script type="text/javascript">

var reportName="",transactionName="",fieldName="";
var gurl="",orderCode="",txtAgainst="";
var cnt=0;
var listRow=2
	$(document).ready(function() {
		

		
		<%if (session.getAttribute("success") != null) {
			            if(session.getAttribute("orderCode") != null){%>
			            orderCode='<%=session.getAttribute("orderCode").toString()%>';
			            <%
			           		 session.removeAttribute("orderCode");
		           			 session.removeAttribute("success");
			            }%>	
			            alert("Data Save successfully");
			<%
			}%>
			
			if(orderCode.length >0){
				$( "#txtSOCode" ).val(orderCode);
				$( "#txtSOCode" ).trigger('blur');
			}
		
	});
	
	
	function drawTree(response){
		
		 tree = '<ul id="'+parseInt(cnt)+'">';
			cnt++;
		
    	$.each(response, function(value,subNode)
		{
    		if(!(jQuery.type(subNode ) === "string")){
    			
    			tree+='<li>';
    			if(parseInt(cnt)>1){
    				
    				var temp=value.split("#");
    				tree+='<input type="checkbox" id="'+temp[1]
					+'" class="checkBox" value="'+temp[1]+'" onClick="funLoadProductsInTable(this);">&nbsp;&nbsp;'+temp[0];
    			}else{
    				var temp=value.split("#");
    				tree+='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+temp[0];
    			}
   				tree+=drawTree(subNode);
    		}
    		else{
    			var temp=value.split("#");
    			tree+='<li>';
				tree+='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+temp[0];;
    			tree+='</li>';
    		}
    	});
    	
    	tree+='</ul>';
    	return tree;
	}
	

	 
	 function funClearProductTable(){

			
	 }
	
	 	
 	function funDeleteProductRow(obj) {
			var index = obj.parentNode.parentNode.rowIndex;
			var table = document.getElementById("tblProductDtl");
			table.deleteRow(index);
		}

	function setProdcutionOrderData(){
		
	}
	
	function setServiceOrderData(){
		
	}
	


	
	function funApplyNumberValidation(){
		$(".numeric").numeric();
		$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
		$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
		$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
	    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit,negative: false});
	}
	
	
</script>

</head>
<body>
	<div id="formHeading">
		<label>Authorised Master</label>
	</div>
	
	<br />
	<br />

	<s:form name="frmAuthorisedMaster" method="POST" action="saveAuthorisedMember.html">
		<table class="transTable">
			<tr>
				<th align="right" colspan="9"><a id="baseUrl" href="#">Attach
						Documents </a></th>
			</tr>
		</table>
	
		
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" class="form_button" /> <input
				type="reset" value="Reset" class="form_button"
				onclick="funResetFields()" />
		</p>

	</s:form>
	<script type="text/javascript">
		funApplyNumberValidation();
	</script>
</body>
</html>