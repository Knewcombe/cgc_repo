<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="../resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="../resources/css/modern-business.css" rel="stylesheet">
<link href="../resources/css/sb-admin.min.css" rel="stylesheet">

<link href="../resources/css/slick-theme.css" rel="stylesheet">
<link href="../resources/css/slick.css" rel="stylesheet">

<link
	href="https://fortawesome.github.io/Font-Awesome/assets/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Graduate"
	rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome Partner</title>
</head>
<body>
	<nav
		class="navbar navbar-expand-lg navbar-dark cl-bg-primary fixed-top"
		id="mainNav"> <a class="navbar-brand nav-titel cl-txt-primary"
		href="./home">COMMUNITY GAME CHANGER</a>
	<button class="navbar-toggler navbar-toggler-right" type="button"
		data-toggle="collapse" data-target="#navbarResponsive"
		aria-controls="navbarResponsive" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarResponsive">
		<ul class="navbar-nav navbar-sidenav" id="exampleAccordion">
			<li class="nav-item" data-toggle="tooltip" data-placement="right"
				title="" data-original-title="Home"><a class="nav-link"
				href="./home"> <i class="fa fa-fw fa-home"></i> <span
					class="nav-link-text">Home</span>
			</a></li>
			<li class="nav-item" data-toggle="tooltip" data-placement="right"
				title="" data-original-title="Reports"><a class="nav-link"
				href="./reports"> <i class="fa fa-fw fa-area-chart"></i> <span
					class="nav-link-text">Reports</span>
			</a></li>
			<li class="nav-item" data-toggle="tooltip" data-placement="right"
				title="" data-original-title="Transaction"><a class="nav-link"
				href="../transaction"> <i class="fa fa-fw fa-credit-card-alt"></i>
					<span class="nav-link-text">Make Transaction</span>
			</a></li>
			<!-- <li class="nav-item" data-toggle="tooltip" data-placement="right"
				title="" data-original-title="Search for User"><a
				class="nav-link" href="../search/user"> <i
					class="fa fa-fw fa-search"></i> <span class="nav-link-text">Search
						for User</span>
			</a></li> -->
		</ul>
		<ul class="navbar-nav sidenav-toggler">
			<li class="nav-item"><a class="nav-link text-center"
				id="sidenavToggler"> <i class="fa fa-fw fa-angle-left"></i>
			</a></li>
		</ul>
		<ul class="navbar-nav ml-auto">
			<li class="nav-item"><a class="nav-link" href="./logout"> <i
					class="fa fa-fw fa-sign-out"></i>Logout
			</a></li>
		</ul>
	</div>
	</nav>
	<div class="content-wrapper">
		<div class="container-fluid">
			<header>
			<div id="carouselExampleIndicators" class="carousel slide"
				data-ride="carousel">
				<ol class="carousel-indicators">
					<li data-target="#carouselExampleIndicators" data-slide-to="0"
						class="active"></li>
					<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
					<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
					<li data-target="#carouselExampleIndicators" data-slide-to="3"></li>
					<li data-target="#carouselExampleIndicators" data-slide-to="4"></li>
				</ol>
				<div class="carousel-inner" role="listbox">
					<!-- Slide One - Set the background image for this slide in the line below -->
					<div class="carousel-item active"
						style="background-image: url('../resources/images/cgc_logo_image.png')">
						<div class="carousel-caption d-none d-md-block">
							<h1 class="my-4 titel">Welcome</h1>
							<p class="text-center titel">${name}</p>
						</div>
					</div>
					<!-- Slide Two - Set the background image for this slide in the line below -->
					<div class="carousel-item"
						style="background-image: url('https://i.ytimg.com/vi/GXASPAy7S9A/maxresdefault.jpg')">
						<div class="bg-overlay">
							<div class="carousel-caption d-none d-md-block">
								<!-- <h3 class="titel">Second Slide</h3>
						<p>This is a description for the second slide.</p> -->
							</div>
						</div>
					</div>
					<!-- Slide Three - Set the background image for this slide in the line below -->
					<div class="carousel-item"
						style="background-image: url('https://static.pexels.com/photos/114296/pexels-photo-114296.jpeg')">
						<div class="carousel-caption d-none d-md-block">
							<!-- <h3 class="titel">Third Slide</h3>
					<p>This is a description for the third slide.</p> -->
						</div>
					</div>
					<div class="carousel-item"
						style="background-image: url('https://35b7f1d7d0790b02114c-1b8897185d70b198c119e1d2b7efd8a2.ssl.cf1.rackcdn.com/website_files/64715/original/_DSC6670-X3.jpg?1476481977')">
						<div class="carousel-caption d-none d-md-block">
							<!-- <h3 class="titel">Third Slide</h3>
					<p>This is a description for the third slide.</p> -->
						</div>
					</div>
					<div class="carousel-item"
						style="background-image: url('http://richmondoval.ca/ovalhp/wp-content/uploads/2016/03/squash-1024x681-1024x681.jpg')">
						<div class="carousel-caption d-none d-md-block">
							<!-- <h3 class="titel">Third Slide</h3>
					<p>This is a description for the third slide.</p> -->
						</div>
					</div>
				</div>
				<a class="carousel-control-prev" href="#carouselExampleIndicators"
					role="button" data-slide="prev"> <span
					class="carousel-control-prev-icon" aria-hidden="true"></span> <span
					class="sr-only">Previous</span>
				</a> <a class="carousel-control-next" href="#carouselExampleIndicators"
					role="button" data-slide="next"> <span
					class="carousel-control-next-icon" aria-hidden="true"></span> <span
					class="sr-only">Next</span>
				</a>
			</div>
			</header>
			<div class="container">
				<h1 class="my-4 text-center titel">Welcome to Community Game
					Changer</h1>
				<div class="row">
					<div class="col-lg-4 mb-4">
						<div class="card h-100">
							<h4 class="card-header">How it works</h4>
							<div class="card-body">
								<p class="card-text">Community Game Changer, CGC, has a
									mandate to improve local communities through cooperation,
									collaboration, and communication.</p>
							</div>
							<div class="card-footer">
								<a href="./how-it-works" class="btn btn-primary">Learn More</a>
							</div>
						</div>
					</div>
					<div class="col-lg-4 mb-4">
						<div class="card h-100">
							<h4 class="card-header">Benefits to Families</h4>
							<div class="card-body">
								<p class="card-text">Individuals, families, sport teams,
									local associations and others can sign up for Community Game
									Changer. It’s easy and it’s profitable – for you and your
									community.</p>
							</div>
							<div class="card-footer">
								<a href="./benefits/family" class="btn btn-primary">Learn
									More</a>
							</div>
						</div>
					</div>
					<div class="col-lg-4 mb-4">
						<div class="card h-100">
							<h4 class="card-header">Benefits to Businesses</h4>
							<div class="card-body">
								<p class="card-text">Community Game Changer is designed to
									see business organizations, local associations, communities,
									and individuals improve their collective bottom line by
									purchasing goods and services from each other - to their mutual
									benefit.</p>
							</div>
							<div class="card-footer">
								<a href="./benefits/businesses" class="btn btn-primary">Learn
									More</a>
							</div>
						</div>
					</div>
				</div>
				<h1 class="my-4 text-center titel">Our Sports</h1>
				<div class="autoplay">
					<div class="mb-4 box">
						<div class="card h-100 text-center">
							<img class="card-img-top"
								src="http://www.theguardian.pe.ca/content/dam/tc/the-guardian/images/2015/5/22/baseball-p-e-i-logo-2874475.jpg.imgtransform/ELRL/image.jpg"
								alt="">
							<div class="card-body">
								<h4 class="card-title">Baseball PEI</h4>
								<h6 class="card-subtitle mb-2 text-muted">40 Enman Crescent
									- Charlottetown, PE</h6>
								<p class="card-text">For the second time in five years, Team
									Atlantic (Maroon) won Tournament 12, and four Islanders were on
									the squad (Noah Duckworth, Ethan Francis, Ben MacDougall and
									Gabriel Penalver).</p>
							</div>
							<div class="card-footer">
								<a href="http://basketballpei.ca/">Click here to see more</a>
							</div>
						</div>
					</div>
					<div class="mb-4 box">
						<div class="card h-100 text-center">
							<img class="card-img-top"
								src="https://upload.wikimedia.org/wikipedia/en/thumb/6/66/Hockey_PEI.svg/1200px-Hockey_PEI.svg.png"
								alt="">
							<div class="card-body">
								<h4 class="card-title">Hockey PEI</h4>
								<h6 class="card-subtitle mb-2 text-muted">40 Enman Crescent
									- Suite 209 - Charlottetown,PE</h6>
								<p class="card-text">Formed in 1975, Hockey PEI operates
									under the democratic council system whereby all members have a
									voice in the operation of the provincial body through their
									respective councils.</p>
							</div>
							<div class="card-footer">
								<a href="http://hockeypei.com/">Click here to see more</a>
							</div>
						</div>
					</div>
					<div class="mb-4 box">
						<div class="card h-100 text-center">
							<img class="card-img-top"
								src="http://site2653.goalline.ca/news_images/org_2653/Image/volleyball_logo_light_bg2.jpg"
								alt="">
							<div class="card-body">
								<h4 class="card-title">Volleyball PEI</h4>
								<h6 class="card-subtitle mb-2 text-muted">40 Enman Crescent
									- Charlottetown, PE</h6>
								<p class="card-text">The professional staff, in consultation
									with the Volleyball PEI Board of Directors, conducts the
									affairs of the organization and deliver its programs and
									services to the membership and the volleyball community.</p>
							</div>
							<div class="card-footer">
								<a href="http://site2653.goalline.ca/">Click here to see
									more</a>
							</div>
						</div>
					</div>
					<div class="mb-4 box">
						<div class="card h-100 text-center">
							<img class="card-img-top"
								src="http://peisoccer.com/news_images/org_841/Image/pei_soccer_assoc_logos_w4.jpg"
								alt="">
							<div class="card-body">
								<h4 class="card-title">Soccer PEI</h4>
								<h6 class="card-subtitle mb-2 text-muted">40 Enman Crescent
									Charlottetown, PE</h6>
								<p class="card-text">The Prince Edward Island Soccer
									Association(PEISA) is the Provincial Sport Governing body for
									soccer on PEI and an affiliated member of the Candian Soccer
									Association. The PEISA has over 6000 members that include
									players, coaches, referees and volunteers.</p>
							</div>
							<div class="card-footer">
								<a href="http://peisoccer.com/">Click here to see more</a>
							</div>
						</div>
					</div>
					<div class="mb-4 box">
						<div class="card h-100 text-center">
							<img class="card-img-top"
								src="http://ringettepei.ca/news_images/org_1250/Image/ringettepei_logo3.jpg"
								alt="">
							<div class="card-body">
								<h4 class="card-title">Ringette PEI</h4>
								<h6 class="card-subtitle mb-2 text-muted">40 Enman Crescent
									Charlottetown, PE</h6>
								<p class="card-text">This is an hour long introduction to
									the game of Ringette. Anyone who is interested in playing but
									wants to try it out first can attend one of these free events
									to get a feel for the game. Come out and join your local club
									for an hour of fun skating and try this exciting winter sport
									for FREE.</p>
							</div>
							<div class="card-footer">
								<a href="http://ringettepei.ca/">Click here to see more</a>
							</div>
						</div>
					</div>
					<div class="mb-4 box">
						<div class="card h-100 text-center">
							<img class="card-img-top"
								src="https://pbs.twimg.com/profile_images/659033933485469696/HS6BEXG4.jpg"
								alt="">
							<div class="card-body">
								<h4 class="card-title">Squash PEI</h4>
								<h6 class="card-subtitle mb-2 text-muted">40 Enman Crescent
									Charlottetown, PE</h6>
								<p class="card-text">Squash PEI is a non-profit sport
									organization dedicated to the promotion of the great game of
									squash. We are based in Charlottetown, Prince Edward Island,
									Canada - 16 hours East of Toronto, 16 hours north of New York
									City, and 16 minutes from some of the most beautiful beaches in
									the world.</p>
							</div>
							<div class="card-footer">
								<a href="http://squashpei.org/">Click here to see more</a>
							</div>
						</div>
					</div>
				</div>

				<h1 class="my-4 text-center titel">Our Partners</h1>
				<div class="row">
					<div class="col-lg-2 col-md-3 col-sm-4 mb-4">
						<img class="img-fluid"
							src="http://peipma.ca/wp-content/uploads/2017/01/4175_1_kenmac_energy.gif"
							alt="" width="100" height="500">
					</div>
					<div class="col-lg-2 col-md-3 col-sm-4 mb-4">
						<img class="img-fluid"
							src="http://logos-vector.com/images/logo/lar/4/7/1/47126/Petro_Canada_6a7c4_250x250.png"
							alt="" width="100" height="500">
					</div>
					<div class="col-lg-2 col-md-3 col-sm-4 mb-4">
						<img class="img-fluid"
							src="https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Dominos_pizza_logo.svg/220px-Dominos_pizza_logo.svg.png"
							alt="" width="100" height="500">
					</div>
					<div class="col-lg-2 col-md-3 col-sm-4 mb-4">
						<img class="img-fluid"
							src="http://www.naylornetwork.com/lbm-NWL/assets/home_hardware.horiz.jpg"
							alt="" width="100" height="500">
					</div>
					<div class="col-lg-2 col-md-3 col-sm-4 mb-4">
						<img class="img-fluid"
							src="http://peterboroughhumanesociety.ca/wp-content/uploads/2015/06/Leons-660x381.jpg"
							alt="" width="100" height="500">
					</div>
					<div class="col-lg-2 col-md-3 col-sm-4 mb-4">
						<img class="img-fluid"
							src="http://www.easternbaseballacademy.com/assets/chuckies.jpg"
							alt="" width="100" height="500">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="../resources/vendor/jquery/jquery.min.js"></script>
<script type="text/javascript"
	src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery.matchHeight/0.7.0/jquery.matchHeight-min.js"></script>
<script src="../resources/vendor/popper/popper.min.js"></script>
<script src="../resources/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="../resources/js/sb-admin.js"></script>
<script src="../resources/js/slick.js"></script>
<script src="../resources/js/modern-business.js"></script>
</html>