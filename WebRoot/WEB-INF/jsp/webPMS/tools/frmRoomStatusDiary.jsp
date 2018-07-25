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
	
	var fieldName;
	
	$(function() 
	{
		$( tblRoomInfo ).tooltip();
		
		var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
		
		$("#txtViewDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtViewDate").datepicker('setDate', pmsDate);
		
		//funFillHeaderRows();
	});
	
	
//Delete a All record from a grid
	function funRemoveHeaderTableRows()
	{
		var table = document.getElementById("tblDays");
		var rowCount = table.rows.length;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	
	
	
	function funRemoveDetailTableRows()
	{
		var table = document.getElementById("tblRoomInfo");
		var rowCount = table.rows.length;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	
	
	
	function funShowRoomStatusFlash()
	{
		var viewDate=$("#txtViewDate").val();
			
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/getRoomStatusList.html?viewDate=" + viewDate,
			dataType : "json",
			success : function(response){ 
				funRemoveHeaderTableRows();
				funFillHeaderRows(response);
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
	
	
	function funShowRoomStatusDtl()
	{
		var viewDate=$("#txtViewDate").val();
			
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/getRoomStatusDtlList.html?viewDate=" + viewDate,
			dataType : "json",
			success : function(response){
				funRemoveDetailTableRows();
				
				$.each(response, function(i,item)
				{
					funFillRoomStatusRows(item.strRoomNo,item.strDay1,item.strDay2,item.strDay3,item.strDay4,item.strDay5,item.strDay6,item.strDay7,item.strRoomStatus,item);
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
	
		
	function funFillHeaderRows(obj)
	{
		var table=document.getElementById("tblDays");
		var rowCount=table.rows.length;
		var row=table.insertRow();

		row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='Room No' >";
		row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+obj[0]+"' >";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+obj[1]+"' >";
		row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+obj[2]+"' >";
		row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+obj[3]+"' >";
		row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+obj[4]+"' >";
		row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+obj[5]+"' >";
		row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+obj[6]+"' >";
		
		/*
		row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+obj.strRoomNo+"' >";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+obj.strRegistrationNo+"' >";
		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+obj.strFolioNo+"' >";
		row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+obj.strGuestName+"' >";
		row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+obj.dblAmount+"' >";
		row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+obj.dteCheckInDate+"' >";
		row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+obj.dteCheckOutDate+"' >";
		row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+obj.strCorporate+"' >";
		*/
		funShowRoomStatusDtl();
	}
	
	
	
	function funFillRoomStatusRows(roomNo,day1,day2,day3,day4,day5,day6,day7,roomStatus,response)
	{
		var table=document.getElementById("tblRoomInfo");
		var rowCount=table.rows.length;
		var row=table.insertRow();
		var color='';
		var toolTipText1="",toolTipText2="",toolTipText3="",toolTipText4="",toolTipText5="",toolTipText6="",toolTipText7="";
		if(roomStatus=='Waiting')
		{
			color='Yellow';
		}
		else if(roomStatus=='Confirmed')
		{
			color='Green';
		}
		else if(roomStatus=='Occupied')
		{
			color='Red';
		}
		else if(roomStatus=='Checked Out')
		{
			color='Gray';
		}
		/*if(response.strReservationNo!=null)
		{
			//toolTipText=response.strReservationNo;
			$.each(response.mapGuestListPerDay, function(i,item)
			 {
				$.each(item, function(j,item1)
				 {
					toolTipText=item1.strRoomNo+"\n"+item1.dteArrivalDate+"\n"+item1.dteDepartureDate;
				 });
			 });
		}
		*/
		if(day1==null)
		{
			day1='';
		}
		else
		{
			if(response.strReservationNo!=null)
			{
				//day1+='               ,'+response.strReservationNo+','+response.strCheckInNo;
				
				$.each(response.mapGuestListPerDay, function(i,item)
				 {
					if(i==0)
					{
						$.each(item, function(j,item1)
						{
					      day1+='               ,'+item1.strRoomNo+','+response.strCheckInNo;
					      //alert(item[i].strRoomNo);
					      toolTipText1+="\n"+item1.strGuestName+"\n"+item1.strRoomNo+"\n"+item1.dteArrivalDate+"\n"+item1.dteDepartureDate;
						});
					}
				 });
			}
		}
		
		if(day2==null)
		{
			day2='';
		}
		else
		{
			if(response.strReservationNo!=null)
			{
				//day2+='               ,'+response.strReservationNo+','+response.strCheckInNo;
				$.each(response.mapGuestListPerDay, function(i,item)
				 {
					if(i==1)
					{
						$.each(item, function(j,item1)
						{
					      day1+='               ,'+item1.strRoomNo+','+response.strCheckInNo;
					      //alert(item[i].strRoomNo);
					      toolTipText2+="\n"+item1.strGuestName+"\n"+item1.strRoomNo+"\n"+item1.dteArrivalDate+"\n"+item1.dteDepartureDate;
						});
					}
					
				 });
	        }
		}
		
		if(day3==null)
		{
			day3='';
		}
		else
		{
			if(response.strReservationNo!=null)
			{
				//day3+='               ,'+response.strReservationNo+','+response.strCheckInNo;
				$.each(response.mapGuestListPerDay, function(i,item)
				 {
					if(i==2)
					{
						$.each(item, function(j,item1)
						{
					      day3+='               ,'+item1.strRoomNo+','+response.strCheckInNo;
					      //alert(item[i].strRoomNo);
					      toolTipText3+="\n"+item1.strGuestName+"\n"+item1.strRoomNo+"\n"+item1.dteArrivalDate+"\n"+item1.dteDepartureDate;
						});
					}
					
				 });
			}
		}
		
		if(day4==null)
		{
			day4='';
		}
		else
		{
			if(response.strReservationNo!=null)
			{
				//day4+='               ,'+response.strReservationNo+','+response.strCheckInNo;
				$.each(response.mapGuestListPerDay, function(i,item)
				 {
					if(i==3)
					{
						$.each(item, function(j,item1)
						{
					      day4+='               ,'+item1.strRoomNo+','+response.strCheckInNo;
					      //alert(item[i].strRoomNo);
					      toolTipText4+="\n"+item1.strGuestName+"\n"+item1.strRoomNo+"\n"+item1.dteArrivalDate+"\n"+item1.dteDepartureDate;
						});
					}
				 });
			}
		}
		
		if(day5==null)
		{
			day5='';
		}
		else
		{
			if(response.strReservationNo!=null)
			{
				//day5+='               ,'+response.strReservationNo+','+response.strCheckInNo;
				$.each(response.mapGuestListPerDay, function(i,item)
				 {
					if(i==4)
					{
						$.each(item, function(j,item1)
						{
					      day5+='               ,'+item1.strRoomNo+','+response.strCheckInNo;
					      //alert(item[i].strRoomNo);
					      toolTipText5+="\n"+item1.strGuestName+"\n"+item1.strRoomNo+"\n"+item1.dteArrivalDate+"\n"+item1.dteDepartureDate;
						});	
					}
				 });
			}
		}
		
		if(day6==null)
		{
			day6='';
		}
		else
		{
			if(response.strReservationNo!=null)
			{
				//day6+='               ,'+response.strReservationNo+','+response.strCheckInNo;
				$.each(response.mapGuestListPerDay, function(i,item)
				 {
					if(i==5)
					{
						$.each(item, function(j,item1)
						{
					      day6+='               ,'+item1.strRoomNo+','+response.strCheckInNo;
					      //alert(item[i].strRoomNo);
					      toolTipText6+="\n"+item1.strGuestName+"\n"+item1.strRoomNo+"\n"+item1.dteArrivalDate+"\n"+item1.dteDepartureDate;
						});
					}
					
				 });
			}
		}
		
		if(day7==null)
		{
			day7='';
		}
		else
		{
			if(response.strReservationNo!=null)
			{
				//day7+='               ,'+response.strReservationNo+','+response.strCheckInNo;
				$.each(response.mapGuestListPerDay, function(i,item)
				 {
					if(i==6)
					{
						$.each(item, function(j,item1)
						{
					      day7+='               ,'+item1.strRoomNo+','+response.strCheckInNo;
					      //alert(item[i].strRoomNo);
					      toolTipText7+="\n"+item1.strGuestName+"\n"+item1.strRoomNo+"\n"+item1.dteArrivalDate+"\n"+item1.dteDepartureDate;
						});
					}
				 });
			}
		}
				
		row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+roomNo+"' >";

		
		/*if(response.dteArrivalDate!=null)
		{
			toolTipText+="\n"+response.dteArrivalDate;
		}	
		if(response.dteDepartureDate!=null)
		{
			toolTipText+="\n"+response.dteDepartureDate;
		}	
		*/
		
		var x1=row.insertCell(1);
		x1.innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+day1+"' onClick='funOnClick(this)' >";
		if(day1!='')
		{
			x1.bgColor=color;
			x1.title=toolTipText1;
		}
		
		var x2=row.insertCell(2);
		x2.innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+day2+"' onClick='funOnClick(this)' >";
		if(day2!='')
		{
			x2.bgColor=color;
			x2.title=toolTipText2;
		}
		
		var x3=row.insertCell(3);
		x3.innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+day3+"' onClick='funOnClick(this)' >";
		if(day3!='')
		{
			x3.bgColor=color;
			x3.title=toolTipText3;
		}
		
		var x4=row.insertCell(4);
		x4.innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+day4+"' onClick='funOnClick(this)' >";
		if(day4!='')
		{
			x4.bgColor=color;
			x4.title=toolTipText4;
		}
		
		var x5=row.insertCell(5);
		x5.innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+day5+"' onClick='funOnClick(this)' >";
		if(day5!='')
		{
			x5.bgColor=color;
			x5.title=toolTipText5;
		}
		
		var x6=row.insertCell(6);
		x6.innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+day6+"' onClick='funOnClick(this)' >";
		if(day6!='')
		{
			x6.bgColor=color;
			x6.title=toolTipText6;
		}
		
		var x7=row.insertCell(7);
		x7.innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width: 100%;\" value='"+day7+"' onClick='funOnClick(this)' >";
		if(day7!='')
		{
			x7.bgColor=color;
			x7.title=toolTipText7;
		}
	}
	
	
	function funOnClick(obj)
	{
		/* var resNo=obj.value;
		resNo=resNo.split(',')[1].trim();
		*/
			
		var color=$(obj).parent().css("backgroundColor");	
		var url='';
		switch(color)
		{
			case 'rgb(255, 0, 0)'://RED-->CHECKED-IN-->OCCUPIED
									  code=obj.value;
									  code=code.split(',')[2].trim();
									  url=getContextPath()+"/frmCheckIn1.html?docCode="+code
									  window.open(url);
			
		   						  break;
			case 'rgb(128, 128, 128)'://GREY-->CHECKED-OUT
									  
				
									  break;
									  
									  
			case 'rgb(255, 255, 0)'://YELLOW-->WAITING
				  code=obj.value;
				  code=code.split(',')[1].trim();
				  url=getContextPath()+"/frmReservation1.html?docCode="+code
				  window.open(url);

				  break;
				  
				  
			case 'rgb(0, 128, 0)'://GREEN-->CONFIRM
				  code=obj.value;
				  code=code.split(',')[1].trim();
				  url=getContextPath()+"/frmReservation1.html?docCode="+code
				  window.open(url);

				  break;	  
									  
		}					
	}
		
	function funSetData(code)
	{
		switch (fieldName)
		{
			
		}
	}
	
	
	
	function funHelp(transactionName)
	{
		fieldName = transactionName;
		window.showModalDialog("searchform.html?formname=" + transactionName + "&searchText=", "","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
</script>


</head>
<body>

	<div id="formHeading">
		<label>Room Status Diary</label>
	</div>

	<br />
	<br />

	<s:form name="RoomStatusDiary" method="POST" action="saveRoomMaster.html">
	
		<div>
			<table class="transTable">
				<tr>
					<td><s:input colspan="1" type="text" id="txtViewDate" path="dteViewDate" cssClass="calenderTextBox" /></td>
				</tr>
			</table>

		
			<table id="tblDays" class="transTable">
				<!-- 
				<tr>
					<td width="70px">Room No</td>
					<td width="100px">Mon  4 Apr 2016</td>
					<td width="100px">Tue  5 Apr 2016</td>
					<td width="100px">Wed  6 Apr 2016</td>
					<td width="100px">Thur 7 Apr 2016</td>
					<td width="100px">Fri  8 Apr 2016</td>
					<td width="100px">Sat  9 Apr 2016</td>
					<td width="100px">Sun  10 Apr 2016</td>
				</tr>
				 -->
			</table>
			
			<br>
			
			<table id="tblRoomInfo" class="transTable">
			
			<!-- 
				<tr>
					<td width="70px">1</td>
					<td width="100px" bgcolor="red" bordercolor="black"><label id="lblCol2">Prashant R Ingale</label></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
				</tr>
				
				<tr>
					<td width="70px">2</td>
					<td width="100px"></td>
					<td width="100px" bgcolor="green"><label id="lblCol3">DDDD WWW RRRR</label></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
				</tr>
				
				<tr>
					<td width="70px">3</td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
				</tr>
				
				<tr>
					<td width="50px">4</td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
				</tr>
				
				<tr>
					<td width="50px">5</td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
				</tr>
				
				 -->
				
			</table>
			
		</div>
		
		<br><br>
		<div>
			<table >
				<tr>
					<td bgcolor="Red">Occupied</td>
					<td></td>
					<td bgcolor="Yellow">Waiting</td>
					<td></td>
					<td bgcolor="Green">Confirmed</td>
					<td></td>
					<td bgcolor="Gray">Checked Out</td>
					<td></td>
				</tr>
			</table>
		</div>
		
		
		<br><br><br><br><br><br>
		
		
		
		<p align="center">
			<input type="button" value="View" tabindex="3" class="form_button" onclick="funShowRoomStatusFlash();"/>
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
		<br><br>

	</s:form>
</body>
</html>
