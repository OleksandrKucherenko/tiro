package com.tiro.entities;

import com.tiro.schema.BaseColumns;
import com.tiro.schema.DbEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/** Base entity with tracking of version. */
@MappedSuperclass
@SuppressWarnings({"unused"})
public abstract class BaseEntity implements BaseColumns, DbEntity {
  /** Abstract class serialization ID. */
  private static final long serialVersionUID = -2514165106826894512L;

  /** Version of the Entity, on each modification version automatically increased. */
  @Version
  @Column(name = VERSION) protected long version;

  /** Timestamp, when instance was created. */
  @Column(name = C_TIME) protected long timestamp;

  protected BaseEntity() {
    this.timestamp = System.nanoTime();
  }

  public long getVersion() {
    return this.version;
  }

  protected void setVersion(final long version) {
    this.version = version;
  }

  public long getTimestamp() {
    return timestamp;
  }

  protected void setTimestamp(final long timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return ", version=" + version + ", timestamp=" + timestamp;
  }

  /** Bump timestamp of the entity. */
  public static <T extends BaseEntity> T touch(final T entity) {
    entity.timestamp = System.nanoTime();
    return entity;
  }
}
