package net.jalg.ironcookie;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

/**
 * SecurityContext class that can also hold extra data from token using a
 * TokenPrincipal.
 * 
 * @author Jan Algermissen, http://jalg.net
 * 
 */
public class TokenSecurityContext implements SecurityContext {

	private String username;
	private String realname;

	public TokenSecurityContext(String username, String realname) {
		this.username = username;
		this.realname = realname;
	}

	@Override
	public String getAuthenticationScheme() {
		return SecurityContext.FORM_AUTH;
	}

	@Override
	public Principal getUserPrincipal() {
		return new TokenPrincipal() {

			@Override
			public String getName() {
				return username;
			}

			@Override
			public String getRealname() {
				return realname;
			}
		};
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public boolean isUserInRole(String arg0) {
		return false;
	}

}
