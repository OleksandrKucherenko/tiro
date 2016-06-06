package com.tiro.rest;

import com.tiro.Services;
import com.tiro.SvcVersioning;
import com.tiro.entities.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/** Try to implement next version of the API. */
@Path(Services.V2_SECURITY)
public class SecurityRestServiceV2 implements SvcVersioning {
  @Override
  public String getVersion() {
    return "2.0";
  }

  @GET
  @Path("/user/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUserById(@PathParam("id") final long id) {
    final User user = new User("", "");

    return Response
        .ok()
        .entity(user)
        .build();
  }
}
