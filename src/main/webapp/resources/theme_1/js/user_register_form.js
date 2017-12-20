var selectedValues = [];
var previous = "";


$(document).ready(function() {
	
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

	
	
	// Need to add a method that will check for the username.
	
	$("#regForm").validate({
		errorPlacement: function errorPlacement(error, element) { element.before(error); },
		debug: true,
	    rules: {
	    	first_name:{
	    		requried: true
	    	},
	    	last_name:{
	        	requried: true
	        },
	        username:{
	        	required: true,
	        	remote: {
	                url: "../checkusername",
	                type: "get",
	                data:{
	                	username: function(){
	                		return $("#username").val();
	                	}
	                }
	            }
	        },
	        password: {
	        	required: true
	        },
	        confirmPass:{
	        	required: true,
	        	equalTo: "#password"
	        },
	        "userProfile.gender":{
	        	required: true
	        },
	        day:{
	        	required: true
	        },
	        month:{
	        	required: true
	        },
	        year: {
	        	required: true
	        },
	        "userProfile.phone":{
	        	required: true,
	        	phoneUS: true
	        },
	        email:{
	        	required: true,
	        	email: true
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
	        postal_code:{
				required: true,
				cdnPostal: true
			}
	    },
	    messages: {
	        password: {
	        	required: "Please enter a password"
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
	        	email: "Please enter a valid email"
	        },
	        confirmEmail:{
	        	required: "Please enter your email to confirm",
	        	equalTo: "email must match"
	        },
	        "userProfile.postal_code":{
				cdnPostal: "Please enter a valid postal code"
			}
	    }
	});
	
	$('#regForm').steps({
		headerTag : "title",
		bodyTag : "div",
		transitionEffect : "slideLeft",
		stepsOrientation : "horizontal",
		onStepChanging : function(event, currentIndex, newIndex) {
			
			return true;
		},
        // Triggered when clicking the Previous/Next buttons
        onStepChanging: function(e, currentIndex, newIndex) {
			 if(newIndex > currentIndex){
			 console.log("Next")
			 $(this).validate().settings.ignore = ":disabled,:hidden";
			 console.log($(this).valid())
			 return $(this).valid();
			 //return true;
			 }else{
			 console.log("previous")
			 return true
			 }
        	//return true;
        },
        // Triggered when clicking the Finish button
        onFinishing: function(e, currentIndex) {
            var teamIsSelected = false;
            var total = 0;
        	$(this).validate().settings.ignore = ":disabled,:hidden";
        	
//        	var diffDivs = [];
        	
//        	$(".percent_span").each(function(index){
//    			diffDivs.push(document.getElementById('test_'+index));
//    		})
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
        	    				$(this).children("#chairty_recipts_"+index).val(selectList[index].selection.receipt);
        					break;
        					case "nonProf":
        						$(this).children("#donation_dysplay_"+index).val(selectList[index].marker/100);
        	    				$(this).children("#nonprof_id_"+index).val(selectList[index].selection.association_id.value);
        					break;
        				}
        			}
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
        	$("#regForm .actions a[href='#finish']").hide();
        	console.log($("#regForm").serialize());
//        	$("#donation_dysplay_0").val($("#slider_select_0").slider('option', 'value') / 100);
//        	$("#donation_dysplay_1").val($("#slider_select_1").slider('option', 'value') / 100);
//        	$("#donation_dysplay_2").val($("#slider_select_2").slider('option', 'value') / 100);
//        	$("#donation_dysplay_3").val($("#slider_select_3").slider('option', 'value') / 100);
        	
        	$("#date_of_birth").val($("#dobday").val()+"/"+$("#dobmonth").val()+"/"+$("#dobyear").val());
            // For testing purpose
// console.log("1 "+$("#donation_dysplay_0").val());
// console.log("2 "+$("#donation_dysplay_1").val());
// console.log("3 "+$("#donation_dysplay_2").val());
// console.log("4 "+$("#donation_dysplay_3").val());
// console.log("DOB "+$("#date_of_birth").val());
        	$.ajax({
        	    url: './registerProcess',
        	    type: "POST",
        	    data: $("#regForm").serialize(),
        	    complete: function(data){
        	    	window.location.replace(data.responseText);
        	    	//console.log("Done");
        	    },
        	    error: function (xhr, ajaxOptions, thrownError) {
        	    	$("#regForm .actions a[href='#finish']").show();
        	        alert("Something went wrong")
        	      }
        	})
        }
		
	})
	
	
	$('#card_select').on('change', function() {
		console.log("test")
		var cardSelectValue = $(this).val();
		$('.card_member').each(function(index) {
// console.log("select " + index);
// console.log("value " + cardSelectValue);
			if (index < cardSelectValue) {
				$(this).show();
			} else {
				$("#card_member_"+index+ " :input").each(function(){
					$(this).val("");
				})
				$(this).hide();
			}
		})
	});
	
	//document.getElementById('test_4')
	
	var limitCount = $(".association_list").length;
	console.log(limitCount);
	
	var selectList = [];
	
	var softSlider = document.getElementById('slider');
	
	function updateSlider(startArray, connectArray){
		var diffDivs = [];
		
		$(".percent_span").each(function(index){
			diffDivs.push(document.getElementById('test_'+index));
		})
		console.log("diffCount "+diffDivs.length);
		if(softSlider.noUiSlider != undefined){
			softSlider.noUiSlider.destroy();
		}
		console.log(selectList.length)
		if(selectList.length == 1){
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

		softSlider.noUiSlider.on('update', function( values, handle ) {
			// valuesDivs[handle].innerHTML = values[handle];
//					diffDivs[0].innerHTML = values[0] - 0;
//					diffDivs[1].innerHTML = values[1] - values[0];
//					diffDivs[2].innerHTML = values[2] - values[1];
//					diffDivs[3].innerHTML = values[3] - values[2];
//					diffDivs[4].innerHTML = 100 - values[3];
				for(var e = 0; e < diffDivs.length; e++){
					var currentValue = (values[e] || 100);
					var previousValue = (values[(e-1)] || 0);
					diffDivs[e].innerHTML = currentValue - previousValue;
					selectList[e].marker = currentValue - previousValue;
				}
				});
		
		var connect = slider.querySelectorAll('.noUi-connect');
		//var classes = ['c-0-color', 'c-1-color', 'c-2-color', 'c-3-color', 'c-4-color'];
		
		for ( var i = 0; i < connect.length; i++ ) {
			if(selectList[i] != undefined){
				connect[i].classList.add(selectList[i].color);
			}
		}
	}
	
	function implementSportSelect(testIndex){
		console.log(testIndex);
		$("#association_sport_row").cascadingDropdown({
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
//						if($('#association_select').val() == 0){
//							$("#selectButton").addClass('disabled');
//						}else{
//							$("#selectButton").removeClass('disabled');
//						}
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
									return{
										label: item.division,
										value: item.division
									}
								}))
								if(testIndex != undefined){
									$.when.apply($, $.map).done(function() {
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
//								$('.team').each(function(){
//							    	var select = $(this);
//							    	$("#"+select.attr("id") + " option").each(function(){
//							    		var hide = false;
//							    		var option = $(this);
//							    		$.each(selectedValues, function(index, value){
//							    			if(value == option.val() && select.val() != value){
//							    				hide = true;
//							    			}
//							    		});
//							    		if(hide){
//							    			option.hide();
//							    		}else{
//							    			option.show();
//							    		}
//							    	});
//							    })
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
									return{
										label: item.name,
										value: item.player_id
									}
								}))
							}
						})
						if(testIndex != undefined){
							$.when.apply($, $.map).done(function() {
								$("#player_id").val(selectList[testIndex].selection.player_id.value);
								$('#player_id').trigger('change');
							});
						}
					}
				}
			]
		})
	}
	
	function implementCharitySelect(testIndex){
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
			$("#receipt_selection").prop("checked", selectList[testIndex].selection.receipt);
		}
	}
	
	function implementNonProfSelect(testIndex){
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
	
	function updateListItems(index, selectedInput, type){
		selectList.push({
			index: index,
			type: type,
			color: 'c-'+index+'-color',
			marker: 0,
			selection: selectedInput
		});
		
		console.log(selectList);
		if(selectList.length >= limitCount){
			$("#myBtn").hide();
		}
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
		}
		
	}
	

	$.dobPicker({
		daySelector : '#dobday',
		monthSelector : '#dobmonth',
		yearSelector : '#dobyear',
		// Minimum age
		minimumAge : 0,
		// Maximum age
		maximumAge : 100

	});

	$('#phone').mask('(000) 000-0000');
	$('.date_of_birth').mask('00/00/0000');

	$('.card_member').hide();
	
	$("#nonprof-alert").hide();
	$("#charity-alert").hide();
	$("#sport-alert").hide();
	
	$("#myBtn").on("click", function(){
		//$("#myModal_"+(selectList.length)).show();
		//implementSportSelect();
		$("#selectionModal").show();
	});
	
	$("#sportSelect").on("click", function(){
		//$("#myModal_"+(selectList.length)).show();
		implementSportSelect();
		$("#selectionModal").hide();
		$("#sportModal").show();
		
		$("#selectSportButton").show();
		$("#updateSportButton").hide();
	})
	
	$("#charitySelect").on("click", function(){
		implementCharitySelect();
		$("#selectionModal").hide();
		$("#charitiyModal").show();
		
		$("#selectCharityButton").show();
		$("#updateCharityButton").hide();
	})
	
	$("#profitSelect").on("click", function(){
		implementNonProfSelect();
		$("#selectionModal").hide();
		$("#nonProfModal").show();
		
		$("#selectNonProfButton").show();
		$("#updateNonProfButton").hide();
	})
	
	
	$(".close, .cancel_button").each(function(){
		$(this).on("click", function(){
			$(".modal").each(function(){
				$('.alert-danger').hide();
				$(".association-test").each(function(){
					if($(this).cascadingDropdown() != undefined){
						$(this).cascadingDropdown('destroy');
					}
				})
				//$("#receipt_selection").val(false);
				$("#receipt_selection").prop("checked", false);
				$(this).hide();
			});
		})
	})
	
	function checkInput(selectInput, type, tempIndexForUpdate){
		console.log("Check Input");
		var returnValue = true;
		if(selectList.length == 0){
			return returnValue;
		}else{
			console.log("Check Input loop");
			$.each(selectList, function(index, value){
				console.log(tempIndexForUpdate);
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
	
	var htmlBlock = '<div id="association_selected_-index-" class="my-2 box association_selected col-lg-2 col-md-4 col-sm-5 col-12"> <div class="card card-body -cssColor- " style="height: 100%;"> <h4 class="card-title text-white"> -associationName- </h4> <p id="association-name" class="text-white"> -teamName-  -playerName- </p> <h4 class="text-white"> <span id="test_-index-" class="percent_span"> -percent- </span>% </h4> <div class="row"><div class="col-12-sm col-centered"><div class="row"><div class="col-6-sm"><button id="changeBtn-index-" class="changeButton btn btn-primary btn" onclick="$(this).changeSelection()" data-index="-index-">Change</button></div><div class="col-6-sm"> <button id="removeBtn-index-" class="removeButton btn btn-primary btn" onclick="$(this).removeItem()" data-index="-index-">Remove</button></div></div></div><div class="col-sm-12"></div></div></div></div>';
	
		$("#sportSelectButton").on("click", function(event){
			event.preventDefault();
			console.log($("#association_select").val());
			if($("#association_select").val() != ''){
				var startArray = [];
				var connectArray = [true];
				var currentMarkers;
				var selectedInput = {
						province_id: {label: ($("#province_select option:selected").text() != "Select" ? $("#province_select option:selected").text() : ""), value: $("#province_select").val() },
						community_id: {label: ($("#community_select option:selected").text() != "Select" ? $("#community_select option:selected").text() : ""), value: $("#community_select").val()},
						sport_id: {label: ($("#sport_items option:selected").text() != "Select" ? $("#sport_items option:selected").text() : ""), value: $("#sport_items").val()},
						association_id: {label: ($("#association_select option:selected").text() != "Select" ? $("#association_select option:selected").text() : ""), value: $("#association_select").val()},
						division_id: {label: ($("#division_select option:selected").text() != "Select" ? $("#division_select option:selected").text() : ""), value: $("#division_select").val()},
						gender_id: {label: ($("#gender_select option:selected").text() != "Select" ? $("#gender_select option:selected").text() : ""), value: $("#gender_select").val()},
						team_id: {label: ($("#team_id option:selected").text() != "Select" ? $("#team_id option:selected").text() : ""), value: $("#team_id").val()},
						player_id: {label: ($("#player_id option:selected").text() != "Select" ? $("#player_id option:selected").text() : ""), value: $("#player_id").val()}
					}
				if(softSlider.noUiSlider != undefined){
					currentMarkers = softSlider.noUiSlider.get();
				}
				if(checkInput(selectedInput, "sport", null)){
					$("#sport-alert").hide();
					updateListItems(selectList.length, selectedInput, "sport");
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
					$("#association_sport_row").cascadingDropdown('destroy');
					$("#sportModal").hide();
				}else{
					$("#sport-alert").show();
				}
				}
				
		});
	
	$("#charitySelectButton").on('click', function(event){
		event.preventDefault();
		console.log("Charity Select");
		
		if($("#name_c_select").val() != ''){
			var startArray = [];
			var connectArray = [true];
			var currentMarkers;
			var selectedInput = {
					province_id: {label: ($("#province_c_select option:selected").text() != "Select" ? $("#province_c_select option:selected").text() : ""), value: $("#province_c_select").val() },
					community_id: {label:  ($("#community_c_select option:selected").text() != "Select" ?  $("#community_c_select option:selected").text() : ""), value: $("#community_c_select").val()},
					association_id: {label: ($("#name_c_select option:selected").text() != "Select" ?  $("#name_c_select option:selected").text() : ""), value: $("#name_c_select").val()},
					receipt: $("#receipt_selection").prop("checked")
				}
			if(softSlider.noUiSlider != undefined){
				currentMarkers = softSlider.noUiSlider.get();
			}
			
			if(checkInput(selectedInput, "charity", null)){
				$("#charity-alert").hide();
				updateListItems(selectList.length, selectedInput, "charity");
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
					$("#association_charity_row").cascadingDropdown('destroy');
					$("#receipt_selection").prop("checked", false);
					$("#charitiyModal").hide();
				}else{
					$("#charity-alert").show();
				}
			}
			
		
	})
	
	$("#nonProfSelectButton").on('click', function(event){
		event.preventDefault();
		console.log("Charity Select");
		
		if($("#name_nonp_select").val() != ''){
			var startArray = [];
			var connectArray = [true];
			var currentMarkers;
			var selectedInput = {
					province_id: {label: ($("#province_nonp_select option:selected").text() != "Select" ? $("#province_nonp_select option:selected").text() : ""), value: $("#province_nonp_select").val() },
					community_id: {label: ($("#community_nonp_select option:selected").text() != "Select" ? $("#community_nonp_select option:selected").text() : ""), value: $("#community_nonp_select").val()},
					association_id: {label: ($("#name_nonp_select option:selected").text() != "Select" ? $("#name_nonp_select option:selected").text() : ""), value: $("#name_nonp_select").val()}
				}
			if(softSlider.noUiSlider != undefined){
				currentMarkers = softSlider.noUiSlider.get();
			}
			
			if(checkInput(selectedInput, "nonProf", null)){
				$("#nonprof-alert").hide();
				updateListItems(selectList.length, selectedInput, "nonProf");
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
					$("#association_nonProf_row").cascadingDropdown('destroy');
					$("#nonProfModal").hide();
				}else{
					$("#nonprof-alert").show();
				}
			}
		
	})
	
	$("select").on("click", function() {
		console.log('Click');
	    if ($(this).attr("data-type") == "association") {
	        previous = $(this).val();
	    }
	})
	
	$.fn.removeItem = function(){
		//Will need to remove the item that was selected.
		var testIndex = $(this).attr("data-index");
		var count = 0;
		$("#association_selected_"+testIndex).remove();
		selectList.splice(testIndex, 1);
		console.log(selectList);
		$.each(selectList, function(newIndex, value){
			count++;
			console.log(value.index +" : "+ testIndex);
			if(value.index >= testIndex){
				console.log($("#association_selected_"+value.index));
				$("#association_selected_"+value.index).remove();
				selectList[newIndex].index = newIndex;
				selectList[newIndex].color = 'c-'+newIndex+'-color';
				//bug when removing anything except the sport teams
				switch(selectList[value.index].type){
				case "sport":
					$('#items-row').children('#myBtn').before(htmlBlock.replace(/\-cssColor-/g, selectList[value.index].color).replace(/\-associationName-/g, selectList[value.index].selection.association_id.label).replace(/\-teamName-/g, selectList[value.index].selection.team_id.label).replace(/\-playerName-/g, selectList[value.index].selection.player_id.label).replace(/\-index-/g, value.index));
					break;
				case "charity":
					$('#items-row').children('#myBtn').before(htmlBlock.replace(/\-cssColor-/g, selectList[value.index].color).replace(/\-associationName-/g, selectList[value.index].selection.association_id.label).replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, value.index));
					break;
				case "nonProf":
					$('#items-row').children('#myBtn').before(htmlBlock.replace(/\-cssColor-/g, selectList[value.index].color).replace(/\-associationName-/g, selectList[value.index].selection.association_id.label).replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, value.index));
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
				
				if(selectList.length == 0){
					softSlider.noUiSlider.destroy();
				}
				
				updateSlider(startArray, connectArray);
			}
		})
	}
	
	var tempIndexForUpdate;

	$.fn.changeSelection = function(){
		console.log($(this).attr("data-index"));
		var testIndex = $(this).attr("data-index");
		tempIndexForUpdate = testIndex;
		switch(selectList[testIndex].type){
		case "sport":
			implementSportSelect(testIndex);
			$("#sportModal").show();
			$("#selectSportButton").hide();
			$("#updateSportButton").show();
			break;
		case "charity":
			implementCharitySelect(testIndex);
			$("#charitiyModal").show();
			$("#selectCharityButton").hide();
			$("#updateCharityButton").show();
			break;
		case "nonProf":
			implementNonProfSelect(testIndex);
			$("#nonProfModal").show();
			$("#selectNonProfButton").hide();
			$("#updateNonProfButton").show();
			break;
		}
	}
	
	$(".update_button").on('click', function(){
		var nextItem = parseInt(tempIndexForUpdate) + 1;
		switch(selectList[tempIndexForUpdate].type){
		case "sport":
			var updateSelection = {
					province_id: {label: ($("#province_select option:selected").text() != "Select" ? $("#province_select option:selected").text() : ""), value: $("#province_select").val() },
					community_id: {label: ($("#community_select option:selected").text() != "Select" ? $("#community_select option:selected").text() : ""), value: $("#community_select").val()},
					sport_id: {label: ($("#sport_items option:selected").text() != "Select" ? $("#sport_items option:selected").text() : ""), value: $("#sport_items").val()},
					association_id: {label: ($("#association_select option:selected").text() != "Select" ? $("#association_select option:selected").text() : ""), value: $("#association_select").val()},
					division_id: {label: ($("#division_select option:selected").text() != "Select" ? $("#division_select option:selected").text() : ""), value: $("#division_select").val()},
					gender_id: {label: ($("#gender_select option:selected").text() != "Select" ? $("#gender_select option:selected").text() : ""), value: $("#gender_select").val()},
					team_id: {label: ($("#team_id option:selected").text() != "Select" ? $("#team_id option:selected").text() : ""), value: $("#team_id").val()},
					player_id: {label: ($("#player_id option:selected").text() != "Select" ? $("#player_id option:selected").text() : ""), value: $("#player_id").val()}
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
				$("#sportModal").hide();
				$("#association_sport_row").cascadingDropdown('destroy');
			}else{
				$("#sport-alert").show();
			}
			break;
		case "charity":
			var updateSelection = {
				province_id: {label: ($("#province_c_select option:selected").text() != "Select" ? $("#province_c_select option:selected").text() : ""), value: $("#province_c_select").val() },
				community_id: {label:  ($("#community_c_select option:selected").text() != "Select" ?  $("#community_c_select option:selected").text() : ""), value: $("#community_c_select").val()},
				association_id: {label: ($("#name_c_select option:selected").text() != "Select" ?  $("#name_c_select option:selected").text() : ""), value: $("#name_c_select").val()},
				receipt: $("#receipt_selection").prop("checked")
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
				$("#charitiyModal").hide();
				$("#association_charity_row").cascadingDropdown('destroy');
				$("#receipt_selection").prop("checked", false);
			}else{
				$("#charity-alert").show();
			}
			break;
		case "nonProf":
			var updateSelection = {
				province_id: {label: ($("#province_nonp_select option:selected").text() != "Select" ? $("#province_nonp_select option:selected").text() : ""), value: $("#province_nonp_select").val() },
				community_id: {label: ($("#community_nonp_select option:selected").text() != "Select" ? $("#community_nonp_select option:selected").text() : ""), value: $("#community_nonp_select").val()},
				association_id: {label: ($("#name_nonp_select option:selected").text() != "Select" ? $("#name_nonp_select option:selected").text() : ""), value: $("#name_nonp_select").val()}
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
				$("#nonProfModal").hide();
				$("#association_nonProf_row").cascadingDropdown('destroy');
			}else{
				$("#nonprof-alert").show();
			}
			break;
		}
		console.log(selectList[tempIndexForUpdate]);
		
		var diffDivs = [];
		
		$(".percent_span").each(function(index){
			diffDivs.push(document.getElementById('test_'+index));
		})
		
		softSlider.noUiSlider.on('update', function( values, handle ) {
				for(var e = 0; e < diffDivs.length; e++){
					var currentValue = (values[e] || 100);
					var previousValue = (values[(e-1)] || 0);
					diffDivs[e].innerHTML = currentValue - previousValue;
					selectList[e].marker = currentValue - previousValue;
				}
		});
	})
})

