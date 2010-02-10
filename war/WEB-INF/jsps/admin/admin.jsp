<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/myjerry.tld" prefix="mj"%>
<%@ page isELIgnored="false" %>

<h2 class="workflow">Welcome to your thought factory!</h2>
 
<div style="clear:both;"></div>

<div class="form">
	<form name="templateForm" method="POST">
	
		<input id="actionParam" name="_action" type="hidden" />
		<input name="blogID" value="${blogID}" type="hidden" />

		<div class="contain">
			
			<h2>Current thought streams</h2>
			
			<div class="form-row">
				<label>Default Blog</label>
				<div class="form-row-input">
					<c:if test="${not empty defaultBlog}">
						${defaultBlog.title} (<a href="/admin/setDefaultBlog.html">Change</a>)
					</c:if>
					<c:if test="${empty defaultBlog}">
						None (<a href="/admin/setDefaultBlog.html">Set Now</a>)
					</c:if> 
				</div>
			</div>

			<c:if test="${not empty blogs}">
				<c:forEach items="${blogs}" var="blog">
					<div class="separator"></div>
					
					<div class="form-row">
						<label>${blog.title}</label>
						<div class="form-widget-container" style="width: 580px;">
							<span>${blog.numPosts} posts , last published on ${blog.lastPublishedDate}</span>
							<a href="">View Blog</a>
							
							<br clear="all" />
							<br clear="all" />
							
							<div class="sub-container">
								<a href="/author/newPost.html?blogID=${blog.blogID}" >New Post</a>
								<a href="/author/editPosts.html?blogID=${blog.blogID}" >Edit Posts</a>
								<a href="/admin/settings.html?blogID=${blog.blogID}" >Settings</a>
								<a href="/admin/layout.html?blogID=${blog.blogID}" >Layout</a>
							</div>
							
							<c:if test="${(not empty blog.unpublishedComments) and (blog.unpublishedComments > 0)}">
								<br clear="all" />
								
								<div class="sub-container">
									${blog.unpublishedComments} unpublished comments. <a href="/admin/moderateComments.html?blogID=${blog.blogID}">Moderate Now</a>
								</div>
							</c:if>
						</div>
					</div>
					
				</c:forEach>
			</c:if>

			<div style="clear:both;"></div>
		</div>

		<div class="button-bar">
			<div class="region">
				<a href="/admin/createBlog.html" class="form-control orange">Create a blog</a>
			</div>
		</div>        
		
		
	</form>
</div>

<c:if test="${empty blogs}" >
	Come on, setup evenstar now, <a href="/admin/createBlog.html">Create a blog</a>! 
</c:if>
