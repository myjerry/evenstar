<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
  function submitForm(name) {
    document.getElementById('actionParam').value = name;
    document.templateForm.submit();
  }
</script>

<h1>Layout Settings</h1>

<h3>Backup/Restore Template</h3>
Before editing your template, you may want to save a copy of it. <a href="">Download Template</a>
<br />
Upload a template from a file on your hard drive:

<h3>Edit Template</h3>
Edit the contents of your template <a href="">Learn More</a>

<div class="form">
	<form name="templateForm" method="POST">
	
		<input id="actionParam" name="_action" type="hidden" />
		<input name="blogID" value="${blogID}" type="hidden" />
		
		<div class="contain">
		
			<div class="form-row">
				<div class="form-row-input">
					<textarea rows="11" cols="100" name="templateCode">${templateCode}</textarea>
				</div>
			</div>

		</div>		

		<div class="button-bar">
			<div class="region">
				<a href="javascript:submitForm('clearEdits');" class="form-control">Clear Edits</a>
				<a href="javascript:submitForm('preview');" class="form-control">Preview</a>
				<a href="javascript:submitForm('saveTemplate');" class="form-control">Save Template</a>
			</div>
		</div>        

	</form>
</div>
