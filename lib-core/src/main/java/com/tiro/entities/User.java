package com.tiro.entities;

import com.tiro.schema.RoleColumns;
import com.tiro.schema.Tables;
import com.tiro.schema.UserColumns;
import org.hibernate.annotations.SQLDelete;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.persistence.GenerationType.IDENTITY;

/** User entity. */
@Entity
@Table(name = Tables.USERS)
@SQLDelete(sql = "UPDATE " + Tables.USERS +
    " SET " + UserColumns.DISABLED + " = 1" +
    " WHERE " + UserColumns.ID + " = ?" +
    " AND " + UserColumns.NICKNAME + " = ?" +
    " AND " + UserColumns.EMAIL + " = ? ")
public class User extends BaseEntity implements UserColumns {
  /** Serialization unique identifier. */
  private static final long serialVersionUID = -4479053420502713622L;

  /** Unique identifier, */
  @Id @GeneratedValue(strategy = IDENTITY) @Column(name = ID) private long _id;

  /** User nick name. */
  @Column(name = NICKNAME, unique = true) private String nickName;

  /** User email address. */
  @Column(name = EMAIL, unique = true) private String email;

  /** Is user disabled or not. */
  @Column(name = DISABLED) private boolean disabled;

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
  protected User() {
  }

  public User(@Nonnull final String email, @Nonnull final String name) {
    super();

    this.nickName = name;
    this.email = email;
  }

  @Override
  public long getId() {
    return _id;
  }

  public String getNickName() {
    return nickName;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disable) {
    disabled = disable;
  }

  @Override
  public String toString() {
    return "User {" +
        " _id=" + _id +
        ", nickName='" + nickName + '\'' +
        ", email='" + email + '\'' +
        ", roles=" + roles.size() +
        ", groups=" + groups.size() +
        ", disabled=" + disabled +
        super.toString() + "}";
  }

  @Nonnull
  public User addRole(@Nonnull final Role role) {
    this.roles.add(role);

    role.getUsers().add(this);

    return this;
  }

  @Nonnull
  public User addGroup(@Nonnull final Group group) {
    this.groups.add(group);

    group.getUsers().add(this);

    return this;
  }

  @Nonnull
  public Set<Role> getRoles() {
    return this.roles;
  }

  /** Get roles promoted by assigned groups. */
  @Nonnull
  public Set<Role> getGroupsRoles() {
    return getGroups().stream()
        .filter(g -> !g.isDisabled())
        .flatMap(g -> g.getRoles().stream())
        .collect(Collectors.toSet());
  }

  /** Get joined set of user roles and roles promoted by groups. */
  @Nonnull
  public Set<Role> getJoinedRoles() {
    return Stream.concat(getRoles().stream(), getGroupsRoles().stream())
        .collect(Collectors.toSet());
  }

  @Nonnull
  public Set<Group> getGroups() {
    return this.groups;
  }

  @PreRemove
  @SuppressWarnings({"unused"})
  protected void onPreRemove() {
    groups.forEach(g -> g.getUsers().remove(this));
    roles.forEach(r -> r.getUsers().remove(this));
  }
}
