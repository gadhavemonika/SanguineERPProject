<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8"/>
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8"/>
	<link rel="stylesheet" type="text/css" href="<spring:url value="/resources/css/Accordian/jquery-ui-1.8.9.custom.css "/>" />	
<%-- 	<script type="text/javascript" src="<spring:url value="/resources/js/Accordian/jquery-ui-1.8.13.custom.min.js"/>"></script> --%>
	<script type="text/javascript" src="<spring:url value="/resources/js/Accordian/jquery.multi-accordion-1.5.3.js"/>"></script>	
		
		<style type="text/css">
		/*demo page css*/
		body{ font: 62.5% "Trebuchet MS", sans-serif; margin: 0px;}
	</style>
	
		
	
	<script type="text/javascript">
		
	$(document).ready(function() {		   	    
		
		

	    
// 	    date picker
    $("#txtdtDateofBirth").datepicker({ dateFormat: 'yy-mm-dd' });
		$("#txtdtDateofBirth" ).datepicker('setDate', 'today');
		$("#txtdtDateofBirth").datepicker();
		
        $("#txtdtBallotDate").datepicker({ dateFormat: 'yy-mm-dd' });
        $("#txtdtBallotDate" ).datepicker('setDate', 'today');
        $("#txtdtBallotDate").datepicker();
            
        $("#txtdtMembershipStartDate").datepicker({ dateFormat: 'yy-mm-dd' });
        $("#txtdtMembershipStartDate" ).datepicker('setDate', 'today');
        $("#txtdtMembershipStartDate").datepicker();
        
        $("#txtdtMembershipEndDate").datepicker({ dateFormat: 'yy-mm-dd' });
        $("#txtdtMembershipEndDate" ).datepicker('setDate', 'today');
        $("#txtdtMembershipEndDate").datepicker();
        
        $("#txtdtToDate").datepicker({ dateFormat: 'yy-mm-dd' });
        $("#txtdtToDate" ).datepicker('setDate', 'today');
        $("#txtdtToDate").datepicker();
        
        $("#txtdtToDate").datepicker({ dateFormat: 'yy-mm-dd' });
        $("#txtdtToDate" ).datepicker('setDate', 'today');
        $("#txtdtToDate").datepicker();
        
        $("#txtdtSpouseDateofBirth").datepicker({ dateFormat: 'yy-mm-dd' });
        $("#txtdtSpouseDateofBirth" ).datepicker('setDate', 'today');
        $("#txtdtSpouseDateofBirth").datepicker();
        
        $("#txtdtAnniversary").datepicker({ dateFormat: 'yy-mm-dd' });
        $("#txtdtAnniversary" ).datepicker('setDate', 'today');
        $("#txtdtAnniversary").datepicker();
        
        $("#txtdteDependentDateofBirth").datepicker({ dateFormat: 'yy-mm-dd' });
        $("#txtdteDependentDateofBirth" ).datepicker('setDate', 'today');
        $("#txtdteDependentDateofBirth").datepicker();
        
        $("#txtdteDependentMemExpDate").datepicker({ dateFormat: 'yy-mm-dd' });
        $("#txtdteDependentMemExpDate" ).datepicker('setDate', 'today');
        $("#txtdteDependentMemExpDate").datepicker();
        
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
            
            
       
            
            $('#cmbMaritalStatus').change(function () {
            	var memcode=$('#txtMemberCode').val();
        		var maritalStatus = $('#cmbMaritalStatus').val();
        		if(maritalStatus=="married")
        			{
	        			document.all[ "headerSpouse" ].style.display = 'block';
	        			document.all[ "divSpouse" ].style.display = 'block';
	        			
	        			
	        			$('#txtChangeDependentCode').val("03");
	        			$('#txtChangeDependentMemberCode').val(memcode);
	        			
	        			
	        			$('#txtSpouseCode').val(memcode +" 02");
	        			
	        			
	        			
        			}else
        			{
        				document.all[ "headerSpouse" ].style.display = 'none';
            			document.all[ "divSpouse" ].style.display = 'none';
            			
            			$('#txtChangeDependentCode').val("02");
            			$('#txtChangeDependentMemberCode').val(memcode);
        			}
        		
        		 
        	});
            
            
            

        });
		
	
        $(document)
                .ready(
                        function()
                        {
                            var message = '';
    <%if (session.getAttribute("success") != null) {
				            if(session.getAttribute("successMessage") != null){%>
				            message='<%=session.getAttribute("successMessage").toString()%>';
				            <%
				            session.removeAttribute("successMessage");
				            }
							boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
							session.removeAttribute("success");
							if (test) {
							%>	
				alert("Data Save successfully\n\n"+message);
			<%
			}}%>

		});
        
        
        function funResetFields()
		{
			location.reload(true); 
		}
        

		 function funloadMemberData(code)
			{
				var searchurl=getContextPath()+"/loadWebClubMemberProfileData.html?primaryCode="+code;
				//alert(searchurl);
				 $.ajax({
					        type: "GET",
					        url: searchurl,
					        success: function(response)
					        {
					        	if(response.strMemberCode=='Invalid Code')
					        	{
					        		alert("Invalid Member Code");
					        		$("#txtMemberCode").val('');
					        	}
					        	else
					        	{  
					        		funRemoveAllRows();
					        		var memberCode = response[0].strMemberCode ;
					        		var menber = memberCode.split(" ");
						        	$("#txtMemberCode").val(menber[0]);
						        	$("#txtChangeDependentMemberCode").val(menber[0]);
						        	$("#txtCustCode").val(response[0].strCustomerCode);
						        	if(response[0].strMaritalStatus=="married")
						        		{
							        		document.all[ "headerSpouse" ].style.display = 'block';
						        			document.all[ "divSpouse" ].style.display = 'block';
						        		}
						        	var isSpous=funSetSpouseData(response[1]);
						        	var k = 1;
						        	if(isSpous==true)
						        		{
						        			k =2;
						        		}
						        	
									for(var i=k;i<response.length;i++)
										{
											funAddDependentTableRow(response[i],i);
										}
						        	$("#txtFirstName").val(response[0].strFirstName);							        							        						        	
						        	$("#txtMiddleName").val(response[0].strMiddleName);
						        	$("#txtLastName").val(response[0].strLastName);
						        	$("#txtFullName").val(response[0].strFullName);
						        	
						        	$("#txtFirstName").focus();
						        	
						        	$("#txtCustomerCode").val(response[0].strCustomerCode);
						        	$("#txtPrefixCode").val(response[0].strPrefixCode);
						        	$("#txtNameOnCard").val(response[0].strNameOnCard);
						        	$("#txtResidentAddressLine1").val(response[0].strResidentAddressLine1);
						        	$("#txtResidentAddressLine2").val(response[0].strResidentAddressLine2);
						        	
						        	$("#txtResidentAddressLine3").val(response[0].strResidentAddressLine3);
						        	$("#txtResidentLandMark").val(response[0].strResidentLandMark);
						        	$("#txtResidentAreaCode").val(response[0].strResidentAreaCode);
						        	$("#txtResidentCtCode").val(response[0].strResidentCtCode);
						        	$("#txtResidentStateCode").val(response[0].strResidentStateCode);
						        	
						        	$("#txtResidentRegionCode").val(response[0].strResidentRegionCode);
						        	$("#txtResidentCountryCode").val(response[0].strResidentCountryCode);
						        	$("#txtResidentPinCode").val(response[0].strResidentPinCode);
						        	$("#txtResidentTelephone1").val(response[0].strResidentTelephone1);
						        	$("#txtResidentTelephone2").val(response[0].strResidentTelephone2);
						        	
						        	$("#txtResidentFax1").val(response[0].strResidentFax1);
						        	$("#txtResidentFax2").val(response[0].strResidentFax2);
						        	$("#txtResidentMobileNo").val(response[0].strResidentMobileNo);
						        	$("#txtResidentEmailID").val(response[0].strResidentEmailID);
						        	$("#txtCompanyCode").val(response[0].strCompanyCode);
						        	
						        	$("#txtCompanyName").val(response[0].strCompanyName);
						        	$("#txtHoldingCode").val(response[0].strHoldingCode);
						        	$("#txtJobProfileCode").val(response[0].strJobProfileCode);
						        	$("#txtCompanyAddressLine1").val(response[0].strCompanyAddressLine1);
						        	$("#txtCompanyAddressLine2").val(response[0].strCompanyAddressLine2);
						        	
						        	$("#txtCompanyAddressLine3").val(response[0].strCompanyAddressLine3);
						        	$("#txtCompanyLandMark").val(response[0].strCompanyLandMark);
						        	$("#txtCompanyAreaCode").val(response[0].strCompanyAreaCode);
						        	$("#txtCompanyCtCode").val(response[0].strCompanyCtCode);
						        	$("#txtCompanyStateCode").val(response[0].strCompanyStateCode);
						        	
						        	$("#txtCompanyRegionCode").val(response[0].strCompanyRegionCode);
						        	$("#txtCompanyCountryCode").val(response[0].strCompanyCountryCode);
						        	$("#txtCompanyPinCode").val(response[0].strCompanyPinCode);
						        	$("txtCompanyTelePhone1").val(response[0].strCompanyTelePhone1);
						        	$("#txtCompanyTelePhone2").val(response[0].strCompanyTelePhone2);
						        	
						        	$("#txtCompanyFax1").val(response[0].strCompanyFax1);
						        	$("#txtCompanyFax2").val(response[0].strCompanyFax2);
						        	$("#txtCompanyMobileNo").val(response[0].strCompanyMobileNo);
						        	$("#txtCompanyEmailID").val(response[0].strCompanyEmailID);
						        	$("#txtBillingAddressLine1").val(response[0].strBillingAddressLine1);
						        	
						        	$("#txtBillingAddressLine2").val(response[0].strBillingAddressLine2);
						        	$("#txtBillingAddressLine3").val(response[0].strBillingAddressLine3);
						        	$("#txtBillingLandMark").val(response[0].strBillingLandMark);
						        	$("#txtBillingAreaCode").val(response[0].strBillingAreaCode);
						        	$("#txtBillingCtCode").val(response[0].strBillingCtCode);
						       
						        	$("#txtBillingStateCode").val(response[0].strBillingStateCode);
						        	$("#txtBillingRegionCode").val(response[0].strBillingRegionCode);
						        	$("#txtBillingCountryCode").val(response[0].strBillingCountryCode);
						        	$("#txtBillingPinCode").val(response[0].strBillingPinCode);
						        	$("#txtBillingTelePhone1").val(response[0].strBillingTelePhone1);
						        	
						        	$("#txtBillingTelePhone2").val(response[0].strBillingTelePhone2);
						        	$("#txtBillingFax1").val(response[0].strBillingFax1);
						        	$("#txtBillingFax2").val(response[0].strBillingFax2);
						        	$("#txtBillingMobileNo").val(response[0].strBillingMobileNo);
						        	$("#txtBillingEmailID").val(response[0].strBillingEmailID);
						        	
						        	$("#txtBillingFlag").val(response[0].strBillingFlag);
						        	$("#txtGender").val(response[0].strGender);
						        	
						        	var dob=response.dteDateofBirth;						        	
						        	//alert("DOB="+dob);
						        	
						        	
						            $("#txtdtDateofBirth").datepicker({dateFormat: 'dd-mm-yy'}).datepicker('setDate', dob);						        	
						        	$("#txtdtDateofBirth").val(response[0].dteDateofBirth);
						        	$("#cmbMaritalStatus").val(response[0].strMaritalStatus);
						        	$("#txtProfessionCode").val(response[0].strProfessionCode);
						        	
						        	$("#txtMSCategoryCode").val(response[0].strCategoryCode);
						        	$("#txtPanNumber").val(response[0].strPanNumber);
						        	$("#txtProposerCode").val(response[0].strProposerCode);
						        	$("#txtSeconderCode").val(response[0].strSeconderCode);
						        	$("#txtBlocked").val(response[0].strBlocked);
						        	
						        	$("#txtBlockedreasonCode").val(response[0].strBlockedreasonCode);
						        	$("#txtQualification").val(response[0].strQualification);
						        	$("#txtDesignationCode").val(response[0].strDesignationCode);
						        	$("#txtLocker").val(response[0].strLocker);
						        	$("#txtLibrary").val(response[0].strLibrary);
						        	
						        	$("#txtInstation").val(response[0].strInstation);
						        	$("#txtSeniorCitizen").val(response[0].strSeniorCitizen);
						        	$("#txtdblEntranceFee").val(response[0].dblEntranceFee);
						        	$("#txtdblSubscriptionFee").val(response[0].dblSubscriptionFee);
						        	$("#txtGolfMemberShip").val(response[0].strGolfMemberShip);
						        	
						        	$("#txtStopCredit").val(response[0].strStopCredit);
						        	$("txtFatherMemberCode").val(response[0].strFatherMemberCode);
						        	//$("#txtStatusCode").val(response.strStatusCode);
						        	
						        	
						        	
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
		 
		 
		 function funSetSpouseData(response)
		 {
			 var flgISSpouse =false;
			 if(!(response.dteAnniversary=="" || response.dteAnniversary==null) )
			 {
				 $("#txtSpouseCode").val(response.strMemberCode);
				 $("#txtSpouseFirstName").val(response.strFirstName);
				 $("#txtSpouseMiddleName").val(response.strMiddleName);
				 $("#txtSpouseLastName").val(response.strLastName);
				 $("#txtSpouseProfessionName").val(response.strProfessionCode);
				 $("#txtdtSpouseDateofBirth").val(response.dteDateofBirth);
				 $("#txtSpouseCompanyCode").val(response.strCompanyCode);
				 $("#txtSpouseJobProfileCode").val(response.strJobProfileCode);
				 $("#txtdtAnniversary").val(response.dteAnniversary);
				 $("#cmbSpouseFacilityBlock").val(response.strBlocked);
				 $("#cmbSpouseStopCredit").val(response.strStopCredit);
				 $("#txtSpouseCustCode").val(response.strCustomerCode);
 			 	 $("#txtSpouseProfessionCode").val(response.strProfessionCode);
				 $("#txtSpouseResidentEmailID").val(response.strResidentEmailID);
	 			 $("#txtSpouseResidentMobileNo").val(response.strResidentMobileNo);
	// 			 $("#").val();
	 			flgISSpouse =true;
			 }
			 return flgISSpouse; 
		 }
		 
		 

		 
		 function funHelp(transactionName)
			{
				fieldName=transactionName;
			//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
				window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
			}
		 
		 
	function funSetData(code){

		switch(fieldName){

			case 'WCResAreaMaster' : 
				funSetResAreaCode(code);
				break;
			case 'WCComAreaMaster' : 
				funSetComAreaCode(code);
				break;
			case 'WCBillingAreaMaster' : 
				funSetBillingAreaCode(code);
				break;
				
				
			case 'WCResCityMaster' : 
				funSetResCityCode(code);
				break;
			case 'WCComCityMaster' : 
				funSetComCityCode(code);
				break;
			case 'WCBillingCityMaster' : 
				funSetBillingCityCode(code);
				break;
			
			case 'WCResCountryMaster' : 
				funSetResCountryCode(code);
				break;
			case 'WCComCountryMaster' : 
				funSetComCountryCode(code);
				break;
			case 'WCBillingCountryMaster' : 
				funSetBillingCountryCode(code);
				break;
			
			case 'WCResStateMaster' : 
				funSetResStateCode(code);
				break;
				
			case 'WCComStateMaster' : 
				funSetComStateCode(code);
				break;
				
			case 'WCBillingStateMaster' : 
				funSetBillingStateCode(code);
				break;
				
			
			case 'WCResRegionMaster' : 
				funSetResRegionCode(code);
				break;
			case 'WCComRegionMaster' : 
				funSetComRegionCode(code);
				break;
			case 'WCBillingRegionMaster' : 
				funSetBillingRegionCode(code);
				break;
				
			case 'WCmemProfile' :
				funloadMemberData(code);
				break;
			
			case 'WCmemProfileCustomer' :
				funloadMemberData(code);
				//funloadMemberCustomerData(code);
				break;	
				
				
			case 'WCEducationMaster' : 
				funSetEducationCode(code);
				break;
			case 'WCMaritalMaster' : 
				funSetMaritalCode(code);
				break;
			case 'WCProfessionMaster' : 
				funSetProfessionCode(code);
				break;
			case 'WCDesignationMaster' : 
				funSetDesignationCode(code);
				break;
			case 'WCReasonMaster' : 
				funSetReasonCode(code);
				break;
			case 'WCBlockReasonMaster' : 
				funSetBlockReasonCode(code);
				break;	
				
			case 'WCProfessionMaster' :
				funSetProfessionForMember(code);
				break;
				
			case 'WCDependentProfessionMaster' :
				funSetProfessionForDependent(code);
				break
				
				
			case 'WCDependentReasonMaster' : 
				funSetDependentReasonCode(code);
				break;	
				
			case 'WCSpouseProfessionMaster' :	
				funSetProfessionForSpouse(code);
				break;
				
				
			case 'WCMemberForm':
		    	funSetFormNo(code);
		        break; 	
			
				
				
		}
	}
	
	
	function funSetResAreaCode(code){

		$("#txtResidentAreaCode").val(code);
		var searchurl=getContextPath()+"/loadAreaCode.html?docCode="+code;
		//alert(searchurl);
		
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strAreaCode=='Invalid Code')
		        	{
		        		alert("Invalid Group Code");
		        		$("#txtGroupCode").val('');
		        	}
		        	else
		        	{
			        	//$("#txtAreaName").val(response.strAreaName);
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
	
	function funSetComAreaCode(code){

		$("#txtCompanyAreaCode").val(code);
		var searchurl=getContextPath()+"/loadAreaCode.html?docCode="+code;
		//alert(searchurl);
		
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strAreaCode=='Invalid Code')
		        	{
		        		alert("Invalid Group Code");
		        		$("#txtGroupCode").val('');
		        	}
		        	else
		        	{
			        	$("#txtCompanyAreaName").val(response.strAreaName);
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
	
	function funSetBillingAreaCode(code){

		$("#txtBillingAreaCode").val(code);
		var searchurl=getContextPath()+"/loadAreaCode.html?docCode="+code;
		//alert(searchurl);
		
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strAreaCode=='Invalid Code')
		        	{
		        		alert("Invalid Group Code");
		        		$("#txtGroupCode").val('');
		        	}
		        	else
		        	{
			        	$("#txtBillingAreaName").val(response.strAreaName);
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
	
	
	
	
function funSetResCityCode(code){
		
		
		
		$("#txtResidentCtCode").val(code);
		var searchurl=getContextPath()+"/loadCityCode.html?docCode="+code;
		//alert(searchurl);
		
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strCityCode=='Invalid Code')
		        	{
		        		alert("Invalid City Code In");
		        		$("#txtResidentCtCode").val('');
		        	}
		        	else
		        	{
// 		        		$("#txtCityName").val(response.strCityName);
// 		        		$("#txtCityStdCode").val(response.strSTDCode);
// 		        		$("#txtStateCode").val(response.strStateCode);
// 		        		$("#txtCountryCode").val(response.strCountryCode);
		        		 
		        		
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
	
function funSetComCityCode(code){
	
	$("#txtCompanyCtCode").val(code);
	var searchurl=getContextPath()+"/loadCityCode.html?docCode="+code;
	//alert(searchurl);
	
		$.ajax({
	        type: "GET",
	        url: searchurl,
	        dataType: "json",
	        success: function(response)
	        {
	        	if(response.strCityCode=='Invalid Code')
	        	{
	        		alert("Invalid City Code In");
	        		$("#txtResidentCtCode").val('');
	        	}
	        	else
	        	{
		        		$("#txtCompanyCtName").val(response.strCityName);
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

function funSetBillingCityCode(code){
	
	$("#txtBillingCtCode").val(code);
	var searchurl=getContextPath()+"/loadCityCode.html?docCode="+code;
	//alert(searchurl);
	
		$.ajax({
	        type: "GET",
	        url: searchurl,
	        dataType: "json",
	        success: function(response)
	        {
	        	if(response.strCityCode=='Invalid Code')
	        	{
	        		alert("Invalid City Code In");
	        		$("#txtResidentCtCode").val('');
	        	}
	        	else
	        	{
		        		$("#txtBillingCtName").val(response.strCityName);
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

function funSetResCountryCode(code){
	    
	$("#txtResidentCountryCode").val(code);
	var searchurl=getContextPath()+"/loadCountryCode.html?docCode="+code;
	//alert(searchurl);
	
		$.ajax({
	        type: "GET",
	        url: searchurl,
	        dataType: "json",
	        success: function(response)
	        {
	        	if(response.strCountryCode=='Invalid Code')
	        	{
	        		alert("Invalid Country Code In");
	        		$("#txtCountryCode").val('');
	        	}
	        	else
	        	{
	        		//$("#txtCountryName").val(response.strCountryName);
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
function funSetComCountryCode(code){
	  
	$("#txtCompanyCountryCode").val(code);
	var searchurl=getContextPath()+"/loadCountryCode.html?docCode="+code;
	//alert(searchurl);
	
		$.ajax({
	        type: "GET",
	        url: searchurl,
	        dataType: "json",
	        success: function(response)
	        {
	        	if(response.strCountryCode=='Invalid Code')
	        	{
	        		alert("Invalid Country Code In");
	        		$("#txtCountryCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtCompanyCountryName").val(response.strCountryName);
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

function funSetBillingCountryCode(code){
	 
	$("#txtBillingCountryCode").val(code);
	var searchurl=getContextPath()+"/loadCountryCode.html?docCode="+code;
	//alert(searchurl);
	
		$.ajax({
	        type: "GET",
	        url: searchurl,
	        dataType: "json",
	        success: function(response)
	        {
	        	if(response.strCountryCode=='Invalid Code')
	        	{
	        		alert("Invalid Country Code In");
	        		$("#txtCountryCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtBillingCountryName").val(response.strCountryName);
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

function funSetResStateCode(code){

	$("#txtResidentStateCode").val(code);
	var searchurl=getContextPath()+"/loadStateCode.html?docCode="+code;
	//alert(searchurl);
	
		$.ajax({
	        type: "GET",
	        url: searchurl,
	        dataType: "json",
	        success: function(response)
	        {
	        	if(response.strStateCode=='Invalid Code')
	        	{
	        		alert("Invalid State Code In");
	        		$("#txtStateCode").val('');
	        	}
	        	else
	        	{
	        		//$("#txtStateName").val(response.strStateName);
	        		
	        		 
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
	
function funSetComStateCode(code){
	  
	$("#txtCompanyStateCode").val(code);
	var searchurl=getContextPath()+"/loadStateCode.html?docCode="+code;
	//alert(searchurl);
	
		$.ajax({
	        type: "GET",
	        url: searchurl,
	        dataType: "json",
	        success: function(response)
	        {
	        	if(response.strStateCode=='Invalid Code')
	        	{
	        		alert("Invalid State Code In");
	        		$("#txtCompanyStateCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtCompanyStateName").val(response.strStateName);
	        		 
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


function funSetBillingStateCode(code){
	  
	$("#txtBillingStateCode").val(code);
	var searchurl=getContextPath()+"/loadStateCode.html?docCode="+code;
	//alert(searchurl);
	
		$.ajax({
	        type: "GET",
	        url: searchurl,
	        dataType: "json",
	        success: function(response)
	        {
	        	if(response.strStateCode=='Invalid Code')
	        	{
	        		alert("Invalid State Code In");
	        		$("#txtStateCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtBillingStateName").val(response.strStateName);
	        		 
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

function funSetResRegionCode(code){
	
	$("#txtResidentRegionCode").val(code);
	var searchurl=getContextPath()+"/loadRegionCode.html?docCode="+code;
	//alert(searchurl);
	
		$.ajax({
	        type: "GET",
	        url: searchurl,
	        dataType: "json",
	        success: function(response)
	        {
	        	if(response.strRegionCode=='Invalid Code')
	        	{
	        		alert("Invalid Region Code In");
	        		$("#txtRegionCode").val('');
	        	}
	        	else
	        	{
	        		//$("#txtRegionName").val(response.strRegionName);
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

function funSetComRegionCode(code){
	
	$("#txtCompanyRegionCode").val(code);
	var searchurl=getContextPath()+"/loadRegionCode.html?docCode="+code;
	//alert(searchurl);
	    
		$.ajax({
	        type: "GET",
	        url: searchurl,
	        dataType: "json",
	        success: function(response)
	        {
	        	if(response.strRegionCode=='Invalid Code')
	        	{
	        		alert("Invalid Region Code In");
	        		$("#txtRegionCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtCompanyRegionName").val(response.strRegionName);
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

function funSetBillingRegionCode(code){
	
	$("#txtBillingRegionCode").val(code);
	var searchurl=getContextPath()+"/loadRegionCode.html?docCode="+code;
	//alert(searchurl);
	
		$.ajax({
	        type: "GET",
	        url: searchurl,
	        dataType: "json",
	        success: function(response)
	        {
	        	if(response.strRegionCode=='Invalid Code')
	        	{
	        		alert("Invalid Region Code In");
	        		$("#txtRegionCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtBillingRegionName").val(response.strRegionName);
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

	function btnAdd_onclick() 
	{	
		var dependentMemberCode=$("#txtChangeDependentMemberCode").val();
	    var ChangedependentCode =$("#txtChangeDependentCode").val();
	    var genratedMemberCode = "";
	    genratedMemberCode = dependentMemberCode+" "+ ChangedependentCode;
		
		if(funDuplicateMember(genratedMemberCode))
			{
	   		funAddDependentRow();
			}
	}
	function funAddDependentRow() 
	{
				var table = document.getElementById("tblDependentData");
				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);
// 				var name = $("#tblDependentData").find('tr:first').find('td:first').text();
// 				alert(name);
// 				alert("Hello "+$(this).find('td:first').text())
				
				
				var dependentCode = $("#txtDependentCode").val();
				var dependentName = $("#txtDependentName").val()
				var dependentMemberCode=$("#txtChangeDependentMemberCode").val();
			    var ChangedependentCode =$("#txtChangeDependentCode").val();
			    var genratedMemberCode = "";
			    var typeMember = "";
			    genratedMemberCode = dependentMemberCode+" "+ ChangedependentCode;
			    
			    var relation = $("#txtDependentRelation").val();
			    var gender = $("#cmbDependentGender").val();
			    
			    var mStatus  = $("#cmbDependentMaritalStatus").val();
			    var DOB = $("#txtdteDependentDateofBirth").val();
			    var memExpDate = $("#txtdteDependentMemExpDate").val();
			    var blocked = $("#cmbDependentBlock").val();
			    var blockedReason = $("#txtDependentReasonCode").val();
			    var profession = $("#txtDependentProfessionCode").val();
			    
			    
			    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listDependentMember["+(rowCount)+"].strMemberCode\" id=\"txttblDependentMemberCode."+(rowCount)+"\" value='"+genratedMemberCode+"' onclick=\"funRowClick()\" >";
			    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listDependentMember["+(rowCount)+"].strDependentFullName\" id=\"txttblDependentName."+(rowCount)+"\" value='"+dependentName+"'>";
			    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listDependentMember["+(rowCount)+"].strDepedentRelation\" id=\"txttblDepedentRelation."+(rowCount)+"\" value='"+relation+"'>";
			    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listDependentMember["+(rowCount)+"].strGender\" id=\"txttblDependentGender."+(rowCount)+"\" value='"+gender+"'>";
			    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listDependentMember["+(rowCount)+"].strMaritalStatus\" id=\"txttblDependentMaritalStatus."+(rowCount)+"\" value='"+mStatus+"'>";
			    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listDependentMember["+(rowCount)+"].dteDependentDateofBirth\" id=\"txttblDependentDateofBirth."+(rowCount)+"\" value='"+DOB+"'>";
			    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listDependentMember["+(rowCount)+"].strBlocked\" id=\"txttblDependentBlocked."+(rowCount)+"\" value='"+blocked+"'>";
			    row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"5%\" name=\"listDependentMember["+(rowCount)+"].strDependentReasonCode\" id=\"txttblDependentReasonCode."+(rowCount)+"\" value='"+blockedReason+"'>";
			    row.insertCell(8).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"5%\" name=\"listDependentMember["+(rowCount)+"].strProfessionCode\" id=\"txttblProfessionCode."+(rowCount)+"\" value='"+profession+"'>";
			    row.insertCell(9).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"5%\" name=\"listDependentMember["+(rowCount)+"].dteMembershipExpiryDate\" id=\"txttbldteMembershipExpiryDate."+(rowCount)+"\" value='"+memExpDate+"'>";
			       
			    var dependentNumber = parseInt(ChangedependentCode) + 1;
			    if(dependentNumber<10)
			    	{
			    		dependentNumber = "0" + dependentNumber;
			    	}
			    $("#txtChangeDependentCode").val(dependentNumber);
			    
			 
			   return false;
	}

	function funAddDependentTableRow(response,i)
		{
				var table = document.getElementById("tblDependentData");
				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);
				
			    var genratedMemberCode = response.strMemberCode;
			    var dependentName = response.strFullName;
			    var relation = response.strDepedentRelation;
			    var gender = response.strGender;
			    var mStatus  = response.strMaritalStatus;
			    var DOB = response.dteDateofBirth ;
			    var memExpDate = response.dteDateofBirth;
			    var blocked = response.strBlocked;
			    var blockedReason = response.strBlockedreasonCode;
			    var profession = response.strProfessionCode;
			    var customerCode = response.strCustomerCode;
			    if(genratedMemberCode==null)
			    	{
			    		genratedMemberCode="";
			    	}
			    if(dependentName==null)
		    	{
			    	dependentName="";
		    	}
			    
			    if(relation==null)
		    	{
			    	relation="";
		    	}
			    
			    if(gender==null)
		    	{
			    	gender="";
		    	}
			    
			    if(mStatus==null)
		    	{
			    	mStatus="";
		    	}
			    
			    if(DOB==null)
		    	{
			    	DOB="";
		    	}
			    
			    if(memExpDate==null)
		    	{
			    	memExpDate="";
		    	}
			    
			    if(blocked==null)
		    	{
			    	blocked="";
		    	}
			    
			    if(blockedReason==null)
		    	{
			    	blockedReason="";
		    	}
			    
			    if(profession==null)
		    	{
			    	profession="";
		    	}
			    
			    if(customerCode==null)
		    	{
			    	customerCode="";
		    	}
			    
			    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listDependentMember["+(rowCount)+"].strMemberCode\" id=\"txttblDependentMemberCode."+(rowCount)+"\" value='"+genratedMemberCode+"' onclick=\"funRowClick(this)\"  >";
			    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listDependentMember["+(rowCount)+"].strDependentFullName\" id=\"txttblDependentName."+(rowCount)+"\" value='"+dependentName+"' onclick=\"funRowClick(this)\">";
			    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listDependentMember["+(rowCount)+"].strDepedentRelation\" id=\"txttblDepedentRelation."+(rowCount)+"\" value='"+relation+"' onclick=\"funRowClick(this)\">";
			    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listDependentMember["+(rowCount)+"].strGender\" id=\"txttblDependentGender."+(rowCount)+"\" value='"+gender+"' onclick=\"funRowClick(this)\">";
			    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listDependentMember["+(rowCount)+"].strMaritalStatus\" id=\"txttblDependentMaritalStatus."+(rowCount)+"\" value='"+mStatus+"' onclick=\"funRowClick(this)\">";
			    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listDependentMember["+(rowCount)+"].dteDependentDateofBirth\" id=\"txttblDependentDateofBirth."+(rowCount)+"\" value='"+DOB+"' onclick=\"funRowClick(this)\">";
			    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listDependentMember["+(rowCount)+"].strBlocked\" id=\"txttblDependentBlocked."+(rowCount)+"\" value='"+blocked+"' onclick=\"funRowClick(this)\">";
			    row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"5%\" name=\"listDependentMember["+(rowCount)+"].strDependentReasonCode\" id=\"txttblDependentReasonCode."+(rowCount)+"\" value='"+blockedReason+"' onclick=\"funRowClick(this)\">";
			    row.insertCell(8).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"5%\" name=\"listDependentMember["+(rowCount)+"].strProfessionCode\" id=\"txttblProfessionCode."+(rowCount)+"\" value='"+profession+"' onclick=\"funRowClick(this)\">";
			    row.insertCell(9).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"5%\" name=\"listDependentMember["+(rowCount)+"].dteMembershipExpiryDate\" id=\"txttbldteMembershipExpiryDate."+(rowCount)+"\" value='"+memExpDate+"' onclick=\"funRowClick(this)\">";
			    row.insertCell(10).innerHTML= "<input Type=\"hidden\" readonly=\"readonly\" class=\"Box\" size=\"1%\" name=\"listDependentMember["+(rowCount)+"].strCustomerCode\" id=\"txttblCustomerCode."+(rowCount)+"\" value='"+customerCode+"' onclick=\"funRowClick(this)\">";
			    
				genratedMemberCode=genratedMemberCode.split(" ");


				
				var  nextdependentCode =1+parseInt(genratedMemberCode[1]);
				if(nextdependentCode<10)
			    	{
					nextdependentCode = "0" + nextdependentCode;
			    	}
				
			    $("#txtChangeDependentCode").val(nextdependentCode);
			    
			   return false;
	}
	


	function funResetTableData()
	{
		
		funDeleteTableAllRows(); 
		$('txtDependentName').val("");
		
		var dependentMemberCode=$("#txtChangeDependentMemberCode").val();
		 var fullName = $('#txtFullName').val();
		 var typeMember = "Primary";
		 var table = document.getElementById("tblDependentData");
		 var dependentcode = dependentMemberCode+" 01";
		 var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listDependentMember["+(rowCount)+"].strDependentMemberCode\" id=\"txtDependentMemberCode."+(rowCount)+"\" value='"+dependentcode+"'>";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listDependentMember["+(rowCount)+"].strDependentName\" id=\"txtDependentName."+(rowCount)+"\" value='"+fullName+"'>";
		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listDependentMember["+(rowCount)+"].strDependentMemberType\" id=\"txtDependentMemberType."+(rowCount)+"\" value='"+typeMember+"'>";
		 
		    $("#txtChangeDependentCode").val("02");  
		
	}
	
	function funFillDependentMasterTableData(docCode)
	{
		
// 		searchUrl=getContextPath()+"/loadDependentMasterData.html?docCode="+docCode;
// 		//alert(searchUrl);
// 		$.ajax({
// 		        type: "GET",
// 		        url: searchUrl,
// 			    dataType: "json",
			
// 			    success: function(response)
// 			    {
//     	    		funDeleteTableAllRows();
// 			    	$.each(response, function(i,item)
// 					{
// 			    		funLoadDependentTableData(response[i]);
// 					}); 
							
					    	
							
// 			    },
// 			    error: function(jqXHR, exception) {
// 		            if (jqXHR.status === 0) {
// 		                alert('Not connect.n Verify Network.');
// 		            } else if (jqXHR.status == 404) {
// 		                alert('Requested page not found. [404]');
// 		            } else if (jqXHR.status == 500) {
// 		                alert('Internal Server Error [500].');
// 		            } else if (exception === 'parsererror') {
// 		                alert('Requested JSON parse failed.');
// 		            } else if (exception === 'timeout') {
// 		                alert('Time out error.');
// 		            } else if (exception === 'abort') {
// 		                alert('Ajax request aborted.');
// 		            } else {
// 		                alert('Uncaught Error.n' + jqXHR.responseText);
// 		            }
// 		        }
// 		      });
	}
	
	
	
	
	
	
	
	function funLoadDependentTableData(rowData)
	{
		var table = document.getElementById("tblDependentData");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
		
		var dependentcode = rowData.strDependentMemberCode;
		var fullName = rowData.strDependentName;
		var arr = dependentcode.split(' ');
		var lastDependentCode = parseInt(arr[1]) +1;
		
		if(lastDependentCode<10)
    	{
			lastDependentCode = "0" + lastDependentCode;
    	}
		
		$('#txtChangeDependentCode').val(lastDependentCode);
		

		if(dependentcode == null){
			dependentcode="";
		}
		
		if(lastDependentCode == null){
			lastDependentCode="";
		}
		
		if(fullName == null){
			fullName="";
		}
		
		var strType="Primary"
		row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listDependentMember["+(rowCount)+"].strDependentMemberCode\" id=\"txtDependentMemberCode."+(rowCount)+"\" value='"+dependentcode+"'>";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listDependentMember["+(rowCount)+"].strDependentName\" id=\"txtDependentName."+(rowCount)+"\" value='"+fullName+"'>";
	   
	    if(lastDependentCode==01)
	    	{
	    		row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\"  id=\"txtTypeName."+(rowCount)+"\" value='"+strType+"'>";
	    	
	    	}
	    else
	    {
	    	row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\"  id=\"txtTypeName."+(rowCount)+"\" value=''>";
	    }
	    
	    
	}

	
	function funDeleteTableAllRows()
	{
		$("#tblDependentData tr").remove();
	}

	
	function funSetEducationCode(code){

		$("#txtQualification").val(code);
		var searchurl=getContextPath()+"/loadEducation.html?docCode="+code;
		//alert(searchurl);
		
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strEducationCode=='Invalid Code')
		        	{
		        		alert("Invalid Qualification Code In");
		        		$("#txtQualification").val('');
		        	}
		        	else
		        	{
		        		$("#txtQualificationName").val(response.strEducationDesc);
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
	
	
	function funSetProfessionCode(code){

		$("#txtProfessionCode").val(code);
		var searchurl=getContextPath()+"/loadProfession.html?docCode="+code;
		//alert(searchurl);
		
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strProfessionCode=='Invalid Code')
		        	{
		        		alert("Invalid Profession Code In");
		        		$("#txtProfessionCode").val('');
		        	}
		        	else
		        	{
		        		$("#txtProfessionName").val(response.strProfessionName);
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
	
	function funSetDesignationCode(code){

		$("#txtDesignationCode").val(code);
		var searchurl=getContextPath()+"/loadDesignation.html?docCode="+code;
		//alert(searchurl);
		
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strDesignationCode=='Invalid Code')
		        	{
		        		alert("Invalid Designation Code In");
		        		$("#txtDesignationCode").val('');
		        	}
		        	else
		        	{
		        		$("#txtDesignationName").val(response.strDesignationName);
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
	
	
	function funSetReasonCode(code){

		$("#txtReasonCode").val(code);
		var searchurl=getContextPath()+"/loadReason.html?docCode="+code;
		//alert(searchurl);
		
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strReasonCode=='Invalid Code')
		        	{
		        		alert("Invalid Reason Code In");
		        		$("#txtReasonCode").val('');
		        	}
		        	else
		        	{
		        		$("#txtReasonName").val(response.strReasonDesc);
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
	
	function funSetBlockReasonCode(code){

		$("#txtBlockedReasonCode").val(code);
		var searchurl=getContextPath()+"/loadReason.html?docCode="+code;
		//alert(searchurl);
		
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strReasonCode=='Invalid Code')
		        	{
		        		alert("Invalid Blocked Reason Code In");
		        		$("#txtBlockedReasonCode").val('');
		        	}
		        	else
		        	{
		        		$("#txtBlockedReasonName").val(response.strReasonDesc);
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
	
	function funRowClick(object)
	{

   var no = object.parentNode.parentNode.rowIndex;
    
//      var no = parseInt( $("#tblDependentData").parent().index() );
      alert(no);
//      no =  parseInt(no)-1;           
    var DependentMemberCode = document.all("txttblDependentMemberCode."+no).value
	var DependentName = document.all("txttblDependentName."+no).value
	var relation = document.all("txttblDepedentRelation."+no).value
	var gender = document.all("txttblDependentGender."+no).value
	var mStatus = document.all("txttblDependentMaritalStatus."+no).value
	var DOB = document.all("txttblDependentDateofBirth."+no).value
	var blocked = document.all("txttblDependentBlocked."+no).value
	var blockedReason = document.all("txttblDependentReasonCode."+no).value
	var profession = document.all("txttblProfessionCode."+no).value
	var memExpDate = document.all("txttbldteMembershipExpiryDate."+no).value
	var customerCode = document.all("txttblCustomerCode."+no).value
    
	DependentMemberCode = DependentMemberCode.split(" ");
	
    
    $("#txtDependentCode").val(DependentMemberCode);
	$("#txtDependentName").val(DependentName)
	$("#txtDependentRelation").val(relation);
	$("#cmbDependentGender").val(gender);
	$("#cmbDependentMaritalStatus").val(mStatus);
	$("#txtdteDependentDateofBirth").val(DOB);
	$("#cmbDependentBlock").val(blocked);
	$("#txtDependentReasonCode").val(blockedReason);
	$("#txtDependentProfessionCode").val(profession);
	$("#txtdteDependentMemExpDate").val(memExpDate);
	 
	$("#txtChangeDependentMemberCode").val(DependentMemberCode[0]);
    $("#txtChangeDependentCode").val(DependentMemberCode[1]);
    
    $("#txttblCustomerCode").val(customerCode);
   
	}
	
	function funRemoveAllRows() 
    {
		 var table = document.getElementById("tblDependentData");
		 var rowCount = table.rows.length;			   
		//alert("rowCount\t"+rowCount);
		for(var i=rowCount-1;i>=0;i--)
		{
			table.deleteRow(i);						
		} 
    }
	
	
	function funSetProfessionForMember(code){

		$("#txtProfessionCode").val(code);
		var searchurl=getContextPath()+"/loadProfession.html?docCode="+code;
		//alert(searchurl);
		
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strProfessionCode=='Invalid Code')
		        	{
		        		alert("Invalid Profession Code In");
		        		$("#txtProfessionCode").val('');
		        	}
		        	else
		        	{
		        		$("#txtProfessionName").val(response.strProfessionName);
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
	
	function funSetProfessionForDependent(code){

		$("#txtDependentProfessionCode").val(code);
		var searchurl=getContextPath()+"/loadProfession.html?docCode="+code;
		//alert(searchurl);
		
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strProfessionCode=='Invalid Code')
		        	{
		        		alert("Invalid Profession Code In");
		        		$("#txtDependentProfessionCode").val('');
		        	}
		        	else
		        	{
		        		$("#strDependentProfessionName").val(response.strProfessionName);
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
	
	function funSetDependentReasonCode(code){

		$("#txtDependentReasonCode").val(code);
		var searchurl=getContextPath()+"/loadReason.html?docCode="+code;
		//alert(searchurl);
		
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strReasonCode=='Invalid Code')
		        	{
		        		alert("Invalid Reason Code In");
		        		$("#txtDependentReasonCode").val('');
		        	}
		        	else
		        	{
		        		$("#txtDependentReasonName").val(response.strReasonDesc);
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
	
	function funSetProfessionForSpouse(code){

		$("#txtSpouseProfessionCode").val(code);
		var searchurl=getContextPath()+"/loadProfession.html?docCode="+code;
		//alert(searchurl);
		
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strProfessionCode=='Invalid Code')
		        	{
		        		alert("Invalid Profession Code In");
		        		$("#txtSpouseProfessionCode").val('');
		        	}
		        	else
		        	{
		        		$("#txtSpouseProfessionName").val(response.strProfessionName);
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
	
	
	
	
	function funDuplicateMember(memberCode)
	{
	    var table = document.getElementById("tblDependentData");
	    var rowCount = table.rows.length;		   
	    var flag=true;
	    if(rowCount > 0)
	    	{
			    $('#tblDependentData tr').each(function()
			    {
				    if(memberCode==$(this).find('input').val())// `this` is TR DOM element
    				{
				    	alert("Already added "+ memberCode);
				    	 //funClearReqData();
	    				flag=false;
    				}
				});
			    
	    	}
	    return flag;
	  
	}
	
	function funProceed(){

		var formNo=$("#txtFormNo").val();
		if(formNo.length>0)
			{
			
				document.all[ 'multiAccordion' ].style.display = 'block';
				document.all[ 'paraSubmit' ].style.display = 'block';
				
				document.all[ 'uitblFrom' ].style.display = 'none';
				document.all[ 'paraProceed' ].style.display = 'none';
				
			//	location.href = "frmMemberPreProfile.html";
				//window.location.assign(getContextPath()+"/frmMemberPreProfile")
				
// 				var searchurl=getContextPath()+"/proceedMemberPreProfile.html?formNo="+formNo;
// 				//alert(searchurl);
				
// 					$.ajax({ location.href='frmMemberPreProfile.html'
// 				        type: "GET",
// 				        url: searchurl,
// 				        dataType: "json",
// 				        success: function(response)
// 				        {
// 				        	if(response.strProfessionCode=='Invalid Code')
// 				        	{
// 				        		alert("Invalid Profession Code In");
// 				        		$("#txtSpouseProfessionCode").val('');
// 				        	}
// 				        	else
// 				        	{
// 				        		$("#txtSpouseProfessionName").val(response.strProfessionName);
// 				        	}
				        	
// 				        },
// 					error: function(jqXHR, exception) {
// 			            if (jqXHR.status === 0) {
// 			                alert('Not connect.n Verify Network.');
// 			            } else if (jqXHR.status == 404) {
// 			                alert('Requested page not found. [404]');
// 			            } else if (jqXHR.status == 500) {
// 			                alert('Internal Server Error [500].');
// 			            } else if (exception === 'parsererror') {
// 			                alert('Requested JSON parse failed.');
// 			            } else if (exception === 'timeout') {
// 			                alert('Time out error.');
// 			            } else if (exception === 'abort') {
// 			                alert('Ajax request aborted.');
// 			            } else {
// 			                alert('Uncaught Error.n' + jqXHR.responseText);
// 			            }		            
// 			        }
// 				});
 			}
		else
			{
			
			}
		
	}
	
	
	
	function funSetFormNo(code)
	{
		$("#txtFormNo").val(code);
	}
	
	
	
	
		 	
</script>
		
		
		
		
		
</head>
<body >
<div id="formHeading">
	<label>Member Pre-Profile</label>
	</div>
	<div>
	<s:form name="frmMemberPreProfile" action="saveMemberPreProfile.html?saddr=${urlHits}" method="POST">
		<br>
		<table class="masterTable" id="uitblFrom">
		
		<tr>
				<td width="15%">From No.</td>
				
				<td ><s:input type="text" id="txtFormNo" 
						name="txtFormNo" path="intFormNo" required="true"
						cssStyle="width: 20%; text-transform: uppercase;" cssClass="longTextBox" ondblclick="funHelp('WCMemberForm')"  /> <s:errors path=""></s:errors></td>
		</tr>
			 
		</table> 
		
		<br>
		<p id="paraProceed" align="center">
		
		<input type="button" value="Proceed"
				onclick="funProceed()"
				class="form_button" /> &nbsp; &nbsp; &nbsp;
		</p>
		
		<div id="multiAccordion" style="display:none">	
		<h3><a href="#">Member Profile Detail</a></h3>
		<div>
					<table class="transTable">
						<tr>
							<td width="120px"><label>Member Code</label></td>
							<td width="150px"><s:input id="txtMemberCode"
									ondblclick="funHelp('WCmemProfileCustomer')" cssClass="searchTextBox"
									type="text" path="strMemberCode" ></s:input></td>
									
<!-- 							<td width="120px"><label>Customer Code</label></td> -->
<%-- 							<td width="150px"><s:input id="txtCustCode"  --%>
<%-- 									ondblclick="funHelp('WCmemProfileCustomer')" cssClass="searchTextBox" readonly="true" --%>
<%-- 									type="text" path="strCustomerCode" ></s:input></td> --%>
							
									
							<td width="120px"><label>Prefix Code</label></td>
							<td width="150px"><s:input id="txtPrefixCode" 
									ondblclick="" cssClass="searchTextBox"
									type="text" path="strPrefixCode" ></s:input></td>
			
							
							<td></td>
							<td></td>
						</tr>
					 	<tr>
							<td><label>FirstName</label></td>
							<td><s:input id="txtFirstName" path="strFirstName" 
									cssClass="longTextBox" type="text"></s:input></td>
							
							<td><label>MiddleName</label></td>
							<td><s:input id="txtMiddleName" path="strMiddleName" 
									cssClass="longTextBox" type="text"></s:input></td>			
									
							<td  width="8%"><label>LastName</label></td>
							<td><s:input id="txtLastName" path="strLastName" 
									cssStyle="width: 56%;" cssClass="longTextBox" type="text"></s:input></td>
							
						</tr>
						<tr>
							<td><label>Name On Card</label></td>
							<td><s:input id="txtNameOnCard" path="strNameOnCard" 
									cssClass="longTextBox" type="text"></s:input></td>
									
							<td><label>Full Name</label></td>
							<td colspan="4"><s:input id="txtFullName" path="strFullName" 
									cssClass="longTextBox" type="text"></s:input></td>
						</tr>
			
				</table>
				</div> 
			
			
		<h3><a href="#">Address Information</a></h3>
		<div>
		
				
		<div id="tab_container" style="height: 130px">
							<ul class="tabs">
								<li class="active" data-state="tab1" style="width: 150px;padding-left: 55px">Resident Address</li>
								<li data-state="tab2"  style="width: 150px;padding-left: 55px">Company Address</li>
								<li data-state="tab3"  style="width: 150px;padding-left: 55px">Billing Address</li>
							</ul>
							
		<div id="tab1" class="tab_content">					
		
			<table class="transTable">
			<tr>
			<th align="right" colspan="8"></th> 
			</tr>
			<tr>
			
				<td><label>Resident Address Line1</label></td>
				<td><s:input id="txtResidentAddressLine1" path="strResidentAddressLine1" required="required"
						cssClass="longTextBox" type="text"></s:input></td>
						
				<td><label>Resident Address Line2</label></td>
				<td><s:input id="txtResidentAddressLine2" path="strResidentAddressLine2" 
						cssClass="longTextBox" type="text"></s:input></td>
				
				<td><label>Resident Address Line3</label></td>
				<td colspan="4"><s:input id="txtResidentAddressLine3" path="strResidentAddressLine3"
						cssStyle="width: 56%;" cssClass="longTextBox" type="text"></s:input></td>
			</tr>
			
			<tr>
				<td><label>Resident LandMark</label></td>
				<td><s:input id="txtResidentLandMark" path="strResidentLandMark" 
						cssClass="longTextBox" type="text"></s:input></td>
						
				 <td><label>Resident Area Code</label></td>
				<td><s:input id="txtResidentAreaCode" path="strResidentAreaCode" 
						ondblclick="funHelp('WCResAreaMaster')" cssClass="searchTextBox" type="text"></s:input></td>
				
				<td><label>Resident City Code</label></td>
				<td colspan="4"><s:input id="txtResidentCtCode" path="strResidentCtCode" required="required"
						 ondblclick="funHelp('WCResCityMaster')" 
						cssClass="searchTextBox" type="text"></s:input></td>	
			
			</tr>
			
			<tr>
				<td><label>Resident State Code</label></td>
				<td><s:input id="txtResidentStateCode" path="strResidentStateCode" 
						ondblclick="funHelp('WCResStateMaster')" cssClass="searchTextBox" type="text"></s:input></td>
						
				<td><label>Resident Region Code</label></td>
				<td><s:input id="txtResidentRegionCode" path="strResidentRegionCode" 
						ondblclick="funHelp('WCResRegionMaster')" cssClass="searchTextBox" type="text"></s:input></td>
				
				<td><label>Resident Country Code</label></td>
				<td colspan="4"><s:input id="txtResidentCountryCode" path="strResidentCountryCode" 
						required="required" ondblclick="funHelp('WCResCountryMaster')" 
						cssClass="searchTextBox" type="text"></s:input></td>		
			         	
			</tr>
			<tr>
			<td><label>Resident Telephone1</label></td>
				<td><s:input id="txtResidentTelephone1" path="strResidentTelephone1" 
					class="decimal-places numberField" type="text"></s:input></td>
				
				<td><label>Resident Telephone2</label></td>
				<td><s:input id="txtResidentTelephone2" path="strResidentTelephone2" 
					class="decimal-places numberField" type="text"></s:input></td>
				
				<td><label>Resident Fax1</label></td>
				<td><s:input id="txtResidentFax1" path="strResidentFax1"  
					class="decimal-places numberField" type="text"></s:input></td>
					
				<td><label>Resident Fax2</label></td>
				<td><s:input id="txtResidentFax2" path="strResidentFax2"  
					class="decimal-places numberField" type="text"></s:input></td> 
			</tr> 
			<tr>
			<td><label>Resident PinCode</label></td>
				<td ><s:input id="txtResidentPinCode" path="strResidentPinCode"  
					class="decimal-places numberField" type="text"></s:input></td> 
			
			<td><label>Resident Mobile No</label></td>
				<td ><s:input id="txtResidentMobileNo" path="strResidentMobileNo"  
					class="decimal-places numberField" type="text"></s:input></td> 
			
			<td ><label>Resident Email ID</label></td>
				<td colspan="3"><s:input id="txtResidentEmailID" path="strResidentEmailID" 
						cssClass="longTextBox" type="text"></s:input></td>
			</tr>
		</table>
		</div>
		
		<div id="tab2" class="tab_content">
		 <table class="transTable">
		<tr>
			<th align="right" colspan="8"></th> 
			</tr>
		<tr>
							<td width="120px"><label>Company Code</label></td>
							<td width="150px"><s:input id="txtCompanyCode"
									ondblclick="" cssClass="searchTextBox"
									 type="text" path="strCompanyCode" ></s:input></td>
									
							<td><s:input id="txtCompanyName" path="strCompanyName" 
									cssClass="longTextBox" type="text"></s:input></td>
									
									
							<td width="120px"><label>Job Profile</label></td>
							<td width="150px"><s:input id="txtJobProfileCode"
									ondblclick="" cssClass="searchTextBox"
									type="text" path="strJobProfileCode" ></s:input></td>
									
							<td colspan="2"><s:input id="txtJobProfileName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
		
		</tr>
		
		<tr>
						<td width="120px"><label>Holding Code</label></td>
							<td width="150px"><s:input id="txtHoldingCode"
									ondblclick="" cssClass="searchTextBox"
									type="text" path="strHoldingCode" ></s:input></td>
									
						<td><s:input id="txtHoldingName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
						
						<td width="120px"><label>Address Line1</label></td>
						<td colspan="3"><s:input id="txtCompanyAddressLine1" path="strCompanyAddressLine1" 
									cssClass="longTextBox" type="text"></s:input></td>
		</tr>
		<tr>
						<td width="120px"><label>Address Line2</label></td>
						<td><s:input id="txtCompanyAddressLine2" path="strCompanyAddressLine2" 
									cssClass="longTextBox" type="text"></s:input></td>
									
						<td width="120px"><label>Address Line3</label></td>
						<td colspan="4"><s:input id="txtCompanyAddressLine3" path="strCompanyAddressLine3" 
									cssClass="longTextBox" type="text"></s:input></td>					
		
		</tr>
		
		<tr>
						<td ><label>Landmark</label></td>
						<td><s:input id="txtCompanyLandMark" path="strCompanyLandMark"
									cssClass="longTextBox" type="text"></s:input></td>
									
						<td ><label>Area Code</label></td>
							<td ><s:input id="txtCompanyAreaCode"
									ondblclick="funHelp('WCComAreaMaster')" cssClass="searchTextBox" 
									type="text" path="strCompanyAreaCode" ></s:input></td>
									
						<td colspan="3"><s:input id="txtCompanyAreaName" path=""
									cssClass="longTextBox" type="text"></s:input></td>
		</tr>
		<tr>
						<td ><label>City Code</label></td>
							<td ><s:input id="txtCompanyCtCode"
									ondblclick="funHelp('WCComCityMaster')" cssClass="searchTextBox" 
									type="text" path="strCompanyCtCode" ></s:input></td>
									
						<td><s:input id="txtCompanyCtName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
									
						<td ><label>State Code</label></td>
							<td ><s:input id="txtCompanyStateCode"
									ondblclick="funHelp('WCComStateMaster')" cssClass="searchTextBox" 
									type="text" path="strCompanyStateCode" ></s:input></td>
									
						<td colspan="2"><s:input id="txtCompanyStateName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>		
		</tr>
		<tr>
						<td><label>Region Code</label></td>
							<td><s:input id="txtCompanyRegionCode"
									ondblclick="funHelp('WCComRegionMaster')" cssClass="searchTextBox" 
									type="text" path="strCompanyRegionCode" ></s:input></td>
									
						<td><s:input id="txtCompanyRegionName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>		
									
						<td><label>Country Code</label></td>
						<td><s:input id="txtCompanyCountryCode" 
									ondblclick="funHelp('WCComCountryMaster')"  cssClass="searchTextBox" 
									type="text" path="strCompanyCountryCode" ></s:input></td>
									
						<td colspan="2"><s:input id="txtCompanyCountryName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>	
		</tr>
		<tr>
						<td ><label>PinCode</label></td>
						<td colspan="2"><s:input id="txtCompanyPinCode" path="strCompanyPinCode" 
									class="decimal-places numberField" type="text" style="width: 36%"></s:input></td>
									
						<td ><label>Telephone</label></td>
						<td ><s:input id="txtCompanyTelePhone1" path="strCompanyTelePhone1" 
									class="decimal-places numberField" type="text"></s:input></td>
						<td ><s:input id="txtCompanyTelePhone2" path="strCompanyTelePhone2" 
									class="decimal-places numberField" type="text"></s:input></td>
								
		</tr>
		
		<tr>
						<td ><label>Fax</label></td>
						<td><s:input id="txtCompanyFax1" path="strCompanyFax1" 
									class="decimal-places numberField" type="text"></s:input></td>
						
						<td ><s:input id="txtCompanyFax2" path="strCompanyFax2" 
									class="decimal-places numberField" type="text"></s:input></td>
									
						<td ><label>Mobile No</label></td>
						<td colspan="2"><s:input id="txtCompanyMobileNo" path="strCompanyMobileNo" 
									class="decimal-places numberField" type="text"></s:input></td>
						
		</tr>
		
		<tr>
				<td ><label>Email ID</label></td>
						<td colspan="7"><s:input id="txtCompanyEmailID" path="strCompanyEmailID" 
									cssClass="longTextBox" type="text" style="width: 56%" ></s:input></td>
						
		</tr>
						
		</table>
		</div>
		
		
		<div id="tab3" class="tab_content">
		 <table class="transTable">
		<tr>
			<th align="right" colspan="8"></th> 
			</tr>
		
		<tr>
						<td width="120px"><label>Address Line1</label></td>
						<td colspan="3"><s:input id="txtBillingAddressLine1" path="strBillingAddressLine1" 
									cssClass="longTextBox" type="text"></s:input></td>
									
						<td width="120px"><label>Address Line2</label></td>
						<td colspan="3"><s:input id="txtBillingAddressLine2" path="strBillingAddressLine2" 
									cssClass="longTextBox" type="text"></s:input></td>					
		
		</tr>
		
		<tr>
						<td width="120px"><label>Address Line3</label></td>
						<td colspan="3"><s:input id="txtBillingAddressLine3" path="strBillingAddressLine3" 
									cssClass="longTextBox" type="text"></s:input></td>
						
						<td width="120px"><label>Landmark</label></td>
						<td colspan="3"><s:input id="txtBillingLandMark" path="strBillingLandMark" 
									cssClass="longTextBox" type="text"></s:input></td>
		</tr>
		
		<tr>
							
						<td width="120px"><label>Area Code</label></td>
							<td width="150px"><s:input id="txtBillingAreaCode"
									ondblclick="funHelp('WCBillingAreaMaster')" cssClass="searchTextBox" required="required"
									type="text" path="strBillingAreaCode" ></s:input></td>
									
						<td colspan="2"><s:input id="txtBillingAreaName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
									
						<td width="120px"><label>City Code</label></td>
							<td width="150px"><s:input id="txtBillingCtCode"
									ondblclick="funHelp('WCBillingCityMaster')" cssClass="searchTextBox" required="required"
									type="text" path="strBillingCtCode" ></s:input></td>
									
						<td colspan="2"><s:input id="txtBillingCtName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
		</tr>
		
		<tr>
						<td width="120px"><label>Country Code</label></td>
							<td width="150px"><s:input id="txtBillingCountryCode"
									ondblclick="funHelp('WCBillingCountryMaster')"  cssClass="searchTextBox" 
									type="text" path="strBillingCountryCode" ></s:input></td>
						 	
						<td colspan="2"> <s:input id="txtBillingCountryName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
									
						<td width="120px"><label>Region Code</label></td>
							<td width="150px"><s:input id="txtBillingRegionCode"
									ondblclick="funHelp('WCBillingRegionMaster')" cssClass="searchTextBox" 
									type="text" path="strBillingRegionCode" ></s:input></td>
									
						<td colspan="2"><s:input id="txtBillingRegionName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
		</tr>
		
		<tr>	
						<td width="120px"><label>Telephone</label></td>
						<td><s:input id="txtBillingTelePhone1" path="strBillingTelePhone1" 
									class="decimal-places numberField" type="text"></s:input></td>
						<td colspan="2"><s:input id="txtBillingTelePhone2" path="strBillingTelePhone2" 
									class="decimal-places numberField" type="text"></s:input></td>
									
						<td width="120px"><label>Fax</label></td>
						<td><s:input id="txtBillingFax1" path="strBillingFax1" 
									class="decimal-places numberField" type="text"></s:input></td>
						<td colspan="2"> <s:input id="txtBillingFax2" path="strBillingFax2" 
									class="decimal-places numberField" type="text"></s:input></td>
						
						
		</tr>
		<tr>
						<td ><label>State Code</label></td>
							<td width="150px"><s:input id="txtBillingStateCode"
									ondblclick="funHelp('WCBillingStateMaster')" cssClass="searchTextBox"
									type="text" path="strBillingStateCode"  required="required" ></s:input></td>
									
						<td colspan="2"><s:input id="txtBillingStateName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
						
						<td ><label>Pincode</label></td>
						<td colspan="2"><s:input id="txtBillingPinCode" path="strBillingPinCode" 
									class="decimal-places numberField" type="text"></s:input></td>
									
						
									
						
		</tr>
		<tr>	
					<td ><label>Mobile</label></td>
						<td colspan="2"><s:input id="txtBillingMobileNo" path="strBillingMobileNo" 
									class="decimal-places numberField" type="text"></s:input></td>
		
					<td ><label>Email Id</label></td>			
						<td colspan="5"><s:input id="txtBillingEmailID" path="strBillingEmailID" 
									class="longTextBox" type="text" style="width: 56%"></s:input></td>
		</tr>
		
		</table>
		</div>
		
		
		
		</div>
		</div>
		
		 
		<h3><a href="#">Personal Information</a></h3>
		<div>
		  <table class="transTable">
		
		<tr>
				<td width="5%"><label>Sex</label></td>
				 <td width="3%"><s:select id="cmbGender" name="cmbGender" path="strGender" cssClass="BoxW124px" >
				 <option value="M">Male</option>
 				 <option value="F">Female</option>
				 </s:select></td>
				 
			    <td width="5%"><label>Date Of Birth</label></td>
			    <td width="5%"><s:input id="txtdtDateofBirth" name="txtdtDateofBirth" path="dteDateofBirth"  cssClass="calenderTextBox" /></td>
		
				<td width="4%"><label>Marital Status</label></td>
				<td width="11%"><s:select id="cmbMaritalStatus" name="cmbMaritalStatus" path="strMaritalStatus" cssClass="BoxW124px" >
				 <option value="Single">Single</option>
 				 <option value="married">married</option>
				 </s:select></td>
				 
		</tr>
		<tr>
				<td width="120px"><label>Profession Code</label></td>
							<td width="150px"><s:input id="txtProfessionCode"
									ondblclick="funHelp('WCProfessionMaster')" cssClass="searchTextBox"
									type="text" path="strProfessionCode" ></s:input></td>
									
						<td colspan="4"><s:input id="txtProfessionName" path="" 
									cssStyle="width: 30%%;" cssClass="longTextBox" type="text"></s:input></td>
		</tr>
				
		</table>  
		</div>
		
		<h3 id="headerSpouse" style="display:none"><a href="#">Spouse Information</a></h3>
		<div id="divSpouse" style="display:none">
			<table class="transTable">
			<tr>
			<td width="120px"><label>Spouse Code</label></td>
							<td width="150px"><s:input id="txtSpouseCode"
									ondblclick="" cssClass="searchTextBox" readonly="true"
									type="text" path="strSpouseCode" ></s:input></td>
			
						
							<td><label>FirstName</label></td>
							<td><s:input id="txtSpouseFirstName" path="strSpouseFirstName" 
									cssClass="longTextBox" type="text"></s:input></td>
							
							<td><label>MiddleName</label></td>
							<td><s:input id="txtSpouseMiddleName" path="strSpouseMiddleName" 
									cssClass="longTextBox" type="text"></s:input></td>			
									
							<td  width="8%"><label>LastName</label></td>
							<td><s:input id="txtSpouseLastName" path="strSpouseLastName" 
									cssStyle="width: 56%;" cssClass="longTextBox" type="text"></s:input></td>
			
			</tr>
			
			<tr>
				<td width="120px"><label>Profession Code</label></td>
							<td width="150px"><s:input id="txtSpouseProfessionCode"
									ondblclick="funHelp('WCSpouseProfessionMaster')" cssClass="searchTextBox"
									type="text" path="strSpouseProfessionCode" ></s:input></td>
									
						<td colspan="2"><s:input id="txtSpouseProfessionName" path="" 
									cssStyle="width: 100%;" cssClass="longTextBox" type="text"></s:input></td>
									
						<td width="5%"><label>Date Of Birth</label></td>
			   		 <td colspan="3"><s:input id="txtdtSpouseDateofBirth" name="txtdtSpouseDateofBirth" path="dteSpouseDateofBirth"  cssClass="calenderTextBox" /></td>
								
			</tr>
			
			<tr>
			
					
					<td><label>Mobile No</label></td>
					<td ><s:input id="txtSpouseResidentMobileNo" path="strSpouseResidentMobileNo"  
					class="decimal-places numberField" type="text"></s:input></td> 
			
					<td ><label>Email ID</label></td>
					<td colspan="5"><s:input id="txtSpouseResidentEmailID" path="strSpouseResidentEmailID" 
						cssClass="longTextBox" type="text"></s:input></td>
			
			
			</tr>
			
			<tr>
					<td width="120px"><label>Company Code</label></td>
							<td width="150px"><s:input id="txtSpouseCompanyCode"
									ondblclick="" cssClass="searchTextBox"
									 type="text" path="strSpouseCompanyCode" ></s:input></td>
									
							<td><s:input id="txtSpouseCompanyName" path=""  
									cssClass="longTextBox" type="text"></s:input></td>
									
									
							<td width="120px"><label>Job Profile</label></td>
							<td width="150px"><s:input id="txtSpouseJobProfileCode"
									ondblclick="" cssClass="searchTextBox"
									type="text" path="strSpouseJobProfileCode" ></s:input></td>
									
							<td colspan="3"><s:input id="txtSpouseJobProfileName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
			</tr> 
			
			<tr>
						<td width="5%"><label>Anniversary Date</label></td>
			   		 	<td width="5%"><s:input id="txtdtAnniversary" name="txtdtAnniversary" path="dteAnniversary"  cssClass="calenderTextBox" /></td>
					
						<td><label>Spouse Block</label></td>
			   			<td><s:select id="cmbSpouseFacilityBlock" name="cmbSpouseFacilityBlock" path="strSpouseBlocked" cssClass="BoxW124px" >
						 <option value="N">No</option>
		 				 <option value="Y">Yes</option>
						 </s:select></td>	
						 
						  <td width="120px"><label>StopCredit Supply </label></td>
						 <td ><s:select id="cmbSpouseStopCredit" name="cmbSpouseStopCredit" path="strSpouseStopCredit" cssClass="BoxW124px" >
						 <option value="N">No</option>
		 				 <option value="Y">Yes</option>
						 </s:select></td>
						 
<!-- 						 <td ><label>Spouse Liquor License</label></td> -->
<%-- 						<td ><s:input id="txtSpouseLiquorLicense" path="strSpouseLiquorLicense"  --%>
<%-- 						cssClass="longTextBox" type="text"></s:input></td> --%>
			
			</tr>
			
			
			
			</table>
		</div>
		
		
		
		
		
		<h3><a href="#">Membership Information</a></h3>
		<div>
		  <table class="transTable">
		
		<tr>	
				<td width="120px"><label>Membership Category</label></td>
							<td width="150px"><s:input id="txtMSCategoryCode"
									ondblclick="" cssClass="searchTextBox" 
									type="text" path="strCategoryCode" required="required" ></s:input></td>
									
						<td><s:input id="txtMemberName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
				
				 <td><label>Ballot Date</label></td>
			    <td colspan="2"><s:input id="txtdtBallotDate" name="txtdtBallotDate" path=""  cssClass="calenderTextBox" /></td>
		</tr>
		<tr>
				<td width="120px"><label>Proposer Code</label></td>
							<td width="150px"><s:input id="txtProposerCode"
									ondblclick="" cssClass="searchTextBox"
									type="text" path="strProposerCode" ></s:input></td>
									
						<td><s:input id="txtProposerName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
			
				<td width="120px"><label>Seconder Code</label></td>
							<td width="150px"><s:input id="txtSeconderCode"
									ondblclick="" cssClass="searchTextBox"
									type="text" path="strSeconderCode" ></s:input></td>
									
						<td><s:input id="txtSeconderName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
		
		</tr>
		<tr>
				
				<td width="120px"><label>Father/Mother Code</label></td>
							<td width="150px"><s:input id="txtFatherMemberCode"
									ondblclick="" cssClass="searchTextBox" 
									type="text" path="strFatherMemberCode" ></s:input></td>
									
						<td><s:input id="txtFatherMemberName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
			
				<td width="120px"><label>Status</label></td>
							<td width="150px"><s:input id="txtStatusCode"
									ondblclick="" cssClass="searchTextBox"
									type="text" path="" ></s:input></td>
									
						<td><s:input id="txtStatusName" path=""
									cssClass="longTextBox" type="text"></s:input></td>
			
		</tr>
		
		 <tr>
				<td><label>Member From</label></td>
			    <td><s:input id="txtdtMembershipStartDate" name="txtdtMembershipStartDate" path="dteMembershipStartDate"  cssClass="calenderTextBox" /></td>
				
				<td><label>Member To</label></td>
			    <td colspan="3"><s:input id="txtdtMembershipEndDate" name="txtdtMembershipEndDate" path="dteMembershipEndDate"  cssClass="calenderTextBox" /></td>
		</tr>
		 
		<tr>
				<td><s:select id="cmbMemberBlock" name="" path="strBlocked" cssClass="BoxW124px" >
				 <option value="N">No</option>
 				 <option value="Y">Yes</option>
				 </s:select></td>
				 
				 <td><label>Allow Card Validation</label></td>
			    <td><s:checkbox element="li" id="chkCardValidtion" path="" value="Yes" /></td>
			    
			    <td width="120px"><label>Reason Code</label></td>
							<td width="150px"><s:input id="txtBlockedReasonCode"
									ondblclick="funHelp('WCBlockReasonMaster')" cssClass="searchTextBox"
									type="text" path="strBlockedreasonCode"  ></s:input></td>
									
						<td><s:input id="txtBlockedReasonName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
		
		</tr>
		<tr>
					 <td width="120px"><label>Qualification</label></td>
							<td width="150px"><s:input id="txtQualification"
									ondblclick="funHelp('WCEducationMaster')" cssClass="searchTextBox"
									type="text" path="strQualification" ></s:input></td>
									
						<td><s:input id="txtQualificationName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
		
						<td width="120px"><label>Designation</label></td>
							<td width="150px"><s:input id="txtDesignationCode"
									ondblclick="funHelp('WCDesignationMaster')" cssClass="searchTextBox"
									type="text" path="strDesignationCode" ></s:input></td>
									
						<td><s:input id="txtDesignationName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
		
		</tr>
		<tr>
						<td width="120px"><label>Entrance Fee</label></td>			
						<td><s:input id="txtdblEntranceFee" path="dblEntranceFee" 
									class="decimal-places numberField" type="text"></s:input></td>
									
						<td width="120px"><label>Subscription Fee</label></td>			
						<td colspan="3"><s:input id="txtdblSubscriptionFee" path="dblSubscriptionFee" 
									class="decimal-places numberField" type="text"></s:input></td>
		
		
		</tr>
		<tr>
						<td width="120px"><label>Pan Number</label></td>			
						<td><s:input id="txtPanNumber" path="strPanNumber" 
									cssClass="longTextBox" type="text"></s:input></td>
									
						<td width="120px"><label>Billing Detail</label></td>
						<td colspan="3"><s:select id="cmbBillingDetail" name="cmbBillingDetail" path="" cssClass="BoxW124px" ></s:select></td>
			
		</tr>
		
		<tr>
						<td width="120px"><label>Locker Detail</label></td>
						<td><s:select id="cmbLockerDetail" name="cmbLockerDetail" path="strLocker" cssClass="BoxW124px" >
						 <option value="N">No</option>
		 				 <option value="Y">Yes</option>
						 </s:select></td>
						 
						 <td width="120px"><label>Library Facility</label></td>
						 <td colspan="3"><s:select id="cmbLockerDetail" name="cmbLockerDetail" path="strLibrary" cssClass="BoxW124px" >
						 <option value="N">No</option>
		 				 <option value="Y">Yes</option>
						 </s:select></td>
		</tr>
		
		<tr>
						<td width="120px"><label>Senior Citizen</label></td>
						 <td><s:select id="cmbSeniorCitizen" name="cmbSeniorCitizen" path="strSeniorCitizen" cssClass="BoxW124px" >
						 <option value="N">No</option>
		 				 <option value="Y">Yes</option>
						 </s:select></td>
						 
						 <td width="120px"><label>StopCredit Supply </label></td>
						 <td colspan="3"><s:select id="cmbStopCredit" name="cmbStopCredit" path="strStopCredit" cssClass="BoxW124px" >
						 <option value="N">No</option>
		 				 <option value="Y">Yes</option>
						 </s:select></td>
		</tr>
		
		<tr>
						<td width="120px"><label>Resident</label></td>
						 <td><s:select id="cmbResident" name="cmbResident" path="" cssClass="BoxW124px" >
						 <option value="N">No</option>
		 				 <option value="Y">Yes</option>
						 </s:select></td>
						 
						 <td width="120px"><label>Instation</label></td>
						 <td colspan="3"><s:select id="txtInstation" name="txtInstation" path="strInstation" cssClass="BoxW124px" >
						 <option value="N">No</option>
		 				 <option value="Y">Yes</option>
						 </s:select></td>
		</tr>
		<tr>
						 <td width="120px"><label>Golf Membership </label></td>
						 <td colspan="5"><s:select id="cmbGolfMemberShip" name="cmbGolfMemberShip" path="strGolfMemberShip" cssClass="BoxW124px" >
						 <option value="N">No</option>
		 				 <option value="Y">Yes</option>
						 </s:select></td>
		</tr>
		
		<tr>
						 <td width="120px"><label>Send Innvoice through </label></td>
						 <td><s:select id="cmbsendInnvoicethrough" name="cmbSendInnvoicethrough" path="" cssClass="BoxW124px" >
						 <option value="N">No</option>
		 				 <option value="Y">Yes</option>
						 </s:select></td>
						 
						 <td width="120px"><label>Circulars/Notice </label></td>
						 <td colspan="3"><s:select id="cmbNotice" name="cmbNotice" path="" cssClass="BoxW124px" >
						 <option value="N">No</option>
		 				 <option value="Y">Yes</option>
						 </s:select></td>
		</tr>
		
		</table>  
		</div>
		
		
		<h3><a href="#">Additional Information</a></h3>
		<div>
			<table class="transTable">
					<tr> 
					
					<td>NO pic aviable</td>
					
					</tr>
		</table>
		</div>
		
		
		<h3><a href="#">Edit Other Information</a></h3>
		<div>
			<table class="transTable">
					<tr> 
					
					<td>Not understand</td>
					
					</tr>
		</table>
			
			
		</div>
		
		
		<h3><a href="#">Facility Information</a></h3>
		<div>
				<table class="transTable">
					<tr> 
						<td width="120px"><label>facility</label></td>
						 <td width="20%"><s:select id="cmbfacility" name="cmbfacility" path="" cssClass="BoxW124px" >
						 </s:select></td>
						
						<td width="120px"><label>Payment Scheme</label></td>
						 <td colspan="2"><s:select id="cmbPaymentScheme" name="cmbPaymentScheme" path="" cssClass="BoxW124px" >
						 </s:select></td>	
					</tr>
					
			<tr>
				<td><label>From Date</label></td>
			    <td><s:input id="txtdtFromDate" name="txtdtFromDate" path=""  cssClass="calenderTextBox" /></td>
				
				<td><label>To Date</label></td>
			    <td colspan="2"><s:input id="txtdtToDate" name="txtdtFromDate" path=""  cssClass="calenderTextBox" /></td>
			</tr>
			
			<tr>
				<td><label>Facility Blocked</label></td>
			   <td><s:select id="cmbFacilityBlock" name="cmbFacilityBlock" path="" cssClass="BoxW124px" >
						 <option value="N">No</option>
		 				 <option value="Y">Yes</option>
						 </s:select></td>
				
				<td width="120px"><label>Reason Code</label></td>
							<td width="150px"><s:input id="txtReasonCode"
									ondblclick="funHelp('WCReasonMaster')" cssClass="searchTextBox"
									type="text" path="" ></s:input></td>
									
						<td><s:input id="txtReasonName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
			</tr>
					
					
		</table> 
				
		</div>
		
		<h3><a href="#">Dependent List</a></h3>
		<div>
				<table class="masterTable">
				
			<tr >
				<td >Dependent Code</td>
				<td ><s:input id="txtDependentCode" path=""
						cssClass="searchTextBox" ondblclick="" /></td>
			
				<td colspan="5"><label>Dependent Name</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:input type="text" id="txtDependentName" 
						name="txtDependentName" path="" 
						cssStyle="width:53% ; text-transform: uppercase;" cssClass="longTextBox"  /> <s:errors path=""></s:errors></td>
			</tr>
			
			<tr>
					<td><label>Change Dependent Code</label></td>
					<td><s:input  type="text" id="txtChangeDependentMemberCode" 
						name="txtChangeDependentMemberCode" path="" readonly="true"
						cssStyle="searchTextBox" cssClass="longTextBox"  /> <s:errors path=""></s:errors></td>
					<td><s:input  type="text" id="txtChangeDependentCode" 
						name="txtChangeDependentCode" path="" required="true"
						cssClass="longTextBox"  /> <s:errors path=""></s:errors></td>
						
						
						<td ><label>Sex</label></td>
						<td ><s:select id="cmbDependentGender" name="cmbDependentGender"
								path="strDependentGender" cssClass="BoxW124px">
								<option value="M">Male</option>
								<option value="F">Female</option>
							</s:select></td>


						<td ><label>Marital Status</label></td>
						<td ><s:select id="cmbDependentMaritalStatus"
								name="cmbDependentMaritalStatus" path="strDependentMaritalStatus"
								cssClass="BoxW124px">
								<option value="Single">Single</option>
								<option value="married">married</option>
							</s:select></td>
			
			</tr>
			
				
				<tr>
					<td ><label>Date Of Birth</label></td>
						<td ><s:input id="txtdteDependentDateofBirth"
								name="txtdteDependentDateofBirth" path="dteDependentDateofBirth"
								cssClass="calenderTextBox" /></td>
				
					<td width="120px"><label>Relation </label></td>
					<td ><s:input id="txtDependentRelation" path="strDependentRelation" 
									cssClass="longTextBox" type="text"></s:input></td>
				
					<td width="120px"><label>Profession Code</label></td>
							<td width="150px"><s:input id="txtDependentProfessionCode"
									ondblclick="funHelp('WCDependentProfessionMaster')" cssClass="searchTextBox"
									type="text" path="strDependentProfessionCode" ></s:input></td>
									
						<td ><s:input id="strDependentProfessionName" path="" 
									cssStyle="width: 30%%;" cssClass="longTextBox" type="text"></s:input></td>
									
						
				
				</tr>
				
				<tr>
				
						<td ><label>Member Exp Date</label></td>
						<td ><s:input id="txtdteDependentMemExpDate"
								name="txtdteDependentMemExpDate" path="dteDependentMemExpDate"
								cssClass="calenderTextBox" /></td>
						
						
							<td><label>Blocked</label></td>
							<td><s:select id="cmbDependentBlock" name="cmbDependentBlock"
									path="strDependentBlock" cssClass="BoxW124px">
									<option value="N">No</option>
									<option value="Y">Yes</option>
								</s:select></td>

							<td width="120px"><label>Block Reason Code</label></td>
							<td width="150px"><s:input id="txtDependentReasonCode"
									ondblclick="funHelp('WCDependentReasonMaster')" cssClass="searchTextBox"
									type="text" path="strDependentReasonCode"></s:input></td>

							<td><s:input id="txtDependentReasonName" path="" 
									cssClass="longTextBox" type="text"></s:input></td>
						</tr>
						
						
			</table>
			
				<br>
			<p align="center">
				<input type="button" value="Add"
					onclick="return btnAdd_onclick()"
					class="form_button" /> &nbsp; &nbsp; &nbsp; <input type="button"
					value="Reset" class="form_button" onclick="funResetTableData()()" />
			</p>
			
				<div class="masterTable" style="height:293px !important width :803px!important " >
						<table
							style="height: 28px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
							<tr bgcolor="#72BEFC">
							<td style="width:10%;">Member Code</td>
							<td style="width:15%;">Full Name</td>
							<td style="width:6%;">Relation</td>
							<td style="width:2%;">Sex</td>
							<td style="width:8%;">Marital</td>
							<td style="width:9%;">DOB</td>
							<td style="width:5%;">Blocked</td>
							<td style="width:9%;">Blocked Reason</td>
							<td style="width:9%;">Profession</td>
							<td style="width:9%;">Mem Exp Date</td>
							<td style="width:1%;"></td>
										
						</tr>
					</table>
					<div
							style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
								<table id="tblDependentData"
								style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
								class="transTablex col8-center">
								<tbody>
								
								<col style="width:10%">					
								<col style="width:15%">
								<col style="width:6%">
								<col style="width:2%">
								<col style="width:8%">
								<col style="width:9%">
								<col style="width:5%">
								<col style="width:9%">
								<col style="width:9%">
								<col style="width:9%">
								<col style="width:0%">
								
								</tbody>
							</table>
						</div>
					</div>
		
		</div>
		
		
		</div> 
		 
		
		
		<br>
		<p id="paraSubmit" align="center" style="display:none">
			<input type="submit" value="Submit"
				onclick=""
				class="form_button" /> &nbsp; &nbsp; &nbsp; <input type="reset"
				value="Reset" class="form_button"  onclick="funResetField()" />
		</p>
		<br><br>
		
		<p>
				<s:input type="hidden" id="txtCustCode"  path="strCustomerCode" ></s:input>
				<s:input type="hidden" id="txtSpouseCustCode"  path="strSpouseCustomerCode" ></s:input>
				
		
		</p>
	
	</s:form>
</div>

<script type="text/javascript">
		$(function(){
			$('#multiAccordion').multiAccordion({
// 				active: [1, 2],
				click: function(event, ui) {
				},
				init: function(event, ui) {
				},
				tabShown: function(event, ui) {
				},
				tabHidden: function(event, ui) {
				}
				
			});
			
			$('#multiAccordion').multiAccordion("option", "active", [0]);  // in this line [0,1,2] wirte then these index are open
		});
	</script>

	
</body>
</html>