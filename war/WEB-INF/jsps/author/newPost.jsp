<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
  function submitForm(name) {
    document.getElementById('actionParam').value = name;
    document.newPostForm.submit();
  }

  function privacyModeChange() {
	  var x = document.getElementById("customPrivacySelector");
	  var visi = 'hidden';
	  if(x.checked) {
		  visi = 'visible';
	  }
	  document.getElementById("postReaderContainer").style.visibility = visi;
  }
</script>

<h2 class="workflow">Mould your thoughts, Express yourself!</h2>
 
<div style="clear:both;"></div>

<div class="form">
	<form name="newPostForm" method="POST" action="/author/post.html">
	
		<input id="actionParam" name="_action" type="hidden" />
		<input name="blogID" value="${blogID}" type="hidden" />
		<input name="postID" value="${postID}" type="hidden" />
		
		<div class="contain">
		
			<h2>Create a new post!</h2>
		
			<div class="form-row">
				<label>Post Title</label>
				<div class="form-row-input">
					<input name="postTitle" maxlength="100" size="50" value="${post.title}" />
				</div>
			</div>
	
			<div class="form-row">
				<label>Your Thoughts</label>
				<div class="form-row-input">
					<textarea rows="20" cols="200" name="postContents" class="wideTextArea">${post.contents}</textarea>
				</div>
			</div>

			<div class="form-row">
				<div class="form-row-input">
					<label>Labels</label>
					<input name="labels" maxlength="100" size="50" value="${post.labels}"/>
				</div>
			</div>
	
			<div class="form-row">
				<div class="form-row-input">
					<label>Custom URL</label>
					<input name="customURL" maxlength="100" size="50" value="${post.url}"/>
				</div>
			</div>

			<div class="form-row">
				<div class="form-row-input">
					<label>Privacy</label>
					<div class="form-widget-container">
						<c:if test="${(privacyMode eq null) or (privacyMode == 1)}">
							<input type="radio" value="public" name="privacy" checked="checked" onchange="javascript:privacyModeChange();" /><label>Public</label>
						</c:if>
						<c:if test="${not((privacyMode eq null) or (privacyMode == 1))}">
							<input type="radio" value="public" name="privacy" onchange="javascript:privacyModeChange();" /><label>Public</label>
						</c:if>
						
						<br clear="all" />
						
						<c:if test="${(2 == privacyMode)}">
							<input type="radio" value="protected" name="privacy" checked="checked" onchange="javascript:privacyModeChange();" /><label>Protected (for that special group)</label>
						</c:if>
						<c:if test="${(2 != privacyMode)}">
							<input type="radio" value="protected" name="privacy" onchange="javascript:privacyModeChange();" /><label>Protected (for that special group)</label>
						</c:if>
						
						<br clear="all" />
						
						<c:if test="${(4 == privacyMode)}">
							<input type="radio" value="private" name="privacy" checked="checked" onchange="javascript:privacyModeChange();" /><label>Private (only for blog authors)</label>
						</c:if>
						<c:if test="${(4 != privacyMode)}">
							<input type="radio" value="private" name="privacy" onchange="javascript:privacyModeChange();" /><label>Private (only for blog authors)</label>
						</c:if>
						
						<br clear="all" />
						
						<c:if test="${(8 == privacyMode)}">
							<input id="customPrivacySelector" type="radio" value="custom" name="privacy" checked="checked" onchange="javascript:privacyModeChange();" /><label>Custom (choose specific reader)</label>
						</c:if>
						<c:if test="${(8 != privacyMode)}">
							<input id="customPrivacySelector" type="radio" value="custom" name="privacy" onchange="javascript:privacyModeChange();" /><label>Custom (choose specific reader)</label>
						</c:if>
					</div>
				</div>
			</div>
			
			<c:if test="${(8 == privacyMode)}">
				<c:set var="visibility" value="visible" />
			</c:if>
			<c:if test="${(8 != privacyMode)}">
				<c:set var="visibility" value="hidden" />
			</c:if>

			<div id="postReaderContainer" style="visibility: ${visibility}">
				<div class="form-row">
					<div class="form-row-input">
						<label>Add Post Reader</label>
						<input name="postReader" maxlength="100" size="50" />
					</div>
				</div>
			</div>
			
			<div style="clear: both;"></div>
	
		</div>		

		<div class="button-bar">
			<div class="region">
				<a href="javascript:submitForm('publishPost');" class="form-control">Publish Post</a>
				<a href="javascript:submitForm('saveAsDraft');" class="form-control">Save Draft</a>
				<a href="/admin.html" class="form-control">Cancel</a>
			</div>
		</div>        

	</form>
</div>
