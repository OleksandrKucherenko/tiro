package com.tiro;

import com.tiro.entities.Group;
import com.tiro.entities.Role;
import com.tiro.entities.User;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/** Implementation of steps used for testing scenarios. */
public class FeatureSteps {

  private Map<String,User> mUsers = new HashMap<>();

  private Map<String, Role> mRoles = new HashMap<>();

  private Map<String, Group> mGroups = new HashMap<>();

  @Given("^User '([^']*)' with email '([^']*)'$")
  public void create_user(final String name, final String email) {
    mUsers.put(name, new User(email, name));
  }

  @Given("^Role '([^']*)'$")
  public void create_role(final String name){
    mRoles.put(name, new Role(name));
  }

  @Given("^Group '([^']*)'$")
  public void create_group(final String name){
    mGroups.put(name, new Group(name));
  }

  @Given("^User '([^']*)' with assigned role '([^']*)'$")
  public void assigned_user_role(final String userName, final String roleName){
    final User user = mUsers.get(userName);
    final Role role = mRoles.get(roleName);

    user.addRole(role);
  }

  @Given("^Group '([^']*)' with assigned role '([^']*)'$")
  public void assigned_group_role(final String groupName, final String roleName){
    final Group group = mGroups.get(groupName);
    final Role role = mRoles.get(roleName);

    group.addRole(role);
  }

  @When("^Assign User '([^']*)' to Group '([^']*)'$")
  public void assign_user_to_group(final String userName, final String groupName){
    final User user = mUsers.get(userName);
    final Group group = mGroups.get(groupName);

    group.addUser(user);
  }

  @When("^Persist$")
  public void persistAll(){
    // TODO: persist all on disk
  }

  @Then("^User '([^']*)' has (\\d+) joined roles$")
  public void validate_user_has_roles_quantity(final String userName, final int number){
    final User user = mUsers.get(userName);

    assertThat(user.getJoinedRoles()).hasSize(number);
  }

  @Then("^User '([^']*)' has assigned role '([^']*)'$")
  public void validate_user_has_role(final String userName, final String roleName){
    final User user = mUsers.get(userName);
    final Role role = mRoles.get(roleName);

    assertThat(user.getJoinedRoles()).contains(role);
  }
}
