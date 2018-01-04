<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
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
			<div class="text-center col-sm-12 my-4">
				<h3 class="login-title cl-txt-primary">Member Register Form</h3>
				<c:if test="${not empty usernameMessage}">
					<div class="alert alert-danger" role="alert">${usernameMessage}</div>
				</c:if>
			</div>
			<div class="col-lg-12">
				<form:form id="regForm" modelAttribute="userAccount" role="login"
					action="./registerProcess" method="post">
					<title>Member Information</title>
					<div data-step="0" style="margin-left: 15%">
						<div class="row">
							<div class="col-sm-12 my-4">
								<h5 class="login-title">Member Information</h5>
								<c:if test="${not empty usernameMessage}">
									<div class="alert alert-danger" role="alert">${usernameMessage}</div>
								</c:if>
							</div>
							<div class="col-sm-6 col-md-3 col-lg-3 form-group">
								<form:errors path="userProfile.first_name"
									cssClass="text-danger" />
								<form:label path="userProfile.first_name">First name</form:label>
								<form:input path="userProfile.first_name" name="first_name"
									id="first_name" class="form-control" placeholder="John"
									autofocus="true" required="true" />
							</div>
							<div class="col-sm-6 col-md-3 col-lg-3 form-group">
								<form:errors path="userProfile.last_name" cssClass="text-danger" />
								<form:label path="userProfile.last_name">Last name</form:label>
								<form:input path="userProfile.last_name" name="last_name"
									id="last_name" class="form-control" placeholder="Doe"
									required="true" />
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 col-md-3 col-lg-3 form-group">
								<form:errors path="username" cssClass="text-danger" />
								<form:label path="username">Username</form:label>
								<form:input path="username" name="username" id="username"
									class="form-control" placeholder="Username" required="true" />
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 col-md-3 col-lg-3 form-group">
								<form:errors path="password" cssClass="text-danger" />
								<form:label path="password">Password</form:label>
								<form:password path="password" name="password" id="password"
									class="form-control" placeholder="Password" required="true" />
							</div>
							<div class="col-sm-6 col-md-3 col-lg-3 form-group">
								<label>Confirm Password</label> <input type="password"
									name="confirmPass" id="confirmPass" class="form-control"
									placeholder="Password" required />
							</div>
						</div>
						<div class="row">
							<div class="col-sm-4 col-md-3 col-lg-3 form-group">
								<form:label path="userProfile.gender">Gender</form:label>
								<form:select path="userProfile.gender" class="form-control"
									id="gender" name="gender" required="true">
									<form:option value="" label="--- Select ---" />
									<form:option value="M" label="Male" />
									<form:option value="F" label="Female" />
								</form:select>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12">
								<form:label path="userProfile.date_of_birth">Date Of Birth</form:label>
							</div>
							<div class="col-sm-3 col-md-3 col-lg-2 form-group">
								<select id="dobday" name="day" class="form-control" required></select>
							</div>
							<div class="col-sm-3 col-md-3 col-lg-2 form-group">
								<select id="dobmonth" name="month" class="form-control" required></select>
							</div>
							<div class="col-sm-3 col-md-3 col-lg-2 form-group">
								<select id="dobyear" name="year" class="form-control" required></select>
							</div>
							<form:input path="userProfile.date_of_birth" name="date_of_birth"
								id="date_of_birth" type="hidden" />
						</div>
						<div class="row">
							<div class="col-sm-6 col-md-3 col-lg-3 form-group">
								<form:errors path="userProfile.phone" cssClass="text-danger" />
								<form:label path="userProfile.phone">Phone Number</form:label>
								<form:input path="userProfile.phone" id="phone" name="phone"
									class="form-control" placeholder="(123) 123-1234"
									required="true" />
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 col-md-3 col-lg-3 form-group">
								<form:errors path="userProfile.email" cssClass="text-danger" />
								<form:label path="userProfile.email">Email</form:label>
								<form:input path="userProfile.email" name="email" id="email"
									class="form-control" placeholder="example@email.com"
									type="email" required="true" />
							</div>
							<div class="col-sm-6 col-md-3 col-lg-3 form-group">
								<label>Confirm Email</label>
								<input name="confirmEmail" id="confirmEmail"
									class="form-control"
									type="email" required="true" />
							</div>
						</div>
						<div class="row">
							<div class="col-sm-4 col-md-3 col-lg-3 form-group">
								<form:errors path="userProfile.province_code"
									cssClass="text-danger" />
								<form:label path="userProfile.province_code">Province</form:label>
								<form:select path="userProfile.province_code"
									name="province_code" id="province_code" class="form-control"
									placeholder="Province" required="true">
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
						<div class="row">
							<div class="col-sm-4 col-md-3 col-lg-3 form-group">
								<form:errors path="userProfile.city" cssClass="text-danger" />
								<form:label path="userProfile.city">City</form:label>
								<form:input path="userProfile.city" name="city" id="city"
									class="form-control" placeholder="Charlottetown"
									required="true" />
							</div>
							<div class="col-sm-4 col-md-3 col-lg-3 form-group">
								<form:errors path="userProfile.address" cssClass="text-danger" />
								<form:label path="userProfile.address">Address</form:label>
								<form:input path="userProfile.address" name="address"
									id="address" class="form-control" placeholder="123 Street Ave"
									required="true" />
							</div>
							<div class="col-sm-4 col-md-3 col-lg-3 form-group">
								<form:errors path="userProfile.postal_code"
									cssClass="text-danger" />
								<form:label path="userProfile.postal_code">Postal Code</form:label>
								<form:input path="userProfile.postal_code" name="postal_code"
									id="postal_code" class="form-control" type="cdnPostal"
									placeholder="123123" required="true" />
							</div>
						</div>
					</div>
					<title>Card Holders</title>
					<div data-step="1">
						<div class="row">
							<div class="col-sm-12 text-center">
								<h5 class="login-title">Card Holders Information</h5>
							</div>
							<div class="col-sm-12 text-center col-centered">
								<p>Select how many cards you would like and information of
									the card holder</p>
							</div>
							<div class="col-sm-3 col-centered">
								<select id="card_select" name="card_select" class="form-control">
									<option value="0">--Select--</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
								</select>
							</div>
						</div>
						<c:forEach items="${userAccount.userProfile.family}" var="family"
							varStatus="status">
							<div class="card_member my-4" id="card_member_${status.index}">
								<div class="col-sm-12 col-centered">
									<h5 class="login-title">Card Holder ${status.index + 1}</h5>
								</div>
								<hr>
								<div class="row">
									<div class="col-sm-4">
										<form:label
											path="userProfile.family[${status.index}].first_name">First name</form:label>
										<form:input
											path="userProfile.family[${status.index}].first_name"
											name="first_name_${status.index}" class="form-control"
											placeholder="First Name" required="true" />
									</div>
									<div class="col-sm-4">
										<form:label
											path="userProfile.family[${status.index}].last_name">Last name</form:label>
										<form:input
											path="userProfile.family[${status.index}].last_name"
											name="last_name_${status.index}" class="form-control"
											placeholder="Last Name" required="true" />
									</div>
									<div class="col-sm-4">
										<form:label
											path="userProfile.family[${status.index}].date_of_birth">Date Of Birth</form:label>
										<form:input
											path="userProfile.family[${status.index}].date_of_birth"
											name="date_of_birth_${status.index}"
											class="form-control date_of_birth" placeholder="MM/DD/YY"
											required="true" />
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
					<title>Sport Associations</title>
					<div data-step="2">
						<div class="text-center col-sm-12 my-3">
							<h5 class="login-title">Community Partners</h5>
							<p>Please select Community Partners you would like to
								support. As least one needs to be selected</p>
							<div class="alert alert-danger" id="association_select_alert"
								style="display: none;">Please select at least one Community Partner to contribute to</div>
							<div class="alert alert-danger" id="percent_error"
								style="display: none;">All Donation amounts must equal
								100%</div>
						</div>
						<div class="row" style="height: 10%">
							<div class="col-sm-12">
								<div class="slider" id="slider"></div>
							</div>
						</div>
						<div id="items-row" class="row" style="height: 30%">
							<!-- need to add the block here -->
							<!-- <button id="myBtn" class="modalButton btn btn-primary btn-lg">Select
								Associations</button> -->
							<div id="myBtn"
								class="my-2 box association_selected col-lg-2 col-md-4 col-sm-5 col-12">
								<div class="card card-body card-color" style="height: 100%;">
									<h1 class="card-title text-white text-center" style="position: relative; top: 50%; transform: translateY(-50%); "><i class="fa fa-plus" aria-hidden="true"></i></h1>
								</div>
							</div>
						</div>
						<div id="selectionModal" class="modal">
							<div class="modal-content">
								<div class="modal-header">
									<span class="close">&times;</span>
									<h2>Select your association</h2>
								</div>
								<div class="modal-body">
									<div class="row">
										<div class="col-12 my-2">
											<!-- <button id="sportSelect"
												class="sportButton btn btn-primary btn-lg">Sport</button> -->
											<div id="sportSelect" class="card text-center card-color text-white">
												<div class="card-block">
													<h4 class="card-title">Sport</h4>
													<p class="card-text">Sport Associations</p>
												</div>
											</div>
										</div>
										<div class="col-12 my-2">
											<!-- <button id="charitySelect"
												class="charityButton btn btn-primary btn-lg">Charity</button> -->
											<div id="charitySelect" class="card text-center card-color text-white">
												<div class="card-block">
													<h4 class="card-title">Charity</h4>
													<p class="card-text">Charity Associations</p>
												</div>
											</div>
										</div>
										<div class="col-12 my-2">
											<!-- <button id="profitSelect"
												class="nonProfButton btn btn-primary btn-lg">Non-Profit</button> -->
											<div id="profitSelect" class="card text-center card-color text-white">
												<div class="card-block">
													<h4 class="card-title">Non-Profit</h4>
													<p class="card-text">Non-Profit Associations</p>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div id="sportModal" class="modal">
							<!-- Modal content -->
							<div class="modal-content">
								<div class="modal-header">
									<span class="close">&times;</span>
									<h2>Sport Select</h2>
								</div>
								<div class="modal-body">
									<div id="association_sport_row"
										class="row association-test my-3">
										<div class="col-sm-12 col-centered">
											<h5 class="login-title">Sport Associations</h5>
											<hr>
										</div>
										<div class="col-sm-12">
											<div class="row col-centered my-3">
											<div class="col-sm-12">
												<div id="sport-alert" class="alert alert-danger">
												  You have already selected this Sport Association, please choose another.
												</div>
											</div>
												<div class="col-sm-4">
													<h6>Province</h6>
													<select id="province_select"
														class="form-control province input-sm"
														name="province_select">
														<option value>Select</option>
													</select>
												</div>
												<div class="col-sm-5">
													<h6>Community</h6>
													<select id="community_select"
														class="form-control community" name="community_select">
														<option value>Select</option>
													</select>
												</div>
												<div class="col-sm-3">
													<h6>Sport</h6>
													<select id="sport_items" class="form-control sport"
														name="sport_select">
														<option value>Select</option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-sm-12">
											<div class="row col-centered my-3">
												<div class="col-sm-6">
													<h6>Association</h6>
													<select id="association_select"
														class="form-control association_select"
														name="association_select">
														<option value>Select</option>
													</select>
												</div>
												<div class="col-sm-3">
													<h6>Division</h6>
													<select id="division_select" class="form-control division"
														name="division_select">
														<option value>Select</option>
													</select>
												</div>
												<div class="col-sm-3">
													<h6>Gender</h6>
													<select id="gender_select" class="form-control gender"
														name="gender_select">
														<option value>Select</option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-sm-12">
											<div class="row my-3">
												<div class="col-sm-6 col-centered">
													<h6>Team</h6>
													<select id="team_id" class="form-control team"
														name="team_id">
														<option value>Select</option>
													</select>
												</div>
											</div>
											<div class="row my-3">
												<div class="col-sm-6 col-centered">
													<h6>Player</h6>
													<select id="player_id" class="form-control player"
														name="player_id">
														<option value>Select</option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-sm-12">
											<div class="row">
												<div id="selectSportButton" class="col-sm-3 col-centered">
													<button id="sportSelectButton"
														class="btn btn-success btn-lg sport_select_button">Select</button>
												</div>
												<div id="updateSportButton" class="col-sm-3 col-centered"
													style="display: none;">
													<button id="nonProfUpdateButton"
														class="btn btn-success btn-lg update_button">Updated</button>
												</div>
												<div class="col-sm-3 col-centered">
													<button id="Cancel"
														class="btn btn-danger btn-lg cancel_button">Cancel</button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div id="charitiyModal" class="modal">
							<div class="modal-content">
								<div class="modal-header">
									<span class="close">&times;</span>
									<h2>Charity</h2>
								</div>
								<div class="modal-body">
									<div id="association_charity_row"
										class="row association-test my-3">
										<div class="col-sm-12 col-centered">
											<h5 class="login-title">Charity Associations</h5>
											<hr>
										</div>
										<div class="col-sm-12">
											<div class="row col-centered my-3">
											<div class="col-sm-12">
												<div id="charity-alert" class="alert alert-danger">
												  You have already chosen this Charity Association, please choose another.
												</div>
											</div>
												<div class="col-sm-12">
													<h6>Province</h6>
													<select id="province_c_select"
														class="form-control province_char" name="province_select">
														<option value>Select</option>
													</select>
												</div>
												<div class="col-sm-12">
													<h6>Community</h6>
													<select id="community_c_select"
														class="form-control community_char"
														name="community_select">
														<option value>Select</option>
													</select>
												</div>
												<div class="col-sm-12">
													<h6>Charity</h6>
													<select id="name_c_select" class="form-control name_select"
														name="name_select">
														<option value>Select</option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-lg-12 my-2">
											<div class="card">
											  <div class="card-body">
											    <h4 class="card-title">Charity Receipt</h4>
											    <p class="card-text">Would you like the selected Charity to send you a receipt of your donations?</p>
											    	<div class="alert alert-warning">
													  <strong>NOTE:</strong> Checking this option will require us to send your contact information to the selected Charity. If you do not want your contact information to be sent to the selected Charity, please leave this unchecked.
													</div>
											    	<div class="form-check">
												    <label class="form-check-label">
												      <input id="receipt_selection" type="checkbox" class="form-check-input">
												      <strong>Yes</strong>, I would like to receive donation receipts from the selected Charity.
												    </label>
												  </div>
											  </div>
											</div>
										</div>
										<div class="col-sm-12">
											<div class="row">
												<div id="selectCharityButton" class="col-sm-3 col-centered">
													<button id="charitySelectButton"
														class="btn btn-success btn-lg charity_select_button">Select</button>
												</div>
												<div id="updateCharityButton" class="col-sm-3 col-centered"
													style="display: none;">
													<button id="nonProfUpdateButton"
														class="btn btn-success btn-lg update_button">Updated</button>
												</div>
												<div class="col-sm-3 col-centered">
													<button id="Cancel"
														class="btn btn-danger btn-lg cancel_button">Cancel</button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div id="nonProfModal" class="modal">
							<div class="modal-content">
								<div class="modal-header">
									<span class="close">&times;</span>
									<h2>Non-Profit</h2>
								</div>
								<div class="modal-body">
									<div id="association_nonProf_row"
										class="row association-test my-3">
										<div class="col-sm-12 col-centered">
											<h5 class="login-title">Non-Profit Associations</h5>
											<hr>
										</div>
										<div class="col-sm-12">
											<div class="row col-centered my-3">
											<div class="col-sm-12">
												<div id="nonprof-alert" class="alert alert-danger">
												  You have already chosen this Non-Profit Association, please choose another.
												</div>
											</div>
												<div class="col-sm-12">
													<h6>Province</h6>
													<select id="province_nonp_select"
														class="form-control province_nonProf"
														name="province_nonp_select">
														<option value>Select</option>
													</select>
												</div>
												<div class="col-sm-12">
													<h6>Community</h6>
													<select id="community_nonp_select"
														class="form-control community_nonProf"
														name="community_select">
														<option value>Select</option>
													</select>
												</div>
												<div class="col-sm-12">
													<h6>Non Profit</h6>
													<select id="name_nonp_select"
														class="form-control name_select_nonProf"
														name="name_select">
														<option value>Select</option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-sm-12">
											<div class="row">
												<div id="selectNonProfButton" class="col-sm-3 col-centered">
													<button id="nonProfSelectButton"
														class="btn btn-success btn-lg nonProf_select_button">Select</button>
												</div>
												<div id="updateNonProfButton" class="col-sm-3 col-centered"
													style="display: none;">
													<button id="nonProfUpdateButton"
														class="btn btn-success btn-lg update_button">Updated</button>
												</div>
												<div class="col-sm-3 col-centered">
													<button id="Cancel"
														class="btn btn-danger btn-lg cancel_button">Cancel</button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<c:forEach items="${userAccount.userProfile.userAssociation}"
							var="don" varStatus="status">
							<div id="association_list_${status.index}"
								class="association_list">
								<form:input
									path="userProfile.userAssociation[${status.index}].donation_amount"
									id="donation_dysplay_${status.index}"
									class="form-control input-sm percent-input" type="hidden"
									name="donation_select" />
								<form:input
									path="userProfile.userAssociation[${status.index}].association_id"
									name="association_id" id="association_id_${status.index}"
									class="form-control" data-type="association"
									data-selection="id" type="hidden" />
									
									<form:input
									path="userProfile.userAssociation[${status.index}].team_id"
									name="team_id" id="team_id_${status.index}"
									class="form-control" data-type="association"
									data-selection="id" type="hidden" />

								<form:input
									path="userProfile.userAssociation[${status.index}].player_id"
									name="player_id" id="player_id_${status.index}"
									class="form-control" data-type="association"
									data-selection="id" type="hidden" />

								<form:input
									path="userProfile.userAssociation[${status.index}].charity_id"
									name="charity_id" id="charity_id_${status.index}"
									class="form-control" data-type="association"
									data-selection="id" type="hidden" />

								<form:input
									path="userProfile.userAssociation[${status.index}].nonprof_id"
									name="nonprof_id" id="nonprof_id_${status.index}"
									class="form-control" data-type="association"
									data-selection="id" type="hidden" />
									
								<form:input
									path="userProfile.userAssociation[${status.index}].chairty_recipts"
									name="chairty_recipts" id="chairty_recipts_${status.index}"
									class="form-control" data-type="association"
									data-selection="id" type="hidden" />
							</div>
						</c:forEach>
					</div>
			</form:form>
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
<script src="../resources/js/jquery.steps.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="../resources/js/jquery-ui-slider-pips.js"></script>
<script src="../resources/js/wNumb.js"></script>
<script src="../resources/js/nouislider.min.js"></script>
<!-- <script src="../resources/js/jquery.linkedsliders.min.js"></script> -->
<script type="text/javascript"
	src="//cdnjs.cloudflare.com/ajax/libs/jqueryui-touch-punch/0.2.3/jquery.ui.touch-punch.min.js"></script>
<script src="../resources/js/dobPicker.min.js"></script>
<script src="../resources/js/jquery.mask.js"></script>
<script src="../resources/js/jquery.validate.min.js"></script>
<script src="../resources/js/jquery.cascadingdropdown.min.js"></script>
<script src="../resources/js/user_register_form.js"></script>
</html>