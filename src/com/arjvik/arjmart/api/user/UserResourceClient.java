package com.arjvik.arjmart.api.user;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UserResourceClient {
	
	@GET
	public Response getUserID();
	
	@GET
	@Path("{ID}")
	public Response getUser(@PathParam("ID") int ID);
	
	@POST
	public Response addUser(User user);
	
	@PUT
	@Path("{ID}")
	public Response editCreditCardNumber(User user, @PathParam("ID") int ID);
	
	@DELETE
	@Path("{ID}")
	public Response deleteUser(@PathParam("ID") int ID);
	
	@GET
	@Path("{ID}/orders")
	public Response getUserOrders(@PathParam("ID") int ID);

}
