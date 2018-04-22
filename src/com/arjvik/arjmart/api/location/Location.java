package com.arjvik.arjmart.api.location;

public class Location {
	private int ID;
	private String address;
	
	public Location() {
		
	}
	public Location(int ID, String address) {
		this();
		this.ID = ID;
		this.address = address;
	}
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		return result;
	}
	@Override
	public String toString() {
		return "Item [ID=" + ID + ", address=" + address + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (ID != other.ID)
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		return true;
	}
}
