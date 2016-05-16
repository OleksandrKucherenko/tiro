package com.tiro.rest;

import com.tiro.Services;
import com.tiro.SvcVersioning;
import com.tiro.entities.Role;

import javax.annotation.Nonnull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Example:<br/>
 * http://localhost:9191/api/v1/security/version
 * <p>
 * Test:<br/>
 * curl -i http://localhost:9191/api/v1/security/version
 */
@Path(Services.V1_SECURITY)
public class SecurityRestServiceV1 implements SvcVersioning {
  @Nonnull
  @Override
  public String getVersion() {
    return "1.0";
  }

  @GET
  @Path("/roles")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getRoles() {
    final List<Role> roles = new ArrayList<>();

    roles.add(new Role("root", "dummy root"));

    return Response
        .ok()
        .entity(roles)
        .build();
  }
}
