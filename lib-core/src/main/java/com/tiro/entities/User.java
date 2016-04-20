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

/** User entity. */
@Entity
@Table(name = Tables.USERS)
public class User extends BaseEntity implements UserColumns {

  /** Unique identifier, */
  @Id @GeneratedValue
  @Column(name = ID) private long _id;

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

  public User(@NotNull final String email, @NotNull final String name) {
    super();

    this.nickName = name;
    this.email = email;
  }

  @NotNull
  public User addRole(@NotNull final Role role) {
    this.roles.add(role);
    return this;
  }

  @NotNull
  public User addToGroup(@NotNull final Group group) {
    this.groups.add(group);
    return this;
  }

  @Override
  public String toString() {
    return "User{" +
        " _id=" + _id +
        ", nickName='" + nickName + '\'' +
        ", email='" + email + '\'' +
        ", roles=" + roles.size() +
        ", groups=" + groups.size() +
        super.toString() + "}";
  }
}
