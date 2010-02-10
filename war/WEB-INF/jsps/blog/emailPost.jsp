<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
  function submitForm(name) {
    document.getElementById('actionParam').value = name;
    document.emailPostForm.submit();
  }
</script>

<h2 class="workflow">Email post to a friend: ${blog.title}</h2>

<div style="clear:both;"></div>

<div class="form">
	<form name="emailPostForm" method="POST" action="/author/post.html">
	
		<input id="actionParam" name="_action" type="hidden" />
		<input name="blogID" value="${blogID}" type="hidden" />
		<input name="postID" value="${postID}" type="hidden" />
		
		<div class="contain">
		
			<h2>The information you provide here will not be used for anything other than sending the email to your friend.</h2>
		
			<div class="form-row">
				<label>Your Name</label>
				<div class="form-row-input">
					<input name="yourName" maxlength="100" size="50" value="${userName}" />
				</div>
			</div>
	
			<div class="form-row">
				<label>Your Email Address</label>
				<div class="form-row-input">
					<input name="yourName" maxlength="100" size="50" value="${userEmail}" />
				</div>
			</div>

			<div class="form-row">
				<label>Your Friend's Addresses</label>
				<div class="form-row-input">
					<textarea rows="5" cols="50" name="friends" class="averageTextArea">${friends}</textarea>
				</div>
			</div>
	
			<div class="form-row">
				<label>Message</label>
				<div class="form-row-input">
					<textarea rows="5" cols="50" name="friends" class="averageTextArea">${message}</textarea>
				</div>
			</div>

			<div class="form-row">
				<label>Word Verification</label>
				<div class="form-row-input">
					
				</div>
			</div>
			
			<div style="clear:both;"></div>

		</div>		

		<div class="button-bar">
			<div class="region">
				<a href="javascript:submitForm('sendMail');" class="form-control">Send Email</a>
				<a href="${postURL}" class="form-control">Cancel</a>
			</div>
		</div>        

	</form>
</div>
