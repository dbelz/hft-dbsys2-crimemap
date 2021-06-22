package de.hft.softec.dbsys2.crimemap;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

		logger.debug("--- getCrimes");

		List<Crime> crimes = crimeRepos.findAll();
		model.addAttribute("filter", new Filter());
		model.addAttribute("crimes", crimes);
		model.addAttribute("districts", districtRepos.findAll());
		model.addAttribute("offenses", offenseRepos.findAll());

		return "crimes";

	}

	@RequestMapping(value = "/", method = RequestMethod.POST, params = "submit")
	public String getFilteredCrimes(Filter filter, Model model) {

		logger.info("--- getFilteredCrimes");
		logger.debug("     * district: " + filter.getDistrict());
		logger.debug("     * offense: " + filter.getOffense());
		logger.debug("     * dateStart: " + filter.getDateStart());
		logger.debug("     * dateEnd: " + filter.getDateEnd());

		Date startDate = filter.getDateStart();
		if (startDate == null) {
			startDate = new Date(0);
		}

		Date endDate = filter.getDateEnd();
		if (endDate == null) {
			endDate = new Date();
		}

		List<Crime> crimes;
		if (filter.getDistrict() > 0 & filter.getOffense() > 0) {
			Offense offense = offenseRepos.findById(filter.getOffense()).orElseThrow(() -> new IllegalArgumentException("Invalid offense Id:" + filter.getOffense()));
			District district = districtRepos.findById(filter.getDistrict()).orElseThrow(() -> new IllegalArgumentException("Invalid district Id:" + filter.getDistrict()));
			crimes = crimeRepos.findAllByOffenseAndDistrictAndDateOfCrimeBetween(offense, district, startDate, endDate);
		} else if (filter.getDistrict() > 0) {
			District district = districtRepos.findById(filter.getDistrict()).orElseThrow(() -> new IllegalArgumentException("Invalid district Id:" + filter.getDistrict()));
			crimes = crimeRepos.findAllByDistrictAndDateOfCrimeBetween(district, startDate, endDate);
		} else if (filter.getOffense() > 0) {
			Offense offense = offenseRepos.findById(filter.getOffense()).orElseThrow(() -> new IllegalArgumentException("Invalid offense Id:" + filter.getOffense()));
			crimes = crimeRepos.findAllByOffenseAndDateOfCrimeBetween(offense, startDate, endDate);
		} else {
			crimes = crimeRepos.findAllByDateOfCrimeBetween(startDate, endDate);
		}

		model.addAttribute("filter", filter);
		model.addAttribute("crimes", crimes);
		model.addAttribute("districts", districtRepos.findAll());
		model.addAttribute("offenses", offenseRepos.findAll());

		return "crimes";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, params = "reset")
	public String resetCrimeFilter(Filter filter, Model model) {

		// FIXME: This is actually the same as getCrimes()
		List<Crime> crimes = crimeRepos.findAll();
		model.addAttribute("filter", new Filter());
		model.addAttribute("crimes", crimes);
		model.addAttribute("districts", districtRepos.findAll());
		model.addAttribute("offenses", offenseRepos.findAll());

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

	@RequestMapping(value = "/save-crime", method = RequestMethod.POST, params = "submit")
	public String saveCrime(Crime crime, Model model) {

		logger.info("--- saveCrime with ID: " + crime.getId());
		logger.debug("CRIME: " + crime.toString());

		crimeRepos.save(crime);
		return "redirect:/";
	}

	@RequestMapping(value = "/save-crime", method = RequestMethod.POST, params = "cancel")
	public String cancelSaveCrime(Crime crime, Model model) {

		logger.info("--- CANCEL saveCrime with ID: " + crime.getId());
		return "redirect:/";

	}

	@GetMapping("/delete-crime/{id}")
	public String deleteCrime(@PathVariable("id") long id, Model model) {
		Crime crime = crimeRepos.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid crime Id:" + id));
		crimeRepos.delete(crime);
		return "redirect:/";
	}

	// ------------------------------------------------------------------------
	// REST API
	// ------------------------------------------------------------------------
	@RequestMapping(value = "/crimes", method = RequestMethod.GET)
	@ResponseBody
	public String getCrimesGeoJSON(
			@RequestParam(name="o", defaultValue = "0", required = false) long offenseId, 
			@RequestParam(name="d", defaultValue = "0", required = false) long districtId,
			@RequestParam(name="ds", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart,
			@RequestParam(name="de", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd) {

		logger.info("--- getCrimesGeoJSON");
		logger.debug("     * district: " + districtId);
		logger.debug("     * offense: " + offenseId);
		logger.debug("     * dateStart: " + dateStart);
		logger.debug("     * dateEnd: " + dateEnd);

		if (dateStart == null) {
			dateStart = new Date(0);
		}

		if (dateEnd == null) {
			dateEnd = new Date();
		}

		List<Crime> crimes;
		if (districtId > 0 & offenseId > 0) {
			Offense offense = offenseRepos.findById(offenseId).orElseThrow(() -> new IllegalArgumentException("Invalid offense Id:" + offenseId));
			District district = districtRepos.findById(districtId).orElseThrow(() -> new IllegalArgumentException("Invalid district Id:" + districtId));
			crimes = crimeRepos.findAllByOffenseAndDistrictAndDateOfCrimeBetween(offense, district, dateStart, dateEnd);
		} else if (districtId > 0) {
			District district = districtRepos.findById(districtId).orElseThrow(() -> new IllegalArgumentException("Invalid district Id:" + districtId));
			crimes = crimeRepos.findAllByDistrictAndDateOfCrimeBetween(district, dateStart, dateEnd);
		} else if (offenseId > 0) {
			Offense offense = offenseRepos.findById(offenseId).orElseThrow(() -> new IllegalArgumentException("Invalid offense Id:" + offenseId));
			crimes = crimeRepos.findAllByOffenseAndDateOfCrimeBetween(offense, dateStart, dateEnd);
		} else {
			crimes = crimeRepos.findAllByDateOfCrimeBetween(dateStart, dateEnd);
		}

		return GeoJSON.convertToGeoJSON(crimes);

	}

	// @Bean
	// public CommandLineRunner loadOffenseData(OffenseRepository repository) {

	// 	return (args) -> {

	// 		repository.save(new Offense(1, "Burglary")); // Einbrüche
	// 		repository.save(new Offense(2, "Accident")); // Unfälle
	// 		repository.save(new Offense(3, "Property crime")); // Eigentumsdelikte
	// 		repository.save(new Offense(4, "Sex crime")); // Sexualdelikte
	// 		repository.save(new Offense(5, "Drugs")); // Drogendelikte
	// 		repository.save(new Offense(6, "Fire")); // Brände
	// 		repository.save(new Offense(7, "Violent act")); // Gewalttaten
	// 		repository.save(new Offense(8, "Drunkenness")); // Trunkenheit
	// 		repository.save(new Offense(9, "Damage to property")); // Sachbeschädigungen

	// 	};

	// }

	// @Bean
	// public CommandLineRunner loadDistrictData(DistrictRepository repository) {

	// 	return (args) -> {

	// 		repository.save(new District(1, "Bad Cannstatt"));
	// 		repository.save(new District(2, "Birkach"));
	// 		repository.save(new District(3, "Botnang"));
	// 		repository.save(new District(4, "Degerloch"));
	// 		repository.save(new District(5, "Feuerbach"));
	// 		repository.save(new District(6, "Hedelfingen"));
	// 		repository.save(new District(7, "Möhringen"));
	// 		repository.save(new District(8, "Mühlhausen"));
	// 		repository.save(new District(9, "Münster"));
	// 		repository.save(new District(10, "Obertürkheim"));
	// 		repository.save(new District(11, "Plieningen"));
	// 		repository.save(new District(12, "Sillenbuch"));
	// 		repository.save(new District(13, "Stammheim"));
	// 		repository.save(new District(14, "Stuttgart‐Mitte"));
	// 		repository.save(new District(15, "Stuttgart‐Nord"));
	// 		repository.save(new District(16, "Stuttgart‐Ost"));
	// 		repository.save(new District(17, "Stuttgart‐Süd"));
	// 		repository.save(new District(18, "Stuttgart‐West"));
	// 		repository.save(new District(19, "Untertürkheim"));
	// 		repository.save(new District(20, "Vaihingen"));
	// 		repository.save(new District(21, "Wangen"));
	// 		repository.save(new District(22, "Weilimdorf"));
	// 		repository.save(new District(23, "Zuffenhausen"));

	// 	};

	// }

}
