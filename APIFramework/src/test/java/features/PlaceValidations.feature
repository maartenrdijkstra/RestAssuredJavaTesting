Feature: Validating Place API's

  @AddPlace @Regression
  Scenario Outline: Verify if Place being successfully added using AddPlaceAPI
    Given Add Place Payload with "<name>" "<language>" "<address>"
    When User calls "ADD_PLACE_API" with "Post" http request
    Then The API call is successful with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And Verify whether place_Id maps to "<name>" using "GET_PLACE_API"

    Examples:
    |name     | language  | address             |
    |AAHouse  | English   | World Cross Center  |
    |BBHouse  | Spanish   | Sea Cross Center    |

    @DeletePlace @Regression
    Scenario: Verify if Delete Place Functionality is working
      Given DeletePlace Payload
      When User calls "DELETE_PLACE_API" with "POST" http request
      Then The API call is successful with status code 200
      And "status" in response body is "OK"


