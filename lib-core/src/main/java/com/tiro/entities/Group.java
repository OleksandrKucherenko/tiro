package com.tiro.entities;

import com.tiro.schema.GroupColumns;
import com.tiro.schema.RoleColumns;
import com.tiro.schema.Tables;
import com.tiro.schema.UserColumns;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

/** Security Group. */
@Entity
@Table(name = Tables.GROUPS)
public class Group extends BaseEntity implements GroupColumns {
  /** Unique identifier, */
  @Id @GeneratedValue
  @Column(name = ID) private long _id;

  /** Group name. */
  @Column(name = NAME) private String name;

  /** Get a list of roles assigned to this group. */
  @ManyToMany(cascade = {ALL})
  @JoinTable(name = Tables.GROUPS_TO_ROLES,
      joinColumns = {@JoinColumn(name = GroupColumns.ID)},
      inverseJoinColumns = {@JoinColumn(name = RoleColumns.ID)})
  private Set<Role> roles = new HashSet<>();

  /** Get list of users included into this group. */
  @ManyToMany(cascade = {ALL})
  @JoinTable(name = Tables.GROUPS_TO_USERS,
      joinColumns = {@JoinColumn(name = GroupColumns.ID)},
      inverseJoinColumns = {@JoinColumn(name = UserColumns.ID)})
  private Set<User> users = new HashSet<>();

  public Group(@NotNull final String name) {
    super();

    this.name = name;
  }

  @NotNull
  public Group addRole(@NotNull final Role role) {
    this.roles.add(role);
    return this;
  }

  @NotNull
  public Group addUser(@NotNull final User user) {
    this.users.add(user);
    return this;
  }
}
