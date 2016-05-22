package com.tiro.entities;

import com.tiro.schema.DbEntity;
import com.tiro.schema.RoleColumns;
import com.tiro.schema.Tables;
import com.tiro.schema.UserColumns;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** Many-to-Many association between {@link User} and {@link Role}. */
@Entity
@Table(name = Tables.USERS_TO_ROLES)
public class UsersToRoles implements DbEntity {
  /** Serialization unique identifier. */
  private static final long serialVersionUID = -4834374782900910186L;

  @Id @Column(name = UserColumns.ID) private long userId;

  @Id @Column(name = RoleColumns.ID) private long roleId;

  /** Hidden constructor. Required by JPA. */
  @SuppressWarnings({"unused"})
  protected UsersToRoles() {
  }

  @SuppressWarnings({"unused"})
  public UsersToRoles(final long userId, final long roleId) {
    this.userId = userId;
    this.roleId = roleId;
  }

  @Override
  public String toString() {
    return "UsersToRoles {" +
        " userId=" + userId +
        ", roleId=" + roleId +
        '}';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final UsersToRoles that = (UsersToRoles) o;

    if (userId != that.userId) return false;

    return roleId == that.roleId;
  }

  @Override
  public int hashCode() {
    final int result = (int) (userId ^ (userId >>> 32));
    final int hash = 31 * result + (int) (roleId ^ (roleId >>> 32));

    return hash;
  }

  /** Return original hash code for instance. */
  /* package */ int superHashCode() {
    return super.hashCode();
  }
}
