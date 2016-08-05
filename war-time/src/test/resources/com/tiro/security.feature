# language: en
# noinspection CucumberUndefinedStep
Feature: Security

  Allows to define Roles, Groups and Users.
  Assign Many-to-Many relations for making configuration of security extra flexible.
  Allows to create set of roles from multiple sources: groups and roles.
  As result we have a easy manageable security configuration.

  Background: System initialized with a minimal set of not joined entities
    Given I have users:
      | name      | email          |
      | admin     | admin@test.com |
      | developer | dev@test.com   |
      | tester    | test@test.com  |
      | user      | user@test.com  |
    And I have roles:
      | name      |
      | root      |
      | developer |
      | tester    |
      | user      |
    And I have groups:
      | name       |
      | admins     |
      | developers |
      | testers    |
      | users      |
    And I do persist

  Scenario: Can access user joined roles
    Given I have user 'admin' with assigned role 'root'
    And I have group 'developers' with assigned role 'developer'
    When I assign user 'admin' to group 'developers'
    And I do persist
    Then I should get user 'admin' with 2 joined roles
    And I should get user 'admin' with role 'developer'
    And I should get user 'admin' with role 'root'

  Scenario: Can duplicate group
    Given I have group 'developers' with assigned role 'developer'
    And I have group 'developers' with assigned user 'user'
    And I have group 'developers' with assigned user 'admin'
    When I duplicate 'developers' with new name 'newgroup'
    And I assign role 'tester' to group 'newgroup'
    And I do persist
    Then I should see group 'newgroup'
    And I should get group 'newgroup' with role 'developer'
    And I should get group 'newgroup' with role 'tester'

  Scenario: Can find user by email
    Given default
    When I search user by email 'admin@test.com'
    Then I should get user 'admin'

  Scenario: Can find user by nickname
    Given default
    When I search user by nickname 'user'
    Then I should get user 'user'

  Scenario: Can find role by name
    Given default
    When I search role by name 'root'
    Then I should get role 'root'

  Scenario: Can find group by name
    Given default
    When I search group by name 'developers'
    Then I should get group 'developers'

  Scenario: Cannot create user with existing email
    Given default
    When I create user 'tester' with email 'admin@test.com'
    Then it should fail with unhandled exception

  Scenario: Cannot create user with existing nickname
    Given default
    When I create user 'admin' with email 'tester@test.com'
    Then it should fail with unhandled exception

  Scenario: Cannot create role with existing name
    Given default
    When I create role 'root'
    Then it should fail with unhandled exception

  Scenario: Cannot create group with existing name
    Given default
    When I create group 'developers'
    Then it should fail with unhandled exception

  # Disable the 'Entity' - mark entity as 'deleted' but do not drop it
  Scenario: Can disable user
    Given default
    When I disable user 'admin'
    Then I should get disabled user 'admin'

  Scenario: Can disable group
    Given default
    When I disable group 'admins'
    Then I should get disabled group 'admins'

  Scenario: Can recover user from 'disabled' state
    Given default
    And User 'admin' is disabled
    When I enable user 'admin'
    Then I should get enabled user 'admin'

  Scenario: Can recover group from 'disabled' state
    Given default
    And I have group 'admins' is disabled
    When I enable group 'admins'
    Then I should get enabled group 'admins'

  Scenario: Can find all disabled users
    Given default
    When I disable user 'admin'
    Then I should get 1 disabled user(s)
    And I should get 3 enabled user(s)

  Scenario: Can find all disabled groups
    Given default
    When I disable group 'admins'
    Then I should get 1 disabled group(s)
    And I should get 3 enabled group(s)

  Scenario: Disabled group roles are not included into user joined roles, part 1
    Given default
    And I have group 'developers' with assigned role 'developer'
    And I have group 'testers' with assigned role 'tester'
    And I have group 'developers' with assigned user 'user'
    And I have group 'testers' with assigned user 'user'
    When I disable group 'developers'
    Then I should get user 'user' with 1 joined role(s)

  Scenario: Disabled group roles are not included into user joined roles, part 2
    Given default
    And I have user 'user' with assigned role 'root'
    And I have group 'developers' with assigned role 'developer'
    And I have group 'testers' with assigned role 'tester'
    And I have group 'developers' with assigned user 'user'
    And I have group 'testers' with assigned user 'user'
    When I disable group 'developers'
    Then I should get user 'user' with 2 joined role(s)