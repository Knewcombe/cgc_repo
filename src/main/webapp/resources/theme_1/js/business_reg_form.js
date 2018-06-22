$(document).ready(function() {
	$('#phone').mask('(000) 000-0000');
	
	jQuery.validator.addMethod('phoneUS', function(phone_number, element) {
	    phone_number = phone_number.replace(/\s+/g, ''); 
	    return this.optional(element) || phone_number.length > 9 &&
	        phone_number.match(/^(1-?)?(\([2-9]\d{2}\)|[2-9]\d{2})-?[2-9]\d{2}-?\d{4}$/);
	}, 'Please enter a valid phone number.');
	
	$("#merchantForm").validate({
		errorPlacement: function errorPlacement(error, element) { element.before(error); },
		debug: true,
	    rules: {
	    	full_name:{
	    		required: true
	    	},
	    	orginization_name:{
	    		required: true
	    	},
	    	phone_number: {
	    		required: true,
	    		phoneUS: true
	    	},
	    	email:{
	        	required: true,
	        	email: true
	        }
	    },
	    messages: {
	    	full_name:{
	    		required: "Please enter your full name"
	    	},
	    	orginization_name:{
	    		required: "Please enter your orginization name"
	    	},
	    	phone_number: {
	    		required: "Please enter your phone number",
	    		phoneUS: "Please enter a valid phone number"
	    	},
	    	email:{
	        	required: "Please enter your email",
	        	email: "Please enter a valid number"
	        }
	    },
	    submitHandler: function(form) {
	        // do other things for a valid form
	    	document.getElementById("loading-background").style.display = "block";
	        form.submit();
	    	//window.location.replace("./complete");
	    }
	});
})