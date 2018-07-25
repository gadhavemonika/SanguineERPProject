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
        <script src="http://code.jquery.com/jquery-1.6.3.min.js"></script>
		<script src="http://code.highcharts.com/highcharts.js"></script>
		<script src="http://code.highcharts.com/modules/exporting.js"></script>
		<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
		<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>  
        <script type="text/javascript">
        
        $(function() 
                {	
		        	var startDate="${startDate}";
					var arr = startDate.split("/");
					Date1=arr[0]+"-"+arr[1]+"-"+arr[2];
                	$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
        			$("#txtFromDate" ).datepicker('setDate', Date1);
        			$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
        			$("#txtToDate" ).datepicker('setDate', 'today');
        			
                }); 
                
                 $(document).ready(function () 
                 {    
                	var startDate="${startDate}";
 					var arr = startDate.split("/");
 					Date1=arr[0]+"-"+arr[1]+"-"+arr[2]; 
                	$("#txtFromDate").datepicker({
              		   dateFormat : 'dd-mm-yy'
              		});
              		$("#txtFromDate").datepicker('setDate', Date1);
              		
              		$("#txtToDate").datepicker({
              			dateFormat : 'dd-mm-yy'
              		});
              		$("#txtToDate").datepicker('setDate', 'today');

        		
//         		   funShowRecord();
                });
         
         
         function funShowRecord()
         {
       	    $('#container1').empty();
       	    $('#container2').empty();
       	    $('#container3').empty();
     	    $('#container4').empty();
       	    funDrawMonthWiseSaleBarGraph();
       	    funDrawMonthWiseLineGraph();
	       	funDrawGroupWiseGRNSalePieChart();
    	   	funDrawGroupWiseInvSalePieChart();
     		
         }
         
         function funDrawMonthWiseSaleBarGraph() 
         {
        	var dataSet = [],xData=[],dataSet1 = [];
          	var fDate = $("#txtFromDate").val();
         	var tDate = $("#txtToDate").val();
        	var gurl = getContextPath()+"/loadComparisonGRNandInv.html";
 			$.ajax({
 				type : "GET",
 				data:{ fromDate:fDate,
 					   toDate:tDate,
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
  						$.each(response.arrGraphGrnInvList,function(i,item)
  			 			{
  			 			            	
  			 				//alert(item.strPOSName)
  			 				dataSet.push([item.dblInvAmt]);
  			 				dataSet1.push([item.dblGrnAmt]);
  							xData.push([item.strMonthName]);
  							
  			 		    });
  						//RenderLineChart(dataSet,xData);
  						funDrawBarChart(dataSet,xData,dataSet1); 
  		        	}
  					
  				}
  			});	 
        	 
        }
         
         
         function funDrawBarChart(dataSet,xData,dataSet1) 
         {
        	 if(dataSet.length === 0)
             {
        		 alert('No Record Found');
             }
        	 else
        	 {
        		 var datasety = dataSet;
          	    var datasety1 = dataSet1;
          	    for(i=0;i<datasety.length;i++)
          	    {
          	        datasety[i] = parseFloat(datasety[i]);

          	    }
          	    
          	    for(i=0;i<datasety1.length;i++)
          	    {
          	        datasety1[i] = parseFloat(datasety1[i]);

          	    }

          	    var datasetx = new Array(); 
          	    datasetx = xData;
          	     
          	  
          	    chart = new Highcharts.Chart({
          	        chart: {
          	            renderTo: 'container1',
          	            type: 'column',
          	            margin: [ 50, 50, 100, 80]
          	        },
          	       title: 
          	        {
          	            text: 'Revenue '
          	        },
          	        style: 
  	   	             {
  	   	                fontWeight: 'bold'
  	   	             },
  	   	             
          	        xAxis: {
          	            categories: datasetx,
          	            labels: {
          	                enabled: true,
          	                rotation: -45,
          	                align: 'right',
          	                style: {
          	                    fontSize: '13px',
          	                    fontFamily: 'Verdana, sans-serif'
          	                }
          	            }
          	        },
          	        yAxis: {
          	           min: 0,
          	           title: 
           	           {
           	                text: 'Revenue Vs Purchase',
           	              style: 
           	              {
           	                fontWeight: 'bold'
           	              }
           	           },
           	           labels: 
           	           { 
           	            formatter: function() 
           	            {
           	               return Math.round(this.value/100000) + 'Lac';
           	            }
           	           }  

          	        },
          	        plotOptions:{ // for different color of bar
          	           // series:{ colorByPoint: true}
          	        	column: {
  	    					dataLabels: {
  	    						enabled: true
  	    					}
  	    				}
          	        },
          	        legend: 
          	        {
          	            enabled: true
          	        },
          	        tooltip: {
          	        	formatter: function() {
         	            	 return '<b>'+ this.x +'</b><br/>'+ this.series.name +': '+ Highcharts.numberFormat(this.y/100000, 2); 
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
          	            name: 'Revenue',
          	            data: datasety,

          	            dataLabels: {
          	                formatter:function(){   // for format value 
          	                    return 'USD'+this.y
          	                    },
          	                enabled: false,
          	                rotation: -90,
          	                color: '#FFFFFF',
          	                align: 'right',
          	                x: 4,
          	                y: 10,
          	                style: {
          	                    fontSize: '13px',
          	                    fontFamily: 'Verdana, sans-serif'
          	                }
          	            }
          	        },
          	        
          	        {
          	            name: 'Purchase',
          	            data: datasety1,

          	            dataLabels: {
          	                formatter:function(){   // for format value 
          	                    return 'USD'+this.y
          	                    },
          	                enabled: false,
          	                rotation: -90,
          	                color: '#FFFFFF',
          	                align: 'right',
          	                x: 4,
          	                y: 10,
          	                style: {
          	                    fontSize: '13px',
          	                    fontFamily: 'Verdana, sans-serif'
          	                }
          	            }
          	        },
          	        ]
          	    });
          	     
        	 }	
        	 
        	 
         };
         
         
         
         
         function funDrawMonthWiseLineGraph() 
         {
        	 var dataSet = [],xData=[];
          	var fDate = $("#txtFromDate").val();
         	var tDate = $("#txtToDate").val();
         	var reportType = "POSMonthwise"
         	var gurl = getContextPath()+"/loadComparisonGRNandInvLineChart.html";
 			$.ajax({
 				type : "GET",
 				data:{ fromDate:fDate,
 					   toDate:tDate,
 					   reportType:reportType
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
  						
  						$.each(response.arrGraphGrnInvList,function(i,item)
  			 			{
  			 				dataSet.push([item.dblGrnAmt]);
  							xData.push([item.strMonthName]);
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
         	            renderTo: 'container2'
         	        },
         	        
         	        title: {
         	            text: 'Month Wise Chart ',
         	            style: 
         	              {
         	                fontWeight: 'bold'
         	              }
         	        },
         	        
         	        yAxis: 
         	        {
         	            title: 
         	            {
         	                text: 'Amount'
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
                            return 'Month: ' + this.key + 'Profit: ' +  Highcharts.numberFormat(this.y/100000, 2) ;
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
            	 
            	
           
        	 }	 
        	 
         };
         
         
         function funDrawGroupWiseGRNSalePieChart() 
         {
        	var dataSet = [];
         	var strReportTypedata="Group Wise Purchase Chart";
        	var fDate = $("#txtFromDate").val();
        	var tDate = $("#txtToDate").val();
        	var posName=$('#cmbPOSName').val();
        	var gurl = getContextPath()+"/loadGroupWiseGrnPieChart.html";
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
 						
 						$.each(response.arrGraphGrnInvList,function(i,item)
 			 			{
 			 			            	
 			 				//alert(item.strPOSName)
 			 				dataSet.push([item.strPOSName, item.dblGrnAmt]);
 			 		    });
 						RenderPieChart('container3', dataSet,strReportTypedata);
 		        	}
 					
 				}
 			});	 
 		 }
         
         
         function funDrawGroupWiseInvSalePieChart() 
         {
        	var dataSet = [];
         	var strReportTypedata="Group Wise Revenue Chart ";
        	var fDate = $("#txtFromDate").val();
        	var tDate = $("#txtToDate").val();
        	var posName=$('#cmbPOSName').val();
        	var gurl = getContextPath()+"/loadGroupWiseInvPieChart.html";
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
 						
 						$.each(response.arrGraphGrnInvList,function(i,item)
 			 			{
 			 			            	
 			 				//alert(item.strPOSName)
 			 				dataSet.push([item.strPOSName, item.dblInvAmt]);
 			 		    });
 						RenderPieChart('container4', dataSet,strReportTypedata);
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
             	 textToDisplay=reportType;
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
         
         
         
    </script>
    </head>
    <body>
     <s:form name="ComparisionGRNandInvoice" method="POST"
		action=""
		target="_blank">
	  <table class="transTable"  style=" margin: auto; float:left; width:100%;height:50px">
               
               <tr style="background-color: #ffffff;">
               
                  <td colspan="4" align="center" style="padding-left: 5px;padding-bottom:3px; font-size: 15px "> <label> Revenue Vs Purchase  </label>
				  </td>
				  <td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td>
		          <td colspan="4" align="left"> <label>From Dater</label> &nbsp;&nbsp;
		          <s:input id="txtFromDate" required="required"
							path="strFromDate" pattern="\d{1,2}-\d{1,2}-\d{4}"
							cssClass="calenderTextBox" /> &nbsp;&nbsp;&nbsp;&nbsp;
				 <label>To Date</label> &nbsp;&nbsp;
		          <s:input id="txtToDate" required="required"
							path="strToDate" pattern="\d{1,2}-\d{1,2}-\d{4}"
							cssClass="calenderTextBox" /> &nbsp;&nbsp;&nbsp;&nbsp;
				 <s:input type="button" id="btnShow" value="Show" path="strShow"  class="form_button" onclick="funShowRecord()"/>
				  </td> 
			   </tr>
			</table>
    
        <br>
             <div style="background-color: #ffffff; border: 1px solid #ccc; display: block; ">
	     <br />
	     <div id="container1" style=" width: 50%; height: 420px; margin: auto;float:left;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		
		<div id="container2" style=" width: 50%; height: 420px; margin:auto;float:left;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		 <br />
		  <br />
		   <br />
		 
		<div id="container3" style=" width: 50%; height: 420px; margin:auto;float:left;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		<div id="container4" style=" width: 50%; height: 420px; margin:auto;float:left;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		</div>	
  
       
	</s:form>	
    </body>
</html> 



