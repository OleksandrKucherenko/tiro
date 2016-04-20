package com.tiro.entities;

import com.tiro.schema.GroupColumns;
import com.tiro.schema.RoleColumns;
import com.tiro.schema.Tables;
import com.tiro.schema.UserColumns;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

/** User entity. */
@Entity
@Table(name = Tables.USERS)
public class User implements UserColumns {
  /** Unique identifier, */
  @Id @GeneratedValue
  @Column(name = ID) private long _id;

  /** Timestamp, when instance was created. */
  @Column(name = INSERT_TIME) private long timestampCreation;

  /** User nick name. */
  @Column(name = NICKNAME) private String nickName;

  /** User email address. */
  @Column(name = EMAIL) private String email;

  /** Get the list of roles associated directly with a user. */
  @ManyToMany(cascade = {ALL})
  @JoinTable(name = Tables.USERS_TO_ROLES,
      joinColumns = {@JoinColumn(name = UserColumns.ID)},
      inverseJoinColumns = {@JoinColumn(name = RoleColumns.ID)})
  private Set<Role> roles = new HashSet<>();

  /** Get the list of groups where user included. */
  @ManyToMany
  @JoinTable(name = Tables.GROUPS_TO_USERS,
      joinColumns = {@JoinColumn(name = UserColumns.ID)},
      inverseJoinColumns = {@JoinColumn(name = GroupColumns.ID)})
  private Set<Group> groups = new HashSet<>();
}
