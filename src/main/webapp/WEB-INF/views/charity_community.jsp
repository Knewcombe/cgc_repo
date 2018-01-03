<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
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
						<a class="dropdown-item" href="../how-it-works">How it
							works</a> <a class="dropdown-item" href="../benefits/family">Benefits
							for Members</a> <a class="dropdown-item" href="../benefits/businesses">Benefits
							for Merchants</a>
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
				<li class="nav-item">
					<a class="btn btn-outline-primary" href="../login">Login</a>
				</li>
				<!-- <li class="nav-item"><a href="#" class="btn btn-success">Sign Up</a></li> -->
				<li class="nav-item">
					<a class="btn btn-outline-success" href="../register/select">Sign
						Up</a>
				</li>
			</ul>
		</div>
	</div>
	</nav>
	<div class="container-fluid">
	<h1 class="my-4 text-center titel">Sport Community Partners</h1>
	<c:forEach items="${charites}" var="charity" varStatus="item">
		<div class="container py-3">
		    <div class="card">
		      <div class="row">
		      <div class="col-md-3">
            	<img src="http://via.placeholder.com/400x400" class="w-100">
          		</div>
		          <div class="col-md-9 px-1 py-3">
		            <div class="card-block px-3">
		              <h4 class="card-title">Name: ${charity.name}</h4>
		              <p class="card-text">Community: ${charity.community} ${charity.province_code}</p>
		            </div>
		          </div>
		        </div>
		      </div>
		    </div>
	</c:forEach>
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
					<li><a href="../how-it-works"> How it works</a></li>
					<li><a href="../benefits/family"> Benefits for Members</a></li>
					<li><a href="../benefits/businesses"> Benefits for Merchants</a></li>
					<li><a href="../contact"> Contact</a></li>
					<li><a href="../communites/sport"> Sport Community</a></li>
					<li><a href="../communites/charity"> Charity Community</a></li>
					<li><a href="../communites/nonprof"> Non-Profit Community</a></li>
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