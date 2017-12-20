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
	    	fullName:{
	    		requried: true
	    	},
	    	businessName:{
	    		required: true
	    	},
	    	phone: {
	    		required: true,
	    		phoneUS: true
	    	},
	    	email:{
	        	required: true,
	        	email: true
	        }
	    },
	    messages: {
	    	fullName:{
	    		requried: "Please enter your full name"
	    	},
	    	businessName:{
	    		required: "Please enter your business name"
	    	},
	    	phone: {
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
	        //form.submit();
	    	window.location.replace("./complete");
	    }
	})
	
//		$("#sendMessageButton").submit(function(){
//			console.log("Clikced");
//			if($("#merchantForm").){
//				
//			}
//		})
	});