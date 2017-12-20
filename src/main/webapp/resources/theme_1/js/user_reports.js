//<h6 class="card-title md-1">${team.name}</h6>
//<p class="card-text small">$ ${df2.format(total * userAssociation.donation_amount)}</p>
(function($) {
	$(".userAssociation").each(function(){
		console.log("Working");
		var stringValue = "";
		var element = $(this);
		if($(this).attr('data-association') != 0){
			$.ajax({
				  dataType: "json",
				  url: 'getSportNames',
				  data: {association_id: $(this).attr('data-association'), team_id: $(this).attr('data-team'), player_id: $(this).attr('data-player')},
				  success: function(data){
					  console.log(data);
					  stringValue += '<h6 class="card-title md-1">'+data.name+'</h6>';
					  if(data.teams.length != 0){
						  console.log(data.teams.length);
						  stringValue += '<p class="card-text">'+data.teams[0].name;
						  if(data.teams[0].players.length != 0){
							  console.log(data.teams[0].players.length);
							  stringValue += " - "+data.teams[0].players[0].name+'</p>';
						  }else{
							  stringValue += '</p>';
						  }
					  }
					  element.children(".card").children(".card-body").prepend(stringValue);
					  $('.box').matchHeight();
				  }
			});
		}else if($(this).attr('data-charity') != 0){
			$.ajax({
				  dataType: "json",
				  url: 'getChairtyName',
				  data: {charity_id: $(this).attr('data-charity')},
				  success: function(data){
					  console.log(data);
					  stringValue += '<h6 class="card-title md-1">'+data.name+'</h6>';
					  element.children(".card").children(".card-body").prepend(stringValue);
					  $('.box').matchHeight();
				  }
			});
		}else if($(this).attr('data-nonProf') != 0){
			$.ajax({
				  dataType: "json",
				  url: 'getNonProfName',
				  data: {nonProf_id: $(this).attr('data-nonProf')},
				  success: function(data){
					  console.log(data);
					  stringValue += '<h6 class="card-title md-1">'+data.name+'</h6>';
					  element.children(".card").children(".card-body").prepend(stringValue);
					  $('.box').matchHeight();
				  }
			});
		}
	})
	
})(jQuery);