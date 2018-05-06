package ro.vichim.test.testdoubles.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ro.vichim.testdoubles.model.Reservation;
import ro.vichim.testdoubles.model.RoomOccupancyPolicy;

public class RoomOccupancyPolicyTest {

	@Test
	public void allowsReservationOfSmallRoomForOnePerson() {
		Reservation reservation = new ReservationBuilder().small().forOnePerson().build();
		assertTrue(new RoomOccupancyPolicy().isAllowed(reservation));
	}

	@Test
	public void allowsReservationOfSmallRoomForManyPeople() {
		Reservation reservation = new ReservationBuilder().small().forManyPeople().build();
		assertTrue(new RoomOccupancyPolicy().isAllowed(reservation));
	}

	@Test
	public void allowsReservationOfBigRoomForManyPeople() {
		Reservation reservation = new ReservationBuilder().big().forManyPeople().build();
		assertTrue(new RoomOccupancyPolicy().isAllowed(reservation));
	}

	@Test
	public void disallowsReservationOfBigRoomForOnePerson() {
		Reservation reservation = new ReservationBuilder().big().forOnePerson().build();
		assertFalse(new RoomOccupancyPolicy().isAllowed(reservation));
	}

}
