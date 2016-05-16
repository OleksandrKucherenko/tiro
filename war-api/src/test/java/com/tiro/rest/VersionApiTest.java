package com.tiro.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.ws.rs.core.Application;
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

  @Override
  protected Application configure() {
    return new ResourceConfig(SecurityRestService.class);
  }

  @Test
  public void testVersionApiCall() throws Exception {
    String version = target(Services.V1_SECURITY).path("/version").request().get(String.class);

    assertThat(version)
        .isNotEmpty()
        .isEqualToIgnoringCase("1.0");
  }
}
