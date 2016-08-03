package com.tiro.entities;

import com.tiro.schema.GroupColumns;
import com.tiro.schema.RoleColumns;
import com.tiro.schema.Tables;
import com.tiro.schema.UserColumns;

import javax.annotation.Nonnull;
import javax.persistence.*;
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
  @Column(name = ID) private long _id;

  /** Group name. */
  @Column(name = NAME, unique = true) private String name;

  /** Is group disabled or not. */
  @Column(name = DISABLED) private boolean disabled;

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
  protected Group() {
  }

  public Group(@Nonnull final String name) {
    super();

    this.name = name;
  }

  @Nonnull
  public static Group from(@Nonnull final Group group, @Nonnull final String newName) {
    final Group result = new Group(newName);

    group.getRoles().forEach(result::addRole);
    group.getUsers().forEach(result::addUser);

    return result;
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

  @Override
  public long getId() {
    return _id;
  }

  @Nonnull
  public String getName() {
    return name;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disable) {
    disabled = disable;
  }

  @Nonnull
  public Set<User> getUsers() {
    return this.users;
  }

  @Nonnull
  public Set<Role> getRoles() {
    return this.roles;
  }

  @Nonnull
  public Group addRole(@Nonnull final Role role) {
    this.roles.add(role);

    role.getGroups().add(this);

    return this;
  }

  @Nonnull
  public Group addUser(@Nonnull final User user) {
    this.users.add(user);

    // use direct instance add, not addGroup(...)
    user.getGroups().add(this);

    return this;
  }
}
