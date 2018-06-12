#Author: marcus.catt@arm.com
@ptS10
Feature: Parts API Scenario 8
  PJ02566-326-Scenario 10: API Minor Version
  PJ02566-326-Scenario 11: API Major Version

  # working call returns latest API version - no api version in header
  # set api- version to correct value
  # then abuse with major and minor corruptions
  # for invalid scenarios the code should return a 415 error, a useful message and set the response header (api-version) to 1.0.0


  Scenario Outline: 
    Given I have a valid <token> that enables access to configurable parts
    And The current api version is <version>
    When the required version in the request is set to <request value>
    Then I will get a response <response>

    Examples: 
      | toke  | version | request value | response      |
      | name1 | 1.0.0   | 1.0.0         | correct_200   |
      | name1 | 1.0.0   | 0.0.0         | incorrect_415|
#      | name1 | 1.0.0   | 0.9.9         | incorrect_415 |
#      | name1 | 1.0.0   | 1.0.1         | incorrect_415 |
#      | name1 | 1.0.0   | 1.A.B         | incorrect_415 |
