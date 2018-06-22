
(function($) {
	var htmlBlock = '<div id="association_selected_-index-" class="my-2 association_selected col-12 col-sm-5 col-md-4 col-lg-3"> <div class="card card-body -cssColor-" style="height:100%"> <div style="height: 250px;"><h4 class="card-title text-white"> -associationName- </h4> <p id="association-name" class="text-white"> -teamName- </p> <p id="association-name" class="text-white"> -playerName- </p></div> <div style="position: absolute; bottom: 10%; left: 0; right: 0;"><h4 class="text-white text-center"><span id="test_-index-" class="percent_span"><input id="spinner_-index-" class="precent_selector" data-index=-index- size=3>%</span></h4><div class="row"><div class="col-12-sm col-centered"><div class="row"><div class="col-6-sm"><button id="changeBtn-index-" class="changeButton btn btn-primary select-button btn" onclick="$(this).changeSelection()" data-index="-index-">Edit</button></div><div class="col-6-sm"> <button id="removeBtn-index-" class="removeButton btn btn-primary select-button btn" onclick="$(this).removeItem()" data-index="-index-">Remove</button></div></div></div><div class="col-sm-12"></div></div></div></div></div>';
	var selectList = [];
	var softSlider = document.getElementById('slider');
	var limitCount = $(".association_list").length;
	
	function updateSlider(startArray, connectArray){
		console.log("Called");
		var diffDivs = [];
		var testDivs = [];
		$(".percent_span").each(function(index){
			//diffDivs.push(document.getElementById('test_'+index));
			testDivs.push(document.getElementById('spinner_'+index))
		})
		console.log("diffCount "+diffDivs.length);
		if(softSlider.noUiSlider != undefined){
			softSlider.noUiSlider.destroy();
			$("#slider-info").hide();
		}
		console.log(selectList.length)
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

		softSlider.noUiSlider.on('update', function( values, handle ) {
			// valuesDivs[handle].innerHTML = values[handle];
//					diffDivs[0].innerHTML = values[0] - 0;
//					diffDivs[1].innerHTML = values[1] - values[0];
//					diffDivs[2].innerHTML = values[2] - values[1];
//					diffDivs[3].innerHTML = values[3] - values[2];
//					diffDivs[4].innerHTML = 100 - values[3];
				
				for(var e = 0; e < testDivs.length; e++){
					var currentValue = (values[e] || 100);
					var previousValue = (values[(e-1)] || 0);
					//diffDivs[e].innerHTML = currentValue - previousValue;
					selectList[e].marker = currentValue - previousValue;
					if(testDivs[e] != null){
						testDivs[e].value = currentValue - previousValue;
					}
				}
		});
		
		var connect = slider.querySelectorAll('.noUi-connect');
		//var classes = ['c-0-color', 'c-1-color', 'c-2-color', 'c-3-color', 'c-4-color'];
		
		for ( var i = 0; i < connect.length; i++ ) {
			if(selectList[i] != undefined){
				connect[i].classList.add(selectList[i].color);
			}
		}
		
		updateSpinners();
		
	}
	
	function updateSpinners(){
		//NOTE: This need to be clear on what going on....
		console.log("Testing");
		$('.precent_selector').spinner({
					value:0 ,min: 0,max: 100,step: 10,spin: function( event, ui ) {
				//$( "#slider" ).slider({value:ui.value})
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
				//softSlider.noUiSlider.set(ui.value);
			}
		}).focus(function () {
		    $(this).blur();
		});
	}
	document.getElementById("loading-background").style.display = "block";
	$( document ).ready(function() {
	    console.log( "ready!" );
	    $(".association_list").each(function(index){
			var selectedInput;
			var test = $(this).attr("data-index");
			var marker = $("#donation_dysplay_"+index).val()*100;
			console.log("Test");
			//$("#donation_dysplay_"+index).val()
			if($(this).attr("data-id") > 0){
				if($("#association_id_"+index).val() > 0){
					//getSportInfo(test);
					//sportArray.push(index);
					selectList.splice(index, 0,{
						index: index,
						type: "sport",
						color: 'c-'+index+'-color',
						marker: marker,
						selection: {}
					});
				}
				if($("#charity_id_"+index).val() > 0){
					//getCharityInfo(test);
					//charityArray.push(index);
					selectList.splice(index, 0,{
						index: index,
						type: "charity",
						color: 'c-'+index+'-color',
						marker: marker,
						selection: {}
					});
				}
				if($("#nonprof_id_"+index).val() > 0){
					//getNonProfInfo(test);
					//console.log("test")
					//nonProfArray.push(index);
					selectList.splice(index, 0,{
						index: index,
						type: "nonProf",
						color: 'c-'+index+'-color',
						marker: marker,
						selection: {}
					});
				}
				if($("#personal_"+index).val() != "false"){
					selectList.splice(index, 0,{
						index: index,
						type: "personal",
						color: 'c-'+index+'-color',
						marker: marker,
						selection: {}
					});
				}
			}
			
			if(($(".association_list").length - 1) <= index){
				console.log(index +" - "+$(".association_list").length);
				var startArray = [];
			  	var connectArray = [true];
		  		var currentMarkers;
				$.each(selectList, function(testIndex, value){
			  		if(softSlider.noUiSlider != undefined){
			  			currentMarkers = softSlider.noUiSlider.get();
					}
					switch(value.type){
					case "sport":
						$.ajax({
							  dataType: "json",
							  url: 'getSportInfo',
							  data: {association_id: $("#association_id_"+value.index).val(),team_id: $("#team_id_"+value.index).val(),player_id: $("#player_id_"+value.index).val()},
							  success: function(data){
								  console.log(data);
								  value.selection = {
										userAssociatioinid: {label: $("#user_association_id_"+value.index).val(), value: $("#user_association_id_"+value.index).val()},
										active: {label: data.active, value: data.active},
									  	province_id: {label: data.province_code, value: data.province_code },
										community_id: {label: data.community, value: data.community},
										sport_id: {label: data.sport, value: data.sport},
										association_id: {label: data.name, value:data.association_id},
										division_id: {label: data.teams[0] != null ? data.teams[0].division : "", value: data.teams[0] != null ? data.teams[0].division : ""},
										gender_id: {label: data.teams[0] != null ? data.teams[0].gender : "", value: data.teams[0] != null ? data.teams[0].gender : ""},
										team_id: {label: data.teams[0] != null ? data.teams[0].name : "", value: data.teams[0] != null ? data.teams[0].team_id : 0},
										player_id: {label: (data.teams[0] != null ? (data.teams[0].players[0] != null ? data.teams[0].players[0].name : "") : ""), value: (data.teams[0] != null ? (data.teams[0].players[0] != null ? data.teams[0].players[0].player_id : 0) : 0)}
								  }
								  
								  if($("#association_selected_"+(value.index+1)).length){
									  $('#items-row').children('#association_selected_'+(value.index+1)).before(htmlBlock.replace(/\-cssColor-/g, 'c-'+value.index+'-color').replace(/\-associationName-/g, value.selection.association_id.label).replace(/\-teamName-/g, value.selection.team_id.label).replace(/\-playerName-/g, value.selection.player_id.label).replace(/\-index-/g, value.index));
								  }else if($("#association_selected_"+(value.index-1)).length){
									  $('#items-row').children('#association_selected_'+(value.index-1)).after(htmlBlock.replace(/\-cssColor-/g, 'c-'+value.index+'-color').replace(/\-associationName-/g, value.selection.association_id.label).replace(/\-teamName-/g, value.selection.team_id.label).replace(/\-playerName-/g, value.selection.player_id.label).replace(/\-index-/g, value.index));
								  }else{
									  $('#items-row').children('#myBtn').before(htmlBlock.replace(/\-cssColor-/g, 'c-'+value.index+'-color').replace(/\-associationName-/g, value.selection.association_id.label).replace(/\-teamName-/g, value.selection.team_id.label).replace(/\-playerName-/g, value.selection.player_id.label).replace(/\-index-/g, value.index));
								  }
								  
								  var testDivs = [];
									$(".percent_span").each(function(index){
										//diffDivs.push(document.getElementById('test_'+index));
										testDivs.push(document.getElementById('spinner_'+index))
									})
									
									softSlider.noUiSlider.on('update', function( values, handle ) {
										// valuesDivs[handle].innerHTML = values[handle];
//												diffDivs[0].innerHTML = values[0] - 0;
//												diffDivs[1].innerHTML = values[1] - values[0];
//												diffDivs[2].innerHTML = values[2] - values[1];
//												diffDivs[3].innerHTML = values[3] - values[2];
//												diffDivs[4].innerHTML = 100 - values[3];
											
											for(var e = 0; e < testDivs.length; e++){
												var currentValue = (values[e] || 100);
												var previousValue = (values[(e-1)] || 0);
												//diffDivs[e].innerHTML = currentValue - previousValue;
												selectList[e].marker = currentValue - previousValue;
												if(testDivs[e] != null){
													testDivs[e].value = currentValue - previousValue;
												}
											}
									});
									
									updateSpinners();
							  }
						})
						break;
					case "charity":
						$.ajax({
							  dataType: "json",
							  url: 'getCharityInfo',
							  data: {charity_id: $("#charity_id_"+value.index).val()},
							  success: function(data){
								  console.log(data);
								  value.selection = {
										  userAssociatioinid: {label: $("#user_association_id_"+value.index).val(), value: $("#user_association_id_"+value.index).val()},
										  active: {label: data.active, value: data.active},
										  province_id: {label: data.province_code, value: data.province_code },
										  community_id: {label:  data.community, value: data.community},
										  association_id: {label: data.name, value: data.association_id}
//										  receipt: $("#chairty_recipts_"+value.index).val()
								  }
								  
								  if($("#association_selected_"+(value.index+1)).length){
									  $('#items-row').children('#association_selected_'+(value.index+1)).before(htmlBlock.replace(/\-cssColor-/g, 'c-'+value.index+'-color').replace(/\-associationName-/g, value.selection.association_id.label).replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, value.index));
								  }else if($("#association_selected_"+(value.index-1)).length){
									  $('#items-row').children('#association_selected_'+(value.index-1)).after(htmlBlock.replace(/\-cssColor-/g, 'c-'+value.index+'-color').replace(/\-associationName-/g, value.selection.association_id.label).replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, value.index));
								  }else{
									  $('#items-row').children('#myBtn').before(htmlBlock.replace(/\-cssColor-/g, 'c-'+value.index+'-color').replace(/\-associationName-/g, value.selection.association_id.label).replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, value.index));
								  }
								  
								  var testDivs = [];
									$(".percent_span").each(function(index){
										//diffDivs.push(document.getElementById('test_'+index));
										testDivs.push(document.getElementById('spinner_'+index))
									})
									
									softSlider.noUiSlider.on('update', function( values, handle ) {
										// valuesDivs[handle].innerHTML = values[handle];
//												diffDivs[0].innerHTML = values[0] - 0;
//												diffDivs[1].innerHTML = values[1] - values[0];
//												diffDivs[2].innerHTML = values[2] - values[1];
//												diffDivs[3].innerHTML = values[3] - values[2];
//												diffDivs[4].innerHTML = 100 - values[3];
											
											for(var e = 0; e < testDivs.length; e++){
												var currentValue = (values[e] || 100);
												var previousValue = (values[(e-1)] || 0);
												//diffDivs[e].innerHTML = currentValue - previousValue;
												selectList[e].marker = currentValue - previousValue;
												if(testDivs[e] != null){
													testDivs[e].value = currentValue - previousValue;
												}
											}
									});
									
									updateSpinners();
							  }
						})
						break;
					case "nonProf":
						$.ajax({
							  dataType: "json",
							  url: 'getNonProfInfo',
							  data: {nonprof_id: $("#nonprof_id_"+value.index).val()},
							  success: function(data){
								  console.log(data);
								  value.selection = {
										  userAssociatioinid: {label: $("#user_association_id_"+value.index).val(), value: $("#user_association_id_"+value.index).val()},
										  active: {label: data.active, value: data.active},
										  province_id: {label: data.province_code, value: data.province_code },
										  community_id: {label:  data.community, value: data.community},
										  association_id: {label: data.name, value: data.association_id}
								  }
								  
								  console.log("#association_selected_"+(value.index))
								  console.log("#association_selected_"+(value.index-1));
								  if($("#association_selected_"+(value.index+1)).length){
									  $('#items-row').children('#association_selected_'+(value.index+1)).before(htmlBlock.replace(/\-cssColor-/g, 'c-'+value.index+'-color').replace(/\-associationName-/g, value.selection.association_id.label).replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, value.index));
								  }else if($("#association_selected_"+(value.index-1)).length){
									  $('#items-row').children('#association_selected_'+(value.index-1)).after(htmlBlock.replace(/\-cssColor-/g, 'c-'+value.index+'-color').replace(/\-associationName-/g, value.selection.association_id.label).replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, value.index));
								  }else{
									  $('#items-row').children('#myBtn').before(htmlBlock.replace(/\-cssColor-/g, 'c-'+value.index+'-color').replace(/\-associationName-/g, value.selection.association_id.label).replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, value.index));
								  }
								  
								  var testDivs = [];
									$(".percent_span").each(function(index){
										//diffDivs.push(document.getElementById('test_'+index));
										testDivs.push(document.getElementById('spinner_'+index))
									})
									
									softSlider.noUiSlider.on('update', function( values, handle ) {
										// valuesDivs[handle].innerHTML = values[handle];
//												diffDivs[0].innerHTML = values[0] - 0;
//												diffDivs[1].innerHTML = values[1] - values[0];
//												diffDivs[2].innerHTML = values[2] - values[1];
//												diffDivs[3].innerHTML = values[3] - values[2];
//												diffDivs[4].innerHTML = 100 - values[3];
											
											for(var e = 0; e < testDivs.length; e++){
												var currentValue = (values[e] || 100);
												var previousValue = (values[(e-1)] || 0);
												//diffDivs[e].innerHTML = currentValue - previousValue;
												selectList[e].marker = currentValue - previousValue;
												if(testDivs[e] != null){
													testDivs[e].value = currentValue - previousValue;
												}
											}
									});
									
									updateSpinners();
							  }
						})
						break;
						case "personal":
							console.log("Personal")
							console.log(value)
							$("#personal_option").hide();
							value.selection = {
								userAssociatioinid: {label: $("#user_association_id_"+value.index).val(), value: $("#user_association_id_"+value.index).val()},
								personal: true
							}
							if($("#association_selected_"+(value.index+1)).length){
								  $('#items-row').children('#association_selected_'+(value.index+1)).before(htmlBlock.replace('<div class="col-6-sm"><button id="changeBtn-index-" class="changeButton btn btn-primary select-button btn" onclick="$(this).changeSelection()" data-index="-index-">Edit</button></div>', '').replace(/\-cssColor-/g, 'c-'+value.index+'-color').replace(/\-associationName-/g, "Personal").replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, value.index));
							  }else if($("#association_selected_"+(value.index-1)).length){
								  $('#items-row').children('#association_selected_'+(value.index-1)).after(htmlBlock.replace('<div class="col-6-sm"><button id="changeBtn-index-" class="changeButton btn btn-primary select-button btn" onclick="$(this).changeSelection()" data-index="-index-">Edit</button></div>', '').replace(/\-cssColor-/g, 'c-'+value.index+'-color').replace(/\-associationName-/g, "Personal").replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, value.index));
							  }else{
								  $('#items-row').children('#myBtn').before(htmlBlock.replace('<div class="col-6-sm"><button id="changeBtn-index-" class="changeButton btn btn-primary select-button btn" onclick="$(this).changeSelection()" data-index="-index-">Edit</button></div>', '').replace(/\-cssColor-/g, 'c-'+value.index+'-color').replace(/\-associationName-/g, "Personal").replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, value.index));
							  }
							  
							  var testDivs = [];
								$(".percent_span").each(function(index){
									//diffDivs.push(document.getElementById('test_'+index));
									testDivs.push(document.getElementById('spinner_'+index))
								})
								if(softSlider.noUiSlider != undefined){
									softSlider.noUiSlider.on('update', function( values, handle ) {
										// valuesDivs[handle].innerHTML = values[handle];
//												diffDivs[0].innerHTML = values[0] - 0;
//												diffDivs[1].innerHTML = values[1] - values[0];
//												diffDivs[2].innerHTML = values[2] - values[1];
//												diffDivs[3].innerHTML = values[3] - values[2];
//												diffDivs[4].innerHTML = 100 - values[3];
											
											for(var e = 0; e < testDivs.length; e++){
												var currentValue = (values[e] || 100);
												var previousValue = (values[(e-1)] || 0);
												//diffDivs[e].innerHTML = currentValue - previousValue;
												selectList[e].marker = currentValue - previousValue;
												if(testDivs[e] != null){
													testDivs[e].value = currentValue - previousValue;
												}
											}
									});
									updateSpinners();
								}
						break;
					}
					if(testIndex != (selectList.length - 1)){
						console.log("Calling loop: "+testIndex);
						if($.isArray(currentMarkers)){
							console.log("Is Array");
							if(currentMarkers[testIndex-1] != undefined){
								console.log("First Marker Found");
								startArray.push(parseInt(currentMarkers[testIndex-1]) + value.marker);
							}else{
								console.log("No Marker Found");
								startArray.push((parseInt(currentMarkers[testIndex-1]) + value.marker));
							}
						}else{
							console.log("Not Array");
							if(currentMarkers != undefined){
								console.log("Marker Found");
								startArray.push(parseInt(currentMarkers)+value.marker);
							}else{
								console.log("Marker not found");
								startArray.push(value.marker);
							}
						}
						connectArray.push(true);
					}
					console.log(startArray)
					updateSlider(startArray, connectArray);
					document.getElementById("loading-background").style.display = "none";
					if(selectList.length >= limitCount){
						//$("#myBtn").hide();
						$("#charitySelect").hide();
						$("#sportSelect").hide();
						$("#profitSelect").hide();
					}else{
						$("#charitySelect").show();
						$("#sportSelect").show();
						$("#profitSelect").show();
					}
				})
			}
		})
	});
	
	
//	$( window ).load(function() {
//		// Run code
//	});
	
	$("#community_form").submit(function( event ) {
	  event.preventDefault();
	  $("#warningModal").show();
	});
	
	$("#confirm_button").on("click", function(){
		console.log("Selected");
		$("#warningModal").hide();
		console.log(selectList);
		document.getElementById("loading-background").style.display = "block";
		if(selectList.length != 0){
			console.log(selectList);
			$("#association_select_alert").hide();
			$.each(selectList, function(selectIndex, select){
				var added = false;
				$(".association_list").each(function(index){
					//console.log("here")
//					console.log($(this).children("#user_association_id_"+index));
					//console.log($(this).children("#user_association_id_"+index).val()+" : "+select.selection.userAssociatioinid.value)
						if(select.selection.userAssociatioinid.value == $(this).children("#user_association_id_"+index).val() && $(this).children("#donation_dysplay_"+index).val() == 0.0){
							added = true;
							console.log($(this).children("#user_association_id_"+index).val()+" : "+select.selection.userAssociatioinid.value)
							console.log("updating old")
							console.log(select);
							switch(select.type){
							case "sport":
								$(this).children("#user_association_id_"+index).val(select.selection.userAssociatioinid.value);
								$(this).children("#donation_dysplay_"+index).val(select.marker/100);
								$(this).children("#association_id_"+index).val(select.selection.association_id.value);
								$(this).children("#team_id_"+index).val(select.selection.team_id.value);
								$(this).children("#player_id_"+index).val(select.selection.player_id.value);
			    				//Remove items from list
								$(this).children("#charity_id_"+index).val(0);
								$(this).children("#chairty_recipts_"+index).val(0);
								$(this).children("#nonprof_id_"+index).val(0);
								$(this).children("#personal_"+index).val(false);
							break;
							case "charity":
								$(this).children("#user_association_id_"+index).val(select.selection.userAssociatioinid.value);
								$(this).children("#donation_dysplay_"+index).val(select.marker/100);
								$(this).children("#charity_id_"+index).val(select.selection.association_id.value);
//								$(this).children("#chairty_recipts_"+index).val(select.selection.receipt);
			    				//removeing items from list
								$(this).children("#nonprof_id_"+index).val(0);
								$(this).children("#association_id_"+index).val(0);
								$(this).children("#team_id_"+index).val(0);
								$(this).children("#player_id_"+index).val(0);
								$(this).children("#personal_"+index).val(false);
							break;
							case "nonProf":
								$(this).children("#user_association_id_"+index).val(select.selection.userAssociatioinid.value);
								$(this).children("#donation_dysplay_"+index).val(select.marker/100);
								$(this).children("#nonprof_id_"+index).val(select.selection.association_id.value);
			    				//removeing items from list
								$(this).children("#association_id_"+index).val(0);
								$(this).children("#team_id_"+index).val(0);
								$(this).children("#player_id_"+index).val(0);
								$(this).children("#charity_id_"+index).val(0);
								$(this).children("#chairty_recipts_"+index).val(0);
								$(this).children("#personal_"+index).val(false);
							break;
							case "personal":
								$(this).children("#user_association_id_"+index).val(select.selection.userAssociatioinid.value);
								$(this).children("#personal_"+index).val(select.selection.personal);
								$(this).children("#donation_dysplay_"+index).val(select.marker/100);
								//removeing items from list
								$(this).children("#association_id_"+index).val(0);
								$(this).children("#team_id_"+index).val(0);
								$(this).children("#player_id_"+index).val(0);
								$(this).children("#charity_id_"+index).val(0);
								$(this).children("#chairty_recipts_"+index).val(0);
								$(this).children("#nonprof_id_"+index).val(0);
							break;
							}
							$(this).children("#active_"+index).val(true);
							return false;
						}else{
							if($(this).children("#user_association_id_"+index).val() == 0 && $(this).children("#donation_dysplay_"+index).val() == 0.0){
				    			console.log("adding new")
				    			console.log(select);
				    			switch(select.type){
								case "sport":
									$(this).children("#user_association_id_"+index).val(select.selection.userAssociatioinid.value);
									$(this).children("#donation_dysplay_"+index).val(select.marker/100);
									$(this).children("#association_id_"+index).val(select.selection.association_id.value);
									$(this).children("#team_id_"+index).val(select.selection.team_id.value);
									$(this).children("#player_id_"+index).val(select.selection.player_id.value);
				    				//Remove items from list
									$(this).children("#charity_id_"+index).val(0);
									$(this).children("#chairty_recipts_"+index).val(0);
				    				$(this).children("#nonprof_id_"+index).val(0);
								break;
								case "charity":
									$(this).children("#user_association_id_"+index).val(select.selection.userAssociatioinid.value);
									$(this).children("#donation_dysplay_"+index).val(select.marker/100);
									$(this).children("#charity_id_"+index).val(select.selection.association_id.value);
//									$(this).children("#chairty_recipts_"+index).val(select.selection.receipt);
				    				//removeing items from list
									$(this).children("#nonprof_id_"+index).val(0);
									$(this).children("#association_id_"+index).val(0);
									$(this).children("#team_id_"+index).val(0);
									$(this).children("#player_id_"+index).val(0);
								break;
								case "nonProf":
									$(this).children("#user_association_id_"+index).val(select.selection.userAssociatioinid.value);
									$(this).children("#donation_dysplay_"+index).val(select.marker/100);
									$(this).children("#nonprof_id_"+index).val(select.selection.association_id.value);
				    				//removeing items from list
									$(this).children("#association_id_"+index).val(0);
									$(this).children("#team_id_"+index).val(0);
									$(this).children("#player_id_"+index).val(0);
									$(this).children("#charity_id_"+index).val(0);
									$(this).children("#chairty_recipts_"+index).val(0);
								break;
								case "personal":
									$(this).children("#user_association_id_"+index).val(select.selection.userAssociatioinid.value);
									$(this).children("#personal_"+index).val(select.selection.personal);
									$(this).children("#donation_dysplay_"+index).val(select.marker/100);
									//removeing items from list
									$(this).children("#association_id_"+index).val(0);
									$(this).children("#team_id_"+index).val(0);
									$(this).children("#player_id_"+index).val(0);
									$(this).children("#charity_id_"+index).val(0);
									$(this).children("#chairty_recipts_"+index).val(0);
									$(this).children("#nonprof_id_"+index).val(0);
								break;
								}
				    			$(this).children("#active_"+index).val(true);
				    			return false;
				    		}
						}
						
	    		})
			})
    		//console.log($("#community_form").serialize())
    		$.ajax({
        	    url: "./updateCommunity",
        	    type: "POST",
        	    data: $("#community_form").serialize(),
        	    complete: function(data){
        	    	console.log("Complete")
        	    	console.log(data)
        	    	document.getElementById("loading-background").style.display = "none";
        	    	//window.location.replace(data.responseText);
        	    	//console.log("Done");
        	    },
        	    error: function (xhr, ajaxOptions, thrownError) {
        	    	//$("#regForm .actions a[href='#finish']").show();
        	    	document.getElementById("loading-background").style.display = "none";
        	        alert("Something went wrong");
        	      }
        	})
		}else{
			$("#association_select_alert").show();
		}
	})
	
	
	function implementSportSelect(testIndex){
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
	}
	
	function implementCharitySelect(testIndex){
		
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
		
		function formatRepo (repo) {
			  if (repo.loading) {
			    return repo.text;
			  }

			  var markup = "<div class='select2-result-repository clearfix'>" +
//			    "<div class='select2-result-repository__avatar'><img src='https://beebom-redkapmedia.netdna-ssl.com/wp-content/uploads/2016/01/Reverse-Image-Search-Engines-Apps-And-Its-Uses-2016.jpg' /></div>" +
			    "<div class='select2-result-repository__meta'>" +
			      "<div class='select2-result-repository__title'>" + repo.name +"</div>"+
			      "<div class='select2-result-repository__description'><i class='fa fa-address-card'></i> " + repo.community + ", " + repo.province_code + "</div>";

//			  if (repo.description) {
//			    markup += "<div class='select2-result-repository__description'><i class='fa fa-address-card'></i> " + repo. + "</div>";
//			  }

//			  markup += "<div class='select2-result-repository__statistics'>" +
//			    "<div class='select2-result-repository__forks'><i class='fa fa-phone'></i> " + repo.phone + "</div>" +
//			    "<div class='select2-result-repository__stargazers'><i class='fa fa-envelope'></i> " + repo.email + "</div>" +
//			    "<div class='select2-result-repository__watchers'><i class='fa fa-id-card'></i> " + repo.card_id + "</div>" +
//			  "</div>";
			  markup += "</div></div>";

			  return markup;
			}

			function formatRepoSelection (repo) {
				return repo.text || repo.name ;
			}
		
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
	
	function implementNonProfSelect(testIndex){
		
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
		
		function formatRepo (repo) {
			  if (repo.loading) {
			    return repo.text;
			  }

			  var markup = "<div class='select2-result-repository clearfix'>" +
//			    "<div class='select2-result-repository__avatar'><img src='https://beebom-redkapmedia.netdna-ssl.com/wp-content/uploads/2016/01/Reverse-Image-Search-Engines-Apps-And-Its-Uses-2016.jpg' /></div>" +
			    "<div class='select2-result-repository__meta'>" +
			      "<div class='select2-result-repository__title'>" + repo.name +"</div>"+
			      "<div class='select2-result-repository__description'><i class='fa fa-address-card'></i> " + repo.community + ", " + repo.province_code + "</div>";

//			  if (repo.description) {
//			    markup += "<div class='select2-result-repository__description'><i class='fa fa-address-card'></i> " + repo. + "</div>";
//			  }

//			  markup += "<div class='select2-result-repository__statistics'>" +
//			    "<div class='select2-result-repository__forks'><i class='fa fa-phone'></i> " + repo.phone + "</div>" +
//			    "<div class='select2-result-repository__stargazers'><i class='fa fa-envelope'></i> " + repo.email + "</div>" +
//			    "<div class='select2-result-repository__watchers'><i class='fa fa-id-card'></i> " + repo.card_id + "</div>" +
//			  "</div>";
			  markup += "</div></div>";

			  return markup;
			}

			function formatRepoSelection (repo) {
				return repo.text || repo.name ;
			}
		
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
	
	
	function updateListItems(index, selectedInput, type, marker){

		selectList.splice(index, 0,{
			index: index,
			type: type,
			color: 'c-'+index+'-color',
			marker: marker,
			selection: selectedInput
		});
		
		if(selectList.length >= limitCount){
			$("#charitySelect").hide();
			$("#sportSelect").hide();
			$("#profitSelect").hide();
			$("#personal_option").hide();
		}else{
			$("#charitySelect").show();
			$("#sportSelect").show();
			$("#profitSelect").show();
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
		case "personal":
			$('#items-row').children('#myBtn').before(htmlBlock.replace('<div class="col-6-sm"><button id="changeBtn-index-" class="changeButton btn btn-primary select-button btn" onclick="$(this).changeSelection()" data-index="-index-">Edit</button></div>', '').replace(/\-cssColor-/g, selectList[index].color).replace(/\-associationName-/g, "Personal").replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, index));
			break;
		}
		
		var personalCheck = false;
		
		$.each(selectList, function(itemIndex, item){
			console.log(item);
			if(item.type == "personal"){
				personalCheck = true;
			}
		})
		console.log(personalCheck);
		if(!personalCheck){
			console.log("show")
			$("#personal_option").show();
		}else{
			console.log("hide")
			$("#personal_option").hide();
		}
		
	}

	//$('.card_member').hide();
	
	$("#nonprof-alert").hide();
	$("#charity-alert").hide();
	$("#sport-alert").hide();
	$("#agreeError").hide();
	
	
//	$("#myBtn").on("click", function(){
//		//$("#myModal_"+(selectList.length)).show();
//		//implementSportSelect();
//		$("#selectionModal").show();
//	});
	
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
	
	$("input[name='playerRadio']").on("click", function(){
		if($(this).val() == 'true'){
			$("#player_selection").show();
		}else{
			$("#player_id").val("");
			$("#player_selection").hide();
		}
	})
	
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
	
		$("#sportSelectButton").on("click", function(event){
			event.preventDefault();
			var selectionMade = false;
			var startArray = [];
			var connectArray = [true];
			var currentMarkers;
			var selectedInput;
			console.log($("#association_select").val());
			if($("#association_select").val() != ''){
				selectionMade = true;
				selectedInput = {
						userAssociatioinid: {label: 0, value: 0},
						active: {lable: "true", value: true},
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
						userAssociatioinid: {label: 0, value: 0},
						active: {lable: "true", value: true},
						province_id: {label: data[0].province_code, value: data[0].province_code },
						community_id: {label: data[0].community, value: data[0].community},
						sport_id: {label: data[0].sport, value: data[0].sport},
						association_id: {label: data[0].name , value: data[0].association_id},
						division_id: {label: data[0].teams[0] != null ? data[0].teams[0].division : "", value: data[0].teams[0] != null ? data[0].teams[0].division : ""},
						gender_id: {label: data[0].teams[0] != null ? data[0].teams[0].gender : "", value: data[0].teams[0] != null ? data[0].teams[0].gender : ""},
						team_id: {label: data[0].teams[0] != null ? data[0].teams[0].name : "", value: data[0].teams[0] != null ? data[0].teams[0].team_id : 0},
						player_id: {label: (data[0].teams[0] != null ? data[0].teams[0].players[0] != null ? data[0].teams[0].players[0].name : "" : ""), value: (data[0].teams[0] != null ? data[0].teams[0].players[0] != null ? data[0].teams[0].players[0].player_id : 0 : 0)}
					}
			}
			
			if(selectionMade){
				if(softSlider.noUiSlider != undefined){
					currentMarkers = softSlider.noUiSlider.get();
				}
				if(checkInput(selectedInput, "sport", null)){
					$("#sport-alert").hide();
					updateListItems(selectList.length, selectedInput, "sport", 0);
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
	
	$("#charitySelectButton").on('click', function(event){
		event.preventDefault();
		console.log("Charity Select");
		var startArray = [];
		var selectionMade = false;
		var connectArray = [true];
		var currentMarkers;
		var selectedInput;
		
		if($("#name_c_select").val() != ''){
			selectionMade = true;
			selectedInput = {
					userAssociatioinid: {label: 0, value: 0},
					active: {lable: "true", value: true},
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
					userAssociatioinid: {label: 0, value: 0},
					active: {lable: "true", value: true},
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
			
			if(checkInput(selectedInput, "charity", null)){
				$("#charity-alert").hide();
				updateListItems(selectList.length, selectedInput, "charity", 0);
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
	
	$("#nonProfSelectButton").on('click', function(event){
		event.preventDefault();
		console.log("Charity Select");
		
		var startArray = [];
		var selectionMade = false;
		var connectArray = [true];
		var currentMarkers;
		var selectedInput;
		
		
		if($("#name_nonp_select").val() != ''){
			selectionMade = true;
			selectedInput = {
					userAssociatioinid: {label: 0, value: 0},
					active: {lable: "true", value: true},
					province_id: {label: ($("#province_nonp_select option:selected").text() != "Select" ? $("#province_nonp_select option:selected").text() : ""), value: $("#province_nonp_select").val() },
					community_id: {label: ($("#community_nonp_select option:selected").text() != "Select" ? $("#community_nonp_select option:selected").text() : ""), value: $("#community_nonp_select").val()},
					association_id: {label: ($("#name_nonp_select option:selected").text() != "Select" ? $("#name_nonp_select option:selected").text() : ""), value: $("#name_nonp_select").val()}
				}
		}else if($("#nonProf_query").select2("val") != ''){
			var data = $("#nonProf_query").select2('data');
			selectionMade = true;
			selectedInput = {
					userAssociatioinid: {label: 0, value: 0},
					active: {lable: "true", value: true},
					province_id: {label: data[0].province_code, value: data[0].province_code },
					community_id: {label:  (data[0].community), value: data[0].community},
					association_id: {label: data[0].name, value: data[0].association_id}
				}
		}
		
		if(selectionMade){
			
			if(softSlider.noUiSlider != undefined){
				currentMarkers = softSlider.noUiSlider.get();
			}
			
			if(checkInput(selectedInput, "nonProf", null)){
				$("#nonprof-alert").hide();
				updateListItems(selectList.length, selectedInput, "nonProf", 0);
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
					$("#nonProf_query").empty().trigger("change");
					$("#nonProfModal").hide();
					$('.content').removeClass('noscroll');
					$('body').removeClass('noscroll');
				}else{
					$("#nonprof-alert").show();
				}
		}
		
	})
	
	$("#personalOption").on('click', function(){
		event.preventDefault();
		var startArray = [];
		var selectionMade = false;
		var connectArray = [true];
		var currentMarkers;
		var selectedInput;
		var personalCheck = false;
		$.each(selectList, function(item){
			if(item.type == "personal"){
				personalCheck = true;
			}
		})
		if(!personalCheck){
			selectedInput = {
					userAssociatioinid: {label: 0, value: 0},
					personal: true
				}
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
					
					updateSlider(startArray, connectArray);
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
		$(".association_list").each(function(index){
			console.log("Testing remove");
			if($(this).attr("data-index") == testIndex){
				$('#active_'+index).val(false);
			}
		});
		var count = 0;
		$("#association_selected_"+testIndex).remove();
		selectList.splice(testIndex, 1);
		console.log(selectList.length);
		var personalCheck = false;
		$.each(selectList, function(newIndex, value){
			count++;
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
				case "personal":
					$('#items-row').children('#myBtn').before(htmlBlock.replace('<div class="col-6-sm"><button id="changeBtn-index-" class="changeButton btn btn-primary select-button btn" onclick="$(this).changeSelection()" data-index="-index-">Edit</button></div>', '').replace(/\-cssColor-/g, selectList[value.index].color).replace(/\-associationName-/g, "Personal").replace(/\-teamName-/g, '').replace(/\-playerName-/g, '').replace(/\-index-/g, value.index));
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
		}else{
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
		}
	}
	
	var tempIndexForUpdate;

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
	
	$(".update_button").on('click', function(){
		console.log(htmlBlock)
		var nextItem = parseInt(tempIndexForUpdate) + 1;
		switch(selectList[tempIndexForUpdate].type){
		case "sport":
			var updateSelection;
			var data = $("#sport_query").select2('data');
			console.log(data[0]);
			if(data[0] != undefined){
				updateSelection = {
						userAssociatioinid: {label: selectList[tempIndexForUpdate].selection.userAssociatioinid.label, value: selectList[tempIndexForUpdate].selection.userAssociatioinid.value},
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
				console.log(selectList[tempIndexForUpdate])
				updateSelection = {
						userAssociatioinid: {label: selectList[tempIndexForUpdate].selection.userAssociatioinid.label, value: selectList[tempIndexForUpdate].selection.userAssociatioinid.value},
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
			if(data[0] != undefined){
				updateSelection = {
						userAssociatioinid: {label: selectList[tempIndexForUpdate].selection.userAssociatioinid.label, value: selectList[tempIndexForUpdate].selection.userAssociatioinid.value},
						province_id: {label: data[0].province_code, value: data[0].province_code},
						community_id: {label:  (data[0].community), value: data[0].community},
						association_id: {label: data[0].name, value: data[0].association_id}
//						receipt: $("#receipt_selection").prop("checked")
					}
			}else{
				updateSelection = {
						userAssociatioinid: {label: selectList[tempIndexForUpdate].selection.userAssociatioinid.label, value: selectList[tempIndexForUpdate].selection.userAssociatioinid.value},
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
			if(data[0] != undefined){
				updateSelection = {
						userAssociatioinid: {label: selectList[tempIndexForUpdate].selection.userAssociatioinid.label, value: selectList[tempIndexForUpdate].selection.userAssociatioinid.value},
						province_id: {label: data[0].province_code, value: data[0].province_code},
						community_id: {label:  (data[0].community), value: data[0].community},
						association_id: {label: data[0].name, value: data[0].association_id}
					}
			}else{
				updateSelection = {
						userAssociatioinid: {label: selectList[tempIndexForUpdate].selection.userAssociatioinid.label, value: selectList[tempIndexForUpdate].selection.userAssociatioinid.value},
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
	
})(jQuery);