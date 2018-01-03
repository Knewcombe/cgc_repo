<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="../resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="../resources/css/modern-business.css" rel="stylesheet">
<link href="../resources/css/sb-admin.min.css" rel="stylesheet">
<link href="../resources/js/datatables/dataTables.bootstrap4.css"
	rel="stylesheet">

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
				href="../transaction"> <i class="fa fa-fw fa-credit-card-alt"></i> <span
					class="nav-link-text">Make Transaction</span>
			</a></li>
			<!-- <li class="nav-item" data-toggle="tooltip" data-placement="right"
				title="" data-original-title="Search for User"><a class="nav-link"
				href="../search/user"> <i class="fa fa-fw fa-search"></i>
					<span class="nav-link-text">Search for User</span>
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
			<div class="row">
			<div class="col-lg-12 text-center">
					<h3>${businessAccount.businessProfile.business_name}</h3>
				</div>
				<div class="col-lg-12 text-center">
					<h4>Total funds raised: $ ${df2.format(total)}</h4>
				</div>
				<div class="col-lg-12">
					<div class="card mb-3">
						<div class="card-header">
							<i class="fa fa-table"></i> Reports
							<div class="btn-group float-right" role="group" aria-label="Basic example">
							  <a href="./reports/pdf" class="btn btn-secondary btn-sm" role="button" aria-pressed="true">PDF</a>
							  <a href="./reports/excel" class="btn btn-secondary btn-sm" role="button" aria-pressed="true">XSL</a>
							</div>
						</div>
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-bordered table-striped" id="dataTable"
									width="100%" cellspacing="0">
									<thead>
										<tr>
											<th>Report #</th>
											<th>User Id</th>
											<th>Amount of purchase</th>
											<th>Total Percent Amount</th>
											<th>Date of Purchase</th>
											<th class="no-sort"></th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th>Report #</th>
											<th>User Id</th>
											<th>Amount of purchase</th>
											<th>Total Percent Amount</th>
											<th>Date of Purchase</th>
											<th></th>
										</tr>
									</tfoot>
									<tbody>
										<c:forEach items="${reports}" var="report" varStatus="loop">
											<tr>
												<td>${report.transaction_id}</td>
												<td>${report.user_profile_id}</td>
												<td>$ ${df2.format(report.total)}</td>
												<td>$ ${df2.format(report.precent_total)}</td>
												<td>${report.date_of_purhase}</td>
												<td><a
													href="./reports/detail?transaction_id=${report.transaction_id}"
													class="btn btn-info">Details</a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<div class="card-footer small text-muted">Updated yesterday
							at 11:59 PM</div>
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
<script src="../resources/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="../resources/js/datatables/dataTables.bootstrap4.js"></script>
<script src="../resources/js/sb-admin.js"></script>
<!-- <script src="../resources/js/sb-admin-datatables.min.js"></script> -->
<script src="../resources/js/reports.js"></script>
</html>