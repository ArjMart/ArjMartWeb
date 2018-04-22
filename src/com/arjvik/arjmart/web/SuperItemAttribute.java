package com.arjvik.arjmart.web;

import com.arjvik.arjmart.api.item.ItemAttribute;

public class SuperItemAttribute extends ItemAttribute {
	
	private int SKU;
	private int ID;
	private String color;
	private String size;
	private String itemName;
	private String itemDescription;
	private String itemThumbnail;
	private double price;
	
	public SuperItemAttribute() {
		
	}
	public SuperItemAttribute(int SKU, int ID, String color, String size) {
		this(SKU, ID, color, size, null, null, null);
	}
	public SuperItemAttribute(int SKU, int ID, String color, String size, String itemName, String itemDescription, String itemThumbnail) {
		this(SKU, ID, color, size, itemName, itemDescription, itemThumbnail, -1);
	}
	public SuperItemAttribute(int SKU, int ID, String color, String size, String itemName, String itemDescription, String itemThumbnail, double price) {
		this.SKU = SKU;
		this.ID = ID;
		this.color = color;
		this.size = size;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.itemThumbnail = itemThumbnail;
		this.price = price;
	}
	public int getSKU() {
		return SKU;
	}
	public void setSKU(int SKU) {
		this.SKU = SKU;
	}
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
}
