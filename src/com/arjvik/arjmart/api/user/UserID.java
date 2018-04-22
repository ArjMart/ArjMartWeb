package com.arjvik.arjmart.api.user;

public class UserID {
	private int ID;
	
	public UserID() {
	}
	public UserID(int ID) {
		this();
		this.ID = ID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		return result;
	}
	@Override
	public String toString() {
		return "User [ID=" + ID + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserID other = (UserID) obj;
		if (ID != other.ID)
			return false;
		return true;
	}
	
}
