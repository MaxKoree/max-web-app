<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript" src="./static/JavaScript/main.js"></script>
<link href="./static/css/main.css" rel="stylesheet" type="text/css">
<meta charset="UTF-8">
<title>Login</title>
</head>



<body>
<span class="dotBlue"></span>
<span class="dotDarkBlue"></span>
<span class="dotGreen"></span>
<span class="dotOrange"></span>
<span class="dotHalfGreen"></span>
<div class="dotTurquoise"></div>
<div class="outer">
<div class="middle">
<div class="dotBlack"></div>
<span class="dotPurple"></span>
<span class="dotHalfBlue"></span>
<div class="dotPink"></div>
<div class="dotYellow"></div>
<span class="dotRed"></span>
<div class="inner">
	<form id="loginForm" action="/login" method="post">
	<h1>Login</h1>
		<br><input id="loginInput" placeholder="Username" type="text" name="username" pattern="[a-zA-Z0-9-]+"><br>
		<br><input id="loginInput" type="password" placeholder="Password" name="password" ><br>
		<br> <input id="loginSubmitButton" onclick="submitButtonGotClicked()" type="submit" value="submit"><br>
<input id="createNewAcountButton" type="submit" value="Create new account" form="createAccountForm">

	</form>
		<form id="createAccountForm" action="/newAccount">

	</form>
</div>
</div>
</div>
</body>
</html>
