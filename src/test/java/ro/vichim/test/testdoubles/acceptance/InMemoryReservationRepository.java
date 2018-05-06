package ro.vichim.test.testdoubles.acceptance;

import java.util.ArrayList;
import java.util.List;

import ro.vichim.testdoubles.model.Reservation;
import ro.vichim.testdoubles.service.ReservationRepository;

public class InMemoryReservationRepository implements ReservationRepository {

	private List<Reservation> all = new ArrayList<>();

	@Override
	public Reservation findSimilar(Reservation reservation) {
		int similarIndex = all.indexOf(reservation);
		return similarIndex >= 0 ? all.get(similarIndex) : null;
	}

	@Override
	public void store(Reservation reservation) {
		all.add(reservation);
	}

}
