package com.arjvik.arjmart.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.mvc.ErrorTemplate;
import org.glassfish.jersey.server.mvc.Template;

import com.arjvik.arjmart.api.item.Item;
import com.arjvik.arjmart.api.item.ItemAttribute;
import com.arjvik.arjmart.api.item.ItemCount;
import com.arjvik.arjmart.api.item.ItemNotFoundException;
import com.arjvik.arjmart.api.item.ItemPrice;
import com.arjvik.arjmart.api.item.ItemResourceClient;
import com.arjvik.arjmart.api.order.Order;
import com.arjvik.arjmart.api.order.OrderLine;
import com.arjvik.arjmart.api.order.OrderResourceClient;
import com.arjvik.arjmart.api.order.OrderTotal;
import com.arjvik.arjmart.api.order.Quantity;
import com.arjvik.arjmart.api.user.User;
import com.arjvik.arjmart.api.user.UserID;
import com.arjvik.arjmart.api.user.UserResourceClient;

@Path("")
@Produces(MediaType.TEXT_HTML)
@RequestScoped
public class WebResource {
	
	private static final int DEFAULT_RECORD_LIMIT = 4;
	private static final int MAX_RECORD_LIMIT = 10;
	
	@GET
	@Template(name="/index")
	public Response root() {
		return home();
	}
	
	@GET
	@Template(name="/index")
	@Path("home") public Response home() {
		return Response.ok(view()).build();
	}

	@GET
	@Template(name="/search")
	@Path("search") public Response search(@QueryParam("query") String query, @QueryParam("page") @DefaultValue("1") int page, @QueryParam("limit") @DefaultValue("-1") int limit) {
		if(limit==-1)
			limit = DEFAULT_RECORD_LIMIT;
		else
			limit = Math.min(MAX_RECORD_LIMIT, limit);
		ItemResourceClient client = ResourceClientProvider.get(ItemResourceClient.class, session);
		List<Item> items = client.getAll((page-1)*limit, limit, query).readEntity(new GenericType<List<Item>>(){});
		WebpageEntity entity = view("query",query,"items",items,"limit",limit,"page",page);
		if(page > 1)
			entity.put("previousPage", page-1);
		int count = client.getItemCount(query).readEntity(ItemCount.class).getCount();
		int pageCount = (int) Math.ceil(((double)count)/limit);
		entity.put("pageCount", pageCount);
		entity.put("resultCount", count);
		if(page < pageCount)
			entity.put("nextPage", page+1);
		return Response.ok(entity).build();
	}
	
	@GET
	@Template(name="/item")
	@ErrorTemplate(name="/item-not-found")
	@Path("item/{SKU:[0-9]+}") public Response getItem(@PathParam("SKU") int SKU) throws ItemNotFoundException {
		ItemResourceClient client = ResourceClientProvider.get(ItemResourceClient.class, session);
		Item item = client.getItem(SKU).readEntity(Item.class);
		List<ItemAttribute> attributes = client.getAllAttribute(SKU).readEntity(new GenericType<List<ItemAttribute>>(){});
		List<Map<String,Object>> attributeWithPrices = new ArrayList<>();
		for (ItemAttribute attribute : attributes) {
			Map<String, Object> map = new HashMap<>();
			map.put("attribute", attribute);
			ItemPrice price = client.getPrice(SKU, attribute.getID()).readEntity(ItemPrice.class);
			map.put("price", price.getPrice());
			attributeWithPrices.add(map);
		}
		return Response.ok(view("item",item,"attributes",attributeWithPrices)).build();
	}
	
	@GET
	@Template(name="/login")
	@Path("login") public Response login(@QueryParam("redirect") @DefaultValue("myAccount") String redirect, @QueryParam("error") String error) {
		WebpageEntity entity = view("redirect", redirect);
		if(error!=null)
			entity.put("errorMessage", error);
		return Response.ok(entity).build();
	}
	
	@POST
	@Template(name="/login")
	@Path("login") public Response handleLogin(@QueryParam("redirect") @DefaultValue("myAccount") String redirect, @FormParam("email") String email, @FormParam("password") String password) {
		User user = new User(-1, email, password, null, -1);
		session.put("user", user);
		UserResourceClient client = ResourceClientProvider.get(UserResourceClient.class, session);
		Response response = client.getUserID();
		switch(response.getStatus()){
		case 401:
		case 403:
			session.remove("user");
			return login(redirect, "Incorrect email or password");
		case 200:
			int userID = response.readEntity(UserID.class).getID();
			User user2 = client.getUser(userID).readEntity(User.class);
			session.put("user", user2);
			return Response.seeOther(uriInfo.getBaseUriBuilder()
											.path(WebResource.class)
											.path(redirect)
											.build())
						   .build();
		default:
			throw new RuntimeException();
		}
	}

	@GET
	@Path("logout") public Response logout() {
		session.remove("user");
		return Response.temporaryRedirect(uriInfo.getBaseUriBuilder()
												 .path(WebResource.class)
												 .build())
					   .build();
	}
	
	@GET
	@Template(name="/signup")
	@Path("signup") public Response signup(@QueryParam("redirect") @DefaultValue("myAccount") String redirect, @QueryParam("error") String error) {
		WebpageEntity entity = view("redirect", redirect);
		if(error!=null)
			entity.put("errorMessage", error);
		return Response.ok(entity).build();
	}
	
	@POST
	@Template(name="/signup")
	@Path("signup") public Response handleSignup(@QueryParam("redirect") @DefaultValue("myAccount") String redirect, @FormParam("email") String email, @FormParam("password") String password, @FormParam("creditCardNumber") String creditCardNumber) {
		UserResourceClient client = ResourceClientProvider.get(UserResourceClient.class, session);
		User user = new User(-1,email,password,creditCardNumber,-1);
		Response response = client.addUser(user);
		switch(response.getStatus()){
		case 409:
			return signup(redirect,"Email already exists");
		case 200:
		case 201:
			user = response.readEntity(User.class);
			session.put("user", user);
			return Response.seeOther(uriInfo.getBaseUriBuilder()
											.path(WebResource.class)
											.path(redirect)
											.build())
						   .build();
		default:
			throw new RuntimeException();
		}
	}
	
	@GET
	@Template(name="/account")
	@Authorized
	@Path("myAccount") public Response myAccount() {
		User user = (User) session.get("user");
		UserResourceClient client = ResourceClientProvider.get(UserResourceClient.class, session);
		Response response = client.getUser(user.getID());
		if(response.getStatus()!=200)
			return Response.temporaryRedirect(uriInfo.getBaseUriBuilder()
													 .path(WebResource.class, "login")
													 .queryParam("error", "Something went wrong. Please login again.")
													 .queryParam("redirect", "myAccount")
													 .build())
						   .build();
		User newUser = response.readEntity(User.class);
		user.setCreditCardNumber(newUser.getCreditCardNumber());
		return Response.ok(view("user", user)).build();
	}
	
	@POST
	@Template(name="/account")
	@Authorized
	@Path("myAccount") public Response myAccountEditCreditCardNumber(@FormParam("creditCardNumber") String creditCardNumber) {
		UserResourceClient client = ResourceClientProvider.get(UserResourceClient.class, session);
		User user = (User) session.get("user");
		user.setCreditCardNumber(creditCardNumber);
		Response response = client.editCreditCardNumber(user,user.getID());
		if(response.getStatus()!=200)
			return Response.temporaryRedirect(uriInfo.getBaseUriBuilder()
													 .path(WebResource.class, "login")
													 .queryParam("error", "Something went wrong. Please login again.")
													 .queryParam("redirect", "myAccount")
													 .build())
						   .build();
		return Response.ok(view("user",user)).build();
	}
	
	@POST
	@Path("handleAddToCart") public Response handleAddToCart(@FormParam("sku") int sku, @FormParam("id") int itemAttributeID, @FormParam("quantity") int quantity) {
		CartItem cartItem = new CartItem(sku,itemAttributeID,quantity);
		session.put("cartItem",cartItem);
		return Response.seeOther(uriInfo.getBaseUriBuilder()
										.path(WebResource.class, "addToCart")
										.build())
					   .build();
	}
	
	@GET
	@Template(name="/add-to-cart")
	@Authorized
	@Path("addToCart") public Response addToCart() {
		CartItem cartItem = (CartItem) session.get("cartItem");
		if(cartItem == null)
			return Response.temporaryRedirect(uriInfo.getBaseUriBuilder()
													 .path(WebResource.class, "home")
													 .build())
						   .build();
		session.remove("cartItem");
		ItemResourceClient itemClient = ResourceClientProvider.get(ItemResourceClient.class, session);
		Item item = itemClient.getItem(cartItem.getSKU()).readEntity(Item.class);
		ItemAttribute itemAttribute = itemClient.getAttribute(cartItem.getSKU(), cartItem.getItemAttributeID()).readEntity(ItemAttribute.class);
		OrderResourceClient client = ResourceClientProvider.get(OrderResourceClient.class, session);
		Order cart = client.getOrAddCart().readEntity(Order.class);
		OrderLine orderLine = new OrderLine();
		orderLine.setSKU(cartItem.getSKU());
		orderLine.setItemAttributeID(cartItem.getItemAttributeID());
		orderLine.setQuantity(cartItem.getQuantity());
		Response response = client.addOrderLine(orderLine, cart.getOrderID());
		switch(response.getStatus()) {
		case 200:
		case 201:
			orderLine = response.readEntity(OrderLine.class);
			return Response.ok(view("orderLine",orderLine,"item",item,"itemAttribute",itemAttribute,"existing",false)).build();
		case 409:
			List<OrderLine> orderLines = client.getOrderLines(cart.getOrderID()).readEntity(new GenericType<List<OrderLine>>(){});
			OrderLine conflictingOrderLine = null;
			conflictSearch: for (OrderLine existingOrderLine : orderLines) {
				if(existingOrderLine.getSKU()==cartItem.getSKU() && existingOrderLine.getItemAttributeID()==cartItem.getItemAttributeID()){
					conflictingOrderLine = existingOrderLine;
					break conflictSearch;
				}
			}
			Quantity quantity = new Quantity(orderLine.getQuantity() + conflictingOrderLine.getQuantity());
			orderLine = client.editOrderLine(quantity, cart.getOrderID(), conflictingOrderLine.getOrderLineID()).readEntity(OrderLine.class);
			return Response.ok(view("orderLine",orderLine,"item",item,"itemAttribute",itemAttribute,"existing",true)).build();
		default:
			throw new RuntimeException();
		}
	}
	
	@GET
	@Template(name="/cart")
	@Authorized
	@Path("cart") public Response viewCart() {
		OrderResourceClient orderClient = ResourceClientProvider.get(OrderResourceClient.class, session);
		ItemResourceClient itemClient = ResourceClientProvider.get(ItemResourceClient.class, session);
		Order cart = orderClient.getOrAddCart().readEntity(Order.class);
		List<OrderLine> lines = orderClient.getOrderLines(cart.getOrderID()).readEntity(new GenericType<List<OrderLine>>(){});
		List<SuperOrderLine> cartItems = new ArrayList<>();
		for (OrderLine line : lines) {
			Item item = itemClient.getItem(line.getSKU()).readEntity(Item.class);
			ItemAttribute itemAttribute = itemClient.getAttribute(line.getSKU(), line.getItemAttributeID()).readEntity(ItemAttribute.class);
			ItemPrice price = itemClient.getPrice(line.getSKU(), line.getItemAttributeID()).readEntity(ItemPrice.class);
			SuperOrderLine superOrderLine = new SuperOrderLine(line, item, itemAttribute, price);
			cartItems.add(superOrderLine);
		}
		OrderTotal total = orderClient.getOrderTotal(cart.getOrderID()).readEntity(OrderTotal.class);
		return Response.ok(view("cart",cartItems,"total",total)).build();
	}
	
	@GET
	@Template(name="/edit-quantity")
	@Authorized
	@Path("editQuantity") public Response editCartQuantity(@QueryParam("id") int orderLineID) {
		OrderResourceClient orderClient = ResourceClientProvider.get(OrderResourceClient.class, session);
		ItemResourceClient itemClient = ResourceClientProvider.get(ItemResourceClient.class, session);
		Order cart = orderClient.getOrAddCart().readEntity(Order.class);
		OrderLine line = orderClient.getOrderLine(cart.getOrderID(), orderLineID).readEntity(OrderLine.class);
		Item item = itemClient.getItem(line.getSKU()).readEntity(Item.class);
		ItemAttribute itemAttribute = itemClient.getAttribute(line.getSKU(), line.getItemAttributeID()).readEntity(ItemAttribute.class);
		ItemPrice price = itemClient.getPrice(line.getSKU(), line.getItemAttributeID()).readEntity(ItemPrice.class);
		SuperOrderLine superOrderLine = new SuperOrderLine(line, item, itemAttribute, price);
		return Response.ok(view("orderLine",superOrderLine)).build();
	}
	
	@POST
	@Authorized
	@Path("editQuantity") public Response handleEditCartQuantity(@FormParam("quantity") int quantity, @FormParam("id") int ID) {
		OrderResourceClient orderClient = ResourceClientProvider.get(OrderResourceClient.class, session);
		Order order = orderClient.getOrAddCart().readEntity(Order.class);
		Quantity q = new Quantity(quantity);
		orderClient.editOrderLine(q, order.getOrderID(), ID);
		return Response.seeOther(uriInfo.getBaseUriBuilder()
										.path(WebResource.class, "viewCart")
										.build())
					   .build();
	}
	
	@GET
	@Authorized
	@Path("removeFromCart") public Response removeFromCart(@QueryParam("id") int ID) {
		OrderResourceClient orderClient = ResourceClientProvider.get(OrderResourceClient.class, session);
		Order order = orderClient.getOrAddCart().readEntity(Order.class);
		orderClient.deleteOrderLine(order.getOrderID(), ID);
		return Response.seeOther(uriInfo.getBaseUriBuilder()
										.path(WebResource.class, "viewCart")
										.build())
					   .build();
	}
	
	@GET
	@Authorized
	@Template(name="/checkout")
	@Path("checkout") public Response checkout() {
		OrderResourceClient orderClient = ResourceClientProvider.get(OrderResourceClient.class, session);
		Order cart = orderClient.getOrAddCart().readEntity(Order.class);
		List<OrderLine> lines = orderClient.getOrderLines(cart.getOrderID()).readEntity(new GenericType<List<OrderLine>>(){});
		if(lines.isEmpty())
			return Response.temporaryRedirect(uriInfo.getBaseUriBuilder()
													 .path(WebResource.class)
													 .build())
						   .build();
		OrderTotal total = orderClient.getOrderTotal(cart.getOrderID()).readEntity(OrderTotal.class);
		User user = (User) session.get("user");
		UserResourceClient client = ResourceClientProvider.get(UserResourceClient.class, session);
		Response response = client.getUser(user.getID());
		if(response.getStatus()!=200)
			return Response.temporaryRedirect(uriInfo.getBaseUriBuilder()
													 .path(WebResource.class, "checkout")
													 .queryParam("error", "Something went wrong. Please login again.")
													 .queryParam("redirect", "myAccount")
													 .build())
						   .build();
		User newUser = response.readEntity(User.class);
		user.setCreditCardNumber(newUser.getCreditCardNumber());
		return Response.ok(view("creditCard",user.getCreditCardNumber(),"total",total)).build();
	}
	
	@GET
	@Authorized
	@Path("handleCheckout") public Response handleCheckout() {
		OrderResourceClient orderClient = ResourceClientProvider.get(OrderResourceClient.class, session);
		Order cart = orderClient.getOrAddCart().readEntity(Order.class);
		Response response = orderClient.checkout(cart.getOrderID());
		switch(response.getStatus()) {
		case 200:
		case 204:
			return Response.temporaryRedirect(uriInfo.getBaseUriBuilder()
													 .path(WebResource.class,"checkoutSuccess")
													 .build())
						   .build();
		case 402:
		case 409:
			String error = response.getStatus() == 402 ? "payment" :
						   response.getStatus() == 409 ? "invalidState" :
														 "unknown";
			return Response.temporaryRedirect(uriInfo.getBaseUriBuilder()
													 .path(WebResource.class,"checkoutFailed")
													 .queryParam("error", error)
													 .build())
						   .build();
		default:
			throw new RuntimeException();
		}
	}
	
	@GET
	@Authorized
	@Template(name="/checkout-success")
	@Path("checkoutSuccess") public Response checkoutSuccess() {
		return Response.ok(view()).build();
	}
	
	@GET
	@Authorized
	@Template(name="/checkout-failed")
	@Path("checkoutFailed") public Response checkoutFailed(@QueryParam("error") @DefaultValue("unknown") String error) {
		String errorMsg;
		switch(error) {
		case "payment":
			errorMsg = "Error charging credit card, please try again (Hint: payments ending in 69 cents are declined)";
			break;
		case "invalidState":
			errorMsg = "Error checking out, order was already checked out";
			break;
		default:
			errorMsg = "Unknown error, please try again";
		}
		return Response.ok(view("error",errorMsg)).build();
	}
	
	@GET
	@Authorized
	@Template(name="/past-orders")
	@Path("pastOrders") public Response pastOrders() {
		User user = (User) session.get("user");
		UserResourceClient client = ResourceClientProvider.get(UserResourceClient.class, session);
		List<Order> orders = client.getUserOrders(user.getID()).readEntity(new GenericType<List<Order>>(){});
		return Response.ok(view("orders", orders)).build();
	}
	
	@GET
	@Authorized
	@Template(name="/order-status")
	@Path("orderStatus/{ID}") public Response orderStatus(@PathParam("ID") int ID) {
		OrderResourceClient client = ResourceClientProvider.get(OrderResourceClient.class, session);
		Response response = client.getOrder(ID);
		if(response.getStatus()!=200)
			throw new RuntimeException();
		Order order = response.readEntity(Order.class);
		OrderTotal orderTotal = client.getOrderTotal(ID).readEntity(OrderTotal.class);
		List<OrderLine> orderLines = client.getOrderLines(ID).readEntity(new GenericType<List<OrderLine>>(){});
		List<SuperOrderLine> superOrderLines = new ArrayList<>();
		ItemResourceClient itemClient = ResourceClientProvider.get(ItemResourceClient.class, session);
		for (OrderLine line : orderLines) {
			Item item = itemClient.getItem(line.getSKU()).readEntity(Item.class);
			ItemAttribute itemAttribute = itemClient.getAttribute(line.getSKU(), line.getItemAttributeID()).readEntity(ItemAttribute.class);
			ItemPrice itemPrice = itemClient.getPrice(line.getSKU(), line.getItemAttributeID()).readEntity(ItemPrice.class);
			SuperOrderLine superOrderLine = new SuperOrderLine(line, item, itemAttribute, itemPrice);
			superOrderLines.add(superOrderLine);
		}
		return Response.ok(view("order", order, "total", orderTotal, "orderLines", superOrderLines)).build();
	}
	
	
	
	//-----------------------------------------------------------------------------------------------------------
	
	@GET
	@Template(name="/404")
	@Path("{all:.*}") public Response notFound(@PathParam("all") String path) {
		return Response.status(Status.NOT_FOUND).entity(view()).build();
	}

	private Session session;
	private UriInfo uriInfo;
	
	@Inject
	public WebResource(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		this.session = new Session(request);
		this.uriInfo = uriInfo;
	}

	private WebpageEntity view(Object... args) {
		return new WebpageEntity(session, getUrlParams(), args);
	}
	
	private static Object[] cachedUrlParams;
	
	private Object[] getUrlParams(){
		if(cachedUrlParams == null)
			cachedUrlParams = new Object[]{
				"cssURL",				buildUriSpecial("resources/style.css"),
				"homeURL",				buildUri("home"),
				"searchURL",			buildUri("search"),
				"accountURL",			buildUri("myAccount"),
				"cartURL",				buildUri("viewCart"),
				"loginURL",				buildUri("login"),
				"logoutURL",			buildUri("logout"),
				"signupURL",			buildUri("signup"),
				"itemURL",				buildUriSpecial("item"),
				"addToCardURL",			buildUri("handleAddToCart"),
				"pastOrdersURL",		buildUri("pastOrders"),
				"orderDetailsURL",		buildUriSpecial("orderStatus"),
				"editCartQuantityURL",	buildUri("editCartQuantity"),
				"removeFromCartURL",	buildUri("removeFromCart"),
				"checkoutURL",			buildUri("checkout"),
				"handleCheckoutURL",	buildUri("handleCheckout"),
			};
		return cachedUrlParams;
	}

	private String buildUri(String method) {
		return uriInfo.getBaseUriBuilder().path(WebResource.class, method).build().toString();
	}
	
	private String buildUriSpecial(String path) {
		return uriInfo.getBaseUriBuilder().path(path).build().toString();
	}
	
}
