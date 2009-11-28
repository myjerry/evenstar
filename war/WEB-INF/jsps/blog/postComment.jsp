<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
  function submitForm(name) {
    document.getElementById('actionParam').value = name;
    document.commentForm.submit();
  }
</script>

<h3>Post Comment On: ${post.blogTitle}</h3>
<h4>${post.title}</h4>

${post.numComments} comments - <a href="">Show Original Post</a>

<c:if test="${result eq true}">
	Your comment has been successfully posted and would be visible in moments (subject to approval). Click <a href="${postURL}">here</a> to go back to the original post.
</c:if>

<c:if test="${(result eq null) or (result eq false)}">

	<c:if test="${result eq false}">
		Unable to post your comment. You may wish to try once again!
	</c:if>
	
	<c:if test="${not empty comments}" >
		<div id="commentWrapper">
			<c:forEach items="${comments}" var="comment">
				<div id="comment">
					<b>${comment.authorID}</b> said...
					<br />
					${comment.content}
					<br />
					<i>on ${comment.timestamp}</i>
					<br />
					<a href="/replyComment.html?commentID=${comment.commentID}&postID=${comment.postID}&blogID=${comment.blogID}">Reply to this Comment</a>
					<a href="/deleteComment.html?commentID=${comment.commentID}&postID=${comment.postID}&blogID=${comment.blogID}">Delete Comment</a>
				</div>
			</c:forEach>
		</div>
	</c:if>
	
	<br />
	<br />
	
	<div class="form">
		<form name="commentForm" method="POST" >
		
			<input id="actionParam" name="_action" type="hidden" />
			<input name="blogID" value="${blogID}" type="hidden" />
			<input name="postID" value="${postID}" type="hidden" />
			<input name="postURL" value="${post.url}" type="hidden" />
			
			<div class="contain">
			
				<div class="form-row">
					<label>Your thoughts</label>
					<div class="form-row-input">
						<textarea rows="5" cols="50" name="thoughts">${thoughts}</textarea>
					</div>
					<label>You may use some HTML tags &lt;b&gt;, &lt;i&gt;, &lt;a&gt;</label>
				</div>
	
				<div class="form-row">
					<label>Comment Moderation has been enabled by the author. All comments must be approved by the blog author before they appear on the post.</label>
				</div>
	
				<div class="form-row">
					<label>Verify Humanity</label>
				</div>
	
			</div>
			
			<div class="button-bar">
				<div class="region">
					<a href="javascript:submitForm('postComment');" class="form-control">Post Comment</a>
					<a href="javascript:submitForm('preview');" class="form-control">Preview</a>
				</div>
			</div>        
	
		</form>
	</div>

</c:if>