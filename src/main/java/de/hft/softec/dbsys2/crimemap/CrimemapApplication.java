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

	@GetMapping("/delete-crime/{id}")
	public String deleteCrime(@PathVariable("id") long id, Model model) {
		Crime crime = crimeRepos.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid crime Id:" + id));
		crimeRepos.delete(crime);
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

	@Bean
	public CommandLineRunner loadOffenseData(OffenseRepository repository) {

		return (args) -> {

			repository.save(new Offense(1, "Burglary")); // Einbrüche
			repository.save(new Offense(2, "Accident")); // Unfälle
			repository.save(new Offense(3, "Property crime")); // Eigentumsdelikte
			repository.save(new Offense(4, "Sex crime")); // Sexualdelikte
			repository.save(new Offense(5, "Drugs")); // Drogendelikte
			repository.save(new Offense(6, "Fire")); // Brände
			repository.save(new Offense(7, "Violent act")); // Gewalttaten
			repository.save(new Offense(8, "Drunkenness")); // Trunkenheit
			repository.save(new Offense(9, "Damage to property")); // Sachbeschädigungen

		};

	}

	@Bean
	public CommandLineRunner loadDistrictData(DistrictRepository repository) {

		return (args) -> {

			repository.save(new District(1, "Bad Cannstatt"));
			repository.save(new District(2, "Birkach"));
			repository.save(new District(3, "Botnang"));
			repository.save(new District(4, "Degerloch"));
			repository.save(new District(5, "Feuerbach"));
			repository.save(new District(6, "Hedelfingen"));
			repository.save(new District(7, "Möhringen"));
			repository.save(new District(8, "Mühlhausen"));
			repository.save(new District(9, "Münster"));
			repository.save(new District(10, "Obertürkheim"));
			repository.save(new District(11, "Plieningen"));
			repository.save(new District(12, "Sillenbuch"));
			repository.save(new District(13, "Stammheim"));
			repository.save(new District(14, "Stuttgart‐Mitte"));
			repository.save(new District(15, "Stuttgart‐Nord"));
			repository.save(new District(16, "Stuttgart‐Ost"));
			repository.save(new District(17, "Stuttgart‐Süd"));
			repository.save(new District(18, "Stuttgart‐West"));
			repository.save(new District(19, "Untertürkheim"));
			repository.save(new District(20, "Vaihingen"));
			repository.save(new District(21, "Wangen"));
			repository.save(new District(22, "Weilimdorf"));
			repository.save(new District(23, "Zuffenhausen"));

		};

	}

}
