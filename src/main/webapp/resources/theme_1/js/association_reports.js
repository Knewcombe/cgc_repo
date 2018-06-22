(function($) {
	$(document).ready(function() {
		
//		$(".userName").each(function(){
//			var element = $(this);
//			console.log("test");
//			console.log(element.attr("data-id"));
//			if(element.attr("data-id") != 0){
//				$.ajax({
//	        	    url: './getUserName',
//	        	    type: "GET",
//	        	    data: {user_profile_id: element.attr("data-id")},
//	        	    complete: function(data){
//	        	    	console.log(data);
//	        	    	document.getElementById(element.attr("id")).innerHTML = data.responseJSON.first_name +" "+ data.responseJSON.last_name;
//	        	    },
//	        	    error: function (xhr, ajaxOptions, thrownError) {
//	        	    	$("#regForm .actions a[href='#finish']").show();
//	        	        alert("Something went wrong")
//	        	    }
//	        	})
//			}else{
//				document.getElementById(element.attr("id")).innerHTML = "N/A";
//			}
//		})
		
		$(".teamName").each(function(){
			var element = $(this);
			if(element.attr("data-id") != 0){
				$.ajax({
	        	    url: './getTeamName',
	        	    type: "GET",
	        	    data: {team_id: element.attr("data-id")},
	        	    complete: function(data){
	        	    	console.log(data);
	        	    	document.getElementById(element.attr("id")).innerHTML = "<a href=\"./reports/team?team_id="+element.attr("data-id")+"\">"+data.responseJSON.name+"</a>";
	        	    },
	        	    error: function (xhr, ajaxOptions, thrownError) {
	        	    	$("#regForm .actions a[href='#finish']").show();
	        	        alert("Something went wrong")
	        	      }
	        	})
			}else{
				document.getElementById(element.attr("id")).innerHTML = "N/A";
			}
		})
		
		$(".playerName").each(function(){
			var element = $(this);
			console.log("test");
			if(element.attr("data-id") != 0){
				$.ajax({
	        	    url: './getPlayerName',
	        	    type: "GET",
	        	    data: {player_id: element.attr("data-id")},
	        	    complete: function(data){
	        	    	console.log(data);
	        	    	document.getElementById(element.attr("id")).innerHTML = "<a href=\"./reports/player?player_id="+element.attr("data-id")+"\">"+data.responseJSON.name+"</a>";
	        	    },
	        	    error: function (xhr, ajaxOptions, thrownError) {
	        	    	$("#regForm .actions a[href='#finish']").show();
	        	        alert("Something went wrong")
	        	      }
	        	})
			}else{
				document.getElementById(element.attr("id")).innerHTML = "N/A";
			}
		})
		
		var index = parseInt($("#report_search").attr("data-index"));
		
		if($("#report_search").length){
			$('#select_team_player').prop('disabled', true);
			$("#report_search").cascadingDropdown({
				selectBoxes: [
					{
						selector: '.division',
						source: function(request, response){
							$.ajax({
								  dataType: "json",
								  url: '../teams/getDivision',
								  data: {association_id: index},
								  success: function(data){
									  response($.map(data, function(item, index){
											return{
												label: item.division,
												value: item.division
												
											}
										}))
								  }
							});
						}
					},
					{
						selector: '.gender',
						requires: ['.division'],
						source: function(request, response){
							$.ajax({
								  dataType: "json",
								  url: '../teams/getGender',
								  data: {association_id: index, division: $('#division_select').val()},
								  success: function(data){
									  console.log(data)
									  response($.map(data, function(item, index){
											return{
												label: item.gender,
												value: item.gender
												
											}
										}))
								  }
							});
						}
					},
					{
						selector: '.team',
						requires: ['.gender'],
						source: function(request, response){
							$.ajax({
								  dataType: "json",
								  url: '../teams/getTeamName',
								  data: {association_id: index, division: $('#division_select').val(), gender: $('#gender_select').val()},
								  success: function(data){
									  console.log(data)
									  response($.map(data, function(item, index){
											return{
												label: item.name,
												value: item.team_id
												
											}
										}))
								  }
							});
						}
					},
					{
						selector: '.player',
						requires: ['.team'],
						source: function(request, response){
							$.ajax({
								  dataType: "json",
								  url: '../teams/getPlayers',
								  data: {team_id: $('#team_select').val()},
								  success: function(data){
									  response($.map(data, function(item, index){
											return{
												label: item.name,
												value: item.player_id
												
											}
										}))
										$('#select_team_player').prop('disabled', false);
								  }
							});
						}
					}
				]
			})
		}
	});
	
	$('#select_team_player').on("click", function(event){
		if($("#player_select").val() != ""){
			window.location.href = "./reports/player?player_id="+$("#player_select").val();
		}else{
			window.location.href = "./reports/team?team_id="+$("#team_select").val();
		}
	})
})(jQuery);