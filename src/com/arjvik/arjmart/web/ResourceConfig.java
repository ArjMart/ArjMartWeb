package com.arjvik.arjmart.web;

import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;

public class ResourceConfig extends org.glassfish.jersey.server.ResourceConfig {
	public ResourceConfig(){
		packages("com.arjvik.arjmart.web");
		register(FreemarkerMvcFeature.class);
	}
}
