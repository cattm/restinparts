#Author: Marcus.Catt@arm.com
@plS1
Feature: Parts Load Scenario 1
  PJO2566-315 - Scenario 1: Check can access system and update Configurable Parts

  # The API is not currently protected by an access token or login
  # The API takes 2 content style header and a JSON body
  # it should return 200 only for records it has correctly written to the DB
  # Any other scenario should generate an error code and helpful message
  # There are 2 arrays in the JSON payload
  # Array of parts to update
  # for the part an array of characteristics
  # the key files in the partId
  
  
  # TODO: various empty payload (where valid) scenarios - some of the fields ar optional 
  Scenario Outline: Valid Parts Load
    Given I am correctly identified in AD <service group> name
    When I send valid configurable <part> items via the API
    Then I will modify the Parts repository

    Examples: 
      | service group | part                                    |
      | some AD group | partsload_api_scene_1_default           |
      | some AD group | partsload_api_scene_1_single_part       |
      | some AD group | partsload_api_scene_1_single_part_range |
      | some AD group | partsload_api_scene_1_multi_multi_1     |
      | some AD group | partsload_api_scene_1_multi_multi_2     |
