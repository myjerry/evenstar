<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
  function submitForm(name) {
    document.getElementById('actionParam').value = name;
    document.templateForm.submit();
  }
</script>

<h2 class="workflow">Paint the canvas, so your thoughts look the way you dream!</h2>
 
<div style="clear:both;"></div>

<div class="form">
	<form name="templateForm" method="POST">
	
		<input id="actionParam" name="_action" type="hidden" />
		<input name="blogID" value="${blogID}" type="hidden" />
		
		<div class="contain">
			
			<h2>Update your blog template</h2>

			<c:if test="${not empty templateCode}">
				<p class="instructions">
					Before editing your template, you may want to save a copy of it. <a href="javascript:submitForm('downloadTemplate');">Download Template</a>
				</p>
			</c:if>
			
			<div class="form-row">
				<label>Restore Template</label>
				<div class="form-widget-container">
					<input type="file" name="templateFile" />
					<a href="" class="form-control upload">Upload</a>
				</div>
			</div>

			<div class="separator" ></div>
			
			<div class="form-row">
				<label>&nbsp;</label>
				<div class="form-row-input">
					<p class="instructions">Edit the contents of your template <a href="">Learn More</a></p>
				</div>
			</div>
		
			<div class="form-row">
				<label>Edit Template</label>
				<div class="form-row-input">
					<textarea rows="11" cols="100" name="templateCode" class="wideTextArea">${templateCode}</textarea>
				</div>
			</div>

		</div>		

		<div class="button-bar">
			<div class="region">
				<a href="javascript:submitForm('saveTemplate');" class="form-control">Save Template</a>
				<a href="javascript:submitForm('preview');" class="form-control">Preview</a>
				<a href="javascript:submitForm('clearEdits');" class="form-control">Clear Edits</a>
				<a href="/admin.html" class="form-control">Cancel</a>
			</div>
		</div>        

	</form>
</div>
