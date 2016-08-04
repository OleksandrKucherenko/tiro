package com.tiro.entities;

import com.tiro.schema.RoleColumns;
import com.tiro.schema.Tables;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/** Security Role. Design for being a CONSTANT mostly all the time. */
@Entity
@Table(name = Tables.ROLES)
public class Role extends BaseEntity implements RoleColumns {
  /** Serialization unique identifier. */
  private static final long serialVersionUID = 3032440515228652740L;

  /** Unique identifier, */
  @Id @GeneratedValue(strategy = IDENTITY)
  @Column(name = ID) private long _id;
  /** User friendly name of the Role. */
  @Column(name = NAME, unique = true) private String name;
  /** User friendly description of the Role purpose. */
  @Column(name = INFO) private String info = "";
  /** Groups that use the role. For easier many-to-many resolving. */
  @ManyToMany(mappedBy = "roles")
  private final Set<Group> groups = new HashSet<>();
  /** Users that uses the role. For easier many-to-many resolving. */
  @ManyToMany(mappedBy = "roles")
  private final Set<User> users = new HashSet<>();

  /** Hidden constructor. Required by JPA. */
  @SuppressWarnings({"unused"})
  protected Role() {
  }

  @Override
  public long getId() {
    return _id;
  }

  public Role(@Nonnull final String name) {
    super();

    this.name = name;
  }

  public Role(@Nonnull final String name, @Nonnull final String info) {
    this.name = name;
    this.info = info;
  }

  public String getName() {
    return name;
  }

  @Nonnull
  /* package */ Role setName(@Nonnull final String name) {
    this.name = name;
    return this;
  }

  public String getInfo() {
    return info;
  }

  @Nonnull
  /* package */ Role setInfo(@Nonnull final String info) {
    this.info = info;
    return this;
  }

  @Nonnull
  /* package */ Set<Group> getGroups() {
    return groups;
  }

  @Nonnull
  /* package */ Set<User> getUsers() {
    return users;
  }

  @Override
  public String toString() {
    return "Role {" +
        " _id=" + _id +
        ", name='" + name + '\'' +
        ", info='" + info + '\'' +
        super.toString() + "}";
  }

  @PreRemove
  @SuppressWarnings({"unused"})
  protected void onPreRemove() {
    groups.forEach(g -> g.getRoles().remove(this));
    users.forEach(u -> u.getRoles().remove(this));
  }
}
