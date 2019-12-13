package com.abnamro.trd.maxwebapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Player class.
 */
public class Player {
  public final User user;
  private final List<Card> hand;
  private final List<Card> score = new ArrayList<>();
  private Card cardOnTable;

  /**
   * Player constructor.
   *
   * @param hand the hand of cards the player will play with.
   * @return the player.
   */
  public Player(final List<Card> hand, final User user) {
    this.user = user;
    this.hand = hand;
  }

  public List<Card> getHand() {
    return hand;
  }

  public List<Card> getScore() {
    return score;
  }

  public Card getCardOnTable() {
    return cardOnTable;
  }

  /**
   * setCardOnTable will place the provided card as the card on table for this player.
   *
   * @param cardOnTable the card that will be 'played' as card on table.
   */
  public void setCardOnTable(final Card cardOnTable) {
    if (this.cardOnTable == null) {
      this.cardOnTable = cardOnTable;
    } else {
      hand.add(this.cardOnTable);
      this.cardOnTable = cardOnTable;
    }
  }

  /**
   * removeCardOnTable will remove the card from cardOnTable.
   *
   */
  public void removeCardOnTable() {
    cardOnTable = null;

  }

  /**
   * addToScore will add the provided card to the score of the player.
   *
   * @param score the card that will be added to the players score.
   */
  public void addToScore(final Card score) {
    this.score.add(score);
  }

  /**
   * playerPlaysCard will remove the card at given location from the players hand.
   *
   * @param hand the players hand.
   * @param location the location of the card that is being played.
   * @return the card that is played.
   */
  public static Card playerPlaysCard(final List<Card> hand, final int location) {
    final Card card = hand.remove(location);
    return card;
  }

  /**
   * realPlayerPlaysCard will remove the card at given location from the players hand from user input.
   *
   * @param reader the scanner required for user input.
   * @return the card that is played.
   */
  public Card realPlayerPlaysCard(final Scanner reader) {

    final int location = getLocation(reader);
    setCardOnTable(getHand().get(location));

    final Card card = getHand().remove(location);
    return card;
  }

  /**
   * getLocation will scan for user input for a location of card in the hand.
   *
   * @param reader the scanner required for user input.
   * @return the location of the card.
   */
  private int getLocation(final Scanner reader) {
    int location = 0;

    System.out.println("Enter the location of your card: ");
    location = reader.nextInt();
    return location;
  }

  public User getUser() {
    return user;
  }
}
