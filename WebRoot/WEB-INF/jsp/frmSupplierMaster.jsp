<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    
	<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
	<%@taglib uri="http://www.springframework.org/tags" prefix="sp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SUPPLIER MASTER</title>
<%-- <tab:tabConfig/> --%>

<script type="text/javascript">
var strcheckboxStatus="N";
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
	});
</script>


	<script type="text/javascript">

		var fieldName;
		var posItemCode;
		
		function funValidateFields()
		{
			var flg=true;
			if($("#txtPartyName").val().trim()=="")
			{
				alert("Please Enter Supplier Name");
				$("#txtPartyName").focus();
				return false;
			}
			if($('#txtPartyCode').val()=='')
			{
				var code = $('#txtPartyName').val().trim();
				 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/checkSuppName.html?suppName="+code,
				        async: false,
				        dataType: "text",
				        success: function(response)
				        {
				        	if(response=="true")
				        		{
				        			alert("Supplier Name Already Exist!");
				        			$('#txtPartyName').focus();
				        			flg= false;
					    		}
					    	else
					    		{
					    			flg=true;
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
			//alert("flg"+flg);
			return flg;
			
	    }
				
		
		function funSetAdd()
		{
			if(document.getElementById("chkShipAdd").checked==true)
			{
				$("#txtShipAdd1").val(document.getElementById("billAdd1").value);
				$("#txtShipAdd2").val(document.getElementById("billAdd2").value);
				$("#txtShipCity").val(document.getElementById("billCity").value);
				$("#txtShipState").val(document.getElementById("billState").value);
				$("#txtShipPin").val(document.getElementById("billPin").value);
				$("#txtShipCountry").val(document.getElementById("billCountry").value);
			}
			else
			{
				$("#txtShipAdd1").val("");
				$("#txtShipAdd2").val("");
				$("#txtShipCity").val("");
				$("#txtShipState").val("");
				$("#txtShipPin").val("");
				$("#txtShipCountry").val("");
			}
		}	
		
		 $(document).ready(function()
		 {
			$("#txtAttName").focus();
			$("#lblParentAttName").text("");
			var propcode='<%=session.getAttribute("propertyCode").toString()%>';
			funSetPropertyData(propcode);
			var message='';
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
			
			$("#txtPartyCode").focus();
	    });
		
		function funHelp(transactionName)
		{
			fieldName=transactionName;
	        
	    //    window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	        window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
	
		
		function funSetSupplier(code)
		{
			gurl=getContextPath()+"/loadSupplierMasterData.html?partyCode=";
			$.ajax({
		        type: "GET",
		        url: gurl+code,
		        dataType: "json",
		        success: function(response)
		        {
		       		if('Invalid Code' == response.strPCode){
		       			alert("Invalid Supplier Code");
		       			$("#txtPartyCode").val('');
		       			$("#txtPartyCode").focus();
		        			
		       		}else{
		       			$("#txtPartyCode").val(response.strPCode);
						$("#txtPartyName").val(response.strPName);
						$("#txtManualCode").val(response.strManualCode);
						$("#txtPhone").val(response.strPhone);
						$("#txtMobile").val(response.strMobile);
						$("#txtFax").val(response.strFax);
						$("#txtContact").val(response.strContact);
						$("#txtEmail").val(response.strEmail);
						$("#txtBankName").val(response.strBankName);
						$("#txtBankAdd1").val(response.strBankAdd1);
						$("#txtBankAdd2").val(response.strBankAdd2);
						$("#txtBankAccountNo").val(response.strBankAccountNo);
						$("#txtBankABANo").val(response.strBankABANo);
						$("#txtIbanNo").val(response.strIBANNo);
						$("#txtSwiftCode").val(response.strSwiftCode);
						$("#txtTaxNo1").val(response.strTaxNo1);
						$("#txtTaxNo2").val(response.strTaxNo2);
						$("#txtVat").val(response.strVAT);
						$("#txtCst").val(response.strCST);
						$("#txtExcise").val(response.strExcise);
						$("#txtServiceTax").val(response.strServiceTax);
						$("#cmbPartyType").val(response.strPartyType);
						$("#txtAcCrCode").val(response.strAcCrCode);
						$("#txtCreditDays").val(response.intCreditDays);
						$("#txtCreditLimit").val(response.dblCreditLimit);
						$("#txtRegistration").val(response.strRegistration);
						$("#txtRange").val(response.strRange);
						$("#txtDivision").val(response.strDivision);
						$("#txtCommissionerate").val(response.strCommissionerate);
						$("#cmbCategory").val(response.strCategory);
						$("#cmbExcisable").val(response.strExcisable);
						$("#txtMainAdd1").val(response.strMAdd1);
						$("#txtMainAdd2").val(response.strMAdd2);
						$("#txtMainCity").val(response.strMCity);
						$("#txtMainState").val(response.strMState);
						$("#txtMainPin").val(response.strMPin);
						$("#txtMainCountry").val(response.strMCountry);
						$("#txtBillAdd1").val(response.strBAdd1);
						$("#txtBillAdd2").val(response.strBAdd2);
						$("#txtBillCity").val(response.strBCity);
						$("#txtBillState").val(response.strBState);
						$("#txtBillPin").val(response.strBPin);
						$("#txtBillCountry").val(response.strBCountry);
						$("#txtShipAdd1").val(response.strSAdd1);
						$("#txtShipAdd2").val(response.strSAdd2);
						$("#txtShipCity").val(response.strSCity);
						$("#txtShipState").val(response.strSState);
						$("#txtShipPin").val(response.strSPin);
						$("#txtShipCountry").val(response.strSCountry);
						$("#cmbPartyIndi").val(response.strPartyIndi);
						$("#txtGSTNo").val(response.strGSTNo);
						$("#txtPropCode").val(response.strPropCode);
						$("#hidDebtorCode").val(response.strDebtorCode);
						$("#txtLocCode").val(response.strLocCode);
					 	$("#txtExternalCode").val(response.strExternalCode);
					 	$("#cmbCurrency").val(response.strCurrency);
						if(response.strLocCode.length>0)
						{}
						if(response.strOperational=='')
						{
							$("#cmbOperationalYN").val('Y');
						}else
						{
							$("#cmbOperationalYN").val(response.strOperational);
						}
						
						if(response.strExcisable=='Y')
				    	{
							$("#cmbExciseSuppYN").val("Yes");
				    	}
				    	else
				    	{
				    		$("#cmbExciseSuppYN").val("No");
				    	}
						
						if(response.strComesaRegion=='Y')
							document.getElementById("chkComesaRegion").checked=true;
				    	else
				    		document.getElementById("chkComesaRegion").checked=false;
				    	
						funSetSupplierTaxDtl(code);
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
		
		
		function funSetSupplierTaxDtl(code)
		{
			funRemoveTaxRows();
			gurl=getContextPath()+"/loadSupplierTaxDtl.html?partyCode=";
			$.ajax({
		        type: "GET",
		        url: gurl+code,
		        dataType: "json",
		        success: function(response)
		        {
		        	$.each(response, function(i, item) 
		        	{
		        		funAddRowTaxForUpdate(item[0],item[1]);
		        	});
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
		
		
		function funSetProduct(code)
		{
			gurl=getContextPath()+"/loadProductMasterData.html?prodCode=";
			$.ajax({
		        type: "GET",
		        url: gurl+code,
		        dataType: "json",
		        success: function(response)
		        {
					$("#txtProdCode").val(response.strProdCode);
		        	$("#lblProdName").text(response.strProdName);
			        posItemCode=response.strPartNo;
			        $("#txtAmount").focus();
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
		
		function funSetData(code)
		{
			switch (fieldName) 
			{		   
			   case 'suppcode':
			    	funSetSupplier(code);
			        break;
			   
			   case 'productmaster':
			    	funSetProduct(code);
			        break;
			        
			   case 'taxmaster':
				   funSetTax(code);
			        break;     
			  
			   case "property":
					funSetPropertyData(code);
					break;
			
			   case 'locationmaster':
				   funSetLocation(code);	
					
			        
			}
		}
		function funSetTax(code)
		{
				$.ajax({
						type: "GET",
				        url: getContextPath()+"/loadTaxMasterData.html?taxCode="+code,
				        dataType: "json",
				        success: function(response)
				        {
				        	if('Invalid Code' == response.strTaxCode){
				        		alert("Invalid tax Code");
				        		$("#txtTaxCode").val('');
				        		$("#txtTaxCode").focus();
				        	}else{
				        		$("#txtTaxCode").val(response.strTaxCode);
					        	$("#txtTaxDesc").val(response.strTaxDesc);
					        	$("#btnTaxAdd").focus();
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
		function funAddRow()
		{
			if(!funCheckNull($("#txtProdCode").val(),"Product Code"))
			{
				$("#txtProdCode").focus();
				return false;
			}
			
			if(!funValidateNumeric($("#txtAmount").val()))
			{
				$("#txtAmount").focus();
				return false;
			}
			 
			var prodCode = $("#txtProdCode").val();
		    var amount = $("#txtAmount").val();
		    var itemName = $("#lblProdName").text();
		    var table = document.getElementById("tblProdDet");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    row.insertCell(0).innerHTML= "<input name=\"listBomDtlModel["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box\" size=\"15%\" id=\"txtProdCode."+(rowCount)+"\" value="+prodCode+">";		    
		    row.insertCell(1).innerHTML= "<input name=\"listBomDtlModel["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"30%\" id=\"txtProdName."+(rowCount)+"\" value='"+itemName+"'/>";
		    row.insertCell(2).innerHTML= "<input name=\"listBomDtlModel["+(rowCount)+"].dblAmount\" id=\"txtAmount."+(rowCount)+"\" required = \"required\" style=\"text-align: right;\" size=\"14%\" class=\"decimal-places-amt\" value="+amount+">";
		    row.insertCell(3).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
		    funApplyNumberValidation();
		    return false;
		}
		 
		function funDeleteRow(obj)
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblChild");
		    table.deleteRow(index);
		}
		function funDeleteRowForTax(obj)
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblPartyTax");
		    table.deleteRow(index);
		}
		
		function funRemoveTaxRows()
		{
			var table = document.getElementById("tblPartyTax");
			var rowCount = table.rows.length;
			while(rowCount>1)
			{
				table.deleteRow(1);
				rowCount--;
			}
		}
		
		function funAddRowTax()
		{
		    var attCode = $("#txtTaxCode").val();
		    if(attCode=='')
		    {
		    	alert("Please select Tax Code");
		    	$("#txtTaxCode").focus();
		    	return false;
		    }
		    var taxCode = $("#txtTaxCode").val();
		    var taxDesc = $("#txtTaxDesc").val();
		    		    		    
		    var table = document.getElementById("tblPartyTax");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    row.insertCell(0).innerHTML= "<input class=\"Box\" size=\"22%\" name=\"listclsPartyTaxIndicatorDtlModel["+(rowCount-1)+"].strTaxCode\" id=\"txtTaxCode."+(rowCount-1)+"\" value='"+taxCode+"'>";
		    row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"22%\" name=\"listclsPartyTaxIndicatorDtlModel["+(rowCount-1)+"].strTaxDesc\" id=\"txtTaxDesc."+(rowCount-1)+"\" value='"+taxDesc+"'>";
		    //row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listclsPartyTaxIndicatorDtlModel["+(rowCount-1)+"].strTaxCode\" id=\"taxcode."+(rowCount-1)+"\" value="+taxCode+">";
		    //row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\"  id=\"taxDesc."+(rowCount-1)+"\" value="+taxDesc+">";		    
		    row.insertCell(2).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRowForTax(this)">';		    
		     funResetTaxField()
		    return false;
		}
		
		
		function funAddRowTaxForUpdate(taxCode,taxDesc)
		{   		    		    
		    var table = document.getElementById("tblPartyTax");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    row.insertCell(0).innerHTML= "<input class=\"Box\" size=\"22%\" name=\"listclsPartyTaxIndicatorDtlModel["+(rowCount-1)+"].strTaxCode\" id=\"txtTaxCode."+(rowCount-1)+"\" value='"+taxCode+"'>";
		    row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"22%\" name=\"listclsPartyTaxIndicatorDtlModel["+(rowCount-1)+"].strTaxDesc\" id=\"txtTaxDesc."+(rowCount-1)+"\" value='"+taxDesc+"'>";
		    row.insertCell(2).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRowForTax(this)">';
		    return false;
		}
		
		
		function funResetTaxField()
		{
			$("#txtTaxCode").val('');
			$("#txtTaxDesc").val('');
			$("#txtTaxCode").focus();
		   
		}
		$(function()
		{
			/*$("#txtPartyCode").blur(function()
			{
			    fieldName='suppliermaster';
			    gurl=getContextPath()+"/loadSupplierMasterData.html?partyCode=";
				funSetData($('#txtPartyCode').val());
			});*/
		    
			$('a#baseUrl').click(function() 
			{
				if($("#txtPartyCode").val().trim()=="")
				{
					alert("Please Select Party Code");
					return false;
				}


				 window.open('attachDoc.html?transName=frmSupplierMaster.jsp&formName= Supplier Master&code='+$('#txtPartyCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
              });
			
			
			$('#bodySupplierMaster').keydown(function(e) {
				if(e.which == 116){
					resetForms('suppliermasterForm');
					funResetFields();
				}
			});
			
			
			$('#txtPartyCode').blur(function() {
					var code = $('#txtPartyCode').val();
					if (code.trim().length > 0 && code !="?" && code !="/") {
						funSetSupplier(code);
					}				
			});
			
		});
		
		function funApplyNumberValidation(){
			$(".numeric").numeric();
			$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
			$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
			$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
		    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
		    $(".decimal-places-amt").numeric({ decimalPlaces: maxAmountDecimalPlaceLimit, negative: false });
		}
		function funResetFields()
		{
			location.reload(true); 
		}
		
		
		function funComoBox()
		{
			//document.getElementById("chkNotInUse").checked=true;
			var strVal=$('#cmbExciseSuppYN').val();
			if(strVal=='N')
				{
				document.all[ 'txtExcise' ].style.display = 'none';
				}else 
					{
					document.all[ 'txtExcise' ].style.display = 'block';
					}		
			
		}
		
		function funSetPropertyData(code) {
			$("#txtPropCode").val(code);
			$.ajax({
				type : "GET",
				url : getContextPath() + "/loadPropertyMasterData.html?Code="
						+ code,
				dataType : "json",
				success : function(resp) {
					// we have the response
					if('Invalid Code' == resp.propertyCode){
						alert("Invalid Property Code")
						$("#txtPropCode").val('');
						$("#lblPropName").val('');
						$("#txtPropCode").focus();
						
					}else{
						$("#txtPropCode").val(resp.propertyCode);
						$("#lblPropName").text(resp.propertyName);
					
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

		function funSetLocation(code)
		{
			$.ajax({
			        type: "GET",
			        url: getContextPath()+"/loadLocationMasterData.html?locCode="+code,
			        dataType: "json",
			        success: function(response)
			        {
				       	if(response.strLocCode=='Invalid Code')
				       	{
				       		alert("Invalid Location Code");
				       		$("#txtLocCode").val('');
				       	}
				       	else
				       	{
				       		var pName= funGetSupplierLocationProperty(code);
				       		if(pName.length>0)
				       			{
				       				alert("Location Already Link to "+pName);
				       			}else
				       				{
				       				$("#txtLocCode").val(response.strLocCode);
							       	$("#lblLocName").text(response.strLocName);
				       				}
				       		
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
		
		function funGetSupplierLocationProperty(code)
		{
			var propCode=$("#txtPropCode").val();
			var retval=""
			$.ajax({
			        type: "GET",
			        url: getContextPath()+"/loadGetSupplierLocationProperty.html?locCode="+code+"&propCode="+propCode,
			        dataType: "json",
			        async:false,
			        success: function(response)
			        {
				       	
				       		retval=response.strPName;
				      
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
			return retval;
		}
		
		
		</script>

</head>
<body  id="bodySupplierMaster">
<div id="formHeading">
		<label>Supplier Master</label>
	</div>
	<s:form name="suppliermasterForm" method="POST" action="saveSupplierMaster.html?saddr=${urlHits}">	
	<br>
		
	<table
			style="border: 0px solid black; width: 100%; height: 100%; margin-left: auto; margin-right: auto; background-color: #C0E4FF;">
			<tr>
				<td>				
				<div id="tab_container" style="height: 550px">
							<ul class="tabs">
								<li class="active" data-state="tab1" style="width: 100px;padding-left: 55px">General</li>
								<li data-state="tab2"  style="width: 100px;padding-left: 55px">Address</li>
								<li data-state="tab3"  style="width: 100px;padding-left: 55px">Products</li>
								<li data-state="tab4"  style="width: 100px;padding-left: 55px">Tax</li>
							</ul>
				<div id="tab1" class="tab_content" style="height: 450px" >
				<br>
				<br>
					<table class="masterTable">
						<tr>
					        <th align="right" colspan="4"> <a id="baseUrl" href="#">Attatch Documents</a>&nbsp; &nbsp; &nbsp; &nbsp;  </th>
					    </tr>
					    
				    	<tr>
				        	<td width="140px">
				        		<label>Supplier Code </label>
				        	</td>
				        	<td  width="15%">
				        		<s:input id="txtPartyCode" name="txtPartyCode"  path="strPCode" ondblclick="funHelp('suppcode')"   cssClass="searchTextBox"/>
				        	</td>		    
				    		<td  width="11%">
				    			<label>Finance Code </label>
				    		</td>
				        	<td>
				        		<s:input id="txtManualCode" cssClass="BoxW116px"  name="txtManualCode" path="strManualCode" />
				        	</td>		    
				   		</tr>
				    	
				    	<tr>
				        	<td><label>Name  </label></td>
				        	<td colspan="3">
				        		<s:input size="80px" type="text" id="txtPartyName" autocomplete="off" name="txtPartyName" path="strPName" cssStyle="text-transform: uppercase;" cssClass="longTextBox" required="true"/>
				        	</td>
				   		</tr>
				    
				    	<tr>
					        <td>
					        	<label>Tel No.</label>
					        </td>
					        <td>
					        	<s:input  type="tel"   id="txtPhone" name="txtPhone" path="strPhone" cssClass="BoxW116px"  />
					        </td>
					   		 
					        <td>
					        	<label> Mobile No.  </label>
					        </td>
					        <td>
					        <!-- pattern="[789][0-9]{9}" -->
					        	<s:input  type="tel" pattern="[0-9]{10,10}"  maxlength="11"  placeholder="Enter Valid MobileNo." id="txtMobile" name="txtMobile" path="strMobile" cssClass="BoxW116px" />
					        </td>
				 		</tr>
				    
				    	<tr>
					    	<td>
					    		<label>Fax</label>
					    	</td>
					        <td>
					        	<s:input id="txtFax" name="txtFax" path="strFax" cssClass="BoxW116px" />
					        </td>
					    	<td>
					    		<label>Contact Person</label>
					    	</td>
					        <td>
					        	<s:input id="txtContact" name="txtContact" path="strContact" autocomplete="off" cssStyle="text-transform: uppercase;" cssClass="BoxW116px" />
					        </td>				    
				  		 </tr>
				   
				   		<tr>
					        <td>
					        	<label>Email  </label>
					        </td>
					        <td colspan="3">
					        	<s:input   placeholder="name@email.com"   id="txtEmail" name="txtEmail" path="strEmail" cssClass="longTextBox"/>
					        </td>
				   		</tr>
				   		
				   		<tr>
					    	<td>
					    		<label>Bank Name  </label>
					    	</td>
					        <td colspan="3">
					        	<s:input  id="txtBankName" name="txtBankName" path="strBankName" autocomplete="off" cssStyle="text-transform: uppercase;" cssClass="longTextBox"/>
					        </td>
				    	</tr>
				    	
				    	<tr>
					    	<td>
					    		<label>Bank Address Line 1  </label>
					    	</td>
					        <td colspan="3">
					        	<s:input  id="txtBankAdd1" name="txtBankAdd1" path="strBankAdd1"  cssStyle="text-transform: uppercase;" cssClass="longTextBox"/>
					        </td>
				    	</tr>
				    	
				    	<tr>
				    		<td>
				    			<label>Bank Address Line 2  </label>
				    		</td>		
		       				<td colspan="3">
		       					<s:input id="txtBankAdd2"  name="txtBankAdd2" path="strBankAdd2" cssStyle="text-transform: uppercase;" cssClass="longTextBox"/>
		       				</td>
				    	</tr>
				    	
				    	<tr>
					    	<td>
					    		<label>Bank Account No.</label>
					    	</td>
					        <td>
					        	<s:input id="txtBankAccountNo" name="txtBankAccountNo" path="strBankAccountNo" cssClass="BoxW116px" />
					        </td>
					    	<td>
					    		<label>ABA No.</label>
					    	</td>
					        <td>
					        	<s:input id="txtBankABANo" name="txtBankABANo" path="strBankABANo" cssClass="BoxW116px" />
					        </td>
				  		 </tr>
				  		 
				    	<tr>
					    	<td>
					    		<label>IBAN No</label>
					    	</td>
					        <td>
					        	<s:input id="txtIbanNo" name="txtIbanNo" path="strIBANNo" cssClass="BoxW116px" />
					        </td>
					    	<td>
					    		<label>Bank Swift Code</label>
					    	</td>
					        <td>
					        	<s:input id="txtSwiftCode" name="txtSwiftCode" path="strSwiftCode" cssClass="BoxW116px" />
					        </td>
				  		</tr>
				  		 
				  		<tr>
				    		<td><label>Tax No. 1</label></td>
					        <td><s:input id="txtTaxNo1" name="txtTaxNo1" path="strTaxNo1" cssClass="BoxW116px" /></td>
					    	<td><label>Tax No. 2</label></td>
					        <td><s:input id="txtTaxNo2" name="txtTaxNo2" path="strTaxNo2" cssClass="BoxW116px" /></td>
				  		</tr>
				  		 
				  		<tr>
					    	<td><label>CST No/GST No</label></td>
					        <td><s:input id="txtCst" name="txtCst" path="strCST" cssClass="BoxW116px" /></td>
					    	<td><label>VAT</label></td>
					        <td><s:input id="txtVat" name="txtVat" path="strVAT" cssClass="BoxW116px" /></td>
				  		</tr>
				  		 
					    <tr>
					    	<td><label  >Excise Supp No.</label></td>
					    	<td><s:select id="cmbExciseSuppYN" name="strExcisable" path="strExcisable" cssClass="BoxW124px" onchange="funComoBox()"  >
					    	 <option value="N">No</option>
 				 			<option value="Y">Yes</option>
					    	</s:select></td>
					        
					        <td><label>Comesa Region Supplier</label></td>
							<td><s:checkbox id="chkComesaRegion" path="strComesaRegion" value="Y"></s:checkbox></td>
					        
					        <td><s:input id="txtExcise" name="txtExcise" path="strExcise" cssClass="BoxW116px" style="display:none" /></td>
				  		</tr>
				  		
				  		<tr>
				  			<td align="left"><label>Currency </label></td>
							<td><s:select id="cmbCurrency" items="${currencyList}" path="strCurrency" cssClass="BoxW124px">
							</s:select></td>
				  		</tr>
				  		
				  		<tr>
				  		 <td><s:label path="strServiceTax" >Service Tax No.</s:label></td>
					        <td colspan="1"><s:input id="txtServiceTax" name="txtServiceTax" path="strServiceTax" cssClass="BoxW116px" /></td>
				  		<td><label>Operational</label></td>
					    	<td><s:select id="cmbOperationalYN" name="strOperational" path="strOperational" cssClass="BoxW124px"  >
 				 			<option value="Y">Yes</option>
 				 			<option value="N">No</option>
					    	</s:select></td>
				  		</tr>
					    <tr>
					    	<td><s:label path="strPartyType" >Supplier Type</s:label></td>
					        <td><s:select id="cmbPartyType" name="cmbPartyType" path="strPartyType" items="${typeList}" cssClass="BoxW124px" /></td>			    	
					    	<td><s:label path="strAcCrCode" >A/C Creditors Code</s:label></td>
					        <td><s:input id="txtAcCrCode" name="txtAcCrCode" path="strAcCrCode" cssClass="BoxW116px" /></td>
				  		</tr>
				  		 
				  		<tr>
				    		<td><label>Credit Days</label></td>
				        	<td><s:input id="txtCreditDays" name="txtCreditDays" path="intCreditDays" class="BoxW116px"/></td>
				    		<td><label>Credit Limit</label></td>
				        	<td><s:input id="txtCreditLimit" name="txtCreditLimit" path="dblCreditLimit" class="BoxW116px"/></td>
				  		</tr>
				  		 
				  		<tr>
					    	<td><label>Registration No.</label></td>
					        <td><s:input id="txtRegistration" name="txtRegistration" path="strRegistration" cssClass="BoxW116px" /></td>
					    	<td><label>Range:</label></td>
					        <td><s:input id="txtRange" name="txtRange" path="strRange" cssClass="BoxW116px" /></td>
				  		</tr>
				  		 
				  		<tr>
				    		<td><s:label path="strDivision" >Division</s:label></td>
				        	<td><s:input id="txtDivision" name="txtDivision" path="strDivision" cssClass="BoxW116px" /></td>
				    		<td><s:label path="strCommissionerate" >Commissionerate</s:label></td>
				        	<td><s:input id="txtCommissionerate" name="txtCommissionerate" path="strCommissionerate" cssClass="BoxW116px" /></td>
				  		</tr>
				  		 <!-- problem -->
				  		<tr>
				    		<td><s:label path="strCategory" >Category</s:label></td>
				        	<td><s:select id="cmbCategory" name="cmbCategory" path="strCategory" items="${categoryList}" cssClass="BoxW124px" /></td>
				    		<td><label >Party Indicator</label></td>
				        	<td><s:select id="cmbPartyIndi" name="cmbPartyIndi" path="strPartyIndi" items="${partyIndicatorList}" cssClass="BoxW124px" /></td>
						</tr>
						<tr>
						   	<td><label>GST No.</label></td>
					        <td><s:input id="txtGSTNo" name="txtGSTNo" path="strGSTNo" cssClass="BoxW116px" /></td>
					        <td><label>External Code</label></td>
						    <td><s:input id="txtExternalCode" name="txtExternalCode" path="strExternalCode"  autocomplete="off" cssClass="BoxW116px"/>
						    	<s:errors path="strExternalCode"></s:errors>
						    </td>
					    </tr>
					    <tr>
					    <td><label>Property Code</label></td>
							<td ><s:input path="strPropCode" id="txtPropCode" ondblclick="funHelp('property');" cssClass="searchTextBox" /></td>
						<td colspan="1"><label id="lblPropName"></label></td>
					    <tr>
						   	<td><label>Location</label></td>
					        <td ><s:input id="txtLocCode" name="txtLocCode" path="strLocCode" cssClass="searchTextBox" ondblclick="funHelp('locationmaster')"/></td>
					   		<td colspan="2"><label id="lblLocName"></label>  </td>	
					    </tr>
					   
					    </tr>
				
					</table>
							
				</div>
							
							
			<div id="tab2" class="tab_content">
			<br><br>
				<table class="masterTable">
					<tr><th colspan="4" align="left">Main Address</th></tr>
				<tr>
		    	<td width="150px"><s:label path="strMAdd1">  Address Line 1  </s:label></td>
		        <td colspan="3"><s:input cssClass="longTextBox" cssStyle="text-transform: uppercase;" id="txtMainAdd1" name="txtMainAdd1" path="strMAdd1"/></td>
		    	</tr>
		    	<tr>
		    	<td><s:label path="strMAdd2">  Address Line 2  </s:label></td>
		        <td colspan="3"><s:input cssClass="longTextBox" cssStyle="text-transform: uppercase;" id="txtMainAdd2" name="txtMainAdd2" path="strMAdd2"/></td>
		    	</tr>
		    	
		    	<tr>
		    	<td><s:label path="strMCity"> City</s:label></td>
		        <td width="150px"><s:input id="txtMainCity" cssStyle="text-transform: uppercase;" name="txtMainCity" path="strMCity" cssClass="BoxW140px" /></td>
		        <td width="60px"><s:label path="strMState"> State</s:label></td>
		        <td ><s:input id="txtMainState" cssStyle="text-transform: uppercase;" name="txtMainState" path="strMState" cssClass="BoxW140px" /></td>
		    	</tr>
		    	
		    	<tr>
		    	<td><s:label path="strMCountry"> Country</s:label></td>
		        <td ><s:input id="txtMainCountry" name="txtMainCountry" cssStyle="text-transform: uppercase;" path="strMCountry" cssClass="BoxW140px" /></td>
		        <td><s:label path="strMPin"> Pin</s:label></td>
		        <td ><s:input pattern="[0-9]{6}" id="txtMainPin" name="txtMainPin" path="strMPin" cssClass="simpleTextBox"/></td>
		    	</tr>
		
				<tr><th colspan="4" align="left">Billing Address</th></tr>
				<tr>
		    	<td><s:label path="strBAdd1">  Address Line 1  </s:label></td>
		        <td colspan="3"><s:input cssClass="longTextBox" cssStyle="text-transform: uppercase;" id="txtBillAdd1" name="txtBillAdd1" path="strBAdd1"/></td>
		    	</tr>
		    	<tr>
		    	<td><s:label path="strBAdd2">  Address Line 2  </s:label></td>
		        <td colspan="3"><s:input cssClass="longTextBox" cssStyle="text-transform: uppercase;" id="txtBillAdd2" name="txtBillAdd2" path="strBAdd2"/></td>
		    	</tr>
		    	
		    	<tr>
		    	<td><s:label path="strBCity"> City</s:label></td>
		        <td ><s:input id="txtBillCity" name="txtBillCity" cssStyle="text-transform: uppercase;" path="strBCity" cssClass="BoxW140px" /></td>
		        <td><s:label path="strBState"> State</s:label></td>
		        <td ><s:input id="txtBillState" name="txtBillState" cssStyle="text-transform: uppercase;" path="strBState" cssClass="BoxW140px" /></td>
		    	</tr>
		    	
		    	<tr>
		    	<td><s:label path="strBCountry"> Country</s:label></td>
		        <td ><s:input id="txtBillCountry" name="txtBillCountry" cssStyle="text-transform: uppercase;" path="strBCountry" cssClass="BoxW140px" /></td>
		        <td><s:label path="strBPin"> Pin</s:label></td>
		        <td ><s:input pattern="[0-9]{6}" id="txtBillPin" name="txtBillPin" path="strBPin" cssClass="simpleTextBox" /></td>
		    	</tr>
		    	
		    	
				<tr><th colspan="4" align="left">Shipping Address</th>
				
				</tr>
				
				<tr>
				<td><s:label path="">  Same as Billing Address  </s:label></td>
		        <td colspan="3"><s:checkbox id="chkShipAdd" name="chkShipAdd" path="" value="" onclick="funSetAdd()"  /></td>
		        </tr>
				
				<tr>
		    	<td><s:label path="strSAdd1">  Address Line 1  </s:label></td>
		        <td colspan="3"><s:input cssClass="longTextBox" cssStyle="text-transform: uppercase;" id="txtShipAdd1" name="txtShipAdd1" path="strSAdd1" /></td>
		    	</tr>
		    	<tr>
		    	<td><s:label path="strSAdd2">  Address Line 2  </s:label></td>
		        <td colspan="3"><s:input cssClass="longTextBox" cssStyle="text-transform: uppercase;" id="txtShipAdd2" name="txtShipAdd2" path="strSAdd2"/></td>
		    	</tr>
		    	
		    	<tr>
		    	<td><s:label path="strSCity"> City</s:label></td>
		        <td ><s:input id="txtShipCity" name="txtShipCity"  cssStyle="text-transform: uppercase;" path="strSCity" cssClass="BoxW140px" /></td>
		        <td><s:label path="strSState"> State</s:label></td>
		        <td ><s:input id="txtShipState" name="txtShipState" cssStyle="text-transform: uppercase;" path="strSState" cssClass="BoxW140px" /></td>
		    	</tr>
		    	
		    	<tr>
		    	<td><s:label path="strSCountry"> Country</s:label></td>
		        <td ><s:input id="txtShipCountry" name="txtShipCountry" cssStyle="text-transform: uppercase;" path="strSCountry" cssClass="BoxW140px" /></td>
		        <td><s:label path="strSPin"> Pin</s:label></td>
		        <td ><s:input pattern="[0-9]{6}" id="txtShipPin" name="txtShipPin" path="strSPin" cssClass="simpleTextBox" /></td>
		    	</tr>		
			   </table>

							
			</div>
		<div id="tab3" class="tab_content">
				<!-- Kindly change funAddRow  So that it cnnot add products -->
						<table style="width: 80%;" class="transTablex">
						<tr>
								<th><label></label></th>
								<th><label></label></th>
								<th><label></label></th>
								<th><label></label></th>
								<th><label></label></th>
								<th><label></label></th>
							</tr>
							<tr>
								<td width="10%">Product Code:</td>
								<td width="10%"><input id="txtProdCode" class="BoxW140px" style="width:90%" ondblclick="funHelp('productmaster')" ></input></td>
								<td width="40%"><label id="lblProdName" class="namelabel"></label></td>
								<td width="10%">Amount:</td>
								<td width="10%"><input id="txtAmount" type="text" class="decimal-places-amt numberField" ></input></td>
								<td width="10%" ><input id="btnAdd" type="button" class="smallButton" value="Add" onclick="return funAddRow()"></input></td>
							</tr>				
						</table>
						<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 80%;">
									<table id="tblProdDet"
										style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
										class="transTablex col4-center">
										<tr >
									<td style="border: 1px white solid;width:10%"><label>Product Code</label></td>
									<td style="border: 1px  white solid;width:50%"><label>Product Name</label></td>
									<td style="border: 1px  white solid;width:10%"><label>Amount</label></td>
									<td style="border: 1px  white solid;width:10%"><label>Delete</label></td>
								</tr>
								</table>
								</div>
			</div>
			<div id="tab4" class="tab_content">
						<table style="width: 80%;" class="transTablex col3-center">
							<tr>
								<th><label></label></th>
								<th><label></label></th>
								<th><label></label></th>
								<th><label></label></th>
								<th><label></label></th>
								<th><label></label></th>
							</tr>
							<tr>
							<td>Tax Code</td>
							<td><input id="txtTaxCode" ondblclick="funHelp('taxmaster')" Class="searchTextBox" ></input></td>
							<td>Tax Description</td>
							<td><input id="txtTaxDesc" class="BoxW140px" readonly="readonly" ></input></td>
							<td><input id="btnTaxAdd" type="button" value="Add"  class="smallButton" onclick="return funAddRowTax()"></input></td>
							</tr>	
						</table>
						<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 80%;">
									<table id="tblPartyTax"
										style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
										class="transTablex col3-center">
						<!-- <table class="masterTable"  id="tblPartyTax" style="width:80%" > -->
								<tr >
									<td style="border: 1px white solid;width:10%"><label>Tax Code</label></td>
									<td style="border: 1px  white solid;width:50%"><label>Tax Description</label></td>
									
									<td style="border: 1px  white solid;width:10%"><label>Delete</label></td>
								</tr>

								<%-- <c:forEach items="${command.listProdAtt}" var="prodAtt"
									varStatus="status">
									<tr>
										<td><input name="listProdAtt[${status.index}].strAttCode"
											value="${prodAtt.strAttCode}" readonly="readonly" /></td>
										<td><input name="listProdAtt[${status.index}].strAttName"
											value="${prodAtt.strAttName}" readonly="readonly" /></td>
										<td><input
											name="listProdAtt[${status.index}].dblAttValue"
											value="${prodAtt.dblAttValue}" /></td>
										<td><input type="hidden"
											name="listProdAtt[${status.index}].strAVCode"
											value="${prodAtt.strAVCode}" readonly="readonly" /></td>
										<td><input type="button" value="Delete"
											onClick="funDeleteRowForAttribute(this)" class="deletebutton"></td>
									</tr>
								</c:forEach> --%>
							</table>
							</div>
			</div>
		</div>
 	 </td>
  </tr>
</table>
		

	<br>
		<p align="center">
			<input type="submit" value="Submit" id="formsubmit" onclick="return funValidateFields()" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick=" funResetFields()"/>
		</p>
		<br>
		<s:input id="hidDebtorCode" type="hidden" path="strDebtorCode" />	
	<br><br>
</s:form>
<script type="text/javascript">
		funApplyNumberValidation();
	</script>
</body>
</html>