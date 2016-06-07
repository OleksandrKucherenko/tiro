package com.tiro;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/** Common setup for unit test. */
public abstract class BaseTest {
  /** Unit test logger. */
  protected static final Logger _log = LoggerFactory.getLogger(Consts.TAG);
  /** Reflection helper instance. */
  protected static Reflections _reflections;

  /** Test Method information. */
  @Rule public TestName mTestName = new TestName();

  /** Default localization. */
  protected Locale mDefaultLocale;

  @Before
  public final void setUp() throws Exception {
    onSetup();

    _log.info("--> " + mTestName.getMethodName());
  }

  @After
  public final void tearDown() throws Exception {
    onTearDown();

    _log.info("<-- " + mTestName.getMethodName());
    System.out.print("\n");
  }

  protected void onSetup() {
    // do nothing, reserved for inheritance
    mDefaultLocale = Locale.getDefault();
  }

  protected void onTearDown() {
    // do nothing, reserved for inheritors
    Locale.setDefault(mDefaultLocale);
  }

  /** Reflection helper. */
  public static Reflections getReflections() {
    if (null == _reflections) {
      _reflections = new Reflections("com.tiro");
    }

    return _reflections;
  }
}
