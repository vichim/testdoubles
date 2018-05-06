package ro.vichim.testdoubles.infrastructure;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import ro.vichim.testdoubles.service.AuthorizationService;

public class ERPGateway implements AuthorizationService {

	private String serviceUri;
	
	private Client httpClient = ClientBuilder.newClient();
	
	public ERPGateway(String serviceUri) {
		this.serviceUri = serviceUri;
	}

	@Override
	public boolean authorizesAccess(String username, String resource) {
		Response response = httpClient.target(serviceUri).path("").queryParam("username", username)
				.queryParam("resource", resource).request().get();

		return response.getStatus() == 200;
	}
	
}
