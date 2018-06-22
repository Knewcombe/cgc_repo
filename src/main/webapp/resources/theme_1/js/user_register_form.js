
/*
 * Author Kyle Newcombe
 * Date April 16 2018
 * 
 * Code is for user register page. will validate and handle community selection.
 * 
 * SelectedValues is global to all elements in code. This will keep track
 * of the user Community Selections
 * */


var selectedValues = [];
var previous = "";


$(document).ready(function() {
	//Check if user leaves page.
	window.onbeforeunload = function() {
        return "The Register form has not been completed. Are you sure you want to leave?";
    }
	//Postal code validation.
	jQuery.validator.addMethod("cdnPostal", function(postal, element) {
	    return this.optional(element) || 
	    postal.match(/[a-zA-Z][0-9][a-zA-Z](-| |)[0-9][a-zA-Z][0-9]/);
	}, "Please specify a valid postal code.");
	
	// Add US Phone Validation
	jQuery.validator.addMethod('phoneUS', function(phone_number, element) {
	    phone_number = phone_number.replace(/\s+/g, ''); 
	    return this.optional(element) || phone_number.length > 9 &&
	        phone_number.match(/^(1-?)?(\([2-9]\d{2}\)|[2-9]\d{2})-?[2-9]\d{2}-?\d{4}$/);
	}, 'Please enter a valid phone number.');
	//Password validation
	$.validator.addMethod("pwcheck", function(value) {
		   return /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/.test(value) // consists of only these 
		});

	//Validation to first part of form
	var validator = $("#regForm").validate({
		errorPlacement: function errorPlacement(error, element) { error.insertAfter(element); },
		debug: true,
		focusInvalid: false,
	    rules: {
	    	first_name:{
	    		requried: true
	    	},
	    	last_name:{
	        	requried: true
	        },
	        "userProfile.card_id":{
				required: true
			},
	        username:{
	        	required: true,
	        	remote: {
	        		//Checking if username is unique.
	                url: "./checkusername",
	                type: "get",
	                data:{
	                	username: function(){
	                		return $("#username").val();
	                	}
	                }
	            }
	        },
	        password: {
	        	required: true,
	        	pwcheck: true
	        },
	        confirmPass:{
	        	required: true,
	        	equalTo: "#password"
	        },
	        "userProfile.gender":{
	        	required: true
	        },
	        year: {
	        	required: true
	        },
	        "userProfile.phone":{
	        	required: true,
	        	phoneUS: true
	        },
	        "userProfile.email":{
	        	required: true,
	        	email: true,
	        	remote: {
	                url: "./checkemail",
	                type: "get",
	                data:{
	                	username: function(){
	                		return $("#email").val();
	                	}
	                }
	            }
	        },
	        confirmEmail:{
	        	required: true,
	        	equalTo: "#email"
	        },
	        province_code:{
	        	required: true,
	        },
	        city:{
	        	required: true
	        },
	        address:{
	        	required: true
	        },
	        "userProfile.postal_code":{
				required: true,
				cdnPostal: true
			},
			"questions[0].question":{
				required: true
			},
			"questions[1].question":{
				required: true
			},
			"questions[2].question":{
				required: true
			},
			"questions[0].answer":{
				required: true
			},
			"questions[1].answer":{
				required: true
			},
			"questions[2].answer":{
				required: true
			},
			checkbox: {
				required: true
			}
	    },
	    messages: {
	        password: {
	        	required: "Please enter a password",
	        	pwcheck: "Password Reqirements have not been met"
	        },
	        confirmPass:{
	        	required: "Please enter your password again",
	        	equalTo: "Password does not match"
	        },
	        username:{
	        	required: "Please enter a username",
	        	remote: "Username has already been taken, please try another"
	        },
	        day:{
	        	required: "Select birth day"
	        },
	        month:{
	        	required: "Select birth month"
	        },
	        year: {
	        	required: "Select birth year"
	        },
	        "userProfile.email":{
	        	required: "Please enter your email",
	        	email: "Please enter a valid email",
	        	remote: "Email is already in use",
	        },
	        confirmEmail:{
	        	required: "Please enter your email to confirm",
	        	equalTo: "email must match"
	        },
	        "userProfile.postal_code":{
				cdnPostal: "Please enter a valid postal code"
			},
			"userProfile.card_id":{
				required: "Please enter you card id, or select 'No' for the option on the left."
			},
			"questions[0].question":{
				required: "Please select a question"
			},
			"questions[1].question":{
				required: "Please select a question"
			},
			"questions[2].question":{
				required: "Please select a question"
			},
			"questions[0].answer":{
				required: "Please enter an answer"
			},
			"questions[1].answer":{
				required: "Please enter an answer"
			},
			"questions[2].answer":{
				required: "Please enter an answer"
			},
			checkbox: {
				required: "You must agree to our terms of service before continuing."
			}
	    }
	});
	//Password requirement popover
	$(function () {
	    $("#password")
	        .popover({ placement: "bottom", html:true, content: function(){return $('#popover-content').html();}})
	        .blur(function () {
	            $(this).popover('hide');
	        });
	});
	//Family member phone number popover
	$(function () {
	    $(".family_phone")
	        .popover({ placement: "bottom", html:true, content: function(){return $('#phone-id').html();}})
	        .blur(function () {
	            $(this).popover('hide');
	        });
	});
	
	//Family card number popover
	$(function () {
	    $(".family_card_id")
	        .popover({ placement: "bottom", html:true, content: function(){return $('#card-id').html();}})
	        .blur(function () {
	            $(this).popover('hide');
	        });
	});
	
	var reachedLastPage = false;
	
	//Steps jquery code. This will allow for muti-step sign up form.
	$('#regForm').steps({
		headerTag : "title",
		bodyTag : "div",
		transitionEffect : "slideLeft",
		stepsOrientation : "horizontal",
		onStepChanging: function(e, currentIndex, newIndex) {
			 if(newIndex > currentIndex){
				 $(this).validate().settings.ignore = ":disabled,:hidden";
				 console.log($(this).valid())
				 return $(this).valid();
			 }else{
				 return true;
			 }
			//return true;
        },
        onStepChanged: function(event, currentIndex, priorIndex){
        	//Checking if current form is complete before moveing to next.
        	$(this).find(".content").scrollTop(0);
        	if(currentIndex == 1){
				$('a[href$="next"]').text('Skip this step');
				var skip = false;
				$(".coGender").each(function(index){
					if($("#"+$(this).attr("id")+" option:selected").text() != 'Title'){
						skip = true;
					}
				});
				if(skip){
					$('a[href$="next"]').text('Next');
				}else{
					$('a[href$="next"]').text('Skip this step');
				}
			}else{
				$('a[href$="next"]').text('Next');
			}
        },
        // Triggered when clicking the Finish button
        onFinishing: function(e, currentIndex) {
            var teamIsSelected = false;
            var total = 0;
        	$(this).validate().settings.ignore = ":disabled,:hidden";
        	//Final check and send user form to server.
    		if(selectList.length != 0){
    			console.log(selectList);
    			$("#association_select_alert").hide();
    			$(".association_list").each(function(index){
    				console.log("Test");
        			if(selectList[index] != undefined){
        				console.log("has item in array" + index);
        				switch(selectList[index].type){
        					case "sport":
        						$(this).children("#donation_dysplay_"+index).val(selectList[index].marker/100);
        	    				$(this).children("#association_id_"+index).val(selectList[index].selection.association_id.value);
        	    				$(this).children("#team_id_"+index).val(selectList[index].selection.team_id.value);
        	    				$(this).children("#player_id_"+index).val(selectList[index].selection.player_id.value);
        					break;
        					case "charity":
        						$(this).children("#donation_dysplay_"+index).val(selectList[index].marker/100);
        	    				$(this).children("#charity_id_"+index).val(selectList[index].selection.association_id.value);
//        	    				$(this).children("#chairty_recipts_"+index).val(selectList[index].selection.receipt);
        					break;
        					case "nonProf":
        						$(this).children("#donation_dysplay_"+index).val(selectList[index].marker/100);
        	    				$(this).children("#nonprof_id_"+index).val(selectList[index].selection.association_id.value);
        					break;
        					case "personal":
        						$(this).children("#personal_"+index).val(selectList[index].selection.personal);
								$(this).children("#donation_dysplay_"+index).val(selectList[index].marker/100);
        					break;
        				}
        				$(this).children('#active_'+index).val(true);
        			}
        			$(".card_member").each(function(index){
	    				if($("#coPhone_"+index).val() == ""){
	    					$("#coPhone_"+index).val($("#phone").val());
	    				}
        			});
        			console.log("Testing");
        			console.log($(this).children("#donation_dysplay_"+index).val() +", "+
    				$(this).children("#association_id_"+index).val() +", "+
    				$(this).children("#team_id_"+index).val() +", "+
    				$(this).children("#player_id_"+index).val() +", ");
        			
        			console.log($(this).children("#donation_dysplay_"+index).val() +", "+
    				$(this).children("#charity_id_"+index).val() +", ");
        			
        			console.log($(this).children("#donation_dysplay_"+index).val() +", "+
        					$(this).children("#nonprof_id_"+index).val() +", ");
        		})
        		return true;
    		}else{
    			$("#association_select_alert").show();
    			return false;
    		}
        },
        onFinished: function(e, currentIndex) {
        	//Once validation is finished and everything is set to the right values send to server.
        	$("#regForm .actions a[href='#finish']").hide();
        	document.getElementById("loading-background").style.display = "block";
        	$.ajax({
        	    url: './registerProcess',
        	    type: "POST",
        	    data: $("#regForm").serialize(),
        	    complete: function(data){
        	    	window.onbeforeunload = null;
        	    	document.getElementById("loading-background").style.display = "none";
        	    	window.location.replace(data.responseText);
        	    	//console.log("Done");
        	    },
        	    error: function (xhr, ajaxOptions, thrownError) {
        	    	$("#regForm .actions a[href='#finish']").show();
        	    	document.getElementById("loading-background").style.display = "none";
        	        alert("Something went wrong");
        	      }
        	})
        }
		
	})
	//Family Member validation form
	$(".coGender").change(function() {
		var skip = false;
		$(".coGender").each(function(index){
			if($("#"+$(this).attr("id")+" option:selected").text() != 'Title'){
				skip = true;
				validator.resetForm();
				$("#coFirstName_"+index).prop('disabled', false);
				
				$("#coLastName_"+index).prop('disabled', false);
				
				$("#coPhone_"+index).prop('disabled', false);
				
				$("#coYear_"+index).prop('disabled', false);
				
				$("#coCardId_"+index).prop('disabled', false);
			}else{
				$("#coFirstName_"+index).val('');
				$("#coFirstName_"+index).prop('disabled', true);
				$("#coFirstName_"+index).removeClass('error');
				$("#coFirstName_"+index).next("label").remove();
				
				$("#coLastName_"+index).val('');
				$("#coLastName_"+index).removeClass('error');
				$("#coLastName_"+index).next("label").remove();
				$("#coLastName_"+index).prop('disabled', true);
				
				
				$("#coPhone_"+index).val('');
				$("#coPhone_"+index).prop('disabled', true);
				
				$("#coYear_"+index).val('');
				$("#coYear_"+index).removeClass('error');
				$("#coYear_"+index).next("label").remove();
				$("#coYear_"+index).prop('disabled', true);
				
				$("#coCardId_"+index).val('');
				$("#coCardId_"+index).prop('disabled', true);
			}
		});
		if(skip){
			$('a[href$="next"]').text('Next');
		}else{
			$('a[href$="next"]').text('Skip this step');
		}
	});
	//Prevent confirm email from pasting.
	$('#confirmEmail').bind("cut copy paste",function(e) {
		  e.preventDefault();
	});
	//Insure user ahas agreed to terms of server and privacy policy.
	$("#agree").on("change", function(){
		if($(this).prop('checked')){
			$("#agreeError").hide();
		}
	})
	//Hind card id on load
	$("#card_id_input").hide();
	//If user selectes they have a card show input form and add validation.
	$("input[name='cardRadio']").on("click", function(){
		if($(this).val() == 'true'){
			$("#card_id_input").show();
		}else{
			$("#card_number").val("");
			$("#card_id_input").hide();
		}
	})
	
	$('#card_select').on('change', function() {
		console.log("test")
		var cardSelectValue = $(this).val();
	});
	
	//Make sure limit count is set. User can only have 10 Communities selected.
	var limitCount = $(".association_list").length;
	console.log(limitCount);
	//Make SelectList to store all Communites selected
	var selectList = [];
	//Using slider to seperate values.
	var softSlider = document.getElementById('slider');
	//When slider changes follow stpes
	function updateSlider(startArray, connectArray){
		var diffDivs = [];
		var testDivs = [];
		//Update all percent span elements page.
		$(".percent_span").each(function(index){
			testDivs.push(document.getElementById('spinner_'+index))
		})
		//remove slider is no Communities are selected
		if(softSlider.noUiSlider != undefined){
			softSlider.noUiSlider.destroy();
			$("#slider-info").hide();
		}
		//Show slider once one is selected
		if(selectList.length == 1){
			$("#slider-info").show();
			noUiSlider.create(softSlider, {
				start: 100,
				connect: [true, false],
				range: {
					'min': 0,
					'max': 100
				},
				pips: { // Show a scale with the slider
					mode: 'steps',
					stepped: false,
					density: 10
				}
			});
			
			var origins = slider.getElementsByClassName('noUi-origin');
			origins[0].setAttribute('disabled', true);
			
		}else{
			//If slider make sure code is added to element.
			$("#slider-info").show();
			noUiSlider.create(softSlider, {
				start: startArray, 
				margin: 10, // Handles must be at least 10 apart
				padding: 10,
		// limit: 100, // ... but no more than 10
				connect: connectArray, // Display a colored bar
															// between the handles
		// direction: 'rtl', // Put '0' at the bottom of the slider
		// orientation: 'horizontal', // Orient the slider vertically
				behaviour: 'tap-drag', // Move handle on tap, bar is draggable
				step: 10,
				tooltips: false,
				format: wNumb({
					decimals: 1
				}),
				range: {
					'min': 0,
					'max': 100
				},
				pips: { // Show a scale with the slider
					mode: 'steps',
					stepped: false,
					density: 10
				}
			})
		}
		//When slider changes update all values.
		softSlider.noUiSlider.on('update', function( values, handle ) {
				for(var e = 0; e < testDivs.length; e++){
					var currentValue = (values[e] || 100);
					var previousValue = (values[(e-1)] || 0);
					//diffDivs[e].innerHTML = currentValue - previousValue;
					selectList[e].marker = currentValue - previousValue;
					testDivs[e].value = currentValue - previousValue;
				}
		});
		
		var connect = slider.querySelectorAll('.noUi-connect');
		
		for ( var i = 0; i < connect.length; i++ ) {
			if(selectList[i] != undefined){
				connect[i].classList.add(selectList[i].color);
			}
		}
		
		updateSpinners();
		
	}
	
	function updateSpinners(){
		
		//Method will insure all elements are updated connectly when makeing changes.
		$('.precent_selector').spinner({
					value:0 ,min: 0,max: 100,step: 10,spin: function( event, ui ) {
			var testValues = [];
			var testindex = $(this).attr("data-index")
			var previousValue = $(this).val();
			var diffVal;
				if(softSlider.noUiSlider != undefined){
					var currentMarkers = softSlider.noUiSlider.get();
					if($.isArray(currentMarkers)){
						if(testindex > currentMarkers.length || testindex == currentMarkers.length){
							diffVal = previousValue - ui.value;
						}else{
							diffVal = ui.value - previousValue;
						}
						$.each(currentMarkers, function(i, val){
							if((parseInt(currentMarkers[i]) + diffVal) >= parseInt(currentMarkers[i+1]) || (parseInt(currentMarkers[i]) + diffVal) >= 100){
								console.log("Overlap FORWARD");
								event.preventDefault();
								testValues.push(null);
							}else if((parseInt(currentMarkers[i]) + diffVal) <= parseInt(currentMarkers[i-1]) || (parseInt(currentMarkers[i]) + diffVal) <= 0){
								console.log("Overlap BACK");
								event.preventDefault();
								testValues.push(null);
							}else{
								if(testindex == i){
									testValues.push(parseInt(currentMarkers[i]) + diffVal);
								}else if(testindex == currentMarkers.length && i == currentMarkers.length - 1){
									testValues.push(parseInt(currentMarkers[i]) + diffVal);
								}else{
									testValues.push(null);
								}
							}
						})
					}else if(currentMarkers != 100){
						console.log("test");
						if(testindex > 0){
							diffVal = previousValue - ui.value;
						}else{
							diffVal = ui.value - previousValue;
						}
						if((parseInt(currentMarkers)+diffVal) <= 0){
							event.preventDefault();
							testValues.push(null);
						}else if((parseInt(currentMarkers)+diffVal) >= 100){
							event.preventDefault();
							testValues.push(null);
						}else{
							testValues.push(parseInt(currentMarkers) + diffVal);
						}
					}else{
						event.preventDefault();
					}
					softSlider.noUiSlider.set(testValues);
				}
			}
		}).focus(function () {
		    $(this).blur();
		});
	}
	//Pop up for when user selected Sport selection.
	function implementSportSelect(testIndex){
		//Create select 2 to search for sports
		$("#sport_query").select2({
			theme: "bootstrap",
			ajax: {
			    url: "./searchSport",
			    dataType: 'json',
			    delay: 250,
			    data: function (params) {
			      return {
			        search: params.term, // search term
			        page: params.page
			      };
			    },
			    processResults: function (data, params) {
			        params.page = params.page || 1;
			        var select2data = $.map(data, function(obj, index) {
			        	console.log(obj);
			        	if(obj.teams[0] != null){
			        		if(obj.teams[0].players[0] != null){
			        			obj.id = obj.teams[0].players[0].player_id
			        		}else{
			        			obj.id = obj.teams[0].team_id
			        		}
			        	}else{
			        		obj.id = obj.association_id
			        	}
//			        	obj.id = .player_id || obj.teams[0].team_id || obj.association_id;
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
			      cache: false
			},
			 placeholder: 'Search for Sport Community Partner',
			  escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
			  minimumInputLength: 1,
			  templateResult: formatRepo,
			  templateSelection: formatRepoSelection
		});
		//Formatting how the server responce looks on page.
		function formatRepo (repo) {
			  if (repo.loading) {
			    return repo.text;
			  }
			  var markup = "<div class='select2-result-repository clearfix'>"+
			  "<div class='select2-result-repository__meta'>";
			  if(repo.teams[0] != null){
				  markup += "<div class='select2-result-repository__title'>" + repo.teams[0].name + "</div>";
				  if(repo.teams[0].players[0] != null){
					  console.log("test")
					  markup += " " + repo.teams[0].players[0].name + "</div>"
				  }else{
					  markup += "</div>";
				  }
				  markup += "<div class='select2-result-repository__description'>" + repo.name +"</div>"+
				  "<div class='select2-result-repository__stargazers'><i class='fa fa-address-card'></i> "+ repo.community + ", " + repo.province_code + "</div>"
			  }else{
				  markup +=  "<div class='select2-result-repository__title'>" + repo.name + "</div>"+
				      "<div class='select2-result-repository__description'><i class='fa fa-address-card'></i> " + repo.community + ", " + repo.province_code + "</div>";
			  }
			  
			  markup += "</div></div></div>";

			  return markup;
			}

			function formatRepoSelection (repo) {
				var name = "";
				console.log(repo)
				if(repo.id != ""){
					if(repo.teams[0] != null){
						name = repo.teams[0].name;
						if(repo.teams[0].players[0] != null){
							name += " - "+ repo.teams[0].players[0].name
						}
					}else{
						name = repo.name;
					}
				}else{
					name = repo.text;
				}
				return name;
			}
		//Setting up multi part selection.
		$('#team_radio').hide();
		$("input[name='teamRadio']")[1].checked = true;
		$('#player_radio').hide();
		$("input[name='playerRadio']")[1].checked = true;
		$('#team_selection').hide();
		$('#player_selection').hide();
		$("#association_sport_row").cascadingDropdown({
			isLoadingClassName: "Test",
			selectBoxes: [
				{
					selector: '.province',
					source: function(request, response){
						$.ajax({
							  dataType: "json",
							  url: '../teams/getProvince',
							  success: function(data){
								  response($.map(data, function(item, index){
										return{
											label: item.province_code,
											value: item.province_code
											
										}
									}))
									if(testIndex != undefined){
										$.when.apply($, $.map).done(function() {
											$("#province_select").val(selectList[testIndex].selection.province_id.value);
											$('#province_select').trigger('change');
										});
									}
							  }
						});
					}
				},
				{
					selector: '.community',
					requires: ['.province'],
					source: function(request, response){
						$.ajax({
							  dataType: "json",
							  url: '../teams/getCommunity',
							  data: {province_code: $('#province_select').val()},
							  success: function(data){
								  response($.map(data, function(item, index){
										return{
											label: item.community,
											value: item.community
											
										}
									}))
									if(testIndex != undefined){
										$.when.apply($, $.map).done(function() {
											$("#community_select").val(selectList[testIndex].selection.community_id.value);
											$('#community_select').trigger('change');
										});
									}
							  }
						});
					}
				},
				{
					selector: '.sport',
					requires: ['.community'],
					requireAll: true,
					source: function(request, response){
						$.ajax({
							dataType: "json",
							url: '../teams/getSport',
							data: {province_code: $('#province_select').val(), community: $('#community_select').val()},
							success: function(data){
								response($.map(data, function(item, index){
									return{
										label: item.sport,
										value: item.sport
									}
								}))
								if(testIndex != undefined){
									$.when.apply($, $.map).done(function() {
										$("#sport_items").val(selectList[testIndex].selection.sport_id.value);
										$('#sport_items').trigger('change');
									});
								}
							}
						})
					}
				},
				{
					selector: '.association_select',
					requires: ['.community', '.sport'],
					source: function(request, response){
						$.ajax({
							dataType: "json",
							url: '../teams/getAssociation',
							data: {province_code: $('#province_select').val(), community: $('#community_select').val(), sport: $('#sport_items').val()},
							success: function(data){
								response($.map(data, function(item, index){
									return{
										label: item.name,
										value: item.association_id
									}
								}))
								if(testIndex != undefined){
									$.when.apply($, $.map).done(function() {
										$("#association_select").val(selectList[testIndex].selection.association_id.value);
										$('#association_select').trigger('change');
									});
								}
							}
						})
					},
					onChange: function(event, selected, requiredValues, requirementsMet){
						if($('#association_select').val() == 0){
							$('#team_radio').hide();
							$("input[name='teamRadio']")[1].checked = true;
							$('#team_selection').hide();
							$('#player_radio').hide();
							$("input[name='playerRadio']")[1].checked = true;
							$('#player_selection').hide();
						}
					}
				},
				{
					selector: '.division',
					requires: ['.association_select'],
					source: function(request, response){
						$.ajax({
							dataType: "json",
							url: '../teams/getDivision',
							data: {association_id: parseInt($('#association_select').val())},
							success: function(data){
								response($.map(data, function(item, index){
									$('#team_radio').show();
									return{
										label: item.division,
										value: item.division
									}
								}))
								if(testIndex != undefined){
									$.when.apply($, $.map).done(function() {
										if(selectList[testIndex].selection.division_id.value != ''){
											$('#team_radio').show();
											$("input[name='teamRadio']")[0].checked = true;
											$('#team_selection').show();
										}
										$("#division_select").val(selectList[testIndex].selection.division_id.value);
										$('#division_select').trigger('change');
									});
								}
							}
						})
					}
				},
				{
					selector: '.gender',
					requires: ['.division'],
					source: function(request, response){
						$.ajax({
							dataType: "json",
							url: '../teams/getGender',
							data: {association_id: parseInt($('#association_select').val()), division: $('#division_select').val()},
							success: function(data){
								response($.map(data, function(item, index){
									return{
										label: item.gender,
										value: item.gender
									}
								}))
								if(testIndex != undefined){
									$.when.apply($, $.map).done(function() {
										$("#gender_select").val(selectList[testIndex].selection.gender_id.value);
										$('#gender_select').trigger('change');
									});
								}
							}
						})
					}
				},
				{
					selector: '.team',
					requires: ['.gender'],
					requiredAll: true,
					source: function(request, response){
						$.ajax({
							dataType: "json",
							url: '../teams/getTeamName',
							data: {association_id: parseInt($('#association_select').val()), division: $('#division_select').val(), gender: $('#gender_select').val()},
							success: function(data){
								response($.map(data, function(item, index){
									return{
										label: item.name,
										value: item.team_id
									}
								}))
							    if(testIndex != undefined){
							    	$.when.apply($, $.map).done(function() {
							    		$("#team_id").val(selectList[testIndex].selection.team_id.value);
										$('#team_id').trigger('change');
							    	});
								}
							}
						})
					}
				},
				{
					selector: '.player',
					requires: ['.team'],
					requiredAll: true,
					source: function(request, response){
						$.ajax({
							dataType: "json",
							url: '../teams/getPlayers',
							data: {team_id: $('#team_id').val()},
							success: function(data){
								response($.map(data, function(item, index){
									$('#player_radio').show();
									return{
										label: item.name,
										value: item.player_id
									}
								}))
								if(testIndex != undefined){
									$.when.apply($, $.map).done(function() {
										if(selectList[testIndex].selection.player_id.value != ''){
											$('#player_radio').show();
											$("input[name='playerRadio']")[0].checked = true;
											$('#player_selection').show();
										}
										$("#player_id").val(selectList[testIndex].selection.player_id.value);
										$('#player_id').trigger('change');
									});
								}
							}
						})
					}
				}
			]
		})
//		$( "#sport_query" ).on('change', function() {
//			//association_sport_row
//			if($("#sport_query").val() != ''){
//				$('#association_sport_row').prop('disabled', 'disabled');
//			}else{
//				$('#association_sport_row').prop('disabled', false);
//			}
//			  alert( "Handler for .select() called." );
//		});
//		
//		$( "#association_sport_row" ).on('change', function() {
//			//association_sport_row
//			if($("#association_sport_row").val() != ''){
//				$('#sport_query').prop('disabled', 'disabled');
//			}else{
//				$('#sport_query').prop('disabled', false);
//			}
//			  alert( "Handler for .select() called." );
//		});
	}
	//Pop up when user select charity
	function implementCharitySelect(testIndex){
		//using select 2 to search for charity
		$("#charity_query").select2({
			theme: "bootstrap",
			ajax: {
			    url: "./searchCharity",
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
			            obj.id = obj.association_id || obj._id.$oid;
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
			 placeholder: 'Search for a charity',
			  escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
			  minimumInputLength: 1,
			  templateResult: formatRepo,
			  templateSelection: formatRepoSelection
		});
		//Formatting how this looks when results come back from server.
		function formatRepo (repo) {
			  if (repo.loading) {
			    return repo.text;
			  }

			  var markup = "<div class='select2-result-repository clearfix'>" +
			    "<div class='select2-result-repository__meta'>" +
			      "<div class='select2-result-repository__title'>" + repo.name +"</div>"+
			      "<div class='select2-result-repository__description'><i class='fa fa-address-card'></i> " + repo.community + ", " + repo.province_code + "</div>";
			  markup += "</div></div>";

			  return markup;
			}

			function formatRepoSelection (repo) {
				return repo.text || repo.name ;
			}
		//Creating multi select to find charity
		$("#association_charity_row").cascadingDropdown({
			selectBoxes: [
				{
					selector: '.province_char',
					source: function(request, response){
						$.ajax({
							  dataType: "json",
							  url: '../charity/getProvince',
							  success: function(data){
								  response($.map(data, function(item, index){
										return{
											label: item.province_code,
											value: item.province_code
										}
									}))
									if(testIndex != undefined){
										$.when.apply($, $.map).done(function() {
											$("#province_c_select").val(selectList[testIndex].selection.province_id.value);
											$('#province_c_select').trigger('change');
										});
									}
							  }
						});
					}
				},
				{
					selector: '.community_char',
					requires: ['.province_char'],
					source: function(request, response){
						$.ajax({
							  dataType: "json",
							  url: '../charity/getCommunity',
							  data: {province_id: $("#province_c_select").val()},
							  success: function(data){
								  response($.map(data, function(item, index){
										return{
											label: item.community,
											value: item.community
										}
									}))
									if(testIndex != undefined){
										$.when.apply($, $.map).done(function() {
											$("#community_c_select").val(selectList[testIndex].selection.community_id.value);
											$('#community_c_select').trigger('change');
										});
									}
							  }
						});
					}
				},
				{
					selector: '.name_select',
					requires: ['.community_char'],
					source: function(request, response){
						$.ajax({
							  dataType: "json",
							  url: '../charity/getName',
							  data: {province_id: $("#province_c_select").val(), community: $("#community_c_select").val()},
							  success: function(data){
								  response($.map(data, function(item, index){
										return{
											label: item.name,
											value: item.association_id
										}
									}))
									if(testIndex != undefined){
										$.when.apply($, $.map).done(function() {
											$("#name_c_select").val(selectList[testIndex].selection.association_id.value);
											$('#name_c_select').trigger('change');
										});
									}
							  }
						});
					}
				}
			]
		});
		if(testIndex != undefined){
//			$("#receipt_selection").prop("checked", selectList[testIndex].selection.receipt);
		}
	}
	
	//Pop up when user select nonprof
	function implementNonProfSelect(testIndex){
		//Creat select 2 to find non prof
		$("#nonProf_query").select2({
			theme: "bootstrap",
			ajax: {
			    url: "./searchNonProf",
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
			            obj.id = obj.association_id || obj._id.$oid;
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
			 placeholder: 'Search for a charity',
			  escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
			  minimumInputLength: 1,
			  templateResult: formatRepo,
			  templateSelection: formatRepoSelection
		});
		//Formatting response from server to display.
		function formatRepo (repo) {
			  if (repo.loading) {
			    return repo.text;
			  }
			  var markup = "<div class='select2-result-repository clearfix'>" +
			    "<div class='select2-result-repository__meta'>" +
			      "<div class='select2-result-repository__title'>" + repo.name +"</div>"+
			      "<div class='select2-result-repository__description'><i class='fa fa-address-card'></i> " + repo.community + ", " + repo.province_code + "</div>";
			  markup += "</div></div>";

			  return markup;
			}

			function formatRepoSelection (repo) {
				return repo.text || repo.name ;
			}
		//Multi select form for nonprof
		$("#association_nonProf_row").cascadingDropdown({
			selectBoxes: [
				{
					selector: '.province_nonProf',
					source: function(request, response){
						$.ajax({
							  dataType: "json",
							  url: '../nonProf/getProvince',
							  success: function(data){
								  response($.map(data, function(item, index){
										return{
											label: item.province_code,
											value: item.province_code
										}
									}))
									if(testIndex != undefined){
										$.when.apply($, $.map).done(function() {
											$("#province_nonp_select").val(selectList[testIndex].selection.province_id.value);
											$('#province_nonp_select').trigger('change');
										});
									}
							  }
						});
					}
				},
				{
					selector: '.community_nonProf',
					requires: ['.province_nonProf'],
					source: function(request, response){
						$.ajax({
							  dataType: "json",
							  url: '../nonProf/getCommunity',
							  data: {province_id: $("#province_nonp_select").val()},
							  success: function(data){
								  response($.map(data, function(item, index){
										return{
											label: item.community,
											value: item.community
										}
									}))
									if(testIndex != undefined){
										$.when.apply($, $.map).done(function() {
											$("#community_nonp_select").val(selectList[testIndex].selection.community_id.value);
											$('#community_nonp_select').trigger('change');
										});
									}
							  }
						});
					}
				},
				{
					selector: '.name_select_nonProf',
					requires: ['.community_nonProf'],
					source: function(request, response){
						$.ajax({
							  dataType: "json",
							  url: '../nonProf/getName',
							  data: {province_id: $("#province_nonp_select").val(), community: $("#community_nonp_select").val()},
							  success: function(data){
								  response($.map(data, function(item, index){
										return{
											label: item.name,
											value: item.association_id
										}
									}))
									if(testIndex != undefined){
										$.when.apply($, $.map).done(function() {
											$("#name_nonp_select").val(selectList[testIndex].selection.association_id.value);
											$('#name_nonp_select').trigger('change');
										});
									}
							  }
						});
					}
				}
			]
		});
	}
	
	//Update selected communities adding to removing item from list
	function updateListItems(index, selectedInput, type){
		selectList.push({
			index: index,
			type: type,
			color: 'c-'+index+'-color',
			marker: 0,
			selection: selectedInput
		});
		
		if(selectList.length >= limitCount){
			$("#charitySelect").hide();
			$("#sportSelect").hide();
			$("#profitSelect").hide();
		}else{
			$("#charitySelect").show();
			$("#sportSelect").show();
			$("#profitSelect").show();
		}
		
		var personalCheck = false;
		
		$.each(selectList, function(itemIndex, item){
			if(item.type == "personal"){
				personalCheck = true;
			}
		})
		
		if(!personalCheck){
			$("#personal_option").show();
		}else{
			$("#personal_option").hide();
		}
		//Html to add to html page
		switch(type){
		case "sport":
			$('#items-row').children('#myBtn').before(htmlBlock.replace(/\-cssColor-/g, selectList[index].color).replace(/\-associationName-/g, selectList[index].selection.association_id.label).replace(/\-teamName-/g, selectList[index].selection.team_id.label).replace(/\-playerName-/g, selectList[index].selection.player_id.label).replace(/\-index-/g, index));
			break;
		case "charity":
			$('#items-row').children('#myBtn').before(htmlBlock.replace(/\-cssColor-/g, selectList[index].color).replace(/\-associationName-/g, selectList[index].selection.association_id.label).replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, index));
			break;
		case "nonProf":
			$('#items-row').children('#myBtn').before(htmlBlock.replace(/\-cssColor-/g, selectList[index].color).replace(/\-associationName-/g, selectList[index].selection.association_id.label).replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, index));
			break;
		case "personal":
			$('#items-row').children('#myBtn').before(htmlBlock.replace(/\-cssColor-/g, selectList[index].color).replace(/\-associationName-/g, "Personal").replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, index));
			break;
		}
		
	}
	
	//Date of birth picker
	$.dobPicker({
		yearSelector : '.year',
		// Minimum age
		minimumAge : 0,
		// Maximum age
		maximumAge : 100
	});
	
	//Phone number mask
	$('.phone').mask('(000) 000-0000');

	//Hide all alerts
	$("#nonprof-alert").hide();
	$("#charity-alert").hide();
	$("#sport-alert").hide();
	$("#agreeError").hide();
	
	
//	$("#myBtn").on("click", function(){
//		//$("#myModal_"+(selectList.length)).show();
//		//implementSportSelect();
//		$("#selectionModal").show();
//	});
	
	//Making a sport selection
	$("#sportSelect").on("click", function(){
		//$("#myModal_"+(selectList.length)).show();
		implementSportSelect();
		//$("#selectionModal").hide();
		$("#sportModal").show();
//		$('#test').addClass('noscroll');
		$('body').addClass('noscroll');
		$('.content').addClass('noscroll');
		$("#sportModal").scrollTop = 0
		
		$("#selectSportButton").show();
		$("#updateSportButton").hide();
	})
	//Making a charity selection
	$("#charitySelect").on("click", function(){
		implementCharitySelect();
		//$("#selectionModal").hide();
		$("#charitiyModal").show();
		$('body').addClass('noscroll');
		$('.content').addClass('noscroll');
		$("#charitiyModal").scrollTop = 0
		$("#selectCharityButton").show();
		$("#updateCharityButton").hide();
	})
	//Making nonprof selection
	$("#profitSelect").on("click", function(){
		implementNonProfSelect();
		//$("#selectionModal").hide();
		$("#nonProfModal").show();
		$('body').addClass('noscroll');
		$('.content').addClass('noscroll');
		$("#nonProfModal").scrollTop = 0
		$("#selectNonProfButton").show();
		$("#updateNonProfButton").hide();
	})
	//checking if player was selected
	$("input[name='playerRadio']").on("click", function(){
		if($(this).val() == 'true'){
			$("#player_selection").show();
		}else{
			$("#player_id").val("");
			$("#player_selection").hide();
		}
	})
	//Checking if team was selected.
	$("input[name='teamRadio']").on("click", function(){
		if($(this).val() == 'true'){
			$("#team_selection").show();
		}else{
			$("#team_id").val("");
			$("#gender_select").val("");
			$("#division_select").val("");
			$("#team_selection").hide();
		}
	})
	
	//Close buttons for all pop ups.
	$(".close, .cancel_button").each(function(){
		$(this).on("click", function(){
			$(".modal").each(function(){
				$('.alert-danger').hide();
				$(".association-test").each(function(){
					if($(this).cascadingDropdown() != undefined){
						$(this).cascadingDropdown('destroy');
						$(this).find(".js-data-example-ajax").empty().trigger("change");
					}
				})
				//$("#receipt_selection").val(false);
//				$("#receipt_selection").prop("checked", false);
				$(this).hide();
			});
			$('.content').removeClass('noscroll');
			$('body').removeClass('noscroll');
		})
	})
	
	//Checking to make sure community selection is correct
	function checkInput(selectInput, type, tempIndexForUpdate){
		console.log("Check Input");
		var returnValue = true;
		if(selectList.length == 0){
			return returnValue;
		}else{
			$.each(selectList, function(index, value){
				if(tempIndexForUpdate != index){
					if(value.type == type){
						console.log("Matching type")
						if(value.selection.association_id.value == selectInput.association_id.value){
							console.log("Matching Association");
							returnValue = false;
							if(selectInput.team_id != undefined && selectInput.player_id != undefined){
								console.log("Has team_id and player_id");
								if(value.selection.team_id.value == selectInput.team_id.value){
									console.log("Team Id matches");
									returnValue = false;
									if(value.selection.player_id.value == selectInput.player_id.value){
										console.log("Player Id matches");
										returnValue = false;
									}else{
										returnValue = true;
									}
								}else{
									returnValue = true;
								}
							}
						}else{
							returnValue = true;
						}
					}
				}else{
					console.log("Updating");
					console.log(selectList);
				}
				console.log(returnValue);
				if(returnValue == false){
					console.log("Test");
					return returnValue;
				}
			});
			return returnValue;
		}
	}
	//Html block to use when Community has been selected
	var htmlBlock = '<div id="association_selected_-index-" class="my-2 association_selected col-12 col-sm-5 col-md-4 col-lg-3"> <div class="card card-body -cssColor-" style="height:100%"> <div style="height: 250px;"><h4 class="card-title text-white"> -associationName- </h4> <p id="association-name" class="text-white"> -teamName- </p> <p id="association-name" class="text-white"> -playerName- </p></div> <div style="position: absolute; bottom: 10%; left: 0; right: 0;"><h4 class="text-white text-center"><span id="test_-index-" class="percent_span"><input id="spinner_-index-" class="precent_selector" data-index=-index- size=3>%</span></h4><div class="row"><div class="col-12-sm col-centered"><div class="row"><div class="col-6-sm"><button id="changeBtn-index-" class="changeButton btn btn-primary select-button btn" onclick="$(this).changeSelection()" data-index="-index-">Edit</button></div><div class="col-6-sm"> <button id="removeBtn-index-" class="removeButton btn btn-primary select-button btn" onclick="$(this).removeItem()" data-index="-index-">Remove</button></div></div></div><div class="col-sm-12"></div></div></div></div></div>';
		
		//When selecting a sport community to add to community list.
		$("#sportSelectButton").on("click", function(event){
			event.preventDefault();
			var selectionMade = false;
			var startArray = [];
			var connectArray = [true];
			var currentMarkers;
			var selectedInput;
			//Adding values to list
			//Checking what input was used.
			if($("#association_select").val() != ''){
				selectionMade = true;
				selectedInput = {
						province_id: {label: ($("#province_select option:selected").text() != "Select" ? $("#province_select option:selected").text() : ""), value: $("#province_select").val() },
						community_id: {label: ($("#community_select option:selected").text() != "Select" ? $("#community_select option:selected").text() : ""), value: $("#community_select").val()},
						sport_id: {label: ($("#sport_items option:selected").text() != "Select" ? $("#sport_items option:selected").text() : ""), value: $("#sport_items").val()},
						association_id: {label: ($("#association_select option:selected").text() != "Select" ? $("#association_select option:selected").text() : ""), value: $("#association_select").val()},
						division_id: {label: ($("#division_select option:selected").text() != "Select" ? $("#division_select option:selected").text() : ""), value: $("#division_select").val()},
						gender_id: {label: ($("#gender_select option:selected").text() != "Select" ? $("#gender_select option:selected").text() : ""), value: $("#gender_select").val()},
						team_id: {label: ($("#team_id option:selected").text() != "Select" ? $("#team_id option:selected").text() : ""), value: $("#team_id").val()},
						player_id: {label: ($("#player_id option:selected").text() != "Select" ? $("#player_id option:selected").text() : ""), value: $("#player_id").val()}
					}
			}else if($("#sport_query").select2("val") != ''){
				var data = $("#sport_query").select2('data');
				console.log($("#sport_query").select2('data'));
				selectionMade = true;
				selectedInput = {
						province_id: {label: data[0].province_code, value: data[0].province_code },
						community_id: {label: data[0].community, value: data[0].community},
						sport_id: {label: data[0].sport, value: data[0].sport},
						association_id: {label: data[0].name , value: data[0].association_id},
						division_id: {label: data[0].teams[0] != null ? data[0].teams[0].division : "", value: data[0].teams[0] != null ? data[0].teams[0].division : ""},
						gender_id: {label: data[0].teams[0] != null ? data[0].teams[0].gender : "", value: data[0].teams[0] != null ? data[0].teams[0].gender : ""},
						team_id: {label: data[0].teams[0] != null ? data[0].teams[0].name : "", value: data[0].teams[0] != null ? data[0].teams[0].team_id : ""},
						player_id: {label: (data[0].teams[0] != null ? data[0].teams[0].players[0] != null ? data[0].teams[0].players[0].name : "" : ""), value: (data[0].teams[0] != null ? data[0].teams[0].players[0] != null ? data[0].teams[0].players[0].player_id : "" : "")}
					}
			}
			
			if(selectionMade){
				//Create slider if not already
				if(softSlider.noUiSlider != undefined){
					currentMarkers = softSlider.noUiSlider.get();
				}
				//Making sure selection has not already been made
				if(checkInput(selectedInput, "sport", null)){
					$("#sport-alert").hide();
					//Updating list with new item 
					updateListItems(selectList.length, selectedInput, "sport");
					for(var i = 1; i < selectList.length; i++){
						//Getting new marker if needed
						//Will be 10 less then previous selection if there is any.
						if($.isArray(currentMarkers)){
							if(currentMarkers[i-1] != undefined){
								console.log("CurrentMarker"+currentMarkers[i-1]);
								//markerTotal += currentMarkers[i-1];
								startArray.push(currentMarkers[i-1]-10.00);
							}else{
								console.log("CurrentMarker + 10 "+(currentMarkers[i-2]-10.00));
								startArray.push((currentMarkers[i-2]-10.00));
							}
						}else{
							if(currentMarkers != undefined){
								startArray.push(currentMarkers-10.00);
							}else{
								startArray.push(10);
							}
						}
						
						connectArray.push(true);
					}
					//Update slider with new item in list.
					updateSlider(startArray, connectArray);
					$("#sport_query").empty().trigger("change");
					$("#association_sport_row").cascadingDropdown('destroy');
					$("#sportModal").hide();
					$('.content').removeClass('noscroll');
					$('body').removeClass('noscroll');
				}else{
					$("#sport-alert").show();
				}
			}
				
		});
	//Add item to list if charity is selected by user.
	$("#charitySelectButton").on('click', function(event){
		event.preventDefault();
		var startArray = [];
		var selectionMade = false;
		var connectArray = [true];
		var currentMarkers;
		var selectedInput;
		//Adding values to list
		if($("#name_c_select").val() != ''){
			selectionMade = true;
			selectedInput = {
					province_id: {label: ($("#province_c_select option:selected").text() != "Select" ? $("#province_c_select option:selected").text() : ""), value: $("#province_c_select").val() },
					community_id: {label:  ($("#community_c_select option:selected").text() != "Select" ?  $("#community_c_select option:selected").text() : ""), value: $("#community_c_select").val()},
					association_id: {label: ($("#name_c_select option:selected").text() != "Select" ?  $("#name_c_select option:selected").text() : ""), value: $("#name_c_select").val()}
//					receipt: $("#receipt_selection").prop("checked")
				}
		}else if($("#charity_query").select2("val") != ''){
			selectionMade = true;
			var data = $("#charity_query").select2('data');
//			console.log($("#charity_query").select2('data'));
			selectionMade = true;
			selectedInput = {
					province_id: {label: data[0].province_code, value: data[0].province_code },
					community_id: {label:  (data[0].community), value: data[0].community},
					association_id: {label: data[0].name, value: data[0].association_id}
//					receipt: $("#receipt_selection").prop("checked")
				}
		}
		
		if(selectionMade){
			if(softSlider.noUiSlider != undefined){
				currentMarkers = softSlider.noUiSlider.get();
			}
			//Making sure selection has not already been made
			if(checkInput(selectedInput, "charity", null)){
				$("#charity-alert").hide();
				//Updating list items with new selection.
				updateListItems(selectList.length, selectedInput, "charity");
				//Getting new marker if needed
				//Will be 10 less then previous selection if there is any.
				for(var i = 1; i < selectList.length; i++){
					if($.isArray(currentMarkers)){
						if(currentMarkers[i-1] != undefined){
							startArray.push(currentMarkers[i-1]-10.00);
						}else{
							startArray.push((currentMarkers[i-2]-10.00));
						}
					}else{
						if(currentMarkers != undefined){
							startArray.push(currentMarkers-10.00);
						}else{
							startArray.push(10);
						}
					}
					
					connectArray.push(true);
				}
					//Update slider with new element
					updateSlider(startArray, connectArray);
					$("#charity_query").empty().trigger("change");
					$("#association_charity_row").cascadingDropdown('destroy');
//					$("#receipt_selection").prop("checked", false);
					$("#charitiyModal").hide();
					$('.content').removeClass('noscroll');
					$('body').removeClass('noscroll');
				}else{
					$("#charity-alert").show();
				}
		}
			
		
	})
	//Adding item to list if the user select nonprof to add to list
	$("#nonProfSelectButton").on('click', function(event){
		event.preventDefault();
		
		var startArray = [];
		var selectionMade = false;
		var connectArray = [true];
		var currentMarkers;
		var selectedInput;
		
		//Getting values to add to list
		if($("#name_nonp_select").val() != ''){
			selectionMade = true;
			selectedInput = {
					province_id: {label: ($("#province_nonp_select option:selected").text() != "Select" ? $("#province_nonp_select option:selected").text() : ""), value: $("#province_nonp_select").val() },
					community_id: {label: ($("#community_nonp_select option:selected").text() != "Select" ? $("#community_nonp_select option:selected").text() : ""), value: $("#community_nonp_select").val()},
					association_id: {label: ($("#name_nonp_select option:selected").text() != "Select" ? $("#name_nonp_select option:selected").text() : ""), value: $("#name_nonp_select").val()}
				}
		}else if($("#nonProf_query").select2("val") != ''){
			var data = $("#nonProf_query").select2('data');
			selectionMade = true;
			selectedInput = {
					province_id: {label: data[0].province_code, value: data[0].province_code },
					community_id: {label:  (data[0].community), value: data[0].community},
					association_id: {label: data[0].name, value: data[0].association_id}
				}
		}
		
		if(selectionMade){
			if(softSlider.noUiSlider != undefined){
				currentMarkers = softSlider.noUiSlider.get();
			}
			//Checking if item has already been selected
			if(checkInput(selectedInput, "nonProf", null)){
				$("#nonprof-alert").hide();
				//Adding new item to list
				updateListItems(selectList.length, selectedInput, "nonProf");
				//Getting new marker if needed
				//Will be 10 less then previous selection if there is any.
				for(var i = 1; i < selectList.length; i++){
					if($.isArray(currentMarkers)){
						if(currentMarkers[i-1] != undefined){
							console.log("CurrentMarker"+currentMarkers[i-1]);
							//markerTotal += currentMarkers[i-1];
							startArray.push(currentMarkers[i-1]-10.00);
						}else{
							console.log("CurrentMarker + 10 "+(currentMarkers[i-2]-10.00));
							startArray.push((currentMarkers[i-2]-10.00));
						}
					}else{
						if(currentMarkers != undefined){
							startArray.push(currentMarkers-10.00);
						}else{
							startArray.push(10);
						}
					}
					
					connectArray.push(true);
				}
					//Updating slider with new item
					updateSlider(startArray, connectArray);
					$("#association_nonProf_row").cascadingDropdown('destroy');
					$("#nonProf_query").empty().trigger("change");
					$("#nonProfModal").hide();
					$('.content').removeClass('noscroll');
					$('body').removeClass('noscroll');
				}else{
					$("#nonprof-alert").show();
				}
		}
		
	})
	//If user selectes personal item
	$("#personalOption").on('click', function(){
		event.preventDefault();
		
		var startArray = [];
		var selectionMade = false;
		var connectArray = [true];
		var currentMarkers;
		var selectedInput;
		var personalCheck = false;
		console.log("test");
		$.each(selectList, function(item){
			if(item.type == "personal"){
				personalCheck = true;
			}
		})
		if(!personalCheck){
			console.log("After");
			selectedInput = {
					personal: true
				}
				//Getting new marker if needed
				//Will be 10 less then previous selection if there is any.
				if(checkInput(selectedInput, "personal", null)){
					updateListItems(selectList.length, selectedInput, "personal", 0);
					for(var i = 1; i < selectList.length; i++){
						if($.isArray(currentMarkers)){
							if(currentMarkers[i-1] != undefined){
								console.log("CurrentMarker"+currentMarkers[i-1]);
								//markerTotal += currentMarkers[i-1];
								startArray.push(currentMarkers[i-1]-10.00);
							}else{
								console.log("CurrentMarker + 10 "+(currentMarkers[i-2]-10.00));
								startArray.push((currentMarkers[i-2]-10.00));
							}
						}else{
							if(currentMarkers != undefined){
								startArray.push(currentMarkers-10.00);
							}else{
								startArray.push(10);
							}
						}
						
						connectArray.push(true);
					}
					//Update slider.
					updateSlider(startArray, connectArray);
				}
		}
	})
	
	//Selecting item from community list
	$("select").on("click", function() {
		console.log('Click');
	    if ($(this).attr("data-type") == "association") {
	        previous = $(this).val();
	    }
	})
	//Removing items
	$.fn.removeItem = function(){
		//Will need to remove the item that was selected.
		var testIndex = $(this).attr("data-index");
		var count = 0;
		$("#association_selected_"+testIndex).remove();
		selectList.splice(testIndex, 1);
		var personalCheck = false;
		$.each(selectList, function(newIndex, value){
			count++;
			if(value.index >= testIndex){
				$("#association_selected_"+value.index).remove();
				selectList[newIndex].index = newIndex;
				selectList[newIndex].color = 'c-'+newIndex+'-color';
				switch(selectList[value.index].type){
				case "sport":
					$('#items-row').children('#myBtn').before(htmlBlock.replace(/\-cssColor-/g, selectList[value.index].color).replace(/\-associationName-/g, selectList[value.index].selection.association_id.label).replace(/\-teamName-/g, selectList[value.index].selection.team_id.label).replace(/\-playerName-/g, selectList[value.index].selection.player_id.label).replace(/\-index-/g, value.index));
					break;
				case "charity":
					$('#items-row').children('#myBtn').before(htmlBlock.replace(/\-cssColor-/g, selectList[value.index].color).replace(/\-associationName-/g, selectList[value.index].selection.association_id.label).replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, value.index));
					break;
				case "nonProf":
					$('#items-row').children('#myBtn').before(htmlBlock.replace(/\-cssColor-/g, selectList[value.index].color).replace(/\-associationName-/g, selectList[value.index].selection.association_id.label).replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, value.index));
					personalCheck = true;
					break;
				case "personal":
					$('#items-row').children('#myBtn').before(htmlBlock.replace(/\-cssColor-/g, selectList[value.index].color).replace(/\-cssColor-/g, 'c-'+value.index+'-color').replace(/\-associationName-/g, "Personal").replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, value.index));
					personalCheck = true;
					break;
				}
			}
			if(count == selectList.length){
				var startArray = [];
				var connectArray = [true];
				var currentMarkers;
				
				if(softSlider.noUiSlider != undefined){
					currentMarkers = softSlider.noUiSlider.get();
				}
				
				for(var i = 1; i < selectList.length; i++){
					if($.isArray(currentMarkers)){
						if(currentMarkers[i-1] != undefined){
							console.log("CurrentMarker"+currentMarkers[i-1]);
							//markerTotal += currentMarkers[i-1];
							startArray.push(currentMarkers[i-1]-10.00);
						}else{
							console.log("CurrentMarker + 10 "+(currentMarkers[i-2]-10.00));
							startArray.push((currentMarkers[i-2]-10.00));
						}
					}else{
						if(currentMarkers != undefined){
							startArray.push(currentMarkers-10.00);
						}else{
							startArray.push(10);
						}
					}
					
					connectArray.push(true);
				}
				
				updateSlider(startArray, connectArray);
				
			}
		})
		
		if(!personalCheck){
			$("#personal_option").show();
		}else{
			$("#personal_option").hide();
		}
		
		if(selectList.length == 0){
			softSlider.noUiSlider.destroy();
			$("#slider-info").hide();
		}
		
		
	}
	
	var tempIndexForUpdate;
	//When Changing selection from list.
	$.fn.changeSelection = function(){
		console.log("Test");
		console.log($(this).attr("data-index"));
		var testIndex = $(this).attr("data-index");
		tempIndexForUpdate = testIndex;
		switch(selectList[testIndex].type){
		case "sport":
			implementSportSelect(testIndex);
			$("#sportModal").show();
			$('.content').addClass('noscroll');
			$('body').addClass('noscroll');
			$("#selectSportButton").hide();
			$("#updateSportButton").show();
			break;
		case "charity":
			implementCharitySelect(testIndex);
			$("#charitiyModal").show();
			$('.content').addClass('noscroll');
			$('body').addClass('noscroll');
			$("#selectCharityButton").hide();
			$("#updateCharityButton").show();
			break;
		case "nonProf":
			implementNonProfSelect(testIndex);
			$("#nonProfModal").show();
			$('.content').addClass('noscroll');
			$('body').addClass('noscroll');
			$("#selectNonProfButton").hide();
			$("#updateNonProfButton").show();
			break;
		}
	}
	//Update the newly selected item
	$(".update_button").on('click', function(){
		console.log(htmlBlock)
		var nextItem = parseInt(tempIndexForUpdate) + 1;
		switch(selectList[tempIndexForUpdate].type){
		case "sport":
			var updateSelection;
			var data = $("#sport_query").select2('data');
			console.log(data);
			if(data[0] != null){
				updateSelection = {
						province_id: {label: data[0].province_code, value: data[0].province_code },
						community_id: {label: data[0].community, value: data[0].community},
						sport_id: {label: data[0].sport, value: data[0].sport},
						association_id: {label: data[0].name , value: data[0].association_id},
						division_id: {label: data[0].teams[0] != null ? data[0].teams[0].division : "", value: data[0].teams[0] != null ? data[0].teams[0].division : ""},
						gender_id: {label: data[0].teams[0] != null ? data[0].teams[0].gender : "", value: data[0].teams[0] != null ? data[0].teams[0].gender : ""},
						team_id: {label: data[0].teams[0] != null ? data[0].teams[0].name : "", value: data[0].teams[0] != null ? data[0].teams[0].team_id : ""},
						player_id: {label: (data[0].teams[0] != null ? data[0].teams[0].players[0] != null ? data[0].teams[0].players[0].name : "" : ""), value: (data[0].teams[0] != null ? data[0].teams[0].players[0] != null ? data[0].teams[0].players[0].player_id : "" : "")}
					}
			}else{
				updateSelection = {
						province_id: {label: ($("#province_select option:selected").text() != "Select" ? $("#province_select option:selected").text() : ""), value: $("#province_select").val() },
						community_id: {label: ($("#community_select option:selected").text() != "Select" ? $("#community_select option:selected").text() : ""), value: $("#community_select").val()},
						sport_id: {label: ($("#sport_items option:selected").text() != "Select" ? $("#sport_items option:selected").text() : ""), value: $("#sport_items").val()},
						association_id: {label: ($("#association_select option:selected").text() != "Select" ? $("#association_select option:selected").text() : ""), value: $("#association_select").val()},
						division_id: {label: ($("#division_select option:selected").text() != "Select" ? $("#division_select option:selected").text() : ""), value: $("#division_select").val()},
						gender_id: {label: ($("#gender_select option:selected").text() != "Select" ? $("#gender_select option:selected").text() : ""), value: $("#gender_select").val()},
						team_id: {label: ($("#team_id option:selected").text() != "Select" ? $("#team_id option:selected").text() : ""), value: $("#team_id").val()},
						player_id: {label: ($("#player_id option:selected").text() != "Select" ? $("#player_id option:selected").text() : ""), value: $("#player_id").val()}
					}
			}
			if(checkInput(updateSelection, selectList[tempIndexForUpdate].type, tempIndexForUpdate)){
				$("#sport-alert").hide();
				selectList[tempIndexForUpdate].selection = updateSelection;
				$("#association_selected_"+tempIndexForUpdate).remove();
				console.log('#association_selected_'+ nextItem);
				if($('#association_selected_'+nextItem).length){
					$('#items-row').children('#association_selected_'+nextItem).before(htmlBlock.replace(/\-cssColor-/g, selectList[tempIndexForUpdate].color).replace(/\-associationName-/g, selectList[tempIndexForUpdate].selection.association_id.label).replace(/\-teamName-/g, selectList[tempIndexForUpdate].selection.team_id.label).replace(/\-playerName-/g, selectList[tempIndexForUpdate].selection.player_id.label).replace(/\-index-/g, tempIndexForUpdate));
				}else{
					$('#items-row').children('#myBtn').before(htmlBlock.replace(/\-cssColor-/g, selectList[tempIndexForUpdate].color).replace(/\-associationName-/g, selectList[tempIndexForUpdate].selection.association_id.label).replace(/\-teamName-/g, selectList[tempIndexForUpdate].selection.team_id.label).replace(/\-playerName-/g, selectList[tempIndexForUpdate].selection.player_id.label).replace(/\-index-/g, tempIndexForUpdate));
				}
				$("#sport_query").empty().trigger("change");
				$("#sportModal").hide();
				$('.content').removeClass('noscroll');
				$('body').removeClass('noscroll');
				$("#association_sport_row").cascadingDropdown('destroy');
			}else{
				$("#sport-alert").show();
			}
			break;
		case "charity":
			var updateSelection;
			var data = $("#charity_query").select2('data');
			if(data[0] != null){
				updateSelection = {
						province_id: {label: data[0].province_code, value: data[0].province_code},
						community_id: {label:  (data[0].community), value: data[0].community},
						association_id: {label: data[0].name, value: data[0].association_id}
//						receipt: $("#receipt_selection").prop("checked")
					}
			}else{
				updateSelection = {
						province_id: {label: ($("#province_c_select option:selected").text() != "Select" ? $("#province_c_select option:selected").text() : ""), value: $("#province_c_select").val() },
						community_id: {label:  ($("#community_c_select option:selected").text() != "Select" ?  $("#community_c_select option:selected").text() : ""), value: $("#community_c_select").val()},
						association_id: {label: ($("#name_c_select option:selected").text() != "Select" ?  $("#name_c_select option:selected").text() : ""), value: $("#name_c_select").val()}
//						receipt: $("#receipt_selection").prop("checked")
					}
			}
			if(checkInput(updateSelection, selectList[tempIndexForUpdate].type, tempIndexForUpdate)){
				$("#charity-alert").hide();
				selectList[tempIndexForUpdate].selection = updateSelection;
				$("#association_selected_"+tempIndexForUpdate).remove();
				if($('#association_selected_'+nextItem).length){
					$('#items-row').children('#association_selected_'+nextItem).before(htmlBlock.replace(/\-cssColor-/g, selectList[tempIndexForUpdate].color).replace(/\-associationName-/g, selectList[tempIndexForUpdate].selection.association_id.label).replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, tempIndexForUpdate));
				}else{
					$('#items-row').children('#myBtn').before(htmlBlock.replace(/\-cssColor-/g, selectList[tempIndexForUpdate].color).replace(/\-associationName-/g, selectList[tempIndexForUpdate].selection.association_id.label).replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, tempIndexForUpdate));
				}
				$("#charity_query").empty().trigger("change");
				$("#charitiyModal").hide();
				$('.content').removeClass('noscroll');
				$('body').removeClass('noscroll');
				$("#association_charity_row").cascadingDropdown('destroy');
//				$("#receipt_selection").prop("checked", false);
			}else{
				$("#charity-alert").show();
			}
			break;
		case "nonProf":
			var updateSelection;
			var data = $("#nonProf_query").select2('data');
			if(data[0] != null){
				updateSelection = {
						province_id: {label: data[0].province_code, value: data[0].province_code},
						community_id: {label:  (data[0].community), value: data[0].community},
						association_id: {label: data[0].name, value: data[0].association_id}
					}
			}else{
				updateSelection = {
						province_id: {label: ($("#province_nonp_select option:selected").text() != "Select" ? $("#province_nonp_select option:selected").text() : ""), value: $("#province_nonp_select").val() },
						community_id: {label: ($("#community_nonp_select option:selected").text() != "Select" ? $("#community_nonp_select option:selected").text() : ""), value: $("#community_nonp_select").val()},
						association_id: {label: ($("#name_nonp_select option:selected").text() != "Select" ? $("#name_nonp_select option:selected").text() : ""), value: $("#name_nonp_select").val()}
					}
			}
			if(checkInput(updateSelection, selectList[tempIndexForUpdate].type, tempIndexForUpdate)){
				$("#nonprof-alert").hide();
				selectList[tempIndexForUpdate].selection = updateSelection;
				$("#association_selected_"+tempIndexForUpdate).remove();
				if($('#association_selected_'+nextItem).length){
					$('#items-row').children('#association_selected_'+nextItem).before(htmlBlock.replace(/\-cssColor-/g, selectList[tempIndexForUpdate].color).replace(/\-associationName-/g, selectList[tempIndexForUpdate].selection.association_id.label).replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, tempIndexForUpdate));
				}else{
					$('#items-row').children('#myBtn').before(htmlBlock.replace(/\-cssColor-/g, selectList[tempIndexForUpdate].color).replace(/\-associationName-/g, selectList[tempIndexForUpdate].selection.association_id.label).replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, tempIndexForUpdate));
				}
				$("#nonProf_query").empty().trigger("change");
				$("#nonProfModal").hide();
				$('.content').removeClass('noscroll');
				$('body').removeClass('noscroll');
				$("#association_nonProf_row").cascadingDropdown('destroy');
			}else{
				$("#nonprof-alert").show();
			}
			break;
		}
		updateSpinners();
		var diffDivs = [];
		
		$(".percent_span").each(function(index){
			diffDivs.push(document.getElementById('spinner_'+index));
		})
		
		softSlider.noUiSlider.on('update', function( values, handle ) {
				for(var e = 0; e < diffDivs.length; e++){
					var currentValue = (values[e] || 100);
					var previousValue = (values[(e-1)] || 0);
					diffDivs[e].value = currentValue - previousValue;
					selectList[e].marker = currentValue - previousValue;
				}
		});
		
	})
})

