#Author: marcus.catt@arm.com
@ptS8
Feature: Parts API Scenario 8
  PJ02566-326-Scenario 8: Check Response Consistency

  # the repeat requests should receive identical response payloads
  # just call a page then call it again and make sure it is identical
  Scenario Outline: Valid Parts API access
    Given I have a valid <auth token> that enables access to configurable parts
    Then multiple requests with the <characteristic> will yeild identical response

    Examples: 
      | auth token | characteristic   |
      | name1      | CFILTER_1_RECORD |
      | name1      | CFILTER_4_PAGE   |
