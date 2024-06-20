Feature:Job Search API Verification

  @dataBase
  Scenario: Verify Job Search API Response
    Given Accept header is "application/json"
    And Request Content Type header is "application/json"
    And Request body contains the following information:
      | appliedFacets |       |
      | limit         | 20    |
      | offset        | 0     |
      | searchText    | Tyler |
    When I send Post request to the "/wday/cxs/hpinc/hpinc/jobs" endpoint
    Then the status code should be 200
    And Response Content type header should be "application/json"
    When I retrieve information from the database
    Then the API response and database information should be equal

