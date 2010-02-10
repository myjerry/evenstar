<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false" %>

<h2 class="workflow">Take a trip down the memory lane!</h2>
 
<div style="clear:both;"></div>

<div class="form">

	<div class="contain">
		<h2>Total Posts: ${totalPosts}</h2>
		
		<c:if test="${not empty posts}">
			<div id="selectedPop" style="width: 826px;">
				<div class="nonsort-a">
				
					<table width="100%">
						<thead>
							<tr>
								<th>&nbsp;</th>
								<th>Title</th>
								<th>Status</th>
								<th>Date</th>
								<th>Last Updated By</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						
						<c:forEach items="${posts}" var="post">
							<tr>
								<td><a href="/author/post.html?blogID=${blogID}&postID=${post.postID}" >Edit</a></td>
								<td>${post.title}</td>
								<td>
									<c:if test="${empty post.postedDate}">
										<i>draft</i>
									</c:if>
									<c:if test="${not empty post.postedDate}">
										&nbsp;
									</c:if>
								</td>
								<td>
									<c:if test="${empty post.postedDate}">
										<i>${post.creationDate}</i>
									</c:if>
									<c:if test="${not empty post.postedDate}">
										${post.postedDate}
									</c:if>
								</td>
								<td>by ${post.lastUpdateUser}</td>
								<td>
									<a href="/author/deletePost.html?blogID=${blogID}&postID=${post.postID}">Delete</a>
								</td>
								<td>
									<input type="checkbox" name="selectedPosts" />
								</td>
							</tr>
						</c:forEach>
						
					</table>
				
				</div>
			</div>
		</c:if>
		
		
		<div style="clear:both;"></div>

	</div>
	
	<div class="button-bar">
		<div class="region">
			<a href="" class="form-control delete">Remove Selected</a>
			<a href="/admin.html" class="form-control">Cancel</a>
		</div>
	</div>

</div>
