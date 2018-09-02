/**
 * 
 */
package com.agoda.model;

import java.io.Serializable;

public class HotelDTO implements Serializable {

	private static final long serialVersionUID = -3019683626635506074L;
	private long id;
	private String city;
	private String room;
	private long price;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof HotelDTO)) {
			return false;
		}
		HotelDTO other = (HotelDTO) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	public String toString() {
		return "Hotel [id=" + id + ", city=" + city + ", room=" + room + ", price=" + price + "]";
	}

}
