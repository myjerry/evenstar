<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

This is the Basic settings page.

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
	
		<input id="actionParam" name="_action" value="submit" type="hidden" />
		<input name="blogID" value="${blogID}" type="hidden" />
		
		<div class="contain">
		
			<div class="form-row">
				<label>Title</label>
				<div class="form-row-input">
					<input name="blogTitle" maxlength="100" size="50" value="${blog.title}" />
				</div>
			</div>

			<div class="form-row">
				<label>Description</label>
				<div class="form-row-input">
					<textarea name="description" rows="5" cols="50" >${blog.description}</textarea>
				</div>
			</div>
			
			<div class="form-row">
				<label>Show Quick Editing on your blog?</label>
				<div class="form-row-input">
					<select name="quickEditing">
						<option value="true" >Yes</option>
						<option value="false" >No</option>
					</select>
				</div>
			</div>

			<div class="form-row">
				<label>Show Email Post links?</label>
				<div class="form-row-input">
					<select name="emailPostLinks">
						<option value="true" >Yes</option>
						<option value="false" >No</option>
					</select>
				</div>
			</div>
			
		</div>
	
	</form>
</div>
