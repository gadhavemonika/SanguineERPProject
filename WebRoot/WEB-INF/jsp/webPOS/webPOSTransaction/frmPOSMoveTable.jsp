<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Move Table</title>
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
</style>
<script type="text/javascript">

var prevRow,prevCell,nxtRow,nxtCell,prevRow1,prevCell1,nxtRow1,nxtCell1,cellStatus="Normal",movedFromTable="",movedToTable="";
//Load bill and kot data


	/**
		* Success Message After Saving Record
		**/
		$(document).ready(function()
		{
			var message='';
			<%if (session.getAttribute("success") != null) 
			{
				if(session.getAttribute("successMessage") != null)
				{%>
					message='<%=session.getAttribute("successMessage").toString()%>';
				    <%
				    session.removeAttribute("successMessage");
				}
				boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
				session.removeAttribute("success");
				if (test) 
				{
					%>alert("Table Moved... \n\n"+message);<%
				}
			}%>
		});

function funLoadData()
{
	  funLoadTable("tblOccupiedTable","Occupied");
	  funLoadTable("tblAllTable","All");
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
	
	
	
	function funLoadTable(tableName,status)
	{
	  var searchurl=getContextPath()+"/LoadMoveTableData.html?TableStatus="+status;
		$.ajax({
			type : "GET",
			url : searchurl,
			dataType : "json",
			success : function(response){ 
				funRemoveHeaderTableRows(tableName);
				var k=0,first="",second="",third="",fourth="";
				var cnt=0,list;
				if(status=='All')
				{
					list=response.listOfAllTable;
				}
				else
				{
					list=response.listOfOccupiedTable;
				}
				
				$.each(list,function(i,item)
				{
					cnt++;
				    if(k==0)
					   {
						  first=item.strTableName+"#"+item.strStatus;
						  k=k+1;
						  if(cnt==list.length)
						  {
							  funFillHeaderRows(first,second,third,fourth,tableName);
						  }
					   }
					  else if(k==1)
					   {
						  second=item.strTableName+"#"+item.strStatus;
						  k=k+1;
						  if(cnt==list.length)
						  {
							  funFillHeaderRows(first,second,third,fourth,tableName);
						  }
					   }
					  else if(k==2)
					   {
						  third=item.strTableName+"#"+item.strStatus;
						  k=k+1;
						  if(cnt==list.length)
						  {
							  funFillHeaderRows(first,second,third,fourth,tableName);
						  }
					   }
					  else if(k==3)
					   {
						  fourth=item.strTableName+"#"+item.strStatus;
						  funFillHeaderRows(first,second,third,fourth,tableName);
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
		var tbl1Data=obj.split('#');
		var tbl2Data=obj1.split('#');
		var tbl3Data=obj2.split('#');
		var tbl4Data=obj3.split('#');
        if(tableName=="tblOccupiedTable")
        {
        	if(obj==(""))
    	    {
    	    	
    	    }
    	    else if(obj1==(""))
    	    {   var row=table.insertRow();
    	    	row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_redbutton\"  value='"+tbl1Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
    	    	row.insertCell(1).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
    	    	row.insertCell(2).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
    	    	row.insertCell(3).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
    	    }
    	    else if(obj2==(""))
    	    {   var row=table.insertRow();
    	    	row.insertCell(0).innerHTML= "<input type=\"button\" class=\"transForm_redbutton\"  value='"+tbl1Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
    	    	row.insertCell(1).innerHTML= "<input type=\"button\" class=\"transForm_redbutton\"  value='"+tbl2Data[0]+"'  onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
    	    	row.insertCell(2).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
    	    	row.insertCell(3).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
    	    }
    	    else if(obj3==(""))
    	    {   var row=table.insertRow();
    	    	row.insertCell(0).innerHTML= "<input type=\"button\" class=\"transForm_redbutton\"  value='"+tbl1Data[0]+"'  onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
    	    	row.insertCell(1).innerHTML= "<input type=\"button\" class=\"transForm_redbutton\"  value='"+tbl2Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
    	    	row.insertCell(2).innerHTML= "<input type=\"button\" class=\"transForm_redbutton\"  value='"+tbl3Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
    	    	row.insertCell(3).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
    	    }
    	    else 
    	    {
    	    	var row=table.insertRow();
    	    	row.insertCell(0).innerHTML= "<input type=\"button\" class=\"transForm_redbutton\"  value='"+tbl1Data[0]+"'  onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
    	    	row.insertCell(1).innerHTML= "<input type=\"button\" class=\"transForm_redbutton\"  value='"+tbl2Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
    	    	row.insertCell(2).innerHTML= "<input type=\"button\" class=\"transForm_redbutton\"  value='"+tbl3Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
    	    	row.insertCell(3).innerHTML= "<input type=\"button\" class=\"transForm_redbutton\"  value='"+tbl4Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
    	    }
        }
        else
        {
        	if(obj==(""))
    	    {
    	    	
    	    }
    	    else if(obj1==(""))
    	    {   var row=table.insertRow();
	    	    if(tbl1Data[1]=="Occupied")
		        {
	    	    	row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_redbutton\"  value='"+tbl1Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	    }
	    	    else
    	    	{
    	    	 row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_button\"  value='"+tbl1Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
    	    	}
	    	    row.insertCell(1).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
    	    	row.insertCell(2).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
    	    	row.insertCell(3).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
    	    }
    	    else if(obj2==(""))
    	    {   var row=table.insertRow();
	    	    if(tbl1Data[1]=="Occupied")
		        {
	    	    	row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_redbutton\"  value='"+tbl1Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	    }
	    	    else
		    	{
		    	 row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_button\"  value='"+tbl1Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
		    	}
	    	    if(tbl2Data[1]=="Occupied")
		        {
	    	    	row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_redbutton\"  value='"+tbl2Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	    }
	    	    else
		    	{
		    	 row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_button\"  value='"+tbl2Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
		    	}
	    	    row.insertCell(2).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
    	    	row.insertCell(3).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
    	    }
    	    else if(obj3==(""))
    	    {   var row=table.insertRow();
	    	    if(tbl1Data[1]=="Occupied")
		        {
	    	    	row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_redbutton\"  value='"+tbl1Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	    }
	    	    else
		    	{
		    	 row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_button\"  value='"+tbl1Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
		    	}
	    	    if(tbl2Data[1]=="Occupied")
		        {
	    	    	row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_redbutton\"  value='"+tbl2Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	    }
	    	    else
		    	{
		    	 row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_button\"  value='"+tbl2Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
		    	}
	    	    if(tbl3Data[1]=="Occupied")
		        {
	    	    	row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_redbutton\"  value='"+tbl3Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	    }
	    	    else
		    	{
		    	 row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_button\"  value='"+tbl3Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
		    	}
    	    	row.insertCell(3).innerHTML= "<input text=\"readonly\" class=\"Box \"   value='' >";
    	    }
    	    else 
    	    {
    	    	var row=table.insertRow();
    	    	if(tbl1Data[1]=="Occupied")
    	    	{
	    	    	row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_redbutton\"  value='"+tbl1Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	    }
	    	    else
		    	{
		    	 row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_button\"  value='"+tbl1Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
		    	}
	    	    if(tbl2Data[1]=="Occupied")
		        {
	    	    	row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_redbutton\"  value='"+tbl2Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	    }
	    	    else
		    	{
		    	 row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_button\"  value='"+tbl2Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
		    	}
	    	    if(tbl3Data[1]=="Occupied")
		        {
	    	    	row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_redbutton\"  value='"+tbl3Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	    }
	    	    else
		    	{
		    	 row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_button\"  value='"+tbl3Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
		    	}
	    	    if(tbl4Data[1]=="Occupied")
		        {
	    	    	row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_redbutton\"  value='"+tbl4Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
	    	    }
	    	    else
		    	{
		    	 row.insertCell(0).innerHTML= "<input type=\"button\"  class=\"transForm_button\"  value='"+tbl4Data[0]+"' onclick=\"funGetSelectedRowIndex(this,"+tableName+")\" >";
		    	}
    	    }
        }	
      
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
		 
		 if(table=="tblOccupiedTable")
		 {
			 if(prevRow==null)
		 	  {
				 prevRow=index;
			  }
			 else
			  {
				 prevRow=nxtRow;
			  }	 
			 if(prevCell==null)
		 	  {
				 prevCell=cellIndex;
			  }
			 else
			 {
				 prevCell=nxtCell;
			 }
			 
			 if(prevRow==index && prevCell==cellIndex)
			  {
				 if(btnType!="transForm_redbutton")
				 {
					// row.insertCell(cellIndex).innerHTML= "<input type=\"button\" class=\"transForm_redbutton\"  value='"+code+"' onclick=\"funGetSelectedRowIndex(this,"+table+")\"/>";
				 }
				 else
				 {
					 row.deleteCell(cellIndex);
					 row.insertCell(cellIndex).innerHTML= "<input  name=\"listOfOccupiedTable["+0+"].strTableName\"  readonly=\"readonly\" class=\"transForm_graybutton\" id=\"btnKot."+0+"\" value='"+code+"' onclick=\"funGetSelectedRowIndex(this,"+table+")\"/>";
					 nxtRow=index;
					 nxtCell=cellIndex;
					 movedFromTable=code;
				 }
			  }
			 else
			  {
				 
				 var secondtblname=tableName.rows[prevRow].cells[prevCell].innerHTML;
				 var secondbtnClassName=secondtblname.split('class="');
				 var secondbtnBackground=secondbtnClassName[1].split('value=');
				 var data1=secondbtnBackground[1].split('onclick=');
				 var code1=data1[0].substring(1, (data1[0].length-2));
				 var row1 = tableName.rows[prevRow];
				 row1.deleteCell(prevCell);
				 row1.insertCell(prevCell).innerHTML= "<input type=\"button\" class=\"transForm_redbutton\"  value='"+code1+"' onclick=\"funGetSelectedRowIndex(this,"+table+")\"/>";
				 
				 if(btnType!="transForm_redbutton")
				 {
					// row.insertCell(cellIndex).innerHTML= "<input type=\"button\" class=\"transForm_redbutton\"  value='"+code+"' onclick=\"funGetSelectedRowIndex(this,"+table+")\"/>";
				 }
				 else
				 {
					 row.deleteCell(cellIndex);
					 row.insertCell(cellIndex).innerHTML= "<input  name=\"listOfOccupiedTable["+0+"].strTableName\"  readonly=\"readonly\" class=\"transForm_graybutton\" id=\"btnKot."+0+"\" value='"+code+"' onclick=\"funGetSelectedRowIndex(this,"+table+")\"/>";
					 nxtRow=index;
					 nxtCell=cellIndex;
					 movedFromTable=code;
				 }
			  }
		 }	
		 else
		 {
			 if(prevRow1==null)
		 	  {
				 prevRow1=index;
			  }
			 else
			  {
				 prevRow1=nxtRow1;
			  }	 
			 if(prevCell1==null)
		 	  {
				 prevCell1=cellIndex;
			  }
			 else
			 {
				 prevCell1=nxtCell1;
			 }
			 
			 
			 if(prevRow1==index && prevCell1==cellIndex)
			  {
				 if(btnType=="transForm_redbutton")
				 {
					 row.deleteCell(cellIndex);
					 row.insertCell(cellIndex).innerHTML= "<input  name=\"listOfAllTable["+0+"].strTableName\"  readonly=\"readonly\" class=\"transForm_graybutton\" id=\"btnBill."+0+"\" value='"+code+"' onclick=\"funGetSelectedRowIndex(this,"+table+")\"/>";
					 nxtRow1=index;
					 nxtCell1=cellIndex;
					 cellStatus="Occupied"+"#"+nxtRow1+"#"+nxtCell1;
					 movedToTable=code;
				 }
				 else
				 {
					 if(btnType!="transForm_button")
					 {  
						// row.insertCell(cellIndex).innerHTML= "<input type=\"button\"  class=\"transForm_button\"  value='"+code+"' onclick=\"funGetSelectedRowIndex(this,"+table+")\"/>";
					 }
					 else
					 {   row.deleteCell(cellIndex);
						 row.insertCell(cellIndex).innerHTML= "<input  name=\"listOfAllTable["+0+"].strTableName\"  readonly=\"readonly\" class=\"transForm_graybutton\" id=\"btnBill."+0+"\" value='"+code+"' onclick=\"funGetSelectedRowIndex(this,"+table+")\"/>";
						 nxtRow1=index;
						 nxtCell1=cellIndex;
						 cellStatus="Normal"+"#"+nxtRow1+"#"+nxtCell1;
						 movedToTable=code;
					 }
				 }	
			  }
			 else
			  {
				 var secondtblname=tableName.rows[prevRow1].cells[prevCell1].innerHTML;
				 var secondbtnClassName=secondtblname.split('class="');
				 var secondbtnBackground=secondbtnClassName[1].split('value=');
				 var data1=secondbtnBackground[1].split('onclick=');
				 var code1=data1[0].substring(1, (data1[0].length-2));
				 var row1 = tableName.rows[prevRow1];
				 var cellData=cellStatus.split('#');
				 row1.deleteCell(prevCell1);
				 if(cellData[0]=="Occupied" && cellData[1]==prevRow1 && cellData[2]==prevCell1)
				  {
					 row1.insertCell(prevCell1).innerHTML= "<input type=\"button\" class=\"transForm_redbutton\"  value='"+code1+"' onclick=\"funGetSelectedRowIndex(this,"+table+")\"/>";
				  }
				 else
				  {
					 row1.insertCell(prevCell1).innerHTML= "<input type=\"button\" class=\"transForm_button\"  value='"+code1+"' onclick=\"funGetSelectedRowIndex(this,"+table+")\"/>";
				  }
				  
				 if(btnType!="transForm_button")
				 {
					// row.insertCell(cellIndex).innerHTML= "<input type=\"button\" class=\"transForm_redbutton\"  value='"+code+"' onclick=\"funGetSelectedRowIndex(this,"+table+")\"/>";
				 }
				 else
				 {
					 row.deleteCell(cellIndex);
					 row.insertCell(cellIndex).innerHTML= "<input  name=\"listOfAllTable["+0+"].strTableName\"  readonly=\"readonly\" class=\"transForm_graybutton\" id=\"btnBill."+0+"\" value='"+code+"' onclick=\"funGetSelectedRowIndex(this,"+table+")\"/>";
					 nxtRow1=index;
					 nxtCell1=cellIndex;
					 movedToTable=code;
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
	
	function funValidateData()
	{
		if(movedFromTable!=movedToTable)
		{
			return true;
		}
		else
		{
			alert("Can not select same table...");
			return false;
		}
	}
	
	



</script>


</head>

<body onload="funLoadData()">
	<div id="formHeading">
		<label>Move Table</label>
	</div>
	<br />
	<br />
	<br />
	<br />
	  <s:form name="Move Table" method="POST" action="saveMoveTable.html?saddr=${urlHits}">
	     <div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; ">
	     <br />
	     <div style=" width: 50%; height: 450px;float:left;  overflow-x: hidden; border-collapse: separate; overflow-y: scroll; background-color: #C0E2FE; ">
			  <table class="transFormTable">
			  <tr>
					 <td>
					    <label>OPEN Tables</label>
				        <br />
	                    <br />
				    </td>
				   
				</tr>
			</table>
			 <table id="tblOccupiedTable" class="transFormTable">
			 </table>
			 </div>
			 
		   
		     <div style=" width: 50%; height: 450px; float:right; border-collapse: separate; overflow-x: hidden; overflow-y: scroll; background-color: #C0E2FE;">
		      <table class="transFormTable">
			  <tr>
					 <td>
					    <label>All Tables</label>
				        <br />
	                    <br />
				    </td>
				   
				</tr>
			</table>
		     <table id="tblAllTable" class="transFormTable">
			 </table>
			 </div>
		 </div>
		
		<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; ">
		   
			<div >
			<table id="tblKOT3" class="transFormTable">
			</table>
			</div>
		</div>
		
		<br><br>
		<br><br><br><br><br><br>
		<p align="center">
			<input type="submit" value="Save" tabindex="3" class="form_button" onclick="return funValidateData();"/>
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
		<br><br>
</s:form>
</body>
</html>