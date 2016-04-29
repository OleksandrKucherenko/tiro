package com.tiro.entities;

import com.tiro.schema.BaseColumns;
import com.tiro.schema.DbEntity;

import javax.persistence.Column;
import javax.persistence.Version;

/** Base entity with tracking of version. */
public abstract class BaseEntity implements BaseColumns, DbEntity {

  /**
   * Version of the Entity, on each modification version automatically increased.
   */
  @Version private long version;

  /** Timestamp, when instance was created. */
  @Column(name = C_TIME) protected long timestamp;

  protected BaseEntity() {
    this.timestamp = System.nanoTime();
  }

  public long getVersion() {
    return this.version;
  }

  @Override
  public String toString() {
    return ", version=" + version + ", timestamp=" + timestamp;
  }
}
