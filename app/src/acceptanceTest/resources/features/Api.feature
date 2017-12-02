Feature: API

  As a API user, I want to interact with

  Scenario: Request movies

    Given the movie "The Avengers" exists

    When the user makes a request for movies

    Then the movie "The Avengers" are shown on response