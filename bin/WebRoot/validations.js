	
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
	