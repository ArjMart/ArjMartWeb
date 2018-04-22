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
import javax.ws.rs.core.UriBuilder;
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
	public WebpageEntity root() {
		return home();
	}
	
	@GET
	@Template(name="/index")
	@Path("home") public WebpageEntity home() {
		return view();
	}

	@GET
	@Template(name="/search")
	@Path("search") public WebpageEntity search(@QueryParam("query") String query, @QueryParam("page") @DefaultValue("1") int page, @QueryParam("limit") @DefaultValue("-1") int limit) {
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
		return entity;
	}
	
	@GET
	@Template(name="/item")
	@ErrorTemplate(name="/item-not-found")
	@Path("item/{SKU:[0-9]+}") public WebpageEntity getItem(@PathParam("SKU") int SKU) throws ItemNotFoundException {
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
		return view("item",item,"attributes",attributeWithPrices);
	}
	
	@GET
	@Template(name="/login")
	@Path("login") public WebpageEntity login(@QueryParam("redirect") @DefaultValue("myAccount") String redirect, @QueryParam("error") String error) {
		WebpageEntity entity = view("redirect", redirect);
		if(error!=null)
			entity.put("errorMessage", error);
		return entity;
	}
	
	@POST
	@Template(name="/login")
	@Path("login") public Response handleLogin(@QueryParam("redirect") @DefaultValue("myAccount") String redirect, @FormParam("email") String email, @FormParam("password") String password) {
		User user = new User(-1, email, password, null);
		session.put("user", user);
		UserResourceClient client = ResourceClientProvider.get(UserResourceClient.class, session);
		Response response = client.getUserID();
		switch(response.getStatus()){
		case 401:
		case 403:
			session.remove("user");
			return Response.ok(login(redirect, "Incorrect email or password")).build();
		case 200:
			int userID = response.readEntity(UserID.class).getID();
			User user2 = client.getUser(userID).readEntity(User.class);
			session.put("user", user2);
			return Response.seeOther(UriBuilder.fromResource(WebResource.class)
						   					   .path(redirect).build())
						   .build();
		default:
			throw new RuntimeException();
		}
	}
	
	@GET
	@Path("logout") public Response logout() {
		session.remove("user");
		return Response.temporaryRedirect(UriBuilder.fromResource(WebResource.class)
													.build())
					   .build();
	}
	
	@GET
	@Template(name="/account")
	@Authorized
	@Path("myAccount") public Response myAccount() {
		User oldUser = (User) session.get("user");
		UserResourceClient client = ResourceClientProvider.get(UserResourceClient.class, session);
		Response response = client.getUser(oldUser.getID());
		if(response.getStatus()!=200)
			return Response.temporaryRedirect(uriInfo.getBaseUriBuilder()
													 .path(WebResource.class, "login")
													 .queryParam("error", "Something went wrong. Please login again.")
													 .queryParam("redirect", "myAccount")
													 .build())
						   .build();
		User user = response.readEntity(User.class);
		session.put("user", user);
		return Response.ok(view("user", user)).build();
	}
	
	@POST
	@Template(name="/account")
	@Authorized
	@Path("myAccount") public Response myAccountEditCreditCardNumber(@FormParam("creditCardNumber") String creditCardNumber) {
		UserResourceClient client = ResourceClientProvider.get(UserResourceClient.class, session);
		Response response = client.getUser(((User) session.get("user")).getID());
		if(response.getStatus()!=200)
			return Response.temporaryRedirect(uriInfo.getBaseUriBuilder()
													 .path(WebResource.class, "login")
													 .queryParam("error", "Something went wrong. Please login again.")
													 .queryParam("redirect", "myAccount")
													 .build())
						   .build();
		User user = response.readEntity(User.class);
		user.setCreditCardNumber(creditCardNumber);
		client.editCreditCardNumber(user,user.getID());
		session.put("user", user);
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
		return Response.ok(view("cart",cartItems)).build();
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
		if(cachedUrlParams == null || true)
			cachedUrlParams = new Object[]{
				"cssURL",				buildUriSpecial("resources/style.css"),
				"homeURL",				buildUri("home"),
				"searchURL",			buildUri("search"),
				"accountURL",			buildUri("myAccount"),
				"cartURL",				buildUri("viewCart"),
				"loginURL",				buildUri("login"),
				"logoutURL",			buildUri("logout"),
				"itemURL",				buildUriSpecial("item"),
				"addToCardURL",			buildUri("handleAddToCart"),
				"pastOrdersURL",		"NOT IMPLEMENTED YET",
				"editCartQuantityURL",	"NOT IMPLEMENTED YET",
				"removeFromCartURL",	"NOT IMPLEMENTED YET",
				"checkoutURL",			"NOT IMPLEMENTED YET",
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
