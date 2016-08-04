package com.tiro.schema;

/** Column names of the {@link com.tiro.entities.Group} table. */
public interface GroupColumns extends BaseColumns {
  String ID = "group_id";

  String NAME = "group_name";

  String DISABLED = "is_disabled";
}
