#Author: marcus.catt@arm.com
  @ptS1
Feature: Parts API Scenario 1
  PJO2566-326 - Scenario 1: Check Configurable Products available for download

  # the characteristic filter is a list of name:value pairs
  # the name is "key" the value is "value in the object values array
  # the partId is returnes together with a link etc

  Scenario Outline: Valid Parts API access
    Given I have a valid <auth token> that enables access to configurable parts
    When I request access to configurable parts that exist with a valid <characteristic>
    Then I am able to view and <validate> configurable parts for download successfully

    Examples: 
      | auth token | characteristic   | validate                      |
      | name1      | CFILTER_1_RECORD | parts_api_scene_1_1_record    |
      | name1      | CFILTER_3_RECORD | parts_api_scene_1_3_records   |
      | name1      | CFILTER_5_RECORD | parts_api_scene_1_5_records   |
      | name1      | CFILTER_6_RECORD | parts_api_scene_1_6_records   |
      | name1      | CFILTER_4_PAGE   | parts_api_scene_4_20_records  |
      | name1      | CFILTER_16_PAGE  | parts_api_scene_16_80_records |


  Scenario Outline: Valid Parts API access
    Given I have a valid <auth token> that enables access to configurable parts
    When I request access to configurable parts that exist with a valid <characteristic>
    Then I am able to select <page> of the page options provided
    And I can <validate> the page loaded

    Examples: 
      | auth token | characteristic   | page | validate         |
      | name1      | CFILTER_5_RECORD | self | parts_api_1_self |
      | name1      | CFILTER_4_PAGE   | last | parts_api_4_last |
      | name1      | CFILTER_4_PAGE   | next | parts_api_4_next |
      | name1      | CFILTER_16_PAGE  |    8 | parts_api_16_8   |
      | name2      | CFILTER_16_PAGE  |   15 | parts_api_16_15  |
