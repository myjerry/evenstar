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

<h5>Current Default Blog:
	<c:if test="${not empty defaultBlog}">
		${defaultBlog.title}
	</c:if>
	<c:if test="${empty defaultBlog}">
		None
	</c:if>
</h5>

<c:if test="${not empty blogs}">
	<div class="form">
		<form name="defaultBlogForm" method="POST" action="/admin/setDefaultBlog.html">
		
			<input id="actionParam" name="_action" type="hidden" />
			<input id="blogIDParam" name="blogID" type="hidden" />
			
			<div class="contain">
	
				<c:forEach items="${blogs}" var="blog">
					<div class="form-row">
						<label>${blog.title}</label>
						<div class="form-row-input">
							<a href="javascript:submitForm('${blog.blogID}');">Set Default</a>
						</div>
					</div>
				</c:forEach>
			
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
