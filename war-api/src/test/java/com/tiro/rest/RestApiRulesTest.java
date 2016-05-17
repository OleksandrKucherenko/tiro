package com.tiro.rest;

import com.tiro.Consts;
import com.tiro.SvcVersioning;
import com.tiro.WarmupHelper;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

/** Generic design rules that developer should follow during the implementing the services. */
@RunWith(JUnit4.class)
public class RestApiRulesTest {
  /** Unit test logger. */
  protected static final Logger _log = LoggerFactory.getLogger(Consts.TAG);
  /** Reflection helper instance. */
  protected static Reflections _reflections;

  protected static final long _start = WarmupHelper.TRICK_WARM_UP;

  /** Test Method information. */
  @Rule public TestName mTestName = new TestName();

  /** Reflection helper. */
  public static Reflections getReflections() {
    if (null == _reflections) {
      _reflections = new Reflections("com.tiro.rest");
    }

    return _reflections;
  }

  @BeforeClass
  public static void initialize() {
    WarmupHelper.warmUpGrizzlyWaiting();
  }

  @Before
  public final void setUp() throws Exception {
    _log.info("--> " + mTestName.getMethodName());
  }

  @After
  public final void tearDown() throws Exception {
    _log.info("<-- " + mTestName.getMethodName());
    System.out.print("\n");
  }

  @Test
  public void testAllServicesShouldImplementVersioning() throws Exception {
    final Set<Class<?>> types = getReflections().getTypesAnnotatedWith(Path.class);

    types.forEach(t -> {
      assertThat(SvcVersioning.class.isAssignableFrom(t))
          .withFailMessage("All services should implement getVersion() method of interface SvcVersioning, class: %s", t.getName())
          .isTrue();

      _log.info("Checked: {}", t.getName());
    });
  }

  @Test
  public void testUniquePathOfAllServices() throws Exception {
    final Set<Class<?>> types = getReflections().getTypesAnnotatedWith(Path.class);
    final Map<String, Class<?>> reverseLookup = new HashMap<>();
    final Set<String> uniquePaths = new HashSet<>();

    types.stream().forEach(t -> {
      final Path annotation = t.getAnnotation(Path.class);
      final String path = annotation.value();

      assertThat(uniquePaths.add(path))
          .withFailMessage("Found path duplicate in declaration of class: %s, path: %s. Conflicts with: %s", t.getName(), path, reverseLookup.get(path))
          .isTrue();

      reverseLookup.put(path, t);
      _log.info("map path: {}, to class: {}", path, t);
    });
  }
}
