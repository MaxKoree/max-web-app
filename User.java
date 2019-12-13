package com.abnamro.trd.maxwebapp;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

/**
 * A user.
 */
public class User {
  private final static int MINUTES_TO_WAIT_BEFORE_AUTO_LOGOUT = 30;

  private final String name;
  private Instant loggedInSince;
  private Instant lastAction;
  private User invitedBy;
  private HttpSession session;

  /**
   * The constructor.
   *
   * @param name the name
   */
  public User(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public Instant getLoggedInSince() {
    return loggedInSince;
  }

  public void setLoggedInSince(final Instant loggedInSince) {
    this.loggedInSince = loggedInSince;
  }

  public Instant getLastAction() {
    return lastAction;
  }

  public synchronized void setLastAction(final Instant lastAction) {
    this.lastAction = lastAction;
    if (lastAction != null) {
      final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
      scheduler.schedule(() -> {
        if (lastAction != null
            || Instant.now().compareTo(lastAction.plus(MINUTES_TO_WAIT_BEFORE_AUTO_LOGOUT, ChronoUnit.MINUTES)) > 0) {
          loggedInSince = null;
          this.lastAction = null;
        }
      }, MINUTES_TO_WAIT_BEFORE_AUTO_LOGOUT, TimeUnit.MINUTES);
    }
  }

  public User getInvitedBy() {
    return invitedBy;
  }

  public void setInvitedBy(final User invitedBy) {
    this.invitedBy = invitedBy;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public boolean equals(final Object object) {
    if (object == null || !(object instanceof User)) {
      return false;
    }
    return name.equals(((User) object).getName());
  }

  public HttpSession getSession() {
    return session;
  }

  public void setSession(final HttpSession session) {
    this.session = session;
  }

  @Override
  public String toString() {
    return name;
  }
}
