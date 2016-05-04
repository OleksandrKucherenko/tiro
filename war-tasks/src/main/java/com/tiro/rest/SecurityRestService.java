package com.tiro.rest;

import com.tiro.entities.Role;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/** */
@Path("/security")
public class SecurityRestService {
  @GET
  @Path("/version")
  @Produces(MediaType.TEXT_HTML)
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
