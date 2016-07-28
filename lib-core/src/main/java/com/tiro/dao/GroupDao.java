package com.tiro.dao;

import com.tiro.entities.Group;
import com.tiro.entities.User;
import com.tiro.exceptions.CoreException;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import java.util.Set;

/** Common actions that user can do with security pattern. */
@SuppressWarnings({"unused"})
public class GroupDao extends BasicDao {

  /**
   * Hidden constructor. Available only for inheritors and {@link DaoFactory}.
   * Inject entity manager instance. Instead of calling constructor use:
   * <pre>
   *   GroupDao dao = DaoFactory.get(entityManagerInstance, GroupDao.class);
   * </pre>
   */
  /* package */ GroupDao(@Nonnull final EntityManager em) {
    super(em);
  }

  /** Create a clone of the provided group. */
  @Nonnull
  public Group duplicate(@Nonnull final Group group, @Nonnull final String newName) {
    return Group.from(group, newName);
  }

  /** Find group by it id. */
  @Nonnull
  public Group findById(final long groupId) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Find group by it name. */
  @Nonnull
  public Group findByName(@Nonnull final String groupName) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Assign user to group. */
  @Nonnull
  public Group assign(@Nonnull final Group group, @Nonnull final User user) throws CoreException {
    group.addUser(user);

    // TODO: persist the change

    return group;
  }

  /** Assign multiple users to one group. */
  @Nonnull
  public Group assign(@Nonnull final Group group, @Nonnull final User... users) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));

//    return group;
  }

  /** Enable group. */
  @Nonnull
  public Group enable(@Nonnull final Group group) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Disable group. */
  @Nonnull
  public Group disable(@Nonnull final Group group) throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Get list of all disabled groups. */
  @Nonnull
  public Set<Group> findAllDisabled() throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Get list of all enabled groups. */
  @Nonnull
  public Set<Group> findAllEnabled() throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }
}
