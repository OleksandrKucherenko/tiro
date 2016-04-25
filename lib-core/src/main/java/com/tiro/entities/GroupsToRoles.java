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
}
