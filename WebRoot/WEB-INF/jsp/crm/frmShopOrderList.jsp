 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="default.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Cost Of Issue</title>
    <style>
  #tblGroup tr:hover , #tblSubGroup tr:hover, #tblloc tr:hover{
	background-color: #72BEFC;
	
}
</style>
    <script type="text/javascript">
    
 		 //Serching on Table when user type in text field
          $(document).ready(function()
    		{
    			var tablename='';
    			
    			$('#txtCustCode').keyup(function()
    	    			{
    						tablename='#tblCust';
    	    				searchTable($(this).val(),tablename);
    	    			});
    			$('#txtLocCode').keyup(function()
    	    			{
    						tablename='#tblloc';
    	    				searchTable($(this).val(),tablename);
    	    			});	
    			
    		});

           //Searching on table on the basis of input value and table name
    		function searchTable(inputVal,tablename)
    		{
    			var table = $(tablename);
    			table.find('tr').each(function(index, row)
    			{
    				var allCells = $(row).find('td');
    				if(allCells.length > 0)
    				{
    					var found = false;
    					allCells.each(function(index, td)
    					{
    						var regExp = new RegExp(inputVal, 'i');
    						if(regExp.test($(td).find('input').val()))
    						{
    							found = true;
    							return false;
    						}
    					});
    					if(found == true)$(row).show();else $(row).hide();
    				}
    			});
    		}
    		
	    var fieldName="";
	    //Ajax Wait Image display
	    $(document).ready(function() 
    		{
    			$(document).ajaxStart(function()
    		 	{
    			    $("#wait").css("display","block");
    		  	});
    			$(document).ajaxComplete(function(){
    			    $("#wait").css("display","none");
    			  });
    		});
    
	    //Set Start Date in date picker
        $(function() 
    		{
	    	      	  
    			/*$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
    			$("#txtFromDate" ).datepicker('setDate', 'today'); */
    			var startDate="${startDate}";
    			var arr = startDate.split("/");
    			Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
    			$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
    			$("#txtFromDate" ).datepicker('setDate', Dat);
    			
    			$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
    			$("#txtToDate" ).datepicker('setDate', 'today'); 
    			
    			
    			$( "#txtFromFulfillment" ).datepicker({ dateFormat: 'dd-mm-yy' });
    			$("#txtFromFulfillment" ).datepicker('setDate', Dat);
    			
    			$( "#txtToFulfillment" ).datepicker({ dateFormat: 'dd-mm-yy' });		
    			$("#txtToFulfillment" ).datepicker('setDate', 'today'); 
    			
    			
    			 var strPropCode='<%=session.getAttribute("propertyCode").toString()%>';
    			 
    			 var locationCode ='<%=session.getAttribute("locationCode").toString()%>';

     			 funSetAllLocationAllPrpoerty();
    			 funSetAllCust();
    			 
    			 
    		});	
      

    
	  //Open Help
      function funHelp(transactionName)
		{
    	  	fieldName=transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;top=500,left=500")
		}
	  
	  //Set data After Seletion of Help
      function funSetData(code)
		{
			switch (fieldName) 
			{

			    case 'custcode':
			    	funSetCustomer(code);
			        break;  
			}
		}
      
   
    //Get and Set All Location on the basis of all Property
      function funSetAllLocationAllPrpoerty() {
			var searchUrl = "";
			searchUrl = getContextPath()+ "/loadAllLocationForAllProperty.html";
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
					if (response.strLocCode == 'Invalid Code') {
						alert("Invalid Location Code");
						$("#txtFromLocCode").val('');
						$("#lblFromLocName").text("");
						$("#txtFromLocCode").focus();
					} else
					{
						$.each(response, function(i,item)
						 		{
							funfillLocationGrid(response[i].strLocCode,response[i].strLocName);
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
    
      //Fill  Location Data
	    function funfillLocationGrid(strLocCode,strLocationName)
		{
			
			 	var table = document.getElementById("tblloc");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    
			    row.insertCell(0).innerHTML= "<input id=\"cbToLocSel."+(rowCount)+"\" name=\"Locthemes\" type=\"checkbox\" class=\"LocCheckBoxClass\"  checked=\"checked\" value='"+strLocCode+"' />";
			    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"strToLocCode."+(rowCount)+"\" value='"+strLocCode+"' >";
			    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\"strToLocName."+(rowCount)+"\" value='"+strLocationName+"' >";
		}
	  
	  
		     
	      //Get and set Customer  Data 
	      function funSetAllCust() {
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
		

				
		    //Fill Supplier Data
		    function funfillCustGrid(strCustCode,strCustName)
			{
				
				 	var table = document.getElementById("tblCust");
				    var rowCount = table.rows.length;
				    var row = table.insertRow(rowCount);
				    
				    row.insertCell(0).innerHTML= "<input id=\"cbSuppSel."+(rowCount)+"\" name=\"Custthemes\" type=\"checkbox\" class=\"CustCheckBoxClass\"  checked=\"checked\" value='"+strCustCode+"' />";
				    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"strCustCode."+(rowCount)+"\" value='"+strCustCode+"' >";
				    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\"strCName."+(rowCount)+"\" value='"+strCustName+"' >";
			}
		    //Remove All Row from Grid Passing Table Id as a parameter
		    function funRemRows(tablename) 
			{
				var table = document.getElementById(tablename);
				var rowCount = table.rows.length;
				while(rowCount>0)
				{
					table.deleteRow(0);
					rowCount--;
				}
			}
		 
			//Select All Group,SubGroup,From Location, To Location When Clicking Select All Check Box
			 $(document).ready(function () 
						{
							
							$("#chkCustALL").click(function () {
							    $(".CustCheckBoxClass").prop('checked', $(this).prop('checked'));
							});
							
							
// 							$("#chkLocALL").click(function () {
// 							    $(".LocCheckBoxClass").prop('checked', $(this).prop('checked'));
// 							});
							
						
							
						});
					 
			 
	   //Submit Data after clicking Submit Button with validation 
	   function btnSubmit_Onclick()
	    {
			 
				 var strCustCode="";
					 
					 $('input[name="Custthemes"]:checked').each(function() {
						 if(strCustCode.length>0)
							 {
							 strCustCode=strCustCode+","+this.value;
							 }
							 else
							 {
								 strCustCode=this.value;
							 }
						 
						});

					 $("#hidCustCodes").val(strCustCode);
					 
					 
// 					 var strLocCode="";
					 
// 					 $('input[name="Locthemes"]:checked').each(function() {
// 						 if(strLocCode.length>0)
// 							 {
// 							 strLocCode=strLocCode+","+this.value;
// 							 }
// 							 else
// 							 {
// 								 strLocCode=this.value;
// 							 }
						 
// 						});
// 					 if(strLocCode=="")
// 					 {
// 					 	alert("Please Select Location");
// 					 	return false;
// 					 }
// 					 $("#hidLocCodes").val(strLocCode);
					 
					 
					 
					 
					 
					 
		    	document.forms["frmShopOrdersList"].submit();
		    }
	  
	    
	   //Reset All Filed After Clicking Reset Button
	    function funResetFields()
		{
			location.reload(true); 
		}
	</script>    
  </head>
  	
	<body id="frmShopOrderList">
	<div id="formHeading">
		<label>Shop Order List</label>
	</div>
		<s:form name="frmShopOrdersList" method="POST" action="rptShopOrderList.html" target="_blank">
	   		<br />
	   		<table class="transTable">
			    <tr>
					<td width="10%"><label>From SO Date :</label></td>
					<td colspan="1" width="10%"><s:input id="txtFromDate" path="dtFromDate" required="true" readonly="readonly" cssClass="calenderTextBox"/></td>
					<td width="10%"><label>To SO Date :</label></td>
					<td colspan="1"><s:input id="txtToDate" path="dtToDate" required="true" readonly="readonly" cssClass="calenderTextBox"/>
					</td>
				</tr>
				<tr>
				<td colspan="4"></td>
				
				</tr>
				 <tr>
					<td width="10%"><label>From Fulfillment Date :</label></td>
					<td colspan="1" width="10%"><s:input id="txtFromFulfillment" path="dteFromFulfillment" required="true" readonly="readonly" cssClass="calenderTextBox"/></td>
					<td width="10%"><label>To Fulfillment Date :</label></td>
					<td colspan="1"><s:input id="txtToFulfillment" path="dteToFulfillment" required="true" readonly="readonly" cssClass="calenderTextBox"/>
					</td>
				</tr>
				
			    </table>
				<br>
			<table class="transTable">
			
			<tr>
			
					
			</tr>
		</table>
		<br>
		<table class="transTable">
				
		<tr>
				<td colspan="2">Customer&nbsp;&nbsp;&nbsp;<input style="width: 35%; background-position: 150px 2px;" type="text" id="txtCustCode" 
			 Class="searchTextBox" placeholder="Type to search"></input>
			<label id="lblCustName"></label></td>
		
			</tr><tr>
						<td colspan="2">
						<div
							style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px;width: 480px; overflow-x: hidden; overflow-y: scroll;">

							<table id="" class="masterTable"
								style="width: 100%; border-collapse: separate;">
								<tbody>
									<tr bgcolor="#72BEFC">
										<td width="15%"><input type="checkbox" checked="checked" 
										id="chkCustALL"/>Select</td>
										<td width="25%">To Customer Code</td>
										<td width="65%">To Customer Name</td>

									</tr>
								</tbody>
							</table>
							<table id="tblCust" class="masterTable"
								style="width: 100%; border-collapse: separate;">

								<tr bgcolor="#72BEFC">
									

								</tr>
							</table>
						</div>
				</td>
				
				<td colspan="2">
<!-- 						<div -->
<!-- 							style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px;width: 490px; overflow-x: hidden; overflow-y: scroll;"> -->

<!-- 							<table id="" class="masterTable" -->
<!-- 								style="width: 100%; border-collapse: separate;"> -->
<!-- 								<tbody> -->
<!-- 									<tr bgcolor="#72BEFC"> -->
<!-- 										<td width="15%"><input type="checkbox" checked="checked"  -->
<!-- 										id="chkLocALL"/>Select</td> -->
<!-- 										<td width="25%"> Location Code</td> -->
<!-- 										<td width="65%"> Location Name</td> -->

<!-- 									</tr> -->
<!-- 								</tbody> -->
<!-- 							</table> -->
<!-- 							<table id="tblloc" class="masterTable" -->
<!-- 								style="width: 100%; border-collapse: separate;"> -->

<!-- 								<tr bgcolor="#72BEFC"> -->
									

<!-- 								</tr> -->
<!-- 							</table> -->
<!-- 						</div> -->
				</td>
				
				
				
			
		</table>
		
		<br>
		<table class="transTable">
			<tr>
				<td width="10%"><label>Report Type :</label></td>
				<td colspan="3"><s:select id="cmbDocType" path="strDocType"
						cssClass="BoxW124px">
 						<s:option value="PDF">PDF</s:option> 
						<s:option value="XLS">EXCEL</s:option>
					</s:select></td>
			</tr>

		</table>
		

		<br>
			<p align="center">
				 <input type="button" value="Submit" onclick="return btnSubmit_Onclick();" class="form_button" />
				 <input type="button" value="Reset" class="form_button" onclick="funResetFields()"/>			     
			</p>  
			
			
			<s:input type="hidden" id="hidCustCodes" path="strSuppCode"></s:input>

			
			
			<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
		</s:form>
	</body>
</html>