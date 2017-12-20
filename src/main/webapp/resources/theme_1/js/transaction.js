

var selectValue = false;

Number.prototype.padLeft = function(base,chr){
	   var  len = (String(base || 10).length - String(this).length)+1;
	   return len > 0? new Array(len).join(chr || '0')+this : this;
	}

	jQuery.noConflict();
	
	if (Prototype.BrowserFeatures.ElementExtensions) {
	    var disablePrototypeJS = function (method, pluginsToDisable) {
	            var handler = function (event) {
	                event.target[method] = undefined;
	                setTimeout(function () {
	                    delete event.target[method];
	                }, 0);
	            };
	            pluginsToDisable.each(function (plugin) { 
	                jQuery(window).on(method + '.bs.' + plugin, handler);
	            });
	        },
	        pluginsToDisable = ['collapse', 'dropdown', 'tooltip', 'popover', 'tab'];
	    disablePrototypeJS('show', pluginsToDisable);
	    disablePrototypeJS('hide', pluginsToDisable);
	}

	
	jQuery(document).ready(function($) {
		
		function getDateAndTime(){
			
		}
		
		$("#test").select2({
			theme: "bootstrap",
			ajax: {
			    url: "../transaction/searchForUser",
			    dataType: 'json',
			    delay: 250,
			    data: function (params) {
			      return {
			        search: params.term, // search term
			        page: params.page
			      };
			    },
			    processResults: function (data, params) {
			        // parse the results into the format expected by Select2
			        // since we are using custom formatting functions we do not need to
			        // alter the remote JSON data, except to indicate that infinite
			        // scrolling can be used
			        params.page = params.page || 1;
			        var select2data = $.map(data, function(obj) {
			            obj.id = obj.user_profile_id || obj._id.$oid;
			            //obj.text = obj.card_id;
			            return obj;
			          });
			        return {
			          results: select2data,
			          pagination: {
			            more: (params.page * 30) < select2data.total_count
			          }
			        };
			      },
			      cache: true
			},
			 placeholder: 'Search for a user',
			  escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
			  minimumInputLength: 1,
			  templateResult: formatRepo,
			  templateSelection: formatRepoSelection
		});
		
		function formatRepo (repo) {
			  if (repo.loading) {
			    return repo.text;
			  }

			  var markup = "<div class='select2-result-repository clearfix'>" +
//			    "<div class='select2-result-repository__avatar'><img src='https://beebom-redkapmedia.netdna-ssl.com/wp-content/uploads/2016/01/Reverse-Image-Search-Engines-Apps-And-Its-Uses-2016.jpg' /></div>" +
			    "<div class='select2-result-repository__meta'>" +
			      "<div class='select2-result-repository__title'>" + repo.first_name +" "+ repo.last_name+ "</div>"+
			      "<div class='select2-result-repository__description'><i class='fa fa-address-card'></i> " + repo.address + ", " + repo.city + ", " + repo.province_code + " " + repo.postal_code + "</div>";

//			  if (repo.description) {
//			    markup += "<div class='select2-result-repository__description'><i class='fa fa-address-card'></i> " + repo. + "</div>";
//			  }

			  markup += "<div class='select2-result-repository__statistics'>" +
			    "<div class='select2-result-repository__forks'><i class='fa fa-phone'></i> " + repo.phone + "</div>" +
			    "<div class='select2-result-repository__stargazers'><i class='fa fa-envelope'></i> " + repo.email + "</div>" +
			    "<div class='select2-result-repository__watchers'><i class='fa fa-id-card'></i> " + repo.card_id + "</div>" +
			  "</div>" +
			  "</div></div>";

			  return markup;
			}

			function formatRepoSelection (repo) {
				return repo.text || repo.first_name +" "+ repo.last_name;
			}
			
			$("#select_user").on("click", function(){
				var user_profile_id = $("#test").select2("val");
				$.ajax({
	        	    url: './getUserCardId',
	        	    type: "GET",
	        	    data: {user_profile_id: user_profile_id},
	        	    success: function(data){
	        	    	console.log("success");
	        	    	console.log(data);
	        	    	$("#card_input").val(data.card_id);
	        	    },error: function (xhr, ajaxOptions, thrownError) {
	        	    	console.log("error");
	        	    }
	        	})
	        	$("#user_profile_id").val(user_profile_id);
				$("#searchModal").hide();
				$("#user_id_success").show();
				$("#card_error").hide();
    	    	$("#user_id_error").hide();
    	    	$("#connection_error").hide();
			})
			
			$("#search_user_select").on("click", function(){
				$("#searchModal").show();
			});
			
			$(".close, .cancel_button").each(function(){
				$(this).on("click", function(){
					$("#test").select2("val", 'All');
					$("#searchModal").hide();
				})
			});
			
		function ajaxCardId(card_id){
			$.ajax({
        	    url: './getUserId',
        	    type: "GET",
        	    data: {cardId: card_id},
        	    success: function(data){
        	    	console.log("success");
        	    	console.log(data);
        	    	$("#card_input").val(card_id);
        	    	if(data.user_profile_id != null){
        	    		//console.log(data.responseJSON.user_profile_id);
	        	    	$("#user_id_success").show();
	        	    	$("#card_error").hide();
	        	    	$("#user_id_error").hide();
	        	    	$("#connection_error").hide();
	        	    	$("#no_user_error").hide();
	        	    	$("#user_profile_id").val(data.user_profile_id);
        	    	}else{
        	    		$("#user_id_success").hide();
	        	    	$("#card_error").hide();
	        	    	$("#connection_error").hide();
	        	    	$("#user_id_error").show();
	        	    	$("#user_profile_id").val(0);
	        	    	$("#no_user_error").hide();
        	    	}
        	    },
        	    error: function (xhr, ajaxOptions, thrownError) {
        	    	console.log("Error");
        	    	$("#card_input").val('');
        	    	if (XMLHttpRequest.readyState == 4) {
        	            // HTTP error (can be checked by XMLHttpRequest.status and XMLHttpRequest.statusText)
        	    		$("#user_id_success").hide();
	        	    	$("#user_id_error").show();
	        	    	$("#card_error").hide();
	        	    	$("#connection_error").hide();
	        	    	$("#user_profile_id").val(0);
	        	    	$("#no_user_error").hide();
        	        }
        	        else if (XMLHttpRequest.readyState == 0) {
        	            // Network error (i.e. connection refused, access denied due to CORS, etc.)
        	        	$("#user_id_success").hide();
	        	    	$("#user_id_error").hide();
	        	    	$("#card_error").hide();
	        	    	$("#connection_error").show();
	        	    	$("#user_profile_id").val(0);
	        	    	$("#no_user_error").hide();
        	        }else{
        	        	$("#user_id_success").hide();
	        	    	$("#user_id_error").hide();
	        	    	$("#card_error").show();
	        	    	$("#connection_error").hide();
	        	    	$("#user_profile_id").val(0);
	        	    	$("#no_user_error").hide();
        	        }
        	        //alert("Something went wrong");
        	      }
        	})
		}
		
		$(document).scannerDetection({
			timeBeforeScanTest: 200, // wait for the next character for upto 200ms
			endChar: [13], // be sure the scan is complete if key 13 (enter) is detected
			avgTimeByChar: 40, // it's not a barcode if a character takes longer than 40ms
			ignoreIfFocusOn: 'input', // turn off scanner detection if an input has focus
			onComplete: function(barcode, qty){ 
				ajaxCardId(barcode)
			}, // main callback function
			scanButtonKeyCode: 116, // the hardware scan button acts as key 116 (F5)
			scanButtonLongPressThreshold: 5, // assume a long press if 5 or more events come in sequence
			//onScanButtonLongPressed: showKeyPad, // callback for long pressing the scan button
			onError: function(string){console.log('Error ' + string);}
		});
		
		$('#card_input').keypress(function(event) {
			   //do stuff here
			var keycode = (event.keyCode ? event.keyCode : event.which);
			console.log(keycode)
			if(keycode == '13'){
				//alert('You pressed a "enter" key in somewhere');
				event.preventDefault();
				ajaxCardId($("#card_input").val());
				console.log("Test");
			}
		});
		
		var reader = new CreditCardReader();
		
		console.log(reader)
		
		reader.observe(document.body);
		
		reader.cardError(function () {
	    	// card swipe error
			$('results').update("error encountered");
	    });

	    reader.cardRead(function (data) {
	    	// data object: {number:number,name:name,exp_date:{year:exp_year,month:exp_month}}
//	      $('results').update("Name: "+data.name.titleize()+"<br>Number: "+data.number+"<br>Exp Date: "+data.exp_date.month+"/"+data.exp_date.year);
	    	if(data.name == "COMMUNITYGAMECHANGER"){
	    		console.log("Right card");
	    		ajaxCardId(data.number[0]);
//	    		$.ajax({
//	        	    url: './getUserId',
//	        	    type: "GET",
//	        	    data: {cardId: data.number[0]},
//	        	    success: function(data){
//	        	    	console.log("success");
//	        	    	console.log(data);
//	        	    	if(data.user_profile_id != null){
//	        	    		//console.log(data.responseJSON.user_profile_id);
//		        	    	$("#user_id_success").show();
//		        	    	$("#card_error").hide();
//		        	    	$("#user_id_error").hide();
//		        	    	$("#connection_error").hide();
//		        	    	$("#user_profile_id").val(data.user_profile_id);
//	        	    	}else{
//	        	    		$("#user_id_success").hide();
//		        	    	$("#card_error").hide();
//		        	    	$("#connection_error").hide();
//		        	    	$("#user_id_error").show();
//		        	    	$("#user_profile_id").val(0);
//	        	    	}
//	        	    },
//	        	    error: function (xhr, ajaxOptions, thrownError) {
//	        	    	console.log("Error");
//	        	    	$("#user_id_success").hide();
//	        	    	$("#user_id_error").hide();
//	        	    	$("#card_error").hide();
//	        	    	$("#connection_error").show();
//	        	    	$("#user_profile_id").val(0);
//	        	        //alert("Something went wrong");
//	        	      }
//	        	})
	    	}else{
	    		$("#card_error").show();
	    		$("#user_id_success").hide();
		    	$("#user_id_error").hide();
		    	$("#connection_error").hide();
		    	$("#user_profile_id").val(0);
		    	$("#no_user_error").hide();
	    		console.log("Not Community Game Changer card");
	    	}
	      console.log(data);
	    });
	    		
		
		$("#error_select").hide();
		$("#user_id_success").hide();
		$("#user_id_error").hide();
		$("#card_error").hide();
		$("#connection_error").hide();
		$("#purcahse_error").hide();
		$("#no_user_error").hide();
		
//		$("tr")
//				.each(
//						function() {
//							if ($(this).attr("id") != "title") {
//								// console.log($(this).find("input").val());
//								$(this).hide();
//								if ($(this).find("input").val() != 0.0
//										&& $(this).find("input")
//												.attr("id") != undefined) {
//									$(this).show();
//									var num = $(this)
//											.find("input")
//											.attr("id")
//											.replace(
//													'transactionDetail_amount_',
//													'');
//									if (!$("#checkbox_" + num)
//											.prop("checked")) {
//										$("#checkbox_" + num).prop(
//												'checked', true);
//									}
//								}
//							}
//						})
		$("span")
				.each(
						function() {
							if ($(this).attr("name") == "error_input"
									|| $(this).attr("name") == "error_radio") {
								$("#" + $(this).attr("id")).hide();
							}
						})
						
//						$('input[type="checkbox"]').on(
//"click",
//function() {
//console.log("Testing")
//if ($(this).prop("checked") == true) {
//	$("#" + $(this).val()).show();
//} else {
//	$("#" + $(this).val()).hide();
//	$("#error_input_" + $(this).val()).hide();
//	$("#error_radio_" + $(this).val()).hide();
//	// val(0.0);
//	$("#transactionDetail_amount_" + $(this).val()).val('0.0')
//	// $('input[name='+$("#transactionDetail_amount_" +
//	// $(this).val()).attr("name")+']').val('0.0');
//	$(
//			"#transactionDetail_method_of_pyment_" + $(this).val()
//					+ " input:radio:checked").val(undefined);
//}
//})

	$("#register").on("click",function(e) {
		var totalSum = 0;
		$(".purchase_input").each(function(){
			if($(this).val() != 0.0){
				totalSum += $(this).val();
				console.log($(this).attr("data-type"));
				console.log();
				if($('#transactionDetail_method_of_pyment_'+$(this).attr("data-type")).find('input:checked').val() != undefined){
					$("#error_radio_"+$(this).attr("data-type")).hide();
				}else{
					$("#error_radio_"+$(this).attr("data-type")).show();
					e.preventDefault();
				}
			}
		})
		
		if(totalSum <= 0.0){
			$("#purcahse_error").show();
			e.preventDefault();
		}else{
			$("#purcahse_error").hide();
		}
		
		if($("#user_profile_id").val() == 0){
			$("#no_user_error").show();
			e.preventDefault();
		}else{
			$("#no_user_error").hide();
		}
	})
})