package ro.vichim.test.testdoubles.model;

import ro.vichim.testdoubles.model.MeetingRoom;
import ro.vichim.testdoubles.model.Reservation;
import ro.vichim.testdoubles.model.RoomLayout;

public class ReservationBuilder {

	private String applicant = "Scott";
	private RoomLayout roomLayout = RoomLayout.SMALL;
	private int noOfPeople = 1;

	public ReservationBuilder small() {
		this.roomLayout = RoomLayout.SMALL;
		return this;
	}

	public ReservationBuilder big() {
		this.roomLayout = RoomLayout.BIG;
		return this;
	}

	public ReservationBuilder forOnePerson() {
		this.noOfPeople = 1;
		return this;
	}

	public ReservationBuilder forManyPeople() {
		this.noOfPeople = 10;
		return this;
	}

	public Reservation build() {
		return new Reservation(applicant, new MeetingRoom("Colosseum", roomLayout), noOfPeople);
	}

}
