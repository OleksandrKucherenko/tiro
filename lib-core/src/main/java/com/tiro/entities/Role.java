package com.tiro.entities;

import com.tiro.schema.RoleColumns;
import com.tiro.schema.Tables;

import javax.persistence.*;

/** Security Role. */
@Entity
@Table(name = Tables.ROLES)
public class Role implements RoleColumns {
  /** Unique identifier, */
  @Id @GeneratedValue
  @Column(name = ID) private long _id;

  /** Timestamp, when instance was created. */
  @Column(name = INSERT_TIME) private long timestampCreation;

  /** User friendly name of the Role. */
  @Column(name = NAME) private String name;
}
