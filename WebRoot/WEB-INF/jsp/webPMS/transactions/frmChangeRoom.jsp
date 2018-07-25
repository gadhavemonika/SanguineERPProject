<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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

var selectedOccupiedMap = new Map();
var selectedFreeMap = new Map();
var mapOccupiedToolTipData = new Map();
var mapFreeToolTipData = new Map();
var selectedOccupiedRoom="",selectedFreeRoom="";


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
					%>alert("Room Changed... \n\n"+message);<%
				}
			}%>
			
			funLoadData();
		});

function funLoadData()
{
	    funLoadOccupiedTable("tblOccupiedTable");
	    funLoadAllRooms("tblAllTable");
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
	
	
	
	function funLoadOccupiedTable(tableName)
	{
	 var searchurl=getContextPath()+"/loadOccupiedRoomData.html";	
	 $.ajax({
			type : "GET",
			url : searchurl,
			dataType : "json",
			success : function(response){ 
				funRemoveHeaderTableRows(tableName);
				
				var k=0,first="",second="",third="",fourth="";
				var cnt=0,list,rowPosition=0;
		
				list=response.hmOccupiedRooms;
				
				var table=document.getElementById(tableName);
				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);
				var row=table.insertRow();
				var toolTipText="";
				$.each(list,function(i,item)
				{
					
					cnt++;
					toolTipText=item.FolioNo+"\n"+item.CheckInDate+"\n"+item.GuestName+"\n"+item.Reason+"\n"+item.Remark;
					
// 					else if(item.Type=="Walk In")
// 					{
// 						toolTipText=item.RoomName+"\n"+item.Type+"\n"+item.WalkinNo;
// 					}
// 					else
// 					{
// 						toolTipText=item.RoomName+"\n"+item.Type+"\n"+item.ReservationNo;
// 					}
					if(cnt<=4)
					  {
						var x=row.insertCell(0);
						x.innerHTML="<input type=\"button\" class = \"transForm_redButton\"  value='"+item.RoomName+"'  onclick=\"funOnClick(this,'tblOccupiedTable')\" >";			
						x.title=toolTipText;
						
					  }
					else
					 {
						row=table.insertRow();
						rowPosition++;
						cnt=1;
						var x=row.insertCell(0);
						x.innerHTML= "<input type=\"button\" class = \"transForm_redButton\"   value='"+item.RoomName+"'  onclick=\"funOnClick(this,'tblOccupiedTable')\" >";
						x.title=toolTipText;
					 }
				
					mapOccupiedToolTipData.set(item.RoomName,toolTipText+"!"+item.RoomNo+"#"+item.CheckinNo+"."+item.Status);
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
	
	
	function funLoadAllRooms(tableName)
	{
	 var searchurl=getContextPath()+"/loadAllRoomData.html";	
	 $.ajax({
			type : "GET",
			url : searchurl,
			dataType : "json",
			success : function(response){ 
				funRemoveHeaderTableRows(tableName);
				var cnt=0,list;
				list=response.hmAllRooms;
				var table=document.getElementById(tableName);
				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);
				var row=table.insertRow();
				
				
				$.each(list,function(i,item)
				{
					cnt++;	
					if(cnt<=4)
					  {
						var x=row.insertCell(0);
						x.innerHTML= "<button type=\"button\" class = \"transForm_button\" value='"+item.RoomName+"' onclick=\"funOnClick(this,'tblAllTable')\" >"+item.RoomName+"<br/>"+item.RoomTypeDesc+"</button>";
						x.title=item.RoomName;
					  }
					else
					 {
						row=table.insertRow();
						cnt=1;
						var x=row.insertCell(0);
						x.innerHTML= "<button type=\"button\" class = \"transForm_button\" value='"+item.RoomName+"' onclick=\"funOnClick(this,'tblAllTable')\" >"+item.RoomName+"<br/>"+item.RoomTypeDesc+"</button>";
						x.title=item.RoomName;	
					 }
					mapFreeToolTipData.set(item.RoomName,item.RoomName+"!"+item.RoomNo+"#"+item.RoomTypeDesc
                );
	    	    	
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

	

	
	function funOnClick(obj,tableId)
	{
		var index,cellIndex,tableName,x,prevIndex,prevCellIndex;
		index = obj.parentNode.parentNode.rowIndex;
	    cellIndex=obj.parentNode.cellIndex;
		tableName = document.getElementById(tableId);
		x=tableName.rows[index].cells[cellIndex];
		var invoiceNo="";
		if(tableId=="tblOccupiedTable")
		{
			if(obj.className=="transForm_SelectedBtn")
			{
				x.innerHTML="<input type=\"button\" class = \"transForm_redButton\"    value='"+obj.value+"'  onclick=\"funOnClick(this,'tblOccupiedTable')\" >";	
				x.title=mapOccupiedToolTipData.get(obj.value).split('!')[0];
			}
			else
			{
				 if(selectedOccupiedMap.size>0)
				 {
				    	if(selectedOccupiedMap.has(obj.value))
				    	 {}
				    	else
				    	{
				    	    var value=selectedOccupiedMap.get(1);
				    		var dataArr = value.split('#');
				    		prevIndex=parseInt(dataArr[0]);
				    		prevCellIndex=parseInt(dataArr[1]);
				    		x=tableName.rows[prevIndex].cells[prevCellIndex];
				    		selectedOccupiedMap = new Map();
				    		x.innerHTML="<input type=\"button\" class = \"transForm_redButton\"    value='"+dataArr[2]+"'  onclick=\"funOnClick(this,'tblOccupiedTable')\" >";	
							x.title=mapOccupiedToolTipData.get(dataArr[2]).split('!')[0];
							$("#txtCheckInNo").val(mapOccupiedToolTipData.get(obj.value).split('#')[1]);
							
							x=tableName.rows[index].cells[cellIndex];
							x.innerHTML="<input type=\"button\" class = \"transForm_SelectedBtn\"    value='"+obj.value+"'  onclick=\"funOnClick(this,'tblOccupiedTable')\" >";	
							x.title=mapOccupiedToolTipData.get(obj.value).split('!')[0];
							selectedOccupiedMap.set(1,index+"#"+cellIndex+"#"+obj.value);
							$("#txtCheckInNo").val(mapOccupiedToolTipData.get(obj.value).split('#')[1]);
				    	}	
				 }
				 else
				 {
					 x.innerHTML="<input type=\"button\" class = \"transForm_SelectedBtn\"    value='"+obj.value+"'  onclick=\"funOnClick(this,'tblOccupiedTable')\" >";	
					 x.title=mapOccupiedToolTipData.get(obj.value).split('!')[0];
					$("#txtCheckInNo").val(mapOccupiedToolTipData.get(obj.value).split('#')[1]);
					
					 selectedOccupiedMap.set(1,index+"#"+cellIndex+"#"+obj.value);
				 }	 
			  }	
			var text = mapOccupiedToolTipData.get(obj.value).split('!')[1];
			selectedOccupiedRoom=text;
			
			$("#txtOccupiedRoomCode").val(selectedOccupiedRoom);
		}
		else
		{
			if(obj.className=="transForm_SelectedBtn")
			{
				x.innerHTML= "<button type=\"button\" class = \"transForm_button\" value='"+obj.value+"' onclick=\"funOnClick(this,'tblAllTable')\" >"+obj.value+"<br/>"+mapFreeToolTipData.get(obj.value).split('!')[1].split('#')[1]+"</button>";
				//x.innerHTML="<input type=\"button\" class = \"transForm_button\"    value='"+obj.value+"'  onclick=\"funOnClick(this,'tblAllTable')\" >";	
				x.title=mapFreeToolTipData.get(obj.value).split('!')[0];
			}
			else
			{
				 if(selectedFreeMap.size>0)
				 {
				    	if(selectedFreeMap.has(obj.value))
				    	 {}
				    	else
				    	{
				    	    var value=selectedFreeMap.get(1);
				    		var dataArr = value.split('#');
				    		prevIndex=parseInt(dataArr[0]);
				    		prevCellIndex=parseInt(dataArr[1]);
				    		x=tableName.rows[prevIndex].cells[prevCellIndex];
				    		selectedFreeMap = new Map();
				    		x.innerHTML= "<button type=\"button\" class = \"transForm_button\" value='"+dataArr[2]+"' onclick=\"funOnClick(this,'tblAllTable')\" >"+dataArr[2]+"<br/>"+mapFreeToolTipData.get(dataArr[2]).split('!')[1].split('#')[1]+"</button>";
				    		//x.innerHTML="<input type=\"button\" class = \"transForm_button\"    value='"+dataArr[2]+"'  onclick=\"funOnClick(this,'tblAllTable')\" >";	
							x.title=mapFreeToolTipData.get(dataArr[2]).split('!')[0];
							
							x=tableName.rows[index].cells[cellIndex];
							x.innerHTML= "<button type=\"button\" class = \"transForm_SelectedBtn\" value='"+obj.value+"' onclick=\"funOnClick(this,'tblAllTable')\" >"+obj.value+"<br/>"+mapFreeToolTipData.get(obj.value).split('!')[1].split('#')[1]+"</button>";
							//x.innerHTML="<input type=\"button\" class = \"transForm_SelectedBtn\"    value='"+obj.value+"'  onclick=\"funOnClick(this,'tblAllTable')\" >";	
							x.title=mapFreeToolTipData.get(obj.value).split('!')[0];
							selectedFreeMap.set(1,index+"#"+cellIndex+"#"+obj.value);
				    	}	
				 }
				 else
				 {
					 //x.innerHTML="<input type=\"button\" class = \"transForm_SelectedBtn\"    value='"+obj.value+"'  onclick=\"funOnClick(this,'tblAllTable')\" >";	
					 x.innerHTML= "<button type=\"button\" class = \"transForm_SelectedBtn\" value='"+obj.value+"' onclick=\"funOnClick(this,'tblAllTable')\" >"+obj.value+"<br/>"+mapFreeToolTipData.get(obj.value).split('!')[1].split('#')[1]+"</button>";
					 x.title=mapFreeToolTipData.get(obj.value).split('!')[0];
					 selectedFreeMap.set(1,index+"#"+cellIndex+"#"+obj.value);
				 }	 
			  }	
			selectedFreeRoom=mapFreeToolTipData.get(obj.value).split('!')[1].split('#')[0];
			$("#txtFreeRoomCode").val(selectedFreeRoom);
		}	
					
	 }
	

	function funHelp(transactionName)
	{
		fieldName = transactionName;
		window.open("searchform.html?formname=" + transactionName + "&searchText=", "","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	//Combo Box Change then set value
	function funOnChange() 
	{
		funLoadKOT();
		
	}
	
	function funValidateData(actionName,object)
	{
		var flg=true;
				
		if($("#txtReasonCode").val().trim().length==0)
		{
			alert("Please Select Reason!!");
			 flg=false;
		}
		else
		{
			if($("#txtRemarks").val().trim().length==0)
			{
				alert("Please Enter Remark!!");
				 flg=false;
			}
		}	
		
		
		
		return flg;	
	}
	
	/**
	* Success Message After Saving Record
**/
function funSetData(code)
{
	switch(fieldName)
	{
		case 'reasonPMS' : 
			funSetReasonData(code);
		break;
		
		
	}
}

/**
* Get and Set data from help file and load data Based on Selection Passing Value(Reason Code)
**/

function funSetReasonData(code)
{
	$("#txtReasonCode").val(code);
	var searchurl=getContextPath()+"/loadPMSReasonMasterData.html?reasonCode="+code;
	 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strReasonCode=='Invalid Code')
		        	{
		        		alert("Invalid Reason Code");
		        		$("#txtReasonCode").val('');
		        	}
		        	else
		        	{	
		        		$("#txtReasonCode").val(response.strReasonCode);
		        		$("#lblReasonDesc").text(response.strReasonDesc);
			        	
		        	}
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

function funResetFields()
{
	$("#txtReasonCode").val('');
	$("#lblReasonDesc").text('');
	$("#txtRemarks").val('');
	$("#txtFreeRoomCode").val('');
	$("#txtOccupiedRoomCode").val('');
	$("#txtReasonCode").focus();
	selectedOccupiedMap.clear();
	selectedFreeMap.clear();
	mapOccupiedToolTipData.clear();
	mapFreeToolTipData.clear();
	selectedOccupiedRoom="",selectedFreeRoom="";
}

</script>


</head>

<body onload="funLoadData()">
	<div id="formHeading">
		<label>Change Room</label>
	</div>
	<br />
	
	  <s:form name="Change Room" method="POST" action="changeRoom.html" >
	   <table class="transTable" style="margin-left: 10px;width:94%">
			<tr>
				<td><label>Reason Code</label></td>
				<td>
					<s:input colspan="1" type="text" id="txtReasonCode" path="strReasonCode" cssClass="searchTextBox" ondblclick="funHelp('reasonPMS');"/>
				</td>
				<td><label id="lblReasonDesc"></label></td>
			
				<td>
						<label>Remarks</label>
				</td>
				<td>
				       <s:input type="text" id="txtRemarks" path="strRemarks" cssClass="longTextBox" />
				</td>
			</tr>
		</table>
		<div style="margin-top: 10px;margin-left: 10px;">
			<div
				style="background-color: #C0E2FE;float:left;margin-left: 10px; border: 1px solid #ccc; display: block; height: 400px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 45%;">
				<label>Occupied Rooms</label>
				
				<table id="tblOccupiedTable" class="masterTable" style="margin-left: 0px;width:100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col8-center">

				</table>
			</div>

			<div
				style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 400px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 45%;">

				<label>Free Rooms</label>
	    		
				<table id="tblAllTable" class="masterTable" style="width: 100%;margin-left: 0px; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col8-center"></table>
			</div>
		</div>
			<s:input type="hidden" id="txtOccupiedRoomCode" path="strRoomCode" />
			<s:input type="hidden" id="txtFreeRoomCode" path="strRoomDesc" />
			<s:input type="hidden" id="txtCheckInNo" path="strRoomType"/>
		
		
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		
     	<p align="center">
            		<input type="submit" value="Save"  class="form_button" onclick="return funValidateData('submit',this);"/>
            		<input type="reset" value="Reset"  class="form_button" onclick="funResetFields()"/>
       </p>     		
   		
	     

</s:form>
</body>
</html>