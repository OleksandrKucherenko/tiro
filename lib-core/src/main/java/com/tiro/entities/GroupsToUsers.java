package com.tiro.entities;

import com.tiro.schema.DbEntity;
import com.tiro.schema.GroupColumns;
import com.tiro.schema.Tables;
import com.tiro.schema.UserColumns;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** Many-to-Many association between {@link Tables#GROUPS} and {@link Tables#USERS}. */
@Entity
@Table(name = Tables.GROUPS_TO_USERS)
public class GroupsToUsers implements DbEntity {
  @Id @Column(name = GroupColumns.ID) private long groupId;

  @Id @Column(name = UserColumns.ID) private long userId;

  private GroupsToUsers() {
  }

  public GroupsToUsers(final long groupId, final long userId) {
    this.groupId = groupId;
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "GroupsToUsers {" +
        " groupId=" + groupId +
        ", userId=" + userId +
        '}';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final GroupsToUsers that = (GroupsToUsers) o;

    if (groupId != that.groupId) return false;

    return userId == that.userId;
  }

  @Override
  public int hashCode() {
    final int result = (int) (groupId ^ (groupId >>> 32));
    final int hash = 31 * result + (int) (userId ^ (userId >>> 32));

    return hash;
  }
}
