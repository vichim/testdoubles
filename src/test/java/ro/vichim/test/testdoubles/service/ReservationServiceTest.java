package ro.vichim.test.testdoubles.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ro.vichim.test.testdoubles.model.ReservationBuilder;
import ro.vichim.testdoubles.model.Reservation;
import ro.vichim.testdoubles.service.AuthorizationService;
import ro.vichim.testdoubles.service.ReservationRepository;
import ro.vichim.testdoubles.service.ReservationService;

public class ReservationServiceTest {

	@Test
	public void acceptsValidReservation() {
		Reservation reservation = new ReservationBuilder().build();
		
		AuthorizationService authService = new AuthorizationService() {
			@Override
			public boolean authorizesAccess(String username, String resource) {
				return true;
			}
		};
		
		ReservationService sut = new ReservationService(authService , emptyRepository());
		
		assertTrue(sut.bookRoom(reservation));
	}
	
	@Test
	public void storesValidReservation() {
		Reservation reservation = new ReservationBuilder().build();

		AuthorizationService authService = new AuthorizationService() {
			@Override
			public boolean authorizesAccess(String username, String resource) {
				return true;
			}
		};
		ReservationRepositorySpy repositorySpy = new ReservationRepositorySpy();

		ReservationService sut = new ReservationService(authService, repositorySpy);
		
		sut.bookRoom(reservation);

		assertEquals(reservation, repositorySpy.getStoredReservation());
	}

	@Test
	public void rejectsReservationFromUnauthorizedUser() {
		Reservation reservation = new ReservationBuilder().build();

		AuthorizationService authService = new AuthorizationService() {
			@Override
			public boolean authorizesAccess(String username, String resource) {
				return false;
			}
		};

		ReservationService sut = new ReservationService(authService, null);

		assertFalse(sut.bookRoom(reservation));
	}

	@Test
	public void rejectsReservationForRoomAlreadyBooked() {
		final Reservation reservation = new ReservationBuilder().build();

		AuthorizationService authService = new AuthorizationService() {
			@Override
			public boolean authorizesAccess(String username, String resource) {
				return true;
			}
		};

		ReservationRepository repository = new ReservationRepository() {
			@Override
			public void store(Reservation reservation) {
			}

			@Override
			public Reservation findSimilar(Reservation reservation) {
				return reservation;
			}
		};

		ReservationService sut = new ReservationService(authService, repository);

		assertFalse(sut.bookRoom(reservation));
	}

	@Test
	public void rejectsReservationBreakingTheRoomOccupancyPolicy() {
		Reservation reservation = new ReservationBuilder().big().forOnePerson().build();

		AuthorizationService authService = new AuthorizationService() {
			@Override
			public boolean authorizesAccess(String username, String resource) {
				return true;
			}
		};

		ReservationService sut = new ReservationService(authService, emptyRepository());

		assertFalse(sut.bookRoom(reservation));
	}

	private ReservationRepository emptyRepository() {
		return new ReservationRepository() {
			@Override
			public void store(Reservation reservation) {
			}

			@Override
			public Reservation findSimilar(Reservation reservation) {
				return null;
			}
		};
	}

	private class ReservationRepositorySpy implements ReservationRepository {
		private Reservation storedReservation;

		@Override
		public Reservation findSimilar(Reservation reservation) {
			return null;
		}

		@Override
		public void store(Reservation reservation) {
			this.storedReservation = reservation;
		}

		public Reservation getStoredReservation() {
			return storedReservation;
		}

	}

}
