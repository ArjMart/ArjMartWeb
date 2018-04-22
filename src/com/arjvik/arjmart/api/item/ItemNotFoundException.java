package com.arjvik.arjmart.api.item;

public class ItemNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	private int SKU;
	
	public ItemNotFoundException(int SKU) {
		super(Integer.toString(SKU));
		this.SKU = SKU;
	}

	public ItemNotFoundException(int SKU, Throwable cause) {
		super(Integer.toString(SKU), cause);
		this.SKU = SKU;
	}

	public int getSKU() {
		return SKU;
	}
}
