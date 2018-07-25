<%@ page language="java" contentType="text/html;charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="<spring:url value="/resources/js/jQuery.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>	
<script type="text/javascript" src="<spring:url value="/resources/js/validations.js"/>"></script>
	
<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/design.css"/>" /> 	

<title>PO for Grn</title>	
<script type="text/javascript">
//From Close after Pressing ESC Button
	window.onkeyup = function (event) {
		if (event.keyCode == 27) {
			window.close ();
		}
	}
</script>
<script type="text/javascript">
	//Ajax Waiting
	$(document).ready(function() 
		{
			$(document).ajaxStart(function()
		 	{
			    $("#wait").css("display","block");
		  	});
		 	
			$(document).ajaxComplete(function()
			{
			    $("#wait").css("display","none");
			});	
		});
		
	//Get Project Path	
	function getContextPath() 
	{
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	//Get supplier Code when Form is loding
$( window ).load(function() {


		funFillDetails();
	});
	
	//Getting Purchase Order Data based on supplier
	function funFillDetails()
	{
		
		var searchUrl=getContextPath()+"/loadCustomerDetail.html";	
		$.ajax({
	 	        type: "GET",
	 	        url: searchUrl,
	 		    dataType: "json",
	 		    success: function(response)
	 		    {
	 		    	funRemRows();
			    	$.each(response, function(i,item)
	 		         { 
			    		//funfillGrid(item[i].strPCode,item[i].strPName,item[i].strMobile,item[i].strEmail,item[i].strContact);
			    		funfillGrid(response[i].strPCode,response[i].strPName,response[i].strMobile,response[i].strEmail,response[i].strContact);
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

	//Filling Grid
	function funfillGrid(strPCode,strPName,strMobile,strEmail,strContact)
	{	   
	    var table = document.getElementById("tblCustDet");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    row.insertCell(0).innerHTML= "<input id=\"cbSel."+(rowCount)+"\" type=\"checkbox\"  />";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"15%\" id=\"strPCode."+(rowCount)+"\" value='"+strPCode+"'>";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"12%\" id=\"strPName."+(rowCount)+"\" value='"+strPName+"' >";
	    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" id=\"strMobile."+(rowCount)+"\" value='"+strMobile+"' >";
	    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"12%\" id=\"strEmail."+(rowCount)+"\" value='"+strEmail+"' >";
	    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"12%\" id=\"strContact."+(rowCount)+"\" value='"+strContact+"' >";
	    
	}
	
	//Remove All Row from Grid
	function funRemRows() 
	{
		var table = document.getElementById("tblCustDet");
		var rowCount = table.rows.length;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	
	//Check Which PO is selected
	function funCheckUncheck()
	{
		
		var table = document.getElementById("tblCustDet");
		var rowCount = table.rows.length;	
		
	    for (var i=0;i<rowCount;i++)
	    {
	        if(document.all("chkALL").checked==true)
	        {
	        	
	        	document.all("cbSel."+i).checked=true; 
	        }
	        else
	        {
	        	document.all("cbSel."+i).checked=false;  
	        }
	    }
		
	}
	
	//After Closing Windows Return back to Data in GRN
	function btnClose_onclick()
	{
	    var table = document.getElementById("tblCustDet");
	    var rowCount = table.rows.length;  
	    var strPCode="";
	    for(no=0;no<rowCount;no++)
	    {
	        if(document.all("cbSel."+no).checked==true)
	        	{
	        		//alert(document.all("strReqCode."+no).innerHTML);
	            	strPCode=strPCode+document.all("strPCode."+no).value+",";
	        	}
	    }
	    strPCode=strPCode.substring(strPCode,strPCode.length-1)    
	    window.returnValue=strPCode;
	    window.close()
	}
	
	


</script>
</head>
<body >
<div style="width: 100%; height: 40px; background-color: #458CCA">
		<p align="center" style="padding-top: 5px;color: white">Load Customer Detail</p>
	</div>
    <s:form id="form1">       
                      
<!--                        <div -->
<!-- 				style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 450px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;"> -->
<!-- 						 <table id="tblDNDet"  class="dynamicTableContainer" style="width: 90%;border-collapse: separate; "> -->
<!--                             <tbody> -->
<!--                              <tr  bgcolor="#75c0ff"> -->
<!--                                 <td width="10%">Select<input type="checkbox" id="chkALL" onclick="funCheckUncheck()" /></td>	 -->
<!-- 								<td width="20%">SO Code</td> -->
<!-- 								<td width="30%">Customer</td>				 -->
<!-- 								<td width="15%">Date</td>     -->
<!-- 								<td width="30%">Location</td>   -->
<!-- 								<td width="10%">Order Type</td>  -->
								  
								                     
<!--                               </tr> -->
<!--                             </tbody> -->
<!--                         </table> -->
<!--                         </div> -->
                        
                        
                        <div class="dynamicTableContainer" style="height: 300px;">
			<table style="height: 20px; border: #0F0;width: 100%;font-size:11px;
 			font-weight: bold;"> 
				<tr bgcolor="#72BEFC">
					<td width="10%">Select<input type="checkbox" id="chkALL" onclick="funCheckUncheck()" /></td>	
					<td width="18.6%">Customer Code</td>
					<td width="30%">Customer Name</td>				
					<td width="15%">Mobile</td>    
					<td width="16%">Email</td>  
					<td width="10%">Contact Person</td> 
				</tr>
			</table>
			<div
				style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px; margin: auto;  width: 99.80%;">
					<table id="tblCustDet"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:11%">					
					<col style="width:20%">
					<col style="width:37%">
					<col style="width:17.5%">
					<col style="width:20%">
					<col style="width:10%">
					
					</tbody>
				</table>
			</div>
		</div>
                                         

                    <input id="btnClose" type="Button" class="form_button" value="Close" onclick="return btnClose_onclick()" />                                      
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
			
		
       
    </s:form>
</body>
</html>