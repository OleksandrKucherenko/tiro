package com.tiro.entities;

import com.tiro.schema.BaseColumns;
import com.tiro.schema.DbEntity;

import javax.persistence.*;
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
  @Column(name = C_TIME) private long createdAt;
  /** Timestamp of the last update operation. Nanos. */
  @Column(name = U_TIME) private long updatedAt;
  /** Timestamp of the delete operation. Nanos. */
  @Column(name = D_TIME) private long deletedAt;

  /* package */ BaseEntity() {
    this.createdAt = System.nanoTime();
  }

  public long getVersion() {
    return this.version;
  }

  protected void setVersion(final long version) {
    this.version = version;
  }

  public long getTimeCreated() {
    return createdAt;
  }

  protected void setTimeCreated(final long timeCreated) {
    this.createdAt = timeCreated;
  }

  public long getTimeUpdated() {
    return updatedAt;
  }

  protected void setTimeUpdated(final long timeUpdated) {
    this.updatedAt = timeUpdated;
  }

  public long getTimeDeleted() {
    return deletedAt;
  }

  protected void setTimeDeleted(final long timeDeleted) {
    this.deletedAt = timeDeleted;
  }

  @Override
  public String toString() {
    return ", i: { version=" + version
        + ", createdAt=" + createdAt
        + ", updatedAt=" + updatedAt
        + ", deletedAt=" + deletedAt
        + " }";
  }

  @PrePersist
  protected void onPrePersist() {
    touch(this);
  }

  @PreUpdate
  protected void onPreUpdate() {
    touch(this);
  }

  @PreRemove
  protected void onPreRemove() {
    delete(this);
  }

  /** refresh the updatedAt of the entity. */
  @NotNull
  public static <T extends BaseEntity> T touch(@NotNull final T entity) {
    entity.setTimeUpdated(System.nanoTime());
    return entity;
  }

  /** refresh the deletedAt of the entity. */
  @NotNull
  public static <T extends BaseEntity> T delete(@NotNull final T entity) {
    entity.setTimeDeleted(System.nanoTime());
    return entity;
  }
}
