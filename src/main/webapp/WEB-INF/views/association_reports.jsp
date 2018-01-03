<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
				<c:if test="${associtationAccount.charityAssociation != null}">
				<div class="col-lg-12 text-center">
				<h3>${associtationAccount.charityAssociation.name}</h3>
				</div>
				<div class="col-lg-12 text-center">
					<c:set var="total" value="${0}"/>
					<c:forEach var="userSum" items="${userAssociation}">
					    <c:set var="total" value="${total + userSum.sum_total}" />
					</c:forEach>
					<h4><c:out value="Total funds: $ ${df2.format(total)}"/></h4>
					<p>From: ${fn:length(userAssociation)} Member(s)</p>
				</div>
					<div class="col-lg-12">
					<div class="card mb-3">
						<div class="card-header">
							<i class="fa fa-table"></i> Users funding
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
											<th>Member</th>
											<th>Total</th>
											<th>Funding Percent</th>
											<th>Charity Receipts</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th>Member</th>
											<th>Total</th>
											<th>Funding Percent</th>
											<th>Charity Receipts</th>
										</tr>
									</tfoot>
									<tbody>
										<c:forEach items="${userAssociation}" var="user" varStatus="item">
											<tr>
												<%-- <td id="username_${item.index}" class="userName" data-id="${user.user_profile_id}"></td> --%>
												<td>${user.user_first_name} ${user.user_last_name}</td>
												<td>$ ${df2.format(user.sum_total)}</td>
												<td>%${user.donation_amount * 100}</td>
												<td><c:choose><c:when test="${user.chairty_recipts}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose></td>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<div class="card-footer small text-muted">Updated yesterday
							at 11:59 PM</div>
					</div>
				</div>
				</c:if>
				<c:if test="${associtationAccount.sportAssociation != null}">
				<div class="col-lg-12 text-center">
				<h3>${associtationAccount.sportAssociation.name}</h3>
				</div>
				<div class="col-lg-12 text-center">
					<c:set var="total" value="${0}"/>
					<c:forEach var="userSum" items="${userAssociation}">
					    <c:set var="total" value="${total + userSum.sum_total}" />
					</c:forEach>
					<h4><c:out value="Total funds: $ ${df2.format(total)}"/></h4>
					<p>From: ${fn:length(userAssociation)} Member(s)</p>
				</div>
					<div class="col-lg-12">
						<div class="card mb-3">
							<div class="card-header">
								<i class="fa fa-table"></i> Refine Reports
							</div>
							<div class="card-body col-centered">
								<div id="report_search" class="row" data-index="${associtationAccount.sportAssociation.association_id}">
								<div class="col-lg-12">
									<h5 class="text-center">Select the team or player you would like to refine your search for</h5>
								</div>
									<div class="col-lg-3 mb-3">
										<h6>Division</h6>
										<select id="division_select" class="form-control division" name="division_select">
											<option value>Select</option>
										</select>
									</div>
									<div class="col-lg-3 mb-3">
										<h6>Gender</h6>
										<select id="gender_select" class="form-control gender" name="gender_select">
											<option value>Select</option>
										</select>
									</div>
									<div class="col-lg-3">
										<h6>Team</h6>
										<select id="team_select" class="form-control team" name="team_select">
											<option value>Select</option>
										</select>
									</div>
									<div class="col-lg-3">
										<h6>Player</h6>
										<select id="player_select" class="form-control player" name="player_select">
											<option value>Select</option>
										</select>
									</div>
									<div class="col-lg-12 mt-3">
										<button id="select_team_player" class="btn btn-success btn-lg disable">Search</button>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-12">
					<div class="card mb-3">
						<div class="card-header">
							<i class="fa fa-table"></i> Users funding
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
												<c:choose>
												    <c:when test="${user.team_id=='0'}">
												        <td>${user.team_name}</td>
												    </c:when>    
												    <c:otherwise>
												        <td><a href="./reports/team?team_id=${user.team_id}">${user.team_name}</a></td>
												    </c:otherwise>
												</c:choose>
												<c:choose>
												    <c:when test="${user.player_id=='0'}">
												        <td>${user.player_name}</td>
												    </c:when>    
												    <c:otherwise>
												        <td><a href="./reports/player?player_id=${user.player_id}">${user.player_name}</a></td>
												    </c:otherwise>
												</c:choose>
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
				</c:if>
				<c:if test="${associtationAccount.nonProfAssociation != null}">
				<div class="col-lg-12 text-center">
				<h3>${associtationAccount.nonProfAssociation.name}</h3>
				</div>
				<div class="col-lg-12 text-center">
					<c:set var="total" value="${0}"/>
					<c:forEach var="userSum" items="${userAssociation}">
					    <c:set var="total" value="${total + userSum.sum_total}" />
					</c:forEach>
					<h4><c:out value="Total funds: $ ${df2.format(total)}"/></h4>
					<p>From: ${fn:length(userAssociation)} Member(s)</p>
				</div>
					<div class="col-lg-12">
					<div class="card mb-3">
						<div class="card-header">
							<i class="fa fa-table"></i> Users funding
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
											<th>Member</th>
											<th>Total</th>
											<th>Funding Percent</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th>Member</th>
											<th>Total</th>
											<th>Funding Percent</th>
										</tr>
									</tfoot>
									<tbody>
										<c:forEach items="${userAssociation}" var="user" varStatus="item">
											<tr>
												<%-- <td id="username_${item.index}" class="userName" data-id="${user.user_profile_id}"></td> --%>
												<td>${user.user_first_name} ${user.user_last_name}</td>
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
				</c:if>
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
<script src="../resources/js/jquery.cascadingdropdown.min.js"></script>
<script src="../resources/js/reports.js"></script>
<script src="../resources/js/association_reports.js"></script>
</html>