package com.tiro.dao;

import com.tiro.entities.Group;
import com.tiro.entities.User;
import com.tiro.exceptions.CoreException;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import java.util.List;

/** Common actions that user can do with security pattern. */
@SuppressWarnings({"unused"})
public class GroupDao extends BasicDao {

  /* package */ GroupDao(@Nonnull final EntityManager em) {
    super(em);
  }

  /** Create a clone of the provided group. */
  @Nonnull
  public Group duplicate(@Nonnull final Group group, @Nonnull final String newName) {
    return Group.from(group, newName);
  }

  /** Find group by it id. */
  public Group findGroup(final long groupId) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Find group by it name. */
  public Group findGroup(@Nonnull final String groupName) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Assign user to group. */
  public Group assign(@Nonnull final Group group, @Nonnull final User user) throws CoreException {
    group.addUser(user);

    // TODO: persist the change

    return group;
  }

  /** Assign multiple users to one group. */
  public Group assign(@Nonnull final Group group, @Nonnull final User... users) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));

//    return group;
  }

  /** Enable group. */
  public Group enable(@Nonnull final Group group) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Disable group. */
  public Group disable(@Nonnull final Group group) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Get list of all disabled groups. */
  public List<Group> findAllDisabled() throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Get list of all enabled groups. */
  public List<Group> findAllEnabled() throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }
}
