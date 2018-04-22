package com.arjvik.arjmart.web;

import java.io.IOException;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.Provider;

@Provider
@Authorized
@Priority(Priorities.AUTHENTICATION)
public class AuthorizationFilter implements ContainerRequestFilter {

	@Context
	HttpServletRequest request;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Session session = new Session(request.getSession());
		if(session.get("user") == null)
			requestContext.abortWith(
					Response.seeOther(UriBuilder.fromMethod(WebResource.class, "login")
												.queryParam("redirect", requestContext.getUriInfo().getPath())
												.build())
							.build());
	}

}
