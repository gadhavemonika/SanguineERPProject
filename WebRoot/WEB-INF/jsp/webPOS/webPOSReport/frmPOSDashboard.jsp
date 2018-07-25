<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>


<html>
    <head>  
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" src="<spring:url value="/resources/js/jquery-1.6.3.min.js"/>"></script>
        <script type="text/javascript" src="<spring:url value="/resources/js/highcharts.js"/>"></script>
        <script type="text/javascript" src="<spring:url value="/resources/js/exporting.js"/>"></script>
        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
		<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>  
        <script type="text/javascript">
        
        $(function() 
        {		
        	$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtFromDate" ).datepicker('setDate', 'today');
			$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtToDate" ).datepicker('setDate', 'today');
			
        }); 
        
         $(document).ready(function () 
         {    
        	 var date = new Date();
        	 $("#txtFromDate").datepicker({
      		   dateFormat : 'dd-mm-yy'
      		});
      		$("#txtFromDate").datepicker('setDate', new Date("04-01-"+date.getFullYear()));
      		
      		$("#txtToDate").datepicker({
      			dateFormat : 'dd-mm-yy'
      		});
      		$("#txtToDate").datepicker('setDate', 'today');

        		
        		funShowRecord();
        		
        		
			
           /* RenderPieChart('container', [
                      ['Firefox', 45.0],
                      ['IE', 26.8],
                      ['Chrome',  12.8],                         
                      ['Safari', 8.5],
                      ['Opera', 6.2],
                      ['Others', 0.7]
                  ]);    
         */
     
      /* $('#btnShow').live('click', function()
    	{ 
         var data = [
                      ['Firefox', 42.0],
                      ['IE', 26.8],
                      {
                          name: 'Chrome',
                          y: 14.8,
                          sliced: true,
                          selected: true
                      },
                      ['Safari', 6.5],
                      ['Opera', 8.2],
                      ['Others', 0.7]
                  ];
         
         
         var IDs = [];

         IDs.push(['Firefox', 72.2]);
         IDs.push(['IE', 27.8]);
             
            RenderPieChart('container', IDs);
            
            
     });
         */
     
            
        });
         
         
         function funShowRecord()
         {
       	    $('#container1').empty();
       	    $('#container2').empty();
	       	$('#container3').empty();
	       	$('#container4').empty();
	       	$('#container5').empty(); 
	       	$('#container6').empty(); 
	       	
        	funDrawMonthWiseSaleLineGraph(); 
        	
     		//funDrawItemWiseSaleChart();
     		
         }
         
         
         function funDrawPOSWiseSaleChart() 
         {
        	var dataSet = [];
         	var strReportTypedata="POS Wise";
        	var fDate = $("#txtFromDate").val();
        	var tDate = $("#txtToDate").val();
        	var posName=$('#cmbPOSName').val();
        	var gurl = getContextPath()+"/loadPOSWiseSalesReportForDashboard.html";
 			$.ajax({
 				type : "GET",
 				data:{ fromDate:fDate,
 					   toDate:tDate,
  					   strReportTypedata:strReportTypedata,
  					   strPOSName:posName,
 					},
 				url : gurl,
 				dataType : "json",
 				success : function(response) 
 				{
 				 	if (response== 0) 
 					{
 						alert("Data Not Found");
 					} 
 					else 
 					{ 
 						$.each(response.arrGraphList,function(i,item)
 			 			{
 			 			            	
 			 				//alert(item.strPOSName)
 			 				dataSet.push([item.strPOSName, item.dblSettlementAmt]);
 			 		    });
 						RenderPieChart('container2', dataSet,strReportTypedata);
 		        	}
 					
 				}
 			});	 
 		 }
         
         
         function funDrawGroupWiseSaleChart() 
         {
        	var dataSet = [];
         	var strReportTypedata="Group Wise";
        	var fDate = $("#txtFromDate").val();
        	var tDate = $("#txtToDate").val();
        	var posName=$('#cmbPOSName').val();
        	var gurl = getContextPath()+"/loadPOSWiseSalesReportForDashboard.html";
 			$.ajax({
 				type : "GET",
 				data:{ fromDate:fDate,
 					   toDate:tDate,
  					   strReportTypedata:strReportTypedata,
  					   strPOSName:posName,
 					},
 				url : gurl,
 				dataType : "json",
 				success : function(response) 
 				{
 				 	if (response== 0) 
 					{
 						alert("Data Not Found");
 					} 
 					else 
 					{ 
 						$.each(response.arrGraphList,function(i,item)
 			 			{
 			 			            	
 			 				//alert(item.strPOSName)
 			 				dataSet.push([item.strItemName, item.dblSettlementAmt]);
 			 		    });
 						RenderPieChart('container3', dataSet,strReportTypedata);
 		        	}
 					
 				}
 			});	 
 		 }
         
         
         function funDrawSubGroupWiseSaleChart() 
         {
        	var dataSet = [];
         	var strReportTypedata="Subgroup Wise";
        	var fDate = $("#txtFromDate").val();
        	var tDate = $("#txtToDate").val();
        	var posName=$('#cmbPOSName').val();
        	var gurl = getContextPath()+"/loadPOSWiseSalesReportForDashboard.html";
 			$.ajax({
 				type : "GET",
 				data:{ fromDate:fDate,
 					   toDate:tDate,
  					   strReportTypedata:strReportTypedata,
  					   strPOSName:posName,
 					},
 				url : gurl,
 				dataType : "json",
 				success : function(response) 
 				{
 				 	if (response== 0) 
 					{
 						alert("Data Not Found");
 					} 
 					else 
 					{ 
 						$.each(response.arrGraphList,function(i,item)
 			 			{
 			 			            	
 			 				//alert(item.strPOSName)
 			 				dataSet.push([item.strItemName, item.dblSettlementAmt]);
 			 		    });
 						RenderPieChart('container4', dataSet,strReportTypedata);
 		        	}
 					
 				}
 			});	 
 		 }
         
         
         function funDrawMenuHeadWiseSaleChart() 
         {
        	var dataSet = [];
         	var strReportTypedata="Menu Wise";
        	var fDate = $("#txtFromDate").val();
        	var tDate = $("#txtToDate").val();
        	var posName=$('#cmbPOSName').val();
        	var gurl = getContextPath()+"/loadPOSWiseSalesReportForDashboard.html";
 			$.ajax({
 				type : "GET",
 				data:{ fromDate:fDate,
 					   toDate:tDate,
  					   strReportTypedata:strReportTypedata,
  					   strPOSName:posName,
 					},
 				url : gurl,
 				dataType : "json",
 				success : function(response) 
 				{
 				 	if (response== 0) 
 					{
 						alert("Data Not Found");
 					} 
 					else 
 					{ 
 						$.each(response.arrGraphList,function(i,item)
 			 			{
 			 			            	
 			 				//alert(item.strPOSName)
 			 				dataSet.push([item.strItemName, item.dblSettlementAmt]);
 			 		    });
 						RenderPieChart('container5', dataSet,strReportTypedata);
 		        	}
 					
 				}
 			});	 
 		 }
         
         
         
         function funDrawItemWiseSaleChart() 
         {
        	var dataSet = [];
         	var strReportTypedata="Item Wise";
        	var fDate = $("#txtFromDate").val();
        	var tDate = $("#txtFromDate").val();
        	var tDate = $("#txtToDate").val();
        	var gurl = getContextPath()+"/loadPOSWiseSalesReportForDashboard.html";
 			$.ajax({
 				type : "GET",
 				data:{ fromDate:fDate,
 					   toDate:tDate,
 					   strReportTypedata:strReportTypedata,
 					},
 				url : gurl,
 				dataType : "json",
 				success : function(response) 
 				{
 				 	if (response== 0) 
 					{
 						alert("Data Not Found");
 					} 
 					else 
 					{ 
 						$.each(response.arrGraphList,function(i,item)
 			 			{
 			 			            	
 			 				//alert(item.strPOSName)
 			 				dataSet.push([item.strItemName, item.dblSettlementAmt]);
 			 		    });
 						RenderPieChart('container6', dataSet,strReportTypedata);
 		        	}
 					
 				}
 			});	 
 		 }
         
         
         function funDrawOperationWiseSaleChart() 
         {
        	var dataSet = [];
         	var strReportTypedata="Operation Wise";
        	var fDate = $("#txtFromDate").val();
        	var tDate = $("#txtToDate").val();
        	var posName=$('#cmbPOSName').val();
        	var gurl = getContextPath()+"/loadPOSWiseSalesReportForDashboard.html";
 			$.ajax({
 				type : "GET",
 				data:{ fromDate:fDate,
 					   toDate:tDate,
  					   strReportTypedata:strReportTypedata,
  					   strPOSName:posName,
 					},
 				url : gurl,
 				dataType : "json",
 				success : function(response) 
 				{
 				 	if (response== 0) 
 					{
 						alert("Data Not Found");
 					} 
 					else 
 					{ 
 						$.each(response.arrGraphList,function(i,item)
 			 			{
 			 			            	
 			 				//alert(item.strPOSName)
 			 				dataSet.push([item.strItemName, item.dblSettlementAmt]);
 			 		    });
 						RenderPieChart('container6', dataSet,strReportTypedata);
 		        	}
 					
 				}
 			});	 
 		 }
         
         
         
         
         function RenderPieChart(elementId, dataList,reportType) 
         {
        	 if(dataList.length === 0)
             {
        		 
             }	
        	 else
             {
        		 var fDate = $("#txtFromDate").val();
            	 var tDate = $("#txtToDate").val();
             	 var textToDisplay='';
             	 textToDisplay=reportType+' Sale ';
                 new Highcharts.Chart({
                     chart: {
                    	 options3d: {
                             enabled: true,
                             alpha: 45,
                             beta: 0},
                         renderTo: elementId,
                         plotBackgroundColor: null,
                         plotBorderWidth: null,
                         plotShadow: false
                     }, title: {
                         text:textToDisplay,
                         style: 
        	              {
        	                fontWeight: 'bold'
        	              }
                     },
                     tooltip: {
                         formatter: function () {
                             return '<b>' + this.point.name + '</b>: ' + Math.round(this.percentage) + ' %';
                         }
                     
                     },
                     plotOptions: {
                         pie: {
                             allowPointSelect: true,
                             cursor: 'pointer',
                             dataLabels: {
                                 enabled: true,
                                 color: '#000000',
                                 connectorColor: '#000000',
                                 formatter: function () {
                                	 return '<b>' + this.point.name + '</b>: ' + Highcharts.numberFormat(this.y/100000, 2)+'Lac' ;	 
                                 }
                             }
                         }
                     },
                     credits: 
                     {
                       enabled: false
                     },
                    exporting: 
                    { 
                    	enabled: false 
                    },	
                    	
                     series: [{
                         type: 'pie',
                         name: 'Browser share',
                         data: dataList
                     }]
                 });
             }		 
        	 
         };
         
         
         
         function funDrawMonthWiseSaleLineGraph() 
         {
        	var dataSet = [],xData=[];
          	var strReportTypedata="Month Wise";
         	var fDate = $("#txtFromDate").val();
         	var tDate = $("#txtToDate").val();
         	var posName=$('#cmbPOSName').val();
        	var gurl = getContextPath()+"/loadPOSWiseSalesReportForDashboard.html";
 			$.ajax({
 				type : "GET",
 				data:{ fromDate:fDate,
 					   toDate:tDate,
  					   strReportTypedata:strReportTypedata,
  					   strPOSName:posName,
  					},
  				url : gurl,
  				dataType : "json",
  				success : function(response) 
  				{
  				 	if (response== 0) 
  					{
  						alert("Data Not Found");
  					} 
  					else 
  					{ 
  						$.each(response.arrGraphList,function(i,item)
  			 			{
  			 			            	
  			 				//alert(item.strPOSName)
  			 				dataSet.push([item.dblSettlementAmt]);
  							xData.push([item.strItemName]);
  			 		    });
  						RenderLineChart(dataSet,xData);
  		        	}
  					
  				}
  			});	 
        	 
        }
         
         
         function RenderLineChart(dataList,xData) 
         {
        	 if(dataList.length === 0)
             {
        		 alert('No Record Found');
             }
        	 else
        	 {
        		 var fDate = $("#txtFromDate").val();
            	 var tDate = $("#txtToDate").val();
            	 
            	 var chart = new Highcharts.Chart({

         	        chart: {
         	            renderTo: 'container1'
         	        },
         	        
         	        title: {
         	            text: 'Month Wise Sale ',
         	            style: 
         	              {
         	                fontWeight: 'bold'
         	              }
         	        },
         	        
         	        yAxis: 
         	        {
         	            title: 
         	            {
         	                text: 'Sale'
         	            },
         	           labels: 
         	           { 
         	            formatter: function() 
         	            {
         	               return Math.round(this.value/100000) + 'Lac';
         	            }
         	           }
         	        },
         	        
         	        xAxis: 
         	        {
         	           title: 
         	           {
         	        	  text: "Month"
         	           },
         	        	tickInterval: 1,
         	           
         	            
         	           categories: xData
         	                
         	       },
         	       
         	       credits: 
                    {
                      enabled: false
                    },
                    exporting: 
                    { 
                   	enabled: false 
                    },
                    
                    tooltip:{
                        formatter:function()
                        {
                            console.log(this);
                            return 'Month: ' + this.key + ' sale: ' +  Highcharts.numberFormat(this.y/100000, 2) ;
                        }
                    },

         	        series: [{
         	        	showInLegend: false,
         	            data: dataList
         	            /*	[20,15,23,15,17,36,30,20,50,35,43,65, 
         	            {
         	                dataLabels: {
         	                    enabled: true,
         	                    align: 'left',
         	                    crop: false,
         	                    
         	                },
         	               
         	            }]
         	        */
         	        }]

         	    });
            	 
            	
            	funDrawPOSWiseSaleChart();
          	    funDrawGroupWiseSaleChart();
          		funDrawSubGroupWiseSaleChart();
          		funDrawMenuHeadWiseSaleChart();
          		funDrawOperationWiseSaleChart();
        	 }	 
        	 
         };
         
         
        
         
    </script>
    </head>
    <body>
     <s:form name="dashboard" method="POST"
		action="rptPOSWiseSalesReport.html?saddr=${urlHits}"
		target="_blank">
	  <table class="masterTable"  style=" margin: auto; float:left; width:100%;height:50px;">
               
               <tr style="background-color: #ffffff;">
               
                  <td colspan="4" align="center" style="padding-left: 5px;padding-bottom:3px; font-size: 15px "> <label>POS DASHBOARD</label>
				  </td>
				  <td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td>
		          <td colspan="4" align="left"> <label>From Date</label> &nbsp;&nbsp;
		          <s:input id="txtFromDate" required="required"
							path="strFromDate" pattern="\d{1,2}-\d{1,2}-\d{4}"
							cssClass="calenderTextBox" /> &nbsp;&nbsp;&nbsp;&nbsp;
				  <label>To Date</label> &nbsp;&nbsp;
		          <s:input id="txtToDate" required="required"
							path="strToDate" pattern="\d{1,2}-\d{1,2}-\d{4}"
							cssClass="calenderTextBox" /> &nbsp;&nbsp;&nbsp;&nbsp;
				 <s:input type="button" id="btnShow" value="Show" path="strShow"  class="form_button" onclick="funShowRecord()"/>
				 &emsp;&ensp;&emsp;&ensp;
				<label>POS Name:</label>
				<s:select id="cmbPOSName" name="cmbPOSName" path="strPOSName" cssClass="BoxW124px" items="${posList}" >
				</s:select>
				 </td> 
			   </tr>
			</table>
    
        <br>
        <div style="background-color: #ffffff; border: 1px solid #ccc; display: block; ">
	     <br />
	     <div id="container1" style=" width: 50%; height: 300px; margin: auto;float:left;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		
		<div id="container2" style=" width: 50%; height: 300px; margin:auto;float:left;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		
		<div id="container3" style=" width: 50%; height: 270px; margin: auto;float:left;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		
		<div id="container4" style=" width: 50%; height: 270px; margin: auto;float:left;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		
		<div id="container5" style=" width: 50%; height: 270px; margin: auto;float:left;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		
		<div id="container6" style=" width: 50%; height: 270px; margin: auto;float:left;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		</div>	
  
       
	</s:form>	
    </body>
</html> 



