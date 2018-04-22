package com.arjvik.arjmart.web;

public class CartItem {
	private int SKU;
	private int itemAttributeID;
	private int quantity;
	
	public CartItem() {
		
	}
	public CartItem(int SKU, int itemAttributeID, int quantity) {
		this.SKU = SKU;
		this.itemAttributeID = itemAttributeID;
		this.quantity = quantity;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + SKU;
		result = prime * result + itemAttributeID;
		result = prime * result + quantity;
		return result;
	}
	@Override
	public String toString() {
		return "CartItem [SKU=" + SKU + ", itemAttributeID=" + itemAttributeID + ", quantity=" + quantity + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		if (SKU != other.SKU)
			return false;
		if (itemAttributeID != other.itemAttributeID)
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}
}
