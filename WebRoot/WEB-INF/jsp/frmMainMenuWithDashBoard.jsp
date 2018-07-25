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
    	funDrawMonthWiseSaleLineGraph(newFromDate,newToDate);
	});
    
    
    function funDrawMonthWiseSaleLineGraph(newFromDate,newToDate) 
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
     	var gurl = getContextPath()+"/loadSaleVSPurchaseDtl.html";
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
     	            renderTo: 'container1',
     	            type: 'column',
     	            margin: [ 50, 50, 100, 80]
     	        },
     	       title: 
     	        {
     	            text: ''
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
	<label> Sales Vs purchase</label>
		  <div style="background-color: #ffffff; border: 1px solid #ccc; display: block; ">
	     <br />
	     <div id="container1" style=" width: 100%; height: 800px; margin: auto;float:center;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		
		</div>
	</body>
</html>