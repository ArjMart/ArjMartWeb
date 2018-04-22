package com.arjvik.arjmart.web;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;

import org.glassfish.jersey.internal.util.Base64;

import com.arjvik.arjmart.api.user.User;

@Priority(Priorities.AUTHENTICATION)
public class ClientSideAuthenticationFilter implements ClientRequestFilter {

	private String email;
	private String password;
	
	public ClientSideAuthenticationFilter(User user) {
		email = user.getEmail();
		password = user.getPassword();
	}

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, "Basic "+Base64.encodeAsString(email+":"+password));
	}

}
