package com.springboot.issues1.Controller;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.springboot.issues1.Beans.Planetary;
import com.springboot.issues1.Constants.UrlConstants;
import com.springboot.issues1.Service.ErrorMap;
import com.springboot.issues1.Service.PlanetaryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path = "/api/object/planetary")
@Api(value = "Planetory Controller", description = "Planetary Controlled CRUD Method")
public class PlanetaryConroller {

	private static final Logger logger = LoggerFactory.getLogger(PlanetaryConroller.class);

	@Autowired
	private ErrorMap errorMap;

	@Autowired
	private PlanetaryService planetaryService;

	public Planetary getPlanetaryData() throws UnirestException, ParseException {

		Planetary planetary = null;
		HttpResponse<JsonNode> jsonNode = Unirest.get(UrlConstants.NASA_URL).header("Content-Type", "application/json")
				.header("Accept", "application/json").asJson();

		JSONObject jsonObject = jsonNode.getBody().getObject();

		planetary = new Planetary();
		for (Object object : jsonObject.keySet()) {
			String propName = (String) object;
			switch (propName) {
			case "date":
				Object dateObject = jsonObject.get(propName);
				String date = (String) dateObject;
				Date simpleDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
				planetary.setDate(simpleDate);
				continue;

			case "explanation":
				Object explanationObject = jsonObject.get(propName);
				String exaplanationValue = (String) explanationObject;
				planetary.setExplanation(exaplanationValue);
				continue;

			case "hdurl":
				Object hdurlObject = jsonObject.get(propName);
				String hdurlValue = (String) hdurlObject;
				planetary.setHdUrl(hdurlValue);
				continue;

			case "media_type":
				Object mediaTypeObject = jsonObject.get(propName);
				String mediaTypeValue = (String) mediaTypeObject;
				planetary.setMediaType(mediaTypeValue);
				continue;

			case "service_version":
				Object serviceVersionObject = jsonObject.get(propName);
				String serviceVersionValue = (String) serviceVersionObject;
				planetary.setServiceVersion(serviceVersionValue);
				continue;

			case "title":
				Object titleObject = jsonObject.get(propName);
				String titleValue = (String) titleObject;
				planetary.setTitle(titleValue);
				continue;

			case "url":
				Object urlObject = jsonObject.get(propName);
				String urlValue = (String) urlObject;
				planetary.setUrl(urlValue);
				continue;

			default:
				logger.info("****************This Is The End Of switch Case**************************");
			}
		}
		return planetary;

	}

	@RequestMapping(path = "/create", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@ApiOperation(value = "Planetary Crud Operation", notes = "This Is The Create Operation of Planetary Class", response = Planetary.class)
	public ResponseEntity<?> createPlanetaryResource() throws UnirestException, ParseException {
		Planetary planetary = getPlanetaryData();
		if (planetary == null) {
			return new ResponseEntity<String>("Bad Request From 3rd Party Api", HttpStatus.BAD_REQUEST);
		}

		Planetary planetaryFromDB = planetaryService.createResource(planetary);
		if (planetaryFromDB == null) {
			return new ResponseEntity<String>("Sorry Could Not Store Planetary Resource", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Planetary>(planetaryFromDB, HttpStatus.CREATED);
	}

	@RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiOperation(value = "Retrieve Planetary Resource", notes = "Retrieve Planetary Value From DB", response = Planetary.class)
	public ResponseEntity<?> retrieveResource(
			@ApiParam(value = "id", required = true, example = "420") @RequestParam(value = "id", required = true) Long id) {
		Planetary planetaryFromDB = planetaryService.getPlanetaryResource(id);
		if (planetaryFromDB == null) {
			return new ResponseEntity<String>("Sorry Could Not Retrieve Data From DB", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Planetary>(planetaryFromDB, HttpStatus.OK);
	}

	@RequestMapping(path = "/getAll", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrieve All Planetary Resource", notes = "This Method will Retrieve All Planetary Resource", response = Planetary.class, responseContainer = "LIST")
	public ResponseEntity<?> getAllPlanetaryResource() {
		List<Planetary> listOfAllPlanetary = planetaryService.getAllPlanetary();
		if (listOfAllPlanetary == null) {
			return new ResponseEntity<String>("Sorry Could Not Retrieve Data From DB", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Planetary>>(listOfAllPlanetary, HttpStatus.OK);
	}

	@RequestMapping(path = "/delete", method = RequestMethod.DELETE, produces = "plain/text")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Remove Planetary Resource", notes = "This will remove thee Planetary Resource From The DB", response = String.class)
	public ResponseEntity<?> removePlanetaryResource(
			@ApiParam(value = "id", required = true, example = "123") @RequestParam(value = "id", required = true) Long id) {
		String response = planetaryService.deletePlanetaryByID(id);
		if (response == null) {
			return new ResponseEntity<String>("Sorry Could Not Remove Planetary Resource From DB",
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@RequestMapping(path = "/update", method = RequestMethod.PATCH, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update Planetary Resource", notes = "Update Planetary Resource By ID", response = Planetary.class)
	public ResponseEntity<?> updatePlanetaryResource(@RequestBody String planetary,
			@ApiParam(value = "id", required = true, example = "123") @RequestParam(value = "id", required = true) Long id)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, JSONException,
			org.json.simple.parser.ParseException, JsonMappingException, JsonProcessingException {

		logger.info("************************Inside Update Resource Method***************************");
		Planetary planetaryResponse = planetaryService.updatePlanetaryResourceByID(planetary, id);
		if (planetaryResponse == null) {
			return new ResponseEntity<String>("Sorry Could Not Update The Planetary Resource", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Planetary>(planetaryResponse, HttpStatus.OK);
	}
}
