package com.abnamro.trd.maxwebapp;

import java.time.Instant;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A controller that maps a URL to a JSP.
 */
@Controller
public class JustAJspController {
  private final Users users;
  private final Games games;

  /**
   * The constructor.
   *
   * @param users the users from this application
   * @param games the games from this application
   */
  public JustAJspController(@Qualifier("USERS") final Users users, @Qualifier("GAMES") final Games games) {
    this.users = users;
    this.games = games;
  }

  /**
   * returns login page
   *
   * @param model Spring web operator
   * @param request session
   * @return call JSP login.jsp
   */
  @GetMapping("/")
  public String callJspRoot(final Map<String, Object> model, final HttpServletRequest request) {
    if (!isLoggedIn(request)) {
      return "login"; // call JSP login.jsp
    } else {
      return gotoLobby(model, request);
    }
  }

  /**
   * user can log in
   *
   * @param model Spring web operator
   * @param request session
   * @param username of the user if an account has been created
   * @param password of the user if an account has been created
   * @return
   */
  @RequestMapping("/login")
  public String callJspLogin(final Map<String, Object> model, final HttpServletRequest request, final String username,
      final String password) {

    if (!isLoggedIn(request)) {
      final HttpSession session = request.getSession();

      if (username == null || username.trim().isEmpty()) {
        return "login";
      }

      if (password == null || password.trim().isEmpty()) {
        return "login";
      }

      final User currentUser = users.authenticate(username, password);

      if (currentUser != null) {
        if (currentUser.getSession() != null) {
          currentUser.getSession().removeAttribute("user");
        }
        session.setAttribute("user", currentUser);
        currentUser.setSession(session);
        model.put("currentUser", username);

        final String createOtherUsers = createOtherUsers(currentUser.getName());

        model.put("otherUsers", createOtherUsers);

        currentUser.setLoggedInSince(Instant.now());
        currentUser.setLastAction(Instant.now());
        return gotoLobby(model, request);
      } else {
        model.put("message", "invalid username or password");

        return "login";
      }
    } else {
      if (getCurrentUser(request).getInvitedBy() != null) {
        model.put("invitedBy", getCurrentUser(request).getInvitedBy().getName());
        return "youAreInvited";
      } else {
        return gotoLobby(model, request);
      }
    }
  }

  /**
   * Creates an account for the user and user is added to the lobby
   *
   * @param model Spring web operator
   * @param request session
   * @param username creates a username based on the input of the user
   * @param password creates a password based on the input of the user
   * @param passwordRepeat user has to repeat password
   * @return returns lobby so the user is logged in
   */
  @GetMapping("/createUser")
  public String callJspcreateUser(final Map<String, Object> model, final HttpServletRequest request,
      final String onlineUsers, final String username, final String password, final String passwordRepeat) {

    if (!isLoggedIn(request)) {
      final HttpSession session = request.getSession();

      if (username == null || username.trim().isEmpty()) {
        model.put("message", "please supply a proper name");
        return "newAccount";
      }

      if (!password.equals(passwordRepeat)) {
        model.put("message", "password are not equal");
        return "newAccount";
      }

      final User currentUser;
      try {
        currentUser = users.add(username, password);
      } catch (final IllegalStateException e) {
        return "login";
      }

      if (currentUser != null) {
        return "login";
      } else {
        model.put("message", "something went wrong");
        return "newAccount";
      }
    } else {
      return gotoLobby(model, request);
    }
  }

  /**
   *
   * @param model Spring web operator
   * @param request session
   * @return newAccount
   */
  @GetMapping("/newAccount")
  public String callJspNewAccount(final Map<String, Object> model, final HttpServletRequest request) {
    if (!isLoggedIn(request)) {
      return "newAccount";
    } else {
      return gotoLobby(model, request);
    }
  }

  /**
   *
   * @param model Spring web operator
   * @param request session
   * @return lobby
   */
  @GetMapping("/lobby")
  public String lobby(final Map<String, Object> model, final HttpServletRequest request) {
    if (!isLoggedIn(request)) {
      return "login";
    } else {
      return gotoLobby(model, request);
    }
  }

  /**
   * Removes the user from session so the player will be logged out
   *
   * @param model Spring web operator
   * @param request session
   * @return returns to the login page
   */
  @GetMapping("/logout")
  public String callJspLogOut(final Map<String, Object> model, final HttpServletRequest request) {
    if (isLoggedIn(request)) {
      final HttpSession session = request.getSession();
      final User currentUser = (User) session.getAttribute("user");
      if (currentUser != null) {
        currentUser.setLoggedInSince(null);
        currentUser.setLastAction(null);
        currentUser.setSession(null);
        currentUser.setInvitedBy(null);

        request.getSession().removeAttribute("user");
      }
    }
    return "login";
  }

  /**
   * Creates list with other users
   *
   * @param username
   * @return returns list of other players
   */
  public String createOtherUsers(final String username) {

    String otherUsers = "<ul>";
    for (final User user : users.getUsers()) {
      if (!user.getName().equals(username)) {
        otherUsers += "<li>" + user.getName() + "</li>";
      }
    }
    return otherUsers += "</ul>";
  }

  /**
   * checks if the user is logged in and sets the time of his/her last action
   *
   * @param request session
   *
   * @return
   */
  public static boolean isLoggedIn(final HttpServletRequest request) {
    final HttpSession session = request.getSession();
    final User currentUser = (User) session.getAttribute("user");

    if (currentUser == null) {
      return false;
    }

    currentUser.setLoggedInSince(Instant.now());
    currentUser.setLastAction(Instant.now());

    return true;
  }

  private User getCurrentUser(final HttpServletRequest request) {
    final HttpSession session = request.getSession();
    final User currentUser = (User) session.getAttribute("user");

    return currentUser;
  }

  /**
   * invites the user and returns startGame he/she accepts
   *
   * @param model Spring web operator
   * @param request session
   * @return
   */
  @GetMapping("/invitePlayers")
  public String callJspInvitePlayers(final Map<String, Object> model, final HttpServletRequest request) {
    try {
      if (isLoggedIn(request)) {
        final User currentUser = getCurrentUser(request);
        final User invitedUser = users.getUser(getName(request, "Invite"));
        model.put("invitedUser", invitedUser);
        invitedUser.setInvitedBy(currentUser);

        for (final Game game : games.getGames()) {
          boolean currentPlayer = false;
          boolean invitedPlayer = false;

          for (final Player player : game.getPlayers()) {
            if (player.getUser().equals(currentUser)) {
              currentPlayer = true;
            }
            if (player.getUser().equals(invitedUser)) {
              invitedPlayer = true;
            }
          }
          if (currentPlayer && invitedPlayer) {
            return "startGame";
          }
        }
        return "waitingOnPlayer";
      } else {
        return "login";
      }
    } catch (final RuntimeException e) {
      return "lobby";
    }
  }

  @GetMapping("/areYouInvited")
  public JSONObject callJspSetInvited(final Map<String, Object> model, final HttpServletRequest request) {
    final User currentUser = getCurrentUser(request);
    final String JSONInvites = currentUser.toString();

    final JSONObject invites = new JSONObject();
    invites.put(JSONInvites, true);
    return invites;
  }

  /**
   *
   * Returns the name that belongs to the first found value that matches the provided value in this call.
   *
   * @param request the request
   * @param value the value
   * @return the name that belongs to the first found value that matches the provided value in this call
   */
  public static String getName(final javax.servlet.http.HttpServletRequest request, final String value) {
    for (final Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
      for (final String currentValue : entry.getValue()) {
        if (currentValue.equals(value)) {
          return entry.getKey();
        }
      }
    }
    return null;
  }

  /**
   * the user has accepted the invite to play
   *
   * @param model Spring web operator
   * @param request session
   * @return returns start game
   */
  @GetMapping("/acceptInvite")
  public String callJspacceptInvite(final Map<String, Object> model, final HttpServletRequest request) {
    if (isLoggedIn(request)) {
      final User currentUser = getCurrentUser(request);
      getCurrentUser(request).setInvitedBy(null);
      return "startGame";
    } else {
      return "login";
    }
  }

  /**
   * the user has declined the invite to play
   *
   * @param model Spring web operator
   * @param request session
   * @return returns lobby if player is logged in
   */
  @GetMapping("/declineInvite")
  public String callJspdeclineInvite(final Map<String, Object> model, final HttpServletRequest request) {
    if (isLoggedIn(request)) {
      return gotoLobby(model, request);
    } else {
      return "login";
    }
  }

  /**
   * prepares the lobby
   *
   * @param model Spring web operator
   * @param request session
   * @return
   */
  private String gotoLobby(final Map<String, Object> model, final HttpServletRequest request) {
    final User currentUser = getCurrentUser(request);
    model.put("currentUser", currentUser.getName());

    final String createOtherUsers = createOtherUsers(currentUser.getName());

    model.put("otherUsers", createOtherUsers);

    return "lobby";
  }

}
