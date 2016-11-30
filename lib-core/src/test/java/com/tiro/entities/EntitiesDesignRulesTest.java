package com.tiro.entities;

import com.tiro.BaseDatabaseTest;
import com.tiro.Categories.DesignRules;
import org.hibernate.annotations.SQLDelete;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.ocpsoft.prettytime.PrettyTime;
import org.reflections.ReflectionUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.metamodel.Type;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.reflections.ReflectionUtils.getFields;
import static org.reflections.ReflectionUtils.withAnnotation;

/** Unit test that confirms registration of all entities in JPA. */
@RunWith(JUnit4.class)
@Category({DesignRules.class})
@SuppressWarnings({"unchecked"})
public class EntitiesDesignRulesTest extends BaseDatabaseTest {

  /** Test that All entities declared in code are known to JPA. */
  @Test
  public void testJpaContainsAllCodeEntities() throws Exception {
    final Set<Class<?>> types = getReflections().getTypesAnnotatedWith(Entity.class);

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
    final Set<Class<?>> types = getReflections().getTypesAnnotatedWith(Entity.class);

    final Set<Class<?>> entities = mEm.getMetamodel().getEntities().stream()
        .map(e -> e.getJavaType())
        .collect(Collectors.toSet());

    // try to find all JPA entities in reflection set
    entities.forEach(e -> assertThat(types).contains(e));
  }

  @Test
  public void testDefaultConstructor() throws Exception {
    final Set<Class<?>> types = getReflections().getTypesAnnotatedWith(Entity.class);

    types.forEach(t -> {
      assertThat(ReflectionUtils.getConstructors(t, c -> c.getParameterCount() == 0))
          .withFailMessage("Expected default constructor for: %s", t.getName())
          .isNotEmpty();
    });
  }

  /** All entities should implement 'private static final long serialVersionUID'. */
  @Test
  public void testSerialVersionUIDExists() throws Exception {
    final Set<Class<?>> types = getReflections().getTypesAnnotatedWith(Entity.class);

    types.forEach(t -> {
      // check that field exists
      final Set<Field> fields = getFields(t, f -> "serialVersionUID".equals(f.getName()));

      assertThat(fields)
          .withFailMessage("Expected serialVersionUID in class: %s", t.getName())
          .hasSize(1);
    });
  }

  /** All entities should implement 'private static final long serialVersionUID'. */
  @Test
  public void testSerialVersionUIDIsUnique() throws Exception {
    final Set<Class<?>> types = getReflections().getTypesAnnotatedWith(Entity.class);
    final Set<Long> unique = new HashSet<>();

    // check unique value of serialization version UID
    types.forEach(t -> {
      getFields(t, f -> "serialVersionUID".equals(f.getName()))
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

  /**  */
  @Test
  public void testTimestamps() throws Exception {
    final long created, start = System.nanoTime();

    // by default creation time is set during instance creation
    final Role roleTest = new Role("time");
    assertThat(created = roleTest.getTimeCreated()).isGreaterThan(start);
    assertThat(roleTest.getTimeUpdated()).isEqualTo(0);
    assertThat(roleTest.getTimeDeleted()).isEqualTo(0);

    // on 'persist' createdAt should be refreshed to a new value, the actual time of saving
    mEm.persist(roleTest);
    mEm.flush();
    assertThat(roleTest.getTimeCreated()).isNotEqualTo(created);
    assertThat(roleTest.getTimeUpdated()).isEqualTo(0);
    assertThat(roleTest.getTimeDeleted()).isEqualTo(0);

    // update the instance values
    mEm.persist(roleTest.setName("time2"));
    mEm.flush();
    assertThat(roleTest.getTimeUpdated()).isGreaterThan(start);
    assertThat(roleTest.getTimeDeleted()).isEqualTo(0);

    // on 'delete' expected detaching of the instance and it 'deletedAt' field updates.
    mEm.remove(roleTest);
    mEm.flush();
    assertThat(roleTest.getTimeDeleted()).isGreaterThan(start);

    // do some pretty time printing
    final PrettyTime pt = new PrettyTime(new Date(TimeUnit.NANOSECONDS.toMillis(start)));
    _log.info("ROLE deleted {}", pt.format(new Date(TimeUnit.NANOSECONDS.toMillis(roleTest.getTimeDeleted()))));
  }

  /** 'Soft Delete' feature is very specific, SQL should contains a specific number of parameters. */
  @Test
  public void testConfirmSqlDeleteParametersNumber() throws Exception {
    final Set<Class<?>> types = getReflections().getTypesAnnotatedWith(SQLDelete.class);

    types.forEach(t -> {
      final SQLDelete delete = t.getAnnotation(SQLDelete.class);
      final Set<Field> fields = getFields(t, withAnnotation(Column.class));
      final Set<Field> fieldsIds = getFields(t, withAnnotation(Id.class), withAnnotation(Column.class));

      // extract column definition for all @Id annotated fields
      final Set<Column> ids = fieldsIds.stream()
          .map(f -> f.getAnnotation(Column.class))
          .collect(Collectors.toSet());

      // extract all columns that should take action in sql delete query (composite key)
      final Set<Column> columns = fields
          .stream()
          .map(f -> f.getAnnotation(Column.class))
          .filter(c -> c.unique() || ids.contains(c))
          .collect(Collectors.toSet());

      long totalParameters = Arrays.stream(delete.sql().split("\\?")).count();

      // check that number of parameters is matching
      assertThat(totalParameters).isGreaterThanOrEqualTo(columns.size()).isLessThan(columns.size() + 2);

      // check that all needed columns are in SQL
      columns.forEach(c -> assertThat(delete.sql()).contains(c.name()));
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
