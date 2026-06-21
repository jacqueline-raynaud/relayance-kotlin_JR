#language: en
Feature: Ajout d'un client
  Afin d'enregistrer un nouveau client dans le CRM
  En tant qu'utilisateur
  Je veux remplir un formulaire et valider l'ajout

  Scenario: Successfully added with a valid email address
    Given an empty customer addition form
    When I enter the name "Jean Dupont" and the email "jean.dupont@example.com"
    And I confirm the addition
    Then The client "Jean Dupont" is present in the list of clients
    And No email errors are displayed

  Scenario: Addition refused due to invalid email format
    Given an empty customer addition form
    When I enter the name "Marie Martin" and the email "marie.martin.example.com"
    And I confirm the addition
    Then An email error is displayed
    And The customer list remains unchanged