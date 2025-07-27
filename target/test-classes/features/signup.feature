Feature: Magento Account Creation and Login

  Scenario: Create a new account
    Given I am on the Magento home page
    When I navigate to the Create Account page
    And I fill in the account details and submit
    Then I should see the account dashboard
