package com.tiro.entities;

import com.tiro.schema.RoleColumns;
import com.tiro.schema.Tables;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

/** Security Role. */
@Entity
@Table(name = Tables.ROLES)
public class Role extends BaseEntity implements RoleColumns {
  /** Serialization unique identifier. */
  private static final long serialVersionUID = 3032440515228652740L;

  /** Unique identifier, */
  @Id @GeneratedValue(strategy = IDENTITY)
  @Column(name = ID) public long _id;

  /** User friendly name of the Role. */
  @Column(name = NAME) private String name;

  /** Hidden constructor. Required by JPA. */
  @SuppressWarnings({"unused"})
  private Role() {
  }

  public Role(@NotNull final String name) {
    super();

    this.name = name;
  }

  @Override
  public String toString() {
    return "Role {" +
        " _id=" + _id +
        ", name='" + name + '\'' +
        super.toString() + "}";
  }
}
