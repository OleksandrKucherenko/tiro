package com.tiro.entities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Unit tests of Security pattern - Users <--> Groups <--> Roles.
 */
@RunWith(JUnit4.class)
public class UsersGroupsRolesSecurityTest extends BaseDatabaseTest {

  @Override
  public void onSetup() {
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
    persistAll(roleAdmin, roleUser, roleBackup, roleTester);
    persistAll(groupAdmins, groupUsers, groupOther);
    persistAll(userAdmin, userUser, userTester);
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

    groupAdmins.addRole(roleAdmin);

    assertThat(groupAdmins.getRoles()).hasSize(1);
    assertThat(roleAdmin.getGroups()).hasSize(1);
  }

  @Test
  public void testGroupsToUsers() throws Exception {
    final Group groupAdmins = mEm.find(Group.class, 1L);
    final User userRoot = mEm.find(User.class, 1L);

    groupAdmins.addUser(userRoot);

    assertThat(userRoot.getGroups()).hasSize(1);
    assertThat(groupAdmins.getUsers()).hasSize(1);
  }

  @Test
  public void testUsersToRoles() throws Exception {
    final Role roleAdmin = mEm.find(Role.class, 1L);
    final User userRoot = mEm.find(User.class, 1L);

    userRoot.addRole(roleAdmin);

    assertThat(userRoot.getRoles()).hasSize(1);
    assertThat(roleAdmin.getUsers()).hasSize(1);
  }

  @Test
  public void testUsersToGroups() throws Exception {
    final Group groupAdmins = mEm.find(Group.class, 1L);
    final User userRoot = mEm.find(User.class, 1L);

    userRoot.addGroup(groupAdmins);

    assertThat(userRoot.getGroups()).hasSize(1);
    assertThat(groupAdmins.getUsers()).hasSize(1);
  }

  @Test
  public void testRoleProperties() throws Exception {
    final Role roleTest = new Role("test");
    assertThat(roleTest.getName()).isNotNull().isNotEmpty().isEqualTo("test");
    assertThat(roleTest.getInfo()).isNotNull().isEmpty();

    final Role roleInfo = new Role("info", "Some additional information");
    assertThat(roleInfo.getName()).isNotNull().isNotEmpty().isEqualTo("info");
    assertThat(roleInfo.getInfo()).isNotNull().isNotEmpty();

    roleInfo.setInfo("");
    assertThat(roleInfo.getInfo()).isNotNull().isEmpty();
  }
}
