package com.agoda.domain;

public class Hotel implements Comparable<Hotel> {
	int id;
	String city;
	String room;
	int price;

	@Override
	public int compareTo(Hotel o) {
		return (this.getPrice() < ((Hotel) o).getPrice() ? 0 : -1);

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
