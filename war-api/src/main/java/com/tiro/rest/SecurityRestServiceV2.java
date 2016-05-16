package com.tiro.rest;

import com.tiro.Services;
import com.tiro.SvcVersioning;

import javax.ws.rs.Path;

/** Try to implement next version of the API. */
@Path(Services.V2_SECURITY)
public class SecurityRestServiceV2 implements SvcVersioning {
  @Override
  public String getVersion() {
    return "2.0";
  }
}
