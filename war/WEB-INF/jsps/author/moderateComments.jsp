<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
	function submitForm(name, commentID) {
		document.getElementById('actionParam').value = name;
		document.getElementById('commentIDParam').value = commentID;
		document.evenstarForm.submit();
	}
</script>

<h2 class="workflow">Don't let other's spoil your thoughts!</h2>
 
<div style="clear:both;"></div>

<c:if test="${not empty resultString}">
	${resultString}
	
	<br clear="all" />
</c:if>

<c:if test="${not empty comments}" >
	<div class="form">
		<form name="evenstarForm" method="POST">
		
			<input id="actionParam" name="_action" type="hidden" />
			<input id="commentIDParam" name="commentID" type="hidden" />
			<input name="blogID" value="${blogID}" type="hidden" />
	
			<div class="contain">
				
				<h2>Other's thoughts!</h2>

				<c:forEach items="${comments}" var="comment">
					<div class="form-row">
						<label>
							<input type="checkbox" />
							&nbsp;${comment.authorID}
						</label>
						<div class="form-widget-container" style="width: 580px;">
							<span>${comment.content}</span>
							
							<br clear="all" />
							<br clear="all" />
							
							<div class="sub-container">
								<i>dropped on, ${comment.timestamp}</i>
								<a href="javascript:submitForm('publish', '${comment.commentID}:${comment.postID}:${comment.blogID}');" >Publish</a>
								<a href="javascript:submitForm('reject', '${comment.commentID}:${comment.postID}:${comment.blogID}');" >Reject</a>
							</div>
							
						</div>
					</div>
					
					<div class="separator"></div>
				</c:forEach>
				
				<div style="clear:both;"></div>	
			</div>
			
			<div class="button-bar">
				<div class="region">
					<a href="javascript:submitForm('publishSelected', '');" class="form-control">Publish Selected</a>
					<a href="javascript:submitForm('rejectSelected', '');" class="form-control orange">Reject Selected</a>
				</div>
			</div>        
			
			
		</form>
	</div>
</c:if>

<c:if test="${empty comments}" >
	Hooray, no dirt here! 
</c:if>
			