<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/myjerry.tld" prefix="mj"%>
<%@ page isELIgnored="false" %>

User: <mj:gae.user />

<a href="/admin/createBlog.html">Create a blog</a>

Current Blogs:
<c:if test="${not empty blogs}">
	<c:forEach items="${blogs}" var="blog">
		<h3>${blog.title}</h3>
		<a href="/author/newPost.html?blogID=${blog.blogID}">New Post</a>
		<a href="/author/editPosts.html?blogID=${blog.blogID}">Edit Posts</a>
		<a href="/admin/settings.html?blogID=${blog.blogID}">Settings</a>
		<a href="/admin/layout.html?blogID=${blog.blogID}">Layout</a>
		<a href="">View Blog</a>
	</c:forEach>
</c:if>

<c:if test="${empty blogs}" >
	No blogs found.
</c:if>
