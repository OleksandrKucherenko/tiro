package com.tiro.dao;

import com.tiro.entities.Role;
import com.tiro.exceptions.CoreException;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Set;

/** Common Role operations. */
public class RoleDao extends BasicDao {
  /** Hidden constructor. Available only for inheritors and {@link DaoFactory}. */
  /* package */ RoleDao(@Nonnull final EntityManager em) {
    super(em);
  }

  /** Get list of all roles. */
  @Nonnull
  public Set<Role> getAllRoles() throws CoreException {
    throw CoreException.wrap(new Exception("Not implemented."));
  }

  /** Get role by name. */
  @Nonnull
  public Role getRole(final String name) throws CoreException {
    final CriteriaBuilder builder = mEm.getCriteriaBuilder();
    final CriteriaQuery<Role> criteria = builder.createQuery(Role.class);
    final Root<Role> r = criteria.from(Role.class);
    final Path<String> columnName = r.get(getFieldNameByColumnName(Role.NAME, Role.class));

    final TypedQuery<Role> query = mEm.createQuery(criteria.select(r).where(builder.equal(columnName, name)));
    final Role role = query.getSingleResult();

    if (null == role)
      throw CoreException.wrap(new Exception("Role not found."));

    return role;
  }
}
