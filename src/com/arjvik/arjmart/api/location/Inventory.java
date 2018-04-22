package com.arjvik.arjmart.api.location;

public class Inventory{
	private int locationID;
	private int SKU;
	private int itemAttributeID;
	private int quantity;
	public Inventory() {
		
	}
	public Inventory(int locationID, int SKU, int itemAttributeID, int quantity) {
		this();
		this.locationID = locationID;
		this.setSKU(SKU);
		this.itemAttributeID = itemAttributeID;
		this.quantity = quantity;
	}
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
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
		result = prime * result + itemAttributeID;
		result = prime * result + locationID;
		result = prime * result + quantity;
		return result;
	}
	@Override
	public String toString() {
		return "Inventory [locationID=" + locationID + ", itemAttributeID=" + itemAttributeID + ", quantity=" + quantity + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventory other = (Inventory) obj;
		if (itemAttributeID != other.itemAttributeID)
			return false;
		if (locationID != other.locationID)
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}
}
