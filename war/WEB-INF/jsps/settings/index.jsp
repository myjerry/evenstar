<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<h2 class="workflow">Update the basic preferences for your blog!</h2>
 
<div style="clear:both;"></div>

<div class="form">
	<form name="basicSettingsForm" method="POST" >
	
		<input id="actionParam" name="_action" value="updateSettings" type="hidden" />
		<input name="blogID" value="${blog.blogID}" type="hidden" />
		
		<div class="tabs" style="width: 826px;">
			<table cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td class="first">
						<div class="tab on">
							<div>
								<a href="/admin/settings.html?blogID=${blog.blogID}">Basic</a>
							</div>
						</div>
					</td>
					<td>
						<div class="tab">
							<div>
								<a href="/admin/settings/format.html?blogID=${blog.blogID}">Formatting</a>
							</div>
						</div>
					</td>
					<td>
						<div class="tab">
							<div>
								<a href="/admin/settings/comments.html?blogID=${blog.blogID}">Comments</a>
							</div>
						</div>
					</td>
					<td>
						<div class="tab">
							<div>
								<a href="/admin/settings/archives.html?blogID=${blog.blogID}">Archiving</a>
							</div>
						</div>
					</td>
					<td>
						<div class="tab">
							<div>
								<a href="/admin/settings/feed.html?blogID=${blog.blogID}">Site Feed</a>
							</div>
						</div>
					</td>
					<td>
						<div class="tab">
							<div>
								<a href="/admin/settings/email.html?blogID=${blog.blogID}">Email</a>
							</div>
						</div>
					</td>
					<td>
						<div class="tab">
							<div>
								<a href="/admin/settings/openID.html?blogID=${blog.blogID}">OpenID</a>
							</div>
						</div>
					</td>
					<td>
						<div class="tab">
							<div>
								<a href="/admin/settings/security.html?blogID=${blog.blogID}">Permissions</a>
							</div>
						</div>
					</td>
					<td class="last">
						<div class="tab">
							<div>
								<a href="/admin/settings/tools.html?blogID=${blog.blogID}">Tools</a>
							</div>
						</div>
					</td>

					<td class="brdr-right">
						<div>
						</div>
					</td>
				</tr>
			</table>
		</div>
      
		<br />
		
		<div class="contain">
		
			<h2>Basic Settings</h2>
		
			<div class="form-row">
				<label>Title</label>
				<div class="form-row-input">
					<input name="blogTitle" maxlength="100" size="50" value="${blog.title}" />
				</div>
			</div>

			<div class="form-row">
				<label>Description</label>
				<div class="form-row-input">
					<textarea name="description" rows="5" cols="100" class="averageTextArea">${blog.description}</textarea>
				</div>
			</div>
			
			<div class="form-row">
				<label>Blog URL</label>
				<div class="form-row-input">
					<input name="blogAddress" maxlength="100" size="50" value="${blog.address}" />
				</div>
			</div>

			<div class="form-row">
				<label>Blog Alias</label>
				<div class="form-row-input">
					<input name="blogAlias" maxlength="100" size="50" value="${blog.alias}" />
				</div>
			</div>
	
			<div class="form-row">
				<label>Show Quick Editing on your blog?</label>
				<div class="form-row-input">
					<select name="quickEditing">
						<c:if test="${((null eq quickEditing) or (true eq quickEditing))}">
							<option value="true" selected="selected" >Yes</option>
							<option value="false" >No</option>
						</c:if>
						<c:if test="${(false eq quickEditing)}">
							<option value="true" >Yes</option>
							<option value="false" selected="selected" >No</option>
						</c:if>
					</select>
				</div>
			</div>

			<div class="form-row">
				<label>Show Email Post links?</label>
				<div class="form-row-input">
					<select name="emailPostLinks">
						<c:if test="${((null eq emailPostLinks) or (true eq emailPostLinks))}">
							<option value="true" selected="selected" >Yes</option>
							<option value="false" >No</option>
						</c:if>
						<c:if test="${(false eq emailPostLinks)}">
							<option value="true" >Yes</option>
							<option value="false" selected="selected" >No</option>
						</c:if>
					</select>
				</div>
			</div>
			
			<div class="form-row">
				<label>Restricted Post Text</label>
				<div class="form-row-input">
					<textarea name="restrictedPostText" rows="5" cols="100" class="averageTextArea">${blog.restrictedPostText}</textarea>
				</div>
			</div>
	
			<div style="clear: both;"></div>
			
		</div>
	
		<div class="button-bar">
			<div class="region">
				<a href="javascript:document.basicSettingsForm.submit();" class="form-control">Update</a>
				<a href="/admin.html" class="form-control">Cancel</a>
			</div>
		</div>        

	</form>
</div>
