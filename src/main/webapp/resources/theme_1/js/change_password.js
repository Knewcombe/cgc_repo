$(document).ready(function() {
	$.validator.addMethod("pwcheck", function(value) {
		   return /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/.test(value) // consists of only these 
		});
	
	$(function () {
	    $("#password")
	        .popover({ placement: "bottom", html:true, content: function(){return $('#popover-content').html();}})
	        .blur(function () {
	            $(this).popover('hide');
	        });
	});
	
	$("#newPass").validate({
		errorPlacement: function errorPlacement(error, element) { error.insertAfter(element); },
		debug: true,
	    rules: {
	    	password: {
	        	required: true,
	        	pwcheck: true
	        },
	        confirmPass:{
	        	required: true,
	        	equalTo: "#password"
	        }
	    },
	    messages:{
	    	password: {
	        	required: "please enter a password",
	        	pwcheck: "Password Reqirements have not been met"
	        },
	        confirmPass:{
	        	required: "Please enter your password again",
	        	equalTo: "Password does not match"
	        }
	    },
	    submitHandler: function(form) {
	        // do other things for a valid form
	    	document.getElementById("loading-background").style.display = "block";
	        form.submit();
	      }
	})
	
});