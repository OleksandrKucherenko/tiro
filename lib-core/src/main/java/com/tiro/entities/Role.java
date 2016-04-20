package com.tiro.entities;

import com.tiro.schema.RoleColumns;
import com.tiro.schema.Tables;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/** Security Role. */
@Entity
@Table(name = Tables.ROLES)
public class Role extends BaseEntity implements RoleColumns {
  /** Unique identifier, */
  @Id @GeneratedValue
  @Column(name = ID) private long _id;

  /** User friendly name of the Role. */
  @Column(name = NAME) private String name;

  public Role(@NotNull final String name) {
    super();

    this.name = name;
  }

  @Override
  public String toString() {
    return "Role{" +
        " _id=" + _id +
        ", name='" + name + '\'' +
        super.toString() + "}";
  }
}
