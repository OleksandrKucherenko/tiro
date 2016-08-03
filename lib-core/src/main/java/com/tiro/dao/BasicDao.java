package com.tiro.dao;

import com.tiro.entities.BaseEntity;
import com.tiro.exceptions.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/** Basic implementation of the DB persistence. */
public abstract class BasicDao implements Dao {
  /** Logger for class. */
  protected final static Logger _log = LoggerFactory.getLogger(Dao.class);
  /** Inner reflection cache for fast lookup. */
  private static final Map<String, Map<Class<?>, Field>> sCache = new HashMap<>();

  /** Reference on entity manager. */
  protected final EntityManager mEm;
  /** Map that allows contains entities that are a part of persistence context. */
  protected final Map<BaseEntity, Long> mPersistContext = new HashMap<>();
  /** Counter of included, not serialized items. */
  protected final AtomicLong mCounter = new AtomicLong(-1L);

  /** Hidden constructor, available only for inheritors. */
  /* package */ BasicDao(@Nonnull final EntityManager em) {
    mEm = em;
  }

  @Override
  @SuppressWarnings("try")
  public void forcedPersist() {

    try (final AutoTransaction t = AutoTransaction.from(mEm)) {

      // persist each entity
      mPersistContext.forEach((entity, identifier) -> {
        mEm.persist(entity);

        // update the IDs of items
        mPersistContext.put(entity, entity.getId());
      });

      // confirm that operation is successful
      t.ok();
    } catch (final Throwable ignored) {
      _log.error("Unexpected exception during the persist operation.", ignored);
    }
  }

  /** Include multiple entities in one call. */
  protected void include(@Nonnull final BaseEntity... entities) {
    for (BaseEntity entity : entities) {
      include(entity);
    }
  }

  @Override
  public long include(@Nonnull final BaseEntity entity) {
    final long identifier = (0 == entity.getId()) ? mCounter.getAndDecrement() : entity.getId();

    mPersistContext.put(entity, identifier);

    return identifier;
  }

  /** Find field name by assigned column name annotation. */
  @Nonnull
  protected static String getFieldNameByColumnName(@Nonnull final String columnName, @Nonnull final Class<?> clazz) throws CoreException {
    Map<Class<?>, Field> map = sCache.get(columnName);

    if (null == map) {
      map = new HashMap<>();
    }

    if (map.containsKey(clazz)) {
      return map.get(clazz).getName();
    }

    for (Field field : clazz.getDeclaredFields()) {
      final Column[] columns = field.getAnnotationsByType(Column.class);

      for (Column column : columns) {
        if (columnName.equals(column.name())) {

          map.put(clazz, field);
          sCache.put(columnName, map);

          return field.getName();
        }
      }
    }

    throw CoreException.wrap(new Exception("Field with specified column name not found. Column name: " + columnName));
  }
}
