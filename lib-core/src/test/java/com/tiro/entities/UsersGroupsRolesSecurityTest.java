package com.tiro.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.constraints.NotNull;

/**
 * Unit tests of Security pattern - Users <--> Groups <--> Roles.
 */
@RunWith(JUnit4.class)
public class UsersGroupsRolesSecurityTest {
  private static final String ALL_MODEL_PACKAGES = User.class.getPackage().getName() + ".*";

  private EntityManager em;

  @Before
  public void setUp() throws Exception {
    // register all entities
    com.objectdb.Enhancer.enhance(ALL_MODEL_PACKAGES);

    final EntityManagerFactory factory = Persistence.createEntityManagerFactory("objectdb:build/tmp/testing.tmp;drop");
    em = factory.createEntityManager();

    // create minimalistic set of entities in tables for good testing
    final Role roleAdmin = new Role("root");
    final Role roleUser = new Role("user");
    final Role roleBackup = new Role("backup");
    final Role roleTester = new Role("tester");

    final Group groupAdmins = new Group("Administrators");
    final Group groupUsers = new Group("Users");
    final Group groupOther = new Group("System");

    final User userAdmin = new User("", "admin");
    final User userUser = new User("", "developer");
    final User userTester = new User("", "tester");

    // save to DB
    em.getTransaction().begin();
    persistAll(roleAdmin, roleUser, roleBackup, roleTester);
    persistAll(groupAdmins, groupUsers, groupOther);
    persistAll(userAdmin, userTester, userUser);
    em.getTransaction().commit();
  }

  public void persistAll(@NotNull final Object... entities) {
    for (Object data : entities) {
      em.persist(data);
    }
  }

  @After
  public void tearDown() throws Exception {
    if (null != em) {
      em.close();
    }
  }

  /**
   * Create database schema from entities.
   */
  @Test
  public void testCreateSchema() throws Exception {


  }

  @Test
  public void testGroupsToRoles() throws Exception {
//    final Group groupAdmins = new Group("Administrators").addRole(roleAdmin).addRole(roleBackup);
//    final Group groupUsers = new Group("Users").addRole(roleUser);
//    final Group groupOther = new Group("System").addRole(roleBackup).addRole(roleTester);
  }
}
