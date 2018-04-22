package com.arjvik.arjmart.api.item;

public class ItemAttributeNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	private int SKU;
	private int ID;
	
	public ItemAttributeNotFoundException(int SKU, int ID) {
		super(SKU + "/" + ID);
		this.SKU = SKU;
		this.ID = ID;
	}

	public ItemAttributeNotFoundException(int SKU, int ID, Throwable cause) {
		super(SKU + "/" + ID, cause);
		this.SKU = SKU;
		this.ID = ID;
	}
	
	public int getSKU() {
		return SKU;
	}

	public int getID() {
		return ID;
	}
}
