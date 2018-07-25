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

<title>PI for PO</title>	
<script type="text/javascript">

	/**
	 * Close window after pressing ESC button 
	 */
	window.onkeyup = function (event) {
		if (event.keyCode == 27) {
			window.close ();
		}
	}
</script>
<script type="text/javascript">

	/**
	 * Ready Function for Ajax Waiting
	**/
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
	/**
	 * Get project Path
	**/
	function getContextPath() 
	{
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	/**
	 * function Onload
	**/
	function funOnLoad()
	{
		funFillDetails();
	}
	
	/**
	 * Get pending Purchase indent
	**/
	function funFillDetails()
	{
		var searchUrl=getContextPath()+"/loadPIforPO.html";	
		//alert(searchUrl);
		$.ajax({
	 	        type: "GET",
	 	        url: searchUrl,
	 		    dataType: "json",
	 		    success: function(response)
	 		    {
	 		    	funRemRows();
			    	$.each(response, function(i,item)
	 		         {
			    		funfillGrid(response[i][0],response[i][1],response[i][2],response[i][3]);
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

	/**
	 * Filling grid
	**/
	function funfillGrid(strPICode,dtPIDate,strLocName,strNarration)
	{	   
	    var table = document.getElementById("tblDNDet");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    row.insertCell(0).innerHTML= "<input id=\"cbSel."+(rowCount)+"\" type=\"checkbox\"  />";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"12%\" id=\"strPICode."+(rowCount)+"\" value='"+strPICode+"'>";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" id=\"dtPIDate."+(rowCount)+"\" value='"+dtPIDate+"' >";
	    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" id=\"strLocName."+(rowCount)+"\" value='"+strLocName+"'>";	 		    
	    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" id=\"strNarration."+(rowCount)+"\" value='"+strNarration+"'>";
	   
	}
	
	/**
	 * Remove all product from grid
	 */
	function funRemRows() 
	{
		var table = document.getElementById("tblDNDet");
		var rowCount = table.rows.length-1;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	/**
	 * Get Selected Purchase indent Code form Grid
	 */
	function funCheckUncheck()
	{
		
		var table = document.getElementById("tblDNDet");
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
	/**
	 * Close windows and return selected purchase indent code in PO form 
	 */
	function btnClose_onclick()
	{
	    var table = document.getElementById("tblDNDet");
	    var rowCount = table.rows.length;  
		var strPICode="";
	    for(no=0;no<rowCount;no++)
	    {
	        if(document.all("cbSel."+no).checked==true)
	        	{
	        		//alert(document.all("strReqCode."+no).innerHTML);
	            	strPICode=strPICode+document.all("strPICode."+no).value+",";
	        	}
	    }
	    strPICode=strPICode.substring(strPICode,strPICode.length-1)    
	    window.returnValue=strPICode+"#";
	    window.close()
	}

</script>
</head>
<body onload="funOnLoad()">
<div style="width: 100%; height: 40px; background-color: #458CCA">
		<p align="center" style="padding-top: 5px;color: white;">PI for PO</p>
	</div>
    <form id="form1">       
                        
                       <div class="dynamicTableContainer" style="width: 100%;height: 500px">
						 <table class="masterTable" style="width: 100%;border-collapse: separate; ">
                            <tbody>
                             <tr  bgcolor="#75c0ff">
                                <td width="5%">Select<input type="checkbox" id="chkALL" onclick="funCheckUncheck()" /></td>	
								<td width="10%">PI Code</td>				
								<td width="8%">Date</td>
			                    <td width="30%">Location</td> 			                    
			                    <td width="20%">Narration</td>                   
					                            
                              </tr>
                            </tbody>
                        </table>
                        <div
				style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 450px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
					<table id="tblDNDet"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:10%">					
					<col style="width:15%">
					<col style="width:12%">
					<col style="width:40%">
					<col style="width:30%">
					
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
    </form>
</body>
</html>