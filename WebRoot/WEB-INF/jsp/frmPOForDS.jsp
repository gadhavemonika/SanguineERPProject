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

<title>PO For DS</title>	
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
	function funOnLoad()
	{
// 		 alert("22");
		funLoadPOCodes();
	}
	
	//Getting Purchase Order Data based on supplier
	function funLoadPOCodes()
	{
		var searchUrl=getContextPath()+"/loadPOforDS.html";	
		$.ajax({
	 	        type: "GET",
	 	        url: searchUrl,
	 		    dataType: "json",
	 		    success: function(response)
	 		    {
	 		    	funRemRows();
			    	$.each(response, function(i,item)
	 		         {
			    		funfillGrid(response[i][0],response[i][1]);
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
	function funfillGrid(strPOCode,dtPODate)
	{	   
	    var table = document.getElementById("tblPOForDS");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    row.insertCell(0).innerHTML= "<input id=\"cbSel."+(rowCount)+"\" type=\"checkbox\"  / >";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"12%\" id=\"strPOCode."+(rowCount)+"\" value='"+strPOCode+"' >";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" id=\"dtPODate."+(rowCount)+"\" value='"+dtPODate+"' >";
	    
	}
	
	//Remove All Row from Grid
	function funRemRows() 
	{
		var table = document.getElementById("tblPOForDS");
		var rowCount = table.rows.length-1;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	
	//Check Which PO is selected
	function funCheckUncheck()
	{
		
		var table = document.getElementById("tblPOForDS");
		var rowCount = table.rows.length;	
		
	    for (var i=1;i<rowCount;i++)
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
	    var table = document.getElementById("tblPOForDS");
	    var rowCount = table.rows.length;  
	    var strPOCode="";
	    for(no=1;no<rowCount;no++)
	    {
	        if(document.all("cbSel."+no).checked==true)
	        	{
	        		//alert(document.all("strReqCode."+no).innerHTML);
	            	strPOCode=strPOCode+document.all("strPOCode."+no).value+",";
	        	}
	    }
	    strPOCode=strPOCode.substring(strPOCode,strPOCode.length-1)    
	    window.returnValue=strPOCode+"#";
	    window.close()
	}

</script>
</head>
<body onload="funOnLoad()">
<div style="width: 100%; height: 40px; background-color: #458CCA">
		<p align="center" style="padding-top: 5px;color: white">PO for DS</p>
	</div>
    <form id="form1">       
                        
                       <div
				style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 450px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
						 <table id="tblPOForDS"  class="masterTable" style="width: 90%;border-collapse: separate; ">
                            <tbody>
                             <tr  bgcolor="#75c0ff">
                                <td width="10%">Select<input type="checkbox" id="chkALL" onclick="funCheckUncheck()" /></td>	
								<td width="30%">PO Code</td>				
								<td width="30%">Date</td>    
								
								                     
                              </tr>
                            </tbody>
                        </table>
                        </div>

                    <input id="btnClose" type="Button" class="form_button" value="Close" onclick="return btnClose_onclick()" />                                      
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
       
    </form>
</body>
</html>