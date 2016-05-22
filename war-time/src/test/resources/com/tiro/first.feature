Feature: User Joined Roles
  Allows to create set of roles from multiple sources: groups and roles.
  As result we have a easy manageable security configuration.

  Scenario: Compose User Joined Roles
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