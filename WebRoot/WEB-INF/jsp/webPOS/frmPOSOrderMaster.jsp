<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GROUP MASTER</title>
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

/*On form Load It Reset form :Ritesh 22 Nov 2014*/
 $(document).ready(function () {
		  $('input#txtOrderCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtOrderDesc').mlKeyboard({layout: 'en_US'});
		  
		  $("form").submit(function(event){
			  if($("#txtOrderDesc").val().trim()=="")
				{
					alert("Please Enter Order Name");
					return false;
				}
			  if(($("#cmbHH").val()=="HH") || ($("#cmbMM").val()=="MM"))
			    {
					alert("Invalid Time");
			   		
			       	return false;
				}
			  else{
				  flg=funCallFormAction();
				  return flg;
			  }
			});
		}); 




	/**
	* Reset The Group Name TextField
	**/
	function funResetFields()
	{
		$("#txtOrderDesc").focus();
		
    }
	
	
		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{	       
	       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(Group Code)
		**/
		function funSetData(code)
		{
			$("#txtOrderCode").val(code);
			var searchurl=getContextPath()+"/loadPOSOrderMasterData.html?orderCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strAreaCode=='Invalid Code')
				        	{
				        		alert("Invalid Area Code");
				        		$("#txtOrderDesc").val('');
				        	}
				        	else
				        	{
					        	$("#txtOrderDesc").val(response.strOrderCode);
					        	$("#txtOrderDesc").val(response.strOrderDesc);
					        	$("#cmbHH").val(response.strHH);
					        	$("#cmbMM").val(response.strMM);
					        	$("#cmbAMPM").val(response.strAMPM);
					        	
					        	$("#txtOrderDesc").focus();
					        	$("#cmbPOSName").val(response.strPOSName);
					        	
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
					%>alert("Data Saved \n\n"+message);<%
				}
			}%>
		});
		
		
		
		
		/**
		*  Check Validation Before Saving Record
		**/
		
		
		function funCallFormAction() 
		{
			var flg=true;
			var name = $('#txtOrderDesc').val();
			var code= $('#txtOrderCode').val();
			
				 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/checkOrderName.html?name="+name+"&code="+code,
				        async: false,
				        dataType: "text",
				        success: function(response)
				        {
				        	if(response=="false")
				        		{
				        			alert("Order Name Already Exist!");
				        			$('#txtOrderDesc').focus();
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
			
			return flg;
		}
</script>


</head>
<body>

	<div id="formHeading">
	<label>POSOrderMaster</label>
	</div>

<br/>
<br/>

	
<s:form name="POSOrderMaster" method="POST" action="savePOSOrderMaster.html?saddr=${urlHits}">
		<table class="masterTable">
			<tr>
				<td>
					<label>OrderCode</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtOrderCode" path="strOrderCode" cssClass="searchTextBox jQKeyboard form-control" readonly="true" ondblclick="funHelp('POSOrderMaster')"  />
				</td>
			</tr>
			<tr>
				<td>
					<label>OrderDesc</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtOrderDesc" path="strOrderDesc" cssStyle="text-transform: uppercase;" cssClass="longTextBox jQKeyboard form-control"  />
				</td>
			</tr>
			<tr>
				<td>
					<label>UpToTime</label>
				</td>
						
				<td><s:select id="cmbHH" name="cmbHH" path="strHH" cssStyle="width:20%" cssClass="BoxW124px" >
				<option value="HH">HH</option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option> 
				<option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
				 </s:select>
				 &nbsp;&nbsp;&nbsp;&nbsp;
				 <s:select id="cmbMM" name="cmbMM" path="strMM" cssStyle="width:20%" cssClass="BoxW124px" >
				<option value="MM">MM</option><option value="00">00</option><option value="01">01</option>
				<option value="02">02</option><option value="03">03</option><option value="04">04</option>
				<option value="05">05</option><option value="06">06</option><option value="07">07</option> 
				<option value="08">08</option><option value="09">09</option><option value="10">10</option>
				<option value="11">11</option><option value="12">12</option><option value="13">13</option>
				<option value="14">14</option><option value="15">15</option><option value="16">16</option>
				<option value="17">17</option><option value="18">18</option><option value="19">19</option>
				<option value="20">20</option><option value="21">21</option><option value="22">22</option>
				<option value="23">23</option><option value="24">24</option><option value="25">25</option>
				<option value="26">26</option><option value="27">27</option><option value="28">28</option>
				<option value="29">29</option><option value="30">30</option><option value="31">31</option>
				<option value="32">32</option><option value="33">33</option><option value="34">34</option>
				<option value="35">35</option><option value="36">36</option><option value="37">37</option>
				<option value="38">38</option><option value="39">39</option><option value="41">41</option>
				<option value="42">42</option><option value="43">43</option><option value="44">44</option>
				<option value="45">45</option><option value="46">46</option><option value="47">47</option>
				<option value="48">48</option><option value="49">49</option><option value="50">50</option>
				<option value="51">51</option><option value="52">52</option><option value="53">53</option>
				<option value="54">54</option><option value="55">55</option><option value="56">56</option>
				<option value="57">57</option><option value="58">58</option><option value="59">59</option>
				 </s:select>
				 &nbsp;&nbsp;&nbsp;&nbsp;
				 <s:select id="cmbAMPM" name="cmbAMPM" path="strAMPM" cssStyle="width:20%" cssClass="BoxW124px" >
				<option value="AM">AM</option>
				 <option value="PM">PM</option>
 				 
				 </s:select></td>
			</tr>
			<tr>
			<td><label>POS Name</label></td>
				<td><s:select id="cmbPOSName" name="cmbPOSName" path="strPOSName" items="${posList}" cssClass="BoxW124px" />
				</td>
				
			</tr>
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>