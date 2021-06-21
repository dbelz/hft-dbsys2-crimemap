package de.hft.softec.dbsys2.crimemap;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class CrimemapApplication {

	private Logger logger = LoggerFactory.getLogger(CrimemapApplication.class);

	@Autowired
	private CrimeRepository crimeRepos;
	@Autowired
	private DistrictRepository districtRepos;
	@Autowired
	private OffenseRepository offenseRepos;

	public static void main(String[] args) {
		SpringApplication.run(CrimemapApplication.class, args);
	}

	@GetMapping
	public String getCrimes(Model model) {

		List<Crime> crimes = crimeRepos.findAll();
		model.addAttribute("crimes", crimes);

		return "crimes";

	}

	@GetMapping("/edit-crime/{id}")
	public String editCrime(@PathVariable("id") long id, Model model) {

		logger.info("--- editCrime with ID: " + id);
		Crime crime = crimeRepos.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid crime Id:" + id));

		model.addAttribute("crime", crime);
		model.addAttribute("districts", districtRepos.findAll());
		model.addAttribute("offenses", offenseRepos.findAll());
		return "edit-crime";
	}

	@GetMapping("/edit-crime")
	public String editCrime(Model model) {

		logger.info("--- editCrime with new crime");

		model.addAttribute("crime", new Crime());
		model.addAttribute("districts", districtRepos.findAll());
		model.addAttribute("offenses", offenseRepos.findAll());
		return "edit-crime";
	}

	@PostMapping("/save-crime")
	public String saveCrime(Crime crime, Model model) {

		logger.info("--- saveCrime with ID: " + crime.getId());
		logger.debug("CRIME: " + crime.toString());

		crimeRepos.save(crime);
		return "redirect:/";
	}





	// https://www.baeldung.com/spring-boot-crud-thymeleaf





	// ------------------------------------------------------------------------
	@RequestMapping(value = "/crimes", method = RequestMethod.GET)
	@ResponseBody
	public String listAllCrimes() {

		logger.info("--- listAllCrimes");

		List<Crime> crimes = crimeRepos.findAll();
		String geoJSONString = GeoJSON.convertToGeoJSON(crimes);

		return geoJSONString;

	}

	// // ------------------------------------------------------------------------
	// @RequestMapping(value="/crimes", method=RequestMethod.POST)
	// public String addCrime(@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateTime,
	// 				@RequestParam(required = true) int zip,
	// 				@RequestParam(required = true) String city,
	// 				@RequestParam(required = true) String address,
	// 				@RequestParam(required = true) BigDecimal lat,
	// 				@RequestParam(required = true) BigDecimal lon,
	// 				@RequestParam(required = true) String offense,
	// 				@RequestParam(required = false) String description,
	// 				@RequestParam(required = false) String urlToPolicePressRelease) {
		
	// 	Crime crime = new Crime(dateTime, zip, city, address, lat, lon, offense);
	// 	if (StringUtils.hasText(description)) {
	// 		crime.setDescription(description);
	// 	}
	// 	if (StringUtils.hasText(urlToPolicePressRelease)) {
	// 		crime.setUrlToPolicePressRelease(urlToPolicePressRelease);
	// 	}

	// 	repository.save(crime);
	// 	return "New crime added: " + crime.toString();

	// }

	// @Bean
	// public CommandLineRunner loadOffenseData(OffenseRepository repository) {

	// 	return (args) -> {

	// 		repository.save(new Offense("Burglary")); // Einbrüche
	// 		repository.save(new Offense("Accident")); // Unfälle
	// 		repository.save(new Offense("Property crime")); // Eigentumsdelikte
	// 		repository.save(new Offense("Sex crime")); // Sexualdelikte
	// 		repository.save(new Offense("Drugs")); // Drogendelikte
	// 		repository.save(new Offense("Fire")); // Brände
	// 		repository.save(new Offense("Violent act")); // Gewalttaten
	// 		repository.save(new Offense("Drunkenness")); // Trunkenheit
	// 		repository.save(new Offense("Damage to property")); // Sachbeschädigungen
			
	// 	};

	// }

	// @Bean
	// public CommandLineRunner loadDistrictData(DistrictRepository repository) {

	// 	return (args) -> {

	// 		repository.save(new District("Bad Cannstatt"));
	// 		repository.save(new District("Birkach"));
	// 		repository.save(new District("Botnang"));
	// 		repository.save(new District("Degerloch"));
	// 		repository.save(new District("Feuerbach"));
	// 		repository.save(new District("Hedelfingen"));
	// 		repository.save(new District("Möhringen"));
	// 		repository.save(new District("Mühlhausen"));
	// 		repository.save(new District("Münster"));
	// 		repository.save(new District("Obertürkheim"));
	// 		repository.save(new District("Plieningen"));
	// 		repository.save(new District("Sillenbuch"));
	// 		repository.save(new District("Stammheim"));
	// 		repository.save(new District("Stuttgart‐Mitte"));
	// 		repository.save(new District("Stuttgart‐Nord"));
	// 		repository.save(new District("Stuttgart‐Ost"));
	// 		repository.save(new District("Stuttgart‐Süd"));
	// 		repository.save(new District("Stuttgart‐West"));
	// 		repository.save(new District("Untertürkheim"));
	// 		repository.save(new District("Vaihingen"));
	// 		repository.save(new District("Wangen"));
	// 		repository.save(new District("Weilimdorf"));
	// 		repository.save(new District("Zuffenhausen"));
			
	// 	};

	// }


	

}
