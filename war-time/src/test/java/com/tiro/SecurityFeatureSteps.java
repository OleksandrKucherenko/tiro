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
import com.tiro.schema.Tables;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/** Implementation of steps used for testing scenarios. */
@SuppressWarnings({"unused"})
public class SecurityFeatureSteps {

  /** Unit test logger. */
  protected static final Logger _log = LoggerFactory.getLogger(Consts.TAG);
  /** JPA factory. */
  private static EntityManagerFactory _factory;

  //region Members
  /** Cache of all created users. Lookup name-to-instance. */
  private final Map<String, User> mUsers = new HashMap<>();
  /** Cache of all created roles. Lookup name-to-instance. */
  private final Map<String, Role> mRoles = new HashMap<>();
  /** Cache of all created groups. Lookup name-to-instance. */
  private final Map<String, Group> mGroups = new HashMap<>();
  /** Temporary reference on search result. */
  private BaseEntity mSearchResult;
  /** Entity manager for our DAO calls. */
  private EntityManager mEm;
  /** Captured Exception. */
  private Throwable mException;
  //endregion

  //region Initialization/Finalization
  @cucumber.api.java.Before
  public void setup() {
    if (null == _factory) {
      _factory = Persistence.createEntityManagerFactory("sqlite");
    }

    mEm = _factory.createEntityManager();
    mException = null;
    mSearchResult = null;
  }

  @cucumber.api.java.After
  public void teardown() {

    // clean DB
    mEm.getTransaction().begin();

    final String sqlDrop = "DELETE FROM ";
    final String sqlPrefix = "";

    mEm.createNativeQuery(sqlDrop + Tables.GROUPS_TO_USERS + sqlPrefix).executeUpdate();
    mEm.createNativeQuery(sqlDrop + Tables.USERS_TO_ROLES + sqlPrefix).executeUpdate();
    mEm.createNativeQuery(sqlDrop + Tables.GROUPS_TO_ROLES + sqlPrefix).executeUpdate();

    mEm.createNativeQuery(sqlDrop + Tables.USERS + sqlPrefix).executeUpdate();
    mEm.createNativeQuery(sqlDrop + Tables.ROLES + sqlPrefix).executeUpdate();
    mEm.createNativeQuery(sqlDrop + Tables.GROUPS + sqlPrefix).executeUpdate();

    mEm.getTransaction().commit();
  }
  //endregion

  //region @Given
  @Given("^default$")
  public void _default() {
    // do nothing
  }

  @Given("^I have users:$")
  public void create_users_table(final List<NameEmail> table) throws CoreException {
    for (NameEmail ne : table) {
      create_user(ne.name, ne.email);
    }
  }

  @Given("^I have roles:$")
  public void create_roles_table(final List<Named> table) throws CoreException {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
    // E,K,V must be a scalar (String, Integer, Date, enum etc)

    for (Named n : table) {
      create_role(n.name);
    }
  }

  @Given("^I have groups:$")
  public void create_groups_table(final List<Named> table) throws CoreException {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
    // E,K,V must be a scalar (String, Integer, Date, enum etc)

    for (Named n : table) {
      create_group(n.name);
    }
  }

  @Given("^I have user '([^']*)' with email '([^']*)'$")
  public void create_user(final String name, final String email) throws CoreException {
    final UserDao dao = DaoFactory.get(mEm, UserDao.class);
    final User user = dao.newUser(email, name);

    mUsers.put(name, user);

    dao.include(user);
    dao.forcedPersist();
  }

  @Given("^I have role '([^']*)'$")
  public void create_role(final String name) throws CoreException {
    final RoleDao dao = DaoFactory.get(mEm, RoleDao.class);
    final Role role = new Role(name);

    mRoles.put(name, role);

    dao.include(role);
    dao.forcedPersist();
  }

  @Given("^I have group '([^']*)'$")
  public void create_group(final String name) throws CoreException {
    final GroupDao dao = DaoFactory.get(mEm, GroupDao.class);
    final Group group = new Group(name);

    mGroups.put(name, group);

    dao.include(group);
    dao.forcedPersist();
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
    final GroupDao dao = DaoFactory.get(mEm, GroupDao.class);

    mGroups.put(newName, dao.duplicate(group, newName));
  }

  @When("^I do persist$")
  public void persistAll() {
    mEm.getTransaction().begin();

    for (final Role role : mRoles.values()) {
      mEm.persist(role);
    }

    for (final User user : mUsers.values()) {
      mEm.persist(user);
    }

    for (final Group group : mGroups.values()) {
      mEm.persist(group);
    }

    mEm.getTransaction().commit();
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
    try {
      create_user(name, email);
    } catch (final Throwable ignored) {
      mException = ignored;
    }
  }

  @When("^I create role '([^']*)'$")
  public void action_create_role(final String name) {
    try {
      create_role(name);
    } catch (final Throwable ignored) {
      mException = ignored;
    }
  }

  @When("^I create group '([^']*)'$")
  public void action_create_group(final String name) {
    try {
      create_group(name);
    } catch (final Throwable ignored) {
      mException = ignored;
    }
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
    assertThat(mException).isNotNull().isInstanceOf(CoreException.class);
  }

  @Then("^I should get user '([^']*)'$")
  public void validate_search_result_user(final String name) throws CoreException {
    assertThat(mSearchResult).isInstanceOf(User.class);

    final User user = (User) mSearchResult;

    assertThat(user).isNotNull();
    assertThat(user.getNickName()).isEqualTo(name);
  }

  @Then("^I should get role '([^']*)'$")
  public void validate_search_result_role(final String name) {
    assertThat(mSearchResult).isInstanceOf(Role.class);

    final Role role = (Role) mSearchResult;

    assertThat(role.getName()).isEqualTo(name);
  }

  @Then("^I should get group '([^']*)'$")
  public void validate_search_result_group(final String name) {
    assertThat(mSearchResult).isInstanceOf(Group.class);

    final Group group = (Group) mSearchResult;

    assertThat(group.getName()).isEqualTo(name);
  }

  @Then("^I should get disabled user '([^']*)'$")
  public void validate_disabled_user(final String name) {
    final User user = mUsers.get(name);

    assertThat(user).isNotNull();
    assertThat(user.isDisabled()).isTrue();
  }

  @Then("^I should get disabled group '([^']*)'$")
  public void validate_disabled_group(final String name) {
    final Group group = mGroups.get(name);

    assertThat(group).isNotNull();
    assertThat(group.isDisabled()).isTrue();
  }

  @Then("^I should get enabled user '([^']*)'$")
  public void validate_enabled_user(final String name) {
    final User user = mUsers.get(name);

    assertThat(user).isNotNull();
    assertThat(user.isDisabled()).isFalse();
  }

  @Then("^I should get enabled group '([^']*)'$")
  public void validate_enabled_group(final String name) {
    final Group group = mGroups.get(name);

    assertThat(group).isNotNull();
    assertThat(group.isDisabled()).isFalse();
  }

  @Then("^I should get (\\d+) disabled group\\(s\\)$")
  public void validate_disabled_groups_size(final int size) throws CoreException {
    final GroupDao dao = DaoFactory.get(mEm, GroupDao.class);
    final Set<Group> groups = dao.findAllDisabled();

    assertThat(groups).isNotNull().hasSize(size);
  }

  @Then("^I should get (\\d+) enabled group\\(s\\)$")
  public void validate_enabled_groups_size(final int size) throws CoreException {
    final GroupDao dao = DaoFactory.get(mEm, GroupDao.class);
    final Set<Group> groups = dao.findAllEnabled();

    assertThat(groups).isNotNull().hasSize(size);
  }

  @Then("^I should get (\\d+) disabled user\\(s\\)$")
  public void validate_disabled_users_size(final int size) throws CoreException {
    final UserDao dao = DaoFactory.get(mEm, UserDao.class);
    final Set<User> users = dao.findAllDisabled();

    assertThat(users).isNotNull().hasSize(size);
  }

  @Then("^I should get (\\d+) enabled user\\(s\\)$")
  public void validate_enabled_users_size(final int size) throws CoreException {
    final UserDao dao = DaoFactory.get(mEm, UserDao.class);
    final Set<User> users = dao.findAllEnabled();

    assertThat(users).isNotNull().hasSize(size);
  }

  @Then("^I should get user '([^']*)' with (\\d+) joined role\\(s\\)$")
  public void validate_user_joined_roles(final String userName, final int size) throws CoreException {
    final UserDao dao = DaoFactory.get(mEm, UserDao.class);
    final User user = dao.findByNickname(userName);

    assertThat(user).isNotNull();
    assertThat(user.getJoinedRoles()).hasSize(size);

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
