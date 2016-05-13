package com.tiro.rest;

import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import org.junit.Test;

import javax.annotation.Nonnull;

import static org.assertj.core.api.Assertions.assertThat;

/** Jersey API testing. */
public class VersionApiTest extends JerseyTest {

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
