package ro.vichim.test.testdoubles.infrastructure;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import ro.vichim.testdoubles.infrastructure.ERPGateway;

public class ERPGatewayTest {

	private static final String SERVICE_URI = "http://localhost:8090/erp";

	@Rule
	public WireMockRule wireMock = new WireMockRule(options().port(8090).notifier(new ConsoleNotifier(true)));

	@Test
	public void authorizesAccessOnPositiveResponseFromService() {
		wireMock.stubFor(get(urlMatching("/erp.*")).willReturn(ok()));

		ERPGateway sut = new ERPGateway(SERVICE_URI);
		assertTrue(sut.authorizesAccess("Scott", "Colosseum"));
	}

	@Test
	public void disallowsAccessOnNegativeResponseFromService() {
		wireMock.stubFor(get(urlMatching("/erp.*")).willReturn(notFound()));

		ERPGateway sut = new ERPGateway(SERVICE_URI);
		assertFalse(sut.authorizesAccess("Scott", "Colosseum"));
	}

	@Test
	public void callsAuthorizationServiceWithCorrectArguments() {
		wireMock.stubFor(get(urlMatching("/erp.*")));

		ERPGateway sut = new ERPGateway(SERVICE_URI);

		String username = "Scott";
		String page = "Colosseum";
		sut.authorizesAccess(username, page);

		wireMock.verify(getRequestedFor(urlMatching("/erp.*")).withQueryParam("username", equalTo(username))
				.withQueryParam("resource", equalTo(page)));
	}

}
