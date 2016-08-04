package com.tiro.rest;

import com.tiro.Services;
import com.tiro.SvcVersioning;
import com.tiro.dao.DaoFactory;
import com.tiro.dao.GroupDao;
import com.tiro.dao.SecurityDao;
import com.tiro.dao.UserDao;
import com.tiro.entities.Group;
import com.tiro.entities.Role;
import com.tiro.entities.User;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
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

  /** Get the reference on Entity manager */
//  @Inject
  private EntityManager mEm;

  @Nonnull
  @Override
  public String getVersion() {
    return "1.0";
  }

  @GET
  @Path("/roles")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getRoles() {
    final SecurityDao dao = DaoFactory.get(mEm, SecurityDao.class);
    final List<Role> roles = new ArrayList<>();

    roles.add(new Role("root", "dummy root"));

    return Response
        .ok()
        .entity(roles)
        .build();
  }

  @GET
  @Path("/users")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUsers() {
    final UserDao dao = DaoFactory.get(mEm, UserDao.class);
    final List<User> users = new ArrayList<>();

    users.add(new User("dummy", "dummy@dummy.com"));

    return Response
        .ok()
        .entity(users)
        .build();
  }

  @GET
  @Path("/groups")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getGroups() {
    final GroupDao dao = DaoFactory.get(mEm, GroupDao.class);
    final List<Group> groups = new ArrayList<>();

    groups.add(new Group("dummy"));

    return Response
        .ok()
        .entity(groups)
        .build();
  }
}
