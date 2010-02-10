<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<h2 class="workflow">Import your blog!</h2>
 
<div style="clear:both;"></div>

<div class="form">
	<form name="evenstarForm" method="POST" enctype="multipart/form-data">
		
		<input id="actionParam" name="_action" type="hidden" value="confirmImportBlog" />
		<input name="blogID" value="${blogID}" type="hidden" />
	
		<div class="contain">
		
			<h2>&nbsp;</h2>
	
			Import posts and comments from a Blogger export file. Imported posts will be merged with any current posts, sorted by date.
			
			<br />
			<br />
			
			Choose an (previously) exported XML file (.xml)

			<div class="form-row">
				<label>Restore Blog</label>
				<div class="form-widget-container">
					<input type="file" name="blogXMLFile" />
				</div>
			</div>

			<div class="form-row">
				<label>&nbsp;</label>
				<div class="form-row-input">
					<input type="checkbox" name="autoPublish" />Automatically publish all imported posts
				</div>
			</div>

			<div class="form-row">
				<label>Word Verification</label>
				<div class="form-row-input">
					<input name="captcha" maxlength="50" />
				</div>
			</div>
			
			<div style="clear:both;"></div>
		</div>
		
		<div class="button-bar">
			<div class="region">
				<a href="javascript:document.evenstarForm.submit();" class="form-control orange">Import Now</a>
				<a href="/admin.html" class="form-control">Cancel</a>
			</div>
		</div>
		        
	</form>
</div>	
