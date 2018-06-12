#Author: your.email@your.domain.com
@ptS4
Feature: Parts API Scenario 4
  PJ02566 - 326-Scenario 4: Check response when characteristics payload is not correct
  PJ02566 - 326-Scenario 7: Check customer response when API call is incorrect

  # The API is protected by a token
  # The API takes 2 content style header and some params
  # it should return 200 only if it can return and intelligent response
  # Any other scenario should generate an error code and helpful message
  Scenario Outline: Invalid Parts Request
    Given I have a valid <auth token> that enables access to configurable parts
    When I set the Get Request <header> to <value>
    And I set the request to an <invalid part> via the API
    Then Response will be <error> message
    
    Examples: 
      | auth token    | invalid part        | error     | header  | value   |
      | some AD group | parts_scene4_errorX | 400_MSG_X | default | default |
