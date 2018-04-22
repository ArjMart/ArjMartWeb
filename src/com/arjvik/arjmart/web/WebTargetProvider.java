package com.arjvik.arjmart.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.jackson.JacksonFeature;

import com.arjvik.arjmart.api.user.User;

public class WebTargetProvider {
	
	private static Client client;
	private static String API_URL;
	
	public static void setApiUrl() {
		try {
			Properties properties = new Properties();
			InputStream in = WebTargetProvider.class.getClassLoader().getResourceAsStream("webservices.properties");
			if (in == null)
				throw new RuntimeException("Could not read API connection info, check if webservices.properties exists in classpath");
			properties.load(in);
			API_URL = properties.getProperty("apiurl");
		} catch (IOException e) {
			throw new RuntimeException("Could not read API connection info, check if webservices.properties exists in classpath",e);
		}
	}
	
	private static Client getClient() {
		if(client==null)
			client = ClientBuilder.newClient()
						.register(JacksonFeature.class);
		return client;
	}
	
	public static WebTarget getWebTarget(Session session) {
		if(API_URL==null)
			setApiUrl();
		Client c = getClient();
		WebTarget wt = c.target(API_URL);
		if(session.get("user")!=null)
			wt.register(new ClientSideAuthenticationFilter((User) session.get("user")));
		return wt;
	}
}
