<%@ taglib uri="/WEB-INF/myjerry.tld" prefix="mj"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<h1>Create a New Blog</h1>

<h3>Owner: <mj:gae.user /> </h3>

<c:if test="${not empty validationErrors}" >
	<ul>
		<c:forEach items="${validationErrors}" var="message">
			<li>${message}</li>
		</c:forEach>
	</ul>
</c:if>

<div class="form">
	<form name="createBlogForm" method="POST">
	
		<input name="_action" value="submit" type="hidden" />
		
		<div class="contain">
		
			<div class="form-row">
				<label>Blog Title</label>
				<div class="form-row-input">
					<input name="blogTitle" maxlength="50" />
				</div>
			</div>
	
			<div class="form-row">
				<label>Blog Address</label>
				<div class="form-row-input">
					<input name="blogAddress" maxlength="50" />
				</div>
			</div>
	
			<div class="form-row">
				<label>Word Verification</label>
				<div class="form-row-input">
					<input name="captcha" maxlength="50" />
				</div>
			</div>

		</div>		

		<div class="button-bar">
			<div class="region">
				<a href="javascript:document.createBlogForm.submit();" class="form-control">Continue</a>
			</div>
		</div>        

	</form>
</div>
