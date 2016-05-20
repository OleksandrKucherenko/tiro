package com.tiro.dao;

import com.tiro.entities.User;

import javax.annotation.Nonnull;

/** Data Access Object. Login/Authenticate operations. */
@SuppressWarnings({"unused"})
public class LoginDao {

  /** Propose nick names for user by his email address. */
  @Nonnull
  public String proposeNickname(@Nonnull final String email) {
    final int at = email.indexOf('@');

    return email.substring(0, at);
  }

  /** Create a new instance of the User class, detached. */
  @Nonnull
  public User newUser(@Nonnull final String email) {
    final String proposedNickName = proposeNickname(email);

    return new User(email, proposedNickName);
  }
}
