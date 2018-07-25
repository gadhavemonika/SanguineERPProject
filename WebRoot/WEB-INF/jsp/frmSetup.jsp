<%@ page language="java" contentType="text/html; charSset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
	$(document).ready(function() {

		$(".tab_content").hide();
		$(".tab_content:first").show();

		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();

			var activeTab = $(this).attr("data-state");
			$("#" + activeTab).fadeIn();
		});
		funOnChangeCriteria();
		$("#strUser1_SB").css('visibility','hidden');
		$("#strUser2_SB").css('visibility','hidden');
		$("#strUser3_SB").css('visibility','hidden');
		$("#strUser4_SB").css('visibility','hidden');
		$("#strUser5_SB").css('visibility','hidden');
		$('#itemImage').attr('src', getContextPath()+"/resources/images/company_Logo.png");
		 var property='<%=session.getAttribute("userProperty").toString()%>';
		 funGetImage();
//  		 funGetProperty(property);
	});
</script>
<script type="text/javascript">
	var fieldName;
	var selectedFieldId;
	
	
	
	
	
	
	function funCheckBoxClicked(obj)
	{
		/* var isChecked=$(obj).prop("checked");
		alert(isChecked); */
		
		/* if(isChecked)
		{
			$(obj).val("Y");
		}
		else
		{
			$(obj).val("N");
		} */
		
		/* var value=$(obj).val();
		alert(value);
		
		var value=$(obj).attr("id");
		alert(value);
		
		 */
		
		
	}
	
	
	
	
	
	
	
	$(function() {
		/* $("#dtStart").datepicker();
		$("#dtEnd").datepicker();
		$("#dtLastTransDate").datepicker();
		$("#dtFromTime").datepicker();
		$("#dtToTime").datepicker(); */
		
		$("#btnAddTC").click(function( event )
		{
			funAddTCRows();
		});
		
		$('#txtReasonCode').blur(function(){
			 var code=$('#txtReasonCode').val();			     
			 if (code.trim().length > 0 && code !="?" && code !="/") {
				 funGetReason(code);
			  	}			 
		});
	});
	
	
	function funClearAutorizatioTabFields() {
		$("#strFormName").val("");
		$("#strUser1").val("");
		$("#strUser2").val("");
		$("#strUser3").val("");
		$("#strUser4").val("");
		$("#strUser5").val("");
		$("#intLevel").val("");
	}
	
	function funValidateFields()
	{
		funGetCheckedAuditForm();
		if($("#strProperty").val()=='--select--')
		{
			alert("Please select Property");
			$("#strProperty").focus();
			return false;
		}
	}
	function funClearAutorizatioTabFields_SB() {
		$("#strFormName_SB").val("");
		$("#strUser1_SB").val("");
		$("#strUser2_SB").val("");
		$("#strUser3_SB").val("");
		$("#strUser4_SB").val("");
		$("#strUser5_SB").val("");
		$("#intLevel_SB").val("");
		$("#strCriteria").val(">");
		$("#intVal1").val("");
		$("#intVal2").val("");
	}

	function btnAdd_onclick() {
		if (document.all("strFormName").value != "") {
			if (document.all("strFormName").value != ""
					&& document.all("intLevel").value != 0) {
				funAddProductRow_Authorization();
				funClearAutorizatioTabFields();
			} else {
				alert("Please Select Level");
				document.all("intLevel").focus();
				return false;
			}
		} else {
			alert("Please Select Form");
			document.all("strFormName").focus();
			return false;
		}
	}

	function btnAdd_onclick_SB() {

		if (document.all("strFormName_SB").value != "") {
		
			if ($("strFormName_SB").val() != "" && $("intLevel_SB").val() != 0) {
				if ($("#strCriteria").val() == 'between') {
					if ($("#intVal1").val() == '') {
						alert("Please Enter Value");
						$("#intVal1").focus();
						return false;
					}
					if ($("#intVal2").val() == '') {
						alert("Please Enter Value");
						$("#intVal2").focus();
						return false;
					}

				}
				if($("#strCriteria").val() == '<' && $("#intVal1").val() == ''){
					
						alert("Please Enter Value");
						document.all("intVal1").focus();
						return false;
					
				}
				if ($("#strCriteria").val() == '>' && $("#intVal1").val() == '') {
				
						alert("Please Enter Value");
						document.all("intVal1").focus();
						return false;
					
				}
				if($("#intLevel_SB").val().trim().length==0 && $("#intLevel_SB").val()=="")
					{
						alert("Please Select Level");
						document.all("intLevel_SB").focus();
						return false;
					}
				else {
					
					funAddProductRow_Authorization_SB();
					funClearAutorizatioTabFields_SB();
				}

			} else {
				alert("Please Select Level");
				$("intLevel_SB").focus();
				return false;
			}
		} else {
			alert("Please Select Form");
			document.all("strFormName_SB").focus();
			return false;
		}
	}
	function funRemoveProductRows_Authorization()
    {
		 var table = document.getElementById("tblAuthorization");
		 var rowCount = table.rows.length;
		 //alert(rowCount);
		for(var i=rowCount;i>=0;i--)
		{
			table.deleteRow(i);						
		} 
    }
	function funAddProductRow_Authorization() {
		debugFlag = false;
		var strUser1 = 'NA';
		var strUser2 = 'NA';
		var strUser3 = 'NA';
		var strUser4 = 'NA';
		var strUser5 = 'NA';

		var strFormName = $("#strFormName").val();
		var intLevel = $("#intLevel").val();
		if (intLevel == '1') {
			strUser1 = $("#strUser1").val();
			strUser2 = 'NA';
			strUser3 = 'NA';
			strUser4 = 'NA';
			strUser5 = 'NA';
		} else if (intLevel == '2') {
			strUser1 = $("#strUser1").val();
			strUser2 = $("#strUser2").val();
			strUser3 = 'NA';
			strUser4 = 'NA';
			strUser5 = 'NA';
		} else if (intLevel == '3') {
			strUser1 = $("#strUser1").val();
			strUser2 = $("#strUser2").val();
			strUser3 = $("#strUser3").val();
			strUser4 = 'NA';
			strUser5 = 'NA';
		} else if (intLevel == '4') {
			strUser1 = $("#strUser1").val();
			strUser2 = $("#strUser2").val();
			strUser3 = $("#strUser3").val();
			strUser4 = $("#strUser4").val();
			strUser5 = 'NA';
		} else if (intLevel == '5') {
			strUser1 = $("#strUser1").val();
			strUser2 = $("#strUser2").val();
			strUser3 = $("#strUser3").val();
			strUser4 = $("#strUser4").val();
			strUser5 = $("#strUser5").val();
		}

		var table = document.getElementById("tblAuthorization");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

		/* debug( funGetLabel(rowCount, 'strFormName', strFormName));
		debugFlag = false; */
		row.insertCell(0).innerHTML = funGetLabel(rowCount, 'strFormName',
				strFormName);
		row.insertCell(1).innerHTML = funGetLabel(rowCount, 'intLevel',
				intLevel);
		row.insertCell(2).innerHTML = funGetLabel(rowCount, 'strUser1',
				strUser1);
		row.insertCell(3).innerHTML = funGetLabel(rowCount, 'strUser2',
				strUser2);
		row.insertCell(4).innerHTML = funGetLabel(rowCount, 'strUser3',
				strUser3);
		row.insertCell(5).innerHTML = funGetLabel(rowCount, 'strUser4',
				strUser4);
		row.insertCell(6).innerHTML = funGetLabel(rowCount, 'strUser5',
				strUser5);
		row.insertCell(7).innerHTML = '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
		//row.insertCell(0).innerHTML= "<label name=\"listclsWorkFlowModel["+(rowCount-1)+"].strFormName\" id=\"strFormName."+(rowCount-1)+"\" value='"+strFormName+"' >"+strFormName+"</label>";			  		   	  
		//  row.insertCell(1).innerHTML= "<label name=\"listclsWorkFlowModel["+(rowCount-1)+"].intLevel\" id=\"intLevel."+(rowCount-1)+"\" value="+intLevel+" ></label>";
		/* 	    row.insertCell(2).innerHTML= "<label name=\"listclsWorkFlowModel["+(rowCount-1)+"].strUser1\" id=\"strUser1."+(rowCount-1)+"\" value="+strUser1+" >";		    	    
		 row.insertCell(3).innerHTML="<input name=\"listclsWorkFlowModel["+(rowCount-1)+"].strUser2\" id=\"strUser2."+(rowCount-1)+"\" value="+strUser2+" >";
		 row.insertCell(4).innerHTML= "<input name=\"listclsWorkFlowModel["+(rowCount-1)+"].strUser3\" id=\"strUser3."+(rowCount-1)+"\" value="+strUser3+" >";	   
		 row.insertCell(5).innerHTML= "<input name=\"listclsWorkFlowModel["+(rowCount-1)+"].strUser4\" id=\"strUser4."+(rowCount-1)+"\" value="+strUser4+" >";	
		 row.insertCell(6).innerHTML= "<input name=\"listclsWorkFlowModel["+(rowCount-1)+"].strUser5\" id=\"strUser5."+(rowCount-1)+"\" value="+strUser5+" >";
		 row.insertCell(7).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
		 debugFlag = false; */
		return false;
	}

	function funGetLabel(rowCount, id, value) {

		return "<label name=\"listclsWorkFlowModel[" + (rowCount - 1) + "]."
				+ id + "\" id=\"" + id + "." + (rowCount - 1) + "\" value='"
				+ value + "' >" + value + "</label>";
	}

	function funAddProductRow_Authorization_SB() {
		var strUser1 = 'NA';
		var strUser2 = 'NA';
		var strUser3 = 'NA';
		var strUser4 = 'NA';
		var strUser5 = 'NA';
		var intVal1 = 0.00;
		var intVal2 = 0.00;
		var strCriteria = $("#strCriteria").val();
	//alert(strCriteria);
		var strFormName = $("#strFormName_SB").val();
		var intLevel = $("#intLevel_SB").val();
		if (intLevel == '1') {
			strUser1 = $("#strUser1_SB").val();

		} else if (intLevel == '2') {
			strUser1 = $("#strUser1_SB").val();
			strUser2 = $("#strUser2_SB").val();

		} else if (intLevel == '3') {
			strUser1 = $("#strUser1_SB").val();
			strUser2 = $("#strUser2_SB").val();
			strUser3 = $("#strUser3_SB").val();

		} else if (intLevel == '4') {
			strUser1 = $("#strUser1_SB").val();
			strUser2 = $("#strUser2_SB").val();
			strUser3 = $("#strUser3_SB").val();
			strUser4 = $("#strUser4_SB").val();

		} else if (intLevel == '5') {
			strUser1 = $("#strUser1_SB").val();
			strUser2 = $("#strUser2_SB").val();
			strUser3 = $("#strUser3_SB").val();
			strUser4 = $("#strUser4_SB").val();
			strUser5 = $("#strUser5_SB").val();
		}
		if (strCriteria == ">" || strCriteria == '<') {
			intVal1 = $("#intVal1").val();
		} else if (strCriteria == 'between') {
			intVal1 = $("#intVal1").val();
			intVal2 = $("#intVal2").val();
		}
		
		var table = document.getElementById("tblAuthorizationSlabBased");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

		row.insertCell(0).innerHTML = "<input name=\"listclsWorkFlowForSlabBasedAuth["
				+ (rowCount - 1)
				+ "].strFormName\" size=\"27%\" class=\"Box\"  readonly=\"readonly\" id=\"strFormName_SB."
				+ (rowCount - 1) + "\" value=\"" + strFormName + "\">";
		row.insertCell(1).innerHTML = "<input name=\"listclsWorkFlowForSlabBasedAuth["
				+ (rowCount - 1)
				+ "].strCriteria\" size=\"11%\" class=\"Box\"  readonly=\"readonly\"  id=\"strCriteria."
				+ (rowCount - 1) + "\" value='" + strCriteria + "'>";
		row.insertCell(2).innerHTML = "<input name=\"listclsWorkFlowForSlabBasedAuth["
				+ (rowCount - 1)
				+ "].intVal1\" size=\"6%\" class=\"Box\"  readonly=\"readonly\"  id=\"intVal1."
				+ (rowCount - 1)
				+ "\" value=" + intVal1 + " >";
		row.insertCell(3).innerHTML = "<input name=\"listclsWorkFlowForSlabBasedAuth["
				+ (rowCount - 1)
				+ "].intVal2\" size=\"6%\" class=\"Box\"  readonly=\"readonly\"  id=\"intVal2."
				+ (rowCount - 1)
				+ "\" value=" + intVal2 + " >";
		row.insertCell(4).innerHTML = "<input name=\"listclsWorkFlowForSlabBasedAuth["
				+ (rowCount - 1)
				+ "].intLevel\" size=\"13%\" class=\"Box\"  readonly=\"readonly\"  id=\"intLevel_SB."
				+ (rowCount - 1) + "\" value=" + intLevel + " >";
		row.insertCell(5).innerHTML = "<input name=\"listclsWorkFlowForSlabBasedAuth["
				+ (rowCount - 1)
				+ "].strUser1\" size=\"12%\" class=\"Box\"  readonly=\"readonly\"  id=\"strUser1_SB."
				+ (rowCount - 1) + "\" value=" + strUser1 + " >";
		row.insertCell(6).innerHTML = "<input name=\"listclsWorkFlowForSlabBasedAuth["
				+ (rowCount - 1)
				+ "].strUser2\" size=\"12%\" class=\"Box\"  readonly=\"readonly\"  id=\"strUser2_SB."
				+ (rowCount - 1) + "\" value=" + strUser2 + " >";
		row.insertCell(7).innerHTML = "<input name=\"listclsWorkFlowForSlabBasedAuth["
				+ (rowCount - 1)
				+ "].strUser3\" size=\"12%\" class=\"Box\"  readonly=\"readonly\"  id=\"strUser3_SB."
				+ (rowCount - 1) + "\" value=" + strUser3 + " >";
		row.insertCell(8).innerHTML = "<input name=\"listclsWorkFlowForSlabBasedAuth["
				+ (rowCount - 1)
				+ "].strUser4\" size=\"12%\" class=\"Box\"  readonly=\"readonly\"  id=\"strUser4_SB."
				+ (rowCount - 1) + "\" value=" + strUser4 + " >";
		row.insertCell(9).innerHTML = "<input name=\"listclsWorkFlowForSlabBasedAuth["
				+ (rowCount - 1)
				+ "].strUser5\" size=\"12%\" class=\"Box\"  readonly=\"readonly\"  id=\"strUser5_SB."
				+ (rowCount - 1) + "\" value=" + strUser5 + " >";
		row.insertCell(10).innerHTML = '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow_SB(this)">';
		return false;
	}

	function funDeleteRow(obj) {
		var index = obj.parentNode.parentNode.rowIndex;
		var table = document.getElementById("tblAuthorization");
		table.deleteRow(index);
	}
	function funDeleteRow_SB(obj) {
		var index = obj.parentNode.parentNode.rowIndex;
		var table = document.getElementById("tblAuthorizationSlabBased");
		table.deleteRow(index);
	}

	function funHelp(transactionName, id) 
	{
		fieldName = transactionName;
		selectedFieldId = id;		
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	}
	
	function funSetData(code) 
	{
		switch(fieldName)
		{
			case "treeMasterForm":
				if (selectedFieldId == 'strFormName')
				{
					document.getElementById("strFormName").value = code;
				} 
				else if (selectedFieldId == 'strFormName_SB')
				{
					document.getElementById("strFormName_SB").value = code;
				}	
				
				break;
				
			case "tcForSetup":
					funSetTCFields(code);
				break;
				
			case "reason":
				funGetReason(code);
			break;	
		}
		
	}

	
	function funSetTCFields(code)
	{
		$.ajax({
	        type: "GET",
	        url: getContextPath()+"/loadTCForSetup.html?tcCode="+code,
	        dataType: "json",
	        success: function(response)
	        {
	        	$("#txtTCCode").val(response.strTCCode);
		        $("#lblTCName").text(response.strTCName);
		        $("#txtTCDesc").focus();
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
	function funGetReason(code) {
		
		$.ajax({
				type : "GET",
				url : getContextPath()
						+ "/loadReasonMasterData.html?reasonCode=" + code,
				dataType : "json",
				success : function(response) {
					if('Invalid Code' == response.strReasonCode){
						alert("Invalid reason Code");
						$("#txtReasonCode").val('');
						$("#txtReasonName").val('');
						$("#txtReasonCode").focus();
					}else{
						$("#txtReasonCode").val(response.strReasonCode);
						$("#txtReasonName").text(response.strReasonName);
						 
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
	
	function funAddTCRows()
	{
		var table = document.getElementById("tblTermsAndCond");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    var tcCode=$("#txtTCCode").val();
	    var tcName=$("#lblTCName").text();
	    var tcDesc=$("#txtTCDesc").val();
	    
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listTCForSetup["+(rowCount)+"].strTCCode\" id=\"txtTCCode."+(rowCount)+"\" value='"+tcCode+"' />";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listTCForSetup["+(rowCount)+"].strTCName\" id=\"txtTCName."+(rowCount)+"\" value='"+tcName+"' />";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"80%\" name=\"listTCForSetup["+(rowCount)+"].strTCDesc\" id=\"txtTCDesc."+(rowCount)+"\" value='"+tcDesc+"' />";	    
	    row.insertCell(3).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteTCRow(this)">';
	    funResetTCFields();
	}
	
	
	function funDeleteTCRow(obj) 
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblTermsAndCond");
	    table.deleteRow(index);
	}
	
	function funResetTCFields() 
	{
	    $("#txtTCCode").val("");
	    $("#lblTCName").text("");
	    $("#txtTCDesc").val("");
	}	
	
	
	function funGetProperty(propertyCode) {
		if (propertyCode=='--select--') {	
			window.location.href ="frmSetup.html";		
		} else {
			
			document.setupForm.action = "loadPropertySetupForm.html";
			document.setupForm.submit();
			
		}
	}

	function funOnChangeCriteria() {
		if ($("#strCriteria").val() == ">" || $("#strCriteria").val() == "<") {
			$('#intVal2').css('visibility', 'hidden');

		} else {
			$('#intVal1').css('visibility', 'visible');
			$("#intVal2").css('visibility', 'visible');
		}
	}
	function funOnChange(id) {
		
		if (id == 'intLevel') {
			if ($("#intLevel").val() == "") {
				$('#strUser1').css('visibility', 'hidden');
				$("#strUser2").css('visibility', 'hidden');
				$("#strUser3").css('visibility', 'hidden');
				$("#strUser4").css('visibility', 'hidden');
				$("#strUser5").css('visibility', 'hidden');
			} else if ($("#intLevel").val() == "1") {
				$('#strUser1').css('visibility', 'visible');
				$("#strUser2").css('visibility', 'hidden');
				$("#strUser3").css('visibility', 'hidden');
				$("#strUser4").css('visibility', 'hidden');
				$("#strUser5").css('visibility', 'hidden');
			} else if ($("#intLevel").val() == "2") {
				$('#strUser1').css('visibility', 'visible');
				$("#strUser2").css('visibility', 'visible');
				$("#strUser3").css('visibility', 'hidden');
				$("#strUser4").css('visibility', 'hidden');
				$("#strUser5").css('visibility', 'hidden');
			} else if ($("#intLevel").val() == "3") {
				$('#strUser1').css('visibility', 'visible');
				$("#strUser2").css('visibility', 'visible');
				$("#strUser3").css('visibility', 'visible');
				$("#strUser4").css('visibility', 'hidden');
				$("#strUser5").css('visibility', 'hidden');
			} else if ($("#intLevel").val() == "4") {
				$('#strUser1').css('visibility', 'visible');
				$("#strUser2").css('visibility', 'visible');
				$("#strUser3").css('visibility', 'visible');
				$("#strUser4").css('visibility', 'visible');
				$("#strUser5").css('visibility', 'hidden');
			} else if ($("#intLevel").val() == "5") {
				$('#strUser1').css('visibility', 'visible');
				$("#strUser2").css('visibility', 'visible');
				$("#strUser3").css('visibility', 'visible');
				$("#strUser4").css('visibility', 'visible');
				$("#strUser5").css('visibility', 'visible');
			}
		} else {
			if ($("#intLevel_SB").val() == "") {
				$('#strUser1_SB').css('visibility', 'hidden');
				$("#strUser2_SB").css('visibility', 'hidden');
				$("#strUser3_SB").css('visibility', 'hidden');
				$("#strUser4_SB").css('visibility', 'hidden');
				$("#strUser5_SB").css('visibility', 'hidden');
			} else if ($("#intLevel_SB").val() == "1") {
				$('#strUser1_SB').css('visibility', 'visible');
				$("#strUser2_SB").css('visibility', 'hidden');
				$("#strUser3_SB").css('visibility', 'hidden');
				$("#strUser4_SB").css('visibility', 'hidden');
				$("#strUser5_SB").css('visibility', 'hidden');
			} else if ($("#intLevel_SB").val() == "2") {
				$('#strUser1_SB').css('visibility', 'visible');
				$("#strUser2_SB").css('visibility', 'visible');
				$("#strUser3_SB").css('visibility', 'hidden');
				$("#strUser4_SB").css('visibility', 'hidden');
				$("#strUser5_SB").css('visibility', 'hidden');
			} else if ($("#intLevel_SB").val() == "3") {
				$('#strUser1_SB').css('visibility', 'visible');
				$("#strUser2_SB").css('visibility', 'visible');
				$("#strUser3_SB").css('visibility', 'visible');
				$("#strUser4_SB").css('visibility', 'hidden');
				$("#strUser5_SB").css('visibility', 'hidden');
			} else if ($("#intLevel_SB").val() == "4") {
				$('#strUser1_SB').css('visibility', 'visible');
				$("#strUser2_SB").css('visibility', 'visible');
				$("#strUser3_SB").css('visibility', 'visible');
				$("#strUser4_SB").css('visibility', 'visible');
				$("#strUser5_SB").css('visibility', 'hidden');
			} else if ($("#intLevel_SB").val() == "5") {
				$('#strUser1_SB').css('visibility', 'visible');
				$("#strUser2_SB").css('visibility', 'visible');
				$("#strUser3_SB").css('visibility', 'visible');
				$("#strUser4_SB").css('visibility', 'visible');
				$("#strUser5_SB").css('visibility', 'visible');
			}
		}

	}
	function getContextPath() 
	{
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	
		function funGetImage()
		{
	
				searchUrl=getContextPath()+"/getCompanyLogo.html";
				$("#itemImage").attr('src', searchUrl);
				funChk();
			
		}
		function funShowImagePreview(input)
		 {
			 
			 if (input.files && input.files[0])
			 {
				 var filerdr = new FileReader();
				 filerdr.onload = function(e) 
				 {
				 $('#itemImage').attr('src', e.target.result);
				 }
				 filerdr.readAsDataURL(input.files[0]);
				
			 }
		 }
		function funChk()
		{
			var strAuditCheckList="${AuditCheckList}";
			if(strAuditCheckList!="")
			{
				var temStr=strAuditCheckList.split(",");
				var table = document.getElementById("tblAudit");
		    	var rowCount = table.rows.length;
		    	var row = table.insertRow(rowCount);
		    	var FromNames="";
		    	for(var i=0;i<rowCount;i++)
		    	{
		    		var fromName=document.all("strAuditFormName."+i).value;
		    		if(fromName==temStr[i]);
		    		{
		    			document.all("strcbAudit."+i).checked="true";
		    		}
		    		
		    	}
			}
		}
		function funGetCheckedAuditForm()
		{
			var table = document.getElementById("tblAudit");
	    	var rowCount = table.rows.length;
	    	var row = table.insertRow(rowCount);
	    	var FromNames="";
	    	for(var i=0;i<rowCount;i++)
	    		{
	    			if(document.all("strcbAudit."+i).checked=="true")
	    				{
	    					var fromName=document.all("strAuditFormName."+i).value;
	    					FromNames=FromNames+","+fromName;
	    				}
	    		}
			$("#strCheckedAuditFormName").val(FromNames);
		}
		
		
	function funCreateSMS()
	{
		
		var field =$("#cmbSMSField").val();
		var content='';
		var mainSMS =$("#txtSMSContent").val();
		
		if(field=='CompanyName')
		{
			content='%%CompanyName';
		}
		if(field=='PropertyName')
		{
			content='%%PropertyName';
		}
		if(field=='PONo')
		{
			content='%%PONo';
		}
		if(field=='PODate')
		{
			content='%%PODate';
		}
		if(field=='DeleveryDate')
		{
			content='%%DeleveryDate';
		}
		if(field=='Amount')
		{
			content='%%Amount';
		}
		if(field=='ContactPerson')
		{
			content='%%ContactPerson';
		}
		
		
		mainSMS+=content;
		$("#txtSMSContent").val(mainSMS);
	}
	
	function funGetLoadPropertyLocation()
	{			
		var isOk=confirm("Do You Want to Select All Location?");
		if(isOk)
		{
			var property= $("#strProperty").val();;
			var searchUrl=getContextPath()+"/loadLocationPropertyWise.html?strProperty="+property;
			//alert(searchUrl);
			$.ajax({
			        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	funRemoveTransactionTimeTable();
			    	$.each(response, function(i,item)
			    	{
			    		funAddTransactionTime(response[i].strLocCode,response[i].strLocName,'12:00pm','12:00pm');
			    	});
			    },
				error: function(e)
			    {
			       	alert('Error:=' + e);
			    }
		      });
		}
	}

	function funAddTransactionTime(locCode, locName, tmeFrom, tmeTo)	{
	    var table = document.getElementById("tblTransactionTable");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    // onClick=\"funCallTime('txttmeFrom."+rowCount+"')\"
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\"  size=\"10%\" name=\"listclsTransactionTimeModel["+(rowCount)+"].strLocCode\" id=\"txtLocCode."+(rowCount)+"\" value='"+locCode+"'>";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\"  size=\"30%\" name=\"listclsTransactionTimeModel["+(rowCount)+"].strLocName\" id=\"txtLocName."+(rowCount)+"\" value='"+locName+"'  >";
	    row.insertCell(2).innerHTML= "<input type=\"text\" class =\"datepicker_recurring_start\"  size=\"10%\" name=\"listclsTransactionTimeModel["+(rowCount)+"].tmeFrom\" id=\"txttmeFrom."+(rowCount)+"\" value='"+tmeFrom+"' onClick=\"funCallTime(this)\" >";
	    row.insertCell(3).innerHTML= "<input type=\"text\"  size=\"10%\" name=\"listclsTransactionTimeModel["+(rowCount)+"].tmeTo\" id=\"txttmeTo."+(rowCount)+"\" value='"+tmeTo+"' onClick=\"funCallTime(this)\">";
	    row.insertCell(4).innerHTML= '<input type="button" class="deletebutton"  value = "Delete" onClick="funDeleteRowTransactionTable(this)">';
	    
	    return false;
}	


	function funRemoveTransactionTimeTable()
    {
		 var table = document.getElementById("tblTransactionTable");
		 var rowCount = table.rows.length;
		 //alert(rowCount);
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		} 
    }
	
	
	function funDeleteRowTransactionTable(obj) {
		var index = obj.parentNode.parentNode.rowIndex;
		var table = document.getElementById("tblTransactionTable");
		table.deleteRow(index);
	}
	
	function funCallTime(obj)
	{
		$(obj).timepicker();

	}
		
		
	
</script>
<title>Insert title here</title>
<%-- <tab:tabConfig /> --%>
</head>
<body onload="funGetImage()">
	<div id="formHeading">
		<label>Setup</label>
	</div>
	<div>
		<s:form action="saveSetupData.html?saddr=${urlHits}" method="POST" name="setupForm" modelAttribute="setUpAttribute" enctype="multipart/form-data">
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
			<table style="font-size:11px;
	font-weight: bold;">
				<tr>
					<td width="80px" align="center"><label>Property</label> </td>
					<td><s:select path="strProperty" items="${properties}"
							id="strProperty" onchange="funGetProperty(this.value)" cssClass="longTextBox" cssStyle="width:300px">
						</s:select></td>
				</tr>
			</table>
			<br/>
			<!--  Start of tabContainer-->
			<table
				style="border: 0px solid black; width: 100%;height:100%; margin-left: auto; margin-right: auto; background-color: #C0E4FF;">
				<tr>
					<td>
						<div id="tab_container" style="height: 900px">
							<ul class="tabs">
								<li class="active" data-state="tab1">Company</li>
								<li data-state="tab2">General</li>
								<li data-state="tab3">Purchase Order</li>
								<!-- <li data-state="tab4">Authorise</li> -->
								<li data-state="tab5">Process</li>
								<li data-state="tab6">Bank</li>
								<li data-state="tab7">Supplier Performance</li>
								<li data-state="tab8">Authorization(Slab Based)</li>
								<li data-state="tab9">Audit</li>
								<li data-state="tab10">SMS Setup</li>
								<li data-state="tab11">Transaction Time</li>

							</ul>
							<div id="tab1" class="tab_content" style="height: 890px">
							<br><br>
								<table class="transTable">
									<tr>
										<td width="120px"><label>Industry Type</label></td>
										<td colspan="3"><s:select path="strIndustryType" cssClass="BoxW140px">
												
												<s:option value="Hospitality">Hospitality</s:option>
												 <s:option value="Manufacturing">Manufacturing</s:option> 
											     <s:option value="Retailing">Retailing</s:option> 
												<%-- <s:option value="Pharmaceutical">Pharmaceutical</s:option>
												<s:option value="Trading">Trading</s:option>
												<s:option value="Corporate">Corporate</s:option>  --%>										</s:select></td>
											<td rowspan="9"  width="20%" style="background-color: #C0E4FF;border: 1px solid black;">
				       					<img id="itemImage" src=""  width="200px" height="200px" alt="Item Image" /></td>
									</tr>

									<tr>
										<td><s:label path="strCompanyCode">Company Code </s:label></td>
										<td width="150px"><s:input path="strCompanyCode" cssClass="BoxW116px" value="${command.strCompanyCode}" readonly="true"/></td>
										<td width="100px"><s:label path="strCompanyName">Name </s:label></td>
										<td><s:input path="strCompanyName" cssClass="BoxW200px" readonly="true"/></td>
									</tr>

									<tr>
										<td><label>Financial Year</label></td>
										<td colspan="3"><s:input path="strFinYear" cssClass="BoxW116px" readonly="true"/></td>
									</tr>

									<tr>
										<td><label>Start Date</label></td>
										<td><s:input path="dtStart" id="dtStart" cssClass="BoxW116px" readonly="true"/></td>
										<td><label>End Date</label></td>
										<td><s:input path="dtEnd" id="dtEnd" cssClass="BoxW116px" readonly="true"/></td>
									</tr>

									<tr>
										<td><label>Last Transaction Date</label>
										<td ><s:input path="dtLastTransDate" id="dtLastTransDate" cssClass="BoxW116px" readonly="true" />
									 <td>Company Logo</td>
									 <td>
									 <input  id="companyLogo" name="companyLogo"  type="file" accept="image/png" onchange="funShowImagePreview(this);" /></td>
									</tr>

									<tr>
										<th align="left" colspan="4"><label>Main Address</label></th>
									</tr>

									<tr>
										<td><label>Address Line 1</label></td>
										<td colspan="3"><s:input path="strAdd1" cssStyle="text-transform: uppercase;"  cssClass="longTextBox"/></td>
									</tr>

									<tr>
										<td><label>Address Line 2</label></td>
										<td colspan="3"><s:input path="strAdd2" cssStyle="text-transform: uppercase;"  cssClass="longTextBox" />
									</tr>

									<tr>
										<td><label>City</label></td>
										<td><s:input path="strCity" cssStyle="text-transform: uppercase;"  cssClass="BoxW116px"/></td>
										<td><label>State</label></td>
										<td><s:input path="strState" cssStyle="text-transform: uppercase;"  cssClass="BoxW116px"/></td>
									</tr>

									<tr>
										<td><label>Country</label></td>
										<td><s:input path="strCountry" cssStyle="text-transform: uppercase;"  cssClass="BoxW116px"/></td>
										<td><label>Pin</label></td>
										<td colspan="5"><s:input path="strPin" cssClass="BoxW116px"/></td>
									</tr>

									<tr >
										<th align="left" colspan="6"><label>Billing Address</label></th>
									</tr>
									<tr>
										<td ><label>Address Line 1</label></td>
										<td colspan="6"><s:input path="strBAdd1" cssStyle="text-transform: uppercase;"  cssClass="longTextBox" /></td>
									</tr>

									<tr>
										<td><label>Address Line 2</label></td>
										<td colspan="6"><s:input path="strBAdd2" cssStyle="text-transform: uppercase;"  cssClass="longTextBox"/>
									</tr>

									<tr>
										<td><label>City</label></td>
										<td><s:input path="strBCity" cssStyle="text-transform: uppercase;"  cssClass="BoxW116px"/></td>
										<td><label>State</label></td>
										<td colspan="6"><s:input path="strBState" cssStyle="text-transform: uppercase;"  cssClass="BoxW116px"/></td>
									</tr>

									<tr>
										<td><label>Country</label></td>
										<td><s:input path="strBCountry" cssStyle="text-transform: uppercase;"  cssClass="BoxW116px"/></td>
										<td><label>Pin</label></td>
										<td  colspan="6"><s:input path="strBPin" cssClass="BoxW116px"/></td>
									</tr>


									<tr>
										<th align="left" colspan="6"><label>Shipping Address</label></th>
									</tr>
									<tr>
										<td><label>Address Line 1</label></td>
										<td colspan="6"><s:input path="strSAdd1" cssStyle="text-transform: uppercase;"  cssClass="longTextBox"/></td>
									</tr>

									<tr>
										<td><label>Address Line 2</label></td>
										<td colspan="6"><s:input path="strSAdd2" cssStyle="text-transform: uppercase;"  cssClass="longTextBox"/>
									</tr>

									<tr>
										<td><label>City</label></td>
										<td><s:input path="strSCity" cssStyle="text-transform: uppercase;"  cssClass="BoxW116px"/></td>
										<td><label>State</label></td>
										<td  colspan="6"><s:input path="strSState" cssStyle="text-transform: uppercase;"  cssClass="BoxW116px"/></td>
									</tr>

									<tr>
										<td><label>Country</label></td>
										<td><s:input path="strSCountry" cssStyle="text-transform: uppercase;"  cssClass="BoxW116px"/></td>
										<td><label>Pin</label></td>
										<td  colspan="6"><s:input path="strSPin" cssClass="BoxW116px"/></td>
									</tr>

									<tr>
										<th align="left" colspan="6"><label>Others</label></th>
									</tr>
									<tr>
										<td><label>Phone No</label></td>
										<td ><s:input path="strPhone" cssClass="BoxW116px"/></td>
										<td><label>Fax</label></td>
										<td  colspan="6"><s:input path="strFax" cssClass="BoxW116px"/></td>
									</tr>


									<tr>
										<td><label>Email id</label></td>
										<td><s:input path="strEmail" type="email" placeholder="Enter a valid email address"  cssClass="BoxW200px"/></td>
										<td><label>Website</label></td>
										<td colspan="6"><s:input type="url" placeholder="Enter a valid Website" path="strWebsite" cssClass="BoxW200px"/>Starting with http or https</td>
									</tr>


									<tr>
										<td><label>Due Days</label></td>
										<td><s:input path="intDueDays" cssClass="BoxW116px"/></td>
										<td><label>P.L.A. No.</label></td>
										<td  colspan="6"><s:input path="strMask" cssClass="BoxW116px"/></td>
									</tr>


									<tr>
										<td><label>CST No/GST No</label></td>
										<td><s:input path="strCST" cssClass="BoxW116px"/></td>
										<td><label>VAT No</label></td>
										<td colspan="6"><s:input path="strVAT" cssClass="BoxW116px"/></td>
									</tr>


									<tr>
										<td><label>Service Tax No</label></td>
										<td><s:input path="strSerTax" cssClass="BoxW200px"/></td>
										<td><label>Pan No </label></td>
										<td colspan="6"><s:input path="strPanNo" cssClass="BoxW116px"/></td>
									</tr>

									<tr>
										<td><label>Location Code No </label></td>
										<td><s:input path="strLocCode" cssClass="BoxW116px"/></td>
										<td><label>Asseese Code No </label></td>
										<td colspan="6"><s:input path="strAsseeCode" cssClass="BoxW116px"/></td>
									</tr>

									<tr>
										<td><label>Purchase Email </label></td>
										<td><s:input path="strPurEmail" type="email" placeholder="Enter a valid email address" cssClass="BoxW200px"/></td>
										<td><label>Sales Email</label></td>
										<td colspan="6"><s:input type="email" placeholder="Enter a valid email address" path="strSaleEmail" cssClass="BoxW200px"/></td>
									</tr>

									<tr>
										<td><label>Range Address</label></td>
										<td colspan="6"><s:input path="strRangeAdd" cssClass="longTextBox"/></td>
									</tr>

									<tr>
										<td><label>Range</label></td>
										<td><s:input path="strRangeDiv" cssClass="BoxW116px"/></td>
										<td><label>Commisionerate </label></td>
										<td colspan="6"><s:input path="strCommi" cssClass="BoxW116px"/></td>
									</tr>

									<tr>
										<td><label>C.Ex Reg No</label></td>
										<td><s:input path="strRegNo" cssClass="BoxW200px"/></td>
										<td><label>Division </label></td>
										<td colspan="6"><s:input path="strDivision" cssClass="BoxW116px"/></td>
									</tr>
									
									<tr>
									<td><label>Division Address</label></td>
										<td colspan="8"><s:input path="strDivisionAdd" cssClass="longTextBox"/></td>
									</tr>

									<tr>
										<td><label>Bond Amount</label></td>
										<td><s:input path="dblBondAmt" cssClass="BoxW116px"/></td>
										<td><label>Acceptance No.</label></td>
										<td colspan="6"><s:input path="strAcceptanceNo" cssClass="BoxW116px"/></td>
									</tr>
									
									<tr>
										<td><label>E.C.C.No.</label></td>
										<td><s:input path="strECCNo" cssClass="BoxW116px"/></td>
									</tr>	

								</table>
							</div>						

						<div id="tab2" class="tab_content">
							<br>
							<br>
								<table class="transTable">
									<tr>
										<th colspan="6">&nbsp;</th>
									</tr>
									<tr>
										<td width="150px"><label>Allow Negative Stock</label></td>
										<td colspan="0"><s:select path="strNegStock"
												cssClass="BoxW62px">
												<s:option value="Y">YES</s:option>
												<s:option value="N">NO</s:option>
											</s:select></td>
										<td width="150px"><label>Rate PickUp From</label></td>
										<td colspan="3"><s:select path="strRatePickUpFrom"
												cssStyle="width:auto" cssClass="BoxW62px">
												<s:option value="SupplierRate">Last Supplier Rate</s:option>
												<s:option value="PurchaseRate">Purchase Rate</s:option>
											</s:select></td>
									</tr>

									<tr>
										<td><label>Production Order BOM</label></td>
										<td width="150px"><s:select path="strPOBOM"
												cssClass="BoxW124px">
												<s:option value="FIRST">FIRST LEVEL</s:option>
												<s:option value="LAST">LAST LEVEL</s:option>
											</s:select></td>
										<td width="130px"><label>Sales Order BOM</label></td>
										<td><s:select path="strSOBOM" cssClass="BoxW124px">
												<s:option value="FIRST">FIRST LEVEL</s:option>
												<s:option value="LAST">LAST LEVEL</s:option>
											</s:select></td>
									</tr>

									<tr>
										<td><label>Total Working Time(min)</label></td>
										<td ><s:input path="strTotalWorkhour"
												cssClass="BoxW116px" /></td>
												
										<td><label>Show All Product To All Location</label></td>
										<td><s:checkbox path="strShowAllProdToAllLoc" value="Y" /></td>		
												
									</tr>


									<tr>
										<td><label>From Time </label></td>
										<td><s:input path="dtFromTime" id="dtFromTime"
												cssClass="BoxW116px" /></td>
										<td><label>To Time</label></td>
										<td><s:input path="dtToTime" id="dtToTime"
												cssClass="BoxW116px" /></td>
									</tr>


									<tr>
										<td><label>Workflowbased Authorisation </label></td>
										<td><s:select path="strWorkFlowbasedAuth"
												cssClass="BoxW124px">
												<s:option value="Y">YES</s:option>
												<s:option value="N">NO</s:option>
												<%-- <s:option value="S">SLAB BASED</s:option> --%>
											</s:select></td>
											
										<td><label>Location Wise Production Order </label></td>	
										<td><s:checkbox path="strLocWiseProductionOrder" value="Y" /></td>			
											
									</tr>
									<tr>
										<td><label>Amount Decimal Places</label></td>
										<td><s:select path="intdec" id="intdec"
												cssClass="BoxW48px">
												<s:option value="-1">-1</s:option>
												<s:option value="0">0</s:option>
												<s:option value="1">1</s:option>
												<s:option value="2">2</s:option>
												<s:option value="3">3</s:option>
												<s:option value="4">4</s:option>
												<s:option value="5">5</s:option>
												<s:option value="6">6</s:option>
												<s:option value="7">7</s:option>
												<s:option value="8">8</s:option>
												<s:option value="9">9</s:option>
												<s:option value="10">10</s:option>
												
											</s:select></td>


										<td><label>Quantity Decimal Places</label></td>
										<td><s:select path="intqtydec" id="intqtydec"
												cssClass="BoxW48px">
												<s:option value="0">0</s:option>
												<s:option value="1">1</s:option>
												<s:option value="2">2</s:option>
												<s:option value="3">3</s:option>
												<s:option value="4">4</s:option>
											</s:select></td>
									</tr>


									<tr>
										<td><label>List Price in PO</label></td>
										<td><s:select path="strListPriceInPO"
												id="strListPriceInPO" cssClass="BoxW124px">
												<s:option value="L">List Price</s:option>
												<s:option value="S">Supplier Price</s:option>
											</s:select></td>
										<td><label>Finance Module</label></td>
										<td><s:checkbox path="strCMSModule" value="Y" /></td>
									</tr>

									<tr>
										<td><label>Batch Method</label></td>
										<td><s:select path="strBatchMethod" id="strBatchMethod"
												cssClass="BoxW124px">
												<s:option value="Manual">Manual</s:option>
												<s:option value="FIFO">FIFO</s:option>
											</s:select></td>
										<td><label>Tally Posting Type</label></td>
										<td><s:select path="strTPostingType" id="strTPostingType"
												cssClass="longTextBox">
												<s:option value="Basic">Linkup Based</s:option>
												<s:option value="ST">Consider SubTotal As Taxable</s:option>
												<s:option value="STandTaxes">Consider SubTotal + Taxes as Taxable</s:option>
											</s:select></td>
									</tr>

									<tr>
										<td><label>Auto DC for TaxInvoice</label></td>
										<td><s:checkbox path="strAutoDC" value="Y" />
										<td><label>Audit</label></td>
										<td><s:checkbox path="strAudit" value="Y" />
									</tr>
									<tr>
										<td>Show Value In Requisition</td>
										<td><s:checkbox path="strShowReqVal" value="Y" />
										<td>Show Stock In Requisition</td>
										<td><s:checkbox path="strShowStkReq" value="Y" />
									</tr>
									<tr>
										<td>Show Value In MIS Slip</td>
										<td><s:checkbox path="strShowValMISSlip" value="Y" />
										<td>Change UOM In Transaction</td>
										<td><s:checkbox path="strChangeUOMTrans" value="Y" />
									</tr>
									<tr>
										<td>Show Location Wise Product In Product Master</td>
										<td><s:checkbox path="strShowProdMaster" value="Y" />
										<td>Show Property Wise Document</td>
										<td><s:checkbox path="strShowProdDoc" value="Y" />
									</tr>
									<tr>
										<td><label>ShowTransaction Help Asc/Desc</label></td>
										<td><s:select path="strShowTransAsc_Desc"
												id="strShowTransAsc_Desc" cssClass="BoxW124px">
												<s:option value="Asc">Ascending</s:option>
												<s:option value="Desc">Descending</s:option>
											</s:select></td>
										<td>Allow Date Change In MIS</td>
										<td><s:checkbox path="strAllowDateChangeInMIS" value="Y" />
									</tr>
									<tr>
										<td><label>Allow Name Change In Product Master </label></td>
										<td colspan="1"><s:checkbox path="strNameChangeProdMast"
												value="Y" />
										<td>Stock Adjustement Reason Code</td>
										<td colspan="1"><s:select id="cmbReason"
												name="txtReasonCode" path="strStkAdjReason"
												cssClass="longTextBox" cssStyle="width:auto">
												<s:options items="${listReason}" />
											</s:select></td>
									</tr>
									<tr>
										<td><label>Notification Time Interval</label></td>
										<td><s:input type="number"
												path="intNotificationTimeinterval" step="any" min="1"
												cssClass="BoxW116px" cssStyle="width:25%"></s:input></td>
									<td><label>Month End</label></td>
									<td><s:checkbox path="strMonthEnd" value="Y" />
									</tr>
									
									<tr>
										<td>Show Avg Qty In Production Order</td>
										<td><s:checkbox path="strShowAvgQtyInOP" value="Y" />
										<td>Show Stock In Production Order</td>
										<td><s:checkbox path="strShowStockInOP" value="Y" />
									</tr>
									
									<tr>
										<td>Show Avg Qty In Sales Order</td>
										<td><s:checkbox path="strShowAvgQtyInSO" value="Y" />
										<td>Show Stock In Sales Order</td>
										<td><s:checkbox path="strShowStockInSO" value="Y" />
									</tr>
									
									<tr>
										<td>Effect Of Disc On PO</td>
										<td><s:checkbox path="strEffectOfDiscOnPO" value="Y" />
										<td>Invoice Print Formate</td>
										<td><s:select path="strInvFormat" id="strInvFormat"
												cssClass="BoxW48px" cssStyle="width:50%">
												<s:option value="Format 1">Invoice Format 1</s:option>
												<s:option value="Format 2">Invoice Format 2</s:option>
												<s:option value="RetailNonGSTA4">Format 3 Retail A4</s:option>
												<%-- <s:option value="RetailNonGSTA4">Format 4 Retail Non GST A4</s:option> --%>
												<s:option value="Format 4 Inv Ret">Format 4 Retail 40Col</s:option>
												<s:option value="Format 5">Format 5</s:option>
												<s:option value="Format 6">Format 6</s:option>
												<s:option value="Format 7">Format 7</s:option>
												
											</s:select></td>
									</tr>	
									<tr>
										<td>Invoice Print Note</td>
										<td><s:textarea cssStyle="width: 200px; height: 50px;" id="txtSMSContent" path="strInvNote"  /></td>
									<td>Currency</td>
										<td><s:select path="strCurrencyCode" id="txtCurrencyCode"
												cssClass="BoxW48px" cssStyle="width:25%" >
												<s:options items="${listCurrency}" />
												</s:select></td>
									</tr>
									<tr>
									
									<td>Show All Properties Customer</td>
										<td><s:checkbox path="strShowAllPropCustomer" value="Y" /></td>
																		
									<td>Effect of Invoice</td>
										<td><s:select path="strEffectOfInvoice" id="txtEffectOfInvoice"
												cssClass="BoxW48px" cssStyle="width:25%" >
												<s:option value="DC" >Delivery Challan</s:option>
												<s:option value="Invoice">Invoice</s:option>
												</s:select></td> 
									</tr>
									
									<tr>
									
									<td colspan="3">Effect of GRN in WebBooks </td>
										<td><s:select path="strEffectOfGRNWebBook" id="txtEffectOfGRNWebBook"
												cssClass="BoxW48px" cssStyle="width:25%" >
												<s:option value="SCBill" >Sundry Creditor Bill</s:option>
												<s:option value="Payment">Payment</s:option>
												</s:select></td> 
									</tr>
									
									<tr>
									
									<td><label>Multiple Currency</label></td>
										<td ><s:select path="strMultiCurrency"
												id="strMultiCurrency" cssClass="BoxW124px">
												<s:option value="N">NO</s:option>
												<s:option value="Y">YES</s:option>
											</s:select>
										</td>
									<td>Show All Supp/Cust/SubCont to All Property</td>
										<td><s:checkbox path="strShowAllPartyToAllLoc" value="Y" />
									</tr>
									
									<tr>
										<td>Show All Taxes on Transaction</td>
										<td><s:checkbox path="strShowAllTaxesOnTransaction" value="Y" />
										<td>Sales Order KOT Print</td>
										<td><s:checkbox id="chkSOKOTPrint" path="strSOKOTPrint"  onclick="funCheckBoxClicked(this)"/>
									</tr>
									
									<tr>
									<td>Rate History Format</td>
										<td><s:select path="strRateHistoryFormat" id="strRateHistoryFormat"
												cssClass="BoxW48px" cssStyle="width:50%">
												<s:option value="Format 1">Format 1</s:option>
												<s:option value="Format 2">Format 2</s:option>
											</s:select></td>
									
										<td>PO Slip Format</td>
										<td><s:select path="strPOSlipFormat" id="strPOSlipFormat"
												cssClass="BoxW48px" cssStyle="width:50%">
												<s:option value="Format 1">Format 1</s:option>
												<s:option value="Format 2">Format 2</s:option>
												<s:option value="Format 3">Format 3 (Global)</s:option>
											</s:select></td>
									</tr>
									<tr>
									<td>Sales Return Slip Format</td>
										<td>
										<s:select path="strSRSlipFormat" id="strSRSlipFormat"
												cssClass="BoxW48px" cssStyle="width:50%">
												<s:option value="Format 1">Format 1</s:option>
												<s:option value="Format 2">Format 2</s:option>
										</s:select>
										</td>
									</tr>
								</table>
							</div>
				
						<div id="tab3" class="tab_content">
							<table class="masterTable" style="width:95%">								
								<tr><th colspan="10" width="100%" align="center"> Purchase Order Terms &amp; Conditions </th></tr>								
							</table>
							<br>
							<table class="transTableMiddle1">
								<tr>
									<td> <label class="namelabel">TC Code</label> </td>
									<td><input id="txtTCCode" ondblclick="funHelp('tcForSetup')" class="searchTextBox" /></td>									
									<td> <label id="lblTCName" class="namelabel"></label> </td>
									<td> <label class="namelabel">TC Description</label> </td>
									<td><input id="txtTCDesc" class="longTextBox" /></td>
									<td colspan="3"><input type="Button" value="Add" id="btnAddTC" class="smallButton" /></td>
								</tr>
							</table>
							
							<div class="dynamicTableContainer" style="height: 250px;">
								<table style="height: 20px; border: #0F0;width: 100%;font-size:11px;
								font-weight: bold;">
									<tr bgcolor="#72BEFC">
									<td width="20%">TC Code</td>
									<td width="20%">TC Name</td>
									<td width="48%">TC Description</td>
									<td width="20%">Delete</td>
								</tr>
							</table>
							<div>
							 <table id="tblTermsAndCond" style="width:100%;border:
					#0F0;table-layout:fixed;overflow:scroll;" class="transTablex col4-center">
								<c:forEach items="${listTCForSetup}" var="SB" varStatus="status">
									<tr>
										<td style="width: 34%; height: 12px;"><input size="27%" class="Box"  readonly="readonly"
											name="listTCForSetup[${status.index}].strTCCode"
											value="${SB.strTCCode}" /></td>

										<td style="width:35%; height: 12px;"><input size="11%" class="Box" readonly="readonly"
											name="listTCForSetup[${status.index}].strTCName"
											value="${SB.strTCName}" /></td>

										<td style="width: 84%; height: 12px;"><input size="11%" class="Box" readonly="readonly"
											name="listTCForSetup[${status.index}].strTCDesc"
											value="${SB.strTCDesc}" /></td>

										<td style="width: 20%; height: 12px;"><input type="Button" value="Delete" size="5%" 
											onClick="Javacsript:funDeleteTCRow(this)" class="deletebutton"/></td>
									</tr>
								</c:forEach>
							</table>
						</div>
						</div>
						</div>
						<div id="tab5" class="tab_content">
						<br><br><br>
							<table id="tblprocess" class="transTable">

								<tr>
									<th colspan="7" align="left">Module Flow</th>
								</tr>
								
								<c:forEach items="${processSetupFormList}"
									var="processsetupform" varStatus="status">
									<tr>
										<td>
										<input type="hidden" name="listProcessSetupForm[${status.index}].strFormName"
											value="${processsetupform.strFormName}"/>
										<input
											name="listProcessSetupForm[${status.index}].strFormDesc"
											value="${processsetupform.strFormDesc}" readonly="readonly"  class="Box"/></td>
										<c:choose>

											<c:when
												test="${processsetupform.strFormName == 'frmPurchaseIndent'}">
												<%-- <td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strSalesOrder"
													<c:if test="${processsetupform.strSalesOrder == 'Sales Order'}">checked="checked"</c:if>
													value="Sales Order">Sales Order<br></td> --%>
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strProductionOrder"
													<c:if test="${processsetupform.strProductionOrder == 'Production Order'}">checked="checked"</c:if>
													value="Production Order">Production Order<br></td>
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strMinimumLevel"
													<c:if test="${processsetupform.strMinimumLevel == 'Minimum Level'}">checked="checked"</c:if>
													value="Minimum Level">Minimum Level <br></td>
												<td colspan="3"><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strRequisition"
													<c:if test="${processsetupform.strRequisition == 'Requisition'}">checked="checked"</c:if>
													value="Requisition">Requisition<br></td>
											</c:when>

											<c:when
												test="${processsetupform.strFormName == 'frmPurchaseOrder'}">
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDirect"
													<c:if test="${processsetupform.strDirect == 'Direct'}">checked="checked"</c:if>
													value="Direct"> Direct <br></td>
												<td ><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strProductionOrder"
													<c:if test="${processsetupform.strProductionOrder == 'Production Order'}">checked="checked"</c:if>
													value="Production Order">Production Order<br></td>
													
												<td colspan="4"><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strPurchaseIndent"
													<c:if test="${processsetupform.strPurchaseIndent == 'Purchase Indent'}">checked="checked"</c:if>
													value="Purchase Indent">Purchase Indent<br></td>	
											</c:when>

											<c:when test="${processsetupform.strFormName == 'Job Order'}">
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strSalesOrder"
													<c:if test="${processsetupform.strSalesOrder == 'Sales Order'}">checked="checked"</c:if>
													value="Sales Order">Sales Order<br></td>
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strProductionOrder"
													<c:if test="${processsetupform.strProductionOrder == 'Production Order'}">checked="checked"</c:if>
													value="Production Order">Production Order<br></td>
												<td colspan="4"><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDirect"
													<c:if test="${processsetupform.strDirect == 'Direct'}">checked="checked"</c:if>
													value="Direct"> Direct <br></td>
											</c:when>

											<c:when
												test="${processsetupform.strFormName == 'Sales Direct Billing'}">
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strSalesOrder"
													<c:if test="${processsetupform.strSalesOrder == 'Sales Order'}">checked="checked"</c:if>
													value="Sales Order">Sales Order<br></td>
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strServiceOrder"
													<c:if test="${processsetupform.strServiceOrder == 'Service Order'}">checked="checked"</c:if>
													value="Service Order"> Service Order <br></td>
												<td colspan="4"><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDirect"
													<c:if test="${processsetupform.strDirect == 'Direct'}">checked="checked"</c:if>
													value="Direct"> Direct <br></td>
											</c:when>

											<c:when
												test="${processsetupform.strFormName == 'frmMaterialReq'}">
													<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDirect"
													<c:if test="${processsetupform.strDirect == 'Direct'}">checked="checked"</c:if>
													value="Direct"> Direct <br></td>
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strWorkOrder"
													<c:if test="${processsetupform.strWorkOrder == 'Work Order'}">checked="checked"</c:if>
													value="Work Order"> Work Order <br></td>
												
											    <td colspan="2"><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strProductionOrder"
													<c:if test="${processsetupform.strProductionOrder == 'Production Order'}">checked="checked"</c:if>
													value="Production Order"> Production Order <br></td>		
													
													
											</c:when>


											<c:when
												test="${processsetupform.strFormName == 'frmMIS'}">
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strRequisition"
													<c:if test="${processsetupform.strRequisition == 'Requisition'}">checked="checked"</c:if>
													value="Requisition">Requisition<br></td>
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strWorkOrder"
													<c:if test="${processsetupform.strWorkOrder == 'Work Order'}">checked="checked"</c:if>
													value="Work Order"> Work Order <br></td>
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDirect"
													<c:if test="${processsetupform.strDirect == 'Direct'}">checked="checked"</c:if>
													value="Direct"> Direct <br></td>
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strProject"
													<c:if test="${processsetupform.strProject == 'Project'}">checked="checked"</c:if>
													value="Project"> Project <br></td>
											</c:when>


											<c:when test="${processsetupform.strFormName == 'frmGRN'}">
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strPurchaseOrder"
													<c:if test="${processsetupform.strPurchaseOrder == 'Purchase Order'}">checked="checked"</c:if>
													value="Purchase Order">Purchase Order<br></td>
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strPurchaseReturn"
													<c:if test="${processsetupform.strPurchaseReturn == 'Purchase Return'}">checked="checked"</c:if>
													value="Purchase Return">Purchase Return<br></td>
												<%-- <td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strServiceOrder"
													<c:if test="${processsetupform.strServiceOrder == 'Service Order'}">checked="checked"</c:if>
													value="Service Order">Service Order<br></td> --%>
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDirect"
													<c:if test="${processsetupform.strDirect == 'Direct'}">checked="checked"</c:if>
													value="Direct"> Direct <br></td>
												<%-- <td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strSalesReturn"
													<c:if test="${processsetupform.strSalesReturn == 'Sales Return'}">checked="checked"</c:if>
													value="Sales Return"> Sales Return <br></td> --%>
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strRateContractor"
													<c:if test="${processsetupform.strRateContractor == 'Rate Contractor'}">checked="checked"</c:if>
													value="Rate Contractor"> Rate Contractor <br></td>
												
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strInvoice"
													<c:if test="${processsetupform.strInvoice == 'Invoice'}">checked="checked"</c:if>
													value="Invoice"> Invoice <br></td>	
													
													<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDeliverySchedule"
													<c:if test="${processsetupform.strDeliverySchedule == 'Delivery Schedule'}">checked="checked"</c:if>
													value="Delivery Schedule"> Delivery Schedule <br></td>
													
													
											</c:when>


											<c:when
												test="${processsetupform.strFormName == 'frmPurchaseReturn'}">
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strGRN"
													<c:if test="${processsetupform.strGRN == 'GRN'}">checked="checked"</c:if>
													value="GRN"> GRN <br></td>
												<td colspan="5"><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDirect"
													<c:if test="${processsetupform.strDirect == 'Direct'}">checked="checked"</c:if>
													value="Direct"> Direct <br></td>
											</c:when>


											<c:when
												test="${processsetupform.strFormName == 'Sub Contractor GRN'}">
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDeliveryNote"
													<c:if test="${processsetupform.strDeliveryNote == 'Delivery Note'}">checked="checked"</c:if>
													value="Delivery Note">Delivery Note<br></td>
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strOpeningStock"
													<c:if test="${processsetupform.strOpeningStock == 'Opening Stock'}">checked="checked"</c:if>
													value="Opening Stock">Opening Stock<br></td>
												<td colspan="4"><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDirect"
													<c:if test="${processsetupform.strDirect == 'Direct'}">checked="checked"</c:if>
													value="Direct"> Direct <br></td>
											</c:when>


											<c:when
												test="${processsetupform.strFormName == 'Delivery Note'}">
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strGRN"
													<c:if test="${processsetupform.strGRN == 'GRN'}">checked="checked"</c:if>
													value="GRN"> GRN <br></td>
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strSubContractorGRN"
													<c:if test="${processsetupform.strSubContractorGRN == 'Sub Contractor GRN'}">checked="checked"</c:if>
													value="Sub Contractor GRN"> Sub Contractor GRN <br></td>
												<td colspan="4"><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDirect"
													<c:if test="${processsetupform.strDirect == 'Direct'}">checked="checked"</c:if>
													value="Direct"> Direct <br></td>
											</c:when>
											
											
											
											<c:when
												test="${processsetupform.strFormName == 'frmMaterialReturn'}">
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDirect"
													<c:if test="${processsetupform.strDirect == 'Direct'}">checked="checked"</c:if>
													value="Direct"> Direct <br></td>
												<td colspan="6"><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strMIS"
													<c:if test="${processsetupform.strMIS == 'MIS'}">checked="checked"</c:if>
													value="MIS"> MIS <br></td>
											</c:when>
											
											
											

											<c:when
												test="${processsetupform.strFormName == 'Excise Challan'}">
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strPurchaseReturn"
													<c:if test="${processsetupform.strPurchaseReturn == 'Purchase  Return'}">checked="checked"</c:if>
													value="Purchase  Return">Purchase Return<br></td>
												<td colspan="4"><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDeliveryNote"
													<c:if test="${processsetupform.strDeliveryNote == 'Delivery Note'}">checked="checked"</c:if>
													value="Delivery Note"> Delivery Note <br></td>
											</c:when>


											<c:when
												test="${processsetupform.strFormName == 'frmProductionOrder'}">
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDirect"
													<c:if test="${processsetupform.strDirect == 'Direct'}">checked="checked"</c:if>
													value="Direct"> Direct <br></td>												
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strSalesOrder"
													<c:if test="${processsetupform.strSalesOrder == 'Sales Order'}">checked="checked"</c:if>
													value="Sales Order">Sales Order<br></td>
												<td colspan="5"><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDeliveryNote"
													<c:if test="${processsetupform.strDeliveryNote == 'Delivery Note'}">checked="checked"</c:if>
													value="Delivery Note"> Delivery Note <br></td>
											</c:when>

											
											<c:when
												test="${processsetupform.strFormName == 'frmBillPassing'}">
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDirect"
													<c:if test="${processsetupform.strDirect == 'Direct'}">checked="checked"</c:if>
													value="Direct"> Direct <br></td>
											<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strGRN"
													<c:if test="${processsetupform.strGRN == 'GRN'}">checked="checked"</c:if>
													value="GRN"> GRN <br></td>
											</c:when>
											
											<c:when
												test="${processsetupform.strFormName == 'frmWorkOrder'}">
												<%-- <td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strSalesOrder"
													<c:if test="${processsetupform.strSalesOrder == 'Sales Order'}">checked="checked"</c:if>
													value="Sales Order">Sales Order<br></td> --%>
												<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strProductionOrder"
													<c:if test="${processsetupform.strProductionOrder == 'Production Order'}">checked="checked"</c:if>
													value="Production Order">Production Order<br></td>
												<%-- <td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strServiceOrder"
													<c:if test="${processsetupform.strServiceOrder == 'Service Order'}">checked="checked"</c:if>
													value="Service Order"> Service Order <br></td> --%>
												<td colspan="3"><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDirect"
													<c:if test="${processsetupform.strDirect == 'Direct'}">checked="checked"</c:if>
													value="Direct"> Direct <br></td>
											</c:when>
											
											<c:when
												test="${processsetupform.strFormName == 'frmStockReq'}">
													<td><input type="checkbox"
													name="listProcessSetupForm[${status.index}].strDirect"
													<c:if test="${processsetupform.strDirect == 'Direct'}">checked="checked"</c:if>
													value="Direct"> Direct <br></td>
												<p>aaa</p>
											</c:when>
											

										</c:choose>


									</tr>
								</c:forEach>


							</table>
							</div>
						
						<div id="tab6" class="tab_content">
						<br><br><br>
							<table class="transTable">
							<tr><th colspan="2"> &nbsp; </th></tr>
								<tr>
									<td width="100px"><label>Bank Name</label></td>
									<td><s:input path="strBankName" cssStyle="text-transform: uppercase;"  cssClass="longTextBox"/></td>
								</tr>


								<tr>
									<td><label>Branch Name</label></td>
									<td><s:input path="strBranchName" cssStyle="text-transform: uppercase;"  cssClass="longTextBox"/></td>
								</tr>

								<tr>
									<td><label>AddressLine1</label></td>
									<td><s:input path="strBankAdd1" cssStyle="text-transform: uppercase;"  cssClass="longTextBox"/></td>
								</tr>

								<tr>
									<td><label>AddressLine2</label></td>
									<td><s:input path="strBankAdd2" cssStyle="text-transform: uppercase;"  cssClass="longTextBox"/></td>
								</tr>

								<tr>
									<td><label>City</label></td>
									<td><s:input path="strBankCity" cssStyle="text-transform: uppercase;"  cssClass="BoxW116px"/></td>
								</tr>


								<tr>
									<td><label>Account Number </label></td>
									<td><s:input path="strBankAccountNo" cssClass="BoxW200px"/></td>
								</tr>


								<tr>
									<td><label>SwiftCode </label></td>
									<td><s:input path="strSwiftCode" cssClass="BoxW116px"/></td>
								</tr>
								<tr>
									<td><label> </label></td>
									<td></td>
								</tr>
							</table>

						</div>
						<div id="tab7" class="tab_content">
						<br><br>
							<table  class="transTable">
								<tr>
									<th align="left" colspan="2">Supplier Performance</th>
								</tr>
								<tr>
									<td><label>Late (Delay)</label></td><td> <s:input path="strLate" cssClass="BoxW116px"/><label>%</label>
									</td>
								</tr>


								<tr>
									<td><label>Rejection</label></td><td> <s:input path="strRej" cssClass="BoxW116px"/><label>%</label>
									</td>
								</tr>


								<tr>
									<td><label>Price Change</label></td><td> <s:input path="strPChange" cssClass="BoxW116px"/><label>%</label>
									</td>
								</tr>

								<tr>
									<td><label>Excess Delay</label></td><td> <s:input path="strExDelay" cssClass="BoxW116px"/><label>%</label>
									</td>
								</tr>



							</table>
						</div>
						<div id="tab8" class="tab_content">
						<br><br>
							<table class="transTable">
								<tr >
									<th align="left" colspan="6">Authorization(Slab Based) </th>
								</tr>
								<tr>
									<!-- 	<td><label>Form Name</label></td> -->
									<td width="100px" >Form Name</td>
									<td  width="100px"><input type="text"
										ondblclick="funHelp('treeMasterForm',this.id)"
										id="strFormName_SB" class="searchTextBox"></td>

									<td width="100px"><label >Criteria</label></td>
									<td  width="150px" ><select  id="strCriteria" 
										onchange="funOnChangeCriteria()" class="BoxW48px" style="width:50%">
											<option value="<">&lt;</option>
											<option value=">">&gt;</option>
											<option value="between">between</option>
									</select></td>
									<td  width="50px"><input type="text" id="intVal1" size="7%" class="inputText-Right" ></td>
									<td  width="50px"><input type="text" id="intVal2" size="7%" class="inputText-Right" ></td>
									</tr><tr>
									<td>Levels<select id="intLevel_SB"
										onchange="funOnChange(this.id)" class="BoxW48px">
											<option value=""></option>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
											<option value="5">5</option>
									</select></td>


									<td><s:select path="strUserCode1" items="${users}"
											id="strUser1_SB" cssClass="BoxW124px">
										</s:select>
										</td>
									<td><s:select path="strUserCode2" items="${users}"
											id="strUser2_SB" cssClass="BoxW124px">
										</s:select></td>

									<td><s:select path="strUserCode3" items="${users}"
											id="strUser3_SB" cssClass="BoxW124px">
										</s:select></td>

									<td><s:select path="strUserCode4" items="${users}"
											id="strUser4_SB" cssClass="BoxW124px">
										</s:select></td>
									<td><s:select path="strUserCode5" items="${users}"
											id="strUser5_SB" cssClass="BoxW124px">
										</s:select></td>

								</tr>
								<tr>
									<td colspan="6" align="right"><input type="button" value="Add"
										onclick="return btnAdd_onclick_SB()"  class="smallButton"/></td>
								</tr>
							</table>

							<table style="margin-left: auto;margin-right: auto;width: 95%;font-size:11px;
							font-weight: bold;">
								<tr bgcolor="#75c0ff">
									<td style="width: 18%; height: 12px;" align="center">Form
										Name</td>
									<td style="width: 9%; height: 12px;" align="center">Criteria</td>
									<td style="width: 6%; height: 12px;" align="center">Value1</td>
									<td style="width: 6%; height: 12px;" align="center">Value2</td>
									<td style="width: 10%; height: 12px;" align="center">Level</td>
									<td style="width: 10%; height: 12px;" align="center">Level
										1</td>
									<td style="width: 10%; height: 12px;" align="center">Level
										2</td>
									<td style="width: 10%; height: 12px;" align="center">Level
										3</td>
									<td style="width: 10%; height: 12px;" align="center">Level
										4</td>
									<td style="width: 10%; height: 12px;" align="center">Level
										5</td>
									<td style="width: 5%; height: 12px;" align="center">Delete</td>
								</tr>
							</table>



							<div id="divSlabBase"style="width: 95%; bgcolor: #d8edff; overflow: scroll;height: 150PX;
							margin-left: auto;margin-right: auto;">
								<table id="tblAuthorizationSlabBased" style="overflow: scroll;margin-left: auto;
	margin-right: auto;width: 100%;" class="transTablex col11-center">
									<tr>
										<td style="width: 18%; height: 12px;" align="center"></td>
										<td style="width: 8%; height: 12px;" align="center"></td>
										<td style="width: 6%; height: 12px;" align="center"></td>
										<td style="width: 6%; height: 12px;" align="center"></td>
										<td style="width: 10%; height: 12px;" align="center"></td>
										<td style="width: 10%; height: 12px;" align="center"></td>
										<td style="width: 10%; height: 12px;" align="center"></td>
										<td style="width: 10%; height: 12px;" align="center"></td>
										<td style="width: 10%; height: 12px;" align="center"></td>
										<td style="width: 10%; height: 12px;" align="center"></td>
										<td style="width: 5%; height: 12px;" align="center"></td>

									</tr>
									<c:forEach items="${listclsWorkFlowForSlabBasedAuth}"
										var="SB" varStatus="status">
										<tr>
											<td style="width: 16%; height: 12px;"><input size="27%" class="Box"  readonly="readonly"
												name="listclsWorkFlowForSlabBasedAuth[${status.index}].strFormName"
												value="${SB.strFormName}" /></td>

											<td style="width: 8%; height: 12px;"><input size="11%" class="Box" readonly="readonly"
												name="listclsWorkFlowForSlabBasedAuth[${status.index}].strCriteria"
												value="${SB.strCriteria}" /></td>

											<td style="width: 6%; height: 12px;"><input size="6%" class="Box" readonly="readonly"
												name="listclsWorkFlowForSlabBasedAuth[${status.index}].intVal1"
												value="${SB.intVal1}" /></td>


											<td style="width: 6%; height: 12px;"><input size="6%" class="Box" readonly="readonly"
												name="listclsWorkFlowForSlabBasedAuth[${status.index}].intVal2"
												value="${SB.intVal2}" /></td>

											<td style="width: 10%; height: 12px;"><input size="13%" class="Box" readonly="readonly"
												name="listclsWorkFlowForSlabBasedAuth[${status.index}].intLevel"
												value="${SB.intLevel}" /></td>
 
											<td style="width: 10%; height: 12px;"><input size="12%" class="Box" readonly="readonly"
												name="listclsWorkFlowForSlabBasedAuth[${status.index}].strUser1"
												value="${SB.strUser1}" /></td>


											<%-- <td><input
												name="llistclsWorkFlowForSlabBasedAuth[${status.index}].strUser2"
												value="${SB.strUser2}" /></td> --%>

										<td style="width: 10%; height: 12px;"><input size="12%" class="Box" readonly="readonly"
												name="listclsWorkFlowForSlabBasedAuth[${status.index}].strUser2"
												value="${SB.strUser2}" /></td>

											<td style="width: 10%; height: 12px;"><input size="12%"  class="Box" readonly="readonly"
												name="listclsWorkFlowForSlabBasedAuth[${status.index}].strUser3"
												value="${SB.strUser3}" /></td>

											<td style="width: 10%; height: 12px;"><input size="12%" class="Box" readonly="readonly"
												name="listclsWorkFlowForSlabBasedAuth[${status.index}].strUser4"
												value="${SB.strUser4}" /></td>

											<td style="width: 10%; height: 12px;"><input size="12%" class="Box" readonly="readonly"
												name="listclsWorkFlowForSlabBasedAuth[${status.index}].strUser5"
												value="${SB.strUser5}" /></td>

											<td style="width: 5%; height: 12px;"><input type="Button" value="Delete" size="5%" 
												onClick="Javacsript:funDeleteRow_SB(this)" class="deletebutton"/></td>
										</tr>
									</c:forEach>
								</table>
						
						</div>
						</div>
						
						
						<div id="tab9" class="tab_content">
						<br><br><br>
							<table id="tblAudit" class="transTable">

								<tr>
									<th colspan="7" align="left">Audit</th>
								</tr>
								
								<c:forEach items="${auditFormList}"
									var="auditsetupform" varStatus="status">
									
									<tr>
									
										<td>
										
											<input type="hidden" id="strAuditFormName.${status.index}"  name="listAuditForm[${status.index}].strFormName"
												value="${auditsetupform.strFormName}"/>
											<input name="listAuditForm[${status.index}].strFormDesc"
											value="${auditsetupform.strFormDesc}" readonly="readonly"  class="Box"/></td>
										
										<td><input type="checkbox" id="strcbAudit.${status.index}" 
													name="listAuditForm[${status.index}].strAuditForm" 
													<c:if test="${auditsetupform.strAuditForm == 'on'}"> checked="checked"</c:if>> 
												 <br></td>
									</tr>

								</c:forEach>
							

							</table>
							</div>
							
							
							<div id="tab10" class="tab_content">
							<br><br><br>
							<table id="tblAudit" class="transTable">
							<tr>
							<td><label >SMS Provider</label></td>
									<td colspan="3"><s:select  id="cmbSMSProvider" path="strSMSProvider" class="BoxW48px" style="width:130px">
											<option value="SANGUINE">SANGUINE</option>
										</s:select>
									</td>
							</tr>
							
							<tr>
							<td><label >SMS API</label></td>
								<td colspan="3"><s:textarea  id="txtSMSAPI" path="strSMSAPI" cssStyle="width: 669px;" /></td>
							</tr>
						 	<tr>
							<td style="width: 130px;"><label >SMS Content For Purchase Order</label></td>
							<td>	
									<select  id="cmbSMSField" class="BoxW48px" style="width:130px" >
										<option value="CompanyName">Company Name</option>
										<option value="PropertyName">Property Name</option>
										<option value="ContactPerson">Contact Person</option>
										<option value="PONo">Purchase No</option>
										<option value="PODate">PO Date</option>
										<option value="DeleveryDate">Delivery Date</option>
										<option value="Amount">Amount</option>
									</select>
							 </td>
							 
									<td><input type="button" value="Add" class="smallButton" onclick="funCreateSMS();" id=btnAddSMS /></td>
									<td><s:textarea cssStyle="width: 373px; height: 101px;" id="txtSMSContent" path="strSMSContent"  /></td>
							</tr> 
							
							
							
							
							</table>							
							</div>
							<div id="tab11" class="tab_content">
							
							<table class="masterTable">
							<tr><th colspan="2"></th></tr>
							<tr>
							<td ><input type="button" value="Select All Location" style="background-color :#a6d1f6;height:30px;width :40%" id="btnShowAllLocation"   onclick="return funGetLoadPropertyLocation();"></input></td>
							
							
							</tr>
							</table>
							<br>
							
							<table class="masterTable">
								<tr>
									<th style="border: 1px white solid;width: 5%"><label>Location</label></th>
									<th style="border: 1px white solid;width: 10%"><label>Location Name</label></th>
									<th style="border: 1px white solid;width: 10%"><label>From Time</label></th>
									<th style="border: 1px white solid;width: 10%"><label>To Time</label></th>
									<th style="border: 1px white solid;width: 5%"><label>Delete</label></th>									
								</tr>	
							</table>
							
							<div class="dynamicTableContainer" style="height: 246px;width: 80%; overflow-y:scroll;border: 1px solid #c0e2fe ">
							<table id="tblTransactionTable" class="masterTable" style="width: 100%">
																			
								<c:forEach items="${listTransactionTime}" var="transactionTime"
									varStatus="status">

 									<tr>
										<td style="width:15%";><input readonly="readonly" class="Box"  size="10%"
											name="listclsTransactionTimeModel[${status.index}].strLocCode"
											value="${transactionTime.strLocCode}" /></td>
										<td style="width:20%;"><input readonly="readonly" class="Box" size="20%"
											name="listclsTransactionTimeModel[${status.index}].sstrLocName"
											value="${transactionTime.strLocName}"  /></td>
										<td width="10%"><input type="text" step="any" required = "required" style="text-align: right;width:60%" size="10%"
											name="listclsTransactionTimeModel[${status.index}].tmeFrom"
											value="${transactionTime.tmeFrom}" id="txttmeFrom" onClick="funCallTime(this)"/></td>
										<td width="10%"><input type="text" step="any" required ="required" style="text-align: right;width:60%" size="10%"
											name="listclsTransactionTimeModel[${status.index}].tmeTo"
											value="${transactionTime.tmeTo}" id="txttmeTo" onClick="funCallTime(this)"/></td>
										<td width="5%"><input type="button" value="Delete" class="deletebutton"
											onClick="funDeleteRowTransactionTable(this)"  class="deletebutton"></td>
									</tr>
									
								</c:forEach>	
								</table>	
								</div>
								</div>	
						
						</div>
						
					</td>
				</tr>
			</table>

			<p align="center">
			<input type="submit" value="Submit" class="form_button" onclick="return funValidateFields()" />
			<input type="reset" value="Reset" class="form_button" /></p><br><br>
		</s:form>
	</div>
</body>
</html>