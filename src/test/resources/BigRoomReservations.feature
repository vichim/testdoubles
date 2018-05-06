Feature: BigRoomReservations 
	Meeting room size and number of people decide reservation success 

Scenario: Many people can reserve big rooms
	Given a big room
	When I request a reservation for 2 persons 
	Then the reservation is accepted