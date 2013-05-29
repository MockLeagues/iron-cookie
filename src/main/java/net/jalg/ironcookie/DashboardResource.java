package net.jalg.ironcookie;

import java.security.Principal;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.SecurityContext;

/**
 * A resource with a protected resource method serving a user's dashboard.
 * 
 * @author Jan Algermissen, http://jalg.net
 * 
 */
@Path("dashboard")
public class DashboardResource {

	@Context
	Request request;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@TokenAuthProtected
	public String getDashboard(@Context SecurityContext sc) {
		Principal principal = sc.getUserPrincipal();
		if (principal == null) {
			throw new WebApplicationException(
					"AuthFilter seems to be missing in chain");
		}
		String username = principal.getName();
		/*
		 * Casting principal to a more specific type is currently the only way I
		 * see how to pass data from a JAX-RS filter to a resource method.
		 * Ideas for improvements are very welcome.
		 */
		String realname = ((TokenPrincipal) principal).getRealname();
		return "This is the dashboard of user " + realname + " with login "
				+ username;
	}

}
