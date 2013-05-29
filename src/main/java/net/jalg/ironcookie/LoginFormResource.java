package net.jalg.ironcookie;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Resource class serving the login form.
 * 
 * @author Jan Algermissen, http://jalg.net
 * 
 */
@Path("form")
public class LoginFormResource {

	@QueryParam("redirectUri")
	URI redirectUri;

	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getLoginForm() {

		URI loginUri = uriInfo.resolve(UriBuilder.fromResource(
				LoginResource.class).build());

		StringBuilder sb = new StringBuilder("<html><body><form action=\"");
		sb.append(loginUri.toASCIIString());
		sb.append("\" method=\"POST\">\n");
		sb.append("<h1>Login to get an iron-cookie</h1>");
		sb.append("Login: <input type=\"text\" name=\"login\"/><br/>\n");
		sb.append("<input type=\"password\" name=\"password\"/><br/>\n");
		sb.append("<input type=\"hidden\" name=\"redirectUri\" value=\"")
				.append(redirectUri.toASCIIString()).append("\"/>\n");
		sb.append("<input type=\"submit\" value=\"Login\"/></form></body></html>");

		return sb.toString();
	}

}
