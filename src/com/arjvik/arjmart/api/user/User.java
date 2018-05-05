package com.arjvik.arjmart.api.user;

public class User {
	private int ID;
	private String email;
	private String password;
	private String creditCardNumber;
	
	public User() {
	}
	public User(int ID, String email, String password, String creditCardNumber) {
		this();
		this.ID = ID;
		this.email = email;
		this.password = password;
		this.creditCardNumber = creditCardNumber;
	}
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((creditCardNumber == null) ? 0 : creditCardNumber.hashCode());
		return result;
	}
	@Override
	public String toString() {
		return "User [ID=" + ID + ", email=" + email + ", password=" + password + ", creditCardNumber=" + creditCardNumber + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (ID != other.ID)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (creditCardNumber == null) {
			if (other.creditCardNumber != null)
				return false;
		} else if (!creditCardNumber.equals(other.creditCardNumber))
			return false;
		return true;
	}
	
}
