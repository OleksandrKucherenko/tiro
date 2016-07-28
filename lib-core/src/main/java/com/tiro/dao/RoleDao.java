package com.tiro.dao;

import com.tiro.entities.Role;
import com.tiro.exceptions.CoreException;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
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
    throw CoreException.wrap(new Exception("Not implemented."));
  }
}
