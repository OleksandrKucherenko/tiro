package com.tiro.dao;

import com.tiro.entities.Group;
import com.tiro.entities.Role;
import com.tiro.entities.User;
import com.tiro.exceptions.CoreException;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import java.util.Set;

/** Basic security operations. */
@SuppressWarnings({"unused"})
public class SecurityDao extends BasicDao {
  /** Hidden constructor. Available only for inheritors and {@link DaoFactory}. */
  /* package */ SecurityDao(@Nonnull final EntityManager em) {
    super(em);
  }

  /** Check that user has a specific role. Role and User resolved by ID. */
  public boolean inRole(final long userId, final long roleId) throws CoreException {
    if (isValid(userId)) {
      // TODO: implement me
    }

    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Check that user has a specific role. Role resolved by name. */
  public boolean inRole(final long userId, final String roleName) throws CoreException {
    if (isValid(userId)) {
      // TODO: implement me
    }

    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Check that user has a specific role. Role resolved by name. */
  public boolean inRole(@Nonnull final User user, @Nonnull final Role role) throws CoreException {
    if (isValid(user)) {
      // TODO: implement me
    }


    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Check that user has a specific role. Group and User resolved by ID. */
  public boolean inGroup(final long userId, final long groupId) throws CoreException {
    if (isValid(userId)) {
      // TODO: implement me
    }


    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Check that user has a specific role. Group resolved by name. */
  public boolean inGroup(final long userId, final String groupName) throws CoreException {
    if (isValid(userId)) {
      // TODO: implement me
    }


    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Check that user has a specific role. Group resolved by name. */
  public boolean inGroup(@Nonnull final User user, @Nonnull final Group group) throws CoreException {
    if (isValid(user)) {
      // TODO: implement me
    }

    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Check is user entity is valid to continue the processing. */
  public boolean isValid(@Nonnull final User user) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Check is user entity is valid to continue the processing. */
  public boolean isValid(final long userId) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }
}
