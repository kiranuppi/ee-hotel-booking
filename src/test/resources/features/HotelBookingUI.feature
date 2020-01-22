@ui
Feature: Hotel Booking and delete Operations

  Background:
    Given I am on Hotel Booking Home page

  @create
  Scenario Outline: Create a new room Booking
    Given I enter Firstname as "<firstname>" Surname as "<surname>" Price as "<price>" Deposit as "<deposit>" Check-in date as "<checkin>" and Check-out date as "<checkout>" details
    When I Click on save button
    Then new booking is created
  Examples:
  | firstname | surname    | price | deposit | checkin    | checkout   |
  | Fname     | Lname | 1500   | false    | 2020-01-21 | 2020-01-27 |


  @deleteBooking
  Scenario: Delete booking
    When I click on delete button
    Then Booking is deleted
