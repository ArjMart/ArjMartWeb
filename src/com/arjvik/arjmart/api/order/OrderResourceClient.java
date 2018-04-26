package com.arjvik.arjmart.api.order;

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

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface OrderResourceClient {
	
	@GET
	public Response getAllOrders();
	
	@GET
	@Path("status/{status}")
	public Response getAllOrdersWithStatus(@PathParam("status") String status);
	
	@GET
	@Path("{ID}")
	public Response getOrder(@PathParam("ID") int ID);
	
	@GET
	@Path("{ID}/total")
	public Response getOrderTotal(@PathParam("ID") int ID);
	
	@POST
	public Response getOrAddCart();
	
	@PUT
	@Path("{ID}")
	public Response changeOrderStatus(Status orderStatus, @PathParam("ID") int ID);
	
	@DELETE
	@Path("{ID}")
	public Response deleteOrder(@PathParam("ID") int ID);
	
	@GET
	@Path("{OrderID}/lines")
	public Response getOrderLines(@PathParam("OrderID") int orderID);
	
	@GET
	@Path("{OrderID}/lines/{OrderLineID}")
	public Response getOrderLine(@PathParam("OrderID") int orderID, @PathParam("OrderLineID") int orderLineID);
	
	@POST
	@Path("{OrderID}/lines")
	public Response addOrderLine(OrderLine orderLine, @PathParam("OrderID") int orderID);
	
	@PUT
	@Path("{OrderID}/lines/{OrderLineID}")
	public Response editOrderLine(Quantity quantity, @PathParam("OrderID") int orderID, @PathParam("OrderLineID") int orderLineID);
	
	@PUT
	@Path("{OrderID}/lines/{OrderLineID}/status")
	public Response editOrderLineStatus(Status status, @PathParam("OrderID") int orderID, @PathParam("OrderLineID") int orderLineID);
	
	@DELETE
	@Path("{OrderID}/lines/{OrderLineID}")
	public Response deleteOrderLine(@PathParam("OrderID") int orderID, @PathParam("OrderLineID") int orderLineID);
	
	//CHECKOUT
	@POST
	@Path("{OrderID}/checkout")
	public Response checkout(@PathParam("OrderID") int orderID);

}
