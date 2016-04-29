# References

## Cucumber for JVM

- https://github.com/cucumber/cucumber-jvm/tree/master/examples/java-gradle
- https://github.com/dkowis/cucumber-jvm-groovy-example
- https://github.com/cucumber/cucumber/wiki/Gherkin
- https://github.com/cucumber/cucumber/wiki/Given-When-Then
- http://www.hascode.com/2014/12/bdd-testing-with-cucumber-java-and-junit/

### Test Scenario Syntax

```
 1: Feature: Some terse yet descriptive text of what is desired
 2:   Textual description of the business value of this feature
 3:   Business rules that govern the scope of the feature
 4:   Any additional information that will make the feature easier to understand
 5:
 6:   Scenario: Some determinable business situation
 7:     Given some precondition
 8:       And some other precondition
 9:      When some action by the actor
10:       And some other action
11:       And yet another action
12:      Then some testable outcome is achieved
13:       And something else we can check happens too
14:
15:   Scenario: A different situation
16:       ...
```