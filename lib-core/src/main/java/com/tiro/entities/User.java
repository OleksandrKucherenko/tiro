package com.tiro.entities;

import com.tiro.schema.RoleColumns;
import com.tiro.schema.Tables;
import com.tiro.schema.UserColumns;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/** User entity. */
@Entity
@Table(name = Tables.USERS)
public class User extends BaseEntity implements UserColumns {
  /** Serialization unique identifier. */
  private static final long serialVersionUID = -4479053420502713622L;

  /** Unique identifier, */
  @Id @GeneratedValue(strategy = IDENTITY)
  @Column(name = ID) private long _id;

  /** User nick name. */
  @Column(name = NICKNAME) private String nickName;

  /** User email address. */
  @Column(name = EMAIL) private String email;

  /** Get the list of roles associated directly with a user. */
  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(name = Tables.USERS_TO_ROLES,
      joinColumns = {@JoinColumn(name = RoleColumns.ID)},
      inverseJoinColumns = {@JoinColumn(name = UserColumns.ID)})
  private final Set<Role> roles = new HashSet<>();

  /** Get the list of groups where user included. */
  @ManyToMany(mappedBy = "users")
  private final Set<Group> groups = new HashSet<>();

  /** Hidden constructor. Required by JPA. */
  @SuppressWarnings({"unused"})
  private User() {
  }

  public User(@NotNull final String email, @NotNull final String name) {
    super();

    this.nickName = name;
    this.email = email;
  }

  @Override
  public String toString() {
    return "User {" +
        " _id=" + _id +
        ", nickName='" + nickName + '\'' +
        ", email='" + email + '\'' +
        ", roles=" + roles.size() +
        ", groups=" + groups.size() +
        super.toString() + "}";
  }

  @NotNull
  public User addRole(@NotNull final Role role) {
    this.roles.add(role);

    role.getUsers().add(this);

    return this;
  }

  @NotNull
  public User addGroup(@NotNull final Group group) {
    this.groups.add(group);

    group.getUsers().add(this);

    return this;
  }

  @NotNull
  public Set<Role> getRoles() {
    return this.roles;
  }

  @NotNull
  public Set<Group> getGroups() {
    return this.groups;
  }

  @PreRemove
  @SuppressWarnings({"unused"})
  protected void OnPreRemove() {
    groups.forEach(g -> g.getUsers().remove(this));
    roles.forEach(r -> r.getUsers().remove(this));
  }
}
