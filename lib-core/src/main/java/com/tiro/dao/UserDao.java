package com.tiro.dao;

import com.tiro.entities.User;
import com.tiro.exceptions.CoreException;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import java.util.Set;

/** Data Access Object. Login/Authenticate operations. */
@SuppressWarnings({"unused"})
public class UserDao extends BasicDao {

  /**
   * Hidden constructor. Available only for inheritors and {@link DaoFactory}.
   * Inject entity manager instance. Instead of calling constructor use:
   * <pre>
   *   UserDao dao = DaoFactory.get(entityManagerInstance, UserDao.class);
   * </pre>
   */
  /* package */ UserDao(@Nonnull final EntityManager em) {
    super(em);
  }

  /** Propose nick names for user by his email address. */
  @Nonnull
  public String proposeNickname(@Nonnull final String email) {
    final int at = email.indexOf('@');
    final String nickname = email.substring(0, at);

    // TODO: search for unique name in DB

    return nickname;
  }

  /** Create a new instance of the User class, detached. */
  @Nonnull
  public User newUser(@Nonnull final String email) {
    final String proposedNickName = proposeNickname(email);

    return newUser(email, proposedNickName);
  }

  /** Create a new instance of the User class, detached. */
  @Nonnull
  public User newUser(@Nonnull final String email, final String nickName) {
    return new User(email, nickName);
  }

  /** Find User by unique id. */
  @Nonnull
  public User findById(final long userId) throws CoreException {
    final User user = mEm.find(User.class, userId);

    if (null == user)
      throw CoreException.wrap(new Exception("User not found."));

    return user;
  }

  /** Find User by it email address. */
  @Nonnull
  public User findByEmail(final String userEmail) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Find User by it nickname. */
  @Nonnull
  public User findByNickname(final String userNickname) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Is specific user disabled or not. */
  public boolean isDisabled(@Nonnull final User user) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Is specific user disabled or not. */
  public boolean isDisabled(final long userId) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Disable user. */
  @Nonnull
  public User disable(@Nonnull final User user) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Enable user. */
  @Nonnull
  public User enable(@Nonnull final User user) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Get all disabled in system users. */
  @Nonnull
  public Set<User> findAllDisabled() throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Get all enabled in system users. */
  @Nonnull
  public Set<User> findAllEnabled() throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }
}
