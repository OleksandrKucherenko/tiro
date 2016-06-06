package com.tiro.dao;

import com.tiro.entities.BaseEntity;

import javax.annotation.Nonnull;

/**
 * Generic methods used inside each DAO class.
 * <p>
 * Responsibilities:<br/>
 * - hide the concrete implementation of how POJOs stored in DB;<br/>
 * - provide high-level API for CRUD business entities in terms of 'business stories';<br/>
 * <p>
 * References:<br/>
 * - http://tutorials.jenkov.com/java-persistence/dao-design-problems.html<br/>
 * - http://www.tutorialspoint.com/design_pattern/data_access_object_pattern.htm<br/>
 */
public interface Dao {
  /** Force instance of DAO class do persistence of the entities to storage/DB. */
  void forcedPersist();

  /**
   * Attach entity to persistence context.
   *
   * @return unique identifier of entity in scope of DAO instance.
   */
  long include(@Nonnull final BaseEntity entity);
}
