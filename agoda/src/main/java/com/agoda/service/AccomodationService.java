package com.agoda.service;

import java.util.List;

import com.agoda.domain.Hotel;

public interface AccomodationService {
	public List<Hotel> getHotelInCity(String city, String sortOrder);

	public List<Hotel> getHotelForRoom(String room, String sortOrder);
}
