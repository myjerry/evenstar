<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

This is the blog home page.
<c:if test="${not empty posts}">
	<c:forEach items="${posts}" var="post">
		<h1>${post.title}</h1>
		<p>${post.contents}</p>
		<hr />
	</c:forEach>
</c:if>