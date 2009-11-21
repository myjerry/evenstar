<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<div id="navigation">
	<div id="navigationLinks">
		<span class="right">
			<ul>
				<li class="first"><a href="<c:url value='/aboutUs.html' />">About Us</a></li>
				<li><a href="<c:url value='/arena.html' />">Developer's Arena</a></li>
				<li><a href="<c:url value='/downloads.html' />">Downloads</a></li>
				<li><a href="<c:url value='http://blog.myjerry.org' />">Blog</a></li>
				<li><a href="<c:url value='/documentation.html' />">Documentation</a></li>
				<li><a href="<c:url value='/support.html' />">Support</a></li>
				<li><a href="<c:url value='/resources.html' />">Resources</a></li>
			</ul>
		</span>
	</div>
	<div id="searchBar">
		<span class="right">
			<%
				String domainName = request.getServerName();
				int port = request.getServerPort();
				String address = null;
				if(port != 80) {
					address = domainName + ":" + port;
				} else {
					address = domainName;
				}
			%>
			<form action=" http://<%=address %>/searchResults.html" id="cse-search-box">
			  <div>
			    <input type="hidden" name="cx" value="003585939550277077326:anh3psiob7o" />
			    <input type="hidden" name="cof" value="FORID:9" />
			    <input type="hidden" name="ie" value="UTF-8" />
			    <input type="text" name="q" size="31" />
			    <input type="submit" name="sa" value="Search" class="searchButton" />
			  </div>
			</form>
			<script type="text/javascript" src="http://www.google.com/cse/brand?form=cse-search-box&lang=en"></script>
		</span>
	</div>
</div>
