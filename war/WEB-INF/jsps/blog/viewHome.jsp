<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/xhtml1-strict.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<html>
	<head>
		<title>Home</title>
	</head>
	<body>
This is the blog home page.
<c:if test="${not empty posts}">
	<c:forEach items="${posts}" var="post">
		<h1>${post.title}</h1>
		<p>${post.contents}</p>
		<hr />
	</c:forEach>
</c:if>
	</body>
</html>

<c:if test="${not empty generatedBlogPage}">
${generatedBlogPage}
</c:if>