<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

	<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib" %>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="sp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GRN</title>
<script type="text/javascript">
   //Set GRN textField focus when Form is Load
	$(document).ready(function () {
	 
	});
	
   //Set tab which have Active on form loding
	$(document).ready(function()
	{
		$(".tab_content").hide();
		$(".tab_content:first").show();

		$("ul.tabs li").click(function() 
		{
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();
			var activeTab = $(this).attr("data-state");
			$("#" + activeTab).fadeIn();
		});
		
		//Ajax Wait 
	 	$(document).ajaxStart(function()
	 	{
		    $("#wait").css("display","block");
	  	});
	 	
		$(document).ajaxComplete(function()
		{
		    $("#wait").css("display","none");
		});	  
		
		
		var message='';
		var retval="";
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
				%> alert("Data Save successfully\n\n"+message); <%
			}
		}%>
		
		var code='';
		<%
		if(null!=session.getAttribute("rptVoucherNo"))
		{%>
			code='<%=session.getAttribute("rptVoucherNo").toString()%>';
			<%session.removeAttribute("rptVoucherNo");%>
			var isOk=confirm("Do You Want to Generate Slip?");
			if(isOk)
			{
				window.open(getContextPath()+"/openRptReciptReport.html?docCode="+code,'_blank');
			}
					
			
		<%}%>
		
		
	});
	
</script>

	<script type="text/javascript">
	
		
	
		
	</script>
</head>

<body>
<div id="formHeading">
		<label>Receipts</label>
	</div>
	<s:form name="receipts" method="POST" action="saveReceipts.html?saddr=${urlHits}">
	<input id="txtWtUnit" type="hidden" value="0" class="decimal-places numberField" ></input>
		<br>
		
		<table style="border: 0px solid black; width: 100%; height: 100%; margin-left: auto; margin-right: auto; background-color: #C0E4FF;">
			<tr>
				<td>
					<div id="tab_container" style="height:900px">
						<ul class="tabs">
							<li class="active" data-state="tab1"
								style="width: 100px; padding-left: 55px">GENERAL</li>
							<li data-state="tab2" style="width: 100px; padding-left: 55px">Taxes</li>
						</ul>
						<div id="tab1" class="tab_content" style="height: 550px">
							<table class="transTable">
								<tr>
									<td><label>Voucher No</label></td>
									<td><s:input id="txtVoucherNo" path="strVoucherNo"
											ondblclick="funHelp('VoucherNo')" cssClass="searchTextBox" /></td>
									<td><label>Date</label></td>
									<td><s:input id="txtReceiptDate" required="required"
											path="dteReceipt" pattern="\d{1,2}-\d{1,2}-\d{4}"
											cssClass="calenderTextBox" /></td>
									<td>Against</td>
									<td><s:select id="cmbType" items="${listType}"
											onchange="funOnChange();" name="cmbType" cssClass="BoxW124px"
											path="strType">
										</s:select></td>

								</tr>

								<tr>
									<td><label id="lblReciverdFrom">Reciverd From</label></td>
									<td><s:input id="txtRecivedFrom" path="strRecivedFrom"
											cssClass="BoxW116px" /></td>

									<td><label id="lblChequeNo">Cheque No</label></td>
									<td><s:input id="txtChequeNo" path="strChequeNo"
											cssClass="BoxW116px" /></td>

									<td><label>Cheque Date</label></td>
									<td><s:input id="txtChequeDate" path="dteCheque"
											pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox" /></td>



								</tr>
								<tr>
									<td style="height: 61px">Narration</td>
									<td style="height: 61px"><s:textarea id="txtNarration"
											cols="50" rows="3" path="strNarration" style="width: 80%"></s:textarea></td>

									<td><label>Amount</label></td>
									<td><s:input id="txtAmount" path="dblAmount"
											cssClass="BoxW116px" /></td>

								</tr>


							</table>


							<table class="transTableMiddle1">

							</table>

							<div class="dynamicTableContainer" style="height: 290px">
								<table
									style="height: 20px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">

									<tr bgcolor="#72BEFC">
										<td width="4%">Code</td>
										<!--  COl1   -->
										<td width="10%">Head</td>
										<!--  COl2   -->

										<td width="3%">DC</td>
										<!--  COl5  -->
										<td width="3%">CF Cd</td>
										<!--  COl6   -->
										<td width="8%">CF. Name</td>
										<!--  COl7   -->
										<td width="3%">Amount</td>
										<!--  COl8   -->
										<td width="3%">Loc</td>
										<!--  COl9   -->
										<td width="3%">Narration</td>
										<!--  COl10   -->

									</tr>
								</table>
								<div
									style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 238px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">


									<table id="tblProduct"
										style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll;"
										class="transTablex col20-center">
										<tbody>
										<col style="width: 5%">
										<!--  COl1   -->
										<col style="width: 11.5%">
										<!--  COl2   -->


										<col style="width: 4%">
										<!--  COl5   -->
										<col style="width: 3.8%">
										<!--  COl6   -->
										<col style="width: 9.7%">
										<!--  COl7   -->
										<col style="width: 3.5%">
										<!--  COl8   -->
										<col style="width: 4%">
										<!--  COl9   -->
										<col style="width: 3.5%">
										<!--  COl10   -->


										</tbody>
									</table>





								</div>

							</div>
							<table class="transTable">
							
							<tr>
								<td><label>Voucher No</label></td>
								<td><s:input id="txtARVoucherNo" path="strARVoucherNo"
											ondblclick="funHelp('arVoucherNo')" cssClass="searchTextBox" /></td>
								<td><label>Running Total</label></td>
								<td><s:input id="txtRunningTotal" path="dblTotal"
											cssClass="BoxW116px" /></td>			
											
							</tr>
						

							</table>
						</div>

						<div id="tab2" class="tab_content">
								<br>
								<br>
								<table class="masterTable">
									
								</table>
								<br>
								<table style="width: 80%;" class="transTablex col5-center">
									<tr>
										<td style="width:10%">Code</td>
										<td style="width:10%">Head</td>
										<td style="width:10%">Rate</td>
										<td style="width:10%">DC</td>
										<td style="width:10%">Gross Amount</td>
										<td style="width:10%">Tax Amount</td>
										<td style="width:3%">From</td>
										<td style="width:5%">Vat</td>
									</tr>							
								</table>
								<div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 150px;
				    				margin: auto;overflow-x: hidden; overflow-y: scroll;width: 80%;">
										<table id="tblTax" class="transTablex col5-center" style="width: 100%;">
										<tbody>    
												<col style="width:10%"><!--  COl1   -->
												<col style="width:10%"><!--  COl2   -->
												<col style="width:10%"><!--  COl3   -->
												<col style="width:10%"><!--  COl4   -->
												<col style="width:10%"><!--  COl5   -->	
												<col style="width:10%"><!--  COl6   -->
												<col style="width:3%"><!--  COl7   -->
												<col style="width:5%"><!--  COl8   -->									
										</tbody>							
										</table>
								</div>			
							<br>
							<table id="tblTaxTotal" class="masterTable">
								
							</table>								
							</div>
							
						</div>
					</td>
				</tr>
			</table>
		
		<br><p align="center">
<!-- 			<input type="submit" value="Submit" -->
<!-- 				onclick="return funValidateFields();" -->
<!-- 				class="form_button" id="btnSaveGRN"/>&nbsp; &nbsp; &nbsp; -->
<!-- 				 <input id="btnReset" type="reset" value="Reset" -->
<!-- 				class="form_button" onclick="funResetFields()" /> -->
<!-- 		</p><br><br> -->
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
	</s:form>
	<script type="text/javascript">
// 	funApplyNumberValidation();
// 	funOnChange();
	</script>
</body>
</html>