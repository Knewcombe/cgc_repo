<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="../resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="../resources/css/select2.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/select2-bootstrap-theme/0.1.0-beta.10/select2-bootstrap.min.css" rel="stylesheet">
<link href="../resources/css/modern-business.css" rel="stylesheet">
<link href="../resources/css/sb-admin.min.css" rel="stylesheet">

<!-- <link href="../resources/js/datatables/dataTables.bootstrap4.css"
	rel="stylesheet"> -->

<link
	href="https://fortawesome.github.io/Font-Awesome/assets/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Graduate"
	rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Partner Reports</title>
</head>
<body>
	<nav
		class="navbar navbar-expand-lg navbar-dark cl-bg-primary fixed-top"
		id="mainNav"> <a class="navbar-brand nav-titel cl-txt-primary"
		href="../business/home">COMMUNITY GAME CHANGER</a>
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
				href="../business/home"> <i class="fa fa-fw fa-home"></i> <span
					class="nav-link-text">Home</span>
			</a></li>
			<li class="nav-item" data-toggle="tooltip" data-placement="right"
				title="" data-original-title="Reports"><a class="nav-link"
				href="../business/reports"> <i class="fa fa-fw fa-area-chart"></i>
					<span class="nav-link-text">Reports</span>
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
			<li class="nav-item"><a class="nav-link"
				href="../business/logout"> <i class="fa fa-fw fa-sign-out"></i>Logout
			</a></li>
		</ul>
	</div>
	</nav>
	<div class="content-wrapper">
		<div class="container-fluid">
			<form:form class="form-signin" id="regForm"
				modelAttribute="transaction" action="complete" method="post">
				<%-- <div class="row">
					<div class="col-lg-12 text-center">
						<h4>Select the type of purchases</h4>
						<div id="error_select" name="error_select"
							class="alert alert-danger col-lg-12" role="alert">Please
							select a type of purchase</div>
					</div>
					<div class="col-lg-12">
						<div class="row my-4">
							<c:forEach
								items="${businessAccount.businessProfile.businessPreferance}"
								var="businessPreference" varStatus="var">
								<div class="col-lg-4">
									<div class="list-group-item">
										<div class="form-check">
											<label class="form-check-label"> 
											<input
												type="checkbox" class="form-check-input"
												id="checkbox_${businessPreference.preference_id}"
												value="${businessPreference.preference_id}">
												${businessPreference.name}
											</label>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div> --%>
				<div class="container">
					<div class="row text-center">
						<div class="col-lg-12">
							<h4>Enter transaction information</h4>
						</div>
					</div>
					<c:if test="${not empty message}">
						<div class="alert alert-danger col-lg-12" role="alert">${message}</div>
					</c:if>
					<form:errors path="user_profile_id" cssClass="text-danger" />
					<div id="user_id_success" class="alert alert-success">
					  <strong>Success!</strong> User has been found.
					</div>
					<div id="user_id_error" class="alert alert-danger">
					  <strong>Error!</strong> User was not found with card.
					</div>
					<div id="card_error" class="alert alert-danger">
					  <strong>Error!</strong> Wrong card. Please try again.
					</div>
					<div id="connection_error" class="alert alert-danger">
					  <strong>Error!</strong> Unable to connect to server, please check the connection.
					</div>
					<div id="no_user_error" class="alert alert-danger my-2">
					  <strong>Error!</strong> No User has been selected.
					</div>
					<div class="input-group">
						<span class="input-group-addon" id="sizing-addon1">Member Card</span>
						<label path="card_id" class="sr-only">1234567</label>
						<input id="card_input" class="form-control"/>
						<div class="input-group-btn">
							<a id="search_user_select" class="btn btn-lg btn-primary btn-block" href="#">Search</a>
						</div>
						<form:input class="form-control" required="required"
							path="user_profile_id" name="user_profile_id"
							id="user_profile_id" type="hidden"/>
					</div>
					<div id="purcahse_error" class="alert alert-danger my-2">
					  <strong>Error!</strong> No values have been added.
					</div>
					<div class="table-responsive">
						<table class="table">
							<tr id="title">
								<th>Type</th>
								<th>Amount</th>
								<th>Method of payment</th>
							</tr>
							<c:forEach items="${transaction.transactionDetail}"
								var="transactionDetail" varStatus="item">
								<tr
									id="${transaction.transactionDetail[item.index].transaction_type}">
									<td>${businessAccount.businessProfile.businessPreferance[item.index].name}
									<td>
										<div class="input-group row">
											<c:choose>
												<c:when
													test="${businessAccount.businessProfile.businessPreferance[item.index].name == 'Gasoline & Fuel'}">
													<span
														id="error_input_${transaction.transactionDetail[item.index].transaction_type}"
														name="error_input" class="text-danger">Test</span>
													<span class="input-group-addon" id="sizing-addon1">L</span>
													<form:label class="sr-only"
														path="transactionDetail[${item.index}].amount"></form:label>
													<form:input class="form-control purchase_input" required="required"
														path="transactionDetail[${item.index}].amount" data-type="${transaction.transactionDetail[item.index].transaction_type}"
														name="transactionDetailamount"
														id="transactionDetail_amount_${transaction.transactionDetail[item.index].transaction_type}" />
												</c:when>

												<c:otherwise>
													<span
														id="error_input_${transaction.transactionDetail[item.index].transaction_type}"
														name="error_input"
														class="text-danger col-sm-12 text-nowrap">Please
														enter an amount of purchase</span>
													<span class="input-group-addon" id="sizing-addon1">$</span>
													<form:label class="sr-only"
														path="transactionDetail[${item.index}].amount"></form:label>
													<form:input class="form-control purchase_input" required="required"
														path="transactionDetail[${item.index}].amount" data-type="${transaction.transactionDetail[item.index].transaction_type}"
														name="transactionDetailamount"
														id="transactionDetail_amount_${transaction.transactionDetail[item.index].transaction_type}" />
												</c:otherwise>
											</c:choose>
											<form:hidden
												path="transactionDetail[${item.index}].transaction_type" />
											<form:hidden path="transactionDetail[${item.index}].name"
												value="${businessAccount.businessProfile.businessPreferance[item.index].name}" />
										</div>
									</td>
									<td><span
										id="error_radio_${transaction.transactionDetail[item.index].transaction_type}"
										name="error_radio"
										class="text-danger col-sm-12 text-nowrap text-small">No
											method selected</span>
										<div class="btn-group container" data-toggle="buttons"
											id="transactionDetail_method_of_pyment_${transaction.transactionDetail[item.index].transaction_type}">
											<label class="btn btn-primary"><form:radiobutton
													class="form-control"
													path="transactionDetail[${item.index}].method_of_pyment"
													name="transactionDetail_method_of_pyment_${transaction.transactionDetail[item.index].transaction_type}" value="DE" />Debit</label>
											<label class="btn btn-primary"><form:radiobutton
													class="form-control"
													path="transactionDetail[${item.index}].method_of_pyment"
													name="transactionDetail_method_of_pyment_${transaction.transactionDetail[item.index].transaction_type}" value="CR" />Credit
											</label> <label class="btn btn-primary"><form:radiobutton
													class="form-control"
													path="transactionDetail[${item.index}].method_of_pyment"
													name="transactionDetail_method_of_pyment_${transaction.transactionDetail[item.index].transaction_type}" value="CA" />
												Cash </label>
										</div></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-offset-5 col-lg-12 text-center">
						<div class="btn-group">
							<form:button id="register" name="register"
								class="btn btn-lg btn-primary btn-block">Submit</form:button>
						</div>
					</div>
				</div>
			</form:form>
			<div id="searchModal" class="modal">
							<div class="modal-content">
								<div class="modal-header">
									<span class="close">&times;</span>
									<h2>Search</h2>
								</div>
								<div class="modal-body">
									<div id="association_charity_row"
										class="row association-test my-3">
										<div class="col-sm-12">
											<h5 class="login-title">Search for user.</h5>
											<hr>
										</div>
										<div class="row col-centered">
											<div class="col-lg-12 my-3">
											<label for="test">
	  											Click this to highlight the multiple select element
	  											<select id="test" class="js-data-example-ajax" style="width: 100%"></select>
											</label>
										</div>
										</div>
										<div class="col-sm-12">
											<div class="row col-centered">
												<div id="selectUserButton" class="col-sm-3 col-centered text-center">
													<button id="select_user"
														class="btn btn-success btn-lg select_button">Select</button>
												</div>
												<div class="col-sm-3 col-centered text-center">
													<button id="Cancel"
														class="btn btn-danger btn-lg cancel_button">Cancel</button>
												</div>
											</div>
										</div>
									</div>
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
<script src="../resources/js/datatables/jquery.dataTables.js"></script>
<script src="../resources/vendor/bootstrap/js/bootstrap.js"></script>
<script src="../resources/js/datatables/dataTables.bootstrap4.js"></script>
<script src="../resources/js/CardReader/prototype.js"></script>
<script src="../resources/js/CardReader/credit_card_reader.js"></script>
<script src="../resources/js/CardReader/inflections.js"></script>
<!-- <script src="../resources/js/require.js"></script> -->
<script src="../resources/js/select2.min.js"></script>
<script src="../resources/js/jquery.scannerdetection.js"></script>
<script src="../resources/js/transaction.js"></script>
<script src="../resources/js/sb-admin.js"></script>

</html>