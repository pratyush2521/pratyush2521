
Feature: Navigating in Google Maps

  Scenario: Navigating with directions in Google Maps
    Given I launch the "edge" browser
    And I naviate to the URL "https://maps.google.com/"
    When I search for "San Francisco, California" location 
    Then I assert the coordinates of the location to be "37.7577627,-122.4726194" 
    And I hit the "Directions" button
    And I enter "Chico, California" to be the starting point
    And I hit the "Driving" button
    And I verify the routes
    And I print the various route details in "routes.txt"
    And I close the browser
