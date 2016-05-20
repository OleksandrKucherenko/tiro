package com.tiro.i18n;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/** Localization helper. */
public class Localization_En extends ResourceBundle {
  /** Instance of the resources accessor. */
  private final ResourceBundle mBundle = ResourceBundle.getBundle("localization", Locale.ENGLISH);

  @Override
  protected Object handleGetObject(final String key) {
    return mBundle.getObject(key);
  }

  @Override
  public Enumeration<String> getKeys() {
    return mBundle.getKeys();
  }
}
