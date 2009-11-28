<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

This is the formatting page.

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
				<label>Show</label>
				<div class="form-row-input">
					<input name="numPosts" maxlength="2" size="2" />
					<select name="postingType">
						<option value="posts">posts</option>
						<option value="days">days</option>
					</select>
				</div>
			</div>

			<div class="form-row">
				<label>Date Header Format</label>
				<div class="form-row-input">
					<select name="dateHeaderType">
					</select>
				</div>
			</div>
			
			<div class="form-row">
				<label>Archive Index Date</label>
				<div class="form-row-input">
					<select name="archiveIndexDate">
					</select>
				</div>
			</div>

			<div class="form-row">
				<label>Timestamp Format</label>
				<div class="form-row-input">
					<select name="archiveIndexDate">
					</select>
				</div>
			</div>

			<div class="form-row">
				<label>Time Zone</label>
				<div class="form-row-input">
					<select name="archiveIndexDate">
					</select>
				</div>
			</div>

			<div class="form-row">
				<label>Convert line breaks</label>
				<div class="form-row-input">
					<select name="archiveIndexDate">
					</select>
				</div>
			</div>

			<div class="form-row">
				<label>Post Template</label>
				<div class="form-row-input">
					<textarea name="postTemplate" rows="5" cols="50"></textarea>
				</div>
			</div>

		</div>
	
	</form>
</div>

