<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/xhtml1-strict.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@page import="com.google.appengine.api.users.User" %>
<%@page import="com.google.appengine.api.users.UserService" %>
<%@page import="com.google.appengine.api.users.UserServiceFactory" %>

<html>
	<head>
		<title>Evenstar :: <tiles:getAsString name="title" /></title>
		<link rel="stylesheet" type="text/css" media="screen, print, projection"  href="<c:url value='/assets/css/evenstar.css' />"></link>
	</head>
	<body>
		<a href="/index.html">Home</a>
		<a href="/admin.html">Admin</a>
		<a href="/_ah/admin" target="_blank">Local Datastore</a>
		<%
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			String thisURL = request.getRequestURL().toString();
			if(user == null) {
		%>
			<a href="<%=userService.createLoginURL(thisURL) %>">Sign In</a>
		<% } else { %>
			<a href="<%=userService.createLogoutURL(thisURL) %>">Sign Out</a>
		<% } %>
		
		<br />
		<br />
		<br />
		
		<tiles:insertAttribute name="body" />
	</body>
</html>
