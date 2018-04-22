package com.arjvik.arjmart.web;

import com.arjvik.arjmart.api.item.Item;
import com.arjvik.arjmart.api.item.ItemAttribute;
import com.arjvik.arjmart.api.item.ItemPrice;
import com.arjvik.arjmart.api.order.OrderLine;

public class SuperOrderLine extends OrderLine {
	
	private int orderID;
	private int orderLineID;
	private int SKU;
	private int itemAttributeID;
	private int quantity;
	private String status;
	private String itemName;
	private String itemDescription;
	private String itemThumbnail;
	private String itemAttributeColor;
	private String itemAttributeSize;
	private double price;

	public SuperOrderLine() {
		//Blank
	}
	public SuperOrderLine(OrderLine orderLine) {
		//OrderLine helper constructor
		this(orderLine.getOrderID(), orderLine.getOrderLineID(), orderLine.getSKU(), orderLine.getItemAttributeID(), orderLine.getQuantity(), orderLine.getStatus());
	}
	public SuperOrderLine(OrderLine orderLine, Item item) {
		//OrderLine, Item helper constructor
		this(orderLine.getOrderID(), orderLine.getOrderLineID(), orderLine.getSKU(), orderLine.getItemAttributeID(), orderLine.getQuantity(), orderLine.getStatus(), item.getName(), item.getDescription(), item.getThumbnail());
	}
	public SuperOrderLine(OrderLine orderLine, Item item, ItemAttribute itemAttribute) {
		//OrderLine, Item, ItemAttributeID helper constructor
		this(orderLine.getOrderID(), orderLine.getOrderLineID(), orderLine.getSKU(), orderLine.getItemAttributeID(), orderLine.getQuantity(), orderLine.getStatus(), item.getName(), item.getDescription(), item.getThumbnail(), itemAttribute.getColor(), itemAttribute.getSize());
	}
	public SuperOrderLine(OrderLine orderLine, Item item, ItemAttribute itemAttribute, ItemPrice price) {
		//OrderLine, Item, ItemAttributeID helper constructor
		this(orderLine.getOrderID(), orderLine.getOrderLineID(), orderLine.getSKU(), orderLine.getItemAttributeID(), orderLine.getQuantity(), orderLine.getStatus(), item.getName(), item.getDescription(), item.getThumbnail(), itemAttribute.getColor(), itemAttribute.getSize(), price.getPrice());
	}
	public SuperOrderLine(int orderID, int orderLineID, int SKU, int itemAttributeID, int quantity, String status) {
		//OrderLine
		this(orderID,orderLineID,SKU,itemAttributeID,quantity,status,null,null,null);
	}
	public SuperOrderLine(int orderID, int orderLineID, int SKU, int itemAttributeID, int quantity, String status, String itemName, String itemDescription, String itemThumbnail) {
		//OrderLine, Item
		this(orderID,orderLineID,SKU,itemAttributeID,quantity,status,itemName,itemDescription,itemThumbnail,null,null);
	}
	public SuperOrderLine(int orderID, int orderLineID, int SKU, int itemAttributeID, int quantity, String status, String itemName, String itemDescription, String itemThumbnail, String itemAttributeColor, String itemAttributeSize) {
		//OrderLine, Item, ItemAttribute
		this(orderID,orderLineID,SKU,itemAttributeID,quantity,status,itemName,itemDescription,itemThumbnail,itemAttributeColor,itemAttributeSize,-1);
	}
	public SuperOrderLine(int orderID, int orderLineID, int SKU, int itemAttributeID, int quantity, String status, String itemName, String itemDescription, String itemThumbnail, String itemAttributeColor, String itemAttributeSize, double price) {
		//Master Constructor: OrderLine, Item, ItemAttribute, ItemPrice
		this();
		this.orderID = orderID;
		this.orderLineID = orderLineID;
		this.SKU = SKU;
		this.itemAttributeID = itemAttributeID;
		this.quantity = quantity;
		this.status = status;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.itemThumbnail = itemThumbnail;
		this.setItemAttributeColor(itemAttributeColor);
		this.setItemAttributeSize(itemAttributeSize);
		this.price = price;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getOrderLineID() {
		return orderLineID;
	}
	public void setOrderLineID(int orderLineID) {
		this.orderLineID = orderLineID;
	}
	public int getSKU() {
		return SKU;
	}
	public void setSKU(int SKU) {
		this.SKU = SKU;
	}
	public int getItemAttributeID() {
		return itemAttributeID;
	}
	public void setItemAttributeID(int itemAttributeID) {
		this.itemAttributeID = itemAttributeID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getItemThumbnail() {
		return itemThumbnail;
	}
	public void setItemThumbnail(String itemThumbnail) {
		this.itemThumbnail = itemThumbnail;
	}
	public String getItemAttributeColor() {
		return itemAttributeColor;
	}
	public void setItemAttributeColor(String itemAttributeColor) {
		this.itemAttributeColor = itemAttributeColor;
	}
	public String getItemAttributeSize() {
		return itemAttributeSize;
	}
	public void setItemAttributeSize(String itemAttributeSize) {
		this.itemAttributeSize = itemAttributeSize;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
