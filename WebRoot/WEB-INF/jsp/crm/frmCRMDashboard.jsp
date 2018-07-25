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
<%--         <script type="text/javascript" src="<spring:url value="/resources/js/jquery-2.1.3.js"/>"></script> --%>
       <script type="text/javascript" src="<spring:url value="/resources/js/canvg.js"/>"></script> 
		<script type="text/javascript" src="<spring:url value="/resources/js/html2canvas.min.js"/>"></script> 
		<script type="text/javascript" src="<spring:url value="/resources/js/jspdf.min.js"/>"></script> 
		<script type="text/javascript" src="<spring:url value="/resources/js/canvg.min.js"/>"></script> 
		<script type="text/javascript" src="<spring:url value="/resources/js/jquery.min.js"/>"></script> 
        <script type="text/javascript" src="<spring:url value="/resources/js/jquery-1.6.3.min.js"/>"></script>
        <script type="text/javascript" src="<spring:url value="/resources/js/highcharts.js"/>"></script>
        <script type="text/javascript" src="<spring:url value="/resources/js/exporting.js"/>"></script>
		<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
		<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>  
<%-- 		<script type="text/javascript" src="<spring:url value="/resources/js/jspdf.debug.js"/>"></script> --%>
		
        <script type="text/javascript">
        
        $(function() 
                {	
        			var startDate="${startDate}";
    				var arr = startDate.split("/");
    				Dat=arr[0]+"-"+arr[1]+"-"+arr[2];	
                	$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
        			$("#txtFromDate" ).datepicker('setDate', Dat);
        			$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
        			$("#txtToDate" ).datepicker('setDate', 'today');
        			
                }); 
                
                 $(document).ready(function () 
                 {   
                	var startDate="${startDate}";
     				var arr = startDate.split("/");
     				Dat=arr[0]+"-"+arr[1]+"-"+arr[2];		 
                	 
                	$("#txtFromDate").datepicker({
              		   dateFormat : 'dd-mm-yy'
              		});
              		$("#txtFromDate").datepicker('setDate', Dat);
              		
              		$("#txtToDate").datepicker({
              			dateFormat : 'dd-mm-yy'
              		});
              		$("#txtToDate").datepicker('setDate', 'today');

        		
        		   funShowRecord();
                });
         
         
         function funShowRecord()
         {
       	    $('#container1').empty();
       	    funDrawMonthWiseSaleVsPurchaseGraph();
       	 	
         }
         
         function funExport()
         {
       	    var pdf = new jsPDF('p','mm', 'letter');
       		$('#tempStyleDiv').append("<style>.highcharts-container>svg>g>text{display:none;}</style>");
     		pdf.addHTML($('#containerGraph'),function() {
     		    pdf.save('dashboard.pdf');
     		    setTimeout(function(){$('#tempStyleDiv').html("");},3000);
     		});

       	 
         }
         
         
         
         function funDrawMonthWiseSaleVsPurchaseGraph() 
         {
     	   	var dataSet = [],xData=[],dataSet1 = [];
     	   	var fDate = $("#txtFromDate").val();
       		var tDate = $("#txtToDate").val();
         	var strReportTypedata="Sale Vs Purchase";
          	var gurl = getContextPath()+"/loadCRMSaleVSPurchaseDtl.html";
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
     					if (response.arrGraphList== 0) 
     					{
     						alert("Data Not Found");
     					}
     					else 
     					{ 
     						$.each(response.arrGraphList,function(i,item)
     			 			{
     			 			            	
     			 				//alert(item.strPOSName)
     			 				dataSet.push([item.dblSettlementAmt]);
     			 				dataSet1.push([item.dblAmount]);
     							xData.push([item.strItemName]);
     							
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
          	            renderTo: 'container5',
          	            type: 'column',
          	            margin: [ 50, 50, 100, 80]
          	        },
          	       title: 
          	        {
          	            text: 'Sale Vs Purchase ',
          	            style: 
        	   	             {
	          	            	fontWeight: 'bold',
	       	   	                fontSize: '18px',
	            	            fontFamily: 'Verdana, sans-serif',
	            	            color:'#800000'
        	   	             },
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
           	                text: 'Sale'
           	           },
           	           labels: 
           	           { 
           	            formatter: function() 
           	            {
           	               return Math.round(this.value/1000) + 'K';
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
         	            	 return '<b>'+ this.x +'</b><br/>'+ this.series.name +': '+ Highcharts.numberFormat(this.y/1000, 2); 
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
          	            name: 'Sale',
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
          	    
          	    
          	  	funDrawMonthWiseSaleLineGraph();
	      		funDrawGroupWiseSaleChart();
	      		funDrawRegionWiseSaleChart(); 
	      		funDrawMonthWiseProfitGraph();
	      		funDrawGroupWiseProfitGraph();
          	     
        	 }	 
        	 
         }
         
         
         function funDrawMonthWiseSaleLineGraph() 
         {
        	var dataSet = [],xData=[];
          	var strReportTypedata="Month Wise";
          	var fDate = $("#txtFromDate").val();
        	var tDate = $("#txtToDate").val();
        	var gurl = getContextPath()+"/loadCRMDashboardGraph.html";
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
     					if (response.arrGraphList== 0) 
     					{
     						alert("Data Not Found");
     					} 
     					else 
     					{ 
     						$.each(response.arrGraphList,function(i,item)
     			 			{
     			 			    dataSet.push([item.dblSettlementAmt]);
     							xData.push([item.strItemName]);
     			 		    });
     						RenderLineChart(dataSet,xData);
     		        	}
     					
     				}
     			});	 
        }
         

         function funDrawGroupWiseSaleChart() 
         {
         	var dataSet = [];
         	var fDate = $("#txtFromDate").val();
        	var tDate = $("#txtToDate").val();
         	var strReportTypedata="Group Wise";
     	   	var gurl = getContextPath()+"/loadCRMDashboardGraph.html";
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
     				 	if (response.arrGraphList== 0) 
     					{
     						alert("Data Not Found");
     					} 
     					else 
     					{ 
     						$.each(response.arrGraphList,function(i,item)
     			 			{          
     			 				dataSet.push([item.strPOSName, item.dblSettlementAmt]);
     			 		    });
     						RenderPieChart('container3', dataSet,strReportTypedata);
     		        	}
     					
     				}
     			});	 
     	 }
         
         
         function funDrawRegionWiseSaleChart() 
         {
         	var dataSet = [];
         	var fDate = $("#txtFromDate").val();
        	var tDate = $("#txtToDate").val();
         	var strReportTypedata="Region Wise";
     	   	var gurl = getContextPath()+"/loadCRMDashboardGraph.html";
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
     					if (response.arrGraphList== 0) 
     					{
     						alert("Data Not Found");
     					} 
     					else 
     					{ 
     						$.each(response.arrGraphList,function(i,item)
     			 			{          
     			 				dataSet.push([item.strPOSName, item.dblSettlementAmt]);
     			 		    });
     						RenderPieChart('container6', dataSet,strReportTypedata);
     		        	}
     					
     				}
     			});	 
     	 }
         
         
         function funDrawMonthWiseProfitGraph() 
         {
     	   	var dataSet = [],xData=[],dataSet1 = [];
     	   	var fDate = $("#txtFromDate").val();
       		var tDate = $("#txtToDate").val();
         	var strReportTypedata="MonthWise Profit";
          	var gurl = getContextPath()+"/loadCRMSaleVSPurchaseDtl.html";
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
     					if (response.arrGraphList== 0) 
     					{
     						alert("Data Not Found");
     					}
     					else 
     					{ 
     						$.each(response.arrGraphList,function(i,item)
     			 			{
     			 				dataSet.push([item.dblAmount]);
     							xData.push([item.strItemName]);	
     			 		    });
     						funDrawProfitBarChart(dataSet,xData); 
     		        	}
     					
     				}
     			});	 
        	 
        }
         
         
         function funDrawGroupWiseProfitGraph() 
         {
     	   	var dataSet = [],xData=[],dataSet1 = [];
     	   	var fDate = $("#txtFromDate").val();
       		var tDate = $("#txtToDate").val();
         	var strReportTypedata="Group Wise Profit";
          	var gurl = getContextPath()+"/loadCRMSaleVSPurchaseDtl.html";
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
     					if (response.arrGraphList== 0) 
     					{
     						alert("Data Not Found");
     					}
     					else 
     					{ 
     						$.each(response.arrGraphList,function(i,item)
     			 			{
     			 				dataSet.push([item.dblAmount]);
     							xData.push([item.strItemName]);	
     			 		    });
     						funDrawGroupWiseProfitBarChart(dataSet,xData); 
     		        	}
     					
     				}
     			});	 
        	 
        }
         
         
         function RenderPieChart(elementId, dataList,reportType) 
         {
        	 var fDate = $("#txtFromDate").val();
         	 var tDate = $("#txtToDate").val();
         	 var textToDisplay='';
         	 textToDisplay=reportType+' Report';
             new Highcharts.Chart({
                 chart: {
                     renderTo: elementId,
                     plotBackgroundColor: null,
                     plotBorderWidth: null,
                     plotShadow: false
                 }, title: {
                     text:textToDisplay,
                     style: 
     	              {
                    	 fontWeight: 'bold',
    	   	             fontSize: '18px',
         	             fontFamily: 'Verdana, sans-serif',
         	             color:'#800000'
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
                                 return '<b>' + this.point.name + '</b>: ' + Math.round(this.percentage) + ' %';
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
         };
         
         
         
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
         	            	fontWeight: 'bold',
       	   	                fontSize: '18px',
            	            fontFamily: 'Verdana, sans-serif',
            	            color:'#800000'
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
         	               return Math.round(this.value/1000) + 'K';
         	            }
         	           }
         	        },
         	        
         	        xAxis: 
         	        {
         	           title: 
         	           {
         	        	  text: "Month",
         	        	  style: 
        	               {
        	                fontWeight: 'bold',
        	                color:'#000000'
        	               }
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
                            return 'Month: ' + this.key + ' sale: ' +  Highcharts.numberFormat(this.y/1000, 2) ;
                        }
                    },

         	        series: [{
         	        	showInLegend: false,
         	            data: dataList
     					 }]

         	    });
            	 
        	    }	 
         };
         
         
         function funDrawProfitBarChart(dataSet,xData,dataSet1) 
         {
        	 if(dataSet.length === 0)
             {
        		 alert('No Record Found');
             }
        	 else
        	 {
        		 var datasety = dataSet;
          	    for(i=0;i<datasety.length;i++)
          	    {
          	        datasety[i] = parseFloat(datasety[i]);

          	    }
          	    var datasetx = new Array(); 
          	    datasetx = xData;
          	    chart = new Highcharts.Chart({
          	        chart: {
          	            renderTo: 'container2',
          	            type: 'column',
          	            margin: [ 50, 50, 100, 80]
          	        },
          	       title: 
          	        {
          	            text: 'MonthWise Profit ',
          	            style: 
        	   	             {
        	   	                fontWeight: 'bold',
        	   	                fontSize: '18px',
             	                fontFamily: 'Verdana, sans-serif',
             	                color:'#800000'
        	   	             },
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
           	                text: 'Profit'
           	           },
           	           labels: 
           	           { 
           	            formatter: function() 
           	            {
           	               return Math.round(this.value/1000) + 'K';
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
         	            	 return '<b>'+ this.x +'</b><br/>'+ this.series.name +': '+ Highcharts.numberFormat(this.y/1000, 2); 
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
          	            name: 'Month',
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
          	        ]
          	    });
          	     
        	 }	 
        	 
         } 
         
         function funDrawGroupWiseProfitBarChart(dataSet,xData,dataSet1) 
         {
        	 if(dataSet.length === 0)
             {
        		 alert('No Record Found');
             }
        	 else
        	 {
        		 var datasety = dataSet;
          	    for(i=0;i<datasety.length;i++)
          	    {
          	        datasety[i] = parseFloat(datasety[i]);

          	    }
          	    var datasetx = new Array(); 
          	    datasetx = xData;
          	    chart = new Highcharts.Chart({
          	        chart: {
          	            renderTo: 'container4',
          	            type: 'column',
          	            margin: [ 50, 50, 100, 80]
          	        },
          	       title: 
          	        {
          	            text: 'Group Wise Profit ',
          	            style: 
        	   	             {
        	   	                fontWeight: 'bold',
        	   	                fontSize: '18px',
             	                fontFamily: 'Verdana, sans-serif',
             	                color:'#800000'
        	   	             },
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
           	                text: 'Profit'
           	           },
           	           labels: 
           	           { 
           	            formatter: function() 
           	            {
           	               return Math.round(this.value/1000) + 'K';
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
         	            	 return '<b>'+ this.x +'</b><br/>'+ this.series.name +': '+ Highcharts.numberFormat(this.y/1000, 2); 
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
          	            name: 'Group',
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
          	        ]
          	    });
          	     
        	 }	 
        	 
         }  
         
    </script>
    </head>
    <body>
     <s:form name="Dashboard"   method="POST"
		action="rptPOSWiseSalesReport.html?saddr=${urlHits}"
		target="_blank">
	  <div id="tempStyleDiv"></div>	
	  <div  id="containerGraph" style=" width: 100%; height: 100%; margin: auto; overflow-x: hidden; border-collapse: separate; background-color: #ffffff; display: block; ">	
	  <table class="masterTable"  style=" margin: auto; float:left; width:100%;height:50px">
               
               <tr style="background-color: #ffffff;">
               
                  <td colspan="4" align="center" style="padding-left: 5px;padding-bottom:3px; font-size: 20px "> <label>Dashboard For WebCRM</label>
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
				 <s:input type="button" id="btnShow" value="Show" path="strShow" align="center" class="form_button" onclick="funShowRecord()"/>
				 <s:input type="button" id="btnExport" value="Export" path="" align="center" class="form_button" onclick="funExport()"/>
				  </td> 
			   </tr>
			</table>
    
        <br>
         <div  style=" width: 100%; height: 100%; margin: auto; overflow-x: hidden; border-collapse: separate; background-color: #ffffff; display: block; ">
	     <br />
	     <div id="container1" style=" width: 50%; height: 300px; margin: auto;float:left;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		
		<div id="container2" style=" width: 50%; height: 300px; margin:auto;float:left;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		
		<div id="container3" style=" width: 50%; height: 300px; margin: auto;float:left;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		
		<div id="container4" style=" width: 50%; height: 300px; margin: auto;float:left;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		
		<div id="container5" style=" width: 50%; height: 300px; margin: auto;float:left;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		
		<div id="container6" style=" width: 50%; height: 300px; margin: auto;float:left;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		</div>
		</div>	
  
       
	</s:form>	
    </body>
</html> 



