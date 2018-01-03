<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="../../resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="../../resources/css/modern-business.css" rel="stylesheet">
<link href="../../resources/css/sb-admin.min.css" rel="stylesheet">
<link href="../../resources/js/datatables/dataTables.bootstrap4.css"
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
		href="../home">COMMUNITY GAME CHANGER</a>
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
				href="../home"> <i class="fa fa-fw fa-home"></i> <span
					class="nav-link-text">Home</span>
			</a></li>
			<li class="nav-item" data-toggle="tooltip" data-placement="right"
				title="" data-original-title="Reports"><a class="nav-link"
				href="./reports"> <i class="fa fa-fw fa-area-chart"></i> <span
					class="nav-link-text">Reports</span>
			</a></li>
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
					<h3>Fundings from: ${player.name}</h3>
					<c:set var="total" value="${0}"/>
					<c:forEach var="userSum" items="${userAssociation}">
					    <c:set var="total" value="${total + userSum.sum_total}" />
					</c:forEach>
					<h3><c:out value="Total funds: $ ${df2.format(total)}"/></h3>
					<p>From: ${fn:length(userAssociation)} Member(s)</p>
				</div>
					<div class="col-lg-12">
					<div class="card mb-3">
						<div class="card-header">
							<i class="fa fa-table"></i> Users funding
							<div class="btn-group float-right" role="group" aria-label="Basic example">
							  <a href="./player/pdf?player_id=${player.player_id}" class="btn btn-secondary btn-sm" role="button" aria-pressed="true">PDF</a>
							  <a href="./player/excel?player_id=${player.player_id}" class="btn btn-secondary btn-sm" role="button" aria-pressed="true">XSL</a>
							</div>
						</div>
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-bordered table-striped" id="dataTable"
									width="100%" cellspacing="0">
									<thead>
										<tr>
											<th>Member</th>
											<th>Team</th>
											<th>Player</th>
											<th>Total</th>
											<th>Funding Percent</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th>Member</th>
											<th>Team</th>
											<th>Player</th>
											<th>Total</th>
											<th>Funding Percent</th>
										</tr>
									</tfoot>
									<tbody>
										<c:forEach items="${userAssociation}" var="user" varStatus="item">
											<tr>
												<%-- <td id="username_${item.index}" class="userName" data-id="${user.user_profile_id}"></td>
												<td id="teamname_${item.index}" class="teamName" data-id="${user.team_id}"></td>
												<td id="playername_${item.index}" class="playerName" data-id="${user.player_id}"></td> --%>
												<td>${user.user_first_name} ${user.user_last_name}</td>
												<td>${user.team_name}</td>
												<td>${user.player_name}</td>
												<td>$ ${df2.format(user.sum_total)}</td>
												<td>%${user.donation_amount * 100}</td>
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
<script src="../../resources/vendor/jquery/jquery.min.js"></script>
<script type="text/javascript"
	src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery.matchHeight/0.7.0/jquery.matchHeight-min.js"></script>
<script src="../../resources/vendor/popper/popper.min.js"></script>
<script src="../../resources/js/datatables/jquery.dataTables.js"></script>
<script src="../../resources/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="../../resources/js/datatables/dataTables.bootstrap4.js"></script>
<script src="../../resources/js/sb-admin.js"></script>
<!-- <script src="../resources/js/sb-admin-datatables.min.js"></script> -->
<script src="../../resources/js/jquery.cascadingdropdown.min.js"></script>
<script src="../../resources/js/reports.js"></script>
<script src="../../resources/js/team_reports.js"></script>
</html>