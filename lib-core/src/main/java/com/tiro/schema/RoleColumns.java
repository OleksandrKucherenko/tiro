package com.tiro.schema;

/** Column names of the {@link com.tiro.entities.Role} table. */
public interface RoleColumns extends BaseColumns {
  /** role unique identifier column name. */
  String ID = "role_id";
  /** role name column name. */
  String NAME = "role_name";
  /** role information column name. English version of user friendly description/information about the role purpose. */
  String INFO = "role_info";
}
