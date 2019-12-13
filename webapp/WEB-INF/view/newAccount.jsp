<!DOCTYPE html>
<html lang="en">
<head>
<link href="./static/css/main.css" rel="stylesheet" type="text/css">
<meta charset="UTF-8">
<title>Create new account</title>
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
<div class="innerNewAccount">
<form action="/createUser" id="createUserForm">
 
    <h1>Sign Up</h1><br><br>
    <p>Please fill in this form to create an account.</p><br><br>

    <label for="username"><b>Username:</b></label><br>
    <input id="createUsername" type="text" placeholder="Enter username" name="username" pattern="[a-zA-Z0-9-]+" required><br><br>

    <label for="psw"><b>Password:</b></label><br>
    <input id="createPassword" type="password" placeholder="Enter Password" name="password" required><br><br>

    <label for="psw-repeat"><b>Repeat Password:</b></label><br>
    <input id="createPassword" type="password" placeholder="Repeat Password" name="passwordRepeat" required><br><br>
        

    <div class="clearfix">
      <button id="signUpButton" type="submit" class="signupbtn">Sign Up</button>
      <input id="cancelCreateUserButton" form="cancelCreateUserForm" type="submit" value="cancel">
    </div>

</form>
<form id="cancelCreateUserForm" action="/login">

</form>
</div>
</div>
</div>
</div>
</body>
</html>