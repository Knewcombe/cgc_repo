<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="../resources/css/modern-business.css" rel="stylesheet">
<link href="../resources/css/jquery.steps.css" rel="stylesheet">
<!-- <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"> -->
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.10.4/themes/flick/jquery-ui.css">
<link rel="stylesheet" href="../resources/css/jquery-ui-slider-pips.css">
<link rel="stylesheet" href="../resources/css/nouislider.min.css" />
<link rel="stylesheet"
	href="http://formvalidation.io/vendor/jquery.steps/css/jquery.steps.css" />
<link href="../resources/css/register.css" rel="stylesheet">

<link
	href="https://fortawesome.github.io/Font-Awesome/assets/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Graduate"
	rel="stylesheet">

<title>Registration</title>
</head>
<body>
	<!-- Navigation -->
	<nav
		class="navbar fixed-top navbar-expand-lg navbar-light cl-bg-primary text-muted fixed-top">
	<div class="container-fluid">
		<a class="navbar-brand nav-titel cl-txt-primary" href="../">COMMUNITY
			GAME CHANGER</a>
		<button class="navbar-toggler navbar-toggler-right" type="button"
			data-toggle="collapse" data-target="#navbarResponsive"
			aria-controls="navbarResponsive" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarResponsive">
			<ul class="nav navbar-nav">
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#"
					id="navbarDropdownPortfolio" data-toggle="dropdown"
					aria-haspopup="true" aria-expanded="false"> About </a>
					<div class="dropdown-menu dropdown-menu-right"
						aria-labelledby="navbarDropdownPortfolio">
						<a class="dropdown-item" href="../how-it-works">What we were thinking...</a> <a class="dropdown-item" href="../benefits/family">Benefits
							for Members</a> <a class="dropdown-item" href="../benefits/businesses">Benefits
							for Merchants</a>
							<a class="dropdown-item" href="../benefits/community">Benefits
							for Community Partners</a>
					</div></li>
					<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#"
					id="navbarDropdownPortfolio" data-toggle="dropdown"
					aria-haspopup="true" aria-expanded="false">
						Community Partners </a>
					<div class="dropdown-menu dropdown-menu-right"
						aria-labelledby="navbarDropdownPortfolio">
						<a class="dropdown-item" href="../communites/sport">Sport Community</a>
						<a class="dropdown-item" href="../communites/charity">Charity Community</a>
						<a class="dropdown-item" href="../communites/nonprof">Non-Profit Community</a>
					</div></li>
				<li class="nav-item"><a class="nav-link" href="../contact">Contact</a>
				</li>
				<!-- <li class="nav-item"><a href="#" class="btn btn-primary">Login</a></li> -->
				
			</ul>
			<ul class="nav navbar-nav ml-auto btn-group">
					<li class="nav-item btn-group">
						<a class="btn btn-primary" href="../login">Login</a>
					</li>
					<!-- <li class="nav-item"><a href="#" class="btn btn-success">Sign Up</a></li> -->
			</ul>
			<ul class="av navbar-nav">
				<li class="nav-item"><a href="https://twitter.com/bootsnipp" target="_blank"><i class="fa fa-twitter"></i></a></li>
        		<li class="nav-item"><a href="https://www.facebook.com/bootsnipp" target="_blank"><i class="fa fa-facebook"></i></a></li>
        		<li class="nav-item"><a href="https://plus.google.com/+Bootsnipp-page" target="_blank"><i class="fa fa-google-plus"></i></a></li>
        	</ul>
		</div>
	</div>
	</nav>
	<div class="container-fluid">
	<h1 class="my-4 text-center titel">Select what you would like to register as...</h1>
		<div class="container py-3">
		    <div class="card">
		      <div class="row">
		      <div class="col-md-4">
            <img src="https://daisiesandbuttercups.com/wp-content/uploads/2017/06/AdobeStock_28477565-400x400.jpeg" class="w-100">
          		</div>
		          <div class="col-md-8 px-3 py-3">
		            <div class="card-block px-3">
		              <h4 class="card-title">Family Member</h4>
		              <p class="card-text">Registering as a Family member will allow you to select the any Community partners you would like to support within your community.</p>
		              <p class="card-text">Shopping at any of our Merchant partner will allow you to contribute to the Community partners you have selected.</p>
		              <a href="../user/register" class="btn btn-primary">Register As Family Member!</a>
		            </div>
		          </div>
		        </div>
		      </div>
		    </div>
		    <div class="container py-3">
		    <div class="card">
		      <div class="row">
		      <div class="col-md-4">
            <img src="https://blog.unfranchise.com/wp-content/uploads/2017/03/bigstock-Black-male-business-owner-stan-154797611-800x445-400x400.jpg" class="w-100">
          </div>
		          <div class="col-md-8 px-3 py-3">
		            <div class="card-block px-3">
		              <h4 class="card-title">Merchant member</h4>
		              <p class="card-text">Registering as a Merchant partner will allow you to take part in helping the community your business is located.</p>
		              <p class="card-text">Any Family member will be able to shop at you Business and contribute to their selected Community partners.</p>
		              <a href="../business/register" class="btn btn-primary">Register as Merchant Member!</a>
		            </div>
		          </div>
		        </div>
		      </div>
		    </div>
		    <div class="container py-3">
		    <div class="card">
		      <div class="row">
		      <div class="col-md-4">
            <img src="https://thumbs.dreamstime.com/z/charity-donations-fundraising-nonprofit-volunteer-concept-77965178.jpg" class="w-100">
          </div>
		          <div class="col-md-8 px-3 py-3">
		            <div class="card-block px-3">
		              <h4 class="card-title">Community member</h4>
		              <p class="card-text">Registering as a Community member will allow Family members to selected your association for contribution</p>
		              <p class="card-text">As long as you are a Non-Profit, Charity or Sport association you can register.</p>
		              <a href="../community/register" class="btn btn-primary">Register Community Member!</a>
		            </div>
		          </div>
		        </div>
		      </div>
		    </div>
		  </div>
	<!--footer start from here-->
	<div class="copyright">
		<div class="container">
			<div class="col-md-6">
				<p>© 2017 - All Rights with Community Game Changer</p>
			</div>
		</div>
	</div>
</body>
<script src="../resources/vendor/jquery/jquery.min.js"></script>
<script type="text/javascript"
	src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
	<script src="../resources/vendor/popper/popper.min.js"></script>
	<script src="../resources/vendor/bootstrap/js/bootstrap.min.js"></script>