<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<div id="footer">
	<div class="clear" ></div>
	<div id="footerLinks">
		<ul>
			<li class="first"><a href="<c:url value='/aboutUs.html' />">About Us</a></li>
			<li><a href="<c:url value='/arena.html' />">Developer's Arena</a></li>
			<li><a href="<c:url value='/downloads.html' />">Downloads</a></li>
			<li><a href="<c:url value='http://blog.myjerry.org' />">Blog</a></li>
			<li><a href="<c:url value='/documentation.html' />">Documentation</a></li>
			<li><a href="<c:url value='/support.html' />">Support</a></li>
			<li><a href="<c:url value='/resources.html' />">Resources</a></li>
		</ul>
	</div>
	<div id="copyrightNotice">
		&copy; Copyright myjerry 2009, All Rights Reserved.
		&nbsp;
		<a href="<c:url value='/terms.html' />">Terms</a>
		&nbsp;|&nbsp;
		<a href="<c:url value='/privacy.html' />">Privacy Policy</a>
	</div>
</div>
