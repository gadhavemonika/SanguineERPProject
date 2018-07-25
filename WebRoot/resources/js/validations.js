
	
	
	
	function funValidateEmail(inputText)
	{
		var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		if(inputText.match(mailformat))
		{
			return true;
		}
		else
		{
			alert("You have entered an invalid email address!");
			return false;
		}
	}
	
	function funValidateDecimal(inputtxt,inputName)
	{
		var decimal=  /^[-+]?[0-9]+\.[0-9]+$/;
		if(inputtxt.match(decimal))
		{
			return true;
		}
		else
		{
			alert("Invalid input for "+inputName);
			return false;
		}
	}
	
	function funValidateNumeric(inputtext)
	{
		var numbers = /^[0-9]+$/;
	    if(inputtext.match(numbers))
	    {	    	
	    	return true;  
	    }
	    else
	    {
	    	alert('Please Enter Numeric characters only');
	    	return false;
	    }
	}
	
	function funCheckNull(inputText,inputName)
	{
		if (inputText==null || inputText=="")
		{
			alert(inputName+" must be filled out");
			return false;
		}
		return true;
	}
	
	function funIsNull(object)
	{
		if(object.trim()=='')
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	function funValidateMobileNo(inputText)
	{
		var phoneno = /^\d{10}$/;
		if(inputText.value.match(phoneno))
	    {
			return true;
	    }
	    else
	    {
	        alert("Invalid Mobile No");
	        return false;
	    }
	}
	
	function funCheckN(e)
	{
		if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57))
	     {
	    	 alert("Enter Digits Only");
		     return false;
	     }
	}
	
	
	function resetForms(formName) {
	    document.forms[formName].reset();	    
	}
	
	function fun_isDate(txtDate)
	{
	    var currVal = txtDate;
	    if(currVal == '')
	        return false;
	    
	    var rxDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/; //Declare Regex
	    var dtArray = currVal.match(rxDatePattern); // is format OK?
	    
	    if (dtArray == null) 
	        return false;
	    
	    //Checks for mm/dd/yyyy format.
	    dtMonth = dtArray[3];
	   
	    dtDay= dtArray[1];
	   
	    dtYear = dtArray[5]; 
	   
	    
	    if (dtMonth < 1 || dtMonth > 12) 
	        return false;
	    else if (dtDay < 1 || dtDay> 31) 
	        return false;
	    else if ((dtMonth==4 || dtMonth==6 || dtMonth==9 || dtMonth==11) && dtDay ==31) 
	        return false;
	    else if (dtMonth == 2) 
	    {
	        var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
	        if (dtDay> 29 || (dtDay ==29 && !isleap)) 
	                return false;
	    }
	    return true;
	}

	
	
	
	$(function()
	{
		$(".target").blur(function() 
		{
			if(isNaN(this.value))
			{
				alert("Not Number");
				this.value = "";
				this.focus();
				return false;
			}
		});
		
		$(".searchTextBox").keydown(function (e) 
		{
			if(e.which == 191)
			{
				document.getElementById(this.id).ondblclick(); 
				document.getElementById(this.id).value = "";
			}
		});
	});
	
	
	