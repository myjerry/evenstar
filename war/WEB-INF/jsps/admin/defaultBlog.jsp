<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/myjerry.tld" prefix="mj"%>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
  function submitForm(id) {
    document.getElementById('actionParam').value = 'setDefaultBlog';
    document.getElementById('blogIDParam').value = id;
    document.defaultBlogForm.submit();
  }
</script>

<h2 class="workflow">Set up which thought stream user's get to see as default!</h2>

<div style="clear:both;"></div>

<c:if test="${not empty blogs}">
	<div class="form">
		<form name="defaultBlogForm" method="POST" action="/admin/setDefaultBlog.html">
		
			<input id="actionParam" name="_action" type="hidden" />
			<input id="blogIDParam" name="blogID" type="hidden" />
			
			<div class="contain">

				<h2 class="workflow">
					Current Default Blog:
					<c:if test="${not empty defaultBlog}">
						${defaultBlog.title}
					</c:if>
					<c:if test="${empty defaultBlog}">
						None
					</c:if>
				</h2>
	
				<c:forEach items="${blogs}" var="blog">
					<div class="form-row">
						<label>${blog.title}</label>
						<div class="form-row-input">
							<a href="javascript:submitForm('${blog.blogID}');">Set Default</a>
						</div>
					</div>
				</c:forEach>
				
				<div style="clear: both;"></div>
			
			</div>		
		
			<div class="button-bar">
				<div class="region">
					<a href="javascript:submitForm('');" class="form-control">Clear Default Blog</a>
					<a href="/admin.html" class="form-control">Cancel</a>
				</div>
			</div>        
	
		</form>
	</div>
</c:if>
