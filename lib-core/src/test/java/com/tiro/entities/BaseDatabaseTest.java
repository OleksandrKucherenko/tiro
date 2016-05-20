package com.tiro.entities;

import com.tiro.Consts;
import com.tiro.schema.DbEntity;
import org.junit.*;
import org.junit.rules.TestName;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;

/** Abstract class that creates and rollbacks transaction. Great for all Database operations. */
public abstract class BaseDatabaseTest {
  /** Unit test logger. */
  protected static final Logger _log = LoggerFactory.getLogger(Consts.TAG);
  /** Reflection helper instance. */
  protected static Reflections _reflections;
  /** JPA factory. */
  protected static EntityManagerFactory _factory;

  /** Entity manager instance. */
  protected EntityManager mEm;

  /** Test Method information. */
  @Rule public TestName mTestName = new TestName();

  @BeforeClass
  public static void initialize() {
    _factory = Persistence.createEntityManagerFactory("sqlite");
  }

  @AfterClass
  public static void destroy() {
    if (null != _factory) {
      _factory.close();
      _factory = null;
    }
  }

  /** Reflection helper. */
  public static Reflections getReflections() {
    if (null == _reflections) {
      _reflections = new Reflections("com.tiro.entities");
    }

    return _reflections;
  }

  @Before
  public final void setUp() throws Exception {
    mEm = _factory.createEntityManager();

    // start transaction which we can rollback at the end of the test
    mEm.getTransaction().begin();

    onSetup();

    _log.info("--> " + mTestName.getMethodName());
  }

  @After
  public final void tearDown() throws Exception {
    onTearDown();

    if (null != mEm) {
      if (mEm.getTransaction().isActive()) {
        mEm.getTransaction().rollback();
      } else {
        _log.debug("NO transaction rollback! No active transaction.");
      }

      mEm.close();
      mEm = null;
    }

    _log.info("<-- " + mTestName.getMethodName());
    System.out.print("\n");
  }

  protected void onSetup() {
    // do nothing, reserved for inheritance
  }

  protected void onTearDown() {
    // do nothing, reserved for inheritors
  }

  protected void persistAll(@Nonnull final DbEntity... entities) {
    for (Object data : entities) {
      mEm.persist(data);
    }
  }

  protected void dumpAll() {
    getReflections().getTypesAnnotatedWith(Entity.class).forEach(this::dump);
  }

  protected void dumpMany(@Nonnull final Class<?>... klass) {
    Arrays.stream(klass).forEach(this::dump);
  }

  protected void dump(@Nonnull final Class<?> klass) {
    mEm.createQuery("SELECT o FROM " + klass.getName() + " o", klass)
        .getResultList()
        .forEach(r -> _log.info(r.toString()));
  }

}
