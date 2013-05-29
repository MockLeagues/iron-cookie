package net.jalg.ironcookie;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import net.jalg.jiron.Jiron;
import net.jalg.jiron.JironException;

/**
 * Resource class processing the login data and redirecting to original page.
 * 
 * @author Jan Algermissen, http://jalg.net
 * 
 */
@Path("login")
public class LoginResource {

	private static Logger LOG = Logger.getLogger(LoginResource.class.getName());

	@FormParam("login")
	String login;
	@FormParam("password")
	String password;
	@FormParam("redirectUri")
	URI redirectUri;

	@POST
	public Response processLoginForm() {

		long now = System.currentTimeMillis() / 1000;
		long expires = now + 60L; // Expires in 1 Minute

		// Authenticate with login and password

		String data = login + "|" + expires + "|Realname-of-" + login;
		String cookieValue;
		LOG.log(Level.INFO, "Data to seal: " + data);

		try {
			cookieValue = Jiron.seal(data, ApplicationConfig.ENCRYPTION_KEY,
					Jiron.DEFAULT_ENCRYPTION_OPTIONS,
					Jiron.DEFAULT_INTEGRITY_OPTIONS);
		} catch (JironException e) {
			LOG.log(Level.SEVERE, "Unable to seal cookie data.", e);
			throw new WebApplicationException(Response.serverError().build());
		}

		NewCookie c = new NewCookie(AuthFilter.COOKIE_NAME, cookieValue);
		return Response.seeOther(redirectUri).cookie(c).build();
	}

}
