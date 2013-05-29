package net.jalg.ironcookie;

import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("r")
public class ApplicationConfig extends Application {
	
	public static final String ENCRYPTION_KEY = "master-password";

}