@frontEnd
Feature: HP Website Validation


  Background: For all scenarios user is on the HP homepage
    Given user is on home page
    When user clicks to About H&P
    And user clicks to the Careers
#About hp link
  Scenario: Validate Right Navigation Bar
    Then validate following info displayed on right navbar
    |OVERVIEW|
    |BENEFITS|
    |TRAINING & DEVELOPMENT|
    |LET’S GO FAR, TOGETHER|
    |WHO WE ARE|


    Scenario: Validate list of open jobs api and DB
      When user navigates to the APPLY NOW
      And user enters "Hotshot Driver" into the search box
      And user clicks the Search button
      #Then user list of jobs
      And user retrieves the actual list of open jobs from the database
      Then validate the actual result with the data retrieved form the database
#APPLY NOW button
#DDT
 # Scenario Outline:
 #   Examples:


