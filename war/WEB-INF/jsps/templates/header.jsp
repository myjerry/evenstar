<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@page import="com.google.appengine.api.users.User" %>
<%@page import="com.google.appengine.api.users.UserService" %>
<%@page import="com.google.appengine.api.users.UserServiceFactory" %>

<div id="header">
	<div id="logo"></div>
	<div id="myjerry"><img src="<c:url value='/assets/images/myjerryText.png' />" /></div>
	<div id="catchPhrase">Open source libraries for <br />the development community</div>
	<div id="javaLogo"><img src="<c:url value='/assets/images/javalogo.png' />" /></div>
	<div id="header-nav">
		<ul>
			<li><a href="/index.html"><img src="<c:url value='/assets/images/home.jpg' />" />&nbsp;Home</a></li>
			
			<%
				UserService userService = UserServiceFactory.getUserService();
				User user = userService.getCurrentUser();
				if(user == null) {
			%>
				<li><a href="/login.html"><img src="<c:url value='/assets/images/myaccount.jpg' />" />&nbsp;Sign In</a></li>
			<% } else { %>
				<li><a href="/myaccount.html"><img src="<c:url value='/assets/images/myaccount.jpg' />" />&nbsp;My Account</a></li>
			<% } %>
			<li><a href="/contactus.html"><img src="<c:url value='/assets/images/contactus.jpg' />" />&nbsp;Contact Us</a></li>
		</ul>
	</div>
</div>
