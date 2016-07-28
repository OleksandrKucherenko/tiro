package com.tiro.utilities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.concurrent.atomic.AtomicInteger;

/**  */
public class EntityManagerProvider {

  /** JPA factory. */
  protected static EntityManagerFactory _factory;
  /** Counter of created entity managers and guard for {@link #_factory} field. */
  protected static final AtomicInteger _counter = new AtomicInteger();

  private static void initialize() {
    _factory = Persistence.createEntityManagerFactory("sqlite");
  }

  private static void destroy() {
    if (null != _factory) {
      _factory.close();
      _factory = null;
    }
  }

  public EntityManager provide() {
    if (null == _factory) {
      synchronized (_counter) {
        if (null == _factory) {
          initialize();
        }
      }
    }

    _counter.incrementAndGet();

    return _factory.createEntityManager();
  }

  public void dispose(final EntityManager instance) {
    instance.close();

    if (0 == _counter.decrementAndGet()) {
      synchronized (_counter) {
        destroy();
      }
    }
  }
}
