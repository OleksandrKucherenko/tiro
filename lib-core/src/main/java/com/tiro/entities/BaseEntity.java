package com.tiro.entities;

import com.tiro.schema.BaseColumns;
import com.tiro.schema.DbEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/** Base entity with tracking of version. */
@MappedSuperclass
@SuppressWarnings({"unused"})
public abstract class BaseEntity implements BaseColumns, DbEntity {
  /** Abstract class serialization ID. */
  private static final long serialVersionUID = -2514165106826894512L;

  /** Version of the Entity, on each modification version automatically increased. */
  @Version
  @Column(name = VERSION) private long version;
  /** Timestamp, when instance was created. Nanos. */
  @Column(name = C_TIME) private long timeCreated;
  /** Timestamp of the last update operation. Nanos. */
  @Column(name = U_TIME) private long timeUpdated;
  /** Timestamp of the delete operation. Nanos. */
  @Column(name = D_TIME) private long timeDeleted;

  /* package */ BaseEntity() {
    this.timeCreated = System.nanoTime();
  }

  public long getVersion() {
    return this.version;
  }

  protected void setVersion(final long version) {
    this.version = version;
  }

  public long getTimeCreated() {
    return timeCreated;
  }

  protected void setTimeCreated(final long timeCreated) {
    this.timeCreated = timeCreated;
  }

  public long getTimeUpdated() {
    return timeUpdated;
  }

  protected void setTimeUpdated(final long timeUpdated) {
    this.timeUpdated = timeUpdated;
  }

  public long getTimeDeleted() {
    return timeDeleted;
  }

  protected void setTimeDeleted(final long timeDeleted) {
    this.timeDeleted = timeDeleted;
  }

  @Override
  public String toString() {
    return ", version=" + version
        + ", timeCreated=" + timeCreated
        + ", timeUpdated=" + timeUpdated
        + ", timeDeleted=" + timeDeleted;
  }

  /** refresh the timeUpdated of the entity. */
  @NotNull
  public static <T extends BaseEntity> T touch(@NotNull final T entity) {
    entity.setTimeUpdated(System.nanoTime());
    return entity;
  }

  /** refresh the timeDeleted of the entity. */
  @NotNull
  public static <T extends BaseEntity> T delete(@NotNull final T entity) {
    entity.setTimeDeleted(System.nanoTime());
    return entity;
  }
}
