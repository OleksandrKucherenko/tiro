package com.tiro.dao;

import com.tiro.entities.BaseEntity;

import javax.annotation.Nonnull;

/**
 * Generic methods used inside each DAO class.
 * <p>
 * References:
 * - http://tutorials.jenkov.com/java-persistence/dao-design-problems.html
 * - http://www.tutorialspoint.com/design_pattern/data_access_object_pattern.htm
 */
public interface Dao {
  /** Force instance of DAO class do persistence of the entities to storage/DB. */
  void forcedPersist();

  /** Attach entity to persistence context. */
  long include(@Nonnull final BaseEntity entity);
}
