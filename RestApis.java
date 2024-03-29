package com.abnamro.trd.maxwebapp;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Rest Api class
 *
 */
@RestController
@RequestMapping("/v1")
@Api(value = "restapis", description = "All controllers available for the 104 application.")
public class RestApis {
  private static final String version = "0.0.1";
  List<Card> deck = GameHelper.createDeck();
  List<Player> players = new ArrayList<>();
  ImmutableList<Player> immutableList;
  Game game = null;
  private final Users users;

  public RestApis(@Qualifier("USERS") final Users users) {
    this.users = users;
  }

  private static final String template = "Hello, the version of this application is: %s!";

  /**
   * About service, showing the version of the app.
   *
   */
  @ApiOperation(value = "Details about the 104 application.")

  @ApiResponses(value = {@ApiResponse(code = 200, message = "Succesfully retrieved message.")})
  @RequestMapping(value = "/about", method = GET)
  public static String getVersion() {
    return new String(String.format(template, version));
  }

  /**
   * CreatePlayer, will create a player each call.
   *
   * @throws Exception when exceeding the player limit
   *
   */
  @RequestMapping(value = "/createPlayer", method = GET)
  public Player createPlayer(final User user) throws Exception {
    GameHelper.shuffleDeck(deck);
    if (deck.size() >= 10) {
      final List<Card> hand = GameHelper.createHand(deck);
      final Player player = new Player(hand, user);
      players.add(player);
      return player;
    } else {
      throw new Exception("No cards left for a new player");
    }
  }

  /**
   * GetPlayers will return the list of players
   *
   */
  @RequestMapping(value = "/getPlayers", method = GET)
  public List<Player> getPlayers(@RequestParam(value = "type") final String type) {
    if ("all".equals(type)) {
      return players;
    } else if ("game".equals(type)) {
      return immutableList;
    } else {
      return null;
    }
  }

  /**
   * startGame, will start a game with the currently created players and deck.
   *
   */
  @RequestMapping(value = "/startGame", method = GET)
  public String startGame() {
    immutableList = ImmutableList.copyOf(players);
    game = new Game(immutableList, deck);
    game.getRow0().add(GameHelper.giveCard(deck));
    game.getRow1().add(GameHelper.giveCard(deck));
    game.getRow2().add(GameHelper.giveCard(deck));
    game.getRow3().add(GameHelper.giveCard(deck));
    return "Game has been started!";
  }

  /**
   * setCardForPlayer, will set a cardOnTable for the player.
   *
   * @param id the player id.
   * @param position the position of the card in players hand.
   * @return String with feedback if action succeeded.
   *
   */
  @RequestMapping(value = "/setCardForPlayer", method = POST)
  public String setCardForPlayer(@RequestParam(value = "id") final User user,
      @RequestParam(value = "position") final int position) {
    for (final Player player : game.getPlayers()) {
      if (player.getUser().equals(user)) {
        player.setCardOnTable(Player.playerPlaysCard(player.getHand(), position));
        return "Card is set!";
      }
    }
    return "Card is not set!";
  }

  /**
   * stopGame, will print each playerscore related to the game.
   *
   */
  @RequestMapping(value = "/stopGame", method = GET)
  public String stopGame() {
    String result = "";
    int totalScore = 0;
    for (final Player p : game.getPlayers()) {
      totalScore = 0;
      for (final Card c : p.getScore()) {
        totalScore = totalScore + c.getScore();
      }
      result = result + "Player's score is: " + totalScore + "\n";
    }
    return result;
  }

  /**
   * executeRound, will execute the round of the game.
   *
   */
  @RequestMapping(value = "/executeRound", method = GET)
  public String executeRound(final User user) {
    String result = "";
    while (!GameHelper.getPlayersCardsOnTable(game.getPlayers()).isEmpty()) {
      result = result + "Lowest Card is: "
          + GameHelper.whoHasLowestCard(GameHelper.getPlayersCardsOnTable(game.getPlayers())) + "\n";
      GameHelper.checkPlayerCardAgainstRows(GameHelper.getPlayerWithLowestCardOnTable(game.getPlayers(), user), game);
      result = result + "Row 0 contains: " + game.getRow0() + "\n";
      result = result + "Row 1 contains: " + game.getRow1() + "\n";
      result = result + "Row 2 contains: " + game.getRow2() + "\n";
      result = result + "Row 3 contains: " + game.getRow3() + "\n";
    }
    result = result + "Round is over!" + "\n";
    return result;
  }

  /**
   *
   * @return list of online users
   */
  @RequestMapping(value = "/createOnlineUsers", method = GET)
  public List<String> getOnlineUsers(final HttpServletRequest request) {
    final HttpSession session = request.getSession();
    final User currentUser = (User) session.getAttribute("user");
    final ArrayList<String> onlineUsers = new ArrayList<>();
    for (final User user : users.getUsers()) {
      if (user.getLastAction() != null) {
        if (!user.getName().equals(currentUser.getName())) {
          onlineUsers.add(user.getName());
        }
      }
    }
    return onlineUsers;
  }

  @RequestMapping(value = "/areYouInvited", method = GET)
  public Map<String, String> areYouInvited(final HttpServletRequest request) {
    final Map<String, String> result = new HashMap<>();

    if (JustAJspController.isLoggedIn(request)) {
      final HttpSession session = request.getSession();
      final User currentUser = (User) session.getAttribute("user");

      final User invitedBy = currentUser.getInvitedBy();

      result.put("invitedBy", invitedBy == null ? "" : invitedBy.getName());
    }

    return result;
  }



}
