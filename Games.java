package com.abnamro.trd.maxwebapp;

import java.util.ArrayList;
import java.util.List;

public class Games {
  private final List<Game> games = new ArrayList<>();

  public void add(final Game game) {
    games.add(game);
  }

  public List<Game> getGames() {
    return games;
  }
}
