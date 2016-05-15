package com.tiro.rest;

import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.logging.Handler;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

/** Jersey API testing. */
public class VersionApiTest extends JerseyTest {
  /** Trick: static variables initialized before the class members, we connect the SLF4J bridge for grizzly. */
  private static final int TRICK_LOG_CONFIGURATION = configureLogger("", new SLF4JBridgeHandler());

  private static int configureLogger(final String packageName, final java.util.logging.Handler handler) {
    final Logger logger = Logger.getLogger(packageName);
    final Handler[] handlers = logger.getHandlers();

    Arrays.stream(handlers).forEach(logger::removeHandler);
    logger.addHandler(handler);

    return handlers.length;
  }

  public VersionApiTest() {
    super(packageName(SecurityRestService.class));
  }


  public static String packageName(@Nonnull final Class<?> clazz) {
    return clazz.getPackage().getName();
  }

  @Override
  protected AppDescriptor configure() {
    return super.configure();
  }

  @Test
  public void testVersionApiCall() throws Exception {
    String version = resource().path(Services.V1_SECURITY).path("/version").get(String.class);

    assertThat(version)
        .isNotEmpty()
        .isEqualToIgnoringCase("1.0");
  }
}
