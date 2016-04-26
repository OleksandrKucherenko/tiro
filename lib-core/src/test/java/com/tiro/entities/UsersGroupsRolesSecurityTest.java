package com.tiro.entities;

import com.tiro.schema.DbEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.constraints.NotNull;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Unit tests of Security pattern - Users <--> Groups <--> Roles.
 */
@RunWith(JUnit4.class)
public class UsersGroupsRolesSecurityTest {
  /** Unit test logger. */
  private static final Logger _log = LoggerFactory.getLogger("dump");
  /** DB entity manager mFactory instance. */
  private EntityManagerFactory mFactory;
  /** Entity manager instance. */
  private EntityManager mEm;
  /** Test Method information. */
  @Rule public TestName mTestName = new TestName();

  @Before
  public void setUp() throws Exception {
    mFactory = Persistence.createEntityManagerFactory("sqlite");
    mEm = mFactory.createEntityManager();

    // create minimalistic set of entities in tables for good testing
    final Role roleAdmin = new Role("root");
    final Role roleUser = new Role("user");
    final Role roleBackup = new Role("backup");
    final Role roleTester = new Role("tester");

    final Group groupAdmins = new Group("Administrators");
    final Group groupUsers = new Group("Users");
    final Group groupOther = new Group("System");

    final User userAdmin = new User("@1", "admin");
    final User userUser = new User("@2", "developer");
    final User userTester = new User("@3", "tester");

    // save to DB
    mEm.getTransaction().begin();
    persistAll(roleAdmin, roleUser, roleBackup, roleTester);
    persistAll(groupAdmins, groupUsers, groupOther);
    persistAll(userAdmin, userTester, userUser);
    mEm.getTransaction().commit();

    _log.info("--> " + mTestName.getMethodName());
  }

  @After
  public void tearDown() throws Exception {
    if (null != mEm) {
      mEm.close();
      mEm = null;
    }

    if (null != mFactory) {
      mFactory.close();
      mFactory = null;
    }

    _log.info("<-- " + mTestName.getMethodName());
    System.out.print("\n");
  }

  /**
   * Create database schema from entities.
   */
  @Test
  public void testCreateSchema() throws Exception {
    dumpAll();
  }

  @Test
  public void testGroupsToRoles() throws Exception {
    final Role roleAdmin = mEm.find(Role.class, 1L);
    final Group groupAdmins = mEm.find(Group.class, 1L);
    final User userRoot = mEm.find(User.class, 1L);

    groupAdmins.addRole(roleAdmin);
    groupAdmins.addUser(userRoot);
    userRoot.addRole(roleAdmin);

    mEm.getTransaction().begin();
    persistAll(groupAdmins, userRoot);
    mEm.getTransaction().commit();

    assertThat(userRoot.getGroups().size(), equalTo(0));

    // get group reference
    mEm.refresh(userRoot);

    assertThat(userRoot.getGroups().size(), equalTo(1));
    assertThat(userRoot.getRoles().size(), equalTo(1));
    assertThat(groupAdmins.getUsers().size(), equalTo(1));
    assertThat(groupAdmins.getRoles().size(), equalTo(1));

    dumpAll();
  }

  private void persistAll(@NotNull final DbEntity... entities) {
    for (Object data : entities) {
      mEm.persist(data);
    }
  }

  private void dumpAll() {
    dump(Role.class);
    dump(Group.class);
    dump(User.class);

    dump(GroupsToRoles.class);
    dump(GroupsToUsers.class);
    dump(UsersToRoles.class);
  }

  private void dump(@NotNull final Class<?> klass) {
    mEm.createQuery("SELECT o FROM " + klass.getName() + " o", klass)
        .getResultList()
        .forEach(r -> _log.info(r.toString()));
  }

}
