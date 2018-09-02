package com.agoda.utils;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import com.agoda.controller.HotelApiController;
import com.agoda.domain.Hotel;
import com.opencsv.CSVReader;

public class CsvReader {

	public static final Logger logger = LoggerFactory.getLogger(HotelApiController.class);

	public static List<Hotel> getHotelsFromCsv() {
		List<Hotel> hotels = new ArrayList<Hotel>();
		try {
			final File file = ResourceUtils.getFile("classpath:hoteldb.csv");
			CSVReader reader = new CSVReader(new FileReader(file), ',');
			String[] record = null;
			reader.readNext(); // skip header
			while ((record = reader.readNext()) != null) {
				try {
					Hotel hotel = new Hotel();
					hotel.setCity(record[0]);
					hotel.setId(Integer.parseInt(record[1]));
					hotel.setRoom(record[2]);
					hotel.setPrice(Integer.parseInt(record[3]));
					hotels.add(hotel);
				} catch (Exception e) {
					logger.error("Error in reading Reading file" + reader.getRecordsRead());
				}
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hotels;
	}
}
