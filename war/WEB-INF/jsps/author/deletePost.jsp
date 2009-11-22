<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<div class="form">
	<form name="deletePostForm" method="POST" action="/author/deletePost.html">
	
		<input id="actionParam" name="_action" value="confirmedDeletePost" type="hidden" />
		<input name="blogID" value="${blogID}" type="hidden" />
		<input name="postID" value="${postID}" type="hidden" />
		
		<div class="contain">
		
			<h1>Are you sure you want to delete this post?</h1>
			
			<hr />
			
			<p>${post.contents}</p>

			<hr />
			
		</div>		

		<div class="button-bar">
			<div class="region">
				<a href="javascript:document.deletePostForm.submit();" class="form-control">Delete Post</a>
				<a href="/author/editPosts.html?blogID=${blogID}" class="form-control">Cancel</a>
			</div>
		</div>        

	</form>
</div>
