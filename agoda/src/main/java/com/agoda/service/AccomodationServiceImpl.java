package com.agoda.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.agoda.domain.Hotel;
import com.agoda.utils.CsvReader;

@Service
public class AccomodationServiceImpl implements AccomodationService {
	List<Hotel> hotels;

	private void initHotel() {
		hotels = CsvReader.getHotelsFromCsv();
	}

	public List<Hotel> getHotelInCity(String city, String sortOrder) {
		List<Hotel> lst = new ArrayList<Hotel>();
		for (Hotel hotel : hotels) {
			if (hotel.getCity().equalsIgnoreCase(city)) {
				lst.add(hotel);
			}
		}
		if (sortOrder.equalsIgnoreCase("DESC")) {
			Collections.sort(lst);
		} else {
			Collections.sort(lst, Collections.reverseOrder());
		}
		return lst;
	}

	public List<Hotel> getHotelForRoom(String room, String sortOrder) {
		List<Hotel> lst = new ArrayList<Hotel>();
		for (Hotel hotel : hotels) {
			if (hotel.getRoom().equalsIgnoreCase(room)) {
				lst.add(hotel);
			}
		}
		if (sortOrder.equalsIgnoreCase("DESC")) {
			Collections.sort(lst);
		} else {
			Collections.sort(lst, Collections.reverseOrder());
		}
		return lst;
	}

	public AccomodationServiceImpl() {
		initHotel();
	}

}
