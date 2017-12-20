<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h3 class="text-center">Enter in any additional family members in your home</h3>
			<c:forEach items="${userAccount.userProfile.family}" var="family"
				varStatus="status">
				<div class="alert alert-warning col-lg-12" role="alert">
					<h5 class="text-center">Family Member</h5>
				</div>
				<div class="row">
					<div class="col-lg-6">
						<form:label path="userProfile.family[${status.index}].first_name"
							class="sr-only">First name</form:label>
						<form:input path="userProfile.family[${status.index}].first_name"
							name="first_name" id="first_name" class="form-control"
							placeholder="First Name" />
					</div>
					<div class="col-lg-6">
						<form:label path="userProfile.family[${status.index}].last_name"
							class="sr-only">Last name</form:label>
						<form:input path="userProfile.family[${status.index}].last_name"
							name="last_name" id="last_name" class="form-control"
							placeholder="Last Name" />
					</div>
					<div class="col-lg-6">
						<form:label
							path="userProfile.family[${status.index}].date_of_birth">Date Of Birth</form:label>
						<form:input
							path="userProfile.family[${status.index}].date_of_birth"
							name="date_of_birth" id="date_of_birth" class="form-control"
							placeholder="MM/DD/YY" />
						<form:label path="userProfile.family[${status.index}].relation">Relation</form:label>
						<form:select path="userProfile.family[${status.index}].relation"
							class="form-control">
							<form:option value="" label="--- Select ---" />
							<form:option value="H" label="Husband" />
							<form:option value="W" label="Wife" />
							<form:option value="S" label="Son" />
							<form:option value="D" label="Daughter" />
							<form:option value="O" label="Other" />
						</form:select>
						<form:label path="userProfile.family[${status.index}].gender">Gender</form:label>
						<form:select path="userProfile.family[${status.index}].gender"
							class="form-control" id="gender">
							<form:option value="" label="--- Select ---" />
							<form:option value="M" label="Male" />
							<form:option value="F" label="Female" />
						</form:select>
					</div>
				</div>
			</c:forEach>
</body>
</html>