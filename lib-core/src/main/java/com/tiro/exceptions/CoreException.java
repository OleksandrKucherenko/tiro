package com.tiro.exceptions;

import javax.annotation.Nonnull;

/** Base class for all exceptions raised inside the library. */
public abstract class CoreException extends Exception {

  /** Initialize by cause of the exception (inner exception). */
  protected CoreException(final Throwable inner) {
    super(inner);
  }

  /** Wrap system exception into our managed exceptions. */
  public static CoreException wrap(@Nonnull final Throwable exception) {
    return new CoreException(exception) {
    };
  }
}
