package com.tiro.entities;

import com.tiro.schema.RoleColumns;
import com.tiro.schema.Tables;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/** Security Role. */
@Entity
@Table(name = Tables.ROLES)
public class Role extends BaseEntity implements RoleColumns {
  /** Serialization unique identifier. */
  private static final long serialVersionUID = 3032440515228652740L;

  /** Unique identifier, */
  @Id @GeneratedValue(strategy = IDENTITY)
  @Column(name = ID) private long _id;
  /** User friendly name of the Role. */
  @Column(name = NAME) private String name;
  /** Groups that use the role. */
  @ManyToMany(mappedBy = "roles")
  private final Set<Group> groups = new HashSet<>();
  /** Users that uses the role. */
  @ManyToMany(mappedBy = "roles")
  private final Set<User> users = new HashSet<>();


  /** Hidden constructor. Required by JPA. */
  @SuppressWarnings({"unused"})
  private Role() {
  }

  public Role(@NotNull final String name) {
    super();

    this.name = name;
  }

  /* package */ Set<Group> getGroups() {
    return groups;
  }

  /* package */ Set<User> getUsers() {
    return users;
  }

  @Override
  public String toString() {
    return "Role {" +
        " _id=" + _id +
        ", name='" + name + '\'' +
        super.toString() + "}";
  }

  @PreRemove
  @SuppressWarnings({"unused"})
  protected void OnPreRemove() {
    groups.forEach(g -> g.getRoles().remove(this));
    users.forEach(u -> u.getRoles().remove(this));
  }
}
