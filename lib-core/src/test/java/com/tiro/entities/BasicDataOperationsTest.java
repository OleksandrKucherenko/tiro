package com.tiro.entities;

import com.tiro.Consts;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

/** Test basic SQL/Data manipulation operations. */
@RunWith(JUnit4.class)
@SuppressWarnings({"unchecked"})
public class BasicDataOperationsTest {
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

    // start transaction which we can rollback at the end of the test
    mEm.getTransaction().begin();

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

  @Test
  public void testInsertEntity() throws Exception {
    mEm.persist(new Role("root")); // 1
    mEm.persist(new Role("user")); // 2
    mEm.persist(new Role("backup")); // 3
    mEm.persist(new Role("tester")); // 4

    assertThat(mEm.find(Role.class, 4L)).isNotNull();
  }

  @Test
  public void testInsertFindUpdateEntity() throws Exception {
    final Role roleFirst;
    mEm.persist(roleFirst = new Role("root")); // 1
    mEm.persist(new Role("user")); // 2

    // select entity from cache
    final Role roleRoot = mEm.find(Role.class, 1L);
    assertThat(roleRoot).isNotNull();
    assertThat(roleFirst).isEqualTo(roleRoot);

    // update operation increase version field value
    mEm.persist(BaseEntity.touch(roleRoot));
    mEm.flush();
    assertThat(roleRoot.getVersion()).isEqualTo(1);
  }

  /** Delete of group does not delete the roles or users. */
  @Test
  public void testDeleteCascadeGroup() throws Exception {
    final Group groupAdmins;
    final User userAdmin;
    final Role roleRoot, roleBackup;

    mEm.persist(roleRoot = new Role("root")); // 1
    mEm.persist(roleBackup = new Role("backup")); // 1
    mEm.persist(groupAdmins = new Group("Administrators")); // 1
    mEm.persist(userAdmin = new User("@1", "admin")); // 1

    _log.info("create many-to-many relations");

    // build connections between the groups-2-roles & groups-2-users
    groupAdmins.addRole(roleRoot).addUser(userAdmin);
    mEm.persist(BaseEntity.touch(groupAdmins));

    // build connection between users-2-roles
    userAdmin.addRole(roleRoot).addRole(roleBackup);
    mEm.persist(BaseEntity.touch(userAdmin));
    mEm.flush();

    _log.info("cascade delete of GROUP");

    // check that user and role stay after the DELETE of group
    mEm.remove(groupAdmins);
    mEm.flush();

    assertThat(mEm.contains(roleRoot)).isTrue();
    assertThat(mEm.contains(userAdmin)).isTrue();

    assertThat(mEm.contains(groupAdmins)).isFalse();
  }

  /** Delete of user does not delete the roles or groups. */
  @Test
  public void testDeleteCascadeUser() throws Exception {
    final Group groupAdmins;
    final User userAdmin;
    final Role roleRoot, roleBackup;

    mEm.persist(roleRoot = new Role("root")); // 1
    mEm.persist(roleBackup = new Role("backup")); // 1
    mEm.persist(groupAdmins = new Group("Administrators")); // 1
    mEm.persist(userAdmin = new User("@1", "admin")); // 1

    _log.info("create GROUP many-to-many relations");

    // build connections between the groups-2-roles & groups-2-users
    groupAdmins.addRole(roleRoot).addUser(userAdmin);
    mEm.persist(BaseEntity.touch(groupAdmins));
    mEm.flush();

    _log.info("USER refresh own groups");
    mEm.refresh(userAdmin);
    assertThat(userAdmin.getGroups()).hasSize(1);

    _log.info("create USER many-to-many relations");

    // build connection between users-2-roles
    userAdmin.addRole(roleRoot).addRole(roleBackup);
    mEm.persist(BaseEntity.touch(userAdmin));
    mEm.flush();

    _log.info("cascade delete of USER");

    // check that role stay after the DELETE of user
    mEm.remove(userAdmin);
    mEm.flush();

    _log.info("validation...");

    assertThat(mEm.contains(roleRoot)).isTrue();
    assertThat(mEm.contains(roleBackup)).isTrue();
    assertThat(mEm.contains(groupAdmins)).isTrue();

    assertThat(mEm.contains(userAdmin)).isFalse();
  }

  /** Delete of role does not delete any group or user. */
  @Test
  public void testDeleteCascadeRole() throws Exception {
    final Group groupAdmins;
    final User userAdmin;
    final Role roleRoot, roleBackup;

    _log.info("insert new instances");

    mEm.persist(roleRoot = new Role("root")); // 1
    mEm.persist(roleBackup = new Role("backup")); // 1
    mEm.persist(groupAdmins = new Group("Administrators")); // 1
    mEm.persist(userAdmin = new User("@1", "admin")); // 1

    _log.info("create many-to-many relations");

    groupAdmins.addRole(roleRoot).addUser(userAdmin);
    userAdmin.addRole(roleRoot).addRole(roleBackup);
    mEm.persist(BaseEntity.touch(groupAdmins));
    mEm.persist(BaseEntity.touch(userAdmin));
    mEm.flush();

    _log.info("delete ROLE");

    mEm.remove(roleRoot);
    mEm.flush();

    assertThat(mEm.contains(userAdmin)).isTrue();
    assertThat(mEm.contains(groupAdmins)).isTrue();

    assertThat(mEm.contains(roleRoot)).isFalse();
  }
}
