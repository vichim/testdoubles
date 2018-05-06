package ro.vichim.testdoubles.service;

import ro.vichim.testdoubles.model.Reservation;
import ro.vichim.testdoubles.model.RoomOccupancyPolicy;

public class ReservationService {

	private AuthorizationService authService;
	private ReservationRepository reservationRepository;

	public ReservationService(AuthorizationService authService, ReservationRepository reservationRepository) {
		this.authService = authService;
		this.reservationRepository = reservationRepository;
	}

	/**
	 * @return true if the reservation is accepted
	 */
	public boolean bookRoom(Reservation reservation) {
		if (!authService.authorizesAccess(reservation.getApplicant(), reservation.getRoom().getName())) {
			return false;
		}
		if (reservationRepository.findSimilar(reservation) != null) {
			return false;
		}
		if (!new RoomOccupancyPolicy().isAllowed(reservation)) {
			return false;
		}
		reservationRepository.store(reservation);

		return true;
	}
}
