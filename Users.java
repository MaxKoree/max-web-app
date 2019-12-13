package com.abnamro.trd.maxwebapp;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * The users.
 */
public class Users {
  private final Path usersFile;
  private final Collection<User> users = new ArrayList<>();

  /**
   * Constructor.
   *
   * @param root the root folder
   */
  public Users(final Path root) {
    usersFile = root.resolve("users.txt");

    try {
      if (!usersFile.toFile().exists()) {
        if (!usersFile.toFile().createNewFile()) {
          throw new IllegalStateException(
              String.format("could not create file %s", usersFile.toFile().getAbsoluteFile()));
        }
      }

      try (BufferedReader reader = Files.newBufferedReader(usersFile, UTF_8)) {
        String line;
        while ((line = reader.readLine()) != null) {
          users.add(new User(line.split(":")[0]));
        }
      }
    } catch (final IOException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * Return the user when the name/password combination is valid.
   *
   * @param name the name
   * @param password the password
   * @return the user when the name/password combination is valid otherwise return null
   */
  public synchronized User authenticate(final String name, final String password) {
    final User user = getUser(name);
    if (user != null) {
      try (BufferedReader reader = Files.newBufferedReader(usersFile, UTF_8)) {
        String line;
        while ((line = reader.readLine()) != null) {
          final String currentName = line.split(":")[0];
          final String currentPassword = line.split(":")[1];
          if (currentName.equals(name)) {
            if (currentPassword.equals(password)) {
              return user;
            }
          }
        }
      } catch (final IOException e) {
        throw new IllegalStateException(e);
      }
    }

    return null;
  }

  /**
   * Add a user.
   *
   * @param name the name"
   * @param password the password
   * @return the user
   */
  public synchronized User add(final String name, final String password) {
    for (final User user : users) {
      if (user.getName().equals(name)) {
        throw new IllegalStateException("user already exists");
      }
    }

    try {
      Files.write(usersFile, (name + ':' + password + '\n').getBytes(UTF_8), StandardOpenOption.APPEND);
    } catch (final IOException e) {
      throw new IllegalStateException(e);
    }

    final User user = new User(name);

    users.add(user);

    return user;
  }

  public synchronized Collection<User> getUsers() {
    return Collections.unmodifiableCollection(users);
  }

  public synchronized User getUser(final String name) {
    for (final User user : users) {
      if (user.getName().equals(name)) {
        return user;
      }
    }
    return null;
  }
}
