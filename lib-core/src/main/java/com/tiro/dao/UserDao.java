package com.tiro.dao;

import com.tiro.entities.User;
import com.tiro.exceptions.CoreException;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
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
    final CriteriaBuilder builder = mEm.getCriteriaBuilder();
    final CriteriaQuery<User> criteria = builder.createQuery(User.class);
    final Root<User> u = criteria.from(User.class);
    final Path<String> columnEmail = u.get(User.EMAIL);

    final TypedQuery<User> query = mEm.createQuery(criteria.select(u).where(builder.equal(columnEmail, userEmail)));
    final User user = query.getSingleResult();

    if (null == user)
      throw CoreException.wrap(new Exception("User not found."));

    return user;
  }

  /** Find User by it nickname. */
  @Nonnull
  public User findByNickname(final String userNickname) throws CoreException {
    final User user = queryFindByNickname(userNickname).getSingleResult();

    if (null == user)
      throw CoreException.wrap(new Exception("User not found."));

    return user;
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
    user.setDisabled(true);

    include(user);

    return user;
  }

  /** Enable user. */
  @Nonnull
  public User enable(@Nonnull final User user) throws CoreException {
    user.setDisabled(false);

    include(user);

    return user;
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

  /** Compose query for searching entity by nickname. */
  private TypedQuery<User> queryFindByNickname(String userNickname) throws CoreException {
    /* SELECT u FROM users u WHERE u.nickname = :name */

    final CriteriaBuilder builder = mEm.getCriteriaBuilder();
    final CriteriaQuery<User> criteria = builder.createQuery(User.class);
    final Root<User> u = criteria.from(User.class);
    final Path<String> columnNickname = u.get(getFieldNameByColumnName(User.NICKNAME, User.class));

    return mEm.createQuery(criteria.select(u).where(builder.equal(columnNickname, userNickname)));
  }

  /** Compose query for finding all disabled users. */
  private TypedQuery<User> queryFindAllDisabled() throws CoreException {
    /* SELECT u FROM users u WHERE u.is_disabled = true */

    return queryFindAllByDisabled(true);
  }

  /** Compose query for finding all enabled users. */
  private TypedQuery<User> queryFindAllEnabled() throws CoreException {
    /* SELECT u FROM users u WHERE u.is_disabled = false */

    return queryFindAllByDisabled(false);
  }

  /** Compose query for finding all users with specified 'disabled' column state. */
  private TypedQuery<User> queryFindAllByDisabled(final boolean disabled) throws CoreException {
    final CriteriaBuilder builder = mEm.getCriteriaBuilder();
    final CriteriaQuery<User> criteria = builder.createQuery(User.class);
    final Root<User> u = criteria.from(User.class);
    final Path<String> columnNickname = u.get(getFieldNameByColumnName(User.DISABLED, User.class));

    return mEm.createQuery(criteria.select(u).where(builder.equal(columnNickname, disabled)));
  }
}
