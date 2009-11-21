<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
  function submitForm(name) {
    document.getElementById('actionParam').value = name;
    document.newPostForm.submit();
  }
</script>

<div class="form">
	<form name="newPostForm" method="POST">
	
		<input id="actionParam" name="_action" value="submit" type="hidden" />
		<input name="blogID" value="${blogID}" type="hidden" />
		
		<div class="contain">
		
			<div class="form-row">
				<label>Post Title</label>
				<div class="form-row-input">
					<input name="postTitle" maxlength="100" size="50" value="${post.title}" />
				</div>
			</div>
	
			<div class="form-row">
				<div class="form-row-input">
					<textarea rows="11" cols="100" name="postContents">${post.contents}</textarea>
				</div>
			</div>

			<div class="form-row">
				<div class="form-row-input">
					<label>Labels</label>
					<input name="labels" maxlength="100" size="50" value="${post.labels}"/>
				</div>
			</div>
	
		</div>		

		<div class="button-bar">
			<div class="region">
				<a href="javascript:submitForm('publishPost');" class="form-control">Publish Post</a>
				<a href="javascript:submitForm('saveAsDraft');" class="form-control">Save Draft</a>
			</div>
		</div>        

	</form>
</div>
