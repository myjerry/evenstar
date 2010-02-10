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
	Your comment has been successfully posted and would be visible in moments (subject to approval). Click <a href="${postURL}">here</a> to go back to the original post.
</c:if>

<c:if test="${(result eq null) or (result eq false)}">

	<c:if test="${result eq false}">
		Unable to post your comment. You may wish to try once again!
	</c:if>
		
	You have chosen to reply to the following comment posted on the post. 
	Your comment would appear as a reply to the previous comment. 
	In case you want to post a normal comment <a href="/postComment.html?postID=${postID}&blogID=${blogID}">click here</a>. 
	
	<br />
	<br />
	<div id="commentWrapper">
		<div id="comment">
			${comment.content}
			<br />
			- commented by ${comment.authorID} on ${comment.timestamp}
		</div>
	</div>
	
	<div class="form">
		<form name="commentForm" method="POST" >
		
			<input id="actionParam" name="_action" type="hidden" />
			<input name="blogID" value="${blogID}" type="hidden" />
			<input name="postID" value="${postID}" type="hidden" />
			<input name="parentID" value="${parentID}" type="hidden" />
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
					<label>Privacy</label>
					<div class="form-row-input">
						<c:if test="${((null eq comment.permissions) or (1 == comment.permissions))}">
							<input type="radio" value="public" name="privacy" checked="checked" disabled="disabled" /><label>Public</label>
							<input type="radio" value="private" name="privacy" disabled="disabled" /><label>Private</label>
						</c:if>
						<c:if test="${(2 == comment.permissions)}">
							<input type="radio" value="public" name="privacy" disabled="disabled" /><label>Public</label>
							<input type="radio" value="private" name="privacy" checked="checked" disabled="disabled" /><label>Private</label>
						</c:if>
					</div>
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
					<a href="javascript:submitForm('postReply');" class="form-control">Post Comment</a>
					<a href="javascript:submitForm('preview');" class="form-control">Preview</a>
				</div>
			</div>        
	
		</form>
	</div>

</c:if>