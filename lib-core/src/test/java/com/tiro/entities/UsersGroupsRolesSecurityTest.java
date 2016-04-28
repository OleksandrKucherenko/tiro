package com.tiro.entities;

import com.tiro.Consts;
import com.tiro.schema.DbEntity;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.constraints.NotNull;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Unit tests of Security pattern - Users <--> Groups <--> Roles.
 */
@RunWith(JUnit4.class)
public class UsersGroupsRolesSecurityTest {
  /** Unit test logger. */
  private static final Logger _log = LoggerFactory.getLogger(Consts.LOG);
  /** JPA factory. */
  private static EntityManagerFactory _factory;

  /** Entity manager instance. */
  private EntityManager mEm;
  /** Test Method information. */
  @Rule public TestName mTestName = new TestName();

  @BeforeClass
  public static void initialize() {
    _factory = Persistence.createEntityManagerFactory("sqlite");
  }

  @AfterClass
  public static void destroy() {
    if (null != _factory) {
      _factory.close();
      _factory = null;
    }
  }

  @Before
  public void setUp() throws Exception {
    mEm = _factory.createEntityManager();

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

    // start transaction which we can rollback at the end of the test
    mEm.getTransaction().begin();

    // save to DB
    persistAll(roleAdmin, roleUser, roleBackup, roleTester);
    persistAll(groupAdmins, groupUsers, groupOther);
    persistAll(userAdmin, userUser, userTester);

    _log.info("--> " + mTestName.getMethodName());
  }

  @After
  public void tearDown() throws Exception {
    if (null != mEm) {
      if (mEm.getTransaction().isActive()) {
        mEm.getTransaction().rollback();
        _log.info("do transaction rollback...");
      }

      mEm.close();
      mEm = null;
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

    persistAll(groupAdmins, userRoot);

    // update DB by new relations many-to-many
//    mEm.getTransaction().commit();
    mEm.flush();

    // userRoot should get reference on Group now
    mEm.refresh(userRoot);

    assertThat(userRoot.getGroups()).hasSize(1);
    assertThat(userRoot.getRoles()).hasSize(1);
    assertThat(groupAdmins.getUsers()).hasSize(1);
    assertThat(groupAdmins.getRoles()).hasSize(1);

//    dumpAll();
  }

  private void persistAll(@NotNull final DbEntity... entities) {
    for (Object data : entities) {
      mEm.persist(data);
    }
  }

  private void dumpAll() {
    EntitiesRegistrationTest._reflections.getTypesAnnotatedWith(Entity.class).forEach(this::dump);
  }

  private void dump(@NotNull final Class<?> klass) {
    mEm.createQuery("SELECT o FROM " + klass.getName() + " o", klass)
        .getResultList()
        .forEach(r -> _log.info(r.toString()));
  }

}
