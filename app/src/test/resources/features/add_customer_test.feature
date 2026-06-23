#language: en
Feature: Add cutomer
  to register a new customer in the CRM
  As a user
  I want to fill out a form and confirm the addition

  Scenario: Successfully added with a valid email address
    Given an empty customer addition form
    When I enter the name "Jean Dupont" and the email "jean.dupont@test.com"
    And I confirm the addition
    Then The client "Jean Dupont" is present in the list of clients
    And No email errors are displayed

  Scenario: Addition refused due to invalid email format
    Given an empty customer addition form
    When I enter the name "Marie Martin" and the email "marie.martin.test.com"
    And I confirm the addition
    Then An email error is displayed
    And The customer list remains unchanged

  Scenario: Addition refused due to empty name
    Given an empty customer addition form
    When I enter the name "   " and the email "bob@test.com"
    And I confirm the addition
    Then A name error is displayed
    And The customer list remains unchanged
