package com.tiro.entities;

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
public class GroupsToUsers {
  @Id @Column(name = GroupColumns.ID) private long groupId;

  @Id @Column(name = UserColumns.ID) private long userId;
}
