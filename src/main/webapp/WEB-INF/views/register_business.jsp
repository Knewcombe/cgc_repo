<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<link href="../resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="../resources/css/modern-business.css" rel="stylesheet">

<link
	href="https://fortawesome.github.io/Font-Awesome/assets/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Graduate"
	rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
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
	<!-- /.container -->
	<div class="container">
		<div class="row">
			<div class="col-lg-12 col-centered">
				<section class="login-form">
					<form:form class="form-signin form-horizontal" role="login"
						id="loginForm" modelAttribute="businessAccount"
						action="./registerProcess" method="post">
						<div class="row">
							<div class="text-center col-sm-12">
								<p class="navbar-brand login-title cl-txt-primary">Partner
									Register Form</p>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 text-center">
								<p class="navbar-brand login-title">Login Information</p>
								<c:if test="${not empty usernameMessage}">
									<div class="alert alert-danger" role="alert">${usernameMessage}</div>
								</c:if>
							</div>
							<div class="col-sm-6">
								<form:errors path="username" cssClass="text-danger" />
								<form:label path="username" class="sr-only">Username</form:label>
								<form:input path="username" name="username" id="username"
									class="form-control" placeholder="Username" required=""
									autofocus="" />
							</div>
							<div class="col-sm-6">
								<form:errors path="password" cssClass="text-danger" />
								<form:label path="password" class="sr-only">Password:</form:label>
								<form:password path="password" name="password" id="password"
									class="form-control" placeholder="Password" required="" />
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="col-sm-12 text-center">
								<p class="navbar-brand login-title">General Information</p>
							</div>
							<div class="col-sm-6">
								<form:errors path="businessProfile.business_name"
									cssClass="text-danger" />
								<form:label path="businessProfile.business_name" class="sr-only">Name of Business</form:label>
								<form:input path="businessProfile.business_name"
									name="business_name" id="business_name" class="form-control"
									placeholder="Name of Business" />
							</div>
							<div class="col-sm-6">
								<form:errors path="businessProfile.main_contact"
									cssClass="text-danger" />
								<form:label path="businessProfile.main_contact" class="sr-only">Main Contact</form:label>
								<form:input path="businessProfile.main_contact"
									name="main_contact" id="main_contact" class="form-control"
									placeholder="Main Contact" />
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="col-sm-12">
								<p class="navbar-brand login-title">Contact Information</p>
							</div>
							<div class="col-sm-6">
								<form:errors path="businessProfile.email" cssClass="text-danger" />
								<form:label path="businessProfile.email" class="sr-only">Email</form:label>
								<form:input path="businessProfile.email" name="email" id="email"
									class="form-control" placeholder="Email" />
							</div>
							<div class="col-sm-6">
								<form:errors path="businessProfile.phone" cssClass="text-danger" />
								<form:label path="businessProfile.phone" class="sr-only">Phone</form:label>
								<form:input path="businessProfile.phone" name="phone" id="phone"
									class="form-control" placeholder="Phone Number" />
							</div>
							<div class="col-sm-4">
								<form:errors path="businessProfile.city" cssClass="text-danger" />
								<form:label path="businessProfile.city" class="sr-only">City</form:label>
								<form:input path="businessProfile.city" name="city" id="city"
									class="form-control" placeholder="City" />
							</div>
							<div class="col-sm-5">
								<form:errors path="businessProfile.address"
									cssClass="text-danger" />
								<form:label path="businessProfile.address" class="sr-only">Address</form:label>
								<form:input path="businessProfile.address" name="address"
									id="address" class="form-control" placeholder="Address" />
							</div>
							<div class="col-sm-3">
								<form:errors path="businessProfile.postal_code"
									cssClass="text-danger" />
								<form:label path="businessProfile.postal_code" class="sr-only">Postal Code</form:label>
								<form:input path="businessProfile.postal_code"
									name="postal_code" id="province_code" class="form-control"
									placeholder="Postal Code" />
							</div>
							<div class="col-sm-3">
								<form:errors path="businessProfile.province_code"
									cssClass="text-danger" />
								<form:label path="businessProfile.province_code">Province</form:label>
								<form:select path="businessProfile.province_code"
									name="province_code" id="province_code" class="form-control"
									placeholder="Province">
									<form:option value="" label="--- Select ---" />
									<form:option value="ON" label="Ontario" />
									<form:option value="QC" label="Quebec" />
									<form:option value="NS" label="Nova Scotia" />
									<form:option value="NB" label="New Brunswick" />
									<form:option value="MB" label="Manitoba" />
									<form:option value="BC" label="British Columbia" />
									<form:option value="PE" label="Prince Edward Island" />
									<form:option value="SK" label="Saskatchewan" />
									<form:option value="AB" label="Alberta" />
									<form:option value="NL" label="Newfoundland and Labrador" />
								</form:select>
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="col-sm-12">
								<p class="navbar-brand login-title">Transaction Options</p>
							</div>
							<div class="col-center">
								<div class="table-responsive">
									<table class="table table-striped">
										<tr>
											<th>Check Applicable</th>
											<th>Cash</th>
											<th>Debit</th>
											<th>Credit</th>
											<th>Sale & Clearance</th>
										</tr>
										<c:forEach
											items="${businessAccount.businessProfile.businessPreferance}"
											var="preferance" varStatus="status">
											<tr class="text-nowrap">
												<c:if test="${status.index == 0}">
													<td><form:checkbox
															path="businessProfile.businessPreferance[${status.index}].name"
															value="Clothing" /> Clothing</td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].cash_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].debit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].credit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].sale_clearance_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
												</c:if>
												<c:if test="${status.index == 1}">
													<td><form:checkbox
															path="businessProfile.businessPreferance[${status.index}].name"
															value="Convenience Store" /> Convenience Store</td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].cash_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].debit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].credit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].sale_clearance_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
												</c:if>
												<c:if test="${status.index == 2}">
													<td><form:checkbox
															path="businessProfile.businessPreferance[${status.index}].name"
															value="Food & Beverage" /> Food & Beverage</td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].cash_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].debit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].credit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].sale_clearance_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
												</c:if>
												<c:if test="${status.index == 3}">
													<td><form:checkbox
															path="businessProfile.businessPreferance[${status.index}].name"
															value="Furniture" /> Furniture</td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].cash_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].debit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].credit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].sale_clearance_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
												</c:if>
												<c:if test="${status.index == 4}">
													<td><form:checkbox
															path="businessProfile.businessPreferance[${status.index}].name"
															value="Gasoline & Fuel" /> Gasoline & Fuel</td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].cash_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">¢/L</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].debit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">¢/L</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].credit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">¢/L</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].sale_clearance_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">¢/L</span>
														</div></td>
												</c:if>
												<c:if test="${status.index == 5}">
													<td><form:checkbox
															path="businessProfile.businessPreferance[${status.index}].name"
															value="General Merchandise" /> General Merchandise</td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].cash_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].debit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].credit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].sale_clearance_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
												</c:if>
												<c:if test="${status.index == 6}">
													<td><form:checkbox
															path="businessProfile.businessPreferance[${status.index}].name"
															value="Hardware" /> Hardware</td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].cash_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].debit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].credit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].sale_clearance_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
												</c:if>
												<c:if test="${status.index == 7}">
													<td><form:checkbox
															path="businessProfile.businessPreferance[${status.index}].name"
															value="Professional Services" /> Professional Services</td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].cash_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].debit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].credit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].sale_clearance_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
												</c:if>
												<c:if test="${status.index == 8}">
													<td><form:checkbox
															path="businessProfile.businessPreferance[${status.index}].name"
															value="Sale & Clearance" /> Sale & Clearance</td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].cash_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].debit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>
													<td><div class="input-group">
															<form:input
																path="businessProfile.businessPreferance[${status.index}].credit_percent"
																class="form-control percent-input" />
															<span class="input-group-addon">%</span>
														</div></td>

												</c:if>
											</tr>
										</c:forEach>
									</table>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-offset-5 col-lg-12 text-center">
								<div class="btn-group">
									<form:button id="register" name="register"
										class="btn btn-lg btn-primary btn-block">Register</form:button>
								</div>
							</div>
						</div>
					</form:form>
				</section>
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
					<li><a href="./benefits/family"> Benefits for Members</a></li>
					<li><a href="./benefits/businesses"> Benefits for Merchants</a></li>
					<li><a href="./contact"> Contact</a></li>
					<li><a href="./communites/sport"> Sport Community</a></li>
					<li><a href="./communites/charity"> Charity Community</a></li>
					<li><a href="./communites/nonprof"> Non-Profit Community</a></li>
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
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery.matchHeight/0.7.0/jquery.matchHeight-min.js"></script>
<script src="../resources/vendor/popper/popper.min.js"></script>
<script src="../resources/vendor/bootstrap/js/bootstrap.min.js"></script>
</html>