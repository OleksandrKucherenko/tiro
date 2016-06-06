package com.tiro.dao;

import com.tiro.entities.BaseEntity;
import com.tiro.entities.Group;
import com.tiro.entities.User;

import javax.annotation.Nonnull;

/** Common actions that user can do with security pattern. */
public class SecurityDao implements Dao {
  /** Create a clone of the provided group. */
  @Nonnull
  public Group duplicateGroup(@Nonnull final Group group, @Nonnull final String newName) {
    return Group.from(group, newName);
  }

  public Group assignUser(@Nonnull final Group group, @Nonnull final User user) {
    group.addUser(user);

    // TODO: persist the change

    return group;
  }

  @Override
  public void forcedPersist() {

  }

  @Override
  public long include(@Nonnull final BaseEntity entity) {
    return 0;
  }
}
