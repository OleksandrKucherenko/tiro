package com.tiro;

import javax.annotation.Nonnull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/** All services should implement this interface for giving clients ability to adjust to server logic. */
public interface SvcVersioning {
  /**
   * Return the version number in two-, three- or four- parts format.
   * <p>
   * Examples:<br/>
   * - 1.0<br/>
   * - 1.0.1<br/>
   * - 1.0.1.119<br/>
   * <p>
   * Pattern: [0-9]+(.[0-9]+){1,3}
   */
  @GET
  @Path("/version")
  @Produces(MediaType.TEXT_HTML)
  @Nonnull
  String getVersion();
}
