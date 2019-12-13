package com.abnamro.trd.maxwebapp;

import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * All beans.
 */
@Configuration
public class Beans {

  @Bean(name = "USERS")
  public Users users() {
    return new Users(Paths.get("."));
  }

  @Bean(name = "GAMES")
  public Games games() {
    return new Games();
  }
}
