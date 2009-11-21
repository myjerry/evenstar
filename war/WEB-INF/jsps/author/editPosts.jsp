<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
  function editPost(postID) {
    document.getElementById('actionParam').value = 'editPost';
    document.getElementById('postIDParam').value = postID;
    document.editPosts.submit();
  }
  function deletePost(postID) {
    document.getElementById('actionParam').value = 'deletePost';
    document.getElementById('postIDParam').value = postID;
    document.editPosts.submit();
  }
</script>

<div class="form">
	<form name="editPosts" method="POST">
	
		<input id="actionParam" name="_action" type="hidden" />
		<input id="postIDParam" name="postID" type="hidden" />
		<input name="blogID" value="${blogID}" type="hidden" />
		
		<div class="contain">
		
			<c:forEach items="${posts}" var="post">
				<div class="form-row">
					<a href="javascript:editPost('${post.postID}');" >Edit Post</a>
					<label>${post.title}</label>
					<c:if test="${empty post.postedDate}">
						<label>draft</label>
						<label>${post.creationDate}</label>
					</c:if>
					<c:if test="${not empty post.postedDate}">
						<label>${post.postedDate}</label>
					</c:if>
					<label>by ${post.lastUpdateUser}</label>
					<a href="javascript:deletePost('${post.postID}');">Delete Post</a>
				</div>
			</c:forEach>
	
		</div>		

	</form>
</div>
