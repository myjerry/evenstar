<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/myjerry.tld" prefix="mj"%>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
  function submitForm(name) {
    document.getElementById('actionParam').value = name;
    document.commentForm.submit();
  }
</script>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script> 

<script type="text/javascript"> 
//<!--
function getCenteredCoords(width, height) {
  return {x: $(window).width()/2-width/2, y: $(window).height()/2-height/2};
}
 
function handleOpenIDResponse(openid_args) {
  $("#login").hide();
  $("#msg").html("<p>Verifying authentication ...</p>");
  $.ajax({
    type: "POST",
    url: "/openidVerify.html",
    data: openid_args,
    dataType: "json",
    success: function(user){
      $("#logout").show();
      $("#msg").html("<p><span class='green'>" + user.b + "</span> is authenticated.</p>")
      var renderDiv = $("#info > div").hide();
      renderDiv.find("span").empty();
      renderDiv.show();
      var info = user.g ? user.g.axschema : {};
      alert(info);
      for(var i in info) {
          alert(i);
        renderDiv.find("span." + i).html(info[i])
      }
    },
    error: function(xhr, errStatus, errMessage) {
      $("#login").show();
      $("#logout").hide();
      $("#msg").html("<p class='red'>Login failed.  Please try again.</p>");
      $("#info > div").hide().find("span").empty();
    }
  });
}
 
$(function(){
  $("#login").click(function(e){
    var w = window.open("/openid.html", "openid_popup", "width=450,height=500,location=1,status=1,resizable=yes");
    var coords = getCenteredCoords(450,500);
    w.moveTo(coords.x, coords.y);
  });
  $("#logout").click(function(e){
    $.ajax({
      type: "GET",
      url: "/openidVerify.html?logout=true",
      success: function(text){
        $("#logout").hide();
        $("#login").show();
        $("#msg").html("<p>You are currently not logged in.</p>");
        $("#info > div").hide().find("span").empty();
      }
    });
  });
  $.ajax({
    type: "GET",
    url: "/openidVerify.html",
    dataType: "json",
    success: function(user){
      $(".init").hide().removeClass("init");
      $("#login").hide();
      $("#logout").show();
      $("#msg").html("<p><span class='green'>" + user.b + "</span> is authenticated.</p>");
      var renderDiv = $("#info > div").hide();
      renderDiv.find("span").empty();
      renderDiv.show();
      var info = user.g ? user.g.info : {};
      for(var i in info)
        renderDiv.find("span." + i).html(info[i])
    },
    error: function(xhr, errStatus, errMessage) {
      $("#logout").hide();
      $("#login").show();
      $("#msg").html("<p>You are currently not logged in.</p>");
      $("#info > div").hide().find("span").empty();
      $(".init").hide().removeClass("init").show(1000);
    }
  });
  
});
//-->
</script> 

<h2 class="workflow">${post.title} (${blog.title})</h2>

<c:if test="${not(result eq true)}">
	<h3>
	We already have ${numComments} comments on the post, you may want to jump to Comment Form
	</h3>
</c:if>

<c:if test="${result eq true}">
	Your comment has been successfully posted and would be visible in moments (subject to approval). Click <a href="${postURL}">here</a> to go back to the original post.
</c:if>

<c:if test="${(result eq null) or (result eq false)}">

	<c:if test="${result eq false}">
		Unable to post your comment. You may wish to try once again!
	</c:if>
	
	<c:if test="${not empty comments}" >
		<div class="form">
			<c:forEach items="${comments}" var="comment">
				<div class="contain" style="min-height: 20px;">
					<h2>${comment.authorID} said on ${comment.timestamp}</h2>

					<p class="instructions">
					${comment.content}
					</p>
					
					<div style="clear:both;"></div>

				</div>

				<div class="button-bar">
					<div class="region">
					<a class="form-control" href="/replyComment.html?commentID=${comment.commentID}&postID=${comment.postID}&blogID=${comment.blogID}">Reply to this Comment</a>
					<a class="form-control" href="/deleteComment.html?commentID=${comment.commentID}&postID=${comment.postID}&blogID=${comment.blogID}">Delete Comment</a>
					</div>
				</div>        

			</c:forEach>
		</div>
	</c:if>
	
	<br /><br />

	<div style="clear:both;"></div>
	
	<div class="form">
		<form name="commentForm" method="POST" >
		
			<input id="actionParam" name="_action" type="hidden" />
			<input name="blogID" value="${blogID}" type="hidden" />
			<input name="postID" value="${postID}" type="hidden" />
			<input name="postURL" value="${post.url}" type="hidden" />
			
			<div class="contain">
			
				<h2>Time to pour your own thoughts</h2>
			
				<div class="form-row">
					<label>Your thoughts</label>
					<div class="form-row-input">
						<textarea rows="5" cols="50" name="thoughts" class="averageTextArea" >${thoughts}</textarea>
					</div>
					
				</div>
				
				<div class="form-row">
					<label>&nbsp;</label>
					<div class="form-row-input">
						You may use some HTML tags &lt;b&gt;, &lt;i&gt;, &lt;a&gt;
					</div>
				</div>
				
				<div class="form-row">
					<label>Privacy</label>
					<div class="form-widget-container">
						<input type="radio" value="public" name="privacy" checked="checked" /><label>Public</label>
						<br clear="all" />
						<input type="radio" value="private" name="privacy" /><label>Private</label>
					</div>
				</div>
				
				<div class="form-row">
					<label>Confirm Identity</label>
					<div class="form-widget-container">
						<mj:core.radio value="google" name="identity" selectedValue="${identity}" /><label>Google Account</label>
						<br clear="all" />
						<mj:core.radio value="openid" name="identity" selectedValue="${identity}" /><label>Open ID</label>
						<br clear="all" />
						<mj:core.radio value="namedurl" name="identity" selectedValue="${identity}" /><label>Name/URL</label>
						<br clear="all" />
						<mj:core.radio value="anonymous" name="identity" selectedValue="${identity}" /><label>Anonymous</label>
						<br clear="all" />
						<input type="button" id="login" value="Sign In"/>
						<input type="button" id="logout" value="Sign Out"/> 

    <div id="msg"></div> 
    <div id="info"> 
      <div class="email">email: <span class="green email"></span></div> 
      <div class="country">country: <span class="green country"></span></div> 
      <div class="language">language: <span class="green language"></span></div> 
    </div> 



					</div>
				</div>
				
				<div class="form-row">
					<label>Verify Humanity</label>
				</div>
				
				<div style="clear: both;"></div>
				
				<c:if test='${moderation != "never"}' >
					<p class="instructions">
						Comment Moderation has been enabled by the author. All comments must be approved by the blog author before they appear on the post.
					</p>
				</c:if>
	
			</div>
			
			<div class="button-bar">
				<div class="region">
					<a href="javascript:submitForm('postComment');" class="form-control">Post Comment</a>
					<a href="javascript:submitForm('preview');" class="form-control">Preview</a>
				</div>
			</div>        
	
		</form>
	</div>

</c:if>