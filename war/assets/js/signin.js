function getCenteredCoords(width, height) {
  return {x: $(window).width()/2-width/2, y: $(window).height()/2-height/2};
}
 
function handleOpenIDResponse(openid_args) {
  $.ajax({
    type: "POST",
    url: "/openidVerify.html",
    data: openid_args,
    dataType: "json",
    success: function(user){
	  location.reload();
    },
    error: function(xhr, errStatus, errMessage) {
    	alert('Sign in failed. Please try again!');
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
      }
    });
  });
  $.ajax({
    type: "GET",
    url: "/openidVerify.html",
    dataType: "json",
    success: function(user){
    },
    error: function(xhr, errStatus, errMessage) {
    }
  });
  
});
