package ro.vichim.testdoubles.service;

public interface AuthorizationService {

	public boolean authorizesAccess(String username, String resource);
	
}
