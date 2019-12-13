<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<link href="./static/css/main.css" rel="stylesheet" type="text/css">
<title>A player invited You</title>
</head>
<span class="dotBlue"></span>
<span class="dotDarkBlue"></span>
<span class="dotGreen"></span>
<span class="dotOrange"></span>
<span class="dotHalfGreen"></span>
<div class="dotTurquoise"></div>
<body>
<div class="outer">
<div class="middle">
<div class="dotBlack"></div>
<span class="dotPurple"></span>
<span class="dotHalfBlue"></span>
<div class="dotPink"></div>
<div class="dotYellow"></div>
<span class="dotRed"></span>
<div class="inner">
<div class="youAreInvitedinner">
<h1 id="youAreInvitedh1" >${invitedBy} has invited you</h1>

<form  action="/acceptInvite">
<input id="acceptInviteButton" type="submit" value="Accept">
<input id="declineInviteButton" type="submit" form="declineInvite" value="Decline">
</form> 
<form id="declineInvite" action="/declineInvite">

</form> 
</div>
</div>
</div>
</div>
</body>
</html>