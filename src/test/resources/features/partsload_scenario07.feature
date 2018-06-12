#Author: your.email@your.domain.com
Feature: Parts Load Scenario 7
  PJ02566-315-Scenario 7: API Minor Version
  PJ02566-315-Scenario 7: API Minor Version

  Scenario Outline: 
    Given The current api version is <version>
    When the required version in the request is set to <request value>
    Then I will get a response <response>

    Examples: 
      | version | request value | response      |
      | 1.0.0   | 1.0.0         | correct_200   |
      | 1.0.0   | 0.0.0         | incorrect_415 |
