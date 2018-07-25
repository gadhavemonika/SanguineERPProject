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


.divcells {
    width: 180px;
    height: 160px;
   float:left;
     background-color: #d5ebfd;
     border: 1px solid #ccc;
     display: block; 
     
	 margin-left:20px; 
	 overflow-x: hidden; 
	 overflow-y: scroll;
}
</style>
<script type="text/javascript">

/*On form Load It Reset form :Ritesh 22 Nov 2014*/
 $(document).ready(function () {
	 

		var message='';
		<%if (session.getAttribute("success") != null) 
		{
			
			boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
			session.removeAttribute("success");
			if (test) 
			{
				%>alert("Order Processed Successfully..!! \n\n"+message);<%
			}
		}%>
		   

     $("form").submit(function(event){
    	 
			 return funSubmit();
			});
		}); 
		
 var arrBillNo=new Array();
 var arrBillHd=new Array();
 var arrSelectedBills=new Array();
 var i=0;
 var newBillSize=0;
 
function funSubmit()
{
	if(arrSelectedBills.length==0)
		{
		alert("Please select Bill To Process.");
		return false;
		}
	else{
	document.KDSForm.action="funKOTOrderProcess.html?selectedBills="+arrSelectedBills;
	document.KDSForm.submit();
	}
}
function funLoadData()
 {
	
	funGetKOTHdDtl();
	
	funFillTableGrid();
	
	setInterval(myTimer, 1000);
	setInterval(funRefreshForm, 1000);

 }
 
 function funRefreshForm()
 {
	 var billSize=arrBillNo.length;
	 funGetNewKOTSize();
	 if(billSize!=newBillSize)
		 {
		 funGetKOTHdDtl();
			
			funFillTableGrid();
		 }
 }
function myTimer() {
    var currDate = new Date();
   
    var ch = currDate.getHours();
    var cm = currDate.getMinutes();
    var cs = currDate.getSeconds();
    var currentSeconds = (ch * 3600) + (cm * 60) + cs;
    var len=arrBillNo.length;
	
	 for(var i=0;i<len;i++)
		{ 
		var arr= arrBillHd[i];
		var delay = new Date();
		   
		delay=arr[0].billDateTime.split(":");
		var dh = parseInt(delay[0]);
		var dm =parseInt( delay[1]);
		var ds = parseInt(delay[2]);
		var delaySeconds = (dh * 3600) + (dm * 60) + ds;
		var differenceSeconds = 0;
		 if (currentSeconds > delaySeconds)
         {
             differenceSeconds = currentSeconds - delaySeconds;
         }
         else
         {
             differenceSeconds = delaySeconds - currentSeconds;
         }
		 
		 var hh =parseInt(differenceSeconds / 3600);
         differenceSeconds = differenceSeconds % 3600;

         var mm = parseInt(differenceSeconds / 60);
         differenceSeconds = differenceSeconds % 60;

         var ss = parseInt(differenceSeconds);

       var  displayDelayTime="";
         if (hh > 0)
         {
             displayDelayTime+=hh + ":";
         }
         if (mm > 0)
         {
             displayDelayTime+=mm + ":";
         }
         displayDelayTime+=ss;
         $('#lblTime' + i ).text(displayDelayTime);
		}
}
 function funGetKOTHdDtl()
 {
	 funRemoveTableRows("tblOrder");
		
	
		var searchurl=getContextPath()+"/funGetKOTHdDtl.html";		
		 $.ajax({
		        type: "GET",
		       
		        url: searchurl,
		        dataType: "json",
		        async: false,
		        success: function(response)
		        {
		        	
		        	arrBillNo =response.KOTNo;
		        	arrBillHd=response.KOTHd;
		        	
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
 

 function funGetNewKOTSize()
 {
	 
	 var searchurl=getContextPath()+"/funGetNewKOTSize.html";		
		 $.ajax({
		        type: "GET",
		       
		        url: searchurl,
		        dataType: "json",
		        async: false,
		        success: function(response)
		        {
		        	newBillSize=response;
		        	
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
 function funFillTableGrid()
 {

		var table = document.getElementById("tblOrder");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		
		var k=0;


		 var len=arrBillNo.length;
		
		 for(var i=0;i<len;i++)
			{ 
				 
			
				row.insertCell(k).innerHTML= '<br><lable style="width:120px; margin-left:150px; color: red; " id="lblTime' + i + '"></lable><br> <lable style="width:120px; margin-left:50px; "  id="lblBillNo' + i + '"></lable><br><div id="bill' + i + '" class="divcells" onclick=\"funGetSelectedRowIndex(this)"></ div>';
			 k++;
			
			  $('#bill'+i).append('<ul id="list'+i+'"></ul>');
			 
			  var arr=arrBillHd[i];
			  $.each(arr, function(n, elem) {
			       $('#list'+i).append("<li>" +parseFloat( elem.dblQuantity) + "  " + elem.strItemName + "</li>");
			    });
			 
			 
				    $('#lblBillNo' + i ).text(arrBillNo[i]);
				    
		      if(k==4)
		      {
		    	  k=0;

		    	  var rowCount = table.rows.length;
		    		var row = table.insertRow(rowCount);
		      }
		     
		      if(len<4)
	    	  {
	    	  if(k==1)
	    		  {
	    			row.insertCell(1).innerHTML= '';
	    			row.insertCell(2).innerHTML= '';
	    			row.insertCell(3).innerHTML= '';
	    			}
	    	  else if(k==2)
			  {
			  row.insertCell(2).innerHTML= "";
			  row.insertCell(3).innerHTML= "";
			  }
	    	  else if(k==3)
			  {
	    		  row.insertCell(3).innerHTML= "";
	    	  } 
	    	  }
			}
 }
 
	function funGetSelectedRowIndex(obj)
	{
		 var tableName = document.getElementById("tblOrder");
		 var table = document.getElementById("tblOrder");
		
		 var index = obj.parentNode.parentNode.rowIndex;
		 var cellIndex=obj.parentNode.cellIndex;
		 var lblId=(index*4)+cellIndex;
		
		 var billNo=  $('#lblBillNo' + lblId ).text();
		
		 if(arrSelectedBills.includes(billNo))
			 {
			 var a = arrSelectedBills.indexOf(billNo);
			 arrSelectedBills.splice(a, 1);
			 i=i-1;
			 selectedRowIndex=index;
			 row = table.rows[selectedRowIndex];
			 document.getElementById('bill' + lblId ).style.border='1px solid #9fd5ff';
			 document.getElementById('bill' + lblId ).hilite = true;
		 
			 }
		 else
			 {
		 arrSelectedBills[i++]=billNo;
		 
			 selectedRowIndex=index;
			 row = table.rows[selectedRowIndex];
			 document.getElementById('bill' + lblId ).style.border='3px solid red';
			 document.getElementById('bill' + lblId ).hilite = true;
			 }
		 
		
	}
	
 function funRemoveTableRows(tblId)
 {
 	var table = document.getElementById(tblId);
 	var rowCount = table.rows.length;
 	while(rowCount>0)
 	{
 		table.deleteRow(0);
 		rowCount--;
 	}
 }
</script>


</head>
<body onload="funLoadData()">
<div id="formHeading">
		<label>Kitchen Display System </label>
	</div>
<s:form name="KDSForm" method="POST" action="funKOTOrderProcess.html">

<br />
		<br />
		<div id="mainDiv" style=" width: 80%; background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 600px;
				    			margin:auto; overflow-x: hidden; overflow-y: scroll; ">
		
		<table id="tblOrder" class="transFormTable" style="width:100%">
		</table>
		</div>
		<br>
		<br>
		<br>
		<br>
		<p align="center">
			<input type="submit" value="Order Process" tabindex="3" class="form_button"/> 
			
		</p>
		</s:form>
</body>
</html>




