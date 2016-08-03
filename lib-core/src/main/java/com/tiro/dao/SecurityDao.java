package com.tiro.dao;

import com.tiro.entities.Group;
import com.tiro.entities.Role;
import com.tiro.entities.User;
import com.tiro.exceptions.CoreException;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;

/** Basic security operations. */
@SuppressWarnings({"unused"})
public class SecurityDao extends BasicDao {
  /** Hidden constructor. Available only for inheritors and {@link DaoFactory}. */
  /* package */ SecurityDao(@Nonnull final EntityManager em) {
    super(em);
  }

  /** Check that user has a specific role. Role and User resolved by ID. */
  public boolean inRole(final long userId, final long roleId) throws CoreException {
    if (isValidUser(userId)) {
      // TODO: implement me
    }

    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Check that user has a specific role. Role resolved by name. */
  public boolean inRole(final long userId, final String roleName) throws CoreException {
    if (isValidUser(userId)) {
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
    if (isValidUser(userId) && isValidGroup(groupId)) {
      // TODO: implement me
    }


    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Check that user has a specific role. Group resolved by name. */
  public boolean inGroup(final long userId, final String groupName) throws CoreException {
    if (isValidUser(userId)) {
      // TODO: implement me
    }


    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Check that user has a specific role. Group resolved by name. */
  public boolean inGroup(@Nonnull final User user, @Nonnull final Group group) throws CoreException {
    if (!isValid(user)) {
      throw CoreException.wrap(new Exception("Not valid user."));
    }

    if (!isValid(group)) {
      throw CoreException.wrap(new Exception("Not valid group."));
    }

    // TODO: implement me
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Check is user entity is valid to continue the processing. */
  public boolean isValid(@Nonnull final User user) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  public boolean isValid(@Nonnull final Group group) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Check is user entity is valid to continue the processing. */
  public boolean isValidUser(final long userId) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Check is group entity is valid to continue the processing. */
  public boolean isValidGroup(final long groupId) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }
}
