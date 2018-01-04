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

<title>Merchant Registration</title>
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
		<div class="row">
        <div class="col-lg-8 mb-4 col-centered">
          <form id="merchantForm" name="sentMessage" id="contactForm" class="form-signin form-horizontal" role="login" novalidate="">
          <!-- <h3>Fill in you information below</h3> -->
          	<div class="text-center">
          		<p class="navbar-brand login-title cl-txt-primary">Merchant Registration</p>
          	</div>
          	<div class="row">
          		<div class="control-group form-group col-lg-12">
              <label>Main Contact (Full Name):</label>
                <input type="text" class="form-control" id="name" name="name" required="">
            </div>
            <div class="control-group form-group col-lg-12">
              <label>Business Name:</label>
                <input type="text" class="form-control" id="businessName" name="businessName" required="" >
            </div>
            <div class="control-group form-group col-lg-6">
              <label>Phone Number:</label>
                <input type="tel" class="form-control" id="phone" name="phone" required="" >
            </div>
            <div class="control-group form-group col-lg-6">
              	<label>Email Address:</label>
                <input type="email" class="form-control" id="email" name="email" required="" >
            </div>
          	</div>
            <div id="success"></div>
            <!-- For success/fail messages -->
            <button type="submit" class="btn btn-primary" id="sendMessageButton">Send Message</button>
          </form>
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
	<script src="../resources/js/jquery.mask.js"></script>
	<script src="../resources/js/jquery.validate.min.js"></script>
	<script src="../resources/js/business_reg_form.js"></script>