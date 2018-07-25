<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Web Stocks</title>
       <script type="text/javascript" src="<spring:url value="/resources/js/jquery-1.6.3.min.js"/>"></script>
        <script type="text/javascript" src="<spring:url value="/resources/js/highcharts.js"/>"></script>
        <script type="text/javascript" src="<spring:url value="/resources/js/exporting.js"/>"></script>
		<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
		<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>  
        
     <script type="text/javascript">
    $(document).ready(function(){
    	var s=getContextPath()+"/resources/images/";
    	
    	
//     	$("#Desktop").attr('src', s+'Desktop.png');
//     	$("#Desktop").attr('title','Desktop');
		var newFromDate="${newFromDate}";
		var newToDate="${newToDate}";
		$('#container1').empty();
		funDrawMonthWiseSaleVsPurchaseGraph(newFromDate,newToDate);
    	funDrawMonthWiseSaleLineGraph(newFromDate,newToDate);
    	funDrawGroupWiseSaleChart(newFromDate,newToDate);
    	funDrawRegionWiseSaleChart(newFromDate,newToDate); 
    	funDrawMonthWiseProfitGraph(newFromDate,newToDate);
    	funDrawGroupWiseProfitGraph(newFromDate,newToDate);
	});
    
    
    function funDrawMonthWiseSaleVsPurchaseGraph(newFromDate,newToDate) 
    {
	   	var dataSet = [],xData=[],dataSet1 = [];
	   	var fromDte=newFromDate.split("-");
		var toDte=newToDate.split("-");
		if(fromDte[1].length==1)
		{
			fromDte[1]="0"+fromDte[1];
		}
     	var fDate =fromDte[2]+"-"+fromDte[1]+"-"+fromDte[0];
    	var tDate = toDte[0]+"-"+toDte[1]+"-"+toDte[2];
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
      	               return Math.round(this.value/10000) + 'K';
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
    	            	 return '<b>'+ this.x +'</b><br/>'+ this.series.name +': '+ Highcharts.numberFormat(this.y/10000, 2); 
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
     	     
   	 }	 
   	 
    }
    
    
    function funDrawMonthWiseSaleLineGraph(newFromDate,newToDate) 
    {
   	    var dataSet = [],xData=[];
     	var strReportTypedata="Month Wise";
    	//var fDate = $("#txtFromDate").val();
    	//var tDate = $("#txtToDate").val();
    	var fromDte=newFromDate.split("-");
		var toDte=newToDate.split("-");
		if(fromDte[1].length==1)
		{
			fromDte[1]="0"+fromDte[1];
		}
     	var fDate =fromDte[2]+"-"+fromDte[1]+"-"+fromDte[0];
    	var tDate = toDte[0]+"-"+toDte[1]+"-"+toDte[2];
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
    

    function funDrawGroupWiseSaleChart(newFromDate,newToDate) 
    {
    	var dataSet = [];
    	var fromDte=newFromDate.split("-");
		var toDte=newToDate.split("-");
		if(fromDte[1].length==1)
		{
			fromDte[1]="0"+fromDte[1];
		}
     	var fDate =fromDte[2]+"-"+fromDte[1]+"-"+fromDte[0];
    	var tDate = toDte[0]+"-"+toDte[1]+"-"+toDte[2];
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
    
    
    function funDrawRegionWiseSaleChart(newFromDate,newToDate) 
    {
    	var dataSet = [];
    	var fromDte=newFromDate.split("-");
		var toDte=newToDate.split("-");
		if(fromDte[1].length==1)
		{
			fromDte[1]="0"+fromDte[1];
		}
     	var fDate =fromDte[2]+"-"+fromDte[1]+"-"+fromDte[0];
    	var tDate = toDte[0]+"-"+toDte[1]+"-"+toDte[2];
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
    
    
    function funDrawMonthWiseProfitGraph(newFromDate,newToDate) 
    {
	   	var dataSet = [],xData=[],dataSet1 = [];
	   	var fromDte=newFromDate.split("-");
		var toDte=newToDate.split("-");
		if(fromDte[1].length==1)
		{
			fromDte[1]="0"+fromDte[1];
		}
     	var fDate =fromDte[2]+"-"+fromDte[1]+"-"+fromDte[0];
    	var tDate = toDte[0]+"-"+toDte[1]+"-"+toDte[2];
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
    
    
    function funDrawGroupWiseProfitGraph(newFromDate,newToDate) 
    {
	   	var dataSet = [],xData=[],dataSet1 = [];
	 	var fromDte=newFromDate.split("-");
		var toDte=newToDate.split("-");
		if(fromDte[1].length==1)
		{
			fromDte[1]="0"+fromDte[1];
		}
     	var fDate =fromDte[2]+"-"+fromDte[1]+"-"+fromDte[0];
    	var tDate = toDte[0]+"-"+toDte[1]+"-"+toDte[2];
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
    	               return Math.round(this.value/10000) + 'K';
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
                       return 'Month: ' + this.key + ' sale: ' +  Highcharts.numberFormat(this.y/10000, 2) ;
                   }
               },

    	        series: [{
    	        	showInLegend: false,
    	            data: dataList
					 }]

    	    });
       	 
       		/*funDrawPOSWiseSaleChart();
     	    funDrawGroupWiseSaleChart();
     		funDrawSubGroupWiseSaleChart();
     		funDrawMenuHeadWiseSaleChart();
     		funDrawOperationWiseSaleChart();
     		*/
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
      	               return Math.round(this.value/10000) + 'K';
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
    	            	 return '<b>'+ this.x +'</b><br/>'+ this.series.name +': '+ Highcharts.numberFormat(this.y/10000, 2); 
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
     	            name: 'Profit',
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
      	               return Math.round(this.value/10000) + 'K';
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
    	            	 return '<b>'+ this.x +'</b><br/>'+ this.series.name +': '+ Highcharts.numberFormat(this.y/10000, 2); 
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
	   
	<br>
     <div>
		<c:forEach items="" var="draw1" varStatus="status1">
			<div class="mainMenuIcon">
				<div style="padding-left: 30%; padding-right: 10%">
				<a href="${draw1.strRequestMapping}?saddr=1"><img id="Desktop" src="../${pageContext.request.contextPath}/resources/images/${draw1.strImgName}" title="${draw1.strFormDesc}" ></a>
				</div>
				<div style=" text-align: center;font-size:0.7em;font-weight:bold;">${draw1.strFormDesc}</div>
			</div>
		</c:forEach>
	 </div>	
	
		 <div style="background-color: #ffffff; border: 1px solid #ccc; display: block; ">
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
		
	</body>
</html>