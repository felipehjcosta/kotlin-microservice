Feature: API

  Scenario: Get movies based on request

    Given a "The Avengers" movie

    When request for a marvel movie

    Then I should be "The Avengers"