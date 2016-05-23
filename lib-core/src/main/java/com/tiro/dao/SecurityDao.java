package com.tiro.dao;

import com.tiro.entities.Group;

import javax.annotation.Nonnull;

/** Common actions that user can do with security pattern. */
public class SecurityDao {
  /** Create a clone of the provided group. */
  @Nonnull
  public Group duplicateGroup(@Nonnull final Group group, @Nonnull final String newName) {
    return Group.from(group, newName);
  }
}
