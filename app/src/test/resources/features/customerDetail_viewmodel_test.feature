Feature: Customer Detail ViewModel

  Scenario: Load an existing customer successfully
    Given a customer with ID 1 named "Alice" exists in the repository
    When I load the customer with ID 1
    Then the detail state is Success and displays the name "Alice"

  Scenario: Load a non-existent customer
    When I load the customer with ID 999
    Then the detail state is Error with the message "Client introuvable"