package com.tiro.entities;

import com.tiro.schema.DbEntity;
import com.tiro.schema.GroupColumns;
import com.tiro.schema.RoleColumns;
import com.tiro.schema.Tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** Many-to-Many association between {@link Tables#GROUPS} and {@link Tables#ROLES}. */
@Entity
@Table(name = Tables.GROUPS_TO_ROLES)
public class GroupsToRoles implements DbEntity {
  @Id @Column(name = GroupColumns.ID) private long groupId;

  @Id @Column(name = RoleColumns.ID) private long roleId;

  /** Hidden constructor. */
  private GroupsToRoles() {
  }

  public GroupsToRoles(final long groupId, final long roleId) {
    this.groupId = groupId;
    this.roleId = roleId;
  }

  @Override
  public String toString() {
    return "GroupsToRoles{" +
        " groupId=" + groupId +
        ", roleId=" + roleId +
        '}';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final GroupsToRoles that = (GroupsToRoles) o;

    if (groupId != that.groupId) return false;

    return roleId == that.roleId;
  }

  @Override
  public int hashCode() {
    final int result = (int) (groupId ^ (groupId >>> 32));
    final int hash = 31 * result + (int) (roleId ^ (roleId >>> 32));

    return hash;
  }
}
