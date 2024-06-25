Feature: Save equation functionality
  Scenario Outline: Save equation successfully
    Given the equation "<equation>"
    When I save the equation "<equation>"
    Then the equation is saved successfully

    Examples:
      | equation            |
      | 2 * x + 5 = 17      |
      | 3 * x - 4 = 8       |
      | x / 2 + 1 = 3       |
      | 5 * x = 10          |
      | 3 * x + 2 * x = 20  |
