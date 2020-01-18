package com.springboot.issues1.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.springboot.issues1.Beans.Planetary;
import com.springboot.issues1.Exception.MyException;
import com.springboot.issues1.Repository.PlanetaryRepository;
import com.springboot.issues1.Utility.ReflectionUtil;

@Service
public class PlanetaryService {

	private static final Logger logger = LoggerFactory.getLogger(PlanetaryService.class);

	private ReflectionUtil refUtil = ReflectionUtil.getInstance();

	@Autowired
	private ObjectMapper objectMapper;

	@PostConstruct
	public void setUp() {
		objectMapper.registerModule(new JavaTimeModule());

	}

	@Autowired
	private PlanetaryRepository planetaryRepository;

	public Planetary createResource(Planetary planetary) {
		Planetary planetaryFromDB = null;
		try {
			planetaryFromDB = planetaryRepository.save(planetary);
			if (planetaryFromDB == null) {
				throw new MyException("Could Not Create Planetary Resource");
			}
			return planetaryFromDB;
		} catch (final MyException ex) {
			logger.info(ex.getMessage());
		}
		return planetaryFromDB;
	}

	public Planetary getPlanetaryResource(Long id) {
		Planetary planetaryFromDB = null;
		try {
			planetaryFromDB = planetaryRepository.getPlanetary(id);
			if (planetaryFromDB == null) {
				throw new MyException("Sorry Could Not Retrieve Data From The Database");
			}
		} catch (final MyException ex) {
			logger.info(ex.getMessage());
		}
		return planetaryFromDB;

	}

	public List<Planetary> getAllPlanetary() {
		// TODO Auto-generated method stub
		List<Planetary> listOfPlanetary = null;
		try {
			listOfPlanetary = planetaryRepository.getAllPlanetary();
			if (listOfPlanetary.size() == 0 || listOfPlanetary == null) {
				throw new MyException("Sorry Could Not Retrieve Even A Single Resource From DB");
			}
		} catch (final MyException ex) {
			logger.info(ex.getMessage());
		}
		return listOfPlanetary;
	}

	public String deletePlanetaryByID(Long id) {
		// TODO Auto-generated method stub

		String response = null;
		try {
			planetaryRepository.deletePlanetaryById(id);
			response = "Successfully Deteted" + " Id" + id;
		} catch (final MyException ex) {
			logger.info(ex.getMessage());
		}
		return response;
	}

	public Planetary updatePlanetaryResourceByID(String planetary, Long id)
			throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			JSONException, JsonMappingException, JsonProcessingException {
		// TODO Auto-generated method stub
		Planetary planetaryFromDB = getPlanetaryResource(id);

		Planetary planetaryFromPayload = objectMapper.readValue(planetary, Planetary.class);
		Date date = planetaryFromPayload.getDate();
		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject) parser.parse(planetary);
			for (Iterator iterator = ((Map<String, String>) object).keySet().iterator(); iterator.hasNext();) {
				String propName = (String) iterator.next();

				if (propName.equals("date")) {
					planetaryFromDB.setDate(date);

				} else {
					refUtil.getSetterMethod("Planetary", propName).invoke(planetaryFromDB, object.get(propName));
				}

			}
		} catch (final MyException ex) {
			logger.info(ex.getMessage());
		}

		Planetary planetaryResponse = planetaryRepository.save(planetaryFromDB);
		return planetaryResponse;
	}
}
