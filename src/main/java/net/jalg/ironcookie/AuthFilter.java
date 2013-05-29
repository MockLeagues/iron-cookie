package net.jalg.ironcookie;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import net.jalg.jiron.Jiron;
import net.jalg.jiron.JironException;
import net.jalg.jiron.JironIntegrityException;

@Provider
@TokenAuthProtected
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {
	public static final String REALNAME_HEADER = "Ironcookie-Realname";

	private static Logger LOG = Logger.getLogger(AuthFilter.class.getName());

	public static String COOKIE_NAME = "authtoken";

	@Context
	UriInfo uriInfo;

	@Override
	public void filter(ContainerRequestContext context) throws IOException {

		URI loginFormUri = uriInfo.resolve(UriBuilder
				.fromResource(LoginFormResource.class)
				.queryParam("redirectUri",
						context.getUriInfo().getRequestUri().toASCIIString())
				.build());

		Cookie cookie = context.getCookies().get(COOKIE_NAME);
		if (cookie == null) {
			context.abortWith(Response.temporaryRedirect(loginFormUri).build());
			return;
		}
		String cookieValue = cookie.getValue();
		String data;
		try {
			data = Jiron.unseal(cookieValue, ApplicationConfig.ENCRYPTION_KEY,
					Jiron.DEFAULT_ENCRYPTION_OPTIONS,
					Jiron.DEFAULT_INTEGRITY_OPTIONS);
		} catch (JironException e) {
			LOG.log(Level.SEVERE, "Error when unsealing token", e);
			context.abortWith(Response.serverError().build());
			return;
		} catch (JironIntegrityException e) {
			LOG.log(Level.SEVERE, "Auth-token integrity error", e);
			throw new WebApplicationException(Response.status(403).build());
		}

		String[] fields = data.split("\\|");
		if (fields.length != 3) {
			LOG.log(Level.SEVERE,
					"Auth-token format error, unable to extract fields from "
							+ data);
			throw new WebApplicationException(Response.status(403).build());
		}

		final String username = fields[0];
		final long expires = Long.parseLong(fields[1]);
		final String realname = fields[2];

		long now = System.currentTimeMillis() / 1000;
		if (now > expires) {
			context.abortWith(Response.temporaryRedirect(loginFormUri).build());
			return;
		}

		context.setSecurityContext(new TokenSecurityContext(username, realname));
	}

}
