package com.tiro.entities;

import com.tiro.schema.BaseColumns;

import javax.persistence.Column;
import javax.persistence.Version;

/** Base entity with tracking of version. */
public abstract class BaseEntity implements BaseColumns {
  @Version private long version;

  /** Timestamp, when instance was created. */
  @Column(name = C_TIME) protected long timestampCreation;

  protected BaseEntity() {
    this.timestampCreation = System.nanoTime();
  }

  public long getVersion() {
    return this.version;
  }
}
