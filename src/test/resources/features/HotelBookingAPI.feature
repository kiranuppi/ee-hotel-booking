@api
Feature: CoinMarketCap API
  As a CoinMarketCap API user I should be able to:
  - Convert Cryptocurrency
  - Get Cryptocurrency info
  - Filter Cryptocurrency

  @creteBooking
  Scenario Outline: Create new Hotel Room Booking
    Given I enter Firstname as "<firstname>" Surname as "<surname>" Room Price as "<price>" Deposit as "<deposit>" Check-in as "<checkin>" and Check-out as "<checkout>" details
    When I save the details
    Then new booking should be created
    Examples:
      | firstname | surname    | price | deposit | checkin    | checkout   |
      | Kiran     | Uppinkudru | 500   | true    | 2020-01-21 | 2020-01-27 |


  @deleteBooking
  Scenario: Delete booking
    Given I have a valid booking ID
    When I delete the booking
    Then Booking should be deleted
