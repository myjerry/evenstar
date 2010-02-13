<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/myjerry.tld" prefix="mj"%>
<%@ page isELIgnored="false" %>

<h2 class="workflow">Everyone does need some private space, right!</h2>
 
<div style="clear:both;"></div>

<div class="form">
	<form name="templateForm" method="POST">
	
		<input id="actionParam" name="_action" type="hidden" />
		<input name="blogID" value="${blogID}" type="hidden" />

		<div class="contain">
			
			<h2>User: <mj:gae.user/></h2>
			
			<div class="form-row">
				<label>Name</label>
				<div class="form-row-input">
					<input name="name" maxlength="2" size="100" value="" />
				</div>
			</div>

			<div class="form-row">
				<label>Email address</label>
				<div class="form-row-input">
					<input name="email" maxlength="2" size="100" value="" />
				</div>
			</div>

			<div class="form-row">
				<label>Web URL</label>
				<div class="form-row-input">
					<input name="weburl" maxlength="2" size="100" value="" />
				</div>
			</div>

			<div class="form-row">
				<label>Brief</label>
				<div class="form-row-input">
					<textarea name="brief" rows="5" cols="100" class="averageTextArea">${blog.description}</textarea>
				</div>
			</div>

			<div style="clear:both;"></div>
		</div>

		<div class="button-bar">
			<div class="region">
				<a href="" class="form-control orange">Update</a>
			</div>
		</div>        
		
		
	</form>
</div>
