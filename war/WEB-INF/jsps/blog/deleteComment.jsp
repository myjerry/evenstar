<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
  function submitForm(name) {
    document.getElementById('actionParam').value = name;
    document.commentForm.submit();
  }
</script>

<h3>${post.title} in ${post.blogTitle}</h3>

<c:if test="${result eq true}">
	Your comment has been successfully deleted. Click <a href="${postURL}">here</a> to go back to the original post.
</c:if>

<c:if test="${(result eq null) or (result eq false)}">

	<c:if test="${result eq false}">
		Unable to delete your comment. You may wish to try once again!
	</c:if>
	
	You have chosen to delete the following comment:
	
	<div class="form">
		<form name="commentForm" method="POST" >
		
			<input id="actionParam" name="_action" type="hidden" />
			<input name="blogID" value="${blogID}" type="hidden" />
			<input name="postID" value="${postID}" type="hidden" />
			<input name="commentID" value="${commentID}" type="hidden" />
			<input name="postURL" value="${post.url}" type="hidden" />
			
			<div class="contain">
			
				<h2>${comment.authorID} on ${comment.timestamp}</h2>
				
				<p class="instructions">
					${comment.content}
				</p>
				
				<div style="clear: both;"></div>
			
			</div>
			
			<div class="button-bar">
				<div class="region">
					<a href="javascript:submitForm('deleteComment');" class="form-control">Delete Comment</a>
					<a href="" class="form-control">Cancel</a>
				</div>
			</div>        
	
		</form>
	</div>
</c:if>