<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<div class="form">
	<div class="contain">
	
		<h5>Total Posts: ${totalPosts}</h5>
	
		<c:forEach items="${posts}" var="post">
			<div class="form-row">
				<a href="/author/post.html?blogID=${blogID}&postID=${post.postID}" >Edit Post</a>
				<label>${post.title}</label>
				<c:if test="${empty post.postedDate}">
					<label>draft</label>
					<label>${post.creationDate}</label>
				</c:if>
				<c:if test="${not empty post.postedDate}">
					<label>${post.postedDate}</label>
				</c:if>
				<label>by ${post.lastUpdateUser}</label>
				<a href="/author/deletePost.html?blogID=${blogID}&postID=${post.postID}">Delete Post</a>
			</div>
		</c:forEach>

	</div>		
</div>
