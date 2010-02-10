<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
	function submitForm(name) {
		document.getElementById('actionParam').value = name;
		document.evenstarForm.submit();
	}
</script>

<h2 class="workflow">Delete your blog!</h2>
 
<div style="clear:both;"></div>

<div class="form">
	<form name="evenstarForm" method="POST" >
		
		<input id="actionParam" name="_action" type="hidden" />
		<input name="blogID" value="${blogID}" type="hidden" />
	
		<div class="contain">
		
			<h2>&nbsp;</h2>
	
			Deleted blogs can be restored within 90 days before they are removed forever. You can create another blog at this address using the Google Account you're currently logged in with.
			
			<br />
			<br />
			
			Before you delete your blog, you may want to <a href="/admin/tools/exportBlog.html?blogID=${blogID}">export</a> it.
			
			<div style="clear:both;"></div>
		</div>
		
		<div class="button-bar">
			<div class="region">
				<a href="javascript:submitForm('confirmDeleteBlog');" class="form-control orange">DELETE</a>
				<a href="/admin.html" class="form-control">Cancel</a>
			</div>
		</div>
		        
	</form>
</div>	