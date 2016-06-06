package com.tiro.dao;

import com.tiro.entities.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/** Basic implementation of the DB persistence. */
public abstract class BasicDao implements Dao {
  /** Logger for class. */
  protected final static Logger _log = LoggerFactory.getLogger(Dao.class);

  /** Reference on entity manager. */
  protected final EntityManager mEm;
  /** Map that allows contains entities that are a part of persistence context. */
  protected final Map<BaseEntity, Long> mPersistContext = new HashMap<>();
  /** Counter of included, not serialized items. */
  protected final AtomicLong mCounter = new AtomicLong(-1L);

  /* package */ BasicDao(@Nonnull final EntityManager em) {
    mEm = em;
  }

  @Override
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

  @Override
  public long include(@Nonnull final BaseEntity entity) {
    final long identifier = (0 == entity.getId()) ? mCounter.getAndDecrement() : entity.getId();

    mPersistContext.put(entity, identifier);

    return identifier;
  }
}
