package com.tiro.dao;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;

/**
 * Factory for accessing the instances of Dao.
 * <p>
 * Responsibilities:<br/>
 * - create instance of DAO by class info;<br/>
 * - cache instances of DAO for future reuse;<br/>
 */
@SuppressWarnings({"unchecked", "unused"})
public final class DaoFactory {

  /** Hidden constructor. */
  private DaoFactory() {
    throw new AssertionError("Not designed for instance creation.");
  }

  /** Get instance of the Dao implementation. */
  public static <T extends Dao> T get(@Nonnull final EntityManager em, @Nonnull final Class<T> clazz) {
    if (SecurityDao.class == clazz) {
      return (T) new SecurityDao(em);
    } else if (UserDao.class == clazz) {
      return (T) new UserDao(em);
    } else if (GroupDao.class == clazz) {
      return (T) new GroupDao(em);
    } else if (RoleDao.class == clazz) {
      return (T) new RoleDao(em);
    }

    throw new Error("Not Implemented. Class: " + clazz.getName());
  }
}
