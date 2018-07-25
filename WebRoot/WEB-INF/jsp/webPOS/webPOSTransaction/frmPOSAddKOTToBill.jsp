<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

<style type="text/css">
.ui-tooltip 
{   
   white-space: pre-wrap;      
}
</style>
<script type="text/javascript">
	
	var fieldName,prevBillRow,prevBillCell,nxtBillRow,nxtBillCell;
	
	
//Load bill and kot data

  function funLoadData()
  {
	  funLoadKOT();
	  funLoadBill();
  }
	
	
//Delete a All record from a grid
	function funRemoveHeaderTableRows(tableName)
	{
		var table = document.getElementById(tableName);
		var rowCount = table.rows.length;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	
	
	
	
	function funLoadKOT()
	{
	  var tableName=$("#cmbTable").val();	
	   var searchurl=getContextPath()+"/LoadADDKOTTableData.html?TableName="+tableName;
		$.ajax({
			type : "GET",
			url : searchurl,
			dataType : "json",
			success : function(response){ 
				funRemoveHeaderTableRows("tblKOT");
				var k=0,first="",second="",third="",fourth="";
				var list=response.listKOTDtl,cnt=0;
				$.each(response.listKOTDtl,function(i,item)
				{
					cnt++;
				    if(k==0)
					   {
						  first=item.strKOTNo;
						  k=k+1;
						  if(cnt==list.length)
						  {
							  funFillHeaderRows(first,second,third,fourth,"tblKOT");
						  }
					   }
					  else if(k==1)
					   {
						  second=item.strKOTNo;
						  k=k+1;
						  if(cnt==list.length)
						  {
							  funFillHeaderRows(first,second,third,fourth,"tblKOT");
						  }
					   }
					  else if(k==2)
					   {
						  third=item.strKOTNo;
						  k=k+1;
						  if(cnt==list.length)
						  {
							  funFillHeaderRows(first,second,third,fourth,"tblKOT");
						  }
					   }
					  else if(k==3)
					   {
						  fourth=item.strKOTNo;
						  funFillHeaderRows(first,second,third,fourth,"tblKOT");
						  k=0;
					   }
				   
				 
				 });
				
				
							
				
			},
			error : function(e){
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
	
	
	
	
	function funLoadBill()
	{
	  var searchurl=getContextPath()+"/LoadUnsettleBill.html";
		$.ajax({
			type : "GET",
			url : searchurl,
			dataType : "json",
			success : function(response){ 
				funRemoveHeaderTableRows("tblBill");
				var k=0,first="",second="",third="",fourth="";
				var list=response.listBillDtl,cnt=0;
				$.each(response.listBillDtl,function(i,item)
				{
					cnt++;
				    if(k==0)
					   {
						  first=item.strBillNo;
						  k=k+1;
						  if(cnt==list.length)
						  {
							  funFillHeaderRows(first,second,third,fourth,"tblBill");
						  }
					   }
					  else if(k==1)
					   {
						  second=item.strBillNo;
						  k=k+1;
						  if(cnt==list.length)
						  {
							  funFillHeaderRows(first,second,third,fourth,"tblBill");
						  }
					   }
					  else if(k==2)
					   {
						  third=item.strBillNo;
						  k=k+1;
						  if(cnt==list.length)
						  {
							  funFillHeaderRows(first,second,third,fourth,"tblBill");
						  }
					   }
					  else if(k==3)
					   {
						  fourth=item.strBillNo;
						  funFillHeaderRows(first,second,third,fourth,"tblBill");
						  k=0;
					   }
				   
				 
				 });
				
				
							
				
			},
			error : function(e){
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

	
	function funFillHeaderRows(obj,obj1,obj2,obj3,tableName)
	{
		var table=document.getElementById(tableName);
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		
		//var rowCount=table.rows.length;
		if(obj==(""))
	    {
	    	
	    }
	    else if(obj1==(""))
	    {   var row=table.insertRow();
	    	row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_button\"  value='"+obj+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	row.insertCell(1).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
	    	row.insertCell(2).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
	    	row.insertCell(3).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
	    }
	    else if(obj2==(""))
	    {   var row=table.insertRow();
	    	row.insertCell(0).innerHTML= "<input type=\"button\" class=\"transForm_button\"  value='"+obj+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	row.insertCell(1).innerHTML= "<input type=\"button\" class=\"transForm_button\"  value='"+obj1+"'  onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	row.insertCell(2).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
	    	row.insertCell(3).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
	    }
	    else if(obj3==(""))
	    {   var row=table.insertRow();
	    	row.insertCell(0).innerHTML= "<input type=\"button\" class=\"transForm_button\"  value='"+obj+"'  onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	row.insertCell(1).innerHTML= "<input type=\"button\" class=\"transForm_button\"  value='"+obj1+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	row.insertCell(2).innerHTML= "<input type=\"button\" class=\"transForm_button\"  value='"+obj2+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	row.insertCell(3).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
	    }
	    else 
	    {
	    	var row=table.insertRow();
	    	row.insertCell(0).innerHTML= "<input type=\"button\" class=\"transForm_button\"  value='"+obj+"'  onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	row.insertCell(1).innerHTML= "<input type=\"button\" class=\"transForm_button\"  value='"+obj1+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	row.insertCell(2).innerHTML= "<input type=\"button\" class=\"transForm_button\"  value='"+obj2+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	row.insertCell(3).innerHTML= "<input type=\"button\" class=\"transForm_button\"  value='"+obj3+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    }
		//selectedTableName=tableName;
        
	}
	
	
	function funGetSelectedRowIndex(obj,tableName)
	{
		 
		 var index = obj.parentNode.parentNode.rowIndex;
		 var cellIndex=obj.parentNode.cellIndex;
		 var tblname=tableName.rows[index].cells[cellIndex].innerHTML;
		 var btnClassName=tblname.split('class="');
		 var btnBackground=btnClassName[1].split('value=');
		 var btnType=btnBackground[0].substring(0, (btnBackground[0].length-2));
		 var data=btnBackground[1].split('onclick=');
		 var code=data[0].substring(1, (data[0].length-2));
		 var selectedTableName=data[1].split('this,');
         var table=selectedTableName[1].substring(0, (selectedTableName[1].length-3));
		 var row = tableName.rows[index]
		 var rowCount = tableName.rows.length;
		 if(table=="tblKOT")
		 {  
			 row.deleteCell(cellIndex);
			 if(btnType!="transForm_button")
			 {
				 row.insertCell(cellIndex).innerHTML= "<input type=\"button\" class=\"transForm_button\"  value='"+code+"' onclick=\"funGetSelectedRowIndex(this,"+table+")\"/>";
			 }
			 else
			 {
				 row.insertCell(cellIndex).innerHTML= "<input  name=\"listKOTDtl["+0+"].strKOTNo\"  readonly=\"readonly\" class=\"transForm_bluebutton\" id=\"btnKot."+0+"\" value='"+code+"' onclick=\"funGetSelectedRowIndex(this,"+table+")\"/>";
			 }
		  }
		 else
		  {
			 if(prevBillRow==null)
		 	  {
				 prevBillRow=index;
			  }
			 else
			  {
				 prevBillRow=nxtBillRow;
			  }	 
			 if(prevBillCell==null)
		 	  {
				 prevBillCell=cellIndex;
			  }
			 else
			 {
				 prevBillCell=nxtBillCell;
			 }
			 if(prevBillRow==index && prevBillCell==cellIndex)
			  {
				 if(btnType!="transForm_button")
				 {   
				 }
				 else
				 {
					 row.deleteCell(cellIndex);
					 row.insertCell(cellIndex).innerHTML= "<input  name=\"listBillDtl["+0+"].strBillNo\"  readonly=\"readonly\" class=\"transForm_graybutton\" id=\"btnBill."+0+"\" value='"+code+"' onclick=\"funGetSelectedRowIndex(this,"+table+")\"/>";
					 nxtBillRow=index;
					 nxtBillCell=cellIndex;
				 }
			  }
			 else
			  {
				 var secondtblname=tableName.rows[prevBillRow].cells[prevBillCell].innerHTML;
				 var secondbtnClassName=secondtblname.split('class="');
				 var secondbtnBackground=secondbtnClassName[1].split('value=');
				 var data1=secondbtnBackground[1].split('onclick=');
				 var code1=data1[0].substring(1, (data1[0].length-2));
				 var row1 = tableName.rows[prevBillRow];
				 row1.deleteCell(prevBillCell);
				 row1.insertCell(prevBillCell).innerHTML= "<input type=\"button\" class=\"transForm_button\"  value='"+code+"' onclick=\"funGetSelectedRowIndex(this,"+table+")\"/>";
				 
				 if(btnType!="transForm_button")
					 
				 {
					
				 }
				 else
				 {
					 row.deleteCell(cellIndex);
					 row.insertCell(cellIndex).innerHTML= "<input  name=\"listBillDtl["+0+"].strBillNo\"  readonly=\"readonly\" class=\"transForm_graybutton\" id=\"btnBill."+0+"\" value='"+code+"' onclick=\"funGetSelectedRowIndex(this,"+table+")\"/>";
					 nxtBillRow=index;
					 nxtBillCell=cellIndex;
				 }
			  }	 
     }

  }
	
	function funHelp(transactionName)
	{
		fieldName = transactionName;
		window.showModalDialog("searchform.html?formname=" + transactionName + "&searchText=", "","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	//Combo Box Change then set value
	function funOnChange() 
	{
		funLoadKOT();
		
	}
	
	
</script>


</head>
<body onload="funLoadData()">

	<div id="formHeading">
		<label>Add KOT To Bill</label>
	</div>

	<br />
	<br />

	<s:form name="Add KOT To Bill" method="POST" action="saveAddKOTToBill.html?saddr=${urlHits}">
	
		<div>
		   <div style=" width: 50%; height: 450px;float:left;  overflow-x: hidden; border-collapse: separate; overflow-y: scroll; background-color: #C0E2FE; ">
			<table class="transFormTable">
			    <tr>
			    
					 <td>
					 <br>
					    <label>OPEN KOT</label>
					     &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
				        <label>Table Name:</label>
				        <s:select id="cmbTable" items="${tableList}" 
						onchange="funOnChange();" name="cmbTable"  cssClass="BoxW124px" path="strTableName">
						</s:select>
						<br>
						<br>
					</td>
				</tr>
				
			</table>
			<table id="tblKOT" class="transFormTable">
			</table>
			
		   </div>
		   
		   <div style=" width: 50%; height: 450px; float:right; border-collapse: separate; overflow-x: hidden; overflow-y: scroll; background-color: #C0E2FE;">
		    <table class="transFormTable">
			  <tr>
					 <td>
					 <br>
					 <label>Bill No</label>
				        <br />
				        <br />
	                </td>
			</tr>
			</table>
		    <table id="tblBill" class="transFormTable">
			</table>
			</div>
			
		</div>
		
			<div>
			<div >
			<table id="tblKOT3" class="transFormTable">
			</table>
			
		   </div>
		   
			
		</div>
		
		<br><br>
		
		
		
		<br><br><br><br><br><br>
		
		
		
		<p align="center">
			<input type="submit" value="Save" tabindex="3" class="form_button" onclick="return funValidateFields();"/>
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
		<br><br>

	</s:form>
</body>
</html>
