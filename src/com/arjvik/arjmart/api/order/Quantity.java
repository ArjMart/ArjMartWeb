package com.arjvik.arjmart.api.order;

public class Quantity {
	private int quantity;

	public Quantity() {
		
	}
	public Quantity(int quantity) {
		this();
		this.quantity = quantity;
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
		result = prime * result + quantity;
		return result;
	}
	@Override
	public String toString() {
		return "Quantity [quantity=" + quantity + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quantity other = (Quantity) obj;
		if (quantity != other.quantity)
			return false;
		return true;
	}
	
}
