package net.jalg.ironcookie;

import java.security.Principal;

/**
 * A Principal interface that enables access to other information besides
 * principal name.
 * 
 * @author Jan Algermissen, http://jalg.net
 * 
 */
public interface TokenPrincipal extends Principal {

	public String getRealname();

}
