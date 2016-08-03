package com.tiro.dao;

import com.tiro.entities.Group;
import com.tiro.entities.User;
import com.tiro.exceptions.CoreException;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
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

  /** Assign user to group. */
  @Nonnull
  public Group assign(@Nonnull final Group group, @Nonnull final User user) throws CoreException {
    group.addUser(user);

    include(group, user);

    return group;
  }

  /** Assign multiple users to one group. */
  @Nonnull
  public Group assign(@Nonnull final Group group, @Nonnull final User... users) throws CoreException {
    for (final User user : users) {
      group.addUser(user);
      include(user);
    }

    include(group);

    return group;
  }

  /** Enable group. */
  @Nonnull
  public Group enable(@Nonnull final Group group) throws CoreException {
    group.setDisabled(false);

    include(group);

    return group;
  }

  /** Disable group. */
  @Nonnull
  public Group disable(@Nonnull final Group group) throws CoreException {
    group.setDisabled(true);

    include(group);

    return group;
  }

  /** Find group by it id. */
  @Nonnull
  public Group findById(final long groupId) throws CoreException {
    final Group group = mEm.find(Group.class, groupId);

    if (null == group)
      throw CoreException.wrap(new Exception("Group not found."));

    return group;
  }

  /** Find group by it name. */
  @Nonnull
  public Group findByName(@Nonnull final String groupName) throws CoreException {
    final Group group = queryFindByName(groupName).getSingleResult();

    if (null == group)
      throw CoreException.wrap(new Exception("Group not found."));

    return group;
  }

  /** Get list of all enabled groups. */
  @Nonnull
  public Set<Group> findAllEnabled() throws CoreException {
    final List<Group> groups = queryFindAllEnabled().getResultList();

    return new HashSet<>(groups);
  }

  /** Get list of all disabled groups. */
  @Nonnull
  public Set<Group> findAllDisabled() throws CoreException {
    final List<Group> groups = queryFindAllDisabled().getResultList();

    return new HashSet<>(groups);
  }

  /** Compose query for finding Group by name column. */
  private TypedQuery<Group> queryFindByName(@Nonnull String groupName) throws CoreException {
    /* SELECT g FROM group g WHERE g.group_name = :name */

    final CriteriaBuilder builder = mEm.getCriteriaBuilder();
    final CriteriaQuery<Group> criteria = builder.createQuery(Group.class);
    final Root<Group> groupTable = criteria.from(Group.class);
    final Path<String> columnName = groupTable.get(getFieldNameByColumnName(Group.NAME, Group.class));

    return mEm.createQuery(criteria.select(groupTable).where(builder.equal(columnName, groupName)));
  }

  /** Compose query for finding all disabled Group's. */
  private TypedQuery<Group> queryFindAllDisabled() throws CoreException {
    /* SELECT g FROM group g WHERE g.is_disabled = true */

    return queryFindAllByDisabled(true);
  }

  /** Compose query for finding all enabled Group's. */
  private TypedQuery<Group> queryFindAllEnabled() throws CoreException {
    /* SELECT g FROM group g WHERE g.is_disabled = false */

    return queryFindAllByDisabled(false);
  }

  private TypedQuery<Group> queryFindAllByDisabled(final boolean disabled) throws CoreException {
    final CriteriaBuilder builder = mEm.getCriteriaBuilder();
    final CriteriaQuery<Group> criteria = builder.createQuery(Group.class);
    final Root<Group> groupTable = criteria.from(Group.class);
    final Path<Boolean> columnName = groupTable.get(getFieldNameByColumnName(Group.DISABLED, Group.class));

    return mEm.createQuery(criteria.select(groupTable).where(builder.equal(columnName, disabled)));
  }
}
