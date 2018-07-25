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
        });
         
         
         function funShowRecord()
         {
       	    $('#container1').empty();
       	    $('#container2').empty();
	       	$('#container3').empty();
	       	$('#container4').empty();
	       	$('#container5').empty(); 
	       	$('#container6').empty(); 
	       	
	       	funDrawPOSMonthWiseSaleLineGraph(); 
	       	funDrawPOSGroupMonthWiseSaleLineGraph();
	       //	funDrawCircularProgressChart();
	       	
	       	funDrawPOSOperationMonthWiseSaleLineGraph();
     		
         }
         
         
      
         function funDrawPOSMonthWiseSaleLineGraph() 
         {
        	var options;
        	var xData=[],mainDataSet=[],data=[];
          	var fDate = $("#txtFromDate").val();
         	var tDate = $("#txtToDate").val();
         	var reportType = "POSMonthwise"
         	var gurl = getContextPath()+"/loadComparisonwiseDashboardForPOS.html";
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
  						var jsonObj = [];
  						$.each(response.arrItemList,function(i,item)
  		  			 	{
  							var series = {};
  							var dataSet =[];
  							$.each(response.arrGraphList,function(j,item1)
  			  			 			{
  								        if(item.strItemName==item1.strItemCode)
  								        {
  								          dataSet.push([item1.dblAmount]);
  								        }	
  			  			 		    });  
  							
  							 mainDataSet.push(dataSet);
  							 series.name = item.strItemName;
  	  					     series.data = dataSet;
  	  					     jsonObj.push(series);
  		  			 				
  		  			   });
  						
  						$.each(response.arrMonthList,function(k,item2)
  		  		  		{
  		  							     xData.push([item2.strItemName]);	
  		  		  		});
  						
  						
  					     RenderLineChart(xData,jsonObj);
  					  
  						
  		        	}
  					
  				}
  			});	 
        	 
        }
         
         
         
         
         function funDrawPOSGroupMonthWiseSaleLineGraph() 
         {
        	var xData=[],mainDataSet=[],data=[];
          	var fDate = $("#txtFromDate").val();
         	var tDate = $("#txtToDate").val();
         	var reportType = "POSGroupMonthwise"
 			var searchurl = getContextPath() + "/loadComparisonwiseDashboard.html";
 			$.ajax({
 				type : "GET",
 				data:{ fromDate:fDate,
					   toDate:tDate,
					   reportType:reportType
					},
 				url : searchurl,
 				dataType : "json",
 				async : false,

 				success : function(response) 
 				{
 					var jsonObj = [], month = [],monthDtlArr={},groupDtlsArr={}; 
 					$.each(response.jArr, function(i, item) 
  					{
				        monthDtlArr=item.MonthDtls;
  						if(i==0)
  						{
  							$.each(monthDtlArr, function(j, monthItem) 
  	 		  	  			{
  	 		  							month.push(monthItem.MonthName);
  	 		  	  			});
  						}
 		  			});
 					
 					
                  $.each(response.POSDetailArrList, function(i, item) 
				   {
                	  var myMap = new Map();
                	   var groupAmtDtls=[],groupDtlsArr={};
                	    var posCode=item.POSCode;
		                groupDtlsArr=item.GroupDtls;
					    $.each(groupDtlsArr, function(k, groupItem) 
 	 		  	 		{
					       if(myMap.size > 0)
					       {
					    	if(myMap.has(groupItem.GroupName))
					    		{
					    		   groupAmtDtls=myMap.get(groupItem.GroupName);
					    	       groupAmtDtls.push(groupItem.TotalSaleAmt);	
						           myMap.set(groupItem.GroupName,groupAmtDtls);
					    		}
					    	else
					    	   {
					    		   groupAmtDtls=[];
					    		   groupAmtDtls.push(groupItem.TotalSaleAmt);	
						           myMap.set(groupItem.GroupName,groupAmtDtls);
					    	   }
					       }
					       else
					       {
					    	  groupAmtDtls=[];
					    	  groupAmtDtls.push(groupItem.TotalSaleAmt);	
						      myMap.set(groupItem.GroupName,groupAmtDtls);
					       }
	  					     
 	 		  	 		});
		                
		       
	              
	                myMap.forEach(function(value, key) 
	                {
	                	  console.log(key + ' = ' + value);
	                	  var series ={};
		                  series.name = item.POSCode;
				          series.stack=key;
	  					  series.data = value;
	  					  jsonObj.push(series);
	                });
				
						
				});
                  
                  RenderBarGraph(month,jsonObj) 

 				},

 				error : function(jqXHR, exception) {
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
         
         
         function funDrawPOSOperationMonthWiseSaleLineGraph() 
         {
        	var xData=[],mainDataSet=[],data=[];
          	var fDate = $("#txtFromDate").val();
         	var tDate = $("#txtToDate").val();
         	var reportType = "POSOperationWiseMonthwise"
 			var searchurl = getContextPath() + "/loadComparisonwiseDashboard.html";
 			$.ajax({
 				type : "GET",
 				data:{ fromDate:fDate,
					   toDate:tDate,
					   reportType:reportType
					},
 				url : searchurl,
 				dataType : "json",
 				async : false,

 				success : function(response) 
 				{
 					var jsonObj = [], month = [],monthDtlArr={},groupDtlsArr={}; 
 					$.each(response.jArr, function(i, item) 
  					{
				        monthDtlArr=item.MonthDtls;
  						if(i==0)
  						{
  							$.each(monthDtlArr, function(j, monthItem) 
  	 		  	  			{
  	 		  							month.push(monthItem.MonthName);
  	 		  	  			});
  						}
 		  			});
 					
 					
                 
                  
                 // RenderBarGraph(month,jsonObj) 

 				},

 				error : function(jqXHR, exception) {
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
         
         
         
         
         function RenderLineChart(xData,jsonObj) 
         {
        	 var processed_json = new Array();   
        	 if(jsonObj.length === 0)
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
         	        
         	       title: 
    	        	{
       		        text: 'POSWISE MONTHWISE SALE ',
       		        style: 
	       	              {
       		        	fontWeight: 'bold',
          	                color: 'gray',
          	                fontSize: '10px',
  	                        fontFamily: 'Verdana, sans-serif'
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
                    
                    series:jsonObj
                    
                  });
             }	 
        	 
         }
         
         
         
         function RenderBarGraph(xData,jsonObj) 
         {
        	 if(jsonObj.length === 0)
             {
        		 alert('No Record Found');
             }
        	 else
        	 {
        		 Highcharts.chart('container2', {

         		    chart: {
         		        type: 'column'
         		    },

         		    title: {
         		        text: 'POSWISE GROUPWISE MONTHWISE SALE ',
         		        style: 
 	       	              {
         		        	fontWeight: 'bold',
            	                color: 'gray',
            	                fontSize: '10px',
    	                        fontFamily: 'Verdana, sans-serif'
 	       	              }
         		    },

         		    xAxis: 
         		    {
         		        categories: xData
         		    },

         		    yAxis: 
         		    {
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
           	        /* stackLabels: {
           	           enabled: true,
           	           style: 
           	           {
           	               fontWeight: 'bold',
           	               color: 'gray',
           	               fontSize: '8px',
   	                       fontFamily: 'Verdana, sans-serif'
           	               
           	           },
           	           formatter: function() {
           	               return  this.stack;
           	           }
           	          }
           	           */
         		    },
         		    
         		    legend : 
         		    {
         		    	      align: 'right',
         		    	      x: 10,
         		    	      verticalAlign: 'top',
         		    	      y: 25,
         		    	      floating: true,
         		    	      backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
         		    	      borderColor: '#CCC',
         		    	      borderWidth: 1,
         		    	      shadow: false,
         		    	      itemStyle: {
         		    	            color: '#000000',
         		    	            fontWeight: 'bold',
         		    	            fontSize: '9px'
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
         		    
         		    tooltip: 
         		    {
         		        formatter: function () {
         		            return '<b>' + this.x + '</b><br/>' +
         		                this.series.name + ': ' + Highcharts.numberFormat(this.y/100000, 2) + '<br/>' +
         		                'Total: ' + Highcharts.numberFormat(this.point.stackTotal/100000, 2) 
         		                + '</b><br/>' +" Group: "+this.series.options.stack;
         		        }
         		    },

         		    plotOptions: 
         		    {
         		        column: {
         		            stacking: 'normal',
         		            pointWidth: 15
         		             
         		        }
         		    },

         		    series:jsonObj
         		    	/*[{
         		        name: 'John',
         		        data: [5, 3, 4, 7, 2],
         		        stack: 'male'
         		    }, {
         		        name: 'Joe',
         		        data: [3, 4, 4, 2, 5],
         		        stack: 'male'
         		    }, {
         		        name: 'Jane',
         		        data: [2, 5, 6, 2, 1],
         		        stack: 'female'
         		    }, {
         		        name: 'Janet',
         		        data: [3, 0, 4, 4, 3],
         		        stack: 'female'
         		    }]
         		    */
         		});
        	 }	
         }
         
         
         function funDrawCircularProgressChart()
         {
        	
        		    // Create the chart
        		    var chart = new Highcharts.Chart({
        		        chart: {
        		            renderTo: 'container3',
        		            type: 'pie'
        		        },
        		        title: {
        		                text: '60%',
        		                align: 'center',
        		                verticalAlign: 'middle'
        		        },
        		        
        		      /*  pane:
        		        {
        		            'center': ['50%', '50%'],
        		            'size': '500px',
        		            'startAngle': 0,
        		            'endAngle': 360,
        		            'background': {
        		              'backgroundColor': '#EEE',
        		              'innerRadius': '90%',
        		              'outerRadius': '100%',
        		              'borderWidth': 0
        		            }
        		          },
        		          
        		          */
        		        plotOptions: {
        		            pie: {
        		                shadow: false
        		                
        		            }
        		        },
        		        series: [{
        		            name: 'Browsers',
        		            data: [
        		               [ "Completed", 60],
        		               { 
        		                   "name": "Incomplete",
        		                   "y": 40,
        		                   color: 'Gainsboro'
        		               }
        		            ],
        		            size: '100%',
        		            innerSize: '95%',
        		            showInLegend:false,
        		            dataLabels: {
        		                enabled: false
        		            }
        		        }]
        		    });
        		
        		    
         }
        
         
         
         
        
         
    </script>
    </head>
    <body>
     <s:form name="ComparisonwiseDashboard" method="POST"
		action="rptPOSWiseSalesReport.html?saddr=${urlHits}"
		target="_blank">
	  <table class="masterTable"  style=" margin: auto; float:left; width:100%;height:50px;">
               
               <tr style="background-color: #ffffff;">
               
                  <td colspan="4" align="center" style="padding-left: 5px;padding-bottom:3px; font-size: 15px "> <label>COMPARISON WISE DASHBOARD</label>
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
		
		<div id="container3" style=" width: 100%; height: 420px; margin: auto;float:left;  overflow-x: hidden; border-collapse: separate; background-color: #ffffff; ">
		</div>
		
		
		
		</div>	
  
       
	</s:form>	
    </body>
</html> 
