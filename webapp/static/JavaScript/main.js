var checkBoxes = {};
var invitations = {};

function loadLobby() {
	document.getElementById("invitePlayersButton").disabled = true;
	document.getElementById("invitePlayersButton").style.opacity = "0.5";

	$(".invitePlayerCheckbox").hide();

	setLobby();

	playerGetsInvited()
}

function handleCheckboxClick(item) {
	if (document.getElementById(item).checked == true) {
		checkBoxes[item] = true;
		document.getElementById("invitePlayersButton").disabled = false;
		document.getElementById("invitePlayersButton").style.opacity = "1";
	} else {
		checkBoxes[item] = false;
		document.getElementById("invitePlayersButton").disabled = true;
		document.getElementById("invitePlayersButton").style.opacity = "0.5";
	}
}

function setLobby() {
	setInterval(function getJSON() {

		// check for invite



		$.getJSON("v1/createOnlineUsers", function(data) {
			onlineUsers = "";
			$.each(data, function(id, item) {
				if (!(item in checkBoxes)) {
					checkBoxes[item] = false;
				}
				var checkboxValue;
				if (checkBoxes[item]) {
					checkboxValue = "checked"
				} else {
					checkboxValue = "";
				}
				onlineUsers += "<ul id=\"onlinePlayerList\" >" + item
					+ "<label class=\"container\"><input id='"
					+ item
					+ "' type=\"checkbox\" value=\"Invite\" " + checkboxValue + " onclick=\"handleCheckboxClick('" + item + "')\" name='"
					+ item
					+ "' style=\"margin-left: 5px\" form=\"invitePlayers\"><span class=\"checkmark\"></span></ul>";
			});
			$("#onlinePlayerList").html(onlineUsers);
		});
	}, 2000);
}



function playerGetsInvited() {

	$.getJSON("v1/areYouInvited", function(data) {
		$.each(data, function(invitedBy, invitedUser) {

			invitations[invitedBy] = invitedUser;
			//alert(invitedBy, invitedUser);

			//alert(invitations)

		});
	});
}








