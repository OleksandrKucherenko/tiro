package com.tiro;

import com.tiro.entities.Role;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;

/** */
@Path("/v1/security")
public class SecurityRestService {
  @GET
  @Path("/roles")
  public Response getRoles() {
    final List<Role> roles = new ArrayList<>();

    roles.add(new Role("root", "dummy root"));

    return Response
        .status(HTTP_OK)
        .entity(roles)
        .build();
  }
}
