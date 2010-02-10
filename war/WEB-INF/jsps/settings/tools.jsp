<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
  function submitForm(name) {
    document.getElementById('actionParam').value = name;
    document.evenstarForm.submit();
  }
</script>

<h2 class="workflow">Touch up your canvas!</h2>
 
<div style="clear:both;"></div>

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
		
			<h2>Quick setup your favorite tools on your blog!</h2>
		
			<div class="form-row">
				<label>Import/Export</label>
				<div class="form-widget-container" style="width: 580px;">
					<div class="sub-container">
						<a href="/admin/tools/importBlog.html?blogID=${blogID}">Import Blog</a>
						|
						<a href="/admin/tools/exportBlog.html?blogID=${blogID}">Export Blog</a>
						|
						<a href="/admin/tools/deleteBlog.html?blogID=${blogID}">Delete Blog</a>
					</div>
					
					<br clear="all" />
					
					You can import posts and comments from a previously exported Blogger blog, export this blog, or permanently delete it.
				</div>
			</div>
						
			<div class="form-row">
				<label>Google Analytics</label>
				<div class="form-row-input">
					<input name="googleAnalytics" maxlength="20" size="50" value="${googleAnalytics}" />
				</div>
			</div>

			<div class="form-row">
				<label>Google Webmaster Tools</label>
				<div class="form-row-input">
					<input name="googleWebmaster" maxlength="20" size="50" value="${googleWebmaster}" />
				</div>
			</div>

			<div class="form-row">
				<label>Yahoo Site Explorer</label>
				<div class="form-row-input">
					<input name="yahooSiteExplorer" maxlength="20" size="50" value="${yahooSiteExplorer}" />
				</div>
			</div>

			<div class="form-row">
				<label>Bing Webmaster Center</label>
				<div class="form-row-input">
					<input name="bingWebmaster" maxlength="20" size="50" value="${bingWebmaster}" />
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
