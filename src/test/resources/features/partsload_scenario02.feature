#Author: Marcus.Catt@arm.com
@plS2
Feature: Parts Load Scenario 2
  PJO2566-315 - Scenario 2: Check malformed payload does not update Configurable Parts

  # The API is not currently protected by an access token or login
  # The API takes 2 content style header and a JSON body
  # it should return 200 only for records it has correctly written to the DB
  # Any other scenario should generate an error code and helpful message
  # There are 2 arrays in the JSON payload
  # Array of parts to update
  # for the part an array of characteristics
  # the key files in the partId
  Scenario Outline: Invalid Parts Load
    Given I am correctly identified in AD <service group> name
    When I set the Post Request <header> to <value>
    And I send invalid configurable <part> items via the API
    Then I will recieve an appropriate <error> message

    Examples: 
      | service group | part                                     | error     | header       | value               |
      | some AD group | partsload_api_scene_2_no_body            | 400_MSG_1 | default      | default             |
      | some AD group | partsload_api_scene_2_bad_content_header | 415_MSG_4 | Content-Type | application/xop+xml |
      | some AD group | partsload_api_scene_2_bad_accept_header  | 406_MSG_3 | Accept       | application/xml     |
      | some AD group | partsload_api_scene_2_no_headers         | 415_MSG_5 | none         | none                |
      | some AD group | partsload_api_scene_2_bad_content_1      | 400_MSG_2 | default      | default             |
      | some AD group | partsload_api_scene_2_bad_content_2      | 400_MSG_7 | default      | default             |
      | some AD group | partsload_api_scene_2_bad_content_3      | 400_MSG_8 | default      | default             |
