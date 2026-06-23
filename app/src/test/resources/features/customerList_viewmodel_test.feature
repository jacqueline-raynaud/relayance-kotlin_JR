Feature: Customer List ViewModel

  Scenario: The list loads successfully
    Given a repository containing 2 customers
    When I load the customer list
    Then the list state is Success with 2 customers

  Scenario: Loading fails
    Given a repository that fails to return customers
    When I load the customer list
    Then the list state is Error