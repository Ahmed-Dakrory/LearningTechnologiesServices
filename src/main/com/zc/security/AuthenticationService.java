package main.com.zc.security;

import javax.annotation.security.RolesAllowed;





public interface AuthenticationService {
	
	boolean login(String username, String password);
	public boolean autoLogin(String username, String password);
	@RolesAllowed({"ROLE_REGISTERED","ROLE_ADMIN"})
	void logout();
}
