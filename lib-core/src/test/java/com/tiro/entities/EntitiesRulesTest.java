package com.tiro.entities;

import com.tiro.Consts;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.Type;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/** Unit test that confirms registration of all entities in JPA. */
@RunWith(JUnit4.class)
@SuppressWarnings({"unchecked"})
public class EntitiesRulesTest {
  /** Unit test logger. */
  private static final Logger _log = LoggerFactory.getLogger(Consts.LOG);
  /** Reflection helper. */
  public static final Reflections _reflections = new Reflections("com.tiro.entities");
  /** JPA factory. */
  private static EntityManagerFactory _factory;

  /** Entities manager instance. */
  private EntityManager mEm;

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

  @Before
  public void setUp() throws Exception {
    mEm = _factory.createEntityManager();

    _log.info("--> " + mTestName.getMethodName());
  }

  @After
  public void tearDown() throws Exception {
    _log.info("<-- " + mTestName.getMethodName());
    System.out.print("\n");

    if (null != mEm) {
      mEm.close();
      mEm = null;
    }
  }

  /** Test that All entities declared in code are known to JPA. */
  @Test
  public void testJpaContainsAllCodeEntities() throws Exception {
    final Set<Class<?>> types = _reflections.getTypesAnnotatedWith(Entity.class);

    final Set<Class<?>> entities = mEm.getMetamodel().getEntities()
        .stream()
        .map(Type::getJavaType)
        .collect(Collectors.toSet());

    // try to find all entities found by reflection in JPA
    types.forEach(t -> assertThat(entities).contains(t));
  }

  /**
   * JPA during parsing may create a special classes for Many-to-Many relations. Test
   * that we have classes that match all JPA entities.
   */
  @Test
  public void testCodeEntitiesContainsAllJpaClasses() throws Exception {
    final Set<Class<?>> types = _reflections.getTypesAnnotatedWith(Entity.class);

    final Set<Class<?>> entities = mEm.getMetamodel().getEntities().stream()
        .map(e -> e.getJavaType())
        .collect(Collectors.toSet());

    // try to find all JPA entities in reflection set
    entities.forEach(e -> assertThat(types).contains(e));
  }

  @Test
  public void testDefaultConstructor() throws Exception {
    final Set<Class<?>> types = _reflections.getTypesAnnotatedWith(Entity.class);

    types.forEach(t -> {
      assertThat(ReflectionUtils.getConstructors(t, c -> c.getParameterCount() == 0))
          .withFailMessage("Expected default constructor for: %s", t.getName())
          .isNotEmpty();
    });
  }

  /** All entities should implement 'private static final long serialVersionUID'. */
  @Test
  public void testSerialVersionUIDExists() {
    final Set<Class<?>> types = _reflections.getTypesAnnotatedWith(Entity.class);

    types.forEach(t -> {
      // check that field exists
      final Set<Field> fields = ReflectionUtils.getFields(t, f -> "serialVersionUID".equals(f.getName()));

      assertThat(fields)
          .withFailMessage("Expected serialVersionUID in class: %s", t.getName())
          .hasSize(1);
    });
  }

  /** All entities should implement 'private static final long serialVersionUID'. */
  @Test
  public void testSerialVersionUIDIsUnique() {
    final Set<Class<?>> types = _reflections.getTypesAnnotatedWith(Entity.class);
    final Set<Long> unique = new HashSet<>();

    // check unique value of serialization version UID
    types.forEach(t -> {
      ReflectionUtils.getFields(t, f -> "serialVersionUID".equals(f.getName()))
          .forEach(f -> {
            final long value = safeGetLong(f);
            _log.info("found: {}, serialVersionUID = {}L", t.getName(), value);

            // value cannot be ZERO
            assertThat(value).isNotEqualTo(0);

            assertThat(unique.add(value))
                .withFailMessage("Not unique serialVersionUID value detected: %s", t.getName())
                .isTrue();
          });
    });
  }

  private static long safeGetLong(final Field f) {
    try {
      f.setAccessible(true);

      return f.getLong(null);
    } catch (IllegalAccessException ignored) {
    }

    return 0;
  }
}
