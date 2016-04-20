package com.tiro.schema;

/** Column names for {@link com.tiro.entities.User} table. */
public interface UserColumns extends BaseColumns {
  String ID = "user_id";

  String NICKNAME = "nickName";

  String EMAIL = "email";
}
