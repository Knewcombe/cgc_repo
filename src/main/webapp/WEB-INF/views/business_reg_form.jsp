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

<title>Registration Merchant</title>
</head>
<body>
	<!-- Navigation -->
	<nav
		class="navbar fixed-top navbar-expand-lg navbar-light cl-bg-primary text-muted fixed-top">
		<div class="container">
			<a class="navbar-brand nav-titel cl-txt-primary" href="../">COMMUNITY
				GAME CHANGER</a>
			<button class="navbar-toggler navbar-toggler-right" type="button"
				data-toggle="collapse" data-target="#navbarResponsive"
				aria-controls="navbarResponsive" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#"
						id="navbarDropdownPortfolio" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false"> About </a>
						<div class="dropdown-menu dropdown-menu-right"
							aria-labelledby="navbarDropdownPortfolio">
							<a class="dropdown-item" href="../how-it-works">How it works</a>
							<a class="dropdown-item" href="../benefits/family">Benefits
								for Families</a> <a class="dropdown-item"
								href="../benefits/businesses">Benefits for Companies</a>
						</div></li>
					<!-- <li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#"
						id="navbarDropdownPortfolio" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false"> For Partners </a>
						<div class="dropdown-menu dropdown-menu-right"
							aria-labelledby="navbarDropdownPortfolio">
							<a class="dropdown-item" href="../business/register">Register</a>
						</div></li> -->
					<li class="nav-item"><a class="nav-link" href="../contact">Contact</a>
					</li>
					<!-- <li class="nav-item"><a href="#" class="btn btn-primary">Login</a></li> -->
					<li class="nav-item"><a class="btn btn-outline-primary"
						href="../login">Login</a></li>
					<!-- <li class="nav-item"><a href="#" class="btn btn-success">Sign Up</a></li> -->
					<!-- <li class="nav-item"><a class="btn btn-outline-success"
						href="../user/register">Sign Up</a></li> -->
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
	<footer>
	<div class="container">
		<div class="row">
			<div class="col-md-5 col-sm-6 footerleft ">
				<div class="logofooter cl-txt-secondary">Logo</div>
				<p>Community Game Changer, CGC, has a mandate to improve local
					communities through cooperation, collaboration, and communication.</p>
				<p>
					<i class="fa fa-map-pin cl-txt-secondary"></i>94 Watts ave,
					Charlottetown PEI - Canada
				</p>
				<p>
					<i class="fa fa-phone cl-txt-secondary"></i> Phone (Canada) : +1
					902 123 1234
				</p>
				<p>
					<i class="fa fa-envelope cl-txt-secondary"></i> E-mail :
					info@cgc.com
				</p>

			</div>
			<div class="col-md-3 col-sm-6 paddingtop-bottom">
				<h6 class="heading7 cl-txt-secondary">GENERAL LINKS</h6>
				<ul class="footer-ul">
					<li><a href="./how-it-works"> How it works</a></li>
					<li><a href="./benefits/family"> Benefits for Families</a></li>
					<li><a href="./benefits/businesses"> Benefits for Companies</a></li>
					<li><a href="./contact"> Contact</a></li>
				</ul>
			</div>
			<div class="col-md-4 col-sm-6 paddingtop-bottom">
				<hr>
				<div class="text-center center-block">
					<p class="txt-railway">Social Media</p>
					<br /> <a href="https://www.facebook.com/bootsnipp"><i
						id="social-fb" class="fa fa-facebook-square fa-3x social"></i></a> <a
						href="https://twitter.com/bootsnipp"><i id="social-tw"
						class="fa fa-twitter-square fa-3x social"></i></a> <a
						href="https://plus.google.com/+Bootsnipp-page"><i
						id="social-gp" class="fa fa-google-plus-square fa-3x social"></i></a>
					<a href="mailto:bootsnipp@gmail.com"><i id="social-em"
						class="fa fa-envelope-square fa-3x social"></i></a>
				</div>
				<hr>
			</div>
		</div>
	</div>
	</footer>
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