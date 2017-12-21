(function($) {
	$(document).ready(function() {
		$('#select_team_player').prop('disabled', true);
		$(".userName").each(function(){
			var element = $(this);
			console.log("test");
			console.log(element.attr("data-id"));
			if(element.attr("data-id") != 0){
				$.ajax({
	        	    url: '../getUserName',
	        	    type: "GET",
	        	    data: {user_profile_id: element.attr("data-id")},
	        	    complete: function(data){
	        	    	console.log(data);
	        	    	document.getElementById(element.attr("id")).innerHTML = data.responseJSON.first_name +" "+ data.responseJSON.last_name;
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
		
		$(".teamName").each(function(){
			var element = $(this);
			if(element.attr("data-id") != 0){
				$.ajax({
	        	    url: '../getTeamName',
	        	    type: "GET",
	        	    data: {team_id: element.attr("data-id")},
	        	    complete: function(data){
	        	    	console.log(data);
	        	    	document.getElementById(element.attr("id")).innerHTML = data.responseJSON.name;
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
	        	    url: '../getPlayerName',
	        	    type: "GET",
	        	    data: {player_id: element.attr("data-id")},
	        	    complete: function(data){
	        	    	console.log(data);
	        	    	document.getElementById(element.attr("id")).innerHTML = data.responseJSON.name;
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
		var team_id = parseInt($("#report_search").attr("data-team"));
		
		
		$("#report_search").cascadingDropdown({
			selectBoxes: [
				{
					selector: '.player',
					source: function(request, response){
						$.ajax({
							  dataType: "json",
							  url: '../../teams/getPlayers',
							  data: {team_id: team_id},
							  success: function(data){
								  response($.map(data, function(item, index){
										return{
											label: item.name,
											value: item.player_id
											
										}
									}))
							  }
						});
					}
				}
			]
		})
	});
	
	$("#player_select").on("change", function(){
		if($("#player_select").val() != ""){
			$('#select_team_player').prop('disabled', false);
		}else{
			$('#select_team_player').prop('disabled', true);
		}
	})
	
	$('#select_team_player').on("click", function(event){
		window.location.href = "../reports/player?player_id="+$("#player_select").val();
	})
})(jQuery);