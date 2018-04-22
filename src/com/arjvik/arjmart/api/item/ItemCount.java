package com.arjvik.arjmart.api.item;

public class ItemCount {
	private int count;
	
	public ItemCount() {
		
	}
	public ItemCount(int count) {
		this();
		this.count = count;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		return result;
	}
	@Override
	public String toString() {
		return "ItemCount [count=" + count + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemCount other = (ItemCount) obj;
		if (count != other.count)
			return false;
		return true;
	}
}
