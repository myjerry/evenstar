<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
  function submitForm(name) {
    document.getElementById('actionParam').value = name;
    document.evenstarForm.submit();
  }
</script>

<h2 class="workflow">Set your blog's privacy settings!</h2>
 
<div style="clear:both;"></div>

<div class="form">
	<form name="evenstarForm" method="post">

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
			
			<h2>Add/Remove friends/foes from various user lists.</h2>
		
			<div class="form-row">
				<label>Add Blog Authors</label>
				<div class="form-row-input">
					<input name="blogAuthor" maxlength="100" size="50" /> &nbsp; <a href="javascript:submitForm('addAuthors');">Add Authors</a>
				</div>
			</div>

			<div class="form-row">
				<label>Add Blog Readers</label>
				<div class="form-row-input">
					<input name="blogReader" maxlength="100" size="50" /> &nbsp; <a href="javascript:submitForm('addReaders');">Add Readers</a>
				</div>
			</div>
			
			<div class="form-row">
				<label>Add Auto-Approval Commentors</label>
				<div class="form-row-input">
					<input name="autoApprover" maxlength="100" size="50" /> &nbsp; <a href="">Add Approved Commentors</a>
				</div>
			</div>
			
			<div class="form-row">
				<label>Add Banned Commentors</label>
				<div class="form-row-input">
					<input name="autoReject" maxlength="100" size="50" /> &nbsp; <a href="">Add Banned Commentors</a>
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
		
		<c:if test="${not empty authors}">
			<h2>Selected Authors</h2>
			<div id="selectedPop" style="width: 820px;">
				<div class="nonsort-a">
					<table>
						<c:forEach items="${authors}" var="author">
							<tr>
								<td>${author.emailAddress}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>		
		</c:if>

		<c:if test="${not empty readers}">
			<h2>Selected Readers</h2>
			<div id="selectedPop" style="width: 820px;">
				<div class="nonsort-a">
					<table>
						<c:forEach items="${readers}" var="reader">
							<tr>
								<td>${reader.emailAddress}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>		
		</c:if>

		<c:if test="${not empty autoApprovals}">
			<h2>Selected Auto-Approval Commentors</h2>
			<div id="selectedPop" style="width: 820px;">
				<div class="nonsort-a">
					<table>
						<c:forEach items="${autoApprovals}" var="viewer">
							<tr>
								<td>${viewer.emailAddress}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>		
		</c:if>

		<c:if test="${not empty banned}">
			<h2>Selected Banned Commentors</h2>
			<div id="selectedPop" style="width: 820px;">
				<div class="nonsort-a">
					<table>
						<c:forEach items="${banned}" var="viewer">
							<tr>
								<td>${viewer.emailAddress}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</c:if>		
		
	</form>
</div>
