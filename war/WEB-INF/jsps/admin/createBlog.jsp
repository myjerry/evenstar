<%@ taglib uri="/WEB-INF/myjerry.tld" prefix="mj"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<h2 class="workflow">Create a New Blog</h2>

<div style="clear:both;"></div>

<c:if test="${not empty validationErrors}" >
	<ul>
		<c:forEach items="${validationErrors}" var="message">
			<li>${message}</li>
		</c:forEach>
	</ul>
</c:if>

<div class="form">
	<form name="createBlogForm" method="POST">
	
		<input id="actionParam" name="_action" value="submit" type="hidden" />
	
		<div class="contain">

			<h2>Owner: <mj:gae.user /> </h2>
					
			<div class="form-row">
				<label>Blog Title</label>
				<div class="form-row-input">
					<input name="blogTitle" maxlength="100" size="100" />
				</div>
			</div>
	
			<div class="form-row">
				<label>Blog URL</label>
				<div class="form-row-input">
					<input name="blogAddress" maxlength="100" size="100" />
				</div>
			</div>

			<div class="form-row">
				<label>Blog Alias</label>
				<div class="form-row-input">
					<input name="blogAlias" maxlength="100" size="100" />
				</div>
			</div>
	
			<div class="form-row">
				<label>Word Verification</label>
				<div class="form-row-input">
					<input name="captcha" maxlength="50" />
				</div>
			</div>
			
			<div style="clear: both;"></div>

		</div>		

		<div class="button-bar">
			<div class="region">
				<a href="javascript:document.createBlogForm.submit();" class="form-control">Continue</a>
			</div>
		</div>        

	</form>
</div>
