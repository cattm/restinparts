#Author: your.email@your.domain.com
Feature: Parts Load Scenario 3
  PJ02566-315-Scenario 3: Check Consistency

  # the repeat requests should receive identical response payloads
  # just call a page then call it again and make sure it is identical
  Scenario Outline: Valid Parts API access
    Then multiple requests with the <characteristic> will yeild identical response

    Examples: 
      characteristic   |

      | CFILTER_1_RECORD |
      | CFILTER_4_PAGE   |
