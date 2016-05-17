package com.tiro.rest;

import com.tiro.Consts;
import com.tiro.WarmupHelper;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.*;
import org.junit.rules.TestName;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Base class for REST API testing. Solves configuration issue with JUL logging. */
@SuppressWarnings({"unused"})
public abstract class TiroJerseyTest extends JerseyTest {
  /** Trick: static variables initialized before the class members, we connect the SLF4J bridge for grizzly. */
  private static final int TRICK_LOG_CONFIGURATION = configure("", new SLF4JBridgeHandler());

  protected static final long _start = WarmupHelper.TRICK_WARM_UP;
  /** Unit test logger. */
  protected static final org.slf4j.Logger _log = LoggerFactory.getLogger(Consts.TAG);
  /** Test Method information. */
  @Rule public TestName mTestName = new TestName();

  /**
   * Configure logger by provided handler. All other handlers will be removed.
   *
   * @return number of handlers found in logger.
   */
  private static int configure(@Nonnull final String packageName, @Nonnull final java.util.logging.Handler handler) {
    final Logger logger = Logger.getLogger(packageName);
    final Handler[] handlers = logger.getHandlers();

    Arrays.stream(handlers).forEach(logger::removeHandler);
    logger.addHandler(handler);

    return handlers.length;
  }

  /* package */ TiroJerseyTest() {
    super();

    set(TestProperties.RECORD_LOG_LEVEL, Level.OFF.intValue());
  }

  @BeforeClass
  public static void initialize() {
    WarmupHelper.warmUpGrizzlyWaiting();
  }

  @AfterClass
  public static void destroy() {

  }

  @Before
  public final void setUpTiro() throws Exception {
    _log.info("--> " + mTestName.getMethodName());

    onSetup();
  }

  @After
  public final void tearDownTiro() throws Exception {
    onTearDown();

    _log.info("<-- " + mTestName.getMethodName());
    System.out.print("\n");
  }

  protected void onSetup() {
    // do nothing, reserved for inheritance
  }

  protected void onTearDown() {
    // do nothing, reserved for inheritors
  }
}
