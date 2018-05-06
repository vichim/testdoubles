package ro.vichim.test.testdoubles.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import ro.vichim.test.testdoubles.model.ReservationBuilder;
import ro.vichim.testdoubles.model.Reservation;
import ro.vichim.testdoubles.service.AuthorizationService;
import ro.vichim.testdoubles.service.ReservationRepository;
import ro.vichim.testdoubles.service.ReservationService;

public class ReservationServiceMockitoTest {

	@Test
	public void acceptsValidReservation() {
		Reservation reservation = new ReservationBuilder().build();

		AuthorizationService authService = mock(AuthorizationService.class);
		when(authService.authorizesAccess(anyString(), anyString())).thenReturn(true);

		ReservationService sut = new ReservationService(authService, mock(ReservationRepository.class));

		assertTrue(sut.bookRoom(reservation));
	}

	@Test
	public void storesValidReservation() {
		Reservation reservation = new ReservationBuilder().build();

		AuthorizationService authService = mock(AuthorizationService.class);
		when(authService.authorizesAccess(anyString(), anyString())).thenReturn(true);

		ReservationRepository repository = mock(ReservationRepository.class);

		ReservationService sut = new ReservationService(authService, repository);

		sut.bookRoom(reservation);

		verify(repository).store(reservation);
	}

	@Test
	public void rejectsReservationFromUnauthorizedUser() {
		Reservation reservation = new ReservationBuilder().build();

		AuthorizationService authService = mock(AuthorizationService.class);
		when(authService.authorizesAccess(anyString(), anyString())).thenReturn(false);

		ReservationService sut = new ReservationService(authService, null);

		assertFalse(sut.bookRoom(reservation));
	}

	@Test
	public void rejectsReservationForRoomAlreadyBooked() {
		Reservation reservation = new ReservationBuilder().build();

		AuthorizationService authService = mock(AuthorizationService.class);
		when(authService.authorizesAccess(anyString(), anyString())).thenReturn(true);

		ReservationRepository repository = mock(ReservationRepository.class);
		when(repository.findSimilar(reservation)).thenReturn(reservation);

		ReservationService sut = new ReservationService(authService, repository);

		assertFalse(sut.bookRoom(reservation));
	}

	@Test
	public void rejectsReservationBreakingTheRoomOccupancyPolicy() {
		Reservation reservation = new ReservationBuilder().big().forOnePerson().build();

		AuthorizationService authService = mock(AuthorizationService.class);
		when(authService.authorizesAccess(anyString(), anyString())).thenReturn(true);

		ReservationService sut = new ReservationService(authService, mock(ReservationRepository.class));

		assertFalse(sut.bookRoom(reservation));
	}

}
