<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<div id="leftWidgetContainer">
	<div id="widget" class="projects">
		<h2><a href="<c:url value='/projects.html' />">Projects</a></h2>
		<ul>
			<li>
				<a href="<c:url value='/projects/jerry.html' />">Jerry</a>
			</li>
			<li>
				<a href="<c:url value='/projects/variations.html' />">Variations</a>
			</li>
		</ul>
	</div>
	<div id="widget">
		<h2><a href="<c:url value='/developers.html' />" >Developers</a></h2>
		<ul>
			<li>
				<a href="<c:url value='/developers/sangupta.html' />">Sandeep</a>
				<br/>
				Becoming Insane
			</li>
			<li>
				<a href="<c:url value='/developers/sunil.html' />">Sunil</a>
				<br/>
				What to say?
			</li>
		</ul>
	</div>
	<div id="widget">
		<h2><a href="<c:url value='/thanks.html' />">Word of Thanks</a></h2>
		<ul>
			<li><a href="<c:url value='http://www.sun.com/' />">Sun Microsystems</a></li>
			<li><a href="<c:url value='http://www.google.com/' />">Google</a></li>
			<li><a href="<c:url value='http://apache.org/' />">Apache Software Foundation</a></li>
			<li><a href="<c:url value='http://www.atlassian.com/' />">Atlassian</a></li>
			<li><a href="<c:url value='http://www.adobe.com/' />">Adobe Systems</a></li>
			<li><a href="<c:url value='http://www.creativedezinesolutions.com' />">Creative Dezine Solutions</a></li>
		</ul>
	</div>
	<div id="widget">
		<h2>Powered by</h2>
		<ul class="imageList">
			<li><a href="<c:url value='http://code.google.com/appengine/' />">Google App Engine</a></li>
			<li><a href="<c:url value='http://www.atlassian.com/software/jira/' />">JIRA</a></li>
			<li><a href="<c:url value='http://www.atlassian.com/software/fisheye/' />">Fisheye</a></li>
			<li><a href="<c:url value='http://www.creativedezinesolutions.com/' />">Creative Dezine</a></li>
		</ul>
	</div>
</div>