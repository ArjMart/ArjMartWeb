package com.arjvik.arjmart.web;

import org.glassfish.jersey.client.proxy.WebResourceFactory;

public class ResourceClientProvider {
	public static <T> T get(Class<T> clazz, Session session) {
		return WebResourceFactory.newResource(clazz, WebTargetProvider.getWebTarget(session));
	}
}
