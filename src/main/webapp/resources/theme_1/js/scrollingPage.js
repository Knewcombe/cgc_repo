//This is where I will use any of the scrolling with anchor points.
$(document).ready(function(){
$('.list-group-item a').click(function(){ 
	 $('html, body').stop().animate({
	        scrollTop: $( $(this).attr('href') ).offset().top - 100
	}, 400); 
 });
});