package ro.vichim.testdoubles.service;

import ro.vichim.testdoubles.model.Reservation;

public interface ReservationRepository {

	/**
	 * @return the reservation in conflict with the requested reservation
	 */
	public Reservation findSimilar(Reservation reservation);

	public void store(Reservation reservation);

}
