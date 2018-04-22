package com.arjvik.arjmart.api.item;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ItemResourceClient {

	@GET
	public Response getAll(@DefaultValue("0") @QueryParam("start") int start, @DefaultValue("-1") @QueryParam("limit") int limit, @QueryParam("query") String query);
	
	@GET
	@Path("count")
	public Response getItemCount(@QueryParam("query") String query);
	
	@GET
	@Path("{SKU}")
	public Response getItem(@PathParam("SKU") int SKU);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{SKU}")
	public Response addSKU(Item item, @PathParam("SKU") int SKU);

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{SKU}")
	public Response editItem(Item item, @PathParam("SKU") int SKU);
	
	@DELETE
	@Path("{SKU}")
	public Response deleteItem(@PathParam("SKU") int SKU);
	
	// ATTRIBUTE STARTS HERE
	
	@GET
	@Path("{SKU}/attributes")
	public Response getAllAttribute(@PathParam("SKU") int SKU);
	
	@GET
	@Path("{SKU}/attributes/{ID}")
	public Response getAttribute(@PathParam("SKU") int SKU, @PathParam("ID") int ID);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{SKU}/attributes")
	public Response addAttribute(ItemAttribute itemAttribute, @PathParam("SKU") int SKU);
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{SKU}/attributes/{ID}")
	public Response editAttribute(ItemAttribute itemAttribute, @PathParam("SKU") int SKU, @PathParam("ID") int ID);
	
	@DELETE
	@Path("{SKU}/attributes/{ID}")
	public Response deleteAttribute(@PathParam("SKU") int SKU, @PathParam("ID") int ID);
	
	// PRICE STARTS HERE
	
	@GET
	@Path("{SKU}/attributes/{ID}/price")
	public Response getPrice(@PathParam("SKU") int SKU, @PathParam("ID") int ItemAttributeID);
	
	@PUT
	@Path("{SKU}/attributes/{ID}/price")
	public Response setPrice(ItemPrice itemPrice, @PathParam("SKU") int SKU, @PathParam("ID") int ItemAttributeID);
}
