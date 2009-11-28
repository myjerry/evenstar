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
				<label>Allow Blog Feeds</label>
				<div class="form-row-input">
					<input name="numPosts" maxlength="2" size="2" />
				</div>
			</div>

			<div class="form-row">
				<label>Post Feed Redirect URL</label>
				<div class="form-row-input">
					<input name="numPosts" maxlength="2" size="2" />
				</div>
			</div>

			<div class="form-row">
				<label>Post Feed Footer</label>
				<div class="form-row-input">
					<input name="numPosts" maxlength="2" size="2" />
				</div>
			</div>

		</div>
		
	</form>
</div>
