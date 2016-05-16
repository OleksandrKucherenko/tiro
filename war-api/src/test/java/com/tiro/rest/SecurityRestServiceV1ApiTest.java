package com.tiro.rest;

import com.tiro.Services;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

import javax.ws.rs.core.Application;

import static org.assertj.core.api.Assertions.*;

/** Jersey API testing. */
public class SecurityRestServiceV1ApiTest extends TiroJerseyTest {
  @Override
  protected Application configure() {
    return new ResourceConfig(SecurityRestServiceV1.class);
  }

  @Test
  public void testVersionApiCall() throws Exception {
    String version = target(Services.V1_SECURITY).path("/version").request().get(String.class);

    assertThat(version)
        .isNotEmpty()
        .containsPattern("[0-9]+(.[0-9]+){1,3}")
        .isEqualToIgnoringCase("1.0");
  }
}
