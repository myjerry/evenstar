<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
  function submitForm(name) {
    document.getElementById('actionParam').value = name;
    document.evenstarForm.submit();
  }
</script>

<div class="form">
	<form name="evenstarForm" method="POST" >
	
		<input id="actionParam" name="_action" type="hidden" />
		<input name="blogID" value="${blogID}" type="hidden" />
		
		<div class="tabs" style="width: 826px;">
			<table cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td class="first">
						<div class="tab on">
							<div>
								<a href="/admin/settings.html?blogID=${blogID}">Basic</a>
							</div>
						</div>
					</td>
					<td>
						<div class="tab">
							<div>
								<a href="/admin/settings/format.html?blogID=${blogID}">Formatting</a>
							</div>
						</div>
					</td>
					<td>
						<div class="tab">
							<div>
								<a href="/admin/settings/comments.html?blogID=${blogID}">Comments</a>
							</div>
						</div>
					</td>
					<td>
						<div class="tab">
							<div>
								<a href="/admin/settings/archives.html?blogID=${blogID}">Archiving</a>
							</div>
						</div>
					</td>
					<td>
						<div class="tab">
							<div>
								<a href="/admin/settings/feed.html?blogID=${blogID}">Site Feed</a>
							</div>
						</div>
					</td>
					<td>
						<div class="tab">
							<div>
								<a href="/admin/settings/email.html?blogID=${blogID}">Email</a>
							</div>
						</div>
					</td>
					<td>
						<div class="tab">
							<div>
								<a href="/admin/settings/openID.html?blogID=${blogID}">OpenID</a>
							</div>
						</div>
					</td>
					<td>
						<div class="tab">
							<div>
								<a href="/admin/settings/security.html?blogID=${blogID}">Permissions</a>
							</div>
						</div>
					</td>
					<td class="last">
						<div class="tab">
							<div>
								<a href="/admin/settings/tools.html?blogID=${blogID}">Tools</a>
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
		
			<h2>Open ID Settings</h2>
		
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
			
			<div style="clear: both;"></div>

		</div>
		
		<div class="button-bar">
			<div class="region">
				<a href="javascript:submitForm('update');" class="form-control orange">Update</a>
				<a href="/admin.html" class="form-control">Cancel</a>
			</div>
		</div>   		

	</form>
</div>
