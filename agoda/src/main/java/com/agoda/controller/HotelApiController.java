package com.agoda.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agoda.domain.Hotel;
import com.agoda.service.AccomodationService;

@RestController
@RequestMapping("/agoda")
public class HotelApiController {

	public static final Logger logger = LoggerFactory.getLogger(HotelApiController.class);

	@Autowired
	AccomodationService accomodationService;

	@RequestMapping(value = "/city/{city}", method = { RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCityHotels(HttpServletRequest request, @PathVariable String city,
			@RequestParam(value = "sort", required = false, defaultValue = "ASC") String sortOrder) {

		// String sortOrder = request.getParameter("sortOrder");
		List<Hotel> lst = accomodationService.getHotelInCity(city, sortOrder);
		return new ResponseEntity<Object>(lst, HttpStatus.OK);

	}

	@RequestMapping(value = "/room/{room}", method = { RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getHotelForRoomType(HttpServletRequest request, @PathVariable String room,
			@RequestParam(value = "sort", required = false, defaultValue = "ASC") String sortOrder) {
		List<Hotel> lst = accomodationService.getHotelForRoom(room, sortOrder);
		return new ResponseEntity<Object>(lst, HttpStatus.OK);
	}

	public AccomodationService getAccomodationService() {
		return accomodationService;
	}

	public void setAccomodationService(AccomodationService accomodationService) {
		this.accomodationService = accomodationService;
	}

}
