<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
	function submitForm(name) {
		document.getElementById('actionParam').value = name;
		document.evenstarForm.submit();
	}
</script>

<h2 class="workflow">Export your blog!</h2>
 
<div style="clear:both;"></div>

<div class="form">
	<form name="evenstarForm" method="POST" >
		
		<input id="actionParam" name="_action" type="hidden" />
		<input name="blogID" value="${blogID}" type="hidden" />
	
		<div class="contain">
		
			<h2>&nbsp;</h2>
	
			Export your blog into the Blogger Atom export format. You can do this to move your blog to another blogging service or simply to store your blog on your own hard drive. Learn more
			
			<br />
			<br />
			
			Don't worry, your blog will still remain on Evenstar until you delete it.
			
			<div style="clear:both;"></div>
		</div>
		
		<div class="button-bar">
			<div class="region">
				<a href="javascript:submitForm('confirmExportBlog');" class="form-control orange">Export Now</a>
				<a href="/admin.html" class="form-control">Cancel</a>
			</div>
		</div>
		        
	</form>
</div>	