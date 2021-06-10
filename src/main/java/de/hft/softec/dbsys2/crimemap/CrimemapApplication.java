package de.hft.softec.dbsys2.crimemap;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CrimemapApplication {

	private Logger logger = LoggerFactory.getLogger(CrimemapApplication.class);

	@Autowired
	private CrimeRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(CrimemapApplication.class, args);
	}

	// ------------------------------------------------------------------------
	@RequestMapping(value = "/crimes", method = RequestMethod.GET)
	public String listAllCrimes() {

		logger.info("--- listAllCrimes");

		List<Crime> crimes = repository.findAll();
		String geoJSONString = GeoJSON.convertToGeoJSON(crimes);

		return geoJSONString;

	}

	// ------------------------------------------------------------------------
	@RequestMapping(value="/crimes", method=RequestMethod.POST)
	public String addCrime(@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateTime,
					@RequestParam(required = true) int zip,
					@RequestParam(required = true) String city,
					@RequestParam(required = true) String address,
					@RequestParam(required = true) BigDecimal lat,
					@RequestParam(required = true) BigDecimal lon,
					@RequestParam(required = true) String offense,
					@RequestParam(required = false) String description,
					@RequestParam(required = false) String urlToPolicePressRelease) {
		
		Crime crime = new Crime(dateTime, zip, city, address, lat, lon, offense);
		if (StringUtils.hasText(description)) {
			crime.setDescription(description);
		}
		if (StringUtils.hasText(urlToPolicePressRelease)) {
			crime.setUrlToPolicePressRelease(urlToPolicePressRelease);
		}

		repository.save(crime);
		return "New crime added: " + crime.toString();

	}


	

}
