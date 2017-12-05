Feature: API

  As a API user, I want to interact with

  Scenario: Request movies

    Given the movie "The Avengers" exists

    When the user makes a request for movies

    Then the movie "The Avengers" are shown on response

  Scenario: Insert movie

    When insert movie with title "The Avengers"

    Then the response should contains an id

  Scenario: Edit movie

    Given the movie "The Avengers" exists

    When the user changes this movie name to "The Avengers 2"

    Then the movie name changed to "The Avengers 2"