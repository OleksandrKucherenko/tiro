package com.tiro.entities;

import com.tiro.BaseDatabaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.*;


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
  public void testUsersRolesGroupsManyToMany() throws Exception {
    final Role roleAdmin = mEm.find(Role.class, 1L);
    final Group groupAdmins = mEm.find(Group.class, 1L);
    final User userRoot = mEm.find(User.class, 1L);

    userRoot.addRole(roleAdmin);
    userRoot.addGroup(groupAdmins);
    groupAdmins.addRole(roleAdmin);

    assertThat(userRoot.getGroups()).hasSize(1);
    assertThat(groupAdmins.getUsers()).hasSize(1);
    assertThat(roleAdmin.getUsers()).hasSize(1);

    persistAll(userRoot, roleAdmin, groupAdmins);

    dumpMany(GroupsToRoles.class, GroupsToUsers.class, UsersToRoles.class);

    // try to find newly created instance by composite primary key
    final GroupsToRoles resultGTR = mEm.find(GroupsToRoles.class, new GroupsToRoles(1, 1));
    assertThat(resultGTR).isNotNull();

    final GroupsToUsers resultGTU = mEm.find(GroupsToUsers.class, new GroupsToUsers(1, 1));
    assertThat(resultGTU).isNotNull();

    final UsersToRoles resultUTR = mEm.find(UsersToRoles.class, new UsersToRoles(1, 1));
    assertThat(resultUTR).isNotNull();
  }

  @Test
  public void testCompositeKeysGTR() throws Exception {
    final Role roleAdmin = mEm.find(Role.class, 1L);
    final Group groupAdmins = mEm.find(Group.class, 1L);
    final User userRoot = mEm.find(User.class, 1L);

    userRoot.addRole(roleAdmin);
    userRoot.addGroup(groupAdmins);
    groupAdmins.addRole(roleAdmin);

    persistAll(userRoot, roleAdmin, groupAdmins);
    dumpMany(GroupsToRoles.class);

    // try to find newly created instance by composite primary key
    final GroupsToRoles primaryKeyGTR = new GroupsToRoles(1, 1);
    final GroupsToRoles resultGTR = mEm.find(GroupsToRoles.class, primaryKeyGTR);

    assertThat(resultGTR).isNotNull().isEqualTo(primaryKeyGTR);
    assertThat(resultGTR.superHashCode()).isNotEqualTo(primaryKeyGTR.superHashCode());
  }

  @Test
  public void testCompositeKeysGTU() throws Exception {
    final Role roleAdmin = mEm.find(Role.class, 1L);
    final Group groupAdmins = mEm.find(Group.class, 1L);
    final User userRoot = mEm.find(User.class, 1L);

    userRoot.addRole(roleAdmin);
    userRoot.addGroup(groupAdmins);
    groupAdmins.addRole(roleAdmin);

    persistAll(userRoot, roleAdmin, groupAdmins);
    dumpMany(GroupsToUsers.class);

    final GroupsToUsers primaryKeyGTU = new GroupsToUsers(1, 1);
    final GroupsToUsers resultGTU = mEm.find(GroupsToUsers.class, primaryKeyGTU);

    assertThat(resultGTU).isNotNull();
    assertThat(resultGTU.superHashCode()).isNotEqualTo(primaryKeyGTU.superHashCode());
  }

  @Test
  public void testCompositeKeysUTR() throws Exception {
    final Role roleAdmin = mEm.find(Role.class, 1L);
    final Group groupAdmins = mEm.find(Group.class, 1L);
    final User userRoot = mEm.find(User.class, 1L);

    userRoot.addRole(roleAdmin);
    userRoot.addGroup(groupAdmins);
    groupAdmins.addRole(roleAdmin);

    persistAll(userRoot, roleAdmin, groupAdmins);
    dumpMany(UsersToRoles.class);

    // try to find newly created instance by composite primary key
    final UsersToRoles primaryKeyUTR = new UsersToRoles(1, 1);
    final UsersToRoles resultUTR = mEm.find(UsersToRoles.class, primaryKeyUTR);

    assertThat(resultUTR).isNotNull();
    assertThat(resultUTR.superHashCode()).isNotEqualTo(primaryKeyUTR.superHashCode());
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
