<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link href="resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="resources/css/modern-business.css" rel="stylesheet">

<link
	href="https://fortawesome.github.io/Font-Awesome/assets/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Graduate"
	rel="stylesheet">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign in</title>
</head>
<body>
	<!-- Navigation -->
	<nav
		class="navbar fixed-top navbar-expand-lg navbar-light cl-bg-primary text-muted fixed-top">
	<div class="container">
		<a class="navbar-brand nav-titel cl-txt-primary" href="./">COMMUNITY
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
						<a class="dropdown-item" href="./how-it-works">How it
							works</a> <a class="dropdown-item" href="./benefits/family">Benefits
							for Members</a> <a class="dropdown-item" href="./benefits/businesses">Benefits
							for Merchants</a>
					</div></li>
					<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#"
					id="navbarDropdownPortfolio" data-toggle="dropdown"
					aria-haspopup="true" aria-expanded="false">
						Community Partners </a>
					<div class="dropdown-menu dropdown-menu-right"
						aria-labelledby="navbarDropdownPortfolio">
						<a class="dropdown-item" href="./communites/sport">Sport Community</a>
						<a class="dropdown-item" href="./communites/charity">Charity Community</a>
						<a class="dropdown-item" href="./communites/nonprof">Non-Profit Community</a>
					</div></li>
				<li class="nav-item"><a class="nav-link" href="./contact">Contact</a>
				</li>
				<!-- <li class="nav-item"><a href="#" class="btn btn-primary">Login</a></li> -->
				<li class="nav-item">
					<a class="btn btn-outline-primary" href="./login">Login</a>
				</li>
				<!-- <li class="nav-item"><a href="#" class="btn btn-success">Sign Up</a></li> -->
				<li class="nav-item">
					<a class="btn btn-outline-success" href="./register/select">Sign
						Up</a>
				</li>
			</ul>
		</div>
	</div>
	</nav>
	<div class="container">
		<div class="row" id="pwd-container">

			<div class="col-md-6 col-sm-12 col-centered">
				<section class="login-form">
				<form:form class="form-signin form-horizontal" role="login" id="loginForm"
				modelAttribute="login" action="./loginProcess" method="post">

					<div class="text-center">
						<p class="navbar-brand login-title cl-txt-primary">COMMUNITY
							GAME CHANGER</p>
					</div>
					<c:if test="${not empty message}">
						<div class="alert alert-danger" role="alert">${message}</div>
					</c:if>
					<form:label path="username" class="sr-only">Username</form:label>
						<form:input path="username" name="username" id="username"
							class="form-control" placeholder="Username" required="true"
							autofocus="true" />
						
						<form:label path="password" class="sr-only">Password:</form:label>
						<form:password path="password" name="password" id="password"
							class="form-control" placeholder="Password" required="true" />


					<button type="submit" name="go"
						class="btn btn-lg btn-primary btn-block">Sign in</button>
					<div>
						<a href="register/select">Create account</a> or <a href="#">reset password</a>
					</div>

				</form:form>

				<!-- <div class="form-links">
          <a href="#">www.website.com</a>
        </div> --> </section>
			</div>
		</div>
	</div>
	<!-- <footer>
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
					<li><a href="../benefits/family"> Benefits for Families</a></li>
					<li><a href="../benefits/businesses"> Benefits for Companies</a></li>
					<li><a href="../contact"> Contact</a></li>
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
	footer start from here
	<div class="copyright">
		<div class="container">
			<div class="col-md-6">
				<p>© 2017 - All Rights with Community Game Changer</p>
			</div>
		</div>
	</div> -->
	
</body>
<script src="resources/vendor/jquery/jquery.min.js"></script>
<script type="text/javascript"
	src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery.matchHeight/0.7.0/jquery.matchHeight-min.js"></script>
<script src="resources/vendor/popper/popper.min.js"></script>
<script src="resources/vendor/bootstrap/js/bootstrap.min.js"></script>
</html>