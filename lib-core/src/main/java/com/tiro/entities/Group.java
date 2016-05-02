package com.tiro.entities;

import com.tiro.schema.GroupColumns;
import com.tiro.schema.RoleColumns;
import com.tiro.schema.Tables;
import com.tiro.schema.UserColumns;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/** Security Group. */
@Entity
@Table(name = Tables.GROUPS)
public class Group extends BaseEntity implements GroupColumns {
  /** Serialization unique identifier. */
  private static final long serialVersionUID = -8271144716636578493L;

  /** Unique identifier, */
  @Id @GeneratedValue(strategy = IDENTITY)
  @Column(name = ID) public long _id;

  /** Group name. */
  @Column(name = NAME) private String name;

  /** Get a list of roles assigned to this group. */
  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(name = Tables.GROUPS_TO_ROLES,
      joinColumns = {@JoinColumn(name = RoleColumns.ID)},
      inverseJoinColumns = {@JoinColumn(name = GroupColumns.ID)})
  private final Set<Role> roles = new HashSet<>();

  /** Get list of users included into this group. */
  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(name = Tables.GROUPS_TO_USERS,
      joinColumns = {@JoinColumn(name = UserColumns.ID)},
      inverseJoinColumns = {@JoinColumn(name = GroupColumns.ID)})
  private final Set<User> users = new HashSet<>();

  /** private constructor. Required by JPA. */
  @SuppressWarnings({"unused"})
  private Group() {
  }

  public Group(@NotNull final String name) {
    super();

    this.name = name;
  }

  @Override
  public String toString() {
    return "Group {" +
        " _id=" + _id +
        ", name='" + name + '\'' +
        ", roles=" + roles.size() +
        ", users=" + users.size() +
        super.toString() + "}";
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

  @NotNull
  public Set<User> getUsers() {
    return this.users;
  }

  @NotNull
  public Set<Role> getRoles() {
    return this.roles;
  }
}
