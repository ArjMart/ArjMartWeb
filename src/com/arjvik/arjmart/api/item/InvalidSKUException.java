package com.arjvik.arjmart.api.item;

public class InvalidSKUException extends Exception {
	private static final long serialVersionUID = 1L;
	private int SKU;

	public InvalidSKUException(int SKU) {
		super(Integer.toString(SKU));
		this.SKU = SKU;
	}
	
	public InvalidSKUException(int SKU, Throwable cause) {
		super(Integer.toString(SKU), cause);
		this.SKU = SKU;
	}
	
	public int getSKU() {
		return SKU;
	}
}
