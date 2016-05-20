package com.tiro.i18n;

import com.tiro.Consts;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.*;

/** Unit tests for localization helpers. */
@RunWith(JUnit4.class)
public class LocalizationTest {
  /** Unit test logger. */
  protected static final Logger _log = LoggerFactory.getLogger(Consts.TAG);

  /** Test Method information. */
  @Rule public TestName mTestName = new TestName();

  private Locale mDefaultLocale;

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

  @Test
  public void testEnglishMessages() throws Exception {
    final Localization_En en = new Localization_En();

    // Error.Message.UserNotFound=User Not Found. Are you registered in system?
    assertThat(en.getString("Error.Message.UserNotFound"))
        .isEqualTo("User Not Found. Are you registered in system?");

    dumpKeys(en);
  }

  @Test
  public void testEnglishNotExistingMessage() throws Exception {
    final Localization_En en = new Localization_En();

    assertThatThrownBy(() -> {
      final String value = en.getString("<dummy test key>");
    }).isInstanceOf(MissingResourceException.class);
  }

  @Test
  public void testRussianFallback() throws Exception {
    final Localization_Ru ru = new Localization_Ru();

    // Error.Message.UserNotFound=User Not Found. Are you registered in system?
    assertThat(ru.getString("Error.Message.UserNotFound"))
        .isEqualTo("User Not Found. Are you registered in system?");

    dumpKeys(ru);
  }

  @Test
  public void testDefaultLocale() throws Exception {
    Locale.setDefault(Localization_Ru.RUSSIAN);

    ResourceBundle bundle = ResourceBundle.getBundle("localization");

    dumpKeys(bundle);

    // logic is: "DefaultLocale" + "CurrentLocale" == "resources"
    // Error.Message.UserNotFound - is available only in localization_en.properties
    assertThatThrownBy(() -> {
      final String value = bundle.getString("Error.Message.UserNotFound");
    }).isInstanceOf(MissingResourceException.class);
  }

  private void dumpKeys(@Nonnull final ResourceBundle resources) {
    final Enumeration<String> keys = resources.getKeys();

    while (keys.hasMoreElements()) {
      final String key = keys.nextElement();
      _log.info("key: {}, value: \"{}\"", key, resources.getObject(key));
    }
  }
}
