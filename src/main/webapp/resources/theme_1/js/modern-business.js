
//$('.autoplay').slick({
//	  slidesToShow: 1,
//	  slidesToScroll: 1,
//	  autoplay: true,
//	  arrows: false,
//	  autoplaySpeed: 100000
////	  responsive: [
////		    {
////		      breakpoint: 768,
////		      settings: {
////		        slidesToShow: 1,
////		        slidesToScroll: 1,
////		        autoplay: true,
////			  	  arrows: true,
////			  	  autoplaySpeed: 10000
////		      }
////		    }
////		    ]
//  });

$(".ad-autoplay").slick({
	  slidesToShow: 1,
	  slidesToScroll: 1,
	  autoplay: true,
	  arrows: false,
	  autoplaySpeed: 100000
})

$(document).ready(function(){
  
  $(function() {
	    $('.box').matchHeight();
	    $(".jumbotron").matchHeight();
	});
});
