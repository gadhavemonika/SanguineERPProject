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
    			$('#txtProdCode').keyup(function()
    	    			{
    						tablename='#tblProd';
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
	    
	    
		$(document).ready(function() {
			$(".tab_content").hide();
			$(".tab_content:first").show();
			$("ul.tabs li").click(function() 
			{
				$("ul.tabs li").removeClass("active");
				$(this).addClass("active");
				$(".tab_content").hide();

				var activeTab = $(this).attr("data-state");
				$("#" + activeTab).fadeIn();
			});
		});
    
	    //Set Start Date in date picker
        $(function() 
    		{
	    	      	  
    			$( "#txtSODate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
    			$("#txtSODate" ).datepicker('setDate', 'today'); 
    			
    			$( "#txtFulmtDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
    			$("#txtFulmtDate" ).datepicker('setDate', 'today'); 
    			
    			 var strPropCode='<%=session.getAttribute("propertyCode").toString()%>';
    			 
    			 var prodCode ='<%=session.getAttribute("locationCode").toString()%>';

     			
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
      
   
    
      //Fill  Product Data
	    function funfillProdGrid(strProdCode,strProdName,dblQty,strCustCode,strCustName)
		{
			
			 	var table = document.getElementById("tblProd");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    
			    row.insertCell(0).innerHTML= "<input id=\"cbProdSel."+(rowCount)+"\" name=\"Prodthemes\" type=\"checkbox\" class=\"ProdCheckBoxClass\"  checked=\"checked\" value='"+strProdCode+"' />";
			    row.insertCell(1).innerHTML= "<input name=\"listPendingCustomerSOProductDtlBean["+(rowCount)+"].strCustCode\" readonly=\"readonly\" class=\"Box\" size=\"9%\" id=\"strCustCode."+(rowCount)+"\" value='"+strCustCode+"' >";
			    row.insertCell(2).innerHTML= "<input name=\"listPendingCustomerSOProductDtlBean["+(rowCount)+"].strCustName\" readonly=\"readonly\" class=\"Box\" size=\"24%\" id=\"strCustName."+(rowCount)+"\" value='"+strCustName+"' >";
			    row.insertCell(3).innerHTML= "<input name=\"listPendingCustomerSOProductDtlBean["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box\" size=\"11%\" id=\"strProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";
			    //row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"strToLocCode."+(rowCount)+"\" value='"+strProdCode+"' >";
			    row.insertCell(4).innerHTML= "<input name=\"listPendingCustomerSOProductDtlBean["+(rowCount)+"].strProductName\" readonly=\"readonly\" class=\"Box \" size=\"45%\" id=\"strProductName."+(rowCount)+"\" value='"+strProdName+"' >";
			    row.insertCell(5).innerHTML= "<input name=\"listPendingCustomerSOProductDtlBean["+(rowCount)+"].dblQty\" class=\"text \" size=\"4%\" id=\"dblQty."+(rowCount)+"\" value='"+dblQty+"' >";
			    
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
				    // onClick=Javacsript:funLoadAllProduct('"+strCustCode+"')
				    row.insertCell(0).innerHTML= "<input id=\"cbSuppSel."+(rowCount)+"\" name=\"Custthemes\" type=\"checkbox\" class=\"CustCheckBoxClass\"   value='"+strCustCode+"'  />";
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
		    
		    
		    function  funLoadAllProduct(strCustCode)
			{
			
					var searchUrl="";
					searchUrl=getContextPath()+"/loadPartyProdData.html?partyCode="+strCustCode;
					//alert(searchUrl);
					$.ajax({
					        type: "GET",
					        url: searchUrl,
						    dataType: "json",
						    success: function(response)
						    {
// 						    	funRemoveProdRows();
						    	$.each(response, function(i,item)
								    	{
						    		
						    		funfillProdGrid(response[i].strProdCode,response[i].strProdName,response[i].dblStandingOrder,strCustCode,response[i].strSuppName);
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
				
		 function funAddProd()
		 {
			 funRemoveAllRowsProdtable(); 
			 var strCustCode="";
			 
			 $('input[name="Custthemes"]:checked').each(function() {
				 if(strCustCode.length>0)
					 {
					 strCustCode=strCustCode+","+this.value;
					 funLoadAllProduct(this.value);
					 }
					 else
					 {
						 strCustCode=this.value;
						 funLoadAllProduct(this.value);
						 
					 }
				 
				});

			 $("#hidCustCodes").val(strCustCode);
			 
			 
			 
			 
		 }
	    
		    
		 
// 			//Select All Group,SubGroup,From Location, To Location When Clicking Select All Check Box
// 			 $(document).ready(function () 
// 						{
							
// 							$("#chkCustALL").click(function () {
// 							    $(".CustCheckBoxClass").prop('checked', $(this).prop('checked'));
// 							});
							
							
// 							$("#chkProdALL").click(function () {
// 							    $(".ProdCheckBoxClass").prop('checked', $(this).prop('checked'));
// 							});
							
						
							
// 						});
					 
			 
	   //Submit Data after clicking Submit Button with validation 
	   function btnSubmit_Onclick()
	    {
			 
					 var strProdCode="";
					 
					 $('input[name="Prodthemes"]:checked').each(function() {
						 if(strProdCode.length>0)
							 {
							 strProdCode=strProdCode+","+this.value;
							 }
							 else
							 {
								 strProdCode=this.value;
							 }
						 
						});
					 if(strProdCode=="")
					 {
					 	alert("Please Select Prod");
					 	return false;
					 }
					 $("#hidProdCodes").val(strProdCode);
					
					 
  		    	document.forms["frmPendingCustomerSO"].submit();
		    }
	  
	    
	   //Reset All Filed After Clicking Reset Button
	    function funResetFields()
		{
			location.reload(true); 
		}
	   
	    $(document).ready(function() {
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
	   
	    function funRemoveAllRowsProdtable() 
	    {
			 var table = document.getElementById("tblProd");
			 var rowCount = table.rows.length;			   
			//alert("rowCount\t"+rowCount);
			for(var i=rowCount-1;i>=0;i--)
			{
				table.deleteRow(i);						
			} 
	    }
	   
	</script>    
  </head>
  	
	<body id="frmPendingCustomerSO" onload="funOnload();">
	<div id="formHeading">
		<label>Pending Customer Sales Order</label>
	</div>
		<s:form name="frmPendingCustomerSO" method="POST" action="savePendingCustomerSO.html" >
	   		<br />
	   		<div id="tab_container" style="height: 600px">
			<ul class="tabs">
				<li class="active" id="t1" data-state="tab1" style="width: 100px;padding-left: 55px">Customers</li>
				<li data-state="tab2" id="t2" style="width: 150px;padding-left: 55px">Products</li>
			</ul>
		<br>
			<div id="tab1" class="tab_content" style="height: 600px" >
				<table class="transTable">
				<tr></tr>
				<tr>
					<td width="10%"><label>SO Date :</label></td>
					<td colspan="1" width="10%"><s:input id="txtSODate" path="dteSODate" required="true" readonly="readonly" cssClass="calenderTextBox"/></td>
					<td width="10%"><label>Fullfillment Date :</label></td>
					<td colspan="1"><s:input id="txtFulmtDate" path="dteFulmtDate" required="true" readonly="readonly" cssClass="calenderTextBox"/></td> 
				</tr>
				</table>
				
				<table class="transTable">
					<tr></tr>
				</table>
				<br>
						
						
				<table class="transTable">
				<tr>
					<td colspan="2">Customer&nbsp;&nbsp;&nbsp;<input style="width: 35%; background-position: 150px 2px;" type="text" id="txtCustCode"  Class="searchTextBox" placeholder="Type to search"></input>
					<label id="lblCustName"></label></td>
					
							</tr><tr>
							
				<td colspan="2">
				<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 570px;width: 1000px; overflow-x: hidden; overflow-y: scroll;">
					<table id="" class="masterTable" style="width: 100%; border-collapse: separate;">
						<tbody>
							<tr bgcolor="#72BEFC">
								<td width="5%"><input type="checkbox" id="chkCustALL"/>Select</td>
								<td width="25%">To Customer Code</td>
								<td width="65%">To Customer Name</td>
							</tr>
						</tbody>
					</table>
					<table id="tblCust" class="masterTable"	style="width: 100%; border-collapse: separate;">
					<tr bgcolor="#72BEFC">
					</tr>
					</table>
				</div>
				</td></tr>
				
				<tr>
				
				<td colspan="2" align="center"><input id="btnAddProd" type="button" value="Add Prod"  class="smallButton" onclick="return funAddProd()"></input></td>
				</tr>
				<tr></tr>
				<tr></tr>
				
				
				</table>
			</div>
			
			
			
			<div id="tab2" class="tab_content" style="height: 650px" >
			<table class="transTable">
			<tr>
			<td colspan="2">Location&nbsp;&nbsp;&nbsp;
					<input type="text" id="txtLocCode" style="width: 35%;background-position: 150px 2px;"  Class="searchTextBox" placeholder="Type to search"  ></input>
						<label id="lblLocName"></label></td>
			</tr>
			<tr>			
				<td colspan="2">			
			
				<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 600px;width: 1000px; overflow-x: hidden; overflow-y: scroll;">
					<table id="" class="masterTable" style="width: 100%; border-collapse: separate;">
						 <tbody>
							 <tr bgcolor="#72BEFC">
								 <td width="4%"><input type="checkbox" checked="checked" id="chkLocALL"/>Select</td>
									<td width="11%"> Cust Code </td>
									<td width="22%"> Cust Name </td>
									<td width="11%"> Product Code</td>
									<td width="45%"> Product Name</td>
									<td width="11%"> Qty </td>
									
							</tr>
						</tbody>
					</table>
					<table id="tblProd" class="masterTable"	style="width: 100%; border-collapse: separate;">
					     <tr bgcolor="#72BEFC">
							</tr>
						</table>
					</div>
					
					</td></tr></table>
			</div>	
					
			</div>			
			<br>
			<br>
			<br>
			<br>			
			<p align="center">
				 <input type="button" value="Submit" onclick="return btnSubmit_Onclick();" class="form_button" />
				 <input type="button" value="Reset" class="form_button" onclick="funResetFields()"/>			     
			</p>  
			<br>
			<br>
			
			<s:input type="hidden" id="hidCustCodes" path="strSuppCode"></s:input>
			<s:input type="hidden" id="hidProdCodes" path="strProdCode"></s:input>
			
			
			<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
		</s:form>
	</body>
</html>