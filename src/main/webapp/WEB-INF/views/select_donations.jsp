<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
        <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
            <title>Registration</title>
        </head>
        <body>
            <form:form id="regForm" command="index" action="/registerProcess/user" method="post">
                <table align="center">
                	<c:forEach var="donation" items="${donations}" varStatus="status"> 
	    				<tr>
	                        <td>
	                            <form:label path="">Select the sport associations you would like to support</form:label>
	                        </td>
	                        <td>
	                            <form:select path="donations.get(${status.index}).orginizatioin_id" name="orginizatioin_id" id="orginizatioin_id" items="${orginizations}"/>
	                            <%-- <form:select path="donation.orginization_id" name="orginization_id" id="orginization_id">
								   <form:option value="NONE" label="--- Select ---"/>
								   <c:forEach var="orginization" items="${orginizations}">
								   	<form:option value="${orginization.orginization_id}" label="${orginization.name}"/>
								   </c:forEach>
								</form:select> --%>
	                        </td>
	                    </tr>
					</c:forEach>
					<tr>
                        <td></td>
                        <td>
                            <form:button id="register" name="register">Next</form:button>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                        	<a href="/">Home</a>
                        </td>
                    </tr>
                </table>
            </form:form>
        </body>
        </html>