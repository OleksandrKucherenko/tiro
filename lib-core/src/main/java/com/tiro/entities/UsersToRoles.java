package com.tiro.entities;

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
public class UsersToRoles {
  @Id @Column(name = UserColumns.ID) private long userId;

  @Id @Column(name = RoleColumns.ID) private long roleId;
}
