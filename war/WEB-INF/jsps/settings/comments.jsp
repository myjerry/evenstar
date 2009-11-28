<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<a href="/admin/settings.html">Basic</a>
<a href="/admin/settings/format.html">Formatting</a>
<a href="/admin/settings/comments.html">Comments</a>
<a href="/admin/settings/archives.html">Archiving</a>
<a href="/admin/settings/feed.html">Site Feed</a>
<a href="/admin/settings/email.html">Email</a>
<a href="/admin/settings/openID.html">OpenID</a>
<a href="/admin/settings/security.html">Permissions</a>
<a href="/admin/settings/tools.html">Tools</a>

<div class="form">
	<form name="basicSettingsForm" method="POST" >
	
		<input id="actionParam" name="_action" type="hidden" />
		<input name="blogID" value="${blogID}" type="hidden" />
		
		<div class="contain">
		
			<div class="form-row">
				<label>Comments</label>
				<div class="form-row-input">
					<input type="radio" value="show" /><label>Show</label>
					<input type="radio" value="hide" /><label>Hide</label>
				</div>
			</div>

			<div class="form-row">
				<label>Who Can Comment?</label>
				<div class="form-row-input">
					<input type="radio" value="show" /><label>Show</label>
					<input type="radio" value="hide" /><label>Hide</label>
				</div>
			</div>

			<div class="form-row">
				<label>Comment Form Placement</label>
				<div class="form-row-input">
					<input type="radio" value="show" /><label>Show</label>
					<input type="radio" value="hide" /><label>Hide</label>
				</div>
			</div>

			<div class="form-row">
				<label>Comments Default for Posts</label>
				<div class="form-row-input">
					<input type="radio" value="show" /><label>Show</label>
					<input type="radio" value="hide" /><label>Hide</label>
				</div>
			</div>

			<div class="form-row">
				<label>Comments Timestamp Format</label>
				<div class="form-row-input">
					<input type="radio" value="show" /><label>Show</label>
					<input type="radio" value="hide" /><label>Hide</label>
				</div>
			</div>

			<div class="form-row">
				<label>Comment Moderation</label>
				<div class="form-row-input">
					<input type="radio" value="show" /><label>Show</label>
					<input type="radio" value="hide" /><label>Hide</label>
				</div>
			</div>

			<div class="form-row">
				<label>Show Captcha?</label>
				<div class="form-row-input">
					<input type="radio" value="show" /><label>Show</label>
					<input type="radio" value="hide" /><label>Hide</label>
				</div>
			</div>

			<div class="form-row">
				<label>Show profile images on Comments?</label>
				<div class="form-row-input">
					<input type="radio" value="show" /><label>Show</label>
					<input type="radio" value="hide" /><label>Hide</label>
				</div>
			</div>

			<div class="form-row">
				<label>Comment Notification Email</label>
				<div class="form-row-input">
					<input type="radio" value="show" /><label>Show</label>
					<input type="radio" value="hide" /><label>Hide</label>
				</div>
			</div>

			<div class="form-row">
				<label>Backlinks</label>
				<div class="form-row-input">
					<input type="radio" value="show" /><label>Show</label>
					<input type="radio" value="hide" /><label>Hide</label>
				</div>
			</div>

			<div class="form-row">
				<label>Backlinks Default for Posts?</label>
				<div class="form-row-input">
					<input type="radio" value="show" /><label>Show</label>
					<input type="radio" value="hide" /><label>Hide</label>
				</div>
			</div>

		</div>
		
	</form>
</div>
