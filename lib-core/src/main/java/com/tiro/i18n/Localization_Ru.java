package com.tiro.i18n;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/** Russian Localization. */
public class Localization_Ru extends Localization_En {
  /** Define Russian locale. */
  public static final Locale RUSSIAN = new Locale("ru", "");
  /** Instance of resource accessor. */
  private final ResourceBundle mBundle = ResourceBundle.getBundle("localization", RUSSIAN);

  @Override
  protected Object handleGetObject(final String key) {
    if (mBundle.containsKey(key)) {
      return mBundle.getObject(key);
    }

    // fallback to english
    return super.handleGetObject(key);
  }

  @Override
  public Enumeration<String> getKeys() {
    return mBundle.getKeys();
  }
}
