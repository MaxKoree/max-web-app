<!DOCTYPE html>
<html lang="en">
<head>
<link href="./static/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript" src="./static/JavaScript/main.js"></script>
</head>
<span class="dotBlue"></span>
<span class="dotDarkBlue"></span>
<span class="dotGreen"></span>
<span class="dotOrange"></span>
<span class="dotHalfGreen"></span>
<div class="dotTurquoise"></div>

<body onload="loadLobby()">
<div class="dotBlack"></div>
<span class="dotPurple"></span>
<span class="dotHalfBlue"></span>
<div class="dotPink"></div>

<span class="dotRed"></span>
	<h1 id="lobbyh1">Current player: ${currentUser}</h1>

	<div class="onlinePlayersList"> Online players:
	<ul id="onlinePlayerList"></ul></div> 
	<form action="/logout">
		<input id="logoutButton" type="submit" value="Log out"
			style="margin-top: 20px; margin-left: 20px;">
	<div class="dotYellow"></div>
	</form>
<form id="invitePlayers" action="/invitePlayers" action="areYouInvited">
<button id="invitePlayersButton" type="submit">Invite Players</button>
</form>
</body>
</html>