package com.tiro;

import com.tiro.dao.DaoFactory;
import com.tiro.dao.GroupDao;
import com.tiro.dao.RoleDao;
import com.tiro.dao.UserDao;
import com.tiro.entities.BaseEntity;
import com.tiro.entities.Group;
import com.tiro.entities.Role;
import com.tiro.entities.User;
import com.tiro.exceptions.CoreException;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

/** Implementation of steps used for testing scenarios. */
@SuppressWarnings({"unused"})
public class SecurityFeatureSteps {

  //region Members
  /** Cache of all created users. Lookup name-to-instance. */
  private Map<String, User> mUsers = new HashMap<>();
  /** Cache of all created roles. Lookup name-to-instance. */
  private Map<String, Role> mRoles = new HashMap<>();
  /** Cache of all created groups. Lookup name-to-instance. */
  private Map<String, Group> mGroups = new HashMap<>();
  /** Temporary reference on search result. */
  private BaseEntity mSearchResult;
  /** Entity manager for our DAO calls. */
  private EntityManager mEm;
  //endregion

  //region Initialization/Finalization
  @cucumber.api.java.Before
  public void setup() {
    mEm = mock(EntityManager.class);
  }

  @cucumber.api.java.After
  public void teardown() {
  }
  //endregion

  //region @Given
  @Given("^default$")
  public void _default() {
    // do nothing
  }

  @Given("^I have users:$")
  public void create_users_table(final List<NameEmail> table) {
    for (NameEmail ne : table) {
      create_user(ne.name, ne.email);
    }
  }

  @Given("^I have roles:$")
  public void create_roles_table(final List<Named> table) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
    // E,K,V must be a scalar (String, Integer, Date, enum etc)

    for (Named n : table) {
      create_role(n.name);
    }
  }

  @Given("^I have groups:$")
  public void create_groups_table(final List<Named> table) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
    // E,K,V must be a scalar (String, Integer, Date, enum etc)

    for (Named n : table) {
      create_group(n.name);
    }
  }

  @Given("^I have user '([^']*)' with email '([^']*)'$")
  public void create_user(final String name, final String email) {
    final UserDao dao = DaoFactory.get(mEm, UserDao.class);
    final User user = dao.newUser(email, name);

    mUsers.put(name, user);

    dao.forcedPersist();
  }

  @Given("^I have role '([^']*)'$")
  public void create_role(final String name) {
    mRoles.put(name, new Role(name));
  }

  @Given("^I have group '([^']*)'$")
  public void create_group(final String name) {
    mGroups.put(name, new Group(name));
  }

  @Given("^I have group '([^']*)' with assigned user '([^']*)'$")
  public void assigned_group_user(final String groupName, final String userName) {
    final Group group = mGroups.get(groupName);
    final User user = mUsers.get(userName);

    group.addUser(user);
  }

  @Given("^I have user '([^']*)' with assigned role '([^']*)'$")
  public void assigned_user_role(final String userName, final String roleName) {
    final User user = mUsers.get(userName);
    final Role role = mRoles.get(roleName);

    user.addRole(role);
  }

  @Given("^I have group '([^']*)' with assigned role '([^']*)'$")
  public void assigned_group_role(final String groupName, final String roleName) {
    final Group group = mGroups.get(groupName);
    final Role role = mRoles.get(roleName);

    group.addRole(role);
  }

  @Given("^User '([^']*)' is disabled$")
  public void disable_user(final String userName) throws CoreException {
    final User user = mUsers.get(userName);
    final UserDao dao = DaoFactory.get(mEm, UserDao.class);

    dao.disable(user);
  }

  @Given("^I have group '([^']*)' is disabled$")
  public void disable_group(final String name) throws CoreException {
    final Group group = mGroups.get(name);
    final GroupDao dao = DaoFactory.get(mEm, GroupDao.class);

    dao.disable(group);
  }
  //endregion

  //region @When
  @When("^I assign user '([^']*)' to group '([^']*)'$")
  public void assign_user_to_group(final String userName, final String groupName) {
    final User user = mUsers.get(userName);
    final Group group = mGroups.get(groupName);

    group.addUser(user);
  }

  @When("^I assign role '([^']*)' to group '([^']*)'$")
  public void assign_role_to_group(final String roleName, final String groupName) {
    final Role role = mRoles.get(roleName);
    final Group group = mGroups.get(groupName);

    group.addRole(role);
  }

  @When("^I duplicate '([^']*)' with new name '([^']*)'$")
  public void duplicate_group(final String groupName, final String newName) {
    final Group group = mGroups.get(groupName);
    final GroupDao dao = DaoFactory.get(null, GroupDao.class);

    mGroups.put(newName, dao.duplicate(group, newName));
  }

  @When("^I do persist$")
  public void persistAll() {
    // TODO: persist all on disk
  }

  @When("^I search user by email '([^']*)'$")
  public void search_user_by_email(final String email) throws CoreException {
    final UserDao dao = DaoFactory.get(mEm, UserDao.class);

    mSearchResult = dao.findByEmail(email);
  }

  @When("^I search user by nickname '([^']*)'$")
  public void search_user_by_nickname(final String name) throws CoreException {
    final UserDao dao = DaoFactory.get(mEm, UserDao.class);

    mSearchResult = dao.findByNickname(name);
  }

  @When("^I search role by name '([^']*)'$")
  public void search_role_by_name(final String name) throws CoreException {
    final RoleDao dao = DaoFactory.get(mEm, RoleDao.class);

    mSearchResult = dao.getRole(name);
  }

  @When("^I search group by name '([^']*)'$")
  public void search_group_by_name(final String name) throws CoreException {
    final GroupDao dao = DaoFactory.get(mEm, GroupDao.class);

    mSearchResult = dao.findByName(name);
  }

  @When("^I create user '([^']*)' with email '([^']*)'$")
  public void action_create_user(final String name, final String email) {
    create_user(name, email);
  }

  @When("^I create role '([^']*)'$")
  public void action_create_role(final String name) {
    create_role(name);
  }

  @When("^I create group '([^']*)'$")
  public void action_create_group(final String name) {
    create_group(name);
  }

  @When("^I disable user '([^']*)'$")
  public void action_disable_user(final String name) throws CoreException {
    final UserDao dao = DaoFactory.get(mEm, UserDao.class);
    final User user = mUsers.get(name);

    dao.disable(user);
    dao.forcedPersist();
  }

  @When("^I disable group '([^']*)'$")
  public void action_disable_group(final String name) throws CoreException {
    final GroupDao dao = DaoFactory.get(mEm, GroupDao.class);
    final Group group = mGroups.get(name);

    dao.disable(group);
    dao.forcedPersist();
  }

  @When("^I enable user '([^']*)'$")
  public void action_enable_user(final String name) throws CoreException {
    final UserDao dao = DaoFactory.get(mEm, UserDao.class);
    final User user = mUsers.get(name);

    dao.enable(user);
    dao.forcedPersist();
  }

  @When("^I enable group '([^']*)'$")
  public void action_enable_group(final String name) throws CoreException {
    final GroupDao dao = DaoFactory.get(mEm, GroupDao.class);
    final Group group = mGroups.get(name);

    dao.enable(group);
    dao.forcedPersist();
  }
  //endregion

  //region @Then
  @Then("^I should get user '([^']*)' with (\\d+) joined roles$")
  public void validate_user_has_roles_quantity(final String userName, final int number) {
    final User user = mUsers.get(userName);

    assertThat(user.getJoinedRoles()).hasSize(number);
  }

  @Then("^I should get user '([^']*)' with role '([^']*)'$")
  public void validate_user_has_role(final String userName, final String roleName) {
    final User user = mUsers.get(userName);
    final Role role = mRoles.get(roleName);

    assertThat(user.getJoinedRoles()).contains(role);
  }

  @Then("^I should see group '([^']*)'$")
  public void validate_group_exists(final String groupName) {
    assertThat(mGroups).containsKey(groupName);
  }

  @Then("^I should get group '([^']*)' with role '([^']*)'$")
  public void validate_group_has_role(final String groupName, final String roleName) {
    final Group group = mGroups.get(groupName);
    final Role role = mRoles.get(roleName);

    assertThat(group.getRoles()).contains(role);
  }

  @Then("^it should fail with unhandled exception$")
  public void validate_fail() {
    throw new PendingException();
  }

  @Then("^I should get user '([^']*)'$")
  public void validate_search_result_user(final String name) {
    throw new PendingException();
  }

  @Then("^I should get role '([^']*)'$")
  public void validate_search_result_role(final String name) {
    throw new PendingException();
  }

  @Then("^I should get group '([^']*)'$")
  public void validate_search_result_group(final String name) {
    throw new PendingException();
  }

  @Then("^I should get disabled user '([^']*)'$")
  public void validate_disabled_user(final String name) {
    throw new PendingException();
  }

  @Then("^I should get disabled group '([^']*)'$")
  public void validate_disabled_group(final String name) {
    throw new PendingException();
  }

  @Then("^I should get enabled user '([^']*)'$")
  public void validate_enabled_user(final String name) {
    throw new PendingException();
  }

  @Then("^I should get enabled group '([^']*)'$")
  public void validate_enabled_group(final String name) {
    throw new PendingException();
  }
  //endregion

  //region Nested declarations
  static class NameEmail {
    public String name;
    public String email;
  }

  static class Named {
    public String name;
  }
  //endregion
}
