<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bill Settlement</title>
<script type="text/javascript">

var selectedRowIndex="";

	$(function() 
	{
	
		funFillUnsettleBillGrid();
	
	});
	function funFillUnsettleBillGrid()
	{
		
		var searchUrl="";
	    var tableName="";
	   
	    
	    searchUrl=getContextPath()+"/fillUnsettleBillData.html?";
		$.ajax({
		        type: "GET",
		        url: searchUrl,
		        async:false,
		        data:"tableName="+tableName,
			    success: function(response)
			    {
			    	$.each(response, function(i,item)
					{
			    		funAddFullRow(response.listUnsettlebill,response.gShowBillsType,response.gCMSIntegrationYN);
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
	
	function funAddFullRow(data,gShowBillsType,gCMSIntegrationYN){
		
		
			$('#tblData tbody').empty()
			var table = document.getElementById("tblData");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);

			if(gShowBillsType=="Table Detail Wise")
            {
        	  row.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value=Bill No >";
        	  row.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value=Table >";
        	  row.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value=Waiter >";
        	  
//                dmBills.addColumn("Bill No");
//                dmBills.addColumn("Table");
//                dmBills.addColumn("Waiter");


                if (gCMSIntegrationYN=='Y')
                {
                	row.insertCell(3).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value=Member >";
//                     dmBills.addColumn("Member");
                }
                else
                {
                	row.insertCell(3).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value=Customer >";
//                    dmBills.addColumn("Customer");
                }
                row.insertCell(4).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value=Time >";
                row.insertCell(5).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value=Amount >";
//                dmBills.addColumn("Time");
//                dmBills.addColumn("Amount");
           }
            else//Delivery Detail Wise
           {
            	  row.insertCell(0).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value=Bill No >";
            	  row.insertCell(1).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value=Table >";
//                dmBills.addColumn("Bill No");
//                dmBills.addColumn("Table");
            if (gCMSIntegrationYN=='Y')
               {
            	  row.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value=Member >";
//                    dmBills.addColumn("Member");
               }
               else
               {
            	   row.insertCell(2).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value=Customer >";
//                    dmBills.addColumn("Customer");
               }
            row.insertCell(3).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value=Area >";
            row.insertCell(4).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value=Del Boy >";
            row.insertCell(5).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value=Time >";
            row.insertCell(6).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"KOT NO.\" value=Amount >";
        
           }
	
          
			
			rowCount++;
		    for(var i=0;i<data.length;i++){
		    	row = table.insertRow(rowCount);
		    	var rowData=data[i];
		    	
		    	for(var j=0;j<rowData.length;j++){
		    		  if(gShowBillsType=="Table Detail Wise")
		              {
// 		    		row.insertCell(j).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+rowData[j]+"\" value='"+rowData[j]+"' onclick=\"funGetSelectedRowIndex(this)\ />";
		    			  row.insertCell(j).innerHTML= "<input name=\readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\""+rowData[j]+"\" value='"+rowData[j]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
		    	}
		    	rowCount++;
		    }
            }

	}
	
	function funGetSelectedRowIndex(obj)
	{
		 var index = obj.parentNode.parentNode.rowIndex;
		 var table = document.getElementById("tblData");
		 if((selectedRowIndex>0) && (index!=selectedRowIndex))
		 {
			 if(selectedRowIndex%2==0)
			 {
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#A3D0F7';
				 selectedRowIndex=index;
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#ffd966';
				 row.hilite = true;
			 }
			 else
			 {
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#C0E4FF';
				 selectedRowIndex=index;
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#ffd966';
				 row.hilite = true;
	         }
			
		 }
		 else
		 {
			 selectedRowIndex=index;
			 row = table.rows[selectedRowIndex];
			 row.style.backgroundColor='#ffd966';
			 row.hilite = true;
		 }
		 funOpenBillSettlement()
		
	}
	 function funOpenBillSettlement()
	 {
		 
			var searchUrl="";
			
	    	var tableName = document.getElementById("tblData");
	       	var dataBilNo= tableName.rows[selectedRowIndex].cells[0].innerHTML; 
	        var btnBackground=dataBilNo.split('value=');
	        var billData=btnBackground[1].split("onclick");
	        var bill=billData[0].substring(1, (billData[0].length-2));
	        
	     	var dataTableNo= tableName.rows[selectedRowIndex].cells[1].innerHTML; 
	        var btnBackgroundTableNo=dataTableNo.split('value=');
	        var tableData=btnBackgroundTableNo[1].split("onclick");
	        var selectedTableNo=tableData[0].substring(1, (tableData[0].length-2));
	      
	        
// 			document.getElementById("txtPSPCode").value=pspCode;
// 			document.getElementById("txtDate").value=date;
			
		
	        
	        $("#hiddBillNo").val(bill);
	        $("#hiddTableNO").val(selectedTableNo);
	        $("#hiddSelectedRow").val(selectedRowIndex);
 	    	document.forms["BillSettlement"].submit();
	        
	        
	        
// 		    searchUrl=getContextPath()+"/fillBillSettlementData.html?";
// 			$.ajax({
// 			        type: "GET",
// 			        url: searchUrl,
// 			        async:false,
// 			        data:"bill="+bill+"&selectedTableNo="+selectedTableNo+"&selectedRowIndex="+selectedRowIndex ,
// 				    success: function(response)
// 				    {
// 				    	$.each(response, function(i,item)
// 						{
				    		
// 						});
// 				    },
// 				    error: function(jqXHR, exception) {
// 			            if (jqXHR.status === 0) {
// 			                alert('Not connect.n Verify Network.');
// 			            } else if (jqXHR.status == 404) {
// 			                alert('Requested page not found. [404]');
// 			            } else if (jqXHR.status == 500) {
// 			                alert('Internal Server Error [500].');
// 			            } else if (exception === 'parsererror') {
// 			                alert('Requested JSON parse failed.');
// 			            } else if (exception === 'timeout') {
// 			                alert('Time out error.');
// 			            } else if (exception === 'abort') {
// 			                alert('Ajax request aborted.');
// 			            } else {
// 			                alert('Uncaught Error.n' + jqXHR.responseText);
// 			            }
// 			        }
// 			      });
		 
	 }
	 
	
	


</script>
	</head>
	<body>

	<s:form name=" BillSettlement" method="GET" action="fillBillSettlementData.html?saddr=${urlHits}"  target="_blank">
	
    
    <div id="formHeading">
		<label>Bill Settlement</label>
			</div>
<br/>
<br/>
<br/>
<br/>
<br/>
    <div style=" background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 400px; margin: auto; overflow-x: scroll; overflow-y: scroll; width: 90%;">
	<table id="tblData"
			style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
			class="transTablex col2-right col3-right col4-right col5-right">

	</table>
	
	<p align="center">
	
</div>
	<s:input type="hidden" path="strBillNo" id="hiddBillNo" />
<s:input type="hidden" path="strTableNo" id="hiddTableNO" />
<s:input type="hidden" path="selectedRow" id="hiddSelectedRow" />
	</s:form>



	</body>
	</html>
