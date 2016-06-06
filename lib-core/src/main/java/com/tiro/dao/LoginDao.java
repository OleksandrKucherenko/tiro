package com.tiro.dao;

import com.tiro.entities.BaseEntity;
import com.tiro.entities.User;
import com.tiro.exceptions.CoreException;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/** Data Access Object. Login/Authenticate operations. */
@SuppressWarnings({"unused"})
public class LoginDao implements Dao {
  /** Reference on entity manager. */
  private final EntityManager mEm;
  /** Map that allows contains entities that are a part of persistence context. */
  private final Map<BaseEntity, Long> mPersistContext = new HashMap<>();
  /** Counter of included, not serialized items. */
  private final AtomicLong mCounter = new AtomicLong(-1L);

  /** Inject entity manager instance. */
  public LoginDao(@Nonnull final EntityManager em) {
    mEm = em;
  }

  /** Propose nick names for user by his email address. */
  @Nonnull
  public String proposeNickname(@Nonnull final String email) {
    final int at = email.indexOf('@');

    // TODO: search for unique name in DB

    return email.substring(0, at);
  }

  /** Create a new instance of the User class, detached. */
  @Nonnull
  public User newUser(@Nonnull final String email) {
    final String proposedNickName = proposeNickname(email);

    return new User(email, proposedNickName);
  }

  /** Find User by unique id. */
  @Nonnull
  public User findUser(final long id) throws CoreException {
    final User user = mEm.find(User.class, id);

    if (null == user)
      throw CoreException.wrap(new Exception("User not found."));

    return user;
  }

  @Override
  public void forcedPersist() {
    mPersistContext.forEach((entity, identifier) -> {
      mEm.persist(entity);

      // update the IDs of items
      mPersistContext.put(entity, entity.getId());
    });

    // reset the counter to initial value
    mCounter.set(-1L);
  }

  @Override
  public long include(@Nonnull final BaseEntity entity) {
    final long identifier = (0 == entity.getId()) ? mCounter.getAndDecrement() : entity.getId();

    mPersistContext.put(entity, identifier);

    return identifier;
  }
}
