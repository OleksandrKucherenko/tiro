package com.tiro.rest;

import com.tiro.Services;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;

import static org.assertj.core.api.Assertions.*;

/** Jersey API testing. */
public class SecurityRestServiceV2ApiTest extends TiroJerseyTest {
  @Override
  protected Application configure() {
    return new ResourceConfig(SecurityRestServiceV2.class)
        .property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true)
        .register(ExceptionsMapperService.class);
  }

  private WebTarget getTargetV2Security() {
    return target(Services.V2_SECURITY);
  }

  @Test
  public void testVersionApiCall() throws Exception {
    final String version = getTargetV2Security().path("/version").request().get(String.class);

    assertThat(version)
        .isNotEmpty()
        .containsPattern("[0-9]+(.[0-9]+){1,3}")
        .isEqualToIgnoringCase("2.0");
  }

  @Test
  public void testApplicationWadl() throws Exception {
    final String xml = target().path("application.wadl").request().get(String.class);

    assertThat(xml)
        .isNotEmpty()
        .contains("<?xml")
        .contains("<resource path=\"/v2/security\">");
  }
}
