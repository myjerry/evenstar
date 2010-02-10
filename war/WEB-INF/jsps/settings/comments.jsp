<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/myjerry.tld" prefix="mj"%>
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
		
			<h2>Control who hinders with your thoughts</h2>
		
			<div class="form-row">
				<label>Comments</label>
				<div class="form-row-input">
					<div class="form-widget-container">
						<mj:core.radio value="true" name="showComments" selectedValue="${showComments}" onDefault="true" /><label>Show</label>
						<br clear="all" />
						<mj:core.radio value="false" name="showComments" selectedValue="${showComments}" /><label>Hide</label>
					</div>
				</div>
			</div>

			<div class="form-row">
				<label>Who Can Comment?</label>
				<div class="form-row-input">
					<div class="form-widget-container">
						<mj:core.radio value="anyone" name="whoCanComment" selectedValue="${whoCanComment}" /><label>Any anonymous user</label>
						<br clear="all" />
						<mj:core.radio value="openid" name="whoCanComment" selectedValue="${whoCanComment}" /><label>Users with Open ID accounts</label>
						<br clear="all" />
						<mj:core.radio value="google" name="whoCanComment" selectedValue="${whoCanComment}" onDefault="true" /><label>Users with Google account</label>
						<br clear="all" />
						<mj:core.radio value="readers" name="whoCanComment" selectedValue="${whoCanComment}" /><label>Members of this blog</label>
					</div>
				</div>
			</div>

			<div class="form-row">
				<label>Comment Form Placement</label>
				<div class="form-row-input">
					<div class="form-widget-container">
						<mj:core.radio value="full" name="commentFormPlacement" selectedValue="${commentFormPlacement}" onDefault="true" /><label>Full Page</label>
						<br clear="all" />
						<mj:core.radio value="popup" name="commentFormPlacement" selectedValue="${commentFormPlacement}" /><label>Pop Up</label>
						<br clear="all" />
						<mj:core.radio value="embedded" name="commentFormPlacement" selectedValue="${commentFormPlacement}" /><label>Embedded below post</label>
					</div>
				</div>
			</div>

			<div class="form-row">
				<label>Comments Default for Posts</label>
				<div class="form-row-input">
					<div class="form-widget-container">
						<mj:core.radio value="true" name="commentsOnNewPosts" selectedValue="${commentsOnNewPosts}" onDefault="true" /><label>New posts have comments</label>
						<br clear="all" />
						<mj:core.radio value="false" name="commentsOnNewPosts" selectedValue="${commentsOnNewPosts}" /><label>New posts do not have comments</label>
					</div>
				</div>
			</div>

			<div class="form-row">
				<label>Comments Timestamp Format</label>
				<div class="form-row-input">
					<select name="commentTimeStampFormat">
						<c:forEach items="${dateHeaderFormats}" var="headerFormat">
							<c:if test="${commentTimeStampFormat == headerFormat.key}">
								<option value="${headerFormat.key}" selected="selected">${headerFormat.value}</option>
							</c:if>
							<c:if test="${commentTimeStampFormat != headerFormat.key}">
								<option value="${headerFormat.key}" >${headerFormat.value}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-row">
				<label>Comment Moderation</label>
				<div class="form-row-input">
					<div class="form-widget-container">
						<mj:core.radio value="always" name="commentModeration" selectedValue="${commentModeration}" /><label>Always</label>
						<br clear="all" />
						<mj:core.radio value="older" name="commentModeration" selectedValue="${commentModeration}" /><label>On posts older than</label><input type="text" size="5" maxlength="3" ></input> days.
						<br clear="all" />
						<mj:core.radio value="never" name="commentModeration" selectedValue="${commentModeration}" onDefault="true" /><label>Never</label>
					</div>
				</div>
			</div>

			<div class="form-row">
				<label>Show Captcha?</label>
				<div class="form-row-input">
					<div class="form-widget-container">
						<mj:core.radio value="true" name="commentCaptcha" selectedValue="${commentCaptcha}" onDefault="true" /><label>Show</label>
						<br clear="all" />
						<mj:core.radio value="false" name="commentCaptcha" selectedValue="${commentCaptcha}" /><label>Hide</label>
					</div>
				</div>
			</div>

			<div class="form-row">
				<label>Comment Notification Email</label>
				<div class="form-row-input">
					<textarea rows="5" cols="50" class="averageTextArea" name="commentNotificationMail">${commentNotificationMail}</textarea>
				</div>
			</div>

			<div class="form-row">
				<label>Backlinks</label>
				<div class="form-row-input">
					<div class="form-widget-container">
						<mj:core.radio value="true" name="showBackLinks" selectedValue="${showBackLinks}" onDefault="true" /><label>Show</label>
						<br clear="all" />
						<mj:core.radio value="false" name="showBackLinks" selectedValue="${showBackLinks}" /><label>Hide</label>
					</div>
				</div>
			</div>

			<div class="form-row">
				<label>Backlinks Default for Posts?</label>
				<div class="form-row-input">
					<div class="form-widget-container">
						<mj:core.radio value="true" name="backLinksOnNewPosts" selectedValue="${backLinksOnNewPosts}" onDefault="true" /><label>New posts have back links</label>
						<br clear="all" />
						<mj:core.radio value="false" name="backLinksOnNewPosts" selectedValue="${backLinksOnNewPosts}" /><label>New posts do not have back links</label>
					</div>
				</div>
			</div>
			
			<div style="clear: both;"></div>

		</div>

		<div class="button-bar">
			<div class="region">
				<a href="javascript:submitForm('update');" class="form-control">Update</a>
				<a href="/admin.html" class="form-control">Cancel</a>
			</div>
		</div>		

	</form>
</div>
