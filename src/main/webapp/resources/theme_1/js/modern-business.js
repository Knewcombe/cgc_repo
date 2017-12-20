$(document).ready(function(){
  $('.autoplay').slick({
	  slidesToShow: 3,
	  slidesToScroll: 1,
	  autoplay: true,
	  arrows: true,
	  autoplaySpeed: 10000,
	  responsive: [
		    {
		      breakpoint: 768,
		      settings: {
		        slidesToShow: 1,
		        slidesToScroll: 1,
		        autoplay: true,
			  	  arrows: true,
			  	  autoplaySpeed: 10000
		      }
		    }
		    ]
  });
  
  $(".ad-autoplay").slick({
	  slidesToShow: 1,
	  slidesToScroll: 1,
	  autoplay: true,
	  arrows: false,
	  autoplaySpeed: 10000
  })
  
  $(function() {
	    $('.box').matchHeight();
	});
});
