package ro.vichim.test.testdoubles.acceptance;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertTrue;

import com.github.tomakehurst.wiremock.WireMockServer;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import ro.vichim.testdoubles.infrastructure.ERPGateway;
import ro.vichim.testdoubles.model.MeetingRoom;
import ro.vichim.testdoubles.model.Reservation;
import ro.vichim.testdoubles.model.RoomLayout;
import ro.vichim.testdoubles.service.ReservationService;

public class BigRoomReservationsSteps {

	private static final String AUTH_SERVICE_URI = "http://localhost:8090/erp";

	private WireMockServer wireMock;

	private MeetingRoom bigRoom;
	private Reservation reservation;

	@Before
	public void stubAuthorizationService() {
		wireMock = new WireMockServer(options().port(8090));
		wireMock.start();
		wireMock.stubFor(get(urlMatching("/erp.*")).willReturn(ok()));
	}

	@After
	public void cleanupStubs() {
		wireMock.stop();
	}

	@Given("a big room")
	public void aBigRoom() {
		this.bigRoom = new MeetingRoom("Milky", RoomLayout.BIG);
	}

	@And("I request a reservation for 2 persons")
	public void reservationIsCreated() {
		this.reservation = new Reservation("App", bigRoom, 2);
	}

	@Then("the reservation is accepted")
	public void reservationIsAccepted() {
		ReservationService sut = new ReservationService(new ERPGateway(AUTH_SERVICE_URI),
				new InMemoryReservationRepository());
		assertTrue(sut.bookRoom(reservation));
	}

}
