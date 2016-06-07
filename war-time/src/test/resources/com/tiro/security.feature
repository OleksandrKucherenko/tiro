# language: en
Feature: Security

  Allows to define Roles, Groups and Users.
  Assign Many-to-Many relations for making configuration of security extra flexible.
  Allows to create set of roles from multiple sources: groups and roles.
  As result we have a easy manageable security configuration.

  Scenario: Can access user joined roles
    Given User 'admin' with email 'test@test.com'
    And Role 'root'
    And Role 'developer'
    And Group 'developers'
    And User 'admin' with assigned role 'root'
    And Group 'developers' with assigned role 'developer'
    When Assign User 'admin' to Group 'developers'
    And Persist
    Then User 'admin' has 2 joined roles
    And User 'admin' has assigned role 'developer'
    And User 'admin' has assigned role 'root'

  Scenario: Can duplicate group
    Given User 'admin' with email 'admin@test.com'
    And User 'user' with email 'user@test.com'
    And Role 'tester'
    And Role 'developer'
    And Group 'developers'
    And Group 'developers' with assigned role 'developer'
    And Group 'developers' with assigned user 'user'
    And Group 'developers' with assigned user 'admin'
    When Duplicate 'developers' with new name 'testers'
    And Assign Role 'tester' to Group 'testers'
    And Persist
    Then Group 'testers' exists
    And Group 'testers' has assigned role 'developer'
    And Group 'testers' has assigned role 'tester'

  Scenario: Can find user by email

  Scenario: Can find user by nickname

  Scenario: Can find role by name

  Scenario: Can find group by name

  Scenario: Cannot create user with existing email

  Scenario: Cannot create user with existing nickname

  Scenario: Cannot create role with existing name

  Scenario: Cannot create group with existing name

  # Disable the 'Entity' - mark entity as 'deleted' but do not drop it
  Scenario: Can disable user

  Scenario: Can disable group

  Scenario: Can recover user from 'disabled' state

  Scenario: Can recover group from 'disabled' state

  Scenario: Can find all disabled users

  Scenario: Can find all disabled groups

  # First scenario that can compromise the architecture/implementation
  Scenario: Disabled group roles are not included into user joined roles
